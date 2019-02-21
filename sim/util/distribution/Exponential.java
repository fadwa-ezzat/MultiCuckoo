/*    */ package sim.util.distribution;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ 
/*    */ public class Exponential extends AbstractContinousDistribution
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected double lambda;
/*    */ 
/*    */   public Exponential(double lambda, MersenneTwisterFast randomGenerator)
/*    */   {
/* 35 */     setRandomGenerator(randomGenerator);
/* 36 */     setState(lambda);
/*    */   }
/*    */ 
/*    */   public double cdf(double x)
/*    */   {
/* 42 */     if (x <= 0.0D) return 0.0D;
/* 43 */     return 1.0D - Math.exp(-x * this.lambda);
/*    */   }
/*    */ 
/*    */   public double nextDouble()
/*    */   {
/* 49 */     return nextDouble(this.lambda);
/*    */   }
/*    */ 
/*    */   public double nextDouble(double lambda)
/*    */   {
/* 55 */     return -Math.log(this.randomGenerator.nextDouble()) / lambda;
/*    */   }
/*    */ 
/*    */   public double pdf(double x)
/*    */   {
/* 61 */     if (x < 0.0D) return 0.0D;
/* 62 */     return this.lambda * Math.exp(-x * this.lambda);
/*    */   }
/*    */ 
/*    */   public void setState(double lambda)
/*    */   {
/* 68 */     this.lambda = lambda;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 75 */     return getClass().getName() + "(" + this.lambda + ")";
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Exponential
 * JD-Core Version:    0.6.2
 */