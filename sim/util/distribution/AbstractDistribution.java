/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class AbstractDistribution
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected MersenneTwisterFast randomGenerator;
/*     */ 
/*     */   public double apply(double dummy)
/*     */   {
/*  59 */     return nextDouble();
/*     */   }
/*     */ 
/*     */   public int apply(int dummy)
/*     */   {
/*  66 */     return nextInt();
/*     */   }
/*     */ 
/*     */   protected MersenneTwisterFast getRandomGenerator()
/*     */   {
/*  87 */     return this.randomGenerator;
/*     */   }
/*     */ 
/*     */   public abstract double nextDouble();
/*     */ 
/*     */   public int nextInt()
/*     */   {
/*  98 */     return (int)Math.round(nextDouble());
/*     */   }
/*     */ 
/*     */   protected void setRandomGenerator(MersenneTwisterFast randomGenerator)
/*     */   {
/* 104 */     this.randomGenerator = randomGenerator;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.AbstractDistribution
 * JD-Core Version:    0.6.2
 */