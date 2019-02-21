/*     */ package sim.portrayal3d.simple;
/*     */ 
/*     */ import com.sun.j3d.utils.geometry.Text2D;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import javax.media.j3d.OrderedGroup;
/*     */ import javax.media.j3d.OrientedShape3D;
/*     */ import javax.media.j3d.PolygonAttributes;
/*     */ import javax.media.j3d.Switch;
/*     */ import javax.media.j3d.Transform3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.vecmath.Color3f;
/*     */ import javax.vecmath.Point3f;
/*     */ import javax.vecmath.Vector3f;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal3d.FieldPortrayal3D;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ 
/*     */ public class LabelledPortrayal3D extends SimplePortrayal3D
/*     */ {
/*  61 */   public static final Transform3D DEFAULT_LABEL_OFFSET = transformForOffset(0.5D, 0.5D, 0.5D);
/*     */   static final int FONT_SIZE = 18;
/*     */   static final double SCALING_MODIFIER = 0.2D;
/*  55 */   double labelScale = 1.0D;
/*     */   Color color;
/*     */   Transform3D offset;
/*     */   Font font;
/*     */   SimplePortrayal3D child;
/*     */   String label;
/* 165 */   boolean showLabel = true;
/*     */   boolean onlyLabelWhenSelected;
/*     */ 
/*     */   public double getLabelScale()
/*     */   {
/*  56 */     return this.labelScale; } 
/*  57 */   public void setLabelScale(double s) { this.labelScale = Math.abs(s); }
/*     */ 
/*     */ 
/*     */   static Transform3D transformForOffset(double x, double y, double z)
/*     */   {
/*  66 */     Transform3D offset = new Transform3D();
/*  67 */     offset.setTranslation(new Vector3f((float)x, (float)y, (float)z));
/*  68 */     return offset;
/*     */   }
/*     */ 
/*     */   public LabelledPortrayal3D(SimplePortrayal3D child)
/*     */   {
/*  80 */     this(child, null, Color.white, false);
/*     */   }
/*     */ 
/*     */   public LabelledPortrayal3D(SimplePortrayal3D child, String label)
/*     */   {
/*  85 */     this(child, label, Color.white, false);
/*     */   }
/*     */ 
/*     */   public LabelledPortrayal3D(SimplePortrayal3D child, String label, Color color, boolean onlyLabelWhenSelected)
/*     */   {
/*  90 */     this(child, DEFAULT_LABEL_OFFSET, null, label, color, onlyLabelWhenSelected);
/*     */   }
/*     */ 
/*     */   public LabelledPortrayal3D(SimplePortrayal3D child, double offset, Font font, String label, Color color, boolean onlyLabelWhenSelected)
/*     */   {
/*  96 */     this(child, transformForOffset(offset, offset, offset), font, label, color, onlyLabelWhenSelected);
/*     */   }
/*     */ 
/*     */   public LabelledPortrayal3D(SimplePortrayal3D child, double offsetx, double offsety, double offsetz, Font font, String label, Color color, boolean onlyLabelWhenSelected)
/*     */   {
/* 102 */     this(child, transformForOffset(offsetx, offsety, offsetz), font, label, color, onlyLabelWhenSelected);
/*     */   }
/*     */ 
/*     */   public LabelledPortrayal3D(SimplePortrayal3D child, Transform3D offset, Font font, String label, Color color, boolean onlyLabelWhenSelected)
/*     */   {
/* 108 */     this.child = child;
/* 109 */     this.color = color;
/* 110 */     this.offset = offset;
/* 111 */     this.onlyLabelWhenSelected = onlyLabelWhenSelected;
/* 112 */     this.label = label;
/* 113 */     if (font == null) font = new Font("SansSerif", 0, 18);
/* 114 */     this.font = font;
/*     */   }
/*     */ 
/*     */   public PolygonAttributes polygonAttributes()
/*     */   {
/* 120 */     return this.child.polygonAttributes();
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/* 125 */     return this.child.getInspector(wrapper, state);
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/* 130 */     return this.child.getName(wrapper);
/*     */   }
/*     */ 
/*     */   public void setCurrentDisplay(Display3D display)
/*     */   {
/* 136 */     super.setCurrentDisplay(display);
/* 137 */     this.child.setCurrentDisplay(display);
/*     */   }
/*     */ 
/*     */   public void setCurrentFieldPortrayal(FieldPortrayal3D p)
/*     */   {
/* 143 */     super.setCurrentFieldPortrayal(p);
/* 144 */     this.child.setCurrentFieldPortrayal(p);
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/* 149 */     if (this.child.setSelected(wrapper, selected))
/* 150 */       return super.setSelected(wrapper, selected);
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */   public String getLabel(Object object, TransformGroup j3dModel)
/*     */   {
/* 160 */     if (this.label == null) return "" + object;
/* 161 */     return this.label;
/*     */   }
/*     */ 
/*     */   public void setOnlyLabelWhenSelected(boolean val)
/*     */   {
/* 168 */     this.onlyLabelWhenSelected = val; } 
/* 169 */   public boolean getOnlyLabelWhenSelected() { return this.onlyLabelWhenSelected; } 
/*     */   public boolean isLabelShowing() {
/* 171 */     return this.showLabel; } 
/* 172 */   public void setLabelShowing(boolean val) { this.showLabel = val; }
/*     */ 
/*     */   public SimplePortrayal3D getChild(Object object)
/*     */   {
/* 176 */     if (this.child != null) return this.child;
/*     */ 
/* 179 */     if (!(object instanceof SimplePortrayal3D))
/* 180 */       throw new RuntimeException("Object provided to LabelledPortrayal3D is not a SimplePortrayal3D: " + object);
/* 181 */     return (SimplePortrayal3D)object;
/*     */   }
/*     */ 
/*     */   void updateSwitch(Switch jswitch, Object object)
/*     */   {
/* 190 */     jswitch.setWhichChild((this.showLabel) && ((isSelected(object)) || (!this.onlyLabelWhenSelected)) ? -2 : -1);
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object obj, TransformGroup j3dModel)
/*     */   {
/* 197 */     if (j3dModel == null)
/*     */     {
/* 208 */       TransformGroup n = getChild(obj).getModel(obj, null);
/*     */ 
/* 211 */       String l = getLabel(obj, n);
/*     */ 
/* 214 */       j3dModel = new TransformGroup();
/* 215 */       j3dModel.setCapability(12);
/* 216 */       j3dModel.clearCapabilityIsFrequent(12);
/*     */ 
/* 219 */       Switch jswitch = new Switch();
/* 220 */       jswitch.setCapability(18);
/* 221 */       jswitch.setCapability(12);
/* 222 */       jswitch.clearCapabilityIsFrequent(18);
/* 223 */       jswitch.clearCapabilityIsFrequent(12);
/*     */ 
/* 226 */       jswitch.setUserData(l);
/*     */ 
/* 229 */       Text2D text = new Text2D(l, new Color3f(this.color), this.font.getFamily(), this.font.getSize(), this.font.getStyle());
/* 230 */       text.setRectangleScaleFactor((float)(this.labelScale * 0.2D));
/*     */ 
/* 241 */       OrientedShape3D o3d = new OrientedShape3D(text.getGeometry(), text.getAppearance(), 1, new Point3f(0.0F, 0.0F, 0.0F));
/*     */ 
/* 243 */       o3d.setCapability(15);
/* 244 */       o3d.setCapability(13);
/* 245 */       o3d.clearCapabilityIsFrequent(15);
/* 246 */       o3d.clearCapabilityIsFrequent(13);
/*     */ 
/* 249 */       TransformGroup o = new TransformGroup();
/* 250 */       o.setCapability(12);
/* 251 */       o.clearCapabilityIsFrequent(12);
/* 252 */       o.setTransform(this.offset);
/*     */ 
/* 255 */       clearPickableFlags(o);
/* 256 */       o.addChild(o3d);
/* 257 */       jswitch.addChild(o);
/*     */ 
/* 259 */       j3dModel.addChild(n);
/* 260 */       j3dModel.addChild(jswitch);
/* 261 */       updateSwitch(jswitch, obj);
/*     */     }
/*     */     else
/*     */     {
/* 265 */       TransformGroup t = null;
/* 266 */       Switch s = null;
/*     */ 
/* 268 */       if ((j3dModel.getChild(0) instanceof OrderedGroup))
/*     */       {
/* 270 */         OrderedGroup g = (OrderedGroup)j3dModel.getChild(0);
/* 271 */         t = (TransformGroup)g.getChild(0);
/* 272 */         s = (Switch)g.getChild(1);
/*     */       }
/*     */       else
/*     */       {
/* 276 */         t = (TransformGroup)j3dModel.getChild(0);
/* 277 */         s = (Switch)j3dModel.getChild(1);
/*     */       }
/*     */ 
/* 281 */       String l = getLabel(obj, t);
/* 282 */       if ((!s.getUserData().equals(l)) && (this.showLabel))
/*     */       {
/* 288 */         Text2D text = new Text2D(l, new Color3f(this.color), this.font.getFamily(), this.font.getSize(), this.font.getStyle());
/* 289 */         text.setRectangleScaleFactor((float)(this.labelScale * 0.2D));
/*     */ 
/* 292 */         TransformGroup t2 = (TransformGroup)s.getChild(0);
/* 293 */         OrientedShape3D o3d = (OrientedShape3D)t2.getChild(0);
/*     */ 
/* 296 */         o3d.setGeometry(text.getGeometry());
/* 297 */         o3d.setAppearance(text.getAppearance());
/*     */       }
/*     */ 
/* 301 */       getChild(obj).getModel(obj, t);
/* 302 */       updateSwitch(s, obj);
/*     */     }
/* 304 */     return j3dModel;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.LabelledPortrayal3D
 * JD-Core Version:    0.6.2
 */