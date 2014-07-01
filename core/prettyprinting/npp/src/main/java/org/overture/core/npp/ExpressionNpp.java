package org.overture.core.npp;

import javax.security.sasl.RealmCallback;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.expressions.AMuExp;
import org.overture.ast.expressions.APlusNumericBinaryExp;
import org.overture.ast.expressions.ARealLiteralExp;
import org.overture.ast.expressions.ASubtractNumericBinaryExp;
import org.overture.ast.expressions.ATimesNumericBinaryExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.node.INode;
import org.overture.ast.types.ARealNumericBasicType;

class ExpressionNpp extends QuestionAnswerAdaptor<IndentTracker, String>
		implements IPrettyPrinter
{

	ISymbolTable mytable;
	IPrettyPrinter rootNpp;

	private static String EXPRESSION_NOT_FOUND = "ERROR: Expression Node not found";
	private static String space = " ";

	public ExpressionNpp(NewPrettyPrinter root, ISymbolTable nst)
	{
		rootNpp = root;
		mytable = nst;
	}

	@Override
	public void setInsTable(ISymbolTable it)
	{
		mytable = it;
	}

	@Override
	public String caseAPlusNumericBinaryExp(APlusNumericBinaryExp node,
			IndentTracker question) throws AnalysisException
	{
		String l = node.getLeft().apply(this, question);
		String r = node.getRight().apply(this, question);
		String op = mytable.getPLUS();

		StringBuilder sb = new StringBuilder();

		sb.append(l);
		sb.append(space);
		sb.append(op);
		sb.append(space);
		sb.append(r);

		return Utilities.wrap(sb.toString());
	}
	
	@Override
	public String caseASubtractNumericBinaryExp(ASubtractNumericBinaryExp node,
			IndentTracker question) throws AnalysisException
	{
		String l = node.getLeft().apply(this, question);
		String r = node.getRight().apply(this, question);
		String op = mytable.getMINUS();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(l);
		sb.append(space);
		sb.append(op);
		sb.append(space);
		sb.append(r);
		
		return Utilities.wrap(sb.toString());
	}
	
	@Override
	public String caseATimesNumericBinaryExp(ATimesNumericBinaryExp node,
			IndentTracker question) throws AnalysisException
	{
		String l = node.getLeft().apply(THIS, question);
		String r = node.getRight().apply(THIS,question);
		String op = mytable.getTIMES();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(l);
		sb.append(space);
		sb.append(op);
		sb.append(space);
		sb.append(r);
		
		return Utilities.wrap(sb.toString());
	}

	@Override
	public String caseAIntLiteralExp(AIntLiteralExp node, IndentTracker question)
			throws AnalysisException
	{
		return Long.toString(node.getValue().getValue());
	}
	
	@Override
	public String caseARealLiteralExp(ARealLiteralExp node,
			IndentTracker question) throws AnalysisException
	{
		return Double.toString(node.getValue().getValue());
	}
	
	@Override
	public String caseAVariableExp(AVariableExp node, IndentTracker question)
			throws AnalysisException
	{
		String var = node.getOriginal();
		
		return var;
		
	}

	@Override
	public String createNewReturnValue(INode node, IndentTracker question)
			throws AnalysisException
	{
		return EXPRESSION_NOT_FOUND;
	}

	@Override
	public String createNewReturnValue(Object node, IndentTracker question)
			throws AnalysisException
	{
		return EXPRESSION_NOT_FOUND;
	}

}
