/*     */ package sim.app.celegans;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import javax.swing.JFrame;
/*     */ import sim.display.Controller;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal3d.continuous.ContinuousPortrayal3D;
/*     */ import sim.portrayal3d.network.NetworkPortrayal3D;
/*     */ import sim.portrayal3d.network.SimpleEdgePortrayal3D;
/*     */ import sim.portrayal3d.network.SpatialNetwork3D;
/*     */ import sim.portrayal3d.simple.SpherePortrayal3D;
/*     */ 
/*     */ public class CelegansWithUI extends GUIState
/*     */ {
/*     */   public Display3D display;
/*     */   public Display3D neuronDisplay;
/*     */   public JFrame displayFrame;
/*     */   public JFrame neuronDisplayFrame;
/*  24 */   ContinuousPortrayal3D nodePortrayal = new ContinuousPortrayal3D();
/*  25 */   ContinuousPortrayal3D neuronPortrayal = new ContinuousPortrayal3D();
/*  26 */   NetworkPortrayal3D synapsePortrayal = new NetworkPortrayal3D();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  30 */     new CelegansWithUI().createController();
/*     */   }
/*     */   public CelegansWithUI() {
/*  33 */     super(new Celegans(System.currentTimeMillis()));
/*     */   }
/*     */   public CelegansWithUI(SimState state) {
/*  36 */     super(state);
/*     */   }
/*     */   public static String getName() {
/*  39 */     return "Caenorhabditis elegans";
/*     */   }
/*  41 */   public Object getSimulationInspectedObject() { return this.state; }
/*     */ 
/*     */   public void start()
/*     */   {
/*  45 */     super.start();
/*  46 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  51 */     super.load(state);
/*  52 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  59 */     Celegans tut = (Celegans)this.state;
/*     */ 
/*  61 */     this.nodePortrayal.setField(tut.cells);
/*     */ 
/*  65 */     this.nodePortrayal.setPortrayalForAll(new CellPortrayal(50.0D));
/*     */ 
/*  67 */     this.neuronPortrayal.setField(tut.neurons);
/*  68 */     this.neuronPortrayal.setPortrayalForAll(new SpherePortrayal3D());
/*     */ 
/*  70 */     this.synapsePortrayal.setField(new SpatialNetwork3D(tut.neurons, tut.synapses));
/*  71 */     SimpleEdgePortrayal3D sep = new SimpleEdgePortrayal3D(Color.red, Color.blue, Color.white);
/*     */ 
/*  73 */     sep.setLabelScale(sep.getLabelScale() / 4.0D);
/*  74 */     this.synapsePortrayal.setPortrayalForAll(sep);
/*     */ 
/*  76 */     this.display.createSceneGraph();
/*  77 */     this.display.reset();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/*  82 */     super.init(c);
/*     */ 
/*  85 */     this.display = new Display3D(600.0D, 600.0D, this);
/*  86 */     this.display.attach(this.nodePortrayal, "Cells");
/*  87 */     this.display.scale(0.025D);
/*     */ 
/*  90 */     this.display.setSelectsAll(false, true);
/*     */ 
/*  92 */     this.displayFrame = this.display.createFrame();
/*  93 */     this.displayFrame.setTitle("Embryo");
/*  94 */     c.registerFrame(this.displayFrame);
/*  95 */     this.displayFrame.setVisible(true);
/*     */ 
/* 101 */     this.neuronDisplay = new Display3D(400.0D, 400.0D, this);
/* 102 */     this.neuronDisplay.attach(this.neuronPortrayal, "Neurons");
/* 103 */     this.neuronDisplay.attach(this.synapsePortrayal, "Synapses");
/* 104 */     this.neuronDisplay.scale(0.025D);
/*     */ 
/* 107 */     this.neuronDisplay.setSelectsAll(false, true);
/*     */ 
/* 109 */     this.neuronDisplayFrame = this.neuronDisplay.createFrame();
/* 110 */     this.neuronDisplayFrame.setTitle("Synapses");
/* 111 */     c.registerFrame(this.neuronDisplayFrame);
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 117 */     super.quit();
/*     */ 
/* 119 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 120 */     this.displayFrame = null;
/* 121 */     this.display = null;
/*     */ 
/* 123 */     if (this.neuronDisplayFrame != null) this.neuronDisplayFrame.dispose();
/* 124 */     this.neuronDisplayFrame = null;
/* 125 */     this.neuronDisplay = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.celegans.CelegansWithUI
 * JD-Core Version:    0.6.2
 */