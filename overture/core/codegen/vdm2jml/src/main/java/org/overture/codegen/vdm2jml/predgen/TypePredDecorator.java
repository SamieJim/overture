package org.overture.codegen.vdm2jml.predgen;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.overture.codegen.ir.SStmIR;
import org.overture.codegen.ir.analysis.AnalysisException;
import org.overture.codegen.ir.declarations.ADefaultClassDeclIR;
import org.overture.codegen.ir.declarations.AFieldDeclIR;
import org.overture.codegen.ir.declarations.AMethodDeclIR;
import org.overture.codegen.ir.declarations.AVarDeclIR;
import org.overture.codegen.ir.expressions.ACastUnaryExpIR;
import org.overture.codegen.ir.expressions.AIdentifierVarExpIR;
import org.overture.codegen.ir.expressions.SVarExpIR;
import org.overture.codegen.ir.statements.AAssignToExpStmIR;
import org.overture.codegen.ir.statements.ABlockStmIR;
import org.overture.codegen.ir.statements.ACallObjectExpStmIR;
import org.overture.codegen.ir.statements.AMapSeqUpdateStmIR;
import org.overture.codegen.ir.statements.AMetaStmIR;
import org.overture.codegen.ir.statements.AReturnStmIR;
import org.overture.codegen.traces.TraceMethodTag;
import org.overture.codegen.vdm2jml.JmlGenerator;
import org.overture.codegen.vdm2jml.data.RecClassInfo;
import org.overture.codegen.vdm2jml.data.StateDesInfo;
import org.overture.codegen.vdm2jml.trans.RecAccessorTrans;
import org.overture.codegen.vdm2jml.trans.TargetNormaliserTrans;

/**
 * This class is responsible for adding additional checks, like assertions, to the IR to preserve the semantics of the
 * contract-based notations of VDM-SL when they are translated to JML annotated Java.
 * 
 * @see RecAccessorTrans
 * @see TargetNormaliserTrans
 */
public class TypePredDecorator extends AtomicAssertTrans
{
	private RecModHandler recHandler;
	private TypePredHandler namedTypeHandler;
	private StateDesInfo stateDesInfo;
	private RecClassInfo recInfo;

	private boolean buildRecChecks = false;

	private Logger log = Logger.getLogger(this.getClass().getName());

	public TypePredDecorator(JmlGenerator jmlGen, StateDesInfo stateDesInfo,
			RecClassInfo recInfo)
	{
		super(jmlGen);
		this.recHandler = new RecModHandler(this);
		this.namedTypeHandler = new TypePredHandler(this);
		this.stateDesInfo = stateDesInfo;
		this.recInfo = recInfo;
	}

	@Override
	public void caseACallObjectExpStmIR(ACallObjectExpStmIR node)
			throws AnalysisException
	{
		if (node.getObj() instanceof SVarExpIR)
		{
			SVarExpIR obj = (SVarExpIR) node.getObj();
			handleStateUpdate(node, obj);
		} else if (node.getObj() instanceof ACastUnaryExpIR)
		{
			ACastUnaryExpIR cast = (ACastUnaryExpIR) node.getObj();

			if (cast.getExp() instanceof SVarExpIR)
			{
				SVarExpIR obj = (SVarExpIR) cast.getExp();
				handleStateUpdate(node, obj);
			} else
			{
				log.error("Expected subject of cast expression to be a variable expression at this point. Got: "
						+ cast.getExp());
			}
		} else
		{
			log.error("Expected object of call object statement "
					+ " to be a variable or cast expression by now. Got: "
					+ node.getObj());
		}
	}

	private void handleStateUpdate(ACallObjectExpStmIR node, SVarExpIR obj)
	{
		handleStateUpdate(node, obj, stateDesInfo.getStateDesVars(node), recHandler.handleCallObj(node), namedTypeHandler.handleCallObj(node));
	}

	@Override
	public void caseAFieldDeclIR(AFieldDeclIR node) throws AnalysisException
	{
		namedTypeHandler.handleField(node);
	}

