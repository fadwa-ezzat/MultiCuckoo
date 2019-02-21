/*     */ package sim.field.grid;
/*     */ 
/*     */ import sim.util.Bag;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.IntBag;
/*     */ import sim.util.LocationLog;
/*     */ 
/*     */ public class ObjectGrid2D extends AbstractGrid2D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public Object[][] field;
/*     */ 
/*     */   public ObjectGrid2D(int width, int height)
/*     */   {
/*  31 */     this.width = width;
/*  32 */     this.height = height;
/*  33 */     this.field = new Object[width][height];
/*     */   }
/*     */ 
/*     */   public ObjectGrid2D(int width, int height, Object initialValue)
/*     */   {
/*  38 */     this(width, height);
/*  39 */     setTo(initialValue);
/*     */   }
/*     */ 
/*     */   public ObjectGrid2D(ObjectGrid2D values)
/*     */   {
/*  44 */     setTo(values);
/*     */   }
/*     */ 
/*     */   public ObjectGrid2D(Object[][] values)
/*     */   {
/*  49 */     setTo(values);
/*     */   }
/*     */ 
/*     */   public final void set(int x, int y, Object val)
/*     */   {
/*  55 */     assert (LocationLog.it(this, new Int2D(x, y)));
/*  56 */     this.field[x][y] = val;
/*     */   }
/*     */ 
/*     */   public final Object get(int x, int y)
/*     */   {
/*  62 */     assert (LocationLog.it(this, new Int2D(x, y)));
/*  63 */     return this.field[x][y];
/*     */   }
/*     */ 
/*     */   public final ObjectGrid2D setTo(Object thisObj)
/*     */   {
/*  70 */     Object[] fieldx = null;
/*  71 */     int width = this.width;
/*  72 */     int height = this.height;
/*  73 */     for (int x = 0; x < width; x++)
/*     */     {
/*  75 */       fieldx = this.field[x];
/*  76 */       for (int y = 0; y < height; y++)
/*     */       {
/*  78 */         assert (LocationLog.it(this, new Int2D(x, y)));
/*  79 */         fieldx[y] = thisObj;
/*     */       }
/*     */     }
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public ObjectGrid2D setTo(Object[][] field)
/*     */   {
/*  91 */     if (field == null)
/*  92 */       throw new RuntimeException("ObjectGrid2D set to null field.");
/*  93 */     int w = field.length;
/*  94 */     int h = 0;
/*  95 */     if (w != 0) h = field[0].length;
/*  96 */     for (int i = 0; i < w; i++) {
/*  97 */       if (field[i].length != h) {
/*  98 */         throw new RuntimeException("ObjectGrid2D initialized with a non-rectangular field.");
/*     */       }
/*     */     }
/*     */ 
/* 102 */     this.width = w;
/* 103 */     this.height = h;
/* 104 */     this.field = new Object[w][h];
/* 105 */     for (int i = 0; i < w; i++)
/* 106 */       this.field[i] = ((Object[])(Object[])field[i].clone());
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   public final Object[] toArray()
/*     */   {
/* 114 */     Object[][] field = this.field;
/* 115 */     Object[] fieldx = null;
/* 116 */     int width = this.width;
/* 117 */     int height = this.height;
/* 118 */     Object[] vals = new Object[width * height];
/* 119 */     int i = 0;
/* 120 */     for (int x = 0; x < width; x++)
/*     */     {
/* 122 */       fieldx = field[x];
/* 123 */       for (int y = 0; y < height; y++)
/*     */       {
/* 125 */         assert (LocationLog.it(this, new Int2D(x, y)));
/* 126 */         vals[(i++)] = fieldx[y];
/*     */       }
/*     */     }
/* 129 */     return vals;
/*     */   }
/*     */ 
/*     */   public final Bag elements()
/*     */   {
/* 136 */     Bag bag = new Bag();
/* 137 */     Object[] fieldx = null;
/* 138 */     int width = this.width;
/* 139 */     int height = this.height;
/* 140 */     for (int x = 0; x < width; x++)
/*     */     {
/* 142 */       fieldx = this.field[x];
/* 143 */       for (int y = 0; y < height; y++)
/*     */       {
/* 145 */         if (fieldx[y] != null)
/*     */         {
/* 147 */           assert (LocationLog.it(this, new Int2D(x, y)));
/* 148 */           bag.add(fieldx[y]);
/*     */         }
/*     */       }
/*     */     }
/* 152 */     return bag;
/*     */   }
/*     */ 
/*     */   public final Bag clear()
/*     */   {
/* 160 */     Bag bag = new Bag();
/* 161 */     Object[] fieldx = null;
/* 162 */     int width = this.width;
/* 163 */     int height = this.height;
/* 164 */     for (int x = 0; x < width; x++)
/*     */     {
/* 166 */       fieldx = this.field[x];
/* 167 */       for (int y = 0; y < height; y++)
/*     */       {
/* 169 */         assert (LocationLog.it(this, new Int2D(x, y)));
/* 170 */         if (fieldx[y] != null)
/* 171 */           bag.add(fieldx[y]);
/* 172 */         fieldx[y] = null;
/*     */       }
/*     */     }
/* 175 */     return bag;
/*     */   }
/*     */ 
/*     */   public final ObjectGrid2D setTo(ObjectGrid2D values)
/*     */   {
/* 183 */     if (LocationLog.assertsEnabled)
/*     */     {
/* 185 */       for (int x = 0; x < values.width; x++) {
/* 186 */         for (int y = 0; y < values.height; y++)
/* 187 */           assert (LocationLog.it(this, new Int2D(x, y)));
/*     */       }
/*     */     }
/* 190 */     if ((this.width != values.width) || (this.height != values.height))
/*     */     {
/* 192 */       int width = this.width = values.width;
/* 193 */       this.height = values.height;
/* 194 */       Object[][] field = this.field = new Object[width][];
/* 195 */       Object[][] ofield = values.field;
/* 196 */       for (int x = 0; x < width; x++)
/* 197 */         field[x] = ((Object[])(Object[])ofield[x].clone());
/*     */     }
/*     */     else
/*     */     {
/* 201 */       Object[][] field = this.field;
/* 202 */       Object[][] ofield = values.field;
/* 203 */       for (int x = 0; x < this.width; x++)
/* 204 */         System.arraycopy(ofield[x], 0, field[x], 0, this.height);
/*     */     }
/* 206 */     return this;
/*     */   }
/*     */ 
/*     */   public final void replaceAll(Object from, Object to)
/*     */   {
/* 218 */     replaceAll(from, to, false);
/*     */   }
/*     */ 
/*     */   public final void replaceAll(Object from, Object to, boolean onlyIfSameObject)
/*     */   {
/* 233 */     int width = this.width;
/* 234 */     int height = this.height;
/* 235 */     Object[] fieldx = null;
/* 236 */     for (int x = 0; x < width; x++)
/*     */     {
/* 238 */       fieldx = this.field[x];
/* 239 */       for (int y = 0; y < height; y++)
/*     */       {
/* 241 */         Object obj = fieldx[y];
/* 242 */         if (((obj == null) && (from == null)) || ((onlyIfSameObject) && (obj == from)) || ((!onlyIfSameObject) && (obj.equals(from))))
/*     */         {
/* 245 */           fieldx[y] = to;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getNeighborsMaxDistance(int x, int y, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 468 */     return getMooreNeighbors(x, y, dist, toroidal ? 2 : 0, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public Bag getMooreNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 499 */     if (xPos == null)
/* 500 */       xPos = new IntBag();
/* 501 */     if (yPos == null) {
/* 502 */       yPos = new IntBag();
/*     */     }
/* 504 */     getMooreLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 505 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   public Bag getMooreNeighborsAndLocations(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 537 */     if (xPos == null)
/* 538 */       xPos = new IntBag();
/* 539 */     if (yPos == null) {
/* 540 */       yPos = new IntBag();
/*     */     }
/* 542 */     getMooreLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 543 */     reduceObjectsAtLocations(xPos, yPos, result);
/* 544 */     return result;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getNeighborsHamiltonianDistance(int x, int y, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 581 */     return getVonNeumannNeighbors(x, y, dist, toroidal ? 2 : 0, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public Bag getVonNeumannNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 613 */     if (xPos == null)
/* 614 */       xPos = new IntBag();
/* 615 */     if (yPos == null) {
/* 616 */       yPos = new IntBag();
/*     */     }
/* 618 */     getVonNeumannLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 619 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   public Bag getVonNeumannNeighborsAndLocations(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 653 */     if (xPos == null)
/* 654 */       xPos = new IntBag();
/* 655 */     if (yPos == null) {
/* 656 */       yPos = new IntBag();
/*     */     }
/* 658 */     getVonNeumannLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 659 */     reduceObjectsAtLocations(xPos, yPos, result);
/* 660 */     return result;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getNeighborsHexagonalDistance(int x, int y, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 698 */     return getHexagonalNeighbors(x, y, dist, toroidal ? 2 : 0, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public Bag getHexagonalNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 730 */     if (xPos == null)
/* 731 */       xPos = new IntBag();
/* 732 */     if (yPos == null) {
/* 733 */       yPos = new IntBag();
/*     */     }
/* 735 */     getHexagonalLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 736 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   public Bag getHexagonalNeighborsAndLocations(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 769 */     if (xPos == null)
/* 770 */       xPos = new IntBag();
/* 771 */     if (yPos == null) {
/* 772 */       yPos = new IntBag();
/*     */     }
/* 774 */     getHexagonalLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 775 */     reduceObjectsAtLocations(xPos, yPos, result);
/* 776 */     return result;
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 783 */     return getRadialNeighbors(x, y, dist, mode, includeOrigin, 1026, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighborsAndLocations(int x, int y, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 789 */     return getRadialNeighborsAndLocations(x, y, dist, mode, includeOrigin, 1026, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 795 */     if (xPos == null)
/* 796 */       xPos = new IntBag();
/* 797 */     if (yPos == null) {
/* 798 */       yPos = new IntBag();
/*     */     }
/* 800 */     getRadialLocations(x, y, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos);
/* 801 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighborsAndLocations(int x, int y, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, Bag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 807 */     if (xPos == null)
/* 808 */       xPos = new IntBag();
/* 809 */     if (yPos == null) {
/* 810 */       yPos = new IntBag();
/*     */     }
/* 812 */     getRadialLocations(x, y, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos);
/* 813 */     reduceObjectsAtLocations(xPos, yPos, result);
/* 814 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   void reduceObjectsAtLocations(IntBag xPos, IntBag yPos, Bag result)
/*     */   {
/* 823 */     if (result == null) result = new Bag(); else {
/* 824 */       result.clear();
/*     */     }
/* 826 */     for (int i = 0; i < xPos.numObjs; i++)
/*     */     {
/* 828 */       assert (LocationLog.it(this, new Int2D(xPos.objs[i], yPos.objs[i])));
/* 829 */       Object val = this.field[xPos.objs[i]][yPos.objs[i]];
/* 830 */       if (val != null) { result.add(val);
/*     */       } else
/*     */       {
/* 833 */         xPos.remove(i);
/* 834 */         yPos.remove(i);
/* 835 */         i--;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   Bag getObjectsAtLocations(IntBag xPos, IntBag yPos, Bag result)
/*     */   {
/* 845 */     if (result == null) result = new Bag(); else {
/* 846 */       result.clear();
/*     */     }
/* 848 */     for (int i = 0; i < xPos.numObjs; i++)
/*     */     {
/* 850 */       assert (LocationLog.it(this, new Int2D(xPos.objs[i], yPos.objs[i])));
/* 851 */       Object val = this.field[xPos.objs[i]][yPos.objs[i]];
/* 852 */       if (val != null) result.add(val);
/*     */     }
/* 854 */     return result;
/*     */   }
/*     */ 
/*     */   public Bag getMooreNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 877 */     return getMooreNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ 
/*     */   public Bag getVonNeumannNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 901 */     return getVonNeumannNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ 
/*     */   public Bag getHexagonalNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 926 */     return getHexagonalNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 932 */     return getRadialNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.grid.ObjectGrid2D
 * JD-Core Version:    0.6.2
 */