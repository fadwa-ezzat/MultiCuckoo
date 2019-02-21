/*    */ package sim.portrayal3d.simple;
/*    */ 
/*    */ import com.sun.j3d.utils.geometry.Cylinder;
/*    */ import java.awt.Color;
/*    */ import java.awt.Image;
/*    */ import javax.media.j3d.Appearance;
/*    */ 
/*    */ public class CylinderPortrayal3D extends PrimitivePortrayal3D
/*    */ {
/*    */   public CylinderPortrayal3D()
/*    */   {
/* 25 */     this(1.0D);
/*    */   }
/*    */ 
/*    */   public CylinderPortrayal3D(double scale)
/*    */   {
/* 31 */     this(Color.white, scale);
/*    */   }
/*    */ 
/*    */   public CylinderPortrayal3D(Color color)
/*    */   {
/* 37 */     this(color, 1.0D);
/*    */   }
/*    */ 
/*    */   public CylinderPortrayal3D(Color color, double scale)
/*    */   {
/* 43 */     this(appearanceForColor(color), true, false, scale);
/*    */   }
/*    */ 
/*    */   public CylinderPortrayal3D(Image image)
/*    */   {
/* 49 */     this(image, 1.0D);
/*    */   }
/*    */ 
/*    */   public CylinderPortrayal3D(Image image, double scale)
/*    */   {
/* 55 */     this(appearanceForImage(image, true), false, true, scale);
/*    */   }
/*    */ 
/*    */   public CylinderPortrayal3D(Appearance appearance, boolean generateNormals, boolean generateTextureCoordinates, double scale)
/*    */   {
/* 62 */     this.appearance = appearance;
/* 63 */     setScale(null, scale);
/*    */ 
/* 65 */     Cylinder cylinder = new Cylinder(0.5F, 1.0F, (generateNormals ? 1 : 0) | (generateTextureCoordinates ? 2 : 0), appearance);
/*    */ 
/* 70 */     setShape3DFlags(cylinder.getShape(0));
/* 71 */     setShape3DFlags(cylinder.getShape(1));
/* 72 */     setShape3DFlags(cylinder.getShape(2));
/*    */ 
/* 74 */     this.group = cylinder;
/*    */   }
/*    */   protected int numShapes() {
/* 77 */     return 3;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.CylinderPortrayal3D
 * JD-Core Version:    0.6.2
 */