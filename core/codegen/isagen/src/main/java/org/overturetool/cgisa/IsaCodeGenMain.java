/*
 * #%~
 * VDM Code Generator
 * %%
 * Copyright (C) 2008 - 2014 Overture
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #~%
 */
package org.overturetool.cgisa;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.Dialect;
import org.overture.ast.modules.AModuleModules;
import org.overture.codegen.analysis.vdm.Renaming;
import org.overture.codegen.analysis.violations.InvalidNamesResult;
import org.overture.codegen.ir.CodeGenBase;
import org.overture.codegen.ir.IRConstants;
import org.overture.codegen.ir.IRSettings;
import org.overture.codegen.ir.IrNodeInfo;
import org.overture.codegen.printer.MsgPrinter;
import org.overture.codegen.utils.*;
import org.overture.config.Release;
import org.overture.config.Settings;
import org.overture.typechecker.util.TypeCheckerUtil;
import org.overture.typechecker.util.TypeCheckerUtil.TypeCheckResult;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class IsaCodeGenMain
{

	public static final String EXP_ARG = "-exp";
	public static final String FOLDER_ARG = "-folder";
	public static final String PRINT_ARG = "-print";
	public static final String OUTPUT_ARG = "-output";
	public static final String GEN_IR = "-genir";
	public static final String NO_WARNINGS = "-nowarnings";
	public static final String VDM_COMMENTS = "-vdmascomment";

	// Folder names
	private static final String GEN_MODEL_CODE_FOLDER = "main";
	private static final String GEN_TESTS_FOLDER = "test";

	public static void main(String[] args) throws IOException {
		long clock = System.currentTimeMillis();
		//Future support of RT & PP here
		Settings.release = Release.VDM_10;
		boolean printClasses = false;
		boolean printWarnings = true;

		if (args.length < 1)
		{
			usage("Too few arguments provided");
		}

		IRSettings irSettings = new IRSettings();
		irSettings.setCharSeqAsString(true);
		irSettings.setGeneratePreConds(true);
		irSettings.setGeneratePreCondChecks(true);
		irSettings.setGeneratePostConds(true);
		irSettings.setGenerateInvariants(true);
		irSettings.setGeneratePostCondChecks(true);
		IsaSettings isaSettings = new IsaSettings();
		isaSettings.setDisableCloning(false);

		List<String> listArgs = Arrays.asList(args);
		String exp = null;
		File outputDir = null;

		List<File> files = new LinkedList<File>();
		Settings.release = Release.VDM_10;
		Settings.dialect = Dialect.VDM_SL;

		boolean separateTestCode = false;
		boolean cgExpr = false;

		for (Iterator<String> i = listArgs.iterator(); i.hasNext();)
		{
			String arg = i.next();

			if (arg.equals(GEN_IR))
			{
				Settings.genir = true;
			}
			else if (arg.equals(NO_WARNINGS))
			{
				printWarnings = false;
			}
			else if (arg.equals(VDM_COMMENTS))
			{
				Settings.vdmcomments = true;
			}
			 else if (arg.equals(EXP_ARG))
			{
				cgExpr = true;
				if (i.hasNext())
				{
					exp = i.next();
				} else
				{
					usage(EXP_ARG + " requires a VDM expression");
				}
			} else if (arg.equals(PRINT_ARG))
			{
				printClasses = true;
			} else if (arg.equals(FOLDER_ARG))
			{
				if (i.hasNext())
				{
					File path = new File(i.next());

					if (path.isDirectory())
					{
						FileWriter in = new FileWriter("in.vdmsl");
						List<File> fileList = filterFiles(GeneralUtils.getFiles(path));
						StringBuffer sb = new StringBuffer();

						fileList.forEach(file -> {
							try {
								Files.lines(file.toPath()).forEach(l -> {
									sb.append(l);
									sb.append('\n');
								});
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						});

						in.write(sb.toString());
						in.close();

						File file = new File("in.vdmsl");
						files.add(file);
					} else
					{
						usage("Could not find path: " + path);
					}
				} else
				{
					usage(FOLDER_ARG + " requires a directory");
				}
			} else if (arg.equals(OUTPUT_ARG))
			{
				if (i.hasNext())
				{
					outputDir = new File(i.next());
					outputDir.mkdirs();

					if (!outputDir.isDirectory())
					{
						usage(outputDir + " is not a directory");
					}

				} else
				{
					usage(OUTPUT_ARG + " requires a directory");
				}
			}
			else
			{
				// It's a file or a directory
				File file = new File(arg);

				if (file.isFile())
				{
					if (GeneralCodeGenUtils.isVdmSourceFile(file))
					{
						files.add(file);
					}
				} else
				{
					usage("Not a file: " + file);
				}
			}
		}

		MsgPrinter.getPrinter().println("Starting code generation...\n");

		if (cgExpr)
		{
			handleExp(exp, irSettings, isaSettings, Settings.dialect);
		} else
		{
			if (files.isEmpty())
			{
				usage("Input files are missing");
			}

			if (outputDir == null && !printClasses)
			{
				MsgPrinter.getPrinter().println("No output directory specified - printing code generated classes instead..\n");
				printClasses = true;
			}

			handleSl(files, irSettings, isaSettings, printClasses, outputDir, separateTestCode, printWarnings);
		}
		clock = System.currentTimeMillis() - clock;
		MsgPrinter.getPrinter().println("\nFinished code generation! Bye... @" + (clock / 1000) + "s");
	}
	
	private static Generated generateIsaFromExp(String exp,
			IRSettings irSettings, IsaSettings isaSettings, Dialect dialect)
			throws AnalysisException
	{
		IsaGen vdmCodeGen = new IsaGen();
		vdmCodeGen.setSettings(irSettings);
		vdmCodeGen.setIsaSettings(isaSettings);

		return generateIsaFromExp(exp, vdmCodeGen, dialect);
	}
	
	private static Generated generateIsaFromExp(String exp,
			IsaGen vdmCodeGen, Dialect dialect) throws AnalysisException

	{
		Settings.dialect = dialect;
		TypeCheckResult<PExp> typeCheckResult = GeneralCodeGenUtils.validateExp(exp);

		if (typeCheckResult.errors.size() > 0)
		{
			throw new AnalysisException("Unable to type check expression: "
					+ exp);
		}

		try
		{
			//@TODO is this enough/right? LF
			Generated g = new Generated(IsaGen.vdmExp2IsaString(typeCheckResult.result));
			//return vdmCodeGen.generateJavaFromVdmExp(typeCheckResult.result);
			return g;

		} catch (AnalysisException
				| org.overture.codegen.ir.analysis.AnalysisException e)
		{
			throw new AnalysisException("Unable to generate code from expression: "
					+ exp + ". Exception message: " + e.getMessage());
		}
	}

	private static void handleExp(String exp, IRSettings irSettings,
			IsaSettings isaSettings, Dialect dialect)
	{
		try
		{
			Settings.release = Release.VDM_10;
			Settings.dialect = Dialect.VDM_PP;

			Generated generated = generateIsaFromExp(exp, irSettings, isaSettings, dialect);

			if (generated.hasMergeErrors())
			{
				MsgPrinter.getPrinter().println(String.format("VDM expression '%s' could not be merged. Following merge errors were found:", exp));
				GeneralCodeGenUtils.printMergeErrors(generated.getMergeErrors());
			} else if (!generated.canBeGenerated())
			{
				MsgPrinter.getPrinter().println("Could not generate VDM expression: "
						+ exp);

				if (generated.hasUnsupportedIrNodes())
				{
					GeneralCodeGenUtils.printUnsupportedIrNodes(generated.getUnsupportedInIr());
				}

				if (generated.hasUnsupportedTargLangNodes())
				{
					GeneralCodeGenUtils.printUnsupportedNodes(generated.getUnsupportedInTargLang());
				}

			} else
			{
				MsgPrinter.getPrinter().println("Code generated expression: "
						+ generated.getContent().trim());
			}
		} catch (AnalysisException e)
		{
			MsgPrinter.getPrinter().println("Could not code generate model: "
					+ e.getMessage());

		}
	}

	public static void handleSl(List<File> files, IRSettings irSettings,
			IsaSettings isaSettings, boolean printCode, File outputDir,
			boolean separateTestCode, boolean printWarnings)
	{
		try
		{
			IsaGen vdmCodGen = new IsaGen();
			vdmCodGen.setSettings(irSettings);
			vdmCodGen.setIsaSettings(isaSettings);

			Settings.dialect = Dialect.VDM_SL;
			TypeCheckResult<List<AModuleModules>> tcResult = TypeCheckerUtil.typeCheckSl(files);

			if (GeneralCodeGenUtils.hasErrors(tcResult))
			{
				MsgPrinter.getPrinter().error("Found errors in VDM model:");
				MsgPrinter.getPrinter().errorln(GeneralCodeGenUtils.errorStr(tcResult));
				return;
			}

			GeneratedData data = vdmCodGen.generate(CodeGenBase.getNodes(tcResult.result));

			processData(printCode, outputDir, vdmCodGen, data, separateTestCode, printWarnings);

		} catch (AnalysisException e)
		{
			MsgPrinter.getPrinter().println("Could not code generate model: "
					+ e.getMessage());
		}
	}

	public static void processData(boolean printCode, final File outputDir,
			IsaGen vdmCodGen, GeneratedData data, boolean separateTestCode,
			boolean printWarnings)
	{
		List<GeneratedModule> generatedClasses = data.getClasses();
		List<String> names = new ArrayList<>();
		int errors_ = 0;
		int warnings_ = 0;
		if (!generatedClasses.isEmpty())
		{
			for (GeneratedModule generatedClass : generatedClasses)
			{
				if (generatedClass.hasMergeErrors())
				{
					MsgPrinter.getPrinter().println(String.format("Class %s could not be merged. Following merge errors were found:", generatedClass.getName()));

					GeneralCodeGenUtils.printMergeErrors(generatedClass.getMergeErrors());
				} else
				{
					if (!generatedClass.canBeGenerated()) {
						MsgPrinter.getPrinter().println("Could not produce complete translation of class: "
								+ generatedClass.getName() + "\n");

						if (generatedClass.hasUnsupportedIrNodes()) {
							MsgPrinter.getPrinter().println("Following VDM constructs are not supported by the code generator:");
							errors_ += generatedClass.getUnsupportedInIr().size();
							GeneralCodeGenUtils.printUnsupportedIrNodes(generatedClass.getUnsupportedInIr());
						}

						if (generatedClass.hasUnsupportedTargLangNodes()) {
							MsgPrinter.getPrinter().println("Following constructs are not supported by the code generator:");
							errors_ += generatedClass.getUnsupportedInTargLang().size();
							GeneralCodeGenUtils.printUnsupportedNodes(generatedClass.getUnsupportedInTargLang());
						}
					}

					if (outputDir != null)
					{
						if (separateTestCode)
						{
							if (generatedClass.isTestCase())
							{
								vdmCodGen.genIsaSourceFile(new File(outputDir, GEN_TESTS_FOLDER), generatedClass);
							} else
							{
								vdmCodGen.genIsaSourceFile(new File(outputDir, GEN_MODEL_CODE_FOLDER), generatedClass);
							}
						} else
						{
							vdmCodGen.genIsaSourceFile(outputDir, generatedClass);
						}
					}

					names.add(generatedClass.getName());

					if (printCode)
					{
						MsgPrinter.getPrinter().println("**********");
						MsgPrinter.getPrinter().println(generatedClass.getContent());
						MsgPrinter.getPrinter().println("\n");
					} else
					{
						MsgPrinter.getPrinter().println("Generated class : "
								+ generatedClass.getName());
					}

					Set<IrNodeInfo> warnings = generatedClass.getTransformationWarnings();

					if (!warnings.isEmpty())
					{
						MsgPrinter.getPrinter().println("Following transformation warnings were found:");
						warnings_ += generatedClass.getTransformationWarnings().size();
						if (printWarnings) 
							GeneralCodeGenUtils.printUnsupportedNodes(generatedClass.getTransformationWarnings());
						else
							MsgPrinter.getPrinter().println("\t ...");
					}
				}
			}
		} else
		{
			MsgPrinter.getPrinter().println("No classes were generated!");
		}

		List<GeneratedModule> quotes = data.getQuoteValues();

		if (quotes != null && !quotes.isEmpty())
		{
			MsgPrinter.getPrinter().println("\nGenerated following quotes (" + quotes.size() + "):");
			
			if (outputDir != null)
			{
				for (GeneratedModule q : quotes)
				{
					if (separateTestCode)
					{
						vdmCodGen.genIsaSourceFile(new File(outputDir, GEN_MODEL_CODE_FOLDER), q);
					} else
					{
						vdmCodGen.genIsaSourceFile(outputDir, q);
					}
				}
			}

			for (GeneratedModule q : quotes)
			{
				MsgPrinter.getPrinter().print(q.getName() + " ");
			}

			MsgPrinter.getPrinter().println("");
		}

		InvalidNamesResult invalidName = data.getInvalidNamesResult();

		if (invalidName != null && !invalidName.isEmpty())
		{
			MsgPrinter.getPrinter().println(GeneralCodeGenUtils.constructNameViolationsString(invalidName));
		}

		List<Renaming> allRenamings = data.getAllRenamings();

		if (allRenamings != null && !allRenamings.isEmpty())
		{
			MsgPrinter.getPrinter().println("\nDue to variable shadowing or normalisation of Java identifiers the following renamings of variables have been made (" + allRenamings.size()+"): ");

			MsgPrinter.getPrinter().println(GeneralCodeGenUtils.constructVarRenamingString(allRenamings));
		}

		if (data.getWarnings() != null && !data.getWarnings().isEmpty() && printWarnings)
		{
			MsgPrinter.getPrinter().println("");
			for (String w : data.getWarnings())
			{
				MsgPrinter.getPrinter().println("[WARNING] " + w);
			}
		}
		List<String> missing = new ArrayList<String>();

		for (GeneratedModule c : generatedClasses)
		{
			missing.add(c.getName());
		}

		missing.removeAll(names);

		MsgPrinter.getPrinter().println("\n Generated " + names.size() + " classes out of " + generatedClasses.size() + " requested" +
				"\n\tMissing : " + missing.toString() +
				"\n\tErrors  : " + errors_ +
				"\n\tWarnings: " + warnings_);
	}

	public static List<File> filterFiles(List<File> files)
	{
		List<File> filtered = new LinkedList<File>();

		for (File f : files)
		{
			if (GeneralCodeGenUtils.isVdmSourceFile(f))
			{
				filtered.add(f);
			}
		}

		return filtered;
	}

	public static void usage(String msg)
	{
		MsgPrinter.getPrinter().errorln("VDM-to-Isabelle Code Generator: " + msg
				+ "\n\nUsage: IsaGen [-options] [file to translate]\n\n");
		MsgPrinter.getPrinter().errorln(EXP_ARG
				+ " <expression>: code generate a VDMPP expression");
		MsgPrinter.getPrinter().errorln(FOLDER_ARG
				+ " <folder path>: a folder containing input vdm source files");
		MsgPrinter.getPrinter().errorln(PRINT_ARG
				+ ": print the generated code to the console");
		MsgPrinter.getPrinter().errorln(VDM_COMMENTS
				+ ": generate the original VDM node above its translation");
		MsgPrinter.getPrinter().errorln(GEN_IR
				+ ": produce a folder containing IR nodes from which Isabelle/HOL was generated");
		MsgPrinter.getPrinter().errorln(OUTPUT_ARG
				+ " <folder path>: the output folder of the generated code");
		MsgPrinter.getPrinter().errorln(NO_WARNINGS
				+ ": To suppress printing detailed warning messages");

		// Terminate
		System.exit(1);
	}
}
