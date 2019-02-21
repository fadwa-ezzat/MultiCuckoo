/*     */ package sim.field.grid;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import sim.util.Double3D;
/*     */ import sim.util.IntBag;
/*     */ 
/*     */ public abstract class AbstractGrid3D
/*     */   implements Grid3D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected int width;
/*     */   protected int height;
/*     */   protected int length;
/*     */ 
/*     */   public final int getWidth()
/*     */   {
/*  39 */     return this.width;
/*     */   }
/*  41 */   public final int getHeight() { return this.height; } 
/*     */   public final int getLength() {
/*  43 */     return this.length;
/*     */   }
/*  45 */   public Map buildMap(Map other) { return new HashMap(other); }
/*     */ 
/*     */   public Map buildMap(int size) {
/*  48 */     if (size <= 0) return new HashMap();
/*  49 */     return new HashMap(size);
/*     */   }
/*     */ 
/*     */   public final int tx(int x)
/*     */   {
/*  66 */     int width = this.width;
/*  67 */     if ((x >= 0) && (x < width)) return x;
/*  68 */     x %= width;
/*  69 */     if (x < 0) x += width;
/*  70 */     return x;
/*     */   }
/*     */ 
/*     */   public final int ty(int y)
/*     */   {
/*  87 */     int height = this.height;
/*  88 */     if ((y >= 0) && (y < height)) return y;
/*  89 */     y %= height;
/*  90 */     if (y < 0) y += height;
/*  91 */     return y;
/*     */   }
/*     */ 
/*     */   public final int tz(int z)
/*     */   {
/* 108 */     int length = this.length;
/* 109 */     if ((z >= 0) && (z < length)) return z;
/* 110 */     z %= length;
/* 111 */     if (z < 0) z += this.height;
/* 112 */     return z;
/*     */   }
/*     */ 
/*     */   public final int stx(int x) {
/* 116 */     if (x >= 0) { if (x < this.width) return x; return x - this.width; } return x + this.width;
/*     */   }
/*     */   public final int sty(int y) {
/* 119 */     if (y >= 0) { if (y < this.height) return y; return y - this.height; } return y + this.height;
/*     */   }
/*     */   public final int stz(int z) {
/* 122 */     if (z >= 0) { if (z < this.length) return z; return z - this.length; } return z + this.length;
/*     */   }
/*     */ 
/*     */   final int stx(int x, int width) {
/* 126 */     if (x >= 0) { if (x < width) return x; return x - width; } return x + width;
/*     */   }
/*     */ 
/*     */   final int sty(int y, int height) {
/* 130 */     if (y >= 0) { if (y < height) return y; return y - height; } return y + height;
/*     */   }
/*     */ 
/*     */   public final int stz(int z, int length) {
/* 134 */     if (z >= 0) { if (z < length) return z; return z - length; } return z + length;
/*     */   }
/*     */ 
/*     */   int tx(int x, int width, int widthtimestwo, int xpluswidth, int xminuswidth)
/*     */   {
/* 141 */     if ((x >= -width) && (x < widthtimestwo))
/*     */     {
/* 143 */       if (x < 0) return xpluswidth;
/* 144 */       if (x < width) return x;
/* 145 */       return xminuswidth;
/*     */     }
/* 147 */     return tx2(x, width);
/*     */   }
/*     */ 
/*     */   int tx2(int x, int width)
/*     */   {
/* 153 */     x %= width;
/* 154 */     if (x < 0) x += width;
/* 155 */     return x;
/*     */   }
/*     */ 
/*     */   int ty(int y, int height, int heighttimestwo, int yplusheight, int yminusheight)
/*     */   {
/* 161 */     if ((y >= -height) && (y < heighttimestwo))
/*     */     {
/* 163 */       if (y < 0) return yplusheight;
/* 164 */       if (y < height) return y;
/* 165 */       return yminusheight;
/*     */     }
/* 167 */     return ty2(y, height);
/*     */   }
/*     */ 
/*     */   int ty2(int y, int height)
/*     */   {
/* 173 */     y %= height;
/* 174 */     if (y < 0) y += height;
/* 175 */     return y;
/*     */   }
/*     */ 
/*     */   int tz(int z, int length, int lengthtimestwo, int zpluslength, int zminuslength)
/*     */   {
/* 181 */     if ((z >= -length) && (z < lengthtimestwo))
/*     */     {
/* 183 */       if (z < 0) return zpluslength;
/* 184 */       if (z < length) return z;
/* 185 */       return zminuslength;
/*     */     }
/* 187 */     return tz2(z, length);
/*     */   }
/*     */ 
/*     */   int tz2(int z, int length)
/*     */   {
/* 193 */     z %= length;
/* 194 */     if (z < 0) z += length;
/* 195 */     return z;
/*     */   }
/*     */ 
/*     */   protected void removeOrigin(int x, int y, int z, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 201 */     int size = xPos.size();
/* 202 */     for (int i = 0; i < size; i++)
/*     */     {
/* 204 */       if ((xPos.get(i) == x) && (yPos.get(i) == y) && (zPos.get(i) == z))
/*     */       {
/* 206 */         xPos.remove(i);
/* 207 */         yPos.remove(i);
/* 208 */         zPos.remove(i);
/* 209 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void removeOriginToroidal(int x, int y, int z, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 217 */     int size = xPos.size();
/* 218 */     x = tx(x, this.width, this.width * 2, x + this.width, x - this.width);
/* 219 */     y = ty(y, this.height, this.height * 2, y + this.height, y - this.height);
/* 220 */     z = tz(z, this.length, this.length * 2, z + this.length, z - this.length);
/*     */ 
/* 222 */     for (int i = 0; i < size; i++)
/*     */     {
/* 224 */       if ((tx(xPos.get(i), this.width, this.width * 2, x + this.width, x - this.width) == x) && (ty(yPos.get(i), this.height, this.height * 2, y + this.height, y - this.height) == y) && (tz(zPos.get(i), this.length, this.length * 2, z + this.length, z - this.length) == z))
/*     */       {
/* 228 */         xPos.remove(i);
/* 229 */         yPos.remove(i);
/* 230 */         zPos.remove(i);
/* 231 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsMaxDistance(int x, int y, int z, int dist, boolean toroidal, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 241 */     getMooreLocations(x, y, z, dist, toroidal ? 2 : 0, true, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public void getMooreLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 246 */     boolean toroidal = mode == 2;
/* 247 */     boolean bounded = mode == 0;
/*     */ 
/* 249 */     if ((mode != 0) && (mode != 1) && (mode != 2))
/*     */     {
/* 251 */       throw new RuntimeException("Mode must be either Grid3D.BOUNDED, Grid3D.UNBOUNDED, or Grid3D.TOROIDAL");
/*     */     }
/*     */ 
/* 255 */     if (dist < 0)
/*     */     {
/* 257 */       throw new RuntimeException("Distance must be positive");
/*     */     }
/*     */ 
/* 260 */     if ((xPos == null) || (yPos == null) || (zPos == null))
/*     */     {
/* 262 */       throw new RuntimeException("xPos and yPos and zPos should not be null");
/*     */     }
/*     */ 
/* 265 */     xPos.clear();
/* 266 */     yPos.clear();
/* 267 */     zPos.clear();
/*     */ 
/* 270 */     int height = this.height;
/* 271 */     int width = this.width;
/* 272 */     int length = this.length;
/*     */ 
/* 275 */     if (toroidal)
/*     */     {
/* 278 */       int xmin = x - dist;
/* 279 */       int xmax = x + dist;
/*     */ 
/* 282 */       if (xmax - xmin >= width) {
/* 283 */         xmax = xmin + width - 1;
/*     */       }
/*     */ 
/* 286 */       int ymin = y - dist;
/* 287 */       int ymax = y + dist;
/*     */ 
/* 290 */       if (ymax - ymin >= height) {
/* 291 */         ymax = ymin + height - 1;
/*     */       }
/*     */ 
/* 294 */       int zmin = z - dist;
/* 295 */       int zmax = z + dist;
/*     */ 
/* 298 */       if (zmax - zmin >= length) {
/* 299 */         zmax = zmin + length - 1;
/*     */       }
/*     */ 
/* 302 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*     */       {
/* 304 */         int x_0 = tx(x0, width, width * 2, x0 + width, x0 - width);
/* 305 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*     */         {
/* 307 */           int y_0 = ty(y0, height, height * 2, y0 + height, y0 - height);
/* 308 */           for (int z0 = zmin; z0 <= zmax; z0++)
/*     */           {
/* 310 */             int z_0 = tz(z0, length, length * 2, z0 + length, z0 - length);
/* 311 */             if ((x_0 != x) || (y_0 != y) || (z_0 != z))
/*     */             {
/* 313 */               xPos.add(x_0);
/* 314 */               yPos.add(y_0);
/* 315 */               zPos.add(z_0);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 320 */       if (!includeOrigin) removeOriginToroidal(x, y, z, xPos, yPos, zPos);
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 325 */       int xmin = (x - dist >= 0) || (!bounded) ? x - dist : 0;
/* 326 */       int xmax = (x + dist <= width - 1) || (!bounded) ? x + dist : width - 1;
/*     */ 
/* 328 */       int ymin = (y - dist >= 0) || (!bounded) ? y - dist : 0;
/* 329 */       int ymax = (y + dist <= height - 1) || (!bounded) ? y + dist : height - 1;
/*     */ 
/* 331 */       int zmin = (z - dist >= 0) || (!bounded) ? z - dist : 0;
/* 332 */       int zmax = (z + dist <= length - 1) || (!bounded) ? z + dist : length - 1;
/*     */ 
/* 334 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*     */       {
/* 336 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*     */         {
/* 338 */           for (int z0 = zmin; z0 <= zmax; z0++)
/*     */           {
/* 340 */             xPos.add(x0);
/* 341 */             yPos.add(y0);
/* 342 */             zPos.add(z0);
/*     */           }
/*     */         }
/*     */       }
/* 346 */       if (!includeOrigin) removeOrigin(x, y, z, xPos, yPos, zPos);
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsHamiltonianDistance(int x, int y, int z, int dist, boolean toroidal, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 354 */     getVonNeumannLocations(x, y, z, dist, toroidal ? 2 : 0, true, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public void getVonNeumannLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 360 */     boolean toroidal = mode == 2;
/* 361 */     boolean bounded = mode == 0;
/*     */ 
/* 363 */     if ((mode != 0) && (mode != 1) && (mode != 2))
/*     */     {
/* 365 */       throw new RuntimeException("Mode must be either Grid3D.BOUNDED, Grid3D.UNBOUNDED, or Grid3D.TOROIDAL");
/*     */     }
/*     */ 
/* 369 */     if (dist < 0)
/*     */     {
/* 371 */       throw new RuntimeException("Distance must be positive");
/*     */     }
/*     */ 
/* 374 */     if ((xPos == null) || (yPos == null) || (zPos == null))
/*     */     {
/* 376 */       throw new RuntimeException("xPos and yPos and zPos should not be null");
/*     */     }
/*     */ 
/* 379 */     xPos.clear();
/* 380 */     yPos.clear();
/* 381 */     zPos.clear();
/*     */ 
/* 384 */     int height = this.height;
/* 385 */     int width = this.width;
/* 386 */     int length = this.length;
/*     */ 
/* 389 */     if (toroidal)
/*     */     {
/* 392 */       int xmax = x + dist;
/* 393 */       int xmin = x - dist;
/* 394 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*     */       {
/* 396 */         int x_0 = tx(x0, width, width * 2, x0 + width, x0 - width);
/*     */ 
/* 398 */         int ymax = y + (dist - (x0 - x >= 0 ? x0 - x : x - x0));
/* 399 */         int ymin = y - (dist - (x0 - x >= 0 ? x0 - x : x - x0));
/* 400 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*     */         {
/* 402 */           int y_0 = ty(y0, height, height * 2, y0 + height, y0 - height);
/* 403 */           int zmax = z + (dist - (x0 - x >= 0 ? x0 - x : x - x0) - (y0 - y >= 0 ? y0 - y : y - y0));
/* 404 */           int zmin = z - (dist - (x0 - x >= 0 ? x0 - x : x - x0) - (y0 - y >= 0 ? y0 - y : y - y0));
/* 405 */           for (int z0 = zmin; z0 <= zmax; z0++)
/*     */           {
/* 407 */             int z_0 = tz(z0, length, length * 2, z0 + length, z0 - length);
/* 408 */             if ((x_0 != x) || (y_0 != y) || (z_0 != z))
/*     */             {
/* 410 */               xPos.add(x_0);
/* 411 */               yPos.add(y_0);
/* 412 */               zPos.add(z_0);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 417 */       if ((dist * 2 >= width) || (dist * 2 >= height) || (dist * 2 >= length))
/*     */       {
/* 419 */         int sz = xPos.size();
/* 420 */         Map map = buildMap(sz);
/* 421 */         for (int i = 0; i < sz; i++)
/*     */         {
/* 423 */           Double3D elem = new Double3D(xPos.get(i), yPos.get(i), zPos.get(i));
/* 424 */           if (map.containsKey(elem))
/*     */           {
/* 426 */             xPos.remove(i);
/* 427 */             yPos.remove(i);
/* 428 */             zPos.remove(i);
/* 429 */             i--;
/* 430 */             sz--;
/*     */           }
/*     */           else
/*     */           {
/* 434 */             map.put(elem, elem);
/*     */           }
/*     */         }
/*     */       }
/* 438 */       if (!includeOrigin) removeOriginToroidal(x, y, z, xPos, yPos, zPos);
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 443 */       int xmax = (x + dist <= width - 1) || (!bounded) ? x + dist : width - 1;
/* 444 */       int xmin = (x - dist >= 0) || (!bounded) ? x - dist : 0;
/* 445 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*     */       {
/* 447 */         int x_0 = x0;
/*     */ 
/* 450 */         int ymax = (y + (dist - (x0 - x >= 0 ? x0 - x : x - x0)) <= height - 1) || (!bounded) ? y + (dist - (x0 - x >= 0 ? x0 - x : x - x0)) : height - 1;
/* 451 */         int ymin = (y - (dist - (x0 - x >= 0 ? x0 - x : x - x0)) >= 0) || (!bounded) ? y - (dist - (x0 - x >= 0 ? x0 - x : x - x0)) : 0;
/* 452 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*     */         {
/* 454 */           int y_0 = y0;
/* 455 */           int zmin = (z - (dist - (x0 - x >= 0 ? x0 - x : x - x0) - (y0 - y >= 0 ? y0 - y : y - y0)) >= 0) || (!bounded) ? z - (dist - (x0 - x >= 0 ? x0 - x : x - x0) - (y0 - y >= 0 ? y0 - y : y - y0)) : 0;
/* 456 */           int zmax = (z + (dist - (x0 - x >= 0 ? x0 - x : x - x0) - (y0 - y >= 0 ? y0 - y : y - y0)) <= length - 1) || (!bounded) ? z + (dist - (x0 - x >= 0 ? x0 - x : x - x0) - (y0 - y >= 0 ? y0 - y : y - y0)) : length - 1;
/* 457 */           for (int z0 = zmin; z0 <= zmax; z0++)
/*     */           {
/* 459 */             int z_0 = z0;
/* 460 */             xPos.add(x_0);
/* 461 */             yPos.add(y_0);
/* 462 */             zPos.add(z_0);
/*     */           }
/*     */         }
/*     */       }
/* 466 */       if (!includeOrigin) removeOrigin(x, y, z, xPos, yPos, zPos);
/*     */     }
/*     */   }
/*     */ 
/*     */   double ds(double d1x, double d1y, double d1z, double d2x, double d2y, double d2z)
/*     */   {
/* 474 */     return (d1x - d2x) * (d1x - d2x) + (d1y - d2y) * (d1y - d2y) + (d1z - d2z) * (d1z - d2z);
/*     */   }
/*     */ 
/*     */   boolean within(double d1x, double d1y, double d1z, double d2x, double d2y, double d2z, double distanceSquared, boolean closed)
/*     */   {
/* 479 */     double d = ds(d1x, d1y, d1z, d2x, d2y, d2z);
/* 480 */     return (d < distanceSquared) || ((d == distanceSquared) && (closed));
/*     */   }
/*     */ 
/*     */   public void getRadialLocations(int x, int y, int z, double dist, int mode, boolean includeOrigin, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 485 */     getRadialLocations(x, y, z, dist, mode, includeOrigin, 1026, true, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public void getRadialLocations(int x, int y, int z, double dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 491 */     boolean toroidal = mode == 2;
/*     */ 
/* 494 */     if (dist < 0.0D)
/*     */     {
/* 496 */       throw new RuntimeException("Distance must be positive");
/*     */     }
/*     */ 
/* 499 */     if ((measurementRule != 1026) && (measurementRule != 1025) && (measurementRule != 1024))
/*     */     {
/* 501 */       throw new RuntimeException(" Measurement rule must be one of ANY, ALL, or CENTER");
/*     */     }
/*     */ 
/* 505 */     getMooreLocations(x, y, z, (int)Math.ceil(dist + 0.5D), mode, includeOrigin, xPos, yPos, zPos);
/* 506 */     int len = xPos.size();
/* 507 */     double distsq = dist * dist;
/*     */ 
/* 510 */     int width = this.width;
/* 511 */     int height = this.height;
/* 512 */     int length = this.length;
/* 513 */     int widthtimestwo = width * 2;
/* 514 */     int heighttimestwo = height * 2;
/* 515 */     int lengthtimestwo = length * 2;
/*     */ 
/* 518 */     for (int i = 0; i < len; i++)
/*     */     {
/* 520 */       int xp = xPos.get(i);
/* 521 */       int yp = yPos.get(i);
/* 522 */       int zp = zPos.get(i);
/* 523 */       boolean remove = false;
/*     */ 
/* 525 */       if (measurementRule == 1026)
/*     */       {
/* 527 */         if (z == zp)
/*     */         {
/* 529 */           if (x == xp)
/*     */           {
/* 531 */             if (y < yp)
/*     */             {
/* 533 */               double d = yp - 0.5D - y;
/* 534 */               remove = (d >= dist) && ((d != dist) || (!closed));
/*     */             }
/*     */             else
/*     */             {
/* 538 */               double d = -(yp - 0.5D - y);
/* 539 */               remove = (d >= dist) && ((d != dist) || (!closed));
/*     */             }
/*     */           }
/* 542 */           else if (y == yp)
/*     */           {
/* 544 */             if (x < xp)
/*     */             {
/* 546 */               double d = xp - 0.5D - x;
/* 547 */               remove = (d >= dist) && ((d != dist) || (!closed));
/*     */             }
/*     */             else
/*     */             {
/* 551 */               double d = -(xp - 0.5D - x);
/* 552 */               remove = (d >= dist) && ((d != dist) || (!closed));
/*     */             }
/*     */           }
/*     */         }
/* 556 */         else if (x == xp)
/*     */         {
/* 558 */           if (y == yp)
/*     */           {
/* 560 */             if (z < zp)
/*     */             {
/* 562 */               double d = zp - 0.5D - z;
/* 563 */               remove = (d >= dist) && ((d != dist) || (!closed));
/*     */             }
/*     */             else
/*     */             {
/* 567 */               double d = -(zp - 0.5D - z);
/* 568 */               remove = (d >= dist) && ((d != dist) || (!closed));
/*     */             }
/*     */           }
/*     */         }
/* 572 */         else if (z < zp)
/*     */         {
/* 574 */           if (x < xp)
/*     */           {
/* 576 */             if (y < yp)
/* 577 */               remove = !within(x, y, z, xp - 0.5D, yp - 0.5D, zp - 0.5D, distsq, closed);
/*     */             else {
/* 579 */               remove = !within(x, y, z, xp - 0.5D, yp + 0.5D, zp - 0.5D, distsq, closed);
/*     */             }
/*     */ 
/*     */           }
/* 583 */           else if (y < yp)
/* 584 */             remove = !within(x, y, z, xp + 0.5D, yp - 0.5D, zp - 0.5D, distsq, closed);
/*     */           else {
/* 586 */             remove = !within(x, y, z, xp + 0.5D, yp + 0.5D, zp - 0.5D, distsq, closed);
/*     */           }
/*     */ 
/*     */         }
/* 591 */         else if (x < xp)
/*     */         {
/* 593 */           if (y < yp)
/* 594 */             remove = !within(x, y, z, xp - 0.5D, yp - 0.5D, zp + 0.5D, distsq, closed);
/*     */           else {
/* 596 */             remove = !within(x, y, z, xp - 0.5D, yp + 0.5D, zp + 0.5D, distsq, closed);
/*     */           }
/*     */ 
/*     */         }
/* 600 */         else if (y < yp)
/* 601 */           remove = !within(x, y, z, xp + 0.5D, yp - 0.5D, zp + 0.5D, distsq, closed);
/*     */         else {
/* 603 */           remove = !within(x, y, z, xp + 0.5D, yp + 0.5D, zp + 0.5D, distsq, closed);
/*     */         }
/*     */ 
/*     */       }
/* 607 */       else if (measurementRule == 1025)
/*     */       {
/* 609 */         if (z < zp)
/*     */         {
/* 611 */           if (x < xp)
/*     */           {
/* 613 */             if (y < yp)
/* 614 */               remove = !within(x, y, z, xp + 0.5D, yp + 0.5D, zp + 0.5D, distsq, closed);
/*     */             else {
/* 616 */               remove = !within(x, y, z, xp + 0.5D, yp - 0.5D, zp + 0.5D, distsq, closed);
/*     */             }
/*     */ 
/*     */           }
/* 620 */           else if (y < yp)
/* 621 */             remove = !within(x, y, z, xp - 0.5D, yp + 0.5D, zp + 0.5D, distsq, closed);
/*     */           else {
/* 623 */             remove = !within(x, y, z, xp - 0.5D, yp - 0.5D, zp + 0.5D, distsq, closed);
/*     */           }
/*     */ 
/*     */         }
/* 628 */         else if (x < xp)
/*     */         {
/* 630 */           if (y < yp)
/* 631 */             remove = !within(x, y, z, xp + 0.5D, yp + 0.5D, zp - 0.5D, distsq, closed);
/*     */           else {
/* 633 */             remove = !within(x, y, z, xp + 0.5D, yp - 0.5D, zp - 0.5D, distsq, closed);
/*     */           }
/*     */ 
/*     */         }
/* 637 */         else if (y < yp)
/* 638 */           remove = !within(x, y, z, xp - 0.5D, yp + 0.5D, zp - 0.5D, distsq, closed);
/*     */         else {
/* 640 */           remove = !within(x, y, z, xp - 0.5D, yp - 0.5D, zp - 0.5D, distsq, closed);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 647 */         remove = !within(x, y, z, xp, yp, zp, distsq, closed);
/*     */       }
/*     */ 
/* 650 */       if (remove) {
/* 651 */         xPos.remove(i); yPos.remove(i); zPos.remove(i); i--; len--;
/* 652 */       } else if (toroidal)
/*     */       {
/* 654 */         int _x = xPos.get(i);
/* 655 */         int _y = yPos.get(i);
/* 656 */         int _z = zPos.get(i);
/* 657 */         xPos.set(i, tx(_x, width, widthtimestwo, _x + width, _x - width));
/* 658 */         yPos.set(i, ty(_y, height, heighttimestwo, _y + width, _y - width));
/* 659 */         zPos.set(i, tz(_z, length, lengthtimestwo, _z + length, _z - length));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkBounds(Grid3D other)
/*     */   {
/* 668 */     if ((getHeight() != other.getHeight()) || (getWidth() != other.getWidth()) || (getLength() != other.getLength()))
/* 669 */       throw new IllegalArgumentException("Grids must be the same dimensions.");
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.grid.AbstractGrid3D
 * JD-Core Version:    0.6.2
 */