package org.overture.vdm2jml.tests.exec;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.codegen.utils.GeneralUtils;
import org.overture.vdm2jml.tests.util.TestUtil;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class JmlSimplePatternExecTests extends JmlExecTestBase
{
	public static final String TEST_DIR = JmlExecTestBase.TEST_RES_DYNAMIC_ANALYSIS_ROOT
			+ "simple_pattern";

	public static final String PROPERTY_ID = "simple_pattern";

	public JmlSimplePatternExecTests(File inputFile)
	{
		super(inputFile);
	}

	@Override
	protected List<String> getSkippedTestsNames()
	{
		return Arrays.asList("String.vdmsl");
	}

	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data()
	{
		return TestUtil.collectVdmslFiles(GeneralUtils.getFilesRecursively(new File(TEST_DIR)));
	}

	protected String getPropertyId()
	{
		return PROPERTY_ID;
	}
}
