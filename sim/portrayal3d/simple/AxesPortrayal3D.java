/*    */ package sim.portrayal3d.simple;
/*    */ 
/*    */ import javax.media.j3d.Group;
/*    */ import javax.media.j3d.TransformGroup;
/*    */ import sim.portrayal3d.SimplePortrayal3D;
/*    */ import sim.util.Double3D;
/*    */ 
/*    */ public class AxesPortrayal3D extends SimplePortrayal3D
/*    */ {
/*    */   double arrowRadius;
/*    */   boolean mLetters;
/*    */ 
/*    */   public AxesPortrayal3D(double arrowRadius, boolean letters)
/*    */   {
/* 32 */     this.arrowRadius = arrowRadius;
/* 33 */     this.mLetters = letters;
/*    */   }
/*    */ 
/*    */   void createAxes(Group group, double arrowRadius, boolean letters)
/*    */   {
/* 38 */     float length = 1.1F;
/* 39 */     group.setCapability(14);
/* 40 */     group.addChild(new Arrow(arrowRadius, new Double3D(0.0D, 0.0D, 0.0D), new Double3D(length, 0.0D, 0.0D), letters ? "O" : null, letters ? "X" : null, null));
/*    */ 
/* 46 */     group.addChild(new Arrow(arrowRadius, new Double3D(0.0D, 0.0D, 0.0D), new Double3D(0.0D, length, 0.0D), null, letters ? "Y" : null, null));
/*    */ 
/* 52 */     group.addChild(new Arrow(arrowRadius, new Double3D(0.0D, 0.0D, 0.0D), new Double3D(0.0D, 0.0D, length), null, letters ? "Z" : null, null));
/*    */   }
/*    */ 
/*    */   public TransformGroup getModel(Object obj, TransformGroup prev)
/*    */   {
/* 63 */     if (prev != null)
/* 64 */       return prev;
/* 65 */     TransformGroup tg = new TransformGroup();
/* 66 */     createAxes(tg, this.arrowRadius, this.mLetters);
/* 67 */     clearPickableFlags(tg);
/* 68 */     return tg;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.AxesPortrayal3D
 * JD-Core Version:    0.6.2
 */