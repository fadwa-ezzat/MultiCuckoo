/*    */ package sim.portrayal3d.grid.quad;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import sim.util.gui.ColorMap;
/*    */ 
/*    */ public class TilePortrayal extends QuadPortrayal
/*    */ {
/*    */   float[] tmpColor;
/*    */ 
/*    */   public TilePortrayal(ColorMap colorDispenser)
/*    */   {
/* 22 */     this(colorDispenser, 0.0D);
/*    */   }
/*    */ 
/*    */   public TilePortrayal(ColorMap colorDispenser, double zScale)
/*    */   {
/* 27 */     super(colorDispenser, zScale);
/*    */ 
/* 29 */     this.tmpColor = new float[4];
/*    */   }
/*    */ 
/*    */   public void setData(ValueGridCellInfo gridCell, float[] coordinates, float[] colors, int quadIndex, int gridWidth, int gridHeight)
/*    */   {
/* 36 */     int x = gridCell.x;
/* 37 */     int y = gridCell.y;
/* 38 */     float value = (float)gridCell.value();
/* 39 */     this.colorDispenser.getColor(value).getComponents(this.tmpColor);
/* 40 */     value = (float)(value * this.zScale);
/*    */ 
/* 42 */     for (int i = 0; i < 4; i++) {
/* 43 */       System.arraycopy(this.tmpColor, 0, colors, (quadIndex * 4 + i) * 3, 3);
/*    */     }
/* 45 */     int offset = quadIndex * 12;
/* 46 */     coordinates[(offset + 0)] = (x - 0.5F);
/* 47 */     coordinates[(offset + 1)] = (y - 0.5F);
/* 48 */     coordinates[(offset + 2)] = value;
/* 49 */     coordinates[(offset + 3)] = (x + 0.5F);
/* 50 */     coordinates[(offset + 4)] = (y - 0.5F);
/* 51 */     coordinates[(offset + 5)] = value;
/* 52 */     coordinates[(offset + 6)] = (x + 0.5F);
/* 53 */     coordinates[(offset + 7)] = (y + 0.5F);
/* 54 */     coordinates[(offset + 8)] = value;
/* 55 */     coordinates[(offset + 9)] = (x - 0.5F);
/* 56 */     coordinates[(offset + 10)] = (y + 0.5F);
/* 57 */     coordinates[(offset + 11)] = value;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.grid.quad.TilePortrayal
 * JD-Core Version:    0.6.2
 */