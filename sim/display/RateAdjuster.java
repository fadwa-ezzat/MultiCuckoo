/*    */ package sim.display;
/*    */ 
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ 
/*    */ public class RateAdjuster
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   long initialTime;
/*    */   long totalTics;
/* 25 */   boolean started = false;
/*    */   double rate;
/*    */   static final int MAX_POLL_ITERATIONS = 3;
/*    */ 
/*    */   public RateAdjuster(double targetRate)
/*    */   {
/* 31 */     this.rate = targetRate;
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 36 */     if (!this.started)
/*    */     {
/* 38 */       this.initialTime = System.currentTimeMillis();
/* 39 */       this.started = true;
/*    */     }
/*    */     else
/*    */     {
/* 43 */       long currentTime = System.currentTimeMillis();
/* 44 */       long time = currentTime - this.initialTime;
/* 45 */       this.totalTics += 1L;
/*    */ 
/* 47 */       long expectedTime = ()(this.totalTics / this.rate * 1000.0D);
/* 48 */       int count = 0;
/* 49 */       while ((time < expectedTime) && (count++ < 3))
/*    */       {
/*    */         try
/*    */         {
/* 53 */           Thread.currentThread(); Thread.sleep(expectedTime - time);
/*    */         } catch (InterruptedException e) {
/*    */         }
/* 56 */         currentTime = System.currentTimeMillis();
/* 57 */         time = currentTime - this.initialTime;
/*    */       }
/*    */ 
/* 61 */       this.initialTime = currentTime;
/* 62 */       this.totalTics = 0L;
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.display.RateAdjuster
 * JD-Core Version:    0.6.2
 */