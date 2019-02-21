/*    */ package sim.app.tutorial6;
/*    */ 
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public class Tutorial6 extends SimState
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   static final int PLUTO = 9;
/*    */   public Continuous2D bodies;
/* 26 */   public static final double[] DISTANCE = { 0.0D, 579.0D, 1082.0D, 1496.0D, 2279.0D, 7786.0D, 14335.0D, 28725.0D, 44951.0D, 58700.0D };
/*    */ 
/* 30 */   public static final double[] DIAMETER = { 139200.0D, 487.89999999999998D, 1210.4000000000001D, 1275.5999999999999D, 679.39999999999998D, 14298.4D, 12053.6D, 5111.8000000000002D, 4952.8000000000002D, 239.0D };
/*    */ 
/* 34 */   public static final double[] PERIOD = { 1.0D, 88.0D, 224.69999999999999D, 365.19999999999999D, 687.0D, 4331.0D, 10747.0D, 30589.0D, 59800.0D, 90588.0D };
/*    */ 
/*    */   public Tutorial6(long seed)
/*    */   {
/* 21 */     super(seed);
/* 22 */     this.bodies = new Continuous2D(DISTANCE[9], DISTANCE[9], DISTANCE[9]);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 39 */     super.start();
/*    */ 
/* 41 */     this.bodies = new Continuous2D(DISTANCE[9], DISTANCE[9], DISTANCE[9]);
/*    */ 
/* 44 */     for (int i = 0; i < 10; i++)
/*    */     {
/* 46 */       Body b = new Body(6.283185307179586D * DISTANCE[i] / PERIOD[i], DISTANCE[i]);
/* 47 */       this.bodies.setObjectLocation(b, new Double2D(DISTANCE[i], 0.0D));
/* 48 */       this.schedule.scheduleRepeating(b);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 54 */     doLoop(Tutorial6.class, args);
/* 55 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial6.Tutorial6
 * JD-Core Version:    0.6.2
 */