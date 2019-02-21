/*     */ package sim.portrayal.inspector;
/*     */ 
/*     */ import java.awt.Frame;
/*     */ import org.jfree.data.general.SeriesChangeEvent;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import sim.display.ChartUtilities.ProvidesDoubleDoublesAndLabels;
/*     */ import sim.display.ChartUtilities.ProvidesDoubles;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.util.Bag;
/*     */ import sim.util.DoubleBag;
/*     */ import sim.util.IntBag;
/*     */ import sim.util.Properties;
/*     */ import sim.util.Valuable;
/*     */ import sim.util.media.chart.BoxPlotGenerator;
/*     */ import sim.util.media.chart.ChartGenerator;
/*     */ import sim.util.media.chart.SeriesAttributes;
/*     */ 
/*     */ public class BoxPlotChartingPropertyInspector extends ChartingPropertyInspector
/*     */ {
/*  36 */   double[] previousValues = { 0.0D };
/*     */ 
/*  38 */   protected boolean validChartGenerator(ChartGenerator generator) { return generator instanceof BoxPlotGenerator; } 
/*     */   protected boolean includeAggregationMethodAttributes() {
/*  40 */     return false;
/*     */   }
/*  42 */   public static String name() { return "Make BoxPlot"; }
/*     */ 
/*     */   public static Class[] types() {
/*  45 */     return new Class[] { new byte[0].getClass(), new short[0].getClass(), new int[0].getClass(), new long[0].getClass(), new float[0].getClass(), new double[0].getClass(), new boolean[0].getClass(), new Valuable[0].getClass(), new Number[0].getClass(), IntBag.class, DoubleBag.class, ChartUtilities.ProvidesDoubles.class, ChartUtilities.ProvidesDoubleDoublesAndLabels.class };
/*     */   }
/*     */ 
/*     */   public BoxPlotChartingPropertyInspector(Properties properties, int index, Frame parent, GUIState simulation)
/*     */   {
/*  57 */     super(properties, index, parent, simulation);
/*  58 */     setupSeriesAttributes(properties, index);
/*     */   }
/*     */ 
/*     */   public BoxPlotChartingPropertyInspector(Properties properties, int index, GUIState simulation, ChartGenerator generator)
/*     */   {
/*  63 */     super(properties, index, simulation, generator);
/*  64 */     setupSeriesAttributes(properties, index);
/*     */   }
/*     */ 
/*     */   private void setupSeriesAttributes(Properties properties, int index)
/*     */   {
/*  70 */     if (isValidInspector())
/*     */     {
/*  72 */       if (getGenerator().getNumSeriesAttributes() == 0)
/*     */       {
/*  75 */         getGenerator().setTitle("" + properties.getName(index) + " of " + properties.getObject());
/*  76 */         ((BoxPlotGenerator)getGenerator()).setYAxisLabel("Frequency");
/*     */       }
/*     */ 
/*  79 */       if ((properties.getValue(index) instanceof ChartUtilities.ProvidesDoubleDoublesAndLabels))
/*     */       {
/*  82 */         String[] labels = ((ChartUtilities.ProvidesDoubleDoublesAndLabels)properties.getValue(index)).provideLabels();
/*  83 */         this.seriesAttributes = ((BoxPlotGenerator)this.generator).addSeries(new double[labels.length][0], labels, properties.getName(index), new SeriesChangeListener()
/*     */         {
/*     */           public void seriesChanged(SeriesChangeEvent event) {
/*  86 */             BoxPlotChartingPropertyInspector.this.getStopper().stop();
/*     */           }
/*     */         });
/*     */       }
/*     */       else {
/*  91 */         this.seriesAttributes = ((BoxPlotGenerator)this.generator).addSeries(this.previousValues, properties.getName(index), new SeriesChangeListener()
/*     */         {
/*     */           public void seriesChanged(SeriesChangeEvent event) {
/*  94 */             BoxPlotChartingPropertyInspector.this.getStopper().stop();
/*     */           }
/*     */         });
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected ChartGenerator createNewGenerator() {
/* 102 */     return new BoxPlotGenerator()
/*     */     {
/*     */       public void quit()
/*     */       {
/* 106 */         super.quit();
/* 107 */         Stoppable stopper = BoxPlotChartingPropertyInspector.this.getStopper();
/* 108 */         if (stopper != null) stopper.stop();
/*     */ 
/* 111 */         BoxPlotChartingPropertyInspector.this.getCharts(BoxPlotChartingPropertyInspector.this.simulation).remove(this);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public void updateSeries(double time, double lastTime)
/*     */   {
/* 118 */     Object obj = this.properties.getValue(this.index);
/* 119 */     if (obj == null) return;
/* 120 */     Class cls = obj.getClass();
/* 121 */     double[] vals = this.previousValues;
/*     */ 
/* 123 */     if (cls.isArray())
/*     */     {
/* 125 */       Class comp = cls.getComponentType();
/*     */ 
/* 127 */       if (comp.equals(Byte.TYPE))
/*     */       {
/* 129 */         byte[] array = (byte[])obj;
/* 130 */         vals = new double[array.length];
/* 131 */         for (int i = 0; i < array.length; i++)
/* 132 */           vals[i] = array[i];
/*     */       }
/* 134 */       else if (comp.equals(Short.TYPE))
/*     */       {
/* 136 */         short[] array = (short[])obj;
/* 137 */         vals = new double[array.length];
/* 138 */         for (int i = 0; i < array.length; i++)
/* 139 */           vals[i] = array[i];
/*     */       }
/* 141 */       else if (comp.equals(Integer.TYPE))
/*     */       {
/* 143 */         int[] array = (int[])obj;
/* 144 */         vals = new double[array.length];
/* 145 */         for (int i = 0; i < array.length; i++)
/* 146 */           vals[i] = array[i];
/*     */       }
/* 148 */       else if (comp.equals(Long.TYPE))
/*     */       {
/* 150 */         long[] array = (long[])obj;
/* 151 */         vals = new double[array.length];
/* 152 */         for (int i = 0; i < array.length; i++)
/* 153 */           vals[i] = array[i];
/*     */       }
/* 155 */       else if (comp.equals(Float.TYPE))
/*     */       {
/* 157 */         float[] array = (float[])obj;
/* 158 */         vals = new double[array.length];
/* 159 */         for (int i = 0; i < array.length; i++)
/* 160 */           vals[i] = array[i];
/*     */       }
/* 162 */       else if (comp.equals(Double.TYPE))
/*     */       {
/* 165 */         double[] array = (double[])obj;
/* 166 */         vals = new double[array.length];
/* 167 */         for (int i = 0; i < array.length; i++)
/* 168 */           vals[i] = array[i];
/*     */       }
/* 170 */       else if (comp.equals(Boolean.TYPE))
/*     */       {
/* 172 */         boolean[] array = (boolean[])obj;
/* 173 */         vals = new double[array.length];
/* 174 */         for (int i = 0; i < array.length; i++)
/* 175 */           vals[i] = (array[i] != 0 ? 1 : 0);
/*     */       }
/* 177 */       else if (comp.equals(Valuable.class))
/*     */       {
/* 179 */         Valuable[] array = (Valuable[])obj;
/* 180 */         vals = new double[array.length];
/* 181 */         for (int i = 0; i < array.length; i++)
/* 182 */           vals[i] = array[i].doubleValue();
/*     */       }
/* 184 */       else if (comp.equals(Number.class))
/*     */       {
/* 186 */         Number[] array = (Number[])obj;
/* 187 */         vals = new double[array.length];
/* 188 */         for (int i = 0; i < array.length; i++)
/* 189 */           vals[i] = array[i].doubleValue();
/*     */       }
/*     */     }
/* 192 */     else if ((obj instanceof IntBag))
/*     */     {
/* 194 */       IntBag bag = (IntBag)obj;
/* 195 */       vals = new double[bag.numObjs];
/* 196 */       for (int i = 0; i < bag.numObjs; i++)
/* 197 */         vals[i] = bag.objs[i];
/*     */     }
/* 199 */     else if ((obj instanceof DoubleBag))
/*     */     {
/* 201 */       DoubleBag bag = (DoubleBag)obj;
/* 202 */       vals = new double[bag.numObjs];
/* 203 */       for (int i = 0; i < bag.numObjs; i++)
/* 204 */         vals[i] = bag.objs[i];
/*     */     }
/* 206 */     else if ((obj instanceof ChartUtilities.ProvidesDoubles))
/*     */     {
/* 208 */       double[] array = ((ChartUtilities.ProvidesDoubles)obj).provide();
/* 209 */       vals = new double[array.length];
/* 210 */       for (int i = 0; i < array.length; i++)
/* 211 */         vals[i] = array[i];
/*     */     }
/* 213 */     else if ((obj instanceof ChartUtilities.ProvidesDoubleDoublesAndLabels))
/*     */     {
/* 216 */       ChartUtilities.ProvidesDoubleDoublesAndLabels o = (ChartUtilities.ProvidesDoubleDoublesAndLabels)obj;
/* 217 */       this.previousValues = null;
/* 218 */       ((BoxPlotGenerator)this.generator).updateSeries(this.seriesAttributes.getSeriesIndex(), o.provide(), o.provideLabels());
/* 219 */       return;
/*     */     }
/*     */ 
/* 222 */     boolean same = true;
/* 223 */     if ((this.previousValues != null) && (vals.length == this.previousValues.length))
/*     */     {
/* 225 */       for (int i = 0; i < vals.length; i++)
/* 226 */         if (vals[i] != this.previousValues[i]) {
/* 227 */           same = false; break; } 
/*     */     }
/* 229 */     else same = false;
/*     */ 
/* 231 */     if (same) return;
/*     */ 
/* 234 */     this.previousValues = vals;
/*     */ 
/* 236 */     ((BoxPlotGenerator)this.generator).updateSeries(this.seriesAttributes.getSeriesIndex(), vals);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.inspector.BoxPlotChartingPropertyInspector
 * JD-Core Version:    0.6.2
 */