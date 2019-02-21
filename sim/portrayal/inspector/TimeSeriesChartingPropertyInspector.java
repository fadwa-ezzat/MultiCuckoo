/*     */ package sim.portrayal.inspector;
/*     */ 
/*     */ import java.awt.Frame;
/*     */ import org.jfree.data.general.SeriesChangeEvent;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import org.jfree.data.xy.XYDataItem;
/*     */ import org.jfree.data.xy.XYSeries;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Properties;
/*     */ import sim.util.Valuable;
/*     */ import sim.util.media.chart.ChartGenerator;
/*     */ import sim.util.media.chart.TimeSeriesAttributes;
/*     */ import sim.util.media.chart.TimeSeriesChartGenerator;
/*     */ import sim.util.media.chart.XYChartGenerator;
/*     */ 
/*     */ public class TimeSeriesChartingPropertyInspector extends ChartingPropertyInspector
/*     */ {
/*  34 */   XYSeries chartSeries = null;
/*  35 */   XYSeries aggregateSeries = new XYSeries("ChartingPropertyInspector.temp", false);
/*     */ 
/*  37 */   protected boolean validChartGenerator(ChartGenerator generator) { return generator instanceof TimeSeriesChartGenerator; } 
/*     */   public static String name() {
/*  39 */     return "Chart";
/*     */   }
/*     */   public static Class[] types() {
/*  42 */     return new Class[] { Number.class, Boolean.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Valuable.class };
/*     */   }
/*     */ 
/*     */   public TimeSeriesChartingPropertyInspector(Properties properties, int index, Frame parent, GUIState simulation)
/*     */   {
/*  52 */     super(properties, index, parent, simulation);
/*  53 */     setupSeriesAttributes(properties, index);
/*     */   }
/*     */ 
/*     */   public TimeSeriesChartingPropertyInspector(Properties properties, int index, GUIState simulation, ChartGenerator generator)
/*     */   {
/*  58 */     super(properties, index, simulation, generator);
/*  59 */     setupSeriesAttributes(properties, index);
/*     */   }
/*     */ 
/*     */   private void setupSeriesAttributes(Properties properties, int index)
/*     */   {
/*  65 */     if (isValidInspector())
/*     */     {
/*  67 */       if (getGenerator().getNumSeriesAttributes() == 0)
/*     */       {
/*  70 */         getGenerator().setTitle("" + properties.getName(index) + " of " + properties.getObject());
/*  71 */         ((XYChartGenerator)getGenerator()).setYAxisLabel("" + properties.getName(index));
/*  72 */         ((XYChartGenerator)getGenerator()).setXAxisLabel("Time");
/*     */       }
/*     */ 
/*  75 */       this.chartSeries = new XYSeries(properties.getName(index), false);
/*     */ 
/*  78 */       this.seriesAttributes = ((TimeSeriesChartGenerator)this.generator).addSeries(this.chartSeries, new SeriesChangeListener() {
/*     */         public void seriesChanged(SeriesChangeEvent event) {
/*  80 */           TimeSeriesChartingPropertyInspector.this.getStopper().stop();
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   protected ChartGenerator createNewGenerator() {
/*  87 */     return new TimeSeriesChartGenerator()
/*     */     {
/*     */       public void quit()
/*     */       {
/*  91 */         super.quit();
/*  92 */         Stoppable stopper = TimeSeriesChartingPropertyInspector.this.getStopper();
/*  93 */         if (stopper != null) stopper.stop();
/*     */ 
/*  96 */         TimeSeriesChartingPropertyInspector.this.getCharts(TimeSeriesChartingPropertyInspector.this.simulation).remove(this);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   protected double valueFor(Object o)
/*     */   {
/* 103 */     if ((o instanceof Number))
/* 104 */       return ((Number)o).doubleValue();
/* 105 */     if ((o instanceof Valuable))
/* 106 */       return ((Valuable)o).doubleValue();
/* 107 */     if ((o instanceof Boolean))
/* 108 */       return ((Boolean)o).booleanValue() ? 1.0D : 0.0D;
/* 109 */     return (0.0D / 0.0D);
/*     */   }
/*     */ 
/*     */   void addToMainSeries(double x, double y, boolean notify)
/*     */   {
/* 114 */     this.chartSeries.add(x, y, false);
/* 115 */     TimeSeriesAttributes attributes = (TimeSeriesAttributes)this.seriesAttributes;
/* 116 */     if (!attributes.possiblyCull())
/*     */     {
/* 118 */       if (notify)
/* 119 */         this.chartSeries.fireSeriesChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void updateSeries(double time, double lastTime)
/*     */   {
/* 125 */     double d = 0.0D;
/*     */ 
/* 127 */     ChartingPropertyInspector.GlobalAttributes globalAttributes = getGlobalAttributes();
/*     */ 
/* 130 */     this.aggregateSeries.add(time, d = valueFor(this.properties.getValue(this.index)), false);
/* 131 */     int len = this.aggregateSeries.getItemCount();
/*     */ 
/* 134 */     long interval = globalAttributes.interval;
/* 135 */     double intervalMark = time % interval;
/* 136 */     if ((intervalMark != 0.0D) && (time - lastTime < interval) && (lastTime % interval <= intervalMark))
/*     */     {
/* 142 */       return;
/*     */     }
/*     */ 
/* 145 */     double y = 0.0D;
/*     */ 
/* 147 */     switch (globalAttributes.aggregationMethod)
/*     */     {
/*     */     case 0:
/* 150 */       addToMainSeries(time, d, false);
/* 151 */       break;
/*     */     case 1:
/* 153 */       double maxX = 0.0D;
/* 154 */       for (int i = 0; i < len; i++)
/*     */       {
/* 156 */         XYDataItem item = this.aggregateSeries.getDataItem(i);
/* 157 */         y = item.getY().doubleValue();
/* 158 */         double temp = item.getX().doubleValue();
/* 159 */         if ((maxX < temp) || (i == 0)) maxX = temp;
/*     */       }
/* 161 */       addToMainSeries(maxX, y, false);
/* 162 */       break;
/*     */     case 2:
/* 164 */       double minX = 0.0D;
/* 165 */       for (int i = 0; i < len; i++)
/*     */       {
/* 167 */         XYDataItem item = this.aggregateSeries.getDataItem(i);
/* 168 */         y = item.getY().doubleValue();
/* 169 */         double temp = item.getX().doubleValue();
/* 170 */         if ((minX > temp) || (i == 0)) minX = temp;
/*     */       }
/* 172 */       addToMainSeries(minX, y, false);
/* 173 */       break;
/*     */     case 3:
/* 175 */       double sumX = 0.0D;
/* 176 */       int n = 0;
/* 177 */       for (int i = 0; i < len; i++)
/*     */       {
/* 179 */         XYDataItem item = this.aggregateSeries.getDataItem(i);
/* 180 */         y = item.getY().doubleValue();
/* 181 */         sumX += item.getX().doubleValue();
/* 182 */         n++;
/*     */       }
/* 184 */       if (n != 0)
/*     */       {
/* 188 */         addToMainSeries(sumX / n, y, false);
/* 189 */       }break;
/*     */     default:
/* 191 */       throw new RuntimeException("No valid aggregation method provided");
/*     */     }
/* 193 */     this.aggregateSeries.clear();
/*     */   }
/*     */ 
/*     */   protected boolean alwaysUpdateable() {
/* 197 */     return false;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.inspector.TimeSeriesChartingPropertyInspector
 * JD-Core Version:    0.6.2
 */