/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.XYBubbleRenderer;
/*     */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import sim.util.gui.ColorWell;
/*     */ import sim.util.gui.NumberTextField;
/*     */ 
/*     */ public class BubbleChartSeriesAttributes extends SeriesAttributes
/*     */ {
/*     */   double[][] values;
/*     */   Color color;
/*     */   ColorWell colorWell;
/*     */   double opacity;
/*     */   NumberTextField opacityField;
/*     */   double scale;
/*     */   NumberTextField scaleField;
/*     */ 
/*     */   public double[][] getValues()
/*     */   {
/*  24 */     return this.values;
/*     */   }
/*     */   public void setValues(double[][] vals) {
/*  27 */     if (vals != null)
/*     */     {
/*  29 */       vals = (double[][])vals.clone();
/*  30 */       for (int i = 0; i < vals.length; i++)
/*  31 */         vals[i] = ((double[])(double[])vals[i].clone());
/*     */     }
/*  33 */     this.values = vals;
/*     */   }
/*     */ 
/*     */   public void setOpacity(double value)
/*     */   {
/*  43 */     this.opacityField.setValue(this.opacityField.newValue(value)); } 
/*  44 */   public double getOpacity() { return this.opacityField.getValue(); } 
/*     */   public void setColor(Color value) {
/*  46 */     this.colorWell.setColor(this.color = value); } 
/*  47 */   public Color getColor() { return this.color; } 
/*     */   public void setScale(double scale) {
/*  49 */     this.scale = scale; } 
/*  50 */   public double getScale() { return this.scale; }
/*     */ 
/*     */ 
/*     */   public BubbleChartSeriesAttributes(ChartGenerator generator, String name, int index, double[][] values, SeriesChangeListener stoppable)
/*     */   {
/*  56 */     super(generator, name, index, stoppable);
/*     */ 
/*  58 */     setValues(values);
/*  59 */     super.setSeriesName(name);
/*     */   }
/*     */ 
/*     */   public void setSeriesName(String val)
/*     */   {
/*  64 */     super.setSeriesName(val);
/*  65 */     ((BubbleChartGenerator)this.generator).update();
/*     */   }
/*     */ 
/*     */   public void rebuildGraphicsDefinitions()
/*     */   {
/*  70 */     XYBubbleRenderer renderer = (XYBubbleRenderer)((XYPlot)getPlot()).getRenderer();
/*  71 */     renderer.setSeriesPaint(getSeriesIndex(), reviseColor(this.color, this.opacity));
/*  72 */     repaint();
/*     */   }
/*     */ 
/*     */   public void buildAttributes()
/*     */   {
/*  79 */     this.opacity = 0.5D;
/*     */ 
/*  81 */     this.scale = 1.0D;
/*     */ 
/*  90 */     this.color = ((Color)((XYPlot)getPlot()).getRenderer().getItemPaint(getSeriesIndex(), -1));
/*     */ 
/*  92 */     this.colorWell = new ColorWell(this.color)
/*     */     {
/*     */       public Color changeColor(Color c)
/*     */       {
/*  96 */         BubbleChartSeriesAttributes.this.color = c;
/*  97 */         BubbleChartSeriesAttributes.this.rebuildGraphicsDefinitions();
/*  98 */         return c;
/*     */       }
/*     */     };
/* 102 */     addLabelled("Color", this.colorWell);
/*     */ 
/* 104 */     this.opacityField = new NumberTextField("Opacity ", this.opacity, 1.0D, 0.125D)
/*     */     {
/*     */       public double newValue(double newValue)
/*     */       {
/* 108 */         if ((newValue < 0.0D) || (newValue > 1.0D))
/* 109 */           newValue = this.currentValue;
/* 110 */         BubbleChartSeriesAttributes.this.opacity = ((float)newValue);
/* 111 */         BubbleChartSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 112 */         return newValue;
/*     */       }
/*     */     };
/* 115 */     addLabelled("", this.opacityField);
/*     */ 
/* 117 */     this.scaleField = new NumberTextField("", this.scale, 2.0D, 0.0D)
/*     */     {
/*     */       public double newValue(double newValue)
/*     */       {
/* 121 */         if (newValue <= 0.0D)
/* 122 */           newValue = this.currentValue;
/* 123 */         BubbleChartSeriesAttributes.this.scale = newValue;
/* 124 */         BubbleChartSeriesAttributes.this.generator.update();
/* 125 */         BubbleChartSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 126 */         return newValue;
/*     */       }
/*     */     };
/* 129 */     addLabelled("Scale", this.scaleField);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.BubbleChartSeriesAttributes
 * JD-Core Version:    0.6.2
 */