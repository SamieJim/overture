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
      //@ assert Utils.is_real(ignorePattern_1);

      /* skip */
    }

    IO.println("Done! Expected no violations");
    return 0L;
  }
  /*@ pure @*/

  public static Number f() {

    final Number realPattern_1 = 1.5;
    //@ assert Utils.is_rat(realPattern_1);

    Boolean success_1 = Utils.equals(realPattern_1, 1.5);
    //@ assert Utils.is_bool(success_1);

    if (!(success_1)) {
      throw new RuntimeException("Real pattern match failed");
    }

    Number ret_1 = 1.5;
    //@ assert Utils.is_real(ret_1);

    return ret_1;
  }

  public String toString() {

    return "Entry{}";
  }
}
