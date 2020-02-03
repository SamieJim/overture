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
package org.overture.codegen.trans;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.overture.ast.patterns.ASetMultipleBind;
import org.overture.codegen.assistant.AssistantBase;
import org.overture.codegen.ir.*;
import org.overture.codegen.ir.analysis.AnalysisException;
import org.overture.codegen.ir.analysis.DepthFirstAnalysisAdaptor;
import org.overture.codegen.ir.declarations.AVarDeclIR;
import org.overture.codegen.ir.expressions.*;
import org.overture.codegen.ir.patterns.*;
import org.overture.codegen.ir.statements.AAssignToExpStmIR;
import org.overture.codegen.ir.statements.ABlockStmIR;
import org.overture.codegen.ir.statements.ACaseAltStmStmIR;
import org.overture.codegen.ir.statements.ACasesStmIR;
import org.overture.codegen.ir.statements.AIfStmIR;
import org.overture.codegen.ir.types.*;
import org.overture.codegen.ir.utils.AHeaderLetBeStIR;
import org.overture.codegen.trans.assistants.TransAssistantIR;
import org.overture.codegen.trans.comp.ComplexCompStrategy;
import org.overture.codegen.trans.comp.MapCompStrategy;
import org.overture.codegen.trans.comp.SeqCompStrategy;
import org.overture.codegen.trans.comp.SetCompStrategy;
import org.overture.codegen.trans.iota.IotaStrategy;
import org.overture.codegen.trans.iterator.ILanguageIterator;
import org.overture.codegen.trans.let.LetBeStStrategy;
import org.overture.codegen.trans.quantifier.CounterData;
import org.overture.codegen.trans.quantifier.Exists1QuantifierStrategy;
import org.overture.codegen.trans.quantifier.OrdinaryQuantifier;
import org.overture.codegen.trans.quantifier.OrdinaryQuantifierStrategy;

public class Exp2StmTrans extends DepthFirstAnalysisAdaptor
{
	protected Logger log = Logger.getLogger(this.getClass().getName());

	protected TransAssistantIR transAssistant;
	protected ILanguageIterator langIterator;

	protected CounterData counterData;
	protected Exp2StmVarPrefixes prefixes;
	protected IterationVarPrefixes iteVarPrefixes;

	public Exp2StmTrans(IterationVarPrefixes iteVarPrefixes,
						TransAssistantIR transAssistant, CounterData counterData,
						ILanguageIterator langIterator, Exp2StmVarPrefixes prefixes)
	{
		this.transAssistant = transAssistant;
		this.counterData = counterData;
		this.langIterator = langIterator;
		this.prefixes = prefixes;
		this.iteVarPrefixes = iteVarPrefixes;
	}

	@Override
	public void caseATernaryIfExpIR(ATernaryIfExpIR node)
			throws AnalysisException
	{
		SStmIR enclosingStm = transAssistant.findEnclosingStm(node);

		if (enclosingStm == null)
		{
			// TODO:
			// Cases such as
			// values
			// public x = 1 + if 2 = 3 then 4 else 5 + 6;
			// Will not be treated
			return;
		}

		String resultVarName = transAssistant.getInfo().getTempVarNameGen().nextVarName(prefixes.ternaryIfExp());

		AVarDeclIR resultDecl = transAssistant.consDecl(resultVarName, node.getType().clone(), transAssistant.getInfo().getExpAssistant().consUndefinedExp());
		AIdentifierVarExpIR resultVar = transAssistant.getInfo().getExpAssistant().consIdVar(resultVarName, resultDecl.getType().clone());

		SExpIR condition = node.getCondition();
		SExpIR trueValue = node.getTrueValue();
		SExpIR falseValue = node.getFalseValue();

		AAssignToExpStmIR trueBranch = new AAssignToExpStmIR();
		trueBranch.setTarget(resultVar.clone());
		trueBranch.setExp(trueValue.clone());

		AAssignToExpStmIR falseBranch = new AAssignToExpStmIR();
		falseBranch.setTarget(resultVar.clone());
		falseBranch.setExp(falseValue);

		AIfStmIR ifStm = new AIfStmIR();
		ifStm.setIfExp(condition.clone());
		ifStm.setThenStm(trueBranch);
		ifStm.setElseStm(falseBranch);

		ABlockStmIR replacementBlock = new ABlockStmIR();

		transAssistant.replaceNodeWith(node, resultVar);
		transAssistant.replaceNodeWith(enclosingStm, replacementBlock);

		ABlockStmIR declBlock = new ABlockStmIR();
		declBlock.getLocalDefs().add(resultDecl);

		replacementBlock.getStatements().add(declBlock);
		replacementBlock.getStatements().add(ifStm);
		replacementBlock.getStatements().add(enclosingStm);

		ifStm.getIfExp().apply(this);
		trueBranch.getExp().apply(this);
		falseBranch.getExp().apply(this);
	}

