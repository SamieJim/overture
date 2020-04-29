package project;

import java.util.*;
import org.overture.codegen.runtime.*;
import org.overture.codegen.vdm2jml.runtime.*;

@SuppressWarnings("all")
//@ nullable_by_default

final public class Entry {
  /*@ public ghost static boolean invChecksOn = true; @*/

  private Entry() {}

  public static void typeUseOk() {

    final Number ignorePattern_1 = 1L;
    //@ assert Utils.is_nat1(ignorePattern_1);

    final Object even = 2L;
    //@ assert (((Utils.is_nat(even) && inv_Entry_Even(even)) || (Utils.is_real(even) && inv_Entry_Large(even))) && inv_Entry_No(even));

    final Number ignorePattern_2 = 3L;
    //@ assert Utils.is_nat1(ignorePattern_2);

    /* skip */

  }

  public static void typeUseNotOk() {

    IO.println("Before breaking named type invariant");
    {
      Object notLarge = 999L;
      //@ assert (((Utils.is_nat(notLarge) && inv_Entry_Even(notLarge)) || (Utils.is_real(notLarge) && inv_Entry_Large(notLarge))) && inv_Entry_No(notLarge));

      IO.println("After breaking named type invariant");
      /* skip */
    }
  }

  public static Object Run() {

    typeUseOk();
    typeUseNotOk();
    return 0L;
  }

  public String toString() {

    return "Entry{}";
  }

  /*@ pure @*/
  /*@ helper @*/

  public static Boolean inv_Entry_No(final Object check_elem) {

    return true;
  }

  /*@ pure @*/
  /*@ helper @*/

  public static Boolean inv_Entry_Even(final Object check_ev) {

    Number ev = ((Number) check_ev);

    return Utils.equals(Utils.mod(ev.longValue(), 2L), 0L);
  }

  /*@ pure @*/
  /*@ helper @*/

  public static Boolean inv_Entry_Large(final Object check_la) {

    Number la = ((Number) check_la);

    return la.doubleValue() > 1000L;
  }
}
