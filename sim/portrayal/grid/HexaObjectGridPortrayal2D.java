/*     */ package sim.portrayal.grid;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.grid.Grid2D;
/*     */ import sim.field.grid.ObjectGrid2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.portrayal.simple.HexagonalPortrayal2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.Int2D;
/*     */ 
/*     */ public class HexaObjectGridPortrayal2D extends ObjectGridPortrayal2D
/*     */ {
/*  28 */   int[] xPoints = new int[6];
/*  29 */   int[] yPoints = new int[6];
/*     */ 
/*  31 */   double[] xyC = new double[2];
/*  32 */   double[] xyC_ul = new double[2];
/*  33 */   double[] xyC_up = new double[2];
/*  34 */   double[] xyC_ur = new double[2];
/*     */ 
/* 150 */   static final double HEXAGONAL_RATIO = 2.0D / Math.sqrt(3.0D);
/*     */ 
/*     */   static final void getxyC(int x, int y, double xScale, double yScale, double tx, double ty, double[] xyC)
/*     */   {
/*  38 */     xyC[0] = (tx + xScale * (1.5D * x + 1.0D));
/*  39 */     xyC[1] = (ty + yScale * (1.0D + 2.0D * y + (x < 0 ? -x % 2 : x % 2)));
/*     */   }
/*     */ 
/*     */   public HexaObjectGridPortrayal2D()
/*     */   {
/*  45 */     this.defaultPortrayal = new HexagonalPortrayal2D();
/*     */   }
/*     */ 
/*     */   public Double2D getScale(DrawInfo2D info)
/*     */   {
/*  50 */     synchronized (info.gui.state.schedule)
/*     */     {
/*  52 */       Grid2D field = (Grid2D)this.field;
/*  53 */       if (field == null) return null;
/*     */ 
/*  55 */       int maxX = field.getWidth();
/*  56 */       int maxY = field.getHeight();
/*  57 */       if ((maxX == 0) || (maxY == 0)) return null;
/*     */ 
/*  59 */       double divideByX = maxX % 2 == 0 ? 3.0D * maxX / 2.0D + 0.5D : 3.0D * maxX / 2.0D + 2.0D;
/*  60 */       double divideByY = 1.0D + 2.0D * maxY;
/*     */ 
/*  62 */       double xScale = info.draw.width / divideByX;
/*  63 */       double yScale = info.draw.height / divideByY;
/*  64 */       return new Double2D(xScale, yScale);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getPositionLocation(Point2D.Double position, DrawInfo2D info)
/*     */   {
/*  71 */     Double2D scale = getScale(info);
/*  72 */     double xScale = scale.x;
/*  73 */     double yScale = scale.y;
/*     */ 
/*  75 */     int startx = (int)(((position.getX() - info.draw.x) / xScale - 0.5D) / 1.5D);
/*  76 */     int starty = (int)((position.getY() - info.draw.y) / (yScale * 2.0D));
/*     */ 
/*  78 */     return new Int2D(startx, starty);
/*     */   }
/*     */ 
/*     */   public Point2D.Double getLocationPosition(Object location, DrawInfo2D info)
/*     */   {
/*  84 */     synchronized (info.gui.state.schedule)
/*     */     {
/*  86 */       Grid2D field = (Grid2D)this.field;
/*  87 */       if (field == null) return null;
/*     */ 
/*  89 */       int maxX = field.getWidth();
/*  90 */       int maxY = field.getHeight();
/*  91 */       if ((maxX == 0) || (maxY == 0)) return null;
/*     */ 
/*  93 */       double divideByX = maxX % 2 == 0 ? 3.0D * maxX / 2.0D + 0.5D : 3.0D * maxX / 2.0D + 2.0D;
/*  94 */       double divideByY = 1.0D + 2.0D * maxY;
/*     */ 
/*  96 */       double xScale = info.draw.width / divideByX;
/*  97 */       double yScale = info.draw.height / divideByY;
/*     */ 
/* 103 */       DrawInfo2D newinfo = new DrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, Math.ceil(info.draw.width / (HEXAGONAL_RATIO * ((maxX - 1) * 3.0D / 4.0D + 1.0D))), Math.ceil(info.draw.height / (maxY + 0.5D))), info.clip);
/*     */ 
/* 107 */       newinfo.precise = info.precise;
/*     */ 
/* 109 */       Int2D loc = (Int2D)location;
/* 110 */       if (loc == null) return null;
/*     */ 
/* 112 */       int x = loc.x;
/* 113 */       int y = loc.y;
/*     */ 
/* 115 */       getxyC(x, y, xScale, yScale, info.draw.x, info.draw.y, this.xyC);
/* 116 */       getxyC(field.ulx(x, y), field.uly(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ul);
/* 117 */       getxyC(field.upx(x, y), field.upy(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_up);
/* 118 */       getxyC(field.urx(x, y), field.ury(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ur);
/*     */ 
/* 120 */       this.xPoints[0] = ((int)(this.xyC_ur[0] - 0.5D * xScale));
/*     */ 
/* 123 */       this.yPoints[1] = ((int)(this.xyC_up[1] + yScale));
/*     */ 
/* 126 */       this.xPoints[3] = ((int)(this.xyC_ul[0] + 0.5D * xScale));
/*     */ 
/* 129 */       this.yPoints[4] = ((int)(this.xyC[1] + yScale));
/*     */ 
/* 136 */       newinfo.draw.x = this.xPoints[3];
/* 137 */       newinfo.draw.y = this.yPoints[1];
/*     */ 
/* 140 */       newinfo.draw.x += (this.xPoints[0] - this.xPoints[3]) / 2.0D;
/* 141 */       newinfo.draw.y += (this.yPoints[4] - this.yPoints[1]) / 2.0D;
/*     */ 
/* 143 */       return new Point2D.Double(newinfo.draw.x, newinfo.draw.y);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void hitOrDraw(Graphics2D graphics, DrawInfo2D info, Bag putInHere)
/*     */   {
/* 154 */     ObjectGrid2D field = (ObjectGrid2D)this.field;
/* 155 */     if (field == null) return;
/*     */ 
/* 165 */     int maxX = field.getWidth();
/* 166 */     int maxY = field.getHeight();
/* 167 */     if ((maxX == 0) || (maxY == 0)) return;
/*     */ 
/* 169 */     double divideByX = maxX % 2 == 0 ? 3.0D * maxX / 2.0D + 0.5D : 3.0D * maxX / 2.0D + 2.0D;
/* 170 */     double divideByY = 1.0D + 2.0D * maxY;
/*     */ 
/* 172 */     double xScale = info.draw.width / divideByX;
/* 173 */     double yScale = info.draw.height / divideByY;
/* 174 */     int startx = (int)(((info.clip.x - info.draw.x) / xScale - 0.5D) / 1.5D) - 2;
/* 175 */     int starty = (int)((info.clip.y - info.draw.y) / (yScale * 2.0D)) - 2;
/* 176 */     int endx = (int)(((info.clip.x - info.draw.x + info.clip.width) / xScale - 0.5D) / 1.5D) + 4;
/* 177 */     int endy = (int)((info.clip.y - info.draw.y + info.clip.height) / (yScale * 2.0D)) + 4;
/*     */ 
/* 202 */     DrawInfo2D newinfo = new DrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, Math.ceil(info.draw.width / (HEXAGONAL_RATIO * ((maxX - 1) * 3.0D / 4.0D + 1.0D))), Math.ceil(info.draw.height / (maxY + 0.5D))), info.clip);
/*     */ 
/* 206 */     newinfo.precise = info.precise;
/* 207 */     newinfo.fieldPortrayal = this;
/* 208 */     newinfo.location = this.locationToPass;
/*     */ 
/* 210 */     if (startx < 0) startx = 0;
/* 211 */     if (starty < 0) starty = 0;
/* 212 */     if (endx > maxX) endx = maxX;
/* 213 */     if (endy > maxY) endy = maxY;
/*     */ 
/* 215 */     for (int y = starty; y < endy; y++)
/* 216 */       for (int x = startx; x < endx; x++)
/*     */       {
/* 218 */         Object obj = field.field[x][y];
/* 219 */         Portrayal p = getPortrayalForObject(obj);
/* 220 */         if (!(p instanceof SimplePortrayal2D)) {
/* 221 */           throw new RuntimeException("Unexpected Portrayal " + p + " for object " + obj + " -- expected a SimplePortrayal2D");
/*     */         }
/* 223 */         SimplePortrayal2D portrayal = (SimplePortrayal2D)p;
/*     */ 
/* 225 */         getxyC(x, y, xScale, yScale, info.draw.x, info.draw.y, this.xyC);
/* 226 */         getxyC(field.ulx(x, y), field.uly(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ul);
/* 227 */         getxyC(field.upx(x, y), field.upy(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_up);
/* 228 */         getxyC(field.urx(x, y), field.ury(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ur);
/*     */ 
/* 230 */         this.xPoints[0] = ((int)(this.xyC_ur[0] - 0.5D * xScale));
/*     */ 
/* 233 */         this.yPoints[1] = ((int)(this.xyC_up[1] + yScale));
/*     */ 
/* 236 */         this.xPoints[3] = ((int)(this.xyC_ul[0] + 0.5D * xScale));
/*     */ 
/* 239 */         this.yPoints[4] = ((int)(this.xyC[1] + yScale));
/*     */ 
/* 246 */         newinfo.draw.x = this.xPoints[3];
/* 247 */         newinfo.draw.y = this.yPoints[1];
/*     */ 
/* 250 */         newinfo.draw.x += (this.xPoints[0] - this.xPoints[3]) / 2.0D;
/* 251 */         newinfo.draw.y += (this.yPoints[4] - this.yPoints[1]) / 2.0D;
/*     */ 
/* 253 */         this.locationToPass.x = x;
/* 254 */         this.locationToPass.y = y;
/*     */ 
/* 256 */         if (graphics == null)
/*     */         {
/* 258 */           if (portrayal.hitObject(obj, newinfo)) {
/* 259 */             putInHere.add(getWrapper(obj, new Int2D(x, y)));
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 265 */           portrayal.draw(obj, graphics, newinfo);
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public void setBorder(boolean on)
/*     */   {
/* 272 */     throw new RuntimeException("Border drawing is not supported by hexagonal portrayals.");
/*     */   }
/*     */   public void setGridLines(boolean on) {
/* 275 */     throw new RuntimeException("Grid line drawing is not supported by hexagonal portrayals.");
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.grid.HexaObjectGridPortrayal2D
 * JD-Core Version:    0.6.2
 */