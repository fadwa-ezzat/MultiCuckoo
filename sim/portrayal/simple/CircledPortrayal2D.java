/*     */ package sim.portrayal.simple;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.event.MouseEvent;
/*     */ import sim.display.GUIState;
/*     */ import sim.display.Manipulating2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ 
/*     */ public class CircledPortrayal2D extends OvalPortrayal2D
/*     */ {
/*     */   public static final double DEFAULT_SCALE = 2.0D;
/*     */   public static final double DEFAULT_OFFSET = 0.0D;
/*     */   public SimplePortrayal2D child;
/*  55 */   boolean showCircle = true;
/*     */   boolean onlyCircleWhenSelected;
/*     */ 
/*     */   public void setOnlyCircleWhenSelected(boolean val)
/*     */   {
/*  58 */     this.onlyCircleWhenSelected = val; } 
/*  59 */   public boolean getOnlyCircleWhenSelected() { return this.onlyCircleWhenSelected; } 
/*     */   public boolean isCircleShowing() {
/*  61 */     return this.showCircle; } 
/*  62 */   public void setCircleShowing(boolean val) { this.showCircle = val; }
/*     */ 
/*     */ 
/*     */   public CircledPortrayal2D(SimplePortrayal2D child, double offset, double scale, Paint paint, boolean onlyCircleWhenSelected)
/*     */   {
/*  69 */     super(paint, scale, false);
/*  70 */     this.offset = offset;
/*  71 */     this.child = child;
/*  72 */     this.paint = paint;
/*  73 */     this.onlyCircleWhenSelected = onlyCircleWhenSelected;
/*     */   }
/*     */ 
/*     */   public CircledPortrayal2D(SimplePortrayal2D child)
/*     */   {
/*  80 */     this(child, Color.blue, false);
/*     */   }
/*     */ 
/*     */   public CircledPortrayal2D(SimplePortrayal2D child, Paint paint, boolean onlyCircleWhenSelected)
/*     */   {
/*  87 */     this(child, 0.0D, 2.0D, paint, onlyCircleWhenSelected);
/*     */   }
/*     */ 
/*     */   public SimplePortrayal2D getChild(Object object)
/*     */   {
/*  92 */     if (this.child != null) return this.child;
/*     */ 
/*  95 */     if (!(object instanceof SimplePortrayal2D))
/*  96 */       throw new RuntimeException("Object provided to CircledPortrayal2D is not a SimplePortrayal2D: " + object);
/*  97 */     return (SimplePortrayal2D)object;
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/* 103 */     getChild(object).draw(object, graphics, info);
/* 104 */     if ((this.showCircle) && ((info.selected) || (!this.onlyCircleWhenSelected)))
/* 105 */       super.draw(object, graphics, info);
/*     */   }
/*     */ 
/*     */   public boolean hitObject(Object object, DrawInfo2D range)
/*     */   {
/* 110 */     return getChild(object).hitObject(object, range);
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/* 115 */     return getChild(wrapper.getObject()).setSelected(wrapper, selected);
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/* 120 */     return getChild(wrapper.getObject()).getInspector(wrapper, state);
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/* 125 */     return getChild(wrapper.getObject()).getName(wrapper);
/*     */   }
/*     */ 
/*     */   public boolean handleMouseEvent(GUIState guistate, Manipulating2D manipulating, LocationWrapper wrapper, MouseEvent event, DrawInfo2D fieldPortrayalDrawInfo, int type)
/*     */   {
/* 131 */     return getChild(wrapper.getObject()).handleMouseEvent(guistate, manipulating, wrapper, event, fieldPortrayalDrawInfo, type);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.simple.CircledPortrayal2D
 * JD-Core Version:    0.6.2
 */