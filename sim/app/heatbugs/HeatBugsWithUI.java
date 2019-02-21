/*     */ package sim.app.heatbugs;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import javax.swing.JFrame;
/*     */ import sim.display.Controller;
/*     */ import sim.display.Display2D;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal.grid.FastValueGridPortrayal2D;
/*     */ import sim.portrayal.grid.SparseGridPortrayal2D;
/*     */ import sim.portrayal.simple.MovablePortrayal2D;
/*     */ import sim.portrayal.simple.OvalPortrayal2D;
/*     */ import sim.util.gui.SimpleColorMap;
/*     */ 
/*     */ public class HeatBugsWithUI extends GUIState
/*     */ {
/*     */   public Display2D display;
/*     */   public JFrame displayFrame;
/*  26 */   FastValueGridPortrayal2D heatPortrayal = new FastValueGridPortrayal2D("Heat");
/*  27 */   SparseGridPortrayal2D bugPortrayal = new SparseGridPortrayal2D();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  38 */     new HeatBugsWithUI().createController();
/*     */   }
/*     */   public HeatBugsWithUI() {
/*  41 */     super(new HeatBugs(System.currentTimeMillis()));
/*     */   }
/*  43 */   public HeatBugsWithUI(SimState state) { super(state); }
/*     */ 
/*     */   public static String getName()
/*     */   {
/*  47 */     return "HeatBugs";
/*     */   }
/*     */   public Object getSimulationInspectedObject() {
/*  50 */     return this.state;
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  92 */     super.start();
/*     */ 
/*  94 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  99 */     super.load(state);
/*     */ 
/* 101 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/* 109 */     this.bugPortrayal.setField(((HeatBugs)this.state).buggrid);
/* 110 */     this.bugPortrayal.setPortrayalForAll(new MovablePortrayal2D(new OvalPortrayal2D(Color.white)));
/*     */ 
/* 112 */     this.heatPortrayal.setField(((HeatBugs)this.state).valgrid);
/* 113 */     this.heatPortrayal.setMap(new SimpleColorMap(0.0D, 32000.0D, Color.black, Color.red));
/*     */ 
/* 130 */     this.display.reset();
/*     */ 
/* 133 */     this.display.repaint();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/* 138 */     super.init(c);
/*     */ 
/* 141 */     this.display = new Display2D(400.0D, 400.0D, this);
/* 142 */     this.displayFrame = this.display.createFrame();
/* 143 */     this.displayFrame.setTitle(this.displayFrame.getTitle() + (HeatBugs.availableProcessors() > 1 ? " (Multiprocessor)" : ""));
/*     */ 
/* 146 */     c.registerFrame(this.displayFrame);
/* 147 */     this.displayFrame.setVisible(true);
/*     */ 
/* 150 */     this.display.attach(this.heatPortrayal, "Heat");
/*     */ 
/* 159 */     this.display.attach(this.bugPortrayal, "Bugs");
/*     */ 
/* 162 */     this.display.setBackdrop(Color.black);
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 167 */     super.quit();
/*     */ 
/* 169 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 170 */     this.displayFrame = null;
/* 171 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.heatbugs.HeatBugsWithUI
 * JD-Core Version:    0.6.2
 */