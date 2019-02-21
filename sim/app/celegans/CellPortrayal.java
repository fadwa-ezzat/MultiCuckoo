/*    */ package sim.app.celegans;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.media.j3d.TransformGroup;
/*    */ import sim.portrayal3d.simple.SpherePortrayal3D;
/*    */ 
/*    */ public class CellPortrayal extends SpherePortrayal3D
/*    */ {
/* 16 */   static final Color[] fateColors = { null, Color.lightGray, Color.blue, Color.yellow, Color.red, Color.green, Color.orange };
/* 17 */   static final Color[] typeColors = { Color.magenta, Color.pink, Color.cyan, Color.darkGray, new Color(255, 0, 255) };
/*    */   double multiply;
/*    */ 
/*    */   public CellPortrayal(double diam)
/*    */   {
/* 22 */     this.multiply = diam;
/*    */   }
/*    */ 
/*    */   public TransformGroup getModel(Object obj, TransformGroup j3dModel)
/*    */   {
/* 27 */     if (j3dModel == null)
/*    */     {
/* 29 */       Cell cell = (Cell)obj;
/* 30 */       Color color = null;
/* 31 */       if (cell.fate > 0) color = fateColors[cell.fate]; else
/* 32 */         color = typeColors[cell.type];
/* 33 */       setAppearance(j3dModel, appearanceForColors(color, null, color, null, 1.0D, 1.0D));
/*    */ 
/* 41 */       setScale(j3dModel, (float)(this.multiply * ((Cell)obj).radius));
/*    */     }
/* 43 */     return super.getModel(obj, j3dModel);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.celegans.CellPortrayal
 * JD-Core Version:    0.6.2
 */