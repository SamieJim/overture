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
package org.overture.typechecker.utilities;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.AnswerAdaptor;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.AImportedDefinition;
import org.overture.ast.definitions.AInheritedDefinition;
import org.overture.ast.definitions.ANamedTraceDefinition;
import org.overture.ast.definitions.ARenamedDefinition;
import org.overture.ast.definitions.AThreadDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

/**
 * This class implements a way to check operation from a node in the AST
 * 
 * @author kel
 */
public class OperationChecker extends AnswerAdaptor<Boolean>
{

	protected ITypeCheckerAssistantFactory af;

	public OperationChecker(ITypeCheckerAssistantFactory af)
	{
		this.af = af;
	}

	@Override
	public Boolean caseAExplicitOperationDefinition(
			AExplicitOperationDefinition node) throws AnalysisException
	{
		return true;
	}

	@Override
	public Boolean caseAImplicitOperationDefinition(
			AImplicitOperationDefinition node) throws AnalysisException
	{
		return true;
	}

	@Override
	public Boolean caseANamedTraceDefinition(ANamedTraceDefinition node)
			throws AnalysisException
	{
		return true;
	}

	@Override
	public Boolean caseAThreadDefinition(AThreadDefinition node)
			throws AnalysisException
	{
		return true;
	}

	@Override
	public Boolean caseAImportedDefinition(AImportedDefinition node)
			throws AnalysisException
	{
		return node.getDef().apply(THIS);
	}

	@Override
	public Boolean caseAInheritedDefinition(AInheritedDefinition node)
			throws AnalysisException
	{
		return node.getSuperdef().apply(THIS);
	}

	@Override
	public Boolean caseARenamedDefinition(ARenamedDefinition node)
			throws AnalysisException
	{
		return node.getDef().apply(THIS);
	}

	@Override
	public Boolean defaultPDefinition(PDefinition node)
			throws AnalysisException
	{
		return false;
	}

	@Override
	public Boolean createNewReturnValue(INode node)
	{
		assert false : "should not happen";
		return null;
	}

	@Override
	public Boolean createNewReturnValue(Object node)
	{
		assert false : "should not happen";
		return null;
	}

}
