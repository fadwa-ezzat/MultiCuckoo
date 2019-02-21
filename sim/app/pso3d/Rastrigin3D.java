/*    */ package sim.app.pso3d;
/*    */ 
/*    */ public class Rastrigin3D
/*    */   implements Evaluatable3D
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public double calcFitness(double x, double y, double z)
/*    */   {
/* 18 */     return 1000.0D - (30.0D + x * x - 10.0D * Math.cos(6.283185307179586D * x) + y * y - 10.0D * Math.cos(6.283185307179586D * y) + z * z - 10.0D * Math.cos(6.283185307179586D * z));
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pso3d.Rastrigin3D
 * JD-Core Version:    0.6.2
 */