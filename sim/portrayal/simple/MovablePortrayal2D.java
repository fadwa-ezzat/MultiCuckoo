/*     */ package sim.portrayal.simple;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import sim.display.GUIState;
/*     */ import sim.display.Manipulating2D;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.FieldPortrayal2D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class MovablePortrayal2D extends SimplePortrayal2D
/*     */ {
/*     */   public SimplePortrayal2D child;
/*  62 */   boolean selectsWhenMoved = true;
/*     */ 
/*  69 */   Point2D originalMousePosition = null;
/*  70 */   Point2D originalObjectPosition = null;
/*     */ 
/*     */   public MovablePortrayal2D(SimplePortrayal2D child)
/*     */   {
/*  43 */     this.child = child;
/*     */   }
/*     */ 
/*     */   public SimplePortrayal2D getChild(Object object)
/*     */   {
/*  48 */     if (this.child != null) return this.child;
/*     */ 
/*  51 */     if (!(object instanceof SimplePortrayal2D))
/*  52 */       throw new RuntimeException("Object provided to MovablePortrayal2D is not a SimplePortrayal2D: " + object);
/*  53 */     return (SimplePortrayal2D)object;
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/*  59 */     getChild(object).draw(object, graphics, info);
/*     */   }
/*     */ 
/*     */   public boolean getSelectsWhenMoved()
/*     */   {
/*  65 */     return this.selectsWhenMoved;
/*     */   }
/*  67 */   public void setSelectsWhenMoved(boolean val) { this.selectsWhenMoved = val; }
/*     */ 
/*     */ 
/*     */   public boolean handleMouseEvent(GUIState guistate, Manipulating2D manipulating, LocationWrapper wrapper, MouseEvent event, DrawInfo2D range, int type)
/*     */   {
/*  74 */     synchronized (guistate.state.schedule)
/*     */     {
/*  76 */       int id = event.getID();
/*  77 */       Point2D.Double objPos = ((FieldPortrayal2D)wrapper.getFieldPortrayal()).getObjectPosition(wrapper.getObject(), range);
/*     */ 
/*  80 */       if ((id == 501) && (objPos != null) && (type == 1))
/*     */       {
/*  82 */         this.originalMousePosition = event.getPoint();
/*  83 */         this.originalObjectPosition = objPos;
/*     */ 
/*  86 */         DrawInfo2D hitRange = new DrawInfo2D(range);
/*  87 */         Double2D scale = ((FieldPortrayal2D)wrapper.getFieldPortrayal()).getScale(range);
/*     */ 
/*  91 */         hitRange.draw.x = this.originalObjectPosition.getX();
/*  92 */         hitRange.draw.y = this.originalObjectPosition.getY();
/*  93 */         hitRange.draw.width = scale.x;
/*  94 */         hitRange.draw.height = scale.y;
/*  95 */         hitRange.clip.x = this.originalMousePosition.getX();
/*  96 */         hitRange.clip.y = this.originalMousePosition.getY();
/*  97 */         hitRange.clip.width = 1.0D;
/*  98 */         hitRange.clip.height = 1.0D;
/*     */ 
/* 100 */         if (hitObject(wrapper.getObject(), hitRange))
/*     */         {
/* 102 */           manipulating.setMovingWrapper(wrapper);
/* 103 */           if (this.selectsWhenMoved)
/* 104 */             manipulating.performSelection(wrapper);
/* 105 */           return true;
/*     */         }
/* 107 */         this.originalMousePosition = (this.originalObjectPosition = null);
/*     */       }
/*     */       else {
/* 110 */         if ((id == 506) && (type == 0) && (this.originalObjectPosition != null))
/*     */         {
/* 112 */           Point2D currentMousePosition = event.getPoint();
/*     */ 
/* 115 */           Point2D.Double d = new Point2D.Double(this.originalObjectPosition.getX() + (currentMousePosition.getX() - this.originalMousePosition.getX()), this.originalObjectPosition.getY() + (currentMousePosition.getY() - this.originalMousePosition.getY()));
/*     */ 
/* 119 */           ((FieldPortrayal2D)wrapper.getFieldPortrayal()).setObjectPosition(wrapper.getObject(), d, range);
/* 120 */           return true;
/*     */         }
/* 122 */         if ((id == 502) && (type == 0))
/*     */         {
/* 124 */           this.originalMousePosition = null;
/* 125 */           this.originalObjectPosition = null;
/* 126 */           manipulating.setMovingWrapper(null);
/*     */         }
/*     */       }
/*     */     }
/* 129 */     return getChild(wrapper.getObject()).handleMouseEvent(guistate, manipulating, wrapper, event, range, type);
/*     */   }
/*     */ 
/*     */   public boolean hitObject(Object object, DrawInfo2D range)
/*     */   {
/* 134 */     return getChild(object).hitObject(object, range);
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/* 139 */     return getChild(wrapper.getObject()).setSelected(wrapper, selected);
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/* 144 */     return getChild(wrapper.getObject()).getInspector(wrapper, state);
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/* 149 */     return getChild(wrapper.getObject()).getName(wrapper);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.simple.MovablePortrayal2D
 * JD-Core Version:    0.6.2
 */