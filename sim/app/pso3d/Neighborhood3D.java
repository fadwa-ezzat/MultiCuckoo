/*    */ package sim.app.pso3d;
/*    */ 
/*    */ import sim.util.MutableDouble3D;
/*    */ 
/*    */ public class Neighborhood3D
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 18 */   public double bestVal = 0.0D;
/* 19 */   MutableDouble3D bestPosition = new MutableDouble3D();
/*    */   public PSO3D po;
/*    */ 
/*    */   public Neighborhood3D(PSO3D p)
/*    */   {
/* 25 */     this.po = p;
/*    */   }
/*    */ 
/*    */   public void updateBest(double currVal, double currX, double currY, double currZ)
/*    */   {
/* 30 */     if (currVal > this.bestVal)
/*    */     {
/* 32 */       this.bestVal = currVal;
/* 33 */       this.bestPosition.setTo(currX, currY, currZ);
/*    */ 
/* 35 */       this.po.updateBest(currVal, currX, currY, currZ);
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pso3d.Neighborhood3D
 * JD-Core Version:    0.6.2
 */