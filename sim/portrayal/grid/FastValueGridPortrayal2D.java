/*     */ package sim.portrayal.grid;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.WritableRaster;
/*     */ import sim.display.Display2D;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ import sim.field.grid.Grid2D;
/*     */ import sim.field.grid.IntGrid2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.util.gui.ColorMap;
/*     */ 
/*     */ public class FastValueGridPortrayal2D extends ValueGridPortrayal2D
/*     */ {
/*     */   BufferedImage buffer;
/*     */   WritableRaster raster;
/* 105 */   int[] data = new int[0];
/*     */ 
/*     */   public FastValueGridPortrayal2D(String valueName, boolean immutableField)
/*     */   {
/*  48 */     super(valueName);
/*  49 */     setImmutableField(immutableField);
/*     */   }
/*     */ 
/*     */   public FastValueGridPortrayal2D(String valueName)
/*     */   {
/*  54 */     this(valueName, false);
/*     */   }
/*     */ 
/*     */   public FastValueGridPortrayal2D(boolean immutableField)
/*     */   {
/*  62 */     setImmutableField(immutableField);
/*     */   }
/*     */ 
/*     */   public FastValueGridPortrayal2D()
/*     */   {
/*  67 */     this(false);
/*     */   }
/*     */ 
/*     */   boolean shouldBuffer(Graphics2D graphics)
/*     */   {
/*  88 */     int buffering = getBuffering();
/*  89 */     if (buffering == 1) return true;
/*  90 */     if (buffering == 2) return false;
/*  91 */     if (Display2D.isMacOSX) {
/*  92 */       return graphics.getDeviceConfiguration().getDevice().getType() != 2;
/*     */     }
/*  94 */     if (Display2D.isWindows) {
/*  95 */       return this.immutableField;
/*     */     }
/*     */ 
/*  98 */     return graphics.getDeviceConfiguration().getDevice().getType() != 2;
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/* 110 */     Grid2D field = (Grid2D)this.field;
/* 111 */     if (field == null) return;
/*     */ 
/* 114 */     int maxX = field.getWidth();
/* 115 */     int maxY = field.getHeight();
/* 116 */     if ((maxX == 0) || (maxY == 0)) return;
/*     */ 
/* 119 */     double xScale = info.draw.width / maxX;
/* 120 */     double yScale = info.draw.height / maxY;
/* 121 */     double startxd = (info.clip.x - info.draw.x) / xScale;
/* 122 */     double startyd = (info.clip.y - info.draw.y) / yScale;
/* 123 */     double endxd = (info.clip.x - info.draw.x + info.clip.width) / xScale;
/* 124 */     double endyd = (info.clip.y - info.draw.y + info.clip.height) / yScale;
/*     */ 
/* 127 */     int startx = (int)startxd;
/* 128 */     int starty = (int)startyd;
/* 129 */     int endx = (int)endxd + 1;
/* 130 */     int endy = (int)endyd + 1;
/*     */ 
/* 136 */     boolean isDoubleGrid2D = field instanceof DoubleGrid2D;
/* 137 */     double[][] doubleField = isDoubleGrid2D ? ((DoubleGrid2D)field).field : (double[][])null;
/* 138 */     int[][] intField = isDoubleGrid2D ? (int[][])null : ((IntGrid2D)field).field;
/*     */ 
/* 141 */     if (shouldBuffer(graphics))
/*     */     {
/* 144 */       boolean newBuffer = false;
/*     */ 
/* 149 */       if ((this.buffer == null) || (this.buffer.getWidth() != maxX) || (this.buffer.getHeight() != maxY))
/*     */       {
/* 159 */         if (this.buffer != null) this.buffer.flush();
/* 160 */         this.buffer = new BufferedImage(maxX, maxY, 2);
/*     */ 
/* 171 */         this.raster = this.buffer.getRaster();
/* 172 */         newBuffer = true;
/*     */       }
/*     */ 
/* 177 */       if ((newBuffer) || (!this.immutableField) || (isDirtyField()))
/*     */       {
/* 179 */         if (endx > maxX) endx = maxX;
/* 180 */         if (endy > maxY) endy = maxY;
/* 181 */         if (startx < 0) startx = 0;
/* 182 */         if (starty < 0) starty = 0;
/*     */ 
/* 184 */         int ex = endx;
/* 185 */         int ey = endy;
/* 186 */         int sx = startx;
/* 187 */         int sy = starty;
/*     */ 
/* 189 */         if (this.immutableField)
/*     */         {
/* 192 */           startx = 0; starty = 0; endx = maxX; endy = maxY;
/*     */         }
/*     */ 
/* 195 */         ColorMap map = this.map;
/*     */ 
/* 197 */         if ((ex - sx > 0) && (ey - sy > 0))
/*     */         {
/* 199 */           int[] data = this.data;
/* 200 */           if (data.length != (ex - sx) * (ey - sy))
/* 201 */             data = this.data = new int[(ex - sx) * (ey - sy)];
/* 202 */           int i = 0;
/* 203 */           if (isDoubleGrid2D)
/* 204 */             for (int y = sy; y < ey; y++)
/* 205 */               for (int x = sx; x < ex; x++)
/* 206 */                 data[(i++)] = map.getRGB(doubleField[x][y]);
/*     */           else
/* 208 */             for (int y = sy; y < ey; y++)
/* 209 */               for (int x = sx; x < ex; x++)
/* 210 */                 data[(i++)] = map.getRGB(intField[x][y]);
/* 211 */           this.raster.setDataElements(sx, sy, ex - sx, ey - sy, data);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 217 */       graphics.drawImage(this.buffer, (int)info.draw.x, (int)info.draw.y, (int)info.draw.width, (int)info.draw.height, null);
/*     */     }
/* 219 */     else if (!info.precise)
/*     */     {
/* 221 */       this.buffer = null;
/*     */ 
/* 223 */       if (endx > maxX) endx = maxX;
/* 224 */       if (endy > maxY) endy = maxY;
/* 225 */       if (startx < 0) startx = 0;
/* 226 */       if (starty < 0) starty = 0;
/*     */ 
/* 228 */       int ex = endx;
/* 229 */       int ey = endy;
/* 230 */       int sx = startx;
/* 231 */       int sy = starty;
/*     */ 
/* 233 */       int _x = 0;
/* 234 */       int _y = 0;
/* 235 */       int _width = 0;
/* 236 */       int _height = 0;
/*     */ 
/* 239 */       ColorMap map = this.map;
/* 240 */       double infodrawx = info.draw.x;
/* 241 */       double infodrawy = info.draw.y;
/*     */ 
/* 244 */       if (isDoubleGrid2D)
/* 245 */         for (int x = sx; x < ex; x++)
/* 246 */           for (int y = sy; y < ey; y++)
/*     */           {
/* 248 */             Color c = map.getColor(doubleField[x][y]);
/* 249 */             if (c.getAlpha() != 0) {
/* 250 */               graphics.setColor(c);
/*     */ 
/* 252 */               _x = (int)(infodrawx + xScale * x);
/* 253 */               _y = (int)(infodrawy + yScale * y);
/* 254 */               _width = (int)(infodrawx + xScale * (x + 1)) - _x;
/* 255 */               _height = (int)(infodrawy + yScale * (y + 1)) - _y;
/*     */ 
/* 260 */               graphics.fillRect(_x, _y, _width, _height);
/*     */             }
/*     */           }
/* 263 */       else for (int x = sx; x < ex; x++)
/* 264 */           for (int y = sy; y < ey; y++)
/*     */           {
/* 266 */             Color c = map.getColor(intField[x][y]);
/* 267 */             if (c.getAlpha() != 0) {
/* 268 */               graphics.setColor(c);
/*     */ 
/* 270 */               _x = (int)(infodrawx + xScale * x);
/* 271 */               _y = (int)(infodrawy + yScale * y);
/* 272 */               _width = (int)(infodrawx + xScale * (x + 1)) - _x;
/* 273 */               _height = (int)(infodrawy + yScale * (y + 1)) - _y;
/*     */ 
/* 278 */               graphics.fillRect(_x, _y, _width, _height);
/*     */             }
/*     */           } 
/*     */     }
/*     */     else
/*     */     {
/* 283 */       graphics.setStroke(new BasicStroke(0.0F));
/* 284 */       Rectangle2D.Double preciseRectangle = new Rectangle2D.Double();
/* 285 */       this.buffer = null;
/*     */ 
/* 287 */       if (endxd > maxX) endxd = maxX;
/* 288 */       if (endyd > maxY) endyd = maxY;
/* 289 */       if (startxd < 0.0D) startxd = 0.0D;
/* 290 */       if (startyd < 0.0D) startyd = 0.0D;
/*     */ 
/* 292 */       double _x = 0.0D;
/* 293 */       double _y = 0.0D;
/* 294 */       double _width = 0.0D;
/* 295 */       double _height = 0.0D;
/*     */ 
/* 298 */       ColorMap map = this.map;
/* 299 */       double infodrawx = info.draw.x;
/* 300 */       double infodrawy = info.draw.y;
/*     */ 
/* 303 */       if (isDoubleGrid2D)
/* 304 */         for (double x = startxd; x < endxd; x += 1.0D)
/* 305 */           for (double y = startyd; y < endyd; y += 1.0D)
/*     */           {
/* 307 */             Color c = map.getColor(doubleField[((int)x)][((int)y)]);
/* 308 */             if (c.getAlpha() != 0) {
/* 309 */               graphics.setColor(c);
/*     */ 
/* 311 */               _x = infodrawx + xScale * x;
/* 312 */               _y = infodrawy + yScale * y;
/* 313 */               _width = infodrawx + xScale * (x + 1.0D) - _x;
/* 314 */               _height = infodrawy + yScale * (y + 1.0D) - _y;
/*     */ 
/* 316 */               preciseRectangle.setFrame(_x, _y, _width, _height);
/* 317 */               graphics.fill(preciseRectangle);
/* 318 */               graphics.draw(preciseRectangle);
/*     */             }
/*     */           }
/* 321 */       else for (double x = startxd; x < endxd; x += 1.0D)
/* 322 */           for (double y = startyd; y < endyd; y += 1.0D)
/*     */           {
/* 324 */             Color c = map.getColor(intField[((int)x)][((int)y)]);
/* 325 */             if (c.getAlpha() != 0) {
/* 326 */               graphics.setColor(c);
/*     */ 
/* 328 */               _x = infodrawx + xScale * x;
/* 329 */               _y = infodrawy + yScale * y;
/* 330 */               _width = infodrawx + xScale * (x + 1.0D) - _x;
/* 331 */               _height = infodrawy + yScale * (y + 1.0D) - _y;
/*     */ 
/* 333 */               preciseRectangle.setFrame(_x, _y, _width, _height);
/* 334 */               graphics.fill(preciseRectangle);
/* 335 */               graphics.draw(preciseRectangle);
/*     */             }
/*     */           }
/*     */     }
/* 339 */     drawGrid(graphics, xScale, yScale, maxX, maxY, info);
/* 340 */     drawBorder(graphics, xScale, info);
/*     */ 
/* 343 */     if (graphics != null) setDirtyField(false);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.grid.FastValueGridPortrayal2D
 * JD-Core Version:    0.6.2
 */