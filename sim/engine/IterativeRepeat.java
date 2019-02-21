/*     */ package sim.engine;
/*     */ 
/*     */ import sim.util.LocationLog;
/*     */ 
/*     */ class IterativeRepeat
/*     */   implements Steppable, Stoppable
/*     */ {
/*     */   double interval;
/*     */   Steppable step;
/*     */   Schedule.Key key;
/*     */ 
/*     */   public IterativeRepeat(Steppable step, double interval, Schedule.Key key)
/*     */   {
/* 774 */     if (interval < 0.0D) {
/* 775 */       throw new IllegalArgumentException("For the Steppable...\n\n" + step + "\n\n...the interval provided (" + interval + ") is less than zero");
/*     */     }
/* 777 */     if (interval != interval) {
/* 778 */       throw new IllegalArgumentException("For the Steppable...\n\n" + step + "\n\n...the interval provided (" + interval + ") is NaN");
/*     */     }
/*     */ 
/* 781 */     this.step = step;
/* 782 */     this.interval = interval;
/* 783 */     this.key = key;
/*     */   }
/*     */ 
/*     */   public synchronized void step(SimState state)
/*     */   {
/* 788 */     if (this.step != null)
/*     */     {
/*     */       try
/*     */       {
/* 793 */         this.key.time += this.interval;
/* 794 */         if (this.key.time < (1.0D / 0.0D))
/* 795 */           state.schedule.scheduleOnce(this.key, this);
/*     */       }
/*     */       catch (IllegalArgumentException e)
/*     */       {
/* 799 */         e.printStackTrace();
/*     */       }
/* 801 */       assert (LocationLog.set(this.step));
/* 802 */       this.step.step(state);
/* 803 */       assert (LocationLog.clear());
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void stop()
/*     */   {
/* 809 */     this.step = null;
/*     */   }
/*     */   public String toString() {
/* 812 */     return "Schedule.IterativeRepeat[" + this.step + "]";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.engine.IterativeRepeat
 * JD-Core Version:    0.6.2
 */