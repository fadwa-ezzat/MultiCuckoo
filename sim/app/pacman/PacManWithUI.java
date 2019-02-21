/*     */ package sim.app.pacman;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
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
/*     */ import sim.display.RateAdjuster;
/*     */ import sim.display.SimpleController;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*     */ import sim.portrayal.grid.ValueGridPortrayal2D;
/*     */ import sim.portrayal.simple.FacetedPortrayal2D;
/*     */ import sim.portrayal.simple.ImagePortrayal2D;
/*     */ import sim.portrayal.simple.OvalPortrayal2D;
/*     */ 
/*     */ public class PacManWithUI extends GUIState
/*     */ {
/*     */   public Display2D display;
/*     */   public JFrame displayFrame;
/*  42 */   public double FRAMES_PER_SECOND = 60.0D;
/*     */ 
/*  67 */   ValueGridPortrayal2D mazePortrayal = new ValueGridPortrayal2D();
/*  68 */   ContinuousPortrayal2D agentPortrayal = new ContinuousPortrayal2D();
/*  69 */   ContinuousPortrayal2D dotPortrayal = new ContinuousPortrayal2D();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  28 */     new PacManWithUI().createController();
/*     */   }
/*     */ 
/*     */   public PacManWithUI()
/*     */   {
/*  33 */     super(new PacMan(System.currentTimeMillis()));
/*     */   }
/*     */ 
/*     */   public PacManWithUI(SimState state)
/*     */   {
/*  38 */     super(state);
/*     */   }
/*     */ 
/*     */   public Controller createController()
/*     */   {
/*  48 */     SimpleController c = new SimpleController(this);
/*  49 */     c.pressPlay();
/*  50 */     return c;
/*     */   }
/*     */   public static String getName() {
/*  53 */     return "Pac Man";
/*     */   }
/*     */ 
/*     */   public void start() {
/*  57 */     super.start();
/*  58 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  63 */     super.load(state);
/*  64 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  73 */     PacMan pacman = (PacMan)this.state;
/*     */ 
/*  76 */     this.agentPortrayal.setField(pacman.agents);
/*     */ 
/*  80 */     this.agentPortrayal.setPortrayalForClass(Pac.class, new PacPortrayal(pacman, Color.yellow)
/*     */     {
/*     */       public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
/*  83 */         if (((Pac)object).tag == 0) this.color = Color.yellow; else this.color = Color.green; super.draw(object, graphics, info);
/*     */       }
/*     */     });
/*  87 */     this.agentPortrayal.setPortrayalForClass(Blinky.class, new FacetedPortrayal2D(new SimplePortrayal2D[] { new ImagePortrayal2D(getClass(), "images/blinkyu.png", 2.0D), new ImagePortrayal2D(getClass(), "images/blinkyl.png", 2.0D), new ImagePortrayal2D(getClass(), "images/blinkyd.png", 2.0D), new ImagePortrayal2D(getClass(), "images/blinkyr.png", 2.0D), new ImagePortrayal2D(getClass(), "images/frightened.png", 2.0D), new ImagePortrayal2D(getClass(), "images/frightened2.png", 2.0D) }));
/*     */ 
/*  99 */     this.agentPortrayal.setPortrayalForClass(Pinky.class, new FacetedPortrayal2D(new SimplePortrayal2D[] { new ImagePortrayal2D(getClass(), "images/pinkyu.png", 2.0D), new ImagePortrayal2D(getClass(), "images/pinkyl.png", 2.0D), new ImagePortrayal2D(getClass(), "images/pinkyd.png", 2.0D), new ImagePortrayal2D(getClass(), "images/pinkyr.png", 2.0D), new ImagePortrayal2D(getClass(), "images/frightened.png", 2.0D), new ImagePortrayal2D(getClass(), "images/frightened2.png", 2.0D) }));
/*     */ 
/* 112 */     this.agentPortrayal.setPortrayalForClass(Inky.class, new FacetedPortrayal2D(new SimplePortrayal2D[] { new ImagePortrayal2D(getClass(), "images/inkyu.png", 2.0D), new ImagePortrayal2D(getClass(), "images/inkyl.png", 2.0D), new ImagePortrayal2D(getClass(), "images/inkyd.png", 2.0D), new ImagePortrayal2D(getClass(), "images/inkyr.png", 2.0D), new ImagePortrayal2D(getClass(), "images/frightened.png", 2.0D), new ImagePortrayal2D(getClass(), "images/frightened2.png", 2.0D) }));
/*     */ 
/* 125 */     this.agentPortrayal.setPortrayalForClass(Clyde.class, new FacetedPortrayal2D(new SimplePortrayal2D[] { new ImagePortrayal2D(getClass(), "images/clydeu.png", 2.0D), new ImagePortrayal2D(getClass(), "images/clydel.png", 2.0D), new ImagePortrayal2D(getClass(), "images/clyded.png", 2.0D), new ImagePortrayal2D(getClass(), "images/clyder.png", 2.0D), new ImagePortrayal2D(getClass(), "images/frightened.png", 2.0D), new ImagePortrayal2D(getClass(), "images/frightened2.png", 2.0D) }));
/*     */ 
/* 137 */     this.dotPortrayal.setField(pacman.dots);
/*     */ 
/* 140 */     this.dotPortrayal.setPortrayalForClass(Energizer.class, new OvalPortrayal2D(Color.white, 1.0D));
/*     */ 
/* 143 */     this.dotPortrayal.setPortrayalForClass(Dot.class, new OvalPortrayal2D(Color.white, 0.4D));
/*     */ 
/* 146 */     this.mazePortrayal.setPortrayalForAll(new MazeCellPortrayal(pacman.maze));
/* 147 */     this.mazePortrayal.setField(pacman.maze);
/*     */ 
/* 150 */     scheduleRepeatingImmediatelyAfter(new RateAdjuster(this.FRAMES_PER_SECOND));
/*     */ 
/* 153 */     this.display.reset();
/*     */ 
/* 156 */     this.display.repaint();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/* 163 */     super.init(c);
/*     */ 
/* 166 */     this.display = new Display2D(448.0D, 560.0D, this) {
/*     */       public void createConsoleMenu() {
/*     */       }
/*     */ 
/*     */       public void quit() {
/* 171 */         super.quit();
/* 172 */         ((SimpleController)this.val$c).doClose();
/*     */       }
/*     */     };
/* 176 */     this.display.setBackdrop(Color.black);
/*     */ 
/* 178 */     this.displayFrame = this.display.createFrame();
/* 179 */     this.displayFrame.setTitle("MASON Pac Man");
/* 180 */     c.registerFrame(this.displayFrame);
/* 181 */     this.displayFrame.setVisible(true);
/*     */ 
/* 184 */     this.display.attach(this.mazePortrayal, "Maze");
/*     */ 
/* 186 */     this.display.attach(this.dotPortrayal, "Dots", 8.0D, 8.0D, true);
/* 187 */     this.display.attach(this.agentPortrayal, "Agents", 8.0D, 8.0D, true);
/* 188 */     this.display.attach(new Overlay(this), "Overlay");
/*     */ 
/* 192 */     this.display.remove(this.display.header);
/*     */ 
/* 194 */     this.display.removeListeners();
/*     */ 
/* 196 */     this.display.display.setVerticalScrollBarPolicy(21);
/* 197 */     this.display.display.setHorizontalScrollBarPolicy(31);
/*     */ 
/* 199 */     this.displayFrame.setDefaultCloseOperation(2);
/*     */ 
/* 201 */     this.displayFrame.setResizable(false);
/*     */ 
/* 203 */     this.display.insideDisplay.setupHints(true, false, false);
/*     */ 
/* 206 */     this.displayFrame.pack();
/*     */ 
/* 209 */     addListeners(this.display);
/*     */   }
/*     */ 
/*     */   public void addListeners(final Display2D display)
/*     */   {
/* 216 */     final PacMan pacman = (PacMan)this.state;
/* 217 */     final SimpleController cont = (SimpleController)this.controller;
/*     */ 
/* 220 */     display.setFocusable(true);
/*     */ 
/* 223 */     this.displayFrame.addWindowListener(new WindowAdapter()
/*     */     {
/*     */       public void windowActivated(WindowEvent e)
/*     */       {
/* 227 */         display.requestFocusInWindow();
/*     */       }
/*     */     });
/* 232 */     display.requestFocusInWindow();
/*     */ 
/* 235 */     display.addKeyListener(new KeyAdapter()
/*     */     {
/*     */       public void keyPressed(KeyEvent e)
/*     */       {
/* 239 */         int c = e.getKeyCode();
/* 240 */         switch (c)
/*     */         {
/*     */         case 38:
/* 243 */           pacman.actions[0] = 0;
/* 244 */           break;
/*     */         case 40:
/* 246 */           pacman.actions[0] = 2;
/* 247 */           break;
/*     */         case 37:
/* 249 */           pacman.actions[0] = 3;
/* 250 */           break;
/*     */         case 39:
/* 252 */           pacman.actions[0] = 1;
/* 253 */           break;
/*     */         case 87:
/* 255 */           pacman.actions[1] = 0;
/* 256 */           break;
/*     */         case 83:
/* 258 */           pacman.actions[1] = 2;
/* 259 */           break;
/*     */         case 65:
/* 261 */           pacman.actions[1] = 3;
/* 262 */           break;
/*     */         case 68:
/* 264 */           pacman.actions[1] = 1;
/* 265 */           break;
/*     */         case 82:
/* 267 */           cont.pressStop();
/* 268 */           cont.pressPlay();
/* 269 */           break;
/*     */         case 80:
/* 271 */           cont.pressPause();
/* 272 */           break;
/*     */         case 77:
/* 274 */           if (cont.getPlayState() != 2)
/* 275 */             cont.pressPause();
/* 276 */           cont.doNew();
/*     */ 
/* 282 */           display.requestFocusInWindow();
/* 283 */           break;
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 295 */     super.quit();
/* 296 */     this.display = null;
/*     */ 
/* 298 */     if (this.displayFrame != null) { JFrame f = this.displayFrame; this.displayFrame = null; f.dispose();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pacman.PacManWithUI
 * JD-Core Version:    0.6.2
 */