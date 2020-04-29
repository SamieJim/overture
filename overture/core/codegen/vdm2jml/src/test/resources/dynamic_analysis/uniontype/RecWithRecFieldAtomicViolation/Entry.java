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

    Object r = new project.Entrytypes.A1(new project.Entrytypes.A2(1L));
    //@ assert (Utils.is_(r,project.Entrytypes.A1.class) || Utils.is_(r,project.Entrytypes.B1.class));

    IO.println("Before valid use");
    Number atomicTmp_1 = -5L;
    //@ assert Utils.is_int(atomicTmp_1);

    Number atomicTmp_2 = 5L;
    //@ assert Utils.is_int(atomicTmp_2);

    {
        /* Start of atomic statement */
      //@ set invChecksOn = false;

      Object apply_1 = null;
      if (r instanceof project.Entrytypes.A1) {
        apply_1 = ((project.Entrytypes.A1) r).get_f();
      } else if (r instanceof project.Entrytypes.B1) {
        apply_1 = ((project.Entrytypes.B1) r).get_f();
      } else {
        throw new RuntimeException("Missing member: f");
      }

      Object stateDes_1 = apply_1;
      if (stateDes_1 instanceof project.Entrytypes.A2) {
        //@ assert stateDes_1 != null;

        ((project.Entrytypes.A2) stateDes_1).set_x(atomicTmp_1);

      } else if (stateDes_1 instanceof project.Entrytypes.B2) {
        //@ assert stateDes_1 != null;

        ((project.Entrytypes.B2) stateDes_1).set_x(atomicTmp_1);

      } else {
        throw new RuntimeException("Missing member: x");
      }

      Object apply_2 = null;
      if (r instanceof project.Entrytypes.A1) {
        apply_2 = ((project.Entrytypes.A1) r).get_f();
      } else if (r instanceof project.Entrytypes.B1) {
        apply_2 = ((project.Entrytypes.B1) r).get_f();
      } else {
        throw new RuntimeException("Missing member: f");
      }

      Object stateDes_2 = apply_2;
      if (stateDes_2 instanceof project.Entrytypes.A2) {
        //@ assert stateDes_2 != null;

        ((project.Entrytypes.A2) stateDes_2).set_x(atomicTmp_2);

      } else if (stateDes_2 instanceof project.Entrytypes.B2) {
        //@ assert stateDes_2 != null;

        ((project.Entrytypes.B2) stateDes_2).set_x(atomicTmp_2);

      } else {
        throw new RuntimeException("Missing member: x");
      }

      //@ set invChecksOn = true;

      //@ assert stateDes_1 instanceof project.Entrytypes.A2 ==> ((project.Entrytypes.A2) stateDes_1).valid();

      //@ assert (Utils.is_(r,project.Entrytypes.A1.class) || Utils.is_(r,project.Entrytypes.B1.class));

      //@ assert r instanceof project.Entrytypes.B1 ==> ((project.Entrytypes.B1) r).valid();

      //@ assert r instanceof project.Entrytypes.A1 ==> ((project.Entrytypes.A1) r).valid();

      //@ assert stateDes_1 instanceof project.Entrytypes.B2 ==> ((project.Entrytypes.B2) stateDes_1).valid();

      //@ assert stateDes_2 instanceof project.Entrytypes.A2 ==> ((project.Entrytypes.A2) stateDes_2).valid();

      //@ assert stateDes_2 instanceof project.Entrytypes.B2 ==> ((project.Entrytypes.B2) stateDes_2).valid();

    } /* End of atomic statement */

    IO.println("After valid use");
    IO.println("Before illegal use");
    Number atomicTmp_3 = 5L;
    //@ assert Utils.is_int(atomicTmp_3);

    Number atomicTmp_4 = -5L;
    //@ assert Utils.is_int(atomicTmp_4);

    {
        /* Start of atomic statement */
      //@ set invChecksOn = false;

      Object apply_3 = null;
      if (r instanceof project.Entrytypes.A1) {
        apply_3 = ((project.Entrytypes.A1) r).get_f();
      } else if (r instanceof project.Entrytypes.B1) {
        apply_3 = ((project.Entrytypes.B1) r).get_f();
      } else {
        throw new RuntimeException("Missing member: f");
      }

      Object stateDes_3 = apply_3;
      if (stateDes_3 instanceof project.Entrytypes.A2) {
        //@ assert stateDes_3 != null;

        ((project.Entrytypes.A2) stateDes_3).set_x(atomicTmp_3);

      } else if (stateDes_3 instanceof project.Entrytypes.B2) {
        //@ assert stateDes_3 != null;

        ((project.Entrytypes.B2) stateDes_3).set_x(atomicTmp_3);

      } else {
        throw new RuntimeException("Missing member: x");
      }

      Object apply_4 = null;
      if (r instanceof project.Entrytypes.A1) {
        apply_4 = ((project.Entrytypes.A1) r).get_f();
      } else if (r instanceof project.Entrytypes.B1) {
        apply_4 = ((project.Entrytypes.B1) r).get_f();
      } else {
        throw new RuntimeException("Missing member: f");
      }

      Object stateDes_4 = apply_4;
      if (stateDes_4 instanceof project.Entrytypes.A2) {
        //@ assert stateDes_4 != null;

        ((project.Entrytypes.A2) stateDes_4).set_x(atomicTmp_4);

      } else if (stateDes_4 instanceof project.Entrytypes.B2) {
        //@ assert stateDes_4 != null;

        ((project.Entrytypes.B2) stateDes_4).set_x(atomicTmp_4);

      } else {
        throw new RuntimeException("Missing member: x");
      }

      //@ set invChecksOn = true;

      //@ assert stateDes_3 instanceof project.Entrytypes.A2 ==> ((project.Entrytypes.A2) stateDes_3).valid();

      //@ assert (Utils.is_(r,project.Entrytypes.A1.class) || Utils.is_(r,project.Entrytypes.B1.class));

      //@ assert r instanceof project.Entrytypes.B1 ==> ((project.Entrytypes.B1) r).valid();

      //@ assert r instanceof project.Entrytypes.A1 ==> ((project.Entrytypes.A1) r).valid();

      //@ assert stateDes_3 instanceof project.Entrytypes.B2 ==> ((project.Entrytypes.B2) stateDes_3).valid();

      //@ assert stateDes_4 instanceof project.Entrytypes.A2 ==> ((project.Entrytypes.A2) stateDes_4).valid();

      //@ assert stateDes_4 instanceof project.Entrytypes.B2 ==> ((project.Entrytypes.B2) stateDes_4).valid();

    } /* End of atomic statement */

    IO.println("After illegal use");
    return 0L;
  }

  public String toString() {

    return "Entry{}";
  }
}
