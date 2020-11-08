package org.overture.vdm2jml.tests.exec;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.codegen.utils.GeneralUtils;
import org.overture.vdm2jml.tests.util.TestUtil;

import java.io.File;
import java.util.Collection;

@RunWith(Parameterized.class)
public class JmlMapTypeExecTests extends JmlExecTestBase
{
	public static final String TEST_DIR = JmlExecTestBase.TEST_RES_DYNAMIC_ANALYSIS_ROOT
			+ "maptype";

	public static final String PROPERTY_ID = "maptype";

	public JmlMapTypeExecTests(File inputFile)
	{
		super(inputFile);
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
