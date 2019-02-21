/*    */ package sim.app.swarmgame;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.util.Bag;
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public class SwarmGame extends SimState
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public Continuous2D agents;
/* 17 */   public double width = 100.0D;
/* 18 */   public double height = 100.0D;
/* 19 */   public int numAgents = 100;
/* 20 */   public double jump = 0.5D;
/*    */ 
/* 22 */   public double stalker_v = 0.5D;
/* 23 */   public double avoider_v = 0.5D;
/* 24 */   public double defender_v = 0.0D;
/* 25 */   public double aggressor_v = 0.0D;
/* 26 */   public double random_v = 0.0D;
/*    */ 
/*    */   public double getGoTowardsA() {
/* 29 */     return this.stalker_v; } 
/* 30 */   public void setGoTowardsA(double val) { this.stalker_v = val; } 
/* 31 */   public double getGoAwayFromB() { return this.avoider_v; } 
/* 32 */   public void setGoAwayFromB(double val) { this.avoider_v = val; } 
/* 33 */   public double getGoBetweenAAndB() { return this.defender_v; } 
/* 34 */   public void setGoBetweenAAndB(double val) { this.defender_v = val; } 
/* 35 */   public double getGetBehindBFromA() { return this.aggressor_v; } 
/* 36 */   public void setGetBehindBFromA(double val) { this.aggressor_v = val; } 
/* 37 */   public double getMoveRandomly() { return this.random_v; } 
/* 38 */   public void setMoveRandomly(double val) { this.random_v = val; }
/*    */ 
/*    */ 
/*    */   public SwarmGame(long seed)
/*    */   {
/* 43 */     super(seed);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 48 */     super.start();
/*    */ 
/* 51 */     this.agents = new Continuous2D(this.width, this.width, this.height);
/*    */ 
/* 54 */     for (int x = 0; x < this.numAgents; x++)
/*    */     {
/* 56 */       Agent agent = new Agent();
/* 57 */       this.agents.setObjectLocation(agent, new Double2D(this.random.nextDouble() * this.width, this.random.nextDouble() * this.height));
/*    */ 
/* 59 */       this.schedule.scheduleRepeating(agent);
/*    */     }
/*    */ 
/* 63 */     for (int x = 0; x < this.agents.allObjects.numObjs; x++)
/*    */     {
/* 65 */       ((Agent)this.agents.allObjects.objs[x]).pick(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 71 */     doLoop(SwarmGame.class, args);
/* 72 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.swarmgame.SwarmGame
 * JD-Core Version:    0.6.2
 */