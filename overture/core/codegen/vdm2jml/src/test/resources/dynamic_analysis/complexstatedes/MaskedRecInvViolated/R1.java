package project.Entrytypes;

import java.util.*;
import org.overture.codegen.runtime.*;
import org.overture.codegen.vdm2jml.runtime.*;

@SuppressWarnings("all")
//@ nullable_by_default

final public class R1 implements Record {
  public project.Entrytypes.R2 r2;
  //@ public instance invariant project.Entry.invChecksOn ==> inv_R1(r2);

  public R1(final project.Entrytypes.R2 _r2) {

    //@ assert Utils.is_(_r2,project.Entrytypes.R2.class);

    r2 = _r2 != null ? Utils.copy(_r2) : null;
    //@ assert Utils.is_(r2,project.Entrytypes.R2.class);

  }
  /*@ pure @*/

  public boolean equals(final Object obj) {

    if (!(obj instanceof project.Entrytypes.R1)) {
      return false;
    }

    project.Entrytypes.R1 other = ((project.Entrytypes.R1) obj);

    return Utils.equals(r2, other.r2);
  }
  /*@ pure @*/

  public int hashCode() {

    return Utils.hashCode(r2);
  }
  /*@ pure @*/

  public project.Entrytypes.R1 copy() {

    return new project.Entrytypes.R1(r2);
  }
  /*@ pure @*/

  public String toString() {

    return "mk_Entry`R1" + Utils.formatFields(r2);
  }
  /*@ pure @*/

  public project.Entrytypes.R2 get_r2() {

    project.Entrytypes.R2 ret_3 = r2;
    //@ assert project.Entry.invChecksOn ==> (Utils.is_(ret_3,project.Entrytypes.R2.class));

    return ret_3;
  }

  public void set_r2(final project.Entrytypes.R2 _r2) {

    //@ assert project.Entry.invChecksOn ==> (Utils.is_(_r2,project.Entrytypes.R2.class));

    r2 = _r2;
    //@ assert project.Entry.invChecksOn ==> (Utils.is_(r2,project.Entrytypes.R2.class));

  }
  /*@ pure @*/

  public Boolean valid() {

    return true;
  }
  /*@ pure @*/
  /*@ helper @*/

  public static Boolean inv_R1(final project.Entrytypes.R2 _r2) {

    return !(Utils.equals(_r2.t3.r4.x, 1L));
  }

  /*@ pure @*/
  /*@ helper @*/

  public static Boolean inv_Entry_T3(final Object check_t3) {

    project.Entrytypes.R3 t3 = ((project.Entrytypes.R3) check_t3);

    return !(Utils.equals(t3.get_r4().get_x(), 10L));
  }
}
