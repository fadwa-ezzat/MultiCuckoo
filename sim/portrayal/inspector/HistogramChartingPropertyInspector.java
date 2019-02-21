/*     */ package sim.portrayal.inspector;
/*     */ 
/*     */ import java.awt.Frame;
/*     */ import org.jfree.data.general.SeriesChangeEvent;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import sim.display.ChartUtilities.ProvidesDoubles;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.util.Bag;
/*     */ import sim.util.DoubleBag;
/*     */ import sim.util.IntBag;
/*     */ import sim.util.Properties;
/*     */ import sim.util.Valuable;
/*     */ import sim.util.media.chart.ChartGenerator;
/*     */ import sim.util.media.chart.HistogramGenerator;
/*     */ import sim.util.media.chart.SeriesAttributes;
/*     */ import sim.util.media.chart.XYChartGenerator;
/*     */ 
/*     */ public class HistogramChartingPropertyInspector extends ChartingPropertyInspector
/*     */ {
/*     */   public static final int DEFAULT_BINS = 8;
/*  38 */   double[] previousValues = { 0.0D };
/*     */ 
/*  40 */   protected boolean validChartGenerator(ChartGenerator generator) { return generator instanceof HistogramGenerator; } 
/*     */   protected boolean includeAggregationMethodAttributes() {
/*  42 */     return false;
/*     */   }
/*  44 */   public static String name() { return "Make Histogram"; }
/*     */ 
/*     */   public static Class[] types() {
/*  47 */     return new Class[] { new byte[0].getClass(), new short[0].getClass(), new int[0].getClass(), new long[0].getClass(), new float[0].getClass(), new double[0].getClass(), new boolean[0].getClass(), new Valuable[0].getClass(), new Number[0].getClass(), IntBag.class, DoubleBag.class, ChartUtilities.ProvidesDoubles.class };
/*     */   }
/*     */ 
/*     */   public HistogramChartingPropertyInspector(Properties properties, int index, Frame parent, GUIState simulation)
/*     */   {
/*  58 */     super(properties, index, parent, simulation);
/*  59 */     setupSeriesAttributes(properties, index);
/*     */   }
/*     */ 
/*     */   public HistogramChartingPropertyInspector(Properties properties, int index, GUIState simulation, ChartGenerator generator)
/*     */   {
/*  64 */     super(properties, index, simulation, generator);
/*  65 */     setupSeriesAttributes(properties, index);
/*     */   }
/*     */ 
/*     */   private void setupSeriesAttributes(Properties properties, int index)
/*     */   {
/*  71 */     if (isValidInspector())
/*     */     {
/*  73 */       if (getGenerator().getNumSeriesAttributes() == 0)
/*     */       {
/*  76 */         getGenerator().setTitle("" + properties.getName(index) + " of " + properties.getObject());
/*  77 */         ((XYChartGenerator)getGenerator()).setYAxisLabel("Frequency");
/*  78 */         ((XYChartGenerator)getGenerator()).setXAxisLabel(properties.getName(index));
/*     */       }
/*     */ 
/*  82 */       this.seriesAttributes = ((HistogramGenerator)this.generator).addSeries(this.previousValues, 8, properties.getName(index), new SeriesChangeListener()
/*     */       {
/*     */         public void seriesChanged(SeriesChangeEvent event) {
/*  85 */           HistogramChartingPropertyInspector.this.getStopper().stop();
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   protected ChartGenerator createNewGenerator() {
/*  92 */     return new HistogramGenerator()
/*     */     {
/*     */       public void quit()
/*     */       {
/*  96 */         super.quit();
/*  97 */         Stoppable stopper = HistogramChartingPropertyInspector.this.getStopper();
/*  98 */         if (stopper != null) stopper.stop();
/*     */ 
/* 101 */         HistogramChartingPropertyInspector.this.getCharts(HistogramChartingPropertyInspector.this.simulation).remove(this);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public void updateSeries(double time, double lastTime)
/*     */   {
/* 108 */     Object obj = this.properties.getValue(this.index);
/* 109 */     if (obj == null) return;
/* 110 */     Class cls = obj.getClass();
/* 111 */     double[] vals = this.previousValues;
/*     */ 
/* 113 */     if (cls.isArray())
/*     */     {
/* 115 */       Class comp = cls.getComponentType();
/*     */ 
/* 117 */       if (comp.equals(Byte.TYPE))
/*     */       {
/* 119 */         byte[] array = (byte[])obj;
/* 120 */         vals = new double[array.length];
/* 121 */         for (int i = 0; i < array.length; i++)
/* 122 */           vals[i] = array[i];
/*     */       }
/* 124 */       else if (comp.equals(Short.TYPE))
/*     */       {
/* 126 */         short[] array = (short[])obj;
/* 127 */         vals = new double[array.length];
/* 128 */         for (int i = 0; i < array.length; i++)
/* 129 */           vals[i] = array[i];
/*     */       }
/* 131 */       else if (comp.equals(Integer.TYPE))
/*     */       {
/* 133 */         int[] array = (int[])obj;
/* 134 */         vals = new double[array.length];
/* 135 */         for (int i = 0; i < array.length; i++)
/* 136 */           vals[i] = array[i];
/*     */       }
/* 138 */       else if (comp.equals(Long.TYPE))
/*     */       {
/* 140 */         long[] array = (long[])obj;
/* 141 */         vals = new double[array.length];
/* 142 */         for (int i = 0; i < array.length; i++)
/* 143 */           vals[i] = array[i];
/*     */       }
/* 145 */       else if (comp.equals(Float.TYPE))
/*     */       {
/* 147 */         float[] array = (float[])obj;
/* 148 */         vals = new double[array.length];
/* 149 */         for (int i = 0; i < array.length; i++)
/* 150 */           vals[i] = array[i];
/*     */       }
/* 152 */       else if (comp.equals(Double.TYPE))
/*     */       {
/* 155 */         double[] array = (double[])obj;
/* 156 */         vals = new double[array.length];
/* 157 */         for (int i = 0; i < array.length; i++)
/* 158 */           vals[i] = array[i];
/*     */       }
/* 160 */       else if (comp.equals(Boolean.TYPE))
/*     */       {
/* 162 */         boolean[] array = (boolean[])obj;
/* 163 */         vals = new double[array.length];
/* 164 */         for (int i = 0; i < array.length; i++)
/* 165 */           vals[i] = (array[i] != 0 ? 1 : 0);
/*     */       }
/* 167 */       else if (comp.equals(Valuable.class))
/*     */       {
/* 169 */         Valuable[] array = (Valuable[])obj;
/* 170 */         vals = new double[array.length];
/* 171 */         for (int i = 0; i < array.length; i++)
/* 172 */           vals[i] = array[i].doubleValue();
/*     */       }
/* 174 */       else if (comp.equals(Number.class))
/*     */       {
/* 176 */         Number[] array = (Number[])obj;
/* 177 */         vals = new double[array.length];
/* 178 */         for (int i = 0; i < array.length; i++)
/* 179 */           vals[i] = array[i].doubleValue();
/*     */       }
/*     */     }
/* 182 */     else if ((obj instanceof IntBag))
/*     */     {
/* 184 */       IntBag bag = (IntBag)obj;
/* 185 */       vals = new double[bag.numObjs];
/* 186 */       for (int i = 0; i < bag.numObjs; i++)
/* 187 */         vals[i] = bag.objs[i];
/*     */     }
/* 189 */     else if ((obj instanceof DoubleBag))
/*     */     {
/* 191 */       DoubleBag bag = (DoubleBag)obj;
/* 192 */       vals = new double[bag.numObjs];
/* 193 */       for (int i = 0; i < bag.numObjs; i++)
/* 194 */         vals[i] = bag.objs[i];
/*     */     }
/* 196 */     else if ((obj instanceof ChartUtilities.ProvidesDoubles))
/*     */     {
/* 198 */       double[] array = ((ChartUtilities.ProvidesDoubles)obj).provide();
/* 199 */       vals = new double[array.length];
/* 200 */       for (int i = 0; i < array.length; i++) {
/* 201 */         vals[i] = array[i];
/*     */       }
/*     */     }
/* 204 */     boolean same = true;
/* 205 */     if ((this.previousValues != null) && (vals.length == this.previousValues.length))
/*     */     {
/* 207 */       for (int i = 0; i < vals.length; i++)
/* 208 */         if (vals[i] != this.previousValues[i]) {
/* 209 */           same = false; break; } 
/*     */     }
/* 211 */     else same = false;
/*     */ 
/* 213 */     if (same) return;
/*     */ 
/* 216 */     this.previousValues = vals;
/*     */ 
/* 218 */     ((HistogramGenerator)this.generator).updateSeries(this.seriesAttributes.getSeriesIndex(), vals);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.inspector.HistogramChartingPropertyInspector
 * JD-Core Version:    0.6.2
 */