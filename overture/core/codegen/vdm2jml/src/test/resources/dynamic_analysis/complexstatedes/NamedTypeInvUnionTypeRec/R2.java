package project.Entrytypes;

import java.util.*;
import org.overture.codegen.runtime.*;
import org.overture.codegen.vdm2jml.runtime.*;

@SuppressWarnings("all")
//@ nullable_by_default

final public class R2 implements Record {
  public Object t3;
  //@ public instance invariant project.Entry.invChecksOn ==> inv_R2(t3);

  public R2(final Object _t3) {

    //@ assert ((Utils.is_(_t3,project.Entrytypes.R3.class) || Utils.is_(_t3,project.Entrytypes.X.class)) && inv_Entry_T3(_t3));

    t3 = _t3 != null ? Utils.copy(_t3) : null;
    //@ assert ((Utils.is_(t3,project.Entrytypes.R3.class) || Utils.is_(t3,project.Entrytypes.X.class)) && inv_Entry_T3(t3));

  }
  /*@ pure @*/

  public boolean equals(final Object obj) {

    if (!(obj instanceof project.Entrytypes.R2)) {
      return false;
    }

    project.Entrytypes.R2 other = ((project.Entrytypes.R2) obj);

    return Utils.equals(t3, other.t3);
  }
  /*@ pure @*/

  public int hashCode() {

    return Utils.hashCode(t3);
  }
  /*@ pure @*/

  public project.Entrytypes.R2 copy() {

    return new project.Entrytypes.R2(t3);
  }
  /*@ pure @*/

  public String toString() {

    return "mk_Entry`R2" + Utils.formatFields(t3);
  }
  /*@ pure @*/

  public Object get_t3() {

    Object ret_4 = t3;
    //@ assert project.Entry.invChecksOn ==> (((Utils.is_(ret_4,project.Entrytypes.R3.class) || Utils.is_(ret_4,project.Entrytypes.X.class)) && inv_Entry_T3(ret_4)));

    return ret_4;
  }

  public void set_t3(final Object _t3) {

    //@ assert project.Entry.invChecksOn ==> (((Utils.is_(_t3,project.Entrytypes.R3.class) || Utils.is_(_t3,project.Entrytypes.X.class)) && inv_Entry_T3(_t3)));

    t3 = _t3;
    //@ assert project.Entry.invChecksOn ==> (((Utils.is_(t3,project.Entrytypes.R3.class) || Utils.is_(t3,project.Entrytypes.X.class)) && inv_Entry_T3(t3)));

  }
  /*@ pure @*/

  public Boolean valid() {

    return true;
  }
  /*@ pure @*/
  /*@ helper @*/

  public static Boolean inv_R2(final Object _t3) {

    Object obj_4 = _t3;
    project.Entrytypes.R4 apply_9 = null;
    if (obj_4 instanceof project.Entrytypes.R3) {
      apply_9 = Utils.copy(((project.Entrytypes.R3) obj_4).r4);
    } else {
      throw new RuntimeException("Missing member: r4");
    }

    return !(Utils.equals(apply_9.x, 2L));
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
