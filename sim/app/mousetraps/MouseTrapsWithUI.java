/*    */ package sim.app.mousetraps;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.JFrame;
/*    */ import sim.display.Controller;
/*    */ import sim.display.Display2D;
/*    */ import sim.display.GUIState;
/*    */ import sim.engine.SimState;
/*    */ import sim.portrayal.grid.FastValueGridPortrayal2D;
/*    */ import sim.util.gui.SimpleColorMap;
/*    */ 
/*    */ public class MouseTrapsWithUI extends GUIState
/*    */ {
/*    */   public Display2D display;
/*    */   public JFrame displayFrame;
/* 20 */   FastValueGridPortrayal2D trapPortrayal = new FastValueGridPortrayal2D("Traps");
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 24 */     new MouseTrapsWithUI().createController();
/*    */   }
/*    */   public MouseTrapsWithUI() {
/* 27 */     super(new MouseTraps(System.currentTimeMillis())); } 
/* 28 */   public MouseTrapsWithUI(SimState state) { super(state); } 
/* 29 */   public static String getName() { return "Mouse Traps"; }
/*    */ 
/*    */   public void start()
/*    */   {
/* 33 */     super.start();
/*    */ 
/* 35 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void load(SimState state)
/*    */   {
/* 40 */     super.load(state);
/*    */ 
/* 42 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void setupPortrayals()
/*    */   {
/* 50 */     this.trapPortrayal.setField(((MouseTraps)this.state).trapStateGrid);
/* 51 */     ((SimpleColorMap)this.trapPortrayal.getMap()).setLevels(0.0D, 1.0D, Color.black, Color.red);
/*    */ 
/* 54 */     this.display.reset();
/*    */ 
/* 57 */     this.display.repaint();
/*    */   }
/*    */ 
/*    */   public void init(Controller c)
/*    */   {
/* 62 */     super.init(c);
/*    */ 
/* 65 */     this.display = new Display2D(400.0D, 400.0D, this);
/* 66 */     this.displayFrame = this.display.createFrame();
/* 67 */     c.registerFrame(this.displayFrame);
/* 68 */     this.displayFrame.setVisible(true);
/*    */ 
/* 71 */     this.display.attach(this.trapPortrayal, "Traps");
/*    */ 
/* 74 */     this.display.setBackdrop(Color.black);
/*    */   }
/*    */ 
/*    */   public void quit()
/*    */   {
/* 79 */     super.quit();
/*    */ 
/* 81 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 82 */     this.displayFrame = null;
/* 83 */     this.display = null;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.mousetraps.MouseTrapsWithUI
 * JD-Core Version:    0.6.2
 */