/*      */ package sim.field.grid;
/*      */ 
/*      */ import java.util.Map;
/*      */ import sim.field.SparseField;
/*      */ import sim.field.SparseField2D;
/*      */ import sim.util.Bag;
/*      */ import sim.util.Double2D;
/*      */ import sim.util.Int2D;
/*      */ import sim.util.IntBag;
/*      */ 
/*      */ public class SparseGrid2D extends SparseField
/*      */   implements Grid2D, SparseField2D
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   protected int width;
/*      */   protected int height;
/*      */ 
/*      */   public SparseGrid2D(int width, int height)
/*      */   {
/*   58 */     this.width = width;
/*   59 */     this.height = height;
/*      */   }
/*      */ 
/*      */   public SparseGrid2D(SparseGrid2D values)
/*      */   {
/*   64 */     super(values);
/*   65 */     this.width = values.width;
/*   66 */     this.height = values.height;
/*      */   }
/*      */ 
/*      */   public int getWidth() {
/*   70 */     return this.width;
/*      */   }
/*      */   public int getHeight() {
/*   73 */     return this.height;
/*      */   }
/*      */ 
/*      */   public final int tx(int x)
/*      */   {
/*   78 */     int width = this.width;
/*   79 */     if ((x >= 0) && (x < width)) return x;
/*   80 */     x %= width;
/*   81 */     if (x < 0) x += width;
/*   82 */     return x;
/*      */   }
/*      */ 
/*      */   public final int ty(int y)
/*      */   {
/*   88 */     int height = this.height;
/*   89 */     if ((y >= 0) && (y < height)) return y;
/*   90 */     y %= height;
/*   91 */     if (y < 0) y += height;
/*   92 */     return y;
/*      */   }
/*      */ 
/*      */   public int stx(int x) {
/*   96 */     if (x >= 0) { if (x < this.width) return x; return x - this.width; } return x + this.width;
/*      */   }
/*      */   public int sty(int y) {
/*   99 */     if (y >= 0) { if (y < this.height) return y; return y - this.height; } return y + this.height;
/*      */   }
/*      */ 
/*      */   final int stx(int x, int width) {
/*  103 */     if (x >= 0) { if (x < width) return x; return x - width; } return x + width;
/*      */   }
/*      */ 
/*      */   final int sty(int y, int height) {
/*  107 */     if (y >= 0) { if (y < height) return y; return y - height; } return y + height;
/*      */   }
/*  109 */   public int ulx(int x, int y) { return x - 1; } 
/*      */   public int uly(int x, int y) {
/*  111 */     if ((x & 0x1) == 0) return y - 1; return y;
/*      */   }
/*  113 */   public int urx(int x, int y) { return x + 1; } 
/*      */   public int ury(int x, int y) {
/*  115 */     if ((x & 0x1) == 0) return y - 1; return y;
/*      */   }
/*  117 */   public int dlx(int x, int y) { return x - 1; } 
/*      */   public int dly(int x, int y) {
/*  119 */     if ((x & 0x1) == 0) return y; return y + 1;
/*      */   }
/*  121 */   public int drx(int x, int y) { return x + 1; } 
/*      */   public int dry(int x, int y) {
/*  123 */     if ((x & 0x1) == 0) return y; return y + 1;
/*      */   }
/*  125 */   public int upx(int x, int y) { return x; } 
/*      */   public int upy(int x, int y) {
/*  127 */     return y - 1;
/*      */   }
/*  129 */   public int downx(int x, int y) { return x; } 
/*      */   public int downy(int x, int y) {
/*  131 */     return y + 1;
/*      */   }
/*  133 */   public boolean trb(int x, int y) { return (x + y & 0x1) == 1; } 
/*      */   public boolean trt(int x, int y) {
/*  135 */     return (x + y & 0x1) == 0;
/*      */   }
/*      */ 
/*      */   int tx(int x, int width, int widthtimestwo, int xpluswidth, int xminuswidth)
/*      */   {
/*  141 */     if ((x >= -width) && (x < widthtimestwo))
/*      */     {
/*  143 */       if (x < 0) return xpluswidth;
/*  144 */       if (x < width) return x;
/*  145 */       return xminuswidth;
/*      */     }
/*  147 */     return tx2(x, width);
/*      */   }
/*      */ 
/*      */   int tx2(int x, int width)
/*      */   {
/*  154 */     x %= width;
/*  155 */     if (x < 0) x += width;
/*  156 */     return x;
/*      */   }
/*      */ 
/*      */   int ty(int y, int height, int heighttimestwo, int yplusheight, int yminusheight)
/*      */   {
/*  162 */     if ((y >= -height) && (y < heighttimestwo))
/*      */     {
/*  164 */       if (y < 0) return yplusheight;
/*  165 */       if (y < height) return y;
/*  166 */       return yminusheight;
/*      */     }
/*  168 */     return ty2(y, height);
/*      */   }
/*      */ 
/*      */   int ty2(int y, int height)
/*      */   {
/*  174 */     y %= height;
/*  175 */     if (y < 0) y += height;
/*  176 */     return y;
/*      */   }
/*      */ 
/*      */   protected void removeOrigin(int x, int y, IntBag xPos, IntBag yPos)
/*      */   {
/*  186 */     int size = xPos.size();
/*  187 */     for (int i = 0; i < size; i++)
/*      */     {
/*  189 */       if ((xPos.get(i) == x) && (yPos.get(i) == y))
/*      */       {
/*  191 */         xPos.remove(i);
/*  192 */         yPos.remove(i);
/*  193 */         return;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void removeOriginToroidal(int x, int y, IntBag xPos, IntBag yPos)
/*      */   {
/*  201 */     int size = xPos.size();
/*  202 */     x = tx(x, this.width, this.width * 2, x + this.width, x - this.width);
/*  203 */     y = ty(y, this.height, this.height * 2, y + this.height, y - this.height);
/*      */ 
/*  205 */     for (int i = 0; i < size; i++)
/*      */     {
/*  207 */       if ((tx(xPos.get(i), this.width, this.width * 2, x + this.width, x - this.width) == x) && (ty(yPos.get(i), this.height, this.height * 2, y + this.height, y - this.height) == y))
/*      */       {
/*  209 */         xPos.remove(i);
/*  210 */         yPos.remove(i);
/*  211 */         return;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public int numObjectsAtLocation(int x, int y)
/*      */   {
/*  220 */     return numObjectsAtLocation(new Int2D(x, y));
/*      */   }
/*      */ 
/*      */   public Bag getObjectsAtLocation(int x, int y)
/*      */   {
/*  232 */     return getObjectsAtLocation(new Int2D(x, y));
/*      */   }
/*      */ 
/*      */   public Double2D getObjectLocationAsDouble2D(Object obj)
/*      */   {
/*  238 */     Int2D loc = (Int2D)super.getRawObjectLocation(obj);
/*  239 */     if (loc == null) return null;
/*  240 */     return new Double2D(loc);
/*      */   }
/*      */ 
/*      */   public Int2D getObjectLocation(Object obj)
/*      */   {
/*  246 */     return (Int2D)super.getRawObjectLocation(obj);
/*      */   }
/*      */ 
/*      */   public Bag removeObjectsAtLocation(int x, int y)
/*      */   {
/*  252 */     return removeObjectsAtLocation(new Int2D(x, y));
/*      */   }
/*      */ 
/*      */   public boolean setObjectLocation(Object obj, int x, int y)
/*      */   {
/*  259 */     return super.setObjectLocation(obj, new Int2D(x, y));
/*      */   }
/*      */ 
/*      */   public boolean setObjectLocation(Object obj, Int2D location)
/*      */   {
/*  266 */     return super.setObjectLocation(obj, location);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void getNeighborsMaxDistance(int x, int y, int dist, boolean toroidal, IntBag xPos, IntBag yPos)
/*      */   {
/*  274 */     getMooreLocations(x, y, dist, toroidal ? 2 : 0, true, xPos, yPos);
/*      */   }
/*      */ 
/*      */   public void getMooreLocations(int x, int y, int dist, int mode, boolean includeOrigin, IntBag xPos, IntBag yPos)
/*      */   {
/*  279 */     boolean toroidal = mode == 2;
/*  280 */     boolean bounded = mode == 0;
/*      */ 
/*  282 */     if ((mode != 0) && (mode != 1) && (mode != 2))
/*      */     {
/*  284 */       throw new RuntimeException("Mode must be either Grid2D.BOUNDED, Grid2D.UNBOUNDED, or Grid2D.TOROIDAL");
/*      */     }
/*      */ 
/*  288 */     if (dist < 0)
/*      */     {
/*  290 */       throw new RuntimeException("Distance must be positive");
/*      */     }
/*      */ 
/*  293 */     if ((xPos == null) || (yPos == null))
/*      */     {
/*  295 */       throw new RuntimeException("xPos and yPos should not be null");
/*      */     }
/*      */ 
/*  298 */     if (((x < 0) || (x >= this.width) || (y < 0) || (y >= this.height)) && (!bounded)) {
/*  299 */       throw new RuntimeException("Invalid initial position");
/*      */     }
/*  301 */     xPos.clear();
/*  302 */     yPos.clear();
/*      */ 
/*  305 */     int height = this.height;
/*  306 */     int width = this.width;
/*      */ 
/*  310 */     if (toroidal)
/*      */     {
/*  313 */       int xmin = x - dist;
/*  314 */       int xmax = x + dist;
/*      */ 
/*  317 */       if (xmax - xmin >= width) {
/*  318 */         xmin = 0; xmax = width - 1;
/*      */       }
/*      */ 
/*  321 */       int ymin = y - dist;
/*  322 */       int ymax = y + dist;
/*      */ 
/*  325 */       if (ymax - ymin >= height) {
/*  326 */         ymin = 0; ymax = width - 1;
/*      */       }
/*  328 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*      */       {
/*  330 */         int x_0 = tx(x0, width, width * 2, x0 + width, x0 - width);
/*  331 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*      */         {
/*  333 */           int y_0 = ty(y0, height, height * 2, y0 + height, y0 - height);
/*  334 */           xPos.add(x_0);
/*  335 */           yPos.add(y_0);
/*      */         }
/*      */       }
/*  338 */       if (!includeOrigin) removeOriginToroidal(x, y, xPos, yPos);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  343 */       int xmin = (x - dist >= 0) || (!bounded) ? x - dist : 0;
/*  344 */       int xmax = (x + dist <= width - 1) || (!bounded) ? x + dist : width - 1;
/*      */ 
/*  346 */       int ymin = (y - dist >= 0) || (!bounded) ? y - dist : 0;
/*  347 */       int ymax = (y + dist <= height - 1) || (!bounded) ? y + dist : height - 1;
/*  348 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*      */       {
/*  350 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*      */         {
/*  352 */           xPos.add(x0);
/*  353 */           yPos.add(y0);
/*      */         }
/*      */       }
/*  356 */       if (!includeOrigin) removeOrigin(x, y, xPos, yPos);
/*      */     }
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void getNeighborsHamiltonianDistance(int x, int y, int dist, boolean toroidal, IntBag xPos, IntBag yPos)
/*      */   {
/*  364 */     getVonNeumannLocations(x, y, dist, toroidal ? 2 : 0, true, xPos, yPos);
/*      */   }
/*      */ 
/*      */   public void getVonNeumannLocations(int x, int y, int dist, int mode, boolean includeOrigin, IntBag xPos, IntBag yPos)
/*      */   {
/*  370 */     boolean toroidal = mode == 2;
/*  371 */     boolean bounded = mode == 0;
/*      */ 
/*  373 */     if ((mode != 0) && (mode != 1) && (mode != 2))
/*      */     {
/*  375 */       throw new RuntimeException("Mode must be either Grid2D.BOUNDED, Grid2D.UNBOUNDED, or Grid2D.TOROIDAL");
/*      */     }
/*      */ 
/*  379 */     if (dist < 0)
/*      */     {
/*  381 */       throw new RuntimeException("Distance must be positive");
/*      */     }
/*      */ 
/*  384 */     if ((xPos == null) || (yPos == null))
/*      */     {
/*  386 */       throw new RuntimeException("xPos and yPos should not be null");
/*      */     }
/*      */ 
/*  389 */     if (((x < 0) || (x >= this.width) || (y < 0) || (y >= this.height)) && (!bounded)) {
/*  390 */       throw new RuntimeException("Invalid initial position");
/*      */     }
/*  392 */     xPos.clear();
/*  393 */     yPos.clear();
/*      */ 
/*  396 */     int height = this.height;
/*  397 */     int width = this.width;
/*      */ 
/*  400 */     if (toroidal)
/*      */     {
/*  403 */       int xmax = x + dist;
/*  404 */       int xmin = x - dist;
/*      */ 
/*  406 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*      */       {
/*  408 */         int x_0 = tx(x0, width, width * 2, x0 + width, x0 - width);
/*      */ 
/*  410 */         int ymax = y + (dist - (x0 - x >= 0 ? x0 - x : x - x0));
/*  411 */         int ymin = y - (dist - (x0 - x >= 0 ? x0 - x : x - x0));
/*  412 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*      */         {
/*  414 */           int y_0 = ty(y0, height, height * 2, y0 + height, y0 - height);
/*  415 */           xPos.add(x_0);
/*  416 */           yPos.add(y_0);
/*      */         }
/*      */       }
/*      */ 
/*  420 */       if ((dist * 2 >= width) || (dist * 2 >= height))
/*      */       {
/*  422 */         int sz = xPos.size();
/*  423 */         Map map = buildMap(sz);
/*  424 */         for (int i = 0; i < sz; i++)
/*      */         {
/*  426 */           Double2D elem = new Double2D(xPos.get(i), yPos.get(i));
/*  427 */           if (map.containsKey(elem))
/*      */           {
/*  429 */             xPos.remove(i);
/*  430 */             yPos.remove(i);
/*  431 */             i--;
/*  432 */             sz--;
/*      */           }
/*      */           else
/*      */           {
/*  436 */             map.put(elem, elem);
/*      */           }
/*      */         }
/*      */       }
/*  440 */       if (!includeOrigin) removeOriginToroidal(x, y, xPos, yPos);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  445 */       int xmax = (x + dist <= width - 1) || (!bounded) ? x + dist : width - 1;
/*  446 */       int xmin = (x - dist >= 0) || (!bounded) ? x - dist : 0;
/*  447 */       for (int x0 = xmin; x0 <= xmax; x0++)
/*      */       {
/*  451 */         int ymax = (y + (dist - (x0 - x >= 0 ? x0 - x : x - x0)) <= height - 1) || (!bounded) ? y + (dist - (x0 - x >= 0 ? x0 - x : x - x0)) : height - 1;
/*  452 */         int ymin = (y - (dist - (x0 - x >= 0 ? x0 - x : x - x0)) >= 0) || (!bounded) ? y - (dist - (x0 - x >= 0 ? x0 - x : x - x0)) : 0;
/*  453 */         for (int y0 = ymin; y0 <= ymax; y0++)
/*      */         {
/*  455 */           xPos.add(x0);
/*  456 */           yPos.add(y0);
/*      */         }
/*      */       }
/*  459 */       if (!includeOrigin) removeOrigin(x, y, xPos, yPos);
/*      */     }
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void getNeighborsHexagonalDistance(int x, int y, int dist, boolean toroidal, IntBag xPos, IntBag yPos)
/*      */   {
/*  467 */     getHexagonalLocations(x, y, dist, toroidal ? 2 : 0, true, xPos, yPos);
/*      */   }
/*      */ 
/*      */   public void getHexagonalLocations(int x, int y, int dist, int mode, boolean includeOrigin, IntBag xPos, IntBag yPos)
/*      */   {
/*  472 */     boolean toroidal = mode == 2;
/*  473 */     boolean bounded = mode == 0;
/*      */ 
/*  475 */     if ((mode != 0) && (mode != 1) && (mode != 2))
/*      */     {
/*  477 */       throw new RuntimeException("Mode must be either Grid2D.BOUNDED, Grid2D.UNBOUNDED, or Grid2D.TOROIDAL");
/*      */     }
/*      */ 
/*  481 */     if (dist < 0)
/*      */     {
/*  483 */       throw new RuntimeException("Distance must be positive");
/*      */     }
/*      */ 
/*  486 */     if ((xPos == null) || (yPos == null))
/*      */     {
/*  488 */       throw new RuntimeException("xPos and yPos should not be null");
/*      */     }
/*      */ 
/*  491 */     if (((x < 0) || (x >= this.width) || (y < 0) || (y >= this.height)) && (!bounded)) {
/*  492 */       throw new RuntimeException("Invalid initial position");
/*      */     }
/*  494 */     xPos.clear();
/*  495 */     yPos.clear();
/*      */ 
/*  498 */     int height = this.height;
/*  499 */     int width = this.width;
/*      */ 
/*  501 */     if ((toroidal) && (height % 2 == 1)) {
/*  502 */       throw new RuntimeException("toroidal hexagonal environment should have even heights");
/*      */     }
/*  504 */     if (toroidal)
/*      */     {
/*  507 */       int ymin = y - dist;
/*  508 */       int ymax = y + dist;
/*  509 */       for (int y0 = ymin; y0 <= ymax; y0 = downy(x, y0))
/*      */       {
/*  511 */         xPos.add(tx(x, width, width * 2, x + width, x - width));
/*  512 */         yPos.add(ty(y0, height, height * 2, y0 + height, y0 - height));
/*      */       }
/*  514 */       int x0 = x;
/*  515 */       for (int i = 1; i <= dist; i++)
/*      */       {
/*  517 */         int temp_ymin = ymin;
/*  518 */         ymin = dly(x0, ymin);
/*  519 */         ymax = uly(x0, ymax);
/*  520 */         x0 = dlx(x0, temp_ymin);
/*  521 */         for (int y0 = ymin; y0 <= ymax; y0 = downy(x0, y0))
/*      */         {
/*  523 */           xPos.add(tx(x0, width, width * 2, x0 + width, x0 - width));
/*  524 */           yPos.add(ty(y0, height, height * 2, y0 + height, y0 - height));
/*      */         }
/*      */       }
/*  527 */       x0 = x;
/*  528 */       ymin = y - dist;
/*  529 */       ymax = y + dist;
/*  530 */       for (int i = 1; i <= dist; i++)
/*      */       {
/*  532 */         int temp_ymin = ymin;
/*  533 */         ymin = dry(x0, ymin);
/*  534 */         ymax = ury(x0, ymax);
/*  535 */         x0 = drx(x0, temp_ymin);
/*  536 */         for (int y0 = ymin; y0 <= ymax; y0 = downy(x0, y0))
/*      */         {
/*  538 */           xPos.add(tx(x0, width, width * 2, x0 + width, x0 - width));
/*  539 */           yPos.add(ty(y0, height, height * 2, y0 + height, y0 - height));
/*      */         }
/*      */       }
/*      */ 
/*  543 */       if ((dist * 2 >= width) || (dist * 2 >= height))
/*      */       {
/*  545 */         int sz = xPos.size();
/*  546 */         Map map = buildMap(sz);
/*  547 */         for (int i = 0; i < sz; i++)
/*      */         {
/*  549 */           Double2D elem = new Double2D(xPos.get(i), yPos.get(i));
/*  550 */           if (map.containsKey(elem))
/*      */           {
/*  552 */             xPos.remove(i);
/*  553 */             yPos.remove(i);
/*  554 */             i--;
/*  555 */             sz--;
/*      */           }
/*      */           else
/*      */           {
/*  559 */             map.put(elem, elem);
/*      */           }
/*      */         }
/*      */       }
/*  563 */       if (!includeOrigin) removeOriginToroidal(x, y, xPos, yPos);
/*      */     }
/*      */     else
/*      */     {
/*  567 */       int ymin = y - dist;
/*  568 */       int ymax = y + dist;
/*      */ 
/*  571 */       int ylBound = (ymin >= 0) || (!bounded) ? ymin : 0;
/*  572 */       int yuBound = (ymax < height) || (!bounded) ? ymax : height - 1;
/*      */ 
/*  575 */       for (int y0 = ylBound; y0 <= yuBound; y0 = downy(x, y0))
/*      */       {
/*  577 */         xPos.add(x);
/*  578 */         yPos.add(y0);
/*      */       }
/*      */ 
/*  582 */       int x0 = x;
/*  583 */       ymin = y - dist;
/*  584 */       ymax = y + dist;
/*  585 */       for (int i = 1; i <= dist; i++)
/*      */       {
/*  587 */         int temp_ymin = ymin;
/*  588 */         ymin = dly(x0, ymin);
/*  589 */         ymax = uly(x0, ymax);
/*  590 */         x0 = dlx(x0, temp_ymin);
/*      */ 
/*  592 */         ylBound = (ymin >= 0) || (!bounded) ? ymin : 0;
/*  593 */         yuBound = (ymax < height) || (!bounded) ? ymax : height - 1;
/*      */ 
/*  597 */         if (x0 >= 0) {
/*  598 */           for (int y0 = ylBound; y0 <= yuBound; y0 = downy(x0, y0))
/*      */           {
/*  600 */             if ((y0 >= 0) || (!bounded))
/*      */             {
/*  602 */               xPos.add(x0);
/*  603 */               yPos.add(y0);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  608 */       x0 = x;
/*  609 */       ymin = y - dist;
/*  610 */       ymax = y + dist;
/*  611 */       for (int i = 1; i <= dist; i++)
/*      */       {
/*  613 */         int temp_ymin = ymin;
/*  614 */         ymin = dry(x0, ymin);
/*  615 */         ymax = ury(x0, ymax);
/*  616 */         x0 = drx(x0, temp_ymin);
/*      */ 
/*  618 */         ylBound = (ymin >= 0) || (!bounded) ? ymin : 0;
/*  619 */         yuBound = (ymax < height) || (!bounded) ? ymax : height - 1;
/*      */ 
/*  623 */         if (x0 < width)
/*  624 */           for (int y0 = ymin; y0 <= yuBound; y0 = downy(x0, y0))
/*      */           {
/*  626 */             if ((y0 >= 0) || (!bounded))
/*      */             {
/*  628 */               xPos.add(x0);
/*  629 */               yPos.add(y0);
/*      */             }
/*      */           }
/*      */       }
/*  633 */       if (!includeOrigin) removeOrigin(x, y, xPos, yPos);
/*      */     }
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Bag getNeighborsMaxDistance(int x, int y, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/*  690 */     return getMooreNeighbors(x, y, dist, toroidal ? 2 : 0, true, result, xPos, yPos);
/*      */   }
/*      */ 
/*      */   public Bag getMooreNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/*  721 */     if (xPos == null)
/*  722 */       xPos = new IntBag();
/*  723 */     if (yPos == null) {
/*  724 */       yPos = new IntBag();
/*      */     }
/*  726 */     getMooreLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/*  727 */     return getObjectsAtLocations(xPos, yPos, result);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Bag getNeighborsAndCorrespondingPositionsMaxDistance(int x, int y, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/*  762 */     return getMooreNeighborsAndLocations(x, y, dist, toroidal ? 2 : 0, result, xPos, yPos);
/*      */   }
/*      */ 
/*      */   public Bag getMooreNeighborsAndLocations(int x, int y, int dist, int mode, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/*  794 */     if (xPos == null)
/*  795 */       xPos = new IntBag();
/*  796 */     if (yPos == null) {
/*  797 */       yPos = new IntBag();
/*      */     }
/*  799 */     getMooreLocations(x, y, dist, mode, true, xPos, yPos);
/*  800 */     reduceObjectsAtLocations(xPos, yPos, result);
/*  801 */     return result;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Bag getNeighborsHamiltonianDistance(int x, int y, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/*  838 */     return getVonNeumannNeighbors(x, y, dist, toroidal ? 2 : 0, true, result, xPos, yPos);
/*      */   }
/*      */ 
/*      */   public Bag getVonNeumannNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/*  870 */     if (xPos == null)
/*  871 */       xPos = new IntBag();
/*  872 */     if (yPos == null) {
/*  873 */       yPos = new IntBag();
/*      */     }
/*  875 */     getVonNeumannLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/*  876 */     return getObjectsAtLocations(xPos, yPos, result);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Bag getNeighborsAndCorrespondingPositionsHamiltonianDistance(int x, int y, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/*  914 */     return getVonNeumannNeighborsAndLocations(x, y, dist, toroidal ? 2 : 0, result, xPos, yPos);
/*      */   }
/*      */ 
/*      */   public Bag getVonNeumannNeighborsAndLocations(int x, int y, int dist, int mode, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/*  947 */     if (xPos == null)
/*  948 */       xPos = new IntBag();
/*  949 */     if (yPos == null) {
/*  950 */       yPos = new IntBag();
/*      */     }
/*  952 */     getVonNeumannLocations(x, y, dist, mode, true, xPos, yPos);
/*  953 */     reduceObjectsAtLocations(xPos, yPos, result);
/*  954 */     return result;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Bag getNeighborsHexagonalDistance(int x, int y, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/*  992 */     return getHexagonalNeighbors(x, y, dist, toroidal ? 2 : 0, true, result, xPos, yPos);
/*      */   }
/*      */ 
/*      */   public Bag getHexagonalNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/* 1024 */     if (xPos == null)
/* 1025 */       xPos = new IntBag();
/* 1026 */     if (yPos == null) {
/* 1027 */       yPos = new IntBag();
/*      */     }
/* 1029 */     getHexagonalLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 1030 */     return getObjectsAtLocations(xPos, yPos, result);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Bag getNeighborsAndCorrespondingPositionsHexagonalDistance(int x, int y, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/* 1067 */     return getHexagonalNeighborsAndLocations(x, y, dist, toroidal ? 2 : 0, result, xPos, yPos);
/*      */   }
/*      */ 
/*      */   public Bag getHexagonalNeighborsAndLocations(int x, int y, int dist, int mode, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/* 1100 */     if (xPos == null)
/* 1101 */       xPos = new IntBag();
/* 1102 */     if (yPos == null) {
/* 1103 */       yPos = new IntBag();
/*      */     }
/* 1105 */     getHexagonalLocations(x, y, dist, mode, true, xPos, yPos);
/* 1106 */     reduceObjectsAtLocations(xPos, yPos, result);
/* 1107 */     return result;
/*      */   }
/*      */ 
/*      */   void reduceObjectsAtLocations(IntBag xPos, IntBag yPos, Bag result)
/*      */   {
/* 1116 */     if (result == null) result = new Bag(); else {
/* 1117 */       result.clear();
/*      */     }
/*      */ 
/* 1120 */     IntBag newXPos = new IntBag();
/* 1121 */     IntBag newYPos = new IntBag();
/*      */ 
/* 1123 */     int len = xPos.numObjs;
/* 1124 */     int[] xs = xPos.objs;
/* 1125 */     int[] ys = yPos.objs;
/*      */ 
/* 1128 */     for (int i = 0; i < len; i++)
/*      */     {
/* 1130 */       Bag temp = getObjectsAtLocation(xs[i], ys[i]);
/* 1131 */       if (temp != null)
/*      */       {
/* 1133 */         int size = temp.numObjs;
/* 1134 */         Object[] os = temp.objs;
/*      */ 
/* 1136 */         for (int j = 0; j < size; j++)
/*      */         {
/* 1139 */           result.add(os[j]);
/* 1140 */           newXPos.add(xs[i]);
/* 1141 */           newYPos.add(ys[i]);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1147 */     xPos.clear();
/* 1148 */     xPos.addAll(newXPos);
/* 1149 */     yPos.clear();
/* 1150 */     yPos.addAll(newYPos);
/*      */   }
/*      */ 
/*      */   Bag getObjectsAtLocations(IntBag xPos, IntBag yPos, Bag result)
/*      */   {
/* 1158 */     if (result == null) result = new Bag(); else {
/* 1159 */       result.clear();
/*      */     }
/* 1161 */     int len = xPos.numObjs;
/* 1162 */     int[] xs = xPos.objs;
/* 1163 */     int[] ys = yPos.objs;
/* 1164 */     for (int i = 0; i < len; i++)
/*      */     {
/* 1168 */       Bag temp = getObjectsAtLocation(xs[i], ys[i]);
/* 1169 */       if (temp != null)
/*      */       {
/* 1171 */         int n = temp.numObjs;
/* 1172 */         if (n == 1) result.add(temp.objs[0]);
/* 1173 */         else if (n > 1) result.addAll(temp);
/*      */       }
/*      */     }
/* 1176 */     return result;
/*      */   }
/*      */ 
/*      */   double ds(double d1x, double d1y, double d2x, double d2y)
/*      */   {
/* 1185 */     return (d1x - d2x) * (d1x - d2x) + (d1y - d2y) * (d1y - d2y);
/*      */   }
/*      */ 
/*      */   boolean within(double d1x, double d1y, double d2x, double d2y, double distanceSquared, boolean closed)
/*      */   {
/* 1190 */     double d = ds(d1x, d1y, d2x, d2y);
/* 1191 */     return (d < distanceSquared) || ((d == distanceSquared) && (closed));
/*      */   }
/*      */ 
/*      */   public void getRadialLocations(int x, int y, double dist, int mode, boolean includeOrigin, IntBag xPos, IntBag yPos)
/*      */   {
/* 1196 */     getRadialLocations(x, y, dist, mode, includeOrigin, 1026, true, xPos, yPos);
/*      */   }
/*      */ 
/*      */   public void getRadialLocations(int x, int y, double dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, IntBag xPos, IntBag yPos)
/*      */   {
/* 1201 */     boolean toroidal = mode == 2;
/*      */ 
/* 1204 */     if (dist < 0.0D)
/*      */     {
/* 1206 */       throw new RuntimeException("Distance must be positive");
/*      */     }
/*      */ 
/* 1209 */     if ((measurementRule != 1026) && (measurementRule != 1025) && (measurementRule != 1024))
/*      */     {
/* 1211 */       throw new RuntimeException(" Measurement rule must be one of ANY, ALL, or CENTER");
/*      */     }
/*      */ 
/* 1215 */     if (toroidal)
/* 1216 */       getMooreLocations(x, y, (int)Math.ceil(dist + 0.5D), 1, includeOrigin, xPos, yPos);
/*      */     else
/* 1218 */       getMooreLocations(x, y, (int)Math.ceil(dist + 0.5D), mode, includeOrigin, xPos, yPos);
/* 1219 */     int len = xPos.size();
/* 1220 */     double distsq = dist * dist;
/*      */ 
/* 1222 */     int width = this.width;
/* 1223 */     int height = this.height;
/* 1224 */     int widthtimestwo = width * 2;
/* 1225 */     int heighttimestwo = height * 2;
/*      */ 
/* 1227 */     for (int i = 0; i < len; i++)
/*      */     {
/* 1229 */       int xp = xPos.get(i);
/* 1230 */       int yp = yPos.get(i);
/* 1231 */       boolean remove = false;
/*      */ 
/* 1233 */       if (measurementRule == 1026)
/*      */       {
/* 1235 */         if (x == xp)
/*      */         {
/* 1237 */           if (y < yp)
/*      */           {
/* 1239 */             double d = yp - 0.5D - y;
/* 1240 */             remove = (d >= dist) && ((d != dist) || (!closed));
/*      */           }
/*      */           else
/*      */           {
/* 1244 */             double d = -(yp - 0.5D - y);
/* 1245 */             remove = (d >= dist) && ((d != dist) || (!closed));
/*      */           }
/*      */         }
/* 1248 */         else if (y == yp)
/*      */         {
/* 1250 */           if (x < xp)
/*      */           {
/* 1252 */             double d = xp - 0.5D - x;
/* 1253 */             remove = (d >= dist) && ((d != dist) || (!closed));
/*      */           }
/*      */           else
/*      */           {
/* 1257 */             double d = -(xp - 0.5D - x);
/* 1258 */             remove = (d >= dist) && ((d != dist) || (!closed));
/*      */           }
/*      */         }
/* 1261 */         if (x < xp)
/*      */         {
/* 1263 */           if (y < yp)
/* 1264 */             remove = !within(x, y, xp - 0.5D, yp - 0.5D, distsq, closed);
/*      */           else {
/* 1266 */             remove = !within(x, y, xp - 0.5D, yp + 0.5D, distsq, closed);
/*      */           }
/*      */ 
/*      */         }
/* 1270 */         else if (y < yp)
/* 1271 */           remove = !within(x, y, xp + 0.5D, yp - 0.5D, distsq, closed);
/*      */         else {
/* 1273 */           remove = !within(x, y, xp + 0.5D, yp + 0.5D, distsq, closed);
/*      */         }
/*      */       }
/* 1276 */       else if (measurementRule == 1025)
/*      */       {
/* 1278 */         if (x < xp)
/*      */         {
/* 1280 */           if (y < yp)
/* 1281 */             remove = !within(x, y, xp + 0.5D, yp + 0.5D, distsq, closed);
/*      */           else {
/* 1283 */             remove = !within(x, y, xp + 0.5D, yp - 0.5D, distsq, closed);
/*      */           }
/*      */ 
/*      */         }
/* 1287 */         else if (y < yp)
/* 1288 */           remove = !within(x, y, xp - 0.5D, yp + 0.5D, distsq, closed);
/*      */         else {
/* 1290 */           remove = !within(x, y, xp - 0.5D, yp - 0.5D, distsq, closed);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1295 */         remove = !within(x, y, xp, yp, distsq, closed);
/*      */       }
/*      */ 
/* 1298 */       if (remove) {
/* 1299 */         xPos.remove(i); yPos.remove(i); i--; len--;
/* 1300 */       } else if (toroidal)
/*      */       {
/* 1302 */         int _x = xPos.get(i);
/* 1303 */         int _y = yPos.get(i);
/* 1304 */         xPos.set(i, tx(_x, width, widthtimestwo, _x + width, _x - width));
/* 1305 */         yPos.set(i, tx(_y, height, heighttimestwo, _y + width, _y - width));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Bag getRadialNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/* 1313 */     return getRadialNeighbors(x, y, dist, mode, includeOrigin, 1026, true, result, xPos, yPos);
/*      */   }
/*      */ 
/*      */   public Bag getRadialNeighborsAndLocations(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/* 1319 */     return getRadialNeighborsAndLocations(x, y, dist, mode, includeOrigin, 1026, true, result, xPos, yPos);
/*      */   }
/*      */ 
/*      */   public Bag getRadialNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/* 1326 */     if (xPos == null)
/* 1327 */       xPos = new IntBag();
/* 1328 */     if (yPos == null) {
/* 1329 */       yPos = new IntBag();
/*      */     }
/* 1331 */     getRadialLocations(x, y, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos);
/* 1332 */     return getObjectsAtLocations(xPos, yPos, result);
/*      */   }
/*      */ 
/*      */   public Bag getRadialNeighborsAndLocations(int x, int y, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, Bag result, IntBag xPos, IntBag yPos)
/*      */   {
/* 1338 */     if (xPos == null)
/* 1339 */       xPos = new IntBag();
/* 1340 */     if (yPos == null) {
/* 1341 */       yPos = new IntBag();
/*      */     }
/* 1343 */     getRadialLocations(x, y, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos);
/* 1344 */     reduceObjectsAtLocations(xPos, yPos, result);
/* 1345 */     return getObjectsAtLocations(xPos, yPos, result);
/*      */   }
/*      */ 
/*      */   public Bag getMooreNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*      */   {
/* 1368 */     return getMooreNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*      */   }
/*      */ 
/*      */   public Bag getVonNeumannNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*      */   {
/* 1392 */     return getVonNeumannNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*      */   }
/*      */ 
/*      */   public Bag getHexagonalNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*      */   {
/* 1417 */     return getHexagonalNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*      */   }
/*      */ 
/*      */   public Bag getRadialNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*      */   {
/* 1423 */     return getRadialNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*      */   }
/*      */ 
/*      */   public final Double2D getDimensions()
/*      */   {
/* 1431 */     return new Double2D(this.width, this.height);
/*      */   }
/*      */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.grid.SparseGrid2D
 * JD-Core Version:    0.6.2
 */