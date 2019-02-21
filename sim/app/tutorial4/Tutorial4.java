/*    */ package sim.app.tutorial4;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.grid.DoubleGrid2D;
/*    */ import sim.field.grid.SparseGrid2D;
/*    */ import sim.util.Int2D;
/*    */ 
/*    */ public class Tutorial4 extends SimState
/*    */ {
/*    */   public DoubleGrid2D trails;
/*    */   public SparseGrid2D particles;
/*    */   public int collisions;
/*    */   public double collisionRate;
/* 21 */   public int gridWidth = 100;
/* 22 */   public int gridHeight = 100;
/* 23 */   public int numParticles = 500;
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public double getCollisionRate()
/*    */   {
/* 19 */     return this.collisionRate;
/*    */   }
/*    */ 
/*    */   public int getWidth()
/*    */   {
/* 25 */     return this.gridWidth; } 
/* 26 */   public void setWidth(int val) { if (val > 0) this.gridWidth = val;  } 
/* 27 */   public int getHeight() { return this.gridHeight; } 
/* 28 */   public void setHeight(int val) { if (val > 0) this.gridHeight = val;  } 
/* 29 */   public int getNumParticles() { return this.numParticles; } 
/* 30 */   public void setNumParticles(int val) { if (val >= 0) this.numParticles = val; 
/*    */   }
/*    */ 
/*    */   public Tutorial4(long seed)
/*    */   {
/* 34 */     super(seed);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 39 */     super.start();
/* 40 */     this.trails = new DoubleGrid2D(this.gridWidth, this.gridHeight);
/* 41 */     this.particles = new SparseGrid2D(this.gridWidth, this.gridHeight);
/*    */ 
/* 45 */     for (int i = 0; i < this.numParticles; i++)
/*    */     {
/* 47 */       Particle p = new Particle(this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
/* 48 */       this.schedule.scheduleRepeating(p);
/* 49 */       this.particles.setObjectLocation(p, new Int2D(this.random.nextInt(this.gridWidth), this.random.nextInt(this.gridHeight)));
/*    */     }
/*    */ 
/* 54 */     BigParticle b = new BigParticle(this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
/* 55 */     this.particles.setObjectLocation(b, new Int2D(this.random.nextInt(this.gridWidth), this.random.nextInt(this.gridHeight)));
/*    */ 
/* 57 */     this.schedule.scheduleRepeating(0.0D, 1, b, 5.0D);
/*    */ 
/* 60 */     Steppable decreaser = new Steppable()
/*    */     {
/*    */       private static final long serialVersionUID = 1L;
/*    */ 
/*    */       public void step(SimState state) {
/* 65 */         Tutorial4.this.trails.multiply(0.9D);
/*    */ 
/* 68 */         Tutorial4.this.collisionRate = (Tutorial4.this.collisions / Tutorial4.this.numParticles);
/* 69 */         Tutorial4.this.collisions = 0;
/*    */       }
/*    */     };
/* 78 */     this.schedule.scheduleRepeating(0.0D, 2, decreaser, 1.0D);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 83 */     doLoop(Tutorial4.class, args);
/* 84 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial4.Tutorial4
 * JD-Core Version:    0.6.2
 */