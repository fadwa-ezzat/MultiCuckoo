/*    */ package sim.portrayal3d.simple;
/*    */ 
/*    */ import com.sun.j3d.utils.geometry.Sphere;
/*    */ import java.awt.Color;
/*    */ import java.awt.Image;
/*    */ import javax.media.j3d.Appearance;
/*    */ 
/*    */ public class SpherePortrayal3D extends PrimitivePortrayal3D
/*    */ {
/*    */   public static final int DEFAULT_DIVISIONS = 15;
/*    */ 
/*    */   public SpherePortrayal3D()
/*    */   {
/* 27 */     this(1.0D);
/*    */   }
/*    */ 
/*    */   public SpherePortrayal3D(double scale)
/*    */   {
/* 33 */     this(Color.white, scale);
/*    */   }
/*    */ 
/*    */   public SpherePortrayal3D(Color color)
/*    */   {
/* 39 */     this(color, 1.0D);
/*    */   }
/*    */ 
/*    */   public SpherePortrayal3D(Color color, double scale)
/*    */   {
/* 45 */     this(color, scale, 15);
/*    */   }
/*    */ 
/*    */   public SpherePortrayal3D(Color color, double scale, int divisions)
/*    */   {
/* 51 */     this(appearanceForColor(color), true, false, scale, divisions);
/*    */   }
/*    */ 
/*    */   public SpherePortrayal3D(Image image)
/*    */   {
/* 57 */     this(image, 1.0D);
/*    */   }
/*    */ 
/*    */   public SpherePortrayal3D(Image image, double scale)
/*    */   {
/* 63 */     this(image, scale, 15);
/*    */   }
/*    */ 
/*    */   public SpherePortrayal3D(Image image, double scale, int divisions)
/*    */   {
/* 69 */     this(appearanceForImage(image, true), false, true, scale, divisions);
/*    */   }
/*    */ 
/*    */   public SpherePortrayal3D(Appearance appearance, boolean generateNormals, boolean generateTextureCoordinates, double scale)
/*    */   {
/* 75 */     this(appearance, generateNormals, generateTextureCoordinates, scale, 15);
/*    */   }
/*    */ 
/*    */   public SpherePortrayal3D(Appearance appearance, boolean generateNormals, boolean generateTextureCoordinates, double scale, int divisions)
/*    */   {
/* 81 */     this.appearance = appearance;
/* 82 */     setScale(null, scale);
/*    */ 
/* 84 */     Sphere sphere = new Sphere(0.5F, (generateNormals ? 1 : 0) | (generateTextureCoordinates ? 2 : 0), divisions, appearance);
/*    */ 
/* 90 */     setShape3DFlags(sphere.getShape(0));
/* 91 */     this.group = sphere;
/*    */   }
/*    */   protected int numShapes() {
/* 94 */     return 1;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.SpherePortrayal3D
 * JD-Core Version:    0.6.2
 */