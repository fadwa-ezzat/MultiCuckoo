/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class Empirical extends AbstractContinousDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected double[] cdf;
/*     */   protected int interpolationType;
/*     */   public static final int LINEAR_INTERPOLATION = 0;
/*     */   public static final int NO_INTERPOLATION = 1;
/*     */ 
/*     */   public Empirical(double[] pdf, int interpolationType, MersenneTwisterFast randomGenerator)
/*     */   {
/*  59 */     setRandomGenerator(randomGenerator);
/*  60 */     setState(pdf, interpolationType);
/*     */   }
/*     */ 
/*     */   public double cdf(int k)
/*     */   {
/*  66 */     if (k < 0) return 0.0D;
/*  67 */     if (k >= this.cdf.length - 1) return 1.0D;
/*  68 */     return this.cdf[k];
/*     */   }
/*     */ 
/*     */   public double nextDouble()
/*     */   {
/*  87 */     double rand = this.randomGenerator.nextDouble();
/*  88 */     if (this.cdf == null) return rand;
/*     */ 
/*  91 */     int nBins = this.cdf.length - 1;
/*  92 */     int nbelow = 0;
/*  93 */     int nabove = nBins;
/*     */ 
/*  95 */     while (nabove > nbelow + 1) {
/*  96 */       int middle = nabove + nbelow + 1 >> 1;
/*  97 */       if (rand >= this.cdf[middle]) nbelow = middle; else {
/*  98 */         nabove = middle;
/*     */       }
/*     */     }
/*     */ 
/* 102 */     if (this.interpolationType == 1) {
/* 103 */       return nbelow / nBins;
/*     */     }
/* 105 */     if (this.interpolationType == 0) {
/* 106 */       double binMeasure = this.cdf[nabove] - this.cdf[nbelow];
/*     */ 
/* 110 */       if (binMeasure == 0.0D)
/*     */       {
/* 114 */         return (nbelow + 0.5D) / nBins;
/*     */       }
/*     */ 
/* 117 */       double binFraction = (rand - this.cdf[nbelow]) / binMeasure;
/* 118 */       return (nbelow + binFraction) / nBins;
/*     */     }
/* 120 */     throw new InternalError();
/*     */   }
/*     */ 
/*     */   public double pdf(double x)
/*     */   {
/* 126 */     throw new RuntimeException("not implemented");
/*     */   }
/*     */ 
/*     */   public double pdf(int k)
/*     */   {
/* 135 */     if ((k < 0) || (k >= this.cdf.length - 1)) return 0.0D;
/* 136 */     return this.cdf[(k - 1)] - this.cdf[k];
/*     */   }
/*     */ 
/*     */   public void setState(double[] pdf, int interpolationType)
/*     */   {
/* 150 */     if ((interpolationType != 0) && (interpolationType != 1))
/*     */     {
/* 152 */       throw new IllegalArgumentException("Illegal Interpolation Type");
/*     */     }
/* 154 */     this.interpolationType = interpolationType;
/*     */ 
/* 156 */     if ((pdf == null) || (pdf.length == 0)) {
/* 157 */       this.cdf = null;
/*     */ 
/* 159 */       return;
/*     */     }
/*     */ 
/* 163 */     int nBins = pdf.length;
/* 164 */     this.cdf = new double[nBins + 1];
/*     */ 
/* 166 */     this.cdf[0] = 0.0D;
/* 167 */     for (int ptn = 0; ptn < nBins; ptn++) {
/* 168 */       double prob = pdf[ptn];
/* 169 */       if (prob < 0.0D) throw new IllegalArgumentException("Negative probability");
/* 170 */       this.cdf[(ptn + 1)] = (this.cdf[ptn] + prob);
/*     */     }
/* 172 */     if (this.cdf[nBins] <= 0.0D) throw new IllegalArgumentException("At leat one probability must be > 0.0");
/* 173 */     for (int ptn = 0; ptn < nBins + 1; ptn++)
/* 174 */       this.cdf[ptn] /= this.cdf[nBins];
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 182 */     String interpolation = null;
/* 183 */     if (this.interpolationType == 1) interpolation = "NO_INTERPOLATION";
/* 184 */     if (this.interpolationType == 0) interpolation = "LINEAR_INTERPOLATION";
/* 185 */     return getClass().getName() + "(" + (this.cdf != null ? this.cdf.length : 0) + "," + interpolation + ")";
/*     */   }
/*     */ 
/*     */   private int xnBins()
/*     */   {
/* 192 */     return this.cdf.length - 1;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Empirical
 * JD-Core Version:    0.6.2
 */