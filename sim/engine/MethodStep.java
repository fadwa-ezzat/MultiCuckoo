/*     */ package sim.engine;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ public class MethodStep
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   Method method;
/*     */   Object object;
/*     */   boolean passInSimState;
/*     */ 
/*     */   public MethodStep(Object object, String methodName)
/*     */   {
/*  70 */     this(object, methodName, false);
/*     */   }
/*     */ 
/*     */   public MethodStep(Object object, String methodName, boolean passInSimState)
/*     */   {
/*  75 */     this.object = object;
/*  76 */     this.passInSimState = passInSimState;
/*  77 */     if (object == null)
/*     */     {
/*  79 */       throw new NullPointerException("MethodStep asked to call the method " + methodName + (passInSimState ? "\"(SimState state)" : "\"") + " on a null object");
/*     */     }
/*     */     try
/*     */     {
/*  83 */       if (passInSimState)
/*  84 */         this.method = object.getClass().getMethod(methodName, new Class[] { SimState.class });
/*     */       else
/*  86 */         this.method = object.getClass().getMethod(methodName, new Class[0]);
/*     */     }
/*     */     catch (NoSuchMethodException ex)
/*     */     {
/*  90 */       throw new RuntimeException("Could not find a public method called \"" + methodName + (passInSimState ? "\"(SimState state)" : "\"") + " in the class " + object.getClass());
/*     */     }
/*     */     catch (SecurityException ex)
/*     */     {
/*  94 */       throw new RuntimeException("Could not find a public method called \"" + methodName + (passInSimState ? "\"(SimState state)" : "\"") + " in the class " + object.getClass());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/*     */     try
/*     */     {
/* 102 */       if (this.passInSimState) this.method.invoke(this.object, new Object[] { state }); else {
/* 103 */         this.method.invoke(this.object, (Object[])null);
/*     */       }
/*     */     }
/*     */     catch (IllegalAccessException ex)
/*     */     {
/* 108 */       throw new RuntimeException("Could not find a public method called \"" + this.method + (this.passInSimState ? "\"(SimState state)" : "\"") + " in the class " + this.object.getClass());
/*     */     }
/*     */     catch (IllegalArgumentException ex)
/*     */     {
/* 112 */       throw new RuntimeException("Could not find a public method called \"" + this.method + (this.passInSimState ? "\"(SimState state)" : "\"") + " in the class " + this.object.getClass());
/*     */     }
/*     */     catch (InvocationTargetException ex)
/*     */     {
/* 123 */       throw new RuntimeException("On calling \"" + this.method + (this.passInSimState ? "\"(SimState state)" : "\"") + " in the class " + this.object.getClass() + ", an Exception was raised in the called method.", ex);
/*     */     }
/*     */     catch (NullPointerException ex)
/*     */     {
/* 129 */       throw new NullPointerException("MethodStep asked to call the method " + this.method + (this.passInSimState ? "\"(SimState state)" : "\"") + " on a null object");
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.engine.MethodStep
 * JD-Core Version:    0.6.2
 */