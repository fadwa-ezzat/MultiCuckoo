/*    */ package sim.util;
/*    */ 
/*    */ public class Interval
/*    */ {
/*    */   Number min;
/*    */   Number max;
/*    */   boolean isDouble;
/*    */ 
/*    */   public Interval(long min, long max)
/*    */   {
/* 17 */     this.min = Long.valueOf(min);
/* 18 */     this.max = Long.valueOf(max);
/* 19 */     this.isDouble = false;
/*    */   }
/*    */ 
/*    */   public Interval(double min, double max)
/*    */   {
/* 24 */     this.min = new Double(min);
/* 25 */     this.max = new Double(max);
/* 26 */     this.isDouble = true;
/*    */   }
/*    */ 
/*    */   public Number getMin()
/*    */   {
/* 33 */     return this.min; } 
/* 34 */   public Number getMax() { return this.max; } 
/* 35 */   public boolean isDouble() { return this.isDouble; } 
/*    */   public boolean contains(Number val) {
/* 37 */     return contains(val.doubleValue()); } 
/* 38 */   public boolean contains(double val) { return (val >= this.min.doubleValue()) && (val <= this.max.doubleValue()); }
/*    */ 
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.Interval
 * JD-Core Version:    0.6.2
 */