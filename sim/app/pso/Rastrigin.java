/*    */ package sim.app.pso;
/*    */ 
/*    */ public class Rastrigin
/*    */   implements Evaluatable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public double calcFitness(double x, double y)
/*    */   {
/* 18 */     return 1000.0D - (20.0D + x * x - 10.0D * Math.cos(6.283185307179586D * x) + y * y - 10.0D * Math.cos(6.283185307179586D * y));
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pso.Rastrigin
 * JD-Core Version:    0.6.2
 */