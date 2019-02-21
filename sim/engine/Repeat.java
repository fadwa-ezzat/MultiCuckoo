/*     */ package sim.engine;
/*     */ 
/*     */ import sim.util.LocationLog;
/*     */ 
/*     */ public abstract class Repeat
/*     */   implements Steppable, Stoppable
/*     */ {
/*     */   Steppable step;
/* 143 */   Schedule.Key key = null;
/*     */   int ordering;
/*     */ 
/*     */   public Repeat(Steppable step, int ordering)
/*     */   {
/* 148 */     this.step = step;
/* 149 */     this.ordering = ordering;
/*     */   }
/*     */ 
/*     */   protected abstract double getNextTime(SimState paramSimState, double paramDouble);
/*     */ 
/*     */   public synchronized void setOrdering(int val)
/*     */   {
/* 156 */     this.ordering = val;
/*     */   }
/*     */ 
/*     */   public synchronized int getOrdering()
/*     */   {
/* 161 */     return this.ordering;
/*     */   }
/*     */ 
/*     */   public synchronized void step(SimState state)
/*     */   {
/* 166 */     if (this.step != null)
/*     */     {
/*     */       try
/*     */       {
/* 170 */         if (this.key == null)
/* 171 */           this.key = new Schedule.Key(state.schedule.getTime(), this.ordering);
/* 172 */         this.key.time = getNextTime(state, this.key.time);
/* 173 */         if (this.key.time < (1.0D / 0.0D))
/* 174 */           state.schedule.scheduleOnce(this.key, this);
/* 175 */         else return;
/*     */       }
/*     */       catch (IllegalArgumentException e)
/*     */       {
/* 179 */         e.printStackTrace();
/*     */       }
/* 181 */       assert (LocationLog.set(this.step));
/* 182 */       this.step.step(state);
/* 183 */       assert (LocationLog.clear());
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void stop()
/*     */   {
/* 189 */     this.step = null;
/*     */   }
/*     */   public String toString() {
/* 192 */     return "Repeat[" + this.step + "]";
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.engine.Repeat
 * JD-Core Version:    0.6.2
 */