/*     */ package sim.app.tutorial6;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JFrame;
/*     */ import sim.display.Controller;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.portrayal3d.continuous.ContinuousPortrayal3D;
/*     */ import sim.portrayal3d.simple.SpherePortrayal3D;
/*     */ import sim.portrayal3d.simple.TransformedPortrayal3D;
/*     */ import sim.util.Bag;
/*     */ 
/*     */ public class Tutorial6WithUI extends GUIState
/*     */ {
/*     */   public Display3D display;
/*     */   public JFrame displayFrame;
/*  22 */   ContinuousPortrayal3D bodyPortrayal = new ContinuousPortrayal3D();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  26 */     new Tutorial6WithUI().createController();
/*     */   }
/*     */   public Tutorial6WithUI() {
/*  29 */     super(new Tutorial6(System.currentTimeMillis())); } 
/*  30 */   public Tutorial6WithUI(SimState state) { super(state); } 
/*     */   public static String getName() {
/*  32 */     return "Tutorial 6: Planets";
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  45 */     super.start();
/*  46 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  51 */     super.load(state);
/*  52 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/*  57 */     super.quit();
/*     */ 
/*  59 */     if (this.displayFrame != null) this.displayFrame.dispose();
/*  60 */     this.displayFrame = null;
/*  61 */     this.display = null;
/*     */   }
/*     */ 
/*     */   public static Image loadImage(String filename)
/*     */   {
/*  67 */     return new ImageIcon(Tutorial6.class.getResource(filename)).getImage();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  74 */     Tutorial6 tut = (Tutorial6)this.state;
/*  75 */     this.bodyPortrayal.setField(tut.bodies);
/*     */ 
/*  78 */     Bag objs = tut.bodies.getAllObjects();
/*     */ 
/*  83 */     String[] imageNames = { "sunmap.jpg", "mercurymap.jpg", "venusmap.jpg", "earthmap.jpg", "marsmap.jpg", "jupitermap.jpg", "saturnmap.jpg", "uranusmap.jpg", "neptunemap.jpg", "plutomap.jpg" };
/*     */ 
/*  94 */     for (int i = 0; i < 10; i++)
/*     */     {
/*  96 */       TransformedPortrayal3D trans = new TransformedPortrayal3D(new SpherePortrayal3D(loadImage(imageNames[i]), (float)(Math.log(Tutorial6.DIAMETER[i]) * 50.0D), 50));
/*     */ 
/* 101 */       trans.rotateX(90.0D);
/* 102 */       this.bodyPortrayal.setPortrayalForObject(objs.objs[i], trans);
/*     */     }
/*     */ 
/* 105 */     this.display.reset();
/* 106 */     this.display.createSceneGraph();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/* 111 */     super.init(c);
/*     */ 
/* 113 */     Tutorial6 tut = (Tutorial6)this.state;
/* 114 */     this.bodyPortrayal.setField(tut.bodies);
/*     */ 
/* 116 */     this.display = new Display3D(600.0D, 600.0D, this);
/* 117 */     this.display.attach(this.bodyPortrayal, "The Solar System");
/* 118 */     this.display.scale(1.0D / (Tutorial6.DISTANCE[9] * 1.05D));
/*     */ 
/* 120 */     this.displayFrame = this.display.createFrame();
/* 121 */     c.registerFrame(this.displayFrame);
/* 122 */     this.displayFrame.setVisible(true);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial6.Tutorial6WithUI
 * JD-Core Version:    0.6.2
 */