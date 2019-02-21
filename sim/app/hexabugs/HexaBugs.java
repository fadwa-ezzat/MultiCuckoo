/*     */ package sim.app.hexabugs;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.lang.reflect.Method;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ import sim.field.grid.SparseGrid2D;
/*     */ import sim.util.DoubleBag;
/*     */ import sim.util.IntBag;
/*     */ import sim.util.Interval;
/*     */ 
/*     */ public class HexaBugs extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  16 */   public double minIdealTemp = 17000.0D;
/*  17 */   public double maxIdealTemp = 31000.0D;
/*  18 */   public double minOutputHeat = 6000.0D;
/*  19 */   public double maxOutputHeat = 10000.0D;
/*     */ 
/*  21 */   public double evaporationRate = 0.993D;
/*  22 */   public double diffusionRate = 1.0D;
/*     */   public static final double MAX_HEAT = 32000.0D;
/*  24 */   public double randomMovementProbability = 0.1D;
/*     */ 
/*  26 */   public int gridHeight = 100;
/*  27 */   public int gridWidth = 100;
/*  28 */   public int bugCount = 100;
/*     */   HexaBug[] bugs;
/*  64 */   public DoubleGrid2D valgrid = new DoubleGrid2D(this.gridWidth, this.gridHeight, 0.0D);
/*  65 */   public DoubleGrid2D valgrid2 = new DoubleGrid2D(this.gridWidth, this.gridHeight, 0.0D);
/*  66 */   public SparseGrid2D buggrid = new SparseGrid2D(this.gridWidth, this.gridHeight);
/*     */ 
/*  69 */   DoubleBag neighVal = new DoubleBag();
/*  70 */   IntBag neighX = new IntBag();
/*  71 */   IntBag neighY = new IntBag();
/*     */ 
/*  81 */   ThreadedHexaDiffuser diffuser = null;
/*     */ 
/*     */   public double getMinimumIdealTemperature()
/*     */   {
/*  31 */     return this.minIdealTemp; } 
/*  32 */   public void setMinimumIdealTemperature(double temp) { if (temp <= this.maxIdealTemp) this.minIdealTemp = temp;  } 
/*  33 */   public double getMaximumIdealTemperature() { return this.maxIdealTemp; } 
/*  34 */   public void setMaximumIdealTemperature(double temp) { if (temp >= this.minIdealTemp) this.maxIdealTemp = temp;  } 
/*  35 */   public double getMinimumOutputHeat() { return this.minOutputHeat; } 
/*  36 */   public void setMinimumOutputHeat(double temp) { if (temp <= this.maxOutputHeat) this.minOutputHeat = temp;  } 
/*  37 */   public double getMaximumOutputHeat() { return this.maxOutputHeat; } 
/*  38 */   public void setMaximumOutputHeat(double temp) { if (temp >= this.minOutputHeat) this.maxOutputHeat = temp;  } 
/*  39 */   public Object domEvaporationConstant() { return new Interval(0.0D, 1.0D); } 
/*  40 */   public double getEvaporationConstant() { return this.evaporationRate; } 
/*  41 */   public void setEvaporationConstant(double temp) { if ((temp >= 0.0D) && (temp <= 1.0D)) this.evaporationRate = temp;  } 
/*  42 */   public Object domDiffusionConstant() { return new Interval(0.0D, 1.0D); } 
/*  43 */   public double getDiffusionConstant() { return this.diffusionRate; } 
/*  44 */   public void setDiffusionConstant(double temp) { if ((temp >= 0.0D) && (temp <= 1.0D)) this.diffusionRate = temp;  } 
/*  45 */   public Object domRandomMovementProbability() { return new Interval(0.0D, 1.0D); } 
/*  46 */   public double getRandomMovementProbability() { return this.randomMovementProbability; }
/*     */ 
/*     */   public void setRandomMovementProbability(double t) {
/*  49 */     if ((t >= 0.0D) && (t <= 1.0D))
/*     */     {
/*  51 */       this.randomMovementProbability = t;
/*  52 */       for (int i = 0; i < this.bugCount; i++)
/*  53 */         if (this.bugs[i] != null)
/*  54 */           this.bugs[i].setRandomMovementProbability(this.randomMovementProbability);  
/*     */     }
/*     */   }
/*     */ 
/*  57 */   public double getMaximumHeat() { return 32000.0D; } 
/*  58 */   public int getGridHeight() { return this.gridHeight; } 
/*  59 */   public int getGridWidth() { return this.gridWidth; } 
/*  60 */   public int getBugCount() { return this.bugCount; } 
/*  61 */   public void setBugCount(int val) { if (val >= 0) this.bugCount = val;
/*     */   }
/*     */ 
/*     */   public HexaBugs(long seed)
/*     */   {
/*  77 */     super(seed);
/*  78 */     this.bugs = new HexaBug[this.bugCount];
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  86 */     super.start();
/*     */ 
/*  89 */     this.valgrid = new DoubleGrid2D(this.gridWidth, this.gridHeight, 0.0D);
/*  90 */     this.valgrid2 = new DoubleGrid2D(this.gridWidth, this.gridHeight, 0.0D);
/*  91 */     this.buggrid = new SparseGrid2D(this.gridWidth, this.gridHeight);
/*  92 */     this.bugs = new HexaBug[this.bugCount];
/*     */ 
/*  96 */     for (int x = 0; x < this.bugCount; x++)
/*     */     {
/*  98 */       this.bugs[x] = new HexaBug(this.random.nextDouble() * (this.maxIdealTemp - this.minIdealTemp) + this.minIdealTemp, this.random.nextDouble() * (this.maxOutputHeat - this.minOutputHeat) + this.minOutputHeat, 32000.0D, this.randomMovementProbability);
/*     */ 
/* 101 */       this.buggrid.setObjectLocation(this.bugs[x], this.random.nextInt(this.gridWidth), this.random.nextInt(this.gridHeight));
/* 102 */       this.schedule.scheduleRepeating(this.bugs[x]);
/*     */     }
/*     */ 
/* 106 */     if (availableProcessors() > 1)
/*     */     {
/* 109 */       this.diffuser = new ThreadedHexaDiffuser(this.valgrid, this.valgrid2, this.evaporationRate, this.diffusionRate);
/* 110 */       this.schedule.scheduleRepeating(0.0D, 1, this.diffuser, 1.0D);
/*     */     }
/*     */     else {
/* 113 */       this.schedule.scheduleRepeating(0.0D, 1, new HexaDiffuser(this.valgrid, this.valgrid2, this.evaporationRate, this.diffusionRate), 1.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void stop() {
/* 118 */     if (this.diffuser != null) this.diffuser.cleanup();
/* 119 */     this.diffuser = null;
/*     */   }
/*     */ 
/*     */   public int availableProcessors()
/*     */   {
/* 124 */     Runtime runtime = Runtime.getRuntime();
/*     */     try { return ((Integer)runtime.getClass().getMethod("availableProcessors", (Class[])null).invoke(runtime, (Object[])null)).intValue(); } catch (Exception e) {
/*     */     }
/* 127 */     return 1;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 132 */     doLoop(HexaBugs.class, args);
/* 133 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.hexabugs.HexaBugs
 * JD-Core Version:    0.6.2
 */