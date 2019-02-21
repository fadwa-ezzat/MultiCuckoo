/*     */ package sim.util.media.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Shape;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Ellipse2D.Double;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import java.util.Arrays;
/*     */ import java.util.Vector;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.JComboBox;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*     */ import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import sim.util.gui.ColorWell;
/*     */ import sim.util.gui.NumberTextField;
/*     */ 
/*     */ public class ScatterPlotSeriesAttributes extends SeriesAttributes
/*     */ {
/*  62 */   static final Shape[] shapes = buildShapes();
/*  63 */   static final String[] shapeNames = { "Circle", "Square", "Diamond", "Cross", "X", "Up Triangle", "Down Triangle" };
/*     */   double[][] values;
/*     */   Color color;
/*     */   ColorWell colorWell;
/*     */   double opacity;
/*     */   NumberTextField opacityField;
/*  92 */   int shapeNum = 0;
/*  93 */   Shape shape = shapes[this.shapeNum];
/*     */   JComboBox shapeList;
/*     */ 
/*     */   static Shape[] buildShapes()
/*     */   {
/*  25 */     Shape[] s = new Shape[7];
/*  26 */     GeneralPath g = null;
/*     */ 
/*  29 */     s[0] = new Ellipse2D.Double(-3.0D, -3.0D, 6.0D, 6.0D);
/*     */ 
/*  32 */     Rectangle2D.Double r = new Rectangle2D.Double(-3.0D, -3.0D, 6.0D, 6.0D);
/*  33 */     s[1] = r;
/*     */ 
/*  36 */     s[2] = AffineTransform.getRotateInstance(0.7853981633974483D).createTransformedShape(r);
/*     */ 
/*  39 */     g = new GeneralPath();
/*  40 */     g.moveTo(-0.5F, -3.0F);
/*  41 */     g.lineTo(-0.5F, -0.5F); g.lineTo(-3.0F, -0.5F); g.lineTo(-3.0F, 0.5F);
/*  42 */     g.lineTo(-0.5F, 0.5F); g.lineTo(-0.5F, 3.0F); g.lineTo(0.5F, 3.0F);
/*  43 */     g.lineTo(0.5F, 0.5F); g.lineTo(3.0F, 0.5F); g.lineTo(3.0F, -0.5F);
/*  44 */     g.lineTo(0.5F, -0.5F); g.lineTo(0.5F, -3.0F); g.closePath();
/*  45 */     s[3] = g;
/*     */ 
/*  48 */     s[4] = g.createTransformedShape(AffineTransform.getRotateInstance(0.7853981633974483D));
/*     */ 
/*  51 */     g = new GeneralPath();
/*  52 */     g.moveTo(0.0F, -3.0F);
/*  53 */     g.lineTo(-3.0F, 3.0F); g.lineTo(3.0F, 3.0F); g.closePath();
/*  54 */     s[5] = g;
/*     */ 
/*  57 */     s[6] = g.createTransformedShape(AffineTransform.getRotateInstance(3.141592653589793D));
/*     */ 
/*  59 */     return s;
/*     */   }
/*     */ 
/*     */   public double[][] getValues()
/*     */   {
/*  69 */     return this.values;
/*     */   }
/*     */   public void setValues(double[][] vals) {
/*  72 */     if (vals != null)
/*     */     {
/*  74 */       vals = (double[][])vals.clone();
/*  75 */       for (int i = 0; i < vals.length; i++)
/*  76 */         vals[i] = ((double[])(double[])vals[i].clone());
/*     */     }
/*  78 */     this.values = vals;
/*     */   }
/*     */ 
/*     */   public void setSymbolOpacity(double value)
/*     */   {
/*  86 */     this.opacityField.setValue(this.opacityField.newValue(value)); } 
/*  87 */   public double getSymbolOpacity() { return this.opacityField.getValue(); } 
/*     */   public void setSymbolColor(Color value) {
/*  89 */     this.colorWell.setColor(this.color = value); } 
/*  90 */   public Color getSymbolColor() { return this.color; }
/*     */ 
/*     */ 
/*     */   public void setShapeNum(int value)
/*     */   {
/*  98 */     if ((value >= 0) && (value < shapes.length))
/*     */     {
/* 100 */       this.shapeList.setSelectedIndex(value);
/* 101 */       this.shapeNum = value;
/* 102 */       this.shape = shapes[this.shapeNum];
/*     */     }
/*     */   }
/* 105 */   public int getShapeNum() { return this.shapeNum; } 
/* 106 */   public Shape getShape() { return this.shape; }
/*     */ 
/*     */ 
/*     */   public ScatterPlotSeriesAttributes(ChartGenerator generator, String name, int index, double[][] values, SeriesChangeListener stoppable)
/*     */   {
/* 113 */     super(generator, name, index, stoppable);
/*     */ 
/* 115 */     setValues(values);
/* 116 */     super.setSeriesName(name);
/*     */ 
/* 119 */     ((ScatterPlotGenerator)generator).shapeCounter += 1;
/* 120 */     if (((ScatterPlotGenerator)generator).shapeCounter >= shapes.length) {
/* 121 */       ((ScatterPlotGenerator)generator).shapeCounter = 0;
/*     */     }
/*     */ 
/* 124 */     this.shapeNum = ((ScatterPlotGenerator)generator).shapeCounter;
/* 125 */     this.shape = shapes[this.shapeNum];
/* 126 */     XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)((XYPlot)getPlot()).getRenderer();
/* 127 */     renderer.setSeriesShape(getSeriesIndex(), this.shape);
/* 128 */     renderer.setAutoPopulateSeriesShape(false);
/*     */   }
/*     */ 
/*     */   public void setSeriesName(String val)
/*     */   {
/* 133 */     super.setSeriesName(val);
/* 134 */     ((ScatterPlotGenerator)this.generator).update();
/*     */   }
/*     */ 
/*     */   public void rebuildGraphicsDefinitions()
/*     */   {
/* 139 */     XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)((XYPlot)getPlot()).getRenderer();
/* 140 */     renderer.setSeriesPaint(getSeriesIndex(), reviseColor(this.color, this.opacity));
/*     */ 
/* 142 */     renderer.setSeriesShape(getSeriesIndex(), this.shape);
/* 143 */     renderer.setAutoPopulateSeriesShape(false);
/* 144 */     repaint();
/*     */   }
/*     */ 
/*     */   public void buildAttributes()
/*     */   {
/* 151 */     this.opacity = 1.0D;
/*     */ 
/* 160 */     this.color = ((Color)((XYPlot)getPlot()).getRenderer().getItemPaint(getSeriesIndex(), -1));
/*     */ 
/* 162 */     this.colorWell = new ColorWell(this.color)
/*     */     {
/*     */       public Color changeColor(Color c)
/*     */       {
/* 166 */         ScatterPlotSeriesAttributes.this.color = c;
/* 167 */         ScatterPlotSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 168 */         return c;
/*     */       }
/*     */     };
/* 172 */     addLabelled("Color", this.colorWell);
/*     */ 
/* 174 */     this.opacityField = new NumberTextField("Opacity ", this.opacity, 1.0D, 0.125D)
/*     */     {
/*     */       public double newValue(double newValue)
/*     */       {
/* 178 */         if ((newValue < 0.0D) || (newValue > 1.0D))
/* 179 */           newValue = this.currentValue;
/* 180 */         ScatterPlotSeriesAttributes.this.opacity = ((float)newValue);
/* 181 */         ScatterPlotSeriesAttributes.this.rebuildGraphicsDefinitions();
/* 182 */         return newValue;
/*     */       }
/*     */     };
/* 185 */     addLabelled("", this.opacityField);
/*     */ 
/* 187 */     this.shapeList = new JComboBox();
/* 188 */     this.shapeList.setEditable(false);
/* 189 */     this.shapeList.setModel(new DefaultComboBoxModel(new Vector(Arrays.asList(shapeNames))));
/* 190 */     this.shapeList.setSelectedIndex(this.shapeNum);
/* 191 */     this.shapeList.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/* 195 */         ScatterPlotSeriesAttributes.this.shapeNum = ScatterPlotSeriesAttributes.this.shapeList.getSelectedIndex();
/* 196 */         ScatterPlotSeriesAttributes.this.shape = ScatterPlotSeriesAttributes.shapes[ScatterPlotSeriesAttributes.this.shapeNum];
/* 197 */         ScatterPlotSeriesAttributes.this.rebuildGraphicsDefinitions();
/*     */       }
/*     */     });
/* 200 */     addLabelled("Shape", this.shapeList);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.chart.ScatterPlotSeriesAttributes
 * JD-Core Version:    0.6.2
 */