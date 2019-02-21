/*    */ package sim.app.particles3d;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.JFrame;
/*    */ import sim.display.Controller;
/*    */ import sim.display.GUIState;
/*    */ import sim.display3d.Display3D;
/*    */ import sim.engine.SimState;
/*    */ import sim.portrayal3d.grid.SparseGridPortrayal3D;
/*    */ import sim.portrayal3d.grid.ValueGridPortrayal3D;
/*    */ import sim.portrayal3d.simple.SpherePortrayal3D;
/*    */ import sim.portrayal3d.simple.WireFrameBoxPortrayal3D;
/*    */ import sim.util.gui.SimpleColorMap;
/*    */ 
/*    */ public class Particles3DWithUI extends GUIState
/*    */ {
/*    */   public Display3D display;
/*    */   public JFrame displayFrame;
/* 21 */   SparseGridPortrayal3D particlesPortrayal = new SparseGridPortrayal3D();
/* 22 */   ValueGridPortrayal3D trailsPortrayal = new ValueGridPortrayal3D("Trail");
/*    */   WireFrameBoxPortrayal3D wireFramePortrayal;
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 28 */     new Particles3DWithUI().createController();
/*    */   }
/*    */   public Particles3DWithUI() {
/* 31 */     super(new Particles3D(System.currentTimeMillis()));
/*    */   }
/* 33 */   public Particles3DWithUI(SimState state) { super(state); } 
/*    */   public static String getName() {
/* 35 */     return "3D Particles";
/*    */   }
/*    */ 
/*    */   public void quit() {
/* 39 */     super.quit();
/*    */ 
/* 41 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 42 */     this.displayFrame = null;
/* 43 */     this.display = null;
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 48 */     super.start();
/*    */ 
/* 50 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void load(SimState state)
/*    */   {
/* 55 */     super.load(state);
/* 56 */     setupPortrayals();
/*    */   }
/*    */ 
/*    */   public void setupPortrayals()
/*    */   {
/* 63 */     this.particlesPortrayal.setField(((Particles3D)this.state).particles);
/* 64 */     this.particlesPortrayal.setPortrayalForAll(new SpherePortrayal3D(Color.red));
/*    */ 
/* 66 */     this.trailsPortrayal.setField(((Particles3D)this.state).trails);
/* 67 */     this.trailsPortrayal.setMap(new SimpleColorMap(0.0D, 1.0D, new Color(0, 0, 0, 0), Color.black));
/*    */ 
/* 70 */     this.display.reset();
/*    */ 
/* 73 */     this.display.createSceneGraph();
/*    */   }
/*    */ 
/*    */   public void init(Controller c)
/*    */   {
/* 78 */     super.init(c);
/* 79 */     this.display = new Display3D(600.0D, 600.0D, this);
/*    */ 
/* 81 */     this.wireFramePortrayal = new WireFrameBoxPortrayal3D(-0.5D, -0.5D, -0.5D, 30.0D, 30.0D, 30.0D, Color.blue);
/*    */ 
/* 84 */     this.display.attach(this.wireFramePortrayal, "Wire Frame");
/* 85 */     this.display.attach(this.particlesPortrayal, "Particles");
/* 86 */     this.display.attach(this.trailsPortrayal, "Trails");
/*    */ 
/* 88 */     this.display.translate(-15.0D, -15.0D, -15.0D);
/*    */ 
/* 92 */     this.display.scale(0.03333333333333333D);
/*    */ 
/* 94 */     this.display.setBackdrop(Color.white);
/* 95 */     this.displayFrame = this.display.createFrame();
/* 96 */     c.registerFrame(this.displayFrame);
/* 97 */     this.displayFrame.setVisible(true);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.particles3d.Particles3DWithUI
 * JD-Core Version:    0.6.2
 */