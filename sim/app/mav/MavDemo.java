/*    */ package sim.app.mav;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public class MavDemo extends SimState
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public Continuous2D ground;
/*    */   public Continuous2D mavs;
/* 20 */   public double width = 500.0D;
/* 21 */   public double height = 500.0D;
/* 22 */   public double crashDistance = 8.0D;
/* 23 */   public double sensorRangeDistance = 50.0D;
/* 24 */   public int numMavs = 30;
/*    */ 
/* 27 */   public Region[] region = { new Region(0, 1, 50.0D, 50.0D), new Region(1, 2, 200.0D, 200.0D), new Region(2, 3, 200.0D, 450.0D) };
/*    */ 
/*    */   public MavDemo(long seed)
/*    */   {
/* 36 */     super(seed);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 41 */     super.start();
/*    */ 
/* 50 */     this.ground = new Continuous2D(this.width > this.height ? this.width : this.height, this.width, this.height);
/* 51 */     for (int i = 0; i < this.region.length; i++) {
/* 52 */       this.ground.setObjectLocation(this.region[i], new Double2D(this.region[i].originx, this.region[i].originy));
/*    */     }
/*    */ 
/* 59 */     this.mavs = new Continuous2D(this.sensorRangeDistance * 2.0D, this.width, this.height);
/* 60 */     for (int i = 0; i < this.numMavs; i++)
/*    */     {
/* 64 */       Mav mav = new Mav(4, this.random.nextDouble() * this.width, this.random.nextDouble() * this.height);
/* 65 */       this.mavs.setObjectLocation(mav, new Double2D(mav.x, mav.y));
/* 66 */       this.schedule.scheduleRepeating(mav);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 72 */     doLoop(MavDemo.class, args);
/* 73 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.mav.MavDemo
 * JD-Core Version:    0.6.2
 */