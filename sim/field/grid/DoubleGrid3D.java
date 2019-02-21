/*     */ package sim.field.grid;
/*     */ 
/*     */ import sim.util.DoubleBag;
/*     */ import sim.util.Int3D;
/*     */ import sim.util.IntBag;
/*     */ import sim.util.LocationLog;
/*     */ 
/*     */ public class DoubleGrid3D extends AbstractGrid3D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public double[][][] field;
/*     */ 
/*     */   public DoubleGrid3D(int width, int height, int length)
/*     */   {
/*  30 */     this.width = width;
/*  31 */     this.height = height;
/*  32 */     this.length = length;
/*  33 */     this.field = new double[width][height][length];
/*     */   }
/*     */ 
/*     */   public DoubleGrid3D(int width, int height, int length, double initialValue)
/*     */   {
/*  38 */     this(width, height, length);
/*  39 */     setTo(initialValue);
/*     */   }
/*     */ 
/*     */   public DoubleGrid3D(DoubleGrid3D values)
/*     */   {
/*  45 */     setTo(values);
/*     */   }
/*     */ 
/*     */   public DoubleGrid3D(double[][][] values)
/*     */   {
/*  50 */     setTo(values);
/*     */   }
/*     */ 
/*     */   public final double set(int x, int y, int z, double val)
/*     */   {
/*  56 */     double returnval = this.field[x][y][z];
/*  57 */     this.field[x][y][z] = val;
/*  58 */     return returnval;
/*     */   }
/*     */ 
/*     */   public final double get(int x, int y, int z)
/*     */   {
/*  64 */     return this.field[x][y][z];
/*     */   }
/*     */ 
/*     */   public final double[] toArray()
/*     */   {
/*  72 */     double[][][] field = this.field;
/*  73 */     double[][] fieldx = (double[][])null;
/*  74 */     double[] fieldxy = null;
/*  75 */     int width = this.width;
/*  76 */     int height = this.height;
/*  77 */     int length = this.length;
/*  78 */     double[] vals = new double[width * height * length];
/*  79 */     int i = 0;
/*  80 */     for (int x = 0; x < width; x++)
/*     */     {
/*  82 */       fieldx = field[x];
/*  83 */       for (int y = 0; y < height; y++)
/*     */       {
/*  85 */         fieldxy = fieldx[y];
/*  86 */         for (int z = 0; z < length; z++)
/*     */         {
/*  88 */           vals[(i++)] = fieldxy[z];
/*     */         }
/*     */       }
/*     */     }
/*  92 */     return vals;
/*     */   }
/*     */ 
/*     */   public final double max()
/*     */   {
/*  98 */     double max = (-1.0D / 0.0D);
/*  99 */     double[][] fieldx = (double[][])null;
/* 100 */     double[] fieldxy = null;
/* 101 */     int width = this.width;
/* 102 */     int height = this.height;
/* 103 */     int length = this.length;
/* 104 */     for (int x = 0; x < width; x++)
/*     */     {
/* 106 */       fieldx = this.field[x];
/* 107 */       for (int y = 0; y < height; y++)
/*     */       {
/* 109 */         fieldxy = fieldx[y];
/* 110 */         for (int z = 0; z < length; z++)
/* 111 */           if (max < fieldxy[z]) max = fieldxy[z];
/*     */       }
/*     */     }
/* 114 */     return max;
/*     */   }
/*     */ 
/*     */   public final double min()
/*     */   {
/* 120 */     double min = (1.0D / 0.0D);
/* 121 */     double[][] fieldx = (double[][])null;
/* 122 */     double[] fieldxy = null;
/* 123 */     int width = this.width;
/* 124 */     int height = this.height;
/* 125 */     int length = this.length;
/* 126 */     for (int x = 0; x < width; x++)
/*     */     {
/* 128 */       fieldx = this.field[x];
/* 129 */       for (int y = 0; y < height; y++)
/*     */       {
/* 131 */         fieldxy = fieldx[y];
/* 132 */         for (int z = 0; z < length; z++)
/* 133 */           if (min > fieldxy[z]) min = fieldxy[z];
/*     */       }
/*     */     }
/* 136 */     return min;
/*     */   }
/*     */ 
/*     */   public final double mean()
/*     */   {
/* 142 */     long count = 0L;
/* 143 */     double mean = 0.0D;
/* 144 */     double[][] fieldx = (double[][])null;
/* 145 */     double[] fieldxy = null;
/* 146 */     int width = this.width;
/* 147 */     int height = this.height;
/* 148 */     int length = this.length;
/* 149 */     for (int x = 0; x < width; x++)
/*     */     {
/* 151 */       fieldx = this.field[x];
/* 152 */       for (int y = 0; y < height; y++)
/*     */       {
/* 154 */         fieldxy = fieldx[y];
/* 155 */         for (int z = 0; z < length; z++) {
/* 156 */           mean += fieldxy[z]; count += 1L;
/*     */         }
/*     */       }
/*     */     }
/* 159 */     return count == 0L ? 0.0D : mean / count;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid3D setTo(double thisMuch)
/*     */   {
/* 165 */     double[][] fieldx = (double[][])null;
/* 166 */     double[] fieldxy = null;
/* 167 */     int width = this.width;
/* 168 */     int height = this.height;
/* 169 */     int length = this.length;
/* 170 */     for (int x = 0; x < width; x++)
/*     */     {
/* 172 */       fieldx = this.field[x];
/* 173 */       for (int y = 0; y < height; y++)
/*     */       {
/* 175 */         fieldxy = fieldx[y];
/* 176 */         for (int z = 0; z < length; z++)
/* 177 */           fieldxy[z] = thisMuch;
/*     */       }
/*     */     }
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid3D setTo(DoubleGrid3D values)
/*     */   {
/* 188 */     if ((this.width != values.width) || (this.height != values.height) || (this.length != values.length))
/*     */     {
/* 190 */       int width = this.width = values.width;
/* 191 */       int height = this.height = values.height;
/* 192 */       this.length = values.length;
/* 193 */       this.field = new double[width][height];
/* 194 */       double[][] fieldx = (double[][])null;
/* 195 */       for (int x = 0; x < width; x++)
/*     */       {
/* 197 */         fieldx = this.field[x];
/* 198 */         for (int y = 0; y < height; y++)
/* 199 */           fieldx[y] = ((double[])(double[])values.field[x][y].clone());
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 204 */       for (int x = 0; x < this.width; x++) {
/* 205 */         for (int y = 0; y < this.height; y++)
/* 206 */           System.arraycopy(values.field[x][y], 0, this.field[x][y], 0, this.length);
/*     */       }
/*     */     }
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */   public DoubleGrid3D setTo(double[][][] field)
/*     */   {
/* 217 */     if (field == null)
/* 218 */       throw new RuntimeException("DoubleGrid3D set to null field.");
/* 219 */     int w = field.length;
/* 220 */     int h = 0;
/* 221 */     int l = 0;
/* 222 */     if (w != 0)
/*     */     {
/* 224 */       h = field[0].length;
/* 225 */       if (h != 0) {
/* 226 */         l = field[0][0].length;
/*     */       }
/*     */     }
/* 229 */     for (int i = 0; i < w; i++)
/*     */     {
/* 231 */       if (field[i].length != h)
/* 232 */         throw new RuntimeException("DoubleGrid3D initialized with a non-rectangular field.");
/* 233 */       for (int j = 0; j < h; j++)
/*     */       {
/* 235 */         if (field[i][j].length != l) {
/* 236 */           throw new RuntimeException("DoubleGrid3D initialized with a non-rectangular field.");
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 242 */     this.width = w;
/* 243 */     this.height = h;
/* 244 */     this.length = l;
/* 245 */     this.field = new double[w][h][l];
/* 246 */     for (int i = 0; i < w; i++)
/* 247 */       for (int j = 0; j < h; j++)
/*     */       {
/* 249 */         this.field[i][j] = ((double[])(double[])field[i][j].clone());
/*     */       }
/* 251 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid3D upperBound(double toNoMoreThanThisMuch)
/*     */   {
/* 261 */     double[][] fieldx = (double[][])null;
/* 262 */     double[] fieldxy = null;
/* 263 */     int width = this.width;
/* 264 */     int height = this.height;
/* 265 */     int length = this.length;
/*     */ 
/* 267 */     for (int x = 0; x < width; x++)
/*     */     {
/* 269 */       fieldx = this.field[x];
/* 270 */       for (int y = 0; y < height; y++)
/*     */       {
/* 272 */         fieldxy = fieldx[y];
/* 273 */         for (int z = 0; z < length; z++)
/* 274 */           if (fieldxy[z] > toNoMoreThanThisMuch)
/* 275 */             fieldxy[z] = toNoMoreThanThisMuch;
/*     */       }
/*     */     }
/* 278 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid3D lowerBound(double toNoLowerThanThisMuch)
/*     */   {
/* 286 */     double[][] fieldx = (double[][])null;
/* 287 */     double[] fieldxy = null;
/* 288 */     int width = this.width;
/* 289 */     int height = this.height;
/* 290 */     int length = this.length;
/* 291 */     for (int x = 0; x < width; x++)
/*     */     {
/* 293 */       fieldx = this.field[x];
/* 294 */       for (int y = 0; y < height; y++)
/*     */       {
/* 296 */         fieldxy = fieldx[y];
/* 297 */         for (int z = 0; z < length; z++)
/* 298 */           if (fieldxy[z] < toNoLowerThanThisMuch)
/* 299 */             fieldxy[z] = toNoLowerThanThisMuch;
/*     */       }
/*     */     }
/* 302 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid3D add(double withThisMuch)
/*     */   {
/* 310 */     if (withThisMuch == 0.0D) return this;
/* 311 */     double[][] fieldx = (double[][])null;
/* 312 */     double[] fieldxy = null;
/* 313 */     int width = this.width;
/* 314 */     int height = this.height;
/* 315 */     int length = this.length;
/*     */ 
/* 317 */     for (int x = 0; x < width; x++)
/*     */     {
/* 319 */       fieldx = this.field[x];
/* 320 */       for (int y = 0; y < height; y++)
/*     */       {
/* 322 */         fieldxy = fieldx[y];
/* 323 */         for (int z = 0; z < length; z++)
/* 324 */           fieldxy[z] += withThisMuch;
/*     */       }
/*     */     }
/* 327 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid3D add(IntGrid3D withThis)
/*     */   {
/* 335 */     checkBounds(withThis);
/* 336 */     int[][][] otherField = withThis.field;
/* 337 */     int[][] ofieldx = (int[][])null;
/* 338 */     int[] ofieldxy = null;
/* 339 */     double[][] fieldx = (double[][])null;
/* 340 */     double[] fieldxy = null;
/* 341 */     int width = this.width;
/* 342 */     int height = this.height;
/* 343 */     int length = this.length;
/*     */ 
/* 345 */     for (int x = 0; x < width; x++)
/*     */     {
/* 347 */       fieldx = this.field[x];
/* 348 */       ofieldx = otherField[x];
/* 349 */       for (int y = 0; y < height; y++)
/*     */       {
/* 351 */         ofieldxy = ofieldx[y];
/* 352 */         fieldxy = fieldx[y];
/* 353 */         for (int z = 0; z < length; z++)
/* 354 */           fieldxy[z] += ofieldxy[z];
/*     */       }
/*     */     }
/* 357 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid3D add(DoubleGrid3D withThis)
/*     */   {
/* 365 */     checkBounds(withThis);
/* 366 */     double[][][] otherField = withThis.field;
/* 367 */     double[][] ofieldx = (double[][])null;
/* 368 */     double[] ofieldxy = null;
/* 369 */     double[][] fieldx = (double[][])null;
/* 370 */     double[] fieldxy = null;
/* 371 */     int width = this.width;
/* 372 */     int height = this.height;
/* 373 */     int length = this.length;
/* 374 */     for (int x = 0; x < width; x++)
/*     */     {
/* 376 */       fieldx = this.field[x];
/* 377 */       ofieldx = otherField[x];
/* 378 */       for (int y = 0; y < height; y++)
/*     */       {
/* 380 */         fieldxy = fieldx[y];
/* 381 */         ofieldxy = ofieldx[y];
/* 382 */         for (int z = 0; z < length; z++)
/* 383 */           fieldxy[z] += ofieldxy[z];
/*     */       }
/*     */     }
/* 386 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid3D multiply(double byThisMuch)
/*     */   {
/* 394 */     if (byThisMuch == 1.0D) return this;
/* 395 */     double[][] fieldx = (double[][])null;
/* 396 */     double[] fieldxy = null;
/* 397 */     int width = this.width;
/* 398 */     int height = this.height;
/* 399 */     int length = this.length;
/* 400 */     for (int x = 0; x < width; x++)
/*     */     {
/* 402 */       fieldx = this.field[x];
/* 403 */       for (int y = 0; y < height; y++)
/*     */       {
/* 406 */         fieldxy = fieldx[y];
/* 407 */         for (int z = 0; z < length; z++)
/* 408 */           fieldxy[z] *= byThisMuch;
/*     */       }
/*     */     }
/* 411 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid3D multiply(IntGrid3D withThis)
/*     */   {
/* 419 */     checkBounds(withThis);
/* 420 */     int[][][] otherField = withThis.field;
/* 421 */     int[][] ofieldx = (int[][])null;
/* 422 */     int[] ofieldxy = null;
/* 423 */     double[][] fieldx = (double[][])null;
/* 424 */     double[] fieldxy = null;
/* 425 */     int width = this.width;
/* 426 */     int height = this.height;
/* 427 */     int length = this.length;
/*     */ 
/* 429 */     for (int x = 0; x < width; x++)
/*     */     {
/* 431 */       fieldx = this.field[x];
/* 432 */       ofieldx = otherField[x];
/* 433 */       for (int y = 0; y < height; y++)
/*     */       {
/* 435 */         ofieldxy = ofieldx[y];
/* 436 */         fieldxy = fieldx[y];
/* 437 */         for (int z = 0; z < length; z++)
/* 438 */           fieldxy[z] *= ofieldxy[z];
/*     */       }
/*     */     }
/* 441 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid3D multiply(DoubleGrid3D withThis)
/*     */   {
/* 449 */     checkBounds(withThis);
/* 450 */     double[][][] otherField = withThis.field;
/* 451 */     double[][] ofieldx = (double[][])null;
/* 452 */     double[] ofieldxy = null;
/* 453 */     double[][] fieldx = (double[][])null;
/* 454 */     double[] fieldxy = null;
/* 455 */     int width = this.width;
/* 456 */     int height = this.height;
/* 457 */     int length = this.length;
/* 458 */     for (int x = 0; x < width; x++)
/*     */     {
/* 460 */       fieldx = this.field[x];
/* 461 */       ofieldx = otherField[x];
/* 462 */       for (int y = 0; y < height; y++)
/*     */       {
/* 464 */         fieldxy = fieldx[y];
/* 465 */         ofieldxy = ofieldx[y];
/* 466 */         for (int z = 0; z < length; z++)
/* 467 */           fieldxy[z] *= ofieldxy[z];
/*     */       }
/*     */     }
/* 470 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid3D floor()
/*     */   {
/* 478 */     double[][] fieldx = (double[][])null;
/* 479 */     double[] fieldxy = null;
/* 480 */     int width = this.width;
/* 481 */     int height = this.height;
/* 482 */     int length = this.length;
/* 483 */     for (int x = 0; x < width; x++)
/*     */     {
/* 485 */       fieldx = this.field[x];
/* 486 */       for (int y = 0; y < height; y++)
/*     */       {
/* 488 */         fieldxy = fieldx[y];
/* 489 */         for (int z = 0; z < length; z++)
/* 490 */           fieldxy[z] = Math.floor(fieldxy[z]);
/*     */       }
/*     */     }
/* 493 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid3D ceiling()
/*     */   {
/* 501 */     double[][] fieldx = (double[][])null;
/* 502 */     double[] fieldxy = null;
/* 503 */     int width = this.width;
/* 504 */     int height = this.height;
/* 505 */     int length = this.length;
/* 506 */     for (int x = 0; x < width; x++)
/*     */     {
/* 508 */       fieldx = this.field[x];
/* 509 */       for (int y = 0; y < height; y++)
/*     */       {
/* 511 */         fieldxy = fieldx[y];
/* 512 */         for (int z = 0; z < length; z++)
/* 513 */           fieldxy[z] = Math.ceil(fieldxy[z]);
/*     */       }
/*     */     }
/* 516 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid3D truncate()
/*     */   {
/* 524 */     double[][] fieldx = (double[][])null;
/* 525 */     double[] fieldxy = null;
/* 526 */     int width = this.width;
/* 527 */     int height = this.height;
/* 528 */     int length = this.length;
/* 529 */     for (int x = 0; x < width; x++)
/*     */     {
/* 531 */       fieldx = this.field[x];
/* 532 */       for (int y = 0; y < height; y++)
/*     */       {
/* 534 */         fieldxy = fieldx[y];
/* 535 */         for (int z = 0; z < length; z++) {
/* 536 */           fieldxy[z] = ((int)fieldxy[z]);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 543 */     return this;
/*     */   }
/*     */ 
/*     */   public final DoubleGrid3D rint()
/*     */   {
/* 553 */     double[][] fieldx = (double[][])null;
/* 554 */     double[] fieldxy = null;
/* 555 */     int width = this.width;
/* 556 */     int height = this.height;
/* 557 */     int length = this.length;
/* 558 */     for (int x = 0; x < width; x++)
/*     */     {
/* 560 */       fieldx = this.field[x];
/* 561 */       for (int y = 0; y < height; y++)
/*     */       {
/* 564 */         fieldxy = fieldx[y];
/* 565 */         for (int z = 0; z < length; z++)
/* 566 */           fieldxy[z] = Math.rint(fieldxy[z]);
/*     */       }
/*     */     }
/* 569 */     return this;
/*     */   }
/*     */ 
/*     */   public final void replaceAll(double from, double to)
/*     */   {
/* 581 */     int width = this.width;
/* 582 */     int height = this.height;
/* 583 */     int length = this.length;
/* 584 */     double[][] fieldx = (double[][])null;
/* 585 */     double[] fieldxy = null;
/* 586 */     for (int x = 0; x < width; x++)
/*     */     {
/* 588 */       fieldx = this.field[x];
/* 589 */       for (int y = 0; y < height; y++)
/*     */       {
/* 591 */         fieldxy = fieldx[y];
/* 592 */         for (int z = 0; z < length; z++)
/*     */         {
/* 594 */           if (fieldxy[z] == from)
/* 595 */             fieldxy[z] = to;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsMaxDistance(int x, int y, int z, int dist, boolean toroidal, DoubleBag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 633 */     getMooreNeighbors(x, y, z, dist, toroidal ? 2 : 0, true, result, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public DoubleBag getMooreNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, DoubleBag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 664 */     if (xPos == null)
/* 665 */       xPos = new IntBag();
/* 666 */     if (yPos == null)
/* 667 */       yPos = new IntBag();
/* 668 */     if (zPos == null) {
/* 669 */       zPos = new IntBag();
/*     */     }
/* 671 */     getMooreLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/* 672 */     return getObjectsAtLocations(xPos, yPos, zPos, result);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getNeighborsHamiltonianDistance(int x, int y, int z, int dist, boolean toroidal, DoubleBag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 712 */     getVonNeumannNeighbors(x, y, z, dist, toroidal ? 2 : 0, true, result, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public DoubleBag getVonNeumannNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, DoubleBag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 745 */     if (xPos == null)
/* 746 */       xPos = new IntBag();
/* 747 */     if (yPos == null)
/* 748 */       yPos = new IntBag();
/* 749 */     if (zPos == null) {
/* 750 */       zPos = new IntBag();
/*     */     }
/* 752 */     getVonNeumannLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/* 753 */     return getObjectsAtLocations(xPos, yPos, zPos, result);
/*     */   }
/*     */ 
/*     */   public DoubleBag getRadialNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, DoubleBag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 759 */     return getRadialNeighbors(x, y, z, dist, mode, includeOrigin, 1026, true, result, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public DoubleBag getRadialNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, DoubleBag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 764 */     if (xPos == null)
/* 765 */       xPos = new IntBag();
/* 766 */     if (yPos == null)
/* 767 */       yPos = new IntBag();
/* 768 */     if (zPos == null) {
/* 769 */       zPos = new IntBag();
/*     */     }
/* 771 */     getRadialLocations(x, y, z, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos, zPos);
/* 772 */     return getObjectsAtLocations(xPos, yPos, zPos, result);
/*     */   }
/*     */ 
/*     */   void reduceObjectsAtLocations(IntBag xPos, IntBag yPos, IntBag zPos, DoubleBag result)
/*     */   {
/* 782 */     if (result == null) result = new DoubleBag(); else {
/* 783 */       result.clear();
/*     */     }
/* 785 */     for (int i = 0; i < xPos.numObjs; i++)
/*     */     {
/* 787 */       assert (LocationLog.it(this, new Int3D(xPos.objs[i], yPos.objs[i], zPos.objs[i])));
/* 788 */       double val = this.field[xPos.objs[i]][yPos.objs[i]][zPos.objs[i]];
/* 789 */       result.add(val);
/*     */     }
/*     */   }
/*     */ 
/*     */   DoubleBag getObjectsAtLocations(IntBag xPos, IntBag yPos, IntBag zPos, DoubleBag result)
/*     */   {
/* 798 */     if (result == null) result = new DoubleBag(); else {
/* 799 */       result.clear();
/*     */     }
/* 801 */     for (int i = 0; i < xPos.numObjs; i++)
/*     */     {
/* 803 */       assert (LocationLog.it(this, new Int3D(xPos.objs[i], yPos.objs[i], zPos.objs[i])));
/* 804 */       double val = this.field[xPos.objs[i]][yPos.objs[i]][zPos.objs[i]];
/* 805 */       result.add(val);
/*     */     }
/* 807 */     return result;
/*     */   }
/*     */ 
/*     */   public DoubleBag getMooreNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 829 */     return getMooreNeighbors(x, y, z, dist, mode, includeOrigin, null, null, null, null);
/*     */   }
/*     */ 
/*     */   public DoubleBag getVonNeumannNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 853 */     return getVonNeumannNeighbors(x, y, z, dist, mode, includeOrigin, null, null, null, null);
/*     */   }
/*     */ 
/*     */   public DoubleBag getRadialNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 862 */     return getRadialNeighbors(x, y, z, dist, mode, includeOrigin, null, null, null, null);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.grid.DoubleGrid3D
 * JD-Core Version:    0.6.2
 */