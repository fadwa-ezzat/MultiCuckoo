/*    */ package sim.portrayal3d.simple;
/*    */ 
/*    */ import com.sun.j3d.utils.geometry.Cone;
/*    */ import java.awt.Color;
/*    */ import java.awt.Image;
/*    */ import javax.media.j3d.Appearance;
/*    */ 
/*    */ public class ConePortrayal3D extends PrimitivePortrayal3D
/*    */ {
/*    */   public ConePortrayal3D()
/*    */   {
/* 26 */     this(1.0D);
/*    */   }
/*    */ 
/*    */   public ConePortrayal3D(double scale)
/*    */   {
/* 32 */     this(Color.white, scale);
/*    */   }
/*    */ 
/*    */   public ConePortrayal3D(Color color)
/*    */   {
/* 38 */     this(color, 1.0D);
/*    */   }
/*    */ 
/*    */   public ConePortrayal3D(Color color, double scale)
/*    */   {
/* 44 */     this(appearanceForColor(color), true, false, scale);
/*    */   }
/*    */ 
/*    */   public ConePortrayal3D(Image image)
/*    */   {
/* 50 */     this(image, 1.0D);
/*    */   }
/*    */ 
/*    */   public ConePortrayal3D(Image image, double scale)
/*    */   {
/* 56 */     this(appearanceForImage(image, true), false, true, scale);
/*    */   }
/*    */ 
/*    */   public ConePortrayal3D(Appearance appearance, boolean generateNormals, boolean generateTextureCoordinates, double scale)
/*    */   {
/* 62 */     this.appearance = appearance;
/* 63 */     setScale(null, scale);
/*    */ 
/* 65 */     Cone cone = new Cone(0.5F, 1.0F, (generateNormals ? 1 : 0) | (generateTextureCoordinates ? 2 : 0), appearance);
/*    */ 
/* 70 */     setShape3DFlags(cone.getShape(0));
/* 71 */     setShape3DFlags(cone.getShape(1));
/* 72 */     this.group = cone;
/*    */   }
/*    */   protected int numShapes() {
/* 75 */     return 2;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.ConePortrayal3D
 * JD-Core Version:    0.6.2
 */