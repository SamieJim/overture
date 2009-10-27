


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
public class UmlBes extends IUmlBes {

// ***** VDMTOOLS START Name=vdmComp KEEP=NO
  static UTIL.VDMCompare vdmComp = new UTIL.VDMCompare();
// ***** VDMTOOLS END Name=vdmComp

// ***** VDMTOOLS START Name=ivName KEEP=NO
  private String ivName = null;
// ***** VDMTOOLS END Name=ivName

// ***** VDMTOOLS START Name=ivStartOs KEEP=NO
  private IUmlMos ivStartOs = null;
// ***** VDMTOOLS END Name=ivStartOs

// ***** VDMTOOLS START Name=ivFinishOs KEEP=NO
  private IUmlMos ivFinishOs = null;
// ***** VDMTOOLS END Name=ivFinishOs

// ***** VDMTOOLS START Name=ivCovered KEEP=NO
  private HashSet ivCovered = new HashSet();
// ***** VDMTOOLS END Name=ivCovered


// ***** VDMTOOLS START Name=vdm_init_UmlBes KEEP=NO
  private void vdm_init_UmlBes () throws CGException {
    try {

      ivName = UTIL.ConvertToString(new String());
      ivStartOs = null;
      ivFinishOs = null;
      ivCovered = new HashSet();
    }
    catch (Exception e){

      e.printStackTrace(System.out);
      System.out.println(e.getMessage());
    }
  }
// ***** VDMTOOLS END Name=vdm_init_UmlBes


// ***** VDMTOOLS START Name=UmlBes KEEP=NO
  public UmlBes () throws CGException {
    vdm_init_UmlBes();
  }
// ***** VDMTOOLS END Name=UmlBes


// ***** VDMTOOLS START Name=identity KEEP=NO
  public String identity () throws CGException {
    return new String("Bes");
  }
// ***** VDMTOOLS END Name=identity


// ***** VDMTOOLS START Name=accept#1|IUmlVisitor KEEP=NO
  public void accept (final IUmlVisitor pVisitor) throws CGException {
    pVisitor.visitBes((IUmlBes) this);
  }
// ***** VDMTOOLS END Name=accept#1|IUmlVisitor


// ***** VDMTOOLS START Name=UmlBes#4|String|IUmlMos|IUmlMos|HashSet KEEP=NO
  public UmlBes (final String p1, final IUmlMos p2, final IUmlMos p3, final HashSet p4) throws CGException {

    vdm_init_UmlBes();
    {

      setName(p1);
      setStartOs((IUmlMos) p2);
      setFinishOs((IUmlMos) p3);
      setCovered(p4);
    }
  }
// ***** VDMTOOLS END Name=UmlBes#4|String|IUmlMos|IUmlMos|HashSet


// ***** VDMTOOLS START Name=UmlBes#6|String|IUmlMos|IUmlMos|HashSet|Long|Long KEEP=NO
  public UmlBes (final String p1, final IUmlMos p2, final IUmlMos p3, final HashSet p4, final Long line, final Long column) throws CGException {

    vdm_init_UmlBes();
    {

      setName(p1);
      setStartOs((IUmlMos) p2);
      setFinishOs((IUmlMos) p3);
      setCovered(p4);
      setPosition(line, column);
    }
  }
// ***** VDMTOOLS END Name=UmlBes#6|String|IUmlMos|IUmlMos|HashSet|Long|Long


// ***** VDMTOOLS START Name=init#1|HashMap KEEP=NO
  public void init (final HashMap data) throws CGException {

    {

      String fname = new String("name");
      Boolean cond_4 = null;
      cond_4 = new Boolean(data.containsKey(fname));
      if (cond_4.booleanValue()) 
        setName(UTIL.ConvertToString(data.get(fname)));
    }
    {

      String fname = new String("startOs");
      Boolean cond_13 = null;
      cond_13 = new Boolean(data.containsKey(fname));
      if (cond_13.booleanValue()) 
        setStartOs((IUmlMos) data.get(fname));
    }
    {

      String fname = new String("finishOs");
      Boolean cond_22 = null;
      cond_22 = new Boolean(data.containsKey(fname));
      if (cond_22.booleanValue()) 
        setFinishOs((IUmlMos) data.get(fname));
    }
    {

      String fname = new String("covered");
      Boolean cond_31 = null;
      cond_31 = new Boolean(data.containsKey(fname));
      if (cond_31.booleanValue()) 
        setCovered((HashSet) data.get(fname));
    }
  }
// ***** VDMTOOLS END Name=init#1|HashMap


// ***** VDMTOOLS START Name=getName KEEP=NO
  public String getName () throws CGException {
    return ivName;
  }
// ***** VDMTOOLS END Name=getName


// ***** VDMTOOLS START Name=setName#1|String KEEP=NO
  public void setName (final String parg) throws CGException {
    ivName = UTIL.ConvertToString(UTIL.clone(parg));
  }
// ***** VDMTOOLS END Name=setName#1|String


// ***** VDMTOOLS START Name=getStartOs KEEP=NO
  public IUmlMos getStartOs () throws CGException {
    return (IUmlMos) ivStartOs;
  }
// ***** VDMTOOLS END Name=getStartOs


// ***** VDMTOOLS START Name=setStartOs#1|IUmlMos KEEP=NO
  public void setStartOs (final IUmlMos parg) throws CGException {
    ivStartOs = (IUmlMos) UTIL.clone(parg);
  }
// ***** VDMTOOLS END Name=setStartOs#1|IUmlMos


// ***** VDMTOOLS START Name=getFinishOs KEEP=NO
  public IUmlMos getFinishOs () throws CGException {
    return (IUmlMos) ivFinishOs;
  }
// ***** VDMTOOLS END Name=getFinishOs


// ***** VDMTOOLS START Name=setFinishOs#1|IUmlMos KEEP=NO
  public void setFinishOs (final IUmlMos parg) throws CGException {
    ivFinishOs = (IUmlMos) UTIL.clone(parg);
  }
// ***** VDMTOOLS END Name=setFinishOs#1|IUmlMos


// ***** VDMTOOLS START Name=getCovered KEEP=NO
  public HashSet getCovered () throws CGException {
    return ivCovered;
  }
// ***** VDMTOOLS END Name=getCovered


// ***** VDMTOOLS START Name=setCovered#1|HashSet KEEP=NO
  public void setCovered (final HashSet parg) throws CGException {
    ivCovered = (HashSet) UTIL.clone(parg);
  }
// ***** VDMTOOLS END Name=setCovered#1|HashSet


// ***** VDMTOOLS START Name=addCovered#1|IUmlNode KEEP=NO
  public void addCovered (final IUmlNode parg) throws CGException {
    ivCovered.add(parg);
  }
// ***** VDMTOOLS END Name=addCovered#1|IUmlNode

}
;