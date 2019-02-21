/*    */ package sim.app.balls3d;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import sim.util.Valuable;
/*    */ 
/*    */ public class Band
/*    */   implements Serializable, Valuable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public double laxDistance;
/*    */   public double strength;
/*    */ 
/*    */   public Band(double laxDistance, double strength)
/*    */   {
/* 19 */     this.laxDistance = laxDistance; this.strength = strength;
/*    */   }
/*    */   public void setStrength(double val) {
/* 22 */     if (val > 0.0D) this.strength = val;  } 
/* 23 */   public double getStrength() { return this.strength; } 
/* 24 */   public void setLaxDistance(double val) { if (val >= 0.0D) this.laxDistance = val;  } 
/* 25 */   public double getLaxDistance() { return this.laxDistance; } 
/*    */   public double doubleValue() {
/* 27 */     return this.strength;
/*    */   }
/* 29 */   public String toString() { return "" + this.strength + " (" + this.laxDistance + ")"; }
/*    */ 
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.balls3d.Band
 * JD-Core Version:    0.6.2
 */