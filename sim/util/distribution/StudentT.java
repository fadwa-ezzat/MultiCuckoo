/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class StudentT extends AbstractContinousDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected double freedom;
/*     */   protected double TERM;
/*     */ 
/*     */   public StudentT(double freedom, MersenneTwisterFast randomGenerator)
/*     */   {
/*  49 */     setRandomGenerator(randomGenerator);
/*  50 */     setState(freedom);
/*     */   }
/*     */ 
/*     */   public double cdf(double x)
/*     */   {
/*  56 */     return Probability.studentT(this.freedom, x);
/*     */   }
/*     */ 
/*     */   public double nextDouble()
/*     */   {
/*  62 */     return nextDouble(this.freedom);
/*     */   }
/*     */ 
/*     */   public double nextDouble(double degreesOfFreedom)
/*     */   {
/*  80 */     if (degreesOfFreedom <= 0.0D) throw new IllegalArgumentException(); double u;
/*     */     double v;
/*     */     double w;
/*     */     do { u = 2.0D * this.randomGenerator.nextDouble() - 1.0D;
/*  85 */       v = 2.0D * this.randomGenerator.nextDouble() - 1.0D;
/*     */     }
/*  87 */     while ((w = u * u + v * v) > 1.0D);
/*     */ 
/*  89 */     return u * Math.sqrt(degreesOfFreedom * (Math.exp(-2.0D / degreesOfFreedom * Math.log(w)) - 1.0D) / w);
/*     */   }
/*     */ 
/*     */   public double pdf(double x)
/*     */   {
/*  95 */     return this.TERM * Math.pow(1.0D + x * x / this.freedom, -(this.freedom + 1.0D) * 0.5D);
/*     */   }
/*     */ 
/*     */   public void setState(double freedom)
/*     */   {
/* 103 */     if (freedom <= 0.0D) throw new IllegalArgumentException();
/* 104 */     this.freedom = freedom;
/*     */ 
/* 106 */     double val = Fun.logGamma((freedom + 1.0D) / 2.0D) - Fun.logGamma(freedom / 2.0D);
/* 107 */     this.TERM = (Math.exp(val) / Math.sqrt(3.141592653589793D * freedom));
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 114 */     return getClass().getName() + "(" + this.freedom + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.StudentT
 * JD-Core Version:    0.6.2
 */