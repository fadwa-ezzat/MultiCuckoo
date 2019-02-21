/*    */ package sim.app.keepaway;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import java.awt.Color;
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public class Keepaway extends SimState
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 18 */   public double xMin = 0.0D;
/* 19 */   public double xMax = 100.0D;
/* 20 */   public double yMin = 0.0D;
/* 21 */   public double yMax = 100.0D;
/*    */   public Continuous2D fieldEnvironment;
/*    */ 
/*    */   public Keepaway(long seed)
/*    */   {
/* 29 */     this(seed, 100, 100);
/*    */   }
/*    */ 
/*    */   public Keepaway(long seed, int width, int height)
/*    */   {
/* 34 */     super(seed);
/* 35 */     this.xMax = width; this.yMax = height;
/* 36 */     createGrids();
/*    */   }
/*    */ 
/*    */   void createGrids()
/*    */   {
/* 41 */     this.fieldEnvironment = new Continuous2D(25.0D, this.xMax - this.xMin, this.yMax - this.yMin);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 47 */     super.start();
/* 48 */     createGrids();
/*    */ 
/* 54 */     double x = this.random.nextDouble() * this.xMax;
/* 55 */     double y = this.random.nextDouble() * this.yMax;
/* 56 */     Bot b = new Bot(x, y, Color.red);
/* 57 */     b.cap = 0.65D;
/* 58 */     this.fieldEnvironment.setObjectLocation(b, new Double2D(x, y));
/* 59 */     this.schedule.scheduleRepeating(b);
/*    */ 
/* 62 */     x = this.random.nextDouble() * this.xMax;
/* 63 */     y = this.random.nextDouble() * this.yMax;
/* 64 */     b = new Bot(x, y, Color.blue);
/* 65 */     b.cap = 0.5D;
/* 66 */     this.fieldEnvironment.setObjectLocation(b, new Double2D(x, y));
/* 67 */     this.schedule.scheduleRepeating(b);
/*    */ 
/* 71 */     x = this.random.nextDouble() * this.xMax;
/* 72 */     y = this.random.nextDouble() * this.yMax;
/* 73 */     b = new Bot(x, y, Color.blue);
/* 74 */     b.cap = 0.5D;
/* 75 */     this.fieldEnvironment.setObjectLocation(b, new Double2D(x, y));
/* 76 */     this.schedule.scheduleRepeating(b);
/*    */ 
/* 79 */     x = this.random.nextDouble() * this.xMax;
/* 80 */     y = this.random.nextDouble() * this.yMax;
/* 81 */     b = new Bot(x, y, Color.blue);
/* 82 */     b.cap = 0.5D;
/* 83 */     this.fieldEnvironment.setObjectLocation(b, new Double2D(x, y));
/* 84 */     this.schedule.scheduleRepeating(b);
/*    */ 
/* 88 */     x = this.random.nextDouble() * this.xMax;
/* 89 */     y = this.random.nextDouble() * this.yMax;
/* 90 */     Ball ba = new Ball(x, y);
/* 91 */     this.fieldEnvironment.setObjectLocation(ba, new Double2D(x, y));
/* 92 */     this.schedule.scheduleRepeating(ba);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 97 */     doLoop(Keepaway.class, args);
/* 98 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.keepaway.Keepaway
 * JD-Core Version:    0.6.2
 */