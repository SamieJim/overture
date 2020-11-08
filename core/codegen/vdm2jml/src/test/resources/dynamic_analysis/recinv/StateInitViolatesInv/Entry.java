package project;

import org.overture.codegen.runtime.Utils;

@SuppressWarnings("all")
//@ nullable_by_default

final public class Entry {
  /*@ spec_public @*/

  private static project.Entrytypes.St St = new project.Entrytypes.St(-5L);
  /*@ public ghost static boolean invChecksOn = true; @*/

  private Entry() {}

  public static Object Run() {

    return 1L;
  }

  public String toString() {

    return "Entry{" + "St := " + Utils.toString(St) + "}";
  }
}
