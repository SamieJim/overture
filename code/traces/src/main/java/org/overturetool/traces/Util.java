//
// THIS FILE IS AUTOMATICALLY GENERATED!!
//
// Generated at 2009-03-07 by the VDM++ to JAVA Code Generator
// (v8.2b - Thu 26-Feb-2009 17:11:12)
//
// Supported compilers: jdk 1.4/1.5/1.6
//

// ***** VDMTOOLS START Name=HeaderComment KEEP=NO
// ***** VDMTOOLS END Name=HeaderComment

// ***** VDMTOOLS START Name=package KEEP=NO
package org.overturetool.traces;

// ***** VDMTOOLS END Name=package

// ***** VDMTOOLS START Name=imports KEEP=YES

import jp.co.csk.vdm.toolbox.VDM.*;
import java.util.*;
import org.overturetool.ast.itf.*;
@SuppressWarnings({"unchecked","unused"})
// ***** VDMTOOLS END Name=imports



public class Util extends StdLib {

// ***** VDMTOOLS START Name=vdmComp KEEP=NO
  static UTIL.VDMCompare vdmComp = new UTIL.VDMCompare();
// ***** VDMTOOLS END Name=vdmComp

// ***** VDMTOOLS START Name=writeType KEEP=YES
  private static Object writeType = new jp.co.csk.vdm.toolbox.VDM.quotes.start();
// ***** VDMTOOLS END Name=writeType

// ***** VDMTOOLS START Name=buf KEEP=NO
  private static String buf = new String("");
// ***** VDMTOOLS END Name=buf

// ***** VDMTOOLS START Name=outputFileName KEEP=NO
  public static String outputFileName = new String("tmp.xmi");
// ***** VDMTOOLS END Name=outputFileName


// ***** VDMTOOLS START Name=vdm_init_Util KEEP=NO
  private void vdm_init_Util () throws CGException {}
// ***** VDMTOOLS END Name=vdm_init_Util


// ***** VDMTOOLS START Name=Util KEEP=NO
  public Util () throws CGException {
    vdm_init_Util();
  }
// ***** VDMTOOLS END Name=Util


// ***** VDMTOOLS START Name=Put#1|String KEEP=NO
  static public void Put (final String pVal) throws CGException {

    String rhs_2 = null;
    rhs_2 = buf.concat(pVal);
    buf = UTIL.ConvertToString(UTIL.clone(rhs_2));
  }
// ***** VDMTOOLS END Name=Put#1|String


// ***** VDMTOOLS START Name=ViewBuf KEEP=NO
  static public void ViewBuf () throws CGException {
    Print(buf);
  }
// ***** VDMTOOLS END Name=ViewBuf


// ***** VDMTOOLS START Name=SaveBuf#1|String KEEP=NO
  static public void SaveBuf (final String fileName) throws CGException {

    SetFileName(fileName);
    PrintL(buf);
  }
// ***** VDMTOOLS END Name=SaveBuf#1|String


// ***** VDMTOOLS START Name=Clear KEEP=NO
  static public void Clear () throws CGException {
    buf = UTIL.ConvertToString(UTIL.clone(new String("")));
  }
// ***** VDMTOOLS END Name=Clear


// ***** VDMTOOLS START Name=Print#1|String KEEP=NO
  static public void Print (final String debugString) throws CGException {

    IOProxy file = (IOProxy) new IOProxy();
    file.print(debugString);
  }
// ***** VDMTOOLS END Name=Print#1|String


// ***** VDMTOOLS START Name=SaveCharSeqMapSeqSeq#2|String|HashMap KEEP=YES
  static public void SaveCharSeqMapSeqSeq (final String filename, final HashMap val) throws CGException {

    Boolean tmpVal_4 = null;
    IOProxy obj_5 = null;
    obj_5 = (IOProxy) new IOProxy();
    tmpVal_4 = (Boolean) obj_5.fwriteval(filename, val, new jp.co.csk.vdm.toolbox.VDM.quotes.start());
  }
// ***** VDMTOOLS END Name=SaveCharSeqMapSeqSeq#2|String|HashMap


// ***** VDMTOOLS START Name=GetNameOfTrace#1|Vector KEEP=NO
  static public String GetNameOfTrace (final Vector names) throws CGException {

    String name = new String("");
    {

      String n = null;
      for (Iterator enm_12 = names.iterator(); enm_12.hasNext(); ) {

        String elem_3 = UTIL.ConvertToString(enm_12.next());
        n = elem_3;
        {

          String rhs_6 = null;
          String var1_7 = null;
          var1_7 = name.concat(new String("/"));
          rhs_6 = var1_7.concat(n);
          name = UTIL.ConvertToString(UTIL.clone(rhs_6));
        }
      }
    }
    String rexpr_13 = null;
    int from_17 = (int) Math.max(new Long(2).doubleValue() - 1, 0);
    int to_18 = (int) Math.min(new Long(name.length()).doubleValue(), name.length());
    if (from_17 > to_18) 
      rexpr_13 = new String();
    else 
      rexpr_13 = new String(name.substring(from_17, to_18));
    return rexpr_13;
  }
// ***** VDMTOOLS END Name=GetNameOfTrace#1|Vector


// ***** VDMTOOLS START Name=PrintL#1|String KEEP=NO
  static public void PrintL (final String line) throws CGException {

    IOProxy file = (IOProxy) new IOProxy();
    file.overwrite(outputFileName, line);
  }
// ***** VDMTOOLS END Name=PrintL#1|String


// ***** VDMTOOLS START Name=SetFileName#1|String KEEP=YES
  static public void SetFileName (final String name) throws CGException {

    outputFileName = UTIL.ConvertToString(UTIL.clone(name));
    writeType = UTIL.clone(new  jp.co.csk.vdm.toolbox.VDM.quotes.start());
  }
// ***** VDMTOOLS END Name=SetFileName#1|String


// ***** VDMTOOLS START Name=ExpandSpecTracesToString#1|HashMap KEEP=NO
  static public HashMap ExpandSpecTracesToString (final HashMap tc_um) throws CGException {

    HashMap rexpr_2 = new HashMap();
    HashMap res_m_3 = new HashMap();
    {

      HashSet e_set_37 = new HashSet();
      e_set_37.clear();
      e_set_37.addAll(tc_um.keySet());
      String clnm = null;
      {
        for (Iterator enm_40 = e_set_37.iterator(); enm_40.hasNext(); ) {

          String elem_39 = UTIL.ConvertToString(enm_40.next());
          clnm = elem_39;
          HashMap mr_5 = new HashMap();
          HashMap res_m_6 = new HashMap();
          {

            HashSet e_set_30 = new HashSet();
            e_set_30.clear();
            e_set_30.addAll(((HashMap) tc_um.get(clnm)).keySet());
            String tdnm = null;
            {
              for (Iterator enm_35 = e_set_30.iterator(); enm_35.hasNext(); ) {

                String elem_34 = UTIL.ConvertToString(enm_35.next());
                tdnm = elem_34;
                HashMap mr_8 = new HashMap();
                {

                  Vector tc_ul = (Vector) UTIL.ConvertToList(((HashMap) tc_um.get(clnm)).get(tdnm));
                  HashMap res_m_15 = new HashMap();
                  {

                    HashSet e_set_22 = new HashSet();
                    HashSet riseq_24 = new HashSet();
                    int max_25 = tc_ul.size();
                    for (int i_26 = 1; i_26 <= max_25; i_26++) 
                      riseq_24.add(new Long(i_26));
                    e_set_22 = riseq_24;
                    Long i = null;
                    {
                      for (Iterator enm_28 = e_set_22.iterator(); enm_28.hasNext(); ) {

                        Long elem_27 = UTIL.NumberToLong(enm_28.next());
                        i = elem_27;
                        Vector mr_17 = null;
                        Vector par_18 = null;
                        if ((1 <= i.intValue()) && (i.intValue() <= tc_ul.size())) 
                          par_18 = (Vector) UTIL.ConvertToList(tc_ul.get(i.intValue() - 1));
                        else 
                          UTIL.RunTime("Run-Time Error:Illegal index");
                        mr_17 = ExprToString(par_18);
                        res_m_15.put(i, mr_17);
                      }
                    }
                  }
                  mr_8 = res_m_15;
                }
                res_m_6.put(tdnm, mr_8);
              }
            }
          }
          mr_5 = res_m_6;
          res_m_3.put(clnm, mr_5);
        }
      }
    }
    rexpr_2 = res_m_3;
    return rexpr_2;
  }
// ***** VDMTOOLS END Name=ExpandSpecTracesToString#1|HashMap


// ***** VDMTOOLS START Name=ExprToString#1|Vector KEEP=NO
  static public Vector ExprToString (final Vector e_ul) throws CGException {

    Vector argexpr_ul = new Vector();
    {

      IOmlExpression e = null;
      for (Iterator enm_14 = e_ul.iterator(); enm_14.hasNext(); ) {

        IOmlExpression elem_3 = (IOmlExpression) enm_14.next();
        e = (IOmlExpression) elem_3;
        {

          Oml2VppVisitor ppvisitor = (Oml2VppVisitor) new Oml2VppVisitor();
          {

            ppvisitor.visitExpression((IOmlExpression) e);
            String e_11 = null;
            e_11 = ppvisitor.result;
            argexpr_ul.add(e_11);
          }
        }
      }
    }
    return argexpr_ul;
  }
// ***** VDMTOOLS END Name=ExprToString#1|Vector


// ***** VDMTOOLS START Name=GetTraceDefinitionClasses#1|IOmlSpecifications KEEP=NO
  static public HashSet GetTraceDefinitionClasses (final IOmlSpecifications spec) throws CGException {

    HashSet classes = new HashSet();
    HashSet res_s_4 = new HashSet();
    {

      Vector e_set_19 = null;
      e_set_19 = spec.getClassList();
      IOmlClass cl = null;
      {
        for (Iterator enm_21 = e_set_19.iterator(); enm_21.hasNext(); ) {

          IOmlClass elem_20 = (IOmlClass) enm_21.next();
          cl = (IOmlClass) elem_20;
          Boolean pred_6 = null;
          Long var1_7 = null;
          HashSet unArg_8 = new HashSet();
          HashSet res_s_9 = new HashSet();
          {

            Vector e_set_14 = null;
            e_set_14 = cl.getClassBody();
            IOmlDefinitionBlock dfs = null;
            {
              for (Iterator enm_16 = e_set_14.iterator(); enm_16.hasNext(); ) {

                IOmlDefinitionBlock elem_15 = (IOmlDefinitionBlock) enm_16.next();
                dfs = (IOmlDefinitionBlock) elem_15;
                if (new Boolean(dfs instanceof IOmlTraceDefinitions).booleanValue()) {
                  res_s_9.add(dfs);
                }
              }
            }
          }
          unArg_8 = res_s_9;
          var1_7 = new Long(unArg_8.size());
          pred_6 = new Boolean((var1_7.intValue()) > (new Long(0).intValue()));
          if (pred_6.booleanValue()) {

            String res_s_5 = null;
            res_s_5 = cl.getIdentifier();
            res_s_4.add(res_s_5);
          }
        }
      }
    }
    classes = res_s_4;
    return classes;
  }
// ***** VDMTOOLS END Name=GetTraceDefinitionClasses#1|IOmlSpecifications

}
;
