/*     */ package sim.app.flockers;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.awt.Color;
/*     */ import javax.swing.JFrame;
/*     */ import sim.display.Controller;
/*     */ import sim.display.Display2D;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*     */ import sim.portrayal.simple.AdjustablePortrayal2D;
/*     */ import sim.portrayal.simple.MovablePortrayal2D;
/*     */ import sim.portrayal.simple.OrientedPortrayal2D;
/*     */ import sim.portrayal.simple.TrailedPortrayal2D;
/*     */ import sim.util.Bag;
/*     */ 
/*     */ public class FlockersWithUI extends GUIState
/*     */ {
/*     */   public Display2D display;
/*     */   public JFrame displayFrame;
/*  28 */   ContinuousPortrayal2D flockersPortrayal = new ContinuousPortrayal2D();
/*     */ 
/*  31 */   ContinuousPortrayal2D trailsPortrayal = new ContinuousPortrayal2D();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  23 */     new FlockersWithUI().createController();
/*     */   }
/*     */   public Object getSimulationInspectedObject() {
/*  26 */     return this.state;
/*     */   }
/*     */ 
/*     */   public FlockersWithUI()
/*     */   {
/*  35 */     super(new Flockers(System.currentTimeMillis()));
/*     */   }
/*     */ 
/*     */   public FlockersWithUI(SimState state)
/*     */   {
/*  40 */     super(state);
/*     */   }
/*     */   public static String getName() {
/*  43 */     return "Flockers";
/*     */   }
/*     */ 
/*     */   public void start() {
/*  47 */     super.start();
/*  48 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  53 */     super.load(state);
/*  54 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  59 */     Flockers flock = (Flockers)this.state;
/*     */ 
/*  61 */     this.flockersPortrayal.setField(flock.flockers);
/*     */ 
/*  63 */     this.trailsPortrayal.setField(flock.flockers);
/*     */ 
/*  66 */     for (int x = 0; x < flock.flockers.allObjects.numObjs; x++)
/*     */     {
/*  68 */       SimplePortrayal2D basic = new TrailedPortrayal2D(this, new OrientedPortrayal2D(new SimplePortrayal2D(), 0, 4.0D, new Color(128 + this.guirandom.nextInt(128), 128 + this.guirandom.nextInt(128), 128 + this.guirandom.nextInt(128)), 2), this.trailsPortrayal, 100.0D);
/*     */ 
/*  86 */       this.flockersPortrayal.setPortrayalForObject(flock.flockers.allObjects.objs[x], new AdjustablePortrayal2D(new MovablePortrayal2D(basic)));
/*     */ 
/*  88 */       this.trailsPortrayal.setPortrayalForObject(flock.flockers.allObjects.objs[x], basic);
/*     */     }
/*     */ 
/*  92 */     double w = flock.flockers.getWidth();
/*  93 */     double h = flock.flockers.getHeight();
/*  94 */     if (w == h) {
/*  95 */       this.display.insideDisplay.width = (this.display.insideDisplay.height = 750.0D);
/*  96 */     } else if (w > h) {
/*  97 */       this.display.insideDisplay.width = 750.0D; this.display.insideDisplay.height = (750.0D * (h / w));
/*  98 */     } else if (w < h) {
/*  99 */       this.display.insideDisplay.height = 750.0D; this.display.insideDisplay.width = (750.0D * (w / h));
/*     */     }
/*     */ 
/* 102 */     this.display.reset();
/*     */ 
/* 105 */     this.display.repaint();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/* 110 */     super.init(c);
/*     */ 
/* 113 */     this.display = new Display2D(750.0D, 750.0D, this);
/* 114 */     this.display.setBackdrop(Color.black);
/*     */ 
/* 117 */     this.displayFrame = this.display.createFrame();
/* 118 */     this.displayFrame.setTitle("Flockers");
/* 119 */     c.registerFrame(this.displayFrame);
/* 120 */     this.displayFrame.setVisible(true);
/*     */ 
/* 122 */     this.display.attach(this.trailsPortrayal, "Trails");
/*     */ 
/* 124 */     this.display.attach(this.flockersPortrayal, "Behold the Flock!");
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 129 */     super.quit();
/*     */ 
/* 131 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 132 */     this.displayFrame = null;
/* 133 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.flockers.FlockersWithUI
 * JD-Core Version:    0.6.2
 */