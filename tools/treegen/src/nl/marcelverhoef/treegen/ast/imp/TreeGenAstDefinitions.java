// this file is automatically generated by treegen. do not modify!

package nl.marcelverhoef.treegen.ast.imp;

// import the abstract tree interfaces
import nl.marcelverhoef.treegen.ast.itf.*;

public class TreeGenAstDefinitions extends TreeGenAstNode implements ITreeGenAstDefinitions
{
	// default constructor
	public TreeGenAstDefinitions()
	{
		super();
	}

	// visitor support
	public void accept(ITreeGenAstVisitor pVisitor) { pVisitor.visitDefinitions(this); }

	// the identity function
	public String identify() { return "TreeGenAstDefinitions"; }
}
