/*     */ package sim.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class CollectionProperties extends Properties
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   Collection collection;
/*     */   Map map;
/*     */   Indexed indexed;
/*     */   boolean isVolatile;
/*     */ 
/*     */   public boolean isVolatile()
/*     */   {
/*  36 */     return this.isVolatile;
/*     */   }
/*     */ 
/*     */   public CollectionProperties(Object o)
/*     */   {
/*  45 */     if (o == null) throw new NullPointerException();
/*  46 */     if ((o instanceof Indexed)) buildCollectionProperties((Indexed)o);
/*  47 */     else if ((o instanceof List)) buildCollectionProperties((List)o);
/*  48 */     else if ((o instanceof Map)) buildCollectionProperties((Map)o);
/*  49 */     else if ((o instanceof Collection)) buildCollectionProperties((Collection)o);
/*  50 */     else if (o.getClass().isArray()) buildCollectionPropertiesForArray(o); else
/*  51 */       throw new IllegalArgumentException("Invalid type for collection properties: " + o);
/*  52 */     this.object = o;
/*     */   }
/*     */ 
/*     */   void buildCollectionProperties(Collection c)
/*     */   {
/*  57 */     this.isVolatile = true;
/*  58 */     this.collection = c;
/*  59 */     this.object = c;
/*     */   }
/*     */ 
/*     */   void buildCollectionProperties(final List list)
/*     */   {
/*  64 */     this.isVolatile = true;
/*  65 */     this.indexed = new Indexed() {
/*     */       public Class componentType() {
/*  67 */         return null; } 
/*  68 */       public int size() { return list.size(); } 
/*  69 */       public Object setValue(int index, Object value) { return list.set(index, value); } 
/*  70 */       public Object getValue(int index) { return list.get(index); }
/*     */ 
/*     */     };
/*  72 */     this.object = list;
/*     */   }
/*     */ 
/*     */   void buildCollectionProperties(Map m)
/*     */   {
/*  77 */     this.isVolatile = true;
/*  78 */     this.map = m;
/*  79 */     this.object = m;
/*     */   }
/*     */ 
/*     */   void buildCollectionProperties(Indexed i)
/*     */   {
/*  84 */     this.isVolatile = true;
/*  85 */     this.indexed = i;
/*  86 */     this.object = i;
/*     */   }
/*     */ 
/*     */   void buildCollectionPropertiesForArray(final Object o)
/*     */   {
/*  91 */     this.isVolatile = false;
/*     */ 
/*  93 */     this.indexed = new Indexed() {
/*     */       public Class componentType() {
/*  95 */         return o.getClass().getComponentType(); } 
/*  96 */       public int size() { return Array.getLength(o); }
/*     */ 
/*     */       public Object setValue(int index, Object value) {
/*  99 */         Object oldVal = getValue(index);
/* 100 */         Array.set(o, index, value);
/* 101 */         return oldVal;
/*     */       }
/* 103 */       public Object getValue(int index) { return Array.get(o, index); }
/*     */ 
/*     */     };
/* 106 */     this.object = o;
/*     */   }
/*     */ 
/*     */   public int numProperties()
/*     */   {
/* 111 */     if (this.indexed != null) return this.indexed.size();
/* 112 */     if (this.collection != null) return this.collection.size();
/* 113 */     return this.map.size();
/*     */   }
/*     */ 
/*     */   Iterator valueIterator()
/*     */   {
/* 119 */     if (this.collection != null) return this.collection.iterator();
/*     */ 
/* 122 */     Set s = this.map.entrySet();
/* 123 */     final Iterator i = s.iterator();
/* 124 */     return new Iterator() {
/*     */       public boolean hasNext() {
/* 126 */         return i.hasNext(); } 
/* 127 */       public Object next() { return ((Map.Entry)i.next()).getValue(); } 
/* 128 */       public void remove() { throw new UnsupportedOperationException("Cannot remove from a CollectionProperties Iterator"); }
/*     */ 
/*     */     };
/*     */   }
/*     */ 
/*     */   public Object getValue(int index)
/*     */   {
/* 136 */     if ((index < 0) || (index > numProperties())) return null;
/*     */ 
/* 138 */     if (this.indexed != null) return this.indexed.getValue(index);
/*     */ 
/* 141 */     Iterator i = valueIterator();
/* 142 */     Object obj = null;
/* 143 */     for (int x = 0; x <= index; x++)
/*     */     {
/* 145 */       if (!i.hasNext()) return null;
/* 146 */       obj = i.next();
/*     */     }
/* 148 */     return obj;
/*     */   }
/*     */ 
/*     */   public boolean isReadWrite(int index)
/*     */   {
/* 154 */     if ((index < 0) || (index > numProperties())) return false;
/*     */ 
/* 156 */     if (this.collection != null) return false;
/*     */ 
/* 158 */     Class type = getTypeConversion(getType(index));
/* 159 */     Object obj = getValue(index);
/* 160 */     if (obj != null)
/*     */     {
/* 162 */       if (!type.isAssignableFrom(obj.getClass()))
/* 163 */         return false;
/*     */     }
/* 165 */     return !isComposite(index);
/*     */   }
/*     */ 
/*     */   public String getName(int index)
/*     */   {
/* 170 */     if ((index < 0) || (index > numProperties())) return null;
/*     */ 
/* 172 */     if (this.map != null)
/*     */     {
/* 174 */       Iterator i = this.map.entrySet().iterator();
/* 175 */       Object obj = null;
/* 176 */       for (int x = 0; x <= index; x++)
/*     */       {
/* 178 */         if (!i.hasNext()) return null;
/* 179 */         obj = i.next();
/*     */       }
/* 181 */       return "" + ((Map.Entry)obj).getKey();
/*     */     }
/* 183 */     if (this.collection != null)
/*     */     {
/* 185 */       return "Member";
/*     */     }
/*     */ 
/* 189 */     return "" + index;
/*     */   }
/*     */ 
/*     */   public Class getType(int index)
/*     */   {
/* 195 */     if ((index < 0) || (index > numProperties())) return null;
/* 196 */     if (this.indexed != null)
/*     */     {
/* 198 */       Class type = this.indexed.componentType();
/* 199 */       if (type != null) return type;
/*     */     }
/* 201 */     Object obj = getValue(index);
/* 202 */     if (obj == null) return Object.class;
/* 203 */     return obj.getClass();
/*     */   }
/*     */ 
/*     */   protected Object _setValue(int index, Object value)
/*     */   {
/* 208 */     if ((index < 0) || (index > numProperties())) return null;
/*     */ 
/* 210 */     if (this.indexed != null) { this.indexed.setValue(index, value);
/* 211 */     } else if (this.map != null)
/*     */     {
/* 213 */       Iterator i = this.map.entrySet().iterator();
/* 214 */       Object obj = null;
/* 215 */       for (int x = 0; x <= index; x++)
/*     */       {
/* 217 */         if (!i.hasNext()) return null;
/* 218 */         obj = i.next();
/*     */       }
/* 220 */       this.map.put(((Map.Entry)obj).getKey(), value);
/*     */     }
/*     */ 
/* 223 */     return getValue(index);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.CollectionProperties
 * JD-Core Version:    0.6.2
 */