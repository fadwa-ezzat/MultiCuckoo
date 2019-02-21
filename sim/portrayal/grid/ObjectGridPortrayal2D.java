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
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.grid.Grid2D;
/*     */ import sim.field.grid.ObjectGrid2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.FieldPortrayal;
/*     */ import sim.portrayal.FieldPortrayal2D;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.portrayal.simple.OvalPortrayal2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.IntBag;
/*     */ import sim.util.MutableInt2D;
/*     */ 
/*     */ public class ObjectGridPortrayal2D extends FieldPortrayal2D
/*     */ {
/*  31 */   SimplePortrayal2D defaultPortrayal = new OvalPortrayal2D();
/*  32 */   SimplePortrayal2D defaultNullPortrayal = new SimplePortrayal2D();
/*     */ 
/* 144 */   protected final MutableInt2D locationToPass = new MutableInt2D(0, 0);
/*     */   static final int SEARCH_DISTANCE = 3;
/* 225 */   IntBag xPos = new IntBag(49);
/* 226 */   IntBag yPos = new IntBag(49);
/*     */ 
/* 252 */   final Message unknown = new Message("It's too costly to figure out where the object went.");
/*     */ 
/* 290 */   LocationWrapper selectedWrapper = null;
/* 291 */   HashMap selectedWrappers = new HashMap();
/*     */ 
/* 318 */   double gridLineFraction = 0.125D;
/* 319 */   Color gridColor = Color.blue;
/* 320 */   int gridModulus = 10;
/* 321 */   double gridMinSpacing = 2.0D;
/* 322 */   double gridLineMinWidth = 1.0D;
/* 323 */   double gridLineMaxWidth = (1.0D / 0.0D);
/* 324 */   boolean gridLines = false;
/*     */ 
/* 375 */   double borderLineFraction = 0.125D;
/* 376 */   Color borderColor = Color.red;
/* 377 */   double borderLineMinWidth = 1.0D;
/* 378 */   double borderLineMaxWidth = (1.0D / 0.0D);
/* 379 */   boolean border = false;
/*     */ 
/*     */   public void setField(Object field)
/*     */   {
/*  41 */     if ((field instanceof ObjectGrid2D)) super.setField(field); else
/*  42 */       throw new RuntimeException("Invalid field for ObjectGridPortrayal2D: " + field);
/*     */   }
/*     */ 
/*     */   public Portrayal getDefaultPortrayal()
/*     */   {
/*  47 */     return this.defaultPortrayal;
/*     */   }
/*     */ 
/*     */   public Portrayal getDefaultNullPortrayal()
/*     */   {
/*  52 */     return this.defaultNullPortrayal;
/*     */   }
/*     */ 
/*     */   public Double2D getScale(DrawInfo2D info)
/*     */   {
/*  57 */     synchronized (info.gui.state.schedule)
/*     */     {
/*  59 */       Grid2D field = (Grid2D)this.field;
/*  60 */       if (field == null) return null;
/*     */ 
/*  62 */       int maxX = field.getWidth();
/*  63 */       int maxY = field.getHeight();
/*     */ 
/*  65 */       double xScale = info.draw.width / maxX;
/*  66 */       double yScale = info.draw.height / maxY;
/*  67 */       return new Double2D(xScale, yScale);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getPositionLocation(Point2D.Double position, DrawInfo2D info)
/*     */   {
/*  73 */     Double2D scale = getScale(info);
/*  74 */     double xScale = scale.x;
/*  75 */     double yScale = scale.y;
/*     */ 
/*  77 */     int startx = (int)((position.getX() - info.draw.x) / xScale);
/*  78 */     int starty = (int)((position.getY() - info.draw.y) / yScale);
/*  79 */     return new Int2D(startx, starty);
/*     */   }
/*     */ 
/*     */   public Object getObjectLocation(Object object, GUIState gui)
/*     */   {
/*  85 */     synchronized (gui.state.schedule)
/*     */     {
/*  87 */       ObjectGrid2D field = (ObjectGrid2D)this.field;
/*  88 */       if (field == null) return null;
/*     */ 
/*  90 */       int maxX = field.getWidth();
/*  91 */       int maxY = field.getHeight();
/*     */ 
/*  94 */       for (int x = 0; x < maxX; x++)
/*     */       {
/*  96 */         Object[] fieldx = field.field[x];
/*  97 */         for (int y = 0; y < maxY; y++)
/*  98 */           if (object == fieldx[y])
/*  99 */             return new Int2D(x, y);
/*     */       }
/* 101 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Point2D.Double getLocationPosition(Object location, DrawInfo2D info)
/*     */   {
/* 107 */     synchronized (info.gui.state.schedule)
/*     */     {
/* 109 */       Grid2D field = (Grid2D)this.field;
/* 110 */       if (field == null) return null;
/*     */ 
/* 112 */       int maxX = field.getWidth();
/* 113 */       int maxY = field.getHeight();
/* 114 */       if ((maxX == 0) || (maxY == 0)) return null;
/*     */ 
/* 116 */       double xScale = info.draw.width / maxX;
/* 117 */       double yScale = info.draw.height / maxY;
/*     */ 
/* 119 */       DrawInfo2D newinfo = new DrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, xScale, yScale), info.clip);
/* 120 */       newinfo.precise = info.precise;
/*     */ 
/* 122 */       Int2D loc = (Int2D)location;
/* 123 */       if (location == null) return null;
/*     */ 
/* 125 */       int x = loc.x;
/* 126 */       int y = loc.y;
/*     */ 
/* 129 */       newinfo.draw.x = ((int)(info.draw.x + xScale * x));
/* 130 */       newinfo.draw.y = ((int)(info.draw.y + yScale * y));
/* 131 */       newinfo.draw.width = ((int)(info.draw.x + xScale * (x + 1)) - newinfo.draw.x);
/* 132 */       newinfo.draw.height = ((int)(info.draw.y + yScale * (y + 1)) - newinfo.draw.y);
/*     */ 
/* 135 */       newinfo.draw.x += newinfo.draw.width / 2.0D;
/* 136 */       newinfo.draw.y += newinfo.draw.height / 2.0D;
/*     */ 
/* 138 */       return new Point2D.Double(newinfo.draw.x, newinfo.draw.y);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void hitOrDraw(Graphics2D graphics, DrawInfo2D info, Bag putInHere)
/*     */   {
/* 148 */     ObjectGrid2D field = (ObjectGrid2D)this.field;
/*     */ 
/* 150 */     if (field == null) return;
/*     */ 
/* 152 */     boolean objectSelected = !this.selectedWrappers.isEmpty();
/* 153 */     Object selectedObject = this.selectedWrapper == null ? null : this.selectedWrapper.getObject();
/*     */ 
/* 163 */     int maxX = field.getWidth();
/* 164 */     int maxY = field.getHeight();
/* 165 */     if ((maxX == 0) || (maxY == 0)) return;
/*     */ 
/* 167 */     double xScale = info.draw.width / maxX;
/* 168 */     double yScale = info.draw.height / maxY;
/* 169 */     int startx = (int)((info.clip.x - info.draw.x) / xScale);
/* 170 */     int starty = (int)((info.clip.y - info.draw.y) / yScale);
/* 171 */     int endx = (int)((info.clip.x - info.draw.x + info.clip.width) / xScale) + 1;
/* 172 */     int endy = (int)((info.clip.y - info.draw.y + info.clip.height) / yScale) + 1;
/*     */ 
/* 174 */     DrawInfo2D newinfo = new DrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, xScale, yScale), info.clip);
/* 175 */     newinfo.precise = info.precise;
/* 176 */     newinfo.location = this.locationToPass;
/* 177 */     newinfo.fieldPortrayal = this;
/*     */ 
/* 179 */     if (endx > maxX) endx = maxX;
/* 180 */     if (endy > maxY) endy = maxY;
/* 181 */     if (startx < 0) startx = 0;
/* 182 */     if (starty < 0) starty = 0;
/* 183 */     for (int x = startx; x < endx; x++) {
/* 184 */       for (int y = starty; y < endy; y++)
/*     */       {
/* 186 */         Object obj = field.field[x][y];
/* 187 */         Portrayal p = getPortrayalForObject(obj);
/* 188 */         if (!(p instanceof SimplePortrayal2D)) {
/* 189 */           throw new RuntimeException("Unexpected Portrayal " + p + " for object " + obj + " -- expected a SimplePortrayal2D");
/*     */         }
/* 191 */         SimplePortrayal2D portrayal = (SimplePortrayal2D)p;
/*     */ 
/* 194 */         newinfo.draw.x = ((int)(info.draw.x + xScale * x));
/* 195 */         newinfo.draw.y = ((int)(info.draw.y + yScale * y));
/* 196 */         newinfo.draw.width = ((int)(info.draw.x + xScale * (x + 1)) - newinfo.draw.x);
/* 197 */         newinfo.draw.height = ((int)(info.draw.y + yScale * (y + 1)) - newinfo.draw.y);
/*     */ 
/* 200 */         newinfo.draw.x += newinfo.draw.width / 2.0D;
/* 201 */         newinfo.draw.y += newinfo.draw.height / 2.0D;
/*     */ 
/* 203 */         this.locationToPass.x = x;
/* 204 */         this.locationToPass.y = y;
/*     */ 
/* 206 */         if (graphics == null)
/*     */         {
/* 208 */           if ((obj != null) && (portrayal.hitObject(obj, newinfo)))
/* 209 */             putInHere.add(getWrapper(obj, new Int2D(x, y)));
/*     */         }
/*     */         else
/*     */         {
/* 213 */           newinfo.selected = ((objectSelected) && ((selectedObject == obj) || (this.selectedWrappers.get(obj) != null)));
/*     */ 
/* 215 */           portrayal.draw(obj, graphics, newinfo);
/*     */         }
/*     */       }
/*     */     }
/* 219 */     drawGrid(graphics, xScale, yScale, maxX, maxY, info);
/* 220 */     drawBorder(graphics, xScale, info);
/*     */   }
/*     */ 
/*     */   Int2D searchForObject(Object object, Int2D loc)
/*     */   {
/* 230 */     ObjectGrid2D field = (ObjectGrid2D)this.field;
/* 231 */     Object[][] grid = field.field;
/* 232 */     if (grid[loc.x][loc.y] == object) {
/* 233 */       return new Int2D(loc.x, loc.y);
/*     */     }
/* 235 */     field.getMooreLocations(loc.x, loc.y, 3, 2, true, this.xPos, this.yPos);
/* 236 */     for (int i = 0; i < this.xPos.numObjs; i++)
/* 237 */       if (grid[this.xPos.get(i)][this.yPos.get(i)] == object) return new Int2D(this.xPos.get(i), this.yPos.get(i));
/* 238 */     return null;
/*     */   }
/*     */ 
/*     */   public LocationWrapper getWrapper(Object object, Int2D location)
/*     */   {
/* 255 */     final ObjectGrid2D field = (ObjectGrid2D)this.field;
/* 256 */     return new LocationWrapper(object, location, this)
/*     */     {
/*     */       public Object getLocation()
/*     */       {
/* 260 */         Int2D loc = (Int2D)super.getLocation();
/* 261 */         if (field.field[loc.x][loc.y] == getObject())
/*     */         {
/* 263 */           return loc;
/*     */         }
/*     */ 
/* 267 */         Int2D result = ObjectGridPortrayal2D.this.searchForObject(this.object, loc);
/* 268 */         if (result != null)
/*     */         {
/* 270 */           this.location = result;
/* 271 */           return result;
/*     */         }
/*     */ 
/* 275 */         return ObjectGridPortrayal2D.this.unknown;
/*     */       }
/*     */ 
/*     */       public String getLocationName()
/*     */       {
/* 282 */         Object loc = getLocation();
/* 283 */         if ((loc instanceof Int2D))
/* 284 */           return ((Int2D)this.location).toCoordinates();
/* 285 */         return "Location Unknown";
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/* 294 */     if (wrapper == null) return true;
/* 295 */     if (wrapper.getFieldPortrayal() != this) return true;
/*     */ 
/* 297 */     Object obj = wrapper.getObject();
/* 298 */     boolean b = getPortrayalForObject(obj).setSelected(wrapper, selected);
/* 299 */     if (selected)
/*     */     {
/* 301 */       if (!b) return false;
/* 302 */       this.selectedWrappers.put(obj, wrapper);
/* 303 */       this.selectedWrapper = wrapper;
/*     */     }
/*     */     else
/*     */     {
/* 307 */       this.selectedWrappers.remove(obj);
/* 308 */       this.selectedWrapper = null;
/*     */     }
/* 310 */     return true;
/*     */   }
/*     */ 
/*     */   public void setGridLines(boolean on)
/*     */   {
/* 327 */     this.gridLines = on;
/*     */   }
/*     */ 
/*     */   public void setGridColor(Color val)
/*     */   {
/* 332 */     if (val == null) throw new RuntimeException("color must be non-null");
/* 333 */     this.gridColor = val;
/*     */   }
/*     */ 
/*     */   public void setGridModulus(int val)
/*     */   {
/* 340 */     if (val <= 0) throw new RuntimeException("modulus must be > 0");
/* 341 */     this.gridModulus = val;
/*     */   }
/*     */ 
/*     */   public void setGridMinSpacing(double val)
/*     */   {
/* 349 */     if ((val < 0.0D) || (val > 1.0D)) throw new RuntimeException("grid min spacing must be > 0");
/* 350 */     this.gridMinSpacing = val;
/*     */   }
/*     */ 
/*     */   public void setGridLineFraction(double val)
/*     */   {
/* 358 */     if (val <= 0.0D) throw new RuntimeException("gridLineFraction must be between 0 and 1");
/* 359 */     this.gridLineFraction = val;
/*     */   }
/*     */ 
/*     */   public void setGridLineMinMaxWidth(double min, double max)
/*     */   {
/* 366 */     if (min <= 0.0D) throw new RuntimeException("minimum width must be between >= 0");
/* 367 */     if (min > max) throw new RuntimeException("maximum width must be >= minimum width");
/* 368 */     this.gridLineMinWidth = min;
/* 369 */     this.gridLineMaxWidth = max;
/*     */   }
/*     */ 
/*     */   public void setBorder(boolean on)
/*     */   {
/* 382 */     this.border = on;
/*     */   }
/*     */ 
/*     */   public void setBorderColor(Color val)
/*     */   {
/* 387 */     if (val == null) throw new RuntimeException("color must be non-null");
/* 388 */     this.borderColor = val;
/*     */   }
/*     */ 
/*     */   public void setBorderLineFraction(double val)
/*     */   {
/* 398 */     if (val <= 0.0D) throw new RuntimeException("borderLineFraction must be between 0 and 1");
/* 399 */     this.borderLineFraction = val;
/*     */   }
/*     */ 
/*     */   public void setBorderLineMinMaxWidth(double min, double max)
/*     */   {
/* 406 */     if (min <= 0.0D) throw new RuntimeException("minimum width must be between >= 0");
/* 407 */     if (min > max) throw new RuntimeException("maximum width must be >= minimum width");
/* 408 */     this.borderLineMinWidth = min;
/* 409 */     this.borderLineMaxWidth = max;
/*     */   }
/*     */ 
/*     */   void drawBorder(Graphics2D graphics, double xScale, DrawInfo2D info)
/*     */   {
/* 415 */     if ((this.border) && (graphics != null))
/*     */     {
/* 417 */       Stroke oldStroke = graphics.getStroke();
/* 418 */       Paint oldPaint = graphics.getPaint();
/* 419 */       Rectangle2D.Double d = new Rectangle2D.Double();
/* 420 */       graphics.setColor(this.borderColor);
/* 421 */       graphics.setStroke(new BasicStroke((float)Math.min(this.borderLineMaxWidth, Math.max(this.borderLineMinWidth, xScale * this.borderLineFraction))));
/* 422 */       d.setRect(info.draw.x, info.draw.y, info.draw.x + info.draw.width, info.draw.y + info.draw.height);
/* 423 */       graphics.draw(d);
/* 424 */       graphics.setStroke(oldStroke);
/* 425 */       graphics.setPaint(oldPaint);
/*     */     }
/*     */   }
/*     */ 
/*     */   void drawGrid(Graphics2D graphics, double xScale, double yScale, int maxX, int maxY, DrawInfo2D info)
/*     */   {
/* 432 */     if ((this.gridLines) && (graphics != null))
/*     */     {
/* 435 */       int skipX = this.gridModulus;
/* 436 */       while (skipX * xScale < this.gridMinSpacing) skipX *= 2;
/* 437 */       int skipY = this.gridModulus;
/* 438 */       while (skipY * yScale < this.gridMinSpacing) skipY *= 2;
/*     */ 
/* 440 */       Stroke oldStroke = graphics.getStroke();
/* 441 */       Paint oldPaint = graphics.getPaint();
/* 442 */       Line2D.Double d = new Line2D.Double();
/* 443 */       graphics.setColor(this.gridColor);
/* 444 */       graphics.setStroke(new BasicStroke((float)Math.min(this.gridLineMaxWidth, Math.max(this.gridLineMinWidth, xScale * this.gridLineFraction))));
/* 445 */       for (int i = this.gridModulus; i < maxX; i += skipX)
/*     */       {
/* 447 */         d.setLine(info.draw.x + xScale * i, info.draw.y, info.draw.x + xScale * i, info.draw.y + info.draw.height);
/* 448 */         graphics.draw(d);
/*     */       }
/*     */ 
/* 451 */       graphics.setStroke(new BasicStroke((float)Math.min(this.gridLineMaxWidth, Math.max(this.gridLineMinWidth, yScale * this.gridLineFraction))));
/* 452 */       for (int i = this.gridModulus; i < maxY; i += skipY)
/*     */       {
/* 454 */         d.setLine(info.draw.x, info.draw.y + yScale * i, info.draw.x + info.draw.width, info.draw.y + yScale * i);
/* 455 */         graphics.draw(d);
/*     */       }
/* 457 */       graphics.setStroke(oldStroke);
/* 458 */       graphics.setPaint(oldPaint);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Message
/*     */   {
/*     */     String message;
/*     */ 
/*     */     public Message(String message)
/*     */     {
/* 245 */       this.message = message;
/*     */     }
/*     */     public String getSorry() {
/* 248 */       return this.message;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.grid.ObjectGridPortrayal2D
 * JD-Core Version:    0.6.2
 */