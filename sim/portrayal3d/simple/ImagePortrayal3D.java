/*     */ package sim.portrayal3d.simple;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import javax.media.j3d.Appearance;
/*     */ import javax.media.j3d.OrientedShape3D;
/*     */ import javax.media.j3d.PolygonAttributes;
/*     */ import javax.media.j3d.QuadArray;
/*     */ import javax.media.j3d.Shape3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.vecmath.Point3f;
/*     */ import javax.vecmath.TexCoord2f;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ 
/*     */ public class ImagePortrayal3D extends SimplePortrayal3D
/*     */ {
/*     */   Shape3D shape;
/*     */ 
/*     */   public ImagePortrayal3D(Class c, String resourceName)
/*     */   {
/*  42 */     this(new ImageIcon(c.getResource(resourceName)));
/*     */   }
/*     */ 
/*     */   public ImagePortrayal3D(Class c, String resourceName, boolean oriented, boolean opaque)
/*     */   {
/*  48 */     this(new ImageIcon(c.getResource(resourceName)), oriented, opaque);
/*     */   }
/*     */ 
/*     */   public ImagePortrayal3D(ImageIcon icon)
/*     */   {
/*  54 */     this(icon.getImage());
/*     */   }
/*     */ 
/*     */   public ImagePortrayal3D(ImageIcon icon, boolean oriented, boolean opaque)
/*     */   {
/*  60 */     this(icon.getImage(), oriented, opaque);
/*     */   }
/*     */ 
/*     */   public ImagePortrayal3D(Image image)
/*     */   {
/*  66 */     this(image, true, false);
/*     */   }
/*     */ 
/*     */   public ImagePortrayal3D(Image image, boolean oriented, boolean opaque)
/*     */   {
/*  72 */     float width = image.getWidth(null);
/*  73 */     float height = image.getHeight(null);
/*     */ 
/*  78 */     if (width > height) {
/*  79 */       width /= height; height = 1.0F;
/*     */     } else {
/*  81 */       height /= width; width = 1.0F;
/*     */     }
/*     */ 
/*  84 */     float[] vertices = { width / 2.0F, -height / 2.0F, 0.0F, width / 2.0F, height / 2.0F, 0.0F, -width / 2.0F, height / 2.0F, 0.0F, -width / 2.0F, -height / 2.0F, 0.0F };
/*     */ 
/*  93 */     QuadArray geometry = new QuadArray(4, 33);
/*  94 */     geometry.setCoordinates(0, vertices);
/*     */ 
/* 100 */     geometry.setTextureCoordinate(0, 0, new TexCoord2f(1.0F, 1.0F));
/* 101 */     geometry.setTextureCoordinate(0, 1, new TexCoord2f(1.0F, 0.0F));
/* 102 */     geometry.setTextureCoordinate(0, 2, new TexCoord2f(0.0F, 0.0F));
/* 103 */     geometry.setTextureCoordinate(0, 3, new TexCoord2f(0.0F, 1.0F));
/*     */ 
/* 105 */     Appearance appearance = appearanceForImage(image, opaque);
/* 106 */     PolygonAttributes pa = new PolygonAttributes();
/* 107 */     pa.setCullFace(oriented ? 1 : 0);
/* 108 */     appearance.setPolygonAttributes(pa);
/*     */ 
/* 111 */     if (oriented) this.shape = new OrientedShape3D(geometry, appearance, 1, new Point3f(0.0F, 0.0F, 0.0F));
/*     */     else
/* 113 */       this.shape = new Shape3D(geometry, appearance);
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object obj, TransformGroup j3dModel)
/*     */   {
/* 118 */     if (j3dModel == null)
/*     */     {
/* 120 */       j3dModel = new TransformGroup();
/* 121 */       j3dModel.setCapability(12);
/*     */ 
/* 124 */       Shape3D s = (Shape3D)this.shape.cloneTree(false);
/*     */ 
/* 127 */       setPickableFlags(s);
/*     */ 
/* 130 */       LocationWrapper pickI = new LocationWrapper(obj, null, getCurrentFieldPortrayal());
/*     */ 
/* 133 */       s.setUserData(pickI);
/*     */ 
/* 135 */       j3dModel.addChild(s);
/*     */     }
/* 137 */     return j3dModel;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.ImagePortrayal3D
 * JD-Core Version:    0.6.2
 */