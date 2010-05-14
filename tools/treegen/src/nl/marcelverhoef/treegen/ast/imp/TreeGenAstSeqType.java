// this file is automatically generated by treegen. do not modify!

package nl.marcelverhoef.treegen.ast.imp;

// import the abstract tree interfaces
import nl.marcelverhoef.treegen.ast.itf.*;

public class TreeGenAstSeqType extends TreeGenAstTypeSpecification implements ITreeGenAstSeqType
{
	// private member variable (type)
	private ITreeGenAstTypeSpecification m_type = null;

	// public operation to retrieve the embedded private field value
	public ITreeGenAstTypeSpecification getType()
	{
		return m_type;
	}

	// public operation to set the embedded private field value
	public void setType(ITreeGenAstTypeSpecification p_type)
	{
		// consistency check (field must be non null!)
		assert(p_type != null);

		// instantiate the member variable
		m_type = p_type;
	}

	// default constructor
	public TreeGenAstSeqType()
	{
		super();
		m_type = null;
	}

	// visitor support
	public void accept(ITreeGenAstVisitor pVisitor) { pVisitor.visitSeqType(this); }

	// the identity function
	public String identify() { return "TreeGenAstSeqType"; }
}
