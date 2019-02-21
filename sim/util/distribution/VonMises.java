/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class VonMises extends AbstractContinousDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected double my_k;
/*  39 */   private double k_set = -1.0D;
/*     */   private double tau;
/*     */   private double rho;
/*     */   private double r;
/*     */ 
/*     */   public VonMises(double freedom, MersenneTwisterFast randomGenerator)
/*     */   {
/*  48 */     setRandomGenerator(randomGenerator);
/*  49 */     setState(freedom);
/*     */   }
/*     */ 
/*     */   public double nextDouble()
/*     */   {
/*  55 */     return nextDouble(this.my_k);
/*     */   }
/*     */ 
/*     */   public double nextDouble(double k)
/*     */   {
/*  82 */     if (k <= 0.0D) throw new IllegalArgumentException();
/*     */ 
/*  84 */     if (this.k_set != k) {
/*  85 */       this.tau = (1.0D + Math.sqrt(1.0D + 4.0D * k * k));
/*  86 */       this.rho = ((this.tau - Math.sqrt(2.0D * this.tau)) / (2.0D * k));
/*  87 */       this.r = ((1.0D + this.rho * this.rho) / (2.0D * this.rho));
/*  88 */       this.k_set = k; } double v;
/*     */     double w;
/*     */     double c;
/*     */     do { double u = this.randomGenerator.nextDouble();
/*  94 */       v = this.randomGenerator.nextDouble();
/*  95 */       double z = Math.cos(3.141592653589793D * u);
/*  96 */       w = (1.0D + this.r * z) / (this.r + z);
/*  97 */       c = k * (this.r - w); }
/*  98 */     while ((c * (2.0D - c) < v) && (Math.log(c / v) + 1.0D < c));
/*     */ 
/* 100 */     return this.randomGenerator.nextDouble() > 0.5D ? Math.acos(w) : -Math.acos(w);
/*     */   }
/*     */ 
/*     */   public void setState(double k)
/*     */   {
/* 108 */     if (k <= 0.0D) throw new IllegalArgumentException();
/* 109 */     this.my_k = k;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 115 */     return getClass().getName() + "(" + this.my_k + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.VonMises
 * JD-Core Version:    0.6.2
 */