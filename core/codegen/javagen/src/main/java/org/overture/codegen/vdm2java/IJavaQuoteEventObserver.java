package org.overture.codegen.vdm2java;

import org.overture.codegen.ir.declarations.ADefaultClassDeclIR;

import java.util.List;

public interface IJavaQuoteEventObserver
{
	public void quoteClassesProduced(List<ADefaultClassDeclIR> quoteClasses);
}
