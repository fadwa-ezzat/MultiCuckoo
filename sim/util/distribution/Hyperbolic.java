/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class Hyperbolic extends AbstractContinousDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected double alpha;
/*     */   protected double beta;
/*  39 */   protected double a_setup = 0.0D; protected double b_setup = -1.0D;
/*     */   protected double x;
/*     */   protected double u;
/*     */   protected double v;
/*     */   protected double e;
/*     */   protected double hr;
/*     */   protected double hl;
/*     */   protected double s;
/*     */   protected double pm;
/*     */   protected double pr;
/*     */   protected double samb;
/*     */   protected double pmr;
/*     */   protected double mpa_1;
/*     */   protected double mmb_1;
/*     */ 
/*     */   public Hyperbolic(double alpha, double beta, MersenneTwisterFast randomGenerator)
/*     */   {
/*  48 */     setRandomGenerator(randomGenerator);
/*  49 */     setState(alpha, beta);
/*     */   }
/*     */ 
/*     */   public double nextDouble()
/*     */   {
/*  55 */     return nextDouble(this.alpha, this.beta);
/*     */   }
/*     */ 
/*     */   public double nextDouble(double alpha, double beta)
/*     */   {
/*  78 */     double a = alpha;
/*  79 */     double b = beta;
/*     */ 
/*  81 */     if ((this.a_setup != a) || (this.b_setup != b))
/*     */     {
/*  86 */       double amb = a * a - b * b;
/*  87 */       this.samb = Math.sqrt(amb);
/*  88 */       double mode = b / this.samb;
/*  89 */       double help_1 = a * Math.sqrt(2.0D * this.samb + 1.0D);
/*  90 */       double help_2 = b * (this.samb + 1.0D);
/*  91 */       double mpa = (help_2 + help_1) / amb;
/*  92 */       double mmb = (help_2 - help_1) / amb;
/*  93 */       double a_ = mpa - mode;
/*  94 */       double b_ = -mmb + mode;
/*  95 */       this.hr = (-1.0D / (-a * mpa / Math.sqrt(1.0D + mpa * mpa) + b));
/*  96 */       this.hl = (1.0D / (-a * mmb / Math.sqrt(1.0D + mmb * mmb) + b));
/*  97 */       double a_1 = a_ - this.hr;
/*  98 */       double b_1 = b_ - this.hl;
/*  99 */       this.mmb_1 = (mode - b_1);
/* 100 */       this.mpa_1 = (mode + a_1);
/*     */ 
/* 102 */       this.s = (a_ + b_);
/* 103 */       this.pm = ((a_1 + b_1) / this.s);
/* 104 */       this.pr = (this.hr / this.s);
/* 105 */       this.pmr = (this.pm + this.pr);
/*     */ 
/* 107 */       this.a_setup = a;
/* 108 */       this.b_setup = b;
/*     */     }
/*     */ 
/*     */     while (true)
/*     */     {
/* 113 */       this.u = this.randomGenerator.nextDouble();
/* 114 */       this.v = this.randomGenerator.nextDouble();
/* 115 */       if (this.u <= this.pm)
/*     */       {
/* 117 */         this.x = (this.mmb_1 + this.u * this.s);
/* 118 */         if (Math.log(this.v) <= -a * Math.sqrt(1.0D + this.x * this.x) + b * this.x + this.samb) break;
/*     */ 
/*     */       }
/* 121 */       else if (this.u <= this.pmr)
/*     */       {
/* 123 */         this.e = (-Math.log((this.u - this.pm) / this.pr));
/* 124 */         this.x = (this.mpa_1 + this.hr * this.e);
/* 125 */         if (Math.log(this.v) - this.e <= -a * Math.sqrt(1.0D + this.x * this.x) + b * this.x + this.samb) break;
/*     */       }
/*     */       else
/*     */       {
/* 129 */         this.e = Math.log((this.u - this.pmr) / (1.0D - this.pmr));
/* 130 */         this.x = (this.mmb_1 + this.hl * this.e);
/* 131 */         if (Math.log(this.v) + this.e <= -a * Math.sqrt(1.0D + this.x * this.x) + b * this.x + this.samb) break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 136 */     return this.x;
/*     */   }
/*     */ 
/*     */   public void setState(double alpha, double beta)
/*     */   {
/* 142 */     this.alpha = alpha;
/* 143 */     this.beta = beta;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 149 */     return getClass().getName() + "(" + this.alpha + "," + this.beta + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Hyperbolic
 * JD-Core Version:    0.6.2
 */