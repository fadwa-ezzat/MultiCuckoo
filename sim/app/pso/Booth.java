/*    */ package sim.app.pso;
/*    */ 
/*    */ public class Booth
/*    */   implements Evaluatable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public double calcFitness(double x, double y)
/*    */   {
/* 18 */     return 1000.0D - ((x + 2.0D * y - 7.0D) * (x + 2.0D * y - 7.0D) + (2.0D * x + y - 5.0D) * (2.0D * x + y - 5.0D));
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pso.Booth
 * JD-Core Version:    0.6.2
 */