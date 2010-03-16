package org.overture.ide.ui.editor.syntax;

import org.eclipse.jface.text.rules.IWordDetector;

public class VdmWordDetector implements IWordDetector {


	/* (non-Javadoc)
	 * Method declared on IWordDetector.
	 */
	public boolean isWordPart(char character) {
		return Character.isJavaIdentifierPart(character);
	}
	
	/* (non-Javadoc)
	 * Method declared on IWordDetector.
	 */
	public boolean isWordStart(char character) {
		return Character.isJavaIdentifierStart(character);
	}

}