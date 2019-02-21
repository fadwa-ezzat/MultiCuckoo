/*     */ package sim.field.grid;
/*     */ 
/*     */ import sim.util.DoubleBag;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.IntBag;
/*     */ import sim.util.LocationLog;
/*     */ 
/*     */ public class DoubleGrid2D extends AbstractGrid2D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public double[][] field;
/*     */ 
/*     */   public double[][] getField()
/*     */   {
/*  26 */     return this.field;
/*     */   }
/*     */ 
/*     */   public DoubleGrid2D(int width, int height) {
/*  30 */     this.width = width;
/*  31 */     this.height = height;
/*  32 */     this.field = new double[width][height];
/*     */   }
/*     */ 
/*     */   public DoubleGrid2D(int width, int height, double initialValue)
/*     */   {
/*  37 */     this(width, height);
/*  38 */     setTo(initialValue);
/*     */   }
/*     */ 
/*     */   public DoubleGrid2D(DoubleGrid2D values)
/*     */   {
/*  43 */     setTo(values);
/*     */   }
/*     */ 
/*     */   public DoubleGrid2D(double[][] values)
/*     */   {
/*  48 */     setTo(values);
/*     */   }
/*     */ 
/*     */   public final void set(int x, int y, double val)
/*     */   {
/*  54 */     this.field[x][y] = val;
/*     */   }
/*     */ 
/*     */   public final double get(int x, int y)
/*     */   {
/*  60 */     return this.field[x][y];
/*     */   }
/*     */ 
/*     */   public final DoubleGrid2D setTo(double thisMuch)
/*     */   {
/*  66 */     double[] fieldx = null;
/*  67 */     int width = this.width;
/*  68 */     int height = this.height;
/*  69 */     for (int x = 0; x < width; x++)
/*     */     {
/*  71 */       fieldx = this.field[x];
/*  72 */       for (int y = 0; y < height; y++)
/*  73 */         fieldx[y] = thisMuch;
/*     */     }
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public DoubleGrid2D setTo(double[][] field)
/*     */   {
/*  83 */     if (field == null)
/*  84 */       throw new RuntimeException("DoubleGrid2D set to null field.");
/*  85 */     int w = field.length;
/*  86 */     int h = 0;
/*  87 */     if (w != 0) h = field[0].length;
/*  88 */     for (int i = 0; i < w; i++) {
/*  89 */       if (field[i].length != h) {
/*  90 */         throw new RuntimeException("DoubleGrid2D initialized with a non-rectangular field.");
/*     */       }
/*     */     }
/*     */ 
/*  94 */     this.width = w;
/*  95 */     this.height = h;
/*  96 */     this.field = new double[w][h];
/*  97 */     for (int i = 0; i < w; i++)
/*  98 */       this.field[i] = ((double[])(double[])field[i].clone());
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid2D setTo(DoubleGrid2D values)
/*     */   {
/* 107 */     if ((this.width != values.width) || (this.height != values.height))
/*     */     {
/* 109 */       int width = this.width = values.width;
/* 110 */       this.height = values.height;
/* 111 */       this.field = new double[width][];
/* 112 */       for (int x = 0; x < width; x++)
/* 113 */         this.field[x] = ((double[])(double[])values.field[x].clone());
/*     */     }
/*     */     else
/*     */     {
/* 117 */       for (int x = 0; x < this.width; x++)
/* 118 */         System.arraycopy(values.field[x], 0, this.field[x], 0, this.height);
/*     */     }
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */   public final double[] toArray()
/*     */   {
/* 127 */     double[][] field = this.field;
/* 128 */     double[] fieldx = null;
/* 129 */     int width = this.width;
/* 130 */     int height = this.height;
/* 131 */     double[] vals = new double[width * height];
/* 132 */     int i = 0;
/* 133 */     for (int x = 0; x < width; x++)
/*     */     {
/* 135 */       fieldx = field[x];
/* 136 */       for (int y = 0; y < height; y++)
/*     */       {
/* 138 */         vals[(i++)] = fieldx[y];
/*     */       }
/*     */     }
/* 141 */     return vals;
/*     */   }
/*     */ 
/*     */   public final double max()
/*     */   {
/* 147 */     double max = (-1.0D / 0.0D);
/* 148 */     int width = this.width;
/* 149 */     int height = this.height;
/* 150 */     double[] fieldx = null;
/* 151 */     for (int x = 0; x < width; x++)
/*     */     {
/* 153 */       fieldx = this.field[x];
/* 154 */       for (int y = 0; y < height; y++)
/* 155 */         if (max < fieldx[y]) max = fieldx[y];
/*     */     }
/* 157 */     return max;
/*     */   }
/*     */ 
/*     */   public final double min()
/*     */   {
/* 163 */     double min = (1.0D / 0.0D);
/* 164 */     int width = this.width;
/* 165 */     int height = this.height;
/* 166 */     double[] fieldx = null;
/* 167 */     for (int x = 0; x < width; x++)
/*     */     {
/* 169 */       fieldx = this.field[x];
/* 170 */       for (int y = 0; y < height; y++)
/* 171 */         if (min > fieldx[y]) min = fieldx[y];
/*     */     }
/* 173 */     return min;
/*     */   }
/*     */ 
/*     */   public final double mean()
/*     */   {
/* 179 */     long count = 0L;
/* 180 */     double mean = 0.0D;
/* 181 */     double[] fieldx = null;
/* 182 */     int width = this.width;
/* 183 */     int height = this.height;
/* 184 */     for (int x = 0; x < width; x++)
/*     */     {
/* 186 */       fieldx = this.field[x];
/* 187 */       for (int y = 0; y < height; y++) {
/* 188 */         mean += fieldx[y]; count += 1L;
/*     */       }
/*     */     }
/* 190 */     return count == 0L ? 0.0D : mean / count;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid2D upperBound(double toNoMoreThanThisMuch)
/*     */   {
/* 198 */     double[] fieldx = null;
/* 199 */     int width = this.width;
/* 200 */     int height = this.height;
/* 201 */     for (int x = 0; x < width; x++)
/*     */     {
/* 203 */       fieldx = this.field[x];
/* 204 */       for (int y = 0; y < height; y++)
/* 205 */         if (fieldx[y] > toNoMoreThanThisMuch)
/* 206 */           fieldx[y] = toNoMoreThanThisMuch;
/*     */     }
/* 208 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid2D lowerBound(double toNoLowerThanThisMuch)
/*     */   {
/* 216 */     double[] fieldx = null;
/* 217 */     int width = this.width;
/* 218 */     int height = this.height;
/* 219 */     for (int x = 0; x < width; x++)
/*     */     {
/* 221 */       fieldx = this.field[x];
/* 222 */       for (int y = 0; y < height; y++)
/* 223 */         if (fieldx[y] < toNoLowerThanThisMuch)
/* 224 */           fieldx[y] = toNoLowerThanThisMuch;
/*     */     }
/* 226 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid2D add(double withThisMuch)
/*     */   {
/* 234 */     int width = this.width;
/* 235 */     int height = this.height;
/* 236 */     if (withThisMuch == 0.0D) return this;
/* 237 */     double[] fieldx = null;
/* 238 */     for (int x = 0; x < width; x++)
/*     */     {
/* 240 */       fieldx = this.field[x];
/* 241 */       for (int y = 0; y < height; y++)
/* 242 */         fieldx[y] += withThisMuch;
/*     */     }
/* 244 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid2D add(IntGrid2D withThis)
/*     */   {
/* 252 */     checkBounds(withThis);
/* 253 */     int[][] otherField = withThis.field;
/* 254 */     double[] fieldx = null;
/* 255 */     int[] ofieldx = null;
/* 256 */     int width = this.width;
/* 257 */     int height = this.height;
/* 258 */     for (int x = 0; x < width; x++)
/*     */     {
/* 260 */       fieldx = this.field[x];
/* 261 */       ofieldx = otherField[x];
/* 262 */       for (int y = 0; y < height; y++)
/* 263 */         fieldx[y] += ofieldx[y];
/*     */     }
/* 265 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid2D add(DoubleGrid2D withThis)
/*     */   {
/* 273 */     checkBounds(withThis);
/* 274 */     double[][] otherField = withThis.field;
/* 275 */     double[] fieldx = null;
/* 276 */     double[] ofieldx = null;
/* 277 */     int width = this.width;
/* 278 */     int height = this.height;
/* 279 */     for (int x = 0; x < width; x++)
/*     */     {
/* 281 */       fieldx = this.field[x];
/* 282 */       ofieldx = otherField[x];
/* 283 */       for (int y = 0; y < height; y++)
/* 284 */         fieldx[y] += ofieldx[y];
/*     */     }
/* 286 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid2D multiply(double byThisMuch)
/*     */   {
/* 294 */     if (byThisMuch == 1.0D) return this;
/* 295 */     double[] fieldx = null;
/* 296 */     int width = this.width;
/* 297 */     int height = this.height;
/* 298 */     for (int x = 0; x < width; x++)
/*     */     {
/* 300 */       fieldx = this.field[x];
/* 301 */       for (int y = 0; y < height; y++)
/* 302 */         fieldx[y] *= byThisMuch;
/*     */     }
/* 304 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid2D multiply(IntGrid2D withThis)
/*     */   {
/* 312 */     checkBounds(withThis);
/* 313 */     int[][] otherField = withThis.field;
/* 314 */     double[] fieldx = null;
/* 315 */     int[] ofieldx = null;
/* 316 */     int width = this.width;
/* 317 */     int height = this.height;
/* 318 */     for (int x = 0; x < width; x++)
/*     */     {
/* 320 */       fieldx = this.field[x];
/* 321 */       ofieldx = otherField[x];
/* 322 */       for (int y = 0; y < height; y++)
/* 323 */         fieldx[y] *= ofieldx[y];
/*     */     }
/* 325 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid2D multiply(DoubleGrid2D withThis)
/*     */   {
/* 333 */     checkBounds(withThis);
/* 334 */     double[][] otherField = withThis.field;
/* 335 */     double[] fieldx = null;
/* 336 */     double[] ofieldx = null;
/* 337 */     int width = this.width;
/* 338 */     int height = this.height;
/* 339 */     for (int x = 0; x < width; x++)
/*     */     {
/* 341 */       fieldx = this.field[x];
/* 342 */       ofieldx = otherField[x];
/* 343 */       for (int y = 0; y < height; y++)
/* 344 */         fieldx[y] *= ofieldx[y];
/*     */     }
/* 346 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid2D floor()
/*     */   {
/* 355 */     double[] fieldx = null;
/* 356 */     int width = this.width;
/* 357 */     int height = this.height;
/* 358 */     for (int x = 0; x < width; x++)
/*     */     {
/* 360 */       fieldx = this.field[x];
/* 361 */       for (int y = 0; y < height; y++)
/* 362 */         fieldx[y] = Math.floor(fieldx[y]);
/*     */     }
/* 364 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid2D ceiling()
/*     */   {
/* 372 */     double[] fieldx = null;
/* 373 */     int width = this.width;
/* 374 */     int height = this.height;
/* 375 */     for (int x = 0; x < width; x++)
/*     */     {
/* 377 */       fieldx = this.field[x];
/* 378 */       for (int y = 0; y < height; y++)
/* 379 */         fieldx[y] = Math.ceil(fieldx[y]);
/*     */     }
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid2D truncate()
/*     */   {
/* 389 */     double[] fieldx = null;
/* 390 */     int width = this.width;
/* 391 */     int height = this.height;
/* 392 */     for (int x = 0; x < width; x++)
/*     */     {
/* 394 */       fieldx = this.field[x];
/* 395 */       for (int y = 0; y < height; y++) {
/* 396 */         fieldx[y] = ((int)fieldx[y]);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 403 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid2D rint()
/*     */   {
/* 413 */     double[] fieldx = null;
/* 414 */     int width = this.width;
/* 415 */     int height = this.height;
/* 416 */     for (int x = 0; x < width; x++)
/*     */     {
/* 418 */       fieldx = this.field[x];
/* 419 */       for (int y = 0; y < height; y++)
/* 420 */         fieldx[y] = Math.rint(fieldx[y]);
/*     */     }
/* 422 */     return this;
/*     */   }
/*     */ 
/*     */   public final void replaceAll(double from, double to)
/*     */   {
/* 433 */     int width = this.width;
/* 434 */     int height = this.height;
/* 435 */     double[] fieldx = null;
/* 436 */     for (int x = 0; x < width; x++)
/*     */     {
/* 438 */       fieldx = this.field[x];
/* 439 */       for (int y = 0; y < height; y++)
/*     */       {
/* 441 */         if (fieldx[y] == from)
/* 442 */           fieldx[y] = to;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsMaxDistance(int x, int y, int dist, boolean toroidal, DoubleBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 669 */     getMooreNeighbors(x, y, dist, toroidal ? 2 : 0, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public DoubleBag getMooreNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, DoubleBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 700 */     if (xPos == null)
/* 701 */       xPos = new IntBag();
/* 702 */     if (yPos == null) {
/* 703 */       yPos = new IntBag();
/*     */     }
/* 705 */     getMooreLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 706 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsHamiltonianDistance(int x, int y, int dist, boolean toroidal, DoubleBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 743 */     getVonNeumannNeighbors(x, y, dist, toroidal ? 2 : 0, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public DoubleBag getVonNeumannNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, DoubleBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 775 */     if (xPos == null)
/* 776 */       xPos = new IntBag();
/* 777 */     if (yPos == null) {
/* 778 */       yPos = new IntBag();
/*     */     }
/* 780 */     getVonNeumannLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 781 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsHexagonalDistance(int x, int y, int dist, boolean toroidal, DoubleBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 819 */     getHexagonalNeighbors(x, y, dist, toroidal ? 2 : 0, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public DoubleBag getHexagonalNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, DoubleBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 851 */     if (xPos == null)
/* 852 */       xPos = new IntBag();
/* 853 */     if (yPos == null) {
/* 854 */       yPos = new IntBag();
/*     */     }
/* 856 */     getHexagonalLocations(x, y, dist, mode, includeOrigin, xPos, yPos);
/* 857 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   public DoubleBag getRadialNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, DoubleBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 863 */     return getRadialNeighbors(x, y, dist, mode, includeOrigin, 1026, true, result, xPos, yPos);
/*     */   }
/*     */ 
/*     */   public DoubleBag getRadialNeighbors(int x, int y, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, DoubleBag result, IntBag xPos, IntBag yPos)
/*     */   {
/* 869 */     if (xPos == null)
/* 870 */       xPos = new IntBag();
/* 871 */     if (yPos == null) {
/* 872 */       yPos = new IntBag();
/*     */     }
/* 874 */     getRadialLocations(x, y, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos);
/* 875 */     return getObjectsAtLocations(xPos, yPos, result);
/*     */   }
/*     */ 
/*     */   void reduceObjectsAtLocations(IntBag xPos, IntBag yPos, DoubleBag result)
/*     */   {
/* 886 */     if (result == null) result = new DoubleBag(); else {
/* 887 */       result.clear();
/*     */     }
/* 889 */     for (int i = 0; i < xPos.numObjs; i++)
/*     */     {
/* 891 */       assert (LocationLog.it(this, new Int2D(xPos.objs[i], yPos.objs[i])));
/* 892 */       double val = this.field[xPos.objs[i]][yPos.objs[i]];
/* 893 */       result.add(val);
/*     */     }
/*     */   }
/*     */ 
/*     */   DoubleBag getObjectsAtLocations(IntBag xPos, IntBag yPos, DoubleBag result)
/*     */   {
/* 902 */     if (result == null) result = new DoubleBag(); else {
/* 903 */       result.clear();
/*     */     }
/* 905 */     for (int i = 0; i < xPos.numObjs; i++)
/*     */     {
/* 907 */       assert (LocationLog.it(this, new Int2D(xPos.objs[i], yPos.objs[i])));
/* 908 */       double val = this.field[xPos.objs[i]][yPos.objs[i]];
/* 909 */       result.add(val);
/*     */     }
/* 911 */     return result;
/*     */   }
/*     */ 
/*     */   public DoubleBag getMooreNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 934 */     return getMooreNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ 
/*     */   public DoubleBag getVonNeumannNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 958 */     return getVonNeumannNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ 
/*     */   public DoubleBag getHexagonalNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 983 */     return getHexagonalNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ 
/*     */   public DoubleBag getRadialNeighbors(int x, int y, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 989 */     return getRadialNeighbors(x, y, dist, mode, includeOrigin, null, null, null);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.grid.DoubleGrid2D
 * JD-Core Version:    0.6.2
 */