	@Override
	public void caseAOrBoolBinaryExpIR(AOrBoolBinaryExpIR node)
			throws AnalysisException
	{
		// left || right
		//
		// is replaced with a variable expression 'orResult' that is
		// computed as:
		//
		// boolean orResult = false;
		// if (left)
		// {
		// orResult = true;
		// }
		// else
		// {
		// orResult = right;
		// }
		//

		SStmIR enclosingStm = transAssistant.findEnclosingStm(node);

		if (transformBoolBinaryExp(node, enclosingStm))
		{
			String resultName = transAssistant.getInfo().getTempVarNameGen().nextVarName(prefixes.orExp());
			handleLogicExp(node, enclosingStm, consOrExpCheck(node, resultName), resultName);
		} else
		{
			visitBoolBinary(node);
		}
	}

	@Override
	public void caseAAndBoolBinaryExpIR(AAndBoolBinaryExpIR node)
			throws AnalysisException
	{
		// left && right
		//
		// is replaced with a variable expression 'andResult' that is
		// computed as:
		//
		// boolean andResult = false;
		// if (left)
		// {
		// if (right)
		// {
		// andResult = true;
		// }
		// }

		SStmIR enclosingStm = transAssistant.findEnclosingStm(node);

		if (transformBoolBinaryExp(node, enclosingStm))
		{
			String resultName = transAssistant.getInfo().getTempVarNameGen().nextVarName(prefixes.andExp());
			handleLogicExp(node, enclosingStm, consAndExpCheck(node, resultName), resultName);
		} else
		{
			visitBoolBinary(node);
		}
	}

	@Override
	public void caseALetBeStExpIR(ALetBeStExpIR node) throws AnalysisException
	{
		SStmIR enclosingStm = transAssistant.getEnclosingStm(node, "let be st expressions");

		AHeaderLetBeStIR header = node.getHeader();
		SMultipleBindIR binding = header.getBinding();
		SExpIR suchThat = header.getSuchThat();

		if(!(binding instanceof ASetMultipleBindIR || binding instanceof ASeqMultipleBindIR))
		{
			transAssistant.getInfo().addTransformationWarning(node.getHeader().getBinding(),
					"This transformation only works for 'let be st' " +
							"expressions with with multiple set or sequence binds and not multiple type binds in '"
							+ this.getClass().getSimpleName() + "'");
			return;
		}

		STypeIR colType = getCol(binding).getType().clone();

		ITempVarGen tempVarNameGen = transAssistant.getInfo().getTempVarNameGen();

		LetBeStStrategy strategy = consLetBeStStrategy(suchThat, colType, tempVarNameGen);

		ABlockStmIR outerBlock = new ABlockStmIR();

		SExpIR letBeStResult = null;

		if (transAssistant.hasEmptySet(binding))
		{
			transAssistant.cleanUpBinding(binding);
			letBeStResult = transAssistant.getInfo().getExpAssistant().consUndefinedExp();
		} else
		{
			String var = tempVarNameGen.nextVarName(prefixes.letBeSt());
			SExpIR value = node.getValue();

			AVarDeclIR resultDecl = transAssistant.consDecl(var, value.getType().clone(), transAssistant.getInfo().getExpAssistant().consUndefinedExp());
			outerBlock.getLocalDefs().add(resultDecl);

			AAssignToExpStmIR setLetBeStResult = new AAssignToExpStmIR();
			setLetBeStResult.setTarget(transAssistant.getInfo().getExpAssistant().consIdVar(var, value.getType().clone()));
			setLetBeStResult.setExp(value);
			outerBlock.getStatements().add(setLetBeStResult);

			AIdentifierVarExpIR varExpResult = new AIdentifierVarExpIR();
			varExpResult.setType(value.getType().clone());
			varExpResult.setIsLocal(true);
			varExpResult.setName(var);
			letBeStResult = varExpResult;
		}

		// Replace the let be st expression with the result expression
		transAssistant.replaceNodeWith(node, letBeStResult);

		LinkedList<SPatternIR> patterns = binding.getPatterns();
		ABlockStmIR block = transAssistant.consIterationBlock(patterns, getCol(binding), tempVarNameGen, strategy, iteVarPrefixes);
		outerBlock.getStatements().addFirst(block);

		// Replace the enclosing statement with the transformation
		transAssistant.replaceNodeWith(enclosingStm, outerBlock);

		// And make sure to have the enclosing statement in the transformed tree
		outerBlock.getStatements().add(enclosingStm);
		outerBlock.apply(this);

		outerBlock.setScoped(transAssistant.getInfo().getStmAssistant().isScoped(outerBlock));
	}

