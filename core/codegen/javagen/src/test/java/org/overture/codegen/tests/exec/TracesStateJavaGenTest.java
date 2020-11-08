package org.overture.codegen.tests.exec;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.lex.Dialect;
import org.overture.codegen.ir.IRSettings;
import org.overture.codegen.tests.exec.base.JavaGenTestBase;
import org.overture.codegen.tests.exec.util.testhandlers.TestHandler;
import org.overture.codegen.tests.exec.util.testhandlers.TraceHandler;
import org.overture.codegen.tests.output.TracesStateOutputTest;
import org.overture.config.Release;

import java.io.File;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class TracesStateJavaGenTest extends JavaGenTestBase
{
	public TracesStateJavaGenTest(String name, File vdmSpec,
			TestHandler testHandler)
	{
		super(vdmSpec, testHandler);
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> getData()
	{
		return collectTests(new File(TracesStateOutputTest.ROOT), new TraceHandler(Release.VDM_10, Dialect.VDM_PP));
	}

	@Override
	public IRSettings getIrSettings()
	{
		IRSettings irSettings = super.getIrSettings();
		irSettings.setGenerateTraces(true);

		return irSettings;
	}

	@Override
	protected String getPropertyId()
	{
		return "tracesstate";
	}
}
