/*    */ package sim.app.wcss.tutorial08;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.JFrame;
/*    */ import sim.display.Console;
/*    */ import sim.display.Controller;
/*    */ import sim.display.Display2D;
/*    */ import sim.display.GUIState;
/*    */ import sim.engine.SimState;
/*    */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*    */ import sim.portrayal.network.NetworkPortrayal2D;
/*    */ import sim.portrayal.network.SimpleEdgePortrayal2D;
/*    */ import sim.portrayal.network.SpatialNetwork2D;
/*    */ import sim.portrayal.simple.OvalPortrayal2D;
/*    */ 
/*    */ public class StudentsWithUI extends GUIState
/*    */ {
/*    */   public Display2D display;
/*    */   public JFrame displayFrame;
/* 22 */   ContinuousPortrayal2D yardPortrayal = new ContinuousPortrayal2D();
/* 23 */   NetworkPortrayal2D buddiesPortrayal = new NetworkPortrayal2D();
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 27 */     StudentsWithUI vid = new StudentsWithUI();
/* 28 */     Console c = new Console(vid);
/* 29 */     c.setVisible(true);
/*    */   }
/*    */   public StudentsWithUI() {
/* 32 */     super(new Students(System.currentTimeMillis())); } 
/* 33 */   public StudentsWithUI(SimState state) { super(state); } 
/*    */   public static String getName() {
/* 35 */     return "WCSS 2008 Tutorial";
/*    */   }
/*    */ 
/*    */   public void start() {
/* 39 */     super.start();
/* 40 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void load(SimState state)
/*    */   {
/* 45 */     super.load(state);
/* 46 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void setupPortrayals()
/*    */   {
/* 51 */     Students students = (Students)this.state;
/*    */ 
/* 54 */     this.yardPortrayal.setField(students.yard);
/* 55 */     this.yardPortrayal.setPortrayalForAll(new OvalPortrayal2D());
/*    */ 
/* 57 */     this.buddiesPortrayal.setField(new SpatialNetwork2D(students.yard, students.buddies));
/* 58 */     this.buddiesPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D());
/*    */ 
/* 61 */     this.display.reset();
/* 62 */     this.display.setBackdrop(Color.white);
/*    */ 
/* 65 */     this.display.repaint();
/*    */   }
/*    */ 
/*    */   public void init(Controller c)
/*    */   {
/* 70 */     super.init(c);
/*    */ 
/* 73 */     this.display = new Display2D(600.0D, 600.0D, this);
/*    */ 
/* 75 */     this.display.setClipping(false);
/*    */ 
/* 77 */     this.displayFrame = this.display.createFrame();
/* 78 */     this.displayFrame.setTitle("Schoolyard Display");
/* 79 */     c.registerFrame(this.displayFrame);
/* 80 */     this.displayFrame.setVisible(true);
/* 81 */     this.display.attach(this.buddiesPortrayal, "Buddies");
/* 82 */     this.display.attach(this.yardPortrayal, "Yard");
/*    */   }
/*    */ 
/*    */   public void quit()
/*    */   {
/* 87 */     super.quit();
/*    */ 
/* 89 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 90 */     this.displayFrame = null;
/* 91 */     this.display = null;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial08.StudentsWithUI
 * JD-Core Version:    0.6.2
 */