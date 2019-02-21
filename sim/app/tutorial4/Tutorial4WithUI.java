/*     */ package sim.app.tutorial4;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Paint;
/*     */ import javax.swing.JFrame;
/*     */ import sim.display.Controller;
/*     */ import sim.display.Display2D;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.grid.FastValueGridPortrayal2D;
/*     */ import sim.portrayal.grid.SparseGridPortrayal2D;
/*     */ import sim.portrayal.simple.OvalPortrayal2D;
/*     */ import sim.portrayal.simple.RectanglePortrayal2D;
/*     */ import sim.util.gui.SimpleColorMap;
/*     */ 
/*     */ public class Tutorial4WithUI extends GUIState
/*     */ {
/*     */   public Display2D display;
/*     */   public JFrame displayFrame;
/*     */   public Display2D display2;
/*     */   public JFrame displayFrame2;
/*  23 */   SparseGridPortrayal2D particlesPortrayal = new SparseGridPortrayal2D();
/*  24 */   SparseGridPortrayal2D particlesPortrayal2 = new SparseGridPortrayal2D();
/*  25 */   FastValueGridPortrayal2D trailsPortrayal = new FastValueGridPortrayal2D("Trail");
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  29 */     new Tutorial4WithUI().createController();
/*     */   }
/*     */   public Tutorial4WithUI() {
/*  32 */     super(new Tutorial4(System.currentTimeMillis()));
/*     */   }
/*  34 */   public Tutorial4WithUI(SimState state) { super(state); } 
/*     */   public static String getName() {
/*  36 */     return "Tutorial4: Particles";
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/*  52 */     super.quit();
/*     */ 
/*  54 */     if (this.displayFrame != null) this.displayFrame.dispose();
/*  55 */     this.displayFrame = null;
/*  56 */     this.display = null;
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  61 */     super.start();
/*     */ 
/*  63 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  68 */     super.load(state);
/*     */ 
/*  70 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  78 */     this.trailsPortrayal.setField(((Tutorial4)this.state).trails);
/*  79 */     this.trailsPortrayal.setMap(new SimpleColorMap(0.0D, 1.0D, Color.black, Color.white));
/*     */ 
/*  82 */     this.particlesPortrayal.setField(((Tutorial4)this.state).particles);
/*  83 */     this.particlesPortrayal.setPortrayalForClass(Particle.class, new OvalPortrayal2D(Color.green));
/*     */ 
/*  85 */     this.particlesPortrayal.setPortrayalForClass(BigParticle.class, new RectanglePortrayal2D(Color.red, 1.5D)
/*     */     {
/*     */       public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */       {
/*  91 */         return new BigParticleInspector(super.getInspector(wrapper, state), wrapper, state);
/*     */       }
/*     */     });
/*  94 */     this.particlesPortrayal2.setField(((Tutorial4)this.state).particles);
/*  95 */     this.particlesPortrayal2.setPortrayalForAll(new RectanglePortrayal2D(Color.green));
/*     */ 
/*  98 */     this.display.reset();
/*  99 */     this.display2.reset();
/*     */ 
/* 102 */     this.display.repaint();
/* 103 */     this.display2.repaint();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/* 108 */     super.init(c);
/*     */ 
/* 110 */     this.display = new Display2D(400.0D, 400.0D, this);
/* 111 */     this.displayFrame = this.display.createFrame();
/* 112 */     c.registerFrame(this.displayFrame);
/* 113 */     this.displayFrame.setVisible(true);
/* 114 */     this.display.setBackdrop(Color.black);
/* 115 */     this.display.attach(this.trailsPortrayal, "Trails");
/* 116 */     this.display.attach(this.particlesPortrayal, "Particles");
/*     */ 
/* 118 */     this.display2 = new Display2D(400.0D, 600.0D, this);
/* 119 */     this.displayFrame2 = this.display2.createFrame();
/* 120 */     this.displayFrame2.setTitle("The Other Display");
/* 121 */     c.registerFrame(this.displayFrame2);
/* 122 */     this.displayFrame2.setVisible(true);
/* 123 */     this.display2.setBackdrop(Color.blue);
/* 124 */     this.display2.attach(this.particlesPortrayal2, "Squished Particles!");
/*     */   }
/*     */ 
/*     */   public Object getSimulationInspectedObject()
/*     */   {
/* 129 */     return this.state;
/*     */   }
/*     */ 
/*     */   public Inspector getInspector()
/*     */   {
/* 134 */     Inspector i = super.getInspector();
/* 135 */     i.setVolatile(true);
/* 136 */     return i;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial4.Tutorial4WithUI
 * JD-Core Version:    0.6.2
 */