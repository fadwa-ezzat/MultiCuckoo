/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class Normal extends AbstractContinousDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected double mean;
/*     */   protected double variance;
/*     */   protected double standardDeviation;
/*     */   protected double cache;
/*     */   protected boolean cacheFilled;
/*     */   protected double SQRT_INV;
/*     */ 
/*     */   public Normal(double mean, double standardDeviation, MersenneTwisterFast randomGenerator)
/*     */   {
/*  57 */     setRandomGenerator(randomGenerator);
/*  58 */     setState(mean, standardDeviation);
/*     */   }
/*     */ 
/*     */   public double cdf(double x)
/*     */   {
/*  64 */     return Probability.normal(this.mean, this.variance, x);
/*     */   }
/*     */ 
/*     */   public double nextDouble()
/*     */   {
/*  70 */     return nextDouble(this.mean, this.standardDeviation);
/*     */   }
/*     */ 
/*     */   public double nextDouble(double mean, double standardDeviation)
/*     */   {
/*  77 */     if ((this.cacheFilled) && (this.mean == mean) && (this.standardDeviation == standardDeviation)) {
/*  78 */       this.cacheFilled = false;
/*  79 */       return this.cache; } double x;
/*     */     double y;
/*     */     double r;
/*     */     do { x = 2.0D * this.randomGenerator.nextDouble() - 1.0D;
/*  85 */       y = 2.0D * this.randomGenerator.nextDouble() - 1.0D;
/*  86 */       r = x * x + y * y; }
/*  87 */     while (r >= 1.0D);
/*     */ 
/*  89 */     double z = Math.sqrt(-2.0D * Math.log(r) / r);
/*  90 */     this.cache = (mean + standardDeviation * x * z);
/*  91 */     this.cacheFilled = true;
/*  92 */     return mean + standardDeviation * y * z;
/*     */   }
/*     */ 
/*     */   public double pdf(double x)
/*     */   {
/*  98 */     double diff = x - this.mean;
/*  99 */     return this.SQRT_INV * Math.exp(-(diff * diff) / (2.0D * this.variance));
/*     */   }
/*     */ 
/*     */   protected void setRandomGenerator(MersenneTwisterFast randomGenerator)
/*     */   {
/* 105 */     super.setRandomGenerator(randomGenerator);
/* 106 */     this.cacheFilled = false;
/*     */   }
/*     */ 
/*     */   public void setState(double mean, double standardDeviation)
/*     */   {
/* 112 */     if ((mean != this.mean) || (standardDeviation != this.standardDeviation)) {
/* 113 */       this.mean = mean;
/* 114 */       this.standardDeviation = standardDeviation;
/* 115 */       this.variance = (standardDeviation * standardDeviation);
/* 116 */       this.cacheFilled = false;
/*     */ 
/* 118 */       this.SQRT_INV = (1.0D / Math.sqrt(6.283185307179586D * this.variance));
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 125 */     return getClass().getName() + "(" + this.mean + "," + this.standardDeviation + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Normal
 * JD-Core Version:    0.6.2
 */