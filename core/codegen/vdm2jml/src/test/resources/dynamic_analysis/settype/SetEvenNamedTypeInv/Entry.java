package project;

import org.overture.codegen.runtime.IO;
import org.overture.codegen.runtime.SetUtil;
import org.overture.codegen.runtime.Utils;
import org.overture.codegen.runtime.VDMSet;

@SuppressWarnings("all")
//@ nullable_by_default

final public class Entry {
  /*@ public ghost static boolean invChecksOn = true; @*/

  private Entry() {}

  public static Object Run() {

    IO.println("Before legal use");
    {
      final VDMSet ignorePattern_1 = SetUtil.set(2L, 4L, 6L);
      //@ assert ((V2J.isSet(ignorePattern_1) && (\forall int i; 0 <= i && i < V2J.size(ignorePattern_1); (Utils.is_nat(V2J.get(ignorePattern_1,i)) && inv_Entry_Even(V2J.get(ignorePattern_1,i))))) && inv_Entry_SetEven(ignorePattern_1));

      /* skip */
    }

    IO.println("After legal use");
    IO.println("Before illegal use");
    {
      final VDMSet xs = SetUtil.set(2L);
      //@ assert ((V2J.isSet(xs) && (\forall int i; 0 <= i && i < V2J.size(xs); (Utils.is_nat(V2J.get(xs,i)) && inv_Entry_Even(V2J.get(xs,i))))) && inv_Entry_SetEven(xs));

      final VDMSet ys = SetUtil.set(1L);
      //@ assert (V2J.isSet(ys) && (\forall int i; 0 <= i && i < V2J.size(ys); Utils.is_nat(V2J.get(ys,i))));

      final VDMSet ignorePattern_2 = SetUtil.union(Utils.copy(xs), Utils.copy(ys));
      //@ assert ((V2J.isSet(ignorePattern_2) && (\forall int i; 0 <= i && i < V2J.size(ignorePattern_2); (Utils.is_nat(V2J.get(ignorePattern_2,i)) && inv_Entry_Even(V2J.get(ignorePattern_2,i))))) && inv_Entry_SetEven(ignorePattern_2));

      /* skip */
    }

    IO.println("After illegal use");
    return 0L;
  }

  public String toString() {

    return "Entry{}";
  }

  /*@ pure @*/
  /*@ helper @*/

  public static Boolean inv_Entry_SetEven(final Object check_elem) {

    return true;
  }

  /*@ pure @*/
  /*@ helper @*/

  public static Boolean inv_Entry_Even(final Object check_e) {

    Number e = ((Number) check_e);

    return Utils.equals(Utils.mod(e.longValue(), 2L), 0L);
  }
}
