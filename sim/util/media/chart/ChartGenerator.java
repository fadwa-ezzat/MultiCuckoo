/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FileDialog;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSplitPane;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.Scrollable;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import org.jfree.chart.ChartPanel;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.event.TitleChangeEvent;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.title.LegendTitle;
/*     */ import org.jfree.chart.title.TextTitle;
/*     */ import org.jfree.data.general.Dataset;
/*     */ import org.jfree.data.general.DatasetChangeEvent;
/*     */ import org.jfree.data.general.SeriesChangeEvent;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import sim.display.SimApplet;
/*     */ import sim.util.gui.DisclosurePanel;
/*     */ import sim.util.gui.LabelledList;
/*     */ import sim.util.gui.MovieMaker;
/*     */ import sim.util.gui.NumberTextField;
/*     */ import sim.util.gui.PropertyField;
/*     */ import sim.util.gui.Utilities;
/*     */ import sim.util.media.PDFEncoder;
/*     */ 
/*     */ public abstract class ChartGenerator extends JPanel
/*     */ {
/*  78 */   protected Box globalAttributes = Box.createVerticalBox();
/*     */ 
/*  80 */   protected Box seriesAttributes = Box.createVerticalBox();
/*     */   protected JFreeChart chart;
/*     */   protected ScrollableChartPanel chartPanel;
/*  87 */   private JScrollPane chartHolder = new JScrollPane();
/*     */   JFrame frame;
/*     */   PropertyField titleField;
/*     */   NumberTextField scaleField;
/*     */   NumberTextField proportionField;
/*     */   JCheckBox fixBox;
/* 100 */   JButton movieButton = new JButton("Create Movie");
/*     */   BufferedImage buffer;
/* 129 */   MovieMaker movieMaker = null;
/*     */   static final long INITIAL_KEY = -1L;
/*     */   public static final long FORCE_KEY = -2L;
/* 133 */   long oldKey = -1L;
/*     */ 
/* 360 */   protected String invalidChartTitle = null;
/* 361 */   protected String validChartTitle = "";
/*     */ 
/* 642 */   public double DEFAULT_CHART_HEIGHT = 480.0D;
/* 643 */   public double DEFAULT_CHART_PROPORTION = 1.5D;
/*     */ 
/* 645 */   double scale = 1.0D;
/* 646 */   double proportion = 1.5D;
/*     */ 
/* 747 */   Thread timer = null;
/*     */ 
/* 780 */   static int DEFAULT_UNIT_FRACTION = 20;
/* 781 */   static int DEFAULT_BLOCK_FRACTION = 2;
/*     */ 
/*     */   public void setChartPanel(ScrollableChartPanel chartPanel)
/*     */   {
/*  73 */     this.chartHolder.getViewport().setView(chartPanel);
/*  74 */     this.chartPanel = chartPanel;
/*     */   }
/*     */ 
/*     */   public JFrame getFrame()
/*     */   {
/*  91 */     return this.frame;
/*     */   }
/*     */ 
/*     */   public abstract Dataset getSeriesDataset();
/*     */ 
/*     */   public abstract void setSeriesDataset(Dataset paramDataset);
/*     */ 
/*     */   protected void update()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected abstract void buildChart();
/*     */ 
/*     */   BufferedImage getBufferedImage()
/*     */   {
/* 115 */     if ((this.buffer == null) || (this.buffer.getWidth(null) != this.chartPanel.getWidth()) || (this.buffer.getHeight(null) != this.chartPanel.getHeight()))
/*     */     {
/* 117 */       this.buffer = getGraphicsConfiguration().createCompatibleImage(this.chartPanel.getWidth(), this.chartPanel.getHeight());
/*     */     }
/*     */ 
/* 121 */     Graphics2D g = (Graphics2D)this.buffer.getGraphics();
/* 122 */     g.setColor(this.chartPanel.getBackground());
/* 123 */     g.fillRect(0, 0, this.buffer.getWidth(null), this.buffer.getHeight(null));
/* 124 */     this.chartPanel.paintComponent(g);
/* 125 */     g.dispose();
/* 126 */     return this.buffer;
/*     */   }
/*     */ 
/*     */   public void update(long key, boolean newData)
/*     */   {
/* 140 */     if ((key == this.oldKey) && (key != -2L)) {
/* 141 */       return;
/*     */     }
/*     */ 
/* 144 */     this.oldKey = key;
/* 145 */     update();
/*     */ 
/* 147 */     if (newData) {
/* 148 */       this.chart.getPlot().datasetChanged(new DatasetChangeEvent(this.chart.getPlot(), null));
/*     */     }
/*     */ 
/* 151 */     if ((newData) && (this.movieMaker != null))
/*     */     {
/* 154 */       this.movieMaker.add(getBufferedImage());
/*     */     }
/*     */   }
/*     */ 
/*     */   void rebuildAttributeIndices()
/*     */   {
/* 161 */     SeriesAttributes[] c = getSeriesAttributes();
/* 162 */     for (int i = 0; i < c.length; i++)
/*     */     {
/* 164 */       SeriesAttributes csa = c[i];
/* 165 */       csa.setSeriesIndex(i);
/* 166 */       csa.rebuildGraphicsDefinitions();
/*     */     }
/* 168 */     revalidate();
/*     */   }
/*     */ 
/*     */   protected SeriesAttributes getSeriesAttribute(int i)
/*     */   {
/* 173 */     return (SeriesAttributes)this.seriesAttributes.getComponent(i);
/*     */   }
/*     */   public int getNumSeriesAttributes() {
/* 176 */     return this.seriesAttributes.getComponents().length;
/*     */   }
/*     */ 
/*     */   protected SeriesAttributes[] getSeriesAttributes() {
/* 180 */     Component[] c = this.seriesAttributes.getComponents();
/* 181 */     SeriesAttributes[] sa = new SeriesAttributes[c.length];
/* 182 */     System.arraycopy(c, 0, sa, 0, c.length);
/* 183 */     return sa;
/*     */   }
/*     */ 
/*     */   protected void setSeriesAttributes(SeriesAttributes[] c)
/*     */   {
/* 188 */     this.seriesAttributes.removeAll();
/* 189 */     for (int i = 0; i < c.length; i++)
/*     */     {
/* 191 */       this.seriesAttributes.add(c[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeSeries(int index)
/*     */   {
/* 199 */     SeriesAttributes[] c = getSeriesAttributes();
/* 200 */     SeriesChangeListener tmpObj = c[index].getStoppable();
/* 201 */     if (tmpObj != null)
/*     */     {
/* 203 */       tmpObj.seriesChanged(new SeriesChangeEvent(this));
/*     */     }
/*     */ 
/* 207 */     Component comp = this.seriesAttributes.getComponent(index);
/* 208 */     ((SeriesAttributes)comp).setSeriesIndex(-1);
/*     */ 
/* 211 */     this.seriesAttributes.remove(index);
/* 212 */     rebuildAttributeIndices();
/* 213 */     revalidate();
/*     */   }
/*     */ 
/*     */   public void moveSeries(int index, boolean up)
/*     */   {
/* 220 */     if (((index > 0) && (up)) || ((index < getSeriesCount() - 1) && (!up)))
/*     */     {
/* 222 */       SeriesAttributes[] c = getSeriesAttributes();
/*     */ 
/* 224 */       if (up)
/*     */       {
/* 226 */         SeriesAttributes s1 = c[index];
/* 227 */         SeriesAttributes s2 = c[(index - 1)];
/* 228 */         c[index] = s2;
/* 229 */         c[(index - 1)] = s1;
/*     */       }
/*     */       else
/*     */       {
/* 233 */         SeriesAttributes s1 = c[index];
/* 234 */         SeriesAttributes s2 = c[(index + 1)];
/* 235 */         c[index] = s2;
/* 236 */         c[(index + 1)] = s1;
/*     */       }
/* 238 */       setSeriesAttributes(c);
/* 239 */       rebuildAttributeIndices();
/* 240 */       revalidate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void startMovie()
/*     */   {
/* 255 */     if (SimApplet.isApplet())
/*     */     {
/* 257 */       Object[] options = { "Oops" };
/* 258 */       JOptionPane.showOptionDialog(this, "You cannot create movies from an applet.", "MASON Applet Restriction", 0, 0, null, options, options[0]);
/*     */ 
/* 263 */       return;
/*     */     }
/*     */ 
/* 266 */     if (this.movieMaker != null) return;
/* 267 */     this.movieMaker = new MovieMaker(getFrame());
/*     */ 
/* 269 */     if (!this.movieMaker.start(getBufferedImage())) {
/* 270 */       this.movieMaker = null;
/*     */     }
/*     */     else {
/* 273 */       this.movieButton.setText("Stop Movie");
/*     */ 
/* 276 */       update(-2L, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void stopMovie()
/*     */   {
/* 286 */     if (this.movieMaker == null) return;
/* 287 */     if (!this.movieMaker.stop())
/*     */     {
/* 289 */       Object[] options = { "Drat" };
/* 290 */       JOptionPane.showOptionDialog(this, "Your movie did not write to disk\ndue to a spurious JMF movie generation bug.", "JMF Movie Generation Bug", 0, 2, null, options, options[0]);
/*     */     }
/*     */ 
/* 296 */     this.movieMaker = null;
/* 297 */     if (this.movieButton != null)
/*     */     {
/* 299 */       this.movieButton.setText("Create Movie");
/*     */     }
/*     */   }
/*     */ 
/*     */   public abstract int getSeriesCount();
/*     */ 
/*     */   public void removeAllSeries()
/*     */   {
/* 311 */     for (int x = getSeriesCount() - 1; x >= 0; x--)
/* 312 */       removeSeries(x);
/*     */   }
/*     */ 
/*     */   public void quit()
/*     */   {
/* 318 */     if (this.movieMaker != null) this.movieMaker.stop();
/* 319 */     removeAllSeries();
/*     */   }
/*     */ 
/*     */   public ChartPanel getChartPanel() {
/* 323 */     return this.chartPanel;
/*     */   }
/*     */ 
/*     */   public void addGlobalAttribute(Component component)
/*     */   {
/* 328 */     this.globalAttributes.add(component);
/*     */   }
/*     */ 
/*     */   public Component getGlobalAttribute(int index)
/*     */   {
/* 336 */     return this.globalAttributes.getComponent(index + 2);
/*     */   }
/*     */   /** @deprecated */
/*     */   public int getGlobalAttributeCount() {
/* 340 */     return getNumGlobalAttributes();
/*     */   }
/*     */ 
/*     */   public int getNumGlobalAttributes()
/*     */   {
/* 347 */     return this.globalAttributes.getComponentCount() - 2;
/*     */   }
/*     */ 
/*     */   public Component removeGlobalAttribute(int index)
/*     */   {
/* 353 */     Component component = getGlobalAttribute(index);
/* 354 */     this.globalAttributes.remove(index);
/* 355 */     return component;
/*     */   }
/*     */ 
/*     */   public void setInvalidChartTitle(String title)
/*     */   {
/* 368 */     this.invalidChartTitle = title;
/* 369 */     setTitle(this.validChartTitle);
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 378 */     this.validChartTitle = title;
/*     */ 
/* 380 */     if (this.invalidChartTitle != null) {
/* 381 */       title = this.invalidChartTitle;
/*     */     }
/* 383 */     this.chart.setTitle(title);
/* 384 */     this.chart.titleChanged(new TitleChangeEvent(new TextTitle(title)));
/* 385 */     if (this.frame != null) this.frame.setTitle(title);
/* 386 */     this.titleField.setValue(title);
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 392 */     return this.validChartTitle;
/*     */   }
/*     */ 
/*     */   public JFreeChart getChart()
/*     */   {
/* 401 */     return this.chart;
/*     */   }
/*     */ 
/*     */   protected void buildGlobalAttributes(LabelledList list)
/*     */   {
/*     */   }
/*     */ 
/*     */   public ChartGenerator()
/*     */   {
/* 410 */     buildChart();
/* 411 */     this.chart.getPlot().setBackgroundPaint(Color.WHITE);
/* 412 */     this.chart.setAntiAlias(true);
/*     */ 
/* 414 */     JSplitPane split = new JSplitPane(1, true);
/* 415 */     split.setBorder(new EmptyBorder(0, 0, 0, 0));
/* 416 */     JScrollPane scroll = new JScrollPane();
/* 417 */     JPanel b = new JPanel();
/* 418 */     b.setLayout(new BorderLayout());
/* 419 */     b.add(this.seriesAttributes, "North");
/* 420 */     b.add(new JPanel(), "Center");
/* 421 */     scroll.getViewport().setView(b);
/* 422 */     scroll.setBackground(getBackground());
/* 423 */     scroll.getViewport().setBackground(getBackground());
/* 424 */     JPanel p = new JPanel();
/* 425 */     p.setLayout(new BorderLayout());
/*     */ 
/* 427 */     LabelledList list = new LabelledList("Chart Properties");
/* 428 */     DisclosurePanel pan1 = new DisclosurePanel("Chart Properties", list);
/* 429 */     this.globalAttributes.add(pan1);
/*     */ 
/* 431 */     JLabel j = new JLabel("Right-Click or Control-Click");
/* 432 */     j.setFont(j.getFont().deriveFont(10.0F).deriveFont(2));
/* 433 */     list.add(j);
/* 434 */     j = new JLabel("on Chart for More Options");
/* 435 */     j.setFont(j.getFont().deriveFont(10.0F).deriveFont(2));
/* 436 */     list.add(j);
/*     */ 
/* 438 */     this.titleField = new PropertyField()
/*     */     {
/*     */       public String newValue(String newValue)
/*     */       {
/* 442 */         ChartGenerator.this.setTitle(newValue);
/* 443 */         ChartGenerator.this.getChartPanel().repaint();
/* 444 */         return newValue;
/*     */       }
/*     */     };
/* 447 */     this.titleField.setValue(this.chart.getTitle().getText());
/*     */ 
/* 449 */     list.add(new JLabel("Title"), this.titleField);
/*     */ 
/* 452 */     buildGlobalAttributes(list);
/*     */ 
/* 456 */     JCheckBox legendCheck = new JCheckBox();
/* 457 */     ItemListener il = new ItemListener()
/*     */     {
/*     */       public void itemStateChanged(ItemEvent e)
/*     */       {
/* 461 */         if (e.getStateChange() == 1)
/*     */         {
/* 463 */           LegendTitle title = new LegendTitle(ChartGenerator.this.chart.getPlot());
/* 464 */           title.setLegendItemGraphicPadding(new RectangleInsets(0.0D, 8.0D, 0.0D, 4.0D));
/* 465 */           ChartGenerator.this.chart.addLegend(title);
/*     */         }
/*     */         else
/*     */         {
/* 469 */           ChartGenerator.this.chart.removeLegend();
/*     */         }
/*     */       }
/*     */     };
/* 473 */     legendCheck.addItemListener(il);
/* 474 */     list.add(new JLabel("Legend"), legendCheck);
/* 475 */     legendCheck.setSelected(true);
/*     */ 
/* 491 */     JPanel pdfButtonPanel = new JPanel();
/* 492 */     pdfButtonPanel.setBorder(new TitledBorder("Chart Output"));
/* 493 */     DisclosurePanel pan2 = new DisclosurePanel("Chart Output", pdfButtonPanel);
/*     */ 
/* 495 */     pdfButtonPanel.setLayout(new BorderLayout());
/* 496 */     Box pdfbox = new Box(1);
/* 497 */     pdfButtonPanel.add(pdfbox, "West");
/*     */ 
/* 499 */     JButton pdfButton = new JButton("Save as PDF");
/* 500 */     pdfbox.add(pdfButton);
/* 501 */     pdfButton.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/* 505 */         FileDialog fd = new FileDialog(ChartGenerator.this.frame, "Choose PDF file...", 1);
/* 506 */         fd.setFile(ChartGenerator.this.chart.getTitle().getText() + ".pdf");
/* 507 */         fd.setVisible(true);
/* 508 */         String fileName = fd.getFile();
/* 509 */         if (fileName != null)
/*     */         {
/* 511 */           Dimension dim = ChartGenerator.this.chartPanel.getPreferredSize();
/* 512 */           PDFEncoder.generatePDF(ChartGenerator.this.chart, dim.width, dim.height, new File(fd.getDirectory(), Utilities.ensureFileEndsWith(fd.getFile(), ".pdf")));
/*     */         }
/*     */       }
/*     */     });
/* 517 */     this.movieButton = new JButton("Create a Movie");
/* 518 */     pdfbox.add(this.movieButton);
/* 519 */     pdfbox.add(Box.createGlue());
/* 520 */     this.movieButton.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/* 524 */         if (ChartGenerator.this.movieMaker == null) ChartGenerator.this.startMovie(); else
/* 525 */           ChartGenerator.this.stopMovie();
/*     */       }
/*     */     });
/* 529 */     this.globalAttributes.add(pan2);
/*     */ 
/* 534 */     Box outerAttributes = Box.createVerticalBox();
/* 535 */     outerAttributes.add(this.globalAttributes);
/* 536 */     outerAttributes.add(Box.createGlue());
/*     */ 
/* 538 */     p.add(outerAttributes, "North");
/* 539 */     p.add(scroll, "Center");
/* 540 */     p.setMinimumSize(new Dimension(0, 0));
/* 541 */     p.setPreferredSize(new Dimension(200, 0));
/* 542 */     split.setLeftComponent(p);
/*     */ 
/* 545 */     Box header = Box.createHorizontalBox();
/*     */ 
/* 547 */     double MAXIMUM_SCALE = 8.0D;
/*     */ 
/* 549 */     this.fixBox = new JCheckBox("Fill");
/* 550 */     this.fixBox.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/* 554 */         ChartGenerator.this.setFixed(ChartGenerator.this.fixBox.isSelected());
/*     */       }
/*     */     });
/* 557 */     header.add(this.fixBox);
/* 558 */     this.fixBox.setSelected(true);
/*     */ 
/* 561 */     this.scaleField = new NumberTextField("  Scale: ", 1.0D, true)
/*     */     {
/*     */       public double newValue(double newValue)
/*     */       {
/* 565 */         if (newValue <= 0.0D) newValue = this.currentValue;
/* 566 */         if (newValue > 8.0D) newValue = this.currentValue;
/* 567 */         ChartGenerator.this.scale = newValue;
/* 568 */         ChartGenerator.this.resizeChart();
/* 569 */         return newValue;
/*     */       }
/*     */     };
/* 572 */     this.scaleField.setToolTipText("Zoom in and out");
/* 573 */     this.scaleField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
/* 574 */     this.scaleField.setEnabled(false);
/* 575 */     this.scaleField.setText("");
/* 576 */     header.add(this.scaleField);
/*     */ 
/* 579 */     this.proportionField = new NumberTextField("  Proportion: ", 1.5D, true)
/*     */     {
/*     */       public double newValue(double newValue)
/*     */       {
/* 583 */         if (newValue <= 0.0D) newValue = this.currentValue;
/* 584 */         ChartGenerator.this.proportion = newValue;
/* 585 */         ChartGenerator.this.resizeChart();
/* 586 */         return newValue;
/*     */       }
/*     */     };
/* 589 */     this.proportionField.setToolTipText("Change the chart proportions (ratio of width to height)");
/* 590 */     this.proportionField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
/* 591 */     header.add(this.proportionField);
/*     */ 
/* 594 */     this.chartHolder.setMinimumSize(new Dimension(0, 0));
/* 595 */     this.chartHolder.setHorizontalScrollBarPolicy(31);
/* 596 */     this.chartHolder.setVerticalScrollBarPolicy(22);
/* 597 */     this.chartHolder.getViewport().setBackground(Color.gray);
/* 598 */     JPanel p2 = new JPanel();
/* 599 */     p2.setLayout(new BorderLayout());
/* 600 */     p2.add(this.chartHolder, "Center");
/* 601 */     p2.add(header, "North");
/* 602 */     split.setRightComponent(p2);
/* 603 */     setLayout(new BorderLayout());
/* 604 */     add(split, "Center");
/*     */ 
/* 607 */     this.chart.setBackgroundPaint(Color.WHITE);
/*     */ 
/* 611 */     this.chartPanel.setMinimumDrawHeight((int)this.DEFAULT_CHART_HEIGHT);
/* 612 */     this.chartPanel.setMaximumDrawHeight((int)this.DEFAULT_CHART_HEIGHT);
/* 613 */     this.chartPanel.setMinimumDrawWidth((int)(this.DEFAULT_CHART_HEIGHT * this.proportion));
/* 614 */     this.chartPanel.setMaximumDrawWidth((int)(this.DEFAULT_CHART_HEIGHT * this.proportion));
/* 615 */     this.chartPanel.setPreferredSize(new Dimension((int)(this.DEFAULT_CHART_HEIGHT * this.DEFAULT_CHART_PROPORTION), (int)this.DEFAULT_CHART_HEIGHT));
/*     */   }
/*     */ 
/*     */   public boolean isFixed()
/*     */   {
/* 620 */     return this.fixBox.isSelected();
/*     */   }
/*     */ 
/*     */   public void setFixed(boolean value)
/*     */   {
/* 625 */     this.fixBox.setSelected(value);
/* 626 */     this.chartHolder.setHorizontalScrollBarPolicy(value ? 31 : 32);
/*     */ 
/* 628 */     this.scaleField.setEnabled(!value);
/* 629 */     if (value) { this.scaleField.setText("");
/*     */     } else
/*     */     {
/* 632 */       double val = this.scaleField.getValue();
/* 633 */       if (val == (int)val)
/* 634 */         this.scaleField.setText("" + (int)val);
/* 635 */       else this.scaleField.setText("" + val);
/*     */     }
/* 637 */     resizeChart();
/*     */   }
/*     */ 
/*     */   public double getScale()
/*     */   {
/* 648 */     return this.scale; } 
/* 649 */   public double getProportion() { return this.proportion; } 
/* 650 */   public void setScale(double val) { this.scale = val; this.scaleField.setValue(val); resizeChart(); } 
/* 651 */   public void setProportion(double val) { this.proportion = val; this.proportionField.setValue(val); resizeChart();
/*     */   }
/*     */ 
/*     */   void resizeChart()
/*     */   {
/* 656 */     double w = this.DEFAULT_CHART_HEIGHT * this.scale * this.proportion;
/* 657 */     double h = this.DEFAULT_CHART_HEIGHT * this.scale;
/* 658 */     Dimension d = new Dimension((int)w, (int)h);
/*     */ 
/* 660 */     this.chartPanel.setSize(new Dimension(d));
/* 661 */     this.chartPanel.setPreferredSize(this.chartPanel.getSize());
/*     */ 
/* 666 */     this.chartPanel.setMinimumDrawHeight((int)this.DEFAULT_CHART_HEIGHT);
/* 667 */     this.chartPanel.setMaximumDrawHeight((int)this.DEFAULT_CHART_HEIGHT);
/* 668 */     this.chartPanel.setMinimumDrawWidth((int)(this.DEFAULT_CHART_HEIGHT * this.proportion));
/* 669 */     this.chartPanel.setMaximumDrawWidth((int)(this.DEFAULT_CHART_HEIGHT * this.proportion));
/*     */ 
/* 671 */     this.chartPanel.repaint();
/*     */   }
/*     */ 
/*     */   public JFrame createFrame()
/*     */   {
/* 678 */     return createFrame(false);
/*     */   }
/*     */ 
/*     */   public JFrame createFrame(boolean inspector)
/*     */   {
/* 686 */     this.frame = new JFrame()
/*     */     {
/*     */       public void dispose()
/*     */       {
/* 690 */         ChartGenerator.this.quit();
/* 691 */         super.dispose();
/*     */       }
/*     */     };
/* 694 */     if (inspector) {
/* 695 */       this.frame.getRootPane().putClientProperty("Window.style", "small");
/*     */     }
/* 697 */     this.frame.setDefaultCloseOperation(1);
/* 698 */     this.frame.getContentPane().setLayout(new BorderLayout());
/* 699 */     this.frame.getContentPane().add(this, "Center");
/* 700 */     this.frame.setResizable(true);
/* 701 */     this.frame.pack();
/* 702 */     this.frame.setTitle(this.chart.getTitle().getText());
/* 703 */     return this.frame;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public JFrame createFrame(Object simulation)
/*     */   {
/* 711 */     return createFrame();
/*     */   }
/*     */ 
/*     */   public void addLegend()
/*     */   {
/* 738 */     if (this.chart.getLegend() != null) {
/* 739 */       return;
/*     */     }
/* 741 */     LegendTitle title = new LegendTitle(this.chart.getPlot());
/* 742 */     title.setLegendItemGraphicPadding(new RectangleInsets(0.0D, 8.0D, 0.0D, 4.0D));
/* 743 */     this.chart.addLegend(title);
/*     */   }
/*     */ 
/*     */   public void updateChartWithin(final long key, long milliseconds)
/*     */   {
/* 752 */     if (this.timer == null)
/*     */     {
/* 754 */       this.timer = Utilities.doLater(milliseconds, new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 758 */           ChartGenerator.this.update(key, true);
/*     */ 
/* 760 */           ChartGenerator.this.timer = null;
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateChartLater(final long key)
/*     */   {
/* 769 */     repaint();
/*     */ 
/* 771 */     SwingUtilities.invokeLater(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 775 */         ChartGenerator.this.update(key, true);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public ScrollableChartPanel buildChartPanel(JFreeChart chart)
/*     */   {
/* 837 */     return new ScrollableChartPanel(chart, true);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/* 719 */       String version = System.getProperty("java.version");
/*     */ 
/* 722 */       if (!version.startsWith("1.3"))
/*     */       {
/* 729 */         UIManager.put("ColorChooserUI", Class.forName("ch.randelshofer.quaqua.Quaqua14ColorChooserUI", true, Thread.currentThread().getContextClassLoader()).getName());
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class UniqueString
/*     */     implements Comparable
/*     */   {
/*     */     String string;
/*     */ 
/*     */     public UniqueString(Object obj)
/*     */     {
/* 850 */       this.string = ("" + obj);
/*     */     }
/*     */ 
/*     */     public boolean equals(Object obj)
/*     */     {
/* 855 */       return obj == this;
/*     */     }
/*     */ 
/*     */     public int compareTo(Object obj)
/*     */     {
/* 860 */       if (obj == this) return 0;
/* 861 */       if (obj == null) throw new NullPointerException();
/* 862 */       if (!(obj instanceof UniqueString)) return -1;
/* 863 */       UniqueString us = (UniqueString)obj;
/* 864 */       if (us.string.equals(this.string))
/*     */       {
/* 866 */         if (System.identityHashCode(this) > System.identityHashCode(us))
/* 867 */           return 1;
/* 868 */         return -1;
/*     */       }
/* 870 */       return us.string.compareTo(this.string);
/*     */     }
/*     */     public String toString() {
/* 873 */       return this.string;
/*     */     }
/*     */   }
/*     */ 
/*     */   class ScrollableChartPanel extends ChartPanel
/*     */     implements Scrollable
/*     */   {
/*     */     public ScrollableChartPanel(JFreeChart chart, boolean useBuffer)
/*     */     {
/* 787 */       super(useBuffer);
/*     */     }
/*     */ 
/*     */     public Dimension getPreferredSize()
/*     */     {
/* 792 */       Dimension size = super.getPreferredSize();
/* 793 */       int viewportWidth = ChartGenerator.this.chartHolder.getViewport().getWidth();
/* 794 */       if (viewportWidth == 0) {
/* 795 */         return size;
/*     */       }
/*     */ 
/* 798 */       if (ChartGenerator.this.isFixed())
/* 799 */         size.height = ((int)(size.height / size.width * viewportWidth));
/* 800 */       return size;
/*     */     }
/*     */ 
/*     */     public Dimension getPreferredScrollableViewportSize()
/*     */     {
/* 805 */       return getPreferredSize();
/*     */     }
/*     */ 
/*     */     public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
/*     */     {
/* 810 */       return (int)(orientation == 0 ? visibleRect.getWidth() / ChartGenerator.DEFAULT_UNIT_FRACTION : visibleRect.getHeight() / ChartGenerator.DEFAULT_UNIT_FRACTION);
/*     */     }
/*     */ 
/*     */     public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
/*     */     {
/* 817 */       return (int)(orientation == 0 ? visibleRect.getWidth() / ChartGenerator.DEFAULT_BLOCK_FRACTION : visibleRect.getHeight() / ChartGenerator.DEFAULT_BLOCK_FRACTION);
/*     */     }
/*     */ 
/*     */     public boolean getScrollableTracksViewportHeight()
/*     */     {
/* 822 */       return false; } 
/* 823 */     public boolean getScrollableTracksViewportWidth() { return ChartGenerator.this.isFixed(); } 
/* 824 */     public Dimension getMaximumSize() { return getPreferredSize(); } 
/* 825 */     public Dimension getMinimumSize() { return getPreferredSize(); }
/*     */ 
/*     */     public void setSize(Dimension d)
/*     */     {
/* 829 */       super.setSize(d);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.ChartGenerator
 * JD-Core Version:    0.6.2
 */