	private SExpIR getCol(SMultipleBindIR binding)
	{
		if(binding instanceof ASetMultipleBindIR)
		{
			return ((ASetMultipleBindIR) binding).getSet();
		}
		else if(binding instanceof ASeqMultipleBindIR)
		{
			return ((ASeqMultipleBindIR) binding).getSeq();
		}

		return null;
	}

	public LetBeStStrategy consLetBeStStrategy(SExpIR suchThat, STypeIR colType, ITempVarGen tempVarNameGen) {
		return new LetBeStStrategy(transAssistant, suchThat, colType, langIterator, tempVarNameGen, iteVarPrefixes);
	}

	@Override
	public void caseARecordModExpIR(ARecordModExpIR node)
			throws AnalysisException
	{
		String recModifierName = transAssistant.getInfo().getTempVarNameGen().nextVarName(prefixes.recModExp());

		AVarDeclIR recDecl = transAssistant.consDecl(recModifierName, node.getType().clone(), node.getRec().clone());
		ABlockStmIR declStm = new ABlockStmIR();
		declStm.getLocalDefs().add(recDecl);

		AIdentifierVarExpIR recVar = transAssistant.getInfo().getExpAssistant().consIdVar(recModifierName, node.getType().clone());

		ABlockStmIR replacementBlock = new ABlockStmIR();
		replacementBlock.getStatements().add(declStm);

		if(transAssistant.getInfo().getTypeAssistant().isIncompleteRecType(node.getRecType())) {
			log.warn("Record type of mu_ expression '" + AssistantBase.getVdmNode(node) + "' has incomplete information." +
					" Record type name: " + node.getRecType().getName().getName() +
					". Defining class of record type: " + node.getRecType().getName().getDefiningClass());
		}

		for (ARecordModifierIR modifier : node.getModifiers())
		{
			String name = modifier.getName();
			SExpIR value = modifier.getValue().clone();

			STypeIR fieldType;

			if(transAssistant.getInfo().getTypeAssistant().isIncompleteRecType(node.getRecType()))
			{
				fieldType = value.getType().clone();
			}
			else
			{
				fieldType = transAssistant.getInfo().getTypeAssistant().getFieldType(transAssistant.getInfo().getClasses(), node.getRecType(), name);
			}

			AFieldExpIR field = new AFieldExpIR();
			field.setType(fieldType);
			field.setObject(recVar.clone());
			field.setMemberName(name);

			AAssignToExpStmIR assignment = new AAssignToExpStmIR();
			assignment.setTarget(field);
			assignment.setExp(value);

			replacementBlock.getStatements().add(assignment);
		}

		SStmIR enclosingStm = transAssistant.getEnclosingStm(node, "record modification expression");

		transform(enclosingStm, replacementBlock, recVar.clone(), node);

		replacementBlock.apply(this);
	}

