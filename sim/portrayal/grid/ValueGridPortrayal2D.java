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
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ import sim.field.grid.Grid2D;
/*     */ import sim.field.grid.IntGrid2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.FieldPortrayal;
/*     */ import sim.portrayal.FieldPortrayal2D;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.portrayal.simple.ValuePortrayal2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.MutableDouble;
/*     */ import sim.util.MutableInt2D;
/*     */ import sim.util.gui.ColorMap;
/*     */ import sim.util.gui.SimpleColorMap;
/*     */ 
/*     */ public class ValueGridPortrayal2D extends FieldPortrayal2D
/*     */ {
/*  52 */   ColorMap map = new SimpleColorMap();
/*     */ 
/*  63 */   SimplePortrayal2D defaultPortrayal = new ValuePortrayal2D();
/*     */   String valueName;
/* 172 */   protected final MutableDouble valueToPass = new MutableDouble(0.0D);
/*     */ 
/* 175 */   protected final MutableInt2D locationToPass = new MutableInt2D(0, 0);
/*     */ 
/* 290 */   double gridLineFraction = 0.125D;
/* 291 */   Color gridColor = Color.blue;
/* 292 */   int gridModulus = 10;
/* 293 */   double gridMinSpacing = 2.0D;
/* 294 */   double gridLineMinWidth = 1.0D;
/* 295 */   double gridLineMaxWidth = (1.0D / 0.0D);
/* 296 */   boolean gridLines = false;
/*     */ 
/* 347 */   double borderLineFraction = 0.125D;
/* 348 */   Color borderColor = Color.red;
/* 349 */   double borderLineMinWidth = 1.0D;
/* 350 */   double borderLineMaxWidth = (1.0D / 0.0D);
/* 351 */   boolean border = false;
/*     */ 
/*     */   public ColorMap getMap()
/*     */   {
/*  53 */     return this.map; } 
/*  54 */   public void setMap(ColorMap m) { this.map = m; }
/*     */ 
/*     */   public void setField(Object field)
/*     */   {
/*  58 */     if (((field instanceof DoubleGrid2D)) || ((field instanceof IntGrid2D)))
/*  59 */       super.setField(field);
/*  60 */     else throw new RuntimeException("Invalid field for ValueGridPortrayal2D: " + field);
/*     */   }
/*     */ 
/*     */   public String getValueName()
/*     */   {
/*  66 */     return this.valueName; } 
/*  67 */   public void setValueName(String name) { this.valueName = name; }
/*     */ 
/*     */   public ValueGridPortrayal2D()
/*     */   {
/*  71 */     this("Value");
/*     */   }
/*     */ 
/*     */   public ValueGridPortrayal2D(String valueName)
/*     */   {
/*  76 */     this.valueName = valueName;
/*     */   }
/*     */ 
/*     */   public double newValue(int x, int y, double value)
/*     */   {
/*  84 */     Grid2D field = (Grid2D)this.field;
/*  85 */     if ((field instanceof IntGrid2D)) value = (int)value;
/*     */ 
/*  87 */     if (this.map.validLevel(value)) return value;
/*     */ 
/*  90 */     if (field != null)
/*     */     {
/*  92 */       if ((field instanceof DoubleGrid2D))
/*  93 */         return ((DoubleGrid2D)field).field[x][y];
/*  94 */       return ((IntGrid2D)field).field[x][y];
/*     */     }
/*  96 */     return this.map.defaultValue();
/*     */   }
/*     */ 
/*     */   public Double2D getScale(DrawInfo2D info)
/*     */   {
/* 101 */     synchronized (info.gui.state.schedule)
/*     */     {
/* 103 */       Grid2D field = (Grid2D)this.field;
/* 104 */       if (field == null) return null;
/*     */ 
/* 106 */       int maxX = field.getWidth();
/* 107 */       int maxY = field.getHeight();
/*     */ 
/* 109 */       double xScale = info.draw.width / maxX;
/* 110 */       double yScale = info.draw.height / maxY;
/* 111 */       return new Double2D(xScale, yScale);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getPositionLocation(Point2D.Double position, DrawInfo2D info)
/*     */   {
/* 117 */     Double2D scale = getScale(info);
/* 118 */     double xScale = scale.x;
/* 119 */     double yScale = scale.y;
/*     */ 
/* 121 */     int startx = (int)((position.getX() - info.draw.x) / xScale);
/* 122 */     int starty = (int)((position.getY() - info.draw.y) / yScale);
/* 123 */     return new Int2D(startx, starty);
/*     */   }
/*     */ 
/*     */   public Point2D.Double getLocationPosition(Object location, DrawInfo2D info)
/*     */   {
/* 130 */     synchronized (info.gui.state.schedule)
/*     */     {
/* 132 */       Grid2D field = (Grid2D)this.field;
/* 133 */       if (field == null) return null;
/*     */ 
/* 135 */       int maxX = field.getWidth();
/* 136 */       int maxY = field.getHeight();
/* 137 */       if ((maxX == 0) || (maxY == 0)) return null;
/*     */ 
/* 139 */       double xScale = info.draw.width / maxX;
/* 140 */       double yScale = info.draw.height / maxY;
/*     */ 
/* 142 */       DrawInfo2D newinfo = new DrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, xScale, yScale), info.clip);
/* 143 */       newinfo.precise = info.precise;
/*     */ 
/* 145 */       Int2D loc = (Int2D)location;
/* 146 */       if (location == null) return null;
/*     */ 
/* 148 */       int x = loc.x;
/* 149 */       int y = loc.y;
/*     */ 
/* 152 */       newinfo.draw.x = ((int)(info.draw.x + xScale * x));
/* 153 */       newinfo.draw.y = ((int)(info.draw.y + yScale * y));
/* 154 */       newinfo.draw.width = ((int)(info.draw.x + xScale * (x + 1)) - newinfo.draw.x);
/* 155 */       newinfo.draw.height = ((int)(info.draw.y + yScale * (y + 1)) - newinfo.draw.y);
/*     */ 
/* 158 */       newinfo.draw.x += newinfo.draw.width / 2.0D;
/* 159 */       newinfo.draw.y += newinfo.draw.height / 2.0D;
/*     */ 
/* 161 */       return new Point2D.Double(newinfo.draw.x, newinfo.draw.y);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Portrayal getDefaultPortrayal()
/*     */   {
/* 168 */     return this.defaultPortrayal;
/*     */   }
/*     */ 
/*     */   protected void hitOrDraw(Graphics2D graphics, DrawInfo2D info, Bag putInHere)
/*     */   {
/* 179 */     Grid2D field = (Grid2D)this.field;
/* 180 */     if (field == null) return;
/*     */ 
/* 190 */     int maxX = field.getWidth();
/* 191 */     int maxY = field.getHeight();
/* 192 */     if ((maxX == 0) || (maxY == 0)) return;
/*     */ 
/* 194 */     double xScale = info.draw.width / maxX;
/* 195 */     double yScale = info.draw.height / maxY;
/* 196 */     int startx = (int)((info.clip.x - info.draw.x) / xScale);
/* 197 */     int starty = (int)((info.clip.y - info.draw.y) / yScale);
/* 198 */     int endx = (int)((info.clip.x - info.draw.x + info.clip.width) / xScale) + 1;
/* 199 */     int endy = (int)((info.clip.y - info.draw.y + info.clip.height) / yScale) + 1;
/*     */ 
/* 203 */     boolean isDoubleGrid2D = field instanceof DoubleGrid2D;
/* 204 */     double[][] doubleField = isDoubleGrid2D ? ((DoubleGrid2D)field).field : (double[][])null;
/* 205 */     int[][] intField = isDoubleGrid2D ? (int[][])null : ((IntGrid2D)field).field;
/*     */ 
/* 210 */     DrawInfo2D newinfo = new DrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, xScale, yScale), info.clip);
/* 211 */     newinfo.precise = info.precise;
/* 212 */     newinfo.location = this.locationToPass;
/* 213 */     newinfo.fieldPortrayal = this;
/*     */ 
/* 215 */     Portrayal p = getPortrayalForObject(this.valueToPass);
/* 216 */     if (!(p instanceof SimplePortrayal2D)) {
/* 217 */       throw new RuntimeException("Unexpected Portrayal " + p + " for object " + this.valueToPass + " -- expected a SimplePortrayal2D");
/*     */     }
/* 219 */     SimplePortrayal2D portrayal = (SimplePortrayal2D)p;
/*     */ 
/* 221 */     if (endx > maxX) endx = maxX;
/* 222 */     if (endy > maxY) endy = maxY;
/* 223 */     if (startx < 0) startx = 0;
/* 224 */     if (starty < 0) starty = 0;
/* 225 */     for (int x = startx; x < endx; x++) {
/* 226 */       for (int y = starty; y < endy; y++)
/*     */       {
/* 229 */         this.valueToPass.val = (isDoubleGrid2D ? doubleField[x][y] : intField[x][y]);
/*     */ 
/* 232 */         newinfo.draw.x = ((int)(info.draw.x + xScale * x));
/* 233 */         newinfo.draw.y = ((int)(info.draw.y + yScale * y));
/* 234 */         newinfo.draw.width = ((int)(info.draw.x + xScale * (x + 1)) - newinfo.draw.x);
/* 235 */         newinfo.draw.height = ((int)(info.draw.y + yScale * (y + 1)) - newinfo.draw.y);
/*     */ 
/* 238 */         newinfo.draw.x += newinfo.draw.width / 2.0D;
/* 239 */         newinfo.draw.y += newinfo.draw.height / 2.0D;
/*     */ 
/* 241 */         this.locationToPass.x = x;
/* 242 */         this.locationToPass.y = y;
/*     */ 
/* 244 */         if (graphics == null)
/*     */         {
/* 246 */           if (portrayal.hitObject(this.valueToPass, newinfo)) {
/* 247 */             putInHere.add(getWrapper(this.valueToPass.val, new Int2D(x, y)));
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 253 */           portrayal.draw(this.valueToPass, graphics, newinfo);
/*     */         }
/*     */       }
/*     */     }
/* 257 */     drawGrid(graphics, xScale, yScale, maxX, maxY, info);
/* 258 */     drawBorder(graphics, xScale, info);
/*     */   }
/*     */ 
/*     */   public LocationWrapper getWrapper(double val, Int2D loc)
/*     */   {
/* 264 */     final Grid2D field = (Grid2D)this.field;
/* 265 */     return new LocationWrapper(new MutableDouble(val), loc, this)
/*     */     {
/*     */       public Object getObject()
/*     */       {
/* 270 */         Int2D loc = (Int2D)this.location;
/* 271 */         MutableDouble val = (MutableDouble)this.object;
/*     */ 
/* 273 */         if ((field instanceof DoubleGrid2D))
/* 274 */           val.val = ((DoubleGrid2D)field).field[loc.x][loc.y];
/*     */         else
/* 276 */           val.val = ((IntGrid2D)field).field[loc.x][loc.y];
/* 277 */         return val;
/*     */       }
/*     */ 
/*     */       public String getLocationName()
/*     */       {
/* 282 */         return ((Int2D)this.location).toCoordinates();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public void setGridLines(boolean on)
/*     */   {
/* 299 */     this.gridLines = on;
/*     */   }
/*     */ 
/*     */   public void setGridColor(Color val)
/*     */   {
/* 304 */     if (val == null) throw new RuntimeException("color must be non-null");
/* 305 */     this.gridColor = val;
/*     */   }
/*     */ 
/*     */   public void setGridModulus(int val)
/*     */   {
/* 312 */     if (val <= 0) throw new RuntimeException("modulus must be > 0");
/* 313 */     this.gridModulus = val;
/*     */   }
/*     */ 
/*     */   public void setGridMinSpacing(double val)
/*     */   {
/* 321 */     if ((val < 0.0D) || (val > 1.0D)) throw new RuntimeException("grid min spacing must be > 0");
/* 322 */     this.gridMinSpacing = val;
/*     */   }
/*     */ 
/*     */   public void setGridLineFraction(double val)
/*     */   {
/* 330 */     if (val <= 0.0D) throw new RuntimeException("gridLineFraction must be between 0 and 1");
/* 331 */     this.gridLineFraction = val;
/*     */   }
/*     */ 
/*     */   public void setGridLineMinMaxWidth(double min, double max)
/*     */   {
/* 338 */     if (min <= 0.0D) throw new RuntimeException("minimum width must be between >= 0");
/* 339 */     if (min > max) throw new RuntimeException("maximum width must be >= minimum width");
/* 340 */     this.gridLineMinWidth = min;
/* 341 */     this.gridLineMaxWidth = max;
/*     */   }
/*     */ 
/*     */   public void setBorder(boolean on)
/*     */   {
/* 354 */     this.border = on;
/*     */   }
/*     */ 
/*     */   public void setBorderColor(Color val)
/*     */   {
/* 359 */     if (val == null) throw new RuntimeException("color must be non-null");
/* 360 */     this.borderColor = val;
/*     */   }
/*     */ 
/*     */   public void setBorderLineFraction(double val)
/*     */   {
/* 370 */     if (val <= 0.0D) throw new RuntimeException("borderLineFraction must be between 0 and 1");
/* 371 */     this.borderLineFraction = val;
/*     */   }
/*     */ 
/*     */   public void setBorderLineMinMaxWidth(double min, double max)
/*     */   {
/* 378 */     if (min <= 0.0D) throw new RuntimeException("minimum width must be between >= 0");
/* 379 */     if (min > max) throw new RuntimeException("maximum width must be >= minimum width");
/* 380 */     this.borderLineMinWidth = min;
/* 381 */     this.borderLineMaxWidth = max;
/*     */   }
/*     */ 
/*     */   void drawBorder(Graphics2D graphics, double xScale, DrawInfo2D info)
/*     */   {
/* 387 */     if ((this.border) && (graphics != null))
/*     */     {
/* 389 */       Stroke oldStroke = graphics.getStroke();
/* 390 */       Paint oldPaint = graphics.getPaint();
/* 391 */       Rectangle2D.Double d = new Rectangle2D.Double();
/* 392 */       graphics.setColor(this.borderColor);
/* 393 */       graphics.setStroke(new BasicStroke((float)Math.min(this.borderLineMaxWidth, Math.max(this.borderLineMinWidth, xScale * this.borderLineFraction))));
/* 394 */       d.setRect(info.draw.x, info.draw.y, info.draw.x + info.draw.width, info.draw.y + info.draw.height);
/* 395 */       graphics.draw(d);
/* 396 */       graphics.setStroke(oldStroke);
/* 397 */       graphics.setPaint(oldPaint);
/*     */     }
/*     */   }
/*     */ 
/*     */   void drawGrid(Graphics2D graphics, double xScale, double yScale, int maxX, int maxY, DrawInfo2D info)
/*     */   {
/* 404 */     if ((this.gridLines) && (graphics != null))
/*     */     {
/* 407 */       int skipX = this.gridModulus;
/* 408 */       while (skipX * xScale < this.gridMinSpacing) skipX *= 2;
/* 409 */       int skipY = this.gridModulus;
/* 410 */       while (skipY * yScale < this.gridMinSpacing) skipY *= 2;
/*     */ 
/* 412 */       Stroke oldStroke = graphics.getStroke();
/* 413 */       Paint oldPaint = graphics.getPaint();
/* 414 */       Line2D.Double d = new Line2D.Double();
/* 415 */       graphics.setColor(this.gridColor);
/* 416 */       graphics.setStroke(new BasicStroke((float)Math.min(this.gridLineMaxWidth, Math.max(this.gridLineMinWidth, xScale * this.gridLineFraction))));
/* 417 */       for (int i = this.gridModulus; i < maxX; i += skipX)
/*     */       {
/* 419 */         d.setLine(info.draw.x + xScale * i, info.draw.y, info.draw.x + xScale * i, info.draw.y + info.draw.height);
/* 420 */         graphics.draw(d);
/*     */       }
/*     */ 
/* 423 */       graphics.setStroke(new BasicStroke((float)Math.min(this.gridLineMaxWidth, Math.max(this.gridLineMinWidth, yScale * this.gridLineFraction))));
/* 424 */       for (int i = this.gridModulus; i < maxY; i += skipY)
/*     */       {
/* 426 */         d.setLine(info.draw.x, info.draw.y + yScale * i, info.draw.x + info.draw.width, info.draw.y + yScale * i);
/* 427 */         graphics.draw(d);
/*     */       }
/* 429 */       graphics.setStroke(oldStroke);
/* 430 */       graphics.setPaint(oldPaint);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.grid.ValueGridPortrayal2D
 * JD-Core Version:    0.6.2
 */