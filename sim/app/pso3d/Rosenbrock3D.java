/*    */ package sim.app.pso3d;
/*    */ 
/*    */ public class Rosenbrock3D
/*    */   implements Evaluatable3D
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public double calcFitness(double x, double y, double z)
/*    */   {
/* 18 */     return 1000.0D - (100.0D * ((x * x - y) * (x * x - y) + (1.0D - x) * (1.0D - x)) + ((y * y - z) * (y * y - z) + (1.0D - y) * (1.0D - y)));
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pso3d.Rosenbrock3D
 * JD-Core Version:    0.6.2
 */