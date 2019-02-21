/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Distributions
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   protected Distributions()
/*     */   {
/*  45 */     throw new RuntimeException("Non instantiable");
/*     */   }
/*     */ 
/*     */   public static double geometricPdf(int k, double p)
/*     */   {
/*  56 */     if (k < 0) throw new IllegalArgumentException();
/*  57 */     return p * Math.pow(1.0D - p, k);
/*     */   }
/*     */ 
/*     */   public static double nextBurr1(double r, int nr, MersenneTwisterFast randomGenerator)
/*     */   {
/*  91 */     double y = Math.exp(Math.log(randomGenerator.nextDouble()) / r);
/*  92 */     switch (nr) {
/*     */     case 2:
/*  94 */       return -Math.log(1.0D / y - 1.0D);
/*     */     case 7:
/*  97 */       return Math.log(2.0D * y / (2.0D - 2.0D * y)) / 2.0D;
/*     */     case 8:
/* 100 */       return Math.log(Math.tan(y * 3.141592653589793D / 2.0D));
/*     */     case 10:
/* 103 */       return Math.sqrt(-Math.log(1.0D - y));
/*     */     case 3:
/*     */     case 4:
/*     */     case 5:
/*     */     case 6:
/* 105 */     case 9: } return 0.0D;
/*     */   }
/*     */ 
/*     */   public static double nextBurr2(double r, double k, int nr, MersenneTwisterFast randomGenerator)
/*     */   {
/* 139 */     double u = randomGenerator.nextDouble();
/* 140 */     double y = Math.exp(-Math.log(u) / r) - 1.0D;
/* 141 */     switch (nr) {
/*     */     case 3:
/* 143 */       return Math.exp(-Math.log(y) / k);
/*     */     case 4:
/* 146 */       y = Math.exp(k * Math.log(y)) + 1.0D;
/* 147 */       y = k / y;
/* 148 */       return y;
/*     */     case 5:
/* 151 */       y = Math.atan(-Math.log(y / k));
/* 152 */       return y;
/*     */     case 6:
/* 155 */       y = -Math.log(y / k) / r;
/* 156 */       y = Math.log(y + Math.sqrt(y * y + 1.0D));
/* 157 */       return y;
/*     */     case 9:
/* 160 */       y = 1.0D + 2.0D * u / (k * (1.0D - u));
/* 161 */       y = Math.exp(Math.log(y) / r) - 1.0D;
/* 162 */       return Math.log(y);
/*     */     case 12:
/* 165 */       return Math.exp(Math.log(y) / k);
/*     */     case 7:
/*     */     case 8:
/*     */     case 10:
/* 167 */     case 11: } return 0.0D;
/*     */   }
/*     */ 
/*     */   public static double nextCauchy(MersenneTwisterFast randomGenerator)
/*     */   {
/* 182 */     return Math.tan(3.141592653589793D * randomGenerator.nextDouble());
/*     */   }
/*     */ 
/*     */   public static double nextErlang(double variance, double mean, MersenneTwisterFast randomGenerator)
/*     */   {
/* 188 */     int k = (int)(mean * mean / variance + 0.5D);
/* 189 */     k = k > 0 ? k : 1;
/* 190 */     double a = k / mean;
/*     */ 
/* 192 */     double prod = 1.0D;
/* 193 */     for (int i = 0; i < k; i++) prod *= randomGenerator.nextDouble();
/* 194 */     return -Math.log(prod) / a;
/*     */   }
/*     */ 
/*     */   public static int nextGeometric(double p, MersenneTwisterFast randomGenerator)
/*     */   {
/* 233 */     double u = randomGenerator.nextDouble();
/* 234 */     return (int)(Math.log(u) / Math.log(1.0D - p));
/*     */   }
/*     */ 
/*     */   public static double nextLambda(double l3, double l4, MersenneTwisterFast randomGenerator)
/*     */   {
/* 248 */     double l_sign;
/*     */     double l_sign;
/* 248 */     if ((l3 < 0.0D) || (l4 < 0.0D)) l_sign = -1.0D; else {
/* 249 */       l_sign = 1.0D;
/*     */     }
/* 251 */     double u = randomGenerator.nextDouble();
/* 252 */     double x = l_sign * (Math.exp(Math.log(u) * l3) - Math.exp(Math.log(1.0D - u) * l4));
/* 253 */     return x;
/*     */   }
/*     */ 
/*     */   public static double nextLaplace(MersenneTwisterFast randomGenerator)
/*     */   {
/* 264 */     double u = randomGenerator.nextDouble();
/* 265 */     u = u + u - 1.0D;
/* 266 */     if (u > 0.0D) return -Math.log(1.0D - u);
/* 267 */     return Math.log(1.0D + u);
/*     */   }
/*     */ 
/*     */   public static double nextLogistic(MersenneTwisterFast randomGenerator)
/*     */   {
/* 276 */     double u = randomGenerator.nextDouble();
/* 277 */     return -Math.log(1.0D / u - 1.0D);
/*     */   }
/*     */ 
/*     */   public static double nextPowLaw(double alpha, double cut, MersenneTwisterFast randomGenerator)
/*     */   {
/* 285 */     return cut * Math.pow(randomGenerator.nextDouble(), 1.0D / (alpha + 1.0D));
/*     */   }
/*     */ 
/*     */   public static double nextTriangular(MersenneTwisterFast randomGenerator)
/*     */   {
/* 309 */     double u = randomGenerator.nextDouble();
/* 310 */     if (u <= 0.5D) return Math.sqrt(2.0D * u) - 1.0D;
/* 311 */     return 1.0D - Math.sqrt(2.0D * (1.0D - u));
/*     */   }
/*     */ 
/*     */   public static double nextWeibull(double alpha, double beta, MersenneTwisterFast randomGenerator)
/*     */   {
/* 321 */     return Math.pow(beta * -Math.log(1.0D - randomGenerator.nextDouble()), 1.0D / alpha);
/*     */   }
/*     */ 
/*     */   public static int nextZipfInt(double z, MersenneTwisterFast randomGenerator)
/*     */   {
/* 338 */     double b = Math.pow(2.0D, z - 1.0D);
/* 339 */     double constant = -1.0D / (z - 1.0D);
/*     */ 
/* 341 */     int result = 0;
/*     */     while (true) {
/* 343 */       double u = randomGenerator.nextDouble();
/* 344 */       double v = randomGenerator.nextDouble();
/* 345 */       result = (int)Math.floor(Math.pow(u, constant));
/* 346 */       double t = Math.pow(1.0D + 1.0D / result, z - 1.0D);
/* 347 */       if (v * result * (t - 1.0D) / (b - 1.0D) <= t / b) break;
/*     */     }
/* 349 */     return result;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Distributions
 * JD-Core Version:    0.6.2
 */