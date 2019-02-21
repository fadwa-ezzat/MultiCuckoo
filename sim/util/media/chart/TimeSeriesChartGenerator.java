/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JLabel;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import org.jfree.data.xy.XYSeries;
/*     */ import org.jfree.data.xy.XYSeriesCollection;
/*     */ import sim.util.gui.DisclosurePanel;
/*     */ import sim.util.gui.LabelledList;
/*     */ import sim.util.gui.NumberTextField;
/*     */ 
/*     */ public class TimeSeriesChartGenerator extends XYChartGenerator
/*     */ {
/*     */   JCheckBox useCullingCheckBox;
/*     */   NumberTextField maxPointsPerSeriesTextField;
/*     */   DataCuller dataCuller;
/*     */ 
/*     */   public void clearAllSeries()
/*     */   {
/*  46 */     SeriesAttributes[] c = getSeriesAttributes();
/*  47 */     for (int i = 0; i < c.length; i++)
/*     */     {
/*  49 */       ((TimeSeriesAttributes)c[i]).clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeSeries(int index)
/*     */   {
/*  55 */     super.removeSeries(index);
/*  56 */     XYSeriesCollection xysc = (XYSeriesCollection)getSeriesDataset();
/*  57 */     xysc.removeSeries(index);
/*     */   }
/*     */ 
/*     */   public void moveSeries(int index, boolean up)
/*     */   {
/*  62 */     super.moveSeries(index, up);
/*     */ 
/*  64 */     if (((index > 0) && (up)) || ((index < getSeriesCount() - 1) && (!up)))
/*     */     {
/*  66 */       XYSeriesCollection xysc = (XYSeriesCollection)getSeriesDataset();
/*     */ 
/*  68 */       ArrayList items = new ArrayList(xysc.getSeries());
/*  69 */       xysc.removeAllSeries();
/*     */ 
/*  71 */       int delta = up ? -1 : 1;
/*     */ 
/*  73 */       items.add(index + delta, items.remove(index));
/*     */ 
/*  76 */       for (int i = 0; i < items.size(); i++)
/*  77 */         xysc.addSeries((XYSeries)items.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   public SeriesAttributes addSeries(XYSeries series, SeriesChangeListener stopper)
/*     */   {
/*  86 */     XYSeriesCollection xysc = (XYSeriesCollection)getSeriesDataset();
/*     */ 
/*  88 */     int i = xysc.getSeriesCount();
/*  89 */     series.setKey(new ChartGenerator.UniqueString(series.getKey()));
/*  90 */     xysc.addSeries(series);
/*  91 */     TimeSeriesAttributes csa = new TimeSeriesAttributes(this, series, i, stopper);
/*  92 */     this.seriesAttributes.add(csa);
/*  93 */     revalidate();
/*  94 */     return csa;
/*     */   }
/*     */ 
/*     */   protected void buildChart()
/*     */   {
/* 100 */     XYSeriesCollection collection = new XYSeriesCollection();
/*     */ 
/* 102 */     this.chart = ChartFactory.createXYLineChart("Untitled Chart", "Untitled X Axis", "Untitled Y Axis", collection, PlotOrientation.VERTICAL, false, true, false);
/*     */ 
/* 104 */     ((XYLineAndShapeRenderer)((XYPlot)this.chart.getPlot()).getRenderer()).setDrawSeriesLineAsPath(true);
/*     */ 
/* 106 */     this.chart.setAntiAlias(true);
/*     */ 
/* 108 */     this.chartPanel = buildChartPanel(this.chart);
/* 109 */     setChartPanel(this.chartPanel);
/*     */ 
/* 113 */     setSeriesDataset(collection);
/*     */   }
/*     */ 
/*     */   public DataCuller getDataCuller()
/*     */   {
/* 120 */     return this.dataCuller; } 
/* 121 */   public void setDataCuller(DataCuller dataCuller) { this.dataCuller = dataCuller; }
/*     */ 
/*     */ 
/*     */   public TimeSeriesChartGenerator()
/*     */   {
/* 126 */     LabelledList globalAttribList = (LabelledList)((DisclosurePanel)getGlobalAttribute(-2)).getDisclosedComponent();
/* 127 */     this.useCullingCheckBox = new JCheckBox();
/*     */ 
/* 129 */     globalAttribList.add(new JLabel("Cull Data"), this.useCullingCheckBox);
/* 130 */     this.maxPointsPerSeriesTextField = new NumberTextField(1000.0D)
/*     */     {
/*     */       public double newValue(double val)
/*     */       {
/* 134 */         int max = (int)val;
/* 135 */         if (val < 2.0D)
/* 136 */           return (int)getValue();
/* 137 */         TimeSeriesChartGenerator.this.dataCuller = new MinGapDataCuller(max);
/* 138 */         return max;
/*     */       }
/*     */     };
/* 141 */     this.useCullingCheckBox.setSelected(true);
/* 142 */     globalAttribList.add(new JLabel("... Over"), this.maxPointsPerSeriesTextField);
/* 143 */     this.maxPointsPerSeriesTextField.setToolTipText("The maximum number of data points in a series before data culling gets triggered.");
/*     */ 
/* 145 */     this.dataCuller = new MinGapDataCuller((int)this.maxPointsPerSeriesTextField.getValue());
/*     */ 
/* 148 */     this.useCullingCheckBox.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event)
/*     */       {
/* 152 */         if (TimeSeriesChartGenerator.this.useCullingCheckBox.isSelected())
/*     */         {
/* 154 */           TimeSeriesChartGenerator.this.maxPointsPerSeriesTextField.setEnabled(true);
/* 155 */           int maxPoints = (int)TimeSeriesChartGenerator.this.maxPointsPerSeriesTextField.getValue();
/* 156 */           TimeSeriesChartGenerator.this.dataCuller = new MinGapDataCuller(maxPoints);
/*     */         }
/*     */         else
/*     */         {
/* 160 */           TimeSeriesChartGenerator.this.maxPointsPerSeriesTextField.setEnabled(false);
/* 161 */           TimeSeriesChartGenerator.this.dataCuller = null;
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.TimeSeriesChartGenerator
 * JD-Core Version:    0.6.2
 */