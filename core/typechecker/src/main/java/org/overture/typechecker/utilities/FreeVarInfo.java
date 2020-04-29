/*******************************************************************************
 *
 *	Copyright (c) 2017 Fujitsu Services Ltd.
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

package org.overture.typechecker.utilities;

import java.util.concurrent.atomic.AtomicBoolean;

import org.overture.typechecker.Environment;

/**
 * An information class for the FreeVariablesChecker visitor.
 */
public class FreeVarInfo
{
	public final Environment globals;
	public final Environment env;
	public final AtomicBoolean returns;
	
	public FreeVarInfo(Environment globals, Environment env, AtomicBoolean returns)
	{
		this.globals = globals;
		this.env = env;
		this.returns = returns;
	}

	public FreeVarInfo(Environment globals, Environment env, boolean b)
	{
		this.globals = globals;
		this.env = env;
		this.returns = new AtomicBoolean(b);
	}

	public FreeVarInfo set(Environment local)
	{
		return new FreeVarInfo(globals, local, returns);
	}
}
