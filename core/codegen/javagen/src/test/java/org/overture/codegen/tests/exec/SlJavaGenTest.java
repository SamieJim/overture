package org.overture.codegen.tests.exec;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.lex.Dialect;
import org.overture.codegen.tests.exec.base.JavaGenTestBase;
import org.overture.codegen.tests.exec.util.testhandlers.ExecutableSpecTestHandler;
import org.overture.codegen.tests.exec.util.testhandlers.TestHandler;
import org.overture.codegen.tests.output.SlOutputTest;
import org.overture.config.Release;

import java.io.File;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class SlJavaGenTest extends JavaGenTestBase
{
	public SlJavaGenTest(String name, File vdmSpec, TestHandler testHandler)
	{
		super(vdmSpec, testHandler);
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> getData()
	{
		return collectTests(new File(SlOutputTest.ROOT), new ExecutableSpecTestHandler(Release.VDM_10, Dialect.VDM_SL));
	}

	@Override
	protected String getPropertyId()
	{
		return "sl";
	}
}
