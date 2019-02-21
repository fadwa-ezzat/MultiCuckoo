/*    */ package sim.app.virus;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.JFrame;
/*    */ import sim.display.Controller;
/*    */ import sim.display.Display2D;
/*    */ import sim.display.GUIState;
/*    */ import sim.engine.SimState;
/*    */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*    */ 
/*    */ public class VirusInfectionDemoWithUI extends GUIState
/*    */ {
/*    */   public Display2D display;
/*    */   public JFrame displayFrame;
/* 22 */   ContinuousPortrayal2D vidPortrayal = new ContinuousPortrayal2D();
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 26 */     new VirusInfectionDemoWithUI().createController();
/*    */   }
/*    */   public VirusInfectionDemoWithUI() {
/* 29 */     super(new VirusInfectionDemo(System.currentTimeMillis())); } 
/* 30 */   public VirusInfectionDemoWithUI(SimState state) { super(state); } 
/*    */   public static String getName() {
/* 32 */     return "Virus Infection";
/*    */   }
/*    */ 
/*    */   public void start() {
/* 36 */     super.start();
/* 37 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void load(SimState state)
/*    */   {
/* 42 */     super.load(state);
/* 43 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void setupPortrayals()
/*    */   {
/* 49 */     this.vidPortrayal.setField(((VirusInfectionDemo)this.state).environment);
/*    */ 
/* 52 */     this.display.reset();
/* 53 */     this.display.setBackdrop(Color.white);
/*    */ 
/* 56 */     this.display.repaint();
/*    */   }
/*    */ 
/*    */   public void init(Controller c)
/*    */   {
/* 61 */     super.init(c);
/*    */ 
/* 64 */     this.display = new Display2D(800.0D, 600.0D, this);
/*    */ 
/* 66 */     this.displayFrame = this.display.createFrame();
/* 67 */     this.displayFrame.setTitle("Virus (Dis)Infection Demonstration Display");
/* 68 */     c.registerFrame(this.displayFrame);
/* 69 */     this.displayFrame.setVisible(true);
/* 70 */     this.display.attach(this.vidPortrayal, "Agents");
/*    */   }
/*    */ 
/*    */   public void quit()
/*    */   {
/* 75 */     super.quit();
/*    */ 
/* 77 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 78 */     this.displayFrame = null;
/* 79 */     this.display = null;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.virus.VirusInfectionDemoWithUI
 * JD-Core Version:    0.6.2
 */