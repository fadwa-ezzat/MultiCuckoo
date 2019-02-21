/*     */ package sim.portrayal.inspector;
/*     */ 
/*     */ import java.awt.Frame;
/*     */ import org.jfree.data.general.SeriesChangeEvent;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import sim.display.ChartUtilities.ProvidesTripleDoubles;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.Double3D;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.Int3D;
/*     */ import sim.util.Properties;
/*     */ import sim.util.media.chart.BubbleChartGenerator;
/*     */ import sim.util.media.chart.ChartGenerator;
/*     */ import sim.util.media.chart.SeriesAttributes;
/*     */ import sim.util.media.chart.XYChartGenerator;
/*     */ 
/*     */ public class BubbleChartChartingPropertyInspector extends ChartingPropertyInspector
/*     */ {
/*  21 */   double[][] previousValues = new double[3][0];
/*     */ 
/*  23 */   protected boolean validChartGenerator(ChartGenerator generator) { return generator instanceof BubbleChartGenerator; } 
/*     */   protected boolean includeAggregationMethodAttributes() {
/*  25 */     return false;
/*     */   }
/*  27 */   public static String name() { return "Make Bubble Chart"; }
/*     */ 
/*     */   public static Class[] types() {
/*  30 */     return new Class[] { new Double2D[0].getClass(), new Int2D[0].getClass(), new Double3D[0].getClass(), new Int3D[0].getClass(), ChartUtilities.ProvidesTripleDoubles.class };
/*     */   }
/*     */ 
/*     */   public BubbleChartChartingPropertyInspector(Properties properties, int index, Frame parent, GUIState simulation)
/*     */   {
/*  39 */     super(properties, index, parent, simulation);
/*  40 */     setupSeriesAttributes(properties, index);
/*     */   }
/*     */ 
/*     */   public BubbleChartChartingPropertyInspector(Properties properties, int index, GUIState simulation, ChartGenerator generator)
/*     */   {
/*  45 */     super(properties, index, simulation, generator);
/*  46 */     setupSeriesAttributes(properties, index);
/*     */   }
/*     */ 
/*     */   private void setupSeriesAttributes(Properties properties, int index)
/*     */   {
/*  52 */     if (isValidInspector())
/*     */     {
/*  54 */       if (getGenerator().getNumSeriesAttributes() == 0)
/*     */       {
/*  57 */         getGenerator().setTitle("" + properties.getName(index) + " of " + properties.getObject());
/*  58 */         ((XYChartGenerator)getGenerator()).setYAxisLabel("Y " + properties.getName(index));
/*  59 */         ((XYChartGenerator)getGenerator()).setXAxisLabel("X " + properties.getName(index));
/*     */       }
/*     */ 
/*  63 */       this.seriesAttributes = ((BubbleChartGenerator)this.generator).addSeries(this.previousValues, properties.getName(index), new SeriesChangeListener()
/*     */       {
/*     */         public void seriesChanged(SeriesChangeEvent event) {
/*  66 */           BubbleChartChartingPropertyInspector.this.getStopper().stop();
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   protected ChartGenerator createNewGenerator() {
/*  73 */     return new BubbleChartGenerator()
/*     */     {
/*     */       public void quit()
/*     */       {
/*  77 */         super.quit();
/*  78 */         Stoppable stopper = BubbleChartChartingPropertyInspector.this.getStopper();
/*  79 */         if (stopper != null) stopper.stop();
/*     */ 
/*  82 */         BubbleChartChartingPropertyInspector.this.getCharts(BubbleChartChartingPropertyInspector.this.simulation).remove(this);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public void updateSeries(double time, double lastTime)
/*     */   {
/*  89 */     Object obj = this.properties.getValue(this.index);
/*  90 */     if (obj == null) return;
/*  91 */     Class cls = obj.getClass();
/*  92 */     double[][] vals = this.previousValues;
/*     */ 
/*  94 */     if (cls.isArray())
/*     */     {
/*  96 */       Class comp = cls.getComponentType();
/*     */ 
/*  98 */       if (comp.equals(Double2D.class))
/*     */       {
/* 100 */         Double2D[] array = (Double2D[])obj;
/* 101 */         vals = new double[3][array.length];
/* 102 */         for (int i = 0; i < array.length; i++) {
/* 103 */           vals[0][i] = array[i].x; vals[1][i] = array[i].y; vals[2][i] = 1.0D;
/*     */         }
/* 105 */       } else if (comp.equals(Int2D.class))
/*     */       {
/* 107 */         Int2D[] array = (Int2D[])obj;
/* 108 */         vals = new double[3][array.length];
/* 109 */         for (int i = 0; i < array.length; i++) {
/* 110 */           vals[0][i] = array[i].x; vals[1][i] = array[i].y; vals[2][i] = 1.0D;
/*     */         }
/* 112 */       } else if (comp.equals(Double3D.class))
/*     */       {
/* 114 */         Double3D[] array = (Double3D[])obj;
/* 115 */         vals = new double[3][array.length];
/* 116 */         for (int i = 0; i < array.length; i++) {
/* 117 */           vals[0][i] = array[i].x; vals[1][i] = array[i].y; vals[2][i] = array[i].z;
/*     */         }
/* 119 */       } else if (comp.equals(Int3D.class))
/*     */       {
/* 121 */         Int3D[] array = (Int3D[])obj;
/* 122 */         vals = new double[3][array.length];
/* 123 */         for (int i = 0; i < array.length; i++) {
/* 124 */           vals[0][i] = array[i].x; vals[1][i] = array[i].y; vals[2][i] = array[i].z;
/*     */         }
/* 126 */       } else if ((obj instanceof ChartUtilities.ProvidesTripleDoubles))
/*     */       {
/* 128 */         double[][] array = ((ChartUtilities.ProvidesTripleDoubles)obj).provide();
/* 129 */         vals = new double[3][array.length];
/* 130 */         for (int i = 0; i < array.length; i++) {
/* 131 */           vals[0][i] = array[0][i]; vals[1][i] = array[1][i]; vals[2][i] = array[2][i];
/*     */         }
/*     */       }
/*     */     }
/* 135 */     boolean same = true;
/* 136 */     if ((this.previousValues != null) && (vals.length == this.previousValues.length))
/*     */     {
/* 138 */       for (int i = 0; i < vals.length; i++)
/* 139 */         if (vals[i] != this.previousValues[i]) {
/* 140 */           same = false; break; } 
/*     */     }
/* 142 */     else same = false;
/*     */ 
/* 144 */     if (same) return;
/*     */ 
/* 147 */     this.previousValues = vals;
/*     */ 
/* 149 */     ((BubbleChartGenerator)this.generator).updateSeries(this.seriesAttributes.getSeriesIndex(), vals);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.inspector.BubbleChartChartingPropertyInspector
 * JD-Core Version:    0.6.2
 */