/*    */ package sim.portrayal3d.network;
/*    */ 
/*    */ import com.sun.j3d.utils.geometry.Cone;
/*    */ import java.awt.Color;
/*    */ import java.awt.Font;
/*    */ import java.awt.Image;
/*    */ import javax.media.j3d.Appearance;
/*    */ import javax.media.j3d.Node;
/*    */ import sim.portrayal3d.simple.PrimitivePortrayal3D;
/*    */ 
/*    */ public class ConeEdgePortrayal3D extends PrimitiveEdgePortrayal3D
/*    */ {
/*    */   /** @deprecated */
/*    */   public ConeEdgePortrayal3D(double radius)
/*    */   {
/* 24 */     this(null, Color.white, null, radius);
/*    */   }
/*    */ 
/*    */   /** @deprecated */
/*    */   public ConeEdgePortrayal3D(Color labelColor)
/*    */   {
/* 30 */     this(null, labelColor, null, 0.5D);
/*    */   }
/*    */ 
/*    */   public ConeEdgePortrayal3D()
/*    */   {
/* 35 */     this(null, Color.white, null, 0.5D);
/*    */   }
/*    */ 
/*    */   public ConeEdgePortrayal3D(Appearance appearance, Color labelColor)
/*    */   {
/* 40 */     this(appearance, labelColor, null, 0.5D);
/*    */   }
/*    */ 
/*    */   public ConeEdgePortrayal3D(Color color, Color labelColor)
/*    */   {
/* 45 */     this(appearanceForColor(color), labelColor, null, 0.5D);
/*    */   }
/*    */ 
/*    */   public ConeEdgePortrayal3D(Image image, Color labelColor)
/*    */   {
/* 51 */     this(appearanceForImage(image, true), labelColor, null, 0.5D);
/*    */   }
/*    */ 
/*    */   public ConeEdgePortrayal3D(Appearance appearance, Color labelColor, Font labelFont, double radius)
/*    */   {
/* 56 */     super(new Cone((float)radius, 2.0F), appearance, labelColor, labelFont);
/*    */   }
/*    */ 
/*    */   protected void init(Node edgeModel)
/*    */   {
/* 61 */     super.init(edgeModel);
/* 62 */     Cone c = (Cone)edgeModel;
/* 63 */     PrimitivePortrayal3D.setShape3DFlags(c.getShape(0));
/* 64 */     PrimitivePortrayal3D.setShape3DFlags(c.getShape(1));
/*    */   }
/*    */ 
/*    */   protected int numShapes() {
/* 68 */     return 2;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.network.ConeEdgePortrayal3D
 * JD-Core Version:    0.6.2
 */