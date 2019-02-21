/*     */ package sim.engine;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OptionalDataException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ 
/*     */ public class SimState
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public MersenneTwisterFast random;
/*     */   public Schedule schedule;
/*  39 */   HashSet asynchronous = new HashSet();
/*     */ 
/*  41 */   Object asynchronousLock = new boolean[1];
/*     */ 
/*  43 */   boolean cleaningAsynchronous = false;
/*     */ 
/* 329 */   long job = 0L;
/* 330 */   long seed = 0L;
/*     */ 
/* 683 */   static Object printLock = new Object[0];
/*     */ 
/*     */   SimState(long seed, MersenneTwisterFast random, Schedule schedule)
/*     */   {
/*  47 */     this.random = random;
/*  48 */     this.schedule = schedule;
/*  49 */     this.seed = ((int)seed);
/*     */   }
/*     */ 
/*     */   public SimState(long seed)
/*     */   {
/*  56 */     this(seed, new MersenneTwisterFast(seed), new Schedule());
/*     */   }
/*     */ 
/*     */   protected SimState(MersenneTwisterFast random, Schedule schedule)
/*     */   {
/*  65 */     this(0L, random, schedule);
/*     */   }
/*     */ 
/*     */   protected SimState(long seed, Schedule schedule)
/*     */   {
/*  74 */     this(seed, new MersenneTwisterFast(seed), schedule);
/*     */   }
/*     */ 
/*     */   protected SimState(MersenneTwisterFast random)
/*     */   {
/*  83 */     this(0L, random, new Schedule());
/*     */   }
/*     */ 
/*     */   public void setSeed(long seed)
/*     */   {
/*  88 */     seed = (int)seed;
/*  89 */     this.random = new MersenneTwisterFast(seed);
/*  90 */     this.seed = seed;
/*     */   }
/*     */ 
/*     */   public static MersenneTwisterFast primeGenerator(MersenneTwisterFast generator)
/*     */   {
/* 101 */     for (int i = 0; i < 1249; i++)
/* 102 */       generator.nextInt();
/* 103 */     return generator;
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/* 113 */     this.random = primeGenerator(this.random);
/*     */ 
/* 115 */     cleanupAsynchronous();
/*     */ 
/* 117 */     this.schedule.reset();
/*     */   }
/*     */ 
/*     */   public void finish()
/*     */   {
/* 127 */     kill();
/*     */   }
/*     */ 
/*     */   public void kill()
/*     */   {
/* 138 */     cleanupAsynchronous();
/* 139 */     this.schedule.clear();
/* 140 */     this.schedule.seal();
/*     */   }
/*     */ 
/*     */   public boolean addToAsynchronousRegistry(AsynchronousSteppable stop)
/*     */   {
/* 154 */     if (stop == null) return false;
/* 155 */     synchronized (this.asynchronousLock)
/*     */     {
/* 157 */       if (this.cleaningAsynchronous) return false;
/* 158 */       this.asynchronous.add(stop);
/* 159 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeFromAsynchronousRegistry(AsynchronousSteppable stop)
/*     */   {
/* 168 */     if (stop == null) return;
/* 169 */     synchronized (this.asynchronousLock)
/*     */     {
/* 171 */       if (!this.cleaningAsynchronous)
/* 172 */         this.asynchronous.remove(stop);
/*     */     }
/*     */   }
/*     */ 
/*     */   public AsynchronousSteppable[] asynchronousRegistry()
/*     */   {
/* 179 */     synchronized (this.asynchronousLock)
/*     */     {
/* 181 */       AsynchronousSteppable[] b = new AsynchronousSteppable[this.asynchronous.size()];
/* 182 */       int x = 0;
/* 183 */       Iterator i = this.asynchronous.iterator();
/* 184 */       while (i.hasNext())
/* 185 */         b[(x++)] = ((AsynchronousSteppable)(AsynchronousSteppable)i.next());
/* 186 */       return b;
/*     */     }
/*     */   }
/*     */ 
/*     */   void cleanupAsynchronous()
/*     */   {
/* 197 */     AsynchronousSteppable[] b = null;
/* 198 */     synchronized (this.asynchronousLock)
/*     */     {
/* 200 */       b = asynchronousRegistry();
/* 201 */       this.cleaningAsynchronous = true;
/*     */     }
/* 203 */     int len = b.length;
/* 204 */     for (int x = 0; x < len; x++) b[x].stop();
/* 205 */     synchronized (this.asynchronousLock)
/*     */     {
/* 207 */       this.asynchronous = new HashSet(this.asynchronous.size());
/* 208 */       this.cleaningAsynchronous = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void preCheckpoint()
/*     */   {
/* 218 */     AsynchronousSteppable[] b = asynchronousRegistry();
/* 219 */     int len = b.length;
/* 220 */     for (int x = 0; x < len; x++) b[x].pause();
/*     */   }
/*     */ 
/*     */   public void postCheckpoint()
/*     */   {
/* 228 */     AsynchronousSteppable[] b = asynchronousRegistry();
/* 229 */     int len = b.length;
/* 230 */     for (int x = 0; x < len; x++) b[x].resume(false);
/*     */   }
/*     */ 
/*     */   public void awakeFromCheckpoint()
/*     */   {
/* 238 */     AsynchronousSteppable[] b = asynchronousRegistry();
/* 239 */     int len = b.length;
/* 240 */     for (int x = 0; x < len; x++) b[x].resume(true);
/*     */   }
/*     */ 
/*     */   public void writeToCheckpoint(OutputStream stream)
/*     */     throws IOException
/*     */   {
/* 249 */     preCheckpoint();
/*     */ 
/* 251 */     GZIPOutputStream g = new GZIPOutputStream(new BufferedOutputStream(stream));
/*     */ 
/* 255 */     ObjectOutputStream s = new ObjectOutputStream(g);
/*     */ 
/* 258 */     s.writeObject(this);
/* 259 */     s.flush();
/* 260 */     g.finish();
/* 261 */     g.flush();
/* 262 */     postCheckpoint();
/*     */   }
/*     */ 
/*     */   public SimState writeToCheckpoint(File file)
/*     */   {
/* 269 */     FileOutputStream f = null;
/*     */     try {
/* 271 */       f = new FileOutputStream(file);
/* 272 */       writeToCheckpoint(f);
/* 273 */       f.close();
/* 274 */       return this;
/*     */     }
/*     */     catch (Exception e) {
/*     */       try {
/* 278 */         if (f != null) f.close();  } catch (Exception e2) {  }
/*     */ 
/* 279 */       e.printStackTrace();
/* 280 */     }return null;
/*     */   }
/*     */ 
/*     */   public static SimState readFromCheckpoint(File file)
/*     */   {
/*     */     try
/*     */     {
/* 288 */       FileInputStream f = new FileInputStream(file);
/* 289 */       SimState state = readFromCheckpoint(f);
/* 290 */       f.close();
/* 291 */       return state;
/*     */     } catch (Exception e) {
/* 293 */       e.printStackTrace(); } return null;
/*     */   }
/*     */ 
/*     */   public static SimState readFromCheckpoint(InputStream stream)
/*     */     throws IOException, ClassNotFoundException, OptionalDataException, ClassCastException
/*     */   {
/* 304 */     ObjectInputStream s = new ObjectInputStream(new GZIPInputStream(new BufferedInputStream(stream)));
/*     */ 
/* 308 */     SimState state = (SimState)s.readObject();
/* 309 */     state.awakeFromCheckpoint();
/* 310 */     return state;
/*     */   }
/*     */ 
/*     */   static boolean keyExists(String key, String[] args)
/*     */   {
/* 315 */     for (int x = 0; x < args.length; x++)
/* 316 */       if (args[x].equalsIgnoreCase(key))
/* 317 */         return true;
/* 318 */     return false;
/*     */   }
/*     */ 
/*     */   static String argumentForKey(String key, String[] args)
/*     */   {
/* 323 */     for (int x = 0; x < args.length - 1; x++)
/* 324 */       if (args[x].equalsIgnoreCase(key))
/* 325 */         return args[(x + 1)];
/* 326 */     return null;
/*     */   }
/*     */ 
/*     */   public long seed()
/*     */   {
/* 336 */     return (int)this.seed;
/*     */   }
/*     */ 
/*     */   public void setJob(long job)
/*     */   {
/* 341 */     this.job = job;
/*     */   }
/*     */ 
/*     */   public long job()
/*     */   {
/* 348 */     return this.job;
/*     */   }
/*     */ 
/*     */   public static void doLoop(Class c, String[] args)
/*     */   {
/* 355 */     doLoop(new MakesSimState()
/*     */     {
/*     */       public SimState newInstance(long seed, String[] args)
/*     */       {
/*     */         try
/*     */         {
/* 361 */           return (SimState)this.val$c.getConstructor(new Class[] { Long.TYPE }).newInstance(new Object[] { Long.valueOf(seed) });
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 365 */           throw new RuntimeException("Exception occurred while trying to construct the simulation " + this.val$c + "\n" + e);
/*     */         }
/*     */       }
/* 368 */       public Class simulationClass() { return this.val$c; }
/*     */ 
/*     */     }
/*     */     , args);
/*     */   }
/*     */ 
/*     */   public static void doLoop(MakesSimState generator, final String[] args)
/*     */   {
/* 382 */     if (keyExists("-help", args))
/*     */     {
/* 384 */       System.err.println("Format:           java " + generator.simulationClass().getName() + " \\\n" + "                       [-help] [-repeat R] [-parallel P] [-seed S] \\\n" + "                       [-until U] [-for F] [-time T] [-docheckpoint D] \\\n" + "                       [-checkpoint C] [-quiet] \n\n" + "-help             Shows this message and exits.\n\n" + "-repeat R         Long value > 0: Runs R jobs.  Unless overridden by a\n" + "                  checkpoint recovery (see -checkpoint), the random seed for\n" + "                  each job is the provided -seed plus the job# (starting at 0).\n" + "                  Default: runs once only: job number is 0.\n\n" + "-parallel P       Long value > 0: Runs P separate batches of jobs in parallel,\n" + "                  each one containing R jobs (as specified by -repeat).  Each\n" + "                  batch has its own independent set of checkpoint files.  Job\n" + "                  numbers are 0, P, P*2, ... for the first batch, then 1, P+1,\n" + "                  P*2+1, ... for the second batch, then 2, P+2, P*2+2, ... for\n" + "                  the third batch, and so on.  -parallel may not be used in\n" + "                  combination with -checkpoint.\n" + "                  Default: one batch only (no parallelism).\n\n" + "-seed S           Long value not 0: the random number generator seed, unless \n" + "                  overridden by a checkpoint recovery (see -checkpoint).\n" + "                  Default: the system time in milliseconds.\n\n" + "-until U          Double value >= 0: the simulation must stop when the\n" + "                  simulation time U has been reached or exceeded.\n" + "                  If -for is also included, the simulation terminates when\n" + "                  either of them is completed.\n" + "                  Default: don't stop.\n\n" + "-for N            Long value >= 0: the simulation must stop when N\n" + "                  simulation steps have transpired.   If -until is also\n" + "                  included, the simulation terminates when either of them is\n" + "                  completed.\n" + "                  Default: don't stop.\n\n" + "-time T           Long value >= 0: print a timestamp every T simulation steps.\n" + "                  If 0, nothing is printed.\n" + "                  Default: auto-chooses number of steps based on how many\n" + "                  appear to fit in one second of wall clock time.  Rounds to\n" + "                  one of 1, 2, 5, 10, 25, 50, 100, 250, 500, 1000, 2500, etc.\n\n" + "-docheckpoint D   Long value > 0: checkpoint every D simulation steps.\n" + "                  Default: never.\n" + "                  Checkpoint files named\n" + "                  <steps>.<job#>." + generator.simulationClass().getName().substring(generator.simulationClass().getName().lastIndexOf(".") + 1) + ".checkpoint\n\n" + "-checkpoint C     String: loads the simulation from file C, recovering the job\n" + "                  number and the seed.  If the checkpointed simulation was begun\n" + "                  on the command line but was passed through the GUI for a while\n" + "                  (even multiply restarted in the GUI) and then recheckpointed,\n" + "                  then the seed and job numbers will be the same as when they\n" + "                  were last on the command line.  If the checkpointed simulation\n" + "                  was begun on the GUI, then the seed will not be recovered and\n" + "                  job will be set to 0. Further jobs and seeds are incremented\n" + "                  from the recovered job and seed.\n" + "                  Default: starts a new simulation rather than loading one, at\n" + "                  job 0 and with the seed given in -seed.\n\n" + "-quiet            Does not print messages except for errors and warnings.\n" + "                  This option implies -time 0.\n" + "                  Default: prints all messages.\n");
/*     */ 
/* 441 */       System.exit(0);
/*     */     }
/*     */ 
/* 444 */     final boolean quiet = keyExists("-quiet", args);
/*     */ 
/* 446 */     NumberFormat n = NumberFormat.getInstance();
/* 447 */     n.setMinimumFractionDigits(0);
/* 448 */     if (!quiet) System.err.println("MASON Version " + n.format(version()) + ".  For further options, try adding ' -help' at end.");
/*     */ 
/* 451 */     double _until = (1.0D / 0.0D);
/* 452 */     String until_s = argumentForKey("-until", args);
/* 453 */     if (until_s != null)
/*     */       try
/*     */       {
/* 456 */         _until = Double.parseDouble(until_s);
/* 457 */         if (_until < 0.0D) throw new Exception();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 461 */         throw new RuntimeException("Invalid 'until' value: " + until_s + ", must be a positive real value");
/*     */       }
/* 463 */     final double until = _until;
/*     */ 
/* 465 */     long _seed = System.currentTimeMillis();
/* 466 */     String seed_s = argumentForKey("-seed", args);
/* 467 */     if (seed_s != null)
/*     */       try
/*     */       {
/* 470 */         _seed = Long.parseLong(seed_s);
/* 471 */         if (_seed == 0L) throw new Exception();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 475 */         throw new RuntimeException("Invalid 'seed' value: " + seed_s + ", must be a non-zero integer, or nonexistent to seed by clock time");
/*     */       }
/* 477 */     final long seed_init = _seed;
/*     */ 
/* 479 */     long __for = -1L;
/* 480 */     String for_s = argumentForKey("-for", args);
/* 481 */     if (for_s != null)
/*     */       try
/*     */       {
/* 484 */         __for = Long.parseLong(for_s);
/* 485 */         if (__for < 0L) throw new Exception();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 489 */         throw new RuntimeException("Invalid 'for' value: " + for_s + ", must be an integer >= 0");
/*     */       }
/* 491 */     final long _for = __for;
/*     */ 
/* 493 */     long _time = -1L;
/* 494 */     String time_s = argumentForKey("-time", args);
/* 495 */     if (time_s != null)
/*     */       try
/*     */       {
/* 498 */         _time = Long.parseLong(time_s);
/* 499 */         if (_time < 0L) throw new Exception();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 503 */         throw new RuntimeException("Invalid 'time' value: " + time_s + ", must be a positive integer");
/*     */       }
/* 505 */     long time_init = _time;
/*     */ 
/* 507 */     long _cmod = 0L;
/* 508 */     String cmod_s = argumentForKey("-docheckpoint", args);
/* 509 */     if (cmod_s != null)
/*     */       try
/*     */       {
/* 512 */         _cmod = Long.parseLong(cmod_s);
/* 513 */         if (_cmod <= 0L) throw new Exception();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 517 */         throw new RuntimeException("Invalid checkpoint modulo: " + cmod_s + ", must be a positive integer");
/*     */       }
/* 519 */     final long cmod = _cmod;
/*     */ 
/* 521 */     long _repeat = 1L;
/* 522 */     String repeat_s = argumentForKey("-repeat", args);
/* 523 */     if (repeat_s != null)
/*     */       try
/*     */       {
/* 526 */         _repeat = Long.parseLong(repeat_s);
/* 527 */         if (_repeat <= 0L) throw new Exception();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 531 */         throw new RuntimeException("Invalid repeat value: " + repeat_s + ", must be a positive integer");
/*     */       }
/* 533 */     final long repeat = _repeat;
/*     */ 
/* 535 */     int _parallel = 1;
/* 536 */     String parallel_s = argumentForKey("-parallel", args);
/* 537 */     if (parallel_s != null)
/*     */       try
/*     */       {
/* 540 */         _parallel = Integer.parseInt(parallel_s);
/* 541 */         if (_parallel <= 0) throw new Exception();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 545 */         throw new RuntimeException("Invalid parallel value: " + parallel_s + ", must be a positive integer");
/*     */       }
/* 547 */     int parallel = _parallel;
/*     */ 
/* 550 */     String checkpointFile = argumentForKey("-checkpoint", args);
/* 551 */     if ((parallel > 1) && (checkpointFile != null))
/*     */     {
/* 553 */       System.err.println("Cannot load from checkpoint and run in parallel at the same time.  Sorry.");
/* 554 */       System.exit(1);
/*     */     }
/*     */ 
/* 566 */     Thread[] threads = new Thread[parallel];
/* 567 */     for (int _thread = 0; _thread < parallel; _thread++)
/*     */     {
/* 569 */       int thread = _thread;
/* 570 */       threads[thread] = new Thread(new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 574 */           long time = this.val$time_init - 1L;
/* 575 */           long job = repeat * seed_init;
/* 576 */           long seed = quiet + job;
/* 577 */           for (long rep = 0L; rep < seed_init; rep += 1L)
/*     */           {
/* 579 */             SimState state = null;
/*     */ 
/* 582 */             if ((rep == 0L) && (args != null))
/*     */             {
/* 584 */               if (!_for) SimState.printlnSynchronized("Loading from checkpoint " + args);
/* 585 */               state = SimState.readFromCheckpoint(new File(args));
/* 586 */               if (state == null) {
/* 587 */                 System.exit(1);
/* 588 */               } else if (state.getClass() != until.simulationClass())
/*     */               {
/* 590 */                 SimState.printlnSynchronized("Checkpoint contains some other simulation: " + state + ", should have been of class " + until.simulationClass());
/* 591 */                 System.exit(1);
/*     */               }
/*     */ 
/* 594 */               state.nameThread();
/*     */ 
/* 596 */               job = state.job();
/* 597 */               if (state.seed() != 0L)
/*     */               {
/* 599 */                 seed = state.seed();
/* 600 */                 if (!_for) SimState.printlnSynchronized("Recovered job: " + state.job() + " Seed: " + state.seed());
/*     */               }
/* 602 */               else if (!_for) { SimState.printlnSynchronized("Renamed job: " + state.job() + " (unknown seed)"); }
/*     */ 
/*     */             }
/*     */ 
/* 606 */             if (state == null)
/*     */             {
/* 608 */               state = until.newInstance(seed, cmod);
/* 609 */               state.nameThread();
/* 610 */               state.job = job;
/* 611 */               state.seed = seed;
/* 612 */               if (!_for) SimState.printlnSynchronized("Job: " + state.job() + " Seed: " + state.seed());
/* 613 */               state.start();
/*     */             }
/*     */ 
/* 616 */             NumberFormat rateFormat = NumberFormat.getInstance();
/* 617 */             rateFormat.setMaximumFractionDigits(5);
/* 618 */             rateFormat.setMinimumIntegerDigits(1);
/*     */ 
/* 621 */             boolean retval = false;
/* 622 */             long steps = 0L;
/*     */ 
/* 624 */             long oldClock = System.currentTimeMillis();
/* 625 */             Schedule schedule = state.schedule;
/* 626 */             long firstSteps = schedule.getSteps();
/*     */ 
/* 628 */             while (((this.val$_for == -1L) || (steps < this.val$_for)) && (schedule.getTime() <= this.val$until))
/*     */             {
/* 630 */               if (!schedule.step(state))
/*     */               {
/* 632 */                 retval = true;
/* 633 */                 break;
/*     */               }
/* 635 */               steps = schedule.getSteps();
/* 636 */               if (time < 0L)
/*     */               {
/* 638 */                 if (System.currentTimeMillis() - oldClock > 1000L)
/*     */                 {
/* 640 */                   time = SimState.figureTime(steps - firstSteps);
/*     */                 }
/*     */               }
/* 643 */               if ((time > 0L) && (steps % time == 0L))
/*     */               {
/* 645 */                 long clock = System.currentTimeMillis();
/* 646 */                 if (!_for) SimState.printlnSynchronized("Job " + job + ": " + "Steps: " + steps + " Time: " + state.schedule.getTimestamp("At Start", "Done") + " Rate: " + rateFormat.format(1000.0D * (steps - firstSteps) / (clock - oldClock)));
/* 647 */                 firstSteps = steps;
/* 648 */                 oldClock = clock;
/*     */               }
/* 650 */               if ((this.val$cmod > 0L) && (steps % this.val$cmod == 0L))
/*     */               {
/* 652 */                 String s = "" + steps + "." + state.job() + "." + state.getClass().getName().substring(state.getClass().getName().lastIndexOf(".") + 1) + ".checkpoint";
/* 653 */                 if (!_for) SimState.printlnSynchronized("Job " + job + ": " + "Checkpointing to file: " + s);
/* 654 */                 state.writeToCheckpoint(new File(s));
/*     */               }
/*     */             }
/*     */ 
/* 658 */             state.finish();
/*     */ 
/* 660 */             if (retval)
/*     */             {
/* 662 */               if (!_for) SimState.printlnSynchronized("Job " + job + ": " + "Exhausted " + state.job);
/*     */ 
/*     */             }
/* 666 */             else if (!_for) SimState.printlnSynchronized("Job " + job + ": " + "Quit " + state.job);
/*     */ 
/* 669 */             job += 1L;
/* 670 */             seed += 1L;
/*     */           }
/*     */         }
/*     */       });
/* 674 */       threads[thread].start();
/*     */     }
/* 676 */     for (int thread = 0; thread < parallel; thread++)
/*     */       try {
/* 678 */         threads[thread].join(); } catch (InterruptedException ex) {
/*     */       }
/* 680 */     System.exit(0);
/*     */   }
/*     */ 
/*     */   public static void printlnSynchronized(String val)
/*     */   {
/* 686 */     synchronized (printLock) { System.err.println(val); }
/*     */ 
/*     */   }
/*     */ 
/*     */   public void nameThread()
/*     */   {
/* 693 */     Thread.currentThread().setName("MASON Model: " + getClass());
/*     */   }
/*     */ 
/*     */   public static double version()
/*     */   {
/* 699 */     return 19.0D;
/*     */   }
/*     */ 
/*     */   static long figureTime(long time)
/*     */   {
/* 708 */     long n = 1L;
/*     */     while (true)
/*     */     {
/* 711 */       if (n >= time) return n;
/* 712 */       if (n * 10L / 4L >= time) return n * 10L / 4L;
/* 713 */       if (n * 10L / 2L >= time) return n * 10L / 2L;
/* 714 */       n *= 10L;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.engine.SimState
 * JD-Core Version:    0.6.2
 */