	@Override
	public void caseACompMapExpIR(ACompMapExpIR node) throws AnalysisException
	{
		SStmIR enclosingStm = transAssistant.getEnclosingStm(node, "map comprehension");

		AMapletExpIR first = node.getFirst();
		SExpIR predicate = node.getPredicate();
		STypeIR type = node.getType();
		ITempVarGen tempVarNameGen = transAssistant.getInfo().getTempVarNameGen();
		String var = tempVarNameGen.nextVarName(prefixes.mapComp());

		ComplexCompStrategy strategy = consMapCompStrategy(first, predicate, type, tempVarNameGen, var);

		List<SMultipleBindIR> bindings = filterBindList(node, node.getBindings());

		ABlockStmIR block = transAssistant.consComplexCompIterationBlock(bindings, tempVarNameGen, strategy, iteVarPrefixes);

		if (block.getStatements().isEmpty())
		{
			// In case the block has no statements the result of the map comprehension is the empty map
			AEnumMapExpIR emptyMap = new AEnumMapExpIR();
			emptyMap.setType(type.clone());

			// Replace the map comprehension with the empty map
			transAssistant.replaceNodeWith(node, emptyMap);
		} else
		{
			replaceCompWithTransformation(enclosingStm, block, type, var, node);
		}

		block.apply(this);
	}

	public MapCompStrategy consMapCompStrategy(AMapletExpIR first, SExpIR predicate, STypeIR type, ITempVarGen tempVarNameGen, String var) {
		return new MapCompStrategy(transAssistant, first, predicate, var, type, langIterator, tempVarNameGen, iteVarPrefixes);
	}

	@Override
	public void caseACompSetExpIR(ACompSetExpIR node) throws AnalysisException
	{
		SStmIR enclosingStm = transAssistant.getEnclosingStm(node, "set comprehension");

		SExpIR first = node.getFirst();
		SExpIR predicate = node.getPredicate();
		STypeIR type = node.getType();
		ITempVarGen tempVarNameGen = transAssistant.getInfo().getTempVarNameGen();
		String var = tempVarNameGen.nextVarName(prefixes.setComp());

		ComplexCompStrategy strategy = consSetCompStrategy(first, predicate, type, tempVarNameGen, var);

		List<SMultipleBindIR> bindings = filterBindList(node, node.getBindings());
		ABlockStmIR block = transAssistant.consComplexCompIterationBlock(bindings, tempVarNameGen, strategy, iteVarPrefixes);

		if (block.getStatements().isEmpty())
		{
			// In case the block has no statements the result of the set comprehension is the empty set
			AEnumSetExpIR emptySet = new AEnumSetExpIR();
			emptySet.setType(type.clone());

			// Replace the set comprehension with the empty set
			transAssistant.replaceNodeWith(node, emptySet);
		} else
		{
			replaceCompWithTransformation(enclosingStm, block, type, var, node);
		}

		block.apply(this);
	}

	public SetCompStrategy consSetCompStrategy(SExpIR first, SExpIR predicate, STypeIR type, ITempVarGen tempVarNameGen, String var) {
		return new SetCompStrategy(transAssistant, first, predicate, var, type, langIterator, tempVarNameGen, iteVarPrefixes);
	}

