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
package org.overture.typechecker.utilities.type;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.AUnknownType;
import org.overture.ast.types.PType;
import org.overture.ast.types.SInvariantType;
import org.overture.ast.types.SSeqType;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

/**
 * Used to determine if a type is a seq type
 * 
 * @author kel
 */
public class SeqBasisChecker extends TypeUnwrapper<Boolean>
{

	protected ITypeCheckerAssistantFactory af;

	public SeqBasisChecker(ITypeCheckerAssistantFactory af)
	{
		this.af = af;
	}

	@Override
	public Boolean defaultSSeqType(SSeqType type) throws AnalysisException
	{
		return true;
	}

	@Override
	public Boolean defaultSInvariantType(SInvariantType type)
			throws AnalysisException
	{
		if (type instanceof ANamedInvariantType)
		{
			if (type.getOpaque())
			{
				return false;
			}
			return ((ANamedInvariantType) type).getType().apply(THIS);// PTypeAssistantTC.isSeq(type.getType());
		} else
		{
			return false;
		}
	}

	@Override
	public Boolean caseAUnionType(AUnionType type) throws AnalysisException
	{
		// return af.createAUnionTypeAssistant().getSeq(type) != null;
		// if (!type.getSeqDone())
		// {
		// type.setSeqDone(true); // Mark early to avoid recursion.
		// type.setSeqType(PTypeAssistantTC.getSeq(AstFactory.newAUnknownType(type.getLocation())));
		//
		// PTypeSet set = new PTypeSet();
		//
		// for (PType t : type.getTypes())
		// {
		// if (PTypeAssistantTC.isSeq(t))
		// {
		// set.add(PTypeAssistantTC.getSeq(t).getSeqof());
		// }
		// }
		//
		// type.setSeqType(set.isEmpty() ? null
		// : AstFactory.newASeqSeqType(type.getLocation(), set.getType(type.getLocation())));
		// }
		//
		// return type.getSeqType() !=null;
		return type.apply(af.getSeqTypeFinder()) != null;
	}

	@Override
	public Boolean caseAUnknownType(AUnknownType type) throws AnalysisException
	{
		return true;
	}

	@Override
	public Boolean defaultPType(PType type) throws AnalysisException
	{
		return false;
	}

}
