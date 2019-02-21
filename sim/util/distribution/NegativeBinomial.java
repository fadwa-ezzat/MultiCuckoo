/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class NegativeBinomial extends AbstractDiscreteDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected int n;
/*     */   protected double p;
/*     */   protected Gamma gamma;
/*     */   protected Poisson poisson;
/*     */ 
/*     */   public NegativeBinomial(int n, double p, MersenneTwisterFast randomGenerator)
/*     */   {
/*  46 */     setRandomGenerator(randomGenerator);
/*  47 */     setNandP(n, p);
/*  48 */     this.gamma = new Gamma(n, 1.0D, randomGenerator);
/*  49 */     this.poisson = new Poisson(0.0D, randomGenerator);
/*     */   }
/*     */ 
/*     */   public double cdf(int k)
/*     */   {
/*  55 */     return Probability.negativeBinomial(k, this.n, this.p);
/*     */   }
/*     */ 
/*     */   public int nextInt()
/*     */   {
/*  78 */     return nextInt(this.n, this.p);
/*     */   }
/*     */ 
/*     */   public int nextInt(int n, double p)
/*     */   {
/* 108 */     double x = p / (1.0D - p);
/*     */ 
/* 110 */     double y = x * this.gamma.nextDouble(n, 1.0D);
/* 111 */     return this.poisson.nextInt(y);
/*     */   }
/*     */ 
/*     */   public double pdf(int k)
/*     */   {
/* 117 */     if (k > this.n) throw new IllegalArgumentException();
/* 118 */     return Arithmetic.binomial(this.n, k) * Math.pow(this.p, k) * Math.pow(1.0D - this.p, this.n - k);
/*     */   }
/*     */ 
/*     */   public void setNandP(int n, double p)
/*     */   {
/* 126 */     this.n = n;
/* 127 */     this.p = p;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 133 */     return getClass().getName() + "(" + this.n + "," + this.p + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.NegativeBinomial
 * JD-Core Version:    0.6.2
 */