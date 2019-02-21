/*     */ package sim.app.pso;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import javax.swing.JFrame;
/*     */ import sim.display.Controller;
/*     */ import sim.display.Display2D;
/*     */ import sim.display.Display2D.InnerDisplay2D;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*     */ import sim.portrayal.simple.RectanglePortrayal2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.gui.SimpleColorMap;
/*     */ 
/*     */ public class PSOWithUI extends GUIState
/*     */ {
/*     */   public Display2D display;
/*     */   public JFrame displayFrame;
/*  40 */   ContinuousPortrayal2D swarmPortrayal = new ContinuousPortrayal2D();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  33 */     new PSOWithUI().createController();
/*     */   }
/*     */   public Object getSimulationInspectedObject() {
/*  36 */     return this.state;
/*     */   }
/*  38 */   public static String getName() { return "Particle Swarm Optimization"; }
/*     */ 
/*     */ 
/*     */   public PSOWithUI()
/*     */   {
/*  44 */     super(new PSO(System.currentTimeMillis()));
/*     */   }
/*     */ 
/*     */   public PSOWithUI(SimState state)
/*     */   {
/*  49 */     super(state);
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  54 */     super.start();
/*  55 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  60 */     super.load(state);
/*  61 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  66 */     PSO swarm = (PSO)this.state;
/*  67 */     SimpleColorMap map = new SimpleColorMap(swarm.fitnessFunctionLowerBound[swarm.fitnessFunction], 1000.0D, Color.blue, Color.red);
/*     */ 
/*  70 */     this.swarmPortrayal.setField(swarm.space);
/*  71 */     for (int x = 0; x < swarm.space.allObjects.numObjs; x++)
/*     */     {
/*  73 */       final Particle p = (Particle)swarm.space.allObjects.objs[x];
/*  74 */       this.swarmPortrayal.setPortrayalForObject(p, new RectanglePortrayal2D(Color.green, 0.05D)
/*     */       {
/*     */         public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */         {
/*  79 */           this.paint = p.getColor(this.val$p.getFitness());
/*  80 */           super.draw(object, graphics, info);
/*     */         }
/*     */ 
/*     */       });
/*     */     }
/*     */ 
/*  86 */     double w = swarm.space.getWidth();
/*  87 */     double h = swarm.space.getHeight();
/*  88 */     if (w == h) {
/*  89 */       this.display.insideDisplay.width = (this.display.insideDisplay.height = 750.0D);
/*  90 */     } else if (w > h) {
/*  91 */       this.display.insideDisplay.width = 750.0D; this.display.insideDisplay.height = (750.0D * (h / w));
/*  92 */     } else if (w < h) {
/*  93 */       this.display.insideDisplay.height = 750.0D; this.display.insideDisplay.width = (750.0D * (w / h));
/*     */     }
/*     */ 
/*  96 */     this.display.reset();
/*     */ 
/*  99 */     this.display.repaint();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/* 104 */     super.init(c);
/*     */ 
/* 107 */     this.display = new Display2D(750.0D, 750.0D, this);
/* 108 */     this.display.setBackdrop(Color.black);
/*     */ 
/* 110 */     this.displayFrame = this.display.createFrame();
/* 111 */     this.displayFrame.setTitle("Particle Swarm Optimization");
/* 112 */     c.registerFrame(this.displayFrame);
/* 113 */     this.displayFrame.setVisible(true);
/* 114 */     this.display.attach(this.swarmPortrayal, "Behold the Swarm!", this.display.insideDisplay.width * 0.5D, this.display.insideDisplay.height * 0.5D, true);
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 120 */     super.quit();
/*     */ 
/* 122 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 123 */     this.displayFrame = null;
/* 124 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pso.PSOWithUI
 * JD-Core Version:    0.6.2
 */