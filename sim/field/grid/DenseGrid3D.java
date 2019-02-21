/*     */ package sim.field.grid;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Int3D;
/*     */ import sim.util.IntBag;
/*     */ 
/*     */ public class DenseGrid3D extends AbstractGrid3D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  30 */   public boolean removeEmptyBags = true;
/*     */ 
/*  33 */   public boolean replaceLargeBags = true;
/*     */   public static final int INITIAL_BAG_SIZE = 16;
/*     */   public static final int MIN_BAG_SIZE = 32;
/*     */   public static final int LARGE_BAG_RATIO = 4;
/*     */   public static final int REPLACEMENT_BAG_RATIO = 2;
/*     */   public Bag[][][] field;
/*     */ 
/*     */   public DenseGrid3D(int width, int height, int length)
/*     */   {
/*  51 */     this.width = width;
/*  52 */     this.height = height;
/*  53 */     this.length = length;
/*  54 */     this.field = new Bag[width][height][length];
/*     */   }
/*     */ 
/*     */   public Bag getObjectsAtLocation(int x, int y, int z)
/*     */   {
/*  66 */     return this.field[x][y][z];
/*     */   }
/*     */ 
/*     */   public Bag getObjectsAtLocation(Int3D location)
/*     */   {
/*  76 */     return getObjectsAtLocation(location.x, location.y, location.z);
/*     */   }
/*     */ 
/*     */   public Bag removeObjectsAtLocation(int x, int y, int z)
/*     */   {
/*  82 */     Bag b = this.field[x][y][z];
/*  83 */     this.field[x][y][z] = null;
/*  84 */     return b;
/*     */   }
/*     */ 
/*     */   public Bag removeObjectsAtLocation(Int3D location)
/*     */   {
/*  89 */     return removeObjectsAtLocation(location.x, location.y, location.z);
/*     */   }
/*     */ 
/*     */   public boolean removeObjectAtLocation(Object obj, int x, int y, int z)
/*     */   {
/*  94 */     Bag b = this.field[x][y][z];
/*  95 */     if (b == null) return false;
/*  96 */     boolean result = b.remove(obj);
/*  97 */     int objsNumObjs = b.numObjs;
/*  98 */     if ((this.removeEmptyBags) && (objsNumObjs == 0)) b = null;
/*  99 */     else if ((this.replaceLargeBags) && (objsNumObjs >= 32) && (objsNumObjs * 4 <= b.objs.length))
/* 100 */       b.shrink(objsNumObjs * 2);
/* 101 */     return result;
/*     */   }
/*     */   public boolean removeObjectAtLocation(Object obj, Int3D location) {
/* 104 */     return removeObjectAtLocation(obj, location.x, location.y, location.z);
/*     */   }
/*     */ 
/*     */   public boolean removeObjectMultiplyAtLocation(Object obj, int x, int y, int z) {
/* 108 */     Bag b = this.field[x][y][z];
/* 109 */     if (b == null) return false;
/* 110 */     boolean result = b.removeMultiply(obj);
/* 111 */     int objsNumObjs = b.numObjs;
/* 112 */     if ((this.removeEmptyBags) && (objsNumObjs == 0)) b = null;
/* 113 */     else if ((this.replaceLargeBags) && (objsNumObjs >= 32) && (objsNumObjs * 4 <= b.objs.length))
/* 114 */       b.shrink(objsNumObjs * 2);
/* 115 */     return result;
/*     */   }
/*     */   public boolean removeObjectMultiplyAtLocation(Object obj, Int3D location) {
/* 118 */     return removeObjectMultiplyAtLocation(obj, location.x, location.y, location.z);
/*     */   }
/*     */ 
/*     */   public boolean moveObject(Object obj, int fromX, int fromY, int fromZ, int toX, int toY, int toZ)
/*     */   {
/* 125 */     boolean result = removeObjectAtLocation(obj, fromX, fromY, fromZ);
/* 126 */     addObjectToLocation(obj, toX, toY, toZ);
/* 127 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean moveObject(Object obj, Int3D from, Int3D to)
/*     */   {
/* 133 */     return moveObject(obj, from.x, from.y, from.z, to.x, to.y, to.z);
/*     */   }
/*     */ 
/*     */   public void moveObjects(int fromX, int fromY, int fromZ, int toX, int toY, int toZ)
/*     */   {
/* 138 */     addObjectsToLocation(removeObjectsAtLocation(fromX, fromY, fromZ), toX, toY, toZ);
/*     */   }
/*     */   public void moveObjects(Int3D from, Int3D to) {
/* 141 */     moveObjects(from.x, from.y, from.z, to.x, to.y, to.z);
/*     */   }
/*     */ 
/*     */   public int numObjectsAtLocation(int x, int y, int z) {
/* 145 */     Bag b = this.field[x][y][z];
/* 146 */     if (b == null) return 0;
/* 147 */     return b.numObjs;
/*     */   }
/*     */   public int numObjectsAtLocation(Int3D location) {
/* 150 */     return numObjectsAtLocation(location.x, location.y, location.z);
/*     */   }
/*     */ 
/*     */   void buildBag(Bag[] fieldxy, int z) {
/* 154 */     fieldxy[z] = new Bag(16);
/*     */   }
/*     */ 
/*     */   public void addObjectToLocation(Object obj, int x, int y, int z)
/*     */   {
/* 161 */     Bag[] fieldxy = this.field[x][y];
/* 162 */     if (fieldxy[z] == null) buildBag(fieldxy, z);
/* 163 */     fieldxy[z].add(obj);
/*     */   }
/*     */   public void addObjectToLocation(Object obj, Int3D location) {
/* 166 */     addObjectToLocation(obj, location.x, location.y, location.z);
/*     */   }
/*     */ 
/*     */   public void addObjectsToLocation(Bag objs, int x, int y, int z)
/*     */   {
/* 171 */     if (objs == null) return;
/* 172 */     Bag[] fieldxy = this.field[x][y];
/* 173 */     if (fieldxy[z] == null) buildBag(fieldxy, z);
/* 174 */     fieldxy[z].addAll(objs);
/*     */   }
/*     */   public void addObjectsToLocation(Bag objs, Int3D location) {
/* 177 */     addObjectsToLocation(objs, location.x, location.y, location.z);
/*     */   }
/*     */ 
/*     */   public void addObjectsToLocation(Object[] objs, int x, int y, int z)
/*     */   {
/* 182 */     if (objs == null) return;
/* 183 */     Bag[] fieldxy = this.field[x][y];
/* 184 */     if (fieldxy[z] == null) buildBag(fieldxy, z);
/* 185 */     fieldxy[z].addAll(0, objs);
/*     */   }
/*     */   public void addObjectsToLocation(Object[] objs, Int3D location) {
/* 188 */     addObjectsToLocation(objs, location.x, location.y, location.z);
/*     */   }
/*     */ 
/*     */   public void addObjectsToLocation(Collection objs, int x, int y, int z)
/*     */   {
/* 193 */     if (objs == null) return;
/* 194 */     Bag[] fieldxy = this.field[x][y];
/* 195 */     if (fieldxy[z] == null) buildBag(fieldxy, z);
/* 196 */     fieldxy[z].addAll(objs);
/*     */   }
/*     */ 
/*     */   public final Bag clear()
/*     */   {
/* 203 */     Bag bag = new Bag();
/* 204 */     Bag[][] fieldx = (Bag[][])null;
/* 205 */     Bag[] fieldxy = null;
/* 206 */     int width = this.width;
/* 207 */     int height = this.height;
/* 208 */     for (int x = 0; x < width; x++)
/*     */     {
/* 210 */       fieldx = this.field[x];
/* 211 */       for (int y = 0; y < height; y++)
/*     */       {
/* 213 */         fieldxy = fieldx[y];
/* 214 */         for (int z = 0; z < this.length; z++)
/*     */         {
/* 216 */           if (fieldxy[z] != null)
/* 217 */             bag.addAll(fieldxy[z]);
/* 218 */           fieldxy[z] = null;
/*     */         }
/*     */       }
/*     */     }
/* 222 */     return bag;
/*     */   }
/*     */ 
/*     */   public final void removeAll(Object from)
/*     */   {
/* 235 */     removeAll(from, false);
/*     */   }
/*     */ 
/*     */   public final void removeAll(Object from, boolean onlyIfSameObject)
/*     */   {
/* 250 */     int width = this.width;
/* 251 */     int height = this.height;
/* 252 */     Bag[][] fieldx = (Bag[][])null;
/* 253 */     Bag[] fieldxy = null;
/* 254 */     for (int x = 0; x < width; x++)
/*     */     {
/* 256 */       fieldx = this.field[x];
/* 257 */       for (int y = 0; y < height; y++)
/*     */       {
/* 259 */         fieldxy = fieldx[y];
/* 260 */         for (int z = 0; z < this.length; z++)
/*     */         {
/* 262 */           Bag bag = fieldxy[z];
/* 263 */           int len = bag.size();
/*     */ 
/* 268 */           for (int i = 0; i < len; i++)
/*     */           {
/* 270 */             Object obj = bag.get(i);
/* 271 */             if (((obj == null) && (from == null)) || ((onlyIfSameObject) && (obj == from)) || ((!onlyIfSameObject) && (obj.equals(from))))
/*     */             {
/* 275 */               bag.remove(i);
/* 276 */               i--;
/* 277 */               len--;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void replaceAll(Object from, Object to)
/*     */   {
/* 296 */     replaceAll(from, to, false);
/*     */   }
/*     */ 
/*     */   public final void replaceAll(Object from, Object to, boolean onlyIfSameObject)
/*     */   {
/* 311 */     int width = this.width;
/* 312 */     int height = this.height;
/* 313 */     Bag[][] fieldx = (Bag[][])null;
/* 314 */     Bag[] fieldxy = null;
/* 315 */     for (int x = 0; x < width; x++)
/*     */     {
/* 317 */       fieldx = this.field[x];
/* 318 */       for (int y = 0; y < height; y++)
/*     */       {
/* 320 */         fieldxy = fieldx[y];
/* 321 */         for (int z = 0; z < this.length; z++)
/*     */         {
/* 323 */           Bag bag = fieldxy[z];
/* 324 */           int len = bag.size();
/*     */ 
/* 329 */           for (int i = 0; i < len; i++)
/*     */           {
/* 331 */             Object obj = bag.get(i);
/* 332 */             if (((obj == null) && (from == null)) || ((onlyIfSameObject) && (obj == from)) || ((!onlyIfSameObject) && (obj.equals(from))))
/*     */             {
/* 335 */               bag.set(i, to);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getNeighborsMaxDistance(int x, int y, int z, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 373 */     return getMooreNeighbors(x, y, z, dist, toroidal ? 2 : 0, true, result, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public Bag getMooreNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 404 */     if (xPos == null)
/* 405 */       xPos = new IntBag();
/* 406 */     if (yPos == null)
/* 407 */       yPos = new IntBag();
/* 408 */     if (zPos == null) {
/* 409 */       zPos = new IntBag();
/*     */     }
/* 411 */     getMooreLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/* 412 */     return getObjectsAtLocations(xPos, yPos, zPos, result);
/*     */   }
/*     */ 
/*     */   public Bag getMooreNeighborsAndLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 444 */     if (xPos == null)
/* 445 */       xPos = new IntBag();
/* 446 */     if (yPos == null)
/* 447 */       yPos = new IntBag();
/* 448 */     if (zPos == null) {
/* 449 */       zPos = new IntBag();
/*     */     }
/* 451 */     getMooreLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/* 452 */     reduceObjectsAtLocations(xPos, yPos, zPos, result);
/* 453 */     return result;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsHamiltonianDistance(int x, int y, int z, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 490 */     getVonNeumannNeighbors(x, y, z, dist, toroidal ? 2 : 0, true, result, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public Bag getVonNeumannNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 522 */     if (xPos == null)
/* 523 */       xPos = new IntBag();
/* 524 */     if (yPos == null)
/* 525 */       yPos = new IntBag();
/* 526 */     if (zPos == null) {
/* 527 */       zPos = new IntBag();
/*     */     }
/* 529 */     getVonNeumannLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/* 530 */     return getObjectsAtLocations(xPos, yPos, zPos, result);
/*     */   }
/*     */ 
/*     */   public Bag getVonNeumannNeighborsAndLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 564 */     if (xPos == null)
/* 565 */       xPos = new IntBag();
/* 566 */     if (yPos == null)
/* 567 */       yPos = new IntBag();
/* 568 */     if (zPos == null) {
/* 569 */       zPos = new IntBag();
/*     */     }
/* 571 */     getVonNeumannLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/* 572 */     reduceObjectsAtLocations(xPos, yPos, zPos, result);
/* 573 */     return result;
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 580 */     return getRadialNeighbors(x, y, z, dist, mode, includeOrigin, 1026, true, result, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighborsAndLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 586 */     return getRadialNeighborsAndLocations(x, y, z, dist, mode, includeOrigin, 1026, true, result, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 592 */     if (xPos == null)
/* 593 */       xPos = new IntBag();
/* 594 */     if (yPos == null)
/* 595 */       yPos = new IntBag();
/* 596 */     if (zPos == null) {
/* 597 */       zPos = new IntBag();
/*     */     }
/* 599 */     getRadialLocations(x, y, z, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos, zPos);
/* 600 */     return getObjectsAtLocations(xPos, yPos, zPos, result);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighborsAndLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 605 */     if (xPos == null)
/* 606 */       xPos = new IntBag();
/* 607 */     if (yPos == null)
/* 608 */       yPos = new IntBag();
/* 609 */     if (zPos == null) {
/* 610 */       zPos = new IntBag();
/*     */     }
/* 612 */     getRadialLocations(x, y, z, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos, zPos);
/* 613 */     reduceObjectsAtLocations(xPos, yPos, zPos, result);
/* 614 */     return result;
/*     */   }
/*     */ 
/*     */   void reduceObjectsAtLocations(IntBag xPos, IntBag yPos, IntBag zPos, Bag result)
/*     */   {
/* 624 */     if (result == null) result = new Bag(); else {
/* 625 */       result.clear();
/*     */     }
/*     */ 
/* 628 */     IntBag newXPos = new IntBag();
/* 629 */     IntBag newYPos = new IntBag();
/* 630 */     IntBag newZPos = new IntBag();
/*     */ 
/* 632 */     int len = xPos.numObjs;
/* 633 */     int[] xs = xPos.objs;
/* 634 */     int[] ys = yPos.objs;
/* 635 */     int[] zs = zPos.objs;
/*     */ 
/* 638 */     for (int i = 0; i < len; i++)
/*     */     {
/* 640 */       Bag temp = this.field[xPos.objs[i]][yPos.objs[i]][zPos.objs[i]];
/* 641 */       int size = temp.numObjs;
/* 642 */       Object[] os = temp.objs;
/*     */ 
/* 644 */       for (int j = 0; j < size; j++)
/*     */       {
/* 647 */         result.add(os[j]);
/* 648 */         newXPos.add(xs[i]);
/* 649 */         newYPos.add(ys[i]);
/* 650 */         newZPos.add(zs[i]);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 655 */     xPos.clear();
/* 656 */     xPos.addAll(newXPos);
/* 657 */     yPos.clear();
/* 658 */     yPos.addAll(newYPos);
/* 659 */     zPos.clear();
/* 660 */     zPos.addAll(newZPos);
/*     */   }
/*     */ 
/*     */   Bag getObjectsAtLocations(IntBag xPos, IntBag yPos, IntBag zPos, Bag result)
/*     */   {
/* 668 */     if (result == null) result = new Bag(); else {
/* 669 */       result.clear();
/*     */     }
/* 671 */     int len = xPos.numObjs;
/* 672 */     int[] xs = xPos.objs;
/* 673 */     int[] ys = yPos.objs;
/* 674 */     int[] zs = zPos.objs;
/* 675 */     for (int i = 0; i < len; i++)
/*     */     {
/* 679 */       Bag temp = this.field[xPos.objs[i]][yPos.objs[i]][zPos.objs[i]];
/* 680 */       if (temp != null)
/*     */       {
/* 682 */         int n = temp.numObjs;
/* 683 */         if (n == 1) result.add(temp.objs[0]);
/* 684 */         else if (n > 1) result.addAll(temp);
/*     */       }
/*     */     }
/* 687 */     return result;
/*     */   }
/*     */ 
/*     */   public Bag getMooreNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 708 */     return getMooreNeighbors(x, y, z, dist, mode, includeOrigin, null, null, null, null);
/*     */   }
/*     */ 
/*     */   public Bag getVonNeumannNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 732 */     return getVonNeumannNeighbors(x, y, z, dist, mode, includeOrigin, null, null, null, null);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 741 */     return getRadialNeighbors(x, y, z, dist, mode, includeOrigin, null, null, null, null);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.grid.DenseGrid3D
 * JD-Core Version:    0.6.2
 */