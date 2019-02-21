/*     */ package sim.portrayal.network;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D.Double;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.field.network.Edge;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.util.Valuable;
/*     */ 
/*     */ public class SimpleEdgePortrayal2D extends SimplePortrayal2D
/*     */ {
/*  38 */   public Paint fromPaint = Color.black;
/*  39 */   public Paint toPaint = Color.black;
/*  40 */   public Paint labelPaint = null;
/*     */   public Font labelFont;
/*     */   Font scaledFont;
/*  43 */   int labelScaling = 2;
/*  44 */   int scaling = 2;
/*     */   public static final int NEVER_SCALE = 0;
/*     */   public static final int SCALE_WHEN_SMALLER = 1;
/*     */   public static final int ALWAYS_SCALE = 2;
/*  48 */   public double baseWidth = 1.0D;
/*     */   public static final int SHAPE_THIN_LINE = 0;
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final int SHAPE_LINE = 0;
/*     */   public static final int SHAPE_LINE_ROUND_ENDS = 1;
/*     */   public static final int SHAPE_LINE_SQUARE_ENDS = 2;
/*     */   public static final int SHAPE_LINE_BUTT_ENDS = 3;
/*     */   public static final int SHAPE_TRIANGLE = 4;
/*  57 */   public int shape = 0;
/*     */   boolean adjustsThickness;
/* 108 */   Line2D.Double preciseLine = new Line2D.Double();
/* 109 */   GeneralPath precisePoly = new GeneralPath();
/*     */ 
/* 148 */   int[] xPoints = new int[3];
/* 149 */   int[] yPoints = new int[3];
/*     */ 
/*     */   public SimpleEdgePortrayal2D()
/*     */   {
/*  64 */     this(Color.black, null);
/*     */   }
/*     */ 
/*     */   public SimpleEdgePortrayal2D(Paint edgePaint, Paint labelPaint)
/*     */   {
/*  70 */     this(edgePaint, edgePaint, labelPaint);
/*     */   }
/*     */ 
/*     */   public SimpleEdgePortrayal2D(Paint fromPaint, Paint toPaint, Paint labelPaint)
/*     */   {
/*  76 */     this(fromPaint, toPaint, labelPaint, new Font("SansSerif", 0, 12));
/*     */   }
/*     */ 
/*     */   public SimpleEdgePortrayal2D(Paint fromPaint, Paint toPaint, Paint labelPaint, Font labelFont)
/*     */   {
/*  82 */     this.fromPaint = fromPaint;
/*  83 */     this.toPaint = toPaint;
/*  84 */     this.labelPaint = labelPaint;
/*  85 */     this.labelFont = labelFont;
/*     */   }
/*     */   public boolean getAdjustsThickness() {
/*  88 */     return this.adjustsThickness; } 
/*  89 */   public void setAdjustsThickness(boolean val) { this.adjustsThickness = val; }
/*     */ 
/*     */   public int getShape() {
/*  92 */     return this.shape;
/*     */   }
/*  94 */   public void setShape(int shape) { this.shape = shape; } 
/*     */   public double getBaseWidth() {
/*  96 */     return this.baseWidth;
/*     */   }
/*     */   public void setBaseWidth(double val) {
/*  99 */     this.baseWidth = val;
/*     */   }
/* 101 */   public int getScaling() { return this.scaling; } 
/* 102 */   public void setScaling(int val) { if ((val >= 0) && (val <= 2)) this.scaling = val;  } 
/*     */   public int getLabelScaling() {
/* 104 */     return this.labelScaling; } 
/* 105 */   public void setLabelScaling(int val) { if ((val >= 0) && (val <= 2)) this.labelScaling = val;
/*     */   }
/*     */ 
/*     */   protected double getPositiveWeight(Object edge, EdgeDrawInfo2D info)
/*     */   {
/* 116 */     if (getAdjustsThickness())
/*     */     {
/* 118 */       Object obj = edge;
/* 119 */       if ((edge instanceof Edge)) obj = ((Edge)edge).info;
/* 120 */       if ((obj instanceof Number))
/* 121 */         return Math.abs(((Number)obj).doubleValue());
/* 122 */       if ((obj instanceof Valuable))
/* 123 */         return Math.abs(((Valuable)obj).doubleValue());
/*     */     }
/* 125 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   public String getLabel(Edge edge, EdgeDrawInfo2D info)
/*     */   {
/* 133 */     Object obj = edge.info;
/* 134 */     if (obj == null) return "";
/* 135 */     return "" + obj;
/*     */   }
/*     */ 
/*     */   BasicStroke getBasicStroke(float thickness)
/*     */   {
/* 140 */     return new BasicStroke(thickness, this.shape == 2 ? 2 : this.shape == 1 ? 1 : 0, 0);
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/* 152 */     if (!(info instanceof EdgeDrawInfo2D))
/* 153 */       throw new RuntimeException("Expected this to be an EdgeDrawInfo2D: " + info);
/* 154 */     EdgeDrawInfo2D e = (EdgeDrawInfo2D)info;
/*     */ 
/* 156 */     double startXd = e.draw.x;
/* 157 */     double startYd = e.draw.y;
/* 158 */     double endXd = e.secondPoint.x;
/* 159 */     double endYd = e.secondPoint.y;
/* 160 */     double midXd = (startXd + endXd) / 2.0D;
/* 161 */     double midYd = (startYd + endYd) / 2.0D;
/* 162 */     int startX = (int)startXd;
/* 163 */     int startY = (int)startYd;
/* 164 */     int endX = (int)endXd;
/* 165 */     int endY = (int)endYd;
/* 166 */     int midX = (int)midXd;
/* 167 */     int midY = (int)midYd;
/*     */ 
/* 170 */     if (this.shape == 4)
/*     */     {
/* 172 */       double weight = getPositiveWeight(object, e);
/* 173 */       double width = getBaseWidth();
/* 174 */       graphics.setPaint(this.fromPaint);
/* 175 */       double len = Math.sqrt((startXd - endXd) * (startXd - endXd) + (startYd - endYd) * (startYd - endYd));
/* 176 */       double vecX = (startXd - endXd) * width * 0.5D * weight / len;
/* 177 */       double vecY = (startYd - endYd) * width * 0.5D * weight / len;
/* 178 */       double scaleWidth = info.draw.width;
/* 179 */       double scaleHeight = info.draw.height;
/* 180 */       this.xPoints[0] = endX; this.yPoints[0] = endY;
/*     */ 
/* 182 */       if (((this.scaling == 1) && (info.draw.width >= 1.0D)) || (this.scaling == 0)) {
/* 183 */         scaleWidth = 1.0D; scaleHeight = 1.0D;
/* 184 */       }if (info.precise)
/*     */       {
/* 186 */         this.precisePoly.reset();
/* 187 */         this.precisePoly.moveTo((float)endXd, (float)endYd);
/* 188 */         this.precisePoly.lineTo((float)(startXd + vecY * scaleWidth), (float)(startYd + -vecX * scaleHeight));
/* 189 */         this.precisePoly.lineTo((float)(startXd + -vecY * scaleWidth), (float)(startYd + vecX * scaleHeight));
/* 190 */         this.precisePoly.lineTo((float)endXd, (float)endYd);
/* 191 */         graphics.fill(this.precisePoly);
/*     */       }
/*     */       else
/*     */       {
/* 195 */         this.xPoints[1] = ((int)(startXd + vecY * scaleWidth)); this.yPoints[1] = ((int)(startYd + -vecX * scaleHeight));
/* 196 */         this.xPoints[2] = ((int)(startXd + -vecY * scaleWidth)); this.yPoints[2] = ((int)(startYd + vecX * scaleHeight));
/* 197 */         graphics.fillPolygon(this.xPoints, this.yPoints, 3);
/* 198 */         graphics.drawPolygon(this.xPoints, this.yPoints, 3);
/*     */       }
/*     */     }
/* 201 */     else if (this.shape == 0)
/*     */     {
/* 203 */       if (this.fromPaint == this.toPaint)
/*     */       {
/* 205 */         graphics.setPaint(this.fromPaint);
/* 206 */         graphics.drawLine(startX, startY, endX, endY);
/*     */       }
/*     */       else
/*     */       {
/* 210 */         graphics.setPaint(this.fromPaint);
/* 211 */         graphics.drawLine(startX, startY, midX, midY);
/* 212 */         graphics.setPaint(this.toPaint);
/* 213 */         graphics.drawLine(midX, midY, endX, endY);
/*     */       }
/*     */ 
/*     */     }
/* 218 */     else if (this.fromPaint == this.toPaint)
/*     */     {
/* 220 */       graphics.setPaint(this.fromPaint);
/* 221 */       double width = getBaseWidth();
/*     */ 
/* 224 */       double scale = info.draw.width;
/* 225 */       if (((this.scaling == 1) && (info.draw.width >= 1.0D)) || (this.scaling == 0)) {
/* 226 */         scale = 1.0D;
/*     */       }
/* 228 */       Stroke oldstroke = graphics.getStroke();
/* 229 */       double weight = getPositiveWeight(object, e);
/* 230 */       graphics.setStroke(getBasicStroke((float)(width * weight * scale)));
/* 231 */       this.preciseLine.setLine(startXd, startYd, endXd, endYd);
/* 232 */       graphics.draw(this.preciseLine);
/* 233 */       graphics.setStroke(oldstroke);
/*     */     }
/*     */     else
/*     */     {
/* 239 */       graphics.setPaint(this.fromPaint);
/* 240 */       double width = getBaseWidth();
/*     */ 
/* 243 */       double scale = info.draw.width;
/* 244 */       if (((this.scaling == 1) && (info.draw.width >= 1.0D)) || (this.scaling == 0)) {
/* 245 */         scale = 1.0D;
/*     */       }
/* 247 */       Stroke oldstroke = graphics.getStroke();
/* 248 */       double weight = getPositiveWeight(object, e);
/* 249 */       graphics.setStroke(getBasicStroke((float)(width * weight * scale)));
/* 250 */       this.preciseLine.setLine(startXd, startYd, midXd, midYd);
/* 251 */       graphics.draw(this.preciseLine);
/* 252 */       graphics.setPaint(this.toPaint);
/* 253 */       this.preciseLine.setLine(midXd, midYd, endXd, endYd);
/* 254 */       graphics.draw(this.preciseLine);
/* 255 */       graphics.setStroke(oldstroke);
/*     */     }
/*     */ 
/* 267 */     if (this.labelPaint != null)
/*     */     {
/* 270 */       Font labelFont = this.labelFont;
/* 271 */       Font scaledFont = this.scaledFont;
/*     */ 
/* 274 */       float size = (this.labelScaling == 2) || ((this.labelScaling == 1) && (info.draw.width < 1.0D)) ? (float)(info.draw.width * labelFont.getSize2D()) : labelFont.getSize2D();
/*     */ 
/* 278 */       if ((scaledFont == null) || (scaledFont.getSize2D() != size) || (!scaledFont.getFamily().equals(labelFont.getFamily())) || (scaledFont.getStyle() != labelFont.getStyle()))
/*     */       {
/* 282 */         scaledFont = this.scaledFont = labelFont.deriveFont(size);
/*     */       }
/*     */ 
/* 285 */       String information = getLabel((Edge)object, e);
/* 286 */       if (information.length() > 0)
/*     */       {
/* 288 */         graphics.setPaint(this.labelPaint);
/* 289 */         graphics.setFont(scaledFont);
/* 290 */         int width = graphics.getFontMetrics().stringWidth(information);
/* 291 */         graphics.drawString(information, midX - width / 2, midY);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean hitObject(Object object, DrawInfo2D range)
/*     */   {
/* 298 */     if (!(range instanceof EdgeDrawInfo2D))
/* 299 */       throw new RuntimeException("Expected this to be an EdgeDrawInfo2D: " + range);
/* 300 */     EdgeDrawInfo2D e = (EdgeDrawInfo2D)range;
/*     */ 
/* 302 */     double startXd = e.draw.x;
/* 303 */     double startYd = e.draw.y;
/* 304 */     double endXd = e.secondPoint.x;
/* 305 */     double endYd = e.secondPoint.y;
/*     */ 
/* 307 */     double weight = getPositiveWeight(object, e);
/* 308 */     double width = getBaseWidth();
/*     */ 
/* 310 */     double SLOP = 5.0D;
/* 311 */     if (this.shape == 0)
/*     */     {
/* 313 */       double scale = range.draw.width;
/* 314 */       if (((this.scaling == 1) && (range.draw.width >= 1.0D)) || (this.scaling == 0)) {
/* 315 */         scale = 1.0D;
/*     */       }
/* 317 */       Line2D.Double line = new Line2D.Double(startXd, startYd, endXd, endYd);
/* 318 */       if (width == 0.0D) {
/* 319 */         return line.intersects(range.clip.x - 5.0D, range.clip.y - 5.0D, range.clip.width + 10.0D, range.clip.height + 10.0D);
/*     */       }
/* 321 */       return new BasicStroke((float)(width * weight * scale), 0, 0).createStrokedShape(line).intersects(range.clip.x - 5.0D, range.clip.y - 5.0D, range.clip.width + 10.0D, range.clip.height + 10.0D);
/*     */     }
/*     */ 
/* 326 */     double len = Math.sqrt((startXd - endXd) * (startXd - endXd) + (startYd - endYd) * (startYd - endYd));
/* 327 */     double vecX = (startXd - endXd) * width * 0.5D * weight / len;
/* 328 */     double vecY = (startYd - endYd) * width * 0.5D * weight / len;
/* 329 */     double scaleWidth = range.draw.width;
/* 330 */     double scaleHeight = range.draw.height;
/* 331 */     this.xPoints[0] = ((int)endXd); this.yPoints[0] = ((int)endYd);
/*     */ 
/* 333 */     if (((this.scaling == 1) && (range.draw.width >= 1.0D)) || (this.scaling == 0)) {
/* 334 */       scaleWidth = 1.0D; scaleHeight = 1.0D;
/* 335 */     }this.xPoints[1] = ((int)(startXd + vecY * scaleWidth)); this.yPoints[1] = ((int)(startYd + -vecX * scaleHeight));
/* 336 */     this.xPoints[2] = ((int)(startXd + -vecY * scaleWidth)); this.yPoints[2] = ((int)(startYd + vecX * scaleHeight));
/* 337 */     Polygon poly = new Polygon(this.xPoints, this.yPoints, 3);
/* 338 */     return poly.intersects(range.clip.x - 5.0D, range.clip.y - 5.0D, range.clip.width + 10.0D, range.clip.height + 10.0D);
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/* 345 */     return "Edge: " + super.getName(wrapper);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.network.SimpleEdgePortrayal2D
 * JD-Core Version:    0.6.2
 */