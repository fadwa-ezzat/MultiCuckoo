/*     */ package sim.portrayal.grid;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Area;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ import sim.field.grid.Grid2D;
/*     */ import sim.field.grid.IntGrid2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.MutableDouble;
/*     */ import sim.util.gui.ColorMap;
/*     */ 
/*     */ public class HexaValueGridPortrayal2D extends ValueGridPortrayal2D
/*     */ {
/*  20 */   int[] xPoints = new int[6];
/*  21 */   int[] yPoints = new int[6];
/*  22 */   float[] xPointsf = new float[6];
/*  23 */   float[] yPointsf = new float[6];
/*     */ 
/*  25 */   double[] xyC = new double[2];
/*  26 */   double[] xyC_ul = new double[2];
/*  27 */   double[] xyC_up = new double[2];
/*  28 */   double[] xyC_ur = new double[2];
/*     */ 
/*  81 */   static final double HEXAGONAL_RATIO = 2.0D / Math.sqrt(3.0D);
/*     */ 
/* 301 */   GeneralPath generalPath = new GeneralPath();
/*     */ 
/*     */   public HexaValueGridPortrayal2D()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HexaValueGridPortrayal2D(String valueName)
/*     */   {
/*  37 */     super(valueName);
/*     */   }
/*     */ 
/*     */   static final void getxyC(int x, int y, double xScale, double yScale, double tx, double ty, double[] xyC)
/*     */   {
/*  42 */     xyC[0] = (tx + xScale * (1.5D * x + 1.0D));
/*  43 */     xyC[1] = (ty + yScale * (1.0D + 2.0D * y + (x < 0 ? -x % 2 : x % 2)));
/*     */   }
/*     */ 
/*     */   public Double2D getScale(DrawInfo2D info)
/*     */   {
/*  49 */     synchronized (info.gui.state.schedule)
/*     */     {
/*  51 */       Grid2D field = (Grid2D)this.field;
/*  52 */       if (field == null) return null;
/*     */ 
/*  54 */       int maxX = field.getWidth();
/*  55 */       int maxY = field.getHeight();
/*  56 */       if ((maxX == 0) || (maxY == 0)) return null;
/*     */ 
/*  58 */       double divideByX = maxX % 2 == 0 ? 3.0D * maxX / 2.0D + 0.5D : 3.0D * maxX / 2.0D + 2.0D;
/*  59 */       double divideByY = 1.0D + 2.0D * maxY;
/*     */ 
/*  61 */       double xScale = info.draw.width / divideByX;
/*  62 */       double yScale = info.draw.height / divideByY;
/*  63 */       return new Double2D(xScale, yScale);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getPositionLocation(Point2D.Double position, DrawInfo2D info)
/*     */   {
/*  70 */     Double2D scale = getScale(info);
/*  71 */     double xScale = scale.x;
/*  72 */     double yScale = scale.y;
/*     */ 
/*  74 */     int startx = (int)(((position.getX() - info.draw.x) / xScale - 0.5D) / 1.5D);
/*  75 */     int starty = (int)((position.getY() - info.draw.y) / (yScale * 2.0D));
/*     */ 
/*  77 */     return new Int2D(startx, starty);
/*     */   }
/*     */ 
/*     */   public Point2D.Double getLocationPosition(Object location, DrawInfo2D info)
/*     */   {
/*  85 */     synchronized (info.gui.state.schedule)
/*     */     {
/*  87 */       Grid2D field = (Grid2D)this.field;
/*  88 */       if (field == null) return null;
/*     */ 
/*  90 */       int maxX = field.getWidth();
/*  91 */       int maxY = field.getHeight();
/*  92 */       if ((maxX == 0) || (maxY == 0)) return null;
/*     */ 
/*  94 */       double divideByX = maxX % 2 == 0 ? 3.0D * maxX / 2.0D + 0.5D : 3.0D * maxX / 2.0D + 2.0D;
/*  95 */       double divideByY = 1.0D + 2.0D * maxY;
/*     */ 
/*  97 */       double xScale = info.draw.width / divideByX;
/*  98 */       double yScale = info.draw.height / divideByY;
/*     */ 
/* 104 */       DrawInfo2D newinfo = new DrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, Math.ceil(info.draw.width / (HEXAGONAL_RATIO * ((maxX - 1) * 3.0D / 4.0D + 1.0D))), Math.ceil(info.draw.height / (maxY + 0.5D))), info.clip);
/*     */ 
/* 108 */       newinfo.precise = info.precise;
/*     */ 
/* 110 */       Int2D loc = (Int2D)location;
/* 111 */       if (loc == null) return null;
/*     */ 
/* 113 */       int x = loc.x;
/* 114 */       int y = loc.y;
/*     */ 
/* 116 */       getxyC(x, y, xScale, yScale, info.draw.x, info.draw.y, this.xyC);
/* 117 */       getxyC(field.ulx(x, y), field.uly(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ul);
/* 118 */       getxyC(field.upx(x, y), field.upy(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_up);
/* 119 */       getxyC(field.urx(x, y), field.ury(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ur);
/*     */ 
/* 121 */       this.xPoints[0] = ((int)(this.xyC_ur[0] - 0.5D * xScale));
/*     */ 
/* 124 */       this.yPoints[1] = ((int)(this.xyC_up[1] + yScale));
/*     */ 
/* 127 */       this.xPoints[3] = ((int)(this.xyC_ul[0] + 0.5D * xScale));
/*     */ 
/* 130 */       this.yPoints[4] = ((int)(this.xyC[1] + yScale));
/*     */ 
/* 137 */       newinfo.draw.x = this.xPoints[3];
/* 138 */       newinfo.draw.y = this.yPoints[1];
/*     */ 
/* 141 */       newinfo.draw.x += (this.xPoints[0] - this.xPoints[3]) / 2.0D;
/* 142 */       newinfo.draw.y += (this.yPoints[4] - this.yPoints[1]) / 2.0D;
/*     */ 
/* 144 */       return new Point2D.Double(newinfo.draw.x, newinfo.draw.y);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void hitOrDraw(Graphics2D graphics, DrawInfo2D info, Bag putInHere)
/*     */   {
/* 154 */     Grid2D field = (Grid2D)this.field;
/* 155 */     if (field == null) return;
/*     */ 
/* 158 */     int maxX = field.getWidth();
/* 159 */     int maxY = field.getHeight();
/* 160 */     if ((maxX == 0) || (maxY == 0)) return;
/*     */ 
/* 162 */     double divideByX = maxX % 2 == 0 ? 3.0D * maxX / 2.0D + 0.5D : 3.0D * maxX / 2.0D + 2.0D;
/* 163 */     double divideByY = 1.0D + 2.0D * maxY;
/*     */ 
/* 165 */     double xScale = info.draw.width / divideByX;
/* 166 */     double yScale = info.draw.height / divideByY;
/* 167 */     int startx = (int)(((info.clip.x - info.draw.x) / xScale - 0.5D) / 1.5D) - 2;
/* 168 */     int starty = (int)((info.clip.y - info.draw.y) / (yScale * 2.0D)) - 2;
/* 169 */     int endx = (int)(((info.clip.x - info.draw.x + info.clip.width) / xScale - 0.5D) / 1.5D) + 4;
/* 170 */     int endy = (int)((info.clip.y - info.draw.y + info.clip.height) / (yScale * 2.0D)) + 4;
/*     */ 
/* 185 */     boolean isDoubleGrid2D = field instanceof DoubleGrid2D;
/* 186 */     double[][] doubleField = isDoubleGrid2D ? ((DoubleGrid2D)field).field : (double[][])null;
/* 187 */     int[][] intField = isDoubleGrid2D ? (int[][])null : ((IntGrid2D)field).field;
/*     */ 
/* 191 */     if (startx < 0) startx = 0;
/* 192 */     if (starty < 0) starty = 0;
/* 193 */     if (endx > maxX) endx = maxX;
/* 194 */     if (endy > maxY) endy = maxY;
/*     */ 
/* 196 */     for (int y = starty; y < endy; y++)
/* 197 */       for (int x = startx; x < endx; x++)
/*     */       {
/* 204 */         double x0 = x; double y0 = y; double tx = info.draw.x; double ty = info.draw.y;
/* 205 */         double xyC_x = tx + xScale * (1.5D * x0 + 1.0D);
/* 206 */         double xyC_y = ty + yScale * (1.0D + 2.0D * y0 + (x0 < 0.0D ? -x0 % 2.0D : x0 % 2.0D));
/*     */ 
/* 208 */         x0 = field.ulx(x, y); y0 = field.uly(x, y); tx = info.draw.x; ty = info.draw.y;
/* 209 */         double xyC_ulx = tx + xScale * (1.5D * x0 + 1.0D);
/* 210 */         double xyC_uly = ty + yScale * (1.0D + 2.0D * y0 + (x0 < 0.0D ? -x0 % 2.0D : x0 % 2.0D));
/*     */ 
/* 212 */         x0 = field.upx(x, y); y0 = field.upy(x, y); tx = info.draw.x; ty = info.draw.y;
/* 213 */         double xyC_upx = tx + xScale * (1.5D * x0 + 1.0D);
/* 214 */         double xyC_upy = ty + yScale * (1.0D + 2.0D * y0 + (x0 < 0.0D ? -x0 % 2.0D : x0 % 2.0D));
/*     */ 
/* 216 */         x0 = field.urx(x, y); y0 = field.ury(x, y); tx = info.draw.x; ty = info.draw.y;
/* 217 */         double xyC_urx = tx + xScale * (1.5D * x0 + 1.0D);
/* 218 */         double xyC_ury = ty + yScale * (1.0D + 2.0D * y0 + (x0 < 0.0D ? -x0 % 2.0D : x0 % 2.0D));
/*     */ 
/* 221 */         if (graphics == null)
/*     */         {
/* 223 */           this.xPointsf[0] = ((float)(xyC_urx - 0.5D * xScale));
/* 224 */           this.yPointsf[0] = ((float)(xyC_ury + yScale));
/* 225 */           this.xPointsf[1] = ((float)(xyC_upx + 0.5D * xScale));
/* 226 */           this.yPointsf[1] = ((float)(xyC_upy + yScale));
/* 227 */           this.xPointsf[2] = ((float)(xyC_upx - 0.5D * xScale));
/* 228 */           this.yPointsf[2] = ((float)(xyC_upy + yScale));
/* 229 */           this.xPointsf[3] = ((float)(xyC_ulx + 0.5D * xScale));
/* 230 */           this.yPointsf[3] = ((float)(xyC_uly + yScale));
/* 231 */           this.xPointsf[4] = ((float)(xyC_x - 0.5D * xScale));
/* 232 */           this.yPointsf[4] = ((float)(xyC_y + yScale));
/* 233 */           this.xPointsf[5] = ((float)(xyC_x + 0.5D * xScale));
/* 234 */           this.yPointsf[5] = ((float)(xyC_y + yScale));
/*     */ 
/* 236 */           this.generalPath.reset();
/* 237 */           this.generalPath.moveTo(this.xPointsf[0], this.yPointsf[0]);
/* 238 */           for (int i = 1; i < 6; i++)
/* 239 */             this.generalPath.lineTo(this.xPointsf[i], this.yPointsf[i]);
/* 240 */           this.generalPath.closePath();
/* 241 */           Area area = new Area(this.generalPath);
/* 242 */           if (area.intersects(info.clip.x, info.clip.y, info.clip.width, info.clip.height))
/*     */           {
/* 244 */             this.valueToPass.val = (isDoubleGrid2D ? doubleField[x][y] : intField[x][y]);
/* 245 */             putInHere.add(getWrapper(this.valueToPass.val, new Int2D(x, y)));
/*     */           }
/*     */         }
/* 248 */         else if (info.precise)
/*     */         {
/* 250 */           this.xPointsf[0] = ((float)(xyC_urx - 0.5D * xScale));
/* 251 */           this.yPointsf[0] = ((float)(xyC_ury + yScale));
/* 252 */           this.xPointsf[1] = ((float)(xyC_upx + 0.5D * xScale));
/* 253 */           this.yPointsf[1] = ((float)(xyC_upy + yScale));
/* 254 */           this.xPointsf[2] = ((float)(xyC_upx - 0.5D * xScale));
/* 255 */           this.yPointsf[2] = ((float)(xyC_upy + yScale));
/* 256 */           this.xPointsf[3] = ((float)(xyC_ulx + 0.5D * xScale));
/* 257 */           this.yPointsf[3] = ((float)(xyC_uly + yScale));
/* 258 */           this.xPointsf[4] = ((float)(xyC_x - 0.5D * xScale));
/* 259 */           this.yPointsf[4] = ((float)(xyC_y + yScale));
/* 260 */           this.xPointsf[5] = ((float)(xyC_x + 0.5D * xScale));
/* 261 */           this.yPointsf[5] = ((float)(xyC_y + yScale));
/*     */ 
/* 263 */           Color c = this.map.getColor(isDoubleGrid2D ? doubleField[x][y] : intField[x][y]);
/* 264 */           if (c.getAlpha() != 0) {
/* 265 */             graphics.setColor(c);
/*     */ 
/* 267 */             this.generalPath.reset();
/* 268 */             this.generalPath.moveTo(this.xPointsf[0], this.yPointsf[0]);
/* 269 */             for (int i = 1; i < 6; i++)
/* 270 */               this.generalPath.lineTo(this.xPointsf[i], this.yPointsf[i]);
/* 271 */             this.generalPath.closePath();
/* 272 */             graphics.fill(this.generalPath);
/*     */           }
/*     */         }
/*     */         else {
/* 276 */           this.xPoints[0] = ((int)(xyC_urx - 0.5D * xScale));
/* 277 */           this.yPoints[0] = ((int)(xyC_ury + yScale));
/* 278 */           this.xPoints[1] = ((int)(xyC_upx + 0.5D * xScale));
/* 279 */           this.yPoints[1] = ((int)(xyC_upy + yScale));
/* 280 */           this.xPoints[2] = ((int)(xyC_upx - 0.5D * xScale));
/* 281 */           this.yPoints[2] = ((int)(xyC_upy + yScale));
/* 282 */           this.xPoints[3] = ((int)(xyC_ulx + 0.5D * xScale));
/* 283 */           this.yPoints[3] = ((int)(xyC_uly + yScale));
/* 284 */           this.xPoints[4] = ((int)(xyC_x - 0.5D * xScale));
/* 285 */           this.yPoints[4] = ((int)(xyC_y + yScale));
/* 286 */           this.xPoints[5] = ((int)(xyC_x + 0.5D * xScale));
/* 287 */           this.yPoints[5] = ((int)(xyC_y + yScale));
/*     */ 
/* 289 */           Color c = this.map.getColor(isDoubleGrid2D ? doubleField[x][y] : intField[x][y]);
/* 290 */           if (c.getAlpha() != 0) {
/* 291 */             graphics.setColor(c);
/*     */ 
/* 295 */             graphics.fillPolygon(this.xPoints, this.yPoints, 6);
/*     */           }
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public void setBorder(boolean on)
/*     */   {
/* 305 */     throw new RuntimeException("Border drawing is not supported by hexagonal portrayals.");
/*     */   }
/*     */   public void setGridLines(boolean on) {
/* 308 */     throw new RuntimeException("Grid line drawing is not supported by hexagonal portrayals.");
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.grid.HexaValueGridPortrayal2D
 * JD-Core Version:    0.6.2
 */