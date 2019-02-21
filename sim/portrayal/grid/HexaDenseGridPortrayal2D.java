/*     */ package sim.portrayal.grid;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import java.util.HashMap;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.grid.DenseGrid2D;
/*     */ import sim.field.grid.Grid2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.portrayal.simple.HexagonalPortrayal2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.Int2D;
/*     */ 
/*     */ public class HexaDenseGridPortrayal2D extends DenseGridPortrayal2D
/*     */ {
/*  26 */   int[] xPoints = new int[6];
/*  27 */   int[] yPoints = new int[6];
/*     */ 
/*  29 */   double[] xyC = new double[2];
/*  30 */   double[] xyC_ul = new double[2];
/*  31 */   double[] xyC_up = new double[2];
/*  32 */   double[] xyC_ur = new double[2];
/*     */ 
/*  55 */   static final double HEXAGONAL_RATIO = 2.0D / Math.sqrt(3.0D);
/*     */ 
/*     */   static final void getxyC(int x, int y, double xScale, double yScale, double tx, double ty, double[] xyC)
/*     */   {
/*  36 */     xyC[0] = (tx + xScale * (1.5D * x + 1.0D));
/*  37 */     xyC[1] = (ty + yScale * (1.0D + 2.0D * y + (x < 0 ? -x % 2 : x % 2)));
/*     */   }
/*     */ 
/*     */   public HexaDenseGridPortrayal2D()
/*     */   {
/*  43 */     this.defaultPortrayal = new HexagonalPortrayal2D();
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public HexaDenseGridPortrayal2D(DrawPolicy policy)
/*     */   {
/*  49 */     super(policy);
/*  50 */     this.defaultPortrayal = new HexagonalPortrayal2D();
/*     */   }
/*     */ 
/*     */   public Double2D getScale(DrawInfo2D info)
/*     */   {
/*  60 */     synchronized (info.gui.state.schedule)
/*     */     {
/*  62 */       Grid2D field = (Grid2D)this.field;
/*  63 */       if (field == null) return null;
/*     */ 
/*  65 */       int maxX = field.getWidth();
/*  66 */       int maxY = field.getHeight();
/*  67 */       if ((maxX == 0) || (maxY == 0)) return null;
/*     */ 
/*  69 */       double divideByX = maxX % 2 == 0 ? 3.0D * maxX / 2.0D + 0.5D : 3.0D * maxX / 2.0D + 2.0D;
/*  70 */       double divideByY = 1.0D + 2.0D * maxY;
/*     */ 
/*  72 */       double xScale = info.draw.width / divideByX;
/*  73 */       double yScale = info.draw.height / divideByY;
/*  74 */       return new Double2D(xScale, yScale);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getPositionLocation(Point2D.Double position, DrawInfo2D info)
/*     */   {
/*  81 */     Double2D scale = getScale(info);
/*  82 */     double xScale = scale.x;
/*  83 */     double yScale = scale.y;
/*     */ 
/*  85 */     int startx = (int)Math.floor(((position.getX() - info.draw.x) / xScale - 0.5D) / 1.5D);
/*  86 */     int starty = (int)Math.floor((position.getY() - info.draw.y) / (yScale * 2.0D));
/*     */ 
/*  88 */     return new Int2D(startx, starty);
/*     */   }
/*     */ 
/*     */   public Point2D.Double getLocationPosition(Object location, DrawInfo2D info)
/*     */   {
/*  94 */     synchronized (info.gui.state.schedule)
/*     */     {
/*  96 */       Grid2D field = (Grid2D)this.field;
/*  97 */       if (field == null) return null;
/*     */ 
/*  99 */       int maxX = field.getWidth();
/* 100 */       int maxY = field.getHeight();
/* 101 */       if ((maxX == 0) || (maxY == 0)) return null;
/*     */ 
/* 103 */       double divideByX = maxX % 2 == 0 ? 3.0D * maxX / 2.0D + 0.5D : 3.0D * maxX / 2.0D + 2.0D;
/* 104 */       double divideByY = 1.0D + 2.0D * maxY;
/*     */ 
/* 106 */       double xScale = info.draw.width / divideByX;
/* 107 */       double yScale = info.draw.height / divideByY;
/*     */ 
/* 113 */       DrawInfo2D newinfo = new DrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, Math.ceil(info.draw.width / (HEXAGONAL_RATIO * ((maxX - 1) * 3.0D / 4.0D + 1.0D))), Math.ceil(info.draw.height / (maxY + 0.5D))), info.clip);
/*     */ 
/* 117 */       newinfo.precise = info.precise;
/*     */ 
/* 119 */       Int2D loc = (Int2D)location;
/* 120 */       if (loc == null) return null;
/*     */ 
/* 122 */       int x = loc.x;
/* 123 */       int y = loc.y;
/*     */ 
/* 125 */       getxyC(x, y, xScale, yScale, info.draw.x, info.draw.y, this.xyC);
/* 126 */       getxyC(field.ulx(x, y), field.uly(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ul);
/* 127 */       getxyC(field.upx(x, y), field.upy(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_up);
/* 128 */       getxyC(field.urx(x, y), field.ury(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ur);
/*     */ 
/* 130 */       this.xPoints[0] = ((int)Math.floor(this.xyC_ur[0] - 0.5D * xScale));
/*     */ 
/* 133 */       this.yPoints[1] = ((int)Math.floor(this.xyC_up[1] + yScale));
/*     */ 
/* 136 */       this.xPoints[3] = ((int)Math.floor(this.xyC_ul[0] + 0.5D * xScale));
/*     */ 
/* 139 */       this.yPoints[4] = ((int)Math.floor(this.xyC[1] + yScale));
/*     */ 
/* 146 */       newinfo.draw.x = this.xPoints[3];
/* 147 */       newinfo.draw.y = this.yPoints[1];
/*     */ 
/* 150 */       newinfo.draw.x += (this.xPoints[0] - this.xPoints[3]) / 2.0D;
/* 151 */       newinfo.draw.y += (this.yPoints[4] - this.yPoints[1]) / 2.0D;
/*     */ 
/* 153 */       return new Point2D.Double(newinfo.draw.x, newinfo.draw.y);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void hitOrDraw(Graphics2D graphics, DrawInfo2D info, Bag putInHere)
/*     */   {
/* 160 */     DenseGrid2D field = (DenseGrid2D)this.field;
/* 161 */     Bag policyBag = new Bag();
/*     */ 
/* 163 */     if (field == null) return;
/*     */ 
/* 165 */     boolean objectSelected = !this.selectedWrappers.isEmpty();
/*     */ 
/* 167 */     int maxX = field.getWidth();
/* 168 */     int maxY = field.getHeight();
/* 169 */     if ((maxX == 0) || (maxY == 0)) return;
/*     */ 
/* 171 */     double divideByX = maxX % 2 == 0 ? 3.0D * maxX / 2.0D + 0.5D : 3.0D * maxX / 2.0D + 2.0D;
/* 172 */     double divideByY = 1.0D + 2.0D * maxY;
/*     */ 
/* 174 */     double xScale = info.draw.width / divideByX;
/* 175 */     double yScale = info.draw.height / divideByY;
/* 176 */     int startx = (int)Math.floor(((info.clip.x - info.draw.x) / xScale - 0.5D) / 1.5D) - 2;
/* 177 */     int starty = (int)Math.floor((info.clip.y - info.draw.y) / (yScale * 2.0D)) - 2;
/* 178 */     int endx = (int)Math.floor(((info.clip.x - info.draw.x + info.clip.width) / xScale - 0.5D) / 1.5D) + 4;
/* 179 */     int endy = (int)Math.floor((info.clip.y - info.draw.y + info.clip.height) / (yScale * 2.0D)) + 4;
/*     */ 
/* 204 */     DrawInfo2D newinfo = new DrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, Math.ceil(info.draw.width / (HEXAGONAL_RATIO * ((maxX - 1) * 3.0D / 4.0D + 1.0D))), Math.ceil(info.draw.height / (maxY + 0.5D))), info.clip);
/*     */ 
/* 208 */     newinfo.precise = info.precise;
/* 209 */     newinfo.fieldPortrayal = this;
/*     */ 
/* 218 */     if (startx < 0) startx = 0;
/* 219 */     if (starty < 0) starty = 0;
/* 220 */     if (endx > maxX) endx = maxX;
/* 221 */     if (endy > maxY) endy = maxY;
/*     */ 
/* 223 */     for (int y = starty; y < endy; y++)
/* 224 */       for (int x = startx; x < endx; x++)
/*     */       {
/* 226 */         Bag objects = field.field[x][y];
/*     */ 
/* 228 */         if (objects != null)
/*     */         {
/* 230 */           if (((this.policy != null ? 1 : 0) & (graphics != null ? 1 : 0)) != 0)
/*     */           {
/* 232 */             policyBag.clear();
/* 233 */             if (this.policy.objectToDraw(objects, policyBag)) {
/* 234 */               objects = policyBag;
/*     */             }
/*     */           }
/* 237 */           for (int i = 0; i < objects.numObjs; i++)
/*     */           {
/* 239 */             Object portrayedObject = objects.objs[i];
/*     */ 
/* 241 */             Portrayal p = getPortrayalForObject(portrayedObject);
/* 242 */             if (!(p instanceof SimplePortrayal2D)) {
/* 243 */               throw new RuntimeException("Unexpected Portrayal " + p + " for object " + portrayedObject + " -- expected a SimplePortrayal2D");
/*     */             }
/* 245 */             SimplePortrayal2D portrayal = (SimplePortrayal2D)p;
/*     */ 
/* 247 */             getxyC(x, y, xScale, yScale, info.draw.x, info.draw.y, this.xyC);
/* 248 */             getxyC(field.ulx(x, y), field.uly(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ul);
/* 249 */             getxyC(field.upx(x, y), field.upy(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_up);
/* 250 */             getxyC(field.urx(x, y), field.ury(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ur);
/*     */ 
/* 252 */             this.xPoints[0] = ((int)(this.xyC_ur[0] - 0.5D * xScale));
/*     */ 
/* 255 */             this.yPoints[1] = ((int)(this.xyC_up[1] + yScale));
/*     */ 
/* 258 */             this.xPoints[3] = ((int)(this.xyC_ul[0] + 0.5D * xScale));
/*     */ 
/* 261 */             this.yPoints[4] = ((int)(this.xyC[1] + yScale));
/*     */ 
/* 268 */             newinfo.draw.x = this.xPoints[3];
/* 269 */             newinfo.draw.y = this.yPoints[1];
/*     */ 
/* 272 */             newinfo.draw.x += (this.xPoints[0] - this.xPoints[3]) / 2.0D;
/* 273 */             newinfo.draw.y += (this.yPoints[4] - this.yPoints[1]) / 2.0D;
/*     */ 
/* 275 */             this.locationToPass.x = x;
/* 276 */             this.locationToPass.y = y;
/*     */ 
/* 278 */             if (graphics == null)
/*     */             {
/* 280 */               if (portrayal.hitObject(portrayedObject, newinfo)) {
/* 281 */                 putInHere.add(getWrapper(portrayedObject, new Int2D(x, y)));
/*     */               }
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 287 */               portrayal.draw(portrayedObject, graphics, newinfo);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public void setBorder(boolean on) {
/* 295 */     throw new RuntimeException("Border drawing is not supported by hexagonal portrayals.");
/*     */   }
/*     */   public void setGridLines(boolean on) {
/* 298 */     throw new RuntimeException("Grid line drawing is not supported by hexagonal portrayals.");
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.grid.HexaDenseGridPortrayal2D
 * JD-Core Version:    0.6.2
 */