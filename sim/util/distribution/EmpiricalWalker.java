/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class EmpiricalWalker extends AbstractDiscreteDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected int K;
/*     */   protected int[] A;
/*     */   protected double[] F;
/*     */   protected double[] cdf;
/*     */ 
/*     */   public EmpiricalWalker(double[] pdf, int interpolationType, MersenneTwisterFast randomGenerator)
/*     */   {
/* 170 */     setRandomGenerator(randomGenerator);
/* 171 */     setState(pdf, interpolationType);
/* 172 */     setState2(pdf);
/*     */   }
/*     */ 
/*     */   public double cdf(int k)
/*     */   {
/* 178 */     if (k < 0) return 0.0D;
/* 179 */     if (k >= this.cdf.length - 1) return 1.0D;
/* 180 */     return this.cdf[k];
/*     */   }
/*     */ 
/*     */   public int nextInt()
/*     */   {
/* 201 */     int c = 0;
/*     */ 
/* 203 */     double u = this.randomGenerator.nextDouble();
/*     */ 
/* 207 */     u *= this.K;
/* 208 */     c = (int)u;
/* 209 */     u -= c;
/*     */ 
/* 211 */     double f = this.F[c];
/*     */ 
/* 213 */     if (f == 1.0D) return c;
/* 214 */     if (u < f) {
/* 215 */       return c;
/*     */     }
/*     */ 
/* 218 */     return this.A[c];
/*     */   }
/*     */ 
/*     */   public double pdf(int k)
/*     */   {
/* 225 */     if ((k < 0) || (k >= this.cdf.length - 1)) return 0.0D;
/* 226 */     return this.cdf[(k - 1)] - this.cdf[k];
/*     */   }
/*     */ 
/*     */   public void setState(double[] pdf, int interpolationType)
/*     */   {
/* 240 */     if ((pdf == null) || (pdf.length == 0)) {
/* 241 */       throw new IllegalArgumentException("Non-existing pdf");
/*     */     }
/*     */ 
/* 245 */     int nBins = pdf.length;
/* 246 */     this.cdf = new double[nBins + 1];
/*     */ 
/* 248 */     this.cdf[0] = 0.0D;
/* 249 */     for (int i = 0; i < nBins; i++) {
/* 250 */       if (pdf[i] < 0.0D) throw new IllegalArgumentException("Negative probability");
/* 251 */       this.cdf[(i + 1)] = (this.cdf[i] + pdf[i]);
/*     */     }
/* 253 */     if (this.cdf[nBins] <= 0.0D) throw new IllegalArgumentException("At leat one probability must be > 0.0");
/*     */ 
/* 255 */     for (int i = 0; i < nBins + 1; i++)
/* 256 */       this.cdf[i] /= this.cdf[nBins];
/*     */   }
/*     */ 
/*     */   public void setState2(double[] pdf)
/*     */   {
/* 271 */     int size = pdf.length;
/*     */ 
/* 277 */     double pTotal = 0.0D;
/*     */ 
/* 286 */     for (int k = 0; k < size; k++)
/*     */     {
/* 290 */       pTotal += pdf[k];
/*     */     }
/*     */ 
/* 294 */     this.K = size;
/* 295 */     this.F = new double[size];
/* 296 */     this.A = new int[size];
/*     */ 
/* 299 */     double[] E = new double[size];
/* 300 */     for (k = 0; k < size; k++) {
/* 301 */       pdf[k] /= pTotal;
/*     */     }
/*     */ 
/* 305 */     double mean = 1.0D / size;
/* 306 */     int nSmalls = 0;
/* 307 */     int nBigs = 0;
/* 308 */     for (k = 0; k < size; k++) {
/* 309 */       if (E[k] < mean) nSmalls++; else
/* 310 */         nBigs++;
/*     */     }
/* 312 */     Stack Bigs = new Stack(nBigs);
/* 313 */     Stack Smalls = new Stack(nSmalls);
/* 314 */     for (k = 0; k < size; k++) {
/* 315 */       if (E[k] < mean) {
/* 316 */         Smalls.push(k);
/*     */       }
/*     */       else {
/* 319 */         Bigs.push(k);
/*     */       }
/*     */     }
/*     */ 
/* 323 */     while (Smalls.size() > 0) {
/* 324 */       int s = Smalls.pop();
/* 325 */       if (Bigs.size() == 0)
/*     */       {
/* 327 */         this.A[s] = s;
/* 328 */         this.F[s] = 1.0D;
/* 329 */         break;
/*     */       }
/* 331 */       int b = Bigs.pop();
/* 332 */       this.A[s] = b;
/* 333 */       this.F[s] = (size * E[s]);
/*     */ 
/* 339 */       double d = mean - E[s];
/* 340 */       E[s] += d;
/* 341 */       E[b] -= d;
/* 342 */       if (E[b] < mean) {
/* 343 */         Smalls.push(b);
/*     */       }
/* 345 */       else if (E[b] > mean) {
/* 346 */         Bigs.push(b);
/*     */       }
/*     */       else
/*     */       {
/* 350 */         this.A[b] = b;
/* 351 */         this.F[b] = 1.0D;
/*     */       }
/*     */     }
/* 354 */     while (Bigs.size() > 0) {
/* 355 */       int b = Bigs.pop();
/* 356 */       this.A[b] = b;
/* 357 */       this.F[b] = 1.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 400 */     String interpolation = null;
/* 401 */     return getClass().getName() + "(" + (this.cdf != null ? this.cdf.length : 0) + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.EmpiricalWalker
 * JD-Core Version:    0.6.2
 */