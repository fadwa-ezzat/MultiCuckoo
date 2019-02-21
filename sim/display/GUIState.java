/*     */ package sim.display;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OptionalDataException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.SimpleInspector;
/*     */ import sim.util.Properties;
/*     */ 
/*     */ public abstract class GUIState
/*     */ {
/*  79 */   public MersenneTwisterFast guirandom = new MersenneTwisterFast();
/*     */   public SimState state;
/*     */   public Controller controller;
/*  88 */   public HashMap storage = new HashMap();
/*     */ 
/* 286 */   boolean started = false;
/*     */   protected Steppable[] before;
/*     */   Steppable[] before2;
/*     */   protected int beforeSize;
/*     */   protected Steppable[] after;
/*     */   Steppable[] after2;
/*     */   protected int afterSize;
/*     */   Steppable[] start;
/*     */   Steppable[] start2;
/*     */   int startSize;
/*     */   Steppable[] finish;
/*     */   Steppable[] finish2;
/*     */   int finishSize;
/*     */ 
/*     */   private GUIState()
/*     */   {
/*     */   }
/*     */ 
/*     */   public GUIState(SimState state)
/*     */   {
/* 100 */     this.state = state;
/* 101 */     resetQueues();
/*     */   }
/*     */ 
/*     */   public static String getTruncatedName(Class theClass)
/*     */   {
/* 109 */     if (theClass == null) return "";
/* 110 */     String fullName = theClass.getName();
/* 111 */     int lastPeriod = fullName.lastIndexOf(".");
/* 112 */     return fullName.substring(lastPeriod + 1);
/*     */   }
/*     */ 
/*     */   public static final String getName(Class theClass)
/*     */   {
/* 121 */     if (theClass == null) return "";
/*     */     try
/*     */     {
/* 124 */       Method m = theClass.getMethod("getName", (Class[])null);
/* 125 */       if (m.getDeclaringClass().equals(GUIState.class))
/* 126 */         return getTruncatedName(theClass);
/* 127 */       return (String)m.invoke(null, (Object[])null);
/*     */     }
/*     */     catch (NoSuchMethodException e)
/*     */     {
/* 131 */       e.printStackTrace();
/* 132 */       return getTruncatedName(theClass);
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/* 136 */       e.printStackTrace();
/* 137 */     }return "Error in retrieving simulation name";
/*     */   }
/*     */ 
/*     */   public Controller createController()
/*     */   {
/* 147 */     Console console = new Console(this);
/* 148 */     console.setVisible(true);
/* 149 */     return console;
/*     */   }
/*     */ 
/*     */   public static String getName()
/*     */   {
/* 164 */     return "This is GUIState's getName() method.  It probably shouldn't have been called.";
/*     */   }
/*     */ 
/*     */   static Object doDefaultInfo(Class theClass)
/*     */   {
/* 169 */     URL url = theClass.getResource("index.html");
/* 170 */     if (url == null)
/* 171 */       return "<html><head><title></title></head><body bgcolor=\"white\"></body></html>";
/* 172 */     return url;
/*     */   }
/*     */ 
/*     */   public static final Object getInfo(Class theClass)
/*     */   {
/* 185 */     if (theClass == null) return "";
/*     */     try
/*     */     {
/* 188 */       Method m = theClass.getMethod("getInfo", (Class[])null);
/* 189 */       if (m.getDeclaringClass().equals(GUIState.class))
/* 190 */         return doDefaultInfo(theClass);
/* 191 */       return m.invoke(null, (Object[])null);
/*     */     }
/*     */     catch (NoSuchMethodException e)
/*     */     {
/* 195 */       return doDefaultInfo(theClass);
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/* 199 */       e.printStackTrace();
/* 200 */     }return "Error in retrieving simulation info";
/*     */   }
/*     */ 
/*     */   public static Object getInfo()
/*     */   {
/* 219 */     return "This is GUIState's getInfo() method.  It probably shouldn't have been called.";
/*     */   }
/*     */ 
/*     */   public Properties getSimulationProperties()
/*     */   {
/* 226 */     return null;
/*     */   }
/*     */   public int getMaximumPropertiesForInspector() {
/* 229 */     return 100;
/*     */   }
/*     */ 
/*     */   public Inspector getInspector()
/*     */   {
/* 236 */     Object object = getSimulationInspectedObject();
/* 237 */     if (object != null)
/*     */     {
/* 239 */       Inspector i = Inspector.getInspector(object, this, null);
/* 240 */       i.setVolatile(false);
/* 241 */       return i;
/*     */     }
/* 243 */     Properties prop = getSimulationProperties();
/* 244 */     if (prop != null)
/*     */     {
/* 246 */       Inspector i = new SimpleInspector(prop, this, null);
/* 247 */       i.setVolatile(true);
/* 248 */       return i;
/*     */     }
/* 250 */     return null;
/*     */   }
/*     */ 
/*     */   public Object getSimulationInspectedObject()
/*     */   {
/* 263 */     return null;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public boolean isInspectorVolatile()
/*     */   {
/* 272 */     return getInspector().isVolatile();
/*     */   }
/*     */ 
/*     */   public void init(Controller controller)
/*     */   {
/* 281 */     this.controller = controller;
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/* 289 */     this.started = true;
/* 290 */     this.state.start();
/*     */ 
/* 295 */     synchronized (this.state.schedule)
/*     */     {
/* 298 */       Steppable[] _start2 = this.start2;
/* 299 */       System.arraycopy(this.start, 0, this.start2, 0, this.startSize);
/* 300 */       int _startSize = this.startSize;
/* 301 */       this.startSize = 0;
/*     */ 
/* 304 */       for (int x = 0; x < _startSize; x++)
/* 305 */         _start2[x].step(this.state);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void finish()
/*     */   {
/* 316 */     if (!this.started) return;
/* 317 */     synchronized (this.state.schedule)
/*     */     {
/* 320 */       Steppable[] _finish2 = this.finish2;
/* 321 */       System.arraycopy(this.finish, 0, this.finish2, 0, this.finishSize);
/* 322 */       int _finishSize = this.finishSize;
/* 323 */       this.finishSize = 0;
/*     */ 
/* 326 */       for (int x = 0; x < _finishSize; x++) {
/* 327 */         _finish2[x].step(this.state);
/*     */       }
/*     */     }
/* 330 */     this.state.finish();
/* 331 */     resetQueues();
/* 332 */     this.started = false;
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean validSimState(SimState state)
/*     */   {
/* 348 */     return (state != null) && (state.getClass().equals(this.state.getClass()));
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/* 359 */     this.state = state;
/* 360 */     this.started = true;
/*     */ 
/* 365 */     synchronized (state.schedule)
/*     */     {
/* 368 */       Steppable[] _start2 = this.start2;
/* 369 */       System.arraycopy(this.start, 0, this.start2, 0, this.startSize);
/* 370 */       int _startSize = this.startSize;
/* 371 */       this.startSize = 0;
/*     */ 
/* 374 */       for (int x = 0; x < _startSize; x++)
/* 375 */         _start2[x].step(state);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean readNewStateFromCheckpoint(File file)
/*     */     throws IOException, ClassNotFoundException, OptionalDataException, ClassCastException, Exception
/*     */   {
/* 386 */     FileInputStream f = new FileInputStream(file);
/* 387 */     SimState state = SimState.readFromCheckpoint(f);
/* 388 */     f.close();
/* 389 */     if (!validSimState(state)) return false;
/* 390 */     finish();
/* 391 */     load(state);
/* 392 */     return true;
/*     */   }
/*     */ 
/*     */   protected void resetQueues()
/*     */   {
/* 416 */     this.before = new Steppable[11];
/* 417 */     this.before2 = new Steppable[11];
/* 418 */     this.beforeSize = 0;
/* 419 */     this.after = new Steppable[11];
/* 420 */     this.after2 = new Steppable[11];
/* 421 */     this.afterSize = 0;
/* 422 */     this.start = new Steppable[11];
/* 423 */     this.start2 = new Steppable[11];
/* 424 */     this.startSize = 0;
/* 425 */     this.finish = new Steppable[11];
/* 426 */     this.finish2 = new Steppable[11];
/* 427 */     this.finishSize = 0;
/*     */   }
/*     */ 
/*     */   private final synchronized void reset(SimState state)
/*     */   {
/* 437 */     state.schedule.reset();
/* 438 */     resetQueues();
/*     */   }
/*     */ 
/*     */   public boolean step()
/*     */   {
/* 444 */     boolean returnval = false;
/* 445 */     synchronized (this.state.schedule)
/*     */     {
/* 449 */       Steppable[] _before2 = this.before2;
/* 450 */       Steppable[] _after2 = this.after2;
/* 451 */       System.arraycopy(this.before, 0, this.before2, 0, this.beforeSize);
/* 452 */       System.arraycopy(this.after, 0, this.after2, 0, this.afterSize);
/* 453 */       int _beforeSize = this.beforeSize;
/* 454 */       int _afterSize = this.afterSize;
/* 455 */       this.afterSize = 0;
/* 456 */       this.beforeSize = 0;
/*     */ 
/* 459 */       for (int x = 0; x < _beforeSize; x++) {
/* 460 */         _before2[x].step(this.state);
/*     */       }
/*     */ 
/* 463 */       returnval = this.state.schedule.step(this.state);
/*     */ 
/* 466 */       for (int x = 0; x < _afterSize; x++)
/* 467 */         _after2[x].step(this.state);
/*     */     }
/* 469 */     return returnval;
/*     */   }
/*     */ 
/*     */   protected Steppable[] increaseSubsteps(Steppable[] substeps)
/*     */   {
/* 476 */     Steppable[] newsubstep = new Steppable[substeps.length * 2 + 1];
/* 477 */     System.arraycopy(substeps, 0, newsubstep, 0, substeps.length);
/* 478 */     return newsubstep;
/*     */   }
/*     */ 
/*     */   public boolean scheduleImmediatelyBefore(Steppable event)
/*     */   {
/* 494 */     return _scheduleImmediate(false, event);
/*     */   }
/*     */ 
/*     */   public boolean scheduleImmediatelyAfter(Steppable event)
/*     */   {
/* 509 */     return _scheduleImmediate(true, event);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public boolean scheduleImmediate(boolean immediatelyAfter, Steppable event)
/*     */   {
/* 526 */     return _scheduleImmediate(immediatelyAfter, event);
/*     */   }
/*     */ 
/*     */   boolean _scheduleImmediate(boolean immediatelyAfter, Steppable event)
/*     */   {
/* 531 */     synchronized (this.state.schedule)
/*     */     {
/* 533 */       if ((event == null) || (this.state.schedule.getTime() >= (1.0D / 0.0D)))
/*     */       {
/* 535 */         if (event == null)
/*     */         {
/* 537 */           throw new IllegalArgumentException("The provided Steppable is null");
/*     */         }
/* 539 */         if (this.state.schedule.getTime() >= (1.0D / 0.0D))
/*     */         {
/* 541 */           throw new IllegalArgumentException("The simulation is over and the item cannot be scheduled.");
/*     */         }
/*     */       }
/* 544 */       if (immediatelyAfter)
/*     */       {
/* 546 */         if (this.afterSize == this.after.length)
/*     */         {
/* 548 */           this.after = increaseSubsteps(this.after);
/* 549 */           this.after2 = new Steppable[this.after.length];
/*     */         }
/* 551 */         this.after[(this.afterSize++)] = event;
/*     */       }
/*     */       else
/*     */       {
/* 555 */         if (this.beforeSize == this.before.length)
/*     */         {
/* 557 */           this.before = increaseSubsteps(this.before);
/* 558 */           this.before2 = new Steppable[this.before.length];
/*     */         }
/* 560 */         this.before[(this.beforeSize++)] = event;
/*     */       }
/*     */     }
/* 563 */     return true;
/*     */   }
/*     */ 
/*     */   public Stoppable scheduleRepeatingImmediatelyBefore(Steppable event)
/*     */   {
/* 580 */     return _scheduleImmediateRepeat(false, event);
/*     */   }
/*     */ 
/*     */   public Stoppable scheduleRepeatingImmediatelyAfter(Steppable event)
/*     */   {
/* 598 */     return _scheduleImmediateRepeat(true, event);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Stoppable scheduleImmediateRepeat(boolean immediatelyAfter, Steppable event)
/*     */   {
/* 617 */     return _scheduleImmediateRepeat(immediatelyAfter, event);
/*     */   }
/*     */ 
/*     */   Stoppable _scheduleImmediateRepeat(boolean immediatelyAfter, Steppable event)
/*     */   {
/* 622 */     Repeat r = new Repeat(immediatelyAfter, event);
/* 623 */     if (scheduleImmediate(immediatelyAfter, r)) return r;
/* 624 */     return null;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public boolean scheduleAtExtreme(Steppable event, boolean atEnd)
/*     */   {
/* 636 */     return _scheduleAtExtreme(event, atEnd);
/*     */   }
/*     */ 
/*     */   boolean _scheduleAtExtreme(Steppable event, boolean atEnd)
/*     */   {
/* 642 */     synchronized (this.state.schedule)
/*     */     {
/* 644 */       if ((event == null) || (this.state.schedule.getTime() >= (1.0D / 0.0D)))
/*     */       {
/* 646 */         if (event == null)
/*     */         {
/* 648 */           throw new IllegalArgumentException("The provided Steppable is null");
/*     */         }
/* 650 */         if (this.state.schedule.getTime() >= (1.0D / 0.0D))
/*     */         {
/* 652 */           throw new IllegalArgumentException("The simulation is over and the item cannot be scheduled.");
/*     */         }
/*     */       }
/* 655 */       if (atEnd)
/*     */       {
/* 657 */         if (this.finishSize == this.finish.length)
/*     */         {
/* 659 */           this.finish = increaseSubsteps(this.finish);
/* 660 */           this.finish2 = new Steppable[this.finish.length];
/*     */         }
/* 662 */         this.finish[(this.finishSize++)] = event;
/*     */       }
/*     */       else
/*     */       {
/* 666 */         if (this.startSize == this.start.length)
/*     */         {
/* 668 */           this.start = increaseSubsteps(this.start);
/* 669 */           this.start2 = new Steppable[this.start.length];
/*     */         }
/* 671 */         this.start[(this.startSize++)] = event;
/*     */       }
/*     */     }
/* 674 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean scheduleAtStart(Steppable event)
/*     */   {
/* 681 */     return _scheduleAtExtreme(event, false);
/*     */   }
/*     */ 
/*     */   public boolean scheduleAtEnd(Steppable event)
/*     */   {
/* 688 */     return _scheduleAtExtreme(event, true);
/*     */   }
/*     */ 
/*     */   class Repeat
/*     */     implements Steppable, Stoppable
/*     */   {
/*     */     protected boolean immediatelyAfter;
/*     */     protected Steppable step;
/*     */ 
/*     */     public Repeat(boolean immediatelyAfter, Steppable step)
/*     */     {
/* 705 */       this.immediatelyAfter = immediatelyAfter;
/* 706 */       this.step = step;
/*     */     }
/*     */ 
/*     */     public synchronized void step(SimState state)
/*     */     {
/* 711 */       if (this.step != null)
/*     */       {
/*     */         try
/*     */         {
/* 715 */           GUIState.this.scheduleImmediate(this.immediatelyAfter, this);
/*     */         } catch (IllegalArgumentException e) {
/*     */         }
/* 718 */         this.step.step(state);
/*     */       }
/*     */     }
/*     */ 
/*     */     public synchronized void stop()
/*     */     {
/* 724 */       this.step = null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.display.GUIState
 * JD-Core Version:    0.6.2
 */