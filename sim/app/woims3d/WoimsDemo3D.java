/*     */ package sim.app.woims3d;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import javax.swing.JFrame;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous3D;
/*     */ import sim.util.Double3D;
/*     */ 
/*     */ public class WoimsDemo3D extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final double XMIN = 0.0D;
/*     */   public static final double XMAX = 200.0D;
/*     */   public static final double YMIN = 0.0D;
/*     */   public static final double YMAX = 200.0D;
/*     */   public static final double ZMIN = 0.0D;
/*     */   public static final double ZMAX = 200.0D;
/*     */   public static final double DIAMETER = 1.0D;
/*  28 */   public static final double[][] obstInfo = { { 40.0D, 40.0D, 40.0D, 40.0D }, { 60.0D, 135.0D, 135.0D, 135.0D } };
/*     */   public static final int NUM_WOIMS = 40;
/*     */   public static final double TIMESTEP = 30.0D;
/*     */   public Display3D display;
/*     */   public JFrame displayFrame;
/*  37 */   public Continuous3D environment = new Continuous3D(2.0D, 200.0D, 200.0D, 200.0D);
/*  38 */   public Continuous3D woimEnvironment = null;
/*  39 */   public Continuous3D obstacles = null;
/*     */   public static final double EXTRA_SPACE = 10.0D;
/*     */ 
/*     */   public WoimsDemo3D(long seed)
/*     */   {
/*  43 */     super(seed);
/*     */   }
/*     */ 
/*     */   public void setObjectLocation(Woim3D woim, Double3D location)
/*     */   {
/*  51 */     double x = location.x;
/*  52 */     while (x < -10.0D)
/*  53 */       x += 220.0D;
/*  54 */     while (x > 210.0D)
/*  55 */       x -= 220.0D;
/*  56 */     double y = location.y;
/*  57 */     while (y < -10.0D)
/*  58 */       y += 220.0D;
/*  59 */     while (y > 210.0D)
/*  60 */       y -= 220.0D;
/*  61 */     double z = location.z;
/*  62 */     while (z < -10.0D)
/*  63 */       z += 220.0D;
/*  64 */     while (z > 210.0D)
/*  65 */       z -= 220.0D;
/*  66 */     location = new Double3D(x, y, z);
/*  67 */     this.environment.setObjectLocation(woim, location);
/*  68 */     this.woimEnvironment.setObjectLocation(woim, location);
/*  69 */     woim.x = location.x;
/*  70 */     woim.y = location.y;
/*  71 */     woim.z = location.z;
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  76 */     super.start();
/*     */ 
/*  78 */     this.environment = new Continuous3D(2.0D, 200.0D, 200.0D, 200.0D);
/*  79 */     this.woimEnvironment = new Continuous3D(Woim3D.MAX_DISTANCE, 200.0D, 200.0D, 200.0D);
/*  80 */     this.obstacles = new Continuous3D(Math.max(200.0D, Math.max(200.0D, 200.0D)), 200.0D, 200.0D, 200.0D);
/*     */ 
/*  84 */     for (int x = 0; x < 40; x++)
/*     */     {
/*  86 */       Double3D loc = null;
/*  87 */       Woim3D woim = null;
/*  88 */       int caz = this.random.nextInt(6);
/*  89 */       switch (caz) {
/*     */       case 0:
/*  91 */         loc = new Double3D(-10.0D, this.random.nextDouble() * 199.0D + 0.0D + 0.5D, this.random.nextDouble() * 199.0D + 0.0D + 0.5D);
/*     */ 
/*  94 */         break;
/*     */       case 1:
/*  95 */         loc = new Double3D(210.0D, this.random.nextDouble() * 199.0D + 0.0D + 0.5D, this.random.nextDouble() * 199.0D + 0.0D + 0.5D);
/*     */ 
/*  98 */         break;
/*     */       case 2:
/*  99 */         loc = new Double3D(this.random.nextDouble() * 199.0D + 0.0D + 0.5D, -10.0D, this.random.nextDouble() * 199.0D + 0.0D + 0.5D);
/*     */ 
/* 102 */         break;
/*     */       case 3:
/* 103 */         loc = new Double3D(this.random.nextDouble() * 199.0D + 0.0D + 0.5D, 210.0D, this.random.nextDouble() * 199.0D + 0.0D + 0.5D);
/*     */ 
/* 106 */         break;
/*     */       case 4:
/* 107 */         loc = new Double3D(this.random.nextDouble() * 199.0D + 0.0D + 0.5D, this.random.nextDouble() * 199.0D + 0.0D + 0.5D, -10.0D);
/*     */ 
/* 110 */         break;
/*     */       case 5:
/* 111 */         loc = new Double3D(this.random.nextDouble() * 199.0D + 0.0D + 0.5D, this.random.nextDouble() * 199.0D + 0.0D + 0.5D, 210.0D);
/*     */       }
/*     */ 
/* 116 */       woim = new Woim3D();
/* 117 */       this.environment.setObjectLocation(woim, loc);
/* 118 */       this.woimEnvironment.setObjectLocation(woim, loc);
/* 119 */       woim.x = loc.x;
/* 120 */       woim.y = loc.y;
/* 121 */       woim.z = loc.z;
/* 122 */       this.schedule.scheduleRepeating(woim);
/*     */     }
/*     */ 
/* 125 */     for (int i = 0; i < obstInfo.length; i++)
/*     */     {
/* 127 */       this.environment.setObjectLocation(new Obstacle3D(obstInfo[i][0]), new Double3D(obstInfo[i][1], obstInfo[i][2], obstInfo[i][3]));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 134 */     doLoop(WoimsDemo3D.class, args);
/* 135 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.woims3d.WoimsDemo3D
 * JD-Core Version:    0.6.2
 */