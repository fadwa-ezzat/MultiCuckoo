/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class PoissonSlow extends AbstractDiscreteDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected double mean;
/*     */   protected double cached_sq;
/*     */   protected double cached_alxm;
/*     */   protected double cached_g;
/*     */   protected static final double MEAN_MAX = 2147483647.0D;
/*     */   protected static final double SWITCH_MEAN = 12.0D;
/*  45 */   protected static final double[] cof = { 76.180091729471457D, -86.505320329416776D, 24.014098240830911D, -1.231739572450155D, 0.00120865097386618D, -5.395239384953E-006D };
/*     */ 
/*     */   public PoissonSlow(double mean, MersenneTwisterFast randomGenerator)
/*     */   {
/*  55 */     setRandomGenerator(randomGenerator);
/*  56 */     setMean(mean);
/*     */   }
/*     */ 
/*     */   public static double logGamma(double xx)
/*     */   {
/*  64 */     double x = xx - 1.0D;
/*  65 */     double tmp = x + 5.5D;
/*  66 */     tmp -= (x + 0.5D) * Math.log(tmp);
/*  67 */     double ser = 1.000000000190015D;
/*     */ 
/*  69 */     double[] coeff = cof;
/*  70 */     for (int j = 0; j <= 5; j++) {
/*  71 */       x += 1.0D;
/*  72 */       ser += coeff[j] / x;
/*     */     }
/*  74 */     return -tmp + Math.log(2.506628274631001D * ser);
/*     */   }
/*     */ 
/*     */   public int nextInt()
/*     */   {
/*  80 */     return nextInt(this.mean);
/*     */   }
/*     */ 
/*     */   private int nextInt(double theMean)
/*     */   {
/*  89 */     double xm = theMean;
/*  90 */     double g = this.cached_g;
/*     */ 
/*  92 */     if (xm == -1.0D) return 0;
/*  93 */     if (xm < 12.0D) {
/*  94 */       int poisson = -1;
/*  95 */       double product = 1.0D;
/*     */       do {
/*  97 */         poisson++;
/*  98 */         product *= this.randomGenerator.nextDouble();
/*  99 */       }while (product >= g);
/*     */ 
/* 101 */       return poisson;
/*     */     }
/* 103 */     if (xm < 2147483647.0D) { double sq = this.cached_sq;
/* 107 */       double alxm = this.cached_alxm;
/*     */ 
/* 109 */       MersenneTwisterFast rand = this.randomGenerator;
/*     */       double em;
/*     */       double t;
/*     */       do { double y;
/*     */         do { y = Math.tan(3.141592653589793D * rand.nextDouble());
/* 114 */           em = sq * y + xm; }
/* 115 */         while (em < 0.0D);
/* 116 */         em = (int)em;
/* 117 */         t = 0.9D * (1.0D + y * y) * Math.exp(em * alxm - logGamma(em + 1.0D) - g); }
/* 118 */       while (rand.nextDouble() > t);
/* 119 */       return (int)em;
/*     */     }
/*     */ 
/* 122 */     return (int)xm;
/*     */   }
/*     */ 
/*     */   protected int nextIntSlow()
/*     */   {
/* 129 */     double bound = Math.exp(-this.mean);
/* 130 */     int count = 0;
/*     */ 
/* 132 */     for (double product = 1.0D; (product >= bound) && (product > 0.0D); count++) {
/* 133 */       product *= this.randomGenerator.nextDouble();
/*     */     }
/* 135 */     if ((product <= 0.0D) && (bound > 0.0D)) return (int)Math.round(this.mean);
/* 136 */     return count - 1;
/*     */   }
/*     */ 
/*     */   public void setMean(double mean)
/*     */   {
/* 142 */     if (mean != this.mean) {
/* 143 */       this.mean = mean;
/* 144 */       if (mean == -1.0D) return;
/* 145 */       if (mean < 12.0D) {
/* 146 */         this.cached_g = Math.exp(-mean);
/*     */       }
/*     */       else {
/* 149 */         this.cached_sq = Math.sqrt(2.0D * mean);
/* 150 */         this.cached_alxm = Math.log(mean);
/* 151 */         this.cached_g = (mean * this.cached_alxm - logGamma(mean + 1.0D));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 160 */     return getClass().getName() + "(" + this.mean + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.PoissonSlow
 * JD-Core Version:    0.6.2
 */