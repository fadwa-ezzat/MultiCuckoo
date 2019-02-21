/*     */ package sim.display;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.util.Bag;
/*     */ 
/*     */ public class SimpleController
/*     */   implements Controller
/*     */ {
/*     */   GUIState simulation;
/*  77 */   long randomSeed = 0L;
/*     */   boolean displayInspectors;
/*     */   Thread playThread;
/* 117 */   final Object playThreadLock = new Object();
/*     */ 
/* 120 */   boolean threadShouldStop = false;
/*     */   public static final int PS_STOPPED = 0;
/*     */   public static final int PS_PLAYING = 1;
/*     */   public static final int PS_PAUSED = 2;
/* 150 */   int playState = 0;
/*     */ 
/* 193 */   boolean isClosing = false;
/*     */ 
/* 195 */   final Object isClosingLock = new Object();
/*     */ 
/* 225 */   boolean incrementSeedOnStop = true;
/*     */ 
/* 379 */   Runnable blocker = new Runnable() {
/* 379 */     public void run() {  }  } ;
/*     */ 
/* 522 */   Vector frameList = new Vector();
/*     */ 
/* 610 */   Vector inspectorStoppables = new Vector();
/*     */ 
/* 615 */   WeakHashMap allInspectors = new WeakHashMap();
/*     */ 
/*     */   public GUIState getSimulation()
/*     */   {
/*  49 */     return this.simulation;
/*     */   }
/*     */ 
/*     */   public SimpleController(GUIState simulation)
/*     */   {
/*  81 */     this(simulation, true);
/*  82 */     this.randomSeed = simulation.state.seed();
/*     */   }
/*     */ 
/*     */   public SimpleController(final GUIState simulation, boolean displayInspectors)
/*     */   {
/*  88 */     this.displayInspectors = displayInspectors;
/*  89 */     this.simulation = simulation;
/*     */ 
/*  92 */     invokeInSwing(new Runnable() { public void run() { simulation.init(SimpleController.this); }
/*     */ 
/*     */     });
/*  95 */     Console.allControllers.put(this, this);
/*     */   }
/*     */ 
/*     */   void invokeInSwing(Runnable runnable)
/*     */   {
/* 102 */     if (SwingUtilities.isEventDispatchThread()) runnable.run(); else
/*     */       try
/*     */       {
/* 105 */         SwingUtilities.invokeAndWait(runnable);
/*     */       }
/*     */       catch (InterruptedException e)
/*     */       {
/*     */       }
/*     */       catch (InvocationTargetException e)
/*     */       {
/*     */       }
/*     */   }
/*     */ 
/*     */   boolean getThreadShouldStop()
/*     */   {
/* 125 */     synchronized (this.playThreadLock)
/*     */     {
/* 127 */       return this.threadShouldStop;
/*     */     }
/*     */   }
/*     */ 
/*     */   void setThreadShouldStop(boolean stop)
/*     */   {
/* 134 */     synchronized (this.playThreadLock)
/*     */     {
/* 136 */       this.threadShouldStop = stop;
/*     */     }
/*     */   }
/*     */ 
/*     */   void setPlayState(int state)
/*     */   {
/* 155 */     synchronized (this.playThreadLock)
/*     */     {
/* 157 */       this.playState = state;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getPlayState()
/*     */   {
/* 164 */     synchronized (this.playThreadLock)
/*     */     {
/* 166 */       return this.playState;
/*     */     }
/*     */   }
/*     */ 
/*     */   void startSimulation()
/*     */   {
/* 178 */     removeAllInspectors(true);
/* 179 */     this.simulation.state.setSeed(this.randomSeed);
/* 180 */     this.simulation.start();
/*     */   }
/*     */ 
/*     */   public void doClose()
/*     */   {
/* 202 */     synchronized (this.isClosingLock)
/*     */     {
/* 204 */       if (this.isClosing) return;
/* 205 */       this.isClosing = true;
/*     */     }
/* 207 */     pressStop();
/* 208 */     this.simulation.quit();
/* 209 */     Console.allControllers.remove(this);
/* 210 */     if (Console.allControllers.size() == 0) Console.doQuit();
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setIncrementSeedOnPlay(boolean val)
/*     */   {
/* 216 */     setIncrementSeedOnStop(val);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public boolean getIncrementSeedOnPlay()
/*     */   {
/* 222 */     return getIncrementSeedOnStop();
/*     */   }
/*     */ 
/*     */   public void setIncrementSeedOnStop(boolean val)
/*     */   {
/* 228 */     this.incrementSeedOnStop = val;
/*     */   }
/*     */ 
/*     */   public boolean getIncrementSeedOnStop()
/*     */   {
/* 233 */     return this.incrementSeedOnStop;
/*     */   }
/*     */ 
/*     */   public synchronized void pressStop()
/*     */   {
/* 243 */     if (getPlayState() != 0)
/*     */     {
/* 245 */       killPlayThread();
/* 246 */       this.simulation.finish();
/* 247 */       stopAllInspectors(true);
/* 248 */       setPlayState(0);
/*     */ 
/* 251 */       if (getIncrementSeedOnStop())
/*     */       {
/* 253 */         this.randomSeed = ((int)(this.randomSeed + 1L));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void pressPause()
/*     */   {
/* 262 */     pressPause(true);
/*     */   }
/*     */ 
/*     */   synchronized void pressPause(boolean shouldStartSimulationIfStopped)
/*     */   {
/* 273 */     if (getPlayState() == 1)
/*     */     {
/* 275 */       killPlayThread();
/* 276 */       setPlayState(2);
/* 277 */       refresh();
/*     */     }
/* 279 */     else if (getPlayState() == 2)
/*     */     {
/* 281 */       spawnPlayThread();
/* 282 */       setPlayState(1);
/*     */     }
/* 284 */     else if (getPlayState() == 0)
/*     */     {
/* 290 */       if (shouldStartSimulationIfStopped) startSimulation();
/*     */ 
/* 292 */       setPlayState(2);
/* 293 */       refresh();
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void pressPlay()
/*     */   {
/* 301 */     if (getPlayState() == 0)
/*     */     {
/* 304 */       startSimulation();
/* 305 */       spawnPlayThread();
/* 306 */       setPlayState(1);
/*     */     }
/* 308 */     else if (getPlayState() == 2)
/*     */     {
/* 311 */       if (!this.simulation.step())
/* 312 */         pressStop();
/* 313 */       refresh();
/*     */     }
/*     */   }
/*     */ 
/*     */   synchronized void killPlayThread()
/*     */   {
/* 336 */     setThreadShouldStop(true);
/*     */     try
/*     */     {
/* 341 */       if (this.playThread != null)
/*     */       {
/*     */         do
/*     */         {
/*     */           try
/*     */           {
/* 359 */             synchronized (this.simulation.state.schedule)
/*     */             {
/* 361 */               this.playThread.interrupt();
/*     */             }
/*     */           } catch (SecurityException ex) {
/*     */           }
/* 365 */           this.playThread.join(50L);
/*     */         }
/* 367 */         while (this.playThread.isAlive());
/* 368 */         this.playThread = null;
/*     */       }
/*     */     }
/*     */     catch (InterruptedException e) {
/* 372 */       System.err.println("WARNING: This should never happen: " + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   synchronized void spawnPlayThread()
/*     */   {
/* 396 */     setThreadShouldStop(false);
/*     */ 
/* 399 */     Runnable run = new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try
/*     */         {
/* 408 */           if ((!Thread.currentThread().isInterrupted()) && (!SimpleController.this.getThreadShouldStop()))
/*     */           {
/*     */             try
/*     */             {
/* 413 */               SwingUtilities.invokeAndWait(SimpleController.this.blocker);
/*     */             }
/*     */             catch (InterruptedException e)
/*     */             {
/*     */               try
/*     */               {
/* 419 */                 Thread.currentThread().interrupt();
/*     */               }
/*     */               catch (SecurityException ex) {
/*     */               }
/*     */             }
/*     */             catch (InvocationTargetException e) {
/* 425 */               System.err.println("This should never happen: " + e);
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 429 */               e.printStackTrace();
/*     */             }
/*     */           }
/*     */ 
/* 433 */           SimpleController.this.simulation.state.nameThread();
/*     */ 
/* 437 */           boolean result = true;
/*     */ 
/* 441 */           while (!SimpleController.this.getThreadShouldStop())
/*     */           {
/* 444 */             result = SimpleController.this.simulation.step();
/*     */ 
/* 456 */             if ((!Thread.currentThread().isInterrupted()) && (!SimpleController.this.getThreadShouldStop()))
/*     */             {
/*     */               try
/*     */               {
/* 461 */                 SwingUtilities.invokeAndWait(SimpleController.this.blocker);
/*     */               }
/*     */               catch (InterruptedException e)
/*     */               {
/*     */                 try
/*     */                 {
/* 467 */                   Thread.currentThread().interrupt();
/*     */                 }
/*     */                 catch (SecurityException ex) {
/*     */                 }
/*     */               }
/*     */               catch (InvocationTargetException e) {
/* 473 */                 System.err.println("This should never happen" + e);
/*     */               }
/*     */               catch (Exception e)
/*     */               {
/* 477 */                 e.printStackTrace();
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 482 */             if ((!result) || (SimpleController.this.getThreadShouldStop()))
/*     */             {
/*     */               break;
/*     */             }
/*     */ 
/* 491 */             if (!result)
/* 492 */               SwingUtilities.invokeLater(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/*     */                   try
/*     */                   {
/* 498 */                     SimpleController.this.pressStop();
/*     */                   }
/*     */                   catch (Exception e)
/*     */                   {
/* 502 */                     System.err.println("This should never happen: " + e);
/*     */                   }
/*     */                 } } );
/*     */           }
/*     */         }
/*     */         catch (Exception e) {
/* 508 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     };
/* 511 */     this.playThread = new Thread(run);
/* 512 */     this.playThread.start();
/*     */   }
/*     */ 
/*     */   public synchronized boolean registerFrame(JFrame frame)
/*     */   {
/* 530 */     this.frameList.add(frame);
/* 531 */     return true;
/*     */   }
/*     */ 
/*     */   public synchronized boolean unregisterFrame(JFrame frame)
/*     */   {
/* 537 */     this.frameList.removeElement(frame);
/* 538 */     return true;
/*     */   }
/*     */ 
/*     */   public synchronized boolean unregisterAllFrames()
/*     */   {
/* 544 */     this.frameList.removeAllElements();
/* 545 */     return true;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public synchronized void doChangeCode(Runnable r)
/*     */   {
/* 553 */     if (this.playThread != null)
/*     */     {
/* 555 */       killPlayThread();
/* 556 */       r.run();
/* 557 */       spawnPlayThread();
/*     */     }
/*     */     else {
/* 560 */       r.run();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void refresh()
/*     */   {
/* 567 */     Enumeration e = this.frameList.elements();
/* 568 */     while (e.hasMoreElements()) {
/* 569 */       ((JFrame)e.nextElement()).getContentPane().repaint();
/*     */     }
/*     */ 
/* 572 */     Iterator i = this.allInspectors.keySet().iterator();
/* 573 */     while (i.hasNext())
/*     */     {
/* 575 */       Inspector c = (Inspector)i.next();
/* 576 */       if ((c != null) && (!c.isStopped()))
/*     */       {
/* 578 */         if (c.isVolatile())
/*     */         {
/* 580 */           c.updateInspector();
/* 581 */           c.repaint();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setInspectors(final Bag inspectors, Bag names)
/*     */   {
/* 621 */     if (!this.displayInspectors) return;
/*     */ 
/* 624 */     removeAllInspectors(false);
/*     */ 
/* 627 */     if (inspectors.numObjs != names.numObjs) {
/* 628 */       throw new RuntimeException("Number of inspectors and names do not match");
/*     */     }
/*     */ 
/* 631 */     for (int x = 0; x < inspectors.numObjs; x++)
/*     */     {
/* 633 */       if (inspectors.objs[x] != null)
/*     */       {
/* 635 */         final int xx = x;
/* 636 */         Steppable stepper = new Steppable()
/*     */         {
/*     */           public void step(final SimState state)
/*     */           {
/* 640 */             SwingUtilities.invokeLater(new Runnable()
/*     */             {
/* 642 */               Inspector inspector = (Inspector)SimpleController.4.this.val$inspectors.objs[SimpleController.4.this.val$xx];
/*     */ 
/*     */               public void run() {
/* 645 */                 synchronized (state.schedule)
/*     */                 {
/* 649 */                   if (this.inspector.isVolatile())
/*     */                   {
/* 651 */                     this.inspector.updateInspector();
/* 652 */                     this.inspector.repaint();
/*     */                   }
/*     */                 }
/*     */               }
/*     */             });
/*     */           }
/*     */         };
/* 660 */         Stoppable stopper = null;
/*     */         try
/*     */         {
/* 663 */           stopper = ((Inspector)inspectors.objs[x]).reviseStopper(this.simulation.scheduleRepeatingImmediatelyAfter(stepper));
/* 664 */           this.inspectorStoppables.addElement(stopper);
/*     */         }
/*     */         catch (IllegalArgumentException ex)
/*     */         {
/*     */         }
/* 669 */         registerInspector((Inspector)inspectors.objs[x], stopper);
/*     */ 
/* 671 */         JFrame frame = ((Inspector)inspectors.objs[x]).createFrame(stopper);
/* 672 */         frame.setVisible(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void registerInspector(Inspector inspector, Stoppable stopper)
/*     */   {
/* 683 */     if (!this.displayInspectors) return;
/* 684 */     this.allInspectors.put(inspector, new WeakReference(stopper));
/*     */   }
/*     */ 
/*     */   public void stopAllInspectors(boolean killDraggedOutWindowsToo)
/*     */   {
/* 692 */     Iterator i = this.allInspectors.keySet().iterator();
/* 693 */     while (i.hasNext())
/*     */     {
/* 695 */       Inspector insp = (Inspector)i.next();
/* 696 */       insp.updateInspector();
/* 697 */       insp.repaint();
/*     */     }
/*     */ 
/* 702 */     for (int x = 0; x < this.inspectorStoppables.size(); x++)
/*     */     {
/* 704 */       Stoppable stopper = (Stoppable)this.inspectorStoppables.elementAt(x);
/* 705 */       if (stopper != null) stopper.stop();
/*     */ 
/*     */     }
/*     */ 
/* 709 */     if (killDraggedOutWindowsToo)
/*     */     {
/* 711 */       i = this.allInspectors.keySet().iterator();
/* 712 */       while (i.hasNext())
/*     */       {
/* 714 */         Inspector insp = (Inspector)i.next();
/* 715 */         Stoppable stopper = (Stoppable)this.allInspectors.get(insp);
/* 716 */         if (stopper != null) stopper.stop();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeAllInspectors(boolean killDraggedOutWindowsToo)
/*     */   {
/* 725 */     stopAllInspectors(killDraggedOutWindowsToo);
/* 726 */     if (killDraggedOutWindowsToo)
/*     */     {
/* 729 */       Iterator i = this.allInspectors.keySet().iterator();
/* 730 */       while (i.hasNext())
/*     */       {
/* 732 */         Component inspector = (Component)i.next();
/*     */ 
/* 735 */         while ((inspector != null) && (!(inspector instanceof JFrame)))
/* 736 */           inspector = inspector.getParent();
/* 737 */         if (inspector != null)
/* 738 */           ((JFrame)inspector).dispose();
/*     */       }
/* 740 */       this.allInspectors = new WeakHashMap();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean doNew()
/*     */   {
/* 747 */     return Console.doNew(null, false);
/*     */   }
/*     */ 
/*     */   public ArrayList getAllInspectors()
/*     */   {
/* 758 */     ArrayList list = new ArrayList();
/* 759 */     Iterator i = this.allInspectors.keySet().iterator();
/* 760 */     while (i.hasNext())
/* 761 */       list.add((Inspector)i.next());
/* 762 */     return list;
/*     */   }
/*     */ 
/*     */   public synchronized ArrayList getAllFrames()
/*     */   {
/* 768 */     return new ArrayList(this.frameList);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  56 */       System.setProperty("Quaqua.TabbedPane.design", "auto");
/*  57 */       System.setProperty("Quaqua.visualMargin", "1,1,1,1");
/*  58 */       UIManager.put("Panel.opaque", Boolean.TRUE);
/*  59 */       UIManager.setLookAndFeel((String)Class.forName("ch.randelshofer.quaqua.QuaquaManager", true, Thread.currentThread().getContextClassLoader()).getMethod("getLookAndFeelClassName", (Class[])null).invoke(null, (Object[])null));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  67 */       System.setProperty("apple.awt.showGrowBox", "true");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.display.SimpleController
 * JD-Core Version:    0.6.2
 */