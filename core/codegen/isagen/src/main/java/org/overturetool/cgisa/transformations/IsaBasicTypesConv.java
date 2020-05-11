package org.overturetool.cgisa.transformations;

import org.overture.cgisa.isair.analysis.DepthFirstAnalysisIsaAdaptor;
import org.overture.codegen.ir.*;
import org.overture.codegen.ir.declarations.*;
import org.overture.codegen.ir.types.AIntNumericBasicTypeIR;
import org.overture.codegen.ir.types.ANat1NumericBasicTypeIR;
import org.overture.codegen.ir.types.ANatNumericBasicTypeIR;
import org.overture.codegen.ir.types.ATokenBasicTypeIR;
import org.overture.codegen.trans.assistants.TransAssistantIR;

import java.util.Map;
import java.util.stream.Collectors;

/***
 * Visitor to convert basic VDM types to VDMToolkit types
 */
public class IsaBasicTypesConv extends DepthFirstAnalysisIsaAdaptor {

    private final Map<String, ATypeDeclIR> isaTypeDeclIRMap;
    private final TransAssistantIR t;
    private final AModuleDeclIR vdmToolkitModuleIR;
    private final IRInfo info;

    private final static String VDMInt = "VDMInt";
    private final static String VDMToken = "VDMToken";
    private final static String VDMNat1 = "VDMNat1";
    private final static String VDMNat = "VDMNat";

    public IsaBasicTypesConv(IRInfo info, TransAssistantIR t, AModuleDeclIR vdmToolkitModuleIR) {
        this.t = t;
        this.info = info;
        this.vdmToolkitModuleIR = vdmToolkitModuleIR;

        this.isaTypeDeclIRMap = this.vdmToolkitModuleIR.getDecls()
                .stream()
                .filter(d -> {
                    if (d instanceof ATypeDeclIR)
                        return true;
                    else
                        return false;
                }).map(d -> (ATypeDeclIR) d)
                .collect(Collectors.toMap(x -> ((ANamedTypeDeclIR) x.getDecl()).getName().getName(), x -> x));
    }

    //Transform int to VDMInt
    public void caseAIntNumericBasicTypeIR(AIntNumericBasicTypeIR x){
    	 if(x.getNamedInvType() == null)
         {
             // Retrieve VDMInt from VDMToolkit
             ATypeDeclIR isa_td = isaTypeDeclIRMap.get(IsaBasicTypesConv.VDMInt);

             x.setNamedInvType((ANamedTypeDeclIR)isa_td.getDecl().clone());
         }

    }


    //transform nat1 to VDMNat1
    public void caseANat1NumericBasicTypeIR(ANat1NumericBasicTypeIR x){
        if(x.getNamedInvType() == null)
        {
            // Retrieve VDMNat1 from VDMToolkit
            ATypeDeclIR isa_td = isaTypeDeclIRMap.get(IsaBasicTypesConv.VDMNat1);

            x.setNamedInvType((ANamedTypeDeclIR)isa_td.getDecl().clone());
        }

    }
    //transform nat to VDMNat
    public void caseANatNumericBasicTypeIR(ANatNumericBasicTypeIR x) {
    	if(x.getNamedInvType() == null)
        {
            // Retrieve VDMNat from VDMToolkit
            ATypeDeclIR isa_td = isaTypeDeclIRMap.get(IsaBasicTypesConv.VDMNat);

            x.setNamedInvType((ANamedTypeDeclIR)isa_td.getDecl().clone());
        }
    }
  //transform token to VDMToken
    public void caseATokenBasicTypeIR(ATokenBasicTypeIR x) {
    	if(x.getNamedInvType() == null)
        {
            // Retrieve VDMToken from VDMToolkit
            ATypeDeclIR isa_td = isaTypeDeclIRMap.get(IsaBasicTypesConv.VDMToken);

            x.setNamedInvType((ANamedTypeDeclIR)isa_td.getDecl().clone());
        }
    }
}
