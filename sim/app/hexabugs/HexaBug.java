/*    */ package sim.app.hexabugs;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.grid.DoubleGrid2D;
/*    */ import sim.field.grid.SparseGrid2D;
/*    */ import sim.util.DoubleBag;
/*    */ import sim.util.Int2D;
/*    */ import sim.util.IntBag;
/*    */ 
/*    */ public class HexaBug
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public double idealTemp;
/*    */   public double heatOutput;
/*    */   public double maxHeat;
/*    */   public double randomMovementProbability;
/*    */ 
/*    */   public double getIdealTemperature()
/*    */   {
/* 17 */     return this.idealTemp; } 
/* 18 */   public void setIdealTemperature(double t) { this.idealTemp = t; }
/*    */ 
/*    */   public double getHeatOutput() {
/* 21 */     return this.heatOutput; } 
/* 22 */   public void setHeatOutput(double t) { this.heatOutput = t; }
/*    */ 
/*    */   public double getMaximumHeat() {
/* 25 */     return this.maxHeat; } 
/* 26 */   public void setMaximumHeat(double t) { this.maxHeat = t; }
/*    */ 
/*    */   public double getRandomMovementProbability() {
/* 29 */     return this.randomMovementProbability; } 
/* 30 */   public void setRandomMovementProbability(double t) { this.randomMovementProbability = t; }
/*    */ 
/*    */   public HexaBug(double idealTemp, double heatOutput, double maxHeat, double randomMovementProbability)
/*    */   {
/* 34 */     this.heatOutput = heatOutput;
/* 35 */     this.idealTemp = idealTemp;
/* 36 */     this.maxHeat = maxHeat;
/* 37 */     this.randomMovementProbability = randomMovementProbability;
/*    */   }
/*    */ 
/*    */   public void addHeat(DoubleGrid2D grid, int x, int y, double Heat)
/*    */   {
/* 42 */     grid.field[x][y] += Heat;
/* 43 */     if (grid.field[x][y] > this.maxHeat) grid.field[x][y] = this.maxHeat;
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 48 */     HexaBugs hb = (HexaBugs)state;
/* 49 */     DoubleBag neighVal = hb.neighVal;
/* 50 */     IntBag neighX = hb.neighX;
/* 51 */     IntBag neighY = hb.neighY;
/*    */ 
/* 53 */     Int2D location = hb.buggrid.getObjectLocation(this);
/* 54 */     int myx = location.x;
/* 55 */     int myy = location.y;
/*    */ 
/* 57 */     int START = -1;
/* 58 */     int bestx = -1;
/* 59 */     int besty = 0;
/*    */ 
/* 61 */     hb.valgrid.getHexagonalNeighbors(myx, myy, 1, 2, true, neighVal, neighX, neighY);
/*    */ 
/* 63 */     if (state.random.nextBoolean(this.randomMovementProbability))
/*    */     {
/* 65 */       int temp_random = state.random.nextInt(neighX.numObjs);
/* 66 */       bestx = neighX.objs[temp_random];
/* 67 */       besty = neighY.objs[temp_random];
/*    */     }
/* 69 */     else if (hb.valgrid.field[myx][myy] > this.idealTemp)
/*    */     {
/* 71 */       for (int i = 0; i < neighX.numObjs; i++)
/* 72 */         if ((neighX.objs[i] != myx) || (neighY.objs[i] != myy))
/*    */         {
/* 75 */           if ((bestx == -1) || (neighVal.objs[i] < hb.valgrid.field[bestx][besty]) || ((neighVal.objs[i] == hb.valgrid.field[bestx][besty]) && (state.random.nextBoolean())))
/*    */           {
/* 78 */             bestx = neighX.objs[i]; besty = neighY.objs[i];
/*    */           }
/*    */         }
/* 81 */     } else if (hb.valgrid.field[myx][myy] < this.idealTemp)
/*    */     {
/* 83 */       for (int i = 0; i < neighX.numObjs; i++)
/* 84 */         if ((neighX.objs[i] != myx) || (neighY.objs[i] != myy))
/*    */         {
/* 86 */           if ((bestx == -1) || (neighVal.objs[i] > hb.valgrid.field[bestx][besty]) || ((neighVal.objs[i] > hb.valgrid.field[bestx][besty]) && (state.random.nextBoolean())))
/*    */           {
/* 89 */             bestx = neighX.objs[i]; besty = neighY.objs[i];
/*    */           }
/*    */         }
/*    */     }
/*    */     else {
/* 94 */       bestx = myx;
/* 95 */       besty = myy;
/*    */     }
/*    */ 
/* 98 */     hb.buggrid.setObjectLocation(this, bestx, besty);
/* 99 */     addHeat(hb.valgrid, bestx, besty, this.heatOutput);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.hexabugs.HexaBug
 * JD-Core Version:    0.6.2
 */