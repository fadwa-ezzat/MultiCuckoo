/*     */ package sim.app.lightcycles;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.awt.Color;
/*     */ import javax.swing.JFrame;
/*     */ import sim.display.Controller;
/*     */ import sim.display.Display2D;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal.grid.FastValueGridPortrayal2D;
/*     */ import sim.portrayal.grid.SparseGridPortrayal2D;
/*     */ import sim.portrayal.simple.OvalPortrayal2D;
/*     */ import sim.util.gui.SimpleColorMap;
/*     */ 
/*     */ public class LightCyclesWithUI extends GUIState
/*     */ {
/*     */   public Display2D display;
/*     */   public JFrame displayFrame;
/*     */   public ControlUI controls;
/*  23 */   FastValueGridPortrayal2D gridPortrayal = new FastValueGridPortrayal2D();
/*     */ 
/*  25 */   SparseGridPortrayal2D cycleGridPortrayal = new SparseGridPortrayal2D();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  29 */     new LightCyclesWithUI().createController();
/*     */   }
/*     */   public LightCyclesWithUI() {
/*  32 */     super(new LightCycles(System.currentTimeMillis())); } 
/*  33 */   public LightCyclesWithUI(SimState state) { super(state); } 
/*     */   public static String getName() {
/*  35 */     return "Light Cycles";
/*     */   }
/*     */ 
/*     */   public void start() {
/*  39 */     super.start();
/*     */ 
/*  41 */     setupPortrayals();
/*     */ 
/*  45 */     if (this.controls != null)
/*  46 */       this.controls.c = null;
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  51 */     super.load(state);
/*     */ 
/*  53 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  59 */     this.gridPortrayal.setField(((LightCycles)this.state).grid);
/*     */ 
/*  62 */     Color[] colors = new Color[((LightCycles)this.state).cycleCount + 1];
/*  63 */     colors[0] = new Color(0, 0, 0, 0);
/*  64 */     for (int i = 1; i < colors.length; i++) {
/*  65 */       colors[i] = new Color(this.guirandom.nextInt(255), this.guirandom.nextInt(255), this.guirandom.nextInt(255));
/*     */     }
/*  67 */     this.gridPortrayal.setMap(new SimpleColorMap(colors));
/*     */ 
/*  70 */     this.cycleGridPortrayal.setField(((LightCycles)this.state).cycleGrid);
/*  71 */     this.cycleGridPortrayal.setPortrayalForClass(Cycle.class, new OvalPortrayal2D(Color.white));
/*     */ 
/*  75 */     this.display.reset();
/*     */ 
/*  78 */     this.display.repaint();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/*  83 */     super.init(c);
/*     */ 
/*  86 */     this.display = new Display2D(400.0D, 400.0D, this);
/*  87 */     this.displayFrame = this.display.createFrame();
/*  88 */     c.registerFrame(this.displayFrame);
/*  89 */     this.displayFrame.setVisible(true);
/*     */ 
/*  92 */     this.display.attach(this.gridPortrayal, "Paths");
/*  93 */     this.display.attach(this.cycleGridPortrayal, "Cycles");
/*     */ 
/*  96 */     this.controls = new ControlUI(this, this.cycleGridPortrayal);
/*     */ 
/*  99 */     this.display.setBackdrop(new Color(0, 0, 128));
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 107 */     super.quit();
/*     */ 
/* 109 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 110 */     this.displayFrame = null;
/* 111 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.lightcycles.LightCyclesWithUI
 * JD-Core Version:    0.6.2
 */