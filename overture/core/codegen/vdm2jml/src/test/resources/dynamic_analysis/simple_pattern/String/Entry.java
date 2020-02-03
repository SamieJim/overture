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
      final String ignorePattern_1 = f();
      //@ assert Utils.is_(ignorePattern_1,String.class);

      /* skip */
    }

    IO.println("Done! Expected no violations");
    return 0L;
  }
  /*@ pure @*/

  public static String f() {

    final String stringPattern_1 = "a";
    //@ assert Utils.is_(stringPattern_1,String.class);

    Boolean success_1 = Utils.equals(stringPattern_1, "a");
    //@ assert Utils.is_bool(success_1);

    if (!(success_1)) {
      throw new RuntimeException("String pattern match failed");
    }

    String ret_1 = "a";
    //@ assert Utils.is_(ret_1,String.class);

    return ret_1;
  }

  public String toString() {

    return "Entry{}";
  }
}
