/*     */ package sim.util.distribution;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ 
/*     */ public class Uniform extends AbstractContinousDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected double min;
/*     */   protected double max;
/*     */ 
/*     */   public Uniform(double min, double max, MersenneTwisterFast randomGenerator)
/*     */   {
/*  33 */     setRandomGenerator(randomGenerator);
/*  34 */     setState(min, max);
/*     */   }
/*     */ 
/*     */   public Uniform(MersenneTwisterFast randomGenerator)
/*     */   {
/*  40 */     this(0.0D, 1.0D, randomGenerator);
/*     */   }
/*     */ 
/*     */   public double cdf(double x)
/*     */   {
/*  46 */     if (x <= this.min) return 0.0D;
/*  47 */     if (x >= this.max) return 1.0D;
/*  48 */     return (x - this.min) / (this.max - this.min);
/*     */   }
/*     */ 
/*     */   public boolean nextBoolean()
/*     */   {
/*  54 */     return this.randomGenerator.nextDouble() > 0.5D;
/*     */   }
/*     */ 
/*     */   public double nextDouble()
/*     */   {
/*  60 */     return this.min + (this.max - this.min) * this.randomGenerator.nextDouble();
/*     */   }
/*     */ 
/*     */   public double nextDoubleFromTo(double from, double to)
/*     */   {
/*  67 */     return from + (to - from) * this.randomGenerator.nextDouble();
/*     */   }
/*     */ 
/*     */   public float nextFloatFromTo(float from, float to)
/*     */   {
/*  74 */     return (float)nextDoubleFromTo(from, to);
/*     */   }
/*     */ 
/*     */   public int nextInt()
/*     */   {
/*  80 */     return nextIntFromTo((int)Math.round(this.min), (int)Math.round(this.max));
/*     */   }
/*     */ 
/*     */   public int nextIntFromTo(int from, int to)
/*     */   {
/*  87 */     return (int)(from + ()((1L + to - from) * this.randomGenerator.nextDouble()));
/*     */   }
/*     */ 
/*     */   public long nextLongFromTo(long from, long to)
/*     */   {
/* 104 */     if ((from >= 0L) && (to < 9223372036854775807L)) {
/* 105 */       return from + ()nextDoubleFromTo(0.0D, to - from + 1L);
/*     */     }
/*     */ 
/* 110 */     double diff = to - from + 1.0D;
/* 111 */     if (diff <= 9.223372036854776E+018D)
/* 112 */       return from + ()nextDoubleFromTo(0.0D, diff);
/*     */     long random;
/* 118 */     if (from == -9223372036854775808L) {
/* 119 */       if (to == 9223372036854775807L)
/*     */       {
/* 121 */         int i1 = nextIntFromTo(-2147483648, 2147483647);
/* 122 */         int i2 = nextIntFromTo(-2147483648, 2147483647);
/* 123 */         return (i1 & 0xFFFFFFFF) << 32 | i2 & 0xFFFFFFFF;
/*     */       }
/* 125 */       long random = Math.round(nextDoubleFromTo(from, to + 1L));
/* 126 */       if (random > to) random = from; 
/*     */     }
/*     */     else
/*     */     {
/* 129 */       random = Math.round(nextDoubleFromTo(from - 1L, to));
/* 130 */       if (random < from) random = to;
/*     */     }
/* 132 */     return random;
/*     */   }
/*     */ 
/*     */   public double pdf(double x)
/*     */   {
/* 138 */     if ((x <= this.min) || (x >= this.max)) return 0.0D;
/* 139 */     return 1.0D / (this.max - this.min);
/*     */   }
/*     */ 
/*     */   public void setState(double min, double max)
/*     */   {
/* 145 */     if (max < min) { setState(max, min); return; }
/* 146 */     this.min = min;
/* 147 */     this.max = max;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 153 */     return getClass().getName() + "(" + this.min + "," + this.max + ")";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Uniform
 * JD-Core Version:    0.6.2
 */