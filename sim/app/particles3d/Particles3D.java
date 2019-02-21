/*    */ package sim.app.particles3d;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.grid.DoubleGrid3D;
/*    */ import sim.field.grid.SparseGrid3D;
/*    */ import sim.util.Int3D;
/*    */ 
/*    */ public class Particles3D extends SimState
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int gridWidth = 30;
/*    */   public static final int gridHeight = 30;
/*    */   public static final int gridLength = 30;
/*    */   public SparseGrid3D particles;
/* 23 */   public DoubleGrid3D trails = new DoubleGrid3D(30, 30, 30);
/*    */ 
/* 25 */   public int numParticles = 20;
/*    */ 
/*    */   public Particles3D(long seed)
/*    */   {
/* 29 */     super(seed);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 34 */     super.start();
/* 35 */     this.particles = new SparseGrid3D(30, 30, 30);
/* 36 */     this.trails = new DoubleGrid3D(30, 30, 30);
/*    */ 
/* 40 */     for (int i = 0; i < this.numParticles; i++)
/*    */     {
/* 42 */       Particle p = new Particle(this.random.nextInt(3) - 1, this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
/* 43 */       this.schedule.scheduleRepeating(p);
/* 44 */       this.particles.setObjectLocation(p, new Int3D(this.random.nextInt(30), this.random.nextInt(30), this.random.nextInt(30)));
/*    */     }
/*    */ 
/* 49 */     Steppable decreaser = new Steppable() {
/*    */       static final long serialVersionUID = 6330208160095250478L;
/*    */ 
/*    */       public void step(SimState state) {
/* 53 */         Particles3D.this.trails.multiply(0.8999999761581421D);
/*    */       }
/*    */     };
/* 58 */     this.schedule.scheduleRepeating(0.0D, 2, decreaser, 1.0D);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 63 */     doLoop(Particles3D.class, args);
/* 64 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.particles3d.Particles3D
 * JD-Core Version:    0.6.2
 */