/*    */ package sim.app.balls3d;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.media.j3d.TransformGroup;
/*    */ import sim.portrayal3d.simple.SpherePortrayal3D;
/*    */ 
/*    */ public class BallPortrayal extends SpherePortrayal3D
/*    */ {
/* 16 */   static final Color obColor = Color.green;
/* 17 */   static final Color colColor = Color.red;
/*    */   float multiply;
/*    */ 
/*    */   public BallPortrayal(double diam)
/*    */   {
/* 22 */     this.multiply = ((float)diam);
/*    */   }
/*    */ 
/*    */   public TransformGroup getModel(Object obj, TransformGroup j3dModel)
/*    */   {
/* 55 */     if ((j3dModel == null) || (((Ball)obj).oldCollision != ((Ball)obj).collision))
/*    */     {
/* 57 */       ((Ball)obj).oldCollision = ((Ball)obj).collision;
/*    */ 
/* 59 */       if (((Ball)obj).collision) {
/* 60 */         setAppearance(j3dModel, appearanceForColors(colColor, null, colColor, null, 1.0D, 1.0D));
/*    */       }
/*    */       else
/*    */       {
/* 67 */         setAppearance(j3dModel, appearanceForColors(obColor, null, obColor, null, 1.0D, 1.0D));
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 76 */     if ((j3dModel == null) || (((Ball)obj).oldMass != ((Ball)obj).mass))
/*    */     {
/* 78 */       ((Ball)obj).oldMass = ((Ball)obj).mass;
/*    */ 
/* 80 */       setScale(j3dModel, this.multiply * (float)((Ball)obj).diameter / 2.0F);
/*    */     }
/*    */ 
/* 83 */     return super.getModel(obj, j3dModel);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.balls3d.BallPortrayal
 * JD-Core Version:    0.6.2
 */