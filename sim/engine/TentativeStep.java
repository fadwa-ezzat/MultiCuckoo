/*    */ package sim.engine;
/*    */ 
/*    */ public class TentativeStep
/*    */   implements Steppable, Stoppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public Steppable step;
/*    */ 
/*    */   public TentativeStep(Steppable step)
/*    */   {
/* 43 */     this.step = step;
/*    */   }
/*    */ 
/*    */   public synchronized void step(SimState state)
/*    */   {
/* 48 */     if (this.step != null)
/* 49 */       this.step.step(state);
/*    */   }
/*    */ 
/*    */   public synchronized void stop()
/*    */   {
/* 54 */     this.step = null;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.engine.TentativeStep
 * JD-Core Version:    0.6.2
 */