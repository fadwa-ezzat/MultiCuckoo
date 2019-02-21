/*    */ package sim.app.pso3d;
/*    */ 
/*    */ public class Griewangk3D
/*    */   implements Evaluatable3D
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 16 */   private final double sqrt2 = Math.sqrt(2.0D);
/* 17 */   private final double sqrt3 = Math.sqrt(3.0D);
/*    */ 
/*    */   public double calcFitness(double x, double y, double z)
/*    */   {
/* 21 */     return 1000.0D - (1.0D + x * x / 4000.0D + y * y / 4000.0D + z * z / 4000.0D - Math.cos(x) * Math.cos(y / this.sqrt2) * Math.cos(z / this.sqrt3));
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pso3d.Griewangk3D
 * JD-Core Version:    0.6.2
 */