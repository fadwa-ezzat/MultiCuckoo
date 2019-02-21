/*    */ package sim.app.pso;
/*    */ 
/*    */ public class Rosenbrock
/*    */   implements Evaluatable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public double calcFitness(double x, double y)
/*    */   {
/* 18 */     double expr1 = x * x - y;
/* 19 */     double expr2 = 1.0D - x;
/* 20 */     return 1000.0D - (100.0D * expr1 * expr1 + expr2 * expr2);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pso.Rosenbrock
 * JD-Core Version:    0.6.2
 */