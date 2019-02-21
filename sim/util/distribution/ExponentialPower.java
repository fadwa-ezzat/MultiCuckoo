/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class ExponentialPower extends AbstractContinousDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected double tau;
/*     */   private double s;
/*     */   private double sm1;
/*  38 */   private double tau_set = -1.0D;
/*     */ 
/*     */   public ExponentialPower(double tau, MersenneTwisterFast randomGenerator)
/*     */   {
/*  46 */     setRandomGenerator(randomGenerator);
/*  47 */     setState(tau);
/*     */   }
/*     */ 
/*     */   public double nextDouble()
/*     */   {
/*  53 */     return nextDouble(this.tau);
/*     */   }
/*     */ 
/*     */   public double nextDouble(double tau)
/*     */   {
/*  62 */     if (tau != this.tau_set) {
/*  63 */       this.s = (1.0D / tau);
/*  64 */       this.sm1 = (1.0D - this.s);
/*     */ 
/*  66 */       this.tau_set = tau; } double u;
/*     */     double v;
/*     */     double x;
/*     */     do { u = this.randomGenerator.nextDouble();
/*  72 */       u = 2.0D * u - 1.0D;
/*  73 */       double u1 = Math.abs(u);
/*  74 */       v = this.randomGenerator.nextDouble();
/*     */       double x;
/*  76 */       if (u1 <= this.sm1) {
/*  77 */         x = u1;
/*     */       }
/*     */       else {
/*  80 */         double y = tau * (1.0D - u1);
/*  81 */         x = this.sm1 - this.s * Math.log(y);
/*  82 */         v *= y;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  87 */     while (Math.log(v) > -Math.exp(Math.log(x) * tau));
/*     */ 
/*  90 */     if (u < 0.0D) {
/*  91 */       return x;
/*     */     }
/*  93 */     return -x;
/*     */   }
/*     */ 
/*     */   public void setState(double tau)
/*     */   {
/* 100 */     if (tau < 1.0D) throw new IllegalArgumentException();
/* 101 */     this.tau = tau;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 107 */     return getClass().getName() + "(" + this.tau + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.ExponentialPower
 * JD-Core Version:    0.6.2
 */