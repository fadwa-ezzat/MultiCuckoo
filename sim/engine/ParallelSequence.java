/*     */ package sim.engine;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Collection;
/*     */ import sim.util.LocationLog;
/*     */ 
/*     */ public class ParallelSequence extends Sequence
/*     */ {
/*     */   ThreadPool threads;
/*  59 */   boolean pleaseDie = false;
/*  60 */   Object operatingLock = new Object();
/*  61 */   boolean operating = false;
/*  62 */   boolean destroysThreads = false;
/*  63 */   int numThreads = 0;
/*     */   public static final int CPUS = -1;
/*     */   public static final int STEPPABLES = -2;
/*  68 */   static int availableProcessors = Runtime.getRuntime().availableProcessors();
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public boolean getDestroysThreads()
/*     */   {
/*  70 */     return this.destroysThreads; } 
/*  71 */   public void setDestroysThreads(boolean val) { this.destroysThreads = val; }
/*     */ 
/*     */ 
/*     */   private void writeObject(ObjectOutputStream p)
/*     */     throws IOException
/*     */   {
/*  77 */     p.writeBoolean(this.pleaseDie);
/*  78 */     p.writeBoolean(this.destroysThreads);
/*  79 */     p.writeInt(this.numThreads);
/*     */   }
/*     */ 
/*     */   private void readObject(ObjectInputStream p)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/*  88 */     this.pleaseDie = p.readBoolean();
/*  89 */     this.destroysThreads = p.readBoolean();
/*  90 */     this.numThreads = p.readInt();
/*     */ 
/*  94 */     this.operatingLock = new Object();
/*     */   }
/*     */ 
/*     */   public Steppable getCleaner()
/*     */   {
/*  99 */     return new Steppable() { public void step(SimState state) { ParallelSequence.this.cleanup(); }
/*     */ 
/*     */     };
/*     */   }
/*     */ 
/*     */   public void cleanup()
/*     */   {
/* 110 */     this.pleaseDie = true;
/* 111 */     if (this.threads != null)
/* 112 */       this.threads.killThreads();
/* 113 */     this.pleaseDie = false;
/* 114 */     this.threads = null;
/*     */   }
/*     */ 
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/* 119 */       cleanup(); } finally {
/* 120 */       super.finalize();
/*     */     }
/*     */   }
/*     */ 
/*     */   public ParallelSequence(Steppable[] steps, int threads)
/*     */   {
/* 128 */     super(steps);
/* 129 */     this.numThreads = threads;
/*     */   }
/*     */ 
/*     */   public ParallelSequence(Steppable[] steps)
/*     */   {
/* 135 */     this(steps, -2);
/*     */   }
/*     */ 
/*     */   public ParallelSequence(Collection steps, int threads)
/*     */   {
/* 143 */     super(steps);
/* 144 */     this.numThreads = threads;
/*     */   }
/*     */ 
/*     */   public ParallelSequence(Collection steps)
/*     */   {
/* 150 */     this(steps, -2);
/*     */   }
/*     */   protected boolean canEnsureOrder() {
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/* 159 */     synchronized (this.operatingLock)
/*     */     {
/* 161 */       if (this.operating) {
/* 162 */         throw new RuntimeException("ParallelSequence stepped, but it's already in progress.\nProbably you have the same ParallelSequence nested, or the same ParallelSequence being stepped in parallel.\nEither way, it's a bug.");
/*     */       }
/*     */ 
/* 165 */       this.operating = true;
/*     */     }
/*     */ 
/* 168 */     loadSteps();
/*     */ 
/* 170 */     if (this.threads == null) {
/* 171 */       this.threads = new ThreadPool();
/*     */     }
/*     */ 
/* 174 */     int size = this.size;
/* 175 */     int n = this.numThreads;
/* 176 */     if (n == -1)
/* 177 */       n = availableProcessors;
/* 178 */     else if (n == -2)
/* 179 */       n = size;
/* 180 */     if (n > size) {
/* 181 */       n = size;
/*     */     }
/* 183 */     int jump = size / n;
/* 184 */     for (int i = 0; i < n; i++)
/*     */     {
/* 194 */       this.threads.startThread(new Worker(state, i * jump, Math.min((i + 1) * jump, size), 1), "ParallelSequence");
/*     */     }
/*     */ 
/* 198 */     if (this.destroysThreads)
/* 199 */       cleanup();
/*     */     else {
/* 201 */       this.threads.joinThreads();
/*     */     }
/*     */ 
/* 204 */     this.operating = false;
/*     */   }
/*     */   class Worker implements Runnable {
/*     */     SimState state;
/*     */     int start;
/*     */     int end;
/*     */     int modulo;
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/* 217 */     public Worker(SimState state, int start, int end, int modulo) { this.state = state;
/* 218 */       this.start = start;
/* 219 */       this.end = end;
/* 220 */       this.modulo = modulo;
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/* 225 */       Steppable[] steps = ParallelSequence.this.steps;
/* 226 */       int modulo = this.modulo;
/* 227 */       for (int s = this.start; s < this.end; s += modulo)
/*     */       {
/* 229 */         if (ParallelSequence.this.pleaseDie) break;
/* 230 */         Steppable step = steps[s];
/* 231 */         assert (LocationLog.set(step));
/* 232 */         steps[s].step(this.state);
/* 233 */         assert (LocationLog.clear());
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.engine.ParallelSequence
 * JD-Core Version:    0.6.2
 */