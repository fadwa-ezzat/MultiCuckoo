/*    */ package sim.app.pso;
/*    */ 
/*    */ public class Griewangk
/*    */   implements Evaluatable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public double calcFitness(double x, double y)
/*    */   {
/* 18 */     return 1000.0D - (1.0D + x * x / 4000.0D + y * y / 4000.0D - Math.cos(x) * Math.cos(y / Math.sqrt(2.0D)));
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pso.Griewangk
 * JD-Core Version:    0.6.2
 */