/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class Gamma extends AbstractContinousDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected double alpha;
/*     */   protected double lambda;
/*     */ 
/*     */   public Gamma(double alpha, double lambda, MersenneTwisterFast randomGenerator)
/*     */   {
/*  61 */     setRandomGenerator(randomGenerator);
/*  62 */     setState(alpha, lambda);
/*     */   }
/*     */ 
/*     */   public double cdf(double x)
/*     */   {
/*  68 */     return Probability.gamma(this.alpha, this.lambda, x);
/*     */   }
/*     */ 
/*     */   public double nextDouble()
/*     */   {
/*  74 */     return nextDouble(this.alpha, this.lambda);
/*     */   }
/*     */ 
/*     */   public double nextDouble(double alpha, double lambda)
/*     */   {
/* 102 */     double a = alpha;
/* 103 */     double aa = -1.0D; double aaa = -1.0D;
/* 104 */     double b = 0.0D; double c = 0.0D; double d = 0.0D; double s = 0.0D; double si = 0.0D; double ss = 0.0D; double q0 = 0.0D;
/* 105 */     double q1 = 0.0416666664D; double q2 = 0.0208333723D; double q3 = 0.0079849875D;
/* 106 */     double q4 = 0.0015746717D; double q5 = -0.0003349403D; double q6 = 0.0003340332D;
/* 107 */     double q7 = 0.0006053049D; double q8 = -0.0004701849D; double q9 = 0.000171032D;
/* 108 */     double a1 = 0.333333333D; double a2 = -0.249999949D; double a3 = 0.199999867D;
/* 109 */     double a4 = -0.166677482D; double a5 = 0.142873973D; double a6 = -0.124385581D;
/* 110 */     double a7 = 0.11036831D; double a8 = -0.112750886D; double a9 = 0.104089866D;
/* 111 */     double e1 = 1.0D; double e2 = 0.499999994D; double e3 = 0.166666848D;
/* 112 */     double e4 = 0.041664508D; double e5 = 0.008345521999999999D; double e6 = 0.001353826D;
/* 113 */     double e7 = 0.000247453D;
/*     */ 
/* 120 */     if (a <= 0.0D) throw new IllegalArgumentException();
/* 121 */     if (lambda <= 0.0D) throw new IllegalArgumentException();
/*     */ 
/* 123 */     if (a < 1.0D) { b = 1.0D + 0.36788794412D * a;
/*     */       double gds;
/*     */       do { double p;
/*     */         double gds;
/*     */         do { p = b * this.randomGenerator.nextDouble();
/* 127 */           if (p > 1.0D) break;
/* 128 */           gds = Math.exp(Math.log(p) / a); }
/* 129 */         while (Math.log(this.randomGenerator.nextDouble()) > -gds); return gds / lambda;
/*     */ 
/* 132 */         gds = -Math.log((b - p) / a); }
/* 133 */       while (Math.log(this.randomGenerator.nextDouble()) > (a - 1.0D) * Math.log(gds)); return gds / lambda;
/*     */     }
/*     */ 
/* 139 */     if (a != aa)
/*     */     {
/* 141 */       ss = a - 0.5D;
/* 142 */       s = Math.sqrt(ss);
/* 143 */       d = 5.656854249D - 12.0D * s;
/*     */     }double v1;
/*     */     double v12;
/*     */     do { v1 = 2.0D * this.randomGenerator.nextDouble() - 1.0D;
/* 148 */       double v2 = 2.0D * this.randomGenerator.nextDouble() - 1.0D;
/* 149 */       v12 = v1 * v1 + v2 * v2; }
/* 150 */     while (v12 > 1.0D);
/* 151 */     double t = v1 * Math.sqrt(-2.0D * Math.log(v12) / v12);
/* 152 */     double x = s + 0.5D * t;
/* 153 */     double gds = x * x;
/* 154 */     if (t >= 0.0D) return gds / lambda;
/*     */ 
/* 156 */     double u = this.randomGenerator.nextDouble();
/* 157 */     if (d * u <= t * t * t) return gds / lambda;
/*     */ 
/* 159 */     if (a != aaa)
/*     */     {
/* 161 */       double r = 1.0D / a;
/* 162 */       q0 = ((((((((q9 * r + q8) * r + q7) * r + q6) * r + q5) * r + q4) * r + q3) * r + q2) * r + q1) * r;
/*     */ 
/* 164 */       if (a > 3.686D) {
/* 165 */         if (a > 13.022D) {
/* 166 */           b = 1.77D;
/* 167 */           si = 0.75D;
/* 168 */           c = 0.1515D / s;
/*     */         }
/*     */         else {
/* 171 */           b = 1.654D + 0.0076D * ss;
/* 172 */           si = 1.68D / s + 0.275D;
/* 173 */           c = 0.062D / s + 0.024D;
/*     */         }
/*     */       }
/*     */       else {
/* 177 */         b = 0.463D + s - 0.178D * ss;
/* 178 */         si = 1.235D;
/* 179 */         c = 0.195D / s - 0.079D + 0.016D * s;
/*     */       }
/*     */     }
/* 182 */     if (x > 0.0D) {
/* 183 */       double v = t / (s + s);
/*     */       double q;
/*     */       double q;
/* 184 */       if (Math.abs(v) > 0.25D) {
/* 185 */         q = q0 - s * t + 0.25D * t * t + (ss + ss) * Math.log(1.0D + v);
/*     */       }
/*     */       else {
/* 188 */         q = q0 + 0.5D * t * t * ((((((((a9 * v + a8) * v + a7) * v + a6) * v + a5) * v + a4) * v + a3) * v + a2) * v + a1) * v;
/*     */       }
/*     */ 
/* 191 */       if (Math.log(1.0D - u) <= q) return gds / lambda;  } double e;
/*     */     double sign_u;
/*     */     double w;
/*     */     do { double q;
/*     */       do { do { e = -Math.log(this.randomGenerator.nextDouble());
/* 197 */           u = this.randomGenerator.nextDouble();
/* 198 */           u = u + u - 1.0D;
/* 199 */           sign_u = u > 0.0D ? 1.0D : -1.0D;
/* 200 */           t = b + e * si * sign_u; }
/* 201 */         while (t <= -0.71874483771719D);
/* 202 */         double v = t / (s + s);
/*     */         double q;
/* 203 */         if (Math.abs(v) > 0.25D) {
/* 204 */           q = q0 - s * t + 0.25D * t * t + (ss + ss) * Math.log(1.0D + v);
/*     */         }
/*     */         else {
/* 207 */           q = q0 + 0.5D * t * t * ((((((((a9 * v + a8) * v + a7) * v + a6) * v + a5) * v + a4) * v + a3) * v + a2) * v + a1) * v;
/*     */         }
/*     */       }
/* 210 */       while (q <= 0.0D);
/*     */       double w;
/* 211 */       if (q > 0.5D) {
/* 212 */         w = Math.exp(q) - 1.0D;
/*     */       }
/*     */       else {
/* 215 */         w = ((((((e7 * q + e6) * q + e5) * q + e4) * q + e3) * q + e2) * q + e1) * q;
/*     */       }
/*     */     }
/* 218 */     while (c * u * sign_u > w * Math.exp(e - 0.5D * t * t));
/* 219 */     x = s + 0.5D * t;
/* 220 */     return x * x / lambda;
/*     */   }
/*     */ 
/*     */   public double pdf(double x)
/*     */   {
/* 229 */     if (x < 0.0D) throw new IllegalArgumentException();
/* 230 */     if (x == 0.0D) {
/* 231 */       if (this.alpha == 1.0D) return 1.0D / this.lambda;
/* 232 */       return 0.0D;
/*     */     }
/* 234 */     if (this.alpha == 1.0D) return Math.exp(-x / this.lambda) / this.lambda;
/*     */ 
/* 236 */     return Math.exp((this.alpha - 1.0D) * Math.log(x / this.lambda) - x / this.lambda - Fun.logGamma(this.alpha)) / this.lambda;
/*     */   }
/*     */ 
/*     */   public void setState(double alpha, double lambda)
/*     */   {
/* 243 */     if (alpha <= 0.0D) throw new IllegalArgumentException();
/* 244 */     if (lambda <= 0.0D) throw new IllegalArgumentException();
/* 245 */     this.alpha = alpha;
/* 246 */     this.lambda = lambda;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 252 */     return getClass().getName() + "(" + this.alpha + "," + this.lambda + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Gamma
 * JD-Core Version:    0.6.2
 */