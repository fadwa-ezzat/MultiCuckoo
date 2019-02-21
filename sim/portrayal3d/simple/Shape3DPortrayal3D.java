/*     */ package sim.portrayal3d.simple;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Image;
/*     */ import javax.media.j3d.Appearance;
/*     */ import javax.media.j3d.CompressedGeometry;
/*     */ import javax.media.j3d.Geometry;
/*     */ import javax.media.j3d.Node;
/*     */ import javax.media.j3d.Shape3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ 
/*     */ public class Shape3DPortrayal3D extends PrimitivePortrayal3D
/*     */ {
/*     */   public Shape3DPortrayal3D(Shape3D shape)
/*     */   {
/*  37 */     this(shape, Color.white);
/*     */   }
/*     */ 
/*     */   public Shape3DPortrayal3D(Shape3D shape, Color color)
/*     */   {
/*  43 */     this(shape, appearanceForColor(color));
/*     */   }
/*     */ 
/*     */   public Shape3DPortrayal3D(Shape3D shape, Image image)
/*     */   {
/*  49 */     this(shape, appearanceForImage(image, true));
/*     */   }
/*     */ 
/*     */   public Shape3DPortrayal3D(Shape3D shape, Appearance appearance)
/*     */   {
/*  55 */     this.appearance = appearance;
/*  56 */     shape = (Shape3D)shape.cloneTree(true);
/*     */ 
/*  58 */     Geometry g = shape.getGeometry();
/*  59 */     if ((g instanceof CompressedGeometry)) {
/*  60 */       ((CompressedGeometry)g).setCapability(2);
/*     */     }
/*  62 */     setShape3DFlags(shape);
/*  63 */     shape.setAppearance(appearance);
/*  64 */     this.group = shape;
/*     */   }
/*     */ 
/*     */   public Shape3DPortrayal3D(Geometry geometry)
/*     */   {
/*  70 */     this(geometry, Color.white);
/*     */   }
/*     */ 
/*     */   public Shape3DPortrayal3D(Geometry geometry, Color color)
/*     */   {
/*  76 */     this(geometry, appearanceForColor(color));
/*     */   }
/*     */ 
/*     */   public Shape3DPortrayal3D(Geometry geometry, Image image)
/*     */   {
/*  82 */     this(geometry, appearanceForImage(image, true));
/*     */   }
/*     */ 
/*     */   public Shape3DPortrayal3D(Geometry geometry, Appearance appearance)
/*     */   {
/*  88 */     this(new Shape3D(geometry), appearance);
/*     */   }
/*     */   protected int numShapes() {
/*  91 */     return 1;
/*     */   }
/*     */ 
/*     */   protected Shape3D getShape(TransformGroup j3dModel, int shapeNumber)
/*     */   {
/*  96 */     Node n = j3dModel;
/*  97 */     while ((n instanceof TransformGroup))
/*  98 */       n = ((TransformGroup)n).getChild(0);
/*  99 */     Shape3D p = (Shape3D)n;
/* 100 */     return p;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.Shape3DPortrayal3D
 * JD-Core Version:    0.6.2
 */