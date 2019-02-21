/*     */ package sim.app.networktest;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import javax.swing.JFrame;
/*     */ import sim.display.Controller;
/*     */ import sim.display.Display2D;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*     */ import sim.portrayal.network.NetworkPortrayal2D;
/*     */ import sim.portrayal.network.SimpleEdgePortrayal2D;
/*     */ import sim.portrayal.network.SpatialNetwork2D;
/*     */ import sim.portrayal.simple.CircledPortrayal2D;
/*     */ import sim.portrayal.simple.MovablePortrayal2D;
/*     */ 
/*     */ public class NetworkTestWithUI extends GUIState
/*     */ {
/*     */   public Display2D display;
/*     */   public JFrame displayFrame;
/*  31 */   NetworkPortrayal2D edgePortrayal = new NetworkPortrayal2D();
/*  32 */   ContinuousPortrayal2D nodePortrayal = new ContinuousPortrayal2D();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  36 */     new NetworkTestWithUI().createController();
/*     */   }
/*     */   public NetworkTestWithUI() {
/*  39 */     super(new NetworkTest(System.currentTimeMillis())); } 
/*  40 */   public NetworkTestWithUI(SimState state) { super(state); } 
/*     */   public static String getName() {
/*  42 */     return "Network Test";
/*     */   }
/*     */ 
/*     */   public void start() {
/*  46 */     super.start();
/*  47 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  52 */     super.load(state);
/*  53 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  59 */     this.edgePortrayal.setField(new SpatialNetwork2D(((NetworkTest)this.state).environment, ((NetworkTest)this.state).network));
/*  60 */     SimpleEdgePortrayal2D p = new SimpleEdgePortrayal2D(Color.lightGray, Color.lightGray, Color.black);
/*  61 */     p.setShape(4);
/*  62 */     p.setBaseWidth(10.0D);
/*  63 */     this.edgePortrayal.setPortrayalForAll(p);
/*  64 */     this.nodePortrayal.setField(((NetworkTest)this.state).environment);
/*     */ 
/*  69 */     this.nodePortrayal.setPortrayalForAll(new MovablePortrayal2D(new CircledPortrayal2D(null, 20.0D, 10.0D, Color.green, true)));
/*     */ 
/*  72 */     this.display.reset();
/*  73 */     this.display.setBackdrop(Color.white);
/*     */ 
/*  76 */     this.display.repaint();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/*  81 */     super.init(c);
/*     */ 
/*  84 */     this.display = new Display2D(800.0D, 600.0D, this);
/*     */ 
/*  86 */     this.displayFrame = this.display.createFrame();
/*  87 */     this.displayFrame.setTitle("Network Test Display");
/*  88 */     c.registerFrame(this.displayFrame);
/*  89 */     this.displayFrame.setVisible(true);
/*  90 */     this.display.attach(this.edgePortrayal, "Edges");
/*  91 */     this.display.attach(this.nodePortrayal, "Nodes");
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 194 */     super.quit();
/*     */ 
/* 196 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 197 */     this.displayFrame = null;
/* 198 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.networktest.NetworkTestWithUI
 * JD-Core Version:    0.6.2
 */