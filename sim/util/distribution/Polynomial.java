/*    */ package sim.util.distribution;
/*    */ 
/*    */ public class Polynomial extends Constants
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public static double p1evl(double x, double[] coef, int N)
/*    */     throws ArithmeticException
/*    */   {
/* 49 */     double ans = x + coef[0];
/*    */ 
/* 51 */     for (int i = 1; i < N; i++) ans = ans * x + coef[i];
/*    */ 
/* 53 */     return ans;
/*    */   }
/*    */ 
/*    */   public static double polevl(double x, double[] coef, int N)
/*    */     throws ArithmeticException
/*    */   {
/* 75 */     double ans = coef[0];
/*    */ 
/* 77 */     for (int i = 1; i <= N; i++) ans = ans * x + coef[i];
/*    */ 
/* 79 */     return ans;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Polynomial
 * JD-Core Version:    0.6.2
 */