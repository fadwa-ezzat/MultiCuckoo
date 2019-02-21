/*     */ package sim.field.grid;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.IntBag;
/*     */ 
/*     */ public class DenseGrid2D extends AbstractGrid2D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  30 */   public boolean removeEmptyBags = true;
/*     */ 
/*  33 */   public boolean replaceLargeBags = true;
/*     */   public static final int INITIAL_BAG_SIZE = 16;
/*     */   public static final int MIN_BAG_SIZE = 32;
/*     */   public static final int LARGE_BAG_RATIO = 4;
/*     */   public static final int REPLACEMENT_BAG_RATIO = 2;
/*     */   public Bag[][] field;
/*     */ 
/*     */   public DenseGrid2D(int width, int height)
/*     */   {
/*  51 */     this.width = width;
/*  52 */     this.height = height;
/*  53 */     this.field = new Bag[width][height];
/*     */   }
/*     */ 
/*     */   public Bag getObjectsAtLocation(int x, int y)
/*     */   {
/*  65 */     return this.field[x][y];
/*     */   }
/*     */ 
/*     */   public Bag getObjectsAtLocation(Int2D location)
/*     */   {
/*  75 */     return getObjectsAtLocation(location.x, location.y);
/*     */   }
/*     */ 
/*     */   public Bag removeObjectsAtLocation(int x, int y)
/*     */   {
/*  81 */     Bag b = this.field[x][y];
/*  82 */     this.field[x][y] = null;
/*  83 */     return b;
/*     */   }
/*     */ 
/*     */   public Bag removeObjectsAtLocation(Int2D location)
/*     */   {
/*  88 */     return removeObjectsAtLocation(location.x, location.y);
/*     */   }
/*     */ 
/*     */   public boolean removeObjectAtLocation(Object obj, int x, int y)
/*     */   {
/*  93 */     Bag b = this.field[x][y];
/*  94 */     if (b == null) return false;
/*  95 */     boolean result = b.remove(obj);
/*  96 */     int objsNumObjs = b.numObjs;
/*  97 */     if ((this.removeEmptyBags) && (objsNumObjs == 0)) b = null;
/*  98 */     else if ((this.replaceLargeBags) && (objsNumObjs >= 32) && (objsNumObjs * 4 <= b.objs.length))
/*  99 */       b.shrink(objsNumObjs * 2);
/* 100 */     return result;
/*     */   }
/*     */   public boolean removeObjectAtLocation(Object obj, Int2D location) {
/* 103 */     return removeObjectAtLocation(obj, location.x, location.y);
/*     */   }
/*     */ 
/*     */   public boolean removeObjectMultiplyAtLocation(Object obj, int x, int y) {
/* 107 */     Bag b = this.field[x][y];
/* 108 */     if (b == null) return false;
/* 109 */     boolean result = b.removeMultiply(obj);
/* 110 */     int objsNumObjs = b.numObjs;
/* 111 */     if ((this.removeEmptyBags) && (objsNumObjs == 0)) b = null;
/* 112 */     else if ((this.replaceLargeBags) && (objsNumObjs >= 32) && (objsNumObjs * 4 <= b.objs.length))
/* 113 */       b.shrink(objsNumObjs * 2);
/* 114 */     return result;
/*     */   }
/*     */   public boolean removeObjectMultiplyAtLocation(Object obj, Int2D location) {
/* 117 */     return removeObjectMultiplyAtLocation(obj, location.x, location.y);
/*     */   }
/*     */ 
/*     */   public boolean moveObject(Object obj, int fromX, int fromY, int toX, int toY)
/*     */   {
/* 124 */     boolean result = removeObjectAtLocation(obj, fromX, fromY);
/* 125 */     addObjectToLocation(obj, toX, toY);
/* 126 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean moveObject(Object obj, Int2D from, Int2D to)
/*     */   {
/* 132 */     return moveObject(obj, from.x, from.y, to.x, to.y);
/*     */   }
/*     */ 
/*     */   public void moveObjects(int fromX, int fromY, int toX, int toY)
/*     */   {
/* 137 */     addObjectsToLocation(removeObjectsAtLocation(fromX, fromY), toX, toY);
/*     */   }
/*     */   public void moveObjects(Int2D from, Int2D to) {
/* 140 */     moveObjects(from.x, from.y, to.x, to.y);
/*     */   }
/*     */ 
/*     */   public int numObjectsAtLocation(int x, int y) {
/* 144 */     Bag b = this.field[x][y];
/* 145 */     if (b == null) return 0;
/* 146 */     return b.numObjs;
/*     */   }
/*     */   public int numObjectsAtLocation(Int2D location) {
/* 149 */     return numObjectsAtLocation(location.x, location.y);
/*     */   }
/*     */ 
/*     */   void buildBag(Bag[] fieldx, int y) {
/* 153 */     fieldx[y] = new Bag(16);
/*     */   }
/*     */ 
/*     */   public void addObjectToLocation(Object obj, int x, int y)
/*     */   {
/* 160 */     Bag[] fieldx = this.field[x];
/* 161 */     if (fieldx[y] == null) buildBag(fieldx, y);
/* 162 */     fieldx[y].add(obj);
/*     */   }
/*     */   public void addObjectToLocation(Object obj, Int2D location) {
/* 165 */     addObjectToLocation(obj, location.x, location.y);
/*     */   }
/*     */ 
/*     */   public void addObjectsToLocation(Bag objs, int x, int y)
/*     */   {
/* 170 */     if (objs == null) return;
/* 171 */     Bag[] fieldx = this.field[x];
/* 172 */     if (fieldx[y] == null) buildBag(fieldx, y);
/* 173 */     fieldx[y].addAll(objs);
/*     */   }
/*     */   public void addObjectsToLocation(Bag objs, Int2D location) {
/* 176 */     addObjectsToLocation(objs, location.x, location.y);
/*     */   }
/*     */ 
/*     */   public void addObjectsToLocation(Object[] objs, int x, int y)
/*     */   {
/* 181 */     if (objs == null) return;
/* 182 */     Bag[] fieldx = this.field[x];
/* 183 */     if (fieldx[y] == null) buildBag(fieldx, y);
/* 184 */     fieldx[y].addAll(0, objs);
/*     */   }
/*     */   public void addObjectsToLocation(Object[] objs, Int2D location) {
/* 187 */     addObjectsToLocation(objs, location.x, location.y);
/*     */   }
/*     */ 
/*     */   public void addObjectsToLocation(Collection objs, int x, int y)
/*     */   {
/* 192 */     if (objs == null) return;
/* 193 */     Bag[] fieldx = this.field[x];
/* 194 */     if (fieldx[y] == null) buildBag(fieldx, y);
/* 195 */     fieldx[y].addAll(objs);
/*     */   }
/*     */ 
/*     */   public final Bag clear()
/*     */   {
/* 202 */     Bag bag = new Bag();
/* 203 */     Bag[] fieldx = null;
/* 204 */     int width = this.width;
/* 205 */     int height = this.height;
/* 206 */     for (int x = 0; x < width; x++)
/*     */     {
/* 208 */       fieldx = this.field[x];
/* 209 */       for (int y = 0; y < height; y++)
/*     */       {
/* 211 */         if (fieldx[y] != null)
/* 212 */           bag.addAll(fieldx[y]);
/* 213 */         fieldx[y] = null;
/*     */       }
/*     */     }
/* 216 */     return bag;
/*     */   }
/*     */ 
/*     */   public final void replaceAll(Object from, Object to)
/*     */   {
/* 231 */     replaceAll(from, to, false);
/*     */   }
/*     */ 
/*     */   public final void replaceAll(Object from, Object to, boolean onlyIfSameObject)
/*     */   {
/* 246 */     int width = this.width;
/* 247 */     int height = this.height;
/* 248 */     Bag[] fieldx = null;
/* 249 */     for (int x = 0; x < width; x++)
/*     */     {
/* 251 */       fieldx = this.field[x];
/* 252 */       for (int y = 0; y < height; y++)
/*     */       {
/* 254 */         Bag bag = fieldx[y];
/* 255 */         int len = bag.size();
/*     */ 
/* 260 */         for (int i = 0; i < len; i++)
/*     */         {
/* 262 */           Object obj = bag.get(i);
/* 263 */           if (((obj == null) && (from == null)) || ((onlyIfSameObject) && (obj == from)) || ((!onlyIfSameObject) && (obj.equals(from))))
/*     */           {
/* 266 */             bag.set(i, to);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void removeAll(Object from)
/*     */   {
/* 282 */     removeAll(from, false);
/*     */   }
/*     */ 
/*     */   public final void removeAll(Object from, boolean onlyIfSameObject)
/*     */   {
/* 297 */     int width = this.width;
/* 298 */     int height = this.height;
/* 299 */     Bag[] fieldx = null;
/* 300 */     for (int x = 0; x < width; x++)
/*     */     {
/* 302 */       fieldx = this.field[x];
/* 303 */       for (int y = 0; y < height; y++)
/*     */       {
/* 305 */         Bag bag = fieldx[y];
/* 306 */         int len = bag.size();
/*     */ 
/* 311 */         for (int i = 0; i < len; i++)
/*     */         {
/* 313 */           Object obj = bag.get(i);
/* 314 */           if (((obj == null) && (from == null)) || ((onlyIfSameObject) && (obj == from)) || ((!onlyIfSameObject) && (obj.equals(from))))
/*     */           {
/* 318 */             bag.remove(i);
/* 319 */             i--;
/* 320 */             len--;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getNeighborsMaxDistance(int x, int y, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 360 */     return getMooreNeighbors(x, y, dist, toroidal ? 2 : 0, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public Bag getMooreNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 391 */     if (xPos == null)
/* 392 */       xPos = new IntBag();
/* 393 */     if (yPos == null) {
/* 394 */       yPos = new IntBag();
/*     */     }
/* 396 */     getMooreLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 397 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   public Bag getMooreNeighborsAndLocations(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 429 */     if (xPos == null)
/* 430 */       xPos = new IntBag();
/* 431 */     if (yPos == null) {
/* 432 */       yPos = new IntBag();
/*     */     }
/* 434 */     getMooreLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 435 */     reduceObjectsAtLocations(xPos, yPos, result);
/* 436 */     return result;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getNeighborsHamiltonianDistance(int x, int y, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 473 */     return getVonNeumannNeighbors(x, y, dist, toroidal ? 2 : 0, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public Bag getVonNeumannNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 505 */     if (xPos == null)
/* 506 */       xPos = new IntBag();
/* 507 */     if (yPos == null) {
/* 508 */       yPos = new IntBag();
/*     */     }
/* 510 */     getVonNeumannLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 511 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   public Bag getVonNeumannNeighborsAndLocations(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 545 */     if (xPos == null)
/* 546 */       xPos = new IntBag();
/* 547 */     if (yPos == null) {
/* 548 */       yPos = new IntBag();
/*     */     }
/* 550 */     getVonNeumannLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 551 */     reduceObjectsAtLocations(xPos, yPos, result);
/* 552 */     return result;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getNeighborsHexagonalDistance(int x, int y, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 590 */     return getHexagonalNeighbors(x, y, dist, toroidal ? 2 : 0, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public Bag getHexagonalNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 622 */     if (xPos == null)
/* 623 */       xPos = new IntBag();
/* 624 */     if (yPos == null) {
/* 625 */       yPos = new IntBag();
/*     */     }
/* 627 */     getHexagonalLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 628 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   public Bag getHexagonalNeighborsAndLocations(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 661 */     if (xPos == null)
/* 662 */       xPos = new IntBag();
/* 663 */     if (yPos == null) {
/* 664 */       yPos = new IntBag();
/*     */     }
/* 666 */     getHexagonalLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 667 */     reduceObjectsAtLocations(xPos, yPos, result);
/* 668 */     return result;
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 674 */     return getRadialNeighbors(x, y, dist, mode, includeOrigin, 1026, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighborsAndLocations(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 680 */     return getRadialNeighborsAndLocations(x, y, dist, mode, includeOrigin, 1026, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 687 */     if (xPos == null)
/* 688 */       xPos = new IntBag();
/* 689 */     if (yPos == null) {
/* 690 */       yPos = new IntBag();
/*     */     }
/* 692 */     getRadialLocations(x, y, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos);
/* 693 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighborsAndLocations(int x, int y, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 699 */     if (xPos == null)
/* 700 */       xPos = new IntBag();
/* 701 */     if (yPos == null) {
/* 702 */       yPos = new IntBag();
/*     */     }
/* 704 */     getRadialLocations(x, y, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos);
/* 705 */     reduceObjectsAtLocations(xPos, yPos, result);
/* 706 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   void reduceObjectsAtLocations(IntBag xPos, IntBag yPos, Bag result)
/*     */   {
/* 717 */     if (result == null) result = new Bag(); else {
/* 718 */       result.clear();
/*     */     }
/*     */ 
/* 721 */     IntBag newXPos = new IntBag();
/* 722 */     IntBag newYPos = new IntBag();
/*     */ 
/* 724 */     int len = xPos.numObjs;
/* 725 */     int[] xs = xPos.objs;
/* 726 */     int[] ys = yPos.objs;
/*     */ 
/* 729 */     for (int i = 0; i < len; i++)
/*     */     {
/* 731 */       Bag temp = this.field[xPos.objs[i]][yPos.objs[i]];
/* 732 */       int size = temp.numObjs;
/* 733 */       Object[] os = temp.objs;
/*     */ 
/* 735 */       for (int j = 0; j < size; j++)
/*     */       {
/* 738 */         result.add(os[j]);
/* 739 */         newXPos.add(xs[i]);
/* 740 */         newYPos.add(ys[i]);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 745 */     xPos.clear();
/* 746 */     xPos.addAll(newXPos);
/* 747 */     yPos.clear();
/* 748 */     yPos.addAll(newYPos);
/*     */   }
/*     */ 
/*     */   Bag getObjectsAtLocations(IntBag xPos, IntBag yPos, Bag result)
/*     */   {
/* 756 */     if (result == null) result = new Bag(); else {
/* 757 */       result.clear();
/*     */     }
/* 759 */     int len = xPos.numObjs;
/* 760 */     int[] xs = xPos.objs;
/* 761 */     int[] ys = yPos.objs;
/* 762 */     for (int i = 0; i < len; i++)
/*     */     {
/* 766 */       Bag temp = this.field[xPos.objs[i]][yPos.objs[i]];
/* 767 */       if (temp != null)
/*     */       {
/* 769 */         int n = temp.numObjs;
/* 770 */         if (n == 1) result.add(temp.objs[0]);
/* 771 */         else if (n > 1) result.addAll(temp);
/*     */       }
/*     */     }
/* 774 */     return result;
/*     */   }
/*     */ 
/*     */   public Bag getMooreNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 795 */     return getMooreNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ 
/*     */   public Bag getVonNeumannNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 819 */     return getVonNeumannNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ 
/*     */   public Bag getHexagonalNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 844 */     return getHexagonalNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 850 */     return getRadialNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.grid.DenseGrid2D
 * JD-Core Version:    0.6.2
 */