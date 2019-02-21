/*     */ package sim.app.wcss.tutorial09;
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
/*     */   public static String getName() {
/*  36 */     return "WCSS 2008 Tutorial";
/*     */   }
/*     */ 
/*     */   public void start() {
/*  40 */     super.start();
/*  41 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  46 */     super.load(state);
/*  47 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  52 */     Students students = (Students)this.state;
/*     */ 
/*  55 */     this.yardPortrayal.setField(students.yard);
/*  56 */     this.yardPortrayal.setPortrayalForAll(new OvalPortrayal2D()
/*     */     {
/*     */       public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */       {
/*  60 */         Student student = (Student)object;
/*     */ 
/*  62 */         int agitationShade = (int)(student.getAgitation() * 255.0D / 10.0D);
/*  63 */         if (agitationShade > 255) agitationShade = 255;
/*  64 */         this.paint = new Color(agitationShade, 0, 255 - agitationShade);
/*  65 */         super.draw(object, graphics, info);
/*     */       }
/*     */     });
/*  69 */     this.buddiesPortrayal.setField(new SpatialNetwork2D(students.yard, students.buddies));
/*  70 */     this.buddiesPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D());
/*     */ 
/*  73 */     this.display.reset();
/*  74 */     this.display.setBackdrop(Color.white);
/*     */ 
/*  77 */     this.display.repaint();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/*  82 */     super.init(c);
/*     */ 
/*  85 */     this.display = new Display2D(600.0D, 600.0D, this);
/*     */ 
/*  87 */     this.display.setClipping(false);
/*     */ 
/*  89 */     this.displayFrame = this.display.createFrame();
/*  90 */     this.displayFrame.setTitle("Schoolyard Display");
/*  91 */     c.registerFrame(this.displayFrame);
/*  92 */     this.displayFrame.setVisible(true);
/*  93 */     this.display.attach(this.buddiesPortrayal, "Buddies");
/*  94 */     this.display.attach(this.yardPortrayal, "Yard");
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/*  99 */     super.quit();
/*     */ 
/* 101 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 102 */     this.displayFrame = null;
/* 103 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial09.StudentsWithUI
 * JD-Core Version:    0.6.2
 */