/*    */ package sim.app.tutorial5;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.JFrame;
/*    */ import sim.display.Controller;
/*    */ import sim.display.Display2D;
/*    */ import sim.display.GUIState;
/*    */ import sim.engine.SimState;
/*    */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*    */ import sim.portrayal.network.NetworkPortrayal2D;
/*    */ import sim.portrayal.network.SpatialNetwork2D;
/*    */ 
/*    */ public class Tutorial5WithUI extends GUIState
/*    */ {
/*    */   public Display2D display;
/*    */   public JFrame displayFrame;
/* 21 */   NetworkPortrayal2D edgePortrayal = new NetworkPortrayal2D();
/* 22 */   ContinuousPortrayal2D nodePortrayal = new ContinuousPortrayal2D();
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 26 */     new Tutorial5WithUI().createController();
/*    */   }
/*    */   public Tutorial5WithUI() {
/* 29 */     super(new Tutorial5(System.currentTimeMillis())); } 
/* 30 */   public Tutorial5WithUI(SimState state) { super(state); } 
/*    */   public static String getName() {
/* 32 */     return "Tutorial 5: Hooke's Law";
/*    */   }
/*    */ 
/*    */   public Object getSimulationInspectedObject()
/*    */   {
/* 43 */     return this.state;
/*    */   }
/*    */ 
/*    */   public void start() {
/* 47 */     super.start();
/* 48 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void load(SimState state)
/*    */   {
/* 53 */     super.load(state);
/* 54 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void setupPortrayals()
/*    */   {
/* 59 */     Tutorial5 tut = (Tutorial5)this.state;
/*    */ 
/* 62 */     this.edgePortrayal.setField(new SpatialNetwork2D(tut.balls, tut.bands));
/* 63 */     this.edgePortrayal.setPortrayalForAll(new BandPortrayal2D());
/* 64 */     this.nodePortrayal.setField(tut.balls);
/*    */ 
/* 67 */     this.display.reset();
/* 68 */     this.display.setBackdrop(Color.white);
/*    */ 
/* 71 */     this.display.repaint();
/*    */   }
/*    */ 
/*    */   public void init(Controller c)
/*    */   {
/* 76 */     super.init(c);
/*    */ 
/* 79 */     this.display = new Display2D(600.0D, 600.0D, this);
/*    */ 
/* 81 */     this.display.setClipping(false);
/*    */ 
/* 83 */     this.displayFrame = this.display.createFrame();
/* 84 */     this.displayFrame.setTitle("Tutorial 5 Display");
/* 85 */     c.registerFrame(this.displayFrame);
/* 86 */     this.displayFrame.setVisible(true);
/* 87 */     this.display.attach(this.edgePortrayal, "Bands");
/* 88 */     this.display.attach(this.nodePortrayal, "Balls");
/*    */   }
/*    */ 
/*    */   public void quit()
/*    */   {
/* 93 */     super.quit();
/*    */ 
/* 95 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 96 */     this.displayFrame = null;
/* 97 */     this.display = null;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial5.Tutorial5WithUI
 * JD-Core Version:    0.6.2
 */