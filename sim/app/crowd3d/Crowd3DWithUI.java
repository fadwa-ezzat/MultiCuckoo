/*     */ package sim.app.crowd3d;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import javax.media.j3d.Appearance;
/*     */ import javax.media.j3d.ColoringAttributes;
/*     */ import javax.media.j3d.Material;
/*     */ import javax.swing.JFrame;
/*     */ import javax.vecmath.Color3f;
/*     */ import sim.display.Controller;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal3d.FieldPortrayal3D;
/*     */ import sim.portrayal3d.Portrayal3D;
/*     */ import sim.portrayal3d.continuous.ContinuousPortrayal3D;
/*     */ import sim.portrayal3d.simple.LightPortrayal3D;
/*     */ import sim.portrayal3d.simple.Shape3DPortrayal3D;
/*     */ import sim.portrayal3d.simple.WireFrameBoxPortrayal3D;
/*     */ import sim.util.Double3D;
/*     */ 
/*     */ public class Crowd3DWithUI extends GUIState
/*     */ {
/*     */   public JFrame displayFrame;
/*     */   FieldPortrayal3D boidsP;
/*     */   Portrayal3D wireFrameP;
/*     */   public Display3D display;
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  29 */     new Crowd3DWithUI().createController();
/*     */   }
/*     */ 
/*     */   public Crowd3DWithUI()
/*     */   {
/*  34 */     this(new CrowdSim(System.currentTimeMillis()));
/*     */   }
/*     */ 
/*     */   public Crowd3DWithUI(CrowdSim b) {
/*  38 */     super(b);
/*  39 */     this.boidsP = new ContinuousPortrayal3D();
/*  40 */     this.wireFrameP = new WireFrameBoxPortrayal3D(0.0D, 0.0D, 0.0D, b.spaceWidth, b.spaceHeight, b.spaceDepth);
/*     */   }
/*     */   public static String getName() {
/*  43 */     return "Crowd Spacing";
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
/*  61 */     this.boidsP.setField(((CrowdSim)this.state).boidSpace);
/*     */ 
/*  63 */     this.display.reset();
/*     */ 
/*  66 */     this.display.createSceneGraph();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/*  73 */     CrowdSim cState = (CrowdSim)this.state;
/*  74 */     super.init(c);
/*  75 */     this.display = new Display3D(500.0D, 500.0D, this);
/*     */ 
/*  77 */     this.display.attach(this.wireFrameP, "Fish tank");
/*  78 */     Appearance appearance = new Appearance();
/*  79 */     appearance.setColoringAttributes(new ColoringAttributes(new Color3f(new Color(0, 0, 255)), 3));
/*     */ 
/*  81 */     Material m = new Material();
/*  82 */     m.setDiffuseColor(new Color3f(new Color(255, 255, 0)));
/*  83 */     m.setSpecularColor(0.5F, 0.5F, 0.5F);
/*  84 */     m.setShininess(64.0F);
/*  85 */     appearance.setMaterial(m);
/*  86 */     this.boidsP.setPortrayalForAll(new Shape3DPortrayal3D(new GullCG(), appearance));
/*     */ 
/*  89 */     this.display.attach(this.boidsP, "boids");
/*  90 */     this.display.attach(new LightPortrayal3D(new Color(127, 127, 255), new Double3D(-1.0D, -1.0D, 1.0D)), "Light One");
/*  91 */     this.display.attach(new LightPortrayal3D(new Color(127, 255, 127), new Double3D(1.0D, -1.0D, -1.0D)), "Light Two");
/*  92 */     this.display.attach(new LightPortrayal3D(new Color(255, 127, 127), new Double3D(1.0D, 1.0D, -1.0D)), "Light Three");
/*  93 */     this.display.setShowsSpotlight(false);
/*     */ 
/*  95 */     this.display.translate(-0.5D * cState.spaceWidth, -0.5D * cState.spaceHeight, -0.5D * cState.spaceDepth);
/*  96 */     this.display.scale(1.0D / Math.max(cState.spaceWidth, Math.max(cState.spaceHeight, cState.spaceDepth)));
/*     */ 
/*  98 */     this.displayFrame = this.display.createFrame();
/*  99 */     c.registerFrame(this.displayFrame);
/* 100 */     this.displayFrame.setVisible(true);
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 105 */     super.quit();
/*     */ 
/* 107 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 108 */     this.displayFrame = null;
/* 109 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.crowd3d.Crowd3DWithUI
 * JD-Core Version:    0.6.2
 */