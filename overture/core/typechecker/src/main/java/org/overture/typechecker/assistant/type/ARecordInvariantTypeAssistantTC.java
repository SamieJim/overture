/*
 * #%~
 * The VDM Type Checker
 * %%
 * Copyright (C) 2008 - 2014 Overture
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #~%
 */
package org.overture.typechecker.assistant.type;

import org.overture.ast.assistant.IAstAssistant;
import org.overture.ast.types.AFieldField;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

public class ARecordInvariantTypeAssistantTC implements IAstAssistant
{
	protected ITypeCheckerAssistantFactory af;

	public ARecordInvariantTypeAssistantTC(ITypeCheckerAssistantFactory af)
	{
		this.af = af;
	}

	public AFieldField findField(ARecordInvariantType rec, String tag)
	{
		for (AFieldField f : rec.getFields())
		{
			if (f.getTag().equals(tag))
			{
				return f;
			}
		}

		return null;
	}

}
