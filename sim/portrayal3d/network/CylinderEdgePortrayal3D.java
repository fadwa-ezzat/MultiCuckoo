/*    */ package sim.portrayal3d.network;
/*    */ 
/*    */ import com.sun.j3d.utils.geometry.Cylinder;
/*    */ import java.awt.Color;
/*    */ import java.awt.Font;
/*    */ import java.awt.Image;
/*    */ import javax.media.j3d.Appearance;
/*    */ import javax.media.j3d.Node;
/*    */ import sim.portrayal3d.simple.PrimitivePortrayal3D;
/*    */ 
/*    */ public class CylinderEdgePortrayal3D extends PrimitiveEdgePortrayal3D
/*    */ {
/*    */   public CylinderEdgePortrayal3D(double radius)
/*    */   {
/* 23 */     this(null, Color.white, null, radius);
/*    */   }
/*    */ 
/*    */   /** @deprecated */
/*    */   public CylinderEdgePortrayal3D(Color labelColor)
/*    */   {
/* 29 */     this(null, labelColor, null, 0.5D);
/*    */   }
/*    */ 
/*    */   /** @deprecated */
/*    */   public CylinderEdgePortrayal3D(double radius, Color labelColor)
/*    */   {
/* 35 */     this(null, labelColor, null, radius);
/*    */   }
/*    */ 
/*    */   public CylinderEdgePortrayal3D()
/*    */   {
/* 40 */     this(null, Color.white, null, 0.5D);
/*    */   }
/*    */ 
/*    */   public CylinderEdgePortrayal3D(Appearance appearance, Color labelColor)
/*    */   {
/* 45 */     this(appearance, labelColor, null, 0.5D);
/*    */   }
/*    */ 
/*    */   public CylinderEdgePortrayal3D(Color color, Color labelColor)
/*    */   {
/* 50 */     this(appearanceForColor(color), labelColor, null, 0.5D);
/*    */   }
/*    */ 
/*    */   public CylinderEdgePortrayal3D(Image image, Color labelColor)
/*    */   {
/* 56 */     this(appearanceForImage(image, true), labelColor, null, 0.5D);
/*    */   }
/*    */ 
/*    */   public CylinderEdgePortrayal3D(Appearance appearance, Color labelColor, Font labelFont, double radius)
/*    */   {
/* 61 */     super(new Cylinder((float)radius, 2.0F), appearance, labelColor, labelFont);
/*    */   }
/*    */ 
/*    */   protected void init(Node edgeModel)
/*    */   {
/* 67 */     super.init(edgeModel);
/* 68 */     Cylinder c = (Cylinder)edgeModel;
/* 69 */     PrimitivePortrayal3D.setShape3DFlags(c.getShape(0));
/* 70 */     PrimitivePortrayal3D.setShape3DFlags(c.getShape(1));
/* 71 */     PrimitivePortrayal3D.setShape3DFlags(c.getShape(2));
/*    */   }
/*    */ 
/*    */   protected int numShapes() {
/* 75 */     return 3;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.network.CylinderEdgePortrayal3D
 * JD-Core Version:    0.6.2
 */