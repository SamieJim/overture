package org.overture.prettyprinter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.EDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AUndefinedExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.PStm;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.AFieldField;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.PType;
import org.overture.ast.util.Utils;

public class PrettyPrinterVisitorDefinitions extends
		QuestionAnswerAdaptor<PrettyPrinterEnv, String>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5018749137104836194L;
	final static TypePrettyPrinterVisitor typePrinter = new TypePrettyPrinterVisitor();

	@Override
	public String caseAClassClassDefinition(AClassClassDefinition node,
			PrettyPrinterEnv question) throws AnalysisException
	{
		StringBuffer sb = new StringBuffer();
		question.setClassName(node.getName().name);

		sb.append("class " + node.getName());
		sb.append("\n");

		// print types
		printDefsToStringBuffer(sb, node, question, EDefinition.TYPE);

		printDefsToStringBuffer(sb, node, question, EDefinition.VALUE);

		printDefsToStringBuffer(sb, node, question, EDefinition.INSTANCEVARIABLE);

		printDefsToStringBuffer(sb, node, question, EDefinition.EXPLICITOPERATION);

		printDefsToStringBuffer(sb, node, question, EDefinition.EXPLICITFUNCTION);

		sb.append("end " + node.getName());
		return sb.toString();
	}

	private void printDefsToStringBuffer(StringBuffer sb,
			AClassClassDefinition node, PrettyPrinterEnv question,
			EDefinition kind) throws AnalysisException
	{
		List<PDefinition> defs = getDefinitions(node.getDefinitions(), kind);

		if (defs.isEmpty())
		{
			return;
		}

		switch (kind)
		{
			case TYPE:
			{
				sb.append("types\n");
				question.increaseIdent();
				for (PDefinition def : defs)
				{
					sb.append(def.apply(this, question));
					sb.append("\n");
				}
				question.decreaseIdent();
			}
				break;
			case VALUE:
			{
				sb.append("values\n");
				question.increaseIdent();
				for (PDefinition def : defs)
				{
					sb.append(def.apply(this, question));
					sb.append("\n");
				}
				question.decreaseIdent();
			}
				break;
			case INSTANCEVARIABLE:
			{
				sb.append("instance variables\n");
				question.increaseIdent();
				for (PDefinition def : defs)
				{
					sb.append(def.apply(this, question));
					sb.append("\n");
				}
				question.decreaseIdent();
			}
				break;
			case EXPLICITOPERATION:
			{
				sb.append("operations\n");
				question.increaseIdent();
				for (PDefinition def : defs)
				{
					sb.append(def.apply(this, question));
					sb.append("\n");
				}
				question.decreaseIdent();
			}
				break;
			case EXPLICITFUNCTION:
			{
				sb.append("functions\n");
				question.increaseIdent();
				for (PDefinition def : defs)
				{
					sb.append(def.apply(this, question));
					sb.append("\n");
				}
				question.decreaseIdent();
			}
				break;
			default:
				break;
		}
		sb.append("\n");
	}

	private List<PDefinition> getDefinitions(
			LinkedList<PDefinition> definitions, EDefinition kind)
	{
		List<PDefinition> result = new Vector<PDefinition>();

		for (PDefinition pDefinition : definitions)
		{
			if (pDefinition.kindPDefinition() == kind)
			{
				result.add(pDefinition);
			}
		}

		return result;
	}

	@Override
	public String caseAInstanceVariableDefinition(
			AInstanceVariableDefinition node, PrettyPrinterEnv question)
			throws AnalysisException
	{
		StringBuilder sb = new StringBuilder(question.getIdent());
		sb.append( node.getName()+":"+node.getType().apply(typePrinter,question)+(node.getExpression()!=null?" := "+node.getExpression():""));
		return sb.toString() + ";";
	}

	@Override
	public String caseAValueDefinition(AValueDefinition node,
			PrettyPrinterEnv question) throws AnalysisException
	{
		StringBuilder sb = new StringBuilder(question.getIdent());
		sb.append( node.getPattern()+(node.getType() == null ? "" : ":" + node.getType().apply(typePrinter,question)) +(node.getExpression()!=null? " = " +node.getExpression():""));//node.toString());
		return sb.toString() + ";";
	}

	@Override
	public String caseATypeDefinition(ATypeDefinition node,
			PrettyPrinterEnv question) throws AnalysisException
	{
		StringBuilder sb = new StringBuilder(question.getIdent());
		
		sb.append(node.getAccess().getAccess()+" ");
		sb.append(node.getName()
				+ (node.getType() instanceof ARecordInvariantType ? " :: "
						: " = ") + node.getType().apply(typePrinter, question));
		return sb.toString() + ";";
	}

	@Override
	public String defaultPType(PType node, PrettyPrinterEnv question)
			throws AnalysisException
	{
		return node.toString();
	}

	@Override
	public String caseARecordInvariantType(ARecordInvariantType node,
			PrettyPrinterEnv question) throws AnalysisException
	{
		StringBuilder sb = new StringBuilder();
		question.increaseIdent();
		sb.append("\n");
		for (AFieldField f : node.getFields())
		{
			sb.append(question.getIdent() + f.getTag() + " : "
					+ f.getType().apply(typePrinter, question) + "\n");
		}
		question.decreaseIdent();

		if (node.getFields().size() > 0)
		{
			sb.delete(sb.length() - 1, sb.length());
		}
		return sb.toString();
	}

	@Override
	public String caseAExplicitOperationDefinition(
			AExplicitOperationDefinition d, PrettyPrinterEnv question)
			throws AnalysisException
	{
		StringBuilder sb = new StringBuilder(question.getIdent());
		String type = ": ";

		if (d.getType().getParameters().isEmpty())
		{
			type += "() ";
		} else
		{
			for (Iterator<PType> iterator = d.getType().getParameters().iterator(); iterator.hasNext();)
			{
				type += iterator.next().apply(typePrinter,question);
				if (iterator.hasNext())
				{
					type += " * ";
				}

			}
		}

		type += " ==> " + d.getType().getResult().apply(typePrinter,question);

		String tmp = d.getAccess()
				+ " "
				+ d.getName()
				+ " "
				+ type
				+ "\n"
				+ question.getIdent()
				+ d.getName()
				+ "("
				+ Utils.listToString(d.getParameterPatterns())
				+ ")"
				+ (d.getBody() == null ? "" : " ==\n"
						+ question.increaseIdent() + d.getBody()
						+ question.decreaseIdent().trim())
				+ (d.getPrecondition() == null ? "" : "\n"
						+ question.getIdent() + "pre " + d.getPrecondition())
				+ (d.getPostcondition() == null ? "" : "\n"
						+ question.getIdent() + "post " + d.getPostcondition());
		sb.append(tmp + ";\n");
		return sb.toString();
	}

	@Override
	public String caseAExplicitFunctionDefinition(
			AExplicitFunctionDefinition d, PrettyPrinterEnv question)
			throws AnalysisException
	{

		StringBuilder params = new StringBuilder();

		for (List<PPattern> plist : d.getParamPatternList())
		{
			params.append("(" + Utils.listToString(plist) + ")");
		}

		String accessStr = d.getAccess().toString();
		if (d.getNameScope() == NameScope.LOCAL)
			accessStr = "";

		String type = ": ";
		if (d.getType().getParameters().isEmpty())
		{
			type += "() ";
		} else
		{
			for (Iterator<PType> iterator = d.getType().getParameters().iterator(); iterator.hasNext();)
			{
				type += iterator.next().apply(typePrinter,question);
				if (iterator.hasNext())
				{
					type += " * ";
				}

			}
		}

		type += " " + (d.getType().getPartial() ? "-" : "+") + "> "
				+ d.getType().getResult().apply(typePrinter,question);

		String tmp = question.getIdent()
				+ accessStr
				+ d.getName().name
				+ type
				+ "\n"
				+ question.getIdent()
				+ d.getName().name
				+ params
				+ " ==\n"
				+ question.increaseIdent()
				+ d.getBody().apply(this, question)
				+ question.decreaseIdent().trim()
				+ (d.getPrecondition() == null ? "" : "\n"
						+ question.getIdent() + "pre " + d.getPrecondition())
				+ (d.getPostcondition() == null ? "" : "\n"
						+ question.getIdent() + "post " + d.getPostcondition());

		return tmp + ";\n";
	}
	
	

	@Override
	public String defaultPStm(PStm node, PrettyPrinterEnv question)
			throws AnalysisException
	{
		return node.toString();
	}
	

	@Override
	public String defaultPExp(PExp node, PrettyPrinterEnv question)
			throws AnalysisException
	{
		return node.toString();
	}
	
	@Override
	public String caseAUndefinedExp(AUndefinedExp node,
			PrettyPrinterEnv question) throws AnalysisException
	{
		return "undefined";
	}

	
	
}
