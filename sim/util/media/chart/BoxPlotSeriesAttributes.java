/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
/*     */ import org.jfree.chart.renderer.category.CategoryItemRenderer;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import sim.util.gui.ColorWell;
/*     */ import sim.util.gui.NumberTextField;
/*     */ 
/*     */ public class BoxPlotSeriesAttributes extends SeriesAttributes
/*     */ {
/*     */   double[][] values;
/*  37 */   String[] labels = { "" };
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
/*     */ 
/*     */   public void setLabels(String[] labels)
/*     */   {
/*  40 */     if (labels != null) labels = (String[])labels.clone(); else
/*  41 */       labels = new String[] { "" };
/*  42 */     this.labels = labels;
/*     */   }
/*  44 */   public String[] getLabels() { return this.labels; } 
/*     */   public double[][] getValues() {
/*  46 */     return this.values;
/*     */   }
/*     */   public void setValues(double[][] vals) {
/*  49 */     if (vals != null)
/*     */     {
/*  51 */       vals = (double[][])vals.clone();
/*  52 */       for (int i = 0; i < vals.length; i++)
/*  53 */         vals[i] = ((double[])(double[])vals[i].clone());
/*     */     }
/*  55 */     this.values = vals;
/*     */   }
/*     */ 
/*     */   public void setFillOpacity(double value)
/*     */   {
/*  78 */     this.fillOpacityField.setValue(this.fillOpacityField.newValue(value)); } 
/*  79 */   public double getFillOpacity() { return this.fillOpacityField.getValue(); } 
/*     */   public void setStrokeOpacity(double value) {
/*  81 */     this.lineOpacityField.setValue(this.lineOpacityField.newValue(value)); } 
/*  82 */   public double getStrokeOpacity() { return this.lineOpacityField.getValue(); } 
/*     */   public void setThickness(double value) {
/*  84 */     this.thicknessField.setValue(this.thicknessField.newValue(value)); } 
/*  85 */   public double getThickness() { return this.thicknessField.getValue(); } 
/*     */   public void setFillColor(Color value) {
/*  87 */     this.fillColorWell.setColor(this.fillColor = value); } 
/*  88 */   public Color getFillColor() { return this.fillColor; } 
/*     */   public void setStrokeColor(Color value) {
/*  90 */     this.strokeColorWell.setColor(this.strokeColor = value); } 
/*  91 */   public Color getStrokeColor() { return this.strokeColor; }
/*     */ 
/*     */ 
/*     */   public BoxPlotSeriesAttributes(ChartGenerator generator, String name, int index, double[][] values, String[] labels, SeriesChangeListener stoppable)
/*     */   {
/*  98 */     super(generator, name, index, stoppable);
/*  99 */     setValues(values);
/* 100 */     setLabels(labels);
/* 101 */     super.setSeriesName(name);
/*     */   }
/*     */ 
/*     */   BoxPlotSeriesAttributes(ChartGenerator generator, String name, int index, double[][] values, SeriesChangeListener stoppable)
/*     */   {
/* 109 */     super(generator, name, index, stoppable);
/* 110 */     setValues(values);
/* 111 */     super.setSeriesName(name);
/*     */   }
/*     */ 
/*     */   public BoxPlotSeriesAttributes(ChartGenerator generator, String name, int index, double[] values, SeriesChangeListener stoppable)
/*     */   {
/* 119 */     super(generator, name, index, stoppable);
/* 120 */     setValues(new double[][] { values });
/* 121 */     super.setSeriesName(name);
/*     */   }
/*     */ 
/*     */   public void setSeriesName(String val)
/*     */   {
/* 129 */     super.setSeriesName(val);
/* 130 */     ((BoxPlotGenerator)this.generator).update();
/*     */   }
/*     */ 
/*     */   public void rebuildGraphicsDefinitions()
/*     */   {
/* 135 */     BoxAndWhiskerRenderer renderer = (BoxAndWhiskerRenderer)getCategoryRenderer();
/*     */ 
/* 137 */     renderer.setSeriesOutlineStroke(getSeriesIndex(), new BasicStroke(this.thickness));
/*     */ 
/* 139 */     renderer.setSeriesStroke(getSeriesIndex(), new BasicStroke(this.thickness));
/*     */ 
/* 142 */     renderer.setSeriesPaint(getSeriesIndex(), reviseColor(this.fillColor, this.fillOpacity));
/* 143 */     renderer.setSeriesOutlinePaint(getSeriesIndex(), reviseColor(this.strokeColor, this.lineOpacity));
/* 144 */     repaint();
/*     */   }
/*     */ 
/*     */   public void buildAttributes()
/*     */   {
/* 151 */     this.thickness = 2.0F;
/* 152 */     this.fillOpacity = 1.0D;
/* 153 */     this.lineOpacity = 1.0D;
/*     */ 
/* 166 */     this.fillColor = ((Color)getCategoryRenderer().getItemPaint(getSeriesIndex(), -1));
/*     */ 
/* 169 */     this.fillColor = ((Color)getCategoryRenderer().getSeriesPaint(getSeriesIndex()));
/* 170 */     this.fillColorWell = new ColorWell(this.fillColor)
/*     */     {
/*     */       public Color changeColor(Color c)
/*     */       {
/* 174 */         BoxPlotSeriesAttributes.this.fillColor = c;
/* 175 */         BoxPlotSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 176 */         return c;
/*     */       }
/*     */     };
/* 180 */     addLabelled("Fill", this.fillColorWell);
/*     */ 
/* 182 */     this.fillOpacityField = new NumberTextField("Opacity ", this.fillOpacity, 1.0D, 0.125D)
/*     */     {
/*     */       public double newValue(double newValue)
/*     */       {
/* 186 */         if ((newValue < 0.0D) || (newValue > 1.0D))
/* 187 */           newValue = this.currentValue;
/* 188 */         BoxPlotSeriesAttributes.this.fillOpacity = ((float)newValue);
/* 189 */         BoxPlotSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 190 */         return newValue;
/*     */       }
/*     */     };
/* 193 */     addLabelled("", this.fillOpacityField);
/*     */ 
/* 195 */     this.strokeColor = Color.black;
/* 196 */     this.strokeColorWell = new ColorWell(this.strokeColor)
/*     */     {
/*     */       public Color changeColor(Color c)
/*     */       {
/* 200 */         BoxPlotSeriesAttributes.this.strokeColor = c;
/* 201 */         BoxPlotSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 202 */         return c;
/*     */       }
/*     */     };
/* 206 */     addLabelled("Line", this.strokeColorWell);
/*     */ 
/* 208 */     this.lineOpacityField = new NumberTextField("Opacity ", this.lineOpacity, 1.0D, 0.125D)
/*     */     {
/*     */       public double newValue(double newValue)
/*     */       {
/* 212 */         if ((newValue < 0.0D) || (newValue > 1.0D))
/* 213 */           newValue = this.currentValue;
/* 214 */         BoxPlotSeriesAttributes.this.lineOpacity = ((float)newValue);
/* 215 */         BoxPlotSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 216 */         return newValue;
/*     */       }
/*     */     };
/* 219 */     addLabelled("", this.lineOpacityField);
/*     */ 
/* 221 */     this.thicknessField = new NumberTextField("Width ", this.thickness, false)
/*     */     {
/*     */       public double newValue(double newValue)
/*     */       {
/* 225 */         if (newValue < 0.0D)
/* 226 */           newValue = this.currentValue;
/* 227 */         BoxPlotSeriesAttributes.this.thickness = ((float)newValue);
/* 228 */         BoxPlotSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 229 */         return newValue;
/*     */       }
/*     */     };
/* 232 */     addLabelled("", this.thicknessField);
/*     */   }
/*     */ 
/*     */   public CategoryItemRenderer getCategoryRenderer()
/*     */   {
/* 237 */     return ((CategoryPlot)getPlot()).getRenderer();
/*     */   }
/*     */ 
/*     */   public void setPlotVisible(boolean val)
/*     */   {
/* 242 */     this.plotVisible = val;
/* 243 */     getCategoryRenderer().setSeriesVisible(this.seriesIndex, Boolean.valueOf(val));
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.BoxPlotSeriesAttributes
 * JD-Core Version:    0.6.2
 */