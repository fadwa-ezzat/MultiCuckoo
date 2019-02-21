/*     */ package sim.app.pso;
/*     */ 
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.MutableDouble2D;
/*     */ 
/*     */ public class Particle
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  19 */   double bestVal = 0.0D;
/*  20 */   MutableDouble2D bestPosition = new MutableDouble2D();
/*     */ 
/*  22 */   MutableDouble2D position = new MutableDouble2D();
/*  23 */   MutableDouble2D velocity = new MutableDouble2D();
/*     */   PSO pso;
/*     */   Evaluatable fitnessFunction;
/*     */   int index;
/*     */ 
/*     */   public Particle()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Particle(double x, double y, double vx, double vy, PSO pso, Evaluatable f, int index)
/*     */   {
/*  38 */     this.position.setTo(x, y);
/*  39 */     this.velocity.setTo(vx, vy);
/*     */ 
/*  41 */     this.pso = pso;
/*  42 */     this.fitnessFunction = f;
/*  43 */     pso.space.setObjectLocation(this, new Double2D(this.position));
/*  44 */     this.index = index;
/*     */   }
/*     */ 
/*     */   public void updateBest(double currVal, double currX, double currY)
/*     */   {
/*  49 */     if (currVal > this.bestVal)
/*     */     {
/*  51 */       this.bestVal = currVal;
/*  52 */       this.bestPosition.setTo(currX, currY);
/*     */ 
/*  54 */       this.pso.updateBest(currVal, currX, currY);
/*     */     }
/*     */   }
/*     */ 
/*     */   public double getFitness()
/*     */   {
/*  60 */     return this.fitnessFunction.calcFitness(this.position.x, this.position.y);
/*     */   }
/*     */ 
/*     */   public void stepUpdateFitness()
/*     */   {
/*  65 */     updateBest(getFitness(), this.position.x, this.position.y);
/*     */   }
/*     */ 
/*     */   public void stepUpdateVelocity()
/*     */   {
/*  70 */     double x = this.position.x;
/*  71 */     double y = this.position.y;
/*     */ 
/*  73 */     MutableDouble2D nBestPos = new MutableDouble2D();
/*  74 */     this.pso.getNeighborhoodBest(this.index, nBestPos);
/*     */ 
/*  78 */     double inertia = this.velocity.x;
/*  79 */     double pDelta = this.bestPosition.x - x;
/*  80 */     double nDelta = nBestPos.x - x;
/*  81 */     double gDelta = this.pso.bestPosition.x - x;
/*  82 */     double pWeight = Math.random() + 0.4D;
/*  83 */     double nWeight = Math.random() + 0.4D;
/*  84 */     double gWeight = Math.random() + 0.4D;
/*  85 */     double vx = (0.9D * inertia + pWeight * pDelta + nWeight * nDelta + gWeight * gDelta) / (1.0D + pWeight + nWeight + gWeight);
/*     */ 
/*  88 */     inertia = this.velocity.y;
/*  89 */     pDelta = this.bestPosition.y - y;
/*  90 */     nDelta = nBestPos.y - y;
/*  91 */     gDelta = this.pso.bestPosition.y - y;
/*  92 */     pWeight = Math.random() + 0.4D;
/*  93 */     nWeight = Math.random() + 0.4D;
/*  94 */     gWeight = Math.random() + 0.4D;
/*  95 */     double vy = (0.9D * inertia + pWeight * pDelta + nWeight * nDelta + gWeight * gDelta) / (1.0D + pWeight + nWeight + gWeight);
/*     */ 
/*  97 */     vx *= this.pso.velocityScalar;
/*  98 */     vy *= this.pso.velocityScalar;
/*     */ 
/* 101 */     this.velocity.setTo(vx, vy);
/*     */   }
/*     */ 
/*     */   public void stepUpdatePosition()
/*     */   {
/* 108 */     this.position.addIn(this.velocity);
/* 109 */     this.pso.space.setObjectLocation(this, new Double2D(this.position));
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pso.Particle
 * JD-Core Version:    0.6.2
 */