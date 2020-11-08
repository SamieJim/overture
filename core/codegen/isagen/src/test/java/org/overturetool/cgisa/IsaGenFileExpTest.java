/*
 * #%~
 * VDM to Isabelle Translation
 * %%
 * Copyright (C) 2008 - 2015 Overture
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

import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.junit.Assume;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.codegen.utils.GeneratedModule;
import org.overture.core.testing.ParamFineGrainTest;
import org.overture.core.testing.ParseTcFacade;
import org.overture.core.testing.PathsProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Main parameterized test class. Runs tests on modules with minimal definitions to exercise the translation with a
 * single construct at a time.
 * 
 * @author ldc
 */
@RunWith(Parameterized.class)
public class IsaGenFileExpTest extends ParamFineGrainTest<CgIsaTestResult>
{

	public IsaGenFileExpTest(String nameParameter, String inputParameter,
			String resultParameter)
	{
		super(nameParameter, inputParameter, resultParameter);
	}

	private static final String UPDATE = "tests.update.isagen";
	private static final String EXPS_ROOT = "src/test/resources/exps";
	private static final List<String> skippedTests = Arrays.asList("DivideExp.vdmsl","MinusExp.vdmsl","TimesExp.vdmsl","DivExp.vdmsl","GTExp.vdmsl","NotEqualsExp.vdmsl","PlusExp.vdmsl","Subset.vdmsl","EqualsExp.vdmsl","OrExp.vdmsl","ForAllUsesVar.vdmsl","ForAll2Vars1Type.vdmsl","ForAll2Vars2Types.vdmsl","ForAll1Var1Type.vdmsl","SeqEnumApply.vdmsl","StringApply.vdmsl","EmptySetEnum.vdmsl","BoolLiteralTrue.vdmsl","BoolLiteralFalse.vdmsl","IsIntExp.vdmsl","NotExp.vdmsl","LetExpSingle.vdmsl","TernaryIf.vdmsl","LetExpMulti.vdmsl");
	
	@Parameters(name = "{index} : {0}")
	public static Collection<Object[]> testData()
	{
		return PathsProvider.computePaths(EXPS_ROOT);
	}

	@Override
	public Type getResultType()
	{
		Type resultType = new TypeToken<CgIsaTestResult>()
		{
		}.getType();
		return resultType;
	}

	@Override
	protected String getUpdatePropertyString()
	{
		return UPDATE;
	}

	@Override
	public void compareResults(CgIsaTestResult actual, CgIsaTestResult expected)
	{
		assertTrue("\n --- Expected: ---\n" + expected.translation
				+ "\n --- Got: ---\n" + actual.translation, expected.compare(actual));
	}

	@Override
	public CgIsaTestResult processSource()
	{
		IsaGen gen = new IsaGen();
		GeneratedModule result = null;
		try
		{
			String exp = FileUtils.readFileToString(new File(modelPath));

			PExp pexp = ParseTcFacade.parseTcExpressionString(exp);

			result = gen.generateIsabelleSyntax(pexp);
			if (!result.canBeGenerated())
			{
				StringBuilder sb = new StringBuilder();
				sb.append(result.getMergeErrors());
				sb.append(result.getUnsupportedInIr());
				sb.append(result.getUnsupportedInTargLang());
				fail(sb.toString());
			}
		} catch (AnalysisException
				| org.overture.codegen.ir.analysis.AnalysisException
				| IOException e)
		{
			fail("Could not process test file " + testName + "Reason: "
					+ e.getMessage());
			return null;
		}

		return CgIsaTestResult.convert(result);
	}

	@Override
	protected void checkAssumptions() {
		Assume.assumeTrue("Test in skip list.",notSkipped());
	}

	private boolean notSkipped() {
		return !skippedTests.contains(testName);
	}

}
