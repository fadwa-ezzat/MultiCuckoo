/*    */ package sim.app.woims;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.JFrame;
/*    */ import sim.display.Controller;
/*    */ import sim.display.Display2D;
/*    */ import sim.display.GUIState;
/*    */ import sim.engine.SimState;
/*    */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*    */ 
/*    */ public class WoimsDemoWithUI extends GUIState
/*    */ {
/*    */   public Display2D display;
/*    */   public JFrame displayFrame;
/* 22 */   ContinuousPortrayal2D woimsPortrayal = new ContinuousPortrayal2D();
/* 23 */   ContinuousPortrayal2D obstaclesPortrayal = new ContinuousPortrayal2D();
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 27 */     new WoimsDemoWithUI().createController();
/*    */   }
/*    */   public WoimsDemoWithUI() {
/* 30 */     super(new WoimsDemo(System.currentTimeMillis())); } 
/* 31 */   public WoimsDemoWithUI(SimState state) { super(state); } 
/*    */   public static String getName() {
/* 33 */     return "Woims";
/*    */   }
/*    */ 
/*    */   public void start() {
/* 37 */     super.start();
/* 38 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void load(SimState state)
/*    */   {
/* 43 */     super.load(state);
/* 44 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void setupPortrayals()
/*    */   {
/* 50 */     this.woimsPortrayal.setField(((WoimsDemo)this.state).woimsEnvironment);
/* 51 */     this.obstaclesPortrayal.setField(((WoimsDemo)this.state).obstaclesEnvironment);
/*    */ 
/* 54 */     this.display.reset();
/* 55 */     this.display.setBackdrop(Color.white);
/*    */ 
/* 58 */     this.display.repaint();
/*    */   }
/*    */ 
/*    */   public void init(Controller c)
/*    */   {
/* 63 */     super.init(c);
/*    */ 
/* 66 */     this.display = new Display2D(600.0D, 600.0D, this);
/*    */ 
/* 68 */     this.displayFrame = this.display.createFrame();
/* 69 */     this.displayFrame.setTitle("Woims Demonstration Display");
/* 70 */     c.registerFrame(this.displayFrame);
/* 71 */     this.displayFrame.setVisible(true);
/* 72 */     this.display.attach(this.woimsPortrayal, "Woims");
/* 73 */     this.display.attach(this.obstaclesPortrayal, "Obstacles");
/*    */   }
/*    */ 
/*    */   public void quit()
/*    */   {
/* 78 */     super.quit();
/*    */ 
/* 80 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 81 */     this.displayFrame = null;
/* 82 */     this.display = null;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.woims.WoimsDemoWithUI
 * JD-Core Version:    0.6.2
 */