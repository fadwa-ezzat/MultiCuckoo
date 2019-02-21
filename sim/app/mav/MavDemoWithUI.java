/*    */ package sim.app.mav;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.JFrame;
/*    */ import sim.display.Controller;
/*    */ import sim.display.Display2D;
/*    */ import sim.display.GUIState;
/*    */ import sim.engine.SimState;
/*    */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*    */ import sim.portrayal.simple.CircledPortrayal2D;
/*    */ import sim.portrayal.simple.LabelledPortrayal2D;
/*    */ import sim.portrayal.simple.OrientedPortrayal2D;
/*    */ import sim.portrayal.simple.OvalPortrayal2D;
/*    */ 
/*    */ public class MavDemoWithUI extends GUIState
/*    */ {
/*    */   public Display2D display;
/*    */   public JFrame displayFrame;
/* 26 */   ContinuousPortrayal2D obstaclePortrayal = new ContinuousPortrayal2D();
/* 27 */   ContinuousPortrayal2D mavPortrayal = new ContinuousPortrayal2D();
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 23 */     new MavDemoWithUI().createController();
/*    */   }
/*    */ 
/*    */   public MavDemoWithUI()
/*    */   {
/* 31 */     super(new MavDemo(System.currentTimeMillis()));
/*    */   }
/*    */ 
/*    */   public MavDemoWithUI(SimState state)
/*    */   {
/* 36 */     super(state);
/*    */   }
/*    */   public static String getName() {
/* 39 */     return "Micro Air Vehicles";
/*    */   }
/*    */ 
/*    */   public void start() {
/* 43 */     super.start();
/* 44 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void load(SimState state)
/*    */   {
/* 49 */     super.load(state);
/* 50 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void setupPortrayals()
/*    */   {
/* 55 */     MavDemo mavdemo = (MavDemo)this.state;
/*    */ 
/* 57 */     this.obstaclePortrayal.setField(mavdemo.ground);
/* 58 */     this.mavPortrayal.setField(mavdemo.mavs);
/* 59 */     this.mavPortrayal.setPortrayalForAll(new CircledPortrayal2D(new LabelledPortrayal2D(new OrientedPortrayal2D(new OvalPortrayal2D(20.0D), 0, 20.0D), 20.0D, null, Color.blue, true), 0.0D, 30.0D, Color.blue, true));
/*    */ 
/* 69 */     this.display.reset();
/* 70 */     this.display.setBackdrop(Color.white);
/*    */ 
/* 73 */     this.display.repaint();
/*    */   }
/*    */ 
/*    */   public void init(Controller c)
/*    */   {
/* 78 */     super.init(c);
/*    */ 
/* 81 */     this.display = new Display2D(500.0D, 500.0D, this);
/*    */ 
/* 83 */     this.displayFrame = this.display.createFrame();
/* 84 */     this.displayFrame.setTitle("Mav Demonstration Display");
/* 85 */     c.registerFrame(this.displayFrame);
/* 86 */     this.displayFrame.setVisible(true);
/* 87 */     this.display.attach(this.obstaclePortrayal, "Regions");
/* 88 */     this.display.attach(this.mavPortrayal, "MAVs");
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
 * Qualified Name:     sim.app.mav.MavDemoWithUI
 * JD-Core Version:    0.6.2
 */