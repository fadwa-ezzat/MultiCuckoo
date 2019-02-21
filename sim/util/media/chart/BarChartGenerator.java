/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Vector;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.ChartPanel;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.event.AxisChangeEvent;
/*     */ import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.renderer.category.BarRenderer;
/*     */ import org.jfree.chart.renderer.category.StackedBarRenderer;
/*     */ import org.jfree.chart.renderer.category.StandardBarPainter;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
/*     */ import org.jfree.data.general.Dataset;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import sim.util.gui.LabelledList;
/*     */ import sim.util.gui.PropertyField;
/*     */ 
/*     */ public class BarChartGenerator extends PieChartGenerator
/*     */ {
/*     */   PropertyField xLabel;
/*     */   PropertyField yLabel;
/*     */   BarRenderer barRenderer;
/*     */   StackedBarRenderer stackedBarRenderer;
/*     */   StackedBarRenderer percentageRenderer;
/* 250 */   boolean hasgridlines = false;
/* 251 */   boolean ishorizontal = false;
/*     */   public static final int MAXIMUM_BAR_CHART_ITEMS = 20;
/*     */ 
/*     */   public void setYAxisLabel(String val)
/*     */   {
/*  65 */     CategoryPlot xyplot = (CategoryPlot)this.chart.getPlot();
/*  66 */     xyplot.getRangeAxis().setLabel(val);
/*  67 */     xyplot.axisChanged(new AxisChangeEvent(xyplot.getRangeAxis()));
/*  68 */     this.yLabel.setValue(val);
/*     */   }
/*     */ 
/*     */   public String getYAxisLabel()
/*     */   {
/*  74 */     return ((CategoryPlot)this.chart.getPlot()).getRangeAxis().getLabel();
/*     */   }
/*     */ 
/*     */   public void setXAxisLabel(String val)
/*     */   {
/*  80 */     CategoryPlot xyplot = (CategoryPlot)this.chart.getPlot();
/*  81 */     xyplot.getDomainAxis().setLabel(val);
/*  82 */     xyplot.axisChanged(new AxisChangeEvent(xyplot.getDomainAxis()));
/*  83 */     this.xLabel.setValue(val);
/*     */   }
/*     */ 
/*     */   public String getXAxisLabel()
/*     */   {
/*  89 */     return ((CategoryPlot)this.chart.getPlot()).getDomainAxis().getLabel();
/*     */   }
/*     */ 
/*     */   BarRenderer getBarRenderer()
/*     */   {
/*  96 */     return this.barRenderer; } 
/*  97 */   BarRenderer getStackedBarRenderer() { return this.stackedBarRenderer; } 
/*  98 */   BarRenderer getPercentageRenderer() { return this.percentageRenderer; }
/*     */ 
/*     */   void reviseRenderer(BarRenderer renderer)
/*     */   {
/* 102 */     renderer.setShadowVisible(false);
/* 103 */     renderer.setBarPainter(new StandardBarPainter());
/* 104 */     renderer.setBaseOutlineStroke(new BasicStroke(2.0F));
/* 105 */     renderer.setDrawBarOutline(true);
/* 106 */     renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{0}", NumberFormat.getInstance()));
/*     */ 
/* 109 */     renderer.setBaseItemLabelsVisible(true);
/*     */   }
/*     */ 
/*     */   protected void buildGlobalAttributes(LabelledList list)
/*     */   {
/* 115 */     CategoryPlot plot = (CategoryPlot)this.chart.getPlot();
/* 116 */     plot.setDomainGridlinesVisible(false);
/* 117 */     plot.setRangeGridlinesVisible(false);
/* 118 */     plot.setDomainGridlinePaint(new Color(200, 200, 200));
/* 119 */     plot.setRangeGridlinePaint(new Color(200, 200, 200));
/*     */ 
/* 122 */     this.barRenderer = new BarRenderer();
/* 123 */     reviseRenderer(this.barRenderer);
/*     */ 
/* 125 */     this.stackedBarRenderer = new StackedBarRenderer(false);
/* 126 */     reviseRenderer(this.stackedBarRenderer);
/*     */ 
/* 128 */     this.percentageRenderer = new StackedBarRenderer(true);
/* 129 */     reviseRenderer(this.percentageRenderer);
/*     */ 
/* 131 */     plot.setRenderer(this.barRenderer);
/*     */ 
/* 133 */     this.xLabel = new PropertyField()
/*     */     {
/*     */       public String newValue(String newValue)
/*     */       {
/* 137 */         BarChartGenerator.this.setXAxisLabel(newValue);
/* 138 */         BarChartGenerator.this.getChartPanel().repaint();
/* 139 */         return newValue;
/*     */       }
/*     */     };
/* 142 */     this.xLabel.setValue(getXAxisLabel());
/*     */ 
/* 144 */     list.add(new JLabel("X Label"), this.xLabel);
/*     */ 
/* 146 */     this.yLabel = new PropertyField()
/*     */     {
/*     */       public String newValue(String newValue)
/*     */       {
/* 150 */         BarChartGenerator.this.setYAxisLabel(newValue);
/* 151 */         BarChartGenerator.this.getChartPanel().repaint();
/* 152 */         return newValue;
/*     */       }
/*     */     };
/* 155 */     this.yLabel.setValue(getYAxisLabel());
/*     */ 
/* 157 */     list.add(new JLabel("Y Label"), this.yLabel);
/*     */ 
/* 159 */     JCheckBox gridlines = new JCheckBox();
/* 160 */     gridlines.setSelected(false);
/* 161 */     ItemListener il = new ItemListener()
/*     */     {
/*     */       public void itemStateChanged(ItemEvent e)
/*     */       {
/* 165 */         BarChartGenerator.this.hasgridlines = (e.getStateChange() == 1);
/* 166 */         BarChartGenerator.this.updateGridLines();
/*     */       }
/*     */     };
/* 169 */     gridlines.addItemListener(il);
/*     */ 
/* 171 */     list.add(new JLabel("Grid Lines"), gridlines);
/*     */ 
/* 173 */     JCheckBox labels = new JCheckBox();
/* 174 */     labels.setSelected(true);
/* 175 */     il = new ItemListener()
/*     */     {
/*     */       public void itemStateChanged(ItemEvent e)
/*     */       {
/* 179 */         if (e.getStateChange() == 1)
/*     */         {
/* 181 */           BarChartGenerator.this.getBarRenderer().setBaseItemLabelsVisible(true);
/* 182 */           BarChartGenerator.this.getStackedBarRenderer().setBaseItemLabelsVisible(true);
/* 183 */           BarChartGenerator.this.getPercentageRenderer().setBaseItemLabelsVisible(true);
/*     */         }
/*     */         else
/*     */         {
/* 187 */           BarChartGenerator.this.getBarRenderer().setBaseItemLabelsVisible(false);
/* 188 */           BarChartGenerator.this.getStackedBarRenderer().setBaseItemLabelsVisible(false);
/* 189 */           BarChartGenerator.this.getPercentageRenderer().setBaseItemLabelsVisible(false);
/*     */         }
/*     */       }
/*     */     };
/* 193 */     labels.addItemListener(il);
/* 194 */     list.add(new JLabel("Labels"), labels);
/*     */ 
/* 196 */     final JComboBox barType = new JComboBox();
/* 197 */     barType.setEditable(false);
/* 198 */     barType.setModel(new DefaultComboBoxModel(new Vector(Arrays.asList(new String[] { "Separate", "Stacked", "Percentage" }))));
/*     */ 
/* 200 */     barType.setSelectedIndex(0);
/* 201 */     barType.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/* 205 */         CategoryPlot plot = (CategoryPlot)BarChartGenerator.this.chart.getPlot();
/* 206 */         int type = barType.getSelectedIndex();
/*     */ 
/* 208 */         if (type == 0)
/*     */         {
/* 210 */           plot.setRenderer(BarChartGenerator.this.getBarRenderer());
/*     */         }
/* 212 */         else if (type == 1)
/*     */         {
/* 214 */           plot.setRenderer(BarChartGenerator.this.getStackedBarRenderer());
/*     */         }
/*     */         else
/*     */         {
/* 218 */           plot.setRenderer(BarChartGenerator.this.getPercentageRenderer());
/*     */         }
/*     */       }
/*     */     });
/* 222 */     list.add(new JLabel("Bars"), barType);
/*     */ 
/* 225 */     JCheckBox horizontal = new JCheckBox();
/* 226 */     horizontal.setSelected(false);
/* 227 */     il = new ItemListener()
/*     */     {
/*     */       public void itemStateChanged(ItemEvent e)
/*     */       {
/* 231 */         CategoryPlot plot = (CategoryPlot)BarChartGenerator.this.chart.getPlot();
/* 232 */         if (e.getStateChange() == 1)
/*     */         {
/* 234 */           plot.setOrientation(PlotOrientation.HORIZONTAL);
/* 235 */           BarChartGenerator.this.ishorizontal = true;
/*     */         }
/*     */         else
/*     */         {
/* 239 */           plot.setOrientation(PlotOrientation.VERTICAL);
/* 240 */           BarChartGenerator.this.ishorizontal = false;
/*     */         }
/* 242 */         BarChartGenerator.this.updateGridLines();
/*     */       }
/*     */     };
/* 245 */     horizontal.addItemListener(il);
/*     */ 
/* 247 */     list.add(new JLabel("Horizontal"), horizontal);
/*     */   }
/*     */ 
/*     */   void updateGridLines()
/*     */   {
/* 254 */     if (this.hasgridlines)
/*     */     {
/* 256 */       if (this.ishorizontal)
/*     */       {
/* 258 */         this.chart.getCategoryPlot().setRangeGridlinesVisible(true);
/* 259 */         this.chart.getCategoryPlot().setDomainGridlinesVisible(false);
/*     */       }
/*     */       else
/*     */       {
/* 263 */         this.chart.getCategoryPlot().setRangeGridlinesVisible(true);
/* 264 */         this.chart.getCategoryPlot().setDomainGridlinesVisible(false);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 269 */       this.chart.getCategoryPlot().setRangeGridlinesVisible(false);
/* 270 */       this.chart.getCategoryPlot().setDomainGridlinesVisible(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Dataset getSeriesDataset() {
/* 275 */     return ((CategoryPlot)this.chart.getPlot()).getDataset();
/*     */   }
/*     */ 
/*     */   public void setSeriesDataset(Dataset obj) {
/* 279 */     if (((CategoryDataset)obj).getRowCount() > 20)
/*     */     {
/* 281 */       ((CategoryPlot)this.chart.getPlot()).setDataset(this.emptyDataset);
/* 282 */       setInvalidChartTitle("[[ Dataset has too many items. ]]");
/*     */     }
/*     */     else
/*     */     {
/* 286 */       ((CategoryPlot)this.chart.getPlot()).setDataset((DefaultCategoryDataset)obj);
/* 287 */       if (this.invalidChartTitle != null)
/* 288 */         setInvalidChartTitle(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected PieChartSeriesAttributes buildNewAttributes(String name, SeriesChangeListener stopper)
/*     */   {
/* 294 */     return new BarChartSeriesAttributes(this, name, getSeriesCount(), stopper);
/*     */   }
/*     */ 
/*     */   protected void buildChart()
/*     */   {
/* 299 */     DefaultCategoryDataset dataset = new DefaultCategoryDataset();
/*     */ 
/* 301 */     this.chart = ChartFactory.createBarChart("Untitled Chart", "Category", "Value", dataset, PlotOrientation.VERTICAL, false, true, false);
/*     */ 
/* 303 */     this.chart.setAntiAlias(true);
/*     */ 
/* 305 */     this.chartPanel = buildChartPanel(this.chart);
/*     */ 
/* 307 */     setChartPanel(this.chartPanel);
/*     */ 
/* 310 */     setSeriesDataset(dataset);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.BarChartGenerator
 * JD-Core Version:    0.6.2
 */