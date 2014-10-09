/*
 * #%~
 * org.overture.ide.vdmpp.ui
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
package org.overture.ide.vdmpp.ui.editor.contentAssist;

import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.overture.ide.ui.templates.VdmContentAssistant;

public class VdmPpContentAssistant extends VdmContentAssistant implements
		IContentAssistant {

	@Override
	public IContentAssistProcessor getContentAssistProcessor(String contentType) {
		return new VdmPpContentAssistProcessor();
	}

}
