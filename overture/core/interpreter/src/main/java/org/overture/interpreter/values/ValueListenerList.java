/*******************************************************************************
 *
 *	Copyright (c) 2010 Fujitsu Services Ltd.
 *
 *	Author: Nick Battle
 *
 *	This file is part of VDMJ.
 *
 *	VDMJ is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	VDMJ is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with VDMJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package org.overture.interpreter.values;

import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.interpreter.runtime.Context;

public class ValueListenerList extends Vector<ValueListener>
{
	private static final long serialVersionUID = 1L;

	public ValueListenerList(ValueListener listener)
	{
		add(listener);
	}

	public ValueListenerList(ValueListenerList list)
	{
		addAll(list);
	}

	public void changedValue(ILexLocation location, Value value, Context ctxt)
			throws AnalysisException
	{
		ValueListenerList copy = new ValueListenerList(this);
		
		for (ValueListener vl : copy)
		{
			vl.changedValue(location, value, ctxt);
		}
	}
}
