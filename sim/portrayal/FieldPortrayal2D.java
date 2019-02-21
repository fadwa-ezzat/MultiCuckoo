/*     */ package sim.portrayal;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.Int2D;
/*     */ 
/*     */ public abstract class FieldPortrayal2D extends FieldPortrayal
/*     */   implements Portrayal2D
/*     */ {
/* 106 */   SimplePortrayal2D simple = new SimplePortrayal2D();
/*     */   public static final int DEFAULT = 0;
/*     */   public static final int USE_BUFFER = 1;
/*     */   public static final int DONT_USE_BUFFER = 2;
/* 182 */   int buffering = 0;
/* 183 */   Object bufferingLock = new Object();
/*     */ 
/*     */   public Point2D.Double getRelativeObjectPosition(Object location, Object otherObjectLocation, DrawInfo2D otherObjectInfo)
/*     */   {
/*  51 */     double dx = 0.0D;
/*  52 */     double dy = 0.0D;
/*     */ 
/*  54 */     if ((location instanceof Int2D))
/*     */     {
/*  56 */       Int2D loc = (Int2D)location;
/*  57 */       Int2D oloc = (Int2D)otherObjectLocation;
/*  58 */       dx = loc.x - oloc.x;
/*  59 */       dy = loc.y - oloc.y;
/*     */     }
/*     */     else
/*     */     {
/*  63 */       Double2D loc = (Double2D)location;
/*  64 */       Double2D oloc = (Double2D)otherObjectLocation;
/*  65 */       dx = loc.x - oloc.x;
/*  66 */       dy = loc.y - oloc.y;
/*     */     }
/*  68 */     double xScale = otherObjectInfo.draw.width;
/*  69 */     double yScale = otherObjectInfo.draw.height;
/*  70 */     return new Point2D.Double(dx * xScale + otherObjectInfo.draw.x, dy * yScale + otherObjectInfo.draw.y);
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/*  84 */     hitOrDraw(graphics, info, null);
/*     */   }
/*     */ 
/*     */   public void hitObjects(DrawInfo2D range, Bag putInHere)
/*     */   {
/*  94 */     hitOrDraw(null, range, putInHere);
/*     */   }
/*     */ 
/*     */   protected void hitOrDraw(Graphics2D graphics, DrawInfo2D info, Bag putInHere)
/*     */   {
/*     */   }
/*     */ 
/*     */   public Portrayal getDefaultPortrayal()
/*     */   {
/* 109 */     return this.simple;
/*     */   }
/*     */ 
/*     */   public void setObjectPosition(Object object, Point2D.Double position, DrawInfo2D fieldPortrayalInfo)
/*     */   {
/*     */   }
/*     */ 
/*     */   public Double2D getScale(DrawInfo2D fieldPortrayalInfo)
/*     */   {
/* 124 */     throw new RuntimeException("getScale not implemented in " + getClass());
/*     */   }
/*     */ 
/*     */   public Object getPositionLocation(Point2D.Double position, DrawInfo2D fieldPortrayalInfo)
/*     */   {
/* 133 */     return null;
/*     */   }
/*     */ 
/*     */   public Object getObjectLocation(Object object, GUIState gui)
/*     */   {
/* 142 */     return null;
/*     */   }
/*     */ 
/*     */   public Point2D.Double getLocationPosition(Object location, DrawInfo2D fieldPortrayalInfo)
/*     */   {
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Object getClipLocation(DrawInfo2D info)
/*     */   {
/* 159 */     return getPositionLocation(new Point2D.Double(info.clip.x, info.clip.y), info);
/*     */   }
/*     */ 
/*     */   public Point2D.Double getObjectPosition(Object object, DrawInfo2D fieldPortrayalInfo)
/*     */   {
/* 167 */     synchronized (fieldPortrayalInfo.gui.state.schedule)
/*     */     {
/* 169 */       Object location = getObjectLocation(object, fieldPortrayalInfo.gui);
/* 170 */       if (location == null) return null;
/* 171 */       return getLocationPosition(location, fieldPortrayalInfo);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getBuffering()
/*     */   {
/* 194 */     synchronized (this.bufferingLock) { return this.buffering; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public void setBuffering(int val)
/*     */   {
/* 205 */     synchronized (this.bufferingLock) { this.buffering = val; }
/*     */ 
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.FieldPortrayal2D
 * JD-Core Version:    0.6.2
 */