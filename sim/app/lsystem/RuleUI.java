/*     */ package sim.app.lsystem;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.FileDialog;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import sim.util.gui.LabelledList;
/*     */ 
/*     */ public class RuleUI extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = -8650952120231540392L;
/*  22 */   JButton buttonGo = new JButton("Calculate");
/*  23 */   JButton buttonCancel = new JButton("Cancel");
/*  24 */   JButton buttonSave = new JButton("Save");
/*  25 */   JButton buttonLoad = new JButton("Load");
/*  26 */   JButton buttonHelp = new JButton("Help");
/*     */ 
/*  29 */   JTable ruleTable = new JTable(20, 2);
/*  30 */   JScrollPane scrollPane = new JScrollPane(this.ruleTable);
/*     */ 
/*  32 */   JProgressBar calcProgress = new JProgressBar(0, 100);
/*     */ 
/*  34 */   JTextField seedField = new JTextField("F-F-F-F", 10);
/*  35 */   JTextField stepField = new JTextField("3", 3);
/*     */ 
/*  38 */   JPanel helpPanel = new JPanel();
/*     */   LSystemWithUI lsui;
/*     */   LSystem ls;
/*     */   DrawUI dui;
/*  48 */   int steps = 0;
/*     */   Runnable calcRunnable;
/*     */   Thread calcThread;
/*  51 */   Object lock = new Object();
/*  52 */   boolean stop = false;
/*     */ 
/*     */   public Frame getFrame()
/*     */   {
/*  57 */     Component c = this;
/*     */ 
/*  59 */     while (c.getParent() != null) {
/*  60 */       c = c.getParent();
/*     */     }
/*  62 */     return (Frame)c;
/*     */   }
/*     */ 
/*     */   void getRulesFromUI()
/*     */   {
/*  73 */     this.ls.l.seed = this.seedField.getText();
/*  74 */     LSystemData.setVector(this.ls.l.code, this.ls.l.seed);
/*     */ 
/*  76 */     this.ls.l.expansions = Integer.valueOf(this.stepField.getText()).intValue();
/*     */ 
/*  79 */     this.ls.l.rules.clear();
/*     */ 
/*  82 */     for (int r = 0; r < this.ruleTable.getRowCount(); r++)
/*     */     {
/*  85 */       if ((this.ruleTable.getValueAt(r, 0) != null) && (this.ruleTable.getValueAt(r, 1) != null))
/*     */       {
/*  87 */         if ((((String)this.ruleTable.getValueAt(r, 0)).length() > 0) && (((String)this.ruleTable.getValueAt(r, 0)).length() > 0)) {
/*  88 */           this.ls.l.rules.add(new Rule((byte)((String)this.ruleTable.getValueAt(r, 0)).substring(0, 1).charAt(0), (String)this.ruleTable.getValueAt(r, 1)));
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  94 */     this.steps = Integer.valueOf(this.stepField.getText()).intValue();
/*     */   }
/*     */ 
/*     */   public RuleUI(LSystemWithUI nLsui, DrawUI nDui)
/*     */   {
/* 101 */     this.lsui = nLsui;
/* 102 */     this.ls = ((LSystem)this.lsui.state);
/* 103 */     this.dui = nDui;
/*     */     try
/*     */     {
/* 107 */       init();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 111 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/* 122 */     this.calcRunnable = new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 126 */         int h = 0;
/* 127 */         int p = 0;
/* 128 */         int r = 0;
/* 129 */         boolean ruleApplied = false;
/*     */ 
/* 135 */         ByteList newCode = new ByteList(RuleUI.this.ls.l.code.b.length);
/*     */         while (true)
/*     */         {
/* 142 */           synchronized (RuleUI.this.lock)
/*     */           {
/* 144 */             if (RuleUI.this.stop) {
/* 145 */               break;
/*     */             }
/*     */           }
/*     */ 
/* 149 */           if (h >= RuleUI.this.steps)
/*     */           {
/*     */             break;
/*     */           }
/* 153 */           ruleApplied = false;
/*     */ 
/* 155 */           for (r = 0; r < RuleUI.this.ls.l.rules.size(); r++)
/*     */           {
/* 157 */             if (RuleUI.this.ls.l.code.b[p] == ((Rule)RuleUI.this.ls.l.rules.get(r)).pattern)
/*     */             {
/* 159 */               newCode.addAll(((Rule)RuleUI.this.ls.l.rules.get(r)).replace);
/* 160 */               ruleApplied = true;
/*     */ 
/* 163 */               break;
/*     */             }
/*     */           }
/*     */ 
/* 167 */           if (!ruleApplied)
/*     */           {
/* 169 */             newCode.add(RuleUI.this.ls.l.code.b[p]);
/*     */           }
/*     */ 
/* 172 */           p++;
/*     */ 
/* 180 */           if (p % 100 == 0)
/*     */           {
/* 182 */             SwingUtilities.invokeLater(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 187 */                 int i = RuleUI.this.calcProgress.getValue();
/* 188 */                 if (i < 100)
/* 189 */                   i++;
/*     */                 else {
/* 191 */                   i = 0;
/*     */                 }
/* 193 */                 RuleUI.this.calcProgress.setValue(i);
/*     */               }
/*     */ 
/*     */             });
/*     */           }
/*     */ 
/* 201 */           if (p >= RuleUI.this.ls.l.code.length)
/*     */           {
/* 203 */             p = 0;
/* 204 */             h++;
/* 205 */             RuleUI.this.ls.l.code = newCode;
/* 206 */             newCode = new ByteList(RuleUI.this.ls.l.code.length);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 212 */         SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 217 */             RuleUI.this.buttonGo.setEnabled(true);
/* 218 */             RuleUI.this.buttonCancel.setEnabled(false);
/* 219 */             RuleUI.this.calcProgress.setValue(0);
/* 220 */             RuleUI.this.calcProgress.setString("Done!");
/*     */           }
/*     */         });
/*     */       }
/*     */     };
/* 229 */     this.buttonGo.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/* 233 */         RuleUI.this.getRulesFromUI();
/*     */ 
/* 236 */         RuleUI.this.calcProgress.setString("Calculating...");
/*     */ 
/* 240 */         RuleUI.this.stop = false;
/* 241 */         RuleUI.this.calcThread = new Thread(RuleUI.this.calcRunnable);
/* 242 */         RuleUI.this.calcThread.start();
/*     */ 
/* 245 */         RuleUI.this.buttonCancel.setEnabled(true);
/* 246 */         RuleUI.this.buttonGo.setEnabled(false);
/*     */       }
/*     */     });
/* 251 */     this.buttonCancel.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/* 255 */         synchronized (RuleUI.this.lock)
/*     */         {
/* 258 */           RuleUI.this.stop = true;
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 264 */           RuleUI.this.calcThread.join();
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 268 */           ex.printStackTrace();
/*     */         }
/*     */ 
/* 272 */         RuleUI.this.calcProgress.setValue(0);
/* 273 */         RuleUI.this.calcProgress.setString("Cancelled");
/* 274 */         RuleUI.this.buttonCancel.setEnabled(false);
/* 275 */         RuleUI.this.buttonGo.setEnabled(true);
/*     */       }
/*     */     });
/* 287 */     this.buttonSave.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/*     */         try
/*     */         {
/* 294 */           JOptionPane.showMessageDialog(RuleUI.this.getFrame(), "<html>IF you have changed the settings since the last time you calculated the L-system,<br>the L-system you save will be the one last calculated--not the current data!</html>");
/*     */ 
/* 299 */           FileDialog fd = new FileDialog(RuleUI.this.getFrame(), "Save Current L-System Settings As", 1);
/*     */ 
/* 301 */           fd.setFile("Untitled.lss");
/* 302 */           fd.setVisible(true);
/*     */ 
/* 305 */           if (fd.getFile() == null) {
/* 306 */             return;
/*     */           }
/*     */ 
/* 309 */           File outputFile = new File(fd.getDirectory(), fd.getFile());
/* 310 */           FileOutputStream outputFileStream = new FileOutputStream(outputFile);
/* 311 */           GZIPOutputStream g = new GZIPOutputStream(new BufferedOutputStream(outputFileStream));
/*     */ 
/* 314 */           ObjectOutputStream out = new ObjectOutputStream(g);
/* 315 */           out.writeObject(RuleUI.this.ls.l);
/*     */ 
/* 321 */           out.flush();
/* 322 */           g.finish();
/* 323 */           g.flush();
/* 324 */           out.close();
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 328 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */     });
/* 334 */     this.buttonLoad.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/*     */         try
/*     */         {
/* 341 */           FileDialog fd = new FileDialog(RuleUI.this.getFrame(), "Open L-System Settings File (.lss)", 0);
/*     */ 
/* 343 */           fd.setFile("*.lss");
/* 344 */           fd.setVisible(true);
/*     */ 
/* 347 */           if (fd.getFile() == null) {
/* 348 */             return;
/*     */           }
/* 350 */           File inputFile = new File(fd.getDirectory(), fd.getFile());
/* 351 */           FileInputStream inputFileStream = new FileInputStream(inputFile);
/* 352 */           ObjectInputStream in = new ObjectInputStream(new GZIPInputStream(new BufferedInputStream(inputFileStream)));
/*     */ 
/* 354 */           RuleUI.this.ls.l = ((LSystemData)in.readObject());
/* 355 */           in.close();
/*     */ 
/* 360 */           RuleUI.this.seedField.setText(RuleUI.this.ls.l.seed);
/*     */ 
/* 362 */           RuleUI.this.stepField.setText(String.valueOf(RuleUI.this.ls.l.expansions));
/*     */ 
/* 364 */           RuleUI.this.dui.distField.setText(String.valueOf(RuleUI.this.ls.l.segsize));
/*     */ 
/* 366 */           RuleUI.this.dui.angleField.setText(String.valueOf(RuleUI.this.ls.l.angle * 180.0D / 3.141592653589793D));
/*     */ 
/* 375 */           for (int t = 0; t < RuleUI.this.ruleTable.getRowCount(); t++)
/*     */           {
/* 377 */             RuleUI.this.ruleTable.setValueAt(null, t, 0);
/* 378 */             RuleUI.this.ruleTable.setValueAt(null, t, 1);
/*     */           }
/*     */ 
/* 382 */           for (int t = 0; t < RuleUI.this.ls.l.rules.size(); t++)
/*     */           {
/* 384 */             RuleUI.this.ruleTable.setValueAt(String.valueOf((char)((Rule)RuleUI.this.ls.l.rules.get(t)).pattern), t, 0);
/* 385 */             RuleUI.this.ruleTable.setValueAt(LSystemData.fromVector(((Rule)RuleUI.this.ls.l.rules.get(t)).replace), t, 1);
/*     */           }
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 390 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */     });
/* 396 */     this.buttonHelp.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/* 402 */         JFrame help = new JFrame();
/* 403 */         help.getContentPane().setLayout(new BorderLayout());
/* 404 */         help.getContentPane().add(RuleUI.this.helpPanel, "Center");
/* 405 */         help.setSize(400, 300);
/* 406 */         help.setVisible(true);
/*     */       }
/*     */     });
/* 413 */     setLayout(new BorderLayout());
/*     */ 
/* 440 */     JPanel top = new JPanel();
/* 441 */     JPanel mid = new JPanel();
/* 442 */     top.setLayout(new BorderLayout());
/* 443 */     mid.setLayout(new BorderLayout());
/*     */ 
/* 449 */     LabelledList list = new LabelledList()
/*     */     {
/*     */       private static final long serialVersionUID = -2153709747861890863L;
/* 452 */       Insets insets = new Insets(5, 5, 5, 5);
/*     */ 
/*     */       public Insets getInsets() {
/* 455 */         return this.insets;
/*     */       }
/*     */     };
/* 459 */     this.seedField.setText(this.ls.l.seed);
/*     */ 
/* 461 */     list.addLabelled("Seed", this.seedField);
/* 462 */     list.addLabelled("Expansions", this.stepField);
/*     */ 
/* 465 */     top.add(list, "North");
/*     */ 
/* 468 */     Box b = new Box(0)
/*     */     {
/*     */       private static final long serialVersionUID = -869949739122977643L;
/* 471 */       Insets insets = new Insets(5, 5, 5, 5);
/*     */ 
/*     */       public Insets getInsets() {
/* 474 */         return this.insets;
/*     */       }
/*     */     };
/* 478 */     b.add(this.buttonGo);
/* 479 */     b.add(this.buttonCancel);
/* 480 */     this.buttonCancel.setEnabled(false);
/* 481 */     b.add(Box.createGlue());
/*     */ 
/* 483 */     top.add(b, "Center");
/*     */ 
/* 485 */     top.add(this.calcProgress, "South");
/* 486 */     this.calcProgress.setStringPainted(true);
/* 487 */     this.calcProgress.setString("Press Calculate");
/*     */ 
/* 489 */     b = new Box(0)
/*     */     {
/*     */       private static final long serialVersionUID = -2124038237393174259L;
/* 492 */       Insets insets = new Insets(5, 5, 5, 5);
/*     */ 
/*     */       public Insets getInsets() {
/* 495 */         return this.insets;
/*     */       }
/*     */     };
/* 499 */     b.add(this.buttonSave);
/* 500 */     b.add(this.buttonLoad);
/* 501 */     b.add(this.buttonHelp);
/* 502 */     b.add(Box.createGlue());
/*     */ 
/* 504 */     mid.add(top, "North");
/* 505 */     mid.add(b, "Center");
/* 506 */     add(mid, "North");
/*     */ 
/* 529 */     this.ruleTable.setModel(new DefaultTableModel(20, 2)
/*     */     {
/*     */       private static final long serialVersionUID = -6638838065039609876L;
/*     */ 
/*     */       public String getColumnName(int i)
/*     */       {
/* 520 */         if (i == 0)
/* 521 */           return "Symbol";
/* 522 */         if (i == 1) {
/* 523 */           return "Replacement";
/*     */         }
/* 525 */         return "Error.";
/*     */       }
/*     */     });
/* 533 */     this.seedField.setText("F");
/*     */ 
/* 535 */     this.ruleTable.setValueAt("F", 0, 0);
/* 536 */     this.ruleTable.setValueAt("F[+F]F[-F]F", 0, 1);
/*     */ 
/* 538 */     add(this.scrollPane, "Center");
/*     */ 
/* 543 */     LabelledList list2 = new LabelledList();
/*     */ 
/* 545 */     this.helpPanel.setLayout(new BorderLayout());
/*     */ 
/* 547 */     list2.addLabelled("Symbols", new JLabel(""));
/* 548 */     list2.addLabelled("Uppercase (A-Z)", new JLabel("Draw forward Distance units"));
/* 549 */     list2.addLabelled("Lowercase (a-z)", new JLabel("Move forward Distance units (no draw)"));
/* 550 */     list2.addLabelled("-", new JLabel("Turn right by angle degrees"));
/* 551 */     list2.addLabelled("+", new JLabel("Turn left by angle degrees"));
/* 552 */     list2.addLabelled("[", new JLabel("Push position, angle"));
/* 553 */     list2.addLabelled("]", new JLabel("Pop position, angle"));
/* 554 */     list2.addLabelled("", new JLabel(""));
/* 555 */     list2.addLabelled("Save: ", new JLabel("Saves the rules, seed, draw settings, and "));
/* 556 */     list2.addLabelled("", new JLabel("calculated expansions of the "));
/* 557 */     list2.addLabelled("", new JLabel("Last calculated L-system."));
/* 558 */     list2.addLabelled("Load: ", new JLabel("Loads saved L-system settings."));
/*     */ 
/* 560 */     this.helpPanel.add(list2, "Center");
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.lsystem.RuleUI
 * JD-Core Version:    0.6.2
 */