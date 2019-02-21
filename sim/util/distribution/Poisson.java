/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class Poisson extends AbstractDiscreteDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected double mean;
/*  48 */   protected double my_old = -1.0D;
/*     */   protected double p;
/*     */   protected double q;
/*     */   protected double p0;
/*  50 */   protected double[] pp = new double[36];
/*     */   protected int llll;
/*  54 */   protected double my_last = -1.0D;
/*     */   protected double ll;
/*     */   protected int k2;
/*     */   protected int k4;
/*     */   protected int k1;
/*     */   protected int k5;
/*     */   protected double dl;
/*     */   protected double dr;
/*     */   protected double r1;
/*     */   protected double r2;
/*     */   protected double r4;
/*     */   protected double r5;
/*     */   protected double lr;
/*     */   protected double l_my;
/*     */   protected double c_pm;
/*     */   protected double f1;
/*     */   protected double f2;
/*     */   protected double f4;
/*     */   protected double f5;
/*     */   protected double p1;
/*     */   protected double p2;
/*     */   protected double p3;
/*     */   protected double p4;
/*     */   protected double p5;
/*     */   protected double p6;
/*     */   protected int m;
/*     */   protected static final double MEAN_MAX = 2147483647.0D;
/*     */   protected static final double SWITCH_MEAN = 10.0D;
/*     */ 
/*     */   public Poisson(double mean, MersenneTwisterFast randomGenerator)
/*     */   {
/*  73 */     setRandomGenerator(randomGenerator);
/*  74 */     setMean(mean);
/*     */   }
/*     */ 
/*     */   public double cdf(int k)
/*     */   {
/*  80 */     return Probability.poisson(k, this.mean);
/*     */   }
/*     */ 
/*     */   private static double f(int k, double l_nu, double c_pm)
/*     */   {
/*  96 */     return Math.exp(k * l_nu - Arithmetic.logFactorial(k) - c_pm);
/*     */   }
/*     */ 
/*     */   public int nextInt()
/*     */   {
/* 102 */     return nextInt(this.mean);
/*     */   }
/*     */ 
/*     */   public int nextInt(double theMean)
/*     */   {
/* 124 */     MersenneTwisterFast gen = this.randomGenerator;
/* 125 */     double my = theMean;
/*     */ 
/* 137 */     if (my < 10.0D) {
/* 138 */       if (my != this.my_old) {
/* 139 */         this.my_old = my;
/* 140 */         this.llll = 0;
/* 141 */         this.p = Math.exp(-my);
/* 142 */         this.q = this.p;
/* 143 */         this.p0 = this.p;
/*     */       }
/*     */ 
/* 146 */       this.m = (my > 1.0D ? (int)my : 1);
/*     */       while (true) {
/* 148 */         double u = gen.nextDouble();
/* 149 */         int k = 0;
/* 150 */         if (u <= this.p0) return k;
/* 151 */         if (this.llll != 0) {
/* 152 */           int i = u > 0.458D ? Math.min(this.llll, this.m) : 1;
/* 153 */           for (k = i; k <= this.llll; k++) if (u <= this.pp[k]) return k;  if (this.llll == 35);
/*     */         }
/*     */         else {
/* 156 */           for (k = this.llll + 1; k <= 35; k++) {
/* 157 */             this.p *= my / k;
/* 158 */             this.q += this.p;
/* 159 */             this.pp[k] = this.q;
/* 160 */             if (u <= this.q) {
/* 161 */               this.llll = k;
/* 162 */               return k;
/*     */             }
/*     */           }
/* 165 */           this.llll = 35;
/*     */         }
/*     */       }
/*     */     }
/* 168 */     if (my < 2147483647.0D)
/*     */     {
/* 176 */       this.m = ((int)my);
/* 177 */       if (my != this.my_last) {
/* 178 */         this.my_last = my;
/*     */ 
/* 181 */         double Ds = Math.sqrt(my + 0.25D);
/*     */ 
/* 185 */         this.k2 = ((int)Math.ceil(my - 0.5D - Ds));
/* 186 */         this.k4 = ((int)(my - 0.5D + Ds));
/* 187 */         this.k1 = (this.k2 + this.k2 - this.m + 1);
/* 188 */         this.k5 = (this.k4 + this.k4 - this.m);
/*     */ 
/* 191 */         this.dl = (this.k2 - this.k1);
/* 192 */         this.dr = (this.k5 - this.k4);
/*     */ 
/* 195 */         this.r1 = (my / this.k1);
/* 196 */         this.r2 = (my / this.k2);
/* 197 */         this.r4 = (my / (this.k4 + 1));
/* 198 */         this.r5 = (my / (this.k5 + 1));
/*     */ 
/* 201 */         this.ll = Math.log(this.r1);
/* 202 */         this.lr = (-Math.log(this.r5));
/*     */ 
/* 205 */         this.l_my = Math.log(my);
/* 206 */         this.c_pm = (this.m * this.l_my - Arithmetic.logFactorial(this.m));
/*     */ 
/* 209 */         this.f2 = f(this.k2, this.l_my, this.c_pm);
/* 210 */         this.f4 = f(this.k4, this.l_my, this.c_pm);
/* 211 */         this.f1 = f(this.k1, this.l_my, this.c_pm);
/* 212 */         this.f5 = f(this.k5, this.l_my, this.c_pm);
/*     */ 
/* 216 */         this.p1 = (this.f2 * (this.dl + 1.0D));
/* 217 */         this.p2 = (this.f2 * this.dl + this.p1);
/* 218 */         this.p3 = (this.f4 * (this.dr + 1.0D) + this.p2);
/* 219 */         this.p4 = (this.f4 * this.dr + this.p3);
/* 220 */         this.p5 = (this.f1 / this.ll + this.p4);
/* 221 */         this.p6 = (this.f5 / this.lr + this.p5); } double W;
/*     */       int X;
/*     */       label1315: 
/*     */       do { double U;
/*     */         int Dk;
/*     */         int X;
/*     */         do { if ((U = gen.nextDouble() * this.p6) < this.p2)
/*     */           {
/* 230 */             double V;
/* 230 */             if ((V = U - this.p1) < 0.0D) return this.k2 + (int)(U / this.f2);
/* 232 */             double W;
/* 232 */             if ((W = V / this.dl) < this.f1) return this.k1 + (int)(V / this.f1);
/*     */ 
/* 236 */             int Dk = (int)(this.dl * gen.nextDouble()) + 1;
/* 237 */             if (W <= this.f2 - Dk * (this.f2 - this.f2 / this.r2)) {
/* 238 */               return this.k2 - Dk;
/*     */             }
/* 240 */             if ((V = this.f2 + this.f2 - W) < 1.0D) {
/* 241 */               int Y = this.k2 + Dk;
/* 242 */               if (V <= this.f2 + Dk * (1.0D - this.f2) / (this.dl + 1.0D)) {
/* 243 */                 return Y;
/*     */               }
/* 245 */               if (V <= f(Y, this.l_my, this.c_pm)) return Y;
/*     */             }
/* 247 */             int X = this.k2 - Dk; break label1315;
/*     */           }
/* 249 */           if (U < this.p4)
/*     */           {
/* 251 */             double V;
/* 251 */             if ((V = U - this.p3) < 0.0D) return this.k4 - (int)((U - this.p2) / this.f4);
/* 253 */             double W;
/* 253 */             if ((W = V / this.dr) < this.f5) return this.k5 - (int)(V / this.f5);
/*     */ 
/* 257 */             int Dk = (int)(this.dr * gen.nextDouble()) + 1;
/* 258 */             if (W <= this.f4 - Dk * (this.f4 - this.f4 * this.r4)) {
/* 259 */               return this.k4 + Dk;
/*     */             }
/* 261 */             if ((V = this.f4 + this.f4 - W) < 1.0D) {
/* 262 */               int Y = this.k4 - Dk;
/* 263 */               if (V <= this.f4 + Dk * (1.0D - this.f4) / this.dr) {
/* 264 */                 return Y;
/*     */               }
/* 266 */               if (V <= f(Y, this.l_my, this.c_pm)) return Y;
/*     */             }
/* 268 */             int X = this.k4 + Dk; break label1315;
/*     */           }
/*     */ 
/* 271 */           W = gen.nextDouble();
/* 272 */           if (U >= this.p5) break;
/* 273 */           Dk = (int)(1.0D - Math.log(W) / this.ll); }
/* 274 */         while ((X = this.k1 - Dk) < 0);
/* 275 */         W *= (U - this.p4) * this.ll;
/* 276 */         if (W <= this.f1 - Dk * (this.f1 - this.f1 / this.r1)) { return X;
/*     */ 
/* 279 */           int Dk = (int)(1.0D - Math.log(W) / this.lr);
/* 280 */           X = this.k5 + Dk;
/* 281 */           W *= (U - this.p5) * this.lr;
/* 282 */           if (W <= this.f5 - Dk * (this.f5 - this.f5 * this.r5)) return X;
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 289 */       while (Math.log(W) > X * this.l_my - Arithmetic.logFactorial(X) - this.c_pm); return X;
/*     */     }
/*     */ 
/* 293 */     return (int)my;
/*     */   }
/*     */ 
/*     */   public double pdf(int k)
/*     */   {
/* 300 */     return Math.exp(k * Math.log(this.mean) - Arithmetic.logFactorial(k) - this.mean);
/*     */   }
/*     */ 
/*     */   public void setMean(double mean)
/*     */   {
/* 309 */     this.mean = mean;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 315 */     return getClass().getName() + "(" + this.mean + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Poisson
 * JD-Core Version:    0.6.2
 */