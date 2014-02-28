package org.overture.codegen.transform;

import java.util.LinkedList;
import java.util.List;

import org.overture.codegen.assistant.ExpAssistantCG;
import org.overture.codegen.cgast.analysis.AnalysisException;
import org.overture.codegen.cgast.declarations.ALocalVarDeclCG;
import org.overture.codegen.cgast.expressions.AAndBoolBinaryExpCG;
import org.overture.codegen.cgast.expressions.ACastUnaryExpCG;
import org.overture.codegen.cgast.expressions.ANewExpCG;
import org.overture.codegen.cgast.expressions.ANotUnaryExpCG;
import org.overture.codegen.cgast.expressions.AStringLiteralExpCG;
import org.overture.codegen.cgast.expressions.AVariableExpCG;
import org.overture.codegen.cgast.expressions.PExpCG;
import org.overture.codegen.cgast.name.ATypeNameCG;
import org.overture.codegen.cgast.pattern.AIdentifierPatternCG;
import org.overture.codegen.cgast.statements.AAssignmentStmCG;
import org.overture.codegen.cgast.statements.ABlockStmCG;
import org.overture.codegen.cgast.statements.AForLoopStmCG;
import org.overture.codegen.cgast.statements.AIfStmCG;
import org.overture.codegen.cgast.statements.AThrowStmCG;
import org.overture.codegen.cgast.statements.PStmCG;
import org.overture.codegen.cgast.types.ABoolBasicTypeCG;
import org.overture.codegen.cgast.types.AClassTypeCG;
import org.overture.codegen.cgast.types.AStringTypeCG;
import org.overture.codegen.cgast.types.PTypeCG;
import org.overture.codegen.constants.IJavaCodeGenConstants;
import org.overture.codegen.constants.JavaTempVarPrefixes;
import org.overture.codegen.ooast.OoAstInfo;

public class LetBeStAssistantCG extends TransformationAssistantCG
{
	public ALocalVarDeclCG consSuccessVarDecl(String successVarName)
	{
		ALocalVarDeclCG successVarDecl = new ALocalVarDeclCG();
		
		successVarDecl.setType(new ABoolBasicTypeCG());
		successVarDecl.setName(successVarName);
		successVarDecl.setExp(ExpAssistantCG.consBoolLiteral(false));
		
		return successVarDecl;
	}
	
	public ABlockStmCG consForBody(PTypeCG setType, PExpCG suchThat, String id, String iteratorName, String successVarName) throws AnalysisException
	{
		ABlockStmCG forBody = new ABlockStmCG();
		
		LinkedList<PStmCG> stms = forBody.getStatements();
		
		stms.add(consNextElement(setType, id, iteratorName));
		
		return forBody;
	}

	private AAssignmentStmCG consNextElement(PTypeCG setType, String id, String iteratorName)
			throws AnalysisException
	{
		PTypeCG elementType = getSetTypeCloned(setType).getSetOf();

		ACastUnaryExpCG cast = new ACastUnaryExpCG();
		cast.setType(elementType.clone());
		cast.setExp(consInstanceCall(consIteratorType(), iteratorName, elementType.clone(), IJavaCodeGenConstants.NEXT_ELEMENT_ITERATOR, null));
		
		AAssignmentStmCG assignment = new AAssignmentStmCG();
		assignment.setTarget(consIdentifier(id));
		assignment.setExp(cast);

		return assignment;
	}
	
	public AAssignmentStmCG consSuccessAssignment(PExpCG suchThat, String successVarName)
	{
		AAssignmentStmCG successAssignment = new AAssignmentStmCG();

		successAssignment.setTarget(consIdentifier(successVarName));
		successAssignment.setExp(suchThat != null ? suchThat.clone() : ExpAssistantCG.consBoolLiteral(true));
		
		return successAssignment;
	}

	public PExpCG conForCondition(PExpCG set, String iteratorName, String successVarName) throws AnalysisException
	{
		AAndBoolBinaryExpCG andExp = new AAndBoolBinaryExpCG();
		
		andExp.setType(new ABoolBasicTypeCG());
		andExp.setLeft(consInstanceCall(consIteratorType(), iteratorName, getSetTypeCloned(set).getSetOf(), IJavaCodeGenConstants.HAS_NEXT_ELEMENT_ITERATOR, null));
		andExp.setRight(consSuccessCheck(successVarName));
		
		return andExp;
	}

