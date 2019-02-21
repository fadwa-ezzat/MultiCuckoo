/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class HyperGeometric extends AbstractDiscreteDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected int my_N;
/*     */   protected int my_s;
/*     */   protected int my_n;
/*  45 */   private int N_last = -1; private int M_last = -1; private int n_last = -1;
/*     */   private int N_Mn;
/*     */   private int m;
/*     */   private int mp;
/*     */   private int b;
/*     */   private double Mp;
/*     */   private double np;
/*     */   private double fm;
/*     */   private int k2;
/*     */   private int k4;
/*     */   private int k1;
/*     */   private int k5;
/*     */   private double dl;
/*     */   private double dr;
/*     */   private double r1;
/*     */   private double r2;
/*     */   private double r4;
/*     */   private double r5;
/*     */   private double ll;
/*     */   private double lr;
/*     */   private double c_pm;
/*     */   private double f1;
/*     */   private double f2;
/*     */   private double f4;
/*     */   private double f5;
/*     */   private double p1;
/*     */   private double p2;
/*     */   private double p3;
/*     */   private double p4;
/*     */   private double p5;
/*     */   private double p6;
/*     */ 
/*     */   public HyperGeometric(int N, int s, int n, MersenneTwisterFast randomGenerator)
/*     */   {
/*  62 */     setRandomGenerator(randomGenerator);
/*  63 */     setState(N, s, n);
/*     */   }
/*     */   private static double fc_lnpk(int k, int N_Mn, int M, int n) {
/*  66 */     return Arithmetic.logFactorial(k) + Arithmetic.logFactorial(M - k) + Arithmetic.logFactorial(n - k) + Arithmetic.logFactorial(N_Mn + k);
/*     */   }
/*     */ 
/*     */   protected int hmdu(int N, int M, int n, MersenneTwisterFast randomGenerator)
/*     */   {
/*  76 */     if ((N != this.N_last) || (M != this.M_last) || (n != this.n_last)) {
/*  77 */       this.N_last = N;
/*  78 */       this.M_last = M;
/*  79 */       this.n_last = n;
/*     */ 
/*  81 */       this.Mp = (M + 1);
/*  82 */       this.np = (n + 1); this.N_Mn = (N - M - n);
/*     */ 
/*  84 */       double p = this.Mp / (N + 2.0D);
/*  85 */       double nu = this.np * p;
/*  86 */       if (((this.m = (int)nu) == nu) && (p == 0.5D)) {
/*  87 */         this.mp = (this.m--);
/*     */       }
/*     */       else {
/*  90 */         this.mp = (this.m + 1);
/*     */       }
/*     */ 
/*  94 */       this.fm = Math.exp(Arithmetic.logFactorial(N - M) - Arithmetic.logFactorial(this.N_Mn + this.m) - Arithmetic.logFactorial(n - this.m) + Arithmetic.logFactorial(M) - Arithmetic.logFactorial(M - this.m) - Arithmetic.logFactorial(this.m) - Arithmetic.logFactorial(N) + Arithmetic.logFactorial(N - n) + Arithmetic.logFactorial(n));
/*     */ 
/* 100 */       this.b = ((int)(nu + 11.0D * Math.sqrt(nu * (1.0D - p) * (1.0D - n / N) + 1.0D)));
/* 101 */       if (this.b > n) this.b = n;
/*     */     }
/*     */     while (true)
/*     */     {
/* 105 */       double U;
/* 105 */       if ((U = randomGenerator.nextDouble() - this.fm) <= 0.0D) return this.m;
/*     */       double d;
/* 106 */       double c = d = this.fm;
/*     */ 
/* 109 */       for (int I = 1; I <= this.m; I++) {
/* 110 */         int K = this.mp - I;
/* 111 */         c *= K / (this.np - K) * ((this.N_Mn + K) / (this.Mp - K));
/* 112 */         if (U -= c <= 0.0D) return K - 1;
/*     */ 
/* 114 */         K = this.m + I;
/* 115 */         d *= (this.np - K) / K * ((this.Mp - K) / (this.N_Mn + K));
/* 116 */         if (U -= d <= 0.0D) return K;
/*     */ 
/*     */       }
/*     */ 
/* 120 */       for (int K = this.mp + this.m; K <= this.b; K++) {
/* 121 */         d *= (this.np - K) / K * ((this.Mp - K) / (this.N_Mn + K));
/* 122 */         if (U -= d <= 0.0D) return K;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int hprs(int N, int M, int n, MersenneTwisterFast randomGenerator)
/*     */   {
/* 133 */     if ((N != this.N_last) || (M != this.M_last) || (n != this.n_last)) {
/* 134 */       this.N_last = N;
/* 135 */       this.M_last = M;
/* 136 */       this.n_last = n;
/*     */ 
/* 138 */       double Mp = M + 1;
/* 139 */       double np = n + 1; this.N_Mn = (N - M - n);
/*     */ 
/* 141 */       double p = Mp / (N + 2.0D); double nu = np * p;
/*     */ 
/* 144 */       double U = Math.sqrt(nu * (1.0D - p) * (1.0D - (n + 2.0D) / (N + 3.0D)) + 0.25D);
/*     */ 
/* 151 */       this.m = ((int)nu);
/* 152 */       this.k2 = ((int)Math.ceil(nu - 0.5D - U)); if (this.k2 >= this.m) this.k2 = (this.m - 1);
/* 153 */       this.k4 = ((int)(nu - 0.5D + U));
/* 154 */       this.k1 = (this.k2 + this.k2 - this.m + 1);
/* 155 */       this.k5 = (this.k4 + this.k4 - this.m);
/*     */ 
/* 158 */       this.dl = (this.k2 - this.k1);
/* 159 */       this.dr = (this.k5 - this.k4);
/*     */ 
/* 162 */       this.r1 = ((np / this.k1 - 1.0D) * (Mp - this.k1) / (this.N_Mn + this.k1));
/* 163 */       this.r2 = ((np / this.k2 - 1.0D) * (Mp - this.k2) / (this.N_Mn + this.k2));
/* 164 */       this.r4 = ((np / (this.k4 + 1) - 1.0D) * (M - this.k4) / (this.N_Mn + this.k4 + 1));
/* 165 */       this.r5 = ((np / (this.k5 + 1) - 1.0D) * (M - this.k5) / (this.N_Mn + this.k5 + 1));
/*     */ 
/* 168 */       this.ll = Math.log(this.r1);
/* 169 */       this.lr = (-Math.log(this.r5));
/*     */ 
/* 172 */       this.c_pm = fc_lnpk(this.m, this.N_Mn, M, n);
/*     */ 
/* 175 */       this.f2 = Math.exp(this.c_pm - fc_lnpk(this.k2, this.N_Mn, M, n));
/* 176 */       this.f4 = Math.exp(this.c_pm - fc_lnpk(this.k4, this.N_Mn, M, n));
/* 177 */       this.f1 = Math.exp(this.c_pm - fc_lnpk(this.k1, this.N_Mn, M, n));
/* 178 */       this.f5 = Math.exp(this.c_pm - fc_lnpk(this.k5, this.N_Mn, M, n));
/*     */ 
/* 182 */       this.p1 = (this.f2 * (this.dl + 1.0D));
/* 183 */       this.p2 = (this.f2 * this.dl + this.p1);
/* 184 */       this.p3 = (this.f4 * (this.dr + 1.0D) + this.p2);
/* 185 */       this.p4 = (this.f4 * this.dr + this.p3);
/* 186 */       this.p5 = (this.f1 / this.ll + this.p4);
/* 187 */       this.p6 = (this.f5 / this.lr + this.p5); } double Y;
/*     */     int X;
/*     */     label1262: 
/*     */     do { double U;
/*     */       int Dk;
/*     */       do { int Dk;
/*     */         int X;
/*     */         do { if ((U = randomGenerator.nextDouble() * this.p6) < this.p2)
/*     */           {
/* 196 */             double W;
/* 196 */             if ((W = U - this.p1) < 0.0D) return this.k2 + (int)(U / this.f2);
/* 198 */             double Y;
/* 198 */             if ((Y = W / this.dl) < this.f1) return this.k1 + (int)(W / this.f1);
/*     */ 
/* 202 */             int Dk = (int)(this.dl * randomGenerator.nextDouble()) + 1;
/* 203 */             if (Y <= this.f2 - Dk * (this.f2 - this.f2 / this.r2)) {
/* 204 */               return this.k2 - Dk;
/*     */             }
/* 206 */             if ((W = this.f2 + this.f2 - Y) < 1.0D) {
/* 207 */               int V = this.k2 + Dk;
/* 208 */               if (W <= this.f2 + Dk * (1.0D - this.f2) / (this.dl + 1.0D)) {
/* 209 */                 return V;
/*     */               }
/* 211 */               if (Math.log(W) <= this.c_pm - fc_lnpk(V, this.N_Mn, M, n)) {
/* 212 */                 return V;
/*     */               }
/*     */             }
/* 215 */             int X = this.k2 - Dk; break label1262;
/*     */           }
/* 217 */           if (U < this.p4)
/*     */           {
/* 220 */             double W;
/* 220 */             if ((W = U - this.p3) < 0.0D) return this.k4 - (int)((U - this.p2) / this.f4);
/* 222 */             double Y;
/* 222 */             if ((Y = W / this.dr) < this.f5) return this.k5 - (int)(W / this.f5);
/*     */ 
/* 226 */             int Dk = (int)(this.dr * randomGenerator.nextDouble()) + 1;
/* 227 */             if (Y <= this.f4 - Dk * (this.f4 - this.f4 * this.r4)) {
/* 228 */               return this.k4 + Dk;
/*     */             }
/* 230 */             if ((W = this.f4 + this.f4 - Y) < 1.0D) {
/* 231 */               int V = this.k4 - Dk;
/* 232 */               if (W <= this.f4 + Dk * (1.0D - this.f4) / this.dr) {
/* 233 */                 return V;
/*     */               }
/* 235 */               if (Math.log(W) <= this.c_pm - fc_lnpk(V, this.N_Mn, M, n)) {
/* 236 */                 return V;
/*     */               }
/*     */             }
/* 239 */             int X = this.k4 + Dk; break label1262;
/*     */           }
/*     */ 
/* 242 */           Y = randomGenerator.nextDouble();
/* 243 */           if (U >= this.p5) break;
/* 244 */           Dk = (int)(1.0D - Math.log(Y) / this.ll); }
/* 245 */         while ((X = this.k1 - Dk) < 0);
/* 246 */         Y *= (U - this.p4) * this.ll;
/* 247 */         if (Y > this.f1 - Dk * (this.f1 - this.f1 / this.r1)) break;
/* 248 */         return X;
/*     */ 
/* 252 */         Dk = (int)(1.0D - Math.log(Y) / this.lr); }
/* 253 */       while ((X = this.k5 + Dk) > n);
/* 254 */       Y *= (U - this.p5) * this.lr;
/* 255 */       if (Y <= this.f5 - Dk * (this.f5 - this.f5 * this.r5)) {
/* 256 */         return X;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 266 */     while (Math.log(Y) > this.c_pm - fc_lnpk(X, this.N_Mn, M, n)); return X;
/*     */   }
/*     */ 
/*     */   public int nextInt()
/*     */   {
/* 273 */     return nextInt(this.my_N, this.my_s, this.my_n, this.randomGenerator);
/*     */   }
/*     */ 
/*     */   public int nextInt(int N, int s, int n)
/*     */   {
/* 279 */     return nextInt(N, s, n, this.randomGenerator);
/*     */   }
/*     */ 
/*     */   protected int nextInt(int N, int M, int n, MersenneTwisterFast randomGenerator)
/*     */   {
/* 329 */     int Nhalf = N / 2;
/* 330 */     int n_le_Nhalf = n <= Nhalf ? n : N - n;
/* 331 */     int M_le_Nhalf = M <= Nhalf ? M : N - M;
/*     */     int K;
/*     */     int K;
/* 333 */     if (n * M / N < 10) {
/* 334 */       K = n_le_Nhalf <= M_le_Nhalf ? hmdu(N, M_le_Nhalf, n_le_Nhalf, randomGenerator) : hmdu(N, n_le_Nhalf, M_le_Nhalf, randomGenerator);
/*     */     }
/*     */     else
/*     */     {
/* 339 */       K = n_le_Nhalf <= M_le_Nhalf ? hprs(N, M_le_Nhalf, n_le_Nhalf, randomGenerator) : hprs(N, n_le_Nhalf, M_le_Nhalf, randomGenerator);
/*     */     }
/*     */ 
/* 344 */     if (n <= Nhalf) {
/* 345 */       return M <= Nhalf ? K : n - K;
/*     */     }
/*     */ 
/* 348 */     return M <= Nhalf ? M - K : n - N + M + K;
/*     */   }
/*     */ 
/*     */   public double pdf(int k)
/*     */   {
/* 355 */     return Arithmetic.binomial(this.my_s, k) * Arithmetic.binomial(this.my_N - this.my_s, this.my_n - k) / Arithmetic.binomial(this.my_N, this.my_n);
/*     */   }
/*     */ 
/*     */   public void setState(int N, int s, int n)
/*     */   {
/* 362 */     this.my_N = N;
/* 363 */     this.my_s = s;
/* 364 */     this.my_n = n;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 370 */     return getClass().getName() + "(" + this.my_N + "," + this.my_s + "," + this.my_n + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.HyperGeometric
 * JD-Core Version:    0.6.2
 */