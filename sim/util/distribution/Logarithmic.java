/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class Logarithmic extends AbstractContinousDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected double my_p;
/*     */   private double t;
/*     */   private double h;
/*  39 */   private double a_prev = -1.0D;
/*     */ 
/*     */   public Logarithmic(double p, MersenneTwisterFast randomGenerator)
/*     */   {
/*  45 */     setRandomGenerator(randomGenerator);
/*  46 */     setState(p);
/*     */   }
/*     */ 
/*     */   public double nextDouble()
/*     */   {
/*  52 */     return nextDouble(this.my_p);
/*     */   }
/*     */ 
/*     */   public double nextDouble(double a)
/*     */   {
/*  94 */     if (a != this.a_prev) {
/*  95 */       this.a_prev = a;
/*  96 */       if (a < 0.97D) this.t = (-a / Math.log(1.0D - a)); else {
/*  97 */         this.h = Math.log(1.0D - a);
/*     */       }
/*     */     }
/* 100 */     double u = this.randomGenerator.nextDouble();
/* 101 */     if (a < 0.97D) {
/* 102 */       int k = 1;
/* 103 */       double p = this.t;
/* 104 */       while (u > p) {
/* 105 */         u -= p;
/* 106 */         k++;
/* 107 */         p *= a * (k - 1.0D) / k;
/*     */       }
/* 109 */       return k;
/*     */     }
/*     */ 
/* 112 */     if (u > a) return 1.0D;
/* 113 */     u = this.randomGenerator.nextDouble();
/* 114 */     double v = u;
/* 115 */     double q = 1.0D - Math.exp(v * this.h);
/* 116 */     if (u <= q * q) {
/* 117 */       int k = (int)(1.0D + Math.log(u) / Math.log(q));
/* 118 */       return k;
/*     */     }
/* 120 */     if (u > q) return 1.0D;
/* 121 */     return 2.0D;
/*     */   }
/*     */ 
/*     */   public void setState(double p)
/*     */   {
/* 127 */     this.my_p = p;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 133 */     return getClass().getName() + "(" + this.my_p + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Logarithmic
 * JD-Core Version:    0.6.2
 */