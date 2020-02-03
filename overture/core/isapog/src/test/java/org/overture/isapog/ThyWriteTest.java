package org.overture.isapog;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.modules.AModuleModules;
import org.overture.ast.node.INode;
import org.overture.core.testing.ParseTcFacade;

public class ThyWriteTest {

    private static final String modelPath = "src/test/resources/thywrite/model.vdmsl";
    private static final String thysPath = "src/test/resources/thywrite/";

    private static final String modelThy = thysPath + "DEFAULT.thy";
    private static final String posThy = thysPath + "DEFAULT_POs.thy";

    @Test
    public void fileWriteTest() throws IOException, AnalysisException,
            org.overture.codegen.ir.analysis.AnalysisException {
        List<INode> ast = ParseTcFacade.typedAst(modelPath, "ThyWrite");

        IsaPog isapo = new IsaPog(ast);
        isapo.writeThyFiles(thysPath);

        File modelFile = new File(modelThy);
        File posFile = new File(posThy);

        assertNotNull(modelFile);
        assertNotNull(posFile);
        assertTrue(modelFile.exists());
        assertTrue(posFile.exists());
        modelFile.deleteOnExit();
        posFile.deleteOnExit();
    }


}
