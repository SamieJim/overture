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
      final VDMSeq ignorePattern_1 = SeqUtil.seq(1L, 2L, 3L);
      //@ assert (V2J.isSeq(ignorePattern_1) && (\forall int i; 0 <= i && i < V2J.size(ignorePattern_1); Utils.is_nat(V2J.get(ignorePattern_1,i))));

      /* skip */
    }

    IO.println("After legal use");
    IO.println("Before illegal uses");
    {
      final VDMSeq ignorePattern_2 = seqOfNatsAndNil();
      //@ assert (V2J.isSeq(ignorePattern_2) && (\forall int i; 0 <= i && i < V2J.size(ignorePattern_2); Utils.is_nat(V2J.get(ignorePattern_2,i))));

      /* skip */
    }

    IO.println("After illegal uses");
    return 0L;
  }
  /*@ pure @*/

  public static VDMSeq seqOfNatsAndNil() {

    VDMSeq ret_1 = SeqUtil.seq(1L, null, 3L);
    //@ assert (V2J.isSeq(ret_1) && (\forall int i; 0 <= i && i < V2J.size(ret_1); ((V2J.get(ret_1,i) == null) || Utils.is_nat(V2J.get(ret_1,i)))));

    return Utils.copy(ret_1);
  }

  public String toString() {

    return "Entry{}";
  }
}
