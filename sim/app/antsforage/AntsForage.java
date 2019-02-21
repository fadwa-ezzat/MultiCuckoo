/*     */ package sim.app.antsforage;
/*     */ 
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ import sim.field.grid.IntGrid2D;
/*     */ import sim.field.grid.SparseGrid2D;
/*     */ import sim.util.Interval;
/*     */ 
/*     */ public class AntsForage extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int GRID_HEIGHT = 100;
/*     */   public static final int GRID_WIDTH = 100;
/*     */   public static final int HOME_XMIN = 75;
/*     */   public static final int HOME_XMAX = 75;
/*     */   public static final int HOME_YMIN = 75;
/*     */   public static final int HOME_YMAX = 75;
/*     */   public static final int FOOD_XMIN = 25;
/*     */   public static final int FOOD_XMAX = 25;
/*     */   public static final int FOOD_YMIN = 25;
/*     */   public static final int FOOD_YMAX = 25;
/*     */   public static final int NO_OBSTACLES = 0;
/*     */   public static final int ONE_OBSTACLE = 1;
/*     */   public static final int TWO_OBSTACLES = 2;
/*     */   public static final int ONE_LONG_OBSTACLE = 3;
/*     */   public static final int OBSTACLES = 2;
/*     */   public static final int ALGORITHM_VALUE_ITERATION = 1;
/*     */   public static final int ALGORITHM_TEMPORAL_DIFERENCE = 2;
/*     */   public static final int ALGORITHM = 1;
/*     */   public static final double IMPOSSIBLY_BAD_PHEROMONE = -1.0D;
/*     */   public static final double LIKELY_MAX_PHEROMONE = 3.0D;
/*     */   public static final int HOME = 1;
/*     */   public static final int FOOD = 2;
/*  49 */   public int numAnts = 1000;
/*  50 */   public double evaporationConstant = 0.999D;
/*  51 */   public double reward = 1.0D;
/*  52 */   public double updateCutDown = 0.9D;
/*  53 */   public double diagonalCutDown = computeDiagonalCutDown();
/*     */ 
/*  55 */   public double momentumProbability = 0.8D;
/*  56 */   public double randomActionProbability = 0.1D;
/*     */ 
/*  82 */   public IntGrid2D sites = new IntGrid2D(100, 100, 0);
/*  83 */   public DoubleGrid2D toFoodGrid = new DoubleGrid2D(100, 100, 0.0D);
/*  84 */   public DoubleGrid2D toHomeGrid = new DoubleGrid2D(100, 100, 0.0D);
/*  85 */   public SparseGrid2D buggrid = new SparseGrid2D(100, 100);
/*  86 */   public IntGrid2D obstacles = new IntGrid2D(100, 100, 0);
/*     */ 
/*     */   public double computeDiagonalCutDown()
/*     */   {
/*  54 */     return Math.pow(this.updateCutDown, Math.sqrt(2.0D));
/*     */   }
/*     */ 
/*     */   public int getNumAnts()
/*     */   {
/*  60 */     return this.numAnts; } 
/*  61 */   public void setNumAnts(int val) { if (val > 0) this.numAnts = val;  } 
/*     */   public double getEvaporationConstant() {
/*  63 */     return this.evaporationConstant; } 
/*  64 */   public void setEvaporationConstant(double val) { if ((val >= 0.0D) && (val <= 1.0D)) this.evaporationConstant = val;  } 
/*     */   public double getReward() {
/*  66 */     return this.reward; } 
/*  67 */   public void setReward(double val) { if (val >= 0.0D) this.reward = val;  } 
/*     */   public double getCutDown() {
/*  69 */     return this.updateCutDown; } 
/*  70 */   public void setCutDown(double val) { if ((val >= 0.0D) && (val <= 1.0D)) this.updateCutDown = val; this.diagonalCutDown = computeDiagonalCutDown(); } 
/*  71 */   public Object domCutDown() { return new Interval(0.0D, 1.0D); } 
/*     */   public double getMomentumProbability() {
/*  73 */     return this.momentumProbability; } 
/*  74 */   public void setMomentumProbability(double val) { if ((val >= 0.0D) && (val <= 1.0D)) this.momentumProbability = val;  } 
/*  75 */   public Object domMomentumProbability() { return new Interval(0.0D, 1.0D); } 
/*     */   public double getRandomActionProbability() {
/*  77 */     return this.randomActionProbability; } 
/*  78 */   public void setRandomActionProbability(double val) { if ((val >= 0.0D) && (val <= 1.0D)) this.randomActionProbability = val;  } 
/*  79 */   public Object domRandomActionProbability() { return new Interval(0.0D, 1.0D); }
/*     */ 
/*     */ 
/*     */   public AntsForage(long seed)
/*     */   {
/*  90 */     super(seed);
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  95 */     super.start();
/*     */ 
/*  98 */     this.sites = new IntGrid2D(100, 100, 0);
/*  99 */     this.toFoodGrid = new DoubleGrid2D(100, 100, 0.0D);
/* 100 */     this.toHomeGrid = new DoubleGrid2D(100, 100, 0.0D);
/*     */ 
/* 102 */     this.buggrid = new SparseGrid2D(100, 100);
/* 103 */     this.obstacles = new IntGrid2D(100, 100, 0);
/*     */ 
/* 105 */     switch (2)
/*     */     {
/*     */     case 0:
/* 108 */       break;
/*     */     case 1:
/* 110 */       for (int x = 0; x < 100; x++)
/* 111 */         for (int y = 0; y < 100; y++)
/*     */         {
/* 113 */           this.obstacles.field[x][y] = 0;
/* 114 */           if (((x - 55) * 0.707D + (y - 35) * 0.707D) * ((x - 55) * 0.707D + (y - 35) * 0.707D) / 36.0D + ((x - 55) * 0.707D - (y - 35) * 0.707D) * ((x - 55) * 0.707D - (y - 35) * 0.707D) / 1024.0D <= 1.0D)
/*     */           {
/* 116 */             this.obstacles.field[x][y] = 1;
/*     */           }
/*     */         }
/* 118 */       break;
/*     */     case 2:
/* 120 */       for (int x = 0; x < 100; x++)
/* 121 */         for (int y = 0; y < 100; y++)
/*     */         {
/* 123 */           this.obstacles.field[x][y] = 0;
/* 124 */           if (((x - 45) * 0.707D + (y - 25) * 0.707D) * ((x - 45) * 0.707D + (y - 25) * 0.707D) / 36.0D + ((x - 45) * 0.707D - (y - 25) * 0.707D) * ((x - 45) * 0.707D - (y - 25) * 0.707D) / 1024.0D <= 1.0D)
/*     */           {
/* 126 */             this.obstacles.field[x][y] = 1;
/* 127 */           }if (((x - 35) * 0.707D + (y - 70) * 0.707D) * ((x - 35) * 0.707D + (y - 70) * 0.707D) / 36.0D + ((x - 35) * 0.707D - (y - 70) * 0.707D) * ((x - 35) * 0.707D - (y - 70) * 0.707D) / 1024.0D <= 1.0D)
/*     */           {
/* 129 */             this.obstacles.field[x][y] = 1;
/*     */           }
/*     */         }
/* 131 */       break;
/*     */     case 3:
/* 133 */       for (int x = 0; x < 100; x++) {
/* 134 */         for (int y = 0; y < 100; y++)
/*     */         {
/* 136 */           this.obstacles.field[x][y] = 0;
/* 137 */           if ((x - 60) * (x - 60) / 1600 + (y - 50) * (y - 50) / 25 <= 1)
/*     */           {
/* 139 */             this.obstacles.field[x][y] = 1;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 145 */     for (int x = 75; x <= 75; x++)
/* 146 */       for (int y = 75; y <= 75; y++)
/* 147 */         this.sites.field[x][y] = 1;
/* 148 */     for (int x = 25; x <= 25; x++) {
/* 149 */       for (int y = 25; y <= 25; y++)
/* 150 */         this.sites.field[x][y] = 2;
/*     */     }
/* 152 */     for (int x = 0; x < this.numAnts; x++)
/*     */     {
/* 154 */       Ant ant = new Ant(this.reward);
/* 155 */       this.buggrid.setObjectLocation(ant, 75, 75);
/* 156 */       this.schedule.scheduleRepeating(0.0D + x, 0, ant, 1.0D);
/*     */     }
/*     */ 
/* 160 */     this.schedule.scheduleRepeating(0.0D, 1, new Steppable() {
/*     */       public void step(SimState state) {
/* 162 */         AntsForage.this.toFoodGrid.multiply(AntsForage.this.evaporationConstant); AntsForage.this.toHomeGrid.multiply(AntsForage.this.evaporationConstant);
/*     */       }
/*     */     }
/*     */     , 1.0D);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 169 */     doLoop(AntsForage.class, args);
/* 170 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.antsforage.AntsForage
 * JD-Core Version:    0.6.2
 */