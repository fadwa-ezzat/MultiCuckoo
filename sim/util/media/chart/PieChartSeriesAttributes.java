/*    */ package sim.util.media.chart;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import org.jfree.data.general.SeriesChangeListener;
/*    */ 
/*    */ public class PieChartSeriesAttributes extends SeriesAttributes
/*    */ {
/* 34 */   Object[] elements = null;
/* 35 */   Collection elements2 = null;
/*    */   double[] values;
/*    */   String[] labels;
/*    */ 
/*    */   Object[] getElements()
/*    */   {
/* 39 */     if (this.elements != null) return this.elements;
/* 40 */     return this.elements2.toArray();
/*    */   }
/*    */   public void setElements(Object[] elts) {
/* 43 */     if (elts != null) elts = (Object[])elts.clone(); this.elements = elts; this.elements2 = null; this.values = null; this.labels = null; } 
/* 44 */   public void setElements(Collection elts) { if (elts != null) elts = new ArrayList(elts); this.elements2 = elts; this.elements = null; this.values = null; this.labels = null; }
/*    */ 
/*    */   public double[] getValues() {
/* 47 */     return this.values; } 
/* 48 */   public void setValues(double[] vals) { if (vals != null) vals = (double[])vals.clone(); this.values = vals; this.elements = null; this.elements2 = null; }
/*    */ 
/*    */   public String[] getLabels() {
/* 51 */     return this.labels; } 
/* 52 */   public void setLabels(String[] labs) { if (labs != null) labs = (String[])labs.clone(); this.labels = labs; }
/*    */ 
/*    */   public PieChartSeriesAttributes(ChartGenerator generator, String name, int index, SeriesChangeListener stoppable)
/*    */   {
/* 56 */     super(generator, name, index, stoppable);
/* 57 */     super.setSeriesName(name);
/*    */   }
/*    */ 
/*    */   public void setSeriesName(String val)
/*    */   {
/* 64 */     super.setSeriesName(val);
/* 65 */     ((PieChartGenerator)this.generator).update();
/*    */   }
/*    */ 
/*    */   public void rebuildGraphicsDefinitions()
/*    */   {
/* 70 */     repaint();
/*    */   }
/*    */ 
/*    */   public void buildAttributes()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setPlotVisible(boolean val)
/*    */   {
/* 80 */     this.plotVisible = val;
/* 81 */     this.generator.update();
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.PieChartSeriesAttributes
 * JD-Core Version:    0.6.2
 */