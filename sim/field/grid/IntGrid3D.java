/*     */ package sim.field.grid;
/*     */ 
/*     */ import sim.util.Int3D;
/*     */ import sim.util.IntBag;
/*     */ import sim.util.LocationLog;
/*     */ 
/*     */ public class IntGrid3D extends AbstractGrid3D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public int[][][] field;
/*     */ 
/*     */   public IntGrid3D(int width, int height, int length)
/*     */   {
/*  29 */     this.width = width;
/*  30 */     this.height = height;
/*  31 */     this.length = length;
/*  32 */     this.field = new int[width][height][length];
/*     */   }
/*     */ 
/*     */   public IntGrid3D(int width, int height, int length, int initialValue)
/*     */   {
/*  37 */     this(width, height, length);
/*  38 */     setTo(initialValue);
/*     */   }
/*     */ 
/*     */   public IntGrid3D(IntGrid3D values)
/*     */   {
/*  44 */     setTo(values);
/*     */   }
/*     */ 
/*     */   public IntGrid3D(int[][][] values)
/*     */   {
/*  49 */     setTo(values);
/*     */   }
/*     */ 
/*     */   public final int set(int x, int y, int z, int val)
/*     */   {
/*  55 */     int returnval = this.field[x][y][z];
/*  56 */     this.field[x][y][z] = val;
/*  57 */     return returnval;
/*     */   }
/*     */ 
/*     */   public final int get(int x, int y, int z)
/*     */   {
/*  63 */     return this.field[x][y][z];
/*     */   }
/*     */ 
/*     */   public final int[] toArray()
/*     */   {
/*  70 */     int[][][] field = this.field;
/*  71 */     int[][] fieldx = (int[][])null;
/*  72 */     int[] fieldxy = null;
/*  73 */     int width = this.width;
/*  74 */     int height = this.height;
/*  75 */     int length = this.length;
/*  76 */     int[] vals = new int[width * height * length];
/*  77 */     int i = 0;
/*  78 */     for (int x = 0; x < width; x++)
/*     */     {
/*  80 */       fieldx = field[x];
/*  81 */       for (int y = 0; y < height; y++)
/*     */       {
/*  83 */         fieldxy = fieldx[y];
/*  84 */         for (int z = 0; z < length; z++)
/*     */         {
/*  86 */           vals[(i++)] = fieldxy[z];
/*     */         }
/*     */       }
/*     */     }
/*  90 */     return vals;
/*     */   }
/*     */ 
/*     */   public final int max()
/*     */   {
/*  96 */     int max = -2147483648;
/*  97 */     int[][] fieldx = (int[][])null;
/*  98 */     int[] fieldxy = null;
/*  99 */     int width = this.width;
/* 100 */     int height = this.height;
/* 101 */     int length = this.length;
/*     */ 
/* 103 */     for (int x = 0; x < width; x++)
/*     */     {
/* 105 */       fieldx = this.field[x];
/* 106 */       for (int y = 0; y < height; y++)
/*     */       {
/* 108 */         fieldxy = fieldx[y];
/* 109 */         for (int z = 0; z < length; z++)
/* 110 */           if (max < fieldxy[z]) max = fieldxy[z];
/*     */       }
/*     */     }
/* 113 */     return max;
/*     */   }
/*     */ 
/*     */   public final int min()
/*     */   {
/* 119 */     int min = 2147483647;
/* 120 */     int[][] fieldx = (int[][])null;
/* 121 */     int[] fieldxy = null;
/* 122 */     int width = this.width;
/* 123 */     int height = this.height;
/* 124 */     int length = this.length;
/* 125 */     for (int x = 0; x < width; x++)
/*     */     {
/* 127 */       fieldx = this.field[x];
/* 128 */       for (int y = 0; y < height; y++)
/*     */       {
/* 130 */         fieldxy = fieldx[y];
/* 131 */         for (int z = 0; z < length; z++)
/* 132 */           if (min > fieldxy[z]) min = fieldxy[z];
/*     */       }
/*     */     }
/* 135 */     return min;
/*     */   }
/*     */ 
/*     */   public final double mean()
/*     */   {
/* 141 */     long count = 0L;
/* 142 */     double mean = 0.0D;
/* 143 */     int[][] fieldx = (int[][])null;
/* 144 */     int[] fieldxy = null;
/* 145 */     int width = this.width;
/* 146 */     int height = this.height;
/* 147 */     int length = this.length;
/* 148 */     for (int x = 0; x < width; x++)
/*     */     {
/* 150 */       fieldx = this.field[x];
/* 151 */       for (int y = 0; y < height; y++)
/*     */       {
/* 153 */         fieldxy = fieldx[y];
/* 154 */         for (int z = 0; z < length; z++) {
/* 155 */           mean += fieldxy[z]; count += 1L;
/*     */         }
/*     */       }
/*     */     }
/* 158 */     return count == 0L ? 0.0D : mean / count;
/*     */   }
/*     */ 
/*     */   public final IntGrid3D setTo(int thisMuch)
/*     */   {
/* 164 */     int[][] fieldx = (int[][])null;
/* 165 */     int[] fieldxy = null;
/* 166 */     int width = this.width;
/* 167 */     int height = this.height;
/* 168 */     int length = this.length;
/* 169 */     for (int x = 0; x < width; x++)
/*     */     {
/* 171 */       fieldx = this.field[x];
/* 172 */       for (int y = 0; y < height; y++)
/*     */       {
/* 174 */         fieldxy = fieldx[y];
/* 175 */         for (int z = 0; z < length; z++)
/* 176 */           fieldxy[z] = thisMuch;
/*     */       }
/*     */     }
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */   public final IntGrid3D setTo(IntGrid3D values)
/*     */   {
/* 187 */     if ((this.width != values.width) || (this.height != values.height) || (this.length != values.length))
/*     */     {
/* 189 */       int width = this.width = values.width;
/* 190 */       int height = this.height = values.height;
/* 191 */       this.length = values.length;
/* 192 */       this.field = new int[width][height];
/* 193 */       int[][] fieldx = (int[][])null;
/* 194 */       int[][] ofieldx = (int[][])null;
/* 195 */       for (int x = 0; x < width; x++)
/*     */       {
/* 197 */         fieldx = this.field[x];
/* 198 */         ofieldx = values.field[x];
/* 199 */         for (int y = 0; y < height; y++)
/* 200 */           fieldx[y] = ((int[])(int[])ofieldx[y].clone());
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 205 */       int[][] fieldx = (int[][])null;
/* 206 */       int[][] ofieldx = (int[][])null;
/* 207 */       for (int x = 0; x < this.width; x++)
/*     */       {
/* 209 */         fieldx = this.field[x];
/* 210 */         ofieldx = values.field[x];
/* 211 */         for (int y = 0; y < this.height; y++) {
/* 212 */           System.arraycopy(ofieldx[y], 0, fieldx[y], 0, this.length);
/*     */         }
/*     */       }
/*     */     }
/* 216 */     return this;
/*     */   }
/*     */ 
/*     */   public IntGrid3D setTo(int[][][] field)
/*     */   {
/* 224 */     if (field == null)
/* 225 */       throw new RuntimeException("IntGrid3D set to null field.");
/* 226 */     int w = field.length;
/* 227 */     int h = 0;
/* 228 */     int l = 0;
/* 229 */     if (w != 0)
/*     */     {
/* 231 */       h = field[0].length;
/* 232 */       if (h != 0) {
/* 233 */         l = field[0][0].length;
/*     */       }
/*     */     }
/* 236 */     for (int i = 0; i < w; i++)
/*     */     {
/* 238 */       if (field[i].length != h)
/* 239 */         throw new RuntimeException("IntGrid3D initialized with a non-rectangular field.");
/* 240 */       for (int j = 0; j < h; j++)
/*     */       {
/* 242 */         if (field[i][j].length != l) {
/* 243 */           throw new RuntimeException("IntGrid3D initialized with a non-rectangular field.");
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 249 */     this.width = w;
/* 250 */     this.height = h;
/* 251 */     this.length = l;
/* 252 */     this.field = new int[w][h][l];
/* 253 */     for (int i = 0; i < w; i++)
/* 254 */       for (int j = 0; j < h; j++)
/*     */       {
/* 256 */         this.field[i][j] = ((int[])(int[])field[i][j].clone());
/*     */       }
/* 258 */     return this;
/*     */   }
/*     */ 
/*     */   public final IntGrid3D upperBound(int toNoMoreThanThisMuch)
/*     */   {
/* 267 */     int[][] fieldx = (int[][])null;
/* 268 */     int[] fieldxy = null;
/* 269 */     int width = this.width;
/* 270 */     int height = this.height;
/* 271 */     int length = this.length;
/* 272 */     for (int x = 0; x < width; x++)
/*     */     {
/* 274 */       fieldx = this.field[x];
/* 275 */       for (int y = 0; y < height; y++)
/*     */       {
/* 277 */         fieldxy = fieldx[y];
/* 278 */         for (int z = 0; z < length; z++)
/* 279 */           if (fieldxy[z] > toNoMoreThanThisMuch)
/* 280 */             fieldxy[z] = toNoMoreThanThisMuch;
/*     */       }
/*     */     }
/* 283 */     return this;
/*     */   }
/*     */ 
/*     */   public final IntGrid3D lowerBound(int toNoLowerThanThisMuch)
/*     */   {
/* 292 */     int[][] fieldx = (int[][])null;
/* 293 */     int[] fieldxy = null;
/* 294 */     int width = this.width;
/* 295 */     int height = this.height;
/* 296 */     int length = this.length;
/* 297 */     for (int x = 0; x < width; x++)
/*     */     {
/* 299 */       fieldx = this.field[x];
/* 300 */       for (int y = 0; y < height; y++)
/*     */       {
/* 302 */         fieldxy = fieldx[y];
/* 303 */         for (int z = 0; z < length; z++)
/* 304 */           if (fieldxy[z] < toNoLowerThanThisMuch)
/* 305 */             fieldxy[z] = toNoLowerThanThisMuch;
/*     */       }
/*     */     }
/* 308 */     return this;
/*     */   }
/*     */ 
/*     */   public final IntGrid3D add(int withThisMuch)
/*     */   {
/* 316 */     if (withThisMuch == 0.0D) return this;
/* 317 */     int[][] fieldx = (int[][])null;
/* 318 */     int[] fieldxy = null;
/* 319 */     int width = this.width;
/* 320 */     int height = this.height;
/* 321 */     int length = this.length;
/* 322 */     for (int x = 0; x < width; x++)
/*     */     {
/* 324 */       fieldx = this.field[x];
/* 325 */       for (int y = 0; y < height; y++)
/*     */       {
/* 327 */         fieldxy = fieldx[y];
/* 328 */         for (int z = 0; z < length; z++)
/* 329 */           fieldxy[z] += withThisMuch;
/*     */       }
/*     */     }
/* 332 */     return this;
/*     */   }
/*     */ 
/*     */   public final IntGrid3D add(IntGrid3D withThis)
/*     */   {
/* 340 */     checkBounds(withThis);
/* 341 */     int[][] fieldx = (int[][])null;
/* 342 */     int[] fieldxy = null;
/* 343 */     int[][][] ofield = withThis.field;
/* 344 */     int[][] ofieldx = (int[][])null;
/* 345 */     int[] ofieldxy = null;
/* 346 */     int width = this.width;
/* 347 */     int height = this.height;
/* 348 */     int length = this.length;
/*     */ 
/* 350 */     for (int x = 0; x < width; x++)
/*     */     {
/* 352 */       fieldx = this.field[x];
/* 353 */       ofieldx = ofield[x];
/* 354 */       for (int y = 0; y < height; y++)
/*     */       {
/* 356 */         fieldxy = fieldx[y];
/* 357 */         ofieldxy = ofieldx[y];
/* 358 */         for (int z = 0; z < length; z++)
/* 359 */           fieldxy[z] += ofieldxy[z];
/*     */       }
/*     */     }
/* 362 */     return this;
/*     */   }
/*     */ 
/*     */   public final IntGrid3D multiply(int byThisMuch)
/*     */   {
/* 370 */     if (byThisMuch == 1.0D) return this;
/* 371 */     int[][] fieldx = (int[][])null;
/* 372 */     int[] fieldxy = null;
/* 373 */     int width = this.width;
/* 374 */     int height = this.height;
/* 375 */     int length = this.length;
/* 376 */     for (int x = 0; x < width; x++)
/*     */     {
/* 378 */       fieldx = this.field[x];
/* 379 */       for (int y = 0; y < height; y++)
/*     */       {
/* 381 */         fieldxy = fieldx[y];
/* 382 */         for (int z = 0; z < length; z++)
/* 383 */           fieldxy[z] *= byThisMuch;
/*     */       }
/*     */     }
/* 386 */     return this;
/*     */   }
/*     */ 
/*     */   public final IntGrid3D multiply(IntGrid3D withThis)
/*     */   {
/* 394 */     checkBounds(withThis);
/* 395 */     int[][] fieldx = (int[][])null;
/* 396 */     int[] fieldxy = null;
/* 397 */     int[][][] ofield = withThis.field;
/* 398 */     int[][] ofieldx = (int[][])null;
/* 399 */     int[] ofieldxy = null;
/* 400 */     int width = this.width;
/* 401 */     int height = this.height;
/* 402 */     int length = this.length;
/* 403 */     for (int x = 0; x < width; x++)
/*     */     {
/* 405 */       fieldx = this.field[x];
/* 406 */       ofieldx = ofield[x];
/* 407 */       for (int y = 0; y < height; y++)
/*     */       {
/* 409 */         fieldxy = fieldx[y];
/* 410 */         ofieldxy = ofieldx[y];
/* 411 */         for (int z = 0; z < length; z++)
/* 412 */           fieldxy[z] *= ofieldxy[z];
/*     */       }
/*     */     }
/* 415 */     return this;
/*     */   }
/*     */ 
/*     */   public final void replaceAll(int from, int to)
/*     */   {
/* 428 */     int width = this.width;
/* 429 */     int height = this.height;
/* 430 */     int length = this.length;
/* 431 */     int[][] fieldx = (int[][])null;
/* 432 */     int[] fieldxy = null;
/* 433 */     for (int x = 0; x < width; x++)
/*     */     {
/* 435 */       fieldx = this.field[x];
/* 436 */       for (int y = 0; y < height; y++)
/*     */       {
/* 438 */         fieldxy = fieldx[y];
/* 439 */         for (int z = 0; z < length; z++)
/*     */         {
/* 441 */           if (fieldxy[z] == from)
/* 442 */             fieldxy[z] = to;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsMaxDistance(int x, int y, int z, int dist, boolean toroidal, IntBag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 481 */     getMooreNeighbors(x, y, z, dist, toroidal ? 2 : 0, true, result, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public IntBag getMooreNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, IntBag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 512 */     if (xPos == null)
/* 513 */       xPos = new IntBag();
/* 514 */     if (yPos == null)
/* 515 */       yPos = new IntBag();
/* 516 */     if (zPos == null) {
/* 517 */       zPos = new IntBag();
/*     */     }
/* 519 */     getMooreLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/* 520 */     return getObjectsAtLocations(xPos, yPos, zPos, result);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsHamiltonianDistance(int x, int y, int z, int dist, boolean toroidal, IntBag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 558 */     getVonNeumannNeighbors(x, y, z, dist, toroidal ? 2 : 0, true, result, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public IntBag getVonNeumannNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, IntBag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 591 */     if (xPos == null)
/* 592 */       xPos = new IntBag();
/* 593 */     if (yPos == null)
/* 594 */       yPos = new IntBag();
/* 595 */     if (zPos == null) {
/* 596 */       zPos = new IntBag();
/*     */     }
/* 598 */     getVonNeumannLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/* 599 */     return getObjectsAtLocations(xPos, yPos, zPos, result);
/*     */   }
/*     */ 
/*     */   public IntBag getRadialNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, IntBag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 606 */     return getRadialNeighbors(x, y, z, dist, mode, includeOrigin, 1026, true, result, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public IntBag getRadialNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, IntBag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 611 */     if (xPos == null)
/* 612 */       xPos = new IntBag();
/* 613 */     if (yPos == null)
/* 614 */       yPos = new IntBag();
/* 615 */     if (zPos == null) {
/* 616 */       zPos = new IntBag();
/*     */     }
/* 618 */     getRadialLocations(x, y, z, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos, zPos);
/* 619 */     return getObjectsAtLocations(xPos, yPos, zPos, result);
/*     */   }
/*     */ 
/*     */   void reduceObjectsAtLocations(IntBag xPos, IntBag yPos, IntBag zPos, IntBag result)
/*     */   {
/* 629 */     if (result == null) result = new IntBag(); else {
/* 630 */       result.clear();
/*     */     }
/* 632 */     for (int i = 0; i < xPos.numObjs; i++)
/*     */     {
/* 634 */       assert (LocationLog.it(this, new Int3D(xPos.objs[i], yPos.objs[i], zPos.objs[i])));
/* 635 */       int val = this.field[xPos.objs[i]][yPos.objs[i]][zPos.objs[i]];
/* 636 */       result.add(val);
/*     */     }
/*     */   }
/*     */ 
/*     */   IntBag getObjectsAtLocations(IntBag xPos, IntBag yPos, IntBag zPos, IntBag result)
/*     */   {
/* 645 */     if (result == null) result = new IntBag(); else {
/* 646 */       result.clear();
/*     */     }
/* 648 */     for (int i = 0; i < xPos.numObjs; i++)
/*     */     {
/* 650 */       assert (LocationLog.it(this, new Int3D(xPos.objs[i], yPos.objs[i], zPos.objs[i])));
/* 651 */       int val = this.field[xPos.objs[i]][yPos.objs[i]][zPos.objs[i]];
/* 652 */       result.add(val);
/*     */     }
/* 654 */     return result;
/*     */   }
/*     */ 
/*     */   public IntBag getMooreNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 676 */     return getMooreNeighbors(x, y, z, dist, mode, includeOrigin, null, null, null, null);
/*     */   }
/*     */ 
/*     */   public IntBag getVonNeumannNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 700 */     return getVonNeumannNeighbors(x, y, z, dist, mode, includeOrigin, null, null, null, null);
/*     */   }
/*     */ 
/*     */   public IntBag getRadialNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 709 */     return getRadialNeighbors(x, y, z, dist, mode, includeOrigin, null, null, null, null);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.grid.IntGrid3D
 * JD-Core Version:    0.6.2
 */