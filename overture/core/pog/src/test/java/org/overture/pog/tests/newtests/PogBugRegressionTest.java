package org.overture.pog.tests.newtests;

import static org.junit.Assert.fail;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.core.testing.ParamStandardTest;
import org.overture.core.testing.PathsProvider;
import org.overture.pog.pub.IProofObligationList;
import org.overture.pog.pub.ProofObligationGenerator;

import com.google.gson.reflect.TypeToken;

/**
 * Quick usage example of new overture testing. Takes an AST and dumps the entire content into a string.
 * 
 * @author ldc
 */
@RunWith(Parameterized.class)
public class PogBugRegressionTest extends ParamStandardTest<PogTestResult>
{

	private static final String UPDATE_PROPERTY = "testing.update.pog.bugregression";

	public PogBugRegressionTest(String name, String testParameter,
			String resultParameter)
	{
		super(name, testParameter, resultParameter);
		// updateResult=true;
	}

	private static final String BUG_REGRESSION_ROOT = "src/test/resources/bug-regression";

	@Parameters(name = "{index} : {0}")
	public static Collection<Object[]> testData()
	{
		return PathsProvider.computePaths(BUG_REGRESSION_ROOT);
	}

	@Override
	public PogTestResult processModel(List<INode> ast)
	{
		try
		{
			IProofObligationList ipol = ProofObligationGenerator.generateProofObligations(ast);
			PogTestResult actual = PogTestResult.convert(ipol);
			return actual;

		} catch (AnalysisException e)
		{
			fail("Could not process test file " + testName);
		}
		// will never hit due to fail()
		return null;
	}

	@Override
	public Type getResultType()
	{
		Type resultType = new TypeToken<PogTestResult>()
		{
		}.getType();
		return resultType;
	}

	@Override
	protected String getUpdatePropertyString()
	{
		return UPDATE_PROPERTY;
	}

}
