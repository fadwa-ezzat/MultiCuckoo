/*     */ package sim.util;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class DoubleBag
/*     */   implements Serializable, Cloneable, Indexed
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public double[] objs;
/*     */   public int numObjs;
/*     */ 
/*     */   public DoubleBag(int capacity)
/*     */   {
/*  33 */     this.numObjs = 0; this.objs = new double[capacity];
/*     */   }
/*  35 */   public DoubleBag() { this.numObjs = 0; this.objs = new double[1];
/*     */   }
/*     */ 
/*     */   public DoubleBag(DoubleBag other)
/*     */   {
/*  42 */     if (other == null) { this.numObjs = 0; this.objs = new double[1];
/*     */     } else
/*     */     {
/*  45 */       this.numObjs = other.numObjs;
/*  46 */       this.objs = new double[this.numObjs];
/*  47 */       System.arraycopy(other.objs, 0, this.objs, 0, this.numObjs);
/*     */     }
/*     */   }
/*     */ 
/*     */   public DoubleBag(double[] other)
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
/*     */   public boolean addAll(double[] other) {
/*  65 */     return addAll(this.numObjs, other);
/*     */   }
/*     */ 
/*     */   public boolean addAll(int index, double[] other)
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
/*     */   public boolean addAll(DoubleBag other) {
/*  86 */     return addAll(this.numObjs, other);
/*     */   }
/*     */ 
/*     */   public boolean addAll(int index, DoubleBag other)
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
/* 109 */     DoubleBag b = (DoubleBag)super.clone();
/* 110 */     b.objs = ((double[])this.objs.clone());
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
/* 123 */     double[] newobjs = new double[toAtLeast];
/* 124 */     System.arraycopy(this.objs, 0, newobjs, 0, this.numObjs);
/* 125 */     this.objs = newobjs;
/*     */   }
/*     */ 
/*     */   public void shrink(int desiredLength)
/*     */   {
/* 133 */     if (desiredLength < this.numObjs) desiredLength = this.numObjs;
/* 134 */     if (desiredLength >= this.objs.length) return;
/* 135 */     double[] newobjs = new double[desiredLength];
/* 136 */     System.arraycopy(this.objs, 0, newobjs, 0, this.numObjs);
/* 137 */     this.objs = newobjs;
/*     */   }
/*     */ 
/*     */   public double top()
/*     */   {
/* 144 */     if (this.numObjs <= 0) return 0.0D;
/* 145 */     return this.objs[(this.numObjs - 1)];
/*     */   }
/*     */ 
/*     */   public double pop()
/*     */   {
/* 152 */     int numObjs = this.numObjs;
/* 153 */     if (numObjs <= 0) return 0.0D;
/* 154 */     double ret = this.objs[(--numObjs)];
/* 155 */     this.numObjs = numObjs;
/* 156 */     return ret;
/*     */   }
/*     */ 
/*     */   public boolean push(double obj)
/*     */   {
/* 163 */     if (this.numObjs >= this.objs.length) doubleCapacityPlusOne();
/* 164 */     this.objs[(this.numObjs++)] = obj;
/* 165 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean add(double obj)
/*     */   {
/* 178 */     if (this.numObjs >= this.objs.length) doubleCapacityPlusOne();
/* 179 */     this.objs[(this.numObjs++)] = obj;
/* 180 */     return true;
/*     */   }
/*     */ 
/*     */   void doubleCapacityPlusOne()
/*     */   {
/* 195 */     double[] newobjs = new double[this.numObjs * 2 + 1];
/* 196 */     System.arraycopy(this.objs, 0, newobjs, 0, this.numObjs);
/* 197 */     this.objs = newobjs;
/*     */   }
/*     */ 
/*     */   public boolean contains(double o)
/*     */   {
/* 202 */     int numObjs = this.numObjs;
/* 203 */     double[] objs = this.objs;
/* 204 */     for (int x = 0; x < numObjs; x++)
/* 205 */       if (o == objs[x]) return true;
/* 206 */     return false;
/*     */   }
/*     */ 
/*     */   public double get(int index)
/*     */   {
/* 211 */     if (index >= this.numObjs) {
/* 212 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 214 */     return this.objs[index];
/*     */   }
/*     */ 
/*     */   public Object getValue(int index)
/*     */   {
/* 219 */     return new Double(get(index));
/*     */   }
/*     */ 
/*     */   public double set(int index, double element)
/*     */   {
/* 224 */     if (index >= this.numObjs) {
/* 225 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 227 */     double returnval = this.objs[index];
/* 228 */     this.objs[index] = element;
/* 229 */     return returnval;
/*     */   }
/*     */ 
/*     */   public Object setValue(int index, Object value)
/*     */   {
/* 234 */     Double old = new Double(get(index));
/* 235 */     Double newval = null;
/*     */     try { newval = (Double)value; } catch (ClassCastException e) {
/* 237 */       throw new IllegalArgumentException("Expected a Double");
/* 238 */     }set(index, newval.doubleValue());
/* 239 */     return old;
/*     */   }
/*     */ 
/*     */   public double removeNondestructively(int index)
/*     */   {
/* 245 */     if (index >= this.numObjs) {
/* 246 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 248 */     double ret = this.objs[index];
/* 249 */     if (index < this.numObjs - 1)
/* 250 */       System.arraycopy(this.objs, index + 1, this.objs, index, this.numObjs - index - 1);
/* 251 */     this.numObjs -= 1;
/* 252 */     return ret;
/*     */   }
/*     */ 
/*     */   public double remove(int index)
/*     */   {
/* 258 */     int _numObjs = this.numObjs;
/* 259 */     if (index >= _numObjs) {
/* 260 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 262 */     double[] _objs = this.objs;
/* 263 */     double ret = _objs[index];
/* 264 */     _objs[index] = _objs[(_numObjs - 1)];
/* 265 */     this.numObjs -= 1;
/* 266 */     return ret;
/*     */   }
/*     */ 
/*     */   public void sort() {
/* 270 */     Arrays.sort(this.objs, 0, this.numObjs);
/*     */   }
/*     */ 
/*     */   public void fill(double o)
/*     */   {
/* 277 */     double[] objs = this.objs;
/* 278 */     int numObjs = this.numObjs;
/*     */ 
/* 280 */     for (int x = 0; x < numObjs; x++)
/* 281 */       objs[x] = o;
/*     */   }
/*     */ 
/*     */   public void shuffle(Random random)
/*     */   {
/* 288 */     double[] objs = this.objs;
/* 289 */     int numObjs = this.numObjs;
/*     */ 
/* 293 */     for (int x = numObjs - 1; x >= 1; x--)
/*     */     {
/* 295 */       int rand = random.nextInt(x + 1);
/* 296 */       double obj = objs[x];
/* 297 */       objs[x] = objs[rand];
/* 298 */       objs[rand] = obj;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void shuffle(MersenneTwisterFast random)
/*     */   {
/* 306 */     double[] objs = this.objs;
/* 307 */     int numObjs = this.numObjs;
/*     */ 
/* 311 */     for (int x = numObjs - 1; x >= 1; x--)
/*     */     {
/* 313 */       int rand = random.nextInt(x + 1);
/* 314 */       double obj = objs[x];
/* 315 */       objs[x] = objs[rand];
/* 316 */       objs[rand] = obj;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reverse()
/*     */   {
/* 324 */     double[] objs = this.objs;
/* 325 */     int numObjs = this.numObjs;
/* 326 */     int l = numObjs / 2;
/*     */ 
/* 328 */     for (int x = 0; x < l; x++)
/*     */     {
/* 330 */       double obj = objs[x];
/* 331 */       objs[x] = objs[(numObjs - x - 1)];
/* 332 */       objs[(numObjs - x - 1)] = obj;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 345 */     this.numObjs = 0;
/*     */   }
/*     */ 
/*     */   public void copyIntoArray(int fromStart, double[] to, int toStart, int len)
/*     */   {
/* 355 */     System.arraycopy(this.objs, fromStart, to, toStart, len);
/*     */   }
/*     */ 
/*     */   public double[] toArray()
/*     */   {
/* 360 */     double[] o = new double[this.numObjs];
/* 361 */     System.arraycopy(this.objs, 0, o, 0, this.numObjs);
/* 362 */     return o;
/*     */   }
/*     */ 
/*     */   public Double[] toDoubleArray()
/*     */   {
/* 367 */     Double[] o = new Double[this.numObjs];
/* 368 */     for (int i = 0; i < this.numObjs; i++)
/* 369 */       o[i] = new Double(this.objs[i]);
/* 370 */     return o;
/*     */   }
/*     */ 
/*     */   public Class componentType()
/*     */   {
/* 375 */     return Double.TYPE;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.DoubleBag
 * JD-Core Version:    0.6.2
 */