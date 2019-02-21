/*     */ package sim.field.continuous;
/*     */ 
/*     */ import java.util.Map;
/*     */ import sim.field.SparseField;
/*     */ import sim.field.SparseField3D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double3D;
/*     */ import sim.util.Int3D;
/*     */ import sim.util.MutableInt3D;
/*     */ 
/*     */ public class Continuous3D extends SparseField
/*     */   implements SparseField3D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  81 */   public Map doubleLocationHash = buildMap(0);
/*     */   public double width;
/*     */   public double height;
/*     */   public double length;
/*     */   public final double discretization;
/*     */ 
/*     */   public Continuous3D(double discretization, double width, double height, double length)
/*     */   {
/*  92 */     this.discretization = discretization;
/*  93 */     this.width = width; this.height = height; this.length = length;
/*     */   }
/*     */ 
/*     */   public Continuous3D(Continuous3D other)
/*     */   {
/*  98 */     super(other);
/*  99 */     this.discretization = other.discretization;
/* 100 */     this.width = other.width;
/* 101 */     this.height = other.height;
/* 102 */     this.length = other.length;
/*     */   }
/*     */ 
/*     */   public final Double3D getObjectLocation(Object obj)
/*     */   {
/* 107 */     return (Double3D)this.doubleLocationHash.get(obj);
/*     */   }
/*     */ 
/*     */   public final Int3D discretize(Double3D location)
/*     */   {
/* 114 */     double discretization = this.discretization;
/* 115 */     return new Int3D((int)(location.x / discretization), (int)(location.y / discretization), (int)(location.z / discretization));
/*     */   }
/*     */ 
/*     */   public final Int3D discretize(Double3D location, int discretization)
/*     */   {
/* 124 */     return new Int3D((int)(location.x / discretization), (int)(location.y / discretization), (int)(location.z / discretization));
/*     */   }
/*     */ 
/*     */   public final boolean setObjectLocation(Object obj, Double3D location)
/*     */   {
/* 131 */     boolean result = super.setObjectLocation(obj, discretize(location));
/* 132 */     if (result) this.doubleLocationHash.put(obj, location);
/* 133 */     return result;
/*     */   }
/*     */ 
/*     */   public final Bag clear()
/*     */   {
/* 138 */     this.doubleLocationHash = buildMap(0);
/* 139 */     return super.clear();
/*     */   }
/*     */ 
/*     */   public final Object remove(Object obj)
/*     */   {
/* 144 */     Object result = super.remove(obj);
/* 145 */     this.doubleLocationHash.remove(obj);
/* 146 */     return result;
/*     */   }
/*     */ 
/*     */   public double getWidth()
/*     */   {
/* 151 */     return this.width;
/*     */   }
/*     */   public double getHeight() {
/* 154 */     return this.height;
/*     */   }
/*     */   public double getLength() {
/* 157 */     return this.length;
/*     */   }
/*     */ 
/*     */   public final double tx(double x)
/*     */   {
/* 164 */     double width = this.width;
/* 165 */     if ((x >= 0.0D) && (x < width)) return x;
/* 166 */     x %= width;
/* 167 */     if (x < 0.0D) x += width;
/* 168 */     return x;
/*     */   }
/*     */ 
/*     */   public final double ty(double y)
/*     */   {
/* 175 */     double height = this.height;
/* 176 */     if ((y >= 0.0D) && (y < height)) return y;
/* 177 */     y %= height;
/* 178 */     if (y < 0.0D) y += height;
/* 179 */     return y;
/*     */   }
/*     */ 
/*     */   public final double tz(double z)
/*     */   {
/* 186 */     double length = this.length;
/* 187 */     if ((z >= 0.0D) && (z < length)) return z;
/* 188 */     z %= length;
/* 189 */     if (z < 0.0D) z += length;
/* 190 */     return z;
/*     */   }
/*     */ 
/*     */   public double stx(double x)
/*     */   {
/* 235 */     if (x >= 0.0D) { if (x < this.width) return x; return x - this.width; } return x + this.width;
/*     */   }
/*     */ 
/*     */   public double sty(double y)
/*     */   {
/* 246 */     if (y >= 0.0D) { if (y < this.height) return y; return y - this.height; } return y + this.height;
/*     */   }
/*     */ 
/*     */   public double stz(double z)
/*     */   {
/* 257 */     if (z >= 0.0D) { if (z < this.length) return z; return z - this.length; } return z + this.length;
/*     */   }
/*     */ 
/*     */   double stx(double x, double width) {
/* 261 */     if (x >= 0.0D) { if (x < width) return x; return x - width; } return x + width;
/*     */   }
/*     */ 
/*     */   public double tdx(double x1, double x2)
/*     */   {
/* 266 */     double width = this.width;
/* 267 */     if (Math.abs(x1 - x2) <= width / 2.0D) {
/* 268 */       return x1 - x2;
/*     */     }
/* 270 */     double dx = stx(x1, width) - stx(x2, width);
/* 271 */     if (dx * 2.0D > width) return dx - width;
/* 272 */     if (dx * 2.0D < -width) return dx + width;
/* 273 */     return dx;
/*     */   }
/*     */ 
/*     */   double sty(double y, double height)
/*     */   {
/* 278 */     if (y >= 0.0D) { if (y < height) return y; return y - height; } return y + height;
/*     */   }
/*     */ 
/*     */   public double tdy(double y1, double y2)
/*     */   {
/* 283 */     double height = this.height;
/* 284 */     if (Math.abs(y1 - y2) <= height / 2.0D) {
/* 285 */       return y1 - y2;
/*     */     }
/* 287 */     double dy = sty(y1, height) - sty(y2, height);
/* 288 */     if (dy * 2.0D > height) return dy - height;
/* 289 */     if (dy * 2.0D < -height) return dy + height;
/* 290 */     return dy;
/*     */   }
/*     */ 
/*     */   double stz(double z, double length) {
/* 294 */     if (z >= 0.0D) { if (z < length) return z; return z - length; } return z + length;
/*     */   }
/*     */ 
/*     */   public double tdz(double z1, double z2)
/*     */   {
/* 299 */     double length = this.length;
/* 300 */     if (Math.abs(z1 - z2) <= length / 2.0D) {
/* 301 */       return z1 - z2;
/*     */     }
/* 303 */     double dz = stz(z1, length) - stz(z2, length);
/* 304 */     if (dz * 2.0D > length) return dz - length;
/* 305 */     if (dz * 2.0D < -length) return dz + length;
/* 306 */     return dz;
/*     */   }
/*     */ 
/*     */   public double tds(Double3D d1, Double3D d2)
/*     */   {
/* 312 */     double dx = tdx(d1.x, d2.x);
/* 313 */     double dy = tdy(d1.y, d2.y);
/* 314 */     double dz = tdz(d1.z, d2.z);
/* 315 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public Double3D tv(Double3D d1, Double3D d2)
/*     */   {
/* 321 */     return new Double3D(tdx(d1.x, d2.x), tdy(d1.y, d2.y), tdz(d1.z, d2.z));
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getObjectsExactlyWithinDistance(Double3D position, double distance)
/*     */   {
/* 335 */     return getObjectsExactlyWithinDistance(position, distance, false, true, true, null);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getObjectsExactlyWithinDistance(Double3D position, double distance, boolean toroidal)
/*     */   {
/* 349 */     return getObjectsExactlyWithinDistance(position, distance, toroidal, true, true, null);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getObjectsExactlyWithinDistance(Double3D position, double distance, boolean toroidal, boolean radial, boolean inclusive, Bag result)
/*     */   {
/* 368 */     return getNeighborsExactlyWithinDistance(position, distance, toroidal, radial, inclusive, result);
/*     */   }
/*     */ 
/*     */   public Bag getNeighborsExactlyWithinDistance(Double3D position, double distance)
/*     */   {
/* 382 */     return getObjectsExactlyWithinDistance(position, distance, false, true, true, null);
/*     */   }
/*     */ 
/*     */   public Bag getNeighborsExactlyWithinDistance(Double3D position, double distance, boolean toroidal)
/*     */   {
/* 395 */     return getNeighborsExactlyWithinDistance(position, distance, toroidal, true, true, null);
/*     */   }
/*     */ 
/*     */   public Bag getNeighborsExactlyWithinDistance(Double3D position, double distance, boolean toroidal, boolean radial, boolean inclusive, Bag result)
/*     */   {
/* 413 */     result = getNeighborsWithinDistance(position, distance, toroidal, false, result);
/* 414 */     int numObjs = result.numObjs;
/* 415 */     Object[] objs = result.objs;
/* 416 */     double distsq = distance * distance;
/* 417 */     if (radial)
/* 418 */       for (int i = 0; i < numObjs; i++)
/*     */       {
/* 420 */         double d = 0.0D;
/* 421 */         Double3D loc = getObjectLocation(objs[i]);
/* 422 */         if (toroidal) d = tds(position, loc); else
/* 423 */           d = position.distanceSq(loc);
/* 424 */         if ((d > distsq) || ((!inclusive) && (d >= distsq))) {
/* 425 */           result.remove(i); i--; numObjs--;
/*     */         }
/*     */       }
/* 428 */     else for (int i = 0; i < numObjs; i++)
/*     */       {
/* 430 */         Double3D loc = getObjectLocation(objs[i]);
/* 431 */         double minx = 0.0D;
/* 432 */         double miny = 0.0D;
/* 433 */         double minz = 0.0D;
/* 434 */         if (toroidal)
/*     */         {
/* 436 */           minx = tdx(loc.x, position.x);
/* 437 */           miny = tdy(loc.y, position.y);
/* 438 */           minz = tdz(loc.z, position.z);
/*     */         }
/*     */         else
/*     */         {
/* 442 */           minx = loc.x - position.x;
/* 443 */           miny = loc.y - position.y;
/* 444 */           minz = loc.z - position.z;
/*     */         }
/* 446 */         if (minx < 0.0D) minx = -minx;
/* 447 */         if (miny < 0.0D) miny = -miny;
/* 448 */         if (minz < 0.0D) minz = -minz;
/* 449 */         if ((minx > distance) || (miny > distance) || (minz > distance) || ((!inclusive) && ((minx >= distance) || (miny >= distance) || (minz >= distance))))
/*     */         {
/* 451 */           result.remove(i); i--; numObjs--;
/*     */         }
/*     */       } return result;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getObjectsWithinDistance(Double3D position, double distance)
/*     */   {
/* 473 */     return getObjectsWithinDistance(position, distance, false, false, new Bag());
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getObjectsWithinDistance(Double3D position, double distance, boolean toroidal)
/*     */   {
/* 489 */     return getObjectsWithinDistance(position, distance, toroidal, false, new Bag());
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getObjectsWithinDistance(Double3D position, double distance, boolean toroidal, boolean nonPointObjects)
/*     */   {
/* 510 */     return getObjectsWithinDistance(position, distance, toroidal, nonPointObjects, new Bag());
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getObjectsWithinDistance(Double3D position, double distance, boolean toroidal, boolean nonPointObjects, Bag result)
/*     */   {
/* 533 */     return getNeighborsWithinDistance(position, distance, toroidal, nonPointObjects, result);
/*     */   }
/*     */ 
/*     */   public Bag getNeighborsWithinDistance(Double3D position, double distance)
/*     */   {
/* 551 */     return getNeighborsWithinDistance(position, distance, false, false, new Bag());
/*     */   }
/*     */ 
/*     */   public Bag getNeighborsWithinDistance(Double3D position, double distance, boolean toroidal)
/*     */   {
/* 566 */     return getNeighborsWithinDistance(position, distance, toroidal, false, new Bag());
/*     */   }
/*     */ 
/*     */   public Bag getNeighborsWithinDistance(Double3D position, double distance, boolean toroidal, boolean nonPointObjects)
/*     */   {
/* 586 */     return getNeighborsWithinDistance(position, distance, toroidal, nonPointObjects, new Bag());
/*     */   }
/*     */ 
/*     */   public Bag getNeighborsWithinDistance(Double3D position, double distance, boolean toroidal, boolean nonPointObjects, Bag result)
/*     */   {
/* 609 */     if ((toroidal) && ((position.x >= this.width) || (position.y >= this.height) || (position.z >= this.length) || (position.x < 0.0D) || (position.y < 0.0D) || (position.z < 0.0D))) {
/* 610 */       position = new Double3D(tx(position.x), ty(position.y), tz(position.z));
/*     */     }
/* 612 */     double discDistance = distance / this.discretization;
/* 613 */     double discX = position.x / this.discretization;
/* 614 */     double discY = position.y / this.discretization;
/* 615 */     double discZ = position.z / this.discretization;
/*     */ 
/* 617 */     if (nonPointObjects)
/*     */     {
/* 623 */       discDistance += 1.0D;
/*     */     }
/*     */ 
/* 626 */     int expectedBagSize = 1;
/* 627 */     if (result != null) result.clear(); else {
/* 628 */       result = new Bag(1);
/*     */     }
/*     */ 
/* 631 */     MutableInt3D speedyMutableInt3D = new MutableInt3D();
/*     */ 
/* 635 */     if (toroidal)
/*     */     {
/* 637 */       int iWidth = (int)StrictMath.ceil(this.width / this.discretization);
/* 638 */       int iHeight = (int)StrictMath.ceil(this.height / this.discretization);
/* 639 */       int iLength = (int)StrictMath.ceil(this.length / this.discretization);
/*     */ 
/* 646 */       int minX = (int)StrictMath.floor(discX - discDistance);
/* 647 */       int maxX = (int)StrictMath.floor(discX + discDistance);
/* 648 */       int minY = (int)StrictMath.floor(discY - discDistance);
/* 649 */       int maxY = (int)StrictMath.floor(discY + discDistance);
/* 650 */       int minZ = (int)StrictMath.floor(discZ - discDistance);
/* 651 */       int maxZ = (int)StrictMath.floor(discZ + discDistance);
/*     */ 
/* 653 */       if ((position.x + distance >= this.width) && (maxX == iWidth - 1)) {
/* 654 */         maxX = 0;
/*     */       }
/* 656 */       if ((position.y + distance >= this.height) && (maxY == iHeight - 1)) {
/* 657 */         maxY = 0;
/*     */       }
/* 659 */       if ((position.z + distance >= this.length) && (maxZ == iLength - 1)) {
/* 660 */         maxZ = 0;
/*     */       }
/*     */ 
/* 665 */       if (maxX - minX >= iWidth) {
/* 666 */         minX = 0; maxX = iWidth - 1;
/* 667 */       }if (maxY - minY >= iHeight) {
/* 668 */         minY = 0; maxY = iHeight - 1;
/* 669 */       }if (maxZ - minZ >= iLength) {
/* 670 */         minZ = 0; maxZ = iLength - 1;
/*     */       }
/*     */ 
/* 673 */       int tmaxX = toroidal(maxX, iWidth);
/* 674 */       int tmaxY = toroidal(maxY, iHeight);
/* 675 */       int tmaxZ = toroidal(maxZ, iLength);
/* 676 */       int tminX = toroidal(minX, iWidth);
/* 677 */       int tminY = toroidal(minY, iHeight);
/* 678 */       int tminZ = toroidal(minZ, iLength);
/*     */ 
/* 680 */       int x = tminX;
/*     */       while (true)
/*     */       {
/* 683 */         int y = tminY;
/*     */         while (true)
/*     */         {
/* 686 */           int z = tminZ;
/*     */           while (true)
/*     */           {
/* 690 */             speedyMutableInt3D.x = x; speedyMutableInt3D.y = y; speedyMutableInt3D.z = z;
/* 691 */             Bag temp = getRawObjectsAtLocation(speedyMutableInt3D);
/* 692 */             if ((temp != null) && (!temp.isEmpty()))
/*     */             {
/* 696 */               int n = temp.numObjs;
/* 697 */               if (n == 1) result.add(temp.objs[0]); else {
/* 698 */                 result.addAll(temp);
/*     */               }
/*     */             }
/*     */ 
/* 702 */             if (z == tmaxZ)
/*     */               break;
/* 704 */             if (z == iLength - 1)
/* 705 */               z = 0;
/*     */             else {
/* 707 */               z++;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 712 */           if (y == tmaxY)
/*     */             break;
/* 714 */           if (y == iHeight - 1)
/* 715 */             y = 0;
/*     */           else {
/* 717 */             y++;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 722 */         if (x == tmaxX)
/*     */           break;
/* 724 */         if (x == iWidth - 1)
/* 725 */           x = 0;
/*     */         else {
/* 727 */           x++;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 738 */       int minX = (int)StrictMath.floor(discX - discDistance);
/* 739 */       int maxX = (int)StrictMath.floor(discX + discDistance);
/* 740 */       int minY = (int)StrictMath.floor(discY - discDistance);
/* 741 */       int maxY = (int)StrictMath.floor(discY + discDistance);
/* 742 */       int minZ = (int)StrictMath.floor(discZ - discDistance);
/* 743 */       int maxZ = (int)StrictMath.floor(discZ + discDistance);
/*     */ 
/* 746 */       for (int x = minX; x <= maxX; x++) {
/* 747 */         for (int y = minY; y <= maxY; y++)
/* 748 */           for (int z = minZ; z <= maxZ; z++)
/*     */           {
/* 751 */             speedyMutableInt3D.x = x; speedyMutableInt3D.y = y; speedyMutableInt3D.z = z;
/* 752 */             Bag temp = getRawObjectsAtLocation(speedyMutableInt3D);
/* 753 */             if ((temp != null) && (!temp.isEmpty()))
/*     */             {
/* 757 */               int n = temp.numObjs;
/* 758 */               if (n == 1) result.add(temp.objs[0]); else
/* 759 */                 result.addAll(temp);
/*     */             }
/*     */           }
/*     */       }
/*     */     }
/* 764 */     return result;
/*     */   }
/*     */ 
/*     */   final int toroidal(int x, int width)
/*     */   {
/* 771 */     if (x >= 0) return x % width;
/* 772 */     int width2 = x % width + width;
/* 773 */     if (width2 < width) return width2;
/* 774 */     return 0;
/*     */   }
/*     */ 
/*     */   public Bag getObjectsAtDiscretizedLocation(Int3D location)
/*     */   {
/* 788 */     return getRawObjectsAtLocation(location);
/*     */   }
/*     */ 
/*     */   public Bag getObjectsAtLocation(Double3D location)
/*     */   {
/* 796 */     if (location == null) return null;
/* 797 */     Bag cell = getRawObjectsAtLocation(discretize(location));
/* 798 */     if (cell == null) return null;
/* 799 */     Bag result = new Bag();
/* 800 */     Object[] objs = cell.objs;
/* 801 */     int numObjs = cell.numObjs;
/*     */ 
/* 803 */     for (int i = 0; i < numObjs; i++)
/*     */     {
/* 805 */       Object loc = getObjectLocation(objs[i]);
/* 806 */       if (loc.equals(location))
/* 807 */         result.add(objs[i]);
/*     */     }
/* 809 */     return result;
/*     */   }
/*     */ 
/*     */   public int numObjectsAtLocation(Double3D location)
/*     */   {
/* 817 */     if (location == null) return 0;
/* 818 */     Bag cell = getRawObjectsAtLocation(discretize(location));
/* 819 */     if (cell == null) return 0;
/* 820 */     int count = 0;
/* 821 */     Object[] objs = cell.objs;
/* 822 */     int numObjs = cell.numObjs;
/*     */ 
/* 824 */     for (int i = 0; i < numObjs; i++)
/*     */     {
/* 826 */       Object loc = getObjectLocation(objs[i]);
/* 827 */       if (loc.equals(location))
/* 828 */         count++;
/*     */     }
/* 830 */     return count;
/*     */   }
/*     */ 
/*     */   public Bag getObjectsAtLocationOfObject(Object obj)
/*     */   {
/* 838 */     Object location = getObjectLocation(obj);
/* 839 */     if (location == null) return null;
/* 840 */     return getObjectsAtLocation(location);
/*     */   }
/*     */ 
/*     */   public int numObjectsAtLocationOfObject(Object obj)
/*     */   {
/* 848 */     Object location = getObjectLocation(obj);
/* 849 */     if (location == null) return 0;
/* 850 */     return numObjectsAtLocation(location);
/*     */   }
/*     */ 
/*     */   public Bag removeObjectsAtLocation(Double3D location)
/*     */   {
/* 857 */     Bag bag = getObjectsAtLocation(location);
/* 858 */     if (bag != null)
/*     */     {
/* 860 */       Object[] objs = bag.objs;
/* 861 */       int numObjs = bag.numObjs;
/* 862 */       for (int i = 0; i < bag.numObjs; i++)
/* 863 */         remove(objs[i]);
/*     */     }
/* 865 */     return bag;
/*     */   }
/*     */   public final Double3D getDimensions() {
/* 868 */     return new Double3D(this.width, this.height, this.length);
/*     */   }
/*     */ 
/*     */   public Double3D getObjectLocationAsDouble3D(Object obj)
/*     */   {
/* 873 */     return (Double3D)this.doubleLocationHash.get(obj);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.continuous.Continuous3D
 * JD-Core Version:    0.6.2
 */