/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import javax.swing.Box;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import org.jfree.data.xy.DefaultXYDataset;
/*     */ 
/*     */ public class ScatterPlotGenerator extends XYChartGenerator
/*     */ {
/*  37 */   int shapeCounter = -1;
/*     */ 
/*     */   public void removeSeries(int index)
/*     */   {
/*  41 */     super.removeSeries(index);
/*  42 */     update();
/*     */   }
/*     */ 
/*     */   public void moveSeries(int index, boolean up)
/*     */   {
/*  47 */     super.moveSeries(index, up);
/*  48 */     update();
/*     */   }
/*     */ 
/*     */   protected void buildChart()
/*     */   {
/*  53 */     DefaultXYDataset dataset = new DefaultXYDataset();
/*  54 */     this.chart = ChartFactory.createScatterPlot("Untitled Chart", "Untitled X Axis", "Untitled Y Axis", dataset, PlotOrientation.VERTICAL, false, true, false);
/*     */ 
/*  56 */     this.chart.setAntiAlias(true);
/*     */ 
/*  58 */     this.chartPanel = buildChartPanel(this.chart);
/*     */ 
/*  60 */     setChartPanel(this.chartPanel);
/*  61 */     this.chart.getXYPlot().setRenderer(new XYLineAndShapeRenderer(false, true));
/*     */ 
/*  65 */     setSeriesDataset(dataset);
/*     */   }
/*     */ 
/*     */   protected void update()
/*     */   {
/*  73 */     SeriesAttributes[] sa = getSeriesAttributes();
/*     */ 
/*  75 */     DefaultXYDataset dataset = new DefaultXYDataset();
/*     */ 
/*  77 */     for (int i = 0; i < sa.length; i++)
/*     */     {
/*  79 */       ScatterPlotSeriesAttributes attributes = (ScatterPlotSeriesAttributes)sa[i];
/*  80 */       dataset.addSeries(new ChartGenerator.UniqueString(attributes.getSeriesName()), attributes.getValues());
/*     */     }
/*     */ 
/*  83 */     setSeriesDataset(dataset);
/*     */   }
/*     */ 
/*     */   public SeriesAttributes addSeries(double[][] values, String name, SeriesChangeListener stopper)
/*     */   {
/*  88 */     DefaultXYDataset dataset = (DefaultXYDataset)getSeriesDataset();
/*  89 */     int i = dataset.getSeriesCount();
/*  90 */     dataset.addSeries(new ChartGenerator.UniqueString(name), values);
/*     */ 
/*  93 */     ScatterPlotSeriesAttributes csa = new ScatterPlotSeriesAttributes(this, name, i, values, stopper);
/*  94 */     this.seriesAttributes.add(csa);
/*     */ 
/*  96 */     revalidate();
/*  97 */     update();
/*     */ 
/* 100 */     SwingUtilities.invokeLater(new Runnable() { public void run() { ScatterPlotGenerator.this.update(); }
/*     */ 
/*     */     });
/* 102 */     return csa;
/*     */   }
/*     */ 
/*     */   public void updateSeries(int index, double[][] vals)
/*     */   {
/* 107 */     if (index < 0) {
/* 108 */       return;
/*     */     }
/* 110 */     if (index >= getNumSeriesAttributes()) {
/* 111 */       return;
/*     */     }
/* 113 */     ScatterPlotSeriesAttributes series = (ScatterPlotSeriesAttributes)getSeriesAttribute(index);
/* 114 */     series.setValues(vals);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.ScatterPlotGenerator
 * JD-Core Version:    0.6.2
 */