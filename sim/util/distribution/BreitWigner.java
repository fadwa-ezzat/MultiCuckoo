/*    */ package sim.util.distribution;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ 
/*    */ public class BreitWigner extends AbstractContinousDistribution
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected double mean;
/*    */   protected double gamma;
/*    */   protected double cut;
/*    */ 
/*    */   public BreitWigner(double mean, double gamma, double cut, MersenneTwisterFast randomGenerator)
/*    */   {
/* 37 */     setRandomGenerator(randomGenerator);
/* 38 */     setState(mean, gamma, cut);
/*    */   }
/*    */ 
/*    */   public double nextDouble()
/*    */   {
/* 44 */     return nextDouble(this.mean, this.gamma, this.cut);
/*    */   }
/*    */ 
/*    */   public double nextDouble(double mean, double gamma, double cut)
/*    */   {
/* 53 */     if (gamma == 0.0D) return mean;
/* 54 */     if (cut == (-1.0D / 0.0D)) {
/* 55 */       double rval = 2.0D * this.randomGenerator.nextDouble() - 1.0D;
/* 56 */       double displ = 0.5D * gamma * Math.tan(rval * 1.570796326794897D);
/* 57 */       return mean + displ;
/*    */     }
/*    */ 
/* 60 */     double val = Math.atan(2.0D * cut / gamma);
/* 61 */     double rval = 2.0D * this.randomGenerator.nextDouble() - 1.0D;
/* 62 */     double displ = 0.5D * gamma * Math.tan(rval * val);
/*    */ 
/* 64 */     return mean + displ;
/*    */   }
/*    */ 
/*    */   public void setState(double mean, double gamma, double cut)
/*    */   {
/* 72 */     this.mean = mean;
/* 73 */     this.gamma = gamma;
/* 74 */     this.cut = cut;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 80 */     return getClass().getName() + "(" + this.mean + "," + this.gamma + "," + this.cut + ")";
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.BreitWigner
 * JD-Core Version:    0.6.2
 */