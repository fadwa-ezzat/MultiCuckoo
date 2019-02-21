/*     */ package sim.app.lsystem;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTabbedPane;
/*     */ import sim.display.Console;
/*     */ import sim.display.Controller;
/*     */ import sim.display.Display2D;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.portrayal.continuous.ContinuousPortrayal2D;
/*     */ 
/*     */ public class LSystemWithUI extends GUIState
/*     */ {
/*     */   public Display2D display;
/*     */   public JFrame displayFrame;
/*     */   public static Console c;
/*  20 */   private ContinuousPortrayal2D systemPortrayal = new ContinuousPortrayal2D();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  24 */     new LSystemWithUI().createController();
/*     */   }
/*     */   public LSystemWithUI() {
/*  27 */     super(new LSystem(System.currentTimeMillis()));
/*     */   }
/*  29 */   public LSystemWithUI(SimState state) { super(state); } 
/*     */   public static String getName() {
/*  31 */     return "Lindenmayer Systems";
/*     */   }
/*     */ 
/*     */   public void start() {
/*  35 */     super.start();
/*  36 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void load(SimState state)
/*     */   {
/*  41 */     super.load(state);
/*  42 */     setupPortrayals();
/*     */   }
/*     */ 
/*     */   public void setupPortrayals()
/*     */   {
/*  50 */     this.systemPortrayal.setField(((LSystem)this.state).drawEnvironment);
/*     */ 
/*  53 */     this.display.reset();
/*     */ 
/*  56 */     this.display.repaint();
/*     */   }
/*     */ 
/*     */   public void init(Controller c)
/*     */   {
/*  61 */     super.init(c);
/*     */ 
/*  64 */     this.display = new Display2D(400.0D, 400.0D, this);
/*     */ 
/*  71 */     this.display.setClipping(false);
/*     */ 
/*  73 */     this.displayFrame = this.display.createFrame();
/*  74 */     c.registerFrame(this.displayFrame);
/*  75 */     this.displayFrame.setVisible(true);
/*     */ 
/*  78 */     this.display.attach(this.systemPortrayal, "LSystem");
/*     */ 
/*  81 */     this.display.setBackdrop(Color.white);
/*     */ 
/*  84 */     LSystem ls = (LSystem)this.state;
/*  85 */     LSystemData.setVector(ls.l.code, "F");
/*  86 */     ls.l.seed = "F";
/*     */ 
/*  88 */     ls.l.rules.add(new Rule((byte)70, "F[+F]F[-F]F"));
/*     */ 
/*  96 */     ((Console)c).getTabPane().removeTabAt(3);
/*     */ 
/*  98 */     DrawUI draw = new DrawUI(this);
/*  99 */     ((Console)c).getTabPane().addTab("Draw", new JScrollPane(draw));
/*     */ 
/* 101 */     ((Console)c).getTabPane().addTab("Rules", new RuleUI(this, draw));
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 107 */     super.quit();
/*     */ 
/* 109 */     if (this.displayFrame != null) this.displayFrame.dispose();
/* 110 */     this.displayFrame = null;
/* 111 */     this.display = null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.lsystem.LSystemWithUI
 * JD-Core Version:    0.6.2
 */