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

    Boolean b = true;
    //@ assert Utils.is_bool(b);

    Boolean bOpt = null;
    //@ assert ((bOpt == null) || Utils.is_bool(bOpt));

    IO.println("Before doing valid assignments");
    bOpt = true;
    //@ assert ((bOpt == null) || Utils.is_bool(bOpt));

    b = bOpt;
    //@ assert Utils.is_bool(b);

    bOpt = null;
    //@ assert ((bOpt == null) || Utils.is_bool(bOpt));

    IO.println("After doing valid assignments");
    IO.println("Before doing illegal assignments");
    b = bOpt;
    //@ assert Utils.is_bool(b);

    IO.println("After doing illegal assignments");
    return true;
  }

  public String toString() {

    return "Entry{}";
  }
}
