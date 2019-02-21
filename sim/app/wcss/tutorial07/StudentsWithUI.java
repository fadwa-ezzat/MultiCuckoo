/*    */ package sim.app.wcss.tutorial07;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.JFrame;
/*    */ import sim.display.Console;
/*    */ import sim.display.Controller;
/*    */ import sim.display.Display2D;
/*    */ import sim.display.GUIState;
/*    */ import sim.engine.SimState;
/*    */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*    */ import sim.portrayal.simple.OvalPortrayal2D;
/*    */ 
/*    */ public class StudentsWithUI extends GUIState
/*    */ {
/*    */   public Display2D display;
/*    */   public JFrame displayFrame;
/* 21 */   ContinuousPortrayal2D yardPortrayal = new ContinuousPortrayal2D();
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 25 */     StudentsWithUI vid = new StudentsWithUI();
/* 26 */     Console c = new Console(vid);
/* 27 */     c.setVisible(true);
/*    */   }
/*    */   public StudentsWithUI() {
/* 30 */     super(new Students(System.currentTimeMillis())); } 
/* 31 */   public StudentsWithUI(SimState state) { super(state); } 
/*    */   public static String getName() {
/* 33 */     return "WCSS 2008 Tutorial";
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
/* 49 */     Students students = (Students)this.state;
/*    */ 
/* 52 */     this.yardPortrayal.setField(students.yard);
/* 53 */     this.yardPortrayal.setPortrayalForAll(new OvalPortrayal2D());
/*    */ 
/* 56 */     this.display.reset();
/* 57 */     this.display.setBackdrop(Color.white);
/*    */ 
/* 60 */     this.display.repaint();
/*    */   }
/*    */ 
/*    */   public void init(Controller c)
/*    */   {
/* 65 */     super.init(c);
/*    */ 
/* 68 */     this.display = new Display2D(600.0D, 600.0D, this);
/*    */ 
/* 70 */     this.display.setClipping(false);
/*    */ 
/* 72 */     this.displayFrame = this.display.createFrame();
/* 73 */     this.displayFrame.setTitle("Schoolyard Display");
/* 74 */     c.registerFrame(this.displayFrame);
/* 75 */     this.displayFrame.setVisible(true);
/* 76 */     this.display.attach(this.yardPortrayal, "Yard");
/*    */   }
/*    */ 
/*    */   public void quit()
/*    */   {
/* 81 */     super.quit();
/*    */ 
/* 83 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 84 */     this.displayFrame = null;
/* 85 */     this.display = null;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial07.StudentsWithUI
 * JD-Core Version:    0.6.2
 */