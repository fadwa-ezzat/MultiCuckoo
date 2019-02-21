/*     */ package sim.field.grid;
/*     */ 
/*     */ import sim.util.Bag;
/*     */ import sim.util.Int3D;
/*     */ import sim.util.IntBag;
/*     */ import sim.util.LocationLog;
/*     */ 
/*     */ public class ObjectGrid3D extends AbstractGrid3D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public Object[][][] field;
/*     */ 
/*     */   public ObjectGrid3D(int width, int height, int length)
/*     */   {
/*  33 */     this.width = width;
/*  34 */     this.height = height;
/*  35 */     this.length = length;
/*  36 */     this.field = new Object[width][height][length];
/*     */   }
/*     */ 
/*     */   public ObjectGrid3D(int width, int height, int length, Object initialValue)
/*     */   {
/*  41 */     this(width, height, length);
/*  42 */     setTo(initialValue);
/*     */   }
/*     */ 
/*     */   public ObjectGrid3D(ObjectGrid3D values)
/*     */   {
/*  47 */     setTo(values);
/*     */   }
/*     */ 
/*     */   public ObjectGrid3D(Object[][][] values)
/*     */   {
/*  52 */     setTo(values);
/*     */   }
/*     */ 
/*     */   public final void set(int x, int y, int z, Object val)
/*     */   {
/*  57 */     this.field[x][y][z] = val;
/*     */   }
/*     */ 
/*     */   public final Object get(int x, int y, int z)
/*     */   {
/*  62 */     return this.field[x][y][z];
/*     */   }
/*     */ 
/*     */   public final ObjectGrid3D setTo(Object thisObj)
/*     */   {
/*  68 */     Object[][][] field = this.field;
/*  69 */     Object[][] fieldx = (Object[][])null;
/*  70 */     Object[] fieldxy = null;
/*  71 */     int width = this.width;
/*  72 */     int height = this.height;
/*  73 */     int length = this.length;
/*  74 */     for (int x = 0; x < width; x++)
/*     */     {
/*  76 */       fieldx = field[x];
/*  77 */       for (int y = 0; y < height; y++)
/*     */       {
/*  79 */         fieldxy = fieldx[y];
/*  80 */         for (int z = 0; z < length; z++)
/*  81 */           fieldxy[z] = thisObj;
/*     */       }
/*     */     }
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */   public final void replaceAll(Object from, Object to)
/*     */   {
/*  97 */     replaceAll(from, to, false);
/*     */   }
/*     */ 
/*     */   public final void replaceAll(Object from, Object to, boolean onlyIfSameObject)
/*     */   {
/* 112 */     int width = this.width;
/* 113 */     int height = this.height;
/* 114 */     int length = this.length;
/* 115 */     Object[][] fieldx = (Object[][])null;
/* 116 */     Object[] fieldxy = null;
/* 117 */     for (int x = 0; x < width; x++)
/*     */     {
/* 119 */       fieldx = this.field[x];
/* 120 */       for (int y = 0; y < height; y++)
/*     */       {
/* 122 */         fieldxy = fieldx[y];
/* 123 */         for (int z = 0; z < length; z++)
/*     */         {
/* 125 */           Object obj = fieldxy[z];
/* 126 */           if (((obj == null) && (from == null)) || ((onlyIfSameObject) && (obj == from)) || ((!onlyIfSameObject) && (obj.equals(from))))
/*     */           {
/* 129 */             fieldxy[z] = to;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public final Object[] toArray()
/*     */   {
/* 139 */     Object[][][] field = this.field;
/* 140 */     Object[][] fieldx = (Object[][])null;
/* 141 */     Object[] fieldxy = null;
/* 142 */     int width = this.width;
/* 143 */     int height = this.height;
/* 144 */     int length = this.length;
/* 145 */     Object[] vals = new Object[width * height * length];
/* 146 */     int i = 0;
/* 147 */     for (int x = 0; x < width; x++)
/*     */     {
/* 149 */       fieldx = field[x];
/* 150 */       for (int y = 0; y < height; y++)
/*     */       {
/* 152 */         fieldxy = fieldx[y];
/* 153 */         for (int z = 0; z < length; z++)
/*     */         {
/* 155 */           vals[(i++)] = fieldxy[z];
/*     */         }
/*     */       }
/*     */     }
/* 159 */     return vals;
/*     */   }
/*     */ 
/*     */   public final Bag elements()
/*     */   {
/* 167 */     Bag bag = new Bag();
/* 168 */     Object[][][] field = this.field;
/* 169 */     Object[][] fieldx = (Object[][])null;
/* 170 */     Object[] fieldxy = null;
/* 171 */     int width = this.width;
/* 172 */     int height = this.height;
/* 173 */     int length = this.length;
/* 174 */     for (int x = 0; x < width; x++)
/*     */     {
/* 176 */       fieldx = field[x];
/* 177 */       for (int y = 0; y < height; y++)
/*     */       {
/* 179 */         fieldxy = fieldx[y];
/* 180 */         for (int z = 0; z < length; z++)
/*     */         {
/* 182 */           if (fieldxy[z] != null)
/* 183 */             bag.add(fieldxy[z]);
/*     */         }
/*     */       }
/*     */     }
/* 187 */     return bag;
/*     */   }
/*     */ 
/*     */   public final Bag clear()
/*     */   {
/* 194 */     Bag bag = new Bag();
/* 195 */     Object[][][] field = this.field;
/* 196 */     Object[][] fieldx = (Object[][])null;
/* 197 */     Object[] fieldxy = null;
/* 198 */     int width = this.width;
/* 199 */     int height = this.height;
/* 200 */     int length = this.length;
/* 201 */     for (int x = 0; x < width; x++)
/*     */     {
/* 203 */       fieldx = field[x];
/* 204 */       for (int y = 0; y < height; y++)
/*     */       {
/* 206 */         fieldxy = fieldx[y];
/* 207 */         for (int z = 0; z < length; z++)
/*     */         {
/* 209 */           if (fieldxy[z] != null)
/* 210 */             bag.add(fieldxy[z]);
/* 211 */           fieldxy[z] = null;
/*     */         }
/*     */       }
/*     */     }
/* 215 */     return bag;
/*     */   }
/*     */ 
/*     */   public final ObjectGrid3D setTo(ObjectGrid3D values)
/*     */   {
/* 221 */     if ((this.width != values.width) || (this.height != values.height))
/*     */     {
/* 223 */       int width = this.width = values.width;
/* 224 */       int height = this.height = values.height;
/* 225 */       this.length = values.length;
/* 226 */       Object[][][] field = this.field = new Object[width][height];
/* 227 */       Object[][][] ofield = values.field;
/* 228 */       Object[][] fieldx = (Object[][])null;
/* 229 */       Object[][] ofieldx = (Object[][])null;
/* 230 */       for (int x = 0; x < width; x++)
/*     */       {
/* 232 */         fieldx = field[x];
/* 233 */         ofieldx = ofield[x];
/* 234 */         for (int y = 0; y < height; y++)
/* 235 */           fieldx[y] = ((Object[])(Object[])ofieldx[y].clone());
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 240 */       Object[][][] field = this.field;
/* 241 */       Object[][][] ofield = values.field;
/* 242 */       Object[][] fieldx = (Object[][])null;
/* 243 */       Object[][] ofieldx = (Object[][])null;
/* 244 */       for (int x = 0; x < this.width; x++)
/*     */       {
/* 246 */         fieldx = field[x];
/* 247 */         ofieldx = ofield[x];
/* 248 */         for (int y = 0; y < this.height; y++)
/* 249 */           System.arraycopy(ofieldx[y], 0, fieldx[y], 0, this.length);
/*     */       }
/*     */     }
/* 252 */     return this;
/*     */   }
/*     */ 
/*     */   public ObjectGrid3D setTo(Object[][][] field)
/*     */   {
/* 262 */     if (field == null)
/* 263 */       throw new RuntimeException("ObjectGrid3D set to null field.");
/* 264 */     int w = field.length;
/* 265 */     int h = 0;
/* 266 */     int l = 0;
/* 267 */     if (w != 0)
/*     */     {
/* 269 */       h = field[0].length;
/* 270 */       if (h != 0) {
/* 271 */         l = field[0][0].length;
/*     */       }
/*     */     }
/* 274 */     for (int i = 0; i < w; i++)
/*     */     {
/* 276 */       if (field[i].length != h)
/* 277 */         throw new RuntimeException("ObjectGrid3D initialized with a non-rectangular field.");
/* 278 */       for (int j = 0; j < h; j++)
/*     */       {
/* 280 */         if (field[i][j].length != l) {
/* 281 */           throw new RuntimeException("ObjectGrid3D initialized with a non-rectangular field.");
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 287 */     this.width = w;
/* 288 */     this.height = h;
/* 289 */     this.length = l;
/* 290 */     this.field = new Object[w][h][l];
/* 291 */     for (int i = 0; i < w; i++)
/* 292 */       for (int j = 0; j < h; j++)
/*     */       {
/* 294 */         this.field[i][j] = ((Object[])(Object[])field[i][j].clone());
/*     */       }
/* 296 */     return this;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getNeighborsMaxDistance(int x, int y, int z, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 335 */     return getMooreNeighbors(x, y, z, dist, toroidal ? 2 : 0, true, result, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public Bag getMooreNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 368 */     if (xPos == null)
/* 369 */       xPos = new IntBag();
/* 370 */     if (yPos == null)
/* 371 */       yPos = new IntBag();
/* 372 */     if (zPos == null) {
/* 373 */       zPos = new IntBag();
/*     */     }
/* 375 */     getMooreLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/* 376 */     return getObjectsAtLocations(xPos, yPos, zPos, result);
/*     */   }
/*     */ 
/*     */   public Bag getMooreNeighborsAndLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 411 */     if (xPos == null)
/* 412 */       xPos = new IntBag();
/* 413 */     if (yPos == null)
/* 414 */       yPos = new IntBag();
/* 415 */     if (zPos == null) {
/* 416 */       zPos = new IntBag();
/*     */     }
/* 418 */     getMooreLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/* 419 */     reduceObjectsAtLocations(xPos, yPos, zPos, result);
/* 420 */     return result;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Bag getNeighborsHamiltonianDistance(int x, int y, int z, int dist, boolean toroidal, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 460 */     return getVonNeumannNeighbors(x, y, z, dist, toroidal ? 2 : 0, true, result, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public Bag getVonNeumannNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 495 */     if (xPos == null)
/* 496 */       xPos = new IntBag();
/* 497 */     if (yPos == null)
/* 498 */       yPos = new IntBag();
/* 499 */     if (zPos == null) {
/* 500 */       zPos = new IntBag();
/*     */     }
/* 502 */     getVonNeumannLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/* 503 */     return getObjectsAtLocations(xPos, yPos, zPos, result);
/*     */   }
/*     */ 
/*     */   public Bag getVonNeumannNeighborsAndLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 540 */     if (xPos == null)
/* 541 */       xPos = new IntBag();
/* 542 */     if (yPos == null)
/* 543 */       yPos = new IntBag();
/* 544 */     if (zPos == null) {
/* 545 */       zPos = new IntBag();
/*     */     }
/* 547 */     getVonNeumannLocations(x, y, z, dist, mode, includeOrigin, xPos, yPos, zPos);
/* 548 */     reduceObjectsAtLocations(xPos, yPos, zPos, result);
/* 549 */     return result;
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 554 */     return getRadialNeighbors(x, y, z, dist, mode, includeOrigin, 1026, true, result, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighborsAndLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 560 */     return getRadialNeighborsAndLocations(x, y, z, dist, mode, includeOrigin, 1026, true, result, xPos, yPos, zPos);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 566 */     if (xPos == null)
/* 567 */       xPos = new IntBag();
/* 568 */     if (yPos == null)
/* 569 */       yPos = new IntBag();
/* 570 */     if (zPos == null) {
/* 571 */       zPos = new IntBag();
/*     */     }
/* 573 */     getRadialLocations(x, y, z, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos, zPos);
/* 574 */     return getObjectsAtLocations(xPos, yPos, zPos, result);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighborsAndLocations(int x, int y, int z, int dist, int mode, boolean includeOrigin, int measurementRule, boolean closed, Bag result, IntBag xPos, IntBag yPos, IntBag zPos)
/*     */   {
/* 579 */     if (xPos == null)
/* 580 */       xPos = new IntBag();
/* 581 */     if (yPos == null)
/* 582 */       yPos = new IntBag();
/* 583 */     if (zPos == null) {
/* 584 */       zPos = new IntBag();
/*     */     }
/* 586 */     getRadialLocations(x, y, z, dist, mode, includeOrigin, measurementRule, closed, xPos, yPos, zPos);
/* 587 */     reduceObjectsAtLocations(xPos, yPos, zPos, result);
/* 588 */     return result;
/*     */   }
/*     */ 
/*     */   void reduceObjectsAtLocations(IntBag xPos, IntBag yPos, IntBag zPos, Bag result)
/*     */   {
/* 595 */     if (result == null) result = new Bag(); else {
/* 596 */       result.clear();
/*     */     }
/* 598 */     for (int i = 0; i < xPos.numObjs; i++)
/*     */     {
/* 600 */       assert (LocationLog.it(this, new Int3D(xPos.objs[i], yPos.objs[i], zPos.objs[i])));
/* 601 */       Object val = this.field[xPos.objs[i]][yPos.objs[i]][zPos.objs[i]];
/* 602 */       if (val != null) { result.add(val);
/*     */       } else
/*     */       {
/* 605 */         xPos.remove(i);
/* 606 */         yPos.remove(i);
/* 607 */         i--;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   Bag getObjectsAtLocations(IntBag xPos, IntBag yPos, IntBag zPos, Bag result)
/*     */   {
/* 617 */     if (result == null) result = new Bag(); else {
/* 618 */       result.clear();
/*     */     }
/* 620 */     for (int i = 0; i < xPos.numObjs; i++)
/*     */     {
/* 622 */       assert (LocationLog.it(this, new Int3D(xPos.objs[i], yPos.objs[i], zPos.objs[i])));
/* 623 */       Object val = this.field[xPos.objs[i]][yPos.objs[i]][zPos.objs[i]];
/* 624 */       if (val != null) result.add(val);
/*     */     }
/* 626 */     return result;
/*     */   }
/*     */ 
/*     */   public Bag getMooreNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 647 */     return getMooreNeighbors(x, y, z, dist, mode, includeOrigin, null, null, null, null);
/*     */   }
/*     */ 
/*     */   public Bag getVonNeumannNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 671 */     return getVonNeumannNeighbors(x, y, z, dist, mode, includeOrigin, null, null, null, null);
/*     */   }
/*     */ 
/*     */   public Bag getRadialNeighbors(int x, int y, int z, int dist, int mode, boolean includeOrigin)
/*     */   {
/* 680 */     return getRadialNeighbors(x, y, z, dist, mode, includeOrigin, null, null, null, null);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.grid.ObjectGrid3D
 * JD-Core Version:    0.6.2
 */