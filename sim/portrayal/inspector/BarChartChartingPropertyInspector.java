/*    */ package sim.portrayal.inspector;
/*    */ 
/*    */ import java.awt.Frame;
/*    */ import java.util.Collection;
/*    */ import sim.display.GUIState;
/*    */ import sim.engine.Stoppable;
/*    */ import sim.util.Bag;
/*    */ import sim.util.Properties;
/*    */ import sim.util.media.chart.BarChartGenerator;
/*    */ import sim.util.media.chart.ChartGenerator;
/*    */ 
/*    */ public class BarChartChartingPropertyInspector extends PieChartChartingPropertyInspector
/*    */ {
/*    */   protected boolean validChartGenerator(ChartGenerator generator)
/*    */   {
/* 32 */     return generator instanceof BarChartGenerator;
/*    */   }
/* 34 */   public static String name() { return "Make Bar Chart"; }
/*    */ 
/*    */   public static Class[] types() {
/* 37 */     return new Class[] { new Object[0].getClass(), Collection.class };
/*    */   }
/*    */ 
/*    */   public BarChartChartingPropertyInspector(Properties properties, int index, Frame parent, GUIState simulation)
/*    */   {
/* 45 */     super(properties, index, parent, simulation);
/*    */   }
/*    */ 
/*    */   public BarChartChartingPropertyInspector(Properties properties, int index, GUIState simulation, ChartGenerator generator)
/*    */   {
/* 50 */     super(properties, index, simulation, generator);
/*    */   }
/*    */ 
/*    */   protected ChartGenerator createNewGenerator()
/*    */   {
/* 55 */     return new BarChartGenerator()
/*    */     {
/*    */       public void quit()
/*    */       {
/* 59 */         super.quit();
/* 60 */         Stoppable stopper = BarChartChartingPropertyInspector.this.getStopper();
/* 61 */         if (stopper != null) stopper.stop();
/*    */ 
/* 64 */         BarChartChartingPropertyInspector.this.getCharts(BarChartChartingPropertyInspector.this.simulation).remove(this);
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.inspector.BarChartChartingPropertyInspector
 * JD-Core Version:    0.6.2
 */