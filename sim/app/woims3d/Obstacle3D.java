/*    */ package sim.app.woims3d;
/*    */ 
/*    */ import com.sun.j3d.utils.geometry.Sphere;
/*    */ import javax.media.j3d.Appearance;
/*    */ import javax.media.j3d.ColoringAttributes;
/*    */ import javax.media.j3d.Material;
/*    */ import javax.media.j3d.TransformGroup;
/*    */ import javax.vecmath.Color3f;
/*    */ import sim.portrayal3d.SimplePortrayal3D;
/*    */ 
/*    */ public class Obstacle3D extends SimplePortrayal3D
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   double diameter;
/* 19 */   protected Color3f obstacleColor = new Color3f(0.7529412F, 1.0F, 0.7529412F);
/*    */ 
/*    */   public Obstacle3D(double diam)
/*    */   {
/* 23 */     this.diameter = diam;
/*    */   }
/*    */ 
/*    */   public TransformGroup getModel(Object obj, TransformGroup j3dModel)
/*    */   {
/* 28 */     if (j3dModel == null)
/*    */     {
/* 30 */       j3dModel = new TransformGroup();
/* 31 */       Sphere s = new Sphere((float)this.diameter / 2.0F);
/* 32 */       Appearance appearance = new Appearance();
/* 33 */       appearance.setColoringAttributes(new ColoringAttributes(this.obstacleColor, 3));
/* 34 */       Material m = new Material();
/* 35 */       m.setAmbientColor(this.obstacleColor);
/* 36 */       m.setEmissiveColor(0.0F, 0.0F, 0.0F);
/* 37 */       m.setDiffuseColor(this.obstacleColor);
/* 38 */       m.setSpecularColor(1.0F, 1.0F, 1.0F);
/* 39 */       m.setShininess(128.0F);
/* 40 */       appearance.setMaterial(m);
/*    */ 
/* 42 */       s.setAppearance(appearance);
/* 43 */       j3dModel.addChild(s);
/* 44 */       clearPickableFlags(j3dModel);
/*    */     }
/* 46 */     return j3dModel;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.woims3d.Obstacle3D
 * JD-Core Version:    0.6.2
 */