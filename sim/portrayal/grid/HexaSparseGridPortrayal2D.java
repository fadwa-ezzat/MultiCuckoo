/*     */ package sim.portrayal.grid;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.grid.Grid2D;
/*     */ import sim.field.grid.SparseGrid2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.Fixed2D;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.portrayal.simple.HexagonalPortrayal2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.Int2D;
/*     */ 
/*     */ public class HexaSparseGridPortrayal2D extends SparseGridPortrayal2D
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
/*     */   public HexaSparseGridPortrayal2D()
/*     */   {
/*  43 */     this.defaultPortrayal = new HexagonalPortrayal2D();
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public HexaSparseGridPortrayal2D(DrawPolicy policy)
/*     */   {
/*  49 */     super(policy);
/*  50 */     this.defaultPortrayal = new HexagonalPortrayal2D();
/*     */   }
/*     */ 
/*     */   public void setObjectPosition(Object object, Point2D.Double position, DrawInfo2D fieldPortrayalInfo)
/*     */   {
/*  60 */     SparseGrid2D field = (SparseGrid2D)this.field;
/*  61 */     if (field == null) return;
/*  62 */     if (field.getObjectLocation(object) == null) return;
/*  63 */     Int2D location = (Int2D)getPositionLocation(position, fieldPortrayalInfo);
/*  64 */     if (location != null)
/*     */     {
/*  66 */       if (((object instanceof Fixed2D)) && (!((Fixed2D)object).maySetLocation(field, location))) {
/*  67 */         return;
/*     */       }
/*     */ 
/*  71 */       field.setObjectLocation(object, location);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Double2D getScale(DrawInfo2D info)
/*     */   {
/*  77 */     synchronized (info.gui.state.schedule)
/*     */     {
/*  79 */       Grid2D field = (Grid2D)this.field;
/*  80 */       if (field == null) return null;
/*     */ 
/*  82 */       int maxX = field.getWidth();
/*  83 */       int maxY = field.getHeight();
/*  84 */       if ((maxX == 0) || (maxY == 0)) return null;
/*     */ 
/*  86 */       double divideByX = maxX % 2 == 0 ? 3.0D * maxX / 2.0D + 0.5D : 3.0D * maxX / 2.0D + 2.0D;
/*  87 */       double divideByY = 1.0D + 2.0D * maxY;
/*     */ 
/*  89 */       double xScale = info.draw.width / divideByX;
/*  90 */       double yScale = info.draw.height / divideByY;
/*  91 */       return new Double2D(xScale, yScale);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getPositionLocation(Point2D.Double position, DrawInfo2D info)
/*     */   {
/*  98 */     Double2D scale = getScale(info);
/*  99 */     double xScale = scale.x;
/* 100 */     double yScale = scale.y;
/*     */ 
/* 102 */     int startx = (int)Math.floor(((position.getX() - info.draw.x) / xScale - 0.5D) / 1.5D);
/* 103 */     int starty = (int)Math.floor((position.getY() - info.draw.y) / (yScale * 2.0D));
/*     */ 
/* 105 */     return new Int2D(startx, starty);
/*     */   }
/*     */ 
/*     */   public Point2D.Double getLocationPosition(Object location, DrawInfo2D info)
/*     */   {
/* 111 */     synchronized (info.gui.state.schedule)
/*     */     {
/* 113 */       Grid2D field = (Grid2D)this.field;
/* 114 */       if (field == null) return null;
/*     */ 
/* 116 */       int maxX = field.getWidth();
/* 117 */       int maxY = field.getHeight();
/* 118 */       if ((maxX == 0) || (maxY == 0)) return null;
/*     */ 
/* 120 */       double divideByX = maxX % 2 == 0 ? 3.0D * maxX / 2.0D + 0.5D : 3.0D * maxX / 2.0D + 2.0D;
/* 121 */       double divideByY = 1.0D + 2.0D * maxY;
/*     */ 
/* 123 */       double xScale = info.draw.width / divideByX;
/* 124 */       double yScale = info.draw.height / divideByY;
/*     */ 
/* 130 */       DrawInfo2D newinfo = new DrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, Math.ceil(info.draw.width / (HEXAGONAL_RATIO * ((maxX - 1) * 3.0D / 4.0D + 1.0D))), Math.ceil(info.draw.height / (maxY + 0.5D))), info.clip);
/*     */ 
/* 134 */       newinfo.precise = info.precise;
/*     */ 
/* 136 */       Int2D loc = (Int2D)location;
/* 137 */       if (loc == null) return null;
/*     */ 
/* 139 */       int x = loc.x;
/* 140 */       int y = loc.y;
/*     */ 
/* 142 */       getxyC(x, y, xScale, yScale, info.draw.x, info.draw.y, this.xyC);
/* 143 */       getxyC(field.ulx(x, y), field.uly(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ul);
/* 144 */       getxyC(field.upx(x, y), field.upy(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_up);
/* 145 */       getxyC(field.urx(x, y), field.ury(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ur);
/*     */ 
/* 147 */       this.xPoints[0] = ((int)Math.floor(this.xyC_ur[0] - 0.5D * xScale));
/*     */ 
/* 150 */       this.yPoints[1] = ((int)Math.floor(this.xyC_up[1] + yScale));
/*     */ 
/* 153 */       this.xPoints[3] = ((int)Math.floor(this.xyC_ul[0] + 0.5D * xScale));
/*     */ 
/* 156 */       this.yPoints[4] = ((int)Math.floor(this.xyC[1] + yScale));
/*     */ 
/* 163 */       newinfo.draw.x = this.xPoints[3];
/* 164 */       newinfo.draw.y = this.yPoints[1];
/*     */ 
/* 167 */       newinfo.draw.x += (this.xPoints[0] - this.xPoints[3]) / 2.0D;
/* 168 */       newinfo.draw.y += (this.yPoints[4] - this.yPoints[1]) / 2.0D;
/*     */ 
/* 170 */       return new Point2D.Double(newinfo.draw.x, newinfo.draw.y);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void hitOrDraw(Graphics2D graphics, DrawInfo2D info, Bag putInHere)
/*     */   {
/* 177 */     SparseGrid2D field = (SparseGrid2D)this.field;
/* 178 */     if (field == null) return;
/*     */ 
/* 180 */     boolean objectSelected = !this.selectedWrappers.isEmpty();
/*     */ 
/* 182 */     int maxX = field.getWidth();
/* 183 */     int maxY = field.getHeight();
/* 184 */     if ((maxX == 0) || (maxY == 0)) return;
/*     */ 
/* 186 */     double divideByX = maxX % 2 == 0 ? 3.0D * maxX / 2.0D + 0.5D : 3.0D * maxX / 2.0D + 2.0D;
/* 187 */     double divideByY = 1.0D + 2.0D * maxY;
/*     */ 
/* 189 */     double xScale = info.draw.width / divideByX;
/* 190 */     double yScale = info.draw.height / divideByY;
/* 191 */     int startx = (int)Math.floor(((info.clip.x - info.draw.x) / xScale - 0.5D) / 1.5D) - 2;
/* 192 */     int starty = (int)Math.floor((info.clip.y - info.draw.y) / (yScale * 2.0D)) - 2;
/* 193 */     int endx = (int)Math.floor(((info.clip.x - info.draw.x + info.clip.width) / xScale - 0.5D) / 1.5D) + 4;
/* 194 */     int endy = (int)Math.floor((info.clip.y - info.draw.y + info.clip.height) / (yScale * 2.0D)) + 4;
/*     */ 
/* 219 */     DrawInfo2D newinfo = new DrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, Math.ceil(info.draw.width / (HEXAGONAL_RATIO * ((maxX - 1) * 3.0D / 4.0D + 1.0D))), Math.ceil(info.draw.height / (maxY + 0.5D))), info.clip);
/*     */ 
/* 223 */     newinfo.precise = info.precise;
/* 224 */     newinfo.fieldPortrayal = this;
/*     */ 
/* 231 */     if ((this.policy != null) && (graphics != null))
/*     */     {
/* 233 */       Bag policyBag = new Bag();
/* 234 */       Iterator iterator = field.locationBagIterator();
/* 235 */       while (iterator.hasNext())
/*     */       {
/* 237 */         Bag objects = (Bag)iterator.next();
/*     */ 
/* 240 */         policyBag.clear();
/* 241 */         if (this.policy.objectToDraw(objects, policyBag)) {
/* 242 */           objects = policyBag;
/*     */         }
/*     */ 
/* 245 */         for (int xO = 0; xO < objects.numObjs; xO++)
/*     */         {
/* 247 */           Object portrayedObject = objects.objs[xO];
/* 248 */           Int2D loc = field.getObjectLocation(portrayedObject);
/* 249 */           int x = loc.x;
/* 250 */           int y = loc.y;
/*     */ 
/* 255 */           if ((loc.x >= startx - 2) && (loc.x < endx + 4) && (loc.y >= starty - 2) && (loc.y < endy + 4))
/*     */           {
/* 258 */             Portrayal p = getPortrayalForObject(portrayedObject);
/* 259 */             if (!(p instanceof SimplePortrayal2D)) {
/* 260 */               throw new RuntimeException("Unexpected Portrayal " + p + " for object " + portrayedObject + " -- expected a SimplePortrayal2D");
/*     */             }
/* 262 */             SimplePortrayal2D portrayal = (SimplePortrayal2D)p;
/*     */ 
/* 264 */             getxyC(x, y, xScale, yScale, info.draw.x, info.draw.y, this.xyC);
/* 265 */             getxyC(field.ulx(x, y), field.uly(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ul);
/* 266 */             getxyC(field.upx(x, y), field.upy(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_up);
/* 267 */             getxyC(field.urx(x, y), field.ury(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ur);
/*     */ 
/* 269 */             this.xPoints[0] = ((int)Math.floor(this.xyC_ur[0] - 0.5D * xScale));
/*     */ 
/* 272 */             this.yPoints[1] = ((int)Math.floor(this.xyC_up[1] + yScale));
/*     */ 
/* 275 */             this.xPoints[3] = ((int)Math.floor(this.xyC_ul[0] + 0.5D * xScale));
/*     */ 
/* 278 */             this.yPoints[4] = ((int)Math.floor(this.xyC[1] + yScale));
/*     */ 
/* 285 */             newinfo.draw.x = this.xPoints[3];
/* 286 */             newinfo.draw.y = this.yPoints[1];
/*     */ 
/* 289 */             newinfo.draw.x += (this.xPoints[0] - this.xPoints[3]) / 2.0D;
/* 290 */             newinfo.draw.y += (this.yPoints[4] - this.yPoints[1]) / 2.0D;
/*     */ 
/* 292 */             newinfo.location = loc;
/*     */ 
/* 304 */             newinfo.selected = ((objectSelected) && (this.selectedWrappers.get(portrayedObject) != null));
/*     */ 
/* 313 */             portrayal.draw(portrayedObject, graphics, newinfo);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 321 */       Bag objects = field.getAllObjects();
/* 322 */       for (int xO = 0; xO < objects.numObjs; xO++)
/*     */       {
/* 324 */         Object portrayedObject = objects.objs[xO];
/* 325 */         Int2D loc = field.getObjectLocation(portrayedObject);
/* 326 */         int x = loc.x;
/* 327 */         int y = loc.y;
/*     */ 
/* 332 */         if ((loc.x >= startx - 2) && (loc.x < endx + 4) && (loc.y >= starty - 2) && (loc.y < endy + 4))
/*     */         {
/* 335 */           Portrayal p = getPortrayalForObject(portrayedObject);
/* 336 */           if (!(p instanceof SimplePortrayal2D)) {
/* 337 */             throw new RuntimeException("Unexpected Portrayal " + p + " for object " + portrayedObject + " -- expected a SimplePortrayal2D");
/*     */           }
/* 339 */           SimplePortrayal2D portrayal = (SimplePortrayal2D)p;
/*     */ 
/* 341 */           getxyC(x, y, xScale, yScale, info.draw.x, info.draw.y, this.xyC);
/* 342 */           getxyC(field.ulx(x, y), field.uly(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ul);
/* 343 */           getxyC(field.upx(x, y), field.upy(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_up);
/* 344 */           getxyC(field.urx(x, y), field.ury(x, y), xScale, yScale, info.draw.x, info.draw.y, this.xyC_ur);
/*     */ 
/* 346 */           this.xPoints[0] = ((int)Math.floor(this.xyC_ur[0] - 0.5D * xScale));
/*     */ 
/* 349 */           this.yPoints[1] = ((int)Math.floor(this.xyC_up[1] + yScale));
/*     */ 
/* 352 */           this.xPoints[3] = ((int)Math.floor(this.xyC_ul[0] + 0.5D * xScale));
/*     */ 
/* 355 */           this.yPoints[4] = ((int)Math.floor(this.xyC[1] + yScale));
/*     */ 
/* 362 */           newinfo.draw.x = this.xPoints[3];
/* 363 */           newinfo.draw.y = this.yPoints[1];
/*     */ 
/* 366 */           newinfo.draw.x += (this.xPoints[0] - this.xPoints[3]) / 2.0D;
/* 367 */           newinfo.draw.y += (this.yPoints[4] - this.yPoints[1]) / 2.0D;
/*     */ 
/* 369 */           if (graphics == null)
/*     */           {
/* 371 */             if (portrayal.hitObject(portrayedObject, newinfo)) {
/* 372 */               putInHere.add(getWrapper(portrayedObject));
/*     */             }
/*     */ 
/*     */           }
/* 378 */           else if ((objectSelected) && (this.selectedWrappers.get(portrayedObject) != null))
/*     */           {
/* 381 */             LocationWrapper wrapper = (LocationWrapper)this.selectedWrappers.get(portrayedObject);
/* 382 */             portrayal.setSelected(wrapper, true);
/* 383 */             portrayal.draw(portrayedObject, graphics, newinfo);
/* 384 */             portrayal.setSelected(wrapper, false);
/*     */           } else {
/* 386 */             portrayal.draw(portrayedObject, graphics, newinfo);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setBorder(boolean on)
/*     */   {
/* 395 */     throw new RuntimeException("Border drawing is not supported by hexagonal portrayals.");
/*     */   }
/*     */   public void setGridLines(boolean on) {
/* 398 */     throw new RuntimeException("Grid line drawing is not supported by hexagonal portrayals.");
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.grid.HexaSparseGridPortrayal2D
 * JD-Core Version:    0.6.2
 */