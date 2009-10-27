package org.overture.ide.builders.vdmj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.overturetool.vdmj.ExitStatus;
import org.overturetool.vdmj.Settings;
import org.overturetool.vdmj.VDMJ;
import org.overturetool.vdmj.definitions.ClassList;
import org.overturetool.vdmj.lex.Dialect;
import org.overturetool.vdmj.messages.InternalException;
import org.overturetool.vdmj.messages.VDMError;
import org.overturetool.vdmj.messages.VDMWarning;
import org.overturetool.vdmj.pog.ProofObligationList;
import org.overturetool.vdmj.runtime.Interpreter;
import org.overturetool.vdmj.typechecker.ClassTypeChecker;
import org.overturetool.vdmj.typechecker.TypeChecker;

/***
 * VDMJ interface used to build VDM-PP models
 * @author kela
 *
 */
public class EclipseVdmjPp extends VDMJ implements IEclipseVdmj {
	public ClassList modules;
	private ArrayList<VDMError> parseErrors = new ArrayList<VDMError>();
	private ArrayList<VDMWarning> parseWarnings = new ArrayList<VDMWarning>();
	private TypeChecker typeChecker;
	
	public EclipseVdmjPp(ClassList m) {
		modules=m;
		typeChecker = null;
		parseErrors = new ArrayList<VDMError>();
		parseWarnings = new ArrayList<VDMWarning>();
		Settings.dialect = Dialect.VDM_PP;
	}
	
	
	public List<VDMError> getParseErrors() {
		return parseErrors;
	}

	public List<VDMWarning> getParseWarnings() {
		return parseWarnings;
	}

	public List<VDMError> getTypeErrors() {
		return TypeChecker.getErrors();
	}

	public List<VDMWarning> getTypeWarnings() {
		return TypeChecker.getWarnings();
	}



	public ExitStatus typeCheck() {
		int terrs = 0;
		if(modules==null)
			return ExitStatus.EXIT_ERRORS; 
		
		long before = System.currentTimeMillis();

   		try
   		{
   			typeChecker = new ClassTypeChecker(modules);
   			typeChecker.typeCheck();
   		}
		catch (InternalException e)
		{
			TypeChecker.getErrors().add(new VDMError(e.number, e.getMessage(), modules.get(0).location));
			println(e.toString());
			
		}
		catch (Throwable e)
		{
			TypeChecker.getErrors().add(new VDMError(0, e.getMessage(), modules.get(0).location));
			println(e.toString());
			terrs++;
		}

   		long after = System.currentTimeMillis();
		terrs += TypeChecker.getErrorCount();

		if (terrs > 0)
		{
			//TypeChecker.printErrors(Console.out);
		}

  		int twarn = TypeChecker.getWarningCount();

		if (twarn > 0 && warnings)
		{
//			TypeChecker.printWarnings(Console.out);
		}

   		int n = modules.notLoaded();

   		if (n > 0)
   		{
    		info("Type checked " + plural(n, "module", "s") +
    			" in " + (double)(after-before)/1000 + " secs. ");
      		info(terrs == 0 ? "No type errors" :
      			"Found " + plural(terrs, "type error", "s"));
      		infoln(twarn == 0 ? "" : " and " +
      			(warnings ? "" : "suppressed ") + plural(twarn, "warning", "s"));
   		}

		if (outfile != null && terrs == 0)
		{
			try
			{
				before = System.currentTimeMillis();
    	        FileOutputStream fos = new FileOutputStream(outfile);
    	        GZIPOutputStream gos = new GZIPOutputStream(fos);
    	        ObjectOutputStream oos = new ObjectOutputStream(gos);

    	        oos.writeObject(modules);
    	        oos.close();
    	   		after = System.currentTimeMillis();

    	   		infoln("Saved " + plural(modules.size(), "module", "s") +
    	   			" to " + outfile + " in " +
    	   			(double)(after-before)/1000 + " secs. ");
			}
			catch (IOException e)
			{
				infoln("Cannot write " + outfile + ": " + e.getMessage());
				terrs++;
			}
		}

		if (pog && terrs == 0)
		{
			ProofObligationList list = modules.getProofObligations();

			if (list.isEmpty())
			{
				println("No proof obligations generated");
			}
			else
			{
    			println("Generated " +
    				plural(list.size(), "proof obligation", "s") + ":\n");
    			print(list.toString());
			}
		}

   		return terrs == 0 ? ExitStatus.EXIT_OK : ExitStatus.EXIT_ERRORS;
	}

	@Override
	public Interpreter getInterpreter() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ExitStatus interpret(List<File> arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ExitStatus parse(List<File> arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	protected static void println(String m)
	{
		//Console.out.println(m);
	}
	
	protected static void info(String m)
	{

	}

	protected static void infoln(String m)
	{

	}

	protected static void print(String m)
	{
	

	}
}
