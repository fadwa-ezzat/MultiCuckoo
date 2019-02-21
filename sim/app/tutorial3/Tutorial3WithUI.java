/*     */ package sim.app.tutorial3;
/*     */ 
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
/*     */ public class Tutorial3WithUI extends GUIState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public Display2D display;
/*     */   public JFrame displayFrame;
/*  21 */   SparseGridPortrayal2D particlesPortrayal = new SparseGridPortrayal2D();
/*  22 */   FastValueGridPortrayal2D trailsPortrayal = new FastValueGridPortrayal2D("Trail");
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  26 */     new Tutorial3WithUI().createController();
/*     */   }
/*     */   public Tutorial3WithUI() {
/*  29 */     super(new Tutorial3(System.currentTimeMillis()));
/*     */   }
/*  31 */   public Tutorial3WithUI(SimState state) { super(state); } 
/*     */   public static String getName() {
/*  33 */     return "Tutorial3: Particles";
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/*  49 */     super.quit();
/*     */ 
/*  51 */     if (this.displayFrame != null) this.displayFrame.dispose();
/*  52 */     this.displayFrame = null;
/*  53 */     this.display = null;
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  58 */     super.start();
/*     */ 
/*  60 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  65 */     super.load(state);
/*     */ 
/*  67 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  76 */     this.trailsPortrayal.setField(((Tutorial3)this.state).trails);
/*     */ 
/*  78 */     this.trailsPortrayal.setMap(new SimpleColorMap(0.0D, 1.0D, Color.black, Color.white));
/*     */ 
/*  81 */     this.particlesPortrayal.setField(((Tutorial3)this.state).particles);
/*  82 */     this.particlesPortrayal.setPortrayalForAll(new OvalPortrayal2D(Color.green));
/*     */ 
/*  85 */     this.display.reset();
/*     */ 
/*  88 */     this.display.repaint();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/*  93 */     super.init(c);
/*     */ 
/*  96 */     this.display = new Display2D(400.0D, 400.0D, this);
/*  97 */     this.displayFrame = this.display.createFrame();
/*  98 */     c.registerFrame(this.displayFrame);
/*  99 */     this.displayFrame.setVisible(true);
/*     */ 
/* 102 */     this.display.setBackdrop(Color.black);
/*     */ 
/* 105 */     this.display.attach(this.trailsPortrayal, "Trails");
/* 106 */     this.display.attach(this.particlesPortrayal, "Particles");
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial3.Tutorial3WithUI
 * JD-Core Version:    0.6.2
 */