/*     */ package sim.engine;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ 
/*     */ public class AsynchronousSteppable
/*     */   implements Stoppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   Thread thread;
/* 248 */   boolean running = false;
/* 249 */   boolean paused = false;
/*     */   protected SimState state;
/*     */ 
/*     */   protected void run(boolean resuming, boolean restoringFromCheckpoint)
/*     */   {
/* 255 */     run(resuming);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   protected void run(boolean resuming)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void halt(boolean pausing)
/*     */   {
/*     */   }
/*     */ 
/*     */   public final synchronized void step(SimState state)
/*     */   {
/* 269 */     if (this.running) return;
/* 270 */     this.running = true;
/* 271 */     this.state = state;
/* 272 */     state.addToAsynchronousRegistry(this);
/* 273 */     this.thread = new Thread(new Runnable() { public void run() { AsynchronousSteppable.this.run(false, false); }
/*     */ 
/*     */     });
/* 274 */     this.thread.setDaemon(true);
/* 275 */     this.thread.setName("Asynchronous Steppable: " + this);
/* 276 */     this.thread.start();
/*     */   }
/*     */ 
/*     */   public final synchronized void stop()
/*     */   {
/* 282 */     boolean joined = false;
/* 283 */     if (!this.running) return;
/* 284 */     halt(false);
/* 285 */     while (!joined) {
/*     */       try {
/* 287 */         this.thread.join(); joined = true;
/*     */       }
/*     */       catch (InterruptedException e)
/*     */       {
/*     */       }
/*     */     }
/*     */ 
/* 294 */     this.state.removeFromAsynchronousRegistry(this);
/* 295 */     this.running = false;
/*     */   }
/*     */ 
/*     */   public final synchronized void pause()
/*     */   {
/* 301 */     boolean joined = false;
/* 302 */     if ((this.paused) || (!this.running)) return;
/* 303 */     halt(true);
/* 304 */     while (!joined) {
/*     */       try {
/* 306 */         this.thread.join(); joined = true;
/*     */       }
/*     */       catch (InterruptedException e)
/*     */       {
/*     */       }
/*     */     }
/*     */ 
/* 313 */     this.paused = true;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public final void resume()
/*     */   {
/* 320 */     resume(false);
/*     */   }
/*     */ 
/*     */   public final synchronized void resume(final boolean restoringFromCheckpoint)
/*     */   {
/* 329 */     if ((!this.paused) || (!this.running)) return;
/* 330 */     this.paused = false;
/* 331 */     this.thread = new Thread(new Runnable() { public void run() { AsynchronousSteppable.this.run(true, restoringFromCheckpoint); }
/*     */ 
/*     */     });
/* 332 */     this.thread.start();
/*     */   }
/*     */ 
/*     */   private void writeObject(ObjectOutputStream p)
/*     */     throws IOException
/*     */   {
/* 339 */     p.writeBoolean(this.running);
/* 340 */     p.writeBoolean(this.paused);
/* 341 */     p.writeObject(this.state);
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream p)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 348 */     this.running = p.readBoolean();
/* 349 */     this.paused = p.readBoolean();
/* 350 */     this.state = ((SimState)p.readObject());
/*     */   }
/*     */ 
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/* 355 */       stop(); } finally {
/* 356 */       super.finalize();
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public final Steppable stopper()
/*     */   {
/* 365 */     return new Steppable() { public void step(SimState state) { AsynchronousSteppable.this.stop(); }
/*     */ 
/*     */     };
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.engine.AsynchronousSteppable
 * JD-Core Version:    0.6.2
 */