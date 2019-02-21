/*     */ package sim.portrayal3d.simple;
/*     */ 
/*     */ import com.sun.j3d.utils.geometry.Sphere;
/*     */ import java.awt.Color;
/*     */ import javax.media.j3d.Appearance;
/*     */ import javax.media.j3d.Node;
/*     */ import javax.media.j3d.PolygonAttributes;
/*     */ import javax.media.j3d.Switch;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal3d.FieldPortrayal3D;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ 
/*     */ public class CircledPortrayal3D extends SimplePortrayal3D
/*     */ {
/*  42 */   public static final Appearance DEFAULT_CIRCLED_APPEARANCE = appearanceForColor(new Color(255, 255, 255, 63));
/*     */   public static final double DEFAULT_SCALE = 2.0D;
/*     */   double scale;
/*     */   Appearance appearance;
/*     */   SimplePortrayal3D child;
/* 130 */   boolean showCircle = true;
/*     */   boolean onlyCircleWhenSelected;
/*     */ 
/*     */   public CircledPortrayal3D(SimplePortrayal3D child)
/*     */   {
/*  51 */     this(child, 2.0D);
/*     */   }
/*     */ 
/*     */   public CircledPortrayal3D(SimplePortrayal3D child, double scale)
/*     */   {
/*  56 */     this(child, scale, false);
/*     */   }
/*     */ 
/*     */   public CircledPortrayal3D(SimplePortrayal3D child, double scale, boolean onlyCircleWhenSelected)
/*     */   {
/*  61 */     this(child, DEFAULT_CIRCLED_APPEARANCE, scale, onlyCircleWhenSelected);
/*     */   }
/*     */ 
/*     */   public CircledPortrayal3D(SimplePortrayal3D child, Color color)
/*     */   {
/*  66 */     this(child, color, 2.0D, false);
/*     */   }
/*     */ 
/*     */   public CircledPortrayal3D(SimplePortrayal3D child, Color color, double scale, boolean onlyCircleWhenSelected)
/*     */   {
/*  71 */     this(child, appearanceForColor(color), scale, onlyCircleWhenSelected);
/*     */   }
/*     */ 
/*     */   public CircledPortrayal3D(SimplePortrayal3D child, Appearance appearance, double scale, boolean onlyCircleWhenSelected)
/*     */   {
/*  76 */     this.child = child;
/*  77 */     this.appearance = appearance;
/*  78 */     this.scale = scale;
/*  79 */     this.onlyCircleWhenSelected = onlyCircleWhenSelected;
/*     */   }
/*     */ 
/*     */   public PolygonAttributes polygonAttributes()
/*     */   {
/*  84 */     return this.child.polygonAttributes();
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/*  89 */     return this.child.getInspector(wrapper, state);
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/*  94 */     return this.child.getName(wrapper);
/*     */   }
/*     */ 
/*     */   public void setCurrentDisplay(Display3D display)
/*     */   {
/* 100 */     super.setCurrentDisplay(display);
/* 101 */     this.child.setCurrentDisplay(display);
/*     */   }
/*     */ 
/*     */   public void setCurrentFieldPortrayal(FieldPortrayal3D p)
/*     */   {
/* 107 */     super.setCurrentFieldPortrayal(p);
/* 108 */     this.child.setCurrentFieldPortrayal(p);
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/* 113 */     if (this.child.setSelected(wrapper, selected))
/* 114 */       return super.setSelected(wrapper, selected);
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */   public SimplePortrayal3D getChild(Object object)
/*     */   {
/* 120 */     if (this.child != null) return this.child;
/*     */ 
/* 123 */     if (!(object instanceof SimplePortrayal3D))
/* 124 */       throw new RuntimeException("Object provided to CircledPortrayal3D is not a SimplePortrayal3D: " + object);
/* 125 */     return (SimplePortrayal3D)object;
/*     */   }
/*     */ 
/*     */   public void setOnlyCircleWhenSelected(boolean val)
/*     */   {
/* 133 */     this.onlyCircleWhenSelected = val; } 
/* 134 */   public boolean getOnlyCircleWhenSelected() { return this.onlyCircleWhenSelected; } 
/*     */   public boolean isCircleShowing() {
/* 136 */     return this.showCircle; } 
/* 137 */   public void setCircleShowing(boolean val) { this.showCircle = val; }
/*     */ 
/*     */   void updateSwitch(Switch jswitch, Object object)
/*     */   {
/* 141 */     if ((this.showCircle) && ((isSelected(object)) || (!this.onlyCircleWhenSelected)))
/* 142 */       jswitch.setWhichChild(-2);
/*     */     else
/* 144 */       jswitch.setWhichChild(-1);
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object obj, TransformGroup j3dModel)
/*     */   {
/* 149 */     if (j3dModel == null)
/*     */     {
/* 151 */       j3dModel = new TransformGroup();
/* 152 */       j3dModel.setCapability(12);
/* 153 */       j3dModel.clearCapabilityIsFrequent(12);
/* 154 */       Switch jswitch = new Switch();
/* 155 */       jswitch.setCapability(18);
/*     */ 
/* 158 */       Sphere sphere = new Sphere((float)(this.scale / 2.0D), this.appearance);
/*     */ 
/* 161 */       clearPickableFlags(sphere);
/*     */ 
/* 164 */       Node n = getChild(obj).getModel(obj, null);
/*     */ 
/* 166 */       j3dModel.addChild(n);
/* 167 */       jswitch.addChild(sphere);
/* 168 */       j3dModel.addChild(jswitch);
/* 169 */       updateSwitch(jswitch, obj);
/*     */     }
/*     */     else
/*     */     {
/* 173 */       TransformGroup t = (TransformGroup)j3dModel.getChild(0);
/* 174 */       getChild(obj).getModel(obj, t);
/* 175 */       updateSwitch((Switch)j3dModel.getChild(1), obj);
/*     */     }
/* 177 */     return j3dModel;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.CircledPortrayal3D
 * JD-Core Version:    0.6.2
 */