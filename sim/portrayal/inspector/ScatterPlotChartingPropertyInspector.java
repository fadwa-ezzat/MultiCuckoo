/*     */ package sim.portrayal.inspector;
/*     */ 
/*     */ import java.awt.Frame;
/*     */ import org.jfree.data.general.SeriesChangeEvent;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import sim.display.ChartUtilities.ProvidesDoubleDoubles;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.Properties;
/*     */ import sim.util.media.chart.ChartGenerator;
/*     */ import sim.util.media.chart.ScatterPlotGenerator;
/*     */ import sim.util.media.chart.SeriesAttributes;
/*     */ import sim.util.media.chart.XYChartGenerator;
/*     */ 
/*     */ public class ScatterPlotChartingPropertyInspector extends ChartingPropertyInspector
/*     */ {
/*  21 */   double[][] previousValues = new double[2][0];
/*     */ 
/*  23 */   protected boolean validChartGenerator(ChartGenerator generator) { return generator instanceof ScatterPlotGenerator; } 
/*     */   protected boolean includeAggregationMethodAttributes() {
/*  25 */     return false;
/*     */   }
/*  27 */   public static String name() { return "Make Scatter Plot"; }
/*     */ 
/*     */   public static Class[] types() {
/*  30 */     return new Class[] { new Double2D[0].getClass(), new Int2D[0].getClass(), ChartUtilities.ProvidesDoubleDoubles.class };
/*     */   }
/*     */ 
/*     */   public ScatterPlotChartingPropertyInspector(Properties properties, int index, Frame parent, GUIState simulation)
/*     */   {
/*  38 */     super(properties, index, parent, simulation);
/*  39 */     setupSeriesAttributes(properties, index);
/*     */   }
/*     */ 
/*     */   public ScatterPlotChartingPropertyInspector(Properties properties, int index, GUIState simulation, ChartGenerator generator)
/*     */   {
/*  44 */     super(properties, index, simulation, generator);
/*  45 */     setupSeriesAttributes(properties, index);
/*     */   }
/*     */ 
/*     */   private void setupSeriesAttributes(Properties properties, int index)
/*     */   {
/*  51 */     if (isValidInspector())
/*     */     {
/*  53 */       if (getGenerator().getNumSeriesAttributes() == 0)
/*     */       {
/*  56 */         getGenerator().setTitle("" + properties.getName(index) + " of " + properties.getObject());
/*  57 */         ((XYChartGenerator)getGenerator()).setYAxisLabel("Y " + properties.getName(index));
/*  58 */         ((XYChartGenerator)getGenerator()).setXAxisLabel("X " + properties.getName(index));
/*     */       }
/*     */ 
/*  62 */       this.seriesAttributes = ((ScatterPlotGenerator)this.generator).addSeries(this.previousValues, properties.getName(index), new SeriesChangeListener()
/*     */       {
/*     */         public void seriesChanged(SeriesChangeEvent event) {
/*  65 */           ScatterPlotChartingPropertyInspector.this.getStopper().stop();
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   protected ChartGenerator createNewGenerator() {
/*  72 */     return new ScatterPlotGenerator()
/*     */     {
/*     */       public void quit()
/*     */       {
/*  76 */         super.quit();
/*  77 */         Stoppable stopper = ScatterPlotChartingPropertyInspector.this.getStopper();
/*  78 */         if (stopper != null) stopper.stop();
/*     */ 
/*  81 */         ScatterPlotChartingPropertyInspector.this.getCharts(ScatterPlotChartingPropertyInspector.this.simulation).remove(this);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public void updateSeries(double time, double lastTime)
/*     */   {
/*  88 */     Object obj = this.properties.getValue(this.index);
/*  89 */     if (obj == null) return;
/*  90 */     Class cls = obj.getClass();
/*  91 */     double[][] vals = this.previousValues;
/*     */ 
/*  93 */     if (cls.isArray())
/*     */     {
/*  95 */       Class comp = cls.getComponentType();
/*     */ 
/*  97 */       if (comp.equals(Double2D.class))
/*     */       {
/*  99 */         Double2D[] array = (Double2D[])obj;
/* 100 */         vals = new double[2][array.length];
/* 101 */         for (int i = 0; i < array.length; i++) {
/* 102 */           vals[0][i] = array[i].x; vals[1][i] = array[i].y;
/*     */         }
/* 104 */       } else if (comp.equals(Int2D.class))
/*     */       {
/* 106 */         Int2D[] array = (Int2D[])obj;
/* 107 */         vals = new double[2][array.length];
/* 108 */         for (int i = 0; i < array.length; i++) {
/* 109 */           vals[0][i] = array[i].x; vals[1][i] = array[i].y;
/*     */         }
/* 111 */       } else if ((obj instanceof ChartUtilities.ProvidesDoubleDoubles))
/*     */       {
/* 113 */         double[][] array = ((ChartUtilities.ProvidesDoubleDoubles)obj).provide();
/* 114 */         vals = new double[2][array.length];
/* 115 */         for (int i = 0; i < array.length; i++) {
/* 116 */           vals[0][i] = array[0][i]; vals[1][i] = array[1][i];
/*     */         }
/*     */       }
/*     */     }
/* 120 */     boolean same = true;
/* 121 */     if ((this.previousValues != null) && (vals.length == this.previousValues.length))
/*     */     {
/* 123 */       for (int i = 0; i < vals.length; i++)
/* 124 */         if (vals[i] != this.previousValues[i]) {
/* 125 */           same = false; break; } 
/*     */     }
/* 127 */     else same = false;
/*     */ 
/* 129 */     if (same) return;
/*     */ 
/* 132 */     this.previousValues = vals;
/*     */ 
/* 134 */     ((ScatterPlotGenerator)this.generator).updateSeries(this.seriesAttributes.getSeriesIndex(), vals);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.inspector.ScatterPlotChartingPropertyInspector
 * JD-Core Version:    0.6.2
 */