/*     */ package sim.app.woims;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class WoimsDemo extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final double XMIN = 0.0D;
/*     */   public static final double XMAX = 200.0D;
/*     */   public static final double YMIN = 0.0D;
/*     */   public static final double YMAX = 200.0D;
/*     */   public static final double DIAMETER = 1.0D;
/*  27 */   public static final double[][] obstInfo = { { 20.0D, 40.0D, 40.0D }, { 30.0D, 135.0D, 135.0D } };
/*     */   public static final int NUM_WOIMS = 40;
/*     */   public static final double TIMESTEP = 2.0D;
/*     */   public static final double EXTRA_SPACE = 25.0D;
/*     */   public static final int MAX_LINKS = 1000;
/*  43 */   public Continuous2D woimsEnvironment = null;
/*  44 */   public Continuous2D obstaclesEnvironment = null;
/*     */ 
/*     */   public WoimsDemo(long seed)
/*     */   {
/*  49 */     super(seed);
/*     */   }
/*     */ 
/*     */   public void setObjectLocation(Woim woim, Double2D location)
/*     */   {
/*  55 */     double x = (location.x + 25.0D - 0.0D + 250.0D) % 250.0D + 0.0D - 25.0D;
/*  56 */     double y = (location.y + 25.0D - 0.0D + 250.0D) % 250.0D + 0.0D - 25.0D;
/*     */ 
/*  58 */     location = new Double2D(x, y);
/*     */ 
/*  60 */     this.woimsEnvironment.setObjectLocation(woim, location);
/*     */ 
/*  63 */     woim.x = location.x;
/*  64 */     woim.y = location.y;
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  69 */     super.start();
/*     */ 
/*  71 */     this.woimsEnvironment = new Continuous2D(Woim.MAX_DISTANCE, 200.0D, 200.0D);
/*  72 */     this.obstaclesEnvironment = new Continuous2D(30.0D, 200.0D, 200.0D);
/*     */ 
/*  76 */     for (int x = 0; x < 40; x++)
/*     */     {
/*  78 */       Double2D loc = null;
/*  79 */       Woim woim = null;
/*  80 */       int caz = this.random.nextInt(4);
/*  81 */       switch (caz) {
/*     */       case 0:
/*  83 */         loc = new Double2D(-25.0D, this.random.nextDouble() * 199.0D + 0.0D + 0.5D);
/*     */ 
/*  85 */         break;
/*     */       case 1:
/*  86 */         loc = new Double2D(225.0D, this.random.nextDouble() * 199.0D + 0.0D + 0.5D);
/*     */ 
/*  88 */         break;
/*     */       case 2:
/*  89 */         loc = new Double2D(this.random.nextDouble() * 199.0D + 0.0D + 0.5D, -25.0D);
/*     */ 
/*  91 */         break;
/*     */       case 3:
/*  92 */         loc = new Double2D(this.random.nextDouble() * 199.0D + 0.0D + 0.5D, 225.0D);
/*     */ 
/*  94 */         break;
/*     */       default:
/*  96 */         throw new RuntimeException("default case should never occur");
/*     */       }
/*  98 */       woim = new Woim();
/*  99 */       this.woimsEnvironment.setObjectLocation(woim, loc);
/* 100 */       woim.x = loc.x;
/* 101 */       woim.y = loc.y;
/* 102 */       this.schedule.scheduleRepeating(woim);
/*     */     }
/*     */ 
/* 106 */     for (int i = 0; i < obstInfo.length; i++)
/*     */     {
/* 108 */       Obstacle obst = new Obstacle(obstInfo[i][0]);
/* 109 */       this.obstaclesEnvironment.setObjectLocation(obst, new Double2D(obstInfo[i][1], obstInfo[i][2]));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 116 */     doLoop(WoimsDemo.class, args);
/* 117 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.woims.WoimsDemo
 * JD-Core Version:    0.6.2
 */