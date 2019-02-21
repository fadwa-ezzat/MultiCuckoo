/*     */ package sim.app.wcss.tutorial13;
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
/*     */ import sim.portrayal.simple.CircledPortrayal2D;
/*     */ import sim.portrayal.simple.LabelledPortrayal2D;
/*     */ import sim.portrayal.simple.MovablePortrayal2D;
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
/*  45 */     return "WCSS Tutorial 13: Student Cliques";
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
/*  65 */     this.yardPortrayal.setPortrayalForAll(new MovablePortrayal2D(new CircledPortrayal2D(new LabelledPortrayal2D(new OvalPortrayal2D()
/*     */     {
/*     */       public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */       {
/*  73 */         Student student = (Student)object;
/*     */ 
/*  75 */         int agitationShade = (int)(student.getAgitation() * 255.0D / 10.0D);
/*  76 */         if (agitationShade > 255) agitationShade = 255;
/*  77 */         this.paint = new Color(agitationShade, 0, 255 - agitationShade);
/*  78 */         super.draw(object, graphics, info);
/*     */       }
/*     */     }
/*     */     , 5.0D, null, Color.black, true), 0.0D, 5.0D, Color.green, true)));
/*     */ 
/*  86 */     this.buddiesPortrayal.setField(new SpatialNetwork2D(students.yard, students.buddies));
/*  87 */     this.buddiesPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D());
/*     */ 
/*  90 */     this.display.reset();
/*  91 */     this.display.setBackdrop(Color.white);
/*     */ 
/*  94 */     this.display.repaint();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/*  99 */     super.init(c);
/*     */ 
/* 102 */     this.display = new Display2D(600.0D, 600.0D, this);
/*     */ 
/* 104 */     this.display.setClipping(false);
/*     */ 
/* 106 */     this.displayFrame = this.display.createFrame();
/* 107 */     this.displayFrame.setTitle("Schoolyard Display");
/* 108 */     c.registerFrame(this.displayFrame);
/* 109 */     this.displayFrame.setVisible(true);
/* 110 */     this.display.attach(this.buddiesPortrayal, "Buddies");
/* 111 */     this.display.attach(this.yardPortrayal, "Yard");
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 116 */     super.quit();
/*     */ 
/* 118 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 119 */     this.displayFrame = null;
/* 120 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial13.StudentsWithUI
 * JD-Core Version:    0.6.2
 */