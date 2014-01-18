package org.overture.ide.plugins.codegen.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.node.INode;
import org.overture.codegen.analysis.violations.InvalidNamesException;
import org.overture.codegen.analysis.violations.UnsupportedModelingException;
import org.overture.codegen.analysis.violations.Violation;
import org.overture.codegen.assistant.LocationAssistantCG;
import org.overture.codegen.constants.IJavaCodeGenConstants;
import org.overture.codegen.constants.IOoAstConstants;
import org.overture.codegen.utils.AnalysisExceptionCG;
import org.overture.codegen.utils.GeneratedModule;
import org.overture.codegen.vdm2java.JavaCodeGen;
import org.overture.codegen.vdm2java.JavaCodeGenUtil;
import org.overture.ide.core.IVdmModel;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.core.resources.IVdmSourceUnit;
import org.overture.ide.plugins.codegen.Activator;
import org.overture.ide.plugins.codegen.CodeGenConsole;
import org.overture.ide.plugins.codegen.util.PluginVdm2JavaUtil;
import org.overture.ide.ui.utility.VdmTypeCheckerUi;

public class Vdm2JavaCommand extends AbstractHandler
{
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		//Validate project
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		
		if(!(selection instanceof IStructuredSelection))
			return null;
		
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		
		Object firstElement = structuredSelection.getFirstElement();
		
		if(!(firstElement instanceof IProject))
			return null;

		IProject project = ((IProject) firstElement);
		IVdmProject vdmProject = (IVdmProject) project.getAdapter(IVdmProject.class);
		
		deleteMarkers(project);
		
		// Validate model before attempting to code generate it
		if (vdmProject == null)
			return null;

		final IVdmModel model = vdmProject.getModel();
		
		if (model == null || !model.isParseCorrect())
			return null;

		if (!model.isTypeChecked())
			VdmTypeCheckerUi.typeCheck(HandlerUtil.getActiveShell(event), vdmProject);

		if (!model.isTypeCorrect()
				|| !PluginVdm2JavaUtil.isSupportedVdmDialect(vdmProject))
			return null;

		// Begin code generation
		final JavaCodeGen vdm2java = new JavaCodeGen();

		CodeGenConsole.GetInstance().show();

