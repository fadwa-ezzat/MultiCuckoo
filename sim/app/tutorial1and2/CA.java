/*    */ package sim.app.tutorial1and2;
/*    */ 
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.grid.IntGrid2D;
/*    */ 
/*    */ public class CA
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 17 */   public IntGrid2D tempGrid = new IntGrid2D(0, 0);
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 21 */     Tutorial1 tut = (Tutorial1)state;
/*    */ 
/* 23 */     this.tempGrid.setTo(tut.grid);
/*    */ 
/* 29 */     int width = this.tempGrid.getWidth();
/* 30 */     int height = this.tempGrid.getHeight();
/* 31 */     for (int x = 0; x < width; x++)
/* 32 */       for (int y = 0; y < height; y++)
/*    */       {
/* 34 */         int count = 0;
/*    */ 
/* 37 */         for (int dx = -1; dx < 2; dx++) {
/* 38 */           for (int dy = -1; dy < 2; dy++) {
/* 39 */             count += this.tempGrid.field[this.tempGrid.stx(x + dx)][this.tempGrid.sty(y + dy)];
/*    */           }
/*    */ 
/*    */         }
/*    */ 
/* 45 */         if ((count <= 2) || (count >= 5))
/* 46 */           tut.grid.field[x][y] = 0;
/* 47 */         else if (count == 3)
/* 48 */           tut.grid.field[x][y] = 1;
/*    */       }
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial1and2.CA
 * JD-Core Version:    0.6.2
 */