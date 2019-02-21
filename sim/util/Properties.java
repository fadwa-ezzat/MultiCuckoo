/*     */ package sim.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class Properties
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected Object object;
/* 215 */   NumberFormat format = NumberFormat.getInstance();
/*     */ 
/*     */   public static Properties getProperties(Object object)
/*     */   {
/*  43 */     return getProperties(object, true, true, false, true);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static Properties getProperties(Object object, boolean expandCollections, boolean includeSuperclasses, boolean includeGetClass)
/*     */   {
/*  58 */     return getProperties(object, expandCollections, includeSuperclasses, includeGetClass, true);
/*     */   }
/*     */ 
/*     */   public static Properties getProperties(Object object, boolean expandCollections, boolean includeSuperclasses, boolean includeGetClass, boolean includeExtensions)
/*     */   {
/*  71 */     if (object == null) return new SimpleProperties(object, includeSuperclasses, includeGetClass, includeExtensions);
/*  72 */     Class c = object.getClass();
/*  73 */     if (c.isArray()) return new CollectionProperties(object);
/*  74 */     if ((expandCollections) && ((Collection.class.isAssignableFrom(c)) || (Indexed.class.isAssignableFrom(c)) || (Map.class.isAssignableFrom(c))))
/*     */     {
/*  77 */       return new CollectionProperties(object);
/*  78 */     }return new SimpleProperties(object, includeSuperclasses, includeGetClass, includeExtensions);
/*     */   }
/*     */ 
/*     */   public Object getObject()
/*     */   {
/*  83 */     return this.object;
/*     */   }
/*     */ 
/*     */   public abstract boolean isVolatile();
/*     */ 
/*     */   public abstract int numProperties();
/*     */ 
/*     */   public abstract Object getValue(int paramInt);
/*     */ 
/*     */   public Object getDomain(int index)
/*     */   {
/* 108 */     return null;
/*     */   }
/*     */ 
/*     */   public String getDescription(int index)
/*     */   {
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */   public abstract boolean isReadWrite(int paramInt);
/*     */ 
/*     */   public boolean isComposite(int index)
/*     */   {
/* 124 */     if ((index < 0) || (index > numProperties())) return false;
/* 125 */     Class type = getTypeConversion(getType(index));
/* 126 */     return (!type.isPrimitive()) && (type != String.class);
/*     */   }
/*     */ 
/*     */   public boolean isHidden(int index) {
/* 130 */     return false;
/*     */   }
/*     */ 
/*     */   public abstract String getName(int paramInt);
/*     */ 
/*     */   public abstract Class getType(int paramInt);
/*     */ 
/*     */   protected abstract Object _setValue(int paramInt, Object paramObject);
/*     */ 
/*     */   public Object setValue(int index, Object value)
/*     */   {
/* 146 */     return _setValue(index, value);
/*     */   }
/*     */ 
/*     */   public Object setValue(int index, String value)
/*     */   {
/*     */     try
/*     */     {
/* 156 */       Class type = getType(index);
/* 157 */       if (type == Boolean.TYPE) return _setValue(index, Boolean.valueOf(value));
/* 158 */       if (type == Byte.TYPE) {
/*     */         try {
/* 160 */           return _setValue(index, Byte.valueOf(value));
/*     */         }
/*     */         catch (NumberFormatException e) {
/* 163 */           double d = Double.parseDouble(value);
/* 164 */           byte b = (byte)(int)d;
/* 165 */           if (b == d) return _setValue(index, Byte.valueOf(b));
/* 166 */           throw e;
/*     */         }
/*     */       }
/* 169 */       if (type == Short.TYPE) {
/*     */         try {
/* 171 */           return _setValue(index, Short.valueOf(value));
/*     */         }
/*     */         catch (NumberFormatException e) {
/* 174 */           double d = Double.parseDouble(value);
/* 175 */           short b = (short)(int)d;
/* 176 */           if (b == d) return _setValue(index, Short.valueOf(b));
/* 177 */           throw e;
/*     */         }
/*     */       }
/* 180 */       if (type == Integer.TYPE) {
/*     */         try {
/* 182 */           return _setValue(index, Integer.valueOf(value));
/*     */         }
/*     */         catch (NumberFormatException e) {
/* 185 */           double d = Double.parseDouble(value);
/* 186 */           int b = (int)d;
/* 187 */           if (b == d) return _setValue(index, Integer.valueOf(b));
/* 188 */           throw e;
/*     */         }
/*     */       }
/* 191 */       if (type == Long.TYPE) {
/*     */         try {
/* 193 */           return _setValue(index, Long.valueOf(value));
/*     */         }
/*     */         catch (NumberFormatException e) {
/* 196 */           double d = Double.parseDouble(value);
/* 197 */           long b = ()d;
/* 198 */           if (b == d) return _setValue(index, Long.valueOf(b));
/* 199 */           throw e;
/*     */         }
/*     */       }
/* 202 */       if (type == Float.TYPE) return _setValue(index, Float.valueOf(value));
/* 203 */       if (type == Double.TYPE) return _setValue(index, betterDoubleValueOf(value));
/* 204 */       if (type == Character.TYPE) return _setValue(index, new Character(value.charAt(0)));
/* 205 */       if (type == String.class) return _setValue(index, value);
/* 206 */       return null;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 210 */       e.printStackTrace();
/* 211 */     }return null;
/*     */   }
/*     */ 
/*     */   Double betterDoubleValueOf(String s)
/*     */     throws ParseException
/*     */   {
/* 235 */     return Double.valueOf(this.format.parse(s).doubleValue());
/*     */   }
/*     */ 
/*     */   protected Class getTypeConversion(Class type)
/*     */   {
/* 252 */     if ((type == Boolean.class) || (type == Boolean.TYPE))
/* 253 */       return Boolean.TYPE;
/* 254 */     if ((type == Byte.class) || (type == Byte.TYPE))
/* 255 */       return Byte.TYPE;
/* 256 */     if ((type == Short.class) || (type == Short.TYPE))
/* 257 */       return Short.TYPE;
/* 258 */     if ((type == Integer.class) || (type == Integer.TYPE))
/* 259 */       return Integer.TYPE;
/* 260 */     if ((type == Long.class) || (type == Long.TYPE))
/* 261 */       return Long.TYPE;
/* 262 */     if ((type == Float.class) || (type == Float.TYPE))
/* 263 */       return Float.TYPE;
/* 264 */     if ((type == Double.class) || (type == Double.TYPE))
/* 265 */       return Double.TYPE;
/* 266 */     if ((type == Character.class) || (type == Character.TYPE))
/* 267 */       return Character.TYPE;
/* 268 */     return type;
/*     */   }
/*     */ 
/*     */   public String betterToString(Object obj)
/*     */   {
/* 274 */     if (obj == null) return "null";
/* 275 */     Class c = obj.getClass();
/* 276 */     if (c.isArray()) return typeToName(c) + "@" + Integer.toHexString(obj.hashCode());
/* 277 */     return "" + obj;
/*     */   }
/*     */ 
/*     */   protected String typeToName(Class type)
/*     */   {
/* 282 */     if (type == null) return null;
/*     */ 
/* 284 */     if (type.isPrimitive())
/*     */     {
/* 286 */       return type.toString();
/*     */     }
/* 288 */     if (type == String.class)
/*     */     {
/* 290 */       return "String";
/*     */     }
/* 292 */     if (type.isArray())
/*     */     {
/* 294 */       Class componentType = type.getComponentType();
/*     */ 
/* 296 */       Class convertedComponentType = getTypeConversion(componentType);
/* 297 */       return typeToName(convertedComponentType) + "[]";
/*     */     }
/*     */ 
/* 300 */     return null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.Properties
 * JD-Core Version:    0.6.2
 */