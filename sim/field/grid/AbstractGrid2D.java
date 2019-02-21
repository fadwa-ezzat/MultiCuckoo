/*     */ package sim.field.grid;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.IntBag;
/*     */ 
/*     */ public abstract class AbstractGrid2D
/*     */   implements Grid2D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected int width;
/*     */   protected int height;
/*     */ 
/*     */   public final int getWidth()
/*     */   {
/*  40 */     return this.width;
/*     */   }
/*  42 */   public final int getHeight() { return this.height; } 
/*     */   public Map buildMap(Map other) {
/*  44 */     return new HashMap(other);
/*     */   }
/*     */   public Map buildMap(int size) {
/*  47 */     if (size <= 0) return new HashMap();
/*  48 */     return new HashMap(size);
/*     */   }
/*     */ 
/*     */   public final int tx(int x)
/*     */   {
/*  54 */     int width = this.width;
/*  55 */     if ((x >= 0) && (x < width)) return x;
/*  56 */     x %= width;
/*  57 */     if (x < 0) x += width;
/*  58 */     return x;
/*     */   }
/*     */ 
/*     */   public final int ty(int y)
/*     */   {
/*  64 */     int height = this.height;
/*  65 */     if ((y >= 0) && (y < height)) return y;
/*  66 */     y %= height;
/*  67 */     if (y < 0) y += height;
/*  68 */     return y;
/*     */   }
/*     */ 
/*     */   public final int stx(int x) {
/*  72 */     if (x >= 0) { if (x < this.width) return x; return x - this.width; } return x + this.width;
/*     */   }
/*     */ 
/*     */   public final int sty(int y) {
/*  76 */     if (y >= 0) { if (y < this.height) return y; return y - this.height; } return y + this.height;
/*     */   }
/*  78 */   public final int ulx(int x, int y) { return x - 1; } 
/*     */   public final int uly(int x, int y) {
/*  80 */     if ((x & 0x1) == 0) return y - 1; return y;
/*     */   }
/*  82 */   public final int urx(int x, int y) { return x + 1; } 
/*     */   public final int ury(int x, int y) {
/*  84 */     if ((x & 0x1) == 0) return y - 1; return y;
/*     */   }
/*  86 */   public final int dlx(int x, int y) { return x - 1; } 
/*     */   public final int dly(int x, int y) {
/*  88 */     if ((x & 0x1) == 0) return y; return y + 1;
/*     */   }
/*  90 */   public final int drx(int x, int y) { return x + 1; } 
/*     */   public final int dry(int x, int y) {
/*  92 */     if ((x & 0x1) == 0) return y; return y + 1;
/*     */   }
/*  94 */   public final int upx(int x, int y) { return x; } 
/*     */   public final int upy(int x, int y) {
/*  96 */     return y - 1;
/*     */   }
/*  98 */   public final int downx(int x, int y) { return x; } 
/*     */   public final int downy(int x, int y) {
/* 100 */     return y + 1;
/*     */   }
/* 102 */   public boolean trb(int x, int y) { return (x + y & 0x1) == 1; } 
/*     */   public boolean trt(int x, int y) {
/* 104 */     return (x + y & 0x1) == 0;
/*     */   }
/*     */ 
/*     */   int tx(int x, int width, int widthtimestwo, int xpluswidth, int xminuswidth)
/*     */   {
/* 110 */     if ((x >= -width) && (x < widthtimestwo))
/*     */     {
/* 112 */       if (x < 0) return xpluswidth;
/* 113 */       if (x < width) return x;
/* 114 */       return xminuswidth;
/*     */     }
/* 116 */     return tx2(x, width);
/*     */   }
/*     */ 
/*     */   int tx2(int x, int width)
/*     */   {
/* 123 */     x %= width;
/* 124 */     if (x < 0) x += width;
/* 125 */     return x;
/*     */   }
/*     */ 
/*     */   int ty(int y, int height, int heighttimestwo, int yplusheight, int yminusheight)
/*     */   {
/* 131 */     if ((y >= -height) && (y < heighttimestwo))
/*     */     {
/* 133 */       if (y < 0) return yplusheight;
/* 134 */       if (y < height) return y;
/* 135 */       return yminusheight;
/*     */     }
/* 137 */     return ty2(y, height);
/*     */   }
/*     */ 
/*     */   int ty2(int y, int height)
/*     */   {
/* 143 */     y %= height;
/* 144 */     if (y < 0) y += height;
/* 145 */     return y;
/*     */   }
/*     */ 
/*     */   protected void removeOrigin(int x, int y, IntBag xPos, IntBag yPos)
/*     */   {
/* 150 */     int size = xPos.size();
/* 151 */     for (int i = 0; i < size; i++)
/*     */     {
/* 153 */       if ((xPos.get(i) == x) && (yPos.get(i) == y))
/*     */       {
/* 155 */         xPos.remove(i);
/* 156 */         yPos.remove(i);
/* 157 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void removeOriginToroidal(int x, int y, IntBag xPos, IntBag yPos)
/*     */   {
/* 165 */     int size = xPos.size();
/* 166 */     x = tx(x, this.width, this.width * 2, x + this.width, x - this.width);
/* 167 */     y = ty(y, this.height, this.height * 2, y + this.height, y - this.height);
/*     */ 
/* 169 */     for (int i = 0; i < size; i++)
/*     */     {
/* 171 */       if ((tx(xPos.get(i), this.width, this.width * 2, x + this.width, x - this.width) == x) && (ty(yPos.get(i), this.height, this.height * 2, y + this.height, y - this.height) == y))
/*     */       {
/* 173 */         xPos.remove(i);
/* 174 */         yPos.remove(i);
/* 175 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsMaxDistance(int x, int y, int dist, boolean toroidal, IntBag xPos, IntBag yPos)
/*     */   {
/* 183 */     getMooreLocations(x, y, dist, toroidal ? 2 : 0, true, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public void getMooreLocations(int x, int y, int dist, int mode, boolean includeOrigin, IntBag xPos, IntBag yPos)
/*     */   {
/* 188 */     boolean toroidal = mode == 2;
/* 189 */     boolean bounded = mode == 0;
/*     */ 
/* 191 */     if ((mode != 0) && (mode != 1) && (mode != 2))
/*     */     {
/* 193 */       throw new RuntimeException("Mode must be either Grid2D.BOUNDED, Grid2D.UNBOUNDED, or Grid2D.TOROIDAL");
/*     */     }
/*     */ 
/* 197 */     if (dist < 0)
/*     */     {
/* 199 */       throw new RuntimeException("Distance must be positive");
/*     */     }
/*     */ 
/* 202 */     if ((xPos == null) || (yPos == null))
/*     */     {
/* 204 */       throw new RuntimeException("xPos and yPos should not be null");
/*     */     }
/*     */ 
/* 207 */     if (((x < 0) || (x >= this.width) || (y < 0) || (y >= this.height)) && (!bounded)) {
/* 208 */       throw new RuntimeException("Invalid initial position");
/*     */     }
/* 210 */     xPos.clear();
/* 211 */     yPos.clear();
/*     */ 
/* 214 */     int height = this.height;
/* 215 */     int width = this.width;
/*     */ 
/* 219 */     if (toroidal)
/*     */     {
/* 222 */       int xmin = x - dist;
/* 223 */       int xmax = x + dist;
/*     */ 
/* 226 */       if (xmax - xmin >= width) {
/* 227 */         xmin = 0; xmax = width - 1;
/*     */       }
/*     */ 
/* 230 */       int ymin = y - dist;
/* 231 */       int ymax = y + dist;
/*     */ 
/* 234 */       if (ymax - ymin >= height) {
/* 235 */         ymin = 0; ymax = width - 1;
/*     */       }
/* 237 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*     */       {
/* 239 */         int x_0 = tx(x0, width, width * 2, x0 + width, x0 - width);
/* 240 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*     */         {
/* 242 */           int y_0 = ty(y0, height, height * 2, y0 + height, y0 - height);
/* 243 */           xPos.add(x_0);
/* 244 */           yPos.add(y_0);
/*     */         }
/*     */       }
/* 247 */       if (!includeOrigin) removeOriginToroidal(x, y, xPos, yPos);
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 252 */       int xmin = (x - dist >= 0) || (!bounded) ? x - dist : 0;
/* 253 */       int xmax = (x + dist <= width - 1) || (!bounded) ? x + dist : width - 1;
/*     */ 
/* 255 */       int ymin = (y - dist >= 0) || (!bounded) ? y - dist : 0;
/* 256 */       int ymax = (y + dist <= height - 1) || (!bounded) ? y + dist : height - 1;
/* 257 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*     */       {
/* 259 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*     */         {
/* 261 */           xPos.add(x0);
/* 262 */           yPos.add(y0);
/*     */         }
/*     */       }
/* 265 */       if (!includeOrigin) removeOrigin(x, y, xPos, yPos);
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsHamiltonianDistance(int x, int y, int dist, boolean toroidal, IntBag xPos, IntBag yPos)
/*     */   {
/* 273 */     getVonNeumannLocations(x, y, dist, toroidal ? 2 : 0, true, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public void getVonNeumannLocations(int x, int y, int dist, int mode, boolean includeOrigin, IntBag xPos, IntBag yPos)
/*     */   {
/* 279 */     boolean toroidal = mode == 2;
/* 280 */     boolean bounded = mode == 0;
/*     */ 
/* 282 */     if ((mode != 0) && (mode != 1) && (mode != 2))
/*     */     {
/* 284 */       throw new RuntimeException("Mode must be either Grid2D.BOUNDED, Grid2D.UNBOUNDED, or Grid2D.TOROIDAL");
/*     */     }
/*     */ 
/* 288 */     if (dist < 0)
/*     */     {
/* 290 */       throw new RuntimeException("Distance must be positive");
/*     */     }
/*     */ 
/* 293 */     if ((xPos == null) || (yPos == null))
/*     */     {
/* 295 */       throw new RuntimeException("xPos and yPos should not be null");
/*     */     }
/*     */ 
/* 298 */     if (((x < 0) || (x >= this.width) || (y < 0) || (y >= this.height)) && (!bounded)) {
/* 299 */       throw new RuntimeException("Invalid initial position");
/*     */     }
/* 301 */     xPos.clear();
/* 302 */     yPos.clear();
/*     */ 
/* 305 */     int height = this.height;
/* 306 */     int width = this.width;
/*     */ 
/* 309 */     if (toroidal)
/*     */     {
/* 312 */       int xmax = x + dist;
/* 313 */       int xmin = x - dist;
/*     */ 
/* 315 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*     */       {
/* 317 */         int x_0 = tx(x0, width, width * 2, x0 + width, x0 - width);
/*     */ 
/* 319 */         int ymax = y + (dist - (x0 - x >= 0 ? x0 - x : x - x0));
/* 320 */         int ymin = y - (dist - (x0 - x >= 0 ? x0 - x : x - x0));
/* 321 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*     */         {
/* 323 */           int y_0 = ty(y0, height, height * 2, y0 + height, y0 - height);
/* 324 */           xPos.add(x_0);
/* 325 */           yPos.add(y_0);
/*     */         }
/*     */       }
/*     */ 
/* 329 */       if ((dist * 2 >= width) || (dist * 2 >= height))
/*     */       {
/* 331 */         int sz = xPos.size();
/* 332 */         Map map = buildMap(sz);
/* 333 */         for (int i = 0; i < sz; i++)
/*     */         {
/* 335 */           Double2D elem = new Double2D(xPos.get(i), yPos.get(i));
/* 336 */           if (map.containsKey(elem))
/*     */           {
/* 338 */             xPos.remove(i);
/* 339 */             yPos.remove(i);
/* 340 */             i--;
/* 341 */             sz--;
/*     */           }
/*     */           else
/*     */           {
/* 345 */             map.put(elem, elem);
/*     */           }
/*     */         }
/*     */       }
/* 349 */       if (!includeOrigin) removeOriginToroidal(x, y, xPos, yPos);
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 354 */       int xmax = (x + dist <= width - 1) || (!bounded) ? x + dist : width - 1;
/* 355 */       int xmin = (x - dist >= 0) || (!bounded) ? x - dist : 0;
/* 356 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*     */       {
/* 360 */         int ymax = (y + (dist - (x0 - x >= 0 ? x0 - x : x - x0)) <= height - 1) || (!bounded) ? y + (dist - (x0 - x >= 0 ? x0 - x : x - x0)) : height - 1;
/* 361 */         int ymin = (y - (dist - (x0 - x >= 0 ? x0 - x : x - x0)) >= 0) || (!bounded) ? y - (dist - (x0 - x >= 0 ? x0 - x : x - x0)) : 0;
/* 362 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*     */         {
/* 364 */           xPos.add(x0);
/* 365 */           yPos.add(y0);
/*     */         }
/*     */       }
/* 368 */       if (!includeOrigin) removeOrigin(x, y, xPos, yPos);
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsHexagonalDistance(int x, int y, int dist, boolean toroidal, IntBag xPos, IntBag yPos)
/*     */   {
/* 376 */     getHexagonalLocations(x, y, dist, toroidal ? 2 : 0, true, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public void getHexagonalLocations(int x, int y, int dist, int mode, boolean includeOrigin, IntBag xPos, IntBag yPos)
/*     */   {
/* 381 */     boolean toroidal = mode == 2;
/* 382 */     boolean bounded = mode == 0;
/*     */ 
/* 384 */     if ((mode != 0) && (mode != 1) && (mode != 2))
/*     */     {
/* 386 */       throw new RuntimeException("Mode must be either Grid2D.BOUNDED, Grid2D.UNBOUNDED, or Grid2D.TOROIDAL");
/*     */     }
/*     */ 
/* 390 */     if (dist < 0)
/*     */     {
/* 392 */       throw new RuntimeException("Distance must be positive");
/*     */     }
/*     */ 
/* 395 */     if ((xPos == null) || (yPos == null))
/*     */     {
/* 397 */       throw new RuntimeException("xPos and yPos should not be null");
/*     */     }
/*     */ 
/* 400 */     if (((x < 0) || (x >= this.width) || (y < 0) || (y >= this.height)) && (!bounded)) {
/* 401 */       throw new RuntimeException("Invalid initial position");
/*     */     }
/* 403 */     xPos.clear();
/* 404 */     yPos.clear();
/*     */ 
/* 407 */     int height = this.height;
/* 408 */     int width = this.width;
/*     */ 
/* 410 */     if ((toroidal) && (height % 2 == 1)) {
/* 411 */       throw new RuntimeException("toroidal hexagonal environment should have even heights");
/*     */     }
/* 413 */     if (toroidal)
/*     */     {
/* 416 */       int ymin = y - dist;
/* 417 */       int ymax = y + dist;
/* 418 */       for (int y0 = ymin; y0 <= ymax; y0 = downy(x, y0))
/*     */       {
/* 420 */         xPos.add(tx(x, width, width * 2, x + width, x - width));
/* 421 */         yPos.add(ty(y0, height, height * 2, y0 + height, y0 - height));
/*     */       }
/* 423 */       int x0 = x;
/* 424 */       for (int i = 1; i <= dist; i++)
/*     */       {
/* 426 */         int temp_ymin = ymin;
/* 427 */         ymin = dly(x0, ymin);
/* 428 */         ymax = uly(x0, ymax);
/* 429 */         x0 = dlx(x0, temp_ymin);
/* 430 */         for (int y0 = ymin; y0 <= ymax; y0 = downy(x0, y0))
/*     */         {
/* 432 */           xPos.add(tx(x0, width, width * 2, x0 + width, x0 - width));
/* 433 */           yPos.add(ty(y0, height, height * 2, y0 + height, y0 - height));
/*     */         }
/*     */       }
/* 436 */       x0 = x;
/* 437 */       ymin = y - dist;
/* 438 */       ymax = y + dist;
/* 439 */       for (int i = 1; i <= dist; i++)
/*     */       {
/* 441 */         int temp_ymin = ymin;
/* 442 */         ymin = dry(x0, ymin);
/* 443 */         ymax = ury(x0, ymax);
/* 444 */         x0 = drx(x0, temp_ymin);
/* 445 */         for (int y0 = ymin; y0 <= ymax; y0 = downy(x0, y0))
/*     */         {
/* 447 */           xPos.add(tx(x0, width, width * 2, x0 + width, x0 - width));
/* 448 */           yPos.add(ty(y0, height, height * 2, y0 + height, y0 - height));
/*     */         }
/*     */       }
/*     */ 
/* 452 */       if ((dist * 2 >= width) || (dist * 2 >= height))
/*     */       {
/* 454 */         int sz = xPos.size();
/* 455 */         Map map = buildMap(sz);
/* 456 */         for (int i = 0; i < sz; i++)
/*     */         {
/* 458 */           Double2D elem = new Double2D(xPos.get(i), yPos.get(i));
/* 459 */           if (map.containsKey(elem))
/*     */           {
/* 461 */             xPos.remove(i);
/* 462 */             yPos.remove(i);
/* 463 */             i--;
/* 464 */             sz--;
/*     */           }
/*     */           else
/*     */           {
/* 468 */             map.put(elem, elem);
/*     */           }
/*     */         }
/*     */       }
/* 472 */       if (!includeOrigin) removeOriginToroidal(x, y, xPos, yPos);
/*     */     }
/*     */     else
/*     */     {
/* 476 */       int ymin = y - dist;
/* 477 */       int ymax = y + dist;
/*     */ 
/* 480 */       int ylBound = (ymin >= 0) || (!bounded) ? ymin : 0;
/* 481 */       int yuBound = (ymax < height) || (!bounded) ? ymax : height - 1;
/*     */ 
/* 484 */       for (int y0 = ylBound; y0 <= yuBound; y0 = downy(x, y0))
/*     */       {
/* 486 */         xPos.add(x);
/* 487 */         yPos.add(y0);
/*     */       }
/*     */ 
/* 491 */       int x0 = x;
/* 492 */       ymin = y - dist;
/* 493 */       ymax = y + dist;
/* 494 */       for (int i = 1; i <= dist; i++)
/*     */       {
/* 496 */         int temp_ymin = ymin;
/* 497 */         ymin = dly(x0, ymin);
/* 498 */         ymax = uly(x0, ymax);
/* 499 */         x0 = dlx(x0, temp_ymin);
/*     */ 
/* 501 */         ylBound = (ymin >= 0) || (!bounded) ? ymin : 0;
/* 502 */         yuBound = (ymax < height) || (!bounded) ? ymax : height - 1;
/*     */ 
/* 506 */         if (x0 >= 0) {
/* 507 */           for (int y0 = ylBound; y0 <= yuBound; y0 = downy(x0, y0))
/*     */           {
/* 509 */             if ((y0 >= 0) || (!bounded))
/*     */             {
/* 511 */               xPos.add(x0);
/* 512 */               yPos.add(y0);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 517 */       x0 = x;
/* 518 */       ymin = y - dist;
/* 519 */       ymax = y + dist;
/* 520 */       for (int i = 1; i <= dist; i++)
/*     */       {
/* 522 */         int temp_ymin = ymin;
/* 523 */         ymin = dry(x0, ymin);
/* 524 */         ymax = ury(x0, ymax);
/* 525 */         x0 = drx(x0, temp_ymin);
/*     */ 
/* 527 */         ylBound = (ymin >= 0) || (!bounded) ? ymin : 0;
/* 528 */         yuBound = (ymax < height) || (!bounded) ? ymax : height - 1;
/*     */ 
/* 532 */         if (x0 < width)
/* 533 */           for (int y0 = ymin; y0 <= yuBound; y0 = downy(x0, y0))
/*     */           {
/* 535 */             if ((y0 >= 0) || (!bounded))
/*     */             {
/* 537 */               xPos.add(x0);
/* 538 */               yPos.add(y0);
/*     */             }
/*     */           }
/*     */       }
/* 542 */       if (!includeOrigin) removeOrigin(x, y, xPos, yPos);
/*     */     }
/*     */   }
/*     */ 
/*     */   double ds(double d1x, double d1y, double d2x, double d2y)
/*     */   {
/* 548 */     return (d1x - d2x) * (d1x - d2x) + (d1y - d2y) * (d1y - d2y);
/*     */   }
/*     */ 
/*     */   boolean within(double d1x, double d1y, double d2x, double d2y, double distanceSquared, boolean closed)
/*     */   {
/* 553 */     double d = ds(d1x, d1y, d2x, d2y);
/* 554 */     return (d < distanceSquared) || ((d == distanceSquared) && (closed));
/*     */   }
/*     */ 
/*     */   public void getRadialLocations(int x, int y, double dist, int mode, boolean includeOrigin, IntBag xPos, IntBag yPos)
/*     */   {
/* 559 */     getRadialLocations(x, y, dist, mode, includeOrigin, 1026, true, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public void getRadialLocations(int x, int y, double dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, IntBag xPos, IntBag yPos)
/*     */   {
/* 564 */     boolean toroidal = mode == 2;
/*     */ 
/* 567 */     if (dist < 0.0D)
/*     */     {
/* 569 */       throw new RuntimeException("Distance must be positive");
/*     */     }
/*     */ 
/* 572 */     if ((measurementRule != 1026) && (measurementRule != 1025) && (measurementRule != 1024))
/*     */     {
/* 574 */       throw new RuntimeException(" Measurement rule must be one of ANY, ALL, or CENTER");
/*     */     }
/*     */ 
/* 578 */     if (toroidal)
/* 579 */       getMooreLocations(x, y, (int)Math.ceil(dist + 0.5D), 1, includeOrigin, xPos, yPos);
/*     */     else
/* 581 */       getMooreLocations(x, y, (int)Math.ceil(dist + 0.5D), mode, includeOrigin, xPos, yPos);
/* 582 */     int len = xPos.size();
/* 583 */     double distsq = dist * dist;
/*     */ 
/* 585 */     int width = this.width;
/* 586 */     int height = this.height;
/* 587 */     int widthtimestwo = width * 2;
/* 588 */     int heighttimestwo = height * 2;
/*     */ 
/* 590 */     for (int i = 0; i < len; i++)
/*     */     {
/* 592 */       int xp = xPos.get(i);
/* 593 */       int yp = yPos.get(i);
/* 594 */       boolean remove = false;
/*     */ 
/* 596 */       if (measurementRule == 1026)
/*     */       {
/* 598 */         if (x == xp)
/*     */         {
/* 600 */           if (y < yp)
/*     */           {
/* 602 */             double d = yp - 0.5D - y;
/* 603 */             remove = (d >= dist) && ((d != dist) || (!closed));
/*     */           }
/*     */           else
/*     */           {
/* 607 */             double d = -(yp - 0.5D - y);
/* 608 */             remove = (d >= dist) && ((d != dist) || (!closed));
/*     */           }
/*     */         }
/* 611 */         else if (y == yp)
/*     */         {
/* 613 */           if (x < xp)
/*     */           {
/* 615 */             double d = xp - 0.5D - x;
/* 616 */             remove = (d >= dist) && ((d != dist) || (!closed));
/*     */           }
/*     */           else
/*     */           {
/* 620 */             double d = -(xp - 0.5D - x);
/* 621 */             remove = (d >= dist) && ((d != dist) || (!closed));
/*     */           }
/*     */         }
/* 624 */         if (x < xp)
/*     */         {
/* 626 */           if (y < yp)
/* 627 */             remove = !within(x, y, xp - 0.5D, yp - 0.5D, distsq, closed);
/*     */           else {
/* 629 */             remove = !within(x, y, xp - 0.5D, yp + 0.5D, distsq, closed);
/*     */           }
/*     */ 
/*     */         }
/* 633 */         else if (y < yp)
/* 634 */           remove = !within(x, y, xp + 0.5D, yp - 0.5D, distsq, closed);
/*     */         else {
/* 636 */           remove = !within(x, y, xp + 0.5D, yp + 0.5D, distsq, closed);
/*     */         }
/*     */       }
/* 639 */       else if (measurementRule == 1025)
/*     */       {
/* 641 */         if (x < xp)
/*     */         {
/* 643 */           if (y < yp)
/* 644 */             remove = !within(x, y, xp + 0.5D, yp + 0.5D, distsq, closed);
/*     */           else {
/* 646 */             remove = !within(x, y, xp + 0.5D, yp - 0.5D, distsq, closed);
/*     */           }
/*     */ 
/*     */         }
/* 650 */         else if (y < yp)
/* 651 */           remove = !within(x, y, xp - 0.5D, yp + 0.5D, distsq, closed);
/*     */         else {
/* 653 */           remove = !within(x, y, xp - 0.5D, yp - 0.5D, distsq, closed);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 658 */         remove = !within(x, y, xp, yp, distsq, closed);
/*     */       }
/*     */ 
/* 661 */       if (remove) {
/* 662 */         xPos.remove(i); yPos.remove(i); i--; len--;
/* 663 */       } else if (toroidal)
/*     */       {
/* 665 */         int _x = xPos.get(i);
/* 666 */         int _y = yPos.get(i);
/* 667 */         xPos.set(i, tx(_x, width, widthtimestwo, _x + width, _x - width));
/* 668 */         yPos.set(i, tx(_y, height, heighttimestwo, _y + width, _y - width));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkBounds(Grid2D other)
/*     */   {
/* 676 */     if ((getHeight() != other.getHeight()) || (getWidth() != other.getWidth()))
/* 677 */       throw new IllegalArgumentException("Grids must be the same dimensions.");
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.grid.AbstractGrid2D
 * JD-Core Version:    0.6.2
 */