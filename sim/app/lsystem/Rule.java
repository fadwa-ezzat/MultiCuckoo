/*    */ package sim.app.lsystem;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Rule
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 6750008059574576396L;
/*    */   public byte pattern;
/*    */   public ByteList replace;
/*    */ 
/*    */   public Rule()
/*    */   {
/* 25 */     this.replace = new ByteList();
/*    */   }
/*    */ 
/*    */   public Rule(byte pattern, String replace)
/*    */   {
/* 30 */     this.pattern = pattern;
/* 31 */     this.replace = new ByteList();
/* 32 */     LSystemData.setVector(this.replace, replace);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.lsystem.Rule
 * JD-Core Version:    0.6.2
 */