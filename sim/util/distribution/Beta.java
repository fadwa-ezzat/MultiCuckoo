/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class Beta extends AbstractContinousDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected double alpha;
/*     */   protected double beta;
/*     */   double PDF_CONST;
/*  50 */   double a_last = 0.0D; double b_last = 0.0D;
/*     */   double a_;
/*     */   double b_;
/*     */   double t;
/*     */   double fa;
/*     */   double fb;
/*     */   double p1;
/*     */   double p2;
/*     */   double c;
/*     */   double ml;
/*     */   double mu;
/*  60 */   double p_last = 0.0D; double q_last = 0.0D;
/*     */   double a;
/*     */   double b;
/*     */   double s;
/*     */   double m;
/*     */   double D;
/*     */   double Dl;
/*     */   double x1;
/*     */   double x2;
/*     */   double x4;
/*     */   double x5;
/*     */   double f1;
/*     */   double f2;
/*     */   double f4;
/*     */   double f5;
/*     */   double ll;
/*     */   double lr;
/*     */   double z2;
/*     */   double z4;
/*     */   double p3;
/*     */   double p4;
/*     */ 
/*     */   public Beta(double alpha, double beta, MersenneTwisterFast randomGenerator)
/*     */   {
/*  69 */     setRandomGenerator(randomGenerator);
/*  70 */     setState(alpha, beta);
/*     */   }
/*     */ 
/*     */   protected double b00(double a, double b, MersenneTwisterFast randomGenerator)
/*     */   {
/*  78 */     if ((a != this.a_last) || (b != this.b_last)) {
/*  79 */       this.a_last = a;
/*  80 */       this.b_last = b;
/*     */ 
/*  82 */       this.a_ = (a - 1.0D);
/*  83 */       this.b_ = (b - 1.0D);
/*  84 */       this.c = (b * this.b_ / (a * this.a_));
/*  85 */       this.t = (this.c == 1.0D ? 0.5D : (1.0D - Math.sqrt(this.c)) / (1.0D - this.c));
/*  86 */       this.fa = Math.exp(this.a_ * Math.log(this.t));
/*  87 */       this.fb = Math.exp(this.b_ * Math.log(1.0D - this.t));
/*     */ 
/*  89 */       this.p1 = (this.t / a);
/*  90 */       this.p2 = ((1.0D - this.t) / b + this.p1);
/*     */     }
/*     */     double X;
/*     */     while (true)
/*     */     {
/*     */       double U;
/*  94 */       if ((U = randomGenerator.nextDouble() * this.p2) <= this.p1) {
/*  95 */         double Z = Math.exp(Math.log(U / this.p1) / a); double X = this.t * Z;
/*     */         double V;
/*  97 */         if ((V = randomGenerator.nextDouble() * this.fb) > 1.0D - this.b_ * X)
/*     */         {
/*  99 */           if ((V <= 1.0D + (this.fb - 1.0D) * Z) && 
/* 101 */             (Math.log(V) <= this.b_ * Math.log(1.0D - X))) break; 
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 105 */         double Z = Math.exp(Math.log((U - this.p1) / (this.p2 - this.p1)) / b); X = 1.0D - (1.0D - this.t) * Z;
/*     */         double V;
/* 107 */         if ((V = randomGenerator.nextDouble() * this.fa) > 1.0D - this.a_ * (1.0D - X))
/*     */         {
/* 109 */           if ((V <= 1.0D + (this.fa - 1.0D) * Z) && 
/* 111 */             (Math.log(V) <= this.a_ * Math.log(X))) break;
/*     */         }
/*     */       }
/*     */     }
/* 115 */     return X;
/*     */   }
/*     */ 
/*     */   protected double b01(double a, double b, MersenneTwisterFast randomGenerator)
/*     */   {
/* 123 */     if ((a != this.a_last) || (b != this.b_last)) {
/* 124 */       this.a_last = a;
/* 125 */       this.b_last = b;
/*     */ 
/* 127 */       this.a_ = (a - 1.0D);
/* 128 */       this.b_ = (b - 1.0D);
/* 129 */       this.t = (this.a_ / (a - b));
/* 130 */       this.fb = Math.exp((this.b_ - 1.0D) * Math.log(1.0D - this.t)); this.fa = (a - (a + this.b_) * this.t);
/* 131 */       this.t -= (this.t - (1.0D - this.fa) * (1.0D - this.t) * this.fb / b) / (1.0D - this.fa * this.fb);
/* 132 */       this.fa = Math.exp(this.a_ * Math.log(this.t));
/* 133 */       this.fb = Math.exp(this.b_ * Math.log(1.0D - this.t));
/* 134 */       if (this.b_ <= 1.0D) {
/* 135 */         this.ml = ((1.0D - this.fb) / this.t);
/* 136 */         this.mu = (this.b_ * this.t);
/*     */       }
/*     */       else {
/* 139 */         this.ml = this.b_;
/* 140 */         this.mu = (1.0D - this.fb);
/*     */       }
/* 142 */       this.p1 = (this.t / a);
/* 143 */       this.p2 = (this.fb * (1.0D - this.t) / b + this.p1);
/*     */     }
/*     */     double X;
/*     */     while (true)
/*     */     {
/*     */       double U;
/* 147 */       if ((U = randomGenerator.nextDouble() * this.p2) <= this.p1) {
/* 148 */         double Z = Math.exp(Math.log(U / this.p1) / a); double X = this.t * Z;
/*     */         double V;
/* 150 */         if ((V = randomGenerator.nextDouble()) > 1.0D - this.ml * X)
/*     */         {
/* 152 */           if ((V <= 1.0D - this.mu * Z) && 
/* 154 */             (Math.log(V) <= this.b_ * Math.log(1.0D - X))) break; 
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 158 */         double Z = Math.exp(Math.log((U - this.p1) / (this.p2 - this.p1)) / b); X = 1.0D - (1.0D - this.t) * Z;
/*     */         double V;
/* 160 */         if ((V = randomGenerator.nextDouble() * this.fa) > 1.0D - this.a_ * (1.0D - X))
/*     */         {
/* 162 */           if ((V <= 1.0D + (this.fa - 1.0D) * Z) && 
/* 164 */             (Math.log(V) <= this.a_ * Math.log(X))) break;
/*     */         }
/*     */       }
/*     */     }
/* 168 */     return X;
/*     */   }
/*     */ 
/*     */   protected double b1prs(double p, double q, MersenneTwisterFast randomGenerator)
/*     */   {
/* 176 */     if ((p != this.p_last) || (q != this.q_last)) {
/* 177 */       this.p_last = p;
/* 178 */       this.q_last = q;
/*     */ 
/* 180 */       this.a = (p - 1.0D);
/* 181 */       this.b = (q - 1.0D);
/* 182 */       this.s = (this.a + this.b); this.m = (this.a / this.s);
/* 183 */       if ((this.a > 1.0D) || (this.b > 1.0D)) this.D = Math.sqrt(this.m * (1.0D - this.m) / (this.s - 1.0D));
/*     */ 
/* 185 */       if (this.a <= 1.0D) {
/* 186 */         this.x2 = (this.Dl = this.m * 0.5D); this.x1 = (this.z2 = 0.0D); this.f1 = (this.ll = 0.0D);
/*     */       }
/*     */       else {
/* 189 */         this.x2 = (this.m - this.D); this.x1 = (this.x2 - this.D);
/* 190 */         this.z2 = (this.x2 * (1.0D - (1.0D - this.x2) / (this.s * this.D)));
/* 191 */         if ((this.x1 <= 0.0D) || ((this.s - 6.0D) * this.x2 - this.a + 3.0D > 0.0D)) {
/* 192 */           this.x1 = this.z2; this.x2 = ((this.x1 + this.m) * 0.5D);
/* 193 */           this.Dl = (this.m - this.x2);
/*     */         }
/*     */         else {
/* 196 */           this.Dl = this.D;
/*     */         }
/* 198 */         this.f1 = f(this.x1, this.a, this.b, this.m);
/* 199 */         this.ll = (this.x1 * (1.0D - this.x1) / (this.s * (this.m - this.x1)));
/*     */       }
/* 201 */       this.f2 = f(this.x2, this.a, this.b, this.m);
/*     */ 
/* 203 */       if (this.b <= 1.0D) {
/* 204 */         this.x4 = (1.0D - (this.D = (1.0D - this.m) * 0.5D)); this.x5 = (this.z4 = 1.0D); this.f5 = (this.lr = 0.0D);
/*     */       }
/*     */       else {
/* 207 */         this.x4 = (this.m + this.D); this.x5 = (this.x4 + this.D);
/* 208 */         this.z4 = (this.x4 * (1.0D + (1.0D - this.x4) / (this.s * this.D)));
/* 209 */         if ((this.x5 >= 1.0D) || ((this.s - 6.0D) * this.x4 - this.a + 3.0D < 0.0D)) {
/* 210 */           this.x5 = this.z4; this.x4 = ((this.m + this.x5) * 0.5D);
/* 211 */           this.D = (this.x4 - this.m);
/*     */         }
/* 213 */         this.f5 = f(this.x5, this.a, this.b, this.m);
/* 214 */         this.lr = (this.x5 * (1.0D - this.x5) / (this.s * (this.x5 - this.m)));
/*     */       }
/* 216 */       this.f4 = f(this.x4, this.a, this.b, this.m);
/*     */ 
/* 218 */       this.p1 = (this.f2 * (this.Dl + this.Dl));
/* 219 */       this.p2 = (this.f4 * (this.D + this.D) + this.p1);
/* 220 */       this.p3 = (this.f1 * this.ll + this.p2);
/* 221 */       this.p4 = (this.f5 * this.lr + this.p3); } double X;
/*     */     double W;
/*     */     label1308: 
/*     */     do { double U;
/*     */       double Y;
/*     */       do { double Y;
/*     */         double X;
/*     */         do { if ((U = randomGenerator.nextDouble() * this.p4) <= this.p1)
/*     */           {
/* 227 */             double W;
/* 227 */             if ((W = U / this.Dl - this.f2) <= 0.0D) return this.m - U / this.f2;
/*     */ 
/* 229 */             if (W <= this.f1) return this.x2 - W / this.f1 * this.Dl;
/*     */ 
/* 231 */             double V = this.Dl * (U = randomGenerator.nextDouble());
/* 232 */             double X = this.x2 - V; double Y = this.x2 + V;
/*     */ 
/* 234 */             if (W * (this.x2 - this.z2) <= this.f2 * (X - this.z2)) return X;
/* 235 */             if ((V = this.f2 + this.f2 - W) >= 1.0D)
/*     */               break label1308;
/* 237 */             if (V <= this.f2 + (1.0D - this.f2) * U) return Y;
/*     */ 
/* 239 */             if (V > f(Y, this.a, this.b, this.m)) break label1308; return Y;
/*     */           }
/*     */ 
/* 242 */           if (U <= this.p2) {
/* 243 */             U -= this.p1;
/*     */             double W;
/* 245 */             if ((W = U / this.D - this.f4) <= 0.0D) return this.m + U / this.f4;
/*     */ 
/* 247 */             if (W <= this.f5) return this.x4 + W / this.f5 * this.D;
/*     */ 
/* 249 */             double V = this.D * (U = randomGenerator.nextDouble());
/* 250 */             double X = this.x4 + V; double Y = this.x4 - V;
/*     */ 
/* 252 */             if (W * (this.z4 - this.x4) <= this.f4 * (this.z4 - X)) return X;
/* 253 */             if ((V = this.f4 + this.f4 - W) >= 1.0D)
/*     */               break label1308;
/* 255 */             if (V <= this.f4 + (1.0D - this.f4) * U) return Y;
/*     */ 
/* 257 */             if (V > f(Y, this.a, this.b, this.m)) break label1308; return Y;
/*     */           }
/*     */ 
/* 260 */           if (U > this.p3) break;
/* 261 */           Y = Math.log(U = (U - this.p2) / (this.p3 - this.p2)); }
/* 262 */         while ((X = this.x1 + this.ll * Y) <= 0.0D);
/* 263 */         double W = randomGenerator.nextDouble() * U;
/*     */ 
/* 266 */         if (W <= 1.0D + Y) return X;
/* 267 */         W *= this.f1; break;
/*     */ 
/* 270 */         Y = Math.log(U = (U - this.p3) / (this.p4 - this.p3)); }
/* 271 */       while ((X = this.x5 - this.lr * Y) >= 1.0D);
/* 272 */       W = randomGenerator.nextDouble() * U;
/*     */ 
/* 275 */       if (W <= 1.0D + Y) return X;
/* 276 */       W *= this.f5;
/*     */     }
/*     */ 
/* 279 */     while (Math.log(W) > this.a * Math.log(X / this.m) + this.b * Math.log((1.0D - X) / (1.0D - this.m))); return X;
/*     */   }
/*     */ 
/*     */   public double cdf(double x)
/*     */   {
/* 286 */     return Probability.beta(this.alpha, this.beta, x);
/*     */   }
/*     */   private static double f(double x, double a, double b, double m) {
/* 289 */     return Math.exp(a * Math.log(x / m) + b * Math.log((1.0D - x) / (1.0D - m)));
/*     */   }
/*     */ 
/*     */   public double nextDouble()
/*     */   {
/* 295 */     return nextDouble(this.alpha, this.beta);
/*     */   }
/*     */ 
/*     */   public double nextDouble(double alpha, double beta)
/*     */   {
/* 343 */     double a = alpha;
/* 344 */     double b = beta;
/* 345 */     if (a > 1.0D) {
/* 346 */       if (b > 1.0D) return b1prs(a, b, this.randomGenerator);
/* 347 */       if (b < 1.0D) return 1.0D - b01(b, a, this.randomGenerator);
/* 348 */       if (b == 1.0D) {
/* 349 */         return Math.exp(Math.log(this.randomGenerator.nextDouble()) / a);
/*     */       }
/*     */     }
/*     */ 
/* 353 */     if (a < 1.0D) {
/* 354 */       if (b > 1.0D) return b01(a, b, this.randomGenerator);
/* 355 */       if (b < 1.0D) return b00(a, b, this.randomGenerator);
/* 356 */       if (b == 1.0D) {
/* 357 */         return Math.exp(Math.log(this.randomGenerator.nextDouble()) / a);
/*     */       }
/*     */     }
/*     */ 
/* 361 */     if (a == 1.0D) {
/* 362 */       if (b != 1.0D) return 1.0D - Math.exp(Math.log(this.randomGenerator.nextDouble()) / b);
/* 363 */       if (b == 1.0D) return this.randomGenerator.nextDouble();
/*     */     }
/*     */ 
/* 366 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double pdf(double x)
/*     */   {
/* 372 */     if ((x < 0.0D) || (x > 1.0D)) return 0.0D;
/* 373 */     return Math.exp(this.PDF_CONST) * Math.pow(x, this.alpha - 1.0D) * Math.pow(1.0D - x, this.beta - 1.0D);
/*     */   }
/*     */ 
/*     */   public void setState(double alpha, double beta)
/*     */   {
/* 379 */     this.alpha = alpha;
/* 380 */     this.beta = beta;
/* 381 */     this.PDF_CONST = (Fun.logGamma(alpha + beta) - Fun.logGamma(alpha) - Fun.logGamma(beta));
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 387 */     return getClass().getName() + "(" + this.alpha + "," + this.beta + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Beta
 * JD-Core Version:    0.6.2
 */