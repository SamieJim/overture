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

    IO.println("Before useOk");
    {
      final Number ignorePattern_1 = useOk();
      //@ assert Utils.is_nat(ignorePattern_1);

      /* skip */
    }

    IO.println("After useOk");
    IO.println("Before useNotOk");
    {
      final Number ignorePattern_2 = useNotOk();
      //@ assert Utils.is_nat(ignorePattern_2);

      /* skip */
    }

    IO.println("After useNotOk");
    return 0L;
  }

  public static Number useOk() {

    project.Entrytypes.R1 r1 =
        new project.Entrytypes.R1(
            new project.Entrytypes.R2(new project.Entrytypes.R3(new project.Entrytypes.R4(5L))));
    //@ assert Utils.is_(r1,project.Entrytypes.R1.class);

    Number atomicTmp_1 = 10L;
    //@ assert Utils.is_int(atomicTmp_1);

    Number atomicTmp_2 = 3L;
    //@ assert Utils.is_int(atomicTmp_2);

    Number atomicTmp_3 = 5L;
    //@ assert Utils.is_int(atomicTmp_3);

    {
        /* Start of atomic statement */
      //@ set invChecksOn = false;

      project.Entrytypes.R2 stateDes_1 = r1.get_r2();

      Object stateDes_2 = stateDes_1.get_t3();

      project.Entrytypes.R4 apply_1 = null;
      if (stateDes_2 instanceof project.Entrytypes.R3) {
        apply_1 = ((project.Entrytypes.R3) stateDes_2).get_r4();
      } else {
        throw new RuntimeException("Missing member: r4");
      }

      project.Entrytypes.R4 stateDes_3 = apply_1;
      //@ assert stateDes_3 != null;

      stateDes_3.set_x(atomicTmp_1);

      project.Entrytypes.R2 stateDes_4 = r1.get_r2();

      Object stateDes_5 = stateDes_4.get_t3();

      project.Entrytypes.R4 apply_2 = null;
      if (stateDes_5 instanceof project.Entrytypes.R3) {
        apply_2 = ((project.Entrytypes.R3) stateDes_5).get_r4();
      } else {
        throw new RuntimeException("Missing member: r4");
      }

      project.Entrytypes.R4 stateDes_6 = apply_2;
      //@ assert stateDes_6 != null;

      stateDes_6.set_x(atomicTmp_2);

      project.Entrytypes.R2 stateDes_7 = r1.get_r2();

      Object stateDes_8 = stateDes_7.get_t3();

      project.Entrytypes.R4 apply_3 = null;
      if (stateDes_8 instanceof project.Entrytypes.R3) {
        apply_3 = ((project.Entrytypes.R3) stateDes_8).get_r4();
      } else {
        throw new RuntimeException("Missing member: r4");
      }

      project.Entrytypes.R4 stateDes_9 = apply_3;
      //@ assert stateDes_9 != null;

      stateDes_9.set_x(atomicTmp_3);

      //@ set invChecksOn = true;

      //@ assert stateDes_3.valid();

      //@ assert ((Utils.is_(stateDes_2,project.Entrytypes.R3.class) || Utils.is_(stateDes_2,project.Entrytypes.X.class)) && inv_Entry_T3(stateDes_2));

      //@ assert stateDes_2 instanceof project.Entrytypes.X ==> ((project.Entrytypes.X) stateDes_2).valid();

      //@ assert stateDes_2 instanceof project.Entrytypes.R3 ==> ((project.Entrytypes.R3) stateDes_2).valid();

      //@ assert Utils.is_(stateDes_1,project.Entrytypes.R2.class);

      //@ assert stateDes_1.valid();

      //@ assert Utils.is_(r1,project.Entrytypes.R1.class);

      //@ assert r1.valid();

      //@ assert stateDes_6.valid();

      //@ assert ((Utils.is_(stateDes_5,project.Entrytypes.R3.class) || Utils.is_(stateDes_5,project.Entrytypes.X.class)) && inv_Entry_T3(stateDes_5));

      //@ assert stateDes_5 instanceof project.Entrytypes.X ==> ((project.Entrytypes.X) stateDes_5).valid();

      //@ assert stateDes_5 instanceof project.Entrytypes.R3 ==> ((project.Entrytypes.R3) stateDes_5).valid();

      //@ assert Utils.is_(stateDes_4,project.Entrytypes.R2.class);

      //@ assert stateDes_4.valid();

      //@ assert stateDes_9.valid();

      //@ assert ((Utils.is_(stateDes_8,project.Entrytypes.R3.class) || Utils.is_(stateDes_8,project.Entrytypes.X.class)) && inv_Entry_T3(stateDes_8));

      //@ assert stateDes_8 instanceof project.Entrytypes.X ==> ((project.Entrytypes.X) stateDes_8).valid();

      //@ assert stateDes_8 instanceof project.Entrytypes.R3 ==> ((project.Entrytypes.R3) stateDes_8).valid();

      //@ assert Utils.is_(stateDes_7,project.Entrytypes.R2.class);

      //@ assert stateDes_7.valid();

    } /* End of atomic statement */

    Number ret_1 = 0L;
    //@ assert Utils.is_nat(ret_1);

    return ret_1;
  }

  public static Number useNotOk() {

    project.Entrytypes.R1 r1 =
        new project.Entrytypes.R1(
            new project.Entrytypes.R2(new project.Entrytypes.R3(new project.Entrytypes.R4(5L))));
    //@ assert Utils.is_(r1,project.Entrytypes.R1.class);

    Number atomicTmp_4 = 3L;
    //@ assert Utils.is_int(atomicTmp_4);

    Number atomicTmp_5 = 3L;
    //@ assert Utils.is_int(atomicTmp_5);

    {
        /* Start of atomic statement */
      //@ set invChecksOn = false;

      project.Entrytypes.R2 stateDes_10 = r1.get_r2();

      Object stateDes_11 = stateDes_10.get_t3();

      project.Entrytypes.R4 apply_4 = null;
      if (stateDes_11 instanceof project.Entrytypes.R3) {
        apply_4 = ((project.Entrytypes.R3) stateDes_11).get_r4();
      } else {
        throw new RuntimeException("Missing member: r4");
      }

      project.Entrytypes.R4 stateDes_12 = apply_4;
      //@ assert stateDes_12 != null;

      stateDes_12.set_x(atomicTmp_4);

      project.Entrytypes.R2 stateDes_13 = r1.get_r2();

      Object stateDes_14 = stateDes_13.get_t3();

      project.Entrytypes.R4 apply_5 = null;
      if (stateDes_14 instanceof project.Entrytypes.R3) {
        apply_5 = ((project.Entrytypes.R3) stateDes_14).get_r4();
      } else {
        throw new RuntimeException("Missing member: r4");
      }

      project.Entrytypes.R4 stateDes_15 = apply_5;
      //@ assert stateDes_15 != null;

      stateDes_15.set_x(atomicTmp_5);

      //@ set invChecksOn = true;

      //@ assert stateDes_12.valid();

      //@ assert ((Utils.is_(stateDes_11,project.Entrytypes.R3.class) || Utils.is_(stateDes_11,project.Entrytypes.X.class)) && inv_Entry_T3(stateDes_11));

      //@ assert stateDes_11 instanceof project.Entrytypes.X ==> ((project.Entrytypes.X) stateDes_11).valid();

      //@ assert stateDes_11 instanceof project.Entrytypes.R3 ==> ((project.Entrytypes.R3) stateDes_11).valid();

      //@ assert Utils.is_(stateDes_10,project.Entrytypes.R2.class);

      //@ assert stateDes_10.valid();

      //@ assert Utils.is_(r1,project.Entrytypes.R1.class);

      //@ assert r1.valid();

      //@ assert stateDes_15.valid();

      //@ assert ((Utils.is_(stateDes_14,project.Entrytypes.R3.class) || Utils.is_(stateDes_14,project.Entrytypes.X.class)) && inv_Entry_T3(stateDes_14));

      //@ assert stateDes_14 instanceof project.Entrytypes.X ==> ((project.Entrytypes.X) stateDes_14).valid();

      //@ assert stateDes_14 instanceof project.Entrytypes.R3 ==> ((project.Entrytypes.R3) stateDes_14).valid();

      //@ assert Utils.is_(stateDes_13,project.Entrytypes.R2.class);

      //@ assert stateDes_13.valid();

    } /* End of atomic statement */

    Number ret_2 = 0L;
    //@ assert Utils.is_nat(ret_2);

    return ret_2;
  }

  public String toString() {

    return "Entry{}";
  }

  /*@ pure @*/
  /*@ helper @*/

  public static Boolean inv_Entry_T3(final Object check_t3) {

    Object t3 = ((Object) check_t3);

    Boolean andResult_1 = false;

    Boolean orResult_1 = false;

    if (!(Utils.is_(t3, project.Entrytypes.R3.class))) {
      orResult_1 = true;
    } else {
      project.Entrytypes.R4 apply_10 = null;
      if (t3 instanceof project.Entrytypes.R3) {
        apply_10 = ((project.Entrytypes.R3) t3).get_r4();
      } else {
        throw new RuntimeException("Missing member: r4");
      }

      orResult_1 = !(Utils.equals(apply_10.get_x(), 10L));
    }

    if (orResult_1) {
      Boolean orResult_2 = false;

      if (!(Utils.is_(t3, project.Entrytypes.X.class))) {
        orResult_2 = true;
      } else {
        Boolean apply_11 = null;
        if (t3 instanceof project.Entrytypes.X) {
          apply_11 = ((project.Entrytypes.X) t3).get_b();
        } else {
          throw new RuntimeException("Missing member: b");
        }

        orResult_2 = apply_11;
      }

      if (orResult_2) {
        andResult_1 = true;
      }
    }

    return andResult_1;
  }
}