	@Override
	public void caseABlockStmIR(ABlockStmIR node) throws AnalysisException
	{
		namedTypeHandler.handleBlock(node);
	}

	@Override
	public void caseAVarDeclIR(AVarDeclIR node) throws AnalysisException
	{
		/**
		 * Variable declarations occurring inside an atomic statement do not need handling. The reason for this is that
		 * the call statement and the map/seq update cases currently take care of generating the assertions for variable
		 * expressions used to represent state designators. TODO: Make this case handle state designators
		 */
		if (inAtomic())
		{
			return;
		}

		if (stateDesInfo.isStateDesDecl(node))
		{
			return;
		}

		/**
		 * Since the target of map/seq updates (e.g. Utils.mapsSeqUpdate(stateDes_3, 4, 'a')) and call object statements
		 * (e.g. stateDes_3.set_x("a")) (i.e. assignments in the VDM-SL model) are split into variables named stateDes_
		 * <n> we can also expect local variable declarations in atomic statement blocks
		 */
		List<AMetaStmIR> as = namedTypeHandler.handleVarDecl(node);

		if (as == null || as.isEmpty())
		{
			return;
		}

		ABlockStmIR encBlock = namedTypeHandler.getEncBlockStm(node);

		if (encBlock == null)
		{
			return;
		}

		/**
		 * The variable declaration is a local declaration of the statement block. Therefore, making the assertion the
		 * first statement in the statement block makes the assertion appear immediately right after the variable
		 * declaration.
		 */
		if (inAtomic())
		{
			for (AMetaStmIR a : as)
			{
				addPostAtomicCheck(a);
			}
		} else
		{
			for (int i = as.size() - 1; i >= 0; i--)
			{
				encBlock.getStatements().addFirst(as.get(i));
			}
		}
	}

	@Override
	public void caseAAssignToExpStmIR(AAssignToExpStmIR node)
			throws AnalysisException
	{
		/**
		 * Record modifications are now all on the form E.g. St = <recvalue>, i.e. node.getTarget() instanceof SVarExpIR
		 * && node.getTarget().getType() instanceof ARecordTypeIR. Invariant violations will therefore be detected when
		 * the record value is constructed or it appears in the temporary variable section if the assignment occurs in
		 * the context of an atomic statement block. Therefore, no record invariant needs to be asserted. Note that more
		 * complicated record modifications (e.g. rec1.rec2.f := 5) appear as nodes of type caseACallObjectExpStmIR
		 */

		if (!inAtomic())
		{
			namedTypeHandler.handleAssign(node);
		}
	}

	@Override
	public void caseAMapSeqUpdateStmIR(AMapSeqUpdateStmIR node)
			throws AnalysisException
	{
		if (node.getCol() instanceof SVarExpIR)
		{
			SVarExpIR col = (SVarExpIR) node.getCol();
			handleStateUpdate(node, col, stateDesInfo.getStateDesVars(node), null, namedTypeHandler.handleMapSeq(node));
		} else
		{
			log.error("Expected collection of map/sequence"
					+ " update to be a variable expression by now. Got: "
					+ node.getCol());
		}
	}

	@Override
	public void caseAMethodDeclIR(AMethodDeclIR node) throws AnalysisException
	{
		if (node.getTag() instanceof TraceMethodTag)
		{
			return;
		}

		namedTypeHandler.handleMethod(node);
	}

	@Override
	public void caseAReturnStmIR(AReturnStmIR node) throws AnalysisException
	{
		namedTypeHandler.handleReturn(node);
	}

	@Override
	public void caseADefaultClassDeclIR(ADefaultClassDeclIR node)
			throws AnalysisException
	{
		namedTypeHandler.handleClass(node);
	}

