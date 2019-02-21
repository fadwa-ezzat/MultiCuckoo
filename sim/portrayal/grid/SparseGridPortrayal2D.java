/*     */ package sim.portrayal.grid;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Line2D.Double;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.grid.Grid2D;
/*     */ import sim.field.grid.SparseGrid2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.FieldPortrayal;
/*     */ import sim.portrayal.FieldPortrayal2D;
/*     */ import sim.portrayal.Fixed2D;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.portrayal.inspector.StableInt2D;
/*     */ import sim.portrayal.simple.OvalPortrayal2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.Int2D;
/*     */ 
/*     */ public class SparseGridPortrayal2D extends FieldPortrayal2D
/*     */ {
/*     */   public DrawPolicy policy;
/*  53 */   SimplePortrayal2D defaultPortrayal = new OvalPortrayal2D();
/*     */ 
/* 323 */   HashMap selectedWrappers = new HashMap();
/*     */ 
/* 348 */   double gridLineFraction = 0.125D;
/* 349 */   Color gridColor = Color.blue;
/* 350 */   int gridModulus = 10;
/* 351 */   double gridMinSpacing = 2.0D;
/* 352 */   double gridLineMinWidth = 1.0D;
/* 353 */   double gridLineMaxWidth = (1.0D / 0.0D);
/* 354 */   boolean gridLines = false;
/*     */ 
/* 405 */   double borderLineFraction = 0.125D;
/* 406 */   Color borderColor = Color.red;
/* 407 */   double borderLineMinWidth = 1.0D;
/* 408 */   double borderLineMaxWidth = (1.0D / 0.0D);
/* 409 */   boolean border = false;
/*     */ 
/*     */   public SparseGridPortrayal2D()
/*     */   {
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public SparseGridPortrayal2D(DrawPolicy policy)
/*     */   {
/*  39 */     this.policy = policy;
/*     */   }
/*     */ 
/*     */   public void setDrawPolicy(DrawPolicy policy)
/*     */   {
/*  44 */     this.policy = policy;
/*     */   }
/*     */ 
/*     */   public DrawPolicy getDrawPolicy()
/*     */   {
/*  49 */     return this.policy;
/*     */   }
/*     */ 
/*     */   public Portrayal getDefaultPortrayal()
/*     */   {
/*  57 */     return this.defaultPortrayal;
/*     */   }
/*     */ 
/*     */   public void setField(Object field)
/*     */   {
/*  62 */     if ((field instanceof SparseGrid2D)) super.setField(field); else
/*  63 */       throw new RuntimeException("Invalid field for Sparse2DPortrayal: " + field);
/*     */   }
/*     */ 
/*     */   public Double2D getScale(DrawInfo2D info)
/*     */   {
/*  68 */     synchronized (info.gui.state.schedule)
/*     */     {
/*  70 */       Grid2D field = (Grid2D)this.field;
/*  71 */       if (field == null) return null;
/*     */ 
/*  73 */       int maxX = field.getWidth();
/*  74 */       int maxY = field.getHeight();
/*     */ 
/*  76 */       double xScale = info.draw.width / maxX;
/*  77 */       double yScale = info.draw.height / maxY;
/*  78 */       return new Double2D(xScale, yScale);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getPositionLocation(Point2D.Double position, DrawInfo2D info)
/*     */   {
/*  84 */     Double2D scale = getScale(info);
/*  85 */     double xScale = scale.x;
/*  86 */     double yScale = scale.y;
/*     */ 
/*  88 */     int startx = (int)Math.floor((position.getX() - info.draw.x) / xScale);
/*  89 */     int starty = (int)Math.floor((position.getY() - info.draw.y) / yScale);
/*  90 */     return new Int2D(startx, starty);
/*     */   }
/*     */ 
/*     */   public void setObjectPosition(Object object, Point2D.Double position, DrawInfo2D fieldPortrayalInfo)
/*     */   {
/*  95 */     synchronized (fieldPortrayalInfo.gui.state.schedule)
/*     */     {
/*  97 */       SparseGrid2D field = (SparseGrid2D)this.field;
/*  98 */       if (field == null) return;
/*  99 */       if (field.getObjectLocation(object) == null) return;
/* 100 */       Int2D location = (Int2D)getPositionLocation(position, fieldPortrayalInfo);
/* 101 */       if (location != null)
/*     */       {
/* 103 */         if (((object instanceof Fixed2D)) && (!((Fixed2D)object).maySetLocation(field, location))) {
/* 104 */           return;
/*     */         }
/*     */ 
/* 107 */         if (location != null)
/* 108 */           field.setObjectLocation(object, location);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getObjectLocation(Object object, GUIState gui)
/*     */   {
/* 115 */     synchronized (gui.state.schedule)
/*     */     {
/* 117 */       SparseGrid2D field = (SparseGrid2D)this.field;
/* 118 */       if (field == null) return null;
/* 119 */       return field.getObjectLocation(object);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Point2D.Double getLocationPosition(Object location, DrawInfo2D info)
/*     */   {
/* 125 */     synchronized (info.gui.state.schedule)
/*     */     {
/* 127 */       Grid2D field = (Grid2D)this.field;
/* 128 */       if (field == null) return null;
/*     */ 
/* 130 */       int maxX = field.getWidth();
/* 131 */       int maxY = field.getHeight();
/*     */ 
/* 133 */       double xScale = info.draw.width / maxX;
/* 134 */       double yScale = info.draw.height / maxY;
/*     */ 
/* 136 */       DrawInfo2D newinfo = new DrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, xScale, yScale), info.clip);
/* 137 */       newinfo.precise = info.precise;
/*     */ 
/* 139 */       Int2D loc = (Int2D)location;
/* 140 */       if (loc == null) return null;
/*     */ 
/* 143 */       newinfo.draw.x = ((int)Math.floor(info.draw.x + xScale * loc.x));
/* 144 */       newinfo.draw.y = ((int)Math.floor(info.draw.y + yScale * loc.y));
/* 145 */       newinfo.draw.width = ((int)Math.floor(info.draw.x + xScale * (loc.x + 1)) - newinfo.draw.x);
/* 146 */       newinfo.draw.height = ((int)Math.floor(info.draw.y + yScale * (loc.y + 1)) - newinfo.draw.y);
/*     */ 
/* 149 */       newinfo.draw.x += newinfo.draw.width / 2.0D;
/* 150 */       newinfo.draw.y += newinfo.draw.height / 2.0D;
/*     */ 
/* 152 */       return new Point2D.Double(newinfo.draw.x, newinfo.draw.y);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void hitOrDraw(Graphics2D graphics, DrawInfo2D info, Bag putInHere)
/*     */   {
/* 159 */     SparseGrid2D field = (SparseGrid2D)this.field;
/* 160 */     if (field == null) return;
/*     */ 
/* 162 */     boolean objectSelected = !this.selectedWrappers.isEmpty();
/*     */ 
/* 164 */     int maxX = field.getWidth();
/* 165 */     int maxY = field.getHeight();
/*     */ 
/* 167 */     double xScale = info.draw.width / maxX;
/* 168 */     double yScale = info.draw.height / maxY;
/* 169 */     int startx = (int)Math.floor((info.clip.x - info.draw.x) / xScale);
/* 170 */     int starty = (int)Math.floor((info.clip.y - info.draw.y) / yScale);
/* 171 */     int endx = (int)Math.floor((info.clip.x - info.draw.x + info.clip.width) / xScale) + 1;
/* 172 */     int endy = (int)Math.floor((info.clip.y - info.draw.y + info.clip.height) / yScale) + 1;
/*     */ 
/* 177 */     DrawInfo2D newinfo = new DrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, xScale, yScale), info.clip);
/* 178 */     newinfo.precise = info.precise;
/* 179 */     newinfo.fieldPortrayal = this;
/*     */ 
/* 186 */     if ((this.policy != null) && (graphics != null))
/*     */     {
/* 188 */       Bag policyBag = new Bag();
/* 189 */       Iterator iterator = field.locationBagIterator();
/* 190 */       while (iterator.hasNext())
/*     */       {
/* 192 */         Bag objects = (Bag)iterator.next();
/*     */ 
/* 194 */         if (objects != null)
/*     */         {
/* 197 */           policyBag.clear();
/* 198 */           if (this.policy.objectToDraw(objects, policyBag)) {
/* 199 */             objects = policyBag;
/*     */           }
/*     */ 
/* 202 */           for (int x = 0; x < objects.numObjs; x++)
/*     */           {
/* 204 */             Object portrayedObject = objects.objs[x];
/* 205 */             Int2D loc = field.getObjectLocation(portrayedObject);
/*     */ 
/* 209 */             if ((loc.x >= startx - 2) && (loc.x < endx + 4) && (loc.y >= starty - 2) && (loc.y < endy + 4))
/*     */             {
/* 212 */               Portrayal p = getPortrayalForObject(portrayedObject);
/* 213 */               if (!(p instanceof SimplePortrayal2D)) {
/* 214 */                 throw new RuntimeException("Unexpected Portrayal " + p + " for object " + portrayedObject + " -- expected a SimplePortrayal2D");
/*     */               }
/* 216 */               SimplePortrayal2D portrayal = (SimplePortrayal2D)p;
/*     */ 
/* 219 */               newinfo.draw.x = ((int)Math.floor(info.draw.x + xScale * loc.x));
/* 220 */               newinfo.draw.y = ((int)Math.floor(info.draw.y + yScale * loc.y));
/* 221 */               newinfo.draw.width = ((int)Math.floor(info.draw.x + xScale * (loc.x + 1)) - newinfo.draw.x);
/* 222 */               newinfo.draw.height = ((int)Math.floor(info.draw.y + yScale * (loc.y + 1)) - newinfo.draw.y);
/*     */ 
/* 225 */               newinfo.draw.x += newinfo.draw.width / 2.0D;
/* 226 */               newinfo.draw.y += newinfo.draw.height / 2.0D;
/*     */ 
/* 228 */               newinfo.location = loc;
/*     */ 
/* 230 */               newinfo.selected = ((objectSelected) && (this.selectedWrappers.get(portrayedObject) != null));
/*     */ 
/* 240 */               portrayal.draw(portrayedObject, graphics, newinfo);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/* 247 */       Bag objects = field.getAllObjects();
/* 248 */       for (int x = 0; x < objects.numObjs; x++)
/*     */       {
/* 250 */         Object portrayedObject = objects.objs[x];
/* 251 */         Int2D loc = field.getObjectLocation(portrayedObject);
/*     */ 
/* 256 */         if ((loc.x >= startx - 2) && (loc.x < endx + 4) && (loc.y >= starty - 2) && (loc.y < endy + 4))
/*     */         {
/* 259 */           Portrayal p = getPortrayalForObject(portrayedObject);
/* 260 */           if (!(p instanceof SimplePortrayal2D)) {
/* 261 */             throw new RuntimeException("Unexpected Portrayal " + p + " for object " + portrayedObject + " -- expected a SimplePortrayal2D");
/*     */           }
/* 263 */           SimplePortrayal2D portrayal = (SimplePortrayal2D)p;
/*     */ 
/* 266 */           newinfo.draw.x = ((int)Math.floor(info.draw.x + xScale * loc.x));
/* 267 */           newinfo.draw.y = ((int)Math.floor(info.draw.y + yScale * loc.y));
/* 268 */           newinfo.draw.width = ((int)Math.floor(info.draw.x + xScale * (loc.x + 1)) - newinfo.draw.x);
/* 269 */           newinfo.draw.height = ((int)Math.floor(info.draw.y + yScale * (loc.y + 1)) - newinfo.draw.y);
/*     */ 
/* 272 */           newinfo.draw.x += newinfo.draw.width / 2.0D;
/* 273 */           newinfo.draw.y += newinfo.draw.height / 2.0D;
/*     */ 
/* 275 */           if (graphics == null)
/*     */           {
/* 277 */             if (portrayal.hitObject(portrayedObject, newinfo))
/*     */             {
/* 279 */               putInHere.add(getWrapper(portrayedObject));
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 286 */             newinfo.selected = ((objectSelected) && (this.selectedWrappers.get(portrayedObject) != null));
/*     */ 
/* 294 */             portrayal.draw(portrayedObject, graphics, newinfo);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 300 */     drawGrid(graphics, xScale, yScale, maxX, maxY, info);
/* 301 */     drawBorder(graphics, xScale, info);
/*     */   }
/*     */ 
/*     */   public LocationWrapper getWrapper(Object object)
/*     */   {
/* 307 */     SparseGrid2D field = (SparseGrid2D)this.field;
/* 308 */     final StableInt2D w = new StableInt2D(field, object);
/* 309 */     return new LocationWrapper(object, null, this)
/*     */     {
/*     */       public Object getLocation()
/*     */       {
/* 313 */         return w;
/*     */       }
/*     */ 
/*     */       public String getLocationName()
/*     */       {
/* 318 */         return w.toString();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/* 326 */     if (wrapper == null) return true;
/* 327 */     if (wrapper.getFieldPortrayal() != this) return true;
/*     */ 
/* 329 */     Object obj = wrapper.getObject();
/* 330 */     boolean b = getPortrayalForObject(obj).setSelected(wrapper, selected);
/* 331 */     if (selected)
/*     */     {
/* 333 */       if (!b) return false;
/* 334 */       this.selectedWrappers.put(obj, wrapper);
/*     */     }
/*     */     else
/*     */     {
/* 338 */       this.selectedWrappers.remove(obj);
/*     */     }
/* 340 */     return true;
/*     */   }
/*     */ 
/*     */   public void setGridLines(boolean on)
/*     */   {
/* 357 */     this.gridLines = on;
/*     */   }
/*     */ 
/*     */   public void setGridColor(Color val)
/*     */   {
/* 362 */     if (val == null) throw new RuntimeException("color must be non-null");
/* 363 */     this.gridColor = val;
/*     */   }
/*     */ 
/*     */   public void setGridModulus(int val)
/*     */   {
/* 370 */     if (val <= 0) throw new RuntimeException("modulus must be > 0");
/* 371 */     this.gridModulus = val;
/*     */   }
/*     */ 
/*     */   public void setGridMinSpacing(double val)
/*     */   {
/* 379 */     if ((val < 0.0D) || (val > 1.0D)) throw new RuntimeException("grid min spacing must be > 0");
/* 380 */     this.gridMinSpacing = val;
/*     */   }
/*     */ 
/*     */   public void setGridLineFraction(double val)
/*     */   {
/* 388 */     if (val <= 0.0D) throw new RuntimeException("gridLineFraction must be between 0 and 1");
/* 389 */     this.gridLineFraction = val;
/*     */   }
/*     */ 
/*     */   public void setGridLineMinMaxWidth(double min, double max)
/*     */   {
/* 396 */     if (min <= 0.0D) throw new RuntimeException("minimum width must be between >= 0");
/* 397 */     if (min > max) throw new RuntimeException("maximum width must be >= minimum width");
/* 398 */     this.gridLineMinWidth = min;
/* 399 */     this.gridLineMaxWidth = max;
/*     */   }
/*     */ 
/*     */   public void setBorder(boolean on)
/*     */   {
/* 412 */     this.border = on;
/*     */   }
/*     */ 
/*     */   public void setBorderColor(Color val)
/*     */   {
/* 417 */     if (val == null) throw new RuntimeException("color must be non-null");
/* 418 */     this.borderColor = val;
/*     */   }
/*     */ 
/*     */   public void setBorderLineFraction(double val)
/*     */   {
/* 428 */     if (val <= 0.0D) throw new RuntimeException("borderLineFraction must be between 0 and 1");
/* 429 */     this.borderLineFraction = val;
/*     */   }
/*     */ 
/*     */   public void setBorderLineMinMaxWidth(double min, double max)
/*     */   {
/* 436 */     if (min <= 0.0D) throw new RuntimeException("minimum width must be between >= 0");
/* 437 */     if (min > max) throw new RuntimeException("maximum width must be >= minimum width");
/* 438 */     this.borderLineMinWidth = min;
/* 439 */     this.borderLineMaxWidth = max;
/*     */   }
/*     */ 
/*     */   void drawBorder(Graphics2D graphics, double xScale, DrawInfo2D info)
/*     */   {
/* 445 */     if ((this.border) && (graphics != null))
/*     */     {
/* 447 */       Stroke oldStroke = graphics.getStroke();
/* 448 */       Paint oldPaint = graphics.getPaint();
/* 449 */       Rectangle2D.Double d = new Rectangle2D.Double();
/* 450 */       graphics.setColor(this.borderColor);
/* 451 */       graphics.setStroke(new BasicStroke((float)Math.min(this.borderLineMaxWidth, Math.max(this.borderLineMinWidth, xScale * this.borderLineFraction))));
/* 452 */       d.setRect(info.draw.x, info.draw.y, info.draw.x + info.draw.width, info.draw.y + info.draw.height);
/* 453 */       graphics.draw(d);
/* 454 */       graphics.setStroke(oldStroke);
/* 455 */       graphics.setPaint(oldPaint);
/*     */     }
/*     */   }
/*     */ 
/*     */   void drawGrid(Graphics2D graphics, double xScale, double yScale, int maxX, int maxY, DrawInfo2D info)
/*     */   {
/* 462 */     if ((this.gridLines) && (graphics != null))
/*     */     {
/* 465 */       int skipX = this.gridModulus;
/* 466 */       while (skipX * xScale < this.gridMinSpacing) skipX *= 2;
/* 467 */       int skipY = this.gridModulus;
/* 468 */       while (skipY * yScale < this.gridMinSpacing) skipY *= 2;
/*     */ 
/* 470 */       Stroke oldStroke = graphics.getStroke();
/* 471 */       Paint oldPaint = graphics.getPaint();
/* 472 */       Line2D.Double d = new Line2D.Double();
/* 473 */       graphics.setColor(this.gridColor);
/* 474 */       graphics.setStroke(new BasicStroke((float)Math.min(this.gridLineMaxWidth, Math.max(this.gridLineMinWidth, xScale * this.gridLineFraction))));
/* 475 */       for (int i = this.gridModulus; i < maxX; i += skipX)
/*     */       {
/* 477 */         d.setLine(info.draw.x + xScale * i, info.draw.y, info.draw.x + xScale * i, info.draw.y + info.draw.height);
/* 478 */         graphics.draw(d);
/*     */       }
/*     */ 
/* 481 */       graphics.setStroke(new BasicStroke((float)Math.min(this.gridLineMaxWidth, Math.max(this.gridLineMinWidth, yScale * this.gridLineFraction))));
/* 482 */       for (int i = this.gridModulus; i < maxY; i += skipY)
/*     */       {
/* 484 */         d.setLine(info.draw.x, info.draw.y + yScale * i, info.draw.x + info.draw.width, info.draw.y + yScale * i);
/* 485 */         graphics.draw(d);
/*     */       }
/* 487 */       graphics.setStroke(oldStroke);
/* 488 */       graphics.setPaint(oldPaint);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.grid.SparseGridPortrayal2D
 * JD-Core Version:    0.6.2
 */