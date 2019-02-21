/*     */ package sim.field.continuous;
/*     */ 
/*     */ import java.util.Map;
/*     */ import sim.field.SparseField;
/*     */ import sim.field.SparseField2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.MutableInt2D;
/*     */ 
/*     */ public class Continuous2D extends SparseField
/*     */   implements SparseField2D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  92 */   public Map doubleLocationHash = buildMap(0);
/*     */   public double width;
/*     */   public double height;
/*     */   public final double discretization;
/* 284 */   static final double SQRT_2_MINUS_1_DIV_2 = (Math.sqrt(2.0D) - 1.0D) * 0.5D;
/*     */   static final int NEAREST_NEIGHBOR_GAIN = 10;
/*     */ 
/*     */   public Continuous2D(double discretization, double width, double height)
/*     */   {
/* 102 */     this.discretization = discretization;
/* 103 */     this.width = width;
/* 104 */     this.height = height;
/*     */   }
/*     */ 
/*     */   public Continuous2D(Continuous2D other)
/*     */   {
/* 109 */     super(other);
/* 110 */     this.discretization = other.discretization;
/* 111 */     this.width = other.width;
/* 112 */     this.height = other.height;
/*     */   }
/*     */ 
/*     */   public final Double2D getObjectLocation(Object obj)
/*     */   {
/* 117 */     return (Double2D)this.doubleLocationHash.get(obj);
/*     */   }
/*     */ 
/*     */   public final Double2D getObjectLocationAsDouble2D(Object obj)
/*     */   {
/* 123 */     return (Double2D)this.doubleLocationHash.get(obj);
/*     */   }
/*     */   public final Double2D getDimensions() {
/* 126 */     return new Double2D(this.width, this.height);
/*     */   }
/*     */ 
/*     */   public final Int2D discretize(Double2D location)
/*     */   {
/* 132 */     return new Int2D((int)(location.x / this.discretization), (int)(location.y / this.discretization));
/*     */   }
/*     */ 
/*     */   public final Int2D discretize(Double2D location, int discretization)
/*     */   {
/* 139 */     return new Int2D((int)(location.x / discretization), (int)(location.y / discretization));
/*     */   }
/*     */ 
/*     */   public final boolean setObjectLocation(Object obj, Double2D location)
/*     */   {
/* 144 */     boolean result = super.setObjectLocation(obj, discretize(location));
/* 145 */     if (result) this.doubleLocationHash.put(obj, location);
/* 146 */     return result;
/*     */   }
/*     */ 
/*     */   public final Bag clear()
/*     */   {
/* 151 */     this.doubleLocationHash = buildMap(0);
/* 152 */     return super.clear();
/*     */   }
/*     */ 
/*     */   public final Object remove(Object obj)
/*     */   {
/* 157 */     Object result = super.remove(obj);
/* 158 */     this.doubleLocationHash.remove(obj);
/* 159 */     return result;
/*     */   }
/*     */ 
/*     */   public double getWidth() {
/* 163 */     return this.width;
/*     */   }
/*     */   public double getHeight() {
/* 166 */     return this.height;
/*     */   }
/*     */ 
/*     */   public final double tx(double x)
/*     */   {
/* 172 */     double width = this.width;
/* 173 */     if ((x >= 0.0D) && (x < width)) return x;
/* 174 */     x %= width;
/* 175 */     if (x < 0.0D) x += width;
/* 176 */     return x;
/*     */   }
/*     */ 
/*     */   public final double ty(double y)
/*     */   {
/* 183 */     double height = this.height;
/* 184 */     if ((y >= 0.0D) && (y < height)) return y;
/* 185 */     y %= height;
/* 186 */     if (y < 0.0D) y += height;
/* 187 */     return y;
/*     */   }
/*     */ 
/*     */   public double stx(double x)
/*     */   {
/* 222 */     if (x >= 0.0D) { if (x < this.width) return x; return x - this.width; } return x + this.width;
/*     */   }
/*     */ 
/*     */   public double sty(double y)
/*     */   {
/* 233 */     if (y >= 0.0D) { if (y < this.height) return y; return y - this.height; } return y + this.height;
/*     */   }
/*     */ 
/*     */   double _stx(double x, double width)
/*     */   {
/* 238 */     if (x >= 0.0D) { if (x < width) return x; return x - width; } return x + width;
/*     */   }
/*     */ 
/*     */   public double tdx(double x1, double x2)
/*     */   {
/* 243 */     double width = this.width;
/* 244 */     if (Math.abs(x1 - x2) <= width / 2.0D) {
/* 245 */       return x1 - x2;
/*     */     }
/* 247 */     double dx = _stx(x1, width) - _stx(x2, width);
/* 248 */     if (dx * 2.0D > width) return dx - width;
/* 249 */     if (dx * 2.0D < -width) return dx + width;
/* 250 */     return dx;
/*     */   }
/*     */ 
/*     */   double _sty(double y, double height)
/*     */   {
/* 255 */     if (y >= 0.0D) { if (y < height) return y; return y - height; } return y + height;
/*     */   }
/*     */ 
/*     */   public double tdy(double y1, double y2)
/*     */   {
/* 260 */     double height = this.height;
/* 261 */     if (Math.abs(y1 - y2) <= height / 2.0D) {
/* 262 */       return y1 - y2;
/*     */     }
/* 264 */     double dy = _sty(y1, height) - _sty(y2, height);
/* 265 */     if (dy * 2.0D > height) return dy - height;
/* 266 */     if (dy * 2.0D < -height) return dy + height;
/* 267 */     return dy;
/*     */   }
/*     */ 
/*     */   public double tds(Double2D d1, Double2D d2)
/*     */   {
/* 273 */     double dx = tdx(d1.x, d2.x);
/* 274 */     double dy = tdy(d1.y, d2.y);
/* 275 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public Double2D tv(Double2D d1, Double2D d2)
/*     */   {
/* 281 */     return new Double2D(tdx(d1.x, d2.x), tdy(d1.y, d2.y));
/*     */   }
/*     */ 
/*     */   public Bag getNearestNeighbors(Double2D position, int atLeastThisMany, boolean toroidal, boolean nonPointObjects, boolean radial, Bag result)
/*     */   {
/* 296 */     if (toroidal) throw new InternalError("Toroidal not presently supported in getNearestNeighbors");
/* 297 */     if (result == null) result = new Bag(atLeastThisMany); else
/* 298 */       result.clear();
/* 299 */     int maxSearches = this.allObjects.numObjs / 10;
/*     */ 
/* 301 */     if (atLeastThisMany >= this.allObjects.numObjs) { result.clear(); result.addAll(this.allObjects); return result;
/*     */     }
/* 303 */     Int2D d = discretize(position);
/* 304 */     int x1 = d.x;
/* 305 */     int x2 = d.x;
/* 306 */     int y1 = d.y;
/* 307 */     int y2 = d.y;
/* 308 */     int searches = 0;
/*     */ 
/* 310 */     MutableInt2D speedyMutableInt2D = new MutableInt2D();
/*     */ 
/* 314 */     if (searches >= maxSearches) { result.clear(); result.addAll(this.allObjects); return result; }
/* 315 */     searches++;
/* 316 */     speedyMutableInt2D.x = x1; speedyMutableInt2D.y = y1;
/* 317 */     Bag temp = getRawObjectsAtLocation(speedyMutableInt2D);
/* 318 */     if (temp != null) result.addAll(temp);
/*     */ 
/* 320 */     boolean nonPointOneMoreTime = false;
/*     */     while (true)
/*     */     {
/* 324 */       if (result.numObjs >= atLeastThisMany)
/*     */       {
/* 326 */         if ((!nonPointObjects) || (nonPointOneMoreTime)) break;
/* 327 */         nonPointOneMoreTime = true;
/*     */       }
/*     */ 
/* 331 */       x1--; y1--; x2++; y2++;
/*     */ 
/* 333 */       speedyMutableInt2D.y = y1;
/* 334 */       for (int x = x1; x <= x2; x++)
/*     */       {
/* 336 */         if (searches >= maxSearches) { result.clear(); result.addAll(this.allObjects); return result; }
/* 337 */         searches++;
/* 338 */         speedyMutableInt2D.x = x;
/* 339 */         temp = getRawObjectsAtLocation(speedyMutableInt2D);
/* 340 */         if (temp != null) result.addAll(temp);
/*     */ 
/*     */       }
/*     */ 
/* 344 */       speedyMutableInt2D.y = y2;
/* 345 */       for (int x = x1; x <= x2; x++)
/*     */       {
/* 347 */         if (searches >= maxSearches) { result.clear(); result.addAll(this.allObjects); return result; }
/* 348 */         searches++;
/* 349 */         speedyMutableInt2D.x = x;
/* 350 */         temp = getRawObjectsAtLocation(speedyMutableInt2D);
/* 351 */         if (temp != null) result.addAll(temp);
/*     */ 
/*     */       }
/*     */ 
/* 355 */       speedyMutableInt2D.x = x1;
/* 356 */       for (int y = y1 + 1; y <= y2 - 1; y++)
/*     */       {
/* 358 */         if (searches >= maxSearches) { result.clear(); result.addAll(this.allObjects); return result; }
/* 359 */         searches++;
/* 360 */         speedyMutableInt2D.y = y;
/* 361 */         temp = getRawObjectsAtLocation(speedyMutableInt2D);
/* 362 */         if (temp != null) result.addAll(temp);
/*     */ 
/*     */       }
/*     */ 
/* 366 */       speedyMutableInt2D.x = x2;
/* 367 */       for (int y = y1 + 1; y <= y2 - 1; y++)
/*     */       {
/* 369 */         if (searches >= maxSearches) { result.clear(); result.addAll(this.allObjects); return result; }
/* 370 */         searches++;
/* 371 */         speedyMutableInt2D.y = y;
/* 372 */         temp = getRawObjectsAtLocation(speedyMutableInt2D);
/* 373 */         if (temp != null) result.addAll(temp);
/*     */       }
/*     */     }
/*     */ 
/* 377 */     if (!radial) return result;
/*     */ 
/* 395 */     int n = x2 - x1 + 1;
/* 396 */     int e = (int)Math.floor(n * SQRT_2_MINUS_1_DIV_2) + 1;
/*     */ 
/* 399 */     int numAdditionalSearches = (x2 - x1 + 1) * e * 4;
/* 400 */     if (searches + numAdditionalSearches >= maxSearches) { result.clear(); result.addAll(this.allObjects); return result;
/*     */     }
/*     */ 
/* 403 */     for (int x = 0; x < x2 - x1 + 1; x++)
/*     */     {
/* 405 */       for (int y = 0; y < e; y++)
/*     */       {
/* 408 */         speedyMutableInt2D.x = (x1 + x);
/* 409 */         speedyMutableInt2D.y = (y1 - e - 1);
/* 410 */         temp = getRawObjectsAtLocation(speedyMutableInt2D);
/* 411 */         if (temp != null) result.addAll(temp);
/*     */ 
/* 414 */         speedyMutableInt2D.x = (x1 + x);
/* 415 */         speedyMutableInt2D.y = (y2 + e + 1);
/* 416 */         temp = getRawObjectsAtLocation(speedyMutableInt2D);
/* 417 */         if (temp != null) result.addAll(temp);
/*     */ 
/* 420 */         speedyMutableInt2D.x = (x1 - e - 1);
/* 421 */         speedyMutableInt2D.y = (y1 + x);
/* 422 */         temp = getRawObjectsAtLocation(speedyMutableInt2D);
/* 423 */         if (temp != null) result.addAll(temp);
/*     */ 
/* 426 */         speedyMutableInt2D.x = (x2 + e + 1);
/* 427 */         speedyMutableInt2D.y = (y1 + x);
/* 428 */         temp = getRawObjectsAtLocation(speedyMutableInt2D);
/* 429 */         if (temp != null) result.addAll(temp);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 434 */     return result;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getObjectsExactlyWithinDistance(Double2D position, double distance)
/*     */   {
/* 448 */     return getObjectsExactlyWithinDistance(position, distance, false, true, true, null);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getObjectsExactlyWithinDistance(Double2D position, double distance, boolean toroidal)
/*     */   {
/* 462 */     return getObjectsExactlyWithinDistance(position, distance, toroidal, true, true, null);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getObjectsExactlyWithinDistance(Double2D position, double distance, boolean toroidal, boolean radial, boolean inclusive, Bag result)
/*     */   {
/* 481 */     return getNeighborsExactlyWithinDistance(position, distance, toroidal, radial, inclusive, result);
/*     */   }
/*     */ 
/*     */   public Bag getNeighborsExactlyWithinDistance(Double2D position, double distance)
/*     */   {
/* 494 */     return getNeighborsExactlyWithinDistance(position, distance, false, true, true, null);
/*     */   }
/*     */ 
/*     */   public Bag getNeighborsExactlyWithinDistance(Double2D position, double distance, boolean toroidal)
/*     */   {
/* 507 */     return getNeighborsExactlyWithinDistance(position, distance, toroidal, true, true, null);
/*     */   }
/*     */ 
/*     */   public Bag getNeighborsExactlyWithinDistance(Double2D position, double distance, boolean toroidal, boolean radial, boolean inclusive, Bag result)
/*     */   {
/* 525 */     result = getNeighborsWithinDistance(position, distance, toroidal, false, result);
/* 526 */     int numObjs = result.numObjs;
/* 527 */     Object[] objs = result.objs;
/* 528 */     double distsq = distance * distance;
/* 529 */     if (radial)
/* 530 */       for (int i = 0; i < numObjs; i++)
/*     */       {
/* 532 */         double d = 0.0D;
/* 533 */         Double2D loc = getObjectLocation(objs[i]);
/* 534 */         if (toroidal) d = tds(position, loc); else
/* 535 */           d = position.distanceSq(loc);
/* 536 */         if ((d > distsq) || ((!inclusive) && (d >= distsq))) {
/* 537 */           result.remove(i); i--; numObjs--;
/*     */         }
/*     */       }
/* 540 */     else for (int i = 0; i < numObjs; i++)
/*     */       {
/* 542 */         Double2D loc = getObjectLocation(objs[i]);
/* 543 */         double minx = 0.0D;
/* 544 */         double miny = 0.0D;
/* 545 */         if (toroidal)
/*     */         {
/* 547 */           minx = tdx(loc.x, position.x);
/* 548 */           miny = tdy(loc.y, position.y);
/*     */         }
/*     */         else
/*     */         {
/* 552 */           minx = loc.x - position.x;
/* 553 */           miny = loc.y - position.y;
/*     */         }
/* 555 */         if (minx < 0.0D) minx = -minx;
/* 556 */         if (miny < 0.0D) miny = -miny;
/* 557 */         if ((minx > distance) || (miny > distance) || ((!inclusive) && ((minx >= distance) || (miny >= distance))))
/*     */         {
/* 559 */           result.remove(i); i--; numObjs--;
/*     */         }
/*     */       } return result;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getObjectsWithinDistance(Double2D position, double distance)
/*     */   {
/* 578 */     return getObjectsWithinDistance(position, distance, false, false, null);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getObjectsWithinDistance(Double2D position, double distance, boolean toroidal)
/*     */   {
/* 595 */     return getObjectsWithinDistance(position, distance, toroidal, false, null);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getObjectsWithinDistance(Double2D position, double distance, boolean toroidal, boolean nonPointObjects)
/*     */   {
/* 616 */     return getObjectsWithinDistance(position, distance, toroidal, nonPointObjects, null);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getObjectsWithinDistance(Double2D position, double distance, boolean toroidal, boolean nonPointObjects, Bag result)
/*     */   {
/* 640 */     return getNeighborsWithinDistance(position, distance, toroidal, nonPointObjects, result);
/*     */   }
/*     */ 
/*     */   public Bag getNeighborsWithinDistance(Double2D position, double distance)
/*     */   {
/* 658 */     return getNeighborsWithinDistance(position, distance, false, false, null);
/*     */   }
/*     */ 
/*     */   public Bag getNeighborsWithinDistance(Double2D position, double distance, boolean toroidal)
/*     */   {
/* 674 */     return getNeighborsWithinDistance(position, distance, toroidal, false, null);
/*     */   }
/*     */ 
/*     */   public Bag getNeighborsWithinDistance(Double2D position, double distance, boolean toroidal, boolean nonPointObjects)
/*     */   {
/* 694 */     return getNeighborsWithinDistance(position, distance, toroidal, nonPointObjects, null);
/*     */   }
/*     */ 
/*     */   public Bag getNeighborsWithinDistance(Double2D position, double distance, boolean toroidal, boolean nonPointObjects, Bag result)
/*     */   {
/* 718 */     if ((toroidal) && ((position.x >= this.width) || (position.y >= this.height) || (position.x < 0.0D) || (position.y < 0.0D))) {
/* 719 */       position = new Double2D(tx(position.x), ty(position.y));
/*     */     }
/* 721 */     double discDistance = distance / this.discretization;
/* 722 */     double discX = position.x / this.discretization;
/* 723 */     double discY = position.y / this.discretization;
/*     */ 
/* 725 */     if (nonPointObjects)
/*     */     {
/* 731 */       discDistance += 1.0D;
/*     */     }
/*     */ 
/* 734 */     int expectedBagSize = 1;
/* 735 */     if (result != null) result.clear(); else {
/* 736 */       result = new Bag(1);
/*     */     }
/*     */ 
/* 739 */     MutableInt2D speedyMutableInt2D = new MutableInt2D();
/*     */ 
/* 743 */     if (toroidal)
/*     */     {
/* 745 */       int iWidth = (int)StrictMath.ceil(this.width / this.discretization);
/* 746 */       int iHeight = (int)StrictMath.ceil(this.height / this.discretization);
/*     */ 
/* 753 */       int minX = (int)StrictMath.floor(discX - discDistance);
/* 754 */       int maxX = (int)StrictMath.floor(discX + discDistance);
/* 755 */       int minY = (int)StrictMath.floor(discY - discDistance);
/* 756 */       int maxY = (int)StrictMath.floor(discY + discDistance);
/*     */ 
/* 758 */       if ((position.x + distance >= this.width) && (maxX == iWidth - 1)) {
/* 759 */         maxX = 0;
/*     */       }
/* 761 */       if ((position.y + distance >= this.height) && (maxY == iHeight - 1)) {
/* 762 */         maxY = 0;
/*     */       }
/*     */ 
/* 767 */       if (maxX - minX >= iWidth) {
/* 768 */         minX = 0; maxX = iWidth - 1;
/* 769 */       }if (maxY - minY >= iHeight) {
/* 770 */         minY = 0; maxY = iHeight - 1;
/*     */       }
/*     */ 
/* 773 */       int tmaxX = toroidal(maxX, iWidth);
/* 774 */       int tmaxY = toroidal(maxY, iHeight);
/* 775 */       int tminX = toroidal(minX, iWidth);
/* 776 */       int tminY = toroidal(minY, iHeight);
/*     */ 
/* 778 */       int x = tminX;
/*     */       while (true)
/*     */       {
/* 781 */         int y = tminY;
/*     */         while (true)
/*     */         {
/* 785 */           speedyMutableInt2D.x = x;
/* 786 */           speedyMutableInt2D.y = y;
/* 787 */           Bag temp = getRawObjectsAtLocation(speedyMutableInt2D);
/* 788 */           if ((temp != null) && (!temp.isEmpty()))
/*     */           {
/* 792 */             int n = temp.numObjs;
/* 793 */             if (n == 1) result.add(temp.objs[0]); else {
/* 794 */               result.addAll(temp);
/*     */             }
/*     */           }
/*     */ 
/* 798 */           if (y == tmaxY)
/*     */             break;
/* 800 */           if (y == iHeight - 1)
/* 801 */             y = 0;
/*     */           else {
/* 803 */             y++;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 808 */         if (x == tmaxX)
/*     */           break;
/* 810 */         if (x == iWidth - 1)
/* 811 */           x = 0;
/*     */         else {
/* 813 */           x++;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 824 */       int minX = (int)StrictMath.floor(discX - discDistance);
/* 825 */       int maxX = (int)StrictMath.floor(discX + discDistance);
/* 826 */       int minY = (int)StrictMath.floor(discY - discDistance);
/* 827 */       int maxY = (int)StrictMath.floor(discY + discDistance);
/*     */ 
/* 830 */       for (int x = minX; x <= maxX; x++) {
/* 831 */         for (int y = minY; y <= maxY; y++)
/*     */         {
/* 834 */           speedyMutableInt2D.x = x;
/* 835 */           speedyMutableInt2D.y = y;
/* 836 */           Bag temp = getRawObjectsAtLocation(speedyMutableInt2D);
/* 837 */           if ((temp != null) && (!temp.isEmpty()))
/*     */           {
/* 841 */             int n = temp.numObjs;
/* 842 */             if (n == 1) result.add(temp.objs[0]); else
/* 843 */               result.addAll(temp);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 848 */     return result;
/*     */   }
/*     */ 
/*     */   final int toroidal(int x, int width)
/*     */   {
/* 855 */     if (x >= 0) return x % width;
/* 856 */     int width2 = x % width + width;
/* 857 */     if (width2 < width) return width2;
/* 858 */     return 0;
/*     */   }
/*     */ 
/*     */   public Bag getObjectsAtDiscretizedLocation(Int2D location)
/*     */   {
/* 871 */     return getRawObjectsAtLocation(location);
/*     */   }
/*     */ 
/*     */   public Bag getObjectsAtLocation(Double2D location)
/*     */   {
/* 879 */     if (location == null) return null;
/* 880 */     Bag cell = getRawObjectsAtLocation(discretize(location));
/* 881 */     if (cell == null) return null;
/* 882 */     Bag result = new Bag();
/* 883 */     Object[] objs = cell.objs;
/* 884 */     int numObjs = cell.numObjs;
/*     */ 
/* 886 */     for (int i = 0; i < numObjs; i++)
/*     */     {
/* 888 */       Object loc = getObjectLocation(objs[i]);
/* 889 */       if (loc.equals(location))
/* 890 */         result.add(objs[i]);
/*     */     }
/* 892 */     return result;
/*     */   }
/*     */ 
/*     */   public int numObjectsAtLocation(Double2D location)
/*     */   {
/* 900 */     if (location == null) return 0;
/* 901 */     Bag cell = getRawObjectsAtLocation(discretize(location));
/* 902 */     if (cell == null) return 0;
/* 903 */     int count = 0;
/* 904 */     Object[] objs = cell.objs;
/* 905 */     int numObjs = cell.numObjs;
/*     */ 
/* 907 */     for (int i = 0; i < numObjs; i++)
/*     */     {
/* 909 */       Object loc = getObjectLocation(objs[i]);
/* 910 */       if (loc.equals(location))
/* 911 */         count++;
/*     */     }
/* 913 */     return count;
/*     */   }
/*     */ 
/*     */   public Bag getObjectsAtLocationOfObject(Object obj)
/*     */   {
/* 921 */     Object location = getObjectLocation(obj);
/* 922 */     if (location == null) return null;
/* 923 */     return getObjectsAtLocation(location);
/*     */   }
/*     */ 
/*     */   public int numObjectsAtLocationOfObject(Object obj)
/*     */   {
/* 931 */     Object location = getObjectLocation(obj);
/* 932 */     if (location == null) return 0;
/* 933 */     return numObjectsAtLocation(location);
/*     */   }
/*     */ 
/*     */   public Bag removeObjectsAtLocation(Double2D location)
/*     */   {
/* 940 */     Bag bag = getObjectsAtLocation(location);
/* 941 */     if (bag != null)
/*     */     {
/* 943 */       Object[] objs = bag.objs;
/* 944 */       int numObjs = bag.numObjs;
/* 945 */       for (int i = 0; i < bag.numObjs; i++)
/* 946 */         remove(objs[i]);
/*     */     }
/* 948 */     return bag;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.continuous.Continuous2D
 * JD-Core Version:    0.6.2
 */