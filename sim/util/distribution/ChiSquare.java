/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class ChiSquare extends AbstractContinousDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected double freedom;
/*  43 */   private double freedom_in = -1.0D;
/*     */   private double b;
/*     */   private double vm;
/*     */   private double vp;
/*     */   private double vd;
/*     */ 
/*     */   public ChiSquare(double freedom, MersenneTwisterFast randomGenerator)
/*     */   {
/*  52 */     setRandomGenerator(randomGenerator);
/*  53 */     setState(freedom);
/*     */   }
/*     */ 
/*     */   public double cdf(double x)
/*     */   {
/*  59 */     return Probability.chiSquare(this.freedom, x);
/*     */   }
/*     */ 
/*     */   public double nextDouble()
/*     */   {
/*  65 */     return nextDouble(this.freedom);
/*     */   }
/*     */ 
/*     */   public double nextDouble(double freedom)
/*     */   {
/*  94 */     if (freedom == 1.0D) { double u;
/*     */       double z;
/*     */       double zz;
/*     */       do { do { u = this.randomGenerator.nextDouble();
/*  97 */           double v = this.randomGenerator.nextDouble() * 0.857763884960707D;
/*  98 */           z = v / u; }
/*  99 */         while (z < 0.0D);
/* 100 */         zz = z * z;
/* 101 */         double r = 2.5D - zz;
/* 102 */         if (z < 0.0D) r += zz * z / (3.0D * z);
/* 103 */         if (u < r * 0.3894003915D) return z * z; 
/*     */       }
/* 104 */       while ((zz > 1.036961043D / u + 1.4D) || 
/* 105 */         (2.0D * Math.log(u) >= -zz * 0.5D)); return z * z;
/*     */     }
/*     */ 
/* 109 */     if (freedom != this.freedom_in) {
/* 110 */       this.b = Math.sqrt(freedom - 1.0D);
/* 111 */       this.vm = (-0.6065306597D * (1.0D - 0.25D / (this.b * this.b + 1.0D)));
/* 112 */       this.vm = (-this.b > this.vm ? -this.b : this.vm);
/* 113 */       this.vp = (0.6065306597D * (0.7071067812D + this.b) / (0.5D + this.b));
/* 114 */       this.vd = (this.vp - this.vm);
/* 115 */       this.freedom_in = freedom; } double u;
/*     */     double z;
/*     */     double zz;
/*     */     do { do { u = this.randomGenerator.nextDouble();
/* 119 */         double v = this.randomGenerator.nextDouble() * this.vd + this.vm;
/* 120 */         z = v / u; }
/* 121 */       while (z < -this.b);
/* 122 */       zz = z * z;
/* 123 */       double r = 2.5D - zz;
/* 124 */       if (z < 0.0D) r += zz * z / (3.0D * (z + this.b));
/* 125 */       if (u < r * 0.3894003915D) return (z + this.b) * (z + this.b); 
/*     */     }
/* 126 */     while ((zz > 1.036961043D / u + 1.4D) || 
/* 127 */       (2.0D * Math.log(u) >= Math.log(1.0D + z / this.b) * this.b * this.b - zz * 0.5D - z * this.b)); return (z + this.b) * (z + this.b);
/*     */   }
/*     */ 
/*     */   public double pdf(double x)
/*     */   {
/* 135 */     if (x <= 0.0D) throw new IllegalArgumentException();
/* 136 */     double logGamma = Fun.logGamma(this.freedom / 2.0D);
/* 137 */     return Math.exp((this.freedom / 2.0D - 1.0D) * Math.log(x / 2.0D) - x / 2.0D - logGamma) / 2.0D;
/*     */   }
/*     */ 
/*     */   public void setState(double freedom)
/*     */   {
/* 145 */     if (freedom < 1.0D) throw new IllegalArgumentException();
/* 146 */     this.freedom = freedom;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 152 */     return getClass().getName() + "(" + this.freedom + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.ChiSquare
 * JD-Core Version:    0.6.2
 */