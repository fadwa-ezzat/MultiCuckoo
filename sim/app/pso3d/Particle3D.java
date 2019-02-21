/*     */ package sim.app.pso3d;
/*     */ 
/*     */ import sim.field.continuous.Continuous3D;
/*     */ import sim.util.Double3D;
/*     */ import sim.util.MutableDouble3D;
/*     */ 
/*     */ public class Particle3D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  20 */   double bestVal = 0.0D;
/*  21 */   MutableDouble3D bestPosition = new MutableDouble3D();
/*     */ 
/*  23 */   MutableDouble3D position = new MutableDouble3D();
/*  24 */   MutableDouble3D velocity = new MutableDouble3D();
/*     */   private PSO3D pso;
/*     */   private Evaluatable3D fitnessFunction;
/*     */   private int index;
/*     */ 
/*     */   public Particle3D()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Particle3D(double x, double y, double z, double vx, double vy, double vz, PSO3D pso, Evaluatable3D f, int index)
/*     */   {
/*  39 */     this.position.setTo(x, y, z);
/*  40 */     this.velocity.setTo(vx, vy, vz);
/*     */ 
/*  42 */     this.pso = pso;
/*  43 */     this.fitnessFunction = f;
/*  44 */     pso.space.setObjectLocation(this, new Double3D(this.position));
/*  45 */     this.index = index;
/*     */   }
/*     */ 
/*     */   public void updateBest(double currVal, double currX, double currY, double currZ)
/*     */   {
/*  50 */     if (currVal > this.bestVal)
/*     */     {
/*  52 */       this.bestVal = currVal;
/*  53 */       this.bestPosition.setTo(currX, currY, currZ);
/*     */ 
/*  55 */       this.pso.updateBest(currVal, currX, currY, currZ);
/*     */     }
/*     */   }
/*     */ 
/*     */   public double getFitness()
/*     */   {
/*  61 */     return this.fitnessFunction.calcFitness(this.position.x, this.position.y, this.position.z);
/*     */   }
/*     */ 
/*     */   public void stepUpdateFitness()
/*     */   {
/*  66 */     updateBest(getFitness(), this.position.x, this.position.y, this.position.z);
/*     */   }
/*     */ 
/*     */   public void stepUpdateVelocity()
/*     */   {
/*  71 */     double x = this.position.x;
/*  72 */     double y = this.position.y;
/*  73 */     double z = this.position.z;
/*     */ 
/*  75 */     MutableDouble3D nBestPosition = new MutableDouble3D();
/*  76 */     this.pso.getNeighborhoodBest(this.index, nBestPosition);
/*     */ 
/*  80 */     double inertia = this.velocity.x;
/*  81 */     double pDelta = this.bestPosition.x - x;
/*  82 */     double nDelta = nBestPosition.x - x;
/*  83 */     double gDelta = this.pso.bestPosition.x - x;
/*  84 */     double pWeight = Math.random() + 0.4D;
/*  85 */     double nWeight = Math.random() + 0.4D;
/*  86 */     double gWeight = Math.random() + 0.4D;
/*  87 */     double vx = (0.9D * inertia + pWeight * pDelta + nWeight * nDelta + gWeight * gDelta) / (1.0D + pWeight + nWeight + gWeight);
/*     */ 
/*  90 */     inertia = this.velocity.y;
/*  91 */     pDelta = this.bestPosition.y - y;
/*  92 */     nDelta = nBestPosition.y - y;
/*  93 */     gDelta = this.pso.bestPosition.y - y;
/*  94 */     pWeight = Math.random() + 0.4D;
/*  95 */     nWeight = Math.random() + 0.4D;
/*  96 */     gWeight = Math.random() + 0.4D;
/*  97 */     double vy = (0.9D * inertia + pWeight * pDelta + nWeight * nDelta + gWeight * gDelta) / (1.0D + pWeight + nWeight + gWeight);
/*     */ 
/* 100 */     inertia = this.velocity.z;
/* 101 */     pDelta = this.bestPosition.z - z;
/* 102 */     nDelta = nBestPosition.z - z;
/* 103 */     gDelta = this.pso.bestPosition.z - z;
/* 104 */     pWeight = Math.random() + 0.4D;
/* 105 */     nWeight = Math.random() + 0.4D;
/* 106 */     gWeight = Math.random() + 0.4D;
/* 107 */     double vz = (0.9D * inertia + pWeight * pDelta + nWeight * nDelta + gWeight * gDelta) / (1.0D + pWeight + nWeight + gWeight);
/*     */ 
/* 109 */     vx *= this.pso.velocityScalar;
/* 110 */     vy *= this.pso.velocityScalar;
/* 111 */     vz *= this.pso.velocityScalar;
/*     */ 
/* 114 */     this.velocity.setTo(vx, vy, vz);
/*     */   }
/*     */ 
/*     */   public void stepUpdatePosition()
/*     */   {
/* 119 */     this.position.addIn(this.velocity);
/* 120 */     this.pso.space.setObjectLocation(this, new Double3D(this.position));
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pso3d.Particle3D
 * JD-Core Version:    0.6.2
 */