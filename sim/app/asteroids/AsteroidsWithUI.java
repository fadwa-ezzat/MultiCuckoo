/*     */ package sim.app.asteroids;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JScrollPane;
/*     */ import sim.display.Controller;
/*     */ import sim.display.Display2D;
/*     */ import sim.display.Display2D.InnerDisplay2D;
/*     */ import sim.display.GUIState;
/*     */ import sim.display.SimpleController;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*     */ 
/*     */ public class AsteroidsWithUI extends GUIState
/*     */ {
/*  25 */   public double FRAMES_PER_SECOND = 60.0D;
/*     */   public Display2D display;
/*     */   public JFrame displayFrame;
/*  34 */   public ContinuousPortrayal2D fieldPortrayal = new ContinuousPortrayal2D();
/*     */ 
/*  37 */   public Overlay overlay = new Overlay(this);
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  42 */     new AsteroidsWithUI().createController();
/*     */   }
/*     */ 
/*     */   public Controller createController()
/*     */   {
/*  48 */     SimpleController c = new SimpleController(this);
/*  49 */     c.pressPlay();
/*  50 */     return c;
/*     */   }
/*     */ 
/*     */   public AsteroidsWithUI()
/*     */   {
/*  56 */     super(new Asteroids(System.currentTimeMillis()));
/*     */   }
/*     */ 
/*     */   public AsteroidsWithUI(SimState state)
/*     */   {
/*  62 */     super(state);
/*     */   }
/*     */ 
/*     */   public static String getName() {
/*  66 */     return "Asteroids";
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  71 */     super.start();
/*  72 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  78 */     super.load(state);
/*  79 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  87 */     Asteroids asteroids = (Asteroids)this.state;
/*     */ 
/*  89 */     this.fieldPortrayal.setField(asteroids.field);
/*  90 */     this.fieldPortrayal.setDisplayingToroidally(true);
/*     */ 
/*  92 */     scheduleRepeatingImmediatelyAfter(new RateAdjuster(this.FRAMES_PER_SECOND));
/*     */ 
/*  95 */     this.display.reset();
/*     */ 
/*  98 */     this.display.repaint();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/* 106 */     super.init(c);
/*     */ 
/* 109 */     this.display = new Display2D(750.0D, 750.0D, this)
/*     */     {
/*     */       public void quit()
/*     */       {
/* 113 */         super.quit();
/* 114 */         ((SimpleController)this.val$c).doClose();
/*     */       }
/*     */     };
/* 118 */     this.display.setBackdrop(Color.black);
/*     */ 
/* 121 */     this.displayFrame = this.display.createFrame();
/* 122 */     this.displayFrame.setTitle("Asteroids");
/* 123 */     c.registerFrame(this.displayFrame);
/* 124 */     this.displayFrame.setVisible(true);
/*     */ 
/* 126 */     this.display.attach(this.fieldPortrayal, "Asteroids");
/* 127 */     this.display.attach(this.overlay, "Overlay");
/*     */ 
/* 133 */     this.display.remove(this.display.header);
/*     */ 
/* 135 */     this.display.removeListeners();
/*     */ 
/* 137 */     this.display.display.setVerticalScrollBarPolicy(21);
/* 138 */     this.display.display.setHorizontalScrollBarPolicy(31);
/*     */ 
/* 140 */     this.displayFrame.setDefaultCloseOperation(2);
/*     */ 
/* 142 */     this.displayFrame.setResizable(false);
/*     */ 
/* 144 */     this.display.insideDisplay.setupHints(true, false, false);
/*     */ 
/* 146 */     this.displayFrame.pack();
/*     */ 
/* 149 */     addListeners(this.display);
/*     */   }
/*     */ 
/*     */   public void addListeners(final Display2D display)
/*     */   {
/* 157 */     final Asteroids asteroids = (Asteroids)this.state;
/* 158 */     final SimpleController cont = (SimpleController)this.controller;
/*     */ 
/* 161 */     display.setFocusable(true);
/*     */ 
/* 164 */     this.displayFrame.addWindowListener(new WindowAdapter()
/*     */     {
/*     */       public void windowActivated(WindowEvent e)
/*     */       {
/* 168 */         display.requestFocusInWindow();
/*     */       }
/*     */     });
/* 173 */     display.requestFocusInWindow();
/*     */ 
/* 175 */     display.addKeyListener(new KeyAdapter()
/*     */     {
/*     */       public void keyReleased(KeyEvent e)
/*     */       {
/* 179 */         int c = e.getKeyCode();
/* 180 */         switch (c)
/*     */         {
/*     */         case 38:
/* 183 */           asteroids.actions[0] &= -3;
/* 184 */           break;
/*     */         case 40:
/* 186 */           asteroids.actions[0] &= -17;
/* 187 */           break;
/*     */         case 37:
/* 189 */           asteroids.actions[0] &= -2;
/* 190 */           break;
/*     */         case 39:
/* 192 */           asteroids.actions[0] &= -5;
/* 193 */           break;
/*     */         case 32:
/* 195 */           asteroids.actions[0] &= -9;
/* 196 */           break;
/*     */         case 33:
/*     */         case 34:
/*     */         case 35:
/*     */         case 36:
/*     */         }
/*     */       }
/*     */ 
/*     */       public void keyPressed(KeyEvent e)
/*     */       {
/* 221 */         int c = e.getKeyCode();
/* 222 */         switch (c)
/*     */         {
/*     */         case 38:
/* 225 */           asteroids.actions[0] |= 2;
/* 226 */           break;
/*     */         case 40:
/* 228 */           asteroids.actions[0] |= 16;
/* 229 */           break;
/*     */         case 37:
/* 231 */           asteroids.actions[0] |= 1;
/* 232 */           break;
/*     */         case 39:
/* 234 */           asteroids.actions[0] |= 4;
/* 235 */           break;
/*     */         case 32:
/* 237 */           asteroids.actions[0] |= 8;
/* 238 */           break;
/*     */         case 82:
/* 257 */           cont.pressStop();
/* 258 */           cont.pressPlay();
/* 259 */           break;
/*     */         case 80:
/* 261 */           cont.pressPause();
/* 262 */           break;
/*     */         case 77:
/* 264 */           if (cont.getPlayState() != 2)
/* 265 */             cont.pressPause();
/* 266 */           cont.doNew();
/*     */ 
/* 272 */           display.requestFocusInWindow();
/* 273 */           break;
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 284 */     super.quit();
/*     */ 
/* 286 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 287 */     this.displayFrame = null;
/* 288 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.asteroids.AsteroidsWithUI
 * JD-Core Version:    0.6.2
 */