/*    */ package sim.app.tutorial3;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.grid.DoubleGrid2D;
/*    */ import sim.field.grid.SparseGrid2D;
/*    */ import sim.util.Int2D;
/*    */ 
/*    */ public class Tutorial3 extends SimState
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public DoubleGrid2D trails;
/*    */   public SparseGrid2D particles;
/* 20 */   public int gridWidth = 100;
/* 21 */   public int gridHeight = 100;
/* 22 */   public int numParticles = 500;
/*    */ 
/*    */   public Tutorial3(long seed)
/*    */   {
/* 26 */     super(seed);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 31 */     super.start();
/* 32 */     this.trails = new DoubleGrid2D(this.gridWidth, this.gridHeight);
/* 33 */     this.particles = new SparseGrid2D(this.gridWidth, this.gridHeight);
/*    */ 
/* 37 */     for (int i = 0; i < this.numParticles; i++)
/*    */     {
/* 39 */       Particle p = new Particle(this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
/* 40 */       this.schedule.scheduleRepeating(p);
/* 41 */       this.particles.setObjectLocation(p, new Int2D(this.random.nextInt(this.gridWidth), this.random.nextInt(this.gridHeight)));
/*    */     }
/*    */ 
/* 46 */     Steppable decreaser = new Steppable()
/*    */     {
/*    */       private static final long serialVersionUID = 1L;
/*    */ 
/*    */       public void step(SimState state)
/*    */       {
/* 53 */         Tutorial3.this.trails.multiply(0.9D);
/*    */       }
/*    */     };
/* 57 */     this.schedule.scheduleRepeating(0.0D, 2, decreaser, 1.0D);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 62 */     doLoop(Tutorial3.class, args);
/* 63 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial3.Tutorial3
 * JD-Core Version:    0.6.2
 */