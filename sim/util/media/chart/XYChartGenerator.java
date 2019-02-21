/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import org.jfree.chart.ChartPanel;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.axis.LogarithmicAxis;
/*     */ import org.jfree.chart.axis.NumberAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.event.AxisChangeEvent;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.data.general.Dataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import sim.util.gui.LabelledList;
/*     */ import sim.util.gui.PropertyField;
/*     */ 
/*     */ public abstract class XYChartGenerator extends ChartGenerator
/*     */ {
/*     */   PropertyField xLabel;
/*     */   PropertyField yLabel;
/*     */   JCheckBox yLog;
/*     */   JCheckBox xLog;
/*     */ 
/*     */   public void setXAxisLogScaled(boolean isLogScaled)
/*     */   {
/*  60 */     this.xLog.setSelected(isLogScaled); } 
/*  61 */   public boolean isXAxisLogScaled() { return this.xLog.isSelected(); } 
/*  62 */   public void setYAxisLogScaled(boolean isLogScaled) { this.yLog.setSelected(isLogScaled); } 
/*  63 */   public boolean isYAxisLogScaled() { return this.yLog.isSelected(); } 
/*     */   public Dataset getSeriesDataset() {
/*  65 */     return ((XYPlot)this.chart.getPlot()).getDataset(); } 
/*  66 */   public void setSeriesDataset(Dataset obj) { ((XYPlot)this.chart.getPlot()).setDataset((XYDataset)obj); }
/*     */ 
/*     */ 
/*     */   public int getSeriesCount()
/*     */   {
/*  75 */     return ((XYDataset)getSeriesDataset()).getSeriesCount();
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setRangeAxisLabel(String val)
/*     */   {
/*  82 */     setYAxisLabel(val);
/*     */   }
/*     */ 
/*     */   public void setYAxisLabel(String val)
/*     */   {
/*  87 */     XYPlot xyplot = (XYPlot)this.chart.getPlot();
/*  88 */     xyplot.getRangeAxis().setLabel(val);
/*  89 */     xyplot.axisChanged(new AxisChangeEvent(xyplot.getRangeAxis()));
/*  90 */     this.yLabel.setValue(val);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public String getRangeAxisLabel() {
/*  95 */     return getYAxisLabel();
/*     */   }
/*     */ 
/*     */   public String getYAxisLabel()
/*     */   {
/* 100 */     return ((XYPlot)this.chart.getPlot()).getRangeAxis().getLabel();
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setDomainAxisLabel(String val) {
/* 105 */     setXAxisLabel(val);
/*     */   }
/*     */ 
/*     */   public void setXAxisLabel(String val)
/*     */   {
/* 110 */     XYPlot xyplot = (XYPlot)this.chart.getPlot();
/* 111 */     xyplot.getDomainAxis().setLabel(val);
/* 112 */     xyplot.axisChanged(new AxisChangeEvent(xyplot.getDomainAxis()));
/* 113 */     this.xLabel.setValue(val);
/*     */   }
/*     */   /** @deprecated */
/*     */   public String getDomainAxisLabel() {
/* 117 */     return getXAxisLabel();
/*     */   }
/*     */ 
/*     */   public String getXAxisLabel()
/*     */   {
/* 122 */     return ((XYPlot)this.chart.getPlot()).getDomainAxis().getLabel();
/*     */   }
/*     */ 
/*     */   public JFreeChart getChart()
/*     */   {
/* 128 */     return this.chart;
/*     */   }
/*     */ 
/*     */   protected void buildGlobalAttributes(LabelledList list)
/*     */   {
/* 135 */     ((XYPlot)this.chart.getPlot()).setDomainGridlinesVisible(false);
/* 136 */     ((XYPlot)this.chart.getPlot()).setRangeGridlinesVisible(false);
/* 137 */     ((XYPlot)this.chart.getPlot()).setDomainGridlinePaint(new Color(200, 200, 200));
/* 138 */     ((XYPlot)this.chart.getPlot()).setRangeGridlinePaint(new Color(200, 200, 200));
/*     */ 
/* 140 */     this.xLabel = new PropertyField()
/*     */     {
/*     */       public String newValue(String newValue)
/*     */       {
/* 144 */         XYChartGenerator.this.setXAxisLabel(newValue);
/* 145 */         XYChartGenerator.this.getChartPanel().repaint();
/* 146 */         return newValue;
/*     */       }
/*     */     };
/* 149 */     this.xLabel.setValue(getXAxisLabel());
/*     */ 
/* 151 */     list.add(new JLabel("X Label"), this.xLabel);
/*     */ 
/* 153 */     this.yLabel = new PropertyField()
/*     */     {
/*     */       public String newValue(String newValue)
/*     */       {
/* 157 */         XYChartGenerator.this.setYAxisLabel(newValue);
/* 158 */         XYChartGenerator.this.getChartPanel().repaint();
/* 159 */         return newValue;
/*     */       }
/*     */     };
/* 162 */     this.yLabel.setValue(getYAxisLabel());
/*     */ 
/* 164 */     list.add(new JLabel("Y Label"), this.yLabel);
/*     */ 
/* 166 */     this.xLog = new JCheckBox();
/* 167 */     this.xLog.addChangeListener(new ChangeListener()
/*     */     {
/*     */       public void stateChanged(ChangeEvent e) {
/* 170 */         if (XYChartGenerator.this.xLog.isSelected())
/*     */         {
/* 172 */           LogarithmicAxis logAxis = new LogarithmicAxis(XYChartGenerator.this.xLabel.getValue());
/* 173 */           logAxis.setStrictValuesFlag(false);
/* 174 */           XYChartGenerator.this.chart.getXYPlot().setDomainAxis(logAxis);
/*     */         }
/*     */         else {
/* 177 */           XYChartGenerator.this.chart.getXYPlot().setDomainAxis(new NumberAxis(XYChartGenerator.this.xLabel.getValue()));
/*     */         }
/*     */       }
/*     */     });
/* 181 */     this.yLog = new JCheckBox();
/* 182 */     this.yLog.addChangeListener(new ChangeListener()
/*     */     {
/*     */       public void stateChanged(ChangeEvent e) {
/* 185 */         if (XYChartGenerator.this.yLog.isSelected())
/*     */         {
/* 187 */           LogarithmicAxis logAxis = new LogarithmicAxis(XYChartGenerator.this.yLabel.getValue());
/* 188 */           logAxis.setStrictValuesFlag(false);
/* 189 */           XYChartGenerator.this.chart.getXYPlot().setRangeAxis(logAxis);
/*     */         }
/*     */         else {
/* 192 */           XYChartGenerator.this.chart.getXYPlot().setRangeAxis(new NumberAxis(XYChartGenerator.this.yLabel.getValue()));
/*     */         }
/*     */       }
/*     */     });
/* 196 */     Box box = Box.createHorizontalBox();
/* 197 */     box.add(new JLabel("X"));
/* 198 */     box.add(this.xLog);
/* 199 */     box.add(new JLabel(" Y"));
/* 200 */     box.add(this.yLog);
/* 201 */     box.add(Box.createGlue());
/* 202 */     list.add(new JLabel("Log Axis"), box);
/*     */ 
/* 204 */     JCheckBox xgridlines = new JCheckBox();
/* 205 */     xgridlines.setSelected(false);
/* 206 */     ItemListener il = new ItemListener()
/*     */     {
/*     */       public void itemStateChanged(ItemEvent e)
/*     */       {
/* 210 */         if (e.getStateChange() == 1)
/*     */         {
/* 212 */           XYChartGenerator.this.chart.getXYPlot().setDomainGridlinesVisible(true);
/*     */         }
/*     */         else
/*     */         {
/* 216 */           XYChartGenerator.this.chart.getXYPlot().setDomainGridlinesVisible(false);
/*     */         }
/*     */       }
/*     */     };
/* 220 */     xgridlines.addItemListener(il);
/*     */ 
/* 222 */     JCheckBox ygridlines = new JCheckBox();
/* 223 */     ygridlines.setSelected(false);
/* 224 */     il = new ItemListener()
/*     */     {
/*     */       public void itemStateChanged(ItemEvent e)
/*     */       {
/* 228 */         if (e.getStateChange() == 1)
/*     */         {
/* 230 */           XYChartGenerator.this.chart.getXYPlot().setRangeGridlinesVisible(true);
/*     */         }
/*     */         else
/*     */         {
/* 234 */           XYChartGenerator.this.chart.getXYPlot().setRangeGridlinesVisible(false);
/*     */         }
/*     */       }
/*     */     };
/* 238 */     ygridlines.addItemListener(il);
/*     */ 
/* 241 */     box = Box.createHorizontalBox();
/* 242 */     box.add(new JLabel("X"));
/* 243 */     box.add(xgridlines);
/* 244 */     box.add(new JLabel(" Y"));
/* 245 */     box.add(ygridlines);
/* 246 */     box.add(Box.createGlue());
/* 247 */     list.add(new JLabel("Grid Lines"), box);
/*     */   }
/*     */   /** @deprecated */
/*     */   public void setRangeAxisRange(double lower, double upper) {
/* 251 */     setYAxisRange(lower, upper);
/*     */   }
/*     */ 
/*     */   public void setYAxisRange(double lower, double upper) {
/* 255 */     XYPlot xyplot = (XYPlot)this.chart.getPlot();
/* 256 */     xyplot.getRangeAxis().setRange(lower, upper);
/*     */   }
/*     */   /** @deprecated */
/*     */   public void setDomainAxisRange(double lower, double upper) {
/* 260 */     setXAxisRange(lower, upper);
/*     */   }
/*     */ 
/*     */   public void setXAxisRange(double lower, double upper) {
/* 264 */     XYPlot xyplot = (XYPlot)this.chart.getPlot();
/* 265 */     xyplot.getDomainAxis().setRange(lower, upper);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.XYChartGenerator
 * JD-Core Version:    0.6.2
 */