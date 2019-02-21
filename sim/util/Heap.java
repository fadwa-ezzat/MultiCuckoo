/*     */ package sim.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Heap
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  31 */   Comparable[] keys = null;
/*     */ 
/*  34 */   Object[] objects = null;
/*     */ 
/*  36 */   int numElem = 0;
/*     */ 
/*     */   public Comparable[] getKeys()
/*     */   {
/*  40 */     Comparable[] k = new Comparable[this.numElem];
/*  41 */     System.arraycopy(this.keys, 0, k, 0, this.numElem);
/*  42 */     return k;
/*     */   }
/*     */ 
/*     */   public Object[] getObjects()
/*     */   {
/*  47 */     Object[] o = new Object[this.numElem];
/*  48 */     System.arraycopy(this.objects, 0, o, 0, this.numElem);
/*  49 */     return o;
/*     */   }
/*     */ 
/*     */   public Heap()
/*     */   {
/*  55 */     this(new Comparable[0], new Object[0]);
/*     */   }
/*     */ 
/*     */   public Heap(Comparable[] keys, Object[] objects)
/*     */   {
/*  61 */     if (keys.length != objects.length)
/*  62 */       throw new IllegalArgumentException("keys and objects must be of the same length");
/*  63 */     this.keys = keys;
/*  64 */     this.objects = objects;
/*  65 */     this.numElem = keys.length;
/*  66 */     buildHeap();
/*     */   }
/*     */ 
/*     */   void buildHeap()
/*     */   {
/*  72 */     for (int i = this.numElem / 2; i >= 1; i--)
/*  73 */       heapify(i, this.numElem);
/*     */   }
/*     */ 
/*     */   void heapify(int i, int heapsize)
/*     */   {
/*  79 */     Object[] objects = this.objects;
/*  80 */     Comparable[] keys = this.keys;
/*     */     while (true)
/*     */     {
/*  84 */       int l = 2 * i;
/*  85 */       int r = 2 * i + 1;
/*     */       int smallest;
/*     */       int smallest;
/*  87 */       if ((l <= heapsize) && (keys[(l - 1)].compareTo(keys[(i - 1)]) < 0))
/*  88 */         smallest = l;
/*     */       else
/*  90 */         smallest = i;
/*  91 */       if ((r <= heapsize) && (keys[(r - 1)].compareTo(keys[(smallest - 1)]) < 0))
/*  92 */         smallest = r;
/*  93 */       if (smallest != i)
/*     */       {
/*  96 */         Comparable tempkey = keys[(i - 1)];
/*  97 */         keys[(i - 1)] = keys[(smallest - 1)];
/*  98 */         keys[(smallest - 1)] = tempkey;
/*     */ 
/* 100 */         Object temp = objects[(i - 1)];
/* 101 */         objects[(i - 1)] = objects[(smallest - 1)];
/* 102 */         objects[(smallest - 1)] = temp;
/*     */ 
/* 104 */         i = smallest;
/*     */       }
/*     */       else {
/* 107 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Comparable getMinKey()
/*     */   {
/* 114 */     if (this.numElem == 0) return null;
/* 115 */     return this.keys[0];
/*     */   }
/*     */ 
/*     */   public Object getMin()
/*     */   {
/* 121 */     if (this.numElem == 0) return null;
/* 122 */     return this.objects[0];
/*     */   }
/*     */ 
/*     */   Bag extractMin(Comparable comparable, Bag putInHere)
/*     */   {
/* 130 */     if (putInHere == null) putInHere = new Bag();
/*     */     while (true)
/*     */     {
/* 133 */       Comparable comp = getMinKey();
/* 134 */       if ((comp == null) || (comparable.compareTo(comp) != 0))
/*     */       {
/* 136 */         return putInHere;
/* 137 */       }putInHere.add(extractMin());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Bag extractMin(Bag putInHere)
/*     */   {
/* 145 */     Comparable min = getMinKey();
/* 146 */     if (min == null)
/*     */     {
/* 148 */       if (putInHere == null) return new Bag(0);
/* 149 */       return putInHere;
/*     */     }
/*     */ 
/* 152 */     if (putInHere == null) putInHere = new Bag();
/* 153 */     putInHere.add(extractMin());
/* 154 */     return extractMin(min, putInHere);
/*     */   }
/*     */ 
/*     */   public Object extractMin()
/*     */   {
/* 161 */     int numElem = this.numElem;
/* 162 */     Object[] objects = this.objects;
/* 163 */     Comparable[] keys = this.keys;
/*     */ 
/* 165 */     if (numElem == 0) {
/* 166 */       return null;
/*     */     }
/* 168 */     keys[0] = keys[(numElem - 1)];
/* 169 */     keys[(numElem - 1)] = null;
/*     */ 
/* 171 */     Object result = objects[0];
/* 172 */     objects[0] = objects[(numElem - 1)];
/* 173 */     objects[(numElem - 1)] = null;
/* 174 */     numElem--;
/*     */ 
/* 176 */     if (numElem > 1) heapify(1, numElem);
/*     */ 
/* 180 */     this.numElem = numElem;
/* 181 */     return result;
/*     */   }
/*     */ 
/*     */   public void add(Object elem, Comparable key)
/*     */   {
/* 188 */     int numElem = this.numElem;
/* 189 */     Object[] objects = this.objects;
/* 190 */     Comparable[] keys = this.keys;
/*     */ 
/* 192 */     numElem++;
/* 193 */     if (numElem - 1 >= objects.length)
/*     */     {
/* 195 */       Object[] temp = new Object[objects.length * 2 + 1];
/* 196 */       System.arraycopy(objects, 0, temp, 0, objects.length);
/* 197 */       objects = temp;
/* 198 */       Comparable[] temptemp = new Comparable[keys.length * 2 + 1];
/* 199 */       System.arraycopy(keys, 0, temptemp, 0, keys.length);
/* 200 */       keys = temptemp;
/*     */ 
/* 203 */       this.objects = objects;
/* 204 */       this.keys = keys;
/*     */     }
/* 206 */     int i = numElem;
/*     */ 
/* 208 */     if (i > 1)
/*     */     {
/* 210 */       while ((i > 1) && (key.compareTo(keys[(i / 2 - 1)]) < 0))
/*     */       {
/* 212 */         objects[(i - 1)] = objects[(i / 2 - 1)];
/* 213 */         keys[(i - 1)] = keys[(i / 2 - 1)];
/* 214 */         i /= 2;
/*     */       }
/*     */     }
/* 217 */     keys[(i - 1)] = key;
/* 218 */     objects[(i - 1)] = elem;
/*     */ 
/* 221 */     this.numElem = numElem;
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 226 */     return this.numElem;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 231 */     return this.numElem == 0;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 236 */     int len = this.numElem;
/*     */ 
/* 239 */     Object[] objects = this.objects;
/* 240 */     Comparable[] keys = this.keys;
/* 241 */     for (int x = 0; x < len; x++)
/*     */     {
/* 243 */       objects[x] = null;
/* 244 */       keys[x] = null;
/*     */     }
/*     */ 
/* 247 */     this.numElem = 0;
/*     */   }
/*     */ 
/*     */   public Heap merge(Heap other)
/*     */   {
/* 256 */     int n = this.numElem + other.numElem;
/* 257 */     Comparable[] combinedKeys = new Comparable[n];
/* 258 */     Object[] combinedObjects = new Object[n];
/*     */ 
/* 260 */     System.arraycopy(this.keys, 0, combinedKeys, 0, this.numElem);
/* 261 */     System.arraycopy(other.keys, 0, combinedKeys, this.numElem, other.numElem);
/*     */ 
/* 263 */     System.arraycopy(this.objects, 0, combinedObjects, 0, this.numElem);
/* 264 */     System.arraycopy(other.objects, 0, combinedObjects, this.numElem, other.numElem);
/*     */ 
/* 266 */     return new Heap(combinedKeys, combinedObjects);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.Heap
 * JD-Core Version:    0.6.2
 */