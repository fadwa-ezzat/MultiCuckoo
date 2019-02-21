/*    */ package sim.app.asteroids;
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
/* 27 */   boolean started = false;
/*    */   double rate;
/*    */ 
/*    */   public RateAdjuster(double targetRate)
/*    */   {
/* 32 */     this.rate = targetRate;
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 37 */     if (!this.started)
/*    */     {
/* 39 */       this.initialTime = System.currentTimeMillis();
/* 40 */       this.started = true;
/*    */     }
/*    */     else
/*    */     {
/* 44 */       long currentTime = System.currentTimeMillis();
/* 45 */       long time = currentTime - this.initialTime;
/* 46 */       this.totalTics += 1L;
/*    */ 
/* 48 */       long expectedTime = ()(this.totalTics / this.rate * 1000.0D);
/* 49 */       if (time < expectedTime)
/*    */       {
/*    */         try {
/* 52 */           Thread.currentThread(); Thread.sleep(expectedTime - time);
/*    */         } catch (InterruptedException e) {
/*    */         }
/*    */       }
/*    */       else {
/* 57 */         this.initialTime = currentTime;
/* 58 */         this.totalTics = 0L;
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.asteroids.RateAdjuster
 * JD-Core Version:    0.6.2
 */