		try
		{
			CodeGenConsole.GetInstance().clearConsole();
			CodeGenConsole.GetInstance().println("Starting Java to VDM code generation...\n");

			File outputFolder = PluginVdm2JavaUtil.getOutputFolder(vdmProject);
			
			//Clean folder with generated Java code
			PluginVdm2JavaUtil.deleteFolderContents(outputFolder);

			// Generate user specified classes
			List<IVdmSourceUnit> sources = model.getSourceUnits();
			List<SClassDefinition> mergedParseLists = PluginVdm2JavaUtil.mergeParseLists(sources);			
			List<GeneratedModule> userspecifiedClasses = vdm2java.generateJavaFromVdm(mergedParseLists);
			vdm2java.generateJavaSourceFiles(outputFolder, userspecifiedClasses);
			outputUserspecifiedModules(outputFolder, userspecifiedClasses);

			// Quotes generation
			handleQuotesGeneration(vdmProject, outputFolder, vdm2java);

			// Utils generation
			handleUtilsGeneration(vdmProject, vdm2java);
			
			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());

		} catch (InvalidNamesException ex)
		{
			handleInvalidNames(ex);
		} catch (UnsupportedModelingException ex)
		{
			handleUnsupportedModeling(ex);
		} catch (AnalysisExceptionCG ex)
		{
			CodeGenConsole.GetInstance().println("Could not code generate VDM model: "
					+ ex.getMessage());
		} catch (Exception ex)
		{
			handleUnexpectedException(ex);
		}

		return null;
	}
	
	private void deleteMarkers(IProject project)
	{
		if(project == null)
			return;
		
		try
		{
			project.deleteMarkers(null, true, IResource.DEPTH_INFINITE);
		} catch (CoreException ex)
		{
			Activator.log("Could not delete markers for project: " + project.toString(), ex);
			ex.printStackTrace();
		}
	}

	private void outputUserspecifiedModules(File outputFolder, List<GeneratedModule> userspecifiedClasses)
	{
		for (GeneratedModule generatedModule : userspecifiedClasses)
		{
			if(generatedModule.canBeGenerated())
			{
				File javaFile = new File(outputFolder, generatedModule.getName() + IJavaCodeGenConstants.JAVA_FILE_EXTENSION);
				CodeGenConsole.GetInstance().println("Generated module: " + generatedModule.getName());
				CodeGenConsole.GetInstance().println("Java source file: " + javaFile.getAbsolutePath());
			}
			else
			{
				List<INode> nodes = LocationAssistantCG.getNodeLocationsSorted(generatedModule.getUnsupportedNodes());
				CodeGenConsole.GetInstance().println("Could not code generate module: " + generatedModule.getName() + ".");
				CodeGenConsole.GetInstance().println("Following constructs are not supported:");
				
				for(INode node : nodes)
				{
					String message = PluginVdm2JavaUtil.formatNodeString(node);
					CodeGenConsole.GetInstance().println(message);

					PluginVdm2JavaUtil.addMarkers(node);
					
				}
			}
			CodeGenConsole.GetInstance().println("");
		}
	}

	private void handleUtilsGeneration(IVdmProject vdmProject, JavaCodeGen vdm2java) throws IOException, CoreException
	{
		List<GeneratedModule> utils = vdm2java.generateJavaCodeGenUtils();
		File utilsFolder = PluginVdm2JavaUtil.getUtilsFolder(vdmProject);
		vdm2java.generateJavaSourceFiles(utilsFolder, utils);
	}

	private void handleQuotesGeneration(IVdmProject vdmProject, File outputFolder, JavaCodeGen vdm2java) throws CoreException
	{
		GeneratedModule quotes = vdm2java.generateJavaFromVdmQuotes();
		if(quotes != null)
		{
			File quotesFolder = PluginVdm2JavaUtil.getQuotesFolder(vdmProject);
			vdm2java.generateJavaSourceFile(quotesFolder, quotes);
			
			CodeGenConsole.GetInstance().println("Quotes interface generated.");
			File quotesFile = new File(outputFolder, IOoAstConstants.QUOTES_INTERFACE_NAME + IJavaCodeGenConstants.JAVA_FILE_EXTENSION);
			CodeGenConsole.GetInstance().println("Java source file: " + quotesFile.getAbsolutePath());
			CodeGenConsole.GetInstance().println("");
		}	
	}

	private void handleUnexpectedException(Exception ex)
	{
		String errorMessage = "Unexpected exception caught when attempting to code generate VDM model.";
		
		Activator.log(errorMessage, ex);
		
		CodeGenConsole.GetInstance().println(errorMessage);
		CodeGenConsole.GetInstance().println(ex.getMessage());
		ex.printStackTrace();
	}

	private void handleUnsupportedModeling(UnsupportedModelingException ex)
	{
		CodeGenConsole.GetInstance().println("Could not code generate VDM model: " + ex.getMessage());

		String violationStr = JavaCodeGenUtil.constructUnsupportedModelingString(ex);
		CodeGenConsole.GetInstance().println(violationStr);
		
		Set<Violation> violations = ex.getViolations();
		PluginVdm2JavaUtil.addMarkers("Modeling rule not supported", violations);
	}

	private void handleInvalidNames(InvalidNamesException ex)
	{
		CodeGenConsole.GetInstance().println("Could not code generate VDM model: " + ex.getMessage());

		String violationStr = JavaCodeGenUtil.constructNameViolationsString(ex);
		CodeGenConsole.GetInstance().println(violationStr);
		
		Set<Violation> typeNameViolations = ex.getTypenameViolations();
		PluginVdm2JavaUtil.addMarkers("Type name violation", typeNameViolations);
		
		Set<Violation> reservedWordViolations = ex.getReservedWordViolations();
		PluginVdm2JavaUtil.addMarkers("Reserved word violations", reservedWordViolations);
	}
}