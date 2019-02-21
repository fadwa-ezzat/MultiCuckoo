/*     */ package sim.portrayal.continuous;
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
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.FieldPortrayal;
/*     */ import sim.portrayal.FieldPortrayal2D;
/*     */ import sim.portrayal.Fixed2D;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.portrayal.inspector.StableDouble2D;
/*     */ import sim.portrayal.simple.OvalPortrayal2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class ContinuousPortrayal2D extends FieldPortrayal2D
/*     */ {
/*  30 */   SimplePortrayal2D defaultPortrayal = new OvalPortrayal2D();
/*     */ 
/* 134 */   static final int[] toroidalX = { 0, 1, -1, 0, 1, -1, 0, 1, -1 };
/* 135 */   static final int[] toroidalY = { 0, 0, 0, 1, 1, 1, -1, -1, -1 };
/*     */ 
/* 137 */   boolean displayingToroidally = false;
/*     */ 
/* 250 */   HashMap selectedWrappers = new HashMap();
/*     */ 
/* 275 */   double axesLineFraction = 0.125D;
/* 276 */   Color axesColor = Color.blue;
/* 277 */   double axesLineMinWidth = 1.0D;
/* 278 */   double axesLineMaxWidth = (1.0D / 0.0D);
/* 279 */   boolean axes = false;
/*     */ 
/* 312 */   double borderLineFraction = 0.125D;
/* 313 */   Color borderColor = Color.red;
/* 314 */   double borderLineMinWidth = 1.0D;
/* 315 */   double borderLineMaxWidth = (1.0D / 0.0D);
/* 316 */   boolean border = false;
/*     */ 
/*     */   public void setField(Object field)
/*     */   {
/*  34 */     if ((field instanceof Continuous2D)) super.setField(field); else
/*  35 */       throw new RuntimeException("Invalid field for ContinuousPortrayal2D: " + field);
/*     */   }
/*     */ 
/*     */   public Portrayal getDefaultPortrayal()
/*     */   {
/*  40 */     return this.defaultPortrayal;
/*     */   }
/*     */ 
/*     */   public Point2D.Double getRelativeObjectPosition(Object location, Object otherObjectLocation, DrawInfo2D otherObjectInfo)
/*     */   {
/*  45 */     Continuous2D field = (Continuous2D)this.field;
/*  46 */     if (field == null) return null;
/*     */ 
/*  48 */     Double2D loc = (Double2D)location;
/*  49 */     Double2D oloc = (Double2D)otherObjectLocation;
/*  50 */     double dx = loc.x - oloc.x;
/*  51 */     double dy = loc.y - oloc.y;
/*  52 */     double xScale = otherObjectInfo.draw.width;
/*  53 */     double yScale = otherObjectInfo.draw.height;
/*  54 */     return new Point2D.Double(dx * xScale + otherObjectInfo.draw.x, dy * yScale + otherObjectInfo.draw.y);
/*     */   }
/*     */ 
/*     */   public Double2D getScale(DrawInfo2D info)
/*     */   {
/*  59 */     synchronized (info.gui.state.schedule)
/*     */     {
/*  61 */       Continuous2D field = (Continuous2D)this.field;
/*  62 */       if (field == null) return null;
/*     */ 
/*  64 */       double xScale = info.draw.width / field.width;
/*  65 */       double yScale = info.draw.height / field.height;
/*  66 */       return new Double2D(xScale, yScale);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getPositionLocation(Point2D.Double position, DrawInfo2D fieldPortrayalInfo)
/*     */   {
/*  72 */     Double2D scale = getScale(fieldPortrayalInfo);
/*  73 */     double xScale = scale.x;
/*  74 */     double yScale = scale.y;
/*     */ 
/*  76 */     double x = (position.getX() - fieldPortrayalInfo.draw.x) / xScale;
/*  77 */     double y = (position.getY() - fieldPortrayalInfo.draw.y) / yScale;
/*  78 */     return new Double2D(x, y);
/*     */   }
/*     */ 
/*     */   public void setObjectPosition(Object object, Point2D.Double position, DrawInfo2D fieldPortrayalInfo)
/*     */   {
/*  83 */     synchronized (fieldPortrayalInfo.gui.state.schedule)
/*     */     {
/*  85 */       Continuous2D field = (Continuous2D)this.field;
/*  86 */       if (field == null) return;
/*  87 */       if (field.getObjectLocation(object) == null) return;
/*  88 */       Double2D location = (Double2D)getPositionLocation(position, fieldPortrayalInfo);
/*  89 */       if (location != null)
/*     */       {
/*  91 */         if (((object instanceof Fixed2D)) && (!((Fixed2D)object).maySetLocation(field, location))) {
/*  92 */           return;
/*     */         }
/*     */ 
/*  95 */         if (location != null)
/*  96 */           field.setObjectLocation(object, location);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getObjectLocation(Object object, GUIState gui)
/*     */   {
/* 103 */     synchronized (gui.state.schedule)
/*     */     {
/* 105 */       Continuous2D field = (Continuous2D)this.field;
/* 106 */       if (field == null) return null;
/* 107 */       return field.getObjectLocation(object);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Point2D.Double getLocationPosition(Object location, DrawInfo2D fieldPortrayalInfo)
/*     */   {
/* 113 */     synchronized (fieldPortrayalInfo.gui.state.schedule)
/*     */     {
/* 115 */       Continuous2D field = (Continuous2D)this.field;
/* 116 */       if (field == null) return null;
/*     */ 
/* 118 */       double xScale = fieldPortrayalInfo.draw.width / field.width;
/* 119 */       double yScale = fieldPortrayalInfo.draw.height / field.height;
/* 120 */       DrawInfo2D newinfo = new DrawInfo2D(fieldPortrayalInfo.gui, fieldPortrayalInfo.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, xScale, yScale), fieldPortrayalInfo.clip);
/* 121 */       newinfo.precise = fieldPortrayalInfo.precise;
/*     */ 
/* 123 */       Double2D loc = (Double2D)location;
/* 124 */       if (loc == null) return null;
/*     */ 
/* 126 */       fieldPortrayalInfo.draw.x += xScale * loc.x;
/* 127 */       fieldPortrayalInfo.draw.y += yScale * loc.y;
/*     */ 
/* 129 */       return new Point2D.Double(newinfo.draw.x, newinfo.draw.y);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setDisplayingToroidally(boolean val)
/*     */   {
/* 142 */     this.displayingToroidally = val;
/*     */   }
/*     */ 
/*     */   public boolean isDisplayingToroidally() {
/* 146 */     return this.displayingToroidally;
/*     */   }
/*     */ 
/*     */   protected void hitOrDraw(Graphics2D graphics, DrawInfo2D info, Bag putInHere) {
/* 150 */     Continuous2D field = (Continuous2D)this.field;
/* 151 */     if (field == null) return;
/*     */ 
/* 153 */     boolean objectSelected = !this.selectedWrappers.isEmpty();
/*     */ 
/* 155 */     double xScale = info.draw.width / field.width;
/* 156 */     double yScale = info.draw.height / field.height;
/* 157 */     int startx = (int)Math.floor((info.clip.x - info.draw.x) / xScale);
/* 158 */     int starty = (int)Math.floor((info.clip.y - info.draw.y) / yScale);
/* 159 */     int endx = (int)Math.floor((info.clip.x - info.draw.x + info.clip.width) / xScale) + 1;
/* 160 */     int endy = (int)Math.floor((info.clip.y - info.draw.y + info.clip.height) / yScale) + 1;
/*     */ 
/* 162 */     DrawInfo2D newinfo = new DrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, xScale, yScale), info.clip);
/* 163 */     newinfo.precise = info.precise;
/* 164 */     newinfo.fieldPortrayal = this;
/*     */ 
/* 169 */     Bag objects = field.getAllObjects();
/* 170 */     double discretizationOverlap = field.discretization;
/* 171 */     for (int x = 0; x < objects.numObjs; x++)
/*     */     {
/* 173 */       Object object = objects.objs[x];
/* 174 */       Double2D objectLoc = field.getObjectLocation(object);
/*     */ 
/* 176 */       if (this.displayingToroidally) {
/* 177 */         objectLoc = new Double2D(field.tx(objectLoc.x), field.tx(objectLoc.y));
/*     */       }
/* 179 */       for (int i = 0; i < toroidalX.length; i++)
/*     */       {
/* 181 */         Double2D loc = null;
/* 182 */         if (i == 0) {
/* 183 */           loc = objectLoc; } else {
/* 184 */           if (!this.displayingToroidally) break;
/* 185 */           loc = new Double2D(objectLoc.x + field.width * toroidalX[i], objectLoc.y + field.height * toroidalY[i]);
/*     */         }
/*     */ 
/* 194 */         if ((loc.x >= startx - discretizationOverlap) && (loc.x < endx + discretizationOverlap) && (loc.y >= starty - discretizationOverlap) && (loc.y < endy + discretizationOverlap))
/*     */         {
/* 197 */           Portrayal p = getPortrayalForObject(object);
/* 198 */           if (!(p instanceof SimplePortrayal2D)) {
/* 199 */             throw new RuntimeException("Unexpected Portrayal " + p + " for object " + objects.objs[x] + " -- expected a SimplePortrayal2D");
/*     */           }
/* 201 */           SimplePortrayal2D portrayal = (SimplePortrayal2D)p;
/*     */ 
/* 203 */           info.draw.x += xScale * loc.x;
/* 204 */           info.draw.y += yScale * loc.y;
/*     */ 
/* 206 */           newinfo.location = loc;
/*     */ 
/* 208 */           Object portrayedObject = object;
/* 209 */           if (graphics == null)
/*     */           {
/* 211 */             if (portrayal.hitObject(portrayedObject, newinfo)) {
/* 212 */               putInHere.add(getWrapper(portrayedObject));
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 218 */             newinfo.selected = ((objectSelected) && (this.selectedWrappers.get(portrayedObject) != null));
/*     */ 
/* 220 */             portrayal.draw(portrayedObject, graphics, newinfo);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 226 */     drawAxes(graphics, xScale, yScale, info);
/* 227 */     drawBorder(graphics, xScale, info);
/*     */   }
/*     */ 
/*     */   public LocationWrapper getWrapper(Object obj)
/*     */   {
/* 233 */     Continuous2D field = (Continuous2D)this.field;
/* 234 */     final StableDouble2D w = new StableDouble2D(field, obj);
/* 235 */     return new LocationWrapper(obj, null, this)
/*     */     {
/*     */       public Object getLocation()
/*     */       {
/* 239 */         return w;
/*     */       }
/*     */ 
/*     */       public String getLocationName()
/*     */       {
/* 244 */         return w.toString();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/* 253 */     if (wrapper == null) return true;
/* 254 */     if (wrapper.getFieldPortrayal() != this) return true;
/*     */ 
/* 256 */     Object obj = wrapper.getObject();
/* 257 */     boolean b = getPortrayalForObject(obj).setSelected(wrapper, selected);
/* 258 */     if (selected)
/*     */     {
/* 260 */       if (!b) return false;
/* 261 */       this.selectedWrappers.put(obj, wrapper);
/*     */     }
/*     */     else
/*     */     {
/* 265 */       this.selectedWrappers.remove(obj);
/*     */     }
/* 267 */     return true;
/*     */   }
/*     */ 
/*     */   public void setAxes(boolean on)
/*     */   {
/* 282 */     this.axes = on;
/*     */   }
/*     */ 
/*     */   public void setAxesColor(Color val)
/*     */   {
/* 287 */     if (val == null) throw new RuntimeException("color must be non-null");
/* 288 */     this.axesColor = val;
/*     */   }
/*     */ 
/*     */   public void setAxesLineFraction(double val)
/*     */   {
/* 295 */     if (val <= 0.0D) throw new RuntimeException("axesLineFraction must be between 0 and 1");
/* 296 */     this.axesLineFraction = val;
/*     */   }
/*     */ 
/*     */   public void setAxesLineMinMaxWidth(double min, double max)
/*     */   {
/* 303 */     if (min <= 0.0D) throw new RuntimeException("minimum width must be between >= 0");
/* 304 */     if (min > max) throw new RuntimeException("maximum width must be >= minimum width");
/* 305 */     this.axesLineMinWidth = min;
/* 306 */     this.axesLineMaxWidth = max;
/*     */   }
/*     */ 
/*     */   public void setBorder(boolean on)
/*     */   {
/* 319 */     this.border = on;
/*     */   }
/*     */ 
/*     */   public void setBorderColor(Color val)
/*     */   {
/* 324 */     if (val == null) throw new RuntimeException("color must be non-null");
/* 325 */     this.borderColor = val;
/*     */   }
/*     */ 
/*     */   public void setBorderLineFraction(double val)
/*     */   {
/* 335 */     if (val <= 0.0D) throw new RuntimeException("borderLineFraction must be between 0 and 1");
/* 336 */     this.borderLineFraction = val;
/*     */   }
/*     */ 
/*     */   public void setBorderLineMinMaxWidth(double min, double max)
/*     */   {
/* 343 */     if (min <= 0.0D) throw new RuntimeException("minimum width must be between >= 0");
/* 344 */     if (min > max) throw new RuntimeException("maximum width must be >= minimum width");
/* 345 */     this.borderLineMinWidth = min;
/* 346 */     this.borderLineMaxWidth = max;
/*     */   }
/*     */ 
/*     */   void drawBorder(Graphics2D graphics, double xScale, DrawInfo2D info)
/*     */   {
/* 354 */     if ((this.border) && (graphics != null))
/*     */     {
/* 356 */       Stroke oldStroke = graphics.getStroke();
/* 357 */       Paint oldPaint = graphics.getPaint();
/* 358 */       Rectangle2D.Double d = new Rectangle2D.Double();
/* 359 */       graphics.setColor(this.borderColor);
/* 360 */       graphics.setStroke(new BasicStroke((float)Math.min(this.borderLineMaxWidth, Math.max(this.borderLineMinWidth, xScale * this.borderLineFraction))));
/* 361 */       d.setRect(info.draw.x, info.draw.y, info.draw.width, info.draw.height);
/* 362 */       graphics.draw(d);
/* 363 */       graphics.setStroke(oldStroke);
/* 364 */       graphics.setPaint(oldPaint);
/*     */     }
/*     */   }
/*     */ 
/*     */   void drawAxes(Graphics2D graphics, double xScale, double yScale, DrawInfo2D info)
/*     */   {
/* 372 */     if ((this.axes) && (graphics != null))
/*     */     {
/* 374 */       Stroke oldStroke = graphics.getStroke();
/* 375 */       Paint oldPaint = graphics.getPaint();
/* 376 */       Line2D.Double d = new Line2D.Double();
/* 377 */       graphics.setColor(this.axesColor);
/* 378 */       graphics.setStroke(new BasicStroke((float)Math.min(this.axesLineMaxWidth, Math.max(this.axesLineMinWidth, xScale * this.axesLineFraction))));
/*     */ 
/* 385 */       graphics.draw(new Line2D.Double(info.clip.x, info.draw.y + info.draw.height / 2.0D, info.clip.x + info.clip.width, info.draw.y + info.draw.height / 2.0D));
/*     */ 
/* 388 */       graphics.setStroke(new BasicStroke((float)Math.min(this.axesLineMaxWidth, Math.max(this.axesLineMinWidth, yScale * this.axesLineFraction))));
/*     */ 
/* 390 */       graphics.draw(new Line2D.Double(info.draw.x + info.draw.width / 2.0D, info.clip.y, info.draw.x + info.draw.width / 2.0D, info.clip.y + info.clip.height));
/*     */ 
/* 392 */       graphics.setStroke(oldStroke);
/* 393 */       graphics.setPaint(oldPaint);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.continuous.ContinuousPortrayal2D
 * JD-Core Version:    0.6.2
 */