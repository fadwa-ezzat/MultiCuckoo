/*    */ package sim.portrayal3d.grid.quad;
/*    */ 
/*    */ import sim.field.grid.DoubleGrid2D;
/*    */ import sim.field.grid.Grid2D;
/*    */ import sim.field.grid.IntGrid2D;
/*    */ import sim.field.grid.ObjectGrid2D;
/*    */ import sim.portrayal3d.grid.ValueGrid2DPortrayal3D;
/*    */ 
/*    */ public class ValueGridCellInfo
/*    */ {
/*    */   Object grid;
/*    */   ValueGrid2DPortrayal3D fieldPortrayal;
/* 24 */   public int x = 0;
/* 25 */   public int y = 0;
/*    */ 
/*    */   public ValueGridCellInfo(ValueGrid2DPortrayal3D fieldPortrayal, Grid2D g)
/*    */   {
/* 29 */     this.grid = g;
/* 30 */     this.fieldPortrayal = fieldPortrayal;
/*    */   }
/*    */ 
/*    */   public double value()
/*    */   {
/* 35 */     if ((this.grid instanceof DoubleGrid2D))
/* 36 */       return ((DoubleGrid2D)this.grid).field[this.x][this.y];
/* 37 */     if ((this.grid instanceof IntGrid2D))
/* 38 */       return ((IntGrid2D)this.grid).field[this.x][this.y];
/* 39 */     if ((this.grid instanceof ObjectGrid2D))
/* 40 */       return this.fieldPortrayal.doubleValue(((ObjectGrid2D)this.grid).field[this.x][this.y]);
/* 41 */     return 0.0D;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.grid.quad.ValueGridCellInfo
 * JD-Core Version:    0.6.2
 */