package org.overture.codegen.vdm2jml.trans;

import org.overture.codegen.ir.INode;
import org.overture.codegen.traces.*;
import org.overture.codegen.trans.IterationVarPrefixes;
import org.overture.codegen.trans.assistants.TransAssistantIR;
import org.overture.codegen.trans.iterator.ILanguageIterator;

import java.util.LinkedList;
import java.util.List;

public class JmlTraceTrans extends TracesTrans
{
	private List<TcExpInfo> tcExpInfo;

	public JmlTraceTrans(TransAssistantIR transAssistant,
			IterationVarPrefixes iteVarPrefixes, TraceNames tracePrefixes,
			ILanguageIterator langIterator,
			ICallStmToStringMethodBuilder toStringBuilder,
			List<INode> cloneFreeNodes)
	{
		super(transAssistant, iteVarPrefixes, tracePrefixes, langIterator, toStringBuilder, cloneFreeNodes);
		this.tcExpInfo = new LinkedList<>();
	}

	@Override
	public TraceStmBuilder consStmBuilder(StoreAssistant storeAssist,
			String traceEnclosingClass)
	{
		return new JmlTraceStmBuilder(this, traceEnclosingClass, storeAssist, tcExpInfo);
	}

	public List<TcExpInfo> getTcExpInfo()
	{
		return tcExpInfo;
	}
}
