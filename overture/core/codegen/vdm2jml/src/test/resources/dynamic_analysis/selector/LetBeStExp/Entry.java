package project;

import java.util.*;
import org.overture.codegen.runtime.*;
import org.overture.codegen.vdm2jml.runtime.*;

@SuppressWarnings("all")
//@ nullable_by_default

final public class Entry {
  /*@ public ghost static boolean invChecksOn = true; @*/

  private Entry() {}

  public static Object Run() {

    {
      final Number ignorePattern_1 = f();
      //@ assert Utils.is_nat(ignorePattern_1);

      /* skip */
    }

    {
      final Number ignorePattern_2 = g();
      //@ assert Utils.is_nat(ignorePattern_2);

      /* skip */
    }

    IO.println("Done! Expected no violations");
    return 0L;
  }
  /*@ pure @*/

  public static Number f() {

    Number letBeStExp_1 = null;
    Number ignorePattern_3 = null;

    Boolean success_1 = false;
    //@ assert Utils.is_bool(success_1);

    VDMSet set_1 = SetUtil.set(1L, 2L, 3L);
    //@ assert (V2J.isSet(set_1) && (\forall int i; 0 <= i && i < V2J.size(set_1); Utils.is_nat1(V2J.get(set_1,i))));

    for (Iterator iterator_1 = set_1.iterator(); iterator_1.hasNext() && !(success_1); ) {
      ignorePattern_3 = ((Number) iterator_1.next());
      success_1 = true;
      //@ assert Utils.is_bool(success_1);

    }
    if (!(success_1)) {
      throw new RuntimeException("Let Be St found no applicable bindings");
    }

    letBeStExp_1 = 0L;
    //@ assert Utils.is_nat(letBeStExp_1);

    Number ret_1 = letBeStExp_1;
    //@ assert Utils.is_nat(ret_1);

    return ret_1;
  }
  /*@ pure @*/

  public static Number g() {

    Number letBeStExp_2 = null;
    Number x = null;

    Boolean success_2 = false;
    //@ assert Utils.is_bool(success_2);

    VDMSet set_2 = SetUtil.set(1L, 2L, 3L);
    //@ assert (V2J.isSet(set_2) && (\forall int i; 0 <= i && i < V2J.size(set_2); Utils.is_nat1(V2J.get(set_2,i))));

    for (Iterator iterator_2 = set_2.iterator(); iterator_2.hasNext() && !(success_2); ) {
      x = ((Number) iterator_2.next());
      success_2 = x.longValue() > 1L;
      //@ assert Utils.is_bool(success_2);

    }
    if (!(success_2)) {
      throw new RuntimeException("Let Be St found no applicable bindings");
    }

    letBeStExp_2 = 0L;
    //@ assert Utils.is_nat(letBeStExp_2);

    Number ret_2 = letBeStExp_2;
    //@ assert Utils.is_nat(ret_2);

    return ret_2;
  }

  public String toString() {

    return "Entry{}";
  }
}
