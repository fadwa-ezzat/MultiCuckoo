/*    */ package sim.app.schelling;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.grid.IntGrid2D;
/*    */ import sim.util.Bag;
/*    */ import sim.util.Int2D;
/*    */ import sim.util.IntBag;
/*    */ 
/*    */ public class Agent
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   Int2D loc;
/* 16 */   IntBag neighborsX = new IntBag(9);
/* 17 */   IntBag neighborsY = new IntBag(9);
/*    */ 
/*    */   public Agent(int x, int y)
/*    */   {
/* 21 */     this.loc = new Int2D(x, y);
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 26 */     Schelling sch = (Schelling)state;
/* 27 */     int[][] locs = sch.neighbors.field;
/* 28 */     int x = this.loc.x;
/* 29 */     int y = this.loc.y;
/*    */ 
/* 31 */     if (locs[x][y] < 2) return;
/* 32 */     if (sch.emptySpaces.numObjs == 0) return;
/*    */ 
/* 35 */     sch.neighbors.getMooreLocations(this.loc.x, this.loc.y, sch.neighborhood, 0, true, this.neighborsX, this.neighborsY);
/*    */ 
/* 38 */     double val = 0.0D;
/* 39 */     int threshold = sch.threshold;
/* 40 */     int numObjs = this.neighborsX.numObjs;
/* 41 */     int[] objsX = this.neighborsX.objs;
/* 42 */     int[] objsY = this.neighborsY.objs;
/* 43 */     int myVal = locs[x][y];
/*    */ 
/* 45 */     for (int i = 0; i < numObjs; i++)
/*    */     {
/* 47 */       if ((locs[objsX[i]][objsY[i]] == myVal) && ((objsX[i] != x) || (objsY[i] != y)))
/*    */       {
/* 50 */         val += 1.0D / Math.sqrt((x - objsX[i]) * (x - objsX[i]) + (y - objsY[i]) * (y - objsY[i]));
/* 51 */         if (val >= threshold) return;
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 56 */     int newLocIndex = state.random.nextInt(sch.emptySpaces.numObjs);
/* 57 */     Int2D newLoc = (Int2D)sch.emptySpaces.objs[newLocIndex];
/* 58 */     sch.emptySpaces.objs[newLocIndex] = this.loc;
/*    */ 
/* 61 */     int swap = locs[newLoc.x][newLoc.y];
/* 62 */     locs[newLoc.x][newLoc.y] = locs[this.loc.x][this.loc.y];
/* 63 */     locs[this.loc.x][this.loc.y] = swap;
/*    */ 
/* 65 */     this.loc = newLoc;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.schelling.Agent
 * JD-Core Version:    0.6.2
 */