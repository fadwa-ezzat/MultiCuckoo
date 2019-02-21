/*     */ package sim.portrayal.simple;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D.Double;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.display.GUIState;
/*     */ import sim.display.Manipulating2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.Oriented2D;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ 
/*     */ public class OrientedPortrayal2D extends SimplePortrayal2D
/*     */ {
/*     */   public static final double DEFAULT_SCALE = 0.5D;
/*     */   public static final int DEFAULT_OFFSET = 0;
/*     */   public static final int SHAPE_LINE = 0;
/*     */   public static final int SHAPE_KITE = 1;
/*     */   public static final int SHAPE_COMPASS = 2;
/*  47 */   int shape = 0;
/*     */   public double scale;
/*     */   public int offset;
/*     */   public Paint paint;
/*     */   public SimplePortrayal2D child;
/*  61 */   boolean showOrientation = true;
/*     */ 
/*  63 */   public boolean drawFilled = true;
/*     */ 
/*  79 */   Shape path = null;
/*     */ 
/*  92 */   boolean onlyDrawWhenSelected = false;
/*     */ 
/* 142 */   int[] simplePolygonX = new int[4];
/* 143 */   int[] simplePolygonY = new int[4];
/* 144 */   double[] simplePolygonXd = new double[4];
/* 145 */   double[] simplePolygonYd = new double[4];
/* 146 */   double lastLength = (0.0D / 0.0D);
/* 147 */   AffineTransform transform = new AffineTransform();
/* 148 */   Stroke stroke = new BasicStroke();
/*     */ 
/* 289 */   boolean orientationHittable = true;
/*     */ 
/*     */   public void setDrawFilled(boolean val)
/*     */   {
/*  64 */     this.drawFilled = val; } 
/*  65 */   public boolean isDrawFilled() { return this.drawFilled; } 
/*     */   public void setShape(int val) {
/*  67 */     if ((val >= 0) && (val <= 2)) this.shape = val; this.path = null; } 
/*  68 */   public int getShape() { return this.shape; } 
/*     */   public boolean isOrientationShowing() {
/*  70 */     return this.showOrientation; } 
/*  71 */   public void setOrientationShowing(boolean val) { this.showOrientation = val; } 
/*     */   /** @deprecated */
/*     */   public boolean isLineShowing() {
/*  74 */     return this.showOrientation;
/*     */   }
/*  77 */   /** @deprecated */
/*     */   public void setLineShowing(boolean val) { this.showOrientation = val; }
/*     */ 
/*     */ 
/*     */   Shape buildPolygon(double[] xpoints, double[] ypoints)
/*     */   {
/*  83 */     GeneralPath path = new GeneralPath();
/*     */ 
/*  86 */     if (xpoints.length > 0) path.moveTo((float)xpoints[0], (float)ypoints[0]);
/*  87 */     for (int i = xpoints.length - 1; i >= 0; i--)
/*  88 */       path.lineTo((float)xpoints[i], (float)ypoints[i]);
/*  89 */     return path;
/*     */   }
/*     */ 
/*     */   public void setOnlyDrawWhenSelected(boolean val)
/*     */   {
/*  94 */     this.onlyDrawWhenSelected = val; } 
/*  95 */   public boolean getOnlyDrawWhenSelected() { return this.onlyDrawWhenSelected; }
/*     */ 
/*     */   public OrientedPortrayal2D(SimplePortrayal2D child, int offset, double scale, Paint paint, int shape)
/*     */   {
/*  99 */     this.offset = offset; this.scale = scale; this.child = child;
/* 100 */     this.paint = paint; setShape(shape);
/*     */   }
/*     */ 
/*     */   public OrientedPortrayal2D(SimplePortrayal2D child, int offset, double scale, Paint paint)
/*     */   {
/* 107 */     this(child, offset, scale, paint, 0);
/*     */   }
/*     */ 
/*     */   public OrientedPortrayal2D(SimplePortrayal2D child)
/*     */   {
/* 114 */     this(child, 0, 0.5D, Color.red);
/*     */   }
/*     */ 
/*     */   public OrientedPortrayal2D(SimplePortrayal2D child, int offset, double scale)
/*     */   {
/* 121 */     this(child, offset, scale, Color.red);
/*     */   }
/*     */ 
/*     */   public OrientedPortrayal2D(SimplePortrayal2D child, Paint paint)
/*     */   {
/* 128 */     this(child, 0, 0.5D, paint);
/*     */   }
/*     */ 
/*     */   public SimplePortrayal2D getChild(Object object)
/*     */   {
/* 133 */     if (this.child != null) return this.child;
/*     */ 
/* 136 */     if (!(object instanceof SimplePortrayal2D))
/* 137 */       throw new RuntimeException("Object provided to OrientedPortrayal2D is not a SimplePortrayal2D: " + object);
/* 138 */     return (SimplePortrayal2D)object;
/*     */   }
/*     */ 
/*     */   public double getOrientation(Object object, DrawInfo2D info)
/*     */   {
/* 156 */     if ((object != null) && ((object instanceof Oriented2D)))
/* 157 */       return ((Oriented2D)object).orientation2D();
/* 158 */     return (0.0D / 0.0D);
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/* 164 */     if ((this.shape == 0) || (!this.drawFilled)) {
/* 165 */       getChild(object).draw(object, graphics, info);
/*     */     }
/* 167 */     if ((this.showOrientation) && ((info.selected) || (!this.onlyDrawWhenSelected)))
/*     */     {
/* 169 */       double theta = getOrientation(object, info);
/* 170 */       if (theta == theta)
/*     */       {
/* 172 */         double length = this.scale * (info.draw.width < info.draw.height ? info.draw.width : info.draw.height) + this.offset;
/*     */ 
/* 174 */         if (length != this.lastLength) {
/* 175 */           this.lastLength = length; this.path = null;
/*     */         }
/* 177 */         graphics.setPaint(this.paint);
/*     */ 
/* 179 */         if (info.precise)
/*     */         {
/* 181 */           this.transform.setToTranslation(info.draw.x, info.draw.y);
/* 182 */           this.transform.rotate(theta);
/*     */ 
/* 184 */           double lenx = 1.0D * length;
/* 185 */           double leny = 0.0D * length;
/* 186 */           switch (this.shape)
/*     */           {
/*     */           case 0:
/*     */           default:
/* 190 */             if (this.path == null)
/*     */             {
/* 192 */               this.path = new Line2D.Double(0.0D, 0.0D, 0.0D, length);
/*     */             }
/* 194 */             graphics.setStroke(this.stroke);
/* 195 */             graphics.draw(this.transform.createTransformedShape(this.path));
/* 196 */             break;
/*     */           case 1:
/* 198 */             if (this.path == null)
/*     */             {
/* 200 */               this.simplePolygonXd[0] = (0.0D + lenx);
/* 201 */               this.simplePolygonYd[0] = (0.0D + leny);
/* 202 */               this.simplePolygonXd[1] = (0.0D + -leny + -lenx);
/* 203 */               this.simplePolygonYd[1] = (0.0D + lenx + -leny);
/* 204 */               this.simplePolygonXd[2] = (0.0D + -lenx / 2.0D);
/* 205 */               this.simplePolygonYd[2] = (0.0D + -leny / 2.0D);
/* 206 */               this.simplePolygonXd[3] = (0.0D + leny + -lenx);
/* 207 */               this.simplePolygonYd[3] = (0.0D + -lenx + -leny);
/* 208 */               this.path = buildPolygon(this.simplePolygonXd, this.simplePolygonYd);
/*     */             }
/* 210 */             if (this.drawFilled) {
/* 211 */               graphics.fill(this.transform.createTransformedShape(this.path));
/*     */             }
/*     */             else {
/* 214 */               graphics.setStroke(this.stroke);
/* 215 */               graphics.draw(this.transform.createTransformedShape(this.path));
/*     */             }
/* 217 */             break;
/*     */           case 2:
/* 219 */             if (this.path == null)
/*     */             {
/* 221 */               this.simplePolygonXd[0] = (0.0D + lenx);
/* 222 */               this.simplePolygonYd[0] = (0.0D + leny);
/* 223 */               this.simplePolygonXd[1] = (0.0D + -leny / 2.0D);
/* 224 */               this.simplePolygonYd[1] = (0.0D + lenx / 2.0D);
/* 225 */               this.simplePolygonXd[2] = (0.0D + -lenx / 2.0D);
/* 226 */               this.simplePolygonYd[2] = (0.0D + -leny / 2.0D);
/* 227 */               this.simplePolygonXd[3] = (0.0D + leny / 2.0D);
/* 228 */               this.simplePolygonYd[3] = (0.0D + -lenx / 2.0D);
/* 229 */               this.path = buildPolygon(this.simplePolygonXd, this.simplePolygonYd);
/*     */             }
/* 231 */             if (this.drawFilled) {
/* 232 */               graphics.fill(this.transform.createTransformedShape(this.path));
/*     */             }
/*     */             else {
/* 235 */               graphics.setStroke(this.stroke);
/* 236 */               graphics.draw(this.transform.createTransformedShape(this.path));
/*     */             }
/*     */             break;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 243 */           double lenx = Math.cos(theta) * length;
/* 244 */           double leny = Math.sin(theta) * length;
/* 245 */           switch (this.shape)
/*     */           {
/*     */           case 0:
/*     */           default:
/* 249 */             graphics.drawLine((int)info.draw.x, (int)info.draw.y, (int)(info.draw.x + lenx), (int)(info.draw.y + leny));
/*     */ 
/* 253 */             break;
/*     */           case 1:
/* 255 */             this.simplePolygonX[0] = ((int)(info.draw.x + lenx));
/* 256 */             this.simplePolygonY[0] = ((int)(info.draw.y + leny));
/* 257 */             this.simplePolygonX[1] = ((int)(info.draw.x + -leny + -lenx));
/* 258 */             this.simplePolygonY[1] = ((int)(info.draw.y + lenx + -leny));
/* 259 */             this.simplePolygonX[2] = ((int)(info.draw.x + -lenx / 2.0D));
/* 260 */             this.simplePolygonY[2] = ((int)(info.draw.y + -leny / 2.0D));
/* 261 */             this.simplePolygonX[3] = ((int)(info.draw.x + leny + -lenx));
/* 262 */             this.simplePolygonY[3] = ((int)(info.draw.y + -lenx + -leny));
/* 263 */             if (this.drawFilled) graphics.fillPolygon(this.simplePolygonX, this.simplePolygonY, 4); else
/* 264 */               graphics.drawPolygon(this.simplePolygonX, this.simplePolygonY, 4);
/* 265 */             break;
/*     */           case 2:
/* 267 */             this.simplePolygonX[0] = ((int)(info.draw.x + lenx));
/* 268 */             this.simplePolygonY[0] = ((int)(info.draw.y + leny));
/* 269 */             this.simplePolygonX[1] = ((int)(info.draw.x + -leny / 2.0D));
/* 270 */             this.simplePolygonY[1] = ((int)(info.draw.y + lenx / 2.0D));
/* 271 */             this.simplePolygonX[2] = ((int)(info.draw.x + -lenx / 2.0D));
/* 272 */             this.simplePolygonY[2] = ((int)(info.draw.y + -leny / 2.0D));
/* 273 */             this.simplePolygonX[3] = ((int)(info.draw.x + leny / 2.0D));
/* 274 */             this.simplePolygonY[3] = ((int)(info.draw.y + -lenx / 2.0D));
/* 275 */             if (this.drawFilled) graphics.fillPolygon(this.simplePolygonX, this.simplePolygonY, 4); else {
/* 276 */               graphics.drawPolygon(this.simplePolygonX, this.simplePolygonY, 4);
/*     */             }
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 284 */     if ((this.shape != 0) && (this.drawFilled))
/* 285 */       getChild(object).draw(object, graphics, info);
/*     */   }
/*     */ 
/*     */   public boolean isOrientationHittable()
/*     */   {
/* 291 */     return this.orientationHittable;
/*     */   }
/* 293 */   public void setOrientationHittable(boolean val) { this.orientationHittable = val; }
/*     */ 
/*     */   public boolean hitObject(Object object, DrawInfo2D range)
/*     */   {
/* 297 */     if (getChild(object).hitObject(object, range)) return true;
/* 298 */     if (!this.orientationHittable) return false;
/*     */ 
/* 302 */     if ((this.showOrientation) && (object != null) && ((object instanceof Oriented2D)))
/*     */     {
/* 304 */       double theta = ((Oriented2D)object).orientation2D();
/* 305 */       double length = this.scale * (range.draw.width < range.draw.height ? range.draw.width : range.draw.height) + this.offset;
/*     */ 
/* 310 */       this.transform.setToTranslation(range.draw.x, range.draw.y);
/* 311 */       this.transform.rotate(theta);
/*     */ 
/* 313 */       double lenx = 1.0D * length;
/* 314 */       double leny = 0.0D * length;
/* 315 */       switch (this.shape) {
/*     */       case 0:
/*     */       default:
/* 318 */         break;
/*     */       case 1:
/* 320 */         if (this.path == null)
/*     */         {
/* 322 */           this.simplePolygonXd[0] = (0.0D + lenx);
/* 323 */           this.simplePolygonYd[0] = (0.0D + leny);
/* 324 */           this.simplePolygonXd[1] = (0.0D + -leny + -lenx);
/* 325 */           this.simplePolygonYd[1] = (0.0D + lenx + -leny);
/* 326 */           this.simplePolygonXd[2] = (0.0D + -lenx / 2.0D);
/* 327 */           this.simplePolygonYd[2] = (0.0D + -leny / 2.0D);
/* 328 */           this.simplePolygonXd[3] = (0.0D + leny + -lenx);
/* 329 */           this.simplePolygonYd[3] = (0.0D + -lenx + -leny);
/* 330 */           this.path = buildPolygon(this.simplePolygonXd, this.simplePolygonYd);
/*     */         }
/* 332 */         return this.transform.createTransformedShape(this.path).intersects(range.clip.x, range.clip.y, range.clip.width, range.clip.height);
/*     */       case 2:
/* 335 */         if (this.path == null)
/*     */         {
/* 337 */           this.simplePolygonXd[0] = (0.0D + lenx);
/* 338 */           this.simplePolygonYd[0] = (0.0D + leny);
/* 339 */           this.simplePolygonXd[1] = (0.0D + -leny / 2.0D);
/* 340 */           this.simplePolygonYd[1] = (0.0D + lenx / 2.0D);
/* 341 */           this.simplePolygonXd[2] = (0.0D + -lenx / 2.0D);
/* 342 */           this.simplePolygonYd[2] = (0.0D + -leny / 2.0D);
/* 343 */           this.simplePolygonXd[3] = (0.0D + leny / 2.0D);
/* 344 */           this.simplePolygonYd[3] = (0.0D + -lenx / 2.0D);
/* 345 */           this.path = buildPolygon(this.simplePolygonXd, this.simplePolygonYd);
/*     */         }
/* 347 */         return this.transform.createTransformedShape(this.path).intersects(range.clip.x, range.clip.y, range.clip.width, range.clip.height);
/*     */       }
/*     */     }
/*     */ 
/* 351 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/* 356 */     return getChild(wrapper.getObject()).setSelected(wrapper, selected);
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/* 361 */     return getChild(wrapper.getObject()).getInspector(wrapper, state);
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/* 366 */     return getChild(wrapper.getObject()).getName(wrapper);
/*     */   }
/*     */ 
/*     */   public boolean handleMouseEvent(GUIState guistate, Manipulating2D manipulating, LocationWrapper wrapper, MouseEvent event, DrawInfo2D fieldPortrayalDrawInfo, int type)
/*     */   {
/* 372 */     return getChild(wrapper.getObject()).handleMouseEvent(guistate, manipulating, wrapper, event, fieldPortrayalDrawInfo, type);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.simple.OrientedPortrayal2D
 * JD-Core Version:    0.6.2
 */