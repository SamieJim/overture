package org.overture.core.testing.samples;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.factory.AstFactoryTC;
import org.overture.ast.lex.Dialect;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.modules.AModuleModules;
import org.overture.config.Settings;
import org.overture.core.testing.ParamExternalsTest;
import org.overture.core.testing.ParseTcFacade;
import org.overture.parser.lex.LexException;
import org.overture.parser.messages.VDMError;
import org.overture.parser.syntax.ParserException;
import org.overture.parser.util.ParserUtil;
import org.overture.parser.util.ParserUtil.ParserResult;
import org.overture.typechecker.ClassTypeChecker;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
import org.overture.typechecker.assistant.TypeCheckerAssistantFactory;
import org.overture.typechecker.util.TypeCheckerUtil;
import org.overture.typechecker.util.TypeCheckerUtil.TypeCheckResult;

import com.google.gson.reflect.TypeToken;

/**
 * A simple test to demo the use of external test inputs. This class reuses
 * {@link SampleTestResult} but rather than printing the entire test model, it just
 * prints a success/failure message regarding the type checking of the test
 * source. </p>
 * Also note that since this test works with external inputs, the data provider
 * is already set up in {@link ParamExternalsTest}. To launch these testing simply
 * use the property <code>-DexternalTestsPath=/path/to/files/</code>.
 * </p>
 * This test also demonstrates explicitl invocation of the parser and type checker,
 * per {@link org.overture.core.testing.ParamFineGrainTest}.
 * </p>
 * <b>Note:</b> Due to some quirks with Parameterized JUnit testing, if the
 * property is not set, the test will still launch, only with 0 cases. It's fine
 * in Maven but in Eclipse you will get a single test run that does nothing.
 *
 * @author ldc
 */
@RunWith(Parameterized.class)
public class SampleExternalsTest extends ParamExternalsTest<SampleTestResult> {

	// the update property for this test
	private static final String UPDATE_PROPERTY = "testing.update.testing.standard.SampleExternalsTest";

	/**
	 * For this test, the constructor only needs to pass the
	 * parameters up to super.
	 *
	 * @param nameParameter
	 * @param testParameter
	 * @param resultParameter
	 */
	public SampleExternalsTest(String nameParameter, String testParameter,
							   String resultParameter) {
		super(nameParameter, testParameter, resultParameter);
	}

	/**
	 * Override result comparison with the one from {@link SampleTestResult}. Notice
	 * that testInfo() is passed to the comparison method in order to print meaningful
	 * error messages.
	 */
	@Override
	public void compareResults(SampleTestResult actual, SampleTestResult expected) {
		SampleTestResult.compare(actual, expected, testInfo());
	}


	/**
	 * Process the VDM source. External inputs can be negative so we control the
	 * parsing and type checking ourselves. This makes the method much longer
	 * and more complex. If we were certain the sources were correct, we could
	 * just call a method from {@link ParseTcFacade}.
	 * </p>
	 * Remember that you must always track the VDM dialect of the source and the
	 * only way to do this with external inputs is by placing them in dialect
	 * folders.
	 * </p>>
	 * Because of this extra size and complexity it's a good idea to split up
	 * the method according to dialect and do some dispatching.
	 *
	 * @return a {@link SampleTestResult} with a list of error messages or an all
	 *         clear message
	 */
	@Override
	public SampleTestResult processSource() {
		// Use file names only in error messages
		LexLocation.absoluteToStringLocation = false;

		SampleTestResult r = new SampleTestResult();
		if (modelPath.contains("sltest")) {
			return processSl();
		}
		if (modelPath.contains("rttest")) {
			try {
				return processRt();
			} catch (ParserException e) {
				r.add("Error performing RT parsing in " + testName);
			} catch (LexException e) {
				r.add("Error performing RT lexing in " + testName);
			}
		}
		if (modelPath.contains("pptest")) {
			return processPp();
		}

		r = new SampleTestResult();
		r.add("Could not test " + testName
				+ " unable to locate model in path: " + modelPath);
		return r;
	}

