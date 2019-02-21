/*     */ package sim.app.balls3d;
/*     */ 
/*     */ import java.text.NumberFormat;
/*     */ import javax.swing.JFrame;
/*     */ import sim.display.Controller;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.display3d.SelectionBehavior;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.network.Edge;
/*     */ import sim.portrayal3d.continuous.ContinuousPortrayal3D;
/*     */ import sim.portrayal3d.network.CylinderEdgePortrayal3D;
/*     */ import sim.portrayal3d.network.NetworkPortrayal3D;
/*     */ import sim.portrayal3d.network.SimpleEdgePortrayal3D;
/*     */ import sim.portrayal3d.network.SpatialNetwork3D;
/*     */ import sim.portrayal3d.simple.CircledPortrayal3D;
/*     */ 
/*     */ public class Balls3DWithUI extends GUIState
/*     */ {
/*     */   public Display3D display;
/*     */   public JFrame displayFrame;
/*  21 */   NetworkPortrayal3D edgePortrayal = new NetworkPortrayal3D();
/*  22 */   ContinuousPortrayal3D nodePortrayal = new ContinuousPortrayal3D();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  26 */     new Balls3DWithUI().createController();
/*     */   }
/*     */   public Balls3DWithUI() {
/*  29 */     super(new Balls3D(System.currentTimeMillis()));
/*     */   }
/*     */ 
/*     */   public Balls3DWithUI(SimState state) {
/*  33 */     super(state);
/*     */   }
/*     */   public static String getName() {
/*  36 */     return "3D Balls and Bands";
/*     */   }
/*  38 */   public Object getSimulationInspectedObject() { return this.state; }
/*     */ 
/*     */   public void start()
/*     */   {
/*  42 */     super.start();
/*  43 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  48 */     super.load(state);
/*  49 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  56 */     Balls3D tut = (Balls3D)this.state;
/*     */ 
/*  58 */     final NumberFormat strengthFormat = NumberFormat.getInstance();
/*  59 */     strengthFormat.setMinimumIntegerDigits(1);
/*  60 */     strengthFormat.setMaximumFractionDigits(2);
/*     */ 
/*  63 */     this.edgePortrayal.setField(new SpatialNetwork3D(tut.balls, tut.bands));
/*  64 */     SimpleEdgePortrayal3D portrayal = new CylinderEdgePortrayal3D()
/*     */     {
/*     */       public String getLabel(Edge e)
/*     */       {
/*  68 */         return strengthFormat.format(e.getWeight());
/*     */       }
/*     */     };
/*  72 */     portrayal.setLabelScale(1.0D);
/*     */ 
/*  74 */     this.edgePortrayal.setPortrayalForAll(portrayal);
/*  75 */     this.nodePortrayal.setField(tut.balls);
/*     */     try
/*     */     {
/*  78 */       this.nodePortrayal.setPortrayalForAll(new CircledPortrayal3D(new BallPortrayal(5.0D), 20.0D, true));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  93 */       throw new RuntimeException("yo", e);
/*     */     }
/*  95 */     this.display.createSceneGraph();
/*  96 */     this.display.reset();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/* 101 */     super.init(c);
/*     */ 
/* 103 */     Balls3D tut = (Balls3D)this.state;
/*     */ 
/* 106 */     this.display = new Display3D(600.0D, 600.0D, this);
/*     */ 
/* 108 */     this.display.attach(this.edgePortrayal, "Bands");
/* 109 */     this.display.attach(this.nodePortrayal, "Balls");
/*     */ 
/* 111 */     this.display.translate(-tut.gridWidth / 2.0D, -tut.gridHeight / 2.0D, -tut.gridLength / 2.0D);
/*     */ 
/* 115 */     this.display.scale(1.0D / tut.gridWidth);
/*     */ 
/* 117 */     this.displayFrame = this.display.createFrame();
/* 118 */     this.displayFrame.setTitle("Balls and Bands");
/* 119 */     c.registerFrame(this.displayFrame);
/* 120 */     this.displayFrame.setVisible(true);
/*     */ 
/* 122 */     this.display.getSelectionBehavior().setTolerance(10.0D);
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 127 */     super.quit();
/*     */ 
/* 129 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 130 */     this.displayFrame = null;
/* 131 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.balls3d.Balls3DWithUI
 * JD-Core Version:    0.6.2
 */