/*     */ package sim.app.mousetraps;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous3D;
/*     */ import sim.field.grid.IntGrid2D;
/*     */ import sim.util.Double3D;
/*     */ 
/*     */ public class MouseTraps extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int BALLS_PER_TRAP = 2;
/*     */   public final double initialVelocity;
/*     */   public static final double GRAVITY_ACC = 9.800000000000001D;
/*     */   public static final double TWO_OVER_G = 0.204081632653061D;
/*     */   public static final double TIME_STEP_DURATION = 0.015625D;
/*     */   public static final double TIME_STEP_FREQUENCY = 64.0D;
/*     */   public static final double TWO_PI = 6.283185307179586D;
/*     */   public static final double HALF_PI = 1.570796326794897D;
/*     */   public final boolean toroidalWorld;
/*     */   public final boolean modelBalls;
/*     */   public final int trapGridHeight;
/*     */   public final int trapGridWidth;
/*     */   public final double spaceWidth;
/*     */   public final double spaceHeight;
/*     */   public final double spaceLength;
/*     */   public final double oneOverSpaceWidth;
/*     */   public final double oneOverSpaceHeight;
/*     */   public final double oneOverSpaceLength;
/*     */   public final double trapSizeX;
/*     */   public final double trapSizeY;
/*     */   public IntGrid2D trapStateGrid;
/*     */   public Continuous3D ballSpace;
/*     */   public static final int ARMED_TRAP = 0;
/*     */   public static final int OFF_TRAP = 1;
/*     */ 
/*     */   public MouseTraps(long seed)
/*     */   {
/*  58 */     this(seed, 0.7D, 100, 100, true);
/*     */   }
/*     */ 
/*     */   public MouseTraps(long seed, double initialVelocity, int width, int height, boolean toroidal)
/*     */   {
/*  63 */     super(seed);
/*  64 */     this.initialVelocity = initialVelocity;
/*  65 */     this.toroidalWorld = toroidal;
/*  66 */     this.modelBalls = false;
/*  67 */     this.trapGridWidth = width;
/*  68 */     this.trapGridHeight = height;
/*  69 */     this.spaceWidth = 1.0D;
/*  70 */     this.spaceHeight = 1.0D;
/*  71 */     this.spaceLength = 1.0D;
/*  72 */     createGrids();
/*  73 */     this.trapSizeX = (this.spaceWidth / this.trapGridWidth);
/*  74 */     this.trapSizeY = (this.spaceHeight / this.trapGridHeight);
/*  75 */     this.oneOverSpaceHeight = (1.0D / this.spaceHeight);
/*  76 */     this.oneOverSpaceWidth = (1.0D / this.spaceWidth);
/*  77 */     this.oneOverSpaceLength = (1.0D / this.spaceLength);
/*     */   }
/*     */ 
/*     */   public MouseTraps(long seed, double initialVelocity, int trapsX, int trapsY, double width, double height, boolean toroidal)
/*     */   {
/*  88 */     super(seed);
/*  89 */     this.initialVelocity = initialVelocity;
/*  90 */     this.toroidalWorld = toroidal;
/*  91 */     this.trapGridWidth = trapsX;
/*  92 */     this.trapGridHeight = trapsY;
/*  93 */     this.modelBalls = true;
/*  94 */     this.spaceWidth = width;
/*  95 */     this.spaceHeight = height;
/*  96 */     this.spaceLength = computeFishTankCeiling();
/*  97 */     createGrids();
/*  98 */     this.trapSizeX = (this.spaceWidth / this.trapGridWidth);
/*  99 */     this.trapSizeY = (this.spaceHeight / this.trapGridHeight);
/* 100 */     this.oneOverSpaceHeight = (1.0D / this.spaceHeight);
/* 101 */     this.oneOverSpaceWidth = (1.0D / this.spaceWidth);
/* 102 */     this.oneOverSpaceLength = (1.0D / this.spaceLength);
/*     */   }
/*     */ 
/*     */   public double computeFishTankCeiling()
/*     */   {
/* 119 */     return 0.5D * this.initialVelocity * this.initialVelocity / 9.800000000000001D;
/*     */   }
/*     */ 
/*     */   void createGrids()
/*     */   {
/* 124 */     this.trapStateGrid = new IntGrid2D(this.trapGridWidth, this.trapGridHeight, 0);
/* 125 */     if (this.modelBalls)
/* 126 */       this.ballSpace = new Continuous3D(Math.max(this.trapGridHeight, this.trapGridWidth) * 2, this.spaceWidth, this.spaceHeight, this.spaceLength);
/*     */   }
/*     */ 
/*     */   public int discretizeX(double position)
/*     */   {
/* 145 */     int i = (int)(position * this.oneOverSpaceWidth * this.trapGridWidth);
/* 146 */     if (this.toroidalWorld)
/* 147 */       return (i + this.trapGridWidth) % this.trapGridWidth;
/* 148 */     i += 2 * this.trapGridWidth;
/* 149 */     i %= 2 * this.trapGridWidth;
/* 150 */     if (i < this.trapGridWidth)
/* 151 */       return i;
/* 152 */     return this.trapGridWidth - i;
/*     */   }
/*     */ 
/*     */   public int discretizeY(double position)
/*     */   {
/* 164 */     int i = (int)(position * this.oneOverSpaceHeight * this.trapGridHeight);
/* 165 */     if (this.toroidalWorld)
/* 166 */       return (i + this.trapGridHeight) % this.trapGridHeight;
/* 167 */     i += 2 * this.trapGridHeight;
/* 168 */     i %= 2 * this.trapGridHeight;
/* 169 */     if (i < this.trapGridHeight)
/* 170 */       return i;
/* 171 */     return this.trapGridHeight - i;
/*     */   }
/*     */ 
/*     */   public int discretizeX(double offset, int location)
/*     */   {
/* 176 */     return discretizeX(offset + (0.5D + location) * this.trapSizeX);
/*     */   }
/*     */ 
/*     */   public int discretizeY(double offset, int location)
/*     */   {
/* 182 */     return discretizeY(offset + (0.5D + location) * this.trapSizeY);
/*     */   }
/*     */ 
/*     */   public double trapPosX(int x)
/*     */   {
/* 188 */     return (0.5D + x) * this.trapSizeX;
/*     */   }
/*     */ 
/*     */   public double trapPosY(int y)
/*     */   {
/* 193 */     return (0.5D + y) * this.trapSizeY;
/*     */   }
/*     */ 
/*     */   public void triggerTrap(int posx, int posy)
/*     */   {
/* 198 */     if (this.trapStateGrid.get(posx, posy) == 1)
/* 199 */       return;
/* 200 */     this.trapStateGrid.set(posx, posy, 1);
/* 201 */     double spacePosX = (0.5D + posx) * this.trapSizeX;
/* 202 */     double spacePosY = (0.5D + posy) * this.trapSizeY;
/*     */ 
/* 205 */     for (int i = 0; i < 2; i++)
/*     */     {
/* 224 */       double azimuth = this.random.nextDouble() * 6.283185307179586D;
/* 225 */       double elevation = this.random.nextDouble() * 1.570796326794897D;
/*     */ 
/* 227 */       double cos_elevation = Math.cos(elevation);
/* 228 */       double sin_elevation = Math.sqrt(1.0D - cos_elevation * cos_elevation);
/*     */ 
/* 230 */       double cos_azimuth = Math.cos(azimuth);
/* 231 */       double sin_azimuth = Math.sin(azimuth);
/*     */ 
/* 234 */       double vz = this.initialVelocity * sin_elevation;
/* 235 */       double vxy = this.initialVelocity * cos_elevation;
/* 236 */       double vx = vxy * cos_azimuth;
/* 237 */       double vy = vxy * sin_azimuth;
/*     */ 
/* 239 */       if (!this.modelBalls)
/*     */       {
/* 241 */         double landing_time = vz * 0.204081632653061D;
/* 242 */         double landing_dx = vx * landing_time;
/* 243 */         double landing_dy = vy * landing_time;
/*     */ 
/* 245 */         this.schedule.scheduleOnce(this.schedule.getTime() + landing_time, new MouseTrap(discretizeX(landing_dx, posx), discretizeY(landing_dy, posy)));
/*     */       }
/*     */       else
/*     */       {
/* 250 */         Ball b = new Ball(spacePosX, spacePosY, 0.0D, vx, vy, vz);
/* 251 */         this.ballSpace.setObjectLocation(b, new Double3D(spacePosX, spacePosY, 0.0D));
/* 252 */         this.schedule.scheduleOnce(this.schedule.getTime() + 1.0D, b);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/* 260 */     super.start();
/*     */ 
/* 263 */     createGrids();
/*     */ 
/* 265 */     int posx = this.trapGridWidth / 2;
/* 266 */     int posy = this.trapGridHeight / 2;
/* 267 */     if (this.modelBalls)
/*     */     {
/* 269 */       double x = (0.5D + posx) * this.trapSizeX;
/* 270 */       double y = (0.5D + posy) * this.trapSizeY;
/* 271 */       double z = computeFishTankCeiling();
/* 272 */       Ball b = new Ball(x, y, z, 0.0D, 0.0D, 0.0D);
/* 273 */       this.ballSpace.setObjectLocation(b, new Double3D(x, y, z));
/* 274 */       this.schedule.scheduleOnce(0.0D, b);
/*     */     }
/*     */     else
/*     */     {
/* 278 */       this.schedule.scheduleOnce(this.initialVelocity * 9.800000000000001D, new MouseTrap(posx, posy));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 284 */     doLoop(MouseTraps.class, args);
/* 285 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.mousetraps.MouseTraps
 * JD-Core Version:    0.6.2
 */