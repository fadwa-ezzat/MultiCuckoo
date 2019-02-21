/*     */ package sim.engine;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ class ThreadPool
/*     */ {
/* 303 */   LinkedList threads = new LinkedList();
/* 304 */   int totalThreads = 0;
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   void killThreads()
/*     */   {
/* 309 */     synchronized (this.threads)
/*     */     {
/* 311 */       joinThreads();
/* 312 */       while (!this.threads.isEmpty())
/*     */       {
/* 314 */         Node node = (Node)this.threads.remove();
/* 315 */         synchronized (node) { node.die = true; node.notify(); } try {
/* 316 */           node.thread.join(); } catch (InterruptedException e) {
/*     */         }
/* 318 */         this.totalThreads -= 1;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void joinThreads()
/*     */   {
/* 326 */     synchronized (this.threads)
/*     */     {
/* 328 */       while (this.totalThreads > this.threads.size()) try {
/* 329 */           this.threads.wait();
/*     */         } catch (InterruptedException e) {
/*     */         } 
/*     */     }
/*     */   }
/*     */ 
/* 335 */   void startThread(Runnable run) { startThread(run, "ParallelSequence"); }
/*     */ 
/*     */ 
/*     */   void startThread(Runnable run, String name)
/*     */   {
/*     */     Node node;
/* 341 */     synchronized (this.threads)
/*     */     {
/* 343 */       if (this.threads.isEmpty())
/*     */       {
/* 345 */         Node node = new Node(name + " Thread " + this.totalThreads);
/* 346 */         node.thread.start();
/* 347 */         this.totalThreads += 1;
/*     */       }
/*     */       else
/*     */       {
/* 351 */         node = (Node)this.threads.remove();
/*     */       }
/*     */     }
/* 354 */     synchronized (node) { node.toRun = run; node.go = true; node.notify();
/*     */     }
/*     */   }
/*     */ 
/*     */   class Node
/*     */     implements Runnable
/*     */   {
/* 263 */     boolean die = false;
/* 264 */     boolean go = false;
/*     */     public Thread thread;
/*     */     public Runnable toRun;
/*     */ 
/*     */     public Node(String name)
/*     */     {
/* 270 */       this.thread = new Thread(this);
/* 271 */       this.thread.setDaemon(true);
/* 272 */       this.thread.setName(name);
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/*     */       while (true)
/*     */       {
/* 279 */         synchronized (this)
/*     */         {
/* 281 */           if ((!this.go) && (!this.die)) {
/*     */             try {
/* 283 */               wait(); } catch (InterruptedException e) {
/* 284 */             }continue;
/*     */           }
/* 286 */           this.go = false;
/* 287 */           if (this.die) { this.die = false; return; }
/*     */         }
/* 289 */         this.toRun.run();
/* 290 */         this.toRun = null;
/*     */ 
/* 292 */         synchronized (ThreadPool.this.threads)
/*     */         {
/* 294 */           ThreadPool.this.threads.add(this);
/* 295 */           if (ThreadPool.this.totalThreads == ThreadPool.this.threads.size())
/* 296 */             ThreadPool.this.threads.notify();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.engine.ThreadPool
 * JD-Core Version:    0.6.2
 */