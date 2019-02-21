/*    */ package sim.app.swarmgame;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.JFrame;
/*    */ import sim.display.Controller;
/*    */ import sim.display.Display2D;
/*    */ import sim.display.GUIState;
/*    */ import sim.engine.SimState;
/*    */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*    */ import sim.portrayal.simple.OrientedPortrayal2D;
/*    */ import sim.portrayal.simple.OvalPortrayal2D;
/*    */ 
/*    */ public class SwarmGameWithUI extends GUIState
/*    */ {
/*    */   public Display2D display;
/*    */   public JFrame displayFrame;
/* 27 */   ContinuousPortrayal2D agentsPortrayal = new ContinuousPortrayal2D();
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 22 */     new SwarmGameWithUI().createController();
/*    */   }
/*    */   public Object getSimulationInspectedObject() {
/* 25 */     return this.state;
/*    */   }
/*    */ 
/*    */   public SwarmGameWithUI()
/*    */   {
/* 31 */     super(new SwarmGame(System.currentTimeMillis()));
/*    */   }
/*    */ 
/*    */   public SwarmGameWithUI(SimState state)
/*    */   {
/* 36 */     super(state);
/*    */   }
/*    */   public static String getName() {
/* 39 */     return "The Swarm Game";
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
/* 55 */     SwarmGame swarm = (SwarmGame)this.state;
/*    */ 
/* 57 */     this.agentsPortrayal.setField(swarm.agents);
/* 58 */     this.agentsPortrayal.setPortrayalForAll(new OrientedPortrayal2D(new OvalPortrayal2D(Color.black), 0, 1.0D));
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
/* 73 */     this.display = new Display2D(500.0D, 500.0D, this);
/* 74 */     this.display.setClipping(false);
/*    */ 
/* 76 */     this.displayFrame = this.display.createFrame();
/* 77 */     this.displayFrame.setTitle("Swarmers");
/* 78 */     c.registerFrame(this.displayFrame);
/* 79 */     this.displayFrame.setVisible(true);
/* 80 */     this.display.attach(this.agentsPortrayal, "Fear the Swarmers!");
/*    */   }
/*    */ 
/*    */   public void quit()
/*    */   {
/* 85 */     super.quit();
/*    */ 
/* 87 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 88 */     this.displayFrame = null;
/* 89 */     this.display = null;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.swarmgame.SwarmGameWithUI
 * JD-Core Version:    0.6.2
 */