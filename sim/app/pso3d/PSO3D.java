/*     */ package sim.app.pso3d;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous3D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.MutableDouble3D;
/*     */ 
/*     */ public class PSO3D extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public Continuous3D space;
/*  25 */   public double width = 10.24D;
/*  26 */   public double height = 10.24D;
/*  27 */   public double length = 10.24D;
/*     */   public Particle3D[] particles;
/*  29 */   private int previousSuccessCount = -1;
/*     */ 
/*  32 */   public int numParticles = 1000;
/*     */ 
/*  36 */   public int neighborhoodSize = 10;
/*     */ 
/*  40 */   public double initialVelocityRange = 1.0D;
/*     */ 
/*  44 */   public double velocityScalar = 2.7D;
/*     */ 
/*  48 */   public int fitnessFunction = 0;
/*     */ 
/*  68 */   public double[] fitnessFunctionLowerBound = { 950.0D, 998.0D, 200.0D };
/*     */ 
/*  75 */   public double successThreshold = 1.0E-008D;
/*     */ 
/*  79 */   public double bestVal = 0.0D;
/*  80 */   MutableDouble3D bestPosition = new MutableDouble3D();
/*     */ 
/*     */   public int getNumParticles()
/*     */   {
/*  33 */     return this.numParticles; } 
/*  34 */   public void setNumParticles(int val) { if (val >= 0) this.numParticles = val;  } 
/*     */   public int getNeighborhoodSize()
/*     */   {
/*  37 */     return this.neighborhoodSize; } 
/*  38 */   public void setNeighborhoodSize(int val) { if ((val >= 0) && (val <= this.numParticles)) this.neighborhoodSize = val;  } 
/*     */   public double getInitialVelocityRange()
/*     */   {
/*  41 */     return this.initialVelocityRange; } 
/*  42 */   public void setInitialVelocityRange(double val) { if (val >= 0.0D) this.initialVelocityRange = val;  } 
/*     */   public double getVelocityScalar()
/*     */   {
/*  45 */     return this.velocityScalar; } 
/*  46 */   public void setVelocityScalar(double val) { if (val >= 0.0D) this.velocityScalar = val;  } 
/*     */   public int getFitnessFunction()
/*     */   {
/*  49 */     return this.fitnessFunction; } 
/*  50 */   public void setFitnessFunction(int val) { this.fitnessFunction = val; }
/*     */ 
/*     */   public Object domFitnessFunction() {
/*  53 */     return new String[] { "Rastrigin", "Griewangk", "Rosenbrock" };
/*     */   }
/*     */ 
/*     */   private Evaluatable3D mapFitnessFunction(int val)
/*     */   {
/*  58 */     switch (val) {
/*     */     case 0:
/*  60 */       return new Rastrigin3D();
/*     */     case 1:
/*  61 */       return new Griewangk3D();
/*     */     case 2:
/*  62 */       return new Rosenbrock3D();
/*     */     }
/*     */ 
/*  65 */     return new Rastrigin3D();
/*     */   }
/*     */ 
/*     */   public double getSuccessThreshold()
/*     */   {
/*  76 */     return this.successThreshold; } 
/*  77 */   public void setSuccessThreshold(double val) { if (val >= 0.0D) this.successThreshold = val;
/*     */   }
/*     */ 
/*     */   public PSO3D(long seed)
/*     */   {
/*  84 */     super(seed);
/*     */   }
/*     */ 
/*     */   public void updateBest(double currVal, double currX, double currY, double currZ)
/*     */   {
/*  89 */     if (currVal > this.bestVal)
/*     */     {
/*  91 */       this.bestVal = currVal;
/*  92 */       this.bestPosition.setTo(currX, currY, currZ);
/*     */     }
/*     */   }
/*     */ 
/*     */   public double getNeighborhoodBest(int index, MutableDouble3D pos) {
/*  97 */     double bv = (-1.0D / 0.0D);
/*     */ 
/* 100 */     int start = index - this.neighborhoodSize / 2;
/* 101 */     if (start < 0) {
/* 102 */       start += this.numParticles;
/*     */     }
/* 104 */     for (int i = 0; i < this.neighborhoodSize; i++)
/*     */     {
/* 106 */       Particle3D p = this.particles[((start + i) % this.numParticles)];
/* 107 */       if (p.bestVal > bv)
/*     */       {
/* 109 */         bv = p.bestVal;
/* 110 */         pos.setTo(p.bestPosition);
/*     */       }
/*     */     }
/* 113 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/* 119 */     this.bestVal = 0.0D;
/*     */ 
/* 121 */     super.start();
/* 122 */     this.particles = new Particle3D[this.numParticles];
/* 123 */     this.space = new Continuous3D(1.0D, this.length, this.width, this.height);
/* 124 */     Evaluatable3D f = mapFitnessFunction(this.fitnessFunction);
/*     */ 
/* 126 */     for (int i = 0; i < this.numParticles; i++)
/*     */     {
/* 128 */       double x = this.random.nextDouble() * this.width - this.width * 0.5D;
/* 129 */       double y = this.random.nextDouble() * this.height - this.height * 0.5D;
/* 130 */       double z = this.random.nextDouble() * this.height - this.height * 0.5D;
/* 131 */       double vx = this.random.nextDouble() * this.initialVelocityRange - this.initialVelocityRange * 0.5D;
/* 132 */       double vy = this.random.nextDouble() * this.initialVelocityRange - this.initialVelocityRange * 0.5D;
/* 133 */       double vz = this.random.nextDouble() * this.initialVelocityRange - this.initialVelocityRange * 0.5D;
/*     */ 
/* 135 */       final Particle3D p = new Particle3D(x, y, z, vx, vy, vz, this, f, i);
/* 136 */       this.particles[i] = p;
/*     */ 
/* 138 */       this.schedule.scheduleRepeating(0.0D, 1, new Steppable() {
/*     */         public void step(SimState state) {
/* 140 */           p.stepUpdateFitness();
/*     */         }
/*     */       });
/* 143 */       this.schedule.scheduleRepeating(0.0D, 2, new Steppable() {
/*     */         public void step(SimState state) {
/* 145 */           p.stepUpdateVelocity();
/*     */         }
/*     */       });
/* 148 */       this.schedule.scheduleRepeating(0.0D, 3, new Steppable() {
/*     */         public void step(SimState state) {
/* 150 */           p.stepUpdatePosition();
/*     */         }
/*     */       });
/*     */     }
/* 154 */     this.schedule.scheduleRepeating(0.0D, 4, new Steppable()
/*     */     {
/*     */       public void step(SimState state)
/*     */       {
/* 158 */         int successCount = 0;
/* 159 */         for (int i = 0; i < PSO3D.this.space.allObjects.numObjs; i++)
/*     */         {
/* 161 */           Particle3D p = (Particle3D)PSO3D.this.space.allObjects.get(i);
/*     */ 
/* 163 */           if (Math.abs(p.getFitness() - 1000.0D) <= PSO3D.this.successThreshold)
/* 164 */             successCount++;
/*     */         }
/* 166 */         if (successCount != PSO3D.this.previousSuccessCount)
/*     */         {
/* 169 */           PSO3D.this.previousSuccessCount = successCount;
/*     */         }
/* 171 */         if (successCount == PSO3D.this.numParticles)
/* 172 */           state.kill();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 179 */     doLoop(PSO3D.class, args);
/* 180 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pso3d.PSO3D
 * JD-Core Version:    0.6.2
 */