/*    */ package sim.app.pacman;
/*    */ 
/*    */ import java.awt.BasicStroke;
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.geom.Line2D.Double;
/*    */ import java.awt.geom.QuadCurve2D.Double;
/*    */ import java.awt.geom.Rectangle2D.Double;
/*    */ import sim.field.grid.IntGrid2D;
/*    */ import sim.portrayal.DrawInfo2D;
/*    */ import sim.portrayal.SimplePortrayal2D;
/*    */ import sim.util.MutableDouble;
/*    */ import sim.util.MutableInt2D;
/*    */ 
/*    */ public class MazeCellPortrayal extends SimplePortrayal2D
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   IntGrid2D field;
/* 32 */   QuadCurve2D.Double curve = new QuadCurve2D.Double();
/* 33 */   Line2D.Double line = new Line2D.Double();
/* 34 */   Color color = new Color(33, 33, 222);
/* 35 */   BasicStroke stroke = new BasicStroke(2.0F);
/*    */ 
/*    */   public MazeCellPortrayal(IntGrid2D field)
/*    */   {
/* 30 */     this.field = field;
/*    */   }
/*    */ 
/*    */   public void draw(Object object, Graphics2D g, DrawInfo2D info)
/*    */   {
/* 39 */     int[][] grid = this.field.field;
/* 40 */     MutableInt2D location = (MutableInt2D)info.location;
/* 41 */     int x = location.x;
/* 42 */     int y = location.y;
/*    */ 
/* 44 */     double ox = info.draw.x;
/* 45 */     double oy = info.draw.y;
/* 46 */     double sc = info.draw.width / 2.0D;
/*    */ 
/* 66 */     if ((int)((MutableDouble)object).val == 0)
/*    */     {
/* 68 */       return;
/*    */     }
/*    */ 
/* 73 */     int height = this.field.getHeight() - 1;
/* 74 */     int width = this.field.getWidth() - 1;
/* 75 */     int n = y == 0 ? 1 : grid[x][(y - 1)];
/* 76 */     int w = x == 0 ? 1 : grid[(x - 1)][y];
/* 77 */     int s = y == height ? 1 : grid[x][(y + 1)];
/* 78 */     int e = x == width ? 1 : grid[(x + 1)][y];
/*    */ 
/* 80 */     g.setColor(this.color);
/* 81 */     g.setStroke(this.stroke);
/*    */ 
/* 83 */     if (n == 0)
/*    */     {
/* 85 */       if (w == 0)
/*    */       {
/* 87 */         this.curve.setCurve(ox + sc, oy, ox, oy, ox, oy + sc);
/* 88 */         g.draw(this.curve);
/*    */       }
/* 90 */       else if (e == 0)
/*    */       {
/* 92 */         this.curve.setCurve(ox - sc, oy, ox, oy, ox, oy + sc);
/* 93 */         g.draw(this.curve);
/*    */       }
/*    */       else
/*    */       {
/* 97 */         this.line.setLine(ox + sc, oy, ox - sc, oy);
/* 98 */         g.draw(this.line);
/*    */       }
/*    */     }
/* 101 */     else if (s == 0)
/*    */     {
/* 103 */       if (w == 0)
/*    */       {
/* 105 */         this.curve.setCurve(ox + sc, oy, ox, oy, ox, oy - sc);
/* 106 */         g.draw(this.curve);
/*    */       }
/* 108 */       else if (e == 0)
/*    */       {
/* 110 */         this.curve.setCurve(ox - sc, oy, ox, oy, ox, oy - sc);
/* 111 */         g.draw(this.curve);
/*    */       }
/*    */       else
/*    */       {
/* 115 */         this.line.setLine(ox + sc, oy, ox - sc, oy);
/* 116 */         g.draw(this.line);
/*    */       }
/*    */     }
/* 119 */     else if (e == 0)
/*    */     {
/* 121 */       this.line.setLine(ox, oy + sc, ox, oy - sc);
/* 122 */       g.draw(this.line);
/*    */     }
/* 124 */     else if (w == 0)
/*    */     {
/* 126 */       this.line.setLine(ox, oy + sc, ox, oy - sc);
/* 127 */       g.draw(this.line);
/*    */     }
/*    */     else
/*    */     {
/* 131 */       int nw = (y == 0) || (x == 0) ? 1 : grid[(x - 1)][(y - 1)];
/* 132 */       int sw = (y == height) || (x == 0) ? 1 : grid[(x - 1)][(y + 1)];
/* 133 */       int ne = (y == 0) || (x == width) ? 1 : grid[(x + 1)][(y - 1)];
/* 134 */       int se = (y == height) || (x == width) ? 1 : grid[(x + 1)][(y + 1)];
/*    */ 
/* 136 */       if (nw == 0)
/*    */       {
/* 138 */         this.curve.setCurve(ox - sc, oy, ox, oy, ox, oy - sc);
/* 139 */         g.draw(this.curve);
/*    */       }
/* 141 */       else if (sw == 0)
/*    */       {
/* 143 */         this.curve.setCurve(ox - sc, oy, ox, oy, ox, oy + sc);
/* 144 */         g.draw(this.curve);
/*    */       }
/* 146 */       else if (ne == 0)
/*    */       {
/* 148 */         this.curve.setCurve(ox + sc, oy, ox, oy, ox, oy - sc);
/* 149 */         g.draw(this.curve);
/*    */       }
/* 151 */       else if (se == 0)
/*    */       {
/* 153 */         this.curve.setCurve(ox + sc, oy, ox, oy, ox, oy + sc);
/* 154 */         g.draw(this.curve);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pacman.MazeCellPortrayal
 * JD-Core Version:    0.6.2
 */