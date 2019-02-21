/*    */ package sim.util.distribution;
/*    */ 
/*    */ public abstract class AbstractDiscreteDistribution extends AbstractDistribution
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public double nextDouble()
/*    */   {
/* 29 */     return nextInt();
/*    */   }
/*    */ 
/*    */   public abstract int nextInt();
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.AbstractDiscreteDistribution
 * JD-Core Version:    0.6.2
 */