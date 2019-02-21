/*    */ package sim.app.schelling;
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
/*    */ public class SchellingWithUI extends GUIState
/*    */ {
/*    */   public Display2D display;
/*    */   public JFrame displayFrame;
/* 19 */   FastValueGridPortrayal2D agentPortrayal = new FastValueGridPortrayal2D("Agents");
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 23 */     new SchellingWithUI().createController();
/*    */   }
/*    */   public SchellingWithUI() {
/* 26 */     super(new Schelling(System.currentTimeMillis()));
/*    */   }
/* 28 */   public SchellingWithUI(SimState state) { super(state); }
/*    */ 
/*    */   public static String getName()
/*    */   {
/* 32 */     return "Schelling Segregation";
/*    */   }
/*    */   public Object getSimulationInspectedObject() {
/* 35 */     return this.state;
/*    */   }
/*    */ 
/*    */   public void start() {
/* 39 */     super.start();
/*    */ 
/* 41 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void load(SimState state)
/*    */   {
/* 46 */     super.load(state);
/*    */ 
/* 48 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void setupPortrayals()
/*    */   {
/* 55 */     this.agentPortrayal.setMap(new SimpleColorMap(new Color[] { new Color(0, 0, 0, 0), new Color(64, 64, 64), Color.red, Color.blue }));
/* 56 */     this.agentPortrayal.setField(((Schelling)this.state).neighbors);
/*    */ 
/* 59 */     this.display.reset();
/*    */ 
/* 62 */     this.display.repaint();
/*    */   }
/*    */ 
/*    */   public void init(Controller c)
/*    */   {
/* 67 */     super.init(c);
/*    */ 
/* 70 */     this.display = new Display2D(400.0D, 400.0D, this);
/* 71 */     this.displayFrame = this.display.createFrame();
/* 72 */     c.registerFrame(this.displayFrame);
/* 73 */     this.displayFrame.setVisible(true);
/*    */ 
/* 76 */     this.display.attach(this.agentPortrayal, "Agents");
/*    */ 
/* 79 */     this.display.setBackdrop(Color.black);
/*    */   }
/*    */ 
/*    */   public void quit()
/*    */   {
/* 84 */     super.quit();
/*    */ 
/* 86 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 87 */     this.displayFrame = null;
/* 88 */     this.display = null;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.schelling.SchellingWithUI
 * JD-Core Version:    0.6.2
 */