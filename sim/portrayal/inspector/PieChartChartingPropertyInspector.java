/*     */ package sim.portrayal.inspector;
/*     */ 
/*     */ import java.awt.Frame;
/*     */ import java.util.Collection;
/*     */ import org.jfree.data.general.SeriesChangeEvent;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import sim.display.ChartUtilities.ProvidesCollection;
/*     */ import sim.display.ChartUtilities.ProvidesDoublesAndLabels;
/*     */ import sim.display.ChartUtilities.ProvidesObjects;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Properties;
/*     */ import sim.util.media.chart.BarChartGenerator;
/*     */ import sim.util.media.chart.ChartGenerator;
/*     */ import sim.util.media.chart.PieChartGenerator;
/*     */ import sim.util.media.chart.SeriesAttributes;
/*     */ 
/*     */ public class PieChartChartingPropertyInspector extends ChartingPropertyInspector
/*     */ {
/*  32 */   Object[] previousValues = new Object[0];
/*     */ 
/*     */   protected boolean validChartGenerator(ChartGenerator generator)
/*     */   {
/*  39 */     return ((generator instanceof PieChartGenerator)) && (!(generator instanceof BarChartGenerator));
/*     */   }
/*     */   protected boolean includeAggregationMethodAttributes() {
/*  42 */     return false;
/*     */   }
/*  44 */   public static String name() { return "Make Pie Chart"; }
/*     */ 
/*     */   public static Class[] types() {
/*  47 */     return new Class[] { new Object[0].getClass(), Collection.class, ChartUtilities.ProvidesDoublesAndLabels.class, ChartUtilities.ProvidesObjects.class, ChartUtilities.ProvidesCollection.class };
/*     */   }
/*     */ 
/*     */   public PieChartChartingPropertyInspector(Properties properties, int index, Frame parent, GUIState simulation)
/*     */   {
/*  58 */     super(properties, index, parent, simulation);
/*  59 */     setupSeriesAttributes(properties, index);
/*     */   }
/*     */ 
/*     */   public PieChartChartingPropertyInspector(Properties properties, int index, GUIState simulation, ChartGenerator generator)
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
/*     */       }
/*     */ 
/*  80 */       this.seriesAttributes = ((PieChartGenerator)this.generator).addSeries(this.previousValues, properties.getName(index), new SeriesChangeListener()
/*     */       {
/*     */         public void seriesChanged(SeriesChangeEvent event) {
/*  83 */           PieChartChartingPropertyInspector.this.getStopper().stop();
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   protected ChartGenerator createNewGenerator() {
/*  90 */     return new PieChartGenerator()
/*     */     {
/*     */       public void quit()
/*     */       {
/*  94 */         super.quit();
/*  95 */         Stoppable stopper = PieChartChartingPropertyInspector.this.getStopper();
/*  96 */         if (stopper != null) stopper.stop();
/*     */ 
/*  99 */         PieChartChartingPropertyInspector.this.getCharts(PieChartChartingPropertyInspector.this.simulation).remove(this);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public void updateSeries(double time, double lastTime)
/*     */   {
/* 106 */     Object obj = this.properties.getValue(this.index);
/* 107 */     if (obj == null) return;
/* 108 */     Class cls = obj.getClass();
/* 109 */     Object[] vals = this.previousValues;
/*     */ 
/* 111 */     if (cls.isArray())
/*     */     {
/* 113 */       Class comp = cls.getComponentType();
/* 114 */       if (Object.class.isAssignableFrom(comp))
/*     */       {
/* 116 */         Object[] array = (Object[])obj;
/* 117 */         vals = new Object[array.length];
/* 118 */         for (int i = 0; i < array.length; i++)
/* 119 */           vals[i] = array[i];
/*     */       }
/*     */     }
/* 122 */     else if (Collection.class.isAssignableFrom(cls))
/*     */     {
/* 124 */       Object[] array = ((Collection)obj).toArray();
/* 125 */       vals = new Object[array.length];
/* 126 */       for (int i = 0; i < array.length; i++)
/* 127 */         vals[i] = array[i];
/*     */     }
/* 129 */     else if ((obj instanceof ChartUtilities.ProvidesObjects))
/*     */     {
/* 131 */       Object[] array = ((ChartUtilities.ProvidesObjects)obj).provide();
/* 132 */       vals = new Object[array.length];
/* 133 */       for (int i = 0; i < array.length; i++)
/* 134 */         vals[i] = array[i];
/*     */     }
/* 136 */     else if ((obj instanceof ChartUtilities.ProvidesCollection))
/*     */     {
/* 138 */       Object[] array = ((ChartUtilities.ProvidesCollection)obj).provide().toArray();
/* 139 */       vals = new Object[array.length];
/* 140 */       for (int i = 0; i < array.length; i++)
/* 141 */         vals[i] = array[i];
/*     */     }
/* 143 */     else if ((obj instanceof ChartUtilities.ProvidesDoublesAndLabels))
/*     */     {
/* 145 */       double[] array = ((ChartUtilities.ProvidesDoublesAndLabels)obj).provide();
/* 146 */       String[] labels = ((ChartUtilities.ProvidesDoublesAndLabels)obj).provideLabels();
/* 147 */       this.previousValues = null;
/* 148 */       ((PieChartGenerator)this.generator).updateSeries(this.seriesAttributes.getSeriesIndex(), array, labels);
/* 149 */       return;
/*     */     }
/*     */ 
/* 152 */     boolean same = true;
/* 153 */     if ((this.previousValues != null) && (vals.length == this.previousValues.length))
/*     */     {
/* 155 */       for (int i = 0; i < vals.length; i++)
/* 156 */         if (vals[i] != this.previousValues[i]) {
/* 157 */           same = false; break; } 
/*     */     }
/* 159 */     else same = false;
/*     */ 
/* 161 */     if (same) return;
/*     */ 
/* 164 */     this.previousValues = vals;
/*     */ 
/* 166 */     ((PieChartGenerator)this.generator).updateSeries(this.seriesAttributes.getSeriesIndex(), vals);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.inspector.PieChartChartingPropertyInspector
 * JD-Core Version:    0.6.2
 */