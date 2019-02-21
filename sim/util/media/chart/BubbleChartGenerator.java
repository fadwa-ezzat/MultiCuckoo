/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import javax.swing.Box;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.XYBubbleRenderer;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import org.jfree.data.xy.DefaultXYZDataset;
/*     */ 
/*     */ public class BubbleChartGenerator extends XYChartGenerator
/*     */ {
/*     */   public void removeSeries(int index)
/*     */   {
/*  38 */     super.removeSeries(index);
/*  39 */     update();
/*     */   }
/*     */ 
/*     */   public void moveSeries(int index, boolean up)
/*     */   {
/*  44 */     super.moveSeries(index, up);
/*  45 */     update();
/*     */   }
/*     */ 
/*     */   protected void buildChart()
/*     */   {
/*  50 */     DefaultXYZDataset dataset = new DefaultXYZDataset();
/*  51 */     this.chart = ChartFactory.createBubbleChart("Untitled Chart", "Untitled X Axis", "Untitled Y Axis", dataset, PlotOrientation.VERTICAL, false, true, false);
/*     */ 
/*  53 */     this.chart.setAntiAlias(true);
/*  54 */     this.chartPanel = buildChartPanel(this.chart);
/*  55 */     setChartPanel(this.chartPanel);
/*     */ 
/*  59 */     this.chart.getXYPlot().setRenderer(new XYBubbleRenderer(1));
/*     */ 
/*  62 */     setSeriesDataset(dataset);
/*     */   }
/*     */ 
/*     */   protected void update()
/*     */   {
/*  70 */     SeriesAttributes[] sa = getSeriesAttributes();
/*     */ 
/*  72 */     DefaultXYZDataset dataset = new DefaultXYZDataset();
/*     */ 
/*  74 */     for (int i = 0; i < sa.length; i++)
/*     */     {
/*  76 */       BubbleChartSeriesAttributes attributes = (BubbleChartSeriesAttributes)sa[i];
/*  77 */       double scale = attributes.getScale();
/*     */ 
/*  85 */       double[][] values = attributes.getValues();
/*  86 */       double[][] v2 = new double[values.length][values[0].length];
/*  87 */       for (int k = 0; k < v2.length; k++)
/*  88 */         for (int j = 0; j < v2[k].length; j++)
/*  89 */           v2[k][j] = values[k][j];
/*  90 */       for (int j = 0; j < v2[2].length; j++) {
/*  91 */         v2[2][j] = Math.sqrt(scale * v2[2][j]);
/*     */       }
/*  93 */       dataset.addSeries(new ChartGenerator.UniqueString(attributes.getSeriesName()), v2);
/*     */     }
/*     */ 
/*  96 */     setSeriesDataset(dataset);
/*     */   }
/*     */ 
/*     */   public SeriesAttributes addSeries(double[][] values, String name, SeriesChangeListener stopper)
/*     */   {
/* 101 */     DefaultXYZDataset dataset = (DefaultXYZDataset)getSeriesDataset();
/* 102 */     int i = dataset.getSeriesCount();
/* 103 */     dataset.addSeries(new ChartGenerator.UniqueString(name), values);
/*     */ 
/* 106 */     BubbleChartSeriesAttributes csa = new BubbleChartSeriesAttributes(this, name, i, values, stopper);
/* 107 */     this.seriesAttributes.add(csa, name);
/*     */ 
/* 109 */     revalidate();
/* 110 */     update();
/*     */ 
/* 113 */     SwingUtilities.invokeLater(new Runnable() { public void run() { BubbleChartGenerator.this.update(); }
/*     */ 
/*     */     });
/* 115 */     return csa;
/*     */   }
/*     */ 
/*     */   public void updateSeries(int index, double[][] vals)
/*     */   {
/* 120 */     if (index < 0) {
/* 121 */       return;
/*     */     }
/* 123 */     if (index >= getNumSeriesAttributes()) {
/* 124 */       return;
/*     */     }
/* 126 */     BubbleChartSeriesAttributes series = (BubbleChartSeriesAttributes)getSeriesAttribute(index);
/* 127 */     series.setValues(vals);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.BubbleChartGenerator
 * JD-Core Version:    0.6.2
 */