	/**
	 * Return the update property for this test. In general, it's good practice
	 * to do put it in a constant and return that.
	 */
	@Override
	protected String getUpdatePropertyString() {
		return UPDATE_PROPERTY;
	}

	/**
	 * Return the {@link Type} or resust for this test. This is needed to help
	 * out with reflection in the deserialization of results.
	 */
	@Override
	public Type getResultType() {
		Type resultType = new TypeToken<SampleTestResult>() {
		}.getType();
		return resultType;
	}


// All the code below is for handling parser and type checker calls. Now you know why you should avoid it.

	private SampleTestResult processPp() {
		Settings.dialect = Dialect.VDM_PP;
		SampleTestResult r = new SampleTestResult();

		ParserResult<List<SClassDefinition>> pr = ParserUtil.parseOo(new File(
				modelPath));
		if (pr.errors.isEmpty()) {
			TypeCheckResult<List<SClassDefinition>> tr = TypeCheckerUtil
					.typeCheck(pr, pr.result, new ClassTypeChecker(pr.result));
			if (tr.errors.isEmpty()) {
				r.add(testName + " parses and type checks");
			} else {
				for (VDMError e : tr.errors) {
					r.add(makeErrorMsg(e));
				}
			}
		} else {
			for (VDMError e : pr.errors) {
				r.add(makeErrorMsg(e));
			}
		}
		return r;
	}

	private SampleTestResult processRt() throws ParserException, LexException {
		Settings.dialect = Dialect.VDM_RT;
		SampleTestResult r = new SampleTestResult();

		ParserResult<List<SClassDefinition>> pr = ParserUtil.parseOo(new File(
				modelPath));
		if (pr.errors.isEmpty()) {
			ITypeCheckerAssistantFactory af = new TypeCheckerAssistantFactory();
			List<SClassDefinition> classes = new Vector<SClassDefinition>();
			classes.addAll(pr.result);
			classes.add(AstFactoryTC.newACpuClassDefinition(af));
			classes.add(AstFactoryTC.newABusClassDefinition(af));
			TypeCheckResult<List<SClassDefinition>> tr = TypeCheckerUtil
					.typeCheck(pr, pr.result, new ClassTypeChecker(pr.result));
			if (tr.errors.isEmpty()) {
				r.add(testName + "parses and type checks");
			} else {
				for (VDMError e : tr.errors) {
					r.add(makeErrorMsg(e));
				}
			}
		} else {
			for (VDMError e : pr.errors) {
				r.add(makeErrorMsg(e));
			}
		}
		return r;
	}

	private SampleTestResult processSl() {
		Settings.dialect = Dialect.VDM_SL;
		SampleTestResult r = new SampleTestResult();

		File modelFile = new File(modelPath);

		ParserResult<List<AModuleModules>> pr = ParserUtil.parseSl(modelFile);
		if (pr.errors.isEmpty()) {
			TypeCheckResult<List<AModuleModules>> tr = TypeCheckerUtil
					.typeCheckSl(modelFile);
			if (tr.errors.isEmpty()) {
				r.add(testName + "parses and type checks");
			} else {
				for (VDMError e : tr.errors) {
					r.add(makeErrorMsg(e));
				}
			}
		} else {
			for (VDMError e : pr.errors) {
				r.add(makeErrorMsg(e));
			}
		}
		return r;
	}

	private String makeErrorMsg(VDMError e) {
		StringBuilder sb = new StringBuilder();

		sb.append("Error ");
		sb.append(e.number);
		sb.append(": ");
		sb.append(e.location.getFile().getName());
		sb.append(" at ");
		sb.append(e.location.getStartLine());
		sb.append(":");
		sb.append(e.location.getStartPos());
		sb.append(" ");
		sb.append(e.message.toString());

		return sb.toString();
	}
}
