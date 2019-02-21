/*    */ package sim.app.woims3d;
/*    */ 
/*    */ import javax.swing.JFrame;
/*    */ import sim.display.Controller;
/*    */ import sim.display.GUIState;
/*    */ import sim.display3d.Display3D;
/*    */ import sim.engine.SimState;
/*    */ import sim.field.continuous.Continuous3D;
/*    */ import sim.portrayal3d.continuous.ContinuousPortrayal3D;
/*    */ import sim.portrayal3d.simple.WireFrameBoxPortrayal3D;
/*    */ 
/*    */ public class WoimsDemo3DWithUI extends GUIState
/*    */ {
/*    */   public Display3D display;
/*    */   public JFrame displayFrame;
/*    */   ContinuousPortrayal3D p2;
/*    */   WireFrameBoxPortrayal3D wireFrameP;
/*    */ 
/*    */   public static String getName()
/*    */   {
/* 22 */     return "3D Woims";
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 26 */     new WoimsDemo3DWithUI(new WoimsDemo3D(System.currentTimeMillis())).createController();
/*    */   }
/*    */ 
/*    */   public WoimsDemo3DWithUI()
/*    */   {
/* 31 */     this(new WoimsDemo3D(System.currentTimeMillis()));
/*    */   }
/*    */ 
/*    */   public WoimsDemo3DWithUI(SimState state)
/*    */   {
/* 39 */     super(state);
/* 40 */     WoimsDemo3D wd = (WoimsDemo3D)state;
/* 41 */     this.p2 = new ContinuousPortrayal3D();
/* 42 */     this.p2.setField(wd.environment);
/*    */ 
/* 45 */     this.wireFrameP = new WireFrameBoxPortrayal3D(-10.0D, -10.0D, -10.0D, wd.environment.width + 20.0D, wd.environment.height + 20.0D, wd.environment.length + 20.0D);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 51 */     super.start();
/* 52 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void load(SimState state)
/*    */   {
/* 57 */     super.load(state);
/* 58 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void setupPortrayals()
/*    */   {
/* 65 */     this.p2.setField(((WoimsDemo3D)this.state).environment);
/*    */ 
/* 67 */     this.display.createSceneGraph();
/* 68 */     this.display.reset();
/*    */   }
/*    */ 
/*    */   public void init(Controller c)
/*    */   {
/* 73 */     super.init(c);
/* 74 */     this.display = new Display3D(600.0D, 600.0D, this);
/*    */ 
/* 76 */     this.display.attach(this.p2, "Woims");
/* 77 */     this.display.attach(this.wireFrameP, "WireFrame");
/*    */ 
/* 79 */     this.display.translate(-100.0D, -100.0D, -100.0D);
/* 80 */     this.display.scale(0.005D);
/*    */ 
/* 82 */     this.displayFrame = this.display.createFrame();
/*    */ 
/* 84 */     c.registerFrame(this.displayFrame);
/* 85 */     this.displayFrame.setVisible(true);
/*    */   }
/*    */ 
/*    */   public void quit()
/*    */   {
/* 90 */     super.quit();
/*    */ 
/* 92 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 93 */     this.displayFrame = null;
/* 94 */     this.display = null;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.woims3d.WoimsDemo3DWithUI
 * JD-Core Version:    0.6.2
 */