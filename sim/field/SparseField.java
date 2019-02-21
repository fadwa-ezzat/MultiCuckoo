/*     */ package sim.field;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import sim.util.Bag;
/*     */ import sim.util.LocationLog;
/*     */ 
/*     */ public abstract class SparseField
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  94 */   public boolean removeEmptyBags = true;
/*     */ 
/*  97 */   public boolean replaceLargeBags = true;
/*     */   public static final int INITIAL_BAG_SIZE = 16;
/*     */   public static final int MIN_BAG_SIZE = 32;
/*     */   public static final int LARGE_BAG_RATIO = 4;
/*     */   public static final int REPLACEMENT_BAG_RATIO = 2;
/* 113 */   public Map locationAndIndexHash = buildMap(0);
/*     */ 
/* 116 */   public Map objectHash = buildMap(0);
/*     */ 
/* 119 */   public Bag allObjects = new Bag();
/*     */   public static final int ANY_SIZE = 0;
/*     */ 
/*     */   public Map buildMap(Map other)
/*     */   {
/* 124 */     return new HashMap(other);
/*     */   }
/*     */ 
/*     */   public Map buildMap(int size) {
/* 128 */     if (size <= 0) return new HashMap();
/* 129 */     return new HashMap(size);
/*     */   }
/*     */ 
/*     */   protected SparseField() {
/*     */   }
/*     */ 
/*     */   protected SparseField(SparseField other) {
/* 136 */     this.removeEmptyBags = other.removeEmptyBags;
/* 137 */     this.replaceLargeBags = other.replaceLargeBags;
/* 138 */     this.locationAndIndexHash = buildMap(other.locationAndIndexHash);
/* 139 */     this.objectHash = buildMap(other.objectHash);
/* 140 */     this.allObjects = new Bag(other.allObjects);
/*     */   }
/*     */ 
/*     */   public int getObjectIndex(Object obj)
/*     */   {
/* 146 */     LocationAndIndex lai = (LocationAndIndex)this.locationAndIndexHash.get(obj);
/* 147 */     if (lai == null) return -1;
/* 148 */     return lai.index;
/*     */   }
/*     */ 
/*     */   public boolean exists(Object obj)
/*     */   {
/* 154 */     return getRawObjectLocation(obj) != null;
/*     */   }
/*     */ 
/*     */   public int size() {
/* 158 */     return this.allObjects.size();
/*     */   }
/*     */ 
/*     */   protected final Object getRawObjectLocation(Object obj)
/*     */   {
/* 166 */     LocationAndIndex lai = (LocationAndIndex)this.locationAndIndexHash.get(obj);
/* 167 */     if (lai == null) return null;
/* 168 */     assert (LocationLog.it(this, lai.location));
/* 169 */     return lai.location;
/*     */   }
/*     */ 
/*     */   public final int numObjectsAtLocation(Object location)
/*     */   {
/* 175 */     Bag b = (Bag)this.objectHash.get(location);
/* 176 */     if (b == null) return 0;
/* 177 */     assert (LocationLog.it(this, location));
/* 178 */     return b.numObjs;
/*     */   }
/*     */ 
/*     */   public Bag getObjectsAtLocation(Object location)
/*     */   {
/* 191 */     return getRawObjectsAtLocation(location);
/*     */   }
/*     */ 
/*     */   protected final Bag getRawObjectsAtLocation(Object location)
/*     */   {
/* 205 */     Bag b = (Bag)this.objectHash.get(location);
/* 206 */     if (b == null) return null;
/* 207 */     if (b.numObjs == 0) return null;
/* 208 */     assert (LocationLog.it(this, location));
/* 209 */     return b;
/*     */   }
/*     */ 
/*     */   public Bag getObjectsAtLocationOfObject(Object obj)
/*     */   {
/* 222 */     LocationAndIndex lai = (LocationAndIndex)this.locationAndIndexHash.get(obj);
/* 223 */     if (lai == null) return null;
/* 224 */     assert (LocationLog.it(this, lai.location));
/* 225 */     return lai.otherObjectsAtLocation;
/*     */   }
/*     */ 
/*     */   public int numObjectsAtLocationOfObject(Object obj)
/*     */   {
/* 233 */     LocationAndIndex lai = (LocationAndIndex)this.locationAndIndexHash.get(obj);
/* 234 */     if (lai == null) return 0;
/* 235 */     assert (LocationLog.it(this, lai.location));
/* 236 */     return lai.otherObjectsAtLocation.numObjs;
/*     */   }
/*     */ 
/*     */   public Bag removeObjectsAtLocation(Object location)
/*     */   {
/* 243 */     Bag objs = (Bag)this.objectHash.remove(location);
/* 244 */     if (objs != null)
/* 245 */       for (int j = 0; j < objs.numObjs; j++)
/*     */       {
/* 248 */         LocationAndIndex lai = (LocationAndIndex)this.locationAndIndexHash.remove(objs.objs[j]);
/*     */ 
/* 250 */         assert (LocationLog.it(this, lai.location));
/* 251 */         this.allObjects.remove(lai.index);
/* 252 */         if (this.allObjects.numObjs > lai.index)
/* 253 */           ((LocationAndIndex)this.locationAndIndexHash.get(this.allObjects.objs[lai.index])).index = lai.index;
/*     */       }
/* 255 */     return objs;
/*     */   }
/*     */ 
/*     */   public Bag clear()
/*     */   {
/* 263 */     this.locationAndIndexHash = buildMap(0);
/* 264 */     this.objectHash = buildMap(0);
/* 265 */     Bag retval = this.allObjects;
/* 266 */     this.allObjects = new Bag();
/* 267 */     return retval;
/*     */   }
/*     */ 
/*     */   public Object remove(Object obj)
/*     */   {
/* 274 */     LocationAndIndex lai = (LocationAndIndex)this.locationAndIndexHash.remove(obj);
/* 275 */     if (lai != null)
/*     */     {
/* 278 */       Bag objs = (Bag)this.objectHash.get(lai.location);
/* 279 */       objs.remove(obj);
/*     */ 
/* 283 */       int objsNumObjs = objs.numObjs;
/* 284 */       if ((this.removeEmptyBags) && (objsNumObjs == 0))
/* 285 */         this.objectHash.remove(lai.location);
/* 286 */       else if ((this.replaceLargeBags) && (objsNumObjs >= 32) && (objsNumObjs * 4 <= objs.objs.length)) {
/* 287 */         objs.shrink(objsNumObjs * 2);
/*     */       }
/*     */ 
/* 291 */       this.allObjects.remove(lai.index);
/* 292 */       if (this.allObjects.numObjs > lai.index) {
/* 293 */         ((LocationAndIndex)this.locationAndIndexHash.get(this.allObjects.objs[lai.index])).index = lai.index;
/*     */       }
/* 295 */       assert (LocationLog.it(this, lai.location));
/* 296 */       return lai.location;
/*     */     }
/* 298 */     return null;
/*     */   }
/*     */ 
/*     */   protected boolean setObjectLocation(Object obj, Object location)
/*     */   {
/* 308 */     if (obj == null) return false;
/* 309 */     if (location == null) return false;
/*     */ 
/* 311 */     Bag canUse = null;
/*     */ 
/* 314 */     LocationAndIndex lai = (LocationAndIndex)this.locationAndIndexHash.get(obj);
/* 315 */     if (lai != null)
/*     */     {
/* 318 */       if (lai.location.equals(location)) return true;
/*     */ 
/* 321 */       Bag objs = lai.otherObjectsAtLocation;
/* 322 */       objs.remove(obj);
/*     */ 
/* 326 */       int objsNumObjs = objs.numObjs;
/* 327 */       if ((this.removeEmptyBags) && (objsNumObjs == 0))
/*     */       {
/* 329 */         this.objectHash.remove(lai.location);
/*     */ 
/* 331 */         canUse = objs;
/*     */       }
/* 333 */       else if ((this.replaceLargeBags) && (objsNumObjs >= 32) && (objsNumObjs * 4 <= objs.objs.length)) {
/* 334 */         objs.shrink(objsNumObjs * 2);
/*     */       }
/*     */ 
/* 338 */       assert (LocationLog.it(this, lai.location));
/* 339 */       lai.location = location;
/*     */     }
/*     */     else
/*     */     {
/* 344 */       this.allObjects.add(obj);
/*     */ 
/* 347 */       this.locationAndIndexHash.put(obj, lai = new LocationAndIndex(location, this.allObjects.numObjs - 1));
/*     */     }
/*     */ 
/* 351 */     assert (LocationLog.it(this, location));
/* 352 */     Bag objs = (Bag)this.objectHash.get(location);
/* 353 */     if (objs == null)
/*     */     {
/* 356 */       if (canUse != null) canUse.clear(); else
/* 357 */         canUse = new Bag(16);
/* 358 */       canUse.add(obj);
/* 359 */       this.objectHash.put(location, objs = canUse);
/*     */     } else {
/* 361 */       objs.add(obj);
/* 362 */     }lai.otherObjectsAtLocation = objs;
/*     */ 
/* 364 */     return true;
/*     */   }
/*     */ 
/*     */   public final Bag getAllObjects()
/*     */   {
/* 372 */     return this.allObjects;
/*     */   }
/*     */ 
/*     */   public Bag getObjectsAtLocations(Bag locations, Bag result)
/*     */   {
/* 379 */     if (result == null) result = new Bag();
/* 380 */     Object[] objs = locations.objs;
/* 381 */     int len = locations.numObjs;
/* 382 */     for (int i = 0; i < len; i++)
/*     */     {
/* 386 */       Bag temp = getObjectsAtLocation(objs[i]);
/* 387 */       if (temp != null)
/*     */       {
/* 389 */         int n = temp.numObjs;
/* 390 */         if (n == 1) result.add(temp.objs[0]); else
/* 391 */           result.addAll(temp);
/*     */       }
/*     */     }
/* 394 */     return result;
/*     */   }
/*     */ 
/*     */   public Iterator iterator()
/*     */   {
/* 412 */     final Iterator i = this.allObjects.iterator();
/* 413 */     return new Iterator() {
/*     */       public boolean hasNext() {
/* 415 */         return i.hasNext(); } 
/* 416 */       public Object next() { return i.next(); } 
/* 417 */       public void remove() { throw new IllegalStateException("Remove not supported in SparseField.iterator()"); }
/*     */ 
/*     */     };
/*     */   }
/*     */ 
/*     */   public Iterator locationBagIterator()
/*     */   {
/* 425 */     final Iterator i = this.objectHash.values().iterator();
/* 426 */     return new Iterator() {
/*     */       public boolean hasNext() {
/* 428 */         return i.hasNext(); } 
/* 429 */       public Object next() { return i.next(); } 
/* 430 */       public void remove() { throw new IllegalStateException("Remove not supported in SparseField.iterator()"); }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static class LocationAndIndex implements Serializable
/*     */   {
/*     */     Object location;
/*     */     int index;
/*     */     Bag otherObjectsAtLocation;
/*     */ 
/*     */     public Object getLocation() {
/* 443 */       return this.location; } 
/* 444 */     public int getIndex() { return this.index; }
/*     */ 
/*     */     public LocationAndIndex(Object location, int index)
/*     */     {
/* 448 */       this.location = location;
/* 449 */       this.index = index;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.field.SparseField
 * JD-Core Version:    0.6.2
 */