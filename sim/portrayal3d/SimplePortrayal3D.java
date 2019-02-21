/*     */ package sim.portrayal3d;
/*     */ 
/*     */ import com.sun.j3d.utils.image.TextureLoader;
/*     */ import java.awt.Color;
/*     */ import java.awt.Image;
/*     */ import java.util.HashMap;
/*     */ import javax.media.j3d.Appearance;
/*     */ import javax.media.j3d.ColoringAttributes;
/*     */ import javax.media.j3d.Geometry;
/*     */ import javax.media.j3d.Material;
/*     */ import javax.media.j3d.Node;
/*     */ import javax.media.j3d.PolygonAttributes;
/*     */ import javax.media.j3d.Shape3D;
/*     */ import javax.media.j3d.TextureAttributes;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.media.j3d.TransparencyAttributes;
/*     */ import javax.vecmath.Color3f;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ 
/*     */ public class SimplePortrayal3D
/*     */   implements Portrayal3D
/*     */ {
/*  46 */   public static final Appearance DEFAULT_APPEARANCE = appearanceForColor(Color.white);
/*     */ 
/*  70 */   static final Color3f BLACK = new Color3f(Color.black);
/*     */ 
/* 172 */   FieldPortrayal3D fieldPortrayal = null;
/*     */ 
/* 183 */   Display3D display = null;
/*     */ 
/* 215 */   HashMap selectedObjects = null;
/*     */ 
/*     */   public static Appearance appearanceForColor(Color unlitColor)
/*     */   {
/*  52 */     Appearance appearance = new Appearance();
/*     */ 
/*  54 */     setAppearanceFlags(appearance);
/*  55 */     float[] c = unlitColor.getRGBComponents(null);
/*  56 */     ColoringAttributes ca = new ColoringAttributes(c[0], c[1], c[2], 2);
/*  57 */     ca.setCapability(1);
/*  58 */     ca.setCapability(0);
/*  59 */     appearance.setColoringAttributes(ca);
/*  60 */     if (c[3] < 1.0D)
/*     */     {
/*  62 */       TransparencyAttributes tta = new TransparencyAttributes(2, 1.0F - c[3]);
/*  63 */       tta.setCapability(3);
/*  64 */       tta.setCapability(2);
/*  65 */       appearance.setTransparencyAttributes(tta);
/*     */     }
/*  67 */     return appearance;
/*     */   }
/*     */ 
/*     */   public static Appearance appearanceForColors(Color ambientColor, Color emissiveColor, Color diffuseColor, Color specularColor, double shininess, double opacity)
/*     */   {
/*  80 */     Appearance appearance = new Appearance();
/*     */ 
/*  82 */     setAppearanceFlags(appearance);
/*  83 */     ColoringAttributes ca = new ColoringAttributes(BLACK, 3);
/*  84 */     ca.setCapability(1);
/*  85 */     ca.setCapability(0);
/*  86 */     appearance.setColoringAttributes(ca);
/*     */ 
/*  88 */     if (opacity > 1.0D) opacity = 1.0D;
/*  89 */     if (opacity < 0.0D) opacity = 0.0D;
/*  90 */     if (shininess > 1.0D) shininess = 1.0D;
/*  91 */     if (shininess < 0.0D) shininess = 0.0D;
/*  92 */     shininess = shininess * 63.0D + 1.0D;
/*     */ 
/*  94 */     Material m = new Material();
/*  95 */     m.setCapability(0);
/*  96 */     m.setCapability(1);
/*     */ 
/*  98 */     if (ambientColor != null) m.setAmbientColor(new Color3f(ambientColor)); else {
/*  99 */       m.setAmbientColor(BLACK);
/*     */     }
/* 101 */     if (emissiveColor != null) m.setEmissiveColor(new Color3f(emissiveColor)); else {
/* 102 */       m.setEmissiveColor(BLACK);
/*     */     }
/* 104 */     if (diffuseColor != null) m.setDiffuseColor(new Color3f(diffuseColor)); else {
/* 105 */       m.setDiffuseColor(BLACK);
/*     */     }
/* 107 */     if (specularColor != null) m.setSpecularColor(new Color3f(specularColor)); else {
/* 108 */       m.setSpecularColor(BLACK);
/*     */     }
/* 110 */     m.setShininess((float)shininess);
/* 111 */     appearance.setMaterial(m);
/* 112 */     if (opacity < 1.0D)
/*     */     {
/* 114 */       TransparencyAttributes tta = new TransparencyAttributes(2, 1.0F - (float)opacity);
/* 115 */       tta.setCapability(3);
/* 116 */       tta.setCapability(2);
/* 117 */       appearance.setTransparencyAttributes(tta);
/*     */     }
/* 119 */     return appearance;
/*     */   }
/*     */ 
/*     */   public static Appearance appearanceForImage(Image image, boolean opaque)
/*     */   {
/* 129 */     Appearance appearance = appearanceForColor(Color.black);
/*     */ 
/* 131 */     if (!opaque)
/*     */     {
/* 133 */       TransparencyAttributes tta = new TransparencyAttributes(2, 1.0F);
/* 134 */       tta.setCapability(3);
/* 135 */       tta.setCapability(2);
/* 136 */       appearance.setTransparencyAttributes(tta);
/*     */     }
/* 138 */     appearance.setTexture(new TextureLoader(image, 2, null).getTexture());
/* 139 */     TextureAttributes ta = new TextureAttributes();
/* 140 */     ta.setTextureMode(5);
/* 141 */     PolygonAttributes pa = new PolygonAttributes();
/* 142 */     pa.setCullFace(0);
/* 143 */     appearance.setPolygonAttributes(pa);
/* 144 */     appearance.setTextureAttributes(ta);
/* 145 */     return appearance;
/*     */   }
/*     */   public PolygonAttributes polygonAttributes() {
/* 148 */     return null;
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object object, TransformGroup prev)
/*     */   {
/* 154 */     if (prev == null) prev = new TransformGroup();
/* 155 */     return prev;
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/* 160 */     if (wrapper == null) return null;
/* 161 */     return Inspector.getInspector(wrapper.getObject(), state, "Properties");
/*     */   }
/*     */   public String getStatus(LocationWrapper wrapper) {
/* 164 */     return getName(wrapper);
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper) {
/* 168 */     if (wrapper == null) return "";
/* 169 */     return "" + wrapper.getObject();
/*     */   }
/*     */ 
/*     */   public void setCurrentFieldPortrayal(FieldPortrayal3D p)
/*     */   {
/* 175 */     this.fieldPortrayal = p;
/*     */   }
/*     */ 
/*     */   public FieldPortrayal3D getCurrentFieldPortrayal()
/*     */   {
/* 180 */     return this.fieldPortrayal;
/*     */   }
/*     */ 
/*     */   public void setCurrentDisplay(Display3D display)
/*     */   {
/* 186 */     this.display = display;
/*     */   }
/*     */ 
/*     */   public Display3D getCurrentDisplay()
/*     */   {
/* 195 */     if (this.display == null)
/*     */     {
/* 197 */       FieldPortrayal3D f = getCurrentFieldPortrayal();
/* 198 */       if (f == null) return null;
/* 199 */       return f.getCurrentDisplay();
/*     */     }
/* 201 */     return this.display;
/*     */   }
/*     */ 
/*     */   public GUIState getCurrentGUIState()
/*     */   {
/* 206 */     Display3D d = getCurrentDisplay();
/* 207 */     return d == null ? null : d.getSimulation();
/*     */   }
/*     */ 
/*     */   public boolean isSelected(Object obj)
/*     */   {
/* 212 */     return (this.selectedObjects != null) && (this.selectedObjects.containsKey(obj));
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/* 241 */     if (this.selectedObjects == null)
/* 242 */       this.selectedObjects = new HashMap(1);
/* 243 */     if (selected) {
/* 244 */       this.selectedObjects.put(wrapper.getObject(), wrapper);
/*     */     }
/*     */     else {
/* 247 */       this.selectedObjects.remove(wrapper.getObject());
/* 248 */       if (this.selectedObjects.isEmpty())
/* 249 */         this.selectedObjects = null;
/*     */     }
/* 251 */     return true;
/*     */   }
/*     */ 
/*     */   public static void setAppearanceFlags(Appearance a)
/*     */   {
/* 259 */     a.setCapability(8);
/* 260 */     a.clearCapabilityIsFrequent(8);
/* 261 */     a.setCapability(9);
/* 262 */     a.clearCapabilityIsFrequent(9);
/* 263 */     a.setCapability(0);
/* 264 */     a.clearCapabilityIsFrequent(0);
/* 265 */     a.setCapability(1);
/* 266 */     a.clearCapabilityIsFrequent(1);
/* 267 */     a.setCapability(14);
/* 268 */     a.clearCapabilityIsFrequent(14);
/* 269 */     a.setCapability(15);
/* 270 */     a.clearCapabilityIsFrequent(15);
/* 271 */     a.setCapability(10);
/* 272 */     a.clearCapabilityIsFrequent(10);
/* 273 */     a.setCapability(11);
/* 274 */     a.clearCapabilityIsFrequent(11);
/* 275 */     a.setCapability(6);
/* 276 */     a.clearCapabilityIsFrequent(6);
/* 277 */     a.setCapability(7);
/* 278 */     a.clearCapabilityIsFrequent(7);
/* 279 */     a.setCapability(2);
/* 280 */     a.clearCapabilityIsFrequent(2);
/* 281 */     a.setCapability(3);
/* 282 */     a.clearCapabilityIsFrequent(3);
/*     */   }
/*     */ 
/*     */   public static void setPickableFlags(Shape3D s3d)
/*     */   {
/* 288 */     s3d.setCapability(12);
/* 289 */     setPickableFlags(s3d.getGeometry());
/*     */ 
/* 291 */     s3d.clearCapabilityIsFrequent(12);
/*     */   }
/*     */ 
/*     */   public static void setPickableFlags(Geometry geom)
/*     */   {
/* 297 */     geom.setCapability(8);
/* 298 */     geom.setCapability(17);
/* 299 */     geom.setCapability(0);
/*     */ 
/* 302 */     geom.clearCapabilityIsFrequent(8);
/* 303 */     geom.clearCapabilityIsFrequent(17);
/* 304 */     geom.clearCapabilityIsFrequent(0);
/*     */   }
/*     */ 
/*     */   public static void clearPickableFlags(Node n)
/*     */   {
/* 310 */     n.setPickable(false);
/* 311 */     n.clearCapability(1);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.SimplePortrayal3D
 * JD-Core Version:    0.6.2
 */