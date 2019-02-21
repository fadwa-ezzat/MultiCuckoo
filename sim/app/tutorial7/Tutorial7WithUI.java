/*     */ package sim.app.tutorial7;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import javax.swing.JFrame;
/*     */ import sim.app.tutorial6.Tutorial6WithUI;
/*     */ import sim.display.Controller;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal3d.grid.SparseGridPortrayal3D;
/*     */ import sim.portrayal3d.grid.ValueGrid2DPortrayal3D;
/*     */ import sim.portrayal3d.grid.quad.MeshPortrayal;
/*     */ import sim.portrayal3d.grid.quad.TilePortrayal;
/*     */ import sim.portrayal3d.simple.SpherePortrayal3D;
/*     */ import sim.util.gui.SimpleColorMap;
/*     */ 
/*     */ public class Tutorial7WithUI extends GUIState
/*     */ {
/*     */   public Display3D display;
/*     */   public JFrame displayFrame;
/*  23 */   SparseGridPortrayal3D fliesPortrayal = new SparseGridPortrayal3D();
/*  24 */   ValueGrid2DPortrayal3D xProjectionPortrayal = new ValueGrid2DPortrayal3D("X Projection");
/*  25 */   ValueGrid2DPortrayal3D yProjectionPortrayal = new ValueGrid2DPortrayal3D("Y Projection");
/*  26 */   ValueGrid2DPortrayal3D zProjectionPortrayal = new ValueGrid2DPortrayal3D("Z Projection");
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  30 */     new Tutorial7WithUI().createController();
/*     */   }
/*     */   public Tutorial7WithUI() {
/*  33 */     super(new Tutorial7(System.currentTimeMillis())); } 
/*  34 */   public Tutorial7WithUI(SimState state) { super(state); } 
/*  35 */   public static String getName() { return "Tutorial 7: Projections"; }
/*     */ 
/*     */ 
/*     */   public void start()
/*     */   {
/*  48 */     super.start();
/*  49 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  54 */     super.load(state);
/*  55 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  62 */     Tutorial7 tut = (Tutorial7)this.state;
/*     */ 
/*  64 */     this.fliesPortrayal.setField(tut.flies);
/*  65 */     this.xProjectionPortrayal.setField(tut.xProjection);
/*  66 */     this.yProjectionPortrayal.setField(tut.yProjection);
/*  67 */     this.zProjectionPortrayal.setField(tut.zProjection);
/*     */ 
/*  69 */     this.display.reset();
/*  70 */     this.display.createSceneGraph();
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/*  75 */     super.quit();
/*     */ 
/*  77 */     if (this.displayFrame != null) this.displayFrame.dispose();
/*  78 */     this.displayFrame = null;
/*  79 */     this.display = null;
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/*  84 */     super.init(c);
/*     */ 
/*  86 */     Tutorial7 tut = (Tutorial7)this.state;
/*     */ 
/*  89 */     this.fliesPortrayal.setPortrayalForAll(new SpherePortrayal3D(0.5D));
/*     */ 
/*  93 */     SimpleColorMap map = new SimpleColorMap(0.0D, 4.0D, Color.green, Color.yellow);
/*  94 */     this.xProjectionPortrayal.setPortrayalForAll(new TilePortrayal(map));
/*  95 */     this.xProjectionPortrayal.setTransparency(0.800000011920929D);
/*     */ 
/*  97 */     this.xProjectionPortrayal.translate(0.0D, 0.0D, -1.0D);
/*  98 */     this.xProjectionPortrayal.rotateX(90.0D);
/*  99 */     this.xProjectionPortrayal.rotateZ(90.0D);
/*     */ 
/* 103 */     map = new SimpleColorMap(0.0D, 4.0D, Color.blue, Color.yellow);
/* 104 */     this.yProjectionPortrayal.setPortrayalForAll(new TilePortrayal(map, 1.0D));
/*     */ 
/* 106 */     this.yProjectionPortrayal.translate(0.0D, 0.0D, 1.0D);
/* 107 */     this.yProjectionPortrayal.rotateX(90.0D);
/*     */ 
/* 111 */     map = new SimpleColorMap(0.0D, 4.0D, Color.red, Color.blue);
/* 112 */     this.zProjectionPortrayal.setPortrayalForAll(new MeshPortrayal(map, -0.5D));
/*     */ 
/* 114 */     this.zProjectionPortrayal.translate(0.0D, 0.0D, -1.0D);
/*     */ 
/* 117 */     this.zProjectionPortrayal.setUsingTriangles(true);
/*     */ 
/* 120 */     this.zProjectionPortrayal.setImage(Tutorial6WithUI.loadImage("earthmap.jpg"));
/*     */ 
/* 123 */     this.display = new Display3D(600.0D, 600.0D, this);
/* 124 */     this.display.attach(this.fliesPortrayal, "Flies");
/* 125 */     this.display.attach(this.xProjectionPortrayal, "X Projection");
/* 126 */     this.display.attach(this.yProjectionPortrayal, "Y Projection");
/* 127 */     this.display.attach(this.zProjectionPortrayal, "Z Projection");
/*     */ 
/* 130 */     float scale = Math.max(Math.max(tut.width, tut.height), tut.length);
/* 131 */     this.display.scale(1.0F / scale);
/*     */ 
/* 133 */     this.displayFrame = this.display.createFrame();
/* 134 */     c.registerFrame(this.displayFrame);
/* 135 */     this.displayFrame.setVisible(true);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial7.Tutorial7WithUI
 * JD-Core Version:    0.6.2
 */