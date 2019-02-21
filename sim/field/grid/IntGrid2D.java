/*     */ package sim.field.grid;
/*     */ 
/*     */ import sim.util.Int2D;
/*     */ import sim.util.IntBag;
/*     */ import sim.util.LocationLog;
/*     */ 
/*     */ public class IntGrid2D extends AbstractGrid2D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public int[][] field;
/*     */ 
/*     */   public IntGrid2D(int width, int height)
/*     */   {
/*  29 */     this.width = width;
/*  30 */     this.height = height;
/*  31 */     this.field = new int[width][height];
/*     */   }
/*     */ 
/*     */   public IntGrid2D(int width, int height, int initialValue)
/*     */   {
/*  36 */     this(width, height);
/*  37 */     setTo(initialValue);
/*     */   }
/*     */ 
/*     */   public IntGrid2D(IntGrid2D values)
/*     */   {
/*  42 */     setTo(values);
/*     */   }
/*     */ 
/*     */   public IntGrid2D(int[][] values)
/*     */   {
/*  47 */     setTo(values);
/*     */   }
/*     */ 
/*     */   public final void set(int x, int y, int val)
/*     */   {
/*  53 */     assert (LocationLog.it(this, new Int2D(x, y)));
/*  54 */     this.field[x][y] = val;
/*     */   }
/*     */ 
/*     */   public final int get(int x, int y)
/*     */   {
/*  60 */     assert (LocationLog.it(this, new Int2D(x, y)));
/*  61 */     return this.field[x][y];
/*     */   }
/*     */ 
/*     */   public final IntGrid2D setTo(int thisMuch)
/*     */   {
/*  67 */     int[] fieldx = null;
/*  68 */     int width = this.width;
/*  69 */     int height = this.height;
/*  70 */     for (int x = 0; x < width; x++)
/*     */     {
/*  72 */       fieldx = this.field[x];
/*  73 */       for (int y = 0; y < height; y++)
/*     */       {
/*  75 */         assert (LocationLog.it(this, new Int2D(x, y)));
/*  76 */         fieldx[y] = thisMuch;
/*     */       }
/*     */     }
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */   public IntGrid2D setTo(int[][] field)
/*     */   {
/*  87 */     if (field == null)
/*  88 */       throw new RuntimeException("IntGrid2D set to null field.");
/*  89 */     int w = field.length;
/*  90 */     int h = 0;
/*  91 */     if (w != 0) h = field[0].length;
/*  92 */     for (int i = 0; i < w; i++) {
/*  93 */       if (field[i].length != h) {
/*  94 */         throw new RuntimeException("IntGrid2D initialized with a non-rectangular field.");
/*     */       }
/*     */     }
/*     */ 
/*  98 */     this.width = w;
/*  99 */     this.height = h;
/* 100 */     this.field = new int[w][h];
/* 101 */     for (int i = 0; i < w; i++)
/* 102 */       this.field[i] = ((int[])(int[])field[i].clone());
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */   public final IntGrid2D setTo(IntGrid2D values)
/*     */   {
/* 111 */     if (LocationLog.assertsEnabled)
/*     */     {
/* 113 */       for (int x = 0; x < values.width; x++) {
/* 114 */         for (int y = 0; y < values.height; y++)
/* 115 */           assert (LocationLog.it(this, new Int2D(x, y)));
/*     */       }
/*     */     }
/* 118 */     if ((this.width != values.width) || (this.height != values.height))
/*     */     {
/* 120 */       int width = this.width = values.width;
/* 121 */       this.height = values.height;
/* 122 */       int[][] field = this.field = new int[width][];
/* 123 */       for (int x = 0; x < width; x++)
/* 124 */         field[x] = ((int[])(int[])values.field[x].clone());
/*     */     }
/*     */     else
/*     */     {
/* 128 */       for (int x = 0; x < this.width; x++)
/*     */       {
/* 130 */         System.arraycopy(values.field[x], 0, this.field[x], 0, this.height);
/*     */       }
/*     */     }
/*     */ 
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   public final int[] toArray()
/*     */   {
/* 141 */     int[][] field = this.field;
/* 142 */     int[] fieldx = null;
/* 143 */     int width = this.width;
/* 144 */     int height = this.height;
/* 145 */     int[] vals = new int[width * height];
/* 146 */     int i = 0;
/* 147 */     for (int x = 0; x < width; x++)
/*     */     {
/* 149 */       fieldx = field[x];
/* 150 */       for (int y = 0; y < height; y++)
/*     */       {
/* 152 */         assert (LocationLog.it(this, new Int2D(x, y)));
/* 153 */         vals[(i++)] = fieldx[y];
/*     */       }
/*     */     }
/* 156 */     return vals;
/*     */   }
/*     */ 
/*     */   public final int max()
/*     */   {
/* 162 */     int max = -2147483648;
/* 163 */     int[] fieldx = null;
/* 164 */     int width = this.width;
/* 165 */     int height = this.height;
/* 166 */     for (int x = 0; x < width; x++)
/*     */     {
/* 168 */       fieldx = this.field[x];
/* 169 */       for (int y = 0; y < height; y++)
/*     */       {
/* 171 */         assert (LocationLog.it(this, new Int2D(x, y)));
/* 172 */         if (max < fieldx[y]) max = fieldx[y];
/*     */       }
/*     */     }
/* 175 */     return max;
/*     */   }
/*     */ 
/*     */   public final int min()
/*     */   {
/* 181 */     int min = 2147483647;
/* 182 */     int[] fieldx = null;
/* 183 */     int width = this.width;
/* 184 */     int height = this.height;
/* 185 */     for (int x = 0; x < width; x++)
/*     */     {
/* 187 */       fieldx = this.field[x];
/* 188 */       for (int y = 0; y < height; y++)
/*     */       {
/* 190 */         assert (LocationLog.it(this, new Int2D(x, y)));
/* 191 */         if (min > fieldx[y]) min = fieldx[y];
/*     */       }
/*     */     }
/* 194 */     return min;
/*     */   }
/*     */ 
/*     */   public final double mean()
/*     */   {
/* 200 */     long count = 0L;
/* 201 */     double mean = 0.0D;
/* 202 */     int[] fieldx = null;
/* 203 */     int width = this.width;
/* 204 */     int height = this.height;
/* 205 */     for (int x = 0; x < width; x++)
/*     */     {
/* 207 */       fieldx = this.field[x];
/* 208 */       for (int y = 0; y < height; y++)
/*     */       {
/* 210 */         assert (LocationLog.it(this, new Int2D(x, y)));
/* 211 */         mean += fieldx[y];
/* 212 */         count += 1L;
/*     */       }
/*     */     }
/* 215 */     return count == 0L ? 0.0D : mean / count;
/*     */   }
/*     */ 
/*     */   public final IntGrid2D upperBound(int toNoMoreThanThisMuch)
/*     */   {
/* 223 */     int[] fieldx = null;
/* 224 */     int width = this.width;
/* 225 */     int height = this.height;
/* 226 */     for (int x = 0; x < width; x++)
/*     */     {
/* 228 */       fieldx = this.field[x];
/* 229 */       for (int y = 0; y < height; y++)
/*     */       {
/* 231 */         assert (LocationLog.it(this, new Int2D(x, y)));
/* 232 */         if (fieldx[y] > toNoMoreThanThisMuch)
/* 233 */           fieldx[y] = toNoMoreThanThisMuch;
/*     */       }
/*     */     }
/* 236 */     return this;
/*     */   }
/*     */ 
/*     */   public final IntGrid2D lowerBound(int toNoLowerThanThisMuch)
/*     */   {
/* 245 */     int[] fieldx = null;
/* 246 */     int width = this.width;
/* 247 */     int height = this.height;
/* 248 */     for (int x = 0; x < width; x++)
/*     */     {
/* 250 */       fieldx = this.field[x];
/* 251 */       for (int y = 0; y < height; y++)
/*     */       {
/* 253 */         assert (LocationLog.it(this, new Int2D(x, y)));
/* 254 */         if (fieldx[y] < toNoLowerThanThisMuch)
/* 255 */           fieldx[y] = toNoLowerThanThisMuch;
/*     */       }
/*     */     }
/* 258 */     return this;
/*     */   }
/*     */ 
/*     */   public final IntGrid2D add(int withThisMuch)
/*     */   {
/* 267 */     if (withThisMuch == 0.0D) return this;
/* 268 */     int[] fieldx = null;
/* 269 */     int width = this.width;
/* 270 */     int height = this.height;
/* 271 */     for (int x = 0; x < width; x++)
/*     */     {
/* 273 */       fieldx = this.field[x];
/* 274 */       for (int y = 0; y < height; y++)
/*     */       {
/* 276 */         assert (LocationLog.it(this, new Int2D(x, y)));
/* 277 */         fieldx[y] += withThisMuch;
/*     */       }
/*     */     }
/* 280 */     return this;
/*     */   }
/*     */ 
/*     */   public final IntGrid2D add(IntGrid2D withThis)
/*     */   {
/* 289 */     checkBounds(withThis);
/* 290 */     int[][] ofield = withThis.field;
/* 291 */     int[] ofieldx = null;
/* 292 */     int[] fieldx = null;
/* 293 */     int width = this.width;
/* 294 */     int height = this.height;
/* 295 */     for (int x = 0; x < width; x++)
/*     */     {
/* 297 */       fieldx = this.field[x];
/* 298 */       ofieldx = ofield[x];
/* 299 */       for (int y = 0; y < height; y++)
/*     */       {
/* 301 */         assert (LocationLog.it(this, new Int2D(x, y)));
/* 302 */         fieldx[y] += ofieldx[y];
/*     */       }
/*     */     }
/* 305 */     return this;
/*     */   }
/*     */ 
/*     */   public final IntGrid2D multiply(int byThisMuch)
/*     */   {
/* 314 */     if (byThisMuch == 1.0D) return this;
/* 315 */     int[] fieldx = null;
/* 316 */     int width = this.width;
/* 317 */     int height = this.height;
/* 318 */     for (int x = 0; x < width; x++)
/*     */     {
/* 320 */       fieldx = this.field[x];
/* 321 */       for (int y = 0; y < height; y++)
/*     */       {
/* 323 */         assert (LocationLog.it(this, new Int2D(x, y)));
/* 324 */         fieldx[y] *= byThisMuch;
/*     */       }
/*     */     }
/* 327 */     return this;
/*     */   }
/*     */ 
/*     */   public final IntGrid2D multiply(IntGrid2D withThis)
/*     */   {
/* 336 */     checkBounds(withThis);
/* 337 */     int[][] ofield = withThis.field;
/* 338 */     int[] ofieldx = null;
/* 339 */     int[] fieldx = null;
/* 340 */     int width = this.width;
/* 341 */     int height = this.height;
/* 342 */     for (int x = 0; x < width; x++)
/*     */     {
/* 344 */       fieldx = this.field[x];
/* 345 */       ofieldx = ofield[x];
/* 346 */       for (int y = 0; y < height; y++)
/*     */       {
/* 348 */         assert (LocationLog.it(this, new Int2D(x, y)));
/* 349 */         fieldx[y] *= ofieldx[y];
/*     */       }
/*     */     }
/* 352 */     return this;
/*     */   }
/*     */ 
/*     */   public final void replaceAll(int from, int to)
/*     */   {
/* 363 */     int width = this.width;
/* 364 */     int height = this.height;
/* 365 */     int[] fieldx = null;
/* 366 */     for (int x = 0; x < width; x++)
/*     */     {
/* 368 */       fieldx = this.field[x];
/* 369 */       for (int y = 0; y < height; y++)
/*     */       {
/* 371 */         if (fieldx[y] == from)
/* 372 */           fieldx[y] = to;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsMaxDistance(int x, int y, int dist, boolean toroidal, IntBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 594 */     getMooreNeighbors(x, y, dist, toroidal ? 2 : 0, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public IntBag getMooreNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, IntBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 623 */     if (xPos == null)
/* 624 */       xPos = new IntBag();
/* 625 */     if (yPos == null) {
/* 626 */       yPos = new IntBag();
/*     */     }
/* 628 */     getMooreLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 629 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsHamiltonianDistance(int x, int y, int dist, boolean toroidal, IntBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 665 */     getVonNeumannNeighbors(x, y, dist, toroidal ? 2 : 0, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public IntBag getVonNeumannNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, IntBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 695 */     if (xPos == null)
/* 696 */       xPos = new IntBag();
/* 697 */     if (yPos == null) {
/* 698 */       yPos = new IntBag();
/*     */     }
/* 700 */     getVonNeumannLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 701 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsHexagonalDistance(int x, int y, int dist, boolean toroidal, IntBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 739 */     getHexagonalNeighbors(x, y, dist, toroidal ? 2 : 0, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public IntBag getHexagonalNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, IntBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 769 */     if (xPos == null)
/* 770 */       xPos = new IntBag();
/* 771 */     if (yPos == null) {
/* 772 */       yPos = new IntBag();
/*     */     }
/* 774 */     getHexagonalLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 775 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   public IntBag getRadialNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, IntBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 781 */     return getRadialNeighbors(x, y, dist, mode, includeOrigin, 1026, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public IntBag getRadialNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, IntBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 786 */     if (xPos == null)
/* 787 */       xPos = new IntBag();
/* 788 */     if (yPos == null) {
/* 789 */       yPos = new IntBag();
/*     */     }
/* 791 */     getRadialLocations(x, y, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos);
/* 792 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   void reduceObjectsAtLocations(IntBag xPos, IntBag yPos, IntBag result)
/*     */   {
/* 803 */     if (result == null) result = new IntBag(); else {
/* 804 */       result.clear();
/*     */     }
/* 806 */     for (int i = 0; i < xPos.numObjs; i++)
/*     */     {
/* 808 */       assert (LocationLog.it(this, new Int2D(xPos.objs[i], yPos.objs[i])));
/* 809 */       int val = this.field[xPos.objs[i]][yPos.objs[i]];
/* 810 */       result.add(val);
/*     */     }
/*     */   }
/*     */ 
/*     */   IntBag getObjectsAtLocations(IntBag xPos, IntBag yPos, IntBag result)
/*     */   {
/* 819 */     if (result == null) result = new IntBag(); else {
/* 820 */       result.clear();
/*     */     }
/* 822 */     for (int i = 0; i < xPos.numObjs; i++)
/*     */     {
/* 824 */       assert (LocationLog.it(this, new Int2D(xPos.objs[i], yPos.objs[i])));
/* 825 */       int val = this.field[xPos.objs[i]][yPos.objs[i]];
/* 826 */       result.add(val);
/*     */     }
/* 828 */     return result;
/*     */   }
/*     */ 
/*     */   public IntBag getMooreNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 851 */     return getMooreNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ 
/*     */   public IntBag getVonNeumannNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 875 */     return getVonNeumannNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ 
/*     */   public IntBag getHexagonalNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 900 */     return getHexagonalNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ 
/*     */   public IntBag getRadialNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 906 */     return getRadialNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.grid.IntGrid2D
 * JD-Core Version:    0.6.2
 */