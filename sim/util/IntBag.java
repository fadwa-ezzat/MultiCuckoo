/*     */ package sim.util;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class IntBag
/*     */   implements Serializable, Cloneable, Indexed
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public int[] objs;
/*     */   public int numObjs;
/*     */ 
/*     */   public IntBag(int capacity)
/*     */   {
/*  33 */     this.numObjs = 0; this.objs = new int[capacity];
/*     */   }
/*  35 */   public IntBag() { this.numObjs = 0; this.objs = new int[1];
/*     */   }
/*     */ 
/*     */   public IntBag(IntBag other)
/*     */   {
/*  42 */     if (other == null) { this.numObjs = 0; this.objs = new int[1];
/*     */     } else
/*     */     {
/*  45 */       this.numObjs = other.numObjs;
/*  46 */       this.objs = new int[this.numObjs];
/*  47 */       System.arraycopy(other.objs, 0, this.objs, 0, this.numObjs);
/*     */     }
/*     */   }
/*     */ 
/*     */   public IntBag(int[] other)
/*     */   {
/*  53 */     this(); if (other != null) addAll(other); 
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/*  57 */     return this.numObjs;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  62 */     return this.numObjs <= 0;
/*     */   }
/*     */   public boolean addAll(int[] other) {
/*  65 */     return addAll(this.numObjs, other);
/*     */   }
/*     */ 
/*     */   public boolean addAll(int index, int[] other)
/*     */   {
/*  72 */     if (index > this.numObjs) {
/*  73 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/*  75 */     if (other.length == 0) return false;
/*     */ 
/*  77 */     if (this.numObjs + other.length > this.objs.length)
/*  78 */       resize(this.numObjs + other.length);
/*  79 */     if (index != this.numObjs)
/*  80 */       System.arraycopy(this.objs, index, this.objs, index + other.length, this.numObjs - index);
/*  81 */     System.arraycopy(other, 0, this.objs, index, other.length);
/*  82 */     this.numObjs += other.length;
/*  83 */     return true;
/*     */   }
/*     */   public boolean addAll(IntBag other) {
/*  86 */     return addAll(this.numObjs, other);
/*     */   }
/*     */ 
/*     */   public boolean addAll(int index, IntBag other)
/*     */   {
/*  93 */     if (index > this.numObjs) {
/*  94 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/*  96 */     if (other.numObjs <= 0) return false;
/*     */ 
/*  98 */     if (this.numObjs + other.numObjs > this.objs.length)
/*  99 */       resize(this.numObjs + other.numObjs);
/* 100 */     if (index != this.numObjs)
/* 101 */       System.arraycopy(this.objs, index, this.objs, index + other.size(), this.numObjs - index);
/* 102 */     System.arraycopy(other.objs, 0, this.objs, index, other.numObjs);
/* 103 */     this.numObjs += other.numObjs;
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */   public Object clone() throws CloneNotSupportedException
/*     */   {
/* 109 */     IntBag b = (IntBag)super.clone();
/* 110 */     b.objs = ((int[])this.objs.clone());
/* 111 */     return b;
/*     */   }
/*     */ 
/*     */   public void resize(int toAtLeast)
/*     */   {
/* 116 */     if (this.objs.length >= toAtLeast) {
/* 117 */       return;
/*     */     }
/* 119 */     if (this.objs.length * 2 > toAtLeast) {
/* 120 */       toAtLeast = this.objs.length * 2;
/*     */     }
/*     */ 
/* 123 */     int[] newobjs = new int[toAtLeast];
/* 124 */     System.arraycopy(this.objs, 0, newobjs, 0, this.numObjs);
/* 125 */     this.objs = newobjs;
/*     */   }
/*     */ 
/*     */   public void shrink(int desiredLength)
/*     */   {
/* 133 */     if (desiredLength < this.numObjs) desiredLength = this.numObjs;
/* 134 */     if (desiredLength >= this.objs.length) return;
/* 135 */     int[] newobjs = new int[desiredLength];
/* 136 */     System.arraycopy(this.objs, 0, newobjs, 0, this.numObjs);
/* 137 */     this.objs = newobjs;
/*     */   }
/*     */ 
/*     */   public int top()
/*     */   {
/* 143 */     if (this.numObjs <= 0) return 0;
/* 144 */     return this.objs[(this.numObjs - 1)];
/*     */   }
/*     */ 
/*     */   public int pop()
/*     */   {
/* 151 */     int numObjs = this.numObjs;
/* 152 */     if (numObjs <= 0) return 0;
/* 153 */     int ret = this.objs[(--numObjs)];
/* 154 */     this.numObjs = numObjs;
/* 155 */     return ret;
/*     */   }
/*     */ 
/*     */   public boolean push(int obj)
/*     */   {
/* 162 */     if (this.numObjs >= this.objs.length) doubleCapacityPlusOne();
/* 163 */     this.objs[(this.numObjs++)] = obj;
/* 164 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean add(int obj)
/*     */   {
/* 177 */     if (this.numObjs >= this.objs.length) doubleCapacityPlusOne();
/* 178 */     this.objs[(this.numObjs++)] = obj;
/* 179 */     return true;
/*     */   }
/*     */ 
/*     */   void doubleCapacityPlusOne()
/*     */   {
/* 194 */     int[] newobjs = new int[this.numObjs * 2 + 1];
/* 195 */     System.arraycopy(this.objs, 0, newobjs, 0, this.numObjs);
/* 196 */     this.objs = newobjs;
/*     */   }
/*     */ 
/*     */   public boolean contains(int o)
/*     */   {
/* 201 */     int numObjs = this.numObjs;
/* 202 */     int[] objs = this.objs;
/* 203 */     for (int x = 0; x < numObjs; x++)
/* 204 */       if (o == objs[x]) return true;
/* 205 */     return false;
/*     */   }
/*     */ 
/*     */   public int get(int index)
/*     */   {
/* 210 */     if (index >= this.numObjs) {
/* 211 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 213 */     return this.objs[index];
/*     */   }
/*     */ 
/*     */   public Object getValue(int index)
/*     */   {
/* 218 */     return Integer.valueOf(get(index));
/*     */   }
/*     */ 
/*     */   public int set(int index, int element)
/*     */   {
/* 223 */     if (index >= this.numObjs) {
/* 224 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 226 */     int returnval = this.objs[index];
/* 227 */     this.objs[index] = element;
/* 228 */     return returnval;
/*     */   }
/*     */ 
/*     */   public Object setValue(int index, Object value)
/*     */   {
/* 233 */     Integer old = new Integer(get(index));
/* 234 */     Integer newval = null;
/*     */     try { newval = (Integer)value; } catch (ClassCastException e) {
/* 236 */       throw new IllegalArgumentException("Expected an Integer");
/* 237 */     }set(index, newval.intValue());
/* 238 */     return old;
/*     */   }
/*     */ 
/*     */   public int removeNondestructively(int index)
/*     */   {
/* 244 */     if (index >= this.numObjs) {
/* 245 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 247 */     int ret = this.objs[index];
/* 248 */     if (index < this.numObjs - 1)
/* 249 */       System.arraycopy(this.objs, index + 1, this.objs, index, this.numObjs - index - 1);
/* 250 */     this.numObjs -= 1;
/* 251 */     return ret;
/*     */   }
/*     */ 
/*     */   public int remove(int index)
/*     */   {
/* 257 */     int _numObjs = this.numObjs;
/* 258 */     if (index >= _numObjs) {
/* 259 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 261 */     int[] _objs = this.objs;
/* 262 */     int ret = _objs[index];
/* 263 */     _objs[index] = _objs[(_numObjs - 1)];
/* 264 */     this.numObjs -= 1;
/* 265 */     return ret;
/*     */   }
/*     */ 
/*     */   public void sort() {
/* 269 */     Arrays.sort(this.objs, 0, this.numObjs);
/*     */   }
/*     */ 
/*     */   public void fill(int o)
/*     */   {
/* 275 */     int[] objs = this.objs;
/* 276 */     int numObjs = this.numObjs;
/*     */ 
/* 278 */     for (int x = 0; x < numObjs; x++)
/* 279 */       objs[x] = o;
/*     */   }
/*     */ 
/*     */   public void shuffle(Random random)
/*     */   {
/* 286 */     int[] objs = this.objs;
/* 287 */     int numObjs = this.numObjs;
/*     */ 
/* 291 */     for (int x = numObjs - 1; x >= 1; x--)
/*     */     {
/* 293 */       int rand = random.nextInt(x + 1);
/* 294 */       int obj = objs[x];
/* 295 */       objs[x] = objs[rand];
/* 296 */       objs[rand] = obj;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void shuffle(MersenneTwisterFast random)
/*     */   {
/* 304 */     int[] objs = this.objs;
/* 305 */     int numObjs = this.numObjs;
/*     */ 
/* 309 */     for (int x = numObjs - 1; x >= 1; x--)
/*     */     {
/* 311 */       int rand = random.nextInt(x + 1);
/* 312 */       int obj = objs[x];
/* 313 */       objs[x] = objs[rand];
/* 314 */       objs[rand] = obj;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reverse()
/*     */   {
/* 322 */     int[] objs = this.objs;
/* 323 */     int numObjs = this.numObjs;
/* 324 */     int l = numObjs / 2;
/*     */ 
/* 326 */     for (int x = 0; x < l; x++)
/*     */     {
/* 328 */       int obj = objs[x];
/* 329 */       objs[x] = objs[(numObjs - x - 1)];
/* 330 */       objs[(numObjs - x - 1)] = obj;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 343 */     this.numObjs = 0;
/*     */   }
/*     */ 
/*     */   public void copyIntoArray(int fromStart, int[] to, int toStart, int len)
/*     */   {
/* 353 */     System.arraycopy(this.objs, fromStart, to, toStart, len);
/*     */   }
/*     */ 
/*     */   public int[] toArray()
/*     */   {
/* 358 */     int[] o = new int[this.numObjs];
/* 359 */     System.arraycopy(this.objs, 0, o, 0, this.numObjs);
/* 360 */     return o;
/*     */   }
/*     */ 
/*     */   public Integer[] toIntegerArray()
/*     */   {
/* 365 */     Integer[] o = new Integer[this.numObjs];
/* 366 */     for (int i = 0; i < this.numObjs; i++)
/* 367 */       o[i] = Integer.valueOf(this.objs[i]);
/* 368 */     return o;
/*     */   }
/*     */ 
/*     */   public Long[] toLongArray()
/*     */   {
/* 373 */     Long[] o = new Long[this.numObjs];
/* 374 */     for (int i = 0; i < this.numObjs; i++)
/* 375 */       o[i] = Long.valueOf(this.objs[i]);
/* 376 */     return o;
/*     */   }
/*     */ 
/*     */   public Double[] toDoubleArray()
/*     */   {
/* 381 */     Double[] o = new Double[this.numObjs];
/* 382 */     for (int i = 0; i < this.numObjs; i++)
/* 383 */       o[i] = new Double(this.objs[i]);
/* 384 */     return o;
/*     */   }
/*     */ 
/*     */   public Class componentType()
/*     */   {
/* 389 */     return Integer.TYPE;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.IntBag
 * JD-Core Version:    0.6.2
 */