package org.overturetool.cgisa.transformations;

import org.overture.cgisa.isair.analysis.DepthFirstAnalysisIsaAdaptor;
import org.overture.codegen.ir.IRInfo;
import org.overture.codegen.ir.declarations.AModuleDeclIR;
import org.overture.codegen.ir.declarations.ANamedTypeDeclIR;
import org.overture.codegen.ir.declarations.ATypeDeclIR;
import org.overture.codegen.ir.types.ASeqSeqTypeIR;
import org.overture.codegen.ir.types.ASetSetTypeIR;
import org.overture.codegen.trans.assistants.TransAssistantIR;

import java.util.Map;
import java.util.stream.Collectors;

/***
 * Visitor to convert sequence or set VDM types to VDMToolkit types
 */
public class IsaTypeTypesConv extends DepthFirstAnalysisIsaAdaptor {

    private final Map<String, ATypeDeclIR> isaTypeDeclIRMap;
    private final TransAssistantIR t;
    private final AModuleDeclIR vdmToolkitModuleIR;
    private final IRInfo info;

    private final static String VDMSet = "isa_VDMSet";

    private final static String VDMSeq = "isa_VDMSeq";

    public IsaTypeTypesConv(IRInfo info, TransAssistantIR t, AModuleDeclIR vdmToolkitModuleIR) {
        this.t = t;
        this.info = info;
        this.vdmToolkitModuleIR = vdmToolkitModuleIR;

        this.isaTypeDeclIRMap = this.vdmToolkitModuleIR.getDecls()
                .stream()
                .filter(d -> {
                    return d instanceof ATypeDeclIR;
                }).map(d -> (ATypeDeclIR) d)
                .collect(Collectors.toMap(x -> ((ANamedTypeDeclIR) x.getDecl()).getName().getName(), x -> x));
    }
    
   //transform seq into VDMSeq
    public void caseASeqSeqTypeIR(ASeqSeqTypeIR x) {
    	if(x.getNamedInvType() == null)
        {
            
            // Retrieve VDMSeq from VDMToolkit
            ATypeDeclIR isa_td = isaTypeDeclIRMap.get(IsaTypeTypesConv.VDMSeq);

            x.setNamedInvType((ANamedTypeDeclIR)isa_td.getDecl().clone());
            
        }
    }
  //transform set into VDMSet
    public void caseASetSetTypeIR(ASetSetTypeIR x) {
    	if(x.getNamedInvType() == null)
        {
            
            // Retrieve VDMSet from VDMToolkit
            ATypeDeclIR isa_td = isaTypeDeclIRMap.get(IsaTypeTypesConv.VDMSet);

            x.setNamedInvType((ANamedTypeDeclIR)isa_td.getDecl().clone());
        }
    }
    
    
    
    
}
