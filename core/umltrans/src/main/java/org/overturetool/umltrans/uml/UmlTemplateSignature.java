


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
public class UmlTemplateSignature extends IUmlTemplateSignature {

// ***** VDMTOOLS START Name=vdmComp KEEP=NO
  static UTIL.VDMCompare vdmComp = new UTIL.VDMCompare();
// ***** VDMTOOLS END Name=vdmComp

// ***** VDMTOOLS START Name=ivTemplateParameters KEEP=NO
  private HashSet ivTemplateParameters = new HashSet();
// ***** VDMTOOLS END Name=ivTemplateParameters


// ***** VDMTOOLS START Name=vdm_init_UmlTemplateSignature KEEP=NO
  private void vdm_init_UmlTemplateSignature () throws CGException {
    try {
      ivTemplateParameters = new HashSet();
    }
    catch (Exception e){

      e.printStackTrace(System.out);
      System.out.println(e.getMessage());
    }
  }
// ***** VDMTOOLS END Name=vdm_init_UmlTemplateSignature


// ***** VDMTOOLS START Name=UmlTemplateSignature KEEP=NO
  public UmlTemplateSignature () throws CGException {
    vdm_init_UmlTemplateSignature();
  }
// ***** VDMTOOLS END Name=UmlTemplateSignature


// ***** VDMTOOLS START Name=identity KEEP=NO
  public String identity () throws CGException {
    return new String("TemplateSignature");
  }
// ***** VDMTOOLS END Name=identity


// ***** VDMTOOLS START Name=accept#1|IUmlVisitor KEEP=NO
  public void accept (final IUmlVisitor pVisitor) throws CGException {
    pVisitor.visitTemplateSignature((IUmlTemplateSignature) this);
  }
// ***** VDMTOOLS END Name=accept#1|IUmlVisitor


// ***** VDMTOOLS START Name=UmlTemplateSignature#1|HashSet KEEP=NO
  public UmlTemplateSignature (final HashSet p1) throws CGException {

    vdm_init_UmlTemplateSignature();
    setTemplateParameters(p1);
  }
// ***** VDMTOOLS END Name=UmlTemplateSignature#1|HashSet


// ***** VDMTOOLS START Name=UmlTemplateSignature#3|HashSet|Long|Long KEEP=NO
  public UmlTemplateSignature (final HashSet p1, final Long line, final Long column) throws CGException {

    vdm_init_UmlTemplateSignature();
    {

      setTemplateParameters(p1);
      setPosition(line, column);
    }
  }
// ***** VDMTOOLS END Name=UmlTemplateSignature#3|HashSet|Long|Long


// ***** VDMTOOLS START Name=init#1|HashMap KEEP=NO
  public void init (final HashMap data) throws CGException {

    String fname = new String("templateParameters");
    Boolean cond_4 = null;
    cond_4 = new Boolean(data.containsKey(fname));
    if (cond_4.booleanValue()) 
      setTemplateParameters((HashSet) data.get(fname));
  }
// ***** VDMTOOLS END Name=init#1|HashMap


// ***** VDMTOOLS START Name=getTemplateParameters KEEP=NO
  public HashSet getTemplateParameters () throws CGException {
    return ivTemplateParameters;
  }
// ***** VDMTOOLS END Name=getTemplateParameters


// ***** VDMTOOLS START Name=setTemplateParameters#1|HashSet KEEP=NO
  public void setTemplateParameters (final HashSet parg) throws CGException {
    ivTemplateParameters = (HashSet) UTIL.clone(parg);
  }
// ***** VDMTOOLS END Name=setTemplateParameters#1|HashSet


// ***** VDMTOOLS START Name=addTemplateParameters#1|IUmlNode KEEP=NO
  public void addTemplateParameters (final IUmlNode parg) throws CGException {
    ivTemplateParameters.add(parg);
  }
// ***** VDMTOOLS END Name=addTemplateParameters#1|IUmlNode

}
;