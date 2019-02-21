/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.XYBarRenderer;
/*     */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import sim.util.gui.ColorWell;
/*     */ import sim.util.gui.NumberTextField;
/*     */ 
/*     */ public class HistogramSeriesAttributes extends SeriesAttributes
/*     */ {
/*     */   double[] values;
/*     */   float thickness;
/*     */   NumberTextField thicknessField;
/*     */   Color fillColor;
/*     */   ColorWell fillColorWell;
/*     */   Color strokeColor;
/*     */   ColorWell strokeColorWell;
/*     */   double fillOpacity;
/*     */   NumberTextField fillOpacityField;
/*     */   double lineOpacity;
/*     */   NumberTextField lineOpacityField;
/*     */   NumberTextField numBinsField;
/*     */   int numBins;
/*     */   static final int DEFAULT_BINS = 8;
/*     */ 
/*     */   public double[] getValues()
/*     */   {
/*  35 */     return this.values;
/*     */   }
/*  37 */   public void setValues(double[] vals) { if (vals != null) vals = (double[])vals.clone(); this.values = vals;
/*     */   }
/*     */ 
/*     */   public void setFillOpacity(double value)
/*     */   {
/*  59 */     this.fillOpacityField.setValue(this.fillOpacityField.newValue(value)); } 
/*  60 */   public double getFillOpacity() { return this.fillOpacityField.getValue(); } 
/*     */   public void setStrokeOpacity(double value) {
/*  62 */     this.lineOpacityField.setValue(this.lineOpacityField.newValue(value)); } 
/*  63 */   public double getStrokeOpacity() { return this.lineOpacityField.getValue(); } 
/*     */   public void setThickness(double value) {
/*  65 */     this.thicknessField.setValue(this.thicknessField.newValue(value)); } 
/*  66 */   public double getThickness() { return this.thicknessField.getValue(); }
/*     */ 
/*     */   public void setNumBins(int value)
/*     */   {
/*  70 */     this.numBinsField.setValue(this.numBinsField.newValue(value)); this.numBins = ((int)this.numBinsField.getValue()); } 
/*  71 */   public int getNumBins() { return this.numBins; } 
/*     */   public void setFillColor(Color value) {
/*  73 */     this.fillColorWell.setColor(this.fillColor = value); } 
/*  74 */   public Color getFillColor() { return this.fillColor; } 
/*     */   public void setStrokeColor(Color value) {
/*  76 */     this.strokeColorWell.setColor(this.strokeColor = value); } 
/*  77 */   public Color getStrokeColor() { return this.strokeColor; }
/*     */ 
/*     */ 
/*     */   public HistogramSeriesAttributes(ChartGenerator generator, String name, int index, double[] values, int bins, SeriesChangeListener stoppable)
/*     */   {
/*  83 */     super(generator, name, index, stoppable);
/*  84 */     setValues(values);
/*  85 */     super.setSeriesName(name);
/*  86 */     this.numBins = bins;
/*  87 */     this.numBinsField.setValue(bins);
/*  88 */     this.numBinsField.setInitialValue(bins);
/*     */   }
/*     */ 
/*     */   public void setSeriesName(String val)
/*     */   {
/*  95 */     super.setSeriesName(val);
/*  96 */     ((HistogramGenerator)this.generator).update();
/*     */   }
/*     */ 
/*     */   public void rebuildGraphicsDefinitions()
/*     */   {
/* 101 */     XYBarRenderer renderer = (XYBarRenderer)((XYPlot)getPlot()).getRenderer();
/*     */ 
/* 103 */     if (this.thickness == 0.0D) {
/* 104 */       renderer.setDrawBarOutline(false);
/*     */     }
/*     */     else {
/* 107 */       renderer.setSeriesOutlineStroke(getSeriesIndex(), new BasicStroke(this.thickness));
/*     */ 
/* 109 */       renderer.setDrawBarOutline(true);
/*     */     }
/*     */ 
/* 112 */     renderer.setSeriesPaint(getSeriesIndex(), reviseColor(this.fillColor, this.fillOpacity));
/* 113 */     renderer.setSeriesOutlinePaint(getSeriesIndex(), reviseColor(this.strokeColor, this.lineOpacity));
/* 114 */     repaint();
/*     */   }
/*     */ 
/*     */   public void buildAttributes()
/*     */   {
/* 122 */     this.thickness = 2.0F;
/* 123 */     this.fillOpacity = 1.0D;
/* 124 */     this.lineOpacity = 1.0D;
/*     */ 
/* 126 */     this.numBinsField = new NumberTextField("", 8.0D, true)
/*     */     {
/*     */       public double newValue(double newValue)
/*     */       {
/* 130 */         newValue = (int)newValue;
/* 131 */         if (newValue < 1.0D) {
/* 132 */           newValue = this.currentValue;
/*     */         }
/*     */ 
/* 135 */         HistogramSeriesAttributes.this.numBins = ((int)newValue);
/* 136 */         ((HistogramGenerator)HistogramSeriesAttributes.this.generator).update();
/* 137 */         return newValue;
/*     */       }
/*     */     };
/* 140 */     addLabelled("Bins", this.numBinsField);
/*     */ 
/* 153 */     this.fillColor = ((Color)((XYPlot)getPlot()).getRenderer().getItemPaint(getSeriesIndex(), -1));
/*     */ 
/* 156 */     this.fillColor = ((Color)((XYPlot)getPlot()).getRenderer().getSeriesPaint(getSeriesIndex()));
/* 157 */     this.fillColorWell = new ColorWell(this.fillColor)
/*     */     {
/*     */       public Color changeColor(Color c)
/*     */       {
/* 161 */         HistogramSeriesAttributes.this.fillColor = c;
/* 162 */         HistogramSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 163 */         return c;
/*     */       }
/*     */     };
/* 167 */     addLabelled("Fill", this.fillColorWell);
/*     */ 
/* 169 */     this.fillOpacityField = new NumberTextField("Opacity ", this.fillOpacity, 1.0D, 0.125D)
/*     */     {
/*     */       public double newValue(double newValue)
/*     */       {
/* 173 */         if ((newValue < 0.0D) || (newValue > 1.0D))
/* 174 */           newValue = this.currentValue;
/* 175 */         HistogramSeriesAttributes.this.fillOpacity = ((float)newValue);
/* 176 */         HistogramSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 177 */         return newValue;
/*     */       }
/*     */     };
/* 180 */     addLabelled("", this.fillOpacityField);
/*     */ 
/* 182 */     this.strokeColor = Color.black;
/* 183 */     this.strokeColorWell = new ColorWell(this.strokeColor)
/*     */     {
/*     */       public Color changeColor(Color c)
/*     */       {
/* 187 */         HistogramSeriesAttributes.this.strokeColor = c;
/* 188 */         HistogramSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 189 */         return c;
/*     */       }
/*     */     };
/* 193 */     addLabelled("Line", this.strokeColorWell);
/*     */ 
/* 195 */     this.lineOpacityField = new NumberTextField("Opacity ", this.lineOpacity, 1.0D, 0.125D)
/*     */     {
/*     */       public double newValue(double newValue)
/*     */       {
/* 199 */         if ((newValue < 0.0D) || (newValue > 1.0D))
/* 200 */           newValue = this.currentValue;
/* 201 */         HistogramSeriesAttributes.this.lineOpacity = ((float)newValue);
/* 202 */         HistogramSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 203 */         return newValue;
/*     */       }
/*     */     };
/* 206 */     addLabelled("", this.lineOpacityField);
/*     */ 
/* 208 */     this.thicknessField = new NumberTextField("Width ", this.thickness, false)
/*     */     {
/*     */       public double newValue(double newValue)
/*     */       {
/* 212 */         if (newValue < 0.0D)
/* 213 */           newValue = this.currentValue;
/* 214 */         HistogramSeriesAttributes.this.thickness = ((float)newValue);
/* 215 */         HistogramSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 216 */         return newValue;
/*     */       }
/*     */     };
/* 219 */     addLabelled("", this.thicknessField);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.HistogramSeriesAttributes
 * JD-Core Version:    0.6.2
 */