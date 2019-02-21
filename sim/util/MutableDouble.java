/*    */ package sim.util;
/*    */ 
/*    */ public class MutableDouble extends Number
/*    */   implements Valuable, Cloneable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public double val;
/*    */ 
/*    */   public MutableDouble()
/*    */   {
/* 24 */     this.val = 0.0D; } 
/* 25 */   public MutableDouble(double val) { this.val = val; } 
/* 26 */   public MutableDouble(MutableDouble md) { this.val = md.val; } 
/*    */   public Double toDouble() {
/* 28 */     return new Double(this.val);
/*    */   }
/*    */   public double doubleValue() {
/* 31 */     return this.val; } 
/* 32 */   public float floatValue() { return (float)this.val; } 
/* 33 */   public int intValue() { return (int)this.val; } 
/* 34 */   public long longValue() { return ()this.val; }
/*    */ 
/*    */   public Object clone()
/*    */   {
/*    */     try
/*    */     {
/* 40 */       return super.clone();
/*    */     }
/*    */     catch (CloneNotSupportedException e) {
/*    */     }
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 50 */     return "" + this.val;
/*    */   }
/*    */ 
/*    */   public boolean isNaN()
/*    */   {
/* 55 */     return Double.isNaN(this.val);
/*    */   }
/*    */ 
/*    */   public boolean isInfinite()
/*    */   {
/* 60 */     return Double.isInfinite(this.val);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.MutableDouble
 * JD-Core Version:    0.6.2
 */