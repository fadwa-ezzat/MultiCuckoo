/*    */ package sim.engine;
/*    */ 
/*    */ public class MultiStep
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   int current;
/*    */   final boolean countdown;
/*    */   final int n;
/*    */   final Steppable step;
/*    */ 
/*    */   public MultiStep(Steppable step, int n, boolean countdown)
/*    */   {
/* 37 */     if (n < 0) n = 0;
/* 38 */     this.n = n;
/* 39 */     this.step = step;
/* 40 */     this.countdown = countdown;
/* 41 */     this.current = n;
/*    */   }
/*    */ 
/*    */   public synchronized void resetCountdown()
/*    */   {
/* 47 */     this.current = this.n;
/*    */   }
/*    */ 
/*    */   public synchronized void resetCountdown(int val)
/*    */   {
/* 54 */     if ((val <= this.n) && (val > 0))
/* 55 */       this.current = val;
/*    */   }
/*    */ 
/*    */   synchronized boolean go()
/*    */   {
/* 62 */     if (--this.current == 0) { this.current = this.n; return true; }
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 68 */     if (this.n != 0)
/* 69 */       if (this.countdown)
/*    */       {
/* 71 */         if (go()) {
/* 72 */           this.step.step(state);
/*    */         }
/*    */       }
/*    */       else
/* 76 */         for (int x = 0; x < this.n; x++)
/* 77 */           this.step.step(state);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.engine.MultiStep
 * JD-Core Version:    0.6.2
 */