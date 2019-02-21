/*    */ package sim.util.distribution;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ 
/*    */ public class BreitWignerMeanSquare extends BreitWigner
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected Uniform uniform;
/*    */ 
/*    */   public BreitWignerMeanSquare(double mean, double gamma, double cut, MersenneTwisterFast randomGenerator)
/*    */   {
/* 34 */     super(mean, gamma, cut, randomGenerator);
/* 35 */     this.uniform = new Uniform(randomGenerator);
/*    */   }
/*    */ 
/*    */   public double nextDouble(double mean, double gamma, double cut)
/*    */   {
/* 55 */     if (gamma == 0.0D) return mean;
/* 56 */     if (cut == (-1.0D / 0.0D)) {
/* 57 */       double val = Math.atan(-mean / gamma);
/* 58 */       double rval = this.uniform.nextDoubleFromTo(val, 1.570796326794897D);
/* 59 */       double displ = gamma * Math.tan(rval);
/* 60 */       return Math.sqrt(mean * mean + mean * displ);
/*    */     }
/*    */ 
/* 63 */     double tmp = Math.max(0.0D, mean - cut);
/* 64 */     double lower = Math.atan((tmp * tmp - mean * mean) / (mean * gamma));
/* 65 */     double upper = Math.atan(((mean + cut) * (mean + cut) - mean * mean) / (mean * gamma));
/* 66 */     double rval = this.uniform.nextDoubleFromTo(lower, upper);
/*    */ 
/* 68 */     double displ = gamma * Math.tan(rval);
/* 69 */     return Math.sqrt(Math.max(0.0D, mean * mean + mean * displ));
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.BreitWignerMeanSquare
 * JD-Core Version:    0.6.2
 */