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

    Number r = 1.23;
    //@ assert ((r == null) || Utils.is_real(r));

    IO.println("Before valid use.");
    doSkip(r);
    r = null;
    //@ assert ((r == null) || Utils.is_real(r));

    IO.println("After valid use.");
    IO.println("Before invalid use.");
    doSkip(r);
    IO.println("After invalid use.");
    return 0L;
  }

  public static void doSkip(final Number ignorePattern_1) {

    //@ assert Utils.is_real(ignorePattern_1);

    /* skip */

  }

  public String toString() {

    return "Entry{}";
  }
}
