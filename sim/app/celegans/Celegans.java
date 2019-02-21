/*    */ package sim.app.celegans;
/*    */ 
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.continuous.Continuous3D;
/*    */ import sim.field.network.Network;
/*    */ 
/*    */ public class Celegans extends SimState
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public Continuous3D cells;
/*    */   public Continuous3D neurons;
/*    */   public Network synapses;
/*    */   Cells database;
/*    */ 
/*    */   public Celegans(long seed)
/*    */   {
/* 26 */     super(seed);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 31 */     super.start();
/* 32 */     if (this.database == null) this.database = new Cells();
/*    */ 
/* 34 */     this.cells = new Continuous3D(100.0D, 100.0D, 100.0D, 100.0D);
/* 35 */     this.neurons = new Continuous3D(100.0D, 100.0D, 100.0D, 100.0D);
/* 36 */     this.synapses = new Network();
/*    */ 
/* 38 */     Cell p0 = this.database.P0;
/*    */ 
/* 40 */     p0.stopper = this.schedule.scheduleRepeating(p0);
/* 41 */     p0.step(this);
/*    */ 
/* 43 */     this.schedule.scheduleOnce(1000.0D, -1, new Steppable() { public void step(SimState state) { state.kill(); } } );
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 48 */     doLoop(Celegans.class, args);
/* 49 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.celegans.Celegans
 * JD-Core Version:    0.6.2
 */