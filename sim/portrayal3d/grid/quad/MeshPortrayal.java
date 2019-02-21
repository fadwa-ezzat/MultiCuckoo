/*    */ package sim.portrayal3d.grid.quad;
/*    */ 
/*    */ import com.sun.j3d.utils.picking.PickIntersection;
/*    */ import java.awt.Color;
/*    */ import sim.field.grid.Grid2D;
/*    */ import sim.util.Int2D;
/*    */ import sim.util.gui.ColorMap;
/*    */ 
/*    */ public class MeshPortrayal extends QuadPortrayal
/*    */ {
/*    */   float[] tmpColor;
/* 41 */   static final int[] dx = { 0, -1, -1, 0 };
/* 42 */   static final int[] dy = { 0, 0, -1, -1 };
/*    */ 
/*    */   public MeshPortrayal(ColorMap colorDispenser)
/*    */   {
/* 31 */     this(colorDispenser, 0.0D);
/*    */   }
/*    */ 
/*    */   public MeshPortrayal(ColorMap colorDispenser, double zScale)
/*    */   {
/* 36 */     super(colorDispenser, zScale);
/*    */ 
/* 38 */     this.tmpColor = new float[4];
/*    */   }
/*    */ 
/*    */   public void setData(ValueGridCellInfo gridCell, float[] coordinates, float[] colors, int quadIndex, int gridWidth, int gridHeight)
/*    */   {
/* 48 */     gridWidth--;
/* 49 */     gridHeight--;
/*    */ 
/* 51 */     int x = gridCell.x;
/* 52 */     int y = gridCell.y;
/* 53 */     float value = (float)gridCell.value();
/* 54 */     this.colorDispenser.getColor(value).getColorComponents(this.tmpColor);
/* 55 */     value = (float)(value * this.zScale);
/*    */ 
/* 69 */     for (int i = 0; i < 4; i++)
/*    */     {
/* 71 */       int cellx = x + dx[i];
/* 72 */       int celly = y + dy[i];
/* 73 */       if ((cellx >= 0) && (celly >= 0) && (cellx < gridWidth) && (celly < gridHeight))
/*    */       {
/* 75 */         int iQuadIndex = cellx * gridHeight + celly;
/* 76 */         int offset = iQuadIndex * 12 + i * 3;
/*    */ 
/* 78 */         coordinates[(offset + 0)] = x;
/* 79 */         coordinates[(offset + 1)] = y;
/* 80 */         coordinates[(offset + 2)] = (value + 0.1F);
/* 81 */         System.arraycopy(this.tmpColor, 0, colors, (iQuadIndex * 4 + i) * 3, 3);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public Int2D getCellForIntersection(PickIntersection pi, Grid2D field) {
/* 87 */     int[] indices = pi.getPrimitiveVertexIndices();
/* 88 */     int closenessOffset = pi.getClosestVertexIndex();
/* 89 */     closenessOffset = indices[closenessOffset] % 4;
/*    */ 
/* 92 */     int xExtraOffset = (closenessOffset == 3) || (closenessOffset == 0) ? 0 : 1;
/* 93 */     int yExtraOffset = (closenessOffset == 3) || (closenessOffset == 2) ? 1 : 0;
/*    */ 
/* 95 */     int height = field.getHeight();
/* 96 */     int x = indices[0] / 4 / (height - 1) + xExtraOffset;
/* 97 */     int y = indices[0] / 4 % (height - 1) + yExtraOffset;
/* 98 */     return new Int2D(x, y);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.grid.quad.MeshPortrayal
 * JD-Core Version:    0.6.2
 */