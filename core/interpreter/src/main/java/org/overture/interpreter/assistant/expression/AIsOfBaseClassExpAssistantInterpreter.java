package org.overture.interpreter.assistant.expression;

import org.overture.ast.expressions.AIsOfBaseClassExp;
import org.overture.interpreter.assistant.IInterpreterAssistantFactory;
import org.overture.interpreter.values.ObjectValue;

public class AIsOfBaseClassExpAssistantInterpreter// extends
// AIsOfBaseClassExpAssistantTC
{
	protected static IInterpreterAssistantFactory af;

	@SuppressWarnings("static-access")
	public AIsOfBaseClassExpAssistantInterpreter(IInterpreterAssistantFactory af)
	{
		this.af = af;
	}

	public boolean search(AIsOfBaseClassExp node, ObjectValue from)
	{
		if (from.type.getName().getName().equals(node.getBaseClass().getName())
				&& from.superobjects.isEmpty())
		{
			return true;
		}

		for (ObjectValue svalue : from.superobjects)
		{
			if (search(node, svalue))
			{
				return true;
			}
		}

		return false;
	}

}
