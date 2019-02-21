/*     */ package sim.app.antsforage;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import javax.swing.JFrame;
/*     */ import sim.display.Controller;
/*     */ import sim.display.Display2D;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal.grid.FastValueGridPortrayal2D;
/*     */ import sim.portrayal.grid.SparseGridPortrayal2D;
/*     */ import sim.util.gui.SimpleColorMap;
/*     */ 
/*     */ public class AntsForageWithUI extends GUIState
/*     */ {
/*     */   public Display2D display;
/*     */   public JFrame displayFrame;
/*  20 */   FastValueGridPortrayal2D homePheromonePortrayal = new FastValueGridPortrayal2D("Home Pheromone");
/*  21 */   FastValueGridPortrayal2D foodPheromonePortrayal = new FastValueGridPortrayal2D("Food Pheromone");
/*  22 */   FastValueGridPortrayal2D sitesPortrayal = new FastValueGridPortrayal2D("Site", true);
/*  23 */   FastValueGridPortrayal2D obstaclesPortrayal = new FastValueGridPortrayal2D("Obstacle", true);
/*  24 */   SparseGridPortrayal2D bugPortrayal = new SparseGridPortrayal2D();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  28 */     new AntsForageWithUI().createController();
/*     */   }
/*     */   public AntsForageWithUI() {
/*  31 */     super(new AntsForage(System.currentTimeMillis())); } 
/*  32 */   public AntsForageWithUI(SimState state) { super(state); }
/*     */ 
/*     */   public Object getSimulationInspectedObject() {
/*  35 */     return this.state;
/*     */   }
/*  37 */   public static String getName() { return "Ant Foraging"; }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  41 */     AntsForage af = (AntsForage)this.state;
/*     */ 
/*  44 */     this.homePheromonePortrayal.setField(af.toHomeGrid);
/*  45 */     this.homePheromonePortrayal.setMap(new SimpleColorMap(0.0D, 3.0D, Color.white, new Color(0, 255, 0, 255))
/*     */     {
/*     */       public double filterLevel(double level)
/*     */       {
/*  51 */         return Math.sqrt(Math.sqrt(level));
/*     */       }
/*     */     });
/*  52 */     this.foodPheromonePortrayal.setField(af.toFoodGrid);
/*  53 */     this.foodPheromonePortrayal.setMap(new SimpleColorMap(0.0D, 3.0D, new Color(0, 0, 255, 0), new Color(0, 0, 255, 255))
/*     */     {
/*     */       public double filterLevel(double level)
/*     */       {
/*  58 */         return Math.sqrt(Math.sqrt(level));
/*     */       }
/*     */     });
/*  59 */     this.sitesPortrayal.setField(af.sites);
/*  60 */     this.sitesPortrayal.setMap(new SimpleColorMap(0.0D, 1.0D, new Color(0, 0, 0, 0), new Color(255, 0, 0, 255)));
/*     */ 
/*  65 */     this.obstaclesPortrayal.setField(af.obstacles);
/*  66 */     this.obstaclesPortrayal.setMap(new SimpleColorMap(0.0D, 1.0D, new Color(0, 0, 0, 0), new Color(128, 64, 64, 255)));
/*     */ 
/*  71 */     this.bugPortrayal.setField(af.buggrid);
/*     */ 
/*  74 */     this.display.reset();
/*     */ 
/*  77 */     this.display.repaint();
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  82 */     super.start();
/*     */ 
/*  84 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  89 */     super.load(state);
/*     */ 
/*  91 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/*  96 */     super.init(c);
/*     */ 
/*  99 */     this.display = new Display2D(400.0D, 400.0D, this);
/* 100 */     this.displayFrame = this.display.createFrame();
/* 101 */     c.registerFrame(this.displayFrame);
/* 102 */     this.displayFrame.setVisible(true);
/*     */ 
/* 105 */     this.display.attach(this.homePheromonePortrayal, "Pheromones To Home");
/* 106 */     this.display.attach(this.foodPheromonePortrayal, "Pheromones To Food");
/* 107 */     this.display.attach(this.sitesPortrayal, "Site Locations");
/* 108 */     this.display.attach(this.obstaclesPortrayal, "Obstacles");
/* 109 */     this.display.attach(this.bugPortrayal, "Agents");
/*     */ 
/* 112 */     this.display.setBackdrop(Color.white);
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 117 */     super.quit();
/*     */ 
/* 121 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 122 */     this.displayFrame = null;
/* 123 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.antsforage.AntsForageWithUI
 * JD-Core Version:    0.6.2
 */