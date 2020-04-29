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

    IO.println("Before evaluating ok()");
    {
      final Token ignorePattern_1 = ok();
      //@ assert Utils.is_token(ignorePattern_1);

      /* skip */
    }

    IO.println("After evaluating ok()");
    IO.println("Before evaluating error()");
    {
      final Token ignorePattern_2 = err();
      //@ assert Utils.is_token(ignorePattern_2);

      /* skip */
    }

    IO.println("After evaluating error()");
    return true;
  }
  /*@ pure @*/

  public static Token ok() {

    final Token aOpt = new Token("");
    //@ assert ((aOpt == null) || Utils.is_token(aOpt));

    Token ret_1 = aOpt;
    //@ assert Utils.is_token(ret_1);

    return ret_1;
  }
  /*@ pure @*/

  public static Token err() {

    final Token aOpt = null;
    //@ assert ((aOpt == null) || Utils.is_token(aOpt));

    Token ret_2 = aOpt;
    //@ assert Utils.is_token(ret_2);

    return ret_2;
  }

  public String toString() {

    return "Entry{}";
  }
}
