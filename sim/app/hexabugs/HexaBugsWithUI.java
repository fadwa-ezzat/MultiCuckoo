/*     */ package sim.app.hexabugs;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JFrame;
/*     */ import sim.display.Controller;
/*     */ import sim.display.Display2D;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.SimpleInspector;
/*     */ import sim.portrayal.grid.FastHexaValueGridPortrayal2D;
/*     */ import sim.portrayal.grid.HexaSparseGridPortrayal2D;
/*     */ import sim.portrayal.grid.HexaValueGridPortrayal2D;
/*     */ import sim.portrayal.simple.MovablePortrayal2D;
/*     */ import sim.portrayal.simple.OvalPortrayal2D;
/*     */ import sim.util.gui.ColorMap;
/*     */ import sim.util.gui.SimpleColorMap;
/*     */ 
/*     */ public class HexaBugsWithUI extends GUIState
/*     */ {
/*     */   public Display2D display;
/*     */   public JFrame displayFrame;
/*  22 */   HexaSparseGridPortrayal2D bugPortrayal = new HexaSparseGridPortrayal2D();
/*     */ 
/*  27 */   FastHexaValueGridPortrayal2D heatPortrayal = new FastHexaValueGridPortrayal2D("Heat");
/*  28 */   HexaValueGridPortrayal2D heatPortrayal2 = new HexaValueGridPortrayal2D("Heat");
/*  29 */   HexaValueGridPortrayal2D currentHeatPortrayal = this.heatPortrayal;
/*     */ 
/* 180 */   public static final double HEXAGONAL_RATIO = 2.0D / Math.sqrt(3.0D);
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  33 */     new HexaBugsWithUI().createController();
/*     */   }
/*     */   public HexaBugsWithUI() {
/*  36 */     super(new HexaBugs(System.currentTimeMillis())); } 
/*  37 */   public HexaBugsWithUI(SimState state) { super(state); } 
/*     */   public static String getName() {
/*  39 */     return "HexaBugs";
/*     */   }
/*  41 */   public Object getSimulationInspectedObject() { return this.state; }
/*     */ 
/*     */ 
/*     */   public Inspector getInspector()
/*     */   {
/*  90 */     final Inspector originalInspector = super.getInspector();
/*  91 */     SimpleInspector hexInspector = new SimpleInspector(new HexagonChoice(), this);
/*     */ 
/* 100 */     originalInspector.setVolatile(true);
/*     */ 
/* 103 */     Inspector newInspector = new Inspector() {
/*     */       public void updateInspector() {
/* 105 */         originalInspector.updateInspector();
/*     */       }
/*     */     };
/* 107 */     newInspector.setVolatile(false);
/*     */ 
/* 116 */     Box b = new Box(0) {
/*     */       public Insets getInsets() {
/* 118 */         return new Insets(2, 2, 2, 2);
/*     */       }
/*     */     };
/* 120 */     b.add(newInspector.makeUpdateButton());
/* 121 */     b.add(Box.createGlue());
/*     */ 
/* 127 */     Box b2 = new Box(1);
/* 128 */     b2.add(b);
/* 129 */     b2.add(hexInspector);
/* 130 */     b2.add(Box.createGlue());
/*     */ 
/* 134 */     newInspector.setLayout(new BorderLayout());
/* 135 */     newInspector.add(b2, "North");
/* 136 */     newInspector.add(originalInspector, "Center");
/*     */ 
/* 138 */     return newInspector;
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/* 145 */     super.start();
/*     */ 
/* 147 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/* 152 */     super.load(state);
/*     */ 
/* 154 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/* 162 */     ColorMap map = new SimpleColorMap(0.0D, 32000.0D, Color.black, Color.red);
/* 163 */     this.heatPortrayal.setField(((HexaBugs)this.state).valgrid);
/* 164 */     this.heatPortrayal.setMap(map);
/* 165 */     this.heatPortrayal2.setField(((HexaBugs)this.state).valgrid);
/* 166 */     this.heatPortrayal2.setMap(map);
/*     */ 
/* 168 */     this.bugPortrayal.setField(((HexaBugs)this.state).buggrid);
/* 169 */     this.bugPortrayal.setPortrayalForAll(new MovablePortrayal2D(new OvalPortrayal2D(Color.white)));
/*     */ 
/* 173 */     this.display.reset();
/*     */ 
/* 176 */     this.display.repaint();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/* 184 */     super.init(c);
/*     */ 
/* 196 */     double scale = 4.0D;
/* 197 */     double m = 100.0D;
/* 198 */     double n = 100.0D;
/* 199 */     int height = 402;
/* 200 */     int width = (int)(75.25D * HEXAGONAL_RATIO * 4.0D);
/*     */ 
/* 202 */     this.display = new Display2D(width, 402.0D, this);
/* 203 */     this.displayFrame = this.display.createFrame();
/* 204 */     c.registerFrame(this.displayFrame);
/* 205 */     this.displayFrame.setVisible(true);
/*     */ 
/* 208 */     this.display.attach(this.currentHeatPortrayal, "Heat");
/* 209 */     this.display.attach(this.bugPortrayal, "Bugs");
/*     */ 
/* 212 */     this.display.setBackdrop(Color.black);
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 217 */     super.quit();
/*     */ 
/* 219 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 220 */     this.displayFrame = null;
/* 221 */     this.display = null;
/*     */   }
/*     */ 
/*     */   public class HexagonChoice
/*     */   {
/*  55 */     int cells = 0;
/*     */ 
/*     */     public HexagonChoice() {  } 
/*  56 */     public Object domDisplayGridCellsAs() { return new Object[] { "Rectangles", "Hexagons" }; } 
/*  57 */     public int getDisplayGridCellsAs() { return this.cells; }
/*     */ 
/*     */     public void setDisplayGridCellsAs(int val) {
/*  60 */       if (val == 0)
/*     */       {
/*  62 */         this.cells = val;
/*  63 */         HexaBugsWithUI.this.currentHeatPortrayal = HexaBugsWithUI.this.heatPortrayal;
/*     */       }
/*  65 */       else if (val == 1)
/*     */       {
/*  67 */         this.cells = val;
/*  68 */         HexaBugsWithUI.this.currentHeatPortrayal = HexaBugsWithUI.this.heatPortrayal2;
/*     */       }
/*     */ 
/*  72 */       HexaBugsWithUI.this.display.detachAll();
/*  73 */       HexaBugsWithUI.this.display.attach(HexaBugsWithUI.this.currentHeatPortrayal, "Heat");
/*  74 */       HexaBugsWithUI.this.display.attach(HexaBugsWithUI.this.bugPortrayal, "Bugs");
/*     */ 
/*  77 */       HexaBugsWithUI.this.display.repaint();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.hexabugs.HexaBugsWithUI
 * JD-Core Version:    0.6.2
 */