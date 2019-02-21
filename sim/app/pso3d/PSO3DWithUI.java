/*     */ package sim.app.pso3d;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import javax.media.j3d.Appearance;
/*     */ import javax.media.j3d.Shape3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.swing.JFrame;
/*     */ import sim.display.Controller;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous3D;
/*     */ import sim.portrayal3d.continuous.ContinuousPortrayal3D;
/*     */ import sim.portrayal3d.simple.CubePortrayal3D;
/*     */ import sim.portrayal3d.simple.WireFrameBoxPortrayal3D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.gui.SimpleColorMap;
/*     */ 
/*     */ public class PSO3DWithUI extends GUIState
/*     */ {
/*     */   public Display3D display;
/*     */   public JFrame displayFrame;
/*  38 */   ContinuousPortrayal3D swarmPortrayal = new ContinuousPortrayal3D();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  31 */     new PSO3DWithUI().createController();
/*     */   }
/*     */   public Object getSimulationInspectedObject() {
/*  34 */     return this.state;
/*     */   }
/*  36 */   public static String getName() { return "Particle Swarm Optimization 3D"; }
/*     */ 
/*     */ 
/*     */   public PSO3DWithUI()
/*     */   {
/*  42 */     super(new PSO3D(System.currentTimeMillis()));
/*     */   }
/*     */ 
/*     */   public PSO3DWithUI(SimState state)
/*     */   {
/*  47 */     super(state);
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  52 */     super.start();
/*  53 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  58 */     super.load(state);
/*  59 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  66 */     PSO3D swarm = (PSO3D)this.state;
/*  67 */     SimpleColorMap map = new SimpleColorMap(swarm.fitnessFunctionLowerBound[swarm.fitnessFunction], 1000.0D, Color.blue, Color.red);
/*     */ 
/*  70 */     this.swarmPortrayal.setField(swarm.space);
/*     */ 
/*  72 */     for (int x = 0; x < swarm.space.allObjects.numObjs; x++)
/*     */     {
/*  74 */       final Particle3D p = (Particle3D)swarm.space.allObjects.objs[x];
/*  75 */       this.swarmPortrayal.setPortrayalForObject(p, new CubePortrayal3D(Color.green, 0.0500000007450581D)
/*     */       {
/*     */         public TransformGroup getModel(Object obj, TransformGroup j3dModel)
/*     */         {
/*  79 */           Appearance appearance = appearanceForColor(p.getColor(this.val$p.getFitness()));
/*  80 */           TransformGroup model = super.getModel(obj, j3dModel);
/*  81 */           Shape3D shape = (Shape3D)model.getChild(0);
/*  82 */           shape.setAppearance(appearance);
/*  83 */           return model;
/*     */         }
/*     */       });
/*     */     }
/*     */ 
/*  88 */     this.display.attach(new WireFrameBoxPortrayal3D(-5.12D, -5.12D, -5.12D, 5.12D, 5.12D, 5.12D), "Bounds");
/*     */ 
/*  90 */     this.display.createSceneGraph();
/*  91 */     this.display.reset();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/*  97 */     super.init(c);
/*     */ 
/*  99 */     double w = 10.24D;
/*     */ 
/* 101 */     this.display = new Display3D(600.0D, 600.0D, this);
/* 102 */     this.display.attach(this.swarmPortrayal, "Swarm");
/*     */ 
/* 104 */     this.display.scale(1.0D / w);
/*     */ 
/* 106 */     this.displayFrame = this.display.createFrame();
/* 107 */     this.displayFrame.setTitle("PSO 3D Display");
/* 108 */     c.registerFrame(this.displayFrame);
/*     */ 
/* 110 */     this.displayFrame.setVisible(true);
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 115 */     super.quit();
/*     */ 
/* 117 */     if (this.displayFrame != null)
/* 118 */       this.displayFrame.dispose();
/* 119 */     this.displayFrame = null;
/* 120 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pso3d.PSO3DWithUI
 * JD-Core Version:    0.6.2
 */