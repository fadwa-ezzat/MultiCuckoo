/*    */ package sim.portrayal3d.simple;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.media.j3d.Appearance;
/*    */ import javax.media.j3d.LineStripArray;
/*    */ import javax.media.j3d.Shape3D;
/*    */ import javax.media.j3d.TransformGroup;
/*    */ import sim.portrayal3d.SimplePortrayal3D;
/*    */ 
/*    */ public class WireFrameBoxPortrayal3D extends SimplePortrayal3D
/*    */ {
/*    */   Appearance appearance;
/* 35 */   static final float[] verts = { 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F };
/*    */ 
/* 45 */   float[] scaledVerts = new float[verts.length];
/*    */ 
/*    */   public WireFrameBoxPortrayal3D() {
/* 48 */     this(-0.5D, -0.5D, -0.5D, 0.5D, 0.5D, 0.5D);
/*    */   }
/*    */ 
/*    */   public WireFrameBoxPortrayal3D(double x, double y, double z, double x2, double y2, double z2)
/*    */   {
/* 53 */     this(x, y, z, x2, y2, z2, Color.white);
/*    */   }
/*    */ 
/*    */   public WireFrameBoxPortrayal3D(double x, double y, double z, double x2, double y2, double z2, Color color)
/*    */   {
/* 59 */     this(x, y, z, x2, y2, z2, appearanceForColor(color));
/*    */   }
/*    */ 
/*    */   public WireFrameBoxPortrayal3D(double x, double y, double z, double x2, double y2, double z2, Appearance appearance)
/*    */   {
/* 65 */     this.appearance = appearance;
/*    */ 
/* 67 */     for (int i = 0; i < verts.length / 3; i++)
/*    */     {
/* 69 */       this.scaledVerts[(3 * i)] = (verts[(3 * i)] * (float)(x2 - x) + (float)x);
/* 70 */       this.scaledVerts[(3 * i + 1)] = (verts[(3 * i + 1)] * (float)(y2 - y) + (float)y);
/* 71 */       this.scaledVerts[(3 * i + 2)] = (verts[(3 * i + 2)] * (float)(z2 - z) + (float)z);
/*    */     }
/*    */   }
/*    */ 
/*    */   public TransformGroup getModel(Object obj, TransformGroup tg)
/*    */   {
/* 77 */     if (tg == null)
/*    */     {
/* 79 */       TransformGroup modelTG = new TransformGroup();
/*    */ 
/* 81 */       LineStripArray box = new LineStripArray(30, 1, new int[] { 5, 5, 5, 5, 5, 5 });
/* 82 */       box.setCoordinates(0, this.scaledVerts);
/*    */ 
/* 84 */       Shape3D s = new Shape3D(box, this.appearance);
/* 85 */       modelTG.addChild(s);
/* 86 */       setPickableFlags(s);
/* 87 */       return modelTG;
/*    */     }
/* 89 */     return tg;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.WireFrameBoxPortrayal3D
 * JD-Core Version:    0.6.2
 */