	@Override
	public void caseACompSeqExpIR(ACompSeqExpIR node) throws AnalysisException
	{
		SStmIR enclosingStm = transAssistant.getEnclosingStm(node, "sequence comprehension");

		SExpIR first = node.getFirst();
		SExpIR predicate = node.getPredicate();
		STypeIR type = node.getType();
		ITempVarGen tempVarNameGen = transAssistant.getInfo().getTempVarNameGen();
		String var = tempVarNameGen.nextVarName(prefixes.seqComp());

		SeqCompStrategy strategy = consSeqCompStrategy(first, predicate, type, tempVarNameGen, var);

		if (transAssistant.isEmptySetSeq(node.getSetSeq()))
		{
			// In case the block has no statements the result of the sequence comprehension is the empty sequence
			AEnumSeqExpIR emptySeq = new AEnumSeqExpIR();
			emptySeq.setType(type.clone());

			// Replace the sequence comprehension with the empty sequence
			transAssistant.replaceNodeWith(node, emptySeq);
		} else
		{
			LinkedList<SPatternIR> patterns = new LinkedList<SPatternIR>();

			if (node.getSetBind() != null)
			{
				patterns.add(node.getSetBind().getPattern().clone());
			} else
			{
				patterns.add(node.getSeqBind().getPattern().clone());
			}

			ABlockStmIR block = transAssistant.consIterationBlock(patterns, node.getSetSeq(), transAssistant.getInfo().getTempVarNameGen(), strategy, iteVarPrefixes);

			replaceCompWithTransformation(enclosingStm, block, type, var, node);

			block.apply(this);
		}
	}

	public SeqCompStrategy consSeqCompStrategy(SExpIR first, SExpIR predicate, STypeIR type, ITempVarGen tempVarNameGen, String var) {
		return new SeqCompStrategy(transAssistant, first, predicate, var, type, langIterator, tempVarNameGen, iteVarPrefixes);
	}

	@Override
	public void caseAForAllQuantifierExpIR(AForAllQuantifierExpIR node)
			throws AnalysisException
	{
		SStmIR enclosingStm = transAssistant.getEnclosingStm(node, "forall expression");

		SExpIR predicate = node.getPredicate();
		ITempVarGen tempVarNameGen = transAssistant.getInfo().getTempVarNameGen();
		String var = tempVarNameGen.nextVarName(prefixes.forAll());

		OrdinaryQuantifierStrategy strategy = consOrdinaryQuantifierStrategy(predicate, tempVarNameGen, var, transAssistant, OrdinaryQuantifier.FORALL, langIterator, iteVarPrefixes);

		List<SMultipleBindIR> multipleSetBinds = filterBindList(node, node.getBindList());

		ABlockStmIR block = transAssistant.consComplexCompIterationBlock(multipleSetBinds, tempVarNameGen, strategy, iteVarPrefixes);

		if (multipleSetBinds.isEmpty())
		{
			ABoolLiteralExpIR forAllResult = transAssistant.getInfo().getExpAssistant().consBoolLiteral(true);
			transAssistant.replaceNodeWith(node, forAllResult);
		} else
		{
			AIdentifierVarExpIR forAllResult = new AIdentifierVarExpIR();
			forAllResult.setIsLocal(true);
			forAllResult.setType(new ABoolBasicTypeIR());
			forAllResult.setName(var);

			transform(enclosingStm, block, forAllResult, node);
			block.apply(this);
		}
	}

	@Override
	public void caseAExistsQuantifierExpIR(AExistsQuantifierExpIR node)
			throws AnalysisException
	{
		SStmIR enclosingStm = transAssistant.getEnclosingStm(node, "exists expression");

		SExpIR predicate = node.getPredicate();
		ITempVarGen tempVarNameGen = transAssistant.getInfo().getTempVarNameGen();
		String var = tempVarNameGen.nextVarName(prefixes.exists());

		OrdinaryQuantifierStrategy strategy = consOrdinaryQuantifierStrategy(predicate, tempVarNameGen, var, transAssistant, OrdinaryQuantifier.EXISTS, langIterator, iteVarPrefixes);

		List<SMultipleBindIR> multipleSetBinds = filterBindList(node, node.getBindList());

		ABlockStmIR block = transAssistant.consComplexCompIterationBlock(multipleSetBinds, tempVarNameGen, strategy, iteVarPrefixes);

		if (multipleSetBinds.isEmpty())
		{
			ABoolLiteralExpIR existsResult = transAssistant.getInfo().getExpAssistant().consBoolLiteral(false);
			transAssistant.replaceNodeWith(node, existsResult);
		} else
		{
			AIdentifierVarExpIR existsResult = new AIdentifierVarExpIR();
			existsResult.setIsLocal(true);
			existsResult.setType(new ABoolBasicTypeIR());
			existsResult.setName(var);

			transform(enclosingStm, block, existsResult, node);
			block.apply(this);
		}
	}

