


//
// THIS FILE IS AUTOMATICALLY GENERATED!!
//
// Generated at 2009-10-24 by the VDM++ to JAVA Code Generator
// (v8.2.1b - Wed 15-Jul-2009 14:09:22)
//
// Supported compilers: jdk 1.4/1.5/1.6
//

// ***** VDMTOOLS START Name=HeaderComment KEEP=NO
// ***** VDMTOOLS END Name=HeaderComment

// ***** VDMTOOLS START Name=package KEEP=NO
package org.overturetool.umltrans.uml;

// ***** VDMTOOLS END Name=package

// ***** VDMTOOLS START Name=imports KEEP=NO

import jp.co.csk.vdm.toolbox.VDM.*;
import java.util.*;
import org.overturetool.ast.itf.*;
import org.overturetool.ast.imp.*;
import org.overturetool.api.io.*;
import org.overturetool.api.io.*;
import org.overturetool.api.xml.*;
import org.overturetool.umltrans.api.*;
import org.overturetool.umltrans.*;
import org.overturetool.umltrans.uml.*;
import org.overturetool.umltrans.uml2vdm.*;
import org.overturetool.umltrans.vdm2uml.*;
// ***** VDMTOOLS END Name=imports



@SuppressWarnings({"all","unchecked","unused"})
public class UmlConstraint extends IUmlConstraint {

// ***** VDMTOOLS START Name=vdmComp KEEP=NO
  static UTIL.VDMCompare vdmComp = new UTIL.VDMCompare();
// ***** VDMTOOLS END Name=vdmComp

// ***** VDMTOOLS START Name=ivConstraintElements KEEP=NO
  private HashSet ivConstraintElements = new HashSet();
// ***** VDMTOOLS END Name=ivConstraintElements

// ***** VDMTOOLS START Name=ivSpecification KEEP=NO
  private IUmlValueSpecification ivSpecification = null;
// ***** VDMTOOLS END Name=ivSpecification


// ***** VDMTOOLS START Name=vdm_init_UmlConstraint KEEP=NO
  private void vdm_init_UmlConstraint () throws CGException {
    try {

      ivConstraintElements = new HashSet();
      ivSpecification = null;
    }
    catch (Exception e){

      e.printStackTrace(System.out);
      System.out.println(e.getMessage());
    }
  }
// ***** VDMTOOLS END Name=vdm_init_UmlConstraint


// ***** VDMTOOLS START Name=UmlConstraint KEEP=NO
  public UmlConstraint () throws CGException {
    vdm_init_UmlConstraint();
  }
// ***** VDMTOOLS END Name=UmlConstraint


// ***** VDMTOOLS START Name=identity KEEP=NO
  public String identity () throws CGException {
    return new String("Constraint");
  }
// ***** VDMTOOLS END Name=identity


// ***** VDMTOOLS START Name=accept#1|IUmlVisitor KEEP=NO
  public void accept (final IUmlVisitor pVisitor) throws CGException {
    pVisitor.visitConstraint((IUmlConstraint) this);
  }
// ***** VDMTOOLS END Name=accept#1|IUmlVisitor


// ***** VDMTOOLS START Name=UmlConstraint#2|HashSet|IUmlValueSpecification KEEP=NO
  public UmlConstraint (final HashSet p1, final IUmlValueSpecification p2) throws CGException {

    vdm_init_UmlConstraint();
    {

      setConstraintElements(p1);
      setSpecification((IUmlValueSpecification) p2);
    }
  }
// ***** VDMTOOLS END Name=UmlConstraint#2|HashSet|IUmlValueSpecification


// ***** VDMTOOLS START Name=UmlConstraint#4|HashSet|IUmlValueSpecification|Long|Long KEEP=NO
  public UmlConstraint (final HashSet p1, final IUmlValueSpecification p2, final Long line, final Long column) throws CGException {

    vdm_init_UmlConstraint();
    {

      setConstraintElements(p1);
      setSpecification((IUmlValueSpecification) p2);
      setPosition(line, column);
    }
  }
// ***** VDMTOOLS END Name=UmlConstraint#4|HashSet|IUmlValueSpecification|Long|Long


// ***** VDMTOOLS START Name=init#1|HashMap KEEP=NO
  public void init (final HashMap data) throws CGException {

    {

      String fname = new String("constraintElements");
      Boolean cond_4 = null;
      cond_4 = new Boolean(data.containsKey(fname));
      if (cond_4.booleanValue()) 
        setConstraintElements((HashSet) data.get(fname));
    }
    {

      String fname = new String("specification");
      Boolean cond_13 = null;
      cond_13 = new Boolean(data.containsKey(fname));
      if (cond_13.booleanValue()) 
        setSpecification((IUmlValueSpecification) data.get(fname));
    }
  }
// ***** VDMTOOLS END Name=init#1|HashMap


// ***** VDMTOOLS START Name=getConstraintElements KEEP=NO
  public HashSet getConstraintElements () throws CGException {
    return ivConstraintElements;
  }
// ***** VDMTOOLS END Name=getConstraintElements


// ***** VDMTOOLS START Name=setConstraintElements#1|HashSet KEEP=NO
  public void setConstraintElements (final HashSet parg) throws CGException {
    ivConstraintElements = (HashSet) UTIL.clone(parg);
  }
// ***** VDMTOOLS END Name=setConstraintElements#1|HashSet


// ***** VDMTOOLS START Name=addConstraintElements#1|String KEEP=NO
  public void addConstraintElements (final String parg) throws CGException {
    ivConstraintElements.add(parg);
  }
// ***** VDMTOOLS END Name=addConstraintElements#1|String


// ***** VDMTOOLS START Name=getSpecification KEEP=NO
  public IUmlValueSpecification getSpecification () throws CGException {
    return (IUmlValueSpecification) ivSpecification;
  }
// ***** VDMTOOLS END Name=getSpecification


// ***** VDMTOOLS START Name=setSpecification#1|IUmlValueSpecification KEEP=NO
  public void setSpecification (final IUmlValueSpecification parg) throws CGException {
    ivSpecification = (IUmlValueSpecification) UTIL.clone(parg);
  }
// ***** VDMTOOLS END Name=setSpecification#1|IUmlValueSpecification

}
;