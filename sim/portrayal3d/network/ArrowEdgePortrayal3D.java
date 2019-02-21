/*     */ package sim.portrayal3d.network;
/*     */ 
/*     */ import com.sun.j3d.utils.geometry.Cone;
/*     */ import com.sun.j3d.utils.geometry.Cylinder;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Image;
/*     */ import javax.media.j3d.Appearance;
/*     */ import javax.media.j3d.Node;
/*     */ import javax.media.j3d.Shape3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import sim.portrayal3d.simple.Arrow;
/*     */ import sim.portrayal3d.simple.PrimitivePortrayal3D;
/*     */ import sim.util.Double3D;
/*     */ 
/*     */ public class ArrowEdgePortrayal3D extends PrimitiveEdgePortrayal3D
/*     */ {
/*  27 */   static Double3D dummyFrom = new Double3D(0.0D, -1.0D, 0.0D);
/*  28 */   static Double3D dummyTo = new Double3D(0.0D, 1.0D, 0.0D);
/*     */ 
/*     */   public ArrowEdgePortrayal3D()
/*     */   {
/*  32 */     this(null, Color.white, null, 0.5D);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public ArrowEdgePortrayal3D(double radius)
/*     */   {
/*  38 */     this(null, Color.white, null, radius);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public ArrowEdgePortrayal3D(double radius, Appearance ap)
/*     */   {
/*  44 */     this(ap, Color.white, null, radius);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public ArrowEdgePortrayal3D(Color labelColor)
/*     */   {
/*  50 */     this(null, labelColor, null, 0.5D);
/*     */   }
/*     */ 
/*     */   public ArrowEdgePortrayal3D(Appearance appearance, Color labelColor)
/*     */   {
/*  55 */     this(appearance, labelColor, null, 0.5D);
/*     */   }
/*     */ 
/*     */   public ArrowEdgePortrayal3D(Color color, Color labelColor)
/*     */   {
/*  60 */     this(appearanceForColor(color), labelColor, null, 0.5D);
/*     */   }
/*     */ 
/*     */   public ArrowEdgePortrayal3D(Image image, Color labelColor)
/*     */   {
/*  66 */     this(appearanceForImage(image, true), labelColor, null, 0.5D);
/*     */   }
/*     */ 
/*     */   public ArrowEdgePortrayal3D(Appearance appearance, Color labelColor, Font labelFont, double radius)
/*     */   {
/*  71 */     super(new Arrow(radius, dummyFrom, dummyTo, null, null, appearance), appearance, labelColor, labelFont);
/*     */   }
/*     */ 
/*     */   protected int numShapes()
/*     */   {
/*  77 */     return 5;
/*     */   }
/*     */ 
/*     */   protected Shape3D getShape(TransformGroup j3dModel, int shapeIndex)
/*     */   {
/*  97 */     TransformGroup endPointTG = (TransformGroup)j3dModel.getChild(0);
/*  98 */     TransformGroup edgeModelClone = (TransformGroup)endPointTG.getChild(0);
/*  99 */     int coneOffset = 3;
/*     */ 
/* 101 */     if (shapeIndex < coneOffset)
/*     */     {
/* 103 */       TransformGroup arrowBody = (TransformGroup)edgeModelClone.getChild(0);
/* 104 */       Cylinder c = (Cylinder)arrowBody.getChild(0);
/* 105 */       return c.getShape(shapeIndex);
/*     */     }
/* 107 */     TransformGroup arrowHead = (TransformGroup)edgeModelClone.getChild(1);
/* 108 */     Cone c = (Cone)arrowHead.getChild(0);
/* 109 */     return c.getShape(shapeIndex - coneOffset);
/*     */   }
/*     */ 
/*     */   protected void init(Node edgeModel)
/*     */   {
/* 114 */     super.init(edgeModel);
/* 115 */     Arrow arrow = (Arrow)edgeModel;
/* 116 */     arrow.setCapability(12);
/*     */ 
/* 118 */     ((TransformGroup)arrow.getChild(0)).setCapability(12);
/*     */ 
/* 120 */     ((TransformGroup)arrow.getChild(1)).setCapability(12);
/*     */ 
/* 122 */     Cylinder body = arrow.getArrowTail();
/* 123 */     PrimitivePortrayal3D.setShape3DFlags(body.getShape(0));
/* 124 */     PrimitivePortrayal3D.setShape3DFlags(body.getShape(1));
/* 125 */     PrimitivePortrayal3D.setShape3DFlags(body.getShape(2));
/* 126 */     Cone head = arrow.getArrowHead();
/* 127 */     PrimitivePortrayal3D.setShape3DFlags(head.getShape(0));
/* 128 */     PrimitivePortrayal3D.setShape3DFlags(head.getShape(1));
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.network.ArrowEdgePortrayal3D
 * JD-Core Version:    0.6.2
 */