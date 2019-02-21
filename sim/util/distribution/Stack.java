/*     */ package sim.util.distribution;
/*     */ 
/*     */ class Stack
/*     */ {
/*     */   int N;
/*     */   int[] v;
/*     */   int i;
/*     */ 
/*     */   public Stack(int capacity)
/*     */   {
/* 415 */     this.N = capacity;
/* 416 */     this.i = -1;
/* 417 */     this.v = new int[this.N];
/*     */   }
/*     */ 
/*     */   public int pop()
/*     */   {
/* 423 */     if (this.i < 0) throw new InternalError("Cannot pop stack!");
/* 424 */     this.i -= 1;
/* 425 */     return this.v[(this.i + 1)];
/*     */   }
/*     */ 
/*     */   public void push(int value)
/*     */   {
/* 431 */     this.i += 1;
/* 432 */     if (this.i >= this.N) throw new InternalError("Cannot push stack!");
/* 433 */     this.v[this.i] = value;
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 439 */     return this.i + 1;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.distribution.Stack
 * JD-Core Version:    0.6.2
 */