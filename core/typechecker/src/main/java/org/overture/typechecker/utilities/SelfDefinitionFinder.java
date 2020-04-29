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
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.node.INode;
import org.overture.ast.typechecker.NameScope;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

/**
 * This class implements a way to find the self definition from a node in the AST
 * 
 * @author kel
 */
public class SelfDefinitionFinder extends AnswerAdaptor<PDefinition>
{

	protected ITypeCheckerAssistantFactory af;

	public SelfDefinitionFinder(ITypeCheckerAssistantFactory af)
	{
		this.af = af;
	}

	@Override
	public PDefinition defaultSClassDefinition(SClassDefinition classDefinition)
			throws AnalysisException
	{
		PDefinition def = AstFactory.newALocalDefinition(classDefinition.getLocation(), classDefinition.getName().getSelfName(), NameScope.LOCAL, af.createPDefinitionAssistant().getType(classDefinition));
		af.createPDefinitionAssistant().markUsed(def);
		return def;
	}

	@Override
	public PDefinition defaultPDefinition(PDefinition node)
			throws AnalysisException
	{
		return node.getClassDefinition().apply(THIS);
	}

	@Override
	public PDefinition createNewReturnValue(INode node)
	{
		assert false : "should not happen";
		return null;
	}

	@Override
	public PDefinition createNewReturnValue(Object node)
	{
		assert false : "should not happen";
		return null;
	}
}
