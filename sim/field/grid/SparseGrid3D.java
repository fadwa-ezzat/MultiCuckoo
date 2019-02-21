/*      */ package sim.field.grid;
/*      */ 
/*      */ import java.util.Map;
/*      */ import sim.field.SparseField;
/*      */ import sim.field.SparseField3D;
/*      */ import sim.util.Bag;
/*      */ import sim.util.Double3D;
/*      */ import sim.util.Int3D;
/*      */ import sim.util.IntBag;
/*      */ 
/*      */ public class SparseGrid3D extends SparseField
/*      */   implements Grid3D, SparseField3D
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   protected int width;
/*      */   protected int height;
/*      */   protected int length;
/*      */ 
/*      */   public SparseGrid3D(int width, int height, int length)
/*      */   {
/*   59 */     this.width = width;
/*   60 */     this.height = height;
/*   61 */     this.length = length;
/*      */   }
/*      */ 
/*      */   public SparseGrid3D(SparseGrid3D values)
/*      */   {
/*   66 */     super(values);
/*   67 */     this.width = values.width;
/*   68 */     this.height = values.height;
/*   69 */     this.length = values.length;
/*      */   }
/*      */ 
/*      */   public int getWidth() {
/*   73 */     return this.width;
/*      */   }
/*      */   public int getHeight() {
/*   76 */     return this.height;
/*      */   }
/*      */   public int getLength() {
/*   79 */     return this.length;
/*      */   }
/*      */ 
/*      */   public final int tx(int x)
/*      */   {
/*   95 */     int width = this.width;
/*   96 */     if ((x >= 0) && (x < width)) return x;
/*   97 */     x %= width;
/*   98 */     if (x < 0) x += width;
/*   99 */     return x;
/*      */   }
/*      */ 
/*      */   public final int ty(int y)
/*      */   {
/*  116 */     int height = this.height;
/*  117 */     if ((y >= 0) && (y < height)) return y;
/*  118 */     y %= height;
/*  119 */     if (y < 0) y += height;
/*  120 */     return y;
/*      */   }
/*      */ 
/*      */   public final int tz(int z)
/*      */   {
/*  137 */     int length = this.length;
/*  138 */     if ((z >= 0) && (z < length)) return z;
/*  139 */     z %= length;
/*  140 */     if (z < 0) z += this.height;
/*  141 */     return z;
/*      */   }
/*      */ 
/*      */   public int stx(int x) {
/*  145 */     if (x >= 0) { if (x < this.width) return x; return x - this.width; } return x + this.width;
/*      */   }
/*      */   public int sty(int y) {
/*  148 */     if (y >= 0) { if (y < this.height) return y; return y - this.height; } return y + this.height;
/*      */   }
/*      */   public int stz(int z) {
/*  151 */     if (z >= 0) { if (z < this.length) return z; return z - this.length; } return z + this.length;
/*      */   }
/*      */ 
/*      */   final int stx(int x, int width) {
/*  155 */     if (x >= 0) { if (x < width) return x; return x - width; } return x + width;
/*      */   }
/*      */ 
/*      */   final int sty(int y, int height) {
/*  159 */     if (y >= 0) { if (y < height) return y; return y - height; } return y + height;
/*      */   }
/*      */ 
/*      */   public final int stz(int z, int length) {
/*  163 */     if (z >= 0) { if (z < length) return z; return z - length; } return z + length;
/*      */   }
/*      */ 
/*      */   public int numObjectsAtLocation(int x, int y, int z)
/*      */   {
/*  169 */     return numObjectsAtLocation(new Int3D(x, y, z));
/*      */   }
/*      */ 
/*      */   public Bag getObjectsAtLocation(int x, int y, int z)
/*      */   {
/*  182 */     return getObjectsAtLocation(new Int3D(x, y, z));
/*      */   }
/*      */ 
/*      */   public Double3D getObjectLocationAsDouble3D(Object obj)
/*      */   {
/*  188 */     Int3D loc = (Int3D)super.getRawObjectLocation(obj);
/*  189 */     if (loc == null) return null;
/*  190 */     return new Double3D(loc);
/*      */   }
/*      */ 
/*      */   public Int3D getObjectLocation(Object obj)
/*      */   {
/*  196 */     return (Int3D)super.getRawObjectLocation(obj);
/*      */   }
/*      */ 
/*      */   public Bag removeObjectsAtLocation(int x, int y, int z)
/*      */   {
/*  202 */     return removeObjectsAtLocation(new Int3D(x, y, z));
/*      */   }
/*      */ 
/*      */   public boolean setObjectLocation(Object obj, int x, int y, int z)
/*      */   {
/*  209 */     return super.setObjectLocation(obj, new Int3D(x, y, z));
/*      */   }
/*      */ 
/*      */   public boolean setObjectLocation(Object obj, Int3D location)
/*      */   {
/*  216 */     return super.setObjectLocation(obj, location);
/*      */   }
/*      */ 
/*      */   int tx(int x, int width, int widthtimestwo, int xpluswidth, int xminuswidth)
/*      */   {
/*  222 */     if ((x >= -width) && (x < widthtimestwo))
/*      */     {
/*  224 */       if (x < 0) return xpluswidth;
/*  225 */       if (x < width) return x;
/*  226 */       return xminuswidth;
/*      */     }
/*  228 */     return tx2(x, width);
/*      */   }
/*      */ 
/*      */   int tx2(int x, int width)
/*      */   {
/*  234 */     x %= width;
/*  235 */     if (x < 0) x += width;
/*  236 */     return x;
/*      */   }
/*      */ 
/*      */   int ty(int y, int height, int heighttimestwo, int yplusheight, int yminusheight)
/*      */   {
/*  242 */     if ((y >= -height) && (y < heighttimestwo))
/*      */     {
/*  244 */       if (y < 0) return yplusheight;
/*  245 */       if (y < height) return y;
/*  246 */       return yminusheight;
/*      */     }
/*  248 */     return ty2(y, height);
/*      */   }
/*      */ 
/*      */   int ty2(int y, int height)
/*      */   {
/*  254 */     y %= height;
/*  255 */     if (y < 0) y += height;
/*  256 */     return y;
/*      */   }
/*      */ 
/*      */   int tz(int z, int length, int lengthtimestwo, int zpluslength, int zminuslength)
/*      */   {
/*  262 */     if ((z >= -length) && (z < lengthtimestwo))
/*      */     {
/*  264 */       if (z < 0) return zpluslength;
/*  265 */       if (z < length) return z;
/*  266 */       return zminuslength;
/*      */     }
/*  268 */     return tz2(z, length);
/*      */   }
/*      */ 
/*      */   int tz2(int z, int length)
/*      */   {
/*  274 */     z %= length;
/*  275 */     if (z < 0) z += length;
/*  276 */     return z;
/*      */   }
/*      */ 
/*      */   protected void removeOrigin(int x, int y, int z, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  282 */     int size = xPos.size();
/*  283 */     for (int i = 0; i < size; i++)
/*      */     {
/*  285 */       if ((xPos.get(i) == x) && (yPos.get(i) == y) && (zPos.get(i) == z))
/*      */       {
/*  287 */         xPos.remove(i);
/*  288 */         yPos.remove(i);
/*  289 */         zPos.remove(i);
/*  290 */         return;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void removeOriginToroidal(int x, int y, int z, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  298 */     int size = xPos.size();
/*  299 */     x = tx(x, this.width, this.width * 2, x + this.width, x - this.width);
/*  300 */     y = ty(y, this.height, this.height * 2, y + this.height, y - this.height);
/*  301 */     z = tz(z, this.length, this.length * 2, z + this.length, z - this.length);
/*      */ 
/*  303 */     for (int i = 0; i < size; i++)
/*      */     {
/*  305 */       if ((tx(xPos.get(i), this.width, this.width * 2, x + this.width, x - this.width) == x) && (ty(yPos.get(i), this.height, this.height * 2, y + this.height, y - this.height) == y) && (tz(zPos.get(i), this.length, this.length * 2, z + this.length, z - this.length) == z))
/*      */       {
/*  309 */         xPos.remove(i);
/*  310 */         yPos.remove(i);
/*  311 */         zPos.remove(i);
/*  312 */         return;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void getNeighborsMaxDistance(int x, int y, int z, int dist, boolean toroidal, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  323 */     getMooreLocations(x, y, z, dist, toroidal ? 2 : 0, true, xPos, yPos, zPos);
/*      */   }
/*      */ 
/*      */   public void getMooreLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  328 */     boolean toroidal = mode == 2;
/*  329 */     boolean bounded = mode == 0;
/*      */ 
/*  331 */     if ((mode != 0) && (mode != 1) && (mode != 2))
/*      */     {
/*  333 */       throw new RuntimeException("Mode must be either Grid3D.BOUNDED, Grid3D.UNBOUNDED, or Grid3D.TOROIDAL");
/*      */     }
/*      */ 
/*  337 */     if (dist < 0)
/*      */     {
/*  339 */       throw new RuntimeException("Distance must be positive");
/*      */     }
/*      */ 
/*  342 */     if ((xPos == null) || (yPos == null) || (zPos == null))
/*      */     {
/*  344 */       throw new RuntimeException("xPos and yPos and zPos should not be null");
/*      */     }
/*      */ 
/*  347 */     xPos.clear();
/*  348 */     yPos.clear();
/*  349 */     zPos.clear();
/*      */ 
/*  352 */     int height = this.height;
/*  353 */     int width = this.width;
/*  354 */     int length = this.length;
/*      */ 
/*  357 */     if (toroidal)
/*      */     {
/*  360 */       int xmin = x - dist;
/*  361 */       int xmax = x + dist;
/*      */ 
/*  364 */       if (xmax - xmin >= width) {
/*  365 */         xmax = xmin + width - 1;
/*      */       }
/*      */ 
/*  368 */       int ymin = y - dist;
/*  369 */       int ymax = y + dist;
/*      */ 
/*  372 */       if (ymax - ymin >= height) {
/*  373 */         ymax = ymin + height - 1;
/*      */       }
/*      */ 
/*  376 */       int zmin = z - dist;
/*  377 */       int zmax = z + dist;
/*      */ 
/*  380 */       if (zmax - zmin >= length) {
/*  381 */         zmax = zmin + length - 1;
/*      */       }
/*      */ 
/*  384 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*      */       {
/*  386 */         int x_0 = tx(x0, width, width * 2, x0 + width, x0 - width);
/*  387 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*      */         {
/*  389 */           int y_0 = ty(y0, height, height * 2, y0 + height, y0 - height);
/*  390 */           for (int z0 = zmin; z0 <= zmax; z0++)
/*      */           {
/*  392 */             int z_0 = tz(z0, length, length * 2, z0 + length, z0 - length);
/*  393 */             if ((x_0 != x) || (y_0 != y) || (z_0 != z))
/*      */             {
/*  395 */               xPos.add(x_0);
/*  396 */               yPos.add(y_0);
/*  397 */               zPos.add(z_0);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  402 */       if (!includeOrigin) removeOriginToroidal(x, y, z, xPos, yPos, zPos);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  407 */       int xmin = (x - dist >= 0) || (!bounded) ? x - dist : 0;
/*  408 */       int xmax = (x + dist <= width - 1) || (!bounded) ? x + dist : width - 1;
/*      */ 
/*  410 */       int ymin = (y - dist >= 0) || (!bounded) ? y - dist : 0;
/*  411 */       int ymax = (y + dist <= height - 1) || (!bounded) ? y + dist : height - 1;
/*      */ 
/*  413 */       int zmin = (z - dist >= 0) || (!bounded) ? z - dist : 0;
/*  414 */       int zmax = (z + dist <= length - 1) || (!bounded) ? z + dist : length - 1;
/*      */ 
/*  416 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*      */       {
/*  418 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*      */         {
/*  420 */           for (int z0 = zmin; z0 <= zmax; z0++)
/*      */           {
/*  422 */             if ((x0 != x) || (y0 != y) || (z0 != z))
/*      */             {
/*  424 */               xPos.add(x0);
/*  425 */               yPos.add(y0);
/*  426 */               zPos.add(z0);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  431 */       if (!includeOrigin) removeOrigin(x, y, z, xPos, yPos, zPos);
/*      */     }
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void getNeighborsHamiltonianDistance(int x, int y, int z, int dist, boolean toroidal, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  439 */     getVonNeumannLocations(x, y, z, dist, toroidal ? 2 : 0, true, xPos, yPos, zPos);
/*      */   }
/*      */ 
/*      */   public void getVonNeumannLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  444 */     boolean toroidal = mode == 2;
/*  445 */     boolean bounded = mode == 0;
/*      */ 
/*  447 */     if ((mode != 0) && (mode != 1) && (mode != 2))
/*      */     {
/*  449 */       throw new RuntimeException("Mode must be either Grid3D.BOUNDED, Grid3D.UNBOUNDED, or Grid3D.TOROIDAL");
/*      */     }
/*      */ 
/*  453 */     if (dist < 0)
/*      */     {
/*  455 */       throw new RuntimeException("Distance must be positive");
/*      */     }
/*      */ 
/*  458 */     if ((xPos == null) || (yPos == null) || (zPos == null))
/*      */     {
/*  460 */       throw new RuntimeException("xPos and yPos and zPos should not be null");
/*      */     }
/*      */ 
/*  463 */     xPos.clear();
/*  464 */     yPos.clear();
/*  465 */     zPos.clear();
/*      */ 
/*  468 */     int height = this.height;
/*  469 */     int width = this.width;
/*  470 */     int length = this.length;
/*      */ 
/*  473 */     if (toroidal)
/*      */     {
/*  476 */       int xmax = x + dist;
/*  477 */       int xmin = x - dist;
/*  478 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*      */       {
/*  480 */         int x_0 = tx(x0, width, width * 2, x0 + width, x0 - width);
/*      */ 
/*  482 */         int ymax = y + (dist - (x0 - x >= 0 ? x0 - x : x - x0));
/*  483 */         int ymin = y - (dist - (x0 - x >= 0 ? x0 - x : x - x0));
/*  484 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*      */         {
/*  486 */           int y_0 = ty(y0, height, height * 2, y0 + height, y0 - height);
/*  487 */           int zmax = z + (dist - (x0 - x >= 0 ? x0 - x : x - x0) - (y0 - y >= 0 ? y0 - y : y - y0));
/*  488 */           int zmin = z - (dist - (x0 - x >= 0 ? x0 - x : x - x0) - (y0 - y >= 0 ? y0 - y : y - y0));
/*  489 */           for (int z0 = zmin; z0 <= zmax; z0++)
/*      */           {
/*  491 */             int z_0 = tz(z0, length, length * 2, z0 + length, z0 - length);
/*  492 */             if ((x_0 != x) || (y_0 != y) || (z_0 != z))
/*      */             {
/*  494 */               xPos.add(x_0);
/*  495 */               yPos.add(y_0);
/*  496 */               zPos.add(z_0);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  501 */       if ((dist * 2 >= width) || (dist * 2 >= height) || (dist * 2 >= length))
/*      */       {
/*  503 */         int sz = xPos.size();
/*  504 */         Map map = buildMap(sz);
/*  505 */         for (int i = 0; i < sz; i++)
/*      */         {
/*  507 */           Double3D elem = new Double3D(xPos.get(i), yPos.get(i), zPos.get(i));
/*  508 */           if (map.containsKey(elem))
/*      */           {
/*  510 */             xPos.remove(i);
/*  511 */             yPos.remove(i);
/*  512 */             zPos.remove(i);
/*  513 */             i--;
/*  514 */             sz--;
/*      */           }
/*      */           else
/*      */           {
/*  518 */             map.put(elem, elem);
/*      */           }
/*      */         }
/*      */       }
/*  522 */       if (!includeOrigin) removeOriginToroidal(x, y, z, xPos, yPos, zPos);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  527 */       int xmax = (x + dist <= width - 1) || (!bounded) ? x + dist : width - 1;
/*  528 */       int xmin = (x - dist >= 0) || (!bounded) ? x - dist : 0;
/*  529 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*      */       {
/*  531 */         int x_0 = x0;
/*      */ 
/*  534 */         int ymax = (y + (dist - (x0 - x >= 0 ? x0 - x : x - x0)) <= height - 1) || (!bounded) ? y + (dist - (x0 - x >= 0 ? x0 - x : x - x0)) : height - 1;
/*  535 */         int ymin = (y - (dist - (x0 - x >= 0 ? x0 - x : x - x0)) >= 0) || (!bounded) ? y - (dist - (x0 - x >= 0 ? x0 - x : x - x0)) : 0;
/*  536 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*      */         {
/*  538 */           int y_0 = y0;
/*  539 */           int zmin = (z - (dist - (x0 - x >= 0 ? x0 - x : x - x0) - (y0 - y >= 0 ? y0 - y : y - y0)) >= 0) || (!bounded) ? z - (dist - (x0 - x >= 0 ? x0 - x : x - x0) - (y0 - y >= 0 ? y0 - y : y - y0)) : 0;
/*  540 */           int zmax = (z + (dist - (x0 - x >= 0 ? x0 - x : x - x0) - (y0 - y >= 0 ? y0 - y : y - y0)) <= length - 1) || (!bounded) ? z + (dist - (x0 - x >= 0 ? x0 - x : x - x0) - (y0 - y >= 0 ? y0 - y : y - y0)) : length - 1;
/*  541 */           for (int z0 = zmin; z0 <= zmax; z0++)
/*      */           {
/*  543 */             int z_0 = z0;
/*  544 */             if ((x_0 != x) || (y_0 != y) || (z_0 != z))
/*      */             {
/*  546 */               xPos.add(x_0);
/*  547 */               yPos.add(y_0);
/*  548 */               zPos.add(z_0);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  553 */       if (!includeOrigin) removeOrigin(x, y, z, xPos, yPos, zPos);
/*      */     }
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Bag getNeighborsMaxDistance(int x, int y, int z, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  567 */     return getMooreNeighbors(x, y, z, dist, toroidal ? 2 : 0, true, result, xPos, yPos, zPos);
/*      */   }
/*      */ 
/*      */   public Bag getMooreNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  573 */     if (xPos == null)
/*  574 */       xPos = new IntBag();
/*  575 */     if (yPos == null)
/*  576 */       yPos = new IntBag();
/*  577 */     if (zPos == null) {
/*  578 */       zPos = new IntBag();
/*      */     }
/*  580 */     getMooreLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/*  581 */     return getObjectsAtLocations(xPos, yPos, zPos, result);
/*      */   }
/*      */ 
/*      */   public Bag getMooreNeighborsAndLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  616 */     if (xPos == null)
/*  617 */       xPos = new IntBag();
/*  618 */     if (yPos == null)
/*  619 */       yPos = new IntBag();
/*  620 */     if (zPos == null) {
/*  621 */       zPos = new IntBag();
/*      */     }
/*  623 */     getMooreLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/*  624 */     reduceObjectsAtLocations(xPos, yPos, zPos, result);
/*  625 */     return result;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Bag getNeighborsHamiltonianDistance(int x, int y, int z, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  665 */     return getVonNeumannNeighbors(x, y, z, dist, toroidal ? 2 : 0, true, result, xPos, yPos, zPos);
/*      */   }
/*      */ 
/*      */   public Bag getVonNeumannNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  700 */     if (xPos == null)
/*  701 */       xPos = new IntBag();
/*  702 */     if (yPos == null)
/*  703 */       yPos = new IntBag();
/*  704 */     if (zPos == null) {
/*  705 */       zPos = new IntBag();
/*      */     }
/*  707 */     getVonNeumannLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/*  708 */     return getObjectsAtLocations(xPos, yPos, zPos, result);
/*      */   }
/*      */ 
/*      */   public Bag getVonNeumannNeighborsAndLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  745 */     if (xPos == null)
/*  746 */       xPos = new IntBag();
/*  747 */     if (yPos == null)
/*  748 */       yPos = new IntBag();
/*  749 */     if (zPos == null) {
/*  750 */       zPos = new IntBag();
/*      */     }
/*  752 */     getVonNeumannLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/*  753 */     reduceObjectsAtLocations(xPos, yPos, zPos, result);
/*  754 */     return result;
/*      */   }
/*      */ 
/*      */   public Bag getRadialNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  760 */     if (xPos == null)
/*  761 */       xPos = new IntBag();
/*  762 */     if (yPos == null)
/*  763 */       yPos = new IntBag();
/*  764 */     if (zPos == null) {
/*  765 */       zPos = new IntBag();
/*      */     }
/*  767 */     getRadialLocations(x, y, z, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos, zPos);
/*  768 */     return getObjectsAtLocations(xPos, yPos, zPos, result);
/*      */   }
/*      */ 
/*      */   public Bag getRadialNeighborsAndLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  773 */     if (xPos == null)
/*  774 */       xPos = new IntBag();
/*  775 */     if (yPos == null)
/*  776 */       yPos = new IntBag();
/*  777 */     if (zPos == null) {
/*  778 */       zPos = new IntBag();
/*      */     }
/*  780 */     getRadialLocations(x, y, z, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos, zPos);
/*  781 */     reduceObjectsAtLocations(xPos, yPos, zPos, result);
/*  782 */     return result;
/*      */   }
/*      */ 
/*      */   void reduceObjectsAtLocations(IntBag xPos, IntBag yPos, IntBag zPos, Bag result)
/*      */   {
/*  793 */     if (result == null) result = new Bag(); else {
/*  794 */       result.clear();
/*      */     }
/*      */ 
/*  797 */     IntBag newXPos = new IntBag();
/*  798 */     IntBag newYPos = new IntBag();
/*  799 */     IntBag newZPos = new IntBag();
/*      */ 
/*  801 */     int len = xPos.numObjs;
/*  802 */     int[] xs = xPos.objs;
/*  803 */     int[] ys = yPos.objs;
/*  804 */     int[] zs = zPos.objs;
/*      */ 
/*  807 */     for (int i = 0; i < len; i++)
/*      */     {
/*  809 */       Bag temp = getObjectsAtLocation(xs[i], ys[i], zs[i]);
/*  810 */       if (temp != null)
/*      */       {
/*  812 */         int size = temp.numObjs;
/*  813 */         Object[] os = temp.objs;
/*      */ 
/*  815 */         for (int j = 0; j < size; j++)
/*      */         {
/*  818 */           result.add(os[j]);
/*  819 */           newXPos.add(xs[i]);
/*  820 */           newYPos.add(ys[i]);
/*  821 */           newZPos.add(zs[i]);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  827 */     xPos.clear();
/*  828 */     xPos.addAll(newXPos);
/*  829 */     yPos.clear();
/*  830 */     yPos.addAll(newYPos);
/*  831 */     zPos.clear();
/*  832 */     zPos.addAll(newZPos);
/*      */   }
/*      */ 
/*      */   public Bag getObjectsAtLocations(IntBag xPos, IntBag yPos, IntBag zPos, Bag result)
/*      */   {
/*  841 */     if (result == null) result = new Bag(); else {
/*  842 */       result.clear();
/*      */     }
/*  844 */     int len = xPos.numObjs;
/*  845 */     int[] xs = xPos.objs;
/*  846 */     int[] ys = yPos.objs;
/*  847 */     int[] zs = zPos.objs;
/*  848 */     for (int i = 0; i < len; i++)
/*      */     {
/*  852 */       Bag temp = getObjectsAtLocation(xs[i], ys[i], zs[i]);
/*  853 */       if (temp != null)
/*      */       {
/*  855 */         int n = temp.numObjs;
/*  856 */         if (n == 1) result.add(temp.objs[0]);
/*  857 */         else if (n > 1) result.addAll(temp);
/*      */       }
/*      */     }
/*  860 */     return result;
/*      */   }
/*      */ 
/*      */   double ds(double d1x, double d1y, double d1z, double d2x, double d2y, double d2z)
/*      */   {
/*  868 */     return (d1x - d2x) * (d1x - d2x) + (d1y - d2y) * (d1y - d2y) + (d1z - d2z) * (d1z - d2z);
/*      */   }
/*      */ 
/*      */   boolean within(double d1x, double d1y, double d1z, double d2x, double d2y, double d2z, double distanceSquared, boolean closed)
/*      */   {
/*  873 */     double d = ds(d1x, d1y, d1z, d2x, d2y, d2z);
/*  874 */     return (d < distanceSquared) || ((d == distanceSquared) && (closed));
/*      */   }
/*      */ 
/*      */   public void getRadialLocations(int x, int y, int z, double dist, int mode, boolean includeOrigin, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  879 */     getRadialLocations(x, y, z, dist, mode, includeOrigin, 1026, true, xPos, yPos, zPos);
/*      */   }
/*      */ 
/*      */   public void getRadialLocations(int x, int y, int z, double dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/*  884 */     boolean toroidal = mode == 2;
/*      */ 
/*  887 */     if (dist < 0.0D)
/*      */     {
/*  889 */       throw new RuntimeException("Distance must be positive");
/*      */     }
/*      */ 
/*  892 */     if ((measurementRule != 1026) && (measurementRule != 1025) && (measurementRule != 1024))
/*      */     {
/*  894 */       throw new RuntimeException(" Measurement rule must be one of ANY, ALL, or CENTER");
/*      */     }
/*      */ 
/*  898 */     getMooreLocations(x, y, z, (int)Math.ceil(dist + 0.5D), mode, includeOrigin, xPos, yPos, zPos);
/*  899 */     int len = xPos.size();
/*  900 */     double distsq = dist * dist;
/*      */ 
/*  903 */     int width = this.width;
/*  904 */     int height = this.height;
/*  905 */     int length = this.length;
/*  906 */     int widthtimestwo = width * 2;
/*  907 */     int heighttimestwo = height * 2;
/*  908 */     int lengthtimestwo = length * 2;
/*      */ 
/*  911 */     for (int i = 0; i < len; i++)
/*      */     {
/*  913 */       int xp = xPos.get(i);
/*  914 */       int yp = yPos.get(i);
/*  915 */       int zp = zPos.get(i);
/*  916 */       boolean remove = false;
/*      */ 
/*  918 */       if (measurementRule == 1026)
/*      */       {
/*  920 */         if (z == zp)
/*      */         {
/*  922 */           if (x == xp)
/*      */           {
/*  924 */             if (y < yp)
/*      */             {
/*  926 */               double d = yp - 0.5D - y;
/*  927 */               remove = (d >= dist) && ((d != dist) || (!closed));
/*      */             }
/*      */             else
/*      */             {
/*  931 */               double d = -(yp - 0.5D - y);
/*  932 */               remove = (d >= dist) && ((d != dist) || (!closed));
/*      */             }
/*      */           }
/*  935 */           else if (y == yp)
/*      */           {
/*  937 */             if (x < xp)
/*      */             {
/*  939 */               double d = xp - 0.5D - x;
/*  940 */               remove = (d >= dist) && ((d != dist) || (!closed));
/*      */             }
/*      */             else
/*      */             {
/*  944 */               double d = -(xp - 0.5D - x);
/*  945 */               remove = (d >= dist) && ((d != dist) || (!closed));
/*      */             }
/*      */           }
/*      */         }
/*  949 */         else if (x == xp)
/*      */         {
/*  951 */           if (y == yp)
/*      */           {
/*  953 */             if (z < zp)
/*      */             {
/*  955 */               double d = zp - 0.5D - z;
/*  956 */               remove = (d >= dist) && ((d != dist) || (!closed));
/*      */             }
/*      */             else
/*      */             {
/*  960 */               double d = -(zp - 0.5D - z);
/*  961 */               remove = (d >= dist) && ((d != dist) || (!closed));
/*      */             }
/*      */           }
/*      */         }
/*  965 */         else if (z < zp)
/*      */         {
/*  967 */           if (x < xp)
/*      */           {
/*  969 */             if (y < yp)
/*  970 */               remove = !within(x, y, z, xp - 0.5D, yp - 0.5D, zp - 0.5D, distsq, closed);
/*      */             else {
/*  972 */               remove = !within(x, y, z, xp - 0.5D, yp + 0.5D, zp - 0.5D, distsq, closed);
/*      */             }
/*      */ 
/*      */           }
/*  976 */           else if (y < yp)
/*  977 */             remove = !within(x, y, z, xp + 0.5D, yp - 0.5D, zp - 0.5D, distsq, closed);
/*      */           else {
/*  979 */             remove = !within(x, y, z, xp + 0.5D, yp + 0.5D, zp - 0.5D, distsq, closed);
/*      */           }
/*      */ 
/*      */         }
/*  984 */         else if (x < xp)
/*      */         {
/*  986 */           if (y < yp)
/*  987 */             remove = !within(x, y, z, xp - 0.5D, yp - 0.5D, zp + 0.5D, distsq, closed);
/*      */           else {
/*  989 */             remove = !within(x, y, z, xp - 0.5D, yp + 0.5D, zp + 0.5D, distsq, closed);
/*      */           }
/*      */ 
/*      */         }
/*  993 */         else if (y < yp)
/*  994 */           remove = !within(x, y, z, xp + 0.5D, yp - 0.5D, zp + 0.5D, distsq, closed);
/*      */         else {
/*  996 */           remove = !within(x, y, z, xp + 0.5D, yp + 0.5D, zp + 0.5D, distsq, closed);
/*      */         }
/*      */ 
/*      */       }
/* 1000 */       else if (measurementRule == 1025)
/*      */       {
/* 1002 */         if (z < zp)
/*      */         {
/* 1004 */           if (x < xp)
/*      */           {
/* 1006 */             if (y < yp)
/* 1007 */               remove = !within(x, y, z, xp + 0.5D, yp + 0.5D, zp + 0.5D, distsq, closed);
/*      */             else {
/* 1009 */               remove = !within(x, y, z, xp + 0.5D, yp - 0.5D, zp + 0.5D, distsq, closed);
/*      */             }
/*      */ 
/*      */           }
/* 1013 */           else if (y < yp)
/* 1014 */             remove = !within(x, y, z, xp - 0.5D, yp + 0.5D, zp + 0.5D, distsq, closed);
/*      */           else {
/* 1016 */             remove = !within(x, y, z, xp - 0.5D, yp - 0.5D, zp + 0.5D, distsq, closed);
/*      */           }
/*      */ 
/*      */         }
/* 1021 */         else if (x < xp)
/*      */         {
/* 1023 */           if (y < yp)
/* 1024 */             remove = !within(x, y, z, xp + 0.5D, yp + 0.5D, zp - 0.5D, distsq, closed);
/*      */           else {
/* 1026 */             remove = !within(x, y, z, xp + 0.5D, yp - 0.5D, zp - 0.5D, distsq, closed);
/*      */           }
/*      */ 
/*      */         }
/* 1030 */         else if (y < yp)
/* 1031 */           remove = !within(x, y, z, xp - 0.5D, yp + 0.5D, zp - 0.5D, distsq, closed);
/*      */         else {
/* 1033 */           remove = !within(x, y, z, xp - 0.5D, yp - 0.5D, zp - 0.5D, distsq, closed);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1040 */         remove = !within(x, y, z, xp, yp, zp, distsq, closed);
/*      */       }
/*      */ 
/* 1043 */       if (remove) {
/* 1044 */         xPos.remove(i); yPos.remove(i); zPos.remove(i); i--; len--;
/* 1045 */       } else if (toroidal)
/*      */       {
/* 1047 */         int _x = xPos.get(i);
/* 1048 */         int _y = yPos.get(i);
/* 1049 */         int _z = zPos.get(i);
/* 1050 */         xPos.set(i, tx(_x, width, widthtimestwo, _x + width, _x - width));
/* 1051 */         yPos.set(i, ty(_y, height, heighttimestwo, _y + width, _y - width));
/* 1052 */         zPos.set(i, tz(_z, length, lengthtimestwo, _z + length, _z - length));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Bag getRadialNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/* 1061 */     return getRadialNeighbors(x, y, z, dist, mode, includeOrigin, 1026, true, result, xPos, yPos, zPos);
/*      */   }
/*      */ 
/*      */   public Bag getRadialNeighborsAndLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*      */   {
/* 1067 */     return getRadialNeighborsAndLocations(x, y, z, dist, mode, includeOrigin, 1026, true, result, xPos, yPos, zPos);
/*      */   }
/*      */ 
/*      */   public Bag getMooreNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin)
/*      */   {
/* 1089 */     return getMooreNeighbors(x, y, z, dist, mode, includeOrigin, null, null, null, null);
/*      */   }
/*      */ 
/*      */   public Bag getVonNeumannNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin)
/*      */   {
/* 1113 */     return getVonNeumannNeighbors(x, y, z, dist, mode, includeOrigin, null, null, null, null);
/*      */   }
/*      */ 
/*      */   public Bag getRadialNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin)
/*      */   {
/* 1122 */     return getRadialNeighbors(x, y, z, dist, mode, includeOrigin, null, null, null, null);
/*      */   }
/*      */ 
/*      */   public final Double3D getDimensions()
/*      */   {
/* 1129 */     return new Double3D(this.width, this.height, this.length);
/*      */   }
/*      */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.grid.SparseGrid3D
 * JD-Core Version:    0.6.2
 */