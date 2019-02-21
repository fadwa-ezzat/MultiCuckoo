/*     */ package sim.app.schelling;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.grid.IntGrid2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.Interval;
/*     */ 
/*     */ public class Schelling extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public int gridHeight;
/*     */   public int gridWidth;
/*  18 */   public int neighborhood = 1;
/*  19 */   public int threshold = 3;
/*  20 */   public double redProbability = 0.3D;
/*  21 */   public double blueProbability = 0.3D;
/*  22 */   public double emptyProbability = 0.3D;
/*  23 */   public double unavailableProbability = 0.1D;
/*     */   public IntGrid2D neighbors;
/*  82 */   public Bag emptySpaces = new Bag();
/*     */   public static final int EMPTY = 0;
/*     */   public static final int UNAVAILABLE = 1;
/*     */   public static final int RED = 2;
/*     */   public static final int BLUE = 3;
/*     */ 
/*     */   public int getGridHeight()
/*     */   {
/*  26 */     return this.gridHeight; } 
/*  27 */   public void setGridHeight(int val) { if (val > 0) this.gridHeight = val;  } 
/*  28 */   public int getGridWidth() { return this.gridWidth; } 
/*  29 */   public void setGridWidth(int val) { if (val > 0) this.gridWidth = val;  } 
/*  30 */   public int getNeighborhood() { return this.neighborhood; } 
/*  31 */   public void setNeighborhood(int val) { if (val > 0) this.neighborhood = val;  } 
/*  32 */   public int getThreshold() { return this.threshold; } 
/*  33 */   public void setThreshold(int val) { if (val >= 0) this.threshold = val;  } 
/*     */   public Object domRedProbability()
/*     */   {
/*  36 */     return new Interval(0.0D, 1.0D); } 
/*  37 */   public double getRedProbability() { return this.redProbability; }
/*     */ 
/*     */   public void setRedProbability(double val) {
/*  40 */     if ((val >= 0.0D) && (val <= 1.0D))
/*     */     {
/*  42 */       this.redProbability = val;
/*     */     }
/*     */   }
/*     */ 
/*  46 */   public Object domBlueProbability() { return new Interval(0.0D, 1.0D); } 
/*  47 */   public double getBlueProbability() { return this.blueProbability; }
/*     */ 
/*     */   public void setBlueProbability(double val) {
/*  50 */     if ((val >= 0.0D) && (val <= 1.0D))
/*     */     {
/*  52 */       this.blueProbability = val;
/*     */     }
/*     */   }
/*     */ 
/*  56 */   public Object domEmptyProbability() { return new Interval(0.0D, 1.0D); } 
/*  57 */   public double getEmptyProbability() { return this.emptyProbability; }
/*     */ 
/*     */   public void setEmptyProbability(double val) {
/*  60 */     if ((val >= 0.0D) && (val <= 1.0D))
/*     */     {
/*  62 */       this.emptyProbability = val;
/*     */     }
/*     */   }
/*     */ 
/*  66 */   public Object domUnavailableProbability() { return new Interval(0.0D, 1.0D); } 
/*  67 */   public double getUnavailableProbability() { return this.unavailableProbability; }
/*     */ 
/*     */   public void setUnavailableProbability(double val) {
/*  70 */     if ((val >= 0.0D) && (val <= 1.0D))
/*     */     {
/*  72 */       this.unavailableProbability = val;
/*  73 */       double total = this.redProbability + this.blueProbability + this.emptyProbability;
/*  74 */       if (total == 0.0D) total = 1.0D;
/*  75 */       this.redProbability *= (1.0D - this.unavailableProbability) / total;
/*  76 */       this.blueProbability *= (1.0D - this.unavailableProbability) / total;
/*  77 */       this.emptyProbability *= (1.0D - this.unavailableProbability) / total;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Schelling(long seed)
/*     */   {
/*  91 */     this(seed, 100, 100);
/*     */   }
/*     */ 
/*     */   public Schelling(long seed, int width, int height)
/*     */   {
/*  96 */     super(seed);
/*  97 */     this.gridWidth = width; this.gridHeight = height;
/*  98 */     createGrids();
/*     */   }
/*     */ 
/*     */   protected void createGrids()
/*     */   {
/* 103 */     this.emptySpaces.clear();
/* 104 */     this.neighbors = new IntGrid2D(this.gridWidth, this.gridHeight, 0);
/* 105 */     int[][] g = this.neighbors.field;
/* 106 */     for (int x = 0; x < this.gridWidth; x++)
/* 107 */       for (int y = 0; y < this.gridHeight; y++)
/*     */       {
/* 109 */         double d = this.random.nextDouble();
/* 110 */         if (d < this.redProbability) { g[x][y] = 2;
/* 111 */         } else if (d < this.redProbability + this.blueProbability) { g[x][y] = 3;
/* 112 */         } else if (d < this.redProbability + this.blueProbability + this.emptyProbability) {
/* 113 */           g[x][y] = 0; this.emptySpaces.add(new Int2D(x, y)); } else {
/* 114 */           g[x][y] = 1;
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/* 121 */     super.start();
/*     */ 
/* 124 */     createGrids();
/* 125 */     for (int x = 0; x < this.gridWidth; x++)
/* 126 */       for (int y = 0; y < this.gridHeight; y++)
/*     */       {
/* 128 */         this.schedule.scheduleRepeating(new Agent(x, y));
/*     */       }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 134 */     doLoop(Schelling.class, args);
/* 135 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.schelling.Schelling
 * JD-Core Version:    0.6.2
 */