/*     */ package sim.util;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class Bag
/*     */   implements Collection, Serializable, Cloneable, Indexed
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public Object[] objs;
/*     */   public int numObjs;
/*     */ 
/*     */   public Bag()
/*     */   {
/*  46 */     this.numObjs = 0; this.objs = new Object[1];
/*     */   }
/*     */   public Bag(int capacity) {
/*  49 */     this.numObjs = 0; this.objs = new Object[capacity];
/*     */   }
/*     */ 
/*     */   public Bag(Bag other)
/*     */   {
/*  56 */     if (other == null) { this.numObjs = 0; this.objs = new Object[1];
/*     */     } else
/*     */     {
/*  59 */       this.numObjs = other.numObjs;
/*  60 */       this.objs = new Object[this.numObjs];
/*  61 */       System.arraycopy(other.objs, 0, this.objs, 0, this.numObjs);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Bag(Object[] other)
/*     */   {
/*  67 */     this(); if (other != null) addAll(other); 
/*     */   }
/*     */ 
/*     */   public Bag(Collection other)
/*     */   {
/*  71 */     this(); if (other != null) addAll(other); 
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/*  75 */     return this.numObjs;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  80 */     return this.numObjs <= 0;
/*     */   }
/*     */ 
/*     */   public boolean addAll(Collection other)
/*     */   {
/*  85 */     if ((other instanceof Bag)) return addAll((Bag)other);
/*  86 */     return addAll(this.numObjs, other.toArray());
/*     */   }
/*     */ 
/*     */   public boolean addAll(int index, Collection other)
/*     */   {
/*  91 */     if ((other instanceof Bag)) return addAll(index, (Bag)other);
/*  92 */     return addAll(index, other.toArray());
/*     */   }
/*     */   public boolean addAll(Object[] other) {
/*  95 */     return addAll(this.numObjs, other);
/*     */   }
/*     */ 
/*     */   public boolean addAll(int index, Object[] other)
/*     */   {
/* 102 */     if (index > this.numObjs) {
/* 103 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 105 */     if (other.length == 0) return false;
/*     */ 
/* 107 */     if (this.numObjs + other.length > this.objs.length)
/* 108 */       resize(this.numObjs + other.length);
/* 109 */     if (index != this.numObjs)
/* 110 */       System.arraycopy(this.objs, index, this.objs, index + other.length, this.numObjs - index);
/* 111 */     System.arraycopy(other, 0, this.objs, index, other.length);
/* 112 */     this.numObjs += other.length;
/* 113 */     return true;
/*     */   }
/*     */   public boolean addAll(Bag other) {
/* 116 */     return addAll(this.numObjs, other);
/*     */   }
/*     */ 
/*     */   public boolean addAll(int index, Bag other)
/*     */   {
/* 123 */     if (index > this.numObjs) {
/* 124 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 126 */     if (other.numObjs <= 0) return false;
/*     */ 
/* 128 */     if (this.numObjs + other.numObjs > this.objs.length)
/* 129 */       resize(this.numObjs + other.numObjs);
/* 130 */     if (index != this.numObjs)
/* 131 */       System.arraycopy(this.objs, index, this.objs, index + other.size(), this.numObjs - index);
/* 132 */     System.arraycopy(other.objs, 0, this.objs, index, other.numObjs);
/* 133 */     this.numObjs += other.numObjs;
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */   public Object clone() throws CloneNotSupportedException
/*     */   {
/* 139 */     Bag b = (Bag)super.clone();
/* 140 */     b.objs = ((Object[])this.objs.clone());
/* 141 */     return b;
/*     */   }
/*     */ 
/*     */   public void resize(int toAtLeast)
/*     */   {
/* 147 */     if (this.objs.length >= toAtLeast) {
/* 148 */       return;
/*     */     }
/* 150 */     if (this.objs.length * 2 > toAtLeast) {
/* 151 */       toAtLeast = this.objs.length * 2;
/*     */     }
/*     */ 
/* 154 */     Object[] newobjs = new Object[toAtLeast];
/* 155 */     System.arraycopy(this.objs, 0, newobjs, 0, this.numObjs);
/* 156 */     this.objs = newobjs;
/*     */   }
/*     */ 
/*     */   public void shrink(int desiredLength)
/*     */   {
/* 164 */     if (desiredLength < this.numObjs) desiredLength = this.numObjs;
/* 165 */     if (desiredLength >= this.objs.length) return;
/* 166 */     Object[] newobjs = new Object[desiredLength];
/* 167 */     System.arraycopy(this.objs, 0, newobjs, 0, this.numObjs);
/* 168 */     this.objs = newobjs;
/*     */   }
/*     */ 
/*     */   public Object top()
/*     */   {
/* 174 */     if (this.numObjs <= 0) return null;
/* 175 */     return this.objs[(this.numObjs - 1)];
/*     */   }
/*     */ 
/*     */   public Object pop()
/*     */   {
/* 182 */     int numObjs = this.numObjs;
/* 183 */     if (numObjs <= 0) return null;
/* 184 */     Object ret = this.objs[(--numObjs)];
/* 185 */     this.objs[numObjs] = null;
/* 186 */     this.numObjs = numObjs;
/* 187 */     return ret;
/*     */   }
/*     */ 
/*     */   public boolean push(Object obj)
/*     */   {
/* 194 */     if (this.numObjs >= this.objs.length) doubleCapacityPlusOne();
/* 195 */     this.objs[(this.numObjs++)] = obj;
/* 196 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean add(Object obj)
/*     */   {
/* 209 */     if (this.numObjs >= this.objs.length) doubleCapacityPlusOne();
/* 210 */     this.objs[(this.numObjs++)] = obj;
/* 211 */     return true;
/*     */   }
/*     */ 
/*     */   void doubleCapacityPlusOne()
/*     */   {
/* 226 */     Object[] newobjs = new Object[this.numObjs * 2 + 1];
/* 227 */     System.arraycopy(this.objs, 0, newobjs, 0, this.numObjs);
/* 228 */     this.objs = newobjs;
/*     */   }
/*     */ 
/*     */   public boolean contains(Object o)
/*     */   {
/* 233 */     int numObjs = this.numObjs;
/* 234 */     Object[] objs = this.objs;
/* 235 */     for (int x = 0; x < numObjs; x++)
/* 236 */       if (o == null ? objs[x] != null : (o == objs[x]) || (o.equals(objs[x]))) return true;
/* 237 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean containsAll(Collection c)
/*     */   {
/* 242 */     Iterator iterator = c.iterator();
/* 243 */     while (iterator.hasNext())
/* 244 */       if (!contains(iterator.next())) return false;
/* 245 */     return true;
/*     */   }
/*     */ 
/*     */   public Object get(int index)
/*     */   {
/* 250 */     if (index >= this.numObjs) {
/* 251 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 253 */     return this.objs[index];
/*     */   }
/*     */ 
/*     */   public Object getValue(int index)
/*     */   {
/* 259 */     if (index >= this.numObjs) {
/* 260 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 262 */     return this.objs[index];
/*     */   }
/*     */ 
/*     */   public Object set(int index, Object element)
/*     */   {
/* 267 */     if (index >= this.numObjs) {
/* 268 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 270 */     Object returnval = this.objs[index];
/* 271 */     this.objs[index] = element;
/* 272 */     return returnval;
/*     */   }
/*     */ 
/*     */   public Object setValue(int index, Object element)
/*     */   {
/* 278 */     if (index >= this.numObjs) {
/* 279 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/* 281 */     Object returnval = this.objs[index];
/* 282 */     this.objs[index] = element;
/* 283 */     return returnval;
/*     */   }
/*     */ 
/*     */   public boolean removeAll(Collection c)
/*     */   {
/* 288 */     boolean flag = false;
/* 289 */     Iterator iterator = c.iterator();
/* 290 */     while (iterator.hasNext())
/* 291 */       if (remove(iterator.next())) flag = true;
/* 292 */     return flag;
/*     */   }
/*     */ 
/*     */   public boolean retainAll(Collection c)
/*     */   {
/* 297 */     boolean flag = false;
/* 298 */     for (int x = 0; x < this.numObjs; x++)
/* 299 */       if (!c.contains(this.objs[x]))
/*     */       {
/* 301 */         flag = true;
/* 302 */         remove(x);
/* 303 */         x--;
/*     */       }
/* 305 */     return flag;
/*     */   }
/*     */ 
/*     */   public Object removeNondestructively(int index)
/*     */   {
/* 311 */     if (index >= this.numObjs)
/*     */     {
/* 313 */       throw new ArrayIndexOutOfBoundsException(index);
/* 314 */     }Object ret = this.objs[index];
/* 315 */     if (index < this.numObjs - 1)
/* 316 */       System.arraycopy(this.objs, index + 1, this.objs, index, this.numObjs - index - 1);
/* 317 */     this.objs[(this.numObjs - 1)] = null;
/* 318 */     this.numObjs -= 1;
/* 319 */     return ret;
/*     */   }
/*     */ 
/*     */   public boolean removeNondestructively(Object o)
/*     */   {
/* 325 */     int numObjs = this.numObjs;
/* 326 */     Object[] objs = this.objs;
/* 327 */     for (int x = 0; x < numObjs; x++)
/* 328 */       if (o == null ? objs[x] != null : (o == objs[x]) || (o.equals(objs[x])))
/*     */       {
/* 330 */         removeNondestructively(x);
/* 331 */         return true;
/*     */       }
/* 333 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean remove(Object o)
/*     */   {
/* 339 */     int numObjs = this.numObjs;
/* 340 */     Object[] objs = this.objs;
/* 341 */     for (int x = 0; x < numObjs; x++)
/* 342 */       if (o == null ? objs[x] != null : (o == objs[x]) || (o.equals(objs[x])))
/*     */       {
/* 344 */         remove(x);
/* 345 */         return true;
/*     */       }
/* 347 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean removeMultiply(Object o)
/*     */   {
/* 353 */     int numObjs = this.numObjs;
/* 354 */     Object[] objs = this.objs;
/* 355 */     boolean flag = false;
/* 356 */     for (int x = 0; x < numObjs; x++)
/* 357 */       if (o == null ? objs[x] != null : (o == objs[x]) || (o.equals(objs[x])))
/*     */       {
/* 359 */         flag = true;
/* 360 */         remove(x);
/* 361 */         x--;
/*     */       }
/* 363 */     return flag;
/*     */   }
/*     */ 
/*     */   public Object remove(int index)
/*     */   {
/* 369 */     int _numObjs = this.numObjs;
/* 370 */     if (index >= _numObjs)
/*     */     {
/* 372 */       throw new ArrayIndexOutOfBoundsException(index);
/* 373 */     }Object[] _objs = this.objs;
/* 374 */     Object ret = _objs[index];
/* 375 */     _objs[index] = _objs[(_numObjs - 1)];
/* 376 */     _objs[(_numObjs - 1)] = null;
/* 377 */     this.numObjs -= 1;
/* 378 */     return ret;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 393 */     int len = this.numObjs;
/* 394 */     Object[] o = this.objs;
/*     */ 
/* 396 */     for (int i = 0; i < len; i++) {
/* 397 */       o[i] = null;
/*     */     }
/* 399 */     this.numObjs = 0;
/*     */   }
/*     */ 
/*     */   public Object[] toArray()
/*     */   {
/* 404 */     Object[] o = new Object[this.numObjs];
/* 405 */     System.arraycopy(this.objs, 0, o, 0, this.numObjs);
/* 406 */     return o;
/*     */   }
/*     */ 
/*     */   public Object[] toArray(Object[] o)
/*     */   {
/* 414 */     if (o.length < this.numObjs)
/* 415 */       o = (Object[])Array.newInstance(o.getClass().getComponentType(), this.numObjs);
/* 416 */     else if (o.length > this.numObjs)
/* 417 */       o[this.numObjs] = null;
/* 418 */     System.arraycopy(this.objs, 0, o, 0, this.numObjs);
/* 419 */     return o;
/*     */   }
/*     */ 
/*     */   public void copyIntoArray(int fromStart, Object[] to, int toStart, int len)
/*     */   {
/* 429 */     System.arraycopy(this.objs, fromStart, to, toStart, len);
/*     */   }
/*     */ 
/*     */   public Iterator iterator()
/*     */   {
/* 437 */     return new BagIterator(this);
/*     */   }
/*     */ 
/*     */   public Class componentType()
/*     */   {
/* 443 */     return null;
/*     */   }
/*     */ 
/*     */   public void sort(Comparator c)
/*     */   {
/* 449 */     Arrays.sort(this.objs, 0, this.numObjs, c);
/*     */   }
/*     */ 
/*     */   public void sort()
/*     */   {
/* 455 */     Arrays.sort(this.objs, 0, this.numObjs);
/*     */   }
/*     */ 
/*     */   public void fill(Object o)
/*     */   {
/* 462 */     Object[] objs = this.objs;
/* 463 */     int numObjs = this.numObjs;
/*     */ 
/* 465 */     for (int x = 0; x < numObjs; x++)
/* 466 */       objs[x] = o;
/*     */   }
/*     */ 
/*     */   public void shuffle(Random random)
/*     */   {
/* 473 */     Object[] objs = this.objs;
/* 474 */     int numObjs = this.numObjs;
/*     */ 
/* 478 */     for (int x = numObjs - 1; x >= 1; x--)
/*     */     {
/* 480 */       int rand = random.nextInt(x + 1);
/* 481 */       Object obj = objs[x];
/* 482 */       objs[x] = objs[rand];
/* 483 */       objs[rand] = obj;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void shuffle(MersenneTwisterFast random)
/*     */   {
/* 491 */     Object[] objs = this.objs;
/* 492 */     int numObjs = this.numObjs;
/*     */ 
/* 496 */     for (int x = numObjs - 1; x >= 1; x--)
/*     */     {
/* 498 */       int rand = random.nextInt(x + 1);
/* 499 */       Object obj = objs[x];
/* 500 */       objs[x] = objs[rand];
/* 501 */       objs[rand] = obj;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reverse()
/*     */   {
/* 509 */     Object[] objs = this.objs;
/* 510 */     int numObjs = this.numObjs;
/* 511 */     int l = numObjs / 2;
/*     */ 
/* 513 */     for (int x = 0; x < l; x++)
/*     */     {
/* 515 */       Object obj = objs[x];
/* 516 */       objs[x] = objs[(numObjs - x - 1)];
/* 517 */       objs[(numObjs - x - 1)] = obj;
/*     */     }
/*     */   }
/*     */ 
/*     */   static class BagIterator
/*     */     implements Iterator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 525 */     int obj = 0;
/*     */     Bag bag;
/* 527 */     boolean canRemove = false;
/*     */ 
/* 529 */     public BagIterator(Bag bag) { this.bag = bag; }
/*     */ 
/*     */     public boolean hasNext()
/*     */     {
/* 533 */       return this.obj < this.bag.numObjs;
/*     */     }
/*     */ 
/*     */     public Object next() {
/* 537 */       if (this.obj >= this.bag.numObjs) throw new NoSuchElementException("No More Elements");
/* 538 */       this.canRemove = true;
/* 539 */       return this.bag.objs[(this.obj++)];
/*     */     }
/*     */ 
/*     */     public void remove() {
/* 543 */       if (!this.canRemove) throw new IllegalStateException("remove() before next(), or remove() called twice");
/*     */ 
/* 545 */       if (this.obj - 1 >= this.bag.numObjs) throw new NoSuchElementException("No More Elements");
/* 546 */       this.bag.removeNondestructively(this.obj - 1);
/* 547 */       this.obj -= 1;
/* 548 */       this.canRemove = false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.Bag
 * JD-Core Version:    0.6.2
 */