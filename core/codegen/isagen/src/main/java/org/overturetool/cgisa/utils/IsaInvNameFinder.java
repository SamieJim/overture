package org.overturetool.cgisa.utils;

import org.overture.ast.types.AUnionType;
import org.overture.cgisa.isair.analysis.AnswerIsaAdaptor;
import org.overture.codegen.ir.INode;
import org.overture.codegen.ir.STypeIR;
import org.overture.codegen.ir.analysis.AnalysisException;
import org.overture.codegen.ir.declarations.AFieldDeclIR;
import org.overture.codegen.ir.declarations.ANamedTypeDeclIR;
import org.overture.codegen.ir.declarations.ARecordDeclIR;
import org.overture.codegen.ir.declarations.AStateDeclIR;
import org.overture.codegen.ir.expressions.ANotImplementedExpIR;
import org.overture.codegen.ir.types.*;

public class IsaInvNameFinder extends AnswerIsaAdaptor<String>
{
    public static String findName(INode node) throws AnalysisException {
        IsaInvNameFinder finder = new IsaInvNameFinder();
        return node.apply(finder);
    }

    @Override
    public String caseANamedTypeDeclIR(ANamedTypeDeclIR node) throws AnalysisException {
        return node.getName().getName();
    }
    
    @Override
    public String caseANotImplementedExpIR(ANotImplementedExpIR node) {
		return "True";
    	
    }
    
    @Override
    public String caseAStateDeclIR(AStateDeclIR node) throws AnalysisException {
        return node.getName();
    }
    
    @Override
    public String caseASetSetTypeIR(ASetSetTypeIR node) throws AnalysisException {
        return "SetElems";
    }
    
    @Override
    public String caseASeqSeqTypeIR(ASeqSeqTypeIR node) throws AnalysisException {
    	ANamedTypeDeclIR n = new ANamedTypeDeclIR();
    	return "SeqElems";
    }
    @Override
    public String caseANatNumericBasicTypeIR(ANatNumericBasicTypeIR node) throws AnalysisException {
    	return "VDMNat";
    }
    
    @Override
    public String caseAIntNumericBasicTypeIR(AIntNumericBasicTypeIR node) throws AnalysisException {
    	return "True";
    }
    
    @Override
    public String caseARealNumericBasicTypeIR(ARealNumericBasicTypeIR node) throws AnalysisException {
    	return "True";
    }
    
    @Override
    public String caseARatNumericBasicTypeIR(ARatNumericBasicTypeIR node) throws AnalysisException {
    	return "True";
    }
    
    
    @Override
    public String caseABoolBasicTypeIR(ABoolBasicTypeIR node) throws AnalysisException {
    	return "True";
    }
    
    @Override
    public String caseACharBasicTypeIR(ACharBasicTypeIR node) throws AnalysisException {
    	return "True";
    }
    
    @Override
    public String caseAMapMapTypeIR(AMapMapTypeIR node) throws AnalysisException {
    	return "True";
    }
    
    @Override
    public String caseATokenBasicTypeIR(ATokenBasicTypeIR node) throws AnalysisException {
    	return "True";
    }

    @Override
    public String caseAQuoteTypeIR(AQuoteTypeIR node) throws AnalysisException {
        return node.getNamedInvType().getName().toString();
    }

    @Override
    public String caseAUnionTypeIR(AUnionTypeIR node) throws AnalysisException {
        return node.getNamedInvType().getName().toString();
    }

    @Override
    public String caseARecordTypeIR(ARecordTypeIR node) throws AnalysisException {
        return node.getName().toString();
    }

    @Override
    public String caseAFieldDeclIR(AFieldDeclIR node) throws AnalysisException {
        return node.getName();
    }
    
    @Override
    public String caseANat1NumericBasicTypeIR(ANat1NumericBasicTypeIR node) throws AnalysisException {
    	return "VDMNat1";
    }
    
    @Override
    public String caseARecordDeclIR(ARecordDeclIR node) throws AnalysisException {
        return node.getName();
    }

    @Override
    public String createNewReturnValue(INode node) throws AnalysisException {
    	String typeName;
        STypeIR n = (STypeIR) node;
        //if not a toolkit or IR node type
    	if (n.getNamedInvType() == null) typeName = "True";
    	else typeName = n.getNamedInvType().getName().getName();
    	return typeName;	
    }

    @Override
    public String createNewReturnValue(Object node) throws AnalysisException {
    	String typeName;
        STypeIR n = (STypeIR) node;
        //if not a toolkit or IR node type
    	if (n.getNamedInvType() == null) typeName = "True";
    	else typeName = n.getNamedInvType().getName().getName();
    	return typeName;	
    }
}
