/*     */ package sim.app.heatbugs;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.lang.reflect.Method;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ import sim.field.grid.SparseGrid2D;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.Interval;
/*     */ 
/*     */ public class HeatBugs extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  17 */   public double minIdealTemp = 17000.0D;
/*  18 */   public double maxIdealTemp = 31000.0D;
/*  19 */   public double minOutputHeat = 6000.0D;
/*  20 */   public double maxOutputHeat = 10000.0D;
/*     */ 
/*  22 */   public double evaporationRate = 0.993D;
/*  23 */   public double diffusionRate = 1.0D;
/*     */   public static final double MAX_HEAT = 32000.0D;
/*  25 */   public double randomMovementProbability = 0.1D;
/*     */   public int gridHeight;
/*     */   public int gridWidth;
/*     */   public int bugCount;
/*     */   HeatBug[] bugs;
/*     */   public DoubleGrid2D valgrid;
/*     */   public DoubleGrid2D valgrid2;
/*     */   public SparseGrid2D buggrid;
/* 122 */   ThreadedDiffuser diffuser = null;
/*     */ 
/*     */   public double getMinimumIdealTemperature()
/*     */   {
/*  32 */     return this.minIdealTemp; } 
/*  33 */   public void setMinimumIdealTemperature(double temp) { if (temp <= this.maxIdealTemp) this.minIdealTemp = temp;  } 
/*  34 */   public double getMaximumIdealTemperature() { return this.maxIdealTemp; } 
/*  35 */   public void setMaximumIdealTemperature(double temp) { if (temp >= this.minIdealTemp) this.maxIdealTemp = temp;  } 
/*  36 */   public double getMinimumOutputHeat() { return this.minOutputHeat; } 
/*  37 */   public void setMinimumOutputHeat(double temp) { if (temp <= this.maxOutputHeat) this.minOutputHeat = temp;  } 
/*  38 */   public double getMaximumOutputHeat() { return this.maxOutputHeat; } 
/*  39 */   public void setMaximumOutputHeat(double temp) { if (temp >= this.minOutputHeat) this.maxOutputHeat = temp;  } 
/*  40 */   public double getEvaporationConstant() { return this.evaporationRate; } 
/*  41 */   public void setEvaporationConstant(double temp) { if ((temp >= 0.0D) && (temp <= 1.0D)) this.evaporationRate = temp;  } 
/*  42 */   public Object domEvaporationConstant() { return new Interval(0.0D, 1.0D); } 
/*  43 */   public double getDiffusionConstant() { return this.diffusionRate; } 
/*  44 */   public void setDiffusionConstant(double temp) { if ((temp >= 0.0D) && (temp <= 1.0D)) this.diffusionRate = temp;  } 
/*  45 */   public Object domDiffusionConstant() { return new Interval(0.0D, 1.0D); } 
/*  46 */   public double getRandomMovementProbability() { return this.randomMovementProbability; }
/*     */ 
/*     */   public double[] getBugXPos()
/*     */   {
/*     */     try {
/*  51 */       double[] d = new double[this.bugs.length];
/*  52 */       for (int x = 0; x < this.bugs.length; x++)
/*     */       {
/*  54 */         d[x] = this.buggrid.getObjectLocation(this.bugs[x]).x;
/*     */       }
/*  56 */       return d; } catch (Exception e) {
/*     */     }
/*  58 */     return new double[0];
/*     */   }
/*     */ 
/*     */   public double[] getBugYPos()
/*     */   {
/*     */     try {
/*  64 */       double[] d = new double[this.bugs.length];
/*  65 */       for (int x = 0; x < this.bugs.length; x++)
/*     */       {
/*  67 */         d[x] = this.buggrid.getObjectLocation(this.bugs[x]).y;
/*     */       }
/*  69 */       return d; } catch (Exception e) {
/*     */     }
/*  71 */     return new double[0];
/*     */   }
/*     */ 
/*     */   public void setRandomMovementProbability(double t)
/*     */   {
/*  77 */     if ((t >= 0.0D) && (t <= 1.0D))
/*     */     {
/*  79 */       this.randomMovementProbability = t;
/*  80 */       for (int i = 0; i < this.bugCount; i++)
/*  81 */         if (this.bugs[i] != null)
/*  82 */           this.bugs[i].setRandomMovementProbability(this.randomMovementProbability);  
/*     */     }
/*     */   }
/*     */ 
/*  85 */   public Object domRandomMovementProbability() { return new Interval(0.0D, 1.0D); } 
/*     */   public double getMaximumHeat() {
/*  87 */     return 32000.0D;
/*     */   }
/*     */   public int getGridHeight() {
/*  90 */     return this.gridHeight; } 
/*  91 */   public void setGridHeight(int val) { if (val > 0) this.gridHeight = val;  } 
/*  92 */   public int getGridWidth() { return this.gridWidth; } 
/*  93 */   public void setGridWidth(int val) { if (val > 0) this.gridWidth = val;  } 
/*  94 */   public int getBugCount() { return this.bugCount; } 
/*  95 */   public void setBugCount(int val) { if (val >= 0) this.bugCount = val;
/*     */   }
/*     */ 
/*     */   public HeatBugs(long seed)
/*     */   {
/* 104 */     this(seed, 100, 100, 100);
/*     */   }
/*     */ 
/*     */   public HeatBugs(long seed, int width, int height, int count)
/*     */   {
/* 109 */     super(seed);
/* 110 */     this.gridWidth = width; this.gridHeight = height; this.bugCount = count;
/* 111 */     createGrids();
/*     */   }
/*     */ 
/*     */   protected void createGrids()
/*     */   {
/* 116 */     this.bugs = new HeatBug[this.bugCount];
/* 117 */     this.valgrid = new DoubleGrid2D(this.gridWidth, this.gridHeight, 0.0D);
/* 118 */     this.valgrid2 = new DoubleGrid2D(this.gridWidth, this.gridHeight, 0.0D);
/* 119 */     this.buggrid = new SparseGrid2D(this.gridWidth, this.gridHeight);
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/* 127 */     super.start();
/*     */ 
/* 130 */     createGrids();
/*     */ 
/* 134 */     for (int x = 0; x < this.bugCount; x++)
/*     */     {
/* 136 */       this.bugs[x] = new HeatBug(this.random.nextDouble() * (this.maxIdealTemp - this.minIdealTemp) + this.minIdealTemp, this.random.nextDouble() * (this.maxOutputHeat - this.minOutputHeat) + this.minOutputHeat, this.randomMovementProbability);
/*     */ 
/* 139 */       this.buggrid.setObjectLocation(this.bugs[x], this.random.nextInt(this.gridWidth), this.random.nextInt(this.gridHeight));
/* 140 */       this.schedule.scheduleRepeating(this.bugs[x]);
/*     */     }
/*     */ 
/* 150 */     if (availableProcessors() > 1)
/*     */     {
/* 153 */       this.diffuser = new ThreadedDiffuser(2);
/* 154 */       this.schedule.scheduleRepeating(0.0D, 1, this.diffuser, 1.0D);
/*     */     }
/*     */     else {
/* 157 */       this.schedule.scheduleRepeating(0.0D, 1, new Diffuser(), 1.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void stop() {
/* 162 */     if (this.diffuser != null) this.diffuser.cleanup();
/* 163 */     this.diffuser = null;
/*     */   }
/*     */ 
/*     */   public static int availableProcessors()
/*     */   {
/* 174 */     Runtime runtime = Runtime.getRuntime();
/*     */     try { return ((Integer)runtime.getClass().getMethod("availableProcessors", (Class[])null).invoke(runtime, (Object[])null)).intValue(); } catch (Exception e) {
/*     */     }
/* 177 */     return 1;
/*     */   }
/*     */ 
/*     */   public String[] provideTabNames()
/*     */   {
/* 185 */     return new String[] { "Temps", "Constants", "Pos" };
/*     */   }
/* 187 */   public String[][] provideTabProperties() { return new String[][] { { "MinimumIdealTemperature", "MaximumIdealTemperature", "MinimumOutputHeat", "MaximumOutputHeat", "MaximumHeat" }, { "EvaporationConstant", "DiffusionConstant", "RandomMovementProbability" }, { "BugXPos", "BugYPos" } }; }
/*     */ 
/*     */   public String provideExtraTab()
/*     */   {
/* 191 */     return "Misc";
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 198 */     doLoop(HeatBugs.class, args);
/* 199 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.heatbugs.HeatBugs
 * JD-Core Version:    0.6.2
 */