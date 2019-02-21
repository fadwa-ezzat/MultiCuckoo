/*     */ package sim.engine;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.io.Serializable;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Heap;
/*     */ import sim.util.LocationLog;
/*     */ 
/*     */ public class Schedule
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final double EPOCH = 0.0D;
/*     */   public static final double BEFORE_SIMULATION = -1.0D;
/*     */   public static final double AFTER_SIMULATION = (1.0D / 0.0D);
/* 105 */   public static final double EPOCH_PLUS_EPSILON = Double.longBitsToDouble(Double.doubleToRawLongBits(0.0D) + 1L);
/*     */   public static final double MAXIMUM_INTEGER = 9007199254740992.0D;
/* 110 */   boolean shuffling = true;
/*     */ 
/* 113 */   protected Heap queue = createHeap();
/*     */   protected double time;
/*     */   protected long steps;
/* 130 */   protected boolean sealed = false;
/*     */ 
/* 133 */   protected Object lock = new boolean[1];
/*     */ 
/* 282 */   Bag currentSteps = new Bag();
/* 283 */   Bag substeps = new Bag();
/* 284 */   boolean inStep = false;
/*     */ 
/*     */   protected Heap createHeap()
/*     */   {
/* 118 */     return new Heap();
/*     */   }
/*     */ 
/*     */   private void setShuffling(boolean val)
/*     */   {
/* 143 */     synchronized (this.lock)
/*     */     {
/* 145 */       this.shuffling = val;
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isShuffling()
/*     */   {
/* 154 */     synchronized (this.lock)
/*     */     {
/* 156 */       return this.shuffling;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Schedule()
/*     */   {
/* 163 */     this.time = -1.0D;
/* 164 */     this.steps = 0L;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public double time()
/*     */   {
/* 170 */     return getTime();
/*     */   }
/*     */   public double getTime() {
/* 173 */     synchronized (this.lock) { return this.time; }
/*     */   }
/*     */ 
/*     */   public boolean isSealed()
/*     */   {
/* 178 */     synchronized (this.lock) { return this.sealed; }
/*     */ 
/*     */   }
/*     */ 
/*     */   public String getTimestamp(String beforeSimulationString, String afterSimulationString)
/*     */   {
/* 185 */     return getTimestamp(getTime(), beforeSimulationString, afterSimulationString);
/*     */   }
/*     */ 
/*     */   public String getTimestamp(double time, String beforeSimulationString, String afterSimulationString)
/*     */   {
/* 194 */     double _time = getTime();
/* 195 */     if (_time < 0.0D) return beforeSimulationString;
/* 196 */     if (_time >= (1.0D / 0.0D)) return afterSimulationString;
/* 197 */     if (_time == ()_time) return Long.toString(()_time);
/* 198 */     return Double.toString(_time);
/*     */   }
/*     */ 
/*     */   public long getSteps() {
/* 202 */     synchronized (this.lock) { return this.steps; }
/*     */ 
/*     */   }
/*     */ 
/*     */   private void pushToAfterSimulation()
/*     */   {
/* 209 */     synchronized (this.lock)
/*     */     {
/* 211 */       this.time = (1.0D / 0.0D);
/* 212 */       this.queue = createHeap();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 222 */     synchronized (this.lock)
/*     */     {
/* 224 */       this.queue = createHeap();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void seal()
/*     */   {
/* 233 */     synchronized (this.lock)
/*     */     {
/* 235 */       this.sealed = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 243 */     synchronized (this.lock)
/*     */     {
/* 245 */       this.time = -1.0D;
/* 246 */       this.steps = 0L;
/* 247 */       this.queue = createHeap();
/* 248 */       this.sealed = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean scheduleComplete()
/*     */   {
/* 255 */     synchronized (this.lock)
/*     */     {
/* 257 */       return this.queue.isEmpty();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void merge(Schedule other)
/*     */   {
/* 268 */     if ((this.inStep) || (other.inStep))
/* 269 */       throw new RuntimeException("May not merge with another schedule while inside a step method.");
/* 270 */     if ((this.sealed) || (other.sealed))
/* 271 */       throw new RuntimeException("May not merge with a sealed schedule.");
/* 272 */     if (!other.queue.isEmpty())
/*     */     {
/* 274 */       double minKey = ((Key)other.queue.getMinKey()).getTime();
/* 275 */       if (minKey <= getTime()) {
/* 276 */         throw new RuntimeException("May not merge with a schedule which has Steppables scheduled for an earlier time than my current time value.");
/*     */       }
/*     */     }
/* 279 */     this.queue = this.queue.merge(other.queue);
/*     */   }
/*     */ 
/*     */   public synchronized boolean step(SimState state)
/*     */   {
/* 290 */     if (this.inStep)
/*     */     {
/* 292 */       throw new RuntimeException("Schedule.step() is not reentrant, yet is being called recursively.");
/*     */     }
/*     */ 
/* 295 */     this.inStep = true;
/* 296 */     Bag currentSteps = this.currentSteps;
/* 297 */     MersenneTwisterFast random = state.random;
/*     */ 
/* 299 */     int topSubstep = 0;
/*     */ 
/* 302 */     synchronized (this.lock)
/*     */     {
/* 304 */       if ((this.time == (1.0D / 0.0D)) || (this.queue.isEmpty())) {
/* 305 */         this.time = (1.0D / 0.0D); this.inStep = false; return false;
/*     */       }
/*     */ 
/* 308 */       this.time = ((Key)this.queue.getMinKey()).time;
/*     */ 
/* 310 */       boolean shuffling = this.shuffling;
/*     */       while (true)
/*     */       {
/* 321 */         this.queue.extractMin(this.substeps);
/*     */ 
/* 324 */         if (this.substeps.numObjs > 1)
/*     */         {
/* 326 */           if (shuffling) this.substeps.shuffle(random); else {
/* 327 */             this.substeps.reverse();
/*     */           }
/*     */         }
/*     */ 
/* 331 */         if (topSubstep < this.substeps.numObjs) topSubstep = this.substeps.numObjs;
/* 332 */         currentSteps.addAll(this.substeps);
/* 333 */         this.substeps.numObjs = 0;
/*     */ 
/* 336 */         Key currentKey = (Key)this.queue.getMinKey();
/* 337 */         if ((currentKey == null) || (currentKey.time != this.time)) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 342 */     this.substeps.numObjs = topSubstep;
/* 343 */     this.substeps.clear();
/*     */ 
/* 346 */     int len = currentSteps.numObjs;
/* 347 */     Object[] objs = currentSteps.objs;
/*     */     try
/*     */     {
/* 350 */       for (int x = 0; x < len; x++)
/*     */       {
/* 352 */         assert (LocationLog.set((Steppable)objs[x]));
/* 353 */         ((Steppable)objs[x]).step(state);
/* 354 */         assert (LocationLog.clear());
/* 355 */         objs[x] = null;
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/* 361 */       currentSteps.numObjs = 0;
/*     */ 
/* 363 */       synchronized (this.lock) { this.steps += 1L; }
/* 364 */       this.inStep = false;
/*     */     }
/* 366 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean scheduleOnce(Steppable event)
/*     */   {
/* 379 */     synchronized (this.lock)
/*     */     {
/* 381 */       return _scheduleOnce(new Key(this.time + 1.0D, 0), event);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean scheduleOnceIn(double delta, Steppable event)
/*     */   {
/* 395 */     synchronized (this.lock)
/*     */     {
/* 397 */       return _scheduleOnce(new Key(this.time + delta, 0), event);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean scheduleOnce(Steppable event, int ordering)
/*     */   {
/* 411 */     synchronized (this.lock)
/*     */     {
/* 413 */       return _scheduleOnce(new Key(this.time + 1.0D, ordering), event);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean scheduleOnceIn(double delta, Steppable event, int ordering)
/*     */   {
/* 427 */     synchronized (this.lock)
/*     */     {
/* 429 */       return _scheduleOnce(new Key(this.time + delta, ordering), event);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean scheduleOnce(double time, Steppable event)
/*     */   {
/* 444 */     synchronized (this.lock)
/*     */     {
/* 446 */       return _scheduleOnce(new Key(time, 0), event);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean scheduleOnce(double time, int ordering, Steppable event)
/*     */   {
/* 461 */     synchronized (this.lock)
/*     */     {
/* 463 */       return _scheduleOnce(new Key(time, ordering), event);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean scheduleOnce(Key key, Steppable event)
/*     */   {
/* 474 */     synchronized (this.lock)
/*     */     {
/* 476 */       return _scheduleOnce(key, event);
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean _scheduleOnce(Key key, Steppable event)
/*     */   {
/* 489 */     double time = this.time;
/* 490 */     double t = key.time;
/*     */ 
/* 493 */     if ((t == time) && (t != (1.0D / 0.0D)))
/*     */     {
/* 495 */       t = key.time = Double.longBitsToDouble(Double.doubleToRawLongBits(t) + 1L);
/*     */     }
/* 497 */     if ((this.sealed) || (t >= (1.0D / 0.0D)))
/*     */     {
/* 499 */       return false;
/*     */     }
/* 501 */     if (t < 0.0D) {
/* 502 */       throw new IllegalArgumentException("For the Steppable...\n\n" + event + "\n\n...the time provided (" + t + ") is < EPOCH (" + 0.0D + ")");
/*     */     }
/* 504 */     if (t != t) {
/* 505 */       throw new IllegalArgumentException("For the Steppable...\n\n" + event + "\n\n...the time provided (" + t + ") is NaN");
/*     */     }
/* 507 */     if (t < time) {
/* 508 */       throw new IllegalArgumentException("For the Steppable...\n\n" + event + "\n\n...the time provided (" + t + ") is less than the current time (" + time + ")");
/*     */     }
/* 510 */     if (event == null) {
/* 511 */       throw new IllegalArgumentException("The provided Steppable is null");
/*     */     }
/* 513 */     this.queue.add(event, key);
/* 514 */     return true;
/*     */   }
/*     */ 
/*     */   public Stoppable scheduleRepeating(Steppable event)
/*     */   {
/* 535 */     synchronized (this.lock)
/*     */     {
/* 537 */       return scheduleRepeating(this.time + 1.0D, 0, event, 1.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Stoppable scheduleRepeating(Steppable event, double interval)
/*     */   {
/* 559 */     synchronized (this.lock)
/*     */     {
/* 561 */       return scheduleRepeating(this.time + interval, 0, event, interval);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Stoppable scheduleRepeating(Steppable event, int ordering, double interval)
/*     */   {
/* 583 */     synchronized (this.lock)
/*     */     {
/* 585 */       return scheduleRepeating(this.time + interval, ordering, event, interval);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Stoppable scheduleRepeating(double time, Steppable event)
/*     */   {
/* 609 */     return scheduleRepeating(time, 0, event, 1.0D);
/*     */   }
/*     */ 
/*     */   public Stoppable scheduleRepeating(double time, Steppable event, double interval)
/*     */   {
/* 632 */     return scheduleRepeating(time, 0, event, interval);
/*     */   }
/*     */ 
/*     */   public Stoppable scheduleRepeating(double time, int ordering, Steppable event)
/*     */   {
/* 655 */     return scheduleRepeating(time, ordering, event, 1.0D);
/*     */   }
/*     */ 
/*     */   public Stoppable scheduleRepeating(double time, int ordering, Steppable event, double interval)
/*     */   {
/* 677 */     if (interval <= 0.0D) throw new IllegalArgumentException("The steppable " + event + " was scheduled repeating with an impossible interval (" + interval + ")");
/* 678 */     Key k = new Key(time, ordering);
/* 679 */     IterativeRepeat r = new IterativeRepeat(event, interval, k);
/*     */ 
/* 681 */     synchronized (this.lock)
/*     */     {
/* 683 */       if (_scheduleOnce(k, r)) return r;
/* 684 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected static class Key implements Comparable, Serializable {
/*     */     double time;
/*     */     int ordering;
/*     */ 
/*     */     public int getOrdering() {
/* 694 */       return this.ordering; } 
/* 695 */     public double getTime() { return this.time; }
/*     */ 
/*     */     public Key(double time, int ordering)
/*     */     {
/* 699 */       this.time = time;
/* 700 */       this.ordering = ordering;
/*     */     }
/*     */ 
/*     */     public boolean equals(Object obj)
/*     */     {
/* 705 */       if ((obj != null) && ((obj instanceof Key)))
/*     */       {
/* 707 */         Key o = (Key)obj;
/* 708 */         return (o.time == this.time) && (o.ordering == this.ordering);
/*     */       }
/* 710 */       return false;
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 715 */       int y = this.ordering;
/* 716 */       y += (y << 15 ^ 0xFFFFFFFF);
/* 717 */       y ^= y >>> 10;
/* 718 */       y += (y << 3);
/* 719 */       y ^= y >>> 6;
/* 720 */       y += (y << 11 ^ 0xFFFFFFFF);
/* 721 */       y ^= y >>> 16;
/*     */ 
/* 723 */       long key = Double.doubleToRawLongBits(this.time);
/* 724 */       key += (key << 32 ^ 0xFFFFFFFF);
/* 725 */       key ^= key >>> 22;
/* 726 */       key += (key << 13 ^ 0xFFFFFFFF);
/* 727 */       key ^= key >>> 8;
/* 728 */       key += (key << 3);
/* 729 */       key ^= key >>> 15;
/* 730 */       key += (key << 27 ^ 0xFFFFFFFF);
/* 731 */       key ^= key >>> 31;
/*     */ 
/* 733 */       return (int)(key ^ key >> 32) ^ y;
/*     */     }
/*     */ 
/*     */     public int compareTo(Object obj)
/*     */     {
/* 738 */       Key o = (Key)obj;
/* 739 */       double time = this.time;
/* 740 */       double time2 = o.time;
/* 741 */       if (time == time2)
/*     */       {
/* 743 */         int ordering = this.ordering;
/* 744 */         int ordering2 = o.ordering;
/* 745 */         if (ordering == ordering2) return 0;
/* 746 */         if (ordering < ordering2) return -1;
/* 747 */         return 1;
/*     */       }
/*     */ 
/* 750 */       if (time < time2) return -1;
/* 751 */       return 1;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.engine.Schedule
 * JD-Core Version:    0.6.2
 */