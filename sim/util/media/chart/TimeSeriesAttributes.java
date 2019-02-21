/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Paint;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.JComboBox;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import org.jfree.data.xy.XYDataItem;
/*     */ import org.jfree.data.xy.XYSeries;
/*     */ import sim.util.Bag;
/*     */ import sim.util.IntBag;
/*     */ import sim.util.gui.ColorWell;
/*     */ import sim.util.gui.NumberTextField;
/*     */ 
/*     */ public class TimeSeriesAttributes extends SeriesAttributes
/*     */ {
/*     */   static final float DASH = 6.0F;
/*     */   static final float DOT = 1.0F;
/*     */   static final float SPACE = 3.0F;
/*     */   static final float SKIP = 6.0F;
/*     */   public static final int PATTERN_SOLID = 0;
/*     */   public static final int PATTERN_LONG_DASH = 1;
/*     */   public static final int PATTERN_STRETCH_DASH = 2;
/*     */   public static final int PATTERN_DASH = 3;
/*     */   public static final int PATTERN_DASH_DASH_DOT = 4;
/*     */   public static final int PATTERN_DASH_DOT = 5;
/*     */   public static final int PATTERN_DASH_DOT_DOT = 6;
/*     */   public static final int PATTERN_DOT = 7;
/*     */   public static final int PATTERN_STRETCH_DOT = 8;
/*  52 */   static final float[][] dashPatterns = { { 6.0F, 0.0F }, { 12.0F, 6.0F }, { 6.0F, 6.0F }, { 6.0F, 3.0F }, { 6.0F, 3.0F, 6.0F, 3.0F, 1.0F, 3.0F }, { 6.0F, 3.0F, 1.0F, 3.0F }, { 6.0F, 3.0F, 1.0F, 3.0F, 1.0F, 3.0F }, { 1.0F, 3.0F }, { 1.0F, 6.0F } };
/*     */   float stretch;
/*     */   NumberTextField stretchField;
/*     */   float thickness;
/*     */   NumberTextField thicknessField;
/*     */   int dashPattern;
/*     */   JComboBox dashPatternList;
/*     */   Color strokeColor;
/*     */   ColorWell strokeColorWell;
/*     */   XYSeries series;
/*     */ 
/*     */   public void setThickness(double value)
/*     */   {
/*  78 */     this.thicknessField.setValue(this.thicknessField.newValue(value)); } 
/*  79 */   public double getThickness() { return this.thicknessField.getValue(); } 
/*     */   public void setStretch(double value) {
/*  81 */     this.stretchField.setValue(this.stretchField.newValue(value)); } 
/*  82 */   public double getStretch() { return this.stretchField.getValue(); }
/*     */ 
/*     */   public void setDashPattern(int value)
/*     */   {
/*  86 */     if ((value >= 0) && (value < dashPatterns.length))
/*     */     {
/*  88 */       this.dashPatternList.setSelectedIndex(value);
/*  89 */       this.dashPattern = value;
/*     */     }
/*     */   }
/*  92 */   public int getDashPattern() { return this.dashPatternList.getSelectedIndex(); } 
/*     */   public void setStrokeColor(Color value) {
/*  94 */     this.strokeColorWell.setColor(this.strokeColor = value); } 
/*  95 */   public Color getStrokeColor() { return this.strokeColor; }
/*     */ 
/*     */   public XYSeries getSeries()
/*     */   {
/*  99 */     return this.series;
/*     */   }
/*     */ 
/*     */   public void setSeries(XYSeries series)
/*     */   {
/* 105 */     this.series.clear();
/* 106 */     int count = series.getItemCount();
/* 107 */     for (int i = 0; i < count; i++)
/* 108 */       this.series.add(series.getDataItem(i), true); 
/*     */   }
/*     */ 
/* 111 */   public void setSeriesName(String val) { this.series.setKey(new ChartGenerator.UniqueString(val)); } 
/* 112 */   public String getSeriesName() { return "" + this.series.getKey(); } 
/*     */   public void clear() {
/* 114 */     this.series.clear();
/*     */   }
/*     */ 
/*     */   public TimeSeriesAttributes(ChartGenerator generator, XYSeries series, int index, SeriesChangeListener stoppable)
/*     */   {
/* 119 */     super(generator, "" + series.getKey(), index, stoppable);
/* 120 */     this.series = series;
/*     */   }
/*     */ 
/*     */   public void rebuildGraphicsDefinitions()
/*     */   {
/* 125 */     float[] newDashPattern = new float[dashPatterns[this.dashPattern].length];
/* 126 */     for (int x = 0; x < newDashPattern.length; x++) {
/* 127 */       if (this.stretch * this.thickness > 0.0F)
/* 128 */         newDashPattern[x] = (dashPatterns[this.dashPattern][x] * this.stretch * this.thickness);
/*     */     }
/* 130 */     XYItemRenderer renderer = ((XYPlot)getPlot()).getRenderer();
/*     */ 
/* 136 */     renderer.setSeriesStroke(getSeriesIndex(), this.dashPattern == 0 ? new BasicStroke(this.thickness, 1, 1, 0.0F) : new BasicStroke(this.thickness, 1, 1, 0.0F, newDashPattern, 0.0F));
/*     */ 
/* 143 */     renderer.setSeriesPaint(getSeriesIndex(), this.strokeColor);
/* 144 */     repaint();
/*     */   }
/*     */ 
/*     */   public void buildAttributes()
/*     */   {
/* 152 */     this.dashPattern = 0;
/* 153 */     this.stretch = 1.0F;
/* 154 */     this.thickness = 2.0F;
/*     */ 
/* 157 */     XYItemRenderer renderer = ((XYPlot)getPlot()).getRenderer();
/*     */ 
/* 166 */     Paint paint = renderer.getItemPaint(getSeriesIndex(), -1);
/*     */ 
/* 168 */     this.strokeColor = ((Color)paint);
/*     */ 
/* 170 */     this.strokeColorWell = new ColorWell(this.strokeColor)
/*     */     {
/*     */       public Color changeColor(Color c)
/*     */       {
/* 174 */         TimeSeriesAttributes.this.strokeColor = c;
/* 175 */         TimeSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 176 */         return c;
/*     */       }
/*     */     };
/* 179 */     addLabelled("Color", this.strokeColorWell);
/*     */ 
/* 181 */     this.thicknessField = new NumberTextField(2.0D, true)
/*     */     {
/*     */       public double newValue(double newValue)
/*     */       {
/* 185 */         if (newValue < 0.0D)
/* 186 */           newValue = this.currentValue;
/* 187 */         TimeSeriesAttributes.this.thickness = ((float)newValue);
/* 188 */         TimeSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 189 */         return newValue;
/*     */       }
/*     */     };
/* 192 */     addLabelled("Width", this.thicknessField);
/*     */ 
/* 194 */     this.dashPatternList = new JComboBox();
/* 195 */     this.dashPatternList.setEditable(false);
/* 196 */     this.dashPatternList.setModel(new DefaultComboBoxModel(new Vector(Arrays.asList(new String[] { "Solid", "__  __  __", "_  _  _  _", "_ _ _ _ _", "_ _ . _ _ .", "_ . _ . _ .", "_ . . _ . .", ". . . . . . .", ".  .  .  .  ." }))));
/*     */ 
/* 199 */     this.dashPatternList.setSelectedIndex(0);
/* 200 */     this.dashPatternList.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/* 204 */         TimeSeriesAttributes.this.dashPattern = TimeSeriesAttributes.this.dashPatternList.getSelectedIndex();
/* 205 */         TimeSeriesAttributes.this.rebuildGraphicsDefinitions();
/*     */       }
/*     */     });
/* 208 */     addLabelled("Dash", this.dashPatternList);
/* 209 */     this.stretchField = new NumberTextField(1.0D, true)
/*     */     {
/*     */       public double newValue(double newValue)
/*     */       {
/* 213 */         if (newValue < 0.0D)
/* 214 */           newValue = this.currentValue;
/* 215 */         TimeSeriesAttributes.this.stretch = ((float)newValue);
/* 216 */         TimeSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 217 */         return newValue;
/*     */       }
/*     */     };
/* 220 */     addLabelled("Stretch", this.stretchField);
/*     */   }
/*     */ 
/*     */   public boolean possiblyCull()
/*     */   {
/* 226 */     DataCuller dataCuller = ((TimeSeriesChartGenerator)this.generator).getDataCuller();
/* 227 */     if ((dataCuller != null) && (dataCuller.tooManyPoints(this.series.getItemCount())))
/*     */     {
/* 229 */       deleteItems(dataCuller.cull(getXValues(), true));
/* 230 */       return true;
/*     */     }
/*     */ 
/* 233 */     return false;
/*     */   }
/*     */ 
/*     */   void deleteItems(IntBag items)
/*     */   {
/* 238 */     Bag tmpBag = new Bag();
/*     */ 
/* 240 */     if (items.numObjs == 0) {
/* 241 */       return;
/*     */     }
/* 243 */     int currentTabooIndex = 0;
/* 244 */     int currentTaboo = items.objs[0];
/* 245 */     Iterator iter = this.series.getItems().iterator();
/* 246 */     int index = 0;
/* 247 */     while (iter.hasNext())
/*     */     {
/* 249 */       Object o = iter.next();
/* 250 */       if (index == currentTaboo)
/*     */       {
/* 253 */         if (currentTabooIndex < items.numObjs - 1)
/*     */         {
/* 255 */           currentTabooIndex++;
/* 256 */           currentTaboo = items.objs[currentTabooIndex];
/*     */         }
/*     */         else {
/* 259 */           currentTaboo = -1;
/*     */         }
/*     */       }
/* 262 */       else tmpBag.add(o);
/* 263 */       index++;
/*     */     }
/*     */ 
/* 266 */     this.series.clear();
/*     */ 
/* 270 */     for (int i = 0; i < tmpBag.numObjs; i++) {
/* 271 */       this.series.add((XYDataItem)tmpBag.objs[i], false);
/*     */     }
/*     */ 
/* 274 */     this.series.fireSeriesChanged();
/*     */   }
/*     */ 
/*     */   double[] getXValues()
/*     */   {
/* 279 */     double[] xValues = new double[this.series.getItemCount()];
/* 280 */     for (int i = 0; i < xValues.length; i++)
/* 281 */       xValues[i] = this.series.getX(i).doubleValue();
/* 282 */     return xValues;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.TimeSeriesAttributes
 * JD-Core Version:    0.6.2
 */