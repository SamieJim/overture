package org.overture.codegen.vdm2java;

import java.util.List;

import org.overture.codegen.assistant.AssistantManager;
import org.overture.codegen.cgast.INode;
import org.overture.codegen.cgast.declarations.AClassDeclCG;
import org.overture.codegen.cgast.declarations.AFieldDeclCG;
import org.overture.codegen.cgast.expressions.AAddrEqualsBinaryExpCG;
import org.overture.codegen.cgast.expressions.AAddrNotEqualsBinaryExpCG;
import org.overture.codegen.cgast.expressions.AApplyExpCG;
import org.overture.codegen.cgast.expressions.AEqualsBinaryExpCG;
import org.overture.codegen.cgast.expressions.AExplicitVarExpCG;
import org.overture.codegen.cgast.expressions.AFieldExpCG;
import org.overture.codegen.cgast.expressions.AFieldNumberExpCG;
import org.overture.codegen.cgast.expressions.AHeadUnaryExpCG;
import org.overture.codegen.cgast.expressions.AInSetBinaryExpCG;
import org.overture.codegen.cgast.expressions.AIndicesUnaryExpCG;
import org.overture.codegen.cgast.expressions.ANewExpCG;
import org.overture.codegen.cgast.expressions.ANotEqualsBinaryExpCG;
import org.overture.codegen.cgast.expressions.ASetProperSubsetBinaryExpCG;
import org.overture.codegen.cgast.expressions.ASetSubsetBinaryExpCG;
import org.overture.codegen.cgast.expressions.ASizeUnaryExpCG;
import org.overture.codegen.cgast.expressions.PExpCG;
import org.overture.codegen.cgast.statements.AApplyObjectDesignatorCG;
import org.overture.codegen.cgast.statements.AForAllStmCG;
import org.overture.codegen.cgast.statements.AIdentifierObjectDesignatorCG;
import org.overture.codegen.cgast.types.AClassTypeCG;
import org.overture.codegen.cgast.types.ARecordTypeCG;
import org.overture.codegen.cgast.types.ATupleTypeCG;
import org.overture.codegen.cgast.types.PTypeCG;
import org.overture.codegen.cgast.types.SMapTypeCG;
import org.overture.codegen.cgast.types.SSeqTypeCG;
import org.overture.codegen.cgast.types.SSetTypeCG;

public class ValueSemantics
{
	private JavaFormat javaFormat;
	private JavaSettings javaSettings;
	
	public ValueSemantics(JavaFormat javaFormat)
	{
		this.javaFormat = javaFormat;
		this.javaSettings = new JavaSettings();
	}
	
	public void setJavaSettings(JavaSettings javaSettings)
	{
		this.javaSettings = javaSettings;
	}
	
	public boolean cloneMember(AFieldNumberExpCG exp)
	{
		if(javaSettings.getDisableCloning())
			return false;
		
		//Generally tuples need to be cloned, for example, if they
		//contain a record field (that must be cloned)
		
		if(exp.parent() instanceof AFieldNumberExpCG)
			return false;
		
		PTypeCG type = exp.getTuple().getType();
		
		if(type instanceof ATupleTypeCG)
		{
			
			ATupleTypeCG tupleType = (ATupleTypeCG) type;
			
			long field = exp.getField();
			PTypeCG fieldType = tupleType.getTypes().get((int) (field - 1));
			
			if(usesStructuralEquivalence(fieldType))
				return true;
		}
		
		return false;
	}
	
	public boolean cloneMember(AFieldExpCG exp)
	{
		if(javaSettings.getDisableCloning())
			return false;
		
		INode parent = exp.parent();
		if (cloneNotNeeded(parent))
			return false;
		
		PTypeCG type = exp.getObject().getType();
		
		if(type instanceof ARecordTypeCG)
		{
			ARecordTypeCG recordType = (ARecordTypeCG) type;
			
			String memberName = exp.getMemberName();
			
			List<AClassDeclCG> classes = javaFormat.getClasses();
			AssistantManager assistantManager = javaFormat.getAssistantManager();
			
			AFieldDeclCG memberField = assistantManager.getDeclAssistant().getFieldDecl(classes, recordType, memberName);
			
			if (memberField != null && usesStructuralEquivalence(memberField.getType()))
				return true;
		}
		
		return false;
	}
	
	public boolean shouldClone(PExpCG exp) 
	{
		if(javaSettings.getDisableCloning())
			return false;
		
		INode parent = exp.parent();
		if (cloneNotNeeded(parent))
		{
			return false;
		}
		
		PTypeCG type = exp.getType();
		
		if(parent instanceof AIdentifierObjectDesignatorCG)
		{
			//Don't clone the variable associated with an identifier object designator
			return false;
		}
		else if(parent instanceof AApplyObjectDesignatorCG)
		{
			//No need to clone the expression - we only use it for lookup
			return usesStructuralEquivalence(exp.getType()) && javaFormat.findElementType((AApplyObjectDesignatorCG) parent) == null;
		}
		else if(usesStructuralEquivalence(type))
		{
			if(parent instanceof ANewExpCG)
			{
				ANewExpCG newExp = (ANewExpCG) parent;
				PTypeCG newExpType = newExp.getType();
				
				if(usesStructuralEquivalence(newExpType))
					return false;
			}
			
			return true;
		}
		
		return false;
	}

	private boolean cloneNotNeeded(INode parent)
	{
		return 	   parent instanceof AFieldExpCG
				|| parent instanceof AFieldNumberExpCG
				|| parent instanceof AApplyExpCG
				|| parent instanceof AEqualsBinaryExpCG
				|| parent instanceof ANotEqualsBinaryExpCG
				|| parent instanceof AAddrEqualsBinaryExpCG
				|| parent instanceof AAddrNotEqualsBinaryExpCG
				|| parent instanceof AForAllStmCG
				|| cloneNotNeededCollectionOperator(parent)
				|| cloneNotNeededUtilCall(parent);
	}
	
	private boolean cloneNotNeededCollectionOperator(INode parent)
	{
		return cloneNotNeededSeqOperators(parent)
				|| cloneNotNeededSetOperators(parent);
	}

	private boolean cloneNotNeededSeqOperators(INode parent)
	{
		return parent instanceof ASizeUnaryExpCG
				|| parent instanceof AIndicesUnaryExpCG
				|| parent instanceof AHeadUnaryExpCG;
	}

	private boolean cloneNotNeededSetOperators(INode parent)
	{
		return parent instanceof AInSetBinaryExpCG
				|| parent instanceof ASetSubsetBinaryExpCG
				|| parent instanceof ASetProperSubsetBinaryExpCG;
	}
	
	private boolean cloneNotNeededUtilCall(INode node)
	{
		if(!(node instanceof AApplyExpCG))
			return false;
		
		AApplyExpCG applyExp = (AApplyExpCG) node;
		PExpCG root = applyExp.getRoot();
		
		if(!(root instanceof AExplicitVarExpCG))
			return false;
		
		AExplicitVarExpCG explicitVar = (AExplicitVarExpCG) root;
		
		AClassTypeCG classType = explicitVar.getClassType();
		
		return classType != null && classType.getName().equals(JavaFormat.UTILS_FILE);
	}
	
	private boolean usesStructuralEquivalence(PTypeCG type)
	{
		return type instanceof ARecordTypeCG || type instanceof ATupleTypeCG
				|| type instanceof SSeqTypeCG || type instanceof SSetTypeCG
				|| type instanceof SMapTypeCG;
	}
}
