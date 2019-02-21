/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.ChartPanel;
/*     */ import org.jfree.chart.ChartTheme;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.LogarithmicAxis;
/*     */ import org.jfree.chart.axis.NumberAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.event.AxisChangeEvent;
/*     */ import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
/*     */ import org.jfree.chart.renderer.category.CategoryItemRenderer;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.general.Dataset;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
/*     */ import sim.util.gui.LabelledList;
/*     */ import sim.util.gui.NumberTextField;
/*     */ import sim.util.gui.PropertyField;
/*     */ 
/*     */ public class BoxPlotGenerator extends ChartGenerator
/*     */ {
/*     */   PropertyField yLabel;
/*     */   PropertyField xLabel;
/*     */   JCheckBox yLog;
/*     */   JCheckBox mean;
/*     */   JCheckBox median;
/*     */   NumberTextField maximumWidthField;
/*     */ 
/*     */   public void setMaximumWidth(double value)
/*     */   {
/*  67 */     this.maximumWidthField.setValue(this.maximumWidthField.newValue(value)); } 
/*  68 */   public double getMaximumWidth() { return this.maximumWidthField.getValue(); } 
/*     */   public void setYAxisLogScaled(boolean isLogScaled) {
/*  70 */     this.yLog.setSelected(isLogScaled); } 
/*  71 */   public boolean isYAxisLogScaled() { return this.yLog.isSelected(); } 
/*     */   public void setMeanShown(boolean val) {
/*  73 */     this.mean.setSelected(val); } 
/*  74 */   public boolean isMeanShown() { return this.mean.isSelected(); } 
/*  75 */   public void setMedianShown(boolean val) { this.median.setSelected(val); } 
/*  76 */   public boolean isMedianShown() { return this.median.isSelected(); }
/*     */ 
/*     */ 
/*     */   public String getYAxisLabel()
/*     */   {
/*  81 */     return ((CategoryPlot)this.chart.getPlot()).getRangeAxis().getLabel();
/*     */   }
/*     */ 
/*     */   public String getXAxisLabel()
/*     */   {
/*  87 */     return ((CategoryPlot)this.chart.getPlot()).getDomainAxis().getLabel();
/*     */   }
/*     */ 
/*     */   public Dataset getSeriesDataset() {
/*  91 */     return ((CategoryPlot)this.chart.getPlot()).getDataset();
/*     */   }
/*     */ 
/*     */   public void setSeriesDataset(Dataset obj) {
/*  95 */     ((CategoryPlot)this.chart.getPlot()).setDataset((DefaultBoxAndWhiskerCategoryDataset)obj);
/*  96 */     if (this.invalidChartTitle != null)
/*  97 */       setInvalidChartTitle(null);
/*     */   }
/*     */ 
/*     */   public int getSeriesCount()
/*     */   {
/* 102 */     DefaultBoxAndWhiskerCategoryDataset dataset = (DefaultBoxAndWhiskerCategoryDataset)getSeriesDataset();
/* 103 */     return dataset.getRowCount();
/*     */   }
/*     */ 
/*     */   public void removeSeries(int index)
/*     */   {
/* 108 */     super.removeSeries(index);
/* 109 */     update();
/*     */   }
/*     */ 
/*     */   public void moveSeries(int index, boolean up)
/*     */   {
/* 115 */     super.moveSeries(index, up);
/* 116 */     update();
/*     */   }
/*     */ 
/*     */   protected void buildChart()
/*     */   {
/* 122 */     DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
/*     */ 
/* 127 */     CategoryAxis categoryAxis = new CategoryAxis("");
/* 128 */     NumberAxis valueAxis = new NumberAxis("Untitled Y Axis");
/* 129 */     valueAxis.setAutoRangeIncludesZero(false);
/* 130 */     BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
/* 131 */     renderer.setBaseToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
/* 132 */     CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis, renderer)
/*     */     {
/*     */       public Range getDataRange(ValueAxis axis)
/*     */       {
/* 140 */         Range range = super.getDataRange(axis);
/* 141 */         if (range == null) return null;
/* 142 */         double EXTRA_PERCENTAGE = 0.02D;
/* 143 */         return Range.expand(range, 0.02D, 0.02D);
/*     */       }
/*     */     };
/* 147 */     this.chart = new JFreeChart("Untitled Chart", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
/* 148 */     ChartFactory.getChartTheme().apply(this.chart);
/*     */ 
/* 150 */     this.chart.setAntiAlias(true);
/* 151 */     this.chartPanel = buildChartPanel(this.chart);
/* 152 */     setChartPanel(this.chartPanel);
/*     */ 
/* 155 */     setSeriesDataset(dataset);
/*     */   }
/*     */ 
/*     */   ArrayList buildList(double[] vals)
/*     */   {
/* 160 */     ArrayList list = new ArrayList();
/* 161 */     for (int i = 0; i < vals.length; i++)
/* 162 */       list.add(new Double(vals[i]));
/* 163 */     return list;
/*     */   }
/*     */ 
/*     */   protected void update()
/*     */   {
/* 171 */     SeriesAttributes[] sa = getSeriesAttributes();
/* 172 */     DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
/*     */ 
/* 174 */     for (int i = 0; i < sa.length; i++)
/*     */     {
/* 176 */       BoxPlotSeriesAttributes attributes = (BoxPlotSeriesAttributes)sa[i];
/* 177 */       double[][] values = attributes.getValues();
/* 178 */       String[] labels = attributes.getLabels();
/*     */ 
/* 180 */       String series = attributes.getSeriesName();
/* 181 */       for (int j = 0; j < values.length; j++)
/*     */       {
/* 183 */         dataset.add(buildList(values[j]), series, labels[j]);
/*     */       }
/*     */     }
/*     */ 
/* 187 */     ((BoxAndWhiskerRenderer)((CategoryPlot)this.chart.getPlot()).getRenderer()).setMaximumBarWidth(getMaximumWidth());
/*     */ 
/* 189 */     setSeriesDataset(dataset);
/*     */   }
/*     */ 
/*     */   public SeriesAttributes addSeries(double[] vals, String name, SeriesChangeListener stopper)
/*     */   {
/* 196 */     double[][] vvals = new double[1][];
/* 197 */     vvals[0] = vals;
/* 198 */     return addSeries(vvals, name, stopper);
/*     */   }
/*     */ 
/*     */   SeriesAttributes addSeries(double[][] vals, String name, SeriesChangeListener stopper)
/*     */   {
/* 206 */     if ((vals == null) || (vals.length == 0)) vals = new double[0][0];
/* 207 */     int i = getSeriesCount();
/*     */ 
/* 210 */     BoxPlotSeriesAttributes csa = new BoxPlotSeriesAttributes(this, name, i, vals, stopper);
/* 211 */     this.seriesAttributes.add(csa);
/*     */ 
/* 213 */     revalidate();
/* 214 */     update();
/*     */ 
/* 217 */     SwingUtilities.invokeLater(new Runnable() { public void run() { BoxPlotGenerator.this.update(); }
/*     */ 
/*     */     });
/* 219 */     return csa;
/*     */   }
/*     */ 
/*     */   public SeriesAttributes addSeries(double[][] vals, String[] labels, String name, SeriesChangeListener stopper)
/*     */   {
/* 225 */     if ((vals == null) || (vals.length == 0)) vals = new double[0][0];
/* 226 */     int i = getSeriesCount();
/*     */ 
/* 229 */     BoxPlotSeriesAttributes csa = new BoxPlotSeriesAttributes(this, name, i, vals, labels, stopper);
/* 230 */     this.seriesAttributes.add(csa);
/*     */ 
/* 232 */     revalidate();
/* 233 */     update();
/*     */ 
/* 236 */     SwingUtilities.invokeLater(new Runnable() { public void run() { BoxPlotGenerator.this.update(); }
/*     */ 
/*     */     });
/* 238 */     return csa;
/*     */   }
/*     */ 
/*     */   public void setYAxisLabel(String val)
/*     */   {
/* 247 */     CategoryPlot xyplot = (CategoryPlot)this.chart.getPlot();
/* 248 */     xyplot.getRangeAxis().setLabel(val);
/* 249 */     xyplot.axisChanged(new AxisChangeEvent(xyplot.getRangeAxis()));
/* 250 */     this.yLabel.setValue(val);
/*     */   }
/*     */ 
/*     */   public void setXAxisLabel(String val)
/*     */   {
/* 256 */     CategoryPlot xyplot = (CategoryPlot)this.chart.getPlot();
/* 257 */     xyplot.getDomainAxis().setLabel(val);
/* 258 */     xyplot.axisChanged(new AxisChangeEvent(xyplot.getDomainAxis()));
/* 259 */     this.xLabel.setValue(val);
/*     */   }
/*     */ 
/*     */   public void updateSeries(int index, double[] vals)
/*     */   {
/* 264 */     double[][] vvals = new double[1][];
/* 265 */     vvals[0] = vals;
/* 266 */     updateSeries(index, vvals);
/*     */   }
/*     */ 
/*     */   public void updateSeries(int index, double[][] vals)
/*     */   {
/* 271 */     if (index < 0) {
/* 272 */       return;
/*     */     }
/* 274 */     if (index >= getNumSeriesAttributes()) {
/* 275 */       return;
/*     */     }
/* 277 */     if ((vals == null) || (vals.length == 0)) vals = new double[0][0];
/* 278 */     BoxPlotSeriesAttributes hsa = (BoxPlotSeriesAttributes)getSeriesAttribute(index);
/* 279 */     hsa.setValues(vals);
/* 280 */     hsa.setLabels(null);
/*     */   }
/*     */ 
/*     */   public void updateSeries(int index, double[][] vals, String[] labels)
/*     */   {
/* 285 */     if (index < 0) {
/* 286 */       return;
/*     */     }
/* 288 */     if (index >= getNumSeriesAttributes()) {
/* 289 */       return;
/*     */     }
/* 291 */     if ((vals == null) || (vals.length == 0)) vals = new double[0][0];
/* 292 */     if ((labels == null) || (labels.length == 0)) labels = new String[0];
/* 293 */     if (vals.length != labels.length) {
/* 294 */       return;
/*     */     }
/* 296 */     BoxPlotSeriesAttributes hsa = (BoxPlotSeriesAttributes)getSeriesAttribute(index);
/* 297 */     hsa.setValues(vals);
/* 298 */     hsa.setLabels(labels);
/*     */   }
/*     */ 
/*     */   protected void buildGlobalAttributes(LabelledList list)
/*     */   {
/* 304 */     ((CategoryPlot)this.chart.getPlot()).setRangeGridlinesVisible(false);
/* 305 */     ((CategoryPlot)this.chart.getPlot()).setRangeGridlinePaint(new Color(200, 200, 200));
/*     */ 
/* 307 */     this.xLabel = new PropertyField()
/*     */     {
/*     */       public String newValue(String newValue)
/*     */       {
/* 311 */         BoxPlotGenerator.this.setXAxisLabel(newValue);
/* 312 */         BoxPlotGenerator.this.getChartPanel().repaint();
/* 313 */         return newValue;
/*     */       }
/*     */     };
/* 316 */     this.xLabel.setValue(getXAxisLabel());
/*     */ 
/* 318 */     list.add(new JLabel("X Label"), this.xLabel);
/*     */ 
/* 320 */     this.yLabel = new PropertyField()
/*     */     {
/*     */       public String newValue(String newValue)
/*     */       {
/* 324 */         BoxPlotGenerator.this.setYAxisLabel(newValue);
/* 325 */         BoxPlotGenerator.this.getChartPanel().repaint();
/* 326 */         return newValue;
/*     */       }
/*     */     };
/* 329 */     this.yLabel.setValue(getYAxisLabel());
/*     */ 
/* 331 */     list.add(new JLabel("Y Label"), this.yLabel);
/*     */ 
/* 333 */     this.yLog = new JCheckBox();
/* 334 */     this.yLog.addChangeListener(new ChangeListener()
/*     */     {
/*     */       public void stateChanged(ChangeEvent e) {
/* 337 */         if (BoxPlotGenerator.this.yLog.isSelected())
/*     */         {
/* 339 */           LogarithmicAxis logAxis = new LogarithmicAxis(BoxPlotGenerator.this.yLabel.getValue());
/* 340 */           logAxis.setStrictValuesFlag(false);
/* 341 */           ((CategoryPlot)BoxPlotGenerator.this.chart.getPlot()).setRangeAxis(logAxis);
/*     */         }
/*     */         else {
/* 344 */           ((CategoryPlot)BoxPlotGenerator.this.chart.getPlot()).setRangeAxis(new NumberAxis(BoxPlotGenerator.this.yLabel.getValue()));
/*     */         }
/*     */       }
/*     */     });
/* 348 */     list.add(new JLabel("Y Log Axis"), this.yLog);
/*     */ 
/* 350 */     JCheckBox ygridlines = new JCheckBox();
/* 351 */     ygridlines.setSelected(false);
/* 352 */     ItemListener il = new ItemListener()
/*     */     {
/*     */       public void itemStateChanged(ItemEvent e)
/*     */       {
/* 356 */         if (e.getStateChange() == 1)
/*     */         {
/* 358 */           ((CategoryPlot)BoxPlotGenerator.this.chart.getPlot()).setRangeGridlinesVisible(true);
/*     */         }
/*     */         else
/*     */         {
/* 362 */           ((CategoryPlot)BoxPlotGenerator.this.chart.getPlot()).setRangeGridlinesVisible(false);
/*     */         }
/*     */       }
/*     */     };
/* 366 */     ygridlines.addItemListener(il);
/*     */ 
/* 373 */     double INITIAL_WIDTH = 0.1D;
/* 374 */     double MAXIMUM_RATIONAL_WIDTH = 0.4D;
/*     */ 
/* 376 */     this.maximumWidthField = new NumberTextField(0.1D, 2.0D, 0.0D)
/*     */     {
/*     */       public double newValue(double newValue)
/*     */       {
/* 380 */         if ((newValue <= 0.0D) || (newValue > 0.4D))
/* 381 */           newValue = this.currentValue;
/* 382 */         ((BoxAndWhiskerRenderer)((CategoryPlot)BoxPlotGenerator.this.chart.getPlot()).getRenderer()).setMaximumBarWidth(newValue);
/*     */ 
/* 384 */         return newValue;
/*     */       }
/*     */     };
/* 387 */     list.addLabelled("Max Width", this.maximumWidthField);
/*     */ 
/* 389 */     Box box = Box.createHorizontalBox();
/* 390 */     box.add(new JLabel(" Y"));
/* 391 */     box.add(ygridlines);
/* 392 */     box.add(Box.createGlue());
/* 393 */     list.add(new JLabel("Y Grid Lines"), ygridlines);
/*     */ 
/* 395 */     this.mean = new JCheckBox();
/* 396 */     this.mean.setSelected(true);
/* 397 */     il = new ItemListener()
/*     */     {
/*     */       public void itemStateChanged(ItemEvent e)
/*     */       {
/* 401 */         BoxAndWhiskerRenderer renderer = (BoxAndWhiskerRenderer)((CategoryPlot)BoxPlotGenerator.this.chart.getPlot()).getRenderer();
/* 402 */         renderer.setMeanVisible(BoxPlotGenerator.this.mean.isSelected());
/*     */       }
/*     */     };
/* 405 */     this.mean.addItemListener(il);
/*     */ 
/* 407 */     this.median = new JCheckBox();
/* 408 */     this.median.setSelected(true);
/* 409 */     il = new ItemListener()
/*     */     {
/*     */       public void itemStateChanged(ItemEvent e)
/*     */       {
/* 413 */         BoxAndWhiskerRenderer renderer = (BoxAndWhiskerRenderer)((CategoryPlot)BoxPlotGenerator.this.chart.getPlot()).getRenderer();
/* 414 */         renderer.setMedianVisible(BoxPlotGenerator.this.median.isSelected());
/*     */       }
/*     */     };
/* 417 */     this.median.addItemListener(il);
/*     */ 
/* 419 */     list.add(new JLabel("Mean"), this.mean);
/* 420 */     list.add(new JLabel("Median"), this.median);
/*     */ 
/* 422 */     JCheckBox horizontal = new JCheckBox();
/* 423 */     horizontal.setSelected(false);
/* 424 */     il = new ItemListener()
/*     */     {
/*     */       public void itemStateChanged(ItemEvent e)
/*     */       {
/* 428 */         CategoryPlot plot = (CategoryPlot)BoxPlotGenerator.this.chart.getPlot();
/* 429 */         if (e.getStateChange() == 1)
/*     */         {
/* 431 */           plot.setOrientation(PlotOrientation.HORIZONTAL);
/*     */         }
/*     */         else
/*     */         {
/* 435 */           plot.setOrientation(PlotOrientation.VERTICAL);
/*     */         }
/*     */       }
/*     */     };
/* 440 */     horizontal.addItemListener(il);
/*     */ 
/* 442 */     list.add(new JLabel("Horizontal"), horizontal);
/*     */ 
/* 445 */     final JCheckBox whiskersUseFillColorButton = new JCheckBox();
/* 446 */     whiskersUseFillColorButton.setSelected(false);
/* 447 */     whiskersUseFillColorButton.addChangeListener(new ChangeListener()
/*     */     {
/*     */       public void stateChanged(ChangeEvent e) {
/* 450 */         BoxAndWhiskerRenderer renderer = (BoxAndWhiskerRenderer)((CategoryPlot)BoxPlotGenerator.this.chart.getPlot()).getRenderer();
/* 451 */         renderer.setUseOutlinePaintForWhiskers(!whiskersUseFillColorButton.isSelected());
/*     */       }
/*     */     });
/* 455 */     box = Box.createHorizontalBox();
/* 456 */     box.add(new JLabel(" Colored"));
/* 457 */     box.add(whiskersUseFillColorButton);
/* 458 */     box.add(Box.createGlue());
/* 459 */     list.add(new JLabel("Whiskers"), box);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.BoxPlotGenerator
 * JD-Core Version:    0.6.2
 */