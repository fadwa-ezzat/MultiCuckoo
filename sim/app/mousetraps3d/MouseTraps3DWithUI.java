/*     */ package sim.app.mousetraps3d;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import javax.swing.JFrame;
/*     */ import sim.app.mousetraps.MouseTraps;
/*     */ import sim.display.Controller;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal3d.continuous.ContinuousPortrayal3D;
/*     */ import sim.portrayal3d.grid.ValueGrid2DPortrayal3D;
/*     */ import sim.portrayal3d.grid.quad.TilePortrayal;
/*     */ import sim.portrayal3d.simple.SpherePortrayal3D;
/*     */ import sim.portrayal3d.simple.WireFrameBoxPortrayal3D;
/*     */ import sim.util.gui.SimpleColorMap;
/*     */ 
/*     */ public class MouseTraps3DWithUI extends GUIState
/*     */ {
/*     */   public JFrame displayFrame;
/*  25 */   ValueGrid2DPortrayal3D trapsPortrayal = new ValueGrid2DPortrayal3D();
/*  26 */   ContinuousPortrayal3D ballPortrayal = new ContinuousPortrayal3D();
/*     */   WireFrameBoxPortrayal3D wireFrameP;
/*     */   public Display3D display;
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  31 */     new MouseTraps3DWithUI().createController();
/*     */   }
/*     */ 
/*     */   public MouseTraps3DWithUI()
/*     */   {
/*  36 */     super(new MouseTraps(System.currentTimeMillis(), 40.0D, 15, 10, 120.0D, 80.0D, false));
/*     */   }
/*     */ 
/*     */   public static String getName()
/*     */   {
/*  41 */     return "3D Mouse Traps";
/*     */   }
/*     */ 
/*     */   public void start() {
/*  45 */     super.start();
/*  46 */     setup3DPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  51 */     super.load(state);
/*     */ 
/*  53 */     setup3DPortrayals();
/*     */   }
/*     */ 
/*     */   public void setup3DPortrayals()
/*     */   {
/*  60 */     this.trapsPortrayal.setField(((MouseTraps)this.state).trapStateGrid);
/*  61 */     this.ballPortrayal.setField(((MouseTraps)this.state).ballSpace);
/*  62 */     this.ballPortrayal.setPortrayalForAll(new SpherePortrayal3D(Color.green));
/*     */ 
/*  65 */     this.display.createSceneGraph();
/*     */ 
/*  68 */     this.display.reset();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/*  75 */     super.init(c);
/*     */ 
/*  79 */     MouseTraps sim = (MouseTraps)this.state;
/*  80 */     this.trapsPortrayal.setField(sim.trapStateGrid);
/*  81 */     SimpleColorMap map = new SimpleColorMap();
/*  82 */     map.setLevels(0.0D, 1.0D, Color.blue, Color.gray);
/*  83 */     this.trapsPortrayal.setPortrayalForAll(new TilePortrayal(map));
/*     */ 
/* 102 */     this.trapsPortrayal.translate(0.5D, 0.5D, 0.0D);
/*     */ 
/* 110 */     this.trapsPortrayal.scale(sim.spaceWidth / sim.trapGridWidth);
/*     */ 
/* 113 */     this.ballPortrayal.setField(sim.ballSpace);
/*     */ 
/* 117 */     this.wireFrameP = new WireFrameBoxPortrayal3D(0.0D, 0.0D, 0.0D, sim.spaceWidth, sim.spaceHeight, sim.spaceLength);
/*     */ 
/* 120 */     this.display = new Display3D(600.0D, 600.0D, this);
/*     */ 
/* 123 */     this.display.attach(this.trapsPortrayal, "Traps");
/* 124 */     this.display.attach(this.ballPortrayal, "Balls");
/* 125 */     this.display.attach(this.wireFrameP, "Fish tank");
/*     */ 
/* 128 */     this.display.translate(-sim.spaceWidth / 2.0D, -sim.spaceHeight / 2.0D, -sim.spaceLength / 2.0D);
/*     */ 
/* 130 */     this.display.scale(1.0D / Math.max(sim.spaceHeight, Math.max(sim.spaceWidth, sim.spaceLength)));
/*     */ 
/* 132 */     this.displayFrame = this.display.createFrame();
/* 133 */     c.registerFrame(this.displayFrame);
/* 134 */     this.displayFrame.setVisible(true);
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 139 */     super.quit();
/*     */ 
/* 141 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 142 */     this.displayFrame = null;
/* 143 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.mousetraps3d.MouseTraps3DWithUI
 * JD-Core Version:    0.6.2
 */