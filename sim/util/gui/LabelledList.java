/*     */ package sim.util.gui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.border.TitledBorder;
/*     */ 
/*     */ public class LabelledList extends JPanel
/*     */ {
/*  46 */   JPanel consolePanel = new JPanel() {
/*     */     public Insets getInsets() {
/*  48 */       return new Insets(0, 2, 0, 2);
/*     */     }
/*  46 */   };
/*     */ 
/*  51 */   GridBagLayout gridbag = new GridBagLayout();
/*  52 */   GridBagConstraints gbc = new GridBagConstraints();
/*  53 */   int y = 0;
/*     */ 
/*     */   public LabelledList()
/*     */   {
/*  66 */     super.setLayout(new BorderLayout());
/*  67 */     this.consolePanel.setLayout(this.gridbag);
/*  68 */     super.add(this.consolePanel, "North");
/*  69 */     this.gbc.ipady = 0; this.gbc.ipady = 0; this.gbc.weighty = 0.0D;
/*     */   }
/*     */ 
/*     */   public LabelledList(String borderLabel)
/*     */   {
/*  76 */     this();
/*  77 */     if (borderLabel != null) setBorder(new TitledBorder(borderLabel));
/*     */   }
/*     */ 
/*     */   public void addLabelled(String left, Component right)
/*     */   {
/*  83 */     add(new JLabel("" + left), right);
/*     */   }
/*     */ 
/*     */   public void add(Component left, Component right)
/*     */   {
/*  89 */     add(null, left, null, right, null);
/*     */   }
/*     */ 
/*     */   public void add(Component farLeft, Component left, Component center, Component right, Component farRight)
/*     */   {
/*  95 */     this.gbc.insets = new Insets(0, 2, 0, 2);
/*  96 */     this.gbc.gridy = this.y;
/*     */ 
/*  98 */     if (farLeft != null)
/*     */     {
/* 100 */       this.gbc.gridx = 0;
/* 101 */       this.gbc.weightx = 0.0D;
/* 102 */       this.gbc.anchor = 17;
/* 103 */       this.gbc.fill = 0;
/* 104 */       this.gbc.gridwidth = 1;
/* 105 */       this.gridbag.setConstraints(farLeft, this.gbc);
/* 106 */       this.consolePanel.add(farLeft);
/*     */     }
/*     */ 
/* 109 */     if (left != null)
/*     */     {
/* 111 */       this.gbc.gridx = 1;
/* 112 */       this.gbc.weightx = 0.0D;
/* 113 */       this.gbc.anchor = 13;
/* 114 */       this.gbc.fill = 0;
/* 115 */       this.gbc.gridwidth = 1;
/* 116 */       this.gridbag.setConstraints(left, this.gbc);
/* 117 */       this.consolePanel.add(left);
/*     */     }
/*     */ 
/* 120 */     if (center != null)
/*     */     {
/* 122 */       this.gbc.gridx = 2;
/* 123 */       this.gbc.weightx = 0.0D;
/* 124 */       this.gbc.anchor = 10;
/* 125 */       this.gbc.fill = 0;
/* 126 */       this.gbc.gridwidth = 1;
/* 127 */       this.gridbag.setConstraints(center, this.gbc);
/* 128 */       this.consolePanel.add(center);
/*     */     }
/*     */ 
/* 131 */     if (right != null)
/*     */     {
/* 133 */       this.gbc.gridx = 3;
/* 134 */       this.gbc.weightx = 1.0D;
/* 135 */       this.gbc.anchor = 17;
/* 136 */       this.gbc.fill = 2;
/* 137 */       this.gbc.gridwidth = 1;
/* 138 */       this.gridbag.setConstraints(right, this.gbc);
/* 139 */       this.consolePanel.add(right);
/*     */     }
/*     */ 
/* 142 */     if (farRight != null)
/*     */     {
/* 144 */       this.gbc.gridx = 4;
/* 145 */       this.gbc.weightx = 0.0D;
/* 146 */       this.gbc.anchor = 13;
/* 147 */       this.gbc.fill = 0;
/* 148 */       this.gbc.gridwidth = 0;
/* 149 */       this.gridbag.setConstraints(farRight, this.gbc);
/* 150 */       this.consolePanel.add(farRight);
/*     */     }
/*     */ 
/* 154 */     this.y += 1;
/*     */   }
/*     */ 
/*     */   public Component add(Component comp)
/*     */   {
/* 160 */     addComponent(comp);
/* 161 */     return comp;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void addComponent(Component comp)
/*     */   {
/* 170 */     this.gbc.gridx = 0; this.gbc.gridy = this.y; this.gbc.weightx = 1.0D; this.gbc.anchor = 10; this.gbc.fill = 2; this.gbc.gridwidth = 5; this.gbc.insets = new Insets(0, 2, 0, 2);
/* 171 */     this.gridbag.setConstraints(comp, this.gbc);
/* 172 */     this.consolePanel.add(comp);
/*     */ 
/* 175 */     this.y += 1;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.LabelledList
 * JD-Core Version:    0.6.2
 */