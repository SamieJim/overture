/*
 * #%~
 * VDM Code Generator
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
package org.overture.codegen.trans.comp;

import java.util.List;

import org.overture.codegen.ir.ITempVarGen;
import org.overture.codegen.ir.SExpIR;
import org.overture.codegen.ir.SPatternIR;
import org.overture.codegen.ir.SStmIR;
import org.overture.codegen.ir.STypeIR;
import org.overture.codegen.ir.expressions.AEnumSeqExpIR;
import org.overture.codegen.ir.expressions.AIdentifierVarExpIR;
import org.overture.codegen.ir.statements.ASeqCompAddStmIR;
import org.overture.codegen.trans.IterationVarPrefixes;
import org.overture.codegen.trans.assistants.TransAssistantIR;
import org.overture.codegen.trans.iterator.ILanguageIterator;

public class SeqCompStrategy extends CompStrategy
{
	protected SExpIR first;

	public SeqCompStrategy(TransAssistantIR transformationAssitant,
			SExpIR first, SExpIR predicate, String var, STypeIR compType,
			ILanguageIterator langIterator, ITempVarGen tempGen,
			IterationVarPrefixes iteVarPrefixes)
	{
		super(transformationAssitant, predicate, var, compType, langIterator, tempGen, iteVarPrefixes);

		this.first = first;
	}

	@Override
	protected SExpIR getEmptyCollection()
	{
		return new AEnumSeqExpIR();
	}

	@Override
	protected List<SStmIR> getConditionalAdd(AIdentifierVarExpIR setVar,
			List<SPatternIR> patterns, SPatternIR pattern)
	{
		AIdentifierVarExpIR seqCompResult = new AIdentifierVarExpIR();
		seqCompResult.setType(compType.clone());
		seqCompResult.setName(idPattern.getName());
		seqCompResult.setIsLambda(false);
		seqCompResult.setIsLocal(true);

		ASeqCompAddStmIR add = new ASeqCompAddStmIR();
		add.setSeq(seqCompResult);
		add.setElement(first.clone());

		return consConditionalAdd(seqCompResult, add);
	}

	@Override
	public List<SStmIR> getForLoopStms(AIdentifierVarExpIR setVar,
			List<SPatternIR> patterns, SPatternIR pattern)
	{
		return getConditionalAdd(setVar, patterns, pattern);
	}
}