	private PExpCG consSuccessCheck(String successVarName)
	{
		AVariableExpCG successVarExp = new AVariableExpCG();
		successVarExp.setType(new ABoolBasicTypeCG());
		successVarExp.setOriginal(successVarName);
		
		ANotUnaryExpCG notSuccess = new ANotUnaryExpCG();
		notSuccess.setType(new ABoolBasicTypeCG());
		notSuccess.setExp(successVarExp);

		return notSuccess;
	}
	
	private AThrowStmCG consThrowException()
	{
		AStringLiteralExpCG runtimeErrorMessage = new AStringLiteralExpCG();
		runtimeErrorMessage.setIsNull(false);
		runtimeErrorMessage.setType(new AStringTypeCG());
		runtimeErrorMessage.setValue("Let Be St found no applicable bindings");
		
		AClassTypeCG exceptionType = new AClassTypeCG();
		exceptionType.setName(IJavaCodeGenConstants.RUNTIME_EXCEPTION_TYPE_NAME);
		
		ATypeNameCG exceptionTypeName = new ATypeNameCG();
		exceptionTypeName.setDefiningClass(null);
		exceptionTypeName.setName(IJavaCodeGenConstants.RUNTIME_EXCEPTION_TYPE_NAME);
		
		ANewExpCG runtimeException = new ANewExpCG();
		runtimeException.setType(exceptionType);
		runtimeException.setName(exceptionTypeName);
		runtimeException.getArgs().add(runtimeErrorMessage);
		
		AThrowStmCG throwStm = new AThrowStmCG();
		throwStm.setExp(runtimeException);
		
		return throwStm;
	}
	
	public AIfStmCG consIfCheck(String successVarName)
	{
		AIfStmCG ifStm = new AIfStmCG();
		ifStm.setIfExp(consSuccessCheck(successVarName));
		ifStm.setThenStm(consThrowException());
		
		return ifStm;
	}
	
	public ABlockStmCG consBlock(List<AIdentifierPatternCG> ids, PExpCG set, PExpCG suchThat, OoAstInfo info)
			throws AnalysisException
	{
		PTypeCG setType = set.getType();
		
		//Variable names
		String setName = info.getTempVarNameGen().nextVarName(JavaTempVarPrefixes.SET_NAME_PREFIX);
		String successVarName = info.getTempVarNameGen().nextVarName(JavaTempVarPrefixes.SUCCESS_VAR_NAME_PREFIX);

		ABlockStmCG outerBlock = new ABlockStmCG();
		LinkedList<ALocalVarDeclCG> outerBlockDecls = outerBlock.getLocalDefs();

		outerBlockDecls.add(consSetBindDecl(setName, set));
		outerBlockDecls.add(consSuccessVarDecl(successVarName));
		
		ABlockStmCG nextBlock = outerBlock;
		
		int numberOfIds = ids.size();
		
		for (int i = 0;;)
		{
			AIdentifierPatternCG id = ids.get(i);

			//Add next id to outer block
			outerBlockDecls.add(consIdDecl(setType, id.getName()));

			//Construct next for loop
			String iteratorName = info.getTempVarNameGen().nextVarName(JavaTempVarPrefixes.ITERATOR_NAME_PREFIX);
			
			AForLoopStmCG forLoop = new AForLoopStmCG();
			forLoop.setInit(consIteratorDecl(iteratorName, setName));
			forLoop.setCond(conForCondition(set, iteratorName, successVarName));
			forLoop.setInc(null);
			
			ABlockStmCG forBody = consForBody(setType, suchThat, id.getName(), iteratorName, successVarName);
			forLoop.setBody(forBody);

			nextBlock.getStatements().add(forLoop);
			
			if (++i < numberOfIds) 
			{
				nextBlock = forBody;
			}
			else
			{
				forBody.getStatements().add(consSuccessAssignment(suchThat, successVarName));
				break;
			}
		}

		LinkedList<PStmCG> outerBlockStms = outerBlock.getStatements();
		outerBlockStms.add(consIfCheck(successVarName));
		return outerBlock;
	}
}