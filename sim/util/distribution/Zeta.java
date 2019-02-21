/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class Zeta extends AbstractDiscreteDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected double ro;
/*     */   protected double pk;
/*     */   protected double c;
/*     */   protected double d;
/*  44 */   protected double ro_prev = -1.0D; protected double pk_prev = -1.0D;
/*  45 */   protected double maxlongint = 9.223372036854776E+018D;
/*     */ 
/*     */   public Zeta(double ro, double pk, MersenneTwisterFast randomGenerator)
/*     */   {
/*  51 */     setRandomGenerator(randomGenerator);
/*  52 */     setState(ro, pk);
/*     */   }
/*     */ 
/*     */   protected long generateZeta(double ro, double pk, MersenneTwisterFast randomGenerator)
/*     */   {
/*  99 */     if ((ro != this.ro_prev) || (pk != this.pk_prev)) {
/* 100 */       this.ro_prev = ro;
/* 101 */       this.pk_prev = pk;
/* 102 */       if (ro < pk) {
/* 103 */         this.c = (pk - 0.5D);
/* 104 */         this.d = 0.0D;
/*     */       }
/*     */       else {
/* 107 */         this.c = (ro - 0.5D);
/* 108 */         this.d = ((1.0D + ro) * Math.log((1.0D + pk) / (1.0D + ro))); }  } double x;
/*     */     long k;
/*     */     double e;
/*     */     do { double v;
/*     */       do { double u = randomGenerator.nextDouble();
/* 114 */         v = randomGenerator.nextDouble();
/* 115 */         x = (this.c + 0.5D) * Math.exp(-Math.log(u) / ro) - this.c; }
/* 116 */       while ((x <= 0.5D) || (x >= this.maxlongint));
/*     */ 
/* 118 */       k = (int)(x + 0.5D);
/* 119 */       e = -Math.log(v); }
/* 120 */     while (e < (1.0D + ro) * Math.log((k + pk) / (x + this.c)) - this.d);
/*     */ 
/* 122 */     return k;
/*     */   }
/*     */ 
/*     */   public int nextInt()
/*     */   {
/* 128 */     return (int)generateZeta(this.ro, this.pk, this.randomGenerator);
/*     */   }
/*     */ 
/*     */   public void setState(double ro, double pk)
/*     */   {
/* 134 */     this.ro = ro;
/* 135 */     this.pk = pk;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 142 */     return getClass().getName() + "(" + this.ro + "," + this.pk + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Zeta
 * JD-Core Version:    0.6.2
 */