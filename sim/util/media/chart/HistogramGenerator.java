/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.StandardXYBarPainter;
/*     */ import org.jfree.chart.renderer.xy.XYBarRenderer;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import org.jfree.data.statistics.HistogramDataset;
/*     */ import org.jfree.data.statistics.HistogramType;
/*     */ import sim.util.gui.DisclosurePanel;
/*     */ import sim.util.gui.LabelledList;
/*     */ 
/*     */ public class HistogramGenerator extends XYChartGenerator
/*     */ {
/*  45 */   HistogramType histogramType = HistogramType.FREQUENCY;
/*     */ 
/*     */   public void removeSeries(int index)
/*     */   {
/*  49 */     super.removeSeries(index);
/*  50 */     update();
/*     */   }
/*     */ 
/*     */   public void moveSeries(int index, boolean up)
/*     */   {
/*  56 */     super.moveSeries(index, up);
/*  57 */     update();
/*     */   }
/*     */ 
/*     */   protected void buildChart()
/*     */   {
/*  63 */     HistogramDataset dataset = new HistogramDataset();
/*  64 */     dataset.setType(HistogramType.FREQUENCY);
/*     */ 
/*  66 */     this.chart = ChartFactory.createHistogram("Untitled Chart", "Untitled X Axis", "Untitled Y Axis", dataset, PlotOrientation.VERTICAL, false, true, false);
/*     */ 
/*  68 */     this.chart.setAntiAlias(true);
/*     */ 
/*  70 */     this.chartPanel = buildChartPanel(this.chart);
/*  71 */     setChartPanel(this.chartPanel);
/*     */ 
/*  73 */     ((XYBarRenderer)this.chart.getXYPlot().getRenderer()).setShadowVisible(false);
/*  74 */     ((XYBarRenderer)this.chart.getXYPlot().getRenderer()).setBarPainter(new StandardXYBarPainter());
/*     */ 
/*  77 */     setSeriesDataset(dataset);
/*     */   }
/*     */ 
/*     */   protected void update()
/*     */   {
/*  85 */     SeriesAttributes[] sa = getSeriesAttributes();
/*  86 */     HistogramDataset dataset = new HistogramDataset();
/*  87 */     dataset.setType(this.histogramType);
/*     */ 
/*  89 */     for (int i = 0; i < sa.length; i++)
/*     */     {
/*  91 */       HistogramSeriesAttributes attributes = (HistogramSeriesAttributes)sa[i];
/*  92 */       dataset.addSeries(new ChartGenerator.UniqueString(attributes.getSeriesName()), attributes.getValues(), attributes.getNumBins());
/*     */     }
/*     */ 
/*  95 */     setSeriesDataset(dataset);
/*     */   }
/*     */ 
/*     */   public HistogramGenerator()
/*     */   {
/* 102 */     LabelledList list = new LabelledList("Show Histogram...");
/* 103 */     DisclosurePanel pan1 = new DisclosurePanel("Show Histogram...", list);
/*     */ 
/* 105 */     final HistogramType[] styles = { HistogramType.FREQUENCY, HistogramType.RELATIVE_FREQUENCY, HistogramType.SCALE_AREA_TO_1 };
/*     */ 
/* 107 */     final JComboBox style = new JComboBox(new String[] { "By Frequency", "By Relative Frequency", "With Area = 1.0" });
/* 108 */     style.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event)
/*     */       {
/* 112 */         HistogramGenerator.this.histogramType = styles[style.getSelectedIndex()];
/* 113 */         HistogramDataset dataset = (HistogramDataset)HistogramGenerator.this.getSeriesDataset();
/* 114 */         dataset.setType(HistogramGenerator.this.histogramType);
/*     */       }
/*     */     });
/* 117 */     list.add(style);
/* 118 */     addGlobalAttribute(pan1);
/*     */   }
/*     */ 
/*     */   public SeriesAttributes addSeries(double[] vals, int bins, String name, SeriesChangeListener stopper)
/*     */   {
/* 126 */     if ((vals == null) || (vals.length == 0)) vals = new double[] { 0.0D };
/* 127 */     HistogramDataset dataset = (HistogramDataset)getSeriesDataset();
/* 128 */     int i = dataset.getSeriesCount();
/* 129 */     dataset.setType(this.histogramType);
/* 130 */     dataset.addSeries(new ChartGenerator.UniqueString(name), vals, bins);
/*     */ 
/* 133 */     HistogramSeriesAttributes csa = new HistogramSeriesAttributes(this, name, i, vals, bins, stopper);
/* 134 */     this.seriesAttributes.add(csa);
/*     */ 
/* 136 */     revalidate();
/* 137 */     update();
/*     */ 
/* 140 */     SwingUtilities.invokeLater(new Runnable() { public void run() { HistogramGenerator.this.update(); }
/*     */ 
/*     */     });
/* 142 */     return csa;
/*     */   }
/*     */ 
/*     */   public void updateSeries(int index, double[] vals)
/*     */   {
/* 148 */     if (index < 0) {
/* 149 */       return;
/*     */     }
/* 151 */     if (index >= getNumSeriesAttributes()) {
/* 152 */       return;
/*     */     }
/* 154 */     if ((vals == null) || (vals.length == 0)) vals = new double[] { 0.0D };
/* 155 */     HistogramSeriesAttributes hsa = (HistogramSeriesAttributes)getSeriesAttribute(index);
/* 156 */     hsa.setValues(vals);
/*     */   }
/*     */ 
/*     */   public void setHistogramType(HistogramType type)
/*     */   {
/* 161 */     this.histogramType = type;
/*     */   }
/*     */ 
/*     */   public HistogramType getHistogramType()
/*     */   {
/* 166 */     return this.histogramType;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.HistogramGenerator
 * JD-Core Version:    0.6.2
 */