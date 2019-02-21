/*     */ package sim.portrayal.simple;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.Ellipse2D.Double;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.display.GUIState;
/*     */ import sim.display.Manipulating2D;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.FieldPortrayal2D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.Orientable2D;
/*     */ import sim.portrayal.Oriented2D;
/*     */ import sim.portrayal.Scalable2D;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ 
/*     */ public class AdjustablePortrayal2D extends SimplePortrayal2D
/*     */ {
/*     */   public static final double CIRCLE_RADIUS = 30.0D;
/*     */   public static final double KNOB_RADIUS = 5.0D;
/*     */   public static final double SLOP = 5.0D;
/*  43 */   public static final Paint LOWER_PAINT = new Color(255, 255, 255, 200);
/*  44 */   public static final Stroke LOWER_STROKE = new BasicStroke(3.0F);
/*  45 */   public static final Paint UPPER_PAINT = new Color(0, 0, 0, 255);
/*  46 */   public static final Stroke UPPER_STROKE = new BasicStroke(1.0F);
/*  47 */   public static final Ellipse2D circle = new Ellipse2D.Double();
/*  48 */   public static final Ellipse2D knob = new Ellipse2D.Double();
/*     */   public SimplePortrayal2D child;
/*  96 */   boolean adjusting = false;
/*  97 */   Object adjustingObject = null;
/*  98 */   double adjustingInitialScale = 1.0D;
/*  99 */   Point2D.Double adjustingInitialPosition = null;
/*     */ 
/*     */   public AdjustablePortrayal2D(SimplePortrayal2D child)
/*     */   {
/*  54 */     this.child = child;
/*     */   }
/*     */ 
/*     */   public SimplePortrayal2D getChild(Object object)
/*     */   {
/*  59 */     if (this.child != null) return this.child;
/*     */ 
/*  62 */     if (!(object instanceof SimplePortrayal2D))
/*  63 */       throw new RuntimeException("Object provided to AdjustablePortrayal2D is not a SimplePortrayal2D: " + object);
/*  64 */     return (SimplePortrayal2D)object;
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/*  70 */     SimplePortrayal2D child = getChild(object);
/*  71 */     child.draw(object, graphics, info);
/*     */ 
/*  73 */     if ((info.selected) && (object != null) && (((object instanceof Oriented2D)) || ((object instanceof Scalable2D))))
/*     */     {
/*  75 */       double orientation = 0.0D;
/*     */ 
/*  77 */       if ((object instanceof Oriented2D)) {
/*  78 */         orientation = ((Oriented2D)object).orientation2D();
/*     */       }
/*     */ 
/*  81 */       circle.setFrame(info.draw.x - 30.0D, info.draw.y - 30.0D, 60.0D, 60.0D);
/*  82 */       knob.setFrame(info.draw.x + 30.0D * Math.cos(orientation) - 5.0D, info.draw.y + 30.0D * Math.sin(orientation) - 5.0D, 10.0D, 10.0D);
/*  83 */       graphics.setPaint(LOWER_PAINT);
/*  84 */       graphics.setStroke(LOWER_STROKE);
/*  85 */       graphics.draw(circle);
/*  86 */       graphics.draw(knob);
/*  87 */       graphics.setPaint(UPPER_PAINT);
/*  88 */       graphics.setStroke(UPPER_STROKE);
/*  89 */       graphics.draw(circle);
/*  90 */       graphics.fill(knob);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean handleMouseEvent(GUIState guistate, Manipulating2D manipulating, LocationWrapper wrapper, MouseEvent event, DrawInfo2D range, int type)
/*     */   {
/* 103 */     synchronized (guistate.state.schedule)
/*     */     {
/* 105 */       Point2D.Double myPosition = ((FieldPortrayal2D)wrapper.getFieldPortrayal()).getObjectPosition(wrapper.getObject(), range);
/* 106 */       Object object = wrapper.getObject();
/* 107 */       if ((myPosition != null) && (object != null) && ((!this.adjusting) || (this.adjustingObject == object)) && (((object instanceof Scalable2D)) || ((object instanceof Orientable2D))))
/*     */       {
/* 118 */         int id = event.getID();
/* 119 */         if (id == 501)
/*     */         {
/* 121 */           if ((this.adjusting) && (object == this.adjustingObject))
/*     */           {
/* 123 */             this.adjusting = false;
/* 124 */             this.adjustingObject = null;
/*     */           }
/*     */ 
/* 128 */           double dx = event.getX() - myPosition.getX();
/* 129 */           double dy = event.getY() - myPosition.getY();
/* 130 */           double distance = Math.sqrt(dx * dx + dy * dy);
/* 131 */           if ((object instanceof Scalable2D))
/* 132 */             this.adjustingInitialScale = ((Scalable2D)object).getScale2D();
/* 133 */           if (Math.abs(distance - 30.0D) <= 5.0D)
/*     */           {
/* 135 */             this.adjusting = true;
/* 136 */             this.adjustingObject = object;
/* 137 */             this.adjustingInitialPosition = myPosition;
/*     */ 
/* 139 */             return true;
/*     */           }
/*     */         } else {
/* 142 */           if ((id == 506) && (this.adjusting))
/*     */           {
/* 144 */             double dx = event.getX() - this.adjustingInitialPosition.getX();
/* 145 */             double dy = event.getY() - this.adjustingInitialPosition.getY();
/* 146 */             double newOrientation = Math.atan2(dy, dx);
/* 147 */             double newScaleMultiplier = Math.sqrt(dx * dx + dy * dy) / 30.0D;
/* 148 */             if ((object instanceof Orientable2D))
/* 149 */               ((Orientable2D)object).setOrientation2D(newOrientation);
/* 150 */             if ((object instanceof Scalable2D)) {
/* 151 */               ((Scalable2D)object).setScale2D(this.adjustingInitialScale * newScaleMultiplier);
/*     */             }
/* 153 */             return true;
/*     */           }
/* 155 */           if ((id == 502) && (this.adjusting))
/*     */           {
/* 157 */             this.adjusting = false;
/* 158 */             this.adjustingObject = null;
/* 159 */             this.adjustingInitialScale = 1.0D;
/* 160 */             this.adjustingInitialPosition = null;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 164 */     return getChild(wrapper.getObject()).handleMouseEvent(guistate, manipulating, wrapper, event, range, type);
/*     */   }
/*     */ 
/*     */   public boolean hitObject(Object object, DrawInfo2D range)
/*     */   {
/* 169 */     return getChild(object).hitObject(object, range);
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/* 174 */     Object object = wrapper.getObject();
/* 175 */     if ((!selected) && (this.adjusting) && (object == this.adjustingObject))
/* 176 */       this.adjusting = false;
/* 177 */     return getChild(wrapper.getObject()).setSelected(wrapper, selected);
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/* 182 */     return getChild(wrapper.getObject()).getInspector(wrapper, state);
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/* 187 */     return getChild(wrapper.getObject()).getName(wrapper);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.simple.AdjustablePortrayal2D
 * JD-Core Version:    0.6.2
 */