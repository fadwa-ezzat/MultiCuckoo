/*     */ package sim.app.pso;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.MutableDouble2D;
/*     */ 
/*     */ public class PSO extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public Continuous2D space;
/*  25 */   public double width = 10.24D;
/*  26 */   public double height = 10.24D;
/*     */   public Particle[] particles;
/*  28 */   int prevSuccessCount = -1;
/*     */ 
/*  31 */   public int numParticles = 1000;
/*     */ 
/*  35 */   public int neighborhoodSize = 10;
/*     */ 
/*  39 */   public double initialVelocityRange = 1.0D;
/*     */ 
/*  43 */   public double velocityScalar = 2.7D;
/*     */ 
/*  47 */   public int fitnessFunction = 0;
/*     */ 
/*  68 */   public double[] fitnessFunctionLowerBound = { 920.0D, 950.0D, 998.0D, 200.0D };
/*     */ 
/*  76 */   public double successThreshold = 1.0E-008D;
/*     */ 
/*  80 */   public double bestVal = 0.0D;
/*  81 */   MutableDouble2D bestPosition = new MutableDouble2D();
/*     */ 
/*     */   public int getNumParticles()
/*     */   {
/*  32 */     return this.numParticles; } 
/*  33 */   public void setNumParticles(int val) { if (val >= 0) this.numParticles = val;  } 
/*     */   public int getNeighborhoodSize()
/*     */   {
/*  36 */     return this.neighborhoodSize; } 
/*  37 */   public void setNeighborhoodSize(int val) { if ((val >= 0) && (val <= this.numParticles)) this.neighborhoodSize = val;  } 
/*     */   public double getInitialVelocityRange()
/*     */   {
/*  40 */     return this.initialVelocityRange; } 
/*  41 */   public void setInitialVelocityRange(double val) { if (val >= 0.0D) this.initialVelocityRange = val;  } 
/*     */   public double getVelocityScalar()
/*     */   {
/*  44 */     return this.velocityScalar; } 
/*  45 */   public void setVelocityScalar(double val) { if (val >= 0.0D) this.velocityScalar = val;  } 
/*     */   public int getFitnessFunction()
/*     */   {
/*  48 */     return this.fitnessFunction; } 
/*  49 */   public void setFitnessFunction(int val) { this.fitnessFunction = val; }
/*     */ 
/*     */   public Object domFitnessFunction() {
/*  52 */     return new String[] { "Booth", "Rastrigin", "Griewangk", "Rosenbrock" };
/*     */   }
/*     */ 
/*     */   private Evaluatable mapFitnessFunction(int val)
/*     */   {
/*  57 */     switch (val) {
/*     */     case 0:
/*  59 */       return new Booth();
/*     */     case 1:
/*  60 */       return new Rastrigin();
/*     */     case 2:
/*  61 */       return new Griewangk();
/*     */     case 3:
/*  62 */       return new Rosenbrock();
/*     */     }
/*     */ 
/*  65 */     return new Booth();
/*     */   }
/*     */ 
/*     */   public double getSuccessThreshold()
/*     */   {
/*  77 */     return this.successThreshold; } 
/*  78 */   public void setSuccessThreshold(double val) { if (val >= 0.0D) this.successThreshold = val;
/*     */   }
/*     */ 
/*     */   public PSO(long seed)
/*     */   {
/*  85 */     super(seed);
/*     */   }
/*     */ 
/*     */   public void updateBest(double currVal, double currX, double currY)
/*     */   {
/*  90 */     if (currVal > this.bestVal)
/*     */     {
/*  92 */       this.bestVal = currVal;
/*  93 */       this.bestPosition.setTo(currX, currY);
/*     */     }
/*     */   }
/*     */ 
/*     */   public double getNeighborhoodBest(int index, MutableDouble2D pos)
/*     */   {
/*  99 */     double bv = (-1.0D / 0.0D);
/*     */ 
/* 102 */     int start = index - this.neighborhoodSize / 2;
/* 103 */     if (start < 0) {
/* 104 */       start += this.numParticles;
/*     */     }
/* 106 */     for (int i = 0; i < this.neighborhoodSize; i++)
/*     */     {
/* 108 */       Particle p = this.particles[((start + i) % this.numParticles)];
/* 109 */       if (p.bestVal > bv)
/*     */       {
/* 111 */         bv = p.bestVal;
/* 112 */         pos.setTo(p.bestPosition);
/*     */       }
/*     */     }
/* 115 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/* 121 */     this.bestVal = 0.0D;
/*     */ 
/* 123 */     super.start();
/* 124 */     this.particles = new Particle[this.numParticles];
/* 125 */     this.space = new Continuous2D(this.height, this.width, this.height);
/* 126 */     Evaluatable f = mapFitnessFunction(this.fitnessFunction);
/*     */ 
/* 128 */     for (int i = 0; i < this.numParticles; i++)
/*     */     {
/* 130 */       double x = this.random.nextDouble() * this.width - this.width * 0.5D;
/* 131 */       double y = this.random.nextDouble() * this.height - this.height * 0.5D;
/* 132 */       double vx = this.random.nextDouble() * this.initialVelocityRange - this.initialVelocityRange * 0.5D;
/* 133 */       double vy = this.random.nextDouble() * this.initialVelocityRange - this.initialVelocityRange * 0.5D;
/*     */ 
/* 135 */       final Particle p = new Particle(x, y, vx, vy, this, f, i);
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
/* 159 */         for (int i = 0; i < PSO.this.space.allObjects.numObjs; i++)
/*     */         {
/* 161 */           Particle p = (Particle)PSO.this.space.allObjects.get(i);
/*     */ 
/* 163 */           if (Math.abs(p.getFitness() - 1000.0D) <= PSO.this.successThreshold)
/* 164 */             successCount++;
/*     */         }
/* 166 */         if (successCount != PSO.this.prevSuccessCount)
/*     */         {
/* 168 */           PSO.this.prevSuccessCount = successCount;
/*     */ 
/* 170 */           if (successCount == PSO.this.numParticles)
/* 171 */             state.kill();
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 179 */     doLoop(PSO.class, args);
/* 180 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pso.PSO
 * JD-Core Version:    0.6.2
 */