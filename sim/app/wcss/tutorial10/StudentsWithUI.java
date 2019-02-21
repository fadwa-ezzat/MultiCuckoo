/*     */ package sim.app.wcss.tutorial10;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import javax.swing.JFrame;
/*     */ import sim.display.Console;
/*     */ import sim.display.Controller;
/*     */ import sim.display.Display2D;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*     */ import sim.portrayal.network.NetworkPortrayal2D;
/*     */ import sim.portrayal.network.SimpleEdgePortrayal2D;
/*     */ import sim.portrayal.network.SpatialNetwork2D;
/*     */ import sim.portrayal.simple.OvalPortrayal2D;
/*     */ 
/*     */ public class StudentsWithUI extends GUIState
/*     */ {
/*     */   public Display2D display;
/*     */   public JFrame displayFrame;
/*  23 */   ContinuousPortrayal2D yardPortrayal = new ContinuousPortrayal2D();
/*  24 */   NetworkPortrayal2D buddiesPortrayal = new NetworkPortrayal2D();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  28 */     StudentsWithUI vid = new StudentsWithUI();
/*  29 */     Console c = new Console(vid);
/*  30 */     c.setVisible(true);
/*     */   }
/*     */   public StudentsWithUI() {
/*  33 */     super(new Students(System.currentTimeMillis())); } 
/*  34 */   public StudentsWithUI(SimState state) { super(state); } 
/*     */   public Object getSimulationInspectedObject() {
/*  36 */     return this.state;
/*     */   }
/*     */ 
/*     */   public Inspector getInspector() {
/*  40 */     Inspector i = super.getInspector();
/*  41 */     i.setVolatile(true);
/*  42 */     return i;
/*     */   }
/*     */   public static String getName() {
/*  45 */     return "WCSS 2008 Tutorial";
/*     */   }
/*     */ 
/*     */   public void start() {
/*  49 */     super.start();
/*  50 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  55 */     super.load(state);
/*  56 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  61 */     Students students = (Students)this.state;
/*     */ 
/*  64 */     this.yardPortrayal.setField(students.yard);
/*  65 */     this.yardPortrayal.setPortrayalForAll(new OvalPortrayal2D()
/*     */     {
/*     */       public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */       {
/*  69 */         Student student = (Student)object;
/*     */ 
/*  71 */         int agitationShade = (int)(student.getAgitation() * 255.0D / 10.0D);
/*  72 */         if (agitationShade > 255) agitationShade = 255;
/*  73 */         this.paint = new Color(agitationShade, 0, 255 - agitationShade);
/*  74 */         super.draw(object, graphics, info);
/*     */       }
/*     */     });
/*  78 */     this.buddiesPortrayal.setField(new SpatialNetwork2D(students.yard, students.buddies));
/*  79 */     this.buddiesPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D());
/*     */ 
/*  82 */     this.display.reset();
/*  83 */     this.display.setBackdrop(Color.white);
/*     */ 
/*  86 */     this.display.repaint();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/*  91 */     super.init(c);
/*     */ 
/*  94 */     this.display = new Display2D(600.0D, 600.0D, this);
/*     */ 
/*  96 */     this.display.setClipping(false);
/*     */ 
/*  98 */     this.displayFrame = this.display.createFrame();
/*  99 */     this.displayFrame.setTitle("Schoolyard Display");
/* 100 */     c.registerFrame(this.displayFrame);
/* 101 */     this.displayFrame.setVisible(true);
/* 102 */     this.display.attach(this.buddiesPortrayal, "Buddies");
/* 103 */     this.display.attach(this.yardPortrayal, "Yard");
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 108 */     super.quit();
/*     */ 
/* 110 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 111 */     this.displayFrame = null;
/* 112 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial10.StudentsWithUI
 * JD-Core Version:    0.6.2
 */