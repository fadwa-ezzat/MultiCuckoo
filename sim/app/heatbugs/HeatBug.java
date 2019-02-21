/*     */ package sim.app.heatbugs;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ import sim.field.grid.SparseGrid2D;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.Interval;
/*     */ 
/*     */ public class HeatBug
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public double idealTemp;
/*     */   public double heatOutput;
/*     */   public double randomMovementProbability;
/*     */ 
/*     */   public double getIdealTemperature()
/*     */   {
/*  17 */     return this.idealTemp; } 
/*  18 */   public void setIdealTemperature(double t) { this.idealTemp = t; }
/*     */ 
/*     */   public double getHeatOutput() {
/*  21 */     return this.heatOutput; } 
/*  22 */   public void setHeatOutput(double t) { this.heatOutput = t; }
/*     */ 
/*     */   public double getRandomMovementProbability() {
/*  25 */     return this.randomMovementProbability; } 
/*  26 */   public void setRandomMovementProbability(double t) { if ((t >= 0.0D) && (t <= 1.0D)) this.randomMovementProbability = t;  } 
/*  27 */   public Object domRandomMovementProbability() { return new Interval(0.0D, 1.0D); }
/*     */ 
/*     */   public HeatBug(double idealTemp, double heatOutput, double randomMovementProbability)
/*     */   {
/*  31 */     this.heatOutput = heatOutput;
/*  32 */     this.idealTemp = idealTemp;
/*  33 */     this.randomMovementProbability = randomMovementProbability;
/*     */   }
/*     */ 
/*     */   public void addHeat(DoubleGrid2D grid, int x, int y, double heat)
/*     */   {
/*  38 */     grid.field[x][y] += heat;
/*  39 */     if (grid.field[x][y] > 32000.0D) grid.field[x][y] = 32000.0D;
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/*  44 */     HeatBugs hb = (HeatBugs)state;
/*     */ 
/*  46 */     Int2D location = hb.buggrid.getObjectLocation(this);
/*  47 */     int myx = location.x;
/*  48 */     int myy = location.y;
/*     */ 
/*  50 */     int START = -1;
/*  51 */     int bestx = -1;
/*  52 */     int besty = 0;
/*     */ 
/*  54 */     if (state.random.nextBoolean(this.randomMovementProbability))
/*     */     {
/*  56 */       bestx = hb.buggrid.stx(state.random.nextInt(3) - 1 + myx);
/*  57 */       besty = hb.buggrid.sty(state.random.nextInt(3) - 1 + myy);
/*     */     }
/*  59 */     else if (hb.valgrid.field[myx][myy] > this.idealTemp)
/*     */     {
/*  61 */       for (int x = -1; x < 2; x++)
/*  62 */         for (int y = -1; y < 2; y++)
/*  63 */           if ((x != 0) || (y != 0))
/*     */           {
/*  65 */             int xx = hb.buggrid.stx(x + myx);
/*  66 */             int yy = hb.buggrid.sty(y + myy);
/*  67 */             if ((bestx == -1) || (hb.valgrid.field[xx][yy] < hb.valgrid.field[bestx][besty]) || ((hb.valgrid.field[xx][yy] == hb.valgrid.field[bestx][besty]) && (state.random.nextBoolean())))
/*     */             {
/*  70 */               bestx = xx; besty = yy;
/*     */             }
/*     */           }
/*  73 */     } else if (hb.valgrid.field[myx][myy] < this.idealTemp)
/*     */     {
/*  75 */       for (int x = -1; x < 2; x++)
/*  76 */         for (int y = -1; y < 2; y++)
/*  77 */           if ((x != 0) || (y != 0))
/*     */           {
/*  79 */             int xx = hb.buggrid.stx(x + myx);
/*  80 */             int yy = hb.buggrid.sty(y + myy);
/*  81 */             if ((bestx == -1) || (hb.valgrid.field[xx][yy] > hb.valgrid.field[bestx][besty]) || ((hb.valgrid.field[xx][yy] == hb.valgrid.field[bestx][besty]) && (state.random.nextBoolean())))
/*     */             {
/*  84 */               bestx = xx; besty = yy;
/*     */             }
/*     */           }
/*     */     }
/*     */     else {
/*  89 */       bestx = myx;
/*  90 */       besty = myy;
/*     */     }
/*     */ 
/*  93 */     hb.buggrid.setObjectLocation(this, bestx, besty);
/*  94 */     addHeat(hb.valgrid, bestx, besty, this.heatOutput);
/*     */   }
/*     */ 
/*     */   public String[] provideTabNames()
/*     */   {
/* 101 */     return new String[] { "Heat", "Probability" };
/*     */   }
/* 103 */   public String[][] provideTabProperties() { return new String[][] { { "IdealTemperature", "HeatOutput" }, { "RandomMovementProbability", "Me" } }; }
/*     */ 
/*     */   public String provideExtraTab() {
/* 106 */     return null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.heatbugs.HeatBug
 * JD-Core Version:    0.6.2
 */