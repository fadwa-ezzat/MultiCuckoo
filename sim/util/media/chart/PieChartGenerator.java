/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.StandardChartTheme;
/*     */ import org.jfree.chart.plot.MultiplePiePlot;
/*     */ import org.jfree.chart.plot.PiePlot;
/*     */ import org.jfree.chart.title.TextTitle;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
/*     */ import org.jfree.data.general.Dataset;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.util.TableOrder;
/*     */ 
/*     */ public class PieChartGenerator extends ChartGenerator
/*     */ {
/*     */   public static final int MAXIMUM_PIE_CHART_ITEMS = 20;
/*  55 */   final DefaultCategoryDataset emptyDataset = new DefaultCategoryDataset();
/*     */ 
/*     */   public void removeSeries(int index)
/*     */   {
/*  42 */     super.removeSeries(index);
/*  43 */     update();
/*     */   }
/*     */ 
/*     */   public void moveSeries(int index, boolean up)
/*     */   {
/*  49 */     super.moveSeries(index, up);
/*  50 */     update();
/*     */   }
/*     */ 
/*     */   public Dataset getSeriesDataset()
/*     */   {
/*  57 */     return ((MultiplePiePlot)this.chart.getPlot()).getDataset();
/*     */   }
/*     */ 
/*     */   public void setSeriesDataset(Dataset obj) {
/*  61 */     if (((CategoryDataset)obj).getRowCount() > 20)
/*     */     {
/*  63 */       ((MultiplePiePlot)this.chart.getPlot()).setDataset(this.emptyDataset);
/*  64 */       setInvalidChartTitle("[[ Dataset has too many items. ]]");
/*     */     }
/*     */     else
/*     */     {
/*  68 */       ((MultiplePiePlot)this.chart.getPlot()).setDataset((DefaultCategoryDataset)obj);
/*  69 */       if (this.invalidChartTitle != null)
/*  70 */         setInvalidChartTitle(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getProspectiveSeriesCount(Object[] objs)
/*     */   {
/*  76 */     HashMap map = convertIntoAmountsAndLabels(objs);
/*  77 */     String[] labels = revisedLabels(map);
/*  78 */     return labels.length;
/*     */   }
/*     */ 
/*     */   public int getSeriesCount()
/*     */   {
/*  83 */     SeriesAttributes[] sa = getSeriesAttributes();
/*  84 */     return sa.length;
/*     */   }
/*     */ 
/*     */   protected void buildChart()
/*     */   {
/*  89 */     DefaultCategoryDataset dataset = new DefaultCategoryDataset();
/*     */ 
/*  91 */     this.chart = ChartFactory.createMultiplePieChart("Untitled Chart", dataset, TableOrder.BY_COLUMN, false, true, false);
/*     */ 
/*  93 */     this.chart.setAntiAlias(true);
/*     */ 
/*  95 */     this.chartPanel = buildChartPanel(this.chart);
/*     */ 
/*  97 */     setChartPanel(this.chartPanel);
/*     */ 
/*  99 */     JFreeChart baseChart = ((MultiplePiePlot)this.chart.getPlot()).getPieChart();
/* 100 */     PiePlot base = (PiePlot)baseChart.getPlot();
/* 101 */     base.setIgnoreZeroValues(true);
/* 102 */     base.setLabelOutlinePaint(Color.WHITE);
/* 103 */     base.setLabelShadowPaint(Color.WHITE);
/* 104 */     base.setMaximumLabelWidth(0.25D);
/* 105 */     base.setInteriorGap(0.0D);
/* 106 */     base.setLabelBackgroundPaint(Color.WHITE);
/* 107 */     base.setOutlinePaint(null);
/* 108 */     base.setBackgroundPaint(null);
/* 109 */     base.setShadowPaint(null);
/* 110 */     base.setSimpleLabels(false);
/*     */ 
/* 113 */     StandardChartTheme theme = new StandardChartTheme("Hi");
/* 114 */     TextTitle title = new TextTitle("Whatever", theme.getLargeFont());
/* 115 */     title.setPaint(theme.getAxisLabelPaint());
/* 116 */     title.setPosition(RectangleEdge.BOTTOM);
/* 117 */     baseChart.setTitle(title);
/*     */ 
/* 120 */     setSeriesDataset(dataset);
/*     */   }
/*     */ 
/*     */   protected void update()
/*     */   {
/* 128 */     SeriesAttributes[] sa = getSeriesAttributes();
/* 129 */     DefaultCategoryDataset dataset = new DefaultCategoryDataset();
/*     */ 
/* 131 */     for (int i = 0; i < sa.length; i++) {
/* 132 */       if (sa[i].isPlotVisible())
/*     */       {
/* 134 */         PieChartSeriesAttributes attributes = (PieChartSeriesAttributes)sa[i];
/*     */ 
/* 136 */         Object[] elements = attributes.getElements();
/* 137 */         double[] values = null;
/* 138 */         String[] labels = null;
/* 139 */         if (elements != null)
/*     */         {
/* 141 */           HashMap map = convertIntoAmountsAndLabels(elements);
/* 142 */           labels = revisedLabels(map);
/* 143 */           values = amounts(map, labels);
/*     */         }
/*     */         else
/*     */         {
/* 147 */           values = attributes.getValues();
/* 148 */           labels = attributes.getLabels();
/*     */         }
/*     */ 
/* 151 */         ChartGenerator.UniqueString seriesName = new ChartGenerator.UniqueString(attributes.getSeriesName());
/*     */ 
/* 153 */         for (int j = 0; j < values.length; j++)
/* 154 */           dataset.addValue(values[j], labels[j], seriesName);
/*     */       }
/*     */     }
/* 157 */     setSeriesDataset(dataset);
/*     */   }
/*     */ 
/*     */   protected PieChartSeriesAttributes buildNewAttributes(String name, SeriesChangeListener stopper)
/*     */   {
/* 168 */     return new PieChartSeriesAttributes(this, name, getSeriesCount(), stopper);
/*     */   }
/*     */ 
/*     */   public SeriesAttributes addSeries(double[] amounts, String[] labels, String name, SeriesChangeListener stopper)
/*     */   {
/* 175 */     int i = getSeriesCount();
/*     */ 
/* 178 */     PieChartSeriesAttributes csa = buildNewAttributes(name, stopper);
/*     */ 
/* 181 */     csa.setValues((double[])amounts.clone());
/* 182 */     csa.setLabels((String[])labels.clone());
/*     */ 
/* 184 */     this.seriesAttributes.add(csa);
/*     */ 
/* 186 */     revalidate();
/* 187 */     update();
/*     */ 
/* 190 */     SwingUtilities.invokeLater(new Runnable() { public void run() { PieChartGenerator.this.update(); }
/*     */ 
/*     */     });
/* 192 */     return csa;
/*     */   }
/*     */ 
/*     */   public SeriesAttributes addSeries(Object[] objs, String name, SeriesChangeListener stopper)
/*     */   {
/* 199 */     int i = getSeriesCount();
/*     */ 
/* 202 */     PieChartSeriesAttributes csa = buildNewAttributes(name, stopper);
/*     */ 
/* 205 */     csa.setElements((Object[])objs.clone());
/*     */ 
/* 207 */     this.seriesAttributes.add(csa);
/*     */ 
/* 209 */     revalidate();
/* 210 */     update();
/*     */ 
/* 213 */     SwingUtilities.invokeLater(new Runnable() { public void run() { PieChartGenerator.this.update(); }
/*     */ 
/*     */     });
/* 215 */     return csa;
/*     */   }
/*     */ 
/*     */   public SeriesAttributes addSeries(Collection objs, String name, SeriesChangeListener stopper)
/*     */   {
/* 226 */     PieChartSeriesAttributes csa = buildNewAttributes(name, stopper);
/*     */ 
/* 229 */     csa.setElements(new ArrayList(objs));
/*     */ 
/* 231 */     this.seriesAttributes.add(csa);
/*     */ 
/* 233 */     revalidate();
/* 234 */     update();
/*     */ 
/* 237 */     SwingUtilities.invokeLater(new Runnable() { public void run() { PieChartGenerator.this.update(); }
/*     */ 
/*     */     });
/* 239 */     return csa;
/*     */   }
/*     */ 
/*     */   HashMap convertIntoAmountsAndLabels(Object[] objs)
/*     */   {
/* 246 */     HashMap map = new HashMap();
/* 247 */     for (int i = 0; i < objs.length; i++)
/*     */     {
/* 249 */       String label = "null";
/* 250 */       if (objs[i] != null)
/* 251 */         label = objs[i].toString();
/* 252 */       if (map.containsKey(label)) {
/* 253 */         map.put(label, new Double(((Double)map.get(label)).doubleValue() + 1.0D));
/*     */       }
/*     */       else
/* 256 */         map.put(label, new Double(1.0D));
/*     */     }
/* 258 */     return map;
/*     */   }
/*     */ 
/*     */   String[] revisedLabels(HashMap map)
/*     */   {
/* 265 */     String[] labels = new String[map.size()];
/* 266 */     labels = (String[])map.keySet().toArray(labels);
/* 267 */     Arrays.sort(labels);
/* 268 */     return labels;
/*     */   }
/*     */ 
/*     */   double[] amounts(HashMap map, String[] revisedLabels)
/*     */   {
/* 275 */     double[] amounts = new double[map.size()];
/* 276 */     for (int i = 0; i < amounts.length; i++)
/* 277 */       amounts[i] = ((Double)(Double)map.get(revisedLabels[i])).doubleValue();
/* 278 */     return amounts;
/*     */   }
/*     */ 
/*     */   public void updateSeries(int index, Collection objs)
/*     */   {
/* 283 */     if (index < 0) {
/* 284 */       return;
/*     */     }
/* 286 */     if (index >= getNumSeriesAttributes()) {
/* 287 */       return;
/*     */     }
/* 289 */     PieChartSeriesAttributes hsa = (PieChartSeriesAttributes)getSeriesAttribute(index);
/* 290 */     hsa.setElements(new ArrayList(objs));
/*     */   }
/*     */ 
/*     */   public void updateSeries(int index, Object[] objs)
/*     */   {
/* 295 */     if (index < 0) {
/* 296 */       return;
/*     */     }
/* 298 */     if (index >= getNumSeriesAttributes()) {
/* 299 */       return;
/*     */     }
/* 301 */     PieChartSeriesAttributes hsa = (PieChartSeriesAttributes)getSeriesAttribute(index);
/* 302 */     hsa.setElements((Object[])objs.clone());
/*     */   }
/*     */ 
/*     */   public void updateSeries(int index, double[] amounts, String[] labels)
/*     */   {
/* 307 */     if (index < 0) {
/* 308 */       return;
/*     */     }
/* 310 */     if (index >= getNumSeriesAttributes()) {
/* 311 */       return;
/*     */     }
/* 313 */     PieChartSeriesAttributes hsa = (PieChartSeriesAttributes)getSeriesAttribute(index);
/* 314 */     hsa.setValues((double[])amounts.clone());
/* 315 */     hsa.setLabels((String[])labels.clone());
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.PieChartGenerator
 * JD-Core Version:    0.6.2
 */