/*    */ package sim.app.keepaway;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.JFrame;
/*    */ import sim.display.Controller;
/*    */ import sim.display.Display2D;
/*    */ import sim.display.GUIState;
/*    */ import sim.engine.SimState;
/*    */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*    */ import sim.portrayal.simple.OvalPortrayal2D;
/*    */ import sim.portrayal.simple.RectanglePortrayal2D;
/*    */ 
/*    */ public class KeepawayWithUI extends GUIState
/*    */ {
/*    */   public Display2D display;
/*    */   public JFrame displayFrame;
/* 19 */   ContinuousPortrayal2D entityPortrayal = new ContinuousPortrayal2D();
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 23 */     new KeepawayWithUI().createController();
/*    */   }
/*    */   public KeepawayWithUI() {
/* 26 */     super(new Keepaway(System.currentTimeMillis()));
/*    */   }
/* 28 */   public KeepawayWithUI(SimState state) { super(state); } 
/*    */   public static String getName() {
/* 30 */     return "Keep-Away Soccer";
/*    */   }
/*    */ 
/*    */   public void start() {
/* 34 */     super.start();
/*    */ 
/* 36 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void load(SimState state)
/*    */   {
/* 41 */     super.load(state);
/*    */ 
/* 43 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void setupPortrayals()
/*    */   {
/* 50 */     this.entityPortrayal.setField(((Keepaway)this.state).fieldEnvironment);
/* 51 */     this.entityPortrayal.setPortrayalForClass(Bot.class, new RectanglePortrayal2D(Color.red));
/* 52 */     this.entityPortrayal.setPortrayalForClass(Ball.class, new OvalPortrayal2D(Color.white));
/*    */ 
/* 55 */     this.display.reset();
/*    */ 
/* 58 */     this.display.repaint();
/*    */   }
/*    */ 
/*    */   public void init(Controller c)
/*    */   {
/* 63 */     super.init(c);
/*    */ 
/* 66 */     this.display = new Display2D(400.0D, 400.0D, this);
/* 67 */     this.displayFrame = this.display.createFrame();
/* 68 */     c.registerFrame(this.displayFrame);
/* 69 */     this.displayFrame.setVisible(true);
/*    */ 
/* 72 */     this.display.attach(this.entityPortrayal, "Bots and Balls");
/*    */ 
/* 75 */     this.display.setBackdrop(new Color(0, 80, 0));
/*    */   }
/*    */ 
/*    */   public void quit()
/*    */   {
/* 80 */     super.quit();
/*    */ 
/* 82 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 83 */     this.displayFrame = null;
/* 84 */     this.display = null;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.keepaway.KeepawayWithUI
 * JD-Core Version:    0.6.2
 */