	private void handleStateUpdate(SStmIR node, SVarExpIR target,
			List<AIdentifierVarExpIR> objVars, AMetaStmIR recAssert,
			List<AMetaStmIR> namedTypeInvAssert)
	{
		if (recAssert == null && namedTypeInvAssert == null && objVars == null)
		{
			return;
		}

		if (!inAtomic())
		{
			// NOT inside atomic statement block
			ABlockStmIR replBlock = new ABlockStmIR();
			jmlGen.getJavaGen().getTransAssistant().replaceNodeWith(node, replBlock);
			replBlock.getStatements().add(node);

			addSubjectAsserts(recAssert, namedTypeInvAssert, replBlock);
			addStateDesAsserts(target, objVars, replBlock);
		} else
		{
			// We'll store the checks and let the atomic statement case handle the assert insertion
			addSubjectAssertAtomic(recAssert, namedTypeInvAssert);
			addStateDesAssertsAtomic(target, objVars);
		}
	}

	private void addSubjectAssertAtomic(AMetaStmIR recAssert,
			List<AMetaStmIR> namedTypeInvAssert)
	{
		for (AMetaStmIR a : consSubjectAsserts(recAssert, namedTypeInvAssert))
		{
			addPostAtomicCheck(a);
		}
	}

	private void addSubjectAsserts(AMetaStmIR recAssert,
			List<AMetaStmIR> namedTypeInvAssert, ABlockStmIR replBlock)
	{
		for (AMetaStmIR a : consSubjectAsserts(recAssert, namedTypeInvAssert))
		{
			replBlock.getStatements().add(a);
		}
	}

	private List<AMetaStmIR> consSubjectAsserts(AMetaStmIR recAssert,
			List<AMetaStmIR> namedTypeInvAsserts)
	{
		List<AMetaStmIR> allAsserts = new LinkedList<AMetaStmIR>();

		add(allAsserts, recAssert);

		if (namedTypeInvAsserts != null)
		{
			for (AMetaStmIR a : namedTypeInvAsserts)
			{
				add(allAsserts, a);
			}
		}

		return allAsserts;
	}

	private void addStateDesAssertsAtomic(SVarExpIR target,
			List<AIdentifierVarExpIR> objVars)
	{
		for (AMetaStmIR a : consObjVarAsserts(target, objVars))
		{
			addPostAtomicCheck(a);
		}
	}

	private void addStateDesAsserts(SVarExpIR target,
			List<AIdentifierVarExpIR> objVars, ABlockStmIR replBlock)
	{
		for (AMetaStmIR a : consObjVarAsserts(target, objVars))
		{
			add(replBlock, a);
		}
	}

	private List<AMetaStmIR> consObjVarAsserts(SVarExpIR target,
			List<AIdentifierVarExpIR> objVars)
	{
		List<AMetaStmIR> objVarAsserts = new LinkedList<AMetaStmIR>();

		if (objVars != null)
		{
			// All of them except the last
			for (int i = 0; i < objVars.size() - 1; i++)
			{
				addAsserts(objVarAsserts, objVars.get(i));
			}

			if (!objVarAsserts.isEmpty())
			{
				AIdentifierVarExpIR last = objVars.get(objVars.size() - 1);

				if (!target.getName().equals(last.getName()))
				{
					addAsserts(objVarAsserts, last);
				}
			}
		}

		Collections.reverse(objVarAsserts);
		return objVarAsserts;
	}

	private void addAsserts(List<AMetaStmIR> objVarAsserts,
			AIdentifierVarExpIR var)
	{
		buildRecChecks = true;
		List<AMetaStmIR> asserts = namedTypeHandler.consAsserts(var);
		buildRecChecks = false;

		if (asserts != null)
		{
			for (AMetaStmIR a : asserts)
			{
				add(objVarAsserts, a);
			}
		}
	}

	private void add(List<AMetaStmIR> asserts, AMetaStmIR as)
	{
		if (as != null)
		{
			asserts.add(as);
		}
	}

	private void add(ABlockStmIR block, AMetaStmIR as)
	{
		if (as != null)
		{
			block.getStatements().add(as);
		}
	}

	public RecClassInfo getRecInfo()
	{
		return recInfo;
	}

	public StateDesInfo getStateDesInfo()
	{
		return stateDesInfo;
	}

	public boolean buildRecValidChecks()
	{
		return buildRecChecks;
	}

	public TypePredUtil getTypePredUtil()
	{
		return this.namedTypeHandler.getTypePredUtil();
	}
}
