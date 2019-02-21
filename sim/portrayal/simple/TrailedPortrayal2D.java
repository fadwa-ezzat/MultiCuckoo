/*     */ package sim.portrayal.simple;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import java.awt.geom.RectangularShape;
/*     */ import java.util.LinkedList;
/*     */ import java.util.ListIterator;
/*     */ import sim.display.GUIState;
/*     */ import sim.display.Manipulating2D;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.field.grid.Grid2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.FieldPortrayal2D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.portrayal.network.EdgeDrawInfo2D;
/*     */ import sim.portrayal.network.SimpleEdgePortrayal2D;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.gui.SimpleColorMap;
/*     */ 
/*     */ public class TrailedPortrayal2D extends SimplePortrayal2D
/*     */ {
/* 109 */   boolean isSelected = false;
/*     */ 
/* 111 */   boolean onlyGrowTrailWhenSelected = false;
/*     */ 
/* 125 */   boolean onlyShowTrailWhenSelected = true;
/*     */ 
/* 132 */   LinkedList places = new LinkedList();
/*     */   SimpleColorMap defaultMap;
/*     */   public SimplePortrayal2D child;
/*     */   public SimplePortrayal2D trail;
/*     */   double length;
/*     */   GUIState state;
/*     */   FieldPortrayal2D fieldPortrayal;
/*     */   public static final double DEFAULT_MAXIMUM_JUMP = 0.75D;
/* 173 */   public double maximumJump = 0.75D;
/*     */ 
/* 186 */   public static final Color DEFAULT_MIN_COLOR = new Color(128, 128, 128, 255);
/*     */ 
/* 188 */   public static final Color DEFAULT_MAX_COLOR = new Color(128, 128, 128, 0);
/*     */ 
/* 243 */   static final Object NO_OBJ = new Object();
/* 244 */   static final Object NO_OBJ2 = new Object();
/*     */   Object lastObj;
/*     */   Object selectedObj;
/* 248 */   boolean locked = false;
/*     */ 
/*     */   public void setOnlyGrowTrailWhenSelected(boolean val)
/*     */   {
/* 115 */     this.onlyGrowTrailWhenSelected = val; } 
/* 117 */   /** @deprecated */
/*     */   public void setGrowTrailOnlyWhenSelected(boolean val) { this.onlyGrowTrailWhenSelected = val; }
/*     */ 
/*     */   public boolean getOnlyGrowTrailWhenSelected()
/*     */   {
/* 121 */     return this.onlyGrowTrailWhenSelected; } 
/* 123 */   /** @deprecated */
/*     */   public boolean getGrowTrailOnlyWhenSelected() { return this.onlyGrowTrailWhenSelected; }
/*     */ 
/*     */   public void setOnlyShowTrailWhenSelected(boolean val)
/*     */   {
/* 127 */     this.onlyShowTrailWhenSelected = val;
/*     */   }
/* 129 */   public boolean getOnlyShowTrailWhenSelected() { return this.onlyShowTrailWhenSelected; }
/*     */ 
/*     */ 
/*     */   public void setLength(double val)
/*     */   {
/* 162 */     if (val >= 0.0D) this.length = val; 
/*     */   }
/* 164 */   public double getLength() { return this.length; }
/*     */ 
/*     */ 
/*     */   public void setMaximumJump(double val)
/*     */   {
/* 178 */     if ((val >= 0.0D) && (val <= 1.0D)) this.maximumJump = val;
/*     */   }
/*     */ 
/*     */   public double getMaximumJump()
/*     */   {
/* 183 */     return this.maximumJump;
/*     */   }
/*     */ 
/*     */   public TrailedPortrayal2D(GUIState state, SimplePortrayal2D child, FieldPortrayal2D fieldPortrayal, SimplePortrayal2D trail, double length)
/*     */   {
/* 193 */     this.state = state;
/* 194 */     this.child = child;
/* 195 */     this.trail = trail;
/* 196 */     this.length = length;
/* 197 */     this.fieldPortrayal = fieldPortrayal;
/* 198 */     this.defaultMap = new SimpleColorMap(0.0D, 1.0D, DEFAULT_MIN_COLOR, DEFAULT_MAX_COLOR);
/*     */   }
/*     */ 
/*     */   public TrailedPortrayal2D(GUIState state, SimplePortrayal2D child, FieldPortrayal2D fieldPortrayal, double length, Color minColor, Color maxColor)
/*     */   {
/* 205 */     this.state = state;
/* 206 */     this.child = child;
/* 207 */     this.trail = new DefaultTrail();
/* 208 */     this.length = length;
/* 209 */     this.fieldPortrayal = fieldPortrayal;
/* 210 */     this.defaultMap = new SimpleColorMap(0.0D, 1.0D, minColor, maxColor);
/*     */   }
/*     */ 
/*     */   public TrailedPortrayal2D(GUIState state, SimplePortrayal2D child, FieldPortrayal2D fieldPortrayal, double length)
/*     */   {
/* 216 */     this(state, child, fieldPortrayal, length, DEFAULT_MIN_COLOR, DEFAULT_MAX_COLOR);
/*     */   }
/*     */ 
/*     */   SimplePortrayal2D getChild(Object object)
/*     */   {
/* 222 */     if (this.child != null) return this.child;
/*     */ 
/* 225 */     if (!(object instanceof SimplePortrayal2D))
/* 226 */       throw new RuntimeException("Object provided to TransformedPortrayal2D is not a SimplePortrayal2D: " + object);
/* 227 */     return (SimplePortrayal2D)object;
/*     */   }
/*     */ 
/*     */   double valueForTimestep(double timestamp, double currentTime)
/*     */   {
/* 234 */     if (this.length == 0.0D) return 0.0D;
/* 235 */     double val = (currentTime - timestamp) / this.length;
/* 236 */     if (val < 0.0D) return 0.0D;
/* 237 */     if (val > 1.0D) return 1.0D;
/* 238 */     return val;
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/* 254 */     if (info.fieldPortrayal != this.fieldPortrayal)
/*     */     {
/* 256 */       if ((info.selected) && (!this.locked))
/*     */       {
/* 258 */         this.selectedObj = object;
/* 259 */         if (this.selectedObj == this.lastObj)
/* 260 */           this.locked = true;
/*     */       }
/* 262 */       else if (this.selectedObj == object) {
/* 263 */         this.selectedObj = NO_OBJ2;
/*     */       }
/* 265 */       getChild(object).draw(object, graphics, info);
/* 266 */       return;
/*     */     }
/*     */ 
/* 270 */     Object selectedObj = this.selectedObj;
/* 271 */     Object lastObj = this.lastObj;
/* 272 */     boolean onlyShowTrailWhenSelected = this.onlyShowTrailWhenSelected;
/* 273 */     boolean onlyGrowTrailWhenSelected = this.onlyGrowTrailWhenSelected;
/*     */ 
/* 277 */     this.locked = false;
/*     */ 
/* 280 */     if ((object == selectedObj) || (!onlyShowTrailWhenSelected))
/*     */     {
/* 282 */       if ((object != lastObj) && (onlyGrowTrailWhenSelected))
/*     */       {
/* 284 */         this.places.clear();
/* 285 */         this.lastObj = object;
/*     */       }
/*     */     }
/*     */ 
/* 289 */     Object currentObjectLocation = NO_OBJ;
/*     */ 
/* 292 */     if ((object == selectedObj) || (!onlyGrowTrailWhenSelected))
/*     */     {
/* 294 */       double currentTime = this.state.state.schedule.getTime();
/*     */ 
/* 297 */       ListIterator iterator = this.places.listIterator();
/* 298 */       while (iterator.hasNext())
/*     */       {
/* 300 */         Place p = (Place)iterator.next();
/*     */ 
/* 303 */         if (p.timestamp > currentTime - this.length)
/*     */           break;
/* 305 */         iterator.remove();
/*     */       }
/*     */ 
/* 311 */       int size = this.places.size();
/* 312 */       currentObjectLocation = this.fieldPortrayal.getObjectLocation(object, info.gui);
/*     */ 
/* 314 */       if ((size == 0) && (currentTime > -1.0D) && (currentTime < (1.0D / 0.0D)))
/*     */       {
/* 316 */         this.places.add(new Place(currentObjectLocation, currentTime));
/*     */       }
/* 318 */       else if (size > 0)
/*     */       {
/* 320 */         Place lastPlace = (Place)this.places.getLast();
/* 321 */         if (lastPlace.timestamp < currentTime)
/*     */         {
/* 323 */           this.places.add(new Place(currentObjectLocation, currentTime));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 330 */     if ((object == selectedObj) || (!onlyShowTrailWhenSelected))
/*     */     {
/* 332 */       if (currentObjectLocation == NO_OBJ) {
/* 333 */         currentObjectLocation = this.fieldPortrayal.getObjectLocation(object, info.gui);
/*     */       }
/* 335 */       double currentTime = this.state.state.schedule.getTime();
/* 336 */       ListIterator iterator = this.places.listIterator();
/* 337 */       Place lastPlace = null;
/* 338 */       Point2D.Double lastPosition = null;
/* 339 */       TrailDrawInfo2D temp = new TrailDrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(info.draw.x, info.draw.y, info.draw.width, info.draw.height), info.clip, null);
/*     */ 
/* 341 */       while (iterator.hasNext())
/*     */       {
/* 343 */         Place p = (Place)iterator.next();
/*     */ 
/* 347 */         Point2D.Double position = this.fieldPortrayal.getRelativeObjectPosition(p.location, currentObjectLocation, info);
/*     */ 
/* 350 */         if (lastPosition != null)
/*     */         {
/* 353 */           boolean jump = false;
/* 354 */           Object field = this.fieldPortrayal.getField();
/* 355 */           double width = 0.0D;
/* 356 */           double height = 0.0D;
/* 357 */           if ((field instanceof Grid2D))
/*     */           {
/* 359 */             Grid2D grid = (Grid2D)field;
/* 360 */             width = grid.getWidth();
/* 361 */             height = grid.getHeight();
/* 362 */             Int2D loc1 = (Int2D)p.location;
/* 363 */             Int2D loc2 = (Int2D)lastPlace.location;
/* 364 */             jump = (Math.abs(loc1.x - loc2.x) > width * this.maximumJump) || (Math.abs(loc1.y - loc2.y) > height * this.maximumJump);
/*     */           }
/* 367 */           else if ((field instanceof Continuous2D))
/*     */           {
/* 369 */             Continuous2D grid = (Continuous2D)field;
/* 370 */             width = grid.getWidth();
/* 371 */             height = grid.getHeight();
/* 372 */             Double2D loc1 = (Double2D)p.location;
/* 373 */             Double2D loc2 = (Double2D)lastPlace.location;
/* 374 */             jump = (Math.abs(loc1.x - loc2.x) > width * this.maximumJump) || (Math.abs(loc1.y - loc2.y) > height * this.maximumJump);
/*     */           }
/*     */ 
/* 379 */           if (!jump)
/*     */           {
/* 381 */             temp.value = valueForTimestep(p.timestamp, currentTime);
/* 382 */             temp.draw.x = position.x;
/* 383 */             temp.draw.y = position.y;
/* 384 */             temp.secondPoint = lastPosition;
/* 385 */             this.trail.draw(object, graphics, temp);
/*     */           }
/*     */         }
/*     */ 
/* 389 */         lastPlace = p;
/* 390 */         lastPosition = position;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean hitObject(Object object, DrawInfo2D range)
/*     */   {
/* 398 */     return getChild(object).hitObject(object, range);
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/* 404 */     Object object = wrapper.getObject();
/* 405 */     boolean returnval = getChild(object).setSelected(wrapper, selected);
/*     */ 
/* 407 */     return returnval;
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/* 413 */     Object object = wrapper.getObject();
/* 414 */     if (wrapper.getFieldPortrayal() != this.fieldPortrayal)
/* 415 */       return getChild(object).getInspector(wrapper, state);
/* 416 */     return null;
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/* 422 */     Object object = wrapper.getObject();
/* 423 */     if (wrapper.getFieldPortrayal() != this.fieldPortrayal)
/* 424 */       return getChild(object).getName(wrapper);
/* 425 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean handleMouseEvent(GUIState guistate, Manipulating2D manipulating, LocationWrapper wrapper, MouseEvent event, DrawInfo2D fieldPortrayalDrawInfo, int type)
/*     */   {
/* 431 */     return getChild(wrapper.getObject()).handleMouseEvent(guistate, manipulating, wrapper, event, fieldPortrayalDrawInfo, type);
/*     */   }
/*     */ 
/*     */   class DefaultTrail extends SimpleEdgePortrayal2D
/*     */   {
/* 140 */     BasicStroke stroke = new BasicStroke(0.5F, 1, 1);
/*     */ 
/*     */     DefaultTrail() {  } 
/* 143 */     public void draw(Object object, Graphics2D graphics, DrawInfo2D info) { info.precise = true;
/* 144 */       float linewidth = this.stroke.getLineWidth();
/* 145 */       if (linewidth * 2.0F != info.draw.width)
/* 146 */         this.stroke = new BasicStroke((float)(info.draw.width / 2.0D), 1, 1);
/* 147 */       graphics.setStroke(this.stroke);
/* 148 */       TrailedPortrayal2D.TrailDrawInfo2D t = (TrailedPortrayal2D.TrailDrawInfo2D)info;
/* 149 */       this.toPaint = (this.fromPaint = TrailedPortrayal2D.this.defaultMap.getColor(t.value));
/* 150 */       super.draw(object, graphics, info);
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Place
/*     */   {
/*     */     Object location;
/*     */     double timestamp;
/*     */ 
/*     */     Place(Object location, double timestamp)
/*     */     {
/* 104 */       this.location = location; this.timestamp = timestamp;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class TrailDrawInfo2D extends EdgeDrawInfo2D
/*     */   {
/*  90 */     public double value = 0.0D;
/*     */ 
/*  91 */     public TrailDrawInfo2D(GUIState gui, FieldPortrayal2D fieldPortrayal, RectangularShape draw, RectangularShape clip, Point2D.Double secondPoint) { super(fieldPortrayal, draw, clip, secondPoint); } 
/*  92 */     public TrailDrawInfo2D(DrawInfo2D other, double translateX, double translateY, Point2D.Double secondPoint) { super(translateX, translateY, secondPoint); } 
/*     */     public TrailDrawInfo2D(DrawInfo2D other, Point2D.Double secondPoint) {
/*  94 */       super(secondPoint); } 
/*  95 */     public TrailDrawInfo2D(EdgeDrawInfo2D other) { super(); }
/*     */ 
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.simple.TrailedPortrayal2D
 * JD-Core Version:    0.6.2
 */