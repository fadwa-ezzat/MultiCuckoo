/*     */ package sim.util.gui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JToggleButton;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.TitledBorder;
/*     */ 
/*     */ public class DisclosurePanel extends JPanel
/*     */ {
/*  37 */   JToggleButton disclosureToggle = new JToggleButton();
/*     */   Component abridgedComponent;
/*     */   Component disclosedComponent;
/*  98 */   boolean disclosed = false;
/*     */ 
/*     */   public DisclosurePanel(String abridgedText, Component disclosedComponent)
/*     */   {
/*  43 */     this(abridgedText, disclosedComponent, null);
/*     */   }
/*     */ 
/*     */   public DisclosurePanel(String abridgedText, Component disclosedComponent, String borderLabel)
/*     */   {
/*  48 */     this(new JButton(abridgedText), disclosedComponent);
/*  49 */     JButton button = (JButton)this.abridgedComponent;
/*  50 */     button.setContentAreaFilled(false);
/*  51 */     button.setFocusPainted(false);
/*  52 */     button.setRequestFocusEnabled(false);
/*  53 */     button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*  54 */     button.setHorizontalAlignment(2);
/*  55 */     button.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event)
/*     */       {
/*  59 */         DisclosurePanel.this.disclosureToggle.doClick();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public DisclosurePanel(Component abridgedComponent, Component disclosedComponent)
/*     */   {
/*  66 */     this(abridgedComponent, disclosedComponent, null);
/*     */   }
/*     */ 
/*     */   public DisclosurePanel(Component abridgedComponent, Component disclosedComponent, String borderLabel)
/*     */   {
/*  71 */     this.disclosureToggle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*  72 */     this.disclosureToggle.setContentAreaFilled(false);
/*  73 */     this.disclosureToggle.setFocusPainted(false);
/*  74 */     this.disclosureToggle.setRequestFocusEnabled(false);
/*  75 */     this.disclosureToggle.setIcon(UIManager.getIcon("Tree.collapsedIcon"));
/*  76 */     this.disclosureToggle.setSelectedIcon(UIManager.getIcon("Tree.expandedIcon"));
/*  77 */     this.abridgedComponent = abridgedComponent;
/*  78 */     this.disclosedComponent = disclosedComponent;
/*  79 */     setLayout(new BorderLayout());
/*  80 */     JPanel b = new JPanel();
/*  81 */     b.setLayout(new BorderLayout());
/*  82 */     b.add(this.disclosureToggle, "North");
/*  83 */     add(b, "West");
/*  84 */     add(abridgedComponent, "Center");
/*     */ 
/*  86 */     if (borderLabel != null) {
/*  87 */       setBorder(new TitledBorder(borderLabel));
/*     */     }
/*  89 */     this.disclosureToggle.addItemListener(new ItemListener()
/*     */     {
/*     */       public void itemStateChanged(ItemEvent e)
/*     */       {
/*  93 */         DisclosurePanel.this.setDisclosed(DisclosurePanel.this.disclosureToggle.isSelected());
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void setDisclosed(boolean disclosed)
/*     */   {
/* 102 */     this.disclosed = disclosed;
/* 103 */     if (disclosed)
/*     */     {
/* 105 */       remove(this.abridgedComponent);
/* 106 */       add(this.disclosedComponent, "Center");
/* 107 */       revalidate();
/*     */     }
/*     */     else
/*     */     {
/* 111 */       remove(this.disclosedComponent);
/* 112 */       add(this.abridgedComponent, "Center");
/* 113 */       revalidate();
/*     */     }
/* 115 */     this.disclosureToggle.setSelected(disclosed);
/*     */   }
/*     */   public boolean isDisclosed() {
/* 118 */     return this.disclosed;
/*     */   }
/*     */ 
/*     */   public Component getAbridgedComponent() {
/* 122 */     return this.abridgedComponent;
/*     */   }
/*     */ 
/*     */   public Component getDisclosedComponent()
/*     */   {
/* 127 */     return this.disclosedComponent;
/*     */   }
/*     */ 
/*     */   public void setAbridgedComponent(Component abridgedComponent)
/*     */   {
/* 132 */     if (!this.disclosureToggle.isSelected())
/*     */     {
/* 134 */       remove(this.abridgedComponent);
/* 135 */       add(abridgedComponent, "Center");
/* 136 */       revalidate();
/*     */     }
/* 138 */     this.abridgedComponent = abridgedComponent;
/*     */   }
/*     */ 
/*     */   public void setDisclosedComponent(Component disclosedComponent)
/*     */   {
/* 143 */     if (this.disclosureToggle.isSelected())
/*     */     {
/* 145 */       remove(this.disclosedComponent);
/* 146 */       add(disclosedComponent, "Center");
/* 147 */       revalidate();
/*     */     }
/* 149 */     this.disclosedComponent = disclosedComponent;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.DisclosurePanel
 * JD-Core Version:    0.6.2
 */