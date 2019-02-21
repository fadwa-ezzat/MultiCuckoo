/*    */ package sim.app.tutorial1and2;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.JFrame;
/*    */ import sim.display.Console;
/*    */ import sim.display.Controller;
/*    */ import sim.display.Display2D;
/*    */ import sim.display.GUIState;
/*    */ import sim.engine.SimState;
/*    */ import sim.portrayal.grid.FastValueGridPortrayal2D;
/*    */ import sim.util.gui.SimpleColorMap;
/*    */ 
/*    */ public class Tutorial2 extends GUIState
/*    */ {
/*    */   public Display2D display;
/*    */   public JFrame displayFrame;
/* 39 */   FastValueGridPortrayal2D gridPortrayal = new FastValueGridPortrayal2D();
/*    */ 
/*    */   public Tutorial2()
/*    */   {
/* 19 */     super(new Tutorial1(System.currentTimeMillis()));
/*    */   }
/* 21 */   public Tutorial2(SimState state) { super(state); } 
/*    */   public static String getName() {
/* 23 */     return "Tutorial 2: Life";
/*    */   }
/*    */ 
/*    */   public void setupPortrayals()
/*    */   {
/* 44 */     this.gridPortrayal.setField(((Tutorial1)this.state).grid);
/* 45 */     this.gridPortrayal.setMap(new SimpleColorMap(new Color[] { new Color(0, 0, 0, 0), Color.blue }));
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 52 */     super.start();
/* 53 */     setupPortrayals();
/* 54 */     this.display.reset();
/* 55 */     this.display.repaint();
/*    */   }
/*    */ 
/*    */   public void init(Controller c)
/*    */   {
/* 60 */     super.init(c);
/*    */ 
/* 63 */     Tutorial1 tut = (Tutorial1)this.state;
/* 64 */     this.display = new Display2D(tut.gridWidth * 4, tut.gridHeight * 4, this);
/* 65 */     this.displayFrame = this.display.createFrame();
/* 66 */     c.registerFrame(this.displayFrame);
/* 67 */     this.displayFrame.setVisible(true);
/*    */ 
/* 70 */     this.display.attach(this.gridPortrayal, "Life");
/*    */ 
/* 73 */     this.display.setBackdrop(Color.black);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 78 */     Tutorial2 tutorial2 = new Tutorial2();
/* 79 */     Console c = new Console(tutorial2);
/* 80 */     c.setVisible(true);
/*    */   }
/*    */ 
/*    */   public void load(SimState state)
/*    */   {
/* 85 */     super.load(state);
/* 86 */     setupPortrayals();
/* 87 */     this.display.reset();
/* 88 */     this.display.repaint();
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial1and2.Tutorial2
 * JD-Core Version:    0.6.2
 */