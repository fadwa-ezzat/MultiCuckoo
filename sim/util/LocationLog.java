/*    */ package sim.util;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import sim.engine.Steppable;
/*    */ 
/*    */ public class LocationLog
/*    */ {
/*    */   static ThreadLocal local;
/*    */   public static boolean assertsEnabled;
/*    */   public static boolean propertyEnabled;
/*    */ 
/*    */   public static boolean test()
/*    */   {
/* 19 */     return true;
/*    */   }
/*    */ 
/*    */   public static boolean set(Steppable agent)
/*    */   {
/* 24 */     if (propertyEnabled)
/* 25 */       local.set(agent);
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */   public static boolean clear()
/*    */   {
/* 31 */     if (propertyEnabled)
/* 32 */       local.remove();
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */   public static boolean it(Object field, Object location)
/*    */   {
/* 38 */     if (propertyEnabled)
/*    */     {
/* 40 */       Steppable agent = (Steppable)local.get();
/* 41 */       System.err.println("" + agent + "\t" + field + "\t" + location + "\t");
/*    */     }
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/*  9 */     local = new ThreadLocal();
/* 10 */     assertsEnabled = false;
/* 11 */     propertyEnabled = System.getProperty("LocationLog") != null;
/*    */ 
/* 14 */     assert ((LocationLog.assertsEnabled = 1) != 0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.LocationLog
 * JD-Core Version:    0.6.2
 */