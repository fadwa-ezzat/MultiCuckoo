/*     */ package sim.app.wcss.tutorial14;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.text.DecimalFormat;
/*     */ import javax.swing.JFrame;
/*     */ import sim.display.Console;
/*     */ import sim.display.Controller;
/*     */ import sim.display.Display2D;
/*     */ import sim.display.GUIState;
/*     */ import sim.display3d.Display3D;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.network.Edge;
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
/*     */ import sim.portrayal3d.continuous.ContinuousPortrayal3D;
/*     */ import sim.portrayal3d.network.CylinderEdgePortrayal3D;
/*     */ import sim.portrayal3d.network.NetworkPortrayal3D;
/*     */ import sim.portrayal3d.network.SimpleEdgePortrayal3D;
/*     */ import sim.portrayal3d.network.SpatialNetwork3D;
/*     */ import sim.portrayal3d.simple.ConePortrayal3D;
/*     */ 
/*     */ public class StudentsWithUI extends GUIState
/*     */ {
/*     */   public Display2D display;
/*     */   public JFrame displayFrame;
/*  30 */   ContinuousPortrayal2D yardPortrayal = new ContinuousPortrayal2D();
/*  31 */   NetworkPortrayal2D buddiesPortrayal = new NetworkPortrayal2D();
/*     */   public Display3D display3d;
/*     */   public JFrame displayFrame3d;
/*  36 */   ContinuousPortrayal3D agitatedYardPortrayal = new ContinuousPortrayal3D();
/*  37 */   NetworkPortrayal3D agitatedBuddiesPortrayal = new NetworkPortrayal3D();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  42 */     StudentsWithUI vid = new StudentsWithUI();
/*  43 */     Console c = new Console(vid);
/*  44 */     c.setVisible(true);
/*     */   }
/*     */   public StudentsWithUI() {
/*  47 */     super(new Students(System.currentTimeMillis())); } 
/*  48 */   public StudentsWithUI(SimState state) { super(state); } 
/*     */   public Object getSimulationInspectedObject() {
/*  50 */     return this.state;
/*     */   }
/*     */ 
/*     */   public Inspector getInspector() {
/*  54 */     Inspector i = super.getInspector();
/*  55 */     i.setVolatile(true);
/*  56 */     return i;
/*     */   }
/*     */   public static String getName() {
/*  59 */     return "WCSS Tutorial 14: Student Cliques (in 3D)";
/*     */   }
/*     */ 
/*     */   public void start() {
/*  63 */     super.start();
/*  64 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  69 */     super.load(state);
/*  70 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  75 */     Students students = (Students)this.state;
/*     */ 
/*  78 */     this.yardPortrayal.setField(students.yard);
/*  79 */     this.yardPortrayal.setPortrayalForAll(new MovablePortrayal2D(new CircledPortrayal2D(new LabelledPortrayal2D(new OvalPortrayal2D()
/*     */     {
/*     */       public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */       {
/*  87 */         Student student = (Student)object;
/*     */ 
/*  89 */         int agitationShade = (int)(student.getAgitation() * 255.0D / 10.0D);
/*  90 */         if (agitationShade > 255) agitationShade = 255;
/*  91 */         this.paint = new Color(agitationShade, 0, 255 - agitationShade);
/*  92 */         super.draw(object, graphics, info);
/*     */       }
/*     */     }
/*     */     , 5.0D, null, Color.black, true), 0.0D, 5.0D, Color.green, true)));
/*     */ 
/* 100 */     this.buddiesPortrayal.setField(new SpatialNetwork2D(students.yard, students.buddies));
/* 101 */     this.buddiesPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D());
/*     */ 
/* 104 */     this.display.reset();
/* 105 */     this.display.setBackdrop(Color.white);
/*     */ 
/* 108 */     this.display.repaint();
/*     */ 
/* 111 */     this.agitatedYardPortrayal.setField(students.agitatedYard);
/* 112 */     this.agitatedYardPortrayal.setPortrayalForAll(new ConePortrayal3D(Color.red, 2.0D));
/*     */ 
/* 114 */     this.agitatedBuddiesPortrayal.setField(new SpatialNetwork3D(students.agitatedYard, students.buddies));
/* 115 */     SimpleEdgePortrayal3D ep = new CylinderEdgePortrayal3D()
/*     */     {
/* 117 */       DecimalFormat format = new DecimalFormat("#.##");
/*     */ 
/*     */       public String getLabel(Edge edge)
/*     */       {
/* 121 */         return "" + this.format.format(edge.getWeight());
/*     */       }
/*     */     };
/* 125 */     ep.setLabelScale(0.5D);
/* 126 */     this.agitatedBuddiesPortrayal.setPortrayalForAll(ep);
/*     */ 
/* 128 */     this.display3d.createSceneGraph();
/* 129 */     this.display3d.reset();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/* 134 */     super.init(c);
/*     */ 
/* 137 */     this.display = new Display2D(600.0D, 600.0D, this);
/*     */ 
/* 139 */     this.display.setClipping(false);
/*     */ 
/* 141 */     this.displayFrame = this.display.createFrame();
/* 142 */     this.displayFrame.setTitle("Schoolyard Display");
/* 143 */     c.registerFrame(this.displayFrame);
/* 144 */     this.displayFrame.setVisible(true);
/* 145 */     this.display.attach(this.buddiesPortrayal, "Buddies");
/* 146 */     this.display.attach(this.yardPortrayal, "Yard");
/*     */ 
/* 149 */     this.display3d = new Display3D(300.0D, 300.0D, this);
/* 150 */     double width = 100.0D;
/* 151 */     this.display3d.translate(-width / 2.0D, -width / 2.0D, 0.0D);
/* 152 */     this.display3d.scale(2.0D / width);
/*     */ 
/* 154 */     this.displayFrame3d = this.display3d.createFrame();
/* 155 */     this.displayFrame3d.setTitle("Schoolyard Display... NOW IN 3-D!");
/* 156 */     c.registerFrame(this.displayFrame3d);
/* 157 */     this.displayFrame3d.setVisible(true);
/* 158 */     this.display3d.attach(this.agitatedBuddiesPortrayal, "Buddies ... IN 3-D!");
/* 159 */     this.display3d.attach(this.agitatedYardPortrayal, "Yard ... IN 3-D!");
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 164 */     super.quit();
/*     */ 
/* 166 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 167 */     this.displayFrame = null;
/* 168 */     this.display = null;
/*     */ 
/* 170 */     if (this.displayFrame3d != null) this.displayFrame3d.dispose();
/* 171 */     this.displayFrame3d = null;
/* 172 */     this.display3d = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial14.StudentsWithUI
 * JD-Core Version:    0.6.2
 */