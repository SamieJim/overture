/*
 * #%~
 * org.overture.ide.ui
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
package org.overture.ide.ui.templates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalSorter;
import org.overture.ide.ui.completion.CompletionUtil;
import org.overture.ide.ui.editor.core.VdmDocument;

public abstract class VdmContentAssistProcessor extends
		VdmTemplateAssistProcessor
{
	private VdmCompleteProcessor processer = new VdmCompleteProcessor();

	public boolean enableTemplate()
	{
		return true;
	}

	/**
	 * @param offset an offset within the document for which completions should be computed
	 */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset)
	{
		List<ICompletionProposal> modList = new ArrayList<ICompletionProposal>();
		ICompletionProposal[] completionProposals = null;

		// IEditorInput editorInput = editor.getEditorInput();
		// String text = viewer.getTextWidget().getText();

		if (enableTemplate())
		{
			ICompletionProposal[] templates = super.computeCompletionProposals(viewer, offset);
			if (templates != null)
			{
				for (int i = 0; i < templates.length; i++)
				{
					modList.add(templates[i]);
				}

			}
		}

		if (viewer.getDocument() instanceof VdmDocument)
		{
			VdmCompletionContext completionContext = CompletionUtil.computeVdmCompletionContext(viewer.getDocument(), offset);
		    Region region = new Region(offset - completionContext.getProposalPrefix().length(), completionContext.getProposalPrefix().length());
		
		    processer.computeCompletionProposals(completionContext, 
		    		(VdmDocument) viewer.getDocument(), modList, offset,
		    		viewer, createContext(viewer, region));
		}

		if (modList.size() > 0)
		{
			completionProposals = (ICompletionProposal[]) modList.toArray(new ICompletionProposal[modList.size()]);
		}
		sortProposals(completionProposals);
		return completionProposals;
	}
	
	@SuppressWarnings("unchecked")
	private void sortProposals(final ICompletionProposal[] proposals) {
		if(proposals != null && proposals.length > 0){
			final ICompletionProposalSorter fSorter = new VdmCompletionProposalSorter();
			Arrays.sort(proposals, new Comparator() {
				public int compare(Object o1, Object o2) {
					return fSorter.compare((ICompletionProposal) o1,(ICompletionProposal) o2);
				}
			});
		}
	}

}