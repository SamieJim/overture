


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
public abstract class IUmlLexem {

// ***** VDMTOOLS START Name=vdmComp KEEP=NO
  static UTIL.VDMCompare vdmComp = new UTIL.VDMCompare();
// ***** VDMTOOLS END Name=vdmComp

// ***** VDMTOOLS START Name=ILEXEMUNKNOWN KEEP=NO
  public static final Long ILEXEMUNKNOWN = new Long(0);
// ***** VDMTOOLS END Name=ILEXEMUNKNOWN

// ***** VDMTOOLS START Name=ILEXEMKEYWORD KEEP=NO
  public static final Long ILEXEMKEYWORD = new Long(1);
// ***** VDMTOOLS END Name=ILEXEMKEYWORD

// ***** VDMTOOLS START Name=ILEXEMIDENTIFIER KEEP=NO
  public static final Long ILEXEMIDENTIFIER = new Long(2);
// ***** VDMTOOLS END Name=ILEXEMIDENTIFIER

// ***** VDMTOOLS START Name=ILEXEMLINECOMMENT KEEP=NO
  public static final Long ILEXEMLINECOMMENT = new Long(3);
// ***** VDMTOOLS END Name=ILEXEMLINECOMMENT

// ***** VDMTOOLS START Name=ILEXEMBLOCKCOMMENT KEEP=NO
  public static final Long ILEXEMBLOCKCOMMENT = new Long(4);
// ***** VDMTOOLS END Name=ILEXEMBLOCKCOMMENT


// ***** VDMTOOLS START Name=vdm_init_IUmlLexem KEEP=NO
  private void vdm_init_IUmlLexem () throws CGException {}
// ***** VDMTOOLS END Name=vdm_init_IUmlLexem


// ***** VDMTOOLS START Name=IUmlLexem KEEP=NO
  public IUmlLexem () throws CGException {
    vdm_init_IUmlLexem();
  }
// ***** VDMTOOLS END Name=IUmlLexem


// ***** VDMTOOLS START Name=accept#1|IUmlVisitor KEEP=NO
  abstract public void accept (final IUmlVisitor var_1_1) throws CGException ;
// ***** VDMTOOLS END Name=accept#1|IUmlVisitor


// ***** VDMTOOLS START Name=getLine KEEP=NO
  abstract public Long getLine () throws CGException ;
// ***** VDMTOOLS END Name=getLine


// ***** VDMTOOLS START Name=getColumn KEEP=NO
  abstract public Long getColumn () throws CGException ;
// ***** VDMTOOLS END Name=getColumn


// ***** VDMTOOLS START Name=getLexval KEEP=NO
  abstract public Long getLexval () throws CGException ;
// ***** VDMTOOLS END Name=getLexval


// ***** VDMTOOLS START Name=getText KEEP=NO
  abstract public String getText () throws CGException ;
// ***** VDMTOOLS END Name=getText


// ***** VDMTOOLS START Name=getType KEEP=NO
  abstract public Long getType () throws CGException ;
// ***** VDMTOOLS END Name=getType


// ***** VDMTOOLS START Name=isKeyword KEEP=NO
  abstract public Boolean isKeyword () throws CGException ;
// ***** VDMTOOLS END Name=isKeyword


// ***** VDMTOOLS START Name=isIdentifier KEEP=NO
  abstract public Boolean isIdentifier () throws CGException ;
// ***** VDMTOOLS END Name=isIdentifier


// ***** VDMTOOLS START Name=isComment KEEP=NO
  abstract public Boolean isComment () throws CGException ;
// ***** VDMTOOLS END Name=isComment


// ***** VDMTOOLS START Name=isLineComment KEEP=NO
  abstract public Boolean isLineComment () throws CGException ;
// ***** VDMTOOLS END Name=isLineComment


// ***** VDMTOOLS START Name=isBlockComment KEEP=NO
  abstract public Boolean isBlockComment () throws CGException ;
// ***** VDMTOOLS END Name=isBlockComment

}
;