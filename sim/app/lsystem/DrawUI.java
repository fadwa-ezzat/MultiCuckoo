/*     */ package sim.app.lsystem;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import sim.util.gui.LabelledList;
/*     */ 
/*     */ public class DrawUI extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 7502392279728858809L;
/*  20 */   JButton buttonSet = new JButton("Set");
/*  21 */   JTextField distField = new JTextField("2", 2);
/*  22 */   JTextField angleField = new JTextField("90", 4);
/*     */   LSystemWithUI lsui;
/*     */   LSystem ls;
/*     */ 
/*     */   public DrawUI(LSystemWithUI nLsui)
/*     */   {
/*  36 */     this.lsui = nLsui;
/*  37 */     this.ls = ((LSystem)this.lsui.state);
/*     */     try
/*     */     {
/*  41 */       init();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  45 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/*  52 */     this.buttonSet.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/*  57 */         DrawUI.this.ls.l.segsize = Double.valueOf(DrawUI.this.distField.getText()).doubleValue();
/*  58 */         DrawUI.this.ls.l.angle = (Double.valueOf(DrawUI.this.angleField.getText()).doubleValue() * 3.141592653589793D / 180.0D);
/*     */ 
/*  63 */         DrawUI.this.ls.l.theta = -1.570796326794897D;
/*     */       }
/*     */     });
/*  70 */     setLayout(new BorderLayout());
/*     */ 
/*  75 */     LabelledList list = new LabelledList()
/*     */     {
/*     */       private static final long serialVersionUID = -1043920050611225098L;
/*  78 */       Insets insets = new Insets(5, 5, 5, 5);
/*     */ 
/*     */       public Insets getInsets() {
/*  81 */         return this.insets;
/*     */       }
/*     */     };
/*  86 */     list.addLabelled("Distance: ", this.distField);
/*  87 */     list.addLabelled("Angle: ", this.angleField);
/*     */ 
/*  92 */     Box b = new Box(0)
/*     */     {
/*     */       private static final long serialVersionUID = 1868200221063009327L;
/*  95 */       Insets insets = new Insets(5, 5, 5, 5);
/*     */ 
/*     */       public Insets getInsets() {
/*  98 */         return this.insets;
/*     */       }
/*     */     };
/* 102 */     b.add(this.buttonSet);
/* 103 */     b.add(Box.createGlue());
/*     */ 
/* 105 */     list.addLabelled("", b);
/*     */ 
/* 108 */     add(list, "Center");
/*     */ 
/* 110 */     setVisible(true);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.lsystem.DrawUI
 * JD-Core Version:    0.6.2
 */