/*     */ package sim.portrayal.simple;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Area;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ 
/*     */ public class ShapePortrayal2D extends AbstractShapePortrayal2D
/*     */ {
/*  20 */   static final Stroke defaultStroke = new BasicStroke();
/*     */   public Shape shape;
/*     */   public Stroke stroke;
/*  23 */   AffineTransform transform = new AffineTransform();
/*     */ 
/*  25 */   double[] xPoints = null;
/*  26 */   double[] yPoints = null;
/*  27 */   double[] scaledXPoints = null;
/*  28 */   double[] scaledYPoints = null;
/*  29 */   int[] translatedXPoints = null;
/*  30 */   int[] translatedYPoints = null;
/*     */   double scaling;
/*     */   double bufferedWidth;
/*     */   double bufferedHeight;
/*     */   Shape bufferedShape;
/*  37 */   public static final double[] X_POINTS_TRIANGLE_DOWN = { -0.5D, 0.0D, 0.5D };
/*  38 */   public static final double[] Y_POINTS_TRIANGLE_DOWN = { -0.5D, 0.5D, -0.5D };
/*  39 */   public static final double[] X_POINTS_TRIANGLE_UP = { -0.5D, 0.0D, 0.5D };
/*  40 */   public static final double[] Y_POINTS_TRIANGLE_UP = { 0.5D, -0.5D, 0.5D };
/*  41 */   public static final double[] X_POINTS_TRIANGLE_RIGHT = { -0.5D, -0.5D, 0.5D };
/*  42 */   public static final double[] Y_POINTS_TRIANGLE_RIGHT = { -0.5D, 0.5D, 0.0D };
/*  43 */   public static final double[] X_POINTS_TRIANGLE_LEFT = { -0.5D, 0.5D, 0.5D };
/*  44 */   public static final double[] Y_POINTS_TRIANGLE_LEFT = { 0.0D, 0.5D, -0.5D };
/*  45 */   public static final double[] X_POINTS_DIAMOND = { -0.5D, 0.0D, 0.5D, 0.0D };
/*  46 */   public static final double[] Y_POINTS_DIAMOND = { 0.0D, 0.5D, 0.0D, -0.5D };
/*  47 */   public static final double[] X_POINTS_SQUARE = { -0.5D, -0.5D, 0.5D, 0.5D };
/*  48 */   public static final double[] Y_POINTS_SQUARE = { -0.5D, 0.5D, 0.5D, -0.5D };
/*  49 */   public static final double[] X_POINTS_BOWTIE = { -0.5D, 0.5D, 0.5D, -0.5D };
/*  50 */   public static final double[] Y_POINTS_BOWTIE = { -0.5D, 0.5D, -0.5D, 0.5D };
/*  51 */   public static final double[] X_POINTS_HOURGLASS = { -0.5D, 0.5D, -0.5D, 0.5D };
/*  52 */   public static final double[] Y_POINTS_HOURGLASS = { -0.5D, 0.5D, 0.5D, -0.5D };
/*     */ 
/*  54 */   static final double OCT_COORD = 1.0D / (1.0D + Math.sqrt(2.0D)) / 2.0D;
/*  55 */   public static final double[] X_POINTS_OCTAGON = { -0.5D, -0.5D, -OCT_COORD, OCT_COORD, 0.5D, 0.5D, OCT_COORD, -OCT_COORD };
/*  56 */   public static final double[] Y_POINTS_OCTAGON = { -OCT_COORD, OCT_COORD, 0.5D, 0.5D, OCT_COORD, -OCT_COORD, -0.5D, -0.5D };
/*     */ 
/*  59 */   public static final double[] X_POINTS_HEXAGON = { -0.5D, -0.25D, 0.25D, 0.5D, 0.25D, -0.25D };
/*  60 */   public static final double[] Y_POINTS_HEXAGON = { 0.0D, 0.5D, 0.5D, 0.0D, -0.5D, -0.5D };
/*  61 */   public static final double[] X_POINTS_HEXAGON_ROTATED = { 0.0D, 0.5D, 0.5D, 0.0D, -0.5D, -0.5D };
/*  62 */   public static final double[] Y_POINTS_HEXAGON_ROTATED = { -0.5D, -0.25D, 0.25D, 0.5D, 0.25D, -0.25D };
/*     */ 
/*     */   Shape buildPolygon(double[] xpoints, double[] ypoints)
/*     */   {
/*  66 */     GeneralPath path = new GeneralPath();
/*     */ 
/*  69 */     if (xpoints.length > 0) path.moveTo((float)xpoints[0], (float)ypoints[0]);
/*  70 */     for (int i = xpoints.length - 1; i >= 0; i--)
/*  71 */       path.lineTo((float)xpoints[i], (float)ypoints[i]);
/*  72 */     return path;
/*     */   }
/*     */   public ShapePortrayal2D(double[] xpoints, double[] ypoints) {
/*  75 */     this(xpoints, ypoints, Color.gray, 1.0D, true); } 
/*  76 */   public ShapePortrayal2D(double[] xpoints, double[] ypoints, Paint paint) { this(xpoints, ypoints, paint, 1.0D, true); } 
/*  77 */   public ShapePortrayal2D(double[] xpoints, double[] ypoints, double scale) { this(xpoints, ypoints, Color.gray, scale, true); } 
/*  78 */   public ShapePortrayal2D(double[] xpoints, double[] ypoints, Paint paint, double scale) { this(xpoints, ypoints, paint, scale, true); } 
/*  79 */   public ShapePortrayal2D(double[] xpoints, double[] ypoints, boolean filled) { this(xpoints, ypoints, Color.gray, 1.0D, filled); } 
/*  80 */   public ShapePortrayal2D(double[] xpoints, double[] ypoints, Paint paint, boolean filled) { this(xpoints, ypoints, paint, 1.0D, filled); } 
/*  81 */   public ShapePortrayal2D(double[] xpoints, double[] ypoints, double scale, boolean filled) { this(xpoints, ypoints, Color.gray, scale, filled); }
/*     */ 
/*     */   public ShapePortrayal2D(double[] xpoints, double[] ypoints, Paint paint, double scale, boolean filled) {
/*  84 */     this(null, paint, scale, filled);
/*  85 */     this.shape = buildPolygon(xpoints, ypoints);
/*  86 */     this.xPoints = xpoints;
/*  87 */     this.yPoints = ypoints;
/*  88 */     this.scaledXPoints = new double[xpoints.length];
/*  89 */     this.scaledYPoints = new double[ypoints.length];
/*  90 */     this.translatedXPoints = new int[xpoints.length];
/*  91 */     this.translatedYPoints = new int[ypoints.length];
/*     */   }
/*     */   public ShapePortrayal2D(Shape shape) {
/*  94 */     this(shape, Color.gray, 1.0D, true); } 
/*  95 */   public ShapePortrayal2D(Shape shape, Paint paint) { this(shape, paint, 1.0D, true); } 
/*  96 */   public ShapePortrayal2D(Shape shape, double scale) { this(shape, Color.gray, scale, true); } 
/*  97 */   public ShapePortrayal2D(Shape shape, Paint paint, double scale) { this(shape, paint, scale, true); } 
/*  98 */   public ShapePortrayal2D(Shape shape, boolean filled) { this(shape, Color.gray, 1.0D, filled); } 
/*  99 */   public ShapePortrayal2D(Shape shape, Paint paint, boolean filled) { this(shape, paint, 1.0D, filled); } 
/* 100 */   public ShapePortrayal2D(Shape shape, double scale, boolean filled) { this(shape, Color.gray, scale, filled); }
/*     */ 
/*     */   public ShapePortrayal2D(Shape shape, Paint paint, double scale, boolean filled) {
/* 103 */     this.shape = shape;
/* 104 */     this.paint = paint;
/* 105 */     this.scale = scale;
/* 106 */     this.filled = filled;
/* 107 */     setStroke(null);
/*     */   }
/*     */ 
/*     */   public void setStroke(Stroke s)
/*     */   {
/* 112 */     this.stroke = s;
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/* 118 */     graphics.setPaint(this.paint);
/*     */ 
/* 121 */     double width = info.draw.width * this.scale;
/* 122 */     double height = info.draw.height * this.scale;
/* 123 */     if ((this.bufferedShape == null) || (width != this.bufferedWidth) || (height != this.bufferedHeight))
/*     */     {
/* 125 */       this.transform.setToScale(this.bufferedWidth = width, this.bufferedHeight = height);
/* 126 */       this.bufferedShape = this.transform.createTransformedShape(this.shape);
/*     */     }
/*     */ 
/* 132 */     this.transform.setToTranslation(info.draw.x, info.draw.y);
/* 133 */     if (this.filled)
/*     */     {
/* 135 */       graphics.fill(this.transform.createTransformedShape(this.bufferedShape));
/*     */     }
/*     */     else
/*     */     {
/* 139 */       graphics.setStroke(this.stroke == null ? defaultStroke : this.stroke);
/* 140 */       graphics.draw(this.transform.createTransformedShape(this.bufferedShape));
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean hitObject(Object object, DrawInfo2D range)
/*     */   {
/* 181 */     double width = range.draw.width * this.scale;
/* 182 */     double height = range.draw.height * this.scale;
/* 183 */     if ((this.bufferedShape == null) || (width != this.bufferedWidth) || (height != this.bufferedHeight))
/*     */     {
/* 185 */       this.transform.setToScale(this.bufferedWidth = width, this.bufferedHeight = height);
/* 186 */       this.bufferedShape = this.transform.createTransformedShape(this.shape);
/*     */     }
/*     */ 
/* 189 */     this.transform.setToTranslation(range.draw.x, range.draw.y);
/*     */ 
/* 192 */     return new Area(this.transform.createTransformedShape(this.bufferedShape)).intersects(range.clip.x, range.clip.y, range.clip.width, range.clip.height);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.simple.ShapePortrayal2D
 * JD-Core Version:    0.6.2
 */