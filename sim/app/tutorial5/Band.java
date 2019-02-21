/*    */ package sim.app.tutorial5;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Band
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public double laxDistance;
/*    */   public double strength;
/*    */ 
/*    */   public Band(double laxDistance, double strength)
/*    */   {
/* 17 */     this.laxDistance = laxDistance; this.strength = strength;
/*    */   }
/*    */   public void setStrength(double val) {
/* 20 */     if (val > 0.0D) this.strength = val;  } 
/* 21 */   public double getStrength() { return this.strength; } 
/* 22 */   public void setLaxDistance(double val) { if (val >= 0.0D) this.laxDistance = val;  } 
/* 23 */   public double getLaxDistance() { return this.laxDistance; } 
/*    */   public String toString() {
/* 25 */     return "" + this.strength + " (" + this.laxDistance + ")";
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial5.Band
 * JD-Core Version:    0.6.2
 */