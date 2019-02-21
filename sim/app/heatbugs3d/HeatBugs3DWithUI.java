/*     */ package sim.app.heatbugs3d;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import javax.swing.JFrame;
/*     */ import sim.app.heatbugs.HeatBugs;
/*     */ import sim.display.Console;
/*     */ import sim.display.Controller;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal3d.FieldPortrayal3D;
/*     */ import sim.portrayal3d.grid.SparseGrid2DPortrayal3D;
/*     */ import sim.portrayal3d.grid.SparseGridPortrayal3D;
/*     */ import sim.portrayal3d.grid.ValueGrid2DPortrayal3D;
/*     */ import sim.portrayal3d.grid.quad.MeshPortrayal;
/*     */ import sim.portrayal3d.grid.quad.QuadPortrayal;
/*     */ import sim.portrayal3d.grid.quad.TilePortrayal;
/*     */ import sim.portrayal3d.simple.ConePortrayal3D;
/*     */ import sim.portrayal3d.simple.TransformedPortrayal3D;
/*     */ import sim.util.gui.SimpleColorMap;
/*     */ 
/*     */ public class HeatBugs3DWithUI extends GUIState
/*     */ {
/*     */   public JFrame displayFrame;
/*     */   public static final int CLASSIC = 0;
/*     */   public static final int STACKED = 1;
/*     */   public static final int TILE = 0;
/*     */   public static final int MESH = 1;
/*     */   public static final int NOZ = 2;
/*  41 */   int heatmode = 1;
/*     */ 
/*  44 */   ValueGrid2DPortrayal3D heatPortrayal = new ValueGrid2DPortrayal3D();
/*  45 */   FieldPortrayal3D bugPortrayal = null;
/*  46 */   QuadPortrayal quadP = null;
/*     */   public Display3D display;
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  50 */     HeatBugs3DWithUI heatbugs = new HeatBugs3DWithUI(new HeatBugs(System.currentTimeMillis(), 100, 100, 100), 1, 1);
/*     */ 
/*  60 */     Console c = new Console(heatbugs);
/*  61 */     c.setVisible(true);
/*     */   }
/*     */ 
/*     */   public HeatBugs3DWithUI()
/*     */   {
/*  66 */     this(new HeatBugs(System.currentTimeMillis()), 1, 1);
/*     */   }
/*     */ 
/*     */   public HeatBugs3DWithUI(SimState state, int bugmode, int heatmode)
/*     */   {
/*  71 */     super(state);
/*  72 */     this.heatmode = heatmode;
/*     */ 
/*  79 */     if (bugmode == 1)
/*  80 */       this.bugPortrayal = new SparseGrid2DPortrayal3D();
/*     */     else
/*  82 */       this.bugPortrayal = new SparseGridPortrayal3D();
/*     */   }
/*     */ 
/*     */   public static String getName()
/*     */   {
/*  87 */     return "3D HeatBugs";
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  92 */     super.start();
/*  93 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  98 */     super.load(state);
/*     */ 
/* 100 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/* 108 */     SimpleColorMap cm = new SimpleColorMap();
/* 109 */     cm.setLevels(0.0D, 32000.0D, Color.blue, Color.red);
/*     */ 
/* 112 */     TransformedPortrayal3D p = new TransformedPortrayal3D(new ConePortrayal3D());
/* 113 */     p.rotateX(90.0D);
/* 114 */     this.bugPortrayal.setPortrayalForAll(p);
/*     */ 
/* 118 */     switch (this.heatmode) {
/*     */     case 0:
/* 120 */       this.quadP = new TilePortrayal(cm, 0.0005000000237487257D); break;
/*     */     case 1:
/* 121 */       this.quadP = new MeshPortrayal(cm, 0.0005000000237487257D); break;
/*     */     case 2:
/* 123 */       this.quadP = new TilePortrayal(cm);
/* 124 */       this.bugPortrayal.translate(0.0D, 0.0D, 1.0D);
/* 125 */       break;
/*     */     default:
/* 127 */       throw new RuntimeException("default case should never occur");
/*     */     }
/* 129 */     this.heatPortrayal.setPortrayalForAll(this.quadP);
/*     */ 
/* 136 */     this.heatPortrayal.setField(((HeatBugs)this.state).valgrid);
/* 137 */     this.bugPortrayal.setField(((HeatBugs)this.state).buggrid);
/*     */ 
/* 141 */     this.display.reset();
/*     */ 
/* 144 */     this.display.createSceneGraph();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/* 151 */     super.init(c);
/*     */ 
/* 153 */     this.display = new Display3D(600.0D, 600.0D, this);
/*     */ 
/* 156 */     this.display.attach(this.heatPortrayal, "Heat");
/* 157 */     this.display.attach(this.bugPortrayal, "Bugs");
/* 158 */     this.heatPortrayal.setValueName("Heat");
/*     */ 
/* 160 */     HeatBugs hbState = (HeatBugs)this.state;
/*     */ 
/* 169 */     this.display.translate((hbState.gridWidth - 1) / -2.0D, (hbState.gridHeight - 1) / -2.0D, 0.0D);
/*     */ 
/* 173 */     this.display.scale(1.0D / Math.max(hbState.gridWidth, hbState.gridHeight));
/*     */ 
/* 175 */     this.displayFrame = this.display.createFrame();
/* 176 */     c.registerFrame(this.displayFrame);
/* 177 */     this.displayFrame.setVisible(true);
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 182 */     super.quit();
/*     */ 
/* 184 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 185 */     this.displayFrame = null;
/* 186 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.heatbugs3d.HeatBugs3DWithUI
 * JD-Core Version:    0.6.2
 */