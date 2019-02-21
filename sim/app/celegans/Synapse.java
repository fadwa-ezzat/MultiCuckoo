/*    */ package sim.app.celegans;
/*    */ 
/*    */ public class Synapse
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 22 */   static int type_chemical = 0;
/* 23 */   static int type_gap = 1;
/*    */   public Cell from;
/*    */   public Cell to;
/*    */   public int type;
/* 27 */   public int number = 1;
/*    */ 
/*    */   public String toString() {
/* 30 */     String s = this.type == type_chemical ? "chemical" : "gap";
/* 31 */     if (this.number > 1) s = s + " (" + this.number + ")";
/* 32 */     return s;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.celegans.Synapse
 * JD-Core Version:    0.6.2
 */