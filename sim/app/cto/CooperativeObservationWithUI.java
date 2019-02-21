/*    */ package sim.app.cto;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.JFrame;
/*    */ import sim.display.Controller;
/*    */ import sim.display.Display2D;
/*    */ import sim.display.GUIState;
/*    */ import sim.engine.SimState;
/*    */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*    */ 
/*    */ public class CooperativeObservationWithUI extends GUIState
/*    */ {
/*    */   public Display2D display;
/*    */   public JFrame displayFrame;
/* 20 */   ContinuousPortrayal2D coPortrayal = new ContinuousPortrayal2D();
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 24 */     new CooperativeObservationWithUI().createController();
/*    */   }
/*    */   public CooperativeObservationWithUI() {
/* 27 */     super(new CooperativeObservation(System.currentTimeMillis())); } 
/* 28 */   public CooperativeObservationWithUI(SimState state) { super(state); } 
/*    */   public static String getName() {
/* 30 */     return "Cooperative Target Observation";
/*    */   }
/*    */ 
/*    */   public void start() {
/* 34 */     super.start();
/* 35 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void load(SimState state)
/*    */   {
/* 40 */     super.load(state);
/* 41 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void setupPortrayals()
/*    */   {
/* 47 */     this.coPortrayal.setField(((CooperativeObservation)this.state).environment);
/*    */ 
/* 49 */     this.display.reset();
/* 50 */     this.display.setBackdrop(Color.white);
/*    */ 
/* 53 */     this.display.repaint();
/*    */   }
/*    */ 
/*    */   public void init(Controller c)
/*    */   {
/* 58 */     super.init(c);
/*    */ 
/* 61 */     this.display = new Display2D(600.0D, 600.0D, this);
/*    */ 
/* 63 */     this.displayFrame = this.display.createFrame();
/* 64 */     this.displayFrame.setTitle("Cooperative Target Observation Display");
/* 65 */     c.registerFrame(this.displayFrame);
/* 66 */     this.displayFrame.setVisible(true);
/* 67 */     this.display.attach(this.coPortrayal, "Agents");
/*    */   }
/*    */ 
/*    */   public void quit()
/*    */   {
/* 72 */     super.quit();
/*    */ 
/* 74 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 75 */     this.displayFrame = null;
/* 76 */     this.display = null;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.cto.CooperativeObservationWithUI
 * JD-Core Version:    0.6.2
 */