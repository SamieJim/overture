package org.overturetool.cgisa.transformations;

import org.overture.cgisa.isair.analysis.DepthFirstAnalysisIsaAdaptor;
import org.overture.codegen.ir.IRInfo;
import org.overture.codegen.ir.declarations.AModuleDeclIR;
import org.overture.codegen.ir.expressions.ASetDifferenceBinaryExpIR;
import org.overture.codegen.trans.assistants.TransAssistantIR;

/***
 * Visitor to convert basic VDM types to VDMToolkit types
 */
public class IsaExpConv extends DepthFirstAnalysisIsaAdaptor {
    private final TransAssistantIR t;
    private final AModuleDeclIR vdmToolkitModuleIR;
    private final IRInfo info;

    private final static String VDMInt = "isa_VDMInt";
    private final static String VDMToken = "isa_VDMToken";
    private final static String VDMNat1 = "isa_VDMNat1";
    private final static String VDMNat = "isa_VDMNat";

    public IsaExpConv(IRInfo info, TransAssistantIR t, AModuleDeclIR vdmToolkitModuleIR) {
        this.t = t;
        this.info = info;
        this.vdmToolkitModuleIR = vdmToolkitModuleIR;
    }


    //Transform int to VDMInt
    public void caseASetDifferenceBinaryExpIR(ASetDifferenceBinaryExpIR x){
        ASetDifferenceBinaryExpIR y = new ASetDifferenceBinaryExpIR();
        y.setLeft(x.getLeft());
        y.setRight(x.getRight());
        y.setMetaData(x.getMetaData());
        y.setSourceNode(x.getSourceNode());
        x.parent().getChildren(true).entrySet().forEach(e -> {
            if (e.getValue() instanceof ASetDifferenceBinaryExpIR) e.setValue(y);
        });
        x = y;
    }
}
