package project;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
//@ nullable_by_default

final public class Entry {
  /*@ public ghost static boolean invChecksOn = true; @*/

  private Entry() {}

  public static Object Run() {

    IO.println("Before legal use");
    {
      final VDMMap ignorePattern_1 = MapUtil.map(new Maplet(false, 0L), new Maplet(true, 1L));
      //@ assert (V2J.isMap(ignorePattern_1) && (\forall int i; 0 <= i && i < V2J.size(ignorePattern_1); Utils.is_bool(V2J.getDom(ignorePattern_1,i)) && Utils.is_nat(V2J.getRng(ignorePattern_1,i))));

      /* skip */
    }

    IO.println("After legal use");
    IO.println("Before illegal use");
    {
      final VDMMap ignorePattern_2 = mapBoolToInt();
      //@ assert (V2J.isMap(ignorePattern_2) && (\forall int i; 0 <= i && i < V2J.size(ignorePattern_2); Utils.is_bool(V2J.getDom(ignorePattern_2,i)) && Utils.is_nat(V2J.getRng(ignorePattern_2,i))));

      /* skip */
    }

    IO.println("After illegal use");
    return 0L;
  }
  /*@ pure @*/

  public static VDMMap mapBoolToInt() {

    VDMMap ret_1 = MapUtil.map(new Maplet(false, 0L), new Maplet(true, -1L));
    //@ assert (V2J.isMap(ret_1) && (\forall int i; 0 <= i && i < V2J.size(ret_1); Utils.is_bool(V2J.getDom(ret_1,i)) && Utils.is_int(V2J.getRng(ret_1,i))));

    return Utils.copy(ret_1);
  }

  public String toString() {

    return "Entry{}";
  }
}
