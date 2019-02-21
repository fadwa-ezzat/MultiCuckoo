/*     */ package sim.portrayal.grid;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.SinglePixelPackedSampleModel;
/*     */ import java.awt.image.WritableRaster;
/*     */ import sim.display.Display2D;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ import sim.field.grid.Grid2D;
/*     */ import sim.field.grid.IntGrid2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.MutableDouble;
/*     */ import sim.util.gui.ColorMap;
/*     */ 
/*     */ public class FastHexaValueGridPortrayal2D extends HexaValueGridPortrayal2D
/*     */ {
/*     */   BufferedImage buffer;
/*     */   WritableRaster raster;
/*     */   DataBufferInt dbuffer;
/*  96 */   final MutableDouble valueToPass = new MutableDouble(0.0D);
/*     */ 
/*     */   public FastHexaValueGridPortrayal2D(String valueName, boolean immutableField)
/*     */   {
/*  26 */     super(valueName);
/*  27 */     setImmutableField(immutableField);
/*     */   }
/*     */ 
/*     */   public FastHexaValueGridPortrayal2D(String valueName)
/*     */   {
/*  32 */     this(valueName, false);
/*     */   }
/*     */ 
/*     */   public FastHexaValueGridPortrayal2D(boolean immutableField)
/*     */   {
/*  40 */     setImmutableField(immutableField);
/*     */   }
/*     */ 
/*     */   public FastHexaValueGridPortrayal2D()
/*     */   {
/*  45 */     this(false);
/*     */   }
/*     */ 
/*     */   boolean shouldBuffer(Graphics2D graphics)
/*     */   {
/*  76 */     int buffering = getBuffering();
/*  77 */     if (buffering == 1) return true;
/*  78 */     if (buffering == 2) return false;
/*  79 */     if (Display2D.isMacOSX) {
/*  80 */       return graphics.getDeviceConfiguration().getDevice().getType() != 2;
/*     */     }
/*  82 */     if (Display2D.isWindows) {
/*  83 */       return this.immutableField;
/*     */     }
/*     */ 
/*  86 */     return graphics.getDeviceConfiguration().getDevice().getType() != 2;
/*     */   }
/*     */ 
/*     */   protected void hitOrDraw(Graphics2D graphics, DrawInfo2D info, Bag putInHere)
/*     */   {
/* 100 */     Grid2D field = (Grid2D)this.field;
/* 101 */     if (field == null) return;
/*     */ 
/* 103 */     boolean isDoubleGrid2D = field instanceof DoubleGrid2D;
/* 104 */     int maxX = field.getWidth();
/* 105 */     int maxY = field.getHeight();
/* 106 */     if ((maxX == 0) || (maxY == 0)) return;
/*     */ 
/* 108 */     double divideByX = maxX % 2 == 0 ? 3.0D * maxX / 2.0D + 0.5D : 3.0D * maxX / 2.0D + 2.0D;
/*     */ 
/* 110 */     double scaleWidth = 1.5D * info.draw.width / (maxX % 2 == 0 ? 3.0D * maxX / 2.0D + 0.5D : 3.0D * maxX / 2.0D + 2.0D);
/* 111 */     double translateWidth = info.draw.width / divideByX - scaleWidth / 2.0D;
/*     */ 
/* 113 */     double[][] doubleField = isDoubleGrid2D ? ((DoubleGrid2D)field).field : (double[][])null;
/* 114 */     int[][] intField = isDoubleGrid2D ? (int[][])null : ((IntGrid2D)field).field;
/*     */ 
/* 117 */     double yScale = info.draw.height / (2 * maxY + 1);
/* 118 */     double startxd = (info.clip.x - translateWidth - info.draw.x) / scaleWidth;
/* 119 */     double startyd = (info.clip.y - info.draw.y) / (2.0D * yScale) - 1.0D;
/* 120 */     double endxd = (info.clip.x - translateWidth - info.draw.x + info.clip.width) / scaleWidth;
/* 121 */     double endyd = (info.clip.y - info.draw.y + info.clip.height) / (2.0D * yScale);
/*     */ 
/* 123 */     int startx = (int)startxd;
/* 124 */     int starty = (int)startyd;
/* 125 */     int endx = (int)endxd + 1;
/* 126 */     int endy = (int)endyd + 1;
/*     */ 
/* 132 */     if ((graphics != null) && (shouldBuffer(graphics)))
/*     */     {
/* 135 */       boolean newBuffer = false;
/*     */ 
/* 139 */       if ((this.buffer == null) || (this.buffer.getWidth() != maxX) || (this.buffer.getHeight() != 2 * maxY + 1))
/*     */       {
/* 149 */         if (this.buffer != null) this.buffer.flush();
/* 150 */         this.buffer = new BufferedImage(maxX, 2 * maxY + 1, 2);
/*     */ 
/* 157 */         newBuffer = true;
/* 158 */         this.raster = this.buffer.getRaster();
/* 159 */         this.dbuffer = ((DataBufferInt)this.raster.getDataBuffer());
/*     */       }
/*     */ 
/* 163 */       DataBufferInt _dbuffer = this.dbuffer;
/*     */ 
/* 166 */       if ((newBuffer) || (!this.immutableField) || (isDirtyField()))
/*     */       {
/* 168 */         if (endx > maxX) endx = maxX;
/* 169 */         if (endy > maxY) endy = maxY;
/* 170 */         if (startx < 0) startx = 0;
/* 171 */         if (starty < 0) starty = 0;
/*     */ 
/* 173 */         if (this.immutableField)
/*     */         {
/* 176 */           startx = 0; starty = 0; endx = maxX; endy = maxY;
/*     */         }
/*     */ 
/* 179 */         int ex = endx;
/* 180 */         int ey = endy;
/* 181 */         int sx = startx;
/* 182 */         int sy = starty;
/*     */ 
/* 184 */         ColorMap map = this.map;
/*     */ 
/* 212 */         int scanlineStride = ((SinglePixelPackedSampleModel)this.raster.getSampleModel()).getScanlineStride();
/*     */ 
/* 214 */         if (isDoubleGrid2D) {
/* 215 */           for (int x = sx; x < ex; x++) {
/* 216 */             for (int y = sy; y < ey; y++)
/*     */             {
/* 218 */               if ((x & 0x1) == 0)
/*     */               {
/* 222 */                 int load = map.getRGB(doubleField[x][y]);
/* 223 */                 _dbuffer.setElem(2 * y * scanlineStride + x, load);
/* 224 */                 _dbuffer.setElem((2 * y + 1) * scanlineStride + x, load);
/*     */               }
/*     */               else
/*     */               {
/* 231 */                 int load = map.getRGB(doubleField[x][y]);
/* 232 */                 _dbuffer.setElem((2 * y + 1) * scanlineStride + x, load);
/* 233 */                 _dbuffer.setElem((2 * y + 2) * scanlineStride + x, load);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 240 */           for (int x = sx; x < ex; x++) {
/* 241 */             for (int y = sy; y < ey; y++)
/*     */             {
/* 243 */               if ((x & 0x1) == 0)
/*     */               {
/* 245 */                 int load = map.getRGB(intField[x][y]);
/* 246 */                 _dbuffer.setElem(2 * y * scanlineStride + x, load);
/* 247 */                 _dbuffer.setElem((2 * y + 1) * scanlineStride + x, load);
/*     */               }
/*     */               else
/*     */               {
/* 254 */                 int load = map.getRGB(intField[x][y]);
/* 255 */                 _dbuffer.setElem((2 * y + 1) * scanlineStride + x, load);
/* 256 */                 _dbuffer.setElem((2 * y + 2) * scanlineStride + x, load);
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 266 */       graphics.drawImage(this.buffer, (int)(info.draw.x + translateWidth), (int)info.draw.y, (int)(maxX * scaleWidth), (int)info.draw.height, null);
/*     */     }
/* 268 */     else if (!info.precise)
/*     */     {
/* 270 */       this.buffer = null;
/*     */ 
/* 272 */       if (endx > maxX) endx = maxX;
/* 273 */       if (endy > maxY) endy = maxY;
/* 274 */       if (startx < 0) startx = 0;
/* 275 */       if (starty < 0) starty = 0;
/*     */ 
/* 277 */       int ex = endx;
/* 278 */       int ey = endy;
/* 279 */       int sx = startx;
/* 280 */       int sy = starty;
/*     */ 
/* 282 */       int _x = 0;
/* 283 */       int _y = 0;
/* 284 */       int _width = 0;
/* 285 */       int _height = 0;
/*     */ 
/* 288 */       ColorMap map = this.map;
/* 289 */       double infodrawx = info.draw.x;
/* 290 */       double infodrawy = info.draw.y;
/*     */ 
/* 293 */       if (isDoubleGrid2D)
/* 294 */         for (int x = sx; x < ex; x++)
/* 295 */           for (int y = sy; y < ey; y++)
/*     */           {
/* 297 */             Color c = map.getColor(doubleField[x][y]);
/* 298 */             if (c.getAlpha() != 0)
/*     */             {
/* 300 */               _x = (int)(translateWidth + infodrawx + scaleWidth * x);
/* 301 */               _y = (int)(infodrawy + yScale * ((x & 0x1) == 0 ? 2 * y : 2 * y + 1));
/* 302 */               _width = (int)(translateWidth + infodrawx + scaleWidth * (x + 1)) - _x;
/* 303 */               _height = (int)(infodrawy + yScale * ((x & 0x1) == 0 ? 2 * y + 2 : 2 * y + 3)) - _y;
/*     */ 
/* 308 */               if (graphics != null)
/*     */               {
/* 310 */                 graphics.setColor(c);
/* 311 */                 graphics.fillRect(_x, _y, _width, _height);
/*     */               }
/* 315 */               else if (info.clip.intersects(_x, _y, _width, _height)) {
/* 316 */                 putInHere.add(getWrapper(doubleField[x][y], new Int2D(x, y)));
/*     */               }
/*     */             }
/*     */           }
/* 320 */       else for (int x = sx; x < ex; x++)
/* 321 */           for (int y = sy; y < ey; y++)
/*     */           {
/* 323 */             Color c = map.getColor(intField[x][y]);
/* 324 */             if (c.getAlpha() != 0)
/*     */             {
/* 326 */               _x = (int)(translateWidth + infodrawx + scaleWidth * x);
/* 327 */               _y = (int)(infodrawy + yScale * ((x & 0x1) == 0 ? 2 * y : 2 * y + 1));
/* 328 */               _width = (int)(translateWidth + infodrawx + scaleWidth * (x + 1)) - _x;
/* 329 */               _height = (int)(infodrawy + yScale * ((x & 0x1) == 0 ? 2 * y + 2 : 2 * y + 3)) - _y;
/*     */ 
/* 334 */               if (graphics != null)
/*     */               {
/* 336 */                 graphics.setColor(c);
/* 337 */                 graphics.fillRect(_x, _y, _width, _height);
/*     */               }
/* 341 */               else if (info.clip.intersects(_x, _y, _width, _height)) {
/* 342 */                 putInHere.add(getWrapper(intField[x][y], new Int2D(x, y)));
/*     */               }
/*     */             }
/*     */           } 
/*     */     }
/*     */     else
/*     */     {
/* 348 */       graphics.setStroke(new BasicStroke(0.0F));
/* 349 */       Rectangle2D.Double preciseRectangle = new Rectangle2D.Double();
/* 350 */       this.buffer = null;
/*     */ 
/* 352 */       if (endxd > maxX) endxd = maxX;
/* 353 */       if (endyd > maxY) endyd = maxY;
/* 354 */       if (startxd < 0.0D) startxd = 0.0D;
/* 355 */       if (startyd < 0.0D) startyd = 0.0D;
/*     */ 
/* 357 */       double _x = 0.0D;
/* 358 */       double _y = 0.0D;
/* 359 */       double _width = 0.0D;
/* 360 */       double _height = 0.0D;
/*     */ 
/* 363 */       ColorMap map = this.map;
/* 364 */       double infodrawx = info.draw.x;
/* 365 */       double infodrawy = info.draw.y;
/*     */ 
/* 368 */       if (isDoubleGrid2D)
/* 369 */         for (double x = startxd; x < endxd; x += 1.0D)
/* 370 */           for (double y = startyd; y < endyd; y += 1.0D)
/*     */           {
/* 372 */             Color c = map.getColor(doubleField[((int)x)][((int)y)]);
/* 373 */             if (c.getAlpha() != 0) {
/* 374 */               graphics.setColor(c);
/*     */ 
/* 376 */               _x = translateWidth + infodrawx + scaleWidth * x;
/* 377 */               _y = infodrawy + yScale * (((int)x & 0x1) == 0 ? 2.0D * y : 2.0D * y + 1.0D);
/* 378 */               _width = translateWidth + infodrawx + scaleWidth * (x + 1.0D) - _x;
/* 379 */               _height = infodrawy + yScale * (((int)x & 0x1) == 0 ? 2.0D * y + 2.0D : 2.0D * y + 3.0D) - _y;
/*     */ 
/* 381 */               preciseRectangle.setFrame(_x, _y, _width, _height);
/* 382 */               graphics.fill(preciseRectangle);
/* 383 */               graphics.draw(preciseRectangle);
/*     */             }
/*     */           }
/* 386 */       else for (double x = startxd; x < endxd; x += 1.0D)
/* 387 */           for (double y = startyd; y < endyd; y += 1.0D)
/*     */           {
/* 389 */             Color c = map.getColor(intField[((int)x)][((int)y)]);
/* 390 */             if (c.getAlpha() != 0) {
/* 391 */               graphics.setColor(c);
/*     */ 
/* 393 */               _x = translateWidth + infodrawx + scaleWidth * x;
/* 394 */               _y = infodrawy + yScale * (((int)x & 0x1) == 0 ? 2.0D * y : 2.0D * y + 1.0D);
/* 395 */               _width = translateWidth + infodrawx + scaleWidth * (x + 1.0D) - _x;
/* 396 */               _height = infodrawy + yScale * (((int)x & 0x1) == 0 ? 2.0D * y + 2.0D : 2.0D * y + 3.0D) - _y;
/*     */ 
/* 398 */               preciseRectangle.setFrame(_x, _y, _width, _height);
/* 399 */               graphics.fill(preciseRectangle);
/* 400 */               graphics.draw(preciseRectangle);
/*     */             }
/*     */           }
/*     */     }
/* 404 */     if (graphics != null) setDirtyField(false);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.grid.FastHexaValueGridPortrayal2D
 * JD-Core Version:    0.6.2
 */