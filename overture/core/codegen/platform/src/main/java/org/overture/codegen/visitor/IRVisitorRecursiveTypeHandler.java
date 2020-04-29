package org.overture.codegen.visitor;

import java.util.Stack;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.types.AUnknownType;
import org.overture.ast.types.PType;
import org.overture.codegen.ir.IRInfo;
import org.overture.codegen.ir.STypeIR;
import org.overture.codegen.ir.types.AObjectTypeIR;

public class IRVisitorRecursiveTypeHandler extends IRVisitor<STypeIR>
{

	private Stack<PType> typeStack;

	public IRVisitorRecursiveTypeHandler(
			AbstractVisitorIR<IRInfo, STypeIR> visitor)
	{
		super(visitor);

		this.typeStack = new Stack<PType>();
	}

	@Override
	public STypeIR defaultINode(INode node, IRInfo question)
			throws AnalysisException
	{

		if (node instanceof PType)
		{

			if (contains((PType) node, question))
			{
				return new AObjectTypeIR();
			}

			typeStack.push((PType) node);

			STypeIR result = super.defaultINode(node, question);

			typeStack.pop();

			return result;
		}

		return super.defaultINode(node, question);
	}

	private boolean contains(PType type, IRInfo question)
	{
		for (PType e : typeStack)
		{
			// Everything equals the unknown type according to the type equality
			// checker so we give unknown types special treatment
			if (type instanceof AUnknownType)
			{
				if (e instanceof AUnknownType)
				{
					return true;
				} else
				{
					return false;
				}
			} else if (e instanceof AUnknownType)
			{
				if (type instanceof AUnknownType)
				{
					return true;
				} else
				{
					return false;
				}
			}
			// Now that we are sure that none of them are unknown types we use
			// the type equality checker
			else if (question.getTcFactory().createPTypeAssistant().equals(type, e)
					&& question.getTcFactory().createPTypeAssistant().equals(e, type))
			{
				return true;
			}
		}

		return false;
	}
}
