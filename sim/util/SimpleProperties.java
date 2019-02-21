/*     */ package sim.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class SimpleProperties extends Properties
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/* 117 */   ArrayList getMethods = new ArrayList();
/* 118 */   ArrayList setMethods = new ArrayList();
/* 119 */   ArrayList domMethods = new ArrayList();
/* 120 */   ArrayList desMethods = new ArrayList();
/* 121 */   ArrayList hideMethods = new ArrayList();
/* 122 */   ArrayList nameMethods = new ArrayList();
/* 123 */   Properties auxillary = null;
/*     */ 
/*     */   public SimpleProperties(Object o)
/*     */   {
/* 129 */     this(o, true, false, true);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public SimpleProperties(Object o, boolean includeSuperclasses, boolean includeGetClass)
/*     */   {
/* 140 */     this(o, includeSuperclasses, includeGetClass, true);
/*     */   }
/*     */ 
/*     */   public SimpleProperties(Object o, boolean includeSuperclasses, boolean includeGetClass, boolean includeExtensions)
/*     */   {
/* 151 */     this.object = o;
/* 152 */     if ((o != null) && ((o instanceof Proxiable)))
/* 153 */       this.object = ((Proxiable)o).propertiesProxy();
/* 154 */     else if ((o != null) && ((o instanceof Propertied)))
/* 155 */       this.auxillary = ((Propertied)o).properties();
/* 156 */     generateProperties(includeSuperclasses, includeGetClass, includeExtensions);
/*     */   }
/*     */ 
/*     */   void generateProperties(boolean includeSuperclasses, boolean includeGetClass, boolean includeExtensions)
/*     */   {
/* 161 */     if ((this.object != null) && (this.auxillary == null))
/*     */     {
/* 164 */       Class c = this.object.getClass();
/*     */       try
/*     */       {
/* 169 */         if (((this.object instanceof Long)) || ((this.object instanceof Integer)) || ((this.object instanceof Short)) || ((this.object instanceof Byte)))
/*     */         {
/* 171 */           Method meth = c.getMethod("longValue", new Class[0]);
/* 172 */           this.getMethods.add(meth);
/* 173 */           this.setMethods.add(null);
/* 174 */           this.domMethods.add(null);
/* 175 */           this.hideMethods.add(null);
/* 176 */           this.desMethods.add(null);
/* 177 */           this.nameMethods.add(null);
/*     */         }
/* 181 */         else if ((this.object instanceof Number))
/*     */         {
/* 183 */           Method meth = c.getMethod("doubleValue", new Class[0]);
/* 184 */           this.getMethods.add(meth);
/* 185 */           this.setMethods.add(null);
/* 186 */           this.domMethods.add(null);
/* 187 */           this.hideMethods.add(null);
/* 188 */           this.desMethods.add(null);
/* 189 */           this.nameMethods.add(null);
/*     */         }
/*     */ 
/* 193 */         if ((this.object instanceof Boolean))
/*     */         {
/* 195 */           Method meth = c.getMethod("booleanValue", new Class[0]);
/* 196 */           this.getMethods.add(meth);
/* 197 */           this.setMethods.add(null);
/* 198 */           this.domMethods.add(null);
/* 199 */           this.hideMethods.add(null);
/* 200 */           this.desMethods.add(null);
/* 201 */           this.nameMethods.add(null);
/*     */         }
/*     */ 
/* 205 */         if ((this.object instanceof CharSequence))
/*     */         {
/* 207 */           Method meth = c.getMethod("toString", new Class[0]);
/* 208 */           this.getMethods.add(meth);
/* 209 */           this.setMethods.add(null);
/* 210 */           this.domMethods.add(null);
/* 211 */           this.hideMethods.add(null);
/* 212 */           this.desMethods.add(null);
/* 213 */           this.nameMethods.add(null);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 218 */         e.printStackTrace();
/*     */       }
/*     */ 
/* 222 */       Method[] m = includeSuperclasses ? c.getMethods() : c.getDeclaredMethods();
/* 223 */       for (int x = 0; x < m.length; x++)
/*     */       {
/*     */         try
/*     */         {
/* 227 */           if ((!"get".equals(m[x].getName())) && (!"is".equals(m[x].getName())) && ((m[x].getName().startsWith("get")) || (m[x].getName().startsWith("is"))))
/*     */           {
/* 230 */             int modifier = m[x].getModifiers();
/* 231 */             if (((includeGetClass) || (!m[x].getName().equals("getClass"))) && (m[x].getParameterTypes().length == 0) && (Modifier.isPublic(modifier)))
/*     */             {
/* 236 */               Class returnType = m[x].getReturnType();
/* 237 */               if (returnType != Void.TYPE)
/*     */               {
/* 239 */                 this.getMethods.add(m[x]);
/* 240 */                 this.setMethods.add(getWriteProperty(m[x], c));
/* 241 */                 this.domMethods.add(getDomain(m[x], c, includeExtensions));
/* 242 */                 this.hideMethods.add(getHidden(m[x], c, includeExtensions));
/* 243 */                 this.desMethods.add(getDescription(m[x], c, includeExtensions));
/* 244 */                 this.nameMethods.add(getName(m[x], c, includeExtensions));
/*     */ 
/* 247 */                 int lastIndex = this.domMethods.size() - 1;
/* 248 */                 Object domain = getDomain(lastIndex);
/* 249 */                 if ((returnType == Float.TYPE) || (returnType == Double.TYPE))
/*     */                 {
/* 251 */                   if ((domain != null) && ((domain instanceof Interval)))
/*     */                   {
/* 253 */                     Interval interval = (Interval)domain;
/* 254 */                     if (!interval.isDouble())
/*     */                     {
/* 256 */                       System.err.println("WARNING: Property is double or float valued, but the Interval provided for the property's domain is byte/short/integer/long valued: " + getName(lastIndex) + " on Object " + this.object);
/*     */ 
/* 259 */                       this.domMethods.set(lastIndex, null);
/*     */                     }
/*     */                   }
/*     */                 }
/* 263 */                 else if ((returnType == Byte.TYPE) || (returnType == Short.TYPE) || (returnType == Integer.TYPE) || (returnType == Long.TYPE))
/*     */                 {
/* 265 */                   if ((domain != null) && ((domain instanceof Interval)))
/*     */                   {
/* 267 */                     Interval interval = (Interval)domain;
/* 268 */                     if (interval.isDouble())
/*     */                     {
/* 270 */                       System.err.println("WARNING: Property is byte/short/integer/long valued, but the Interval provided for the property's domain is double or float valued: " + getName(lastIndex) + " on Object " + this.object);
/*     */ 
/* 273 */                       this.domMethods.set(lastIndex, null);
/*     */                     }
/*     */                   }
/*     */                 }
/* 277 */                 else if ((domain != null) && ((domain instanceof Interval)))
/*     */                 {
/* 279 */                   System.err.println("WARNING: Property is not a basic number type, but an Interval was provided for the property's domain: " + getName(lastIndex) + " on Object " + this.object);
/*     */ 
/* 282 */                   this.domMethods.set(lastIndex, null);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Exception e1)
/*     */         {
/* 290 */           e1.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   Method getHidden(Method m, Class c, boolean includeExtensions)
/*     */   {
/* 301 */     if (!includeExtensions) return null;
/*     */     try
/*     */     {
/* 304 */       if (m.getName().startsWith("get"))
/*     */       {
/* 306 */         Method m2 = c.getMethod("hide" + m.getName().substring(3), new Class[0]);
/* 307 */         if (m2.getReturnType() == Boolean.TYPE) return m2;
/*     */       }
/* 309 */       else if (m.getName().startsWith("is"))
/*     */       {
/* 311 */         Method m2 = c.getMethod("hide" + m.getName().substring(2), new Class[0]);
/* 312 */         if (m2.getReturnType() == Boolean.TYPE) return m2;
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */ 
/* 319 */     return null;
/*     */   }
/*     */ 
/*     */   Method getWriteProperty(Method m, Class c)
/*     */   {
/*     */     try
/*     */     {
/* 326 */       if (m.getName().startsWith("get"))
/*     */       {
/* 328 */         return c.getMethod("set" + m.getName().substring(3), new Class[] { m.getReturnType() });
/*     */       }
/* 330 */       if (m.getName().startsWith("is"))
/*     */       {
/* 332 */         return c.getMethod("set" + m.getName().substring(2), new Class[] { m.getReturnType() });
/*     */       }
/* 334 */       return null;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/* 339 */     return null;
/*     */   }
/*     */ 
/*     */   Method getDomain(Method m, Class c, boolean includeExtensions)
/*     */   {
/* 345 */     if (!includeExtensions) return null;
/*     */     try
/*     */     {
/* 348 */       if (m.getName().startsWith("get"))
/*     */       {
/* 350 */         return c.getMethod("dom" + m.getName().substring(3), new Class[0]);
/*     */       }
/* 352 */       if (m.getName().startsWith("is"))
/*     */       {
/* 354 */         return c.getMethod("dom" + m.getName().substring(2), new Class[0]);
/*     */       }
/* 356 */       return null;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/* 361 */     return null;
/*     */   }
/*     */ 
/*     */   Method getDescription(Method m, Class c, boolean includeExtensions)
/*     */   {
/* 367 */     if (!includeExtensions) return null;
/*     */     try
/*     */     {
/* 370 */       if (m.getName().startsWith("get"))
/*     */       {
/* 372 */         return c.getMethod("des" + m.getName().substring(3), new Class[0]);
/*     */       }
/* 374 */       if (m.getName().startsWith("is"))
/*     */       {
/* 376 */         return c.getMethod("des" + m.getName().substring(2), new Class[0]);
/*     */       }
/* 378 */       return null;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/* 383 */     return null;
/*     */   }
/*     */ 
/*     */   Method getName(Method m, Class c, boolean includeExtensions)
/*     */   {
/* 389 */     if (!includeExtensions) return null;
/*     */     try
/*     */     {
/* 392 */       if (m.getName().startsWith("get"))
/*     */       {
/* 394 */         return c.getMethod("name" + m.getName().substring(3), new Class[0]);
/*     */       }
/* 396 */       if (m.getName().startsWith("is"))
/*     */       {
/* 398 */         return c.getMethod("name" + m.getName().substring(2), new Class[0]);
/*     */       }
/* 400 */       return null;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/* 405 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isVolatile() {
/* 409 */     if (this.auxillary != null) return this.auxillary.isVolatile(); return false;
/*     */   }
/*     */ 
/*     */   public int numProperties()
/*     */   {
/* 414 */     if (this.auxillary != null) return this.auxillary.numProperties();
/* 415 */     return this.getMethods.size();
/*     */   }
/*     */ 
/*     */   public String getName(int index)
/*     */   {
/* 422 */     if (this.auxillary != null) return this.auxillary.getName(index);
/* 423 */     if ((index < 0) || (index >= numProperties())) return null;
/*     */ 
/*     */     try
/*     */     {
/* 427 */       if (this.nameMethods.get(index) != null)
/* 428 */         return (String)((Method)this.nameMethods.get(index)).invoke(this.object, new Object[0]);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 432 */       e.printStackTrace();
/* 433 */       return null;
/*     */     }
/*     */ 
/* 436 */     String name = ((Method)this.getMethods.get(index)).getName();
/* 437 */     if (name.startsWith("is"))
/* 438 */       return name.substring(2);
/* 439 */     if (name.equals("longValue"))
/* 440 */       return "Value";
/* 441 */     if (name.equals("doubleValue"))
/* 442 */       return "Value";
/* 443 */     if (name.equals("booleanValue"))
/* 444 */       return "Value";
/* 445 */     if (name.equals("toString"))
/* 446 */       return "Value";
/* 447 */     return name.substring(3);
/*     */   }
/*     */ 
/*     */   public boolean isReadWrite(int index)
/*     */   {
/* 454 */     if (this.auxillary != null) return this.auxillary.isReadWrite(index);
/* 455 */     if ((index < 0) || (index >= numProperties())) return false;
/* 456 */     if (isComposite(index)) return false;
/* 457 */     return this.setMethods.get(index) != null;
/*     */   }
/*     */ 
/*     */   public Class getType(int index)
/*     */   {
/* 464 */     if (this.auxillary != null) return this.auxillary.getType(index);
/* 465 */     if ((index < 0) || (index >= numProperties())) return null;
/* 466 */     Class returnType = ((Method)this.getMethods.get(index)).getReturnType();
/*     */ 
/* 468 */     return getTypeConversion(returnType);
/*     */   }
/*     */ 
/*     */   public Object getValue(int index)
/*     */   {
/* 476 */     if (this.auxillary != null) return this.auxillary.getValue(index);
/* 477 */     if ((index < 0) || (index >= numProperties())) return null;
/*     */     try
/*     */     {
/* 480 */       return ((Method)this.getMethods.get(index)).invoke(this.object, new Object[0]);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 484 */       e.printStackTrace();
/* 485 */     }return null;
/*     */   }
/*     */ 
/*     */   protected Object _setValue(int index, Object value)
/*     */   {
/* 491 */     if (this.auxillary != null) return this.auxillary.setValue(index, value);
/*     */     try
/*     */     {
/* 494 */       if (this.setMethods.get(index) == null) return null;
/* 495 */       ((Method)this.setMethods.get(index)).invoke(this.object, new Object[] { value });
/* 496 */       return getValue(index);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 500 */       e.printStackTrace();
/* 501 */     }return null;
/*     */   }
/*     */ 
/*     */   public String getDescription(int index)
/*     */   {
/* 507 */     if (this.auxillary != null) return this.auxillary.getDescription(index);
/* 508 */     if ((index < 0) || (index >= numProperties())) return null;
/*     */     try
/*     */     {
/* 511 */       if (this.desMethods.get(index) == null) return null;
/* 512 */       return (String)((Method)this.desMethods.get(index)).invoke(this.object, new Object[0]);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 516 */       e.printStackTrace();
/* 517 */     }return null;
/*     */   }
/*     */ 
/*     */   public Object getDomain(int index)
/*     */   {
/* 523 */     if (this.auxillary != null) return this.auxillary.getDomain(index);
/* 524 */     if ((index < 0) || (index >= numProperties())) return null;
/*     */     try
/*     */     {
/* 527 */       if (this.domMethods.get(index) == null) return null;
/* 528 */       return ((Method)this.domMethods.get(index)).invoke(this.object, new Object[0]);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 532 */       e.printStackTrace();
/* 533 */     }return null;
/*     */   }
/*     */ 
/*     */   public boolean isHidden(int index)
/*     */   {
/* 539 */     if (this.auxillary != null) return this.auxillary.isHidden(index);
/* 540 */     if ((index < 0) || (index >= numProperties())) return false;
/*     */     try
/*     */     {
/* 543 */       if (this.hideMethods.get(index) == null) return false;
/* 544 */       return ((Boolean)((Method)this.hideMethods.get(index)).invoke(this.object, new Object[0])).booleanValue();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 548 */       e.printStackTrace();
/* 549 */     }return false;
/*     */   }
/*     */ 
/*     */   SimpleProperties()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SimpleProperties getPropertiesSubset(String[] propertyNames, boolean retain)
/*     */   {
/* 568 */     if (this.auxillary != null) {
/* 569 */       throw new RuntimeException("Properties may not be reduced if the SimpleProperties has an auxillary.");
/*     */     }
/* 571 */     SimpleProperties props = new SimpleProperties() {
/*     */       public boolean isVolatile() {
/* 573 */         return SimpleProperties.this.isVolatile();
/*     */       }
/*     */     };
/* 575 */     props.object = this.object;
/*     */ 
/* 581 */     boolean[] found = new boolean[propertyNames.length];
/* 582 */     for (int index = 0; index < numProperties(); index++)
/*     */     {
/* 585 */       for (int i = 0; i < propertyNames.length; i++)
/*     */       {
/* 587 */         if (getName(index).equals(propertyNames[i])) {
/* 588 */           found[i] = true; break;
/*     */         }
/*     */       }
/* 591 */       if (((i < propertyNames.length) && (retain)) || ((i >= propertyNames.length) && (!retain)))
/*     */       {
/* 594 */         props.getMethods.add(this.getMethods.get(index));
/* 595 */         props.setMethods.add(this.setMethods.get(index));
/* 596 */         props.domMethods.add(this.domMethods.get(index));
/* 597 */         props.desMethods.add(this.desMethods.get(index));
/* 598 */         props.hideMethods.add(this.hideMethods.get(index));
/* 599 */         props.nameMethods.add(this.nameMethods.get(index));
/*     */       }
/*     */     }
/*     */ 
/* 603 */     if (retain)
/*     */     {
/* 605 */       for (int i = 0; i < found.length; i++)
/*     */       {
/* 607 */         if (found[i] == 0) {
/* 608 */           throw new RuntimeException("Unknown property name " + propertyNames[i] + " in object " + this.object);
/*     */         }
/*     */       }
/*     */     }
/* 612 */     return props;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 617 */     String s = "{ ";
/* 618 */     for (int i = 0; i < numProperties(); i++)
/*     */     {
/* 620 */       if (i > 0) s = s + ", ";
/* 621 */       s = s + getName(i);
/*     */     }
/* 623 */     s = s + "}";
/* 624 */     return s;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.SimpleProperties
 * JD-Core Version:    0.6.2
 */