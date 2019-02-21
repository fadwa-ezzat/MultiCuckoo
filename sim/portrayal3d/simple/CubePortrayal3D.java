/*     */ package sim.portrayal3d.simple;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Image;
/*     */ import javax.media.j3d.Appearance;
/*     */ import javax.media.j3d.PolygonAttributes;
/*     */ import javax.media.j3d.QuadArray;
/*     */ import javax.media.j3d.Shape3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.vecmath.TexCoord2f;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ 
/*     */ public class CubePortrayal3D extends SimplePortrayal3D
/*     */ {
/*     */   Appearance appearance;
/*     */   boolean generateTextureCoordinates;
/*  70 */   final float[] scaledVerts = new float[verts.length];
/*     */ 
/*  72 */   static final float[] verts = { 0.5F, -0.5F, 0.5F, 0.5F, 0.5F, 0.5F, -0.5F, 0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F, -0.5F, -0.5F, -0.5F, 0.5F, -0.5F, 0.5F, 0.5F, -0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F, -0.5F, 0.5F, 0.5F, -0.5F, 0.5F, 0.5F, 0.5F, 0.5F, -0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F, 0.5F, 0.5F, -0.5F, 0.5F, -0.5F, -0.5F, -0.5F, -0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F, -0.5F, 0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F, -0.5F, -0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F, 0.5F };
/*     */ 
/*     */   public CubePortrayal3D()
/*     */   {
/*  28 */     this(1.0D);
/*     */   }
/*     */ 
/*     */   public CubePortrayal3D(double scale)
/*     */   {
/*  34 */     this(Color.white, scale);
/*     */   }
/*     */ 
/*     */   public CubePortrayal3D(Color color)
/*     */   {
/*  40 */     this(color, 1.0D);
/*     */   }
/*     */ 
/*     */   public CubePortrayal3D(Color color, double scale)
/*     */   {
/*  46 */     this(appearanceForColor(color), false, scale);
/*     */   }
/*     */ 
/*     */   public CubePortrayal3D(Image image)
/*     */   {
/*  52 */     this(image, 1.0D);
/*     */   }
/*     */ 
/*     */   public CubePortrayal3D(Image image, double scale)
/*     */   {
/*  58 */     this(appearanceForImage(image, true), true, scale);
/*     */   }
/*     */ 
/*     */   public CubePortrayal3D(Appearance appearance, boolean generateTextureCoordinates, double scale)
/*     */   {
/*  64 */     this.generateTextureCoordinates = generateTextureCoordinates;
/*  65 */     this.appearance = appearance;
/*  66 */     for (int i = 0; i < this.scaledVerts.length; i++)
/*  67 */       this.scaledVerts[i] = (verts[i] * (float)scale);
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object obj, TransformGroup j3dModel)
/*     */   {
/*  96 */     if (j3dModel == null)
/*     */     {
/*  98 */       j3dModel = new TransformGroup();
/*  99 */       j3dModel.setCapability(12);
/*     */ 
/* 101 */       QuadArray quadArray = new QuadArray(24, 0x1 | (this.generateTextureCoordinates ? 32 : 0));
/*     */ 
/* 103 */       quadArray.setCoordinates(0, this.scaledVerts);
/*     */ 
/* 110 */       if (this.generateTextureCoordinates)
/*     */       {
/* 112 */         quadArray.setTextureCoordinate(0, 0, new TexCoord2f(1.0F, 1.0F));
/* 113 */         quadArray.setTextureCoordinate(0, 1, new TexCoord2f(1.0F, 0.0F));
/* 114 */         quadArray.setTextureCoordinate(0, 2, new TexCoord2f(0.0F, 0.0F));
/* 115 */         quadArray.setTextureCoordinate(0, 3, new TexCoord2f(0.0F, 1.0F));
/*     */       }
/*     */ 
/* 118 */       PolygonAttributes pa = new PolygonAttributes();
/* 119 */       pa.setCullFace(1);
/* 120 */       if (!this.appearance.isLive())
/* 121 */         this.appearance.setCapability(15);
/* 122 */       this.appearance.setPolygonAttributes(pa);
/*     */ 
/* 125 */       Shape3D localShape = new Shape3D(quadArray, this.appearance);
/* 126 */       localShape.setCapability(15);
/* 127 */       setPickableFlags(localShape);
/*     */ 
/* 130 */       LocationWrapper pickI = new LocationWrapper(obj, null, getCurrentFieldPortrayal());
/* 131 */       localShape.setUserData(pickI);
/*     */ 
/* 133 */       j3dModel.addChild(localShape);
/*     */     }
/* 135 */     return j3dModel;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.CubePortrayal3D
 * JD-Core Version:    0.6.2
 */