package project;

import org.overture.codegen.runtime.IO;
import org.overture.codegen.runtime.SeqUtil;
import org.overture.codegen.runtime.Utils;
import org.overture.codegen.runtime.VDMSeq;

@SuppressWarnings("all")
//@ nullable_by_default

final public class Entry {
  /*@ public ghost static boolean invChecksOn = true; @*/

  private Entry() {}

  public static Object Run() {

    IO.println("Before legal use");
    {
      final VDMSeq ignorePattern_1 = SeqUtil.seq();
      //@ assert ((V2J.isSeq(ignorePattern_1) && (\forall int i; 0 <= i && i < V2J.size(ignorePattern_1); (Utils.is_nat(V2J.get(ignorePattern_1,i)) && inv_Entry_Even(V2J.get(ignorePattern_1,i))))) && inv_Entry_SeqEven(ignorePattern_1));

      /* skip */
    }

    {
      final VDMSeq ignorePattern_2 = SeqUtil.seq(2L, 4L, 6L, 8L);
      //@ assert ((V2J.isSeq(ignorePattern_2) && (\forall int i; 0 <= i && i < V2J.size(ignorePattern_2); (Utils.is_nat(V2J.get(ignorePattern_2,i)) && inv_Entry_Even(V2J.get(ignorePattern_2,i))))) && inv_Entry_SeqEven(ignorePattern_2));

      /* skip */
    }

    IO.println("After legal use");
    IO.println("Before illegal use");
    {
      final VDMSeq ignorePattern_3 = SeqUtil.seq(2L, 4L, 6L, 8L, 9L);
      //@ assert ((V2J.isSeq(ignorePattern_3) && (\forall int i; 0 <= i && i < V2J.size(ignorePattern_3); (Utils.is_nat(V2J.get(ignorePattern_3,i)) && inv_Entry_Even(V2J.get(ignorePattern_3,i))))) && inv_Entry_SeqEven(ignorePattern_3));

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

  public static Boolean inv_Entry_SeqEven(final Object check_elem) {

    return true;
  }

  /*@ pure @*/
  /*@ helper @*/

  public static Boolean inv_Entry_Even(final Object check_e) {

    Number e = ((Number) check_e);

    return Utils.equals(Utils.mod(e.longValue(), 2L), 0L);
  }
}
