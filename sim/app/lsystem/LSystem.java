/*    */ package sim.app.lsystem;
/*    */ 
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ 
/*    */ public class LSystem extends SimState
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 16 */   public double xMin = 0.0D;
/* 17 */   public double xMax = 100.0D;
/* 18 */   public double yMin = 0.0D;
/* 19 */   public double yMax = 100.0D;
/*    */ 
/* 21 */   public LSystemData l = new LSystemData();
/*    */   public Continuous2D drawEnvironment;
/*    */ 
/*    */   public LSystem(long seed)
/*    */   {
/* 28 */     this(seed, 100, 100);
/*    */   }
/*    */ 
/*    */   public LSystem(long seed, int width, int height)
/*    */   {
/* 33 */     super(seed);
/* 34 */     this.xMax = width; this.yMax = height;
/*    */ 
/* 36 */     createGrids();
/*    */   }
/*    */ 
/*    */   void createGrids()
/*    */   {
/* 41 */     this.drawEnvironment = new Continuous2D(5.0D, this.xMax - this.xMin, this.yMax - this.yMin);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 47 */     super.start();
/* 48 */     createGrids();
/*    */ 
/* 50 */     LSystemDrawer ld = new LSystemDrawer(this.l);
/* 51 */     ld.stopper = this.schedule.scheduleRepeating(ld);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 56 */     doLoop(LSystem.class, args);
/* 57 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.lsystem.LSystem
 * JD-Core Version:    0.6.2
 */