	@Override
	public void caseAIotaExpIR(AIotaExpIR node) throws AnalysisException {

		SStmIR enclosingStm = transAssistant.getEnclosingStm(node, "iota expression");

		SExpIR predicate = node.getPredicate();
		ITempVarGen tempVarNameGen = transAssistant.getInfo().getTempVarNameGen();
		String resVarName = tempVarNameGen.nextVarName(prefixes.iota());
		String counterName = tempVarNameGen.nextVarName(prefixes.iotaCounter());

		IotaStrategy strategy = consIotaStrategy(predicate, tempVarNameGen, resVarName, counterName, node.getPredicate());

		List<SMultipleBindIR> multipleSetBinds = filterBindList(node, node.getBindList());

		ABlockStmIR block = transAssistant.consComplexCompIterationBlock(multipleSetBinds, tempVarNameGen, strategy, iteVarPrefixes);

		if (multipleSetBinds.isEmpty())
		{
			transAssistant.replaceNodeWith(node, transAssistant.getInfo().getExpAssistant().consUndefinedExp());
		} else
		{
			AIdentifierVarExpIR iotaResult = new AIdentifierVarExpIR();
			iotaResult.setIsLocal(true);
			iotaResult.setType(node.getType().clone());
			iotaResult.setName(resVarName);

			transform(enclosingStm, block, iotaResult, node);
			block.apply(this);
		}
	}

	public IotaStrategy consIotaStrategy(SExpIR pred, ITempVarGen tempVarNameGen, String resName, String counterName, SExpIR predicate) {

		return new IotaStrategy(transAssistant, predicate, resName, counterName, langIterator, tempVarNameGen, iteVarPrefixes, counterData);
	}

	public OrdinaryQuantifierStrategy consOrdinaryQuantifierStrategy(SExpIR predicate, ITempVarGen tempVarNameGen, String var, TransAssistantIR transAssistant, OrdinaryQuantifier exists, ILanguageIterator langIterator, IterationVarPrefixes iteVarPrefixes) {
		return new OrdinaryQuantifierStrategy(transAssistant, predicate, var, exists, langIterator, tempVarNameGen, iteVarPrefixes);
	}

	@Override
	public void caseAExists1QuantifierExpIR(AExists1QuantifierExpIR node)
			throws AnalysisException
	{
		SStmIR enclosingStm = transAssistant.getEnclosingStm(node, "exists1 expression");

		SExpIR predicate = node.getPredicate();
		ITempVarGen tempVarNameGen = transAssistant.getInfo().getTempVarNameGen();
		String var = tempVarNameGen.nextVarName(prefixes.exists1());

		Exists1QuantifierStrategy strategy = consExists1QuantifierStrategy(predicate, tempVarNameGen, var);

		List<SMultipleBindIR> multipleSetBinds = filterBindList(node, node.getBindList());

		ABlockStmIR block = transAssistant.consComplexCompIterationBlock(multipleSetBinds, tempVarNameGen, strategy, iteVarPrefixes);

		if (multipleSetBinds.isEmpty())
		{
			ABoolLiteralExpIR exists1Result = transAssistant.getInfo().getExpAssistant().consBoolLiteral(false);
			transAssistant.replaceNodeWith(node, exists1Result);
		} else
		{
			AIdentifierVarExpIR counter = new AIdentifierVarExpIR();
			counter.setType(new AIntNumericBasicTypeIR());
			counter.setIsLocal(true);
			counter.setName(var);

			AEqualsBinaryExpIR exists1Result = new AEqualsBinaryExpIR();
			exists1Result.setType(new ABoolBasicTypeIR());
			exists1Result.setLeft(counter);
			exists1Result.setRight(transAssistant.getInfo().getExpAssistant().consIntLiteral(1));

			transform(enclosingStm, block, exists1Result, node);
			block.apply(this);
		}
	}

	public Exists1QuantifierStrategy consExists1QuantifierStrategy(SExpIR predicate, ITempVarGen tempVarNameGen, String var) {
		return new Exists1QuantifierStrategy(transAssistant, predicate, var, langIterator, tempVarNameGen, iteVarPrefixes, counterData);
	}

	public void caseALetDefExpIR(ALetDefExpIR node) throws AnalysisException
	{
		SStmIR enclosingStm = transAssistant.getEnclosingStm(node, "let def expression");

		SExpIR exp = node.getExp();
		transAssistant.replaceNodeWith(node, exp);

		ABlockStmIR topBlock = new ABlockStmIR();
		ABlockStmIR current = topBlock;

		for (AVarDeclIR local : node.getLocalDefs())
		{
			ABlockStmIR tmp = new ABlockStmIR();
			tmp.getLocalDefs().add(local.clone());
			current.getStatements().add(tmp);
			current = tmp;
		}

		transAssistant.replaceNodeWith(enclosingStm, topBlock);
		topBlock.getStatements().add(enclosingStm);

		exp.apply(this);
		topBlock.apply(this);

		topBlock.setScoped(transAssistant.getInfo().getStmAssistant().isScoped(topBlock));
	}

	protected void replaceCompWithTransformation(SStmIR enclosingStm,
												 ABlockStmIR block, STypeIR type, String var, SExpIR comp)
	{
		AIdentifierVarExpIR compResult = new AIdentifierVarExpIR();
		compResult.setType(type.clone());
		compResult.setName(var);
		compResult.setIsLambda(false);
		compResult.setIsLocal(true);

		transform(enclosingStm, block, compResult, comp);
	}

	protected void transform(SStmIR enclosingStm, ABlockStmIR block,
							 SExpIR nodeResult, SExpIR node)
	{
		// Replace the node with the node result
		transAssistant.replaceNodeWith(node, nodeResult);

		// Replace the enclosing statement with the transformation
		transAssistant.replaceNodeWith(enclosingStm, block);

		// And make sure to have the enclosing statement in the transformed tree
		block.getStatements().add(enclosingStm);
	}

	protected AAssignToExpStmIR assignToVar(AIdentifierVarExpIR var, SExpIR exp)
	{
		AAssignToExpStmIR assignment = new AAssignToExpStmIR();
		assignment.setTarget(var.clone());
		assignment.setExp(exp.clone());

		return assignment;
	}

	@Override
	public void caseACasesExpIR(ACasesExpIR node) throws AnalysisException
	{
		SStmIR enclosingStm = transAssistant.getEnclosingStm(node, "cases expression");

		AIdentifierPatternIR idPattern = new AIdentifierPatternIR();
		IRInfo info = transAssistant.getInfo();
		String casesExpResultName = info.getTempVarNameGen().nextVarName(prefixes.casesExp());
		idPattern.setName(casesExpResultName);

		AVarDeclIR resultVarDecl = info.getDeclAssistant().consLocalVarDecl(node.getType().clone(), idPattern, info.getExpAssistant().consUndefinedExp());

		AIdentifierVarExpIR resultVar = new AIdentifierVarExpIR();
		resultVar.setIsLocal(true);
		resultVar.setIsLambda(false);
		resultVar.setName(casesExpResultName);
		resultVar.setType(node.getType().clone());

		ACasesStmIR casesStm = new ACasesStmIR();
		casesStm.setExp(node.getExp().clone());

		for (ACaseAltExpExpIR altExp : node.getCases())
		{
			ACaseAltStmStmIR altStm = new ACaseAltStmStmIR();
			altStm.setPattern(altExp.getPattern().clone());
			altStm.setResult(assignToVar(resultVar, altExp.getResult()));
			altStm.setPatternType(altExp.getPatternType().clone());

			casesStm.getCases().add(altStm);
		}

		if (node.getOthers() != null)
		{
			casesStm.setOthers(assignToVar(resultVar, node.getOthers()));
		}

		ABlockStmIR block = new ABlockStmIR();

		ABlockStmIR wrapperBlock = new ABlockStmIR();
		wrapperBlock.getLocalDefs().add(resultVarDecl);

		block.getStatements().add(wrapperBlock);
		block.getStatements().add(casesStm);

		transform(enclosingStm, block, resultVar, node);

		casesStm.apply(this);
	}

	protected AIfStmIR consAndExpCheck(AAndBoolBinaryExpIR node,
									   String andResultVarName)
	{
		SExpIR left = node.getLeft().clone();
		SExpIR right = node.getRight().clone();

		AIfStmIR leftCheck = new AIfStmIR();
		leftCheck.setIfExp(left);

		AIfStmIR rightCheck = new AIfStmIR();
		rightCheck.setIfExp(right);

		AAssignToExpStmIR assignAndVar = new AAssignToExpStmIR();
		assignAndVar.setTarget(transAssistant.consBoolCheck(andResultVarName, false));
		assignAndVar.setExp(transAssistant.getInfo().getAssistantManager().getExpAssistant().consBoolLiteral(true));

		rightCheck.setThenStm(assignAndVar);

		leftCheck.setThenStm(rightCheck);

		return leftCheck;
	}

	protected SStmIR consOrExpCheck(AOrBoolBinaryExpIR node,
									String orResultVarName)
	{
		SExpIR left = node.getLeft().clone();
		SExpIR right = node.getRight().clone();

		AIfStmIR leftCheck = new AIfStmIR();
		leftCheck.setIfExp(left);

		AAssignToExpStmIR setOrResultVarTrue = new AAssignToExpStmIR();
		setOrResultVarTrue.setTarget(transAssistant.consBoolCheck(orResultVarName, false));
		setOrResultVarTrue.setExp(transAssistant.getInfo().getAssistantManager().getExpAssistant().consBoolLiteral(true));

		leftCheck.setThenStm(setOrResultVarTrue);

		AAssignToExpStmIR setOrResultVarToRightExp = new AAssignToExpStmIR();
		setOrResultVarToRightExp.setTarget(transAssistant.consBoolCheck(orResultVarName, false));
		setOrResultVarToRightExp.setExp(right);

		leftCheck.setElseStm(setOrResultVarToRightExp);

		return leftCheck;
	}

	protected boolean transformBoolBinaryExp(SBoolBinaryExpIR node,
											 SStmIR enclosingStm)
	{
		// First condition: The enclosing statement can be 'null' if we only try to code generate an expression rather
		// than
		// a complete specification.

		return enclosingStm != null
				&& !transAssistant.getInfo().getExpAssistant().isLoopCondition(node);
	}

	protected void visitBoolBinary(SBoolBinaryExpIR node)
			throws AnalysisException
	{
		node.getLeft().apply(this);
		node.getRight().apply(this);
		node.getType().apply(this);
	}

	protected void handleLogicExp(SBoolBinaryExpIR node, SStmIR enclosingStm,
								  SStmIR checkBlock, String resultName) throws AnalysisException
	{
		AVarDeclIR andResultDecl = transAssistant.consBoolVarDecl(resultName, false);

		ABlockStmIR declBlock = new ABlockStmIR();
		declBlock.getLocalDefs().add(andResultDecl);

		ABlockStmIR replacementBlock = new ABlockStmIR();

		transAssistant.replaceNodeWith(enclosingStm, replacementBlock);
		transAssistant.replaceNodeWith(node, transAssistant.consBoolCheck(resultName, false));

		replacementBlock.getStatements().add(declBlock);
		replacementBlock.getStatements().add(checkBlock);
		replacementBlock.getStatements().add(enclosingStm);

		replacementBlock.apply(this);
	}

	protected List<SMultipleBindIR> filterBindList(INode node,
												   List<SMultipleBindIR> bindList)
	{
		List<SMultipleBindIR> multipleBinds = new LinkedList<SMultipleBindIR>();

		for (SMultipleBindIR b : bindList)
		{
			if (b instanceof ATypeMultipleBindIR)
			{
				transAssistant.getInfo().addTransformationWarning(node, "Transformation only works for "
						+ "expressions with multiple set binds and not multiple "
						+ "type binds in '" + this.getClass().getSimpleName()
						+ "'");
			} else
			{
				multipleBinds.add(b.clone());
			}
		}

		return multipleBinds;
	}
}
