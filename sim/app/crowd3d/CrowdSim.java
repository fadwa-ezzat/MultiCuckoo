/*    */ package sim.app.crowd3d;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.continuous.Continuous3D;
/*    */ import sim.util.Double3D;
/*    */ 
/*    */ public class CrowdSim extends SimState
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 17 */   public double spaceHeight = 20.0D;
/* 18 */   public double spaceWidth = 20.0D;
/* 19 */   public double spaceDepth = 20.0D;
/* 20 */   public int boidCount = 0;
/* 21 */   public final int STEPS_BETWEEN_INSERTS = 200;
/*    */   public double maxFnVal;
/* 24 */   public Continuous3D boidSpace = new Continuous3D(5.0D, this.spaceWidth, this.spaceHeight, this.spaceDepth);
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 28 */     doLoop(CrowdSim.class, args);
/* 29 */     System.exit(0);
/*    */   }
/*    */ 
/*    */   public CrowdSim(long seed)
/*    */   {
/* 34 */     super(seed);
/* 35 */     this.maxFnVal = (Math.min(this.spaceHeight, Math.min(this.spaceWidth, this.spaceDepth)) / 2.0D);
/*    */   }
/*    */ 
/*    */   private void spawnBoid()
/*    */   {
/* 40 */     Agent boid = new Agent();
/* 41 */     this.boidSpace.setObjectLocation(boid, new Double3D(this.random.nextDouble() * this.spaceWidth, this.random.nextDouble() * this.spaceHeight, this.random.nextDouble() * this.spaceDepth));
/*    */ 
/* 45 */     boid.setStopper(this.schedule.scheduleRepeating(boid));
/* 46 */     this.boidCount += 1;
/*    */   }
/*    */ 
/*    */   protected void killBoid()
/*    */   {
/* 51 */     Agent victim = (Agent)this.boidSpace.allObjects.objs[((int)(this.random.nextDouble() * this.boidCount))];
/* 52 */     victim.stop();
/* 53 */     this.boidSpace.remove(victim);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 59 */     super.start();
/*    */ 
/* 61 */     this.boidSpace = new Continuous3D(5.0D, this.spaceWidth, this.spaceHeight, this.spaceDepth);
/*    */ 
/* 63 */     Steppable spawner = new Steppable() { public void step(SimState state) { CrowdSim.this.spawnBoid(); }
/*    */ 
/*    */     };
/* 65 */     this.schedule.scheduleRepeating(0.0D, 1, spawner, 200.0D);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.crowd3d.CrowdSim
 * JD-Core Version:    0.6.2
 */