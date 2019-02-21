/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class Binomial extends AbstractDiscreteDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected int n;
/*     */   protected double p;
/*  37 */   private int n_last = -1; private int n_prev = -1;
/*     */   private double par;
/*     */   private double np;
/*     */   private double p0;
/*     */   private double q;
/*  38 */   private double p_last = -1.0D; private double p_prev = -1.0D;
/*     */   private int b;
/*     */   private int m;
/*     */   private int nm;
/*     */   private double pq;
/*     */   private double rc;
/*     */   private double ss;
/*     */   private double xm;
/*     */   private double xl;
/*     */   private double xr;
/*     */   private double ll;
/*     */   private double lr;
/*     */   private double c;
/*     */   private double p1;
/*     */   private double p2;
/*     */   private double p3;
/*     */   private double p4;
/*     */   private double ch;
/*     */   private double log_p;
/*     */   private double log_q;
/*     */   private double log_n;
/*     */ 
/*     */   public Binomial(int n, double p, MersenneTwisterFast randomGenerator)
/*     */   {
/*  54 */     setRandomGenerator(randomGenerator);
/*  55 */     setNandP(n, p);
/*     */   }
/*     */ 
/*     */   public double cdf(int k)
/*     */   {
/*  61 */     return Probability.binomial(k, this.n, this.p);
/*     */   }
/*     */ 
/*     */   private double cdfSlow(int k)
/*     */   {
/*  67 */     if (k < 0) throw new IllegalArgumentException();
/*     */ 
/*  69 */     double sum = 0.0D;
/*  70 */     for (int r = 0; r <= k; r++) sum += pdf(r);
/*     */ 
/*  72 */     return sum;
/*     */   }
/*     */ 
/*     */   protected int generateBinomial(int n, double p)
/*     */   {
/* 117 */     double C1_3 = 0.3333333333333333D;
/* 118 */     double C5_8 = 0.625D;
/* 119 */     double C1_6 = 0.1666666666666667D;
/* 120 */     int DMAX_KM = 20;
/*     */ 
/* 126 */     if ((n != this.n_last) || (p != this.p_last)) {
/* 127 */       this.n_last = n;
/* 128 */       this.p_last = p;
/* 129 */       this.par = Math.min(p, 1.0D - p);
/* 130 */       this.q = (1.0D - this.par);
/* 131 */       this.np = (n * this.par);
/*     */ 
/* 135 */       if (this.np <= 0.0D) return -1;
/*     */ 
/* 137 */       double rm = this.np + this.par;
/* 138 */       this.m = ((int)rm);
/* 139 */       if (this.np < 10.0D) {
/* 140 */         this.p0 = Math.exp(n * Math.log(this.q));
/* 141 */         int bh = (int)(this.np + 10.0D * Math.sqrt(this.np * this.q));
/* 142 */         this.b = Math.min(n, bh);
/*     */       }
/*     */       else {
/* 145 */         this.rc = ((n + 1.0D) * (this.pq = this.par / this.q));
/* 146 */         this.ss = (this.np * this.q);
/* 147 */         int i = (int)(2.195D * Math.sqrt(this.ss) - 4.6D * this.q);
/* 148 */         this.xm = (this.m + 0.5D);
/* 149 */         this.xl = (this.m - i);
/* 150 */         this.xr = (this.m + i + 1L);
/* 151 */         double f = (rm - this.xl) / (rm - this.xl * this.par); this.ll = (f * (1.0D + 0.5D * f));
/* 152 */         f = (this.xr - rm) / (this.xr * this.q); this.lr = (f * (1.0D + 0.5D * f));
/* 153 */         this.c = (0.134D + 20.5D / (15.300000000000001D + this.m));
/*     */ 
/* 155 */         this.p1 = (i + 0.5D);
/* 156 */         this.p2 = (this.p1 * (1.0D + this.c + this.c));
/* 157 */         this.p3 = (this.p2 + this.c / this.ll);
/* 158 */         this.p4 = (this.p3 + this.c / this.lr);
/*     */       }
/*     */     }
/*     */ 
/* 162 */     if (this.np < 10.0D)
/*     */     {
/* 165 */       int K = 0;
/* 166 */       double pk = this.p0;
/* 167 */       double U = this.randomGenerator.nextDouble();
/* 168 */       while (U > pk) {
/* 169 */         K++;
/* 170 */         if (K > this.b) {
/* 171 */           U = this.randomGenerator.nextDouble();
/* 172 */           K = 0;
/* 173 */           pk = this.p0;
/*     */         }
/*     */         else {
/* 176 */           U -= pk;
/* 177 */           pk = (n - K + 1) * this.par * pk / (K * this.q);
/*     */         }
/*     */       }
/* 180 */       return p > 0.5D ? n - K : K;
/*     */     }
/*     */     while (true)
/*     */     {
/* 184 */       double V = this.randomGenerator.nextDouble();
/*     */       double U;
/* 185 */       if ((U = this.randomGenerator.nextDouble() * this.p4) <= this.p1) {
/* 186 */         int K = (int)(this.xm - U + this.p1 * V);
/* 187 */         return p > 0.5D ? n - K : K;
/*     */       }
/* 189 */       if (U <= this.p2) {
/* 190 */         double X = this.xl + (U - this.p1) / this.c;
/* 191 */         if ((V = V * this.c + 1.0D - Math.abs(this.xm - X) / this.p1) < 1.0D)
/* 192 */           int K = (int)X;
/*     */       }
/* 194 */       else if (U <= this.p3)
/*     */       {
/*     */         double X;
/* 195 */         if ((X = this.xl + Math.log(V) / this.ll) >= 0.0D) {
/* 196 */           int K = (int)X;
/* 197 */           V *= (U - this.p2) * this.ll;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*     */         int K;
/* 200 */         if ((K = (int)(this.xr - Math.log(V) / this.lr)) <= n) {
/* 201 */           V *= (U - this.p3) * this.lr;
/*     */           int Km;
/* 205 */           if (((Km = Math.abs(K - this.m)) <= 20) || (Km + Km + 2L >= this.ss))
/*     */           {
/* 208 */             double f = 1.0D;
/*     */             int i;
/* 209 */             if (this.m < K) {
/* 210 */               int i = this.m;
/*     */               do if (i >= K)
/*     */                   break; while (f *= (this.rc / ++i - this.pq) >= V);
/*     */             }
/*     */             else
/*     */             {
/* 215 */               for (i = K; i < this.m; ) {
/* 216 */                 if (V *= (this.rc / ++i - this.pq) > f) break;
/*     */               }
/*     */             }
/* 219 */             if (V <= f) break;
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 224 */             V = Math.log(V);
/* 225 */             double T = -Km * Km / (this.ss + this.ss);
/* 226 */             double E = Km / this.ss * ((Km * (Km * 0.3333333333333333D + 0.625D) + 0.1666666666666667D) / this.ss + 0.5D);
/* 227 */             if (V > T - E)
/* 228 */               if (V <= T + E) {
/* 229 */                 if ((n != this.n_prev) || (this.par != this.p_prev)) {
/* 230 */                   this.n_prev = n;
/* 231 */                   this.p_prev = this.par;
/*     */ 
/* 233 */                   this.nm = (n - this.m + 1);
/* 234 */                   this.ch = (this.xm * Math.log((this.m + 1.0D) / (this.pq * this.nm)) + Arithmetic.stirlingCorrection(this.m + 1) + Arithmetic.stirlingCorrection(this.nm));
/*     */                 }
/*     */ 
/* 237 */                 int nK = n - K + 1;
/*     */ 
/* 241 */                 if (V <= this.ch + (n + 1.0D) * Math.log(this.nm / nK) + (K + 0.5D) * Math.log(nK * this.pq / (K + 1.0D)) - Arithmetic.stirlingCorrection(K + 1) - Arithmetic.stirlingCorrection(nK))
/*     */                 {
/* 243 */                   break;
/*     */                 }
/*     */               } 
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 247 */     return p > 0.5D ? n - K : K;
/*     */   }
/*     */ 
/*     */   public int nextInt()
/*     */   {
/* 253 */     return generateBinomial(this.n, this.p);
/*     */   }
/*     */ 
/*     */   public int nextInt(int n, double p)
/*     */   {
/* 262 */     if (n * Math.min(p, 1.0D - p) <= 0.0D) throw new IllegalArgumentException();
/* 263 */     return generateBinomial(n, p);
/*     */   }
/*     */ 
/*     */   public double pdf(int k)
/*     */   {
/* 269 */     if (k < 0) throw new IllegalArgumentException();
/* 270 */     int r = this.n - k;
/* 271 */     return Math.exp(this.log_n - Arithmetic.logFactorial(k) - Arithmetic.logFactorial(r) + this.log_p * k + this.log_q * r);
/*     */   }
/*     */ 
/*     */   public void setNandP(int n, double p)
/*     */   {
/* 280 */     if (n * Math.min(p, 1.0D - p) <= 0.0D) throw new IllegalArgumentException();
/* 281 */     this.n = n;
/* 282 */     this.p = p;
/*     */ 
/* 284 */     this.log_p = Math.log(p);
/* 285 */     this.log_q = Math.log(1.0D - p);
/* 286 */     this.log_n = Arithmetic.logFactorial(n);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 292 */     return getClass().getName() + "(" + this.n + "," + this.p + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Binomial
 * JD-Core Version:    0.6.2
 */