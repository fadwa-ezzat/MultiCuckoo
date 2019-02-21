/*    */ package sim.display;
/*    */ 
/*    */ import java.util.prefs.BackingStoreException;
/*    */ import java.util.prefs.Preferences;
/*    */ 
/*    */ public class Prefs
/*    */ {
/*    */   public static final String MASON_PREFERENCES = "edu/gmu/mason/global/";
/*    */   public static final String APP_PREFERENCES = "edu/gmu/mason/app/";
/*    */ 
/*    */   public static Preferences getGlobalPreferences(String namespace)
/*    */   {
/* 38 */     return Preferences.userRoot().node("edu/gmu/mason/global/" + namespace);
/*    */   }
/*    */ 
/*    */   public static Preferences getAppPreferences(GUIState simulation, String namespace)
/*    */   {
/* 44 */     return Preferences.userRoot().node("edu/gmu/mason/app/" + simulation.getClass().getName().replace('.', '/') + "/" + namespace);
/*    */   }
/*    */ 
/*    */   public static boolean removeGlobalPreferences(String namespace)
/*    */   {
/*    */     try
/*    */     {
/* 53 */       getGlobalPreferences(namespace).removeNode();
/* 54 */       return true;
/*    */     }
/*    */     catch (BackingStoreException e) {
/*    */     }
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean removeAppPreferences(GUIState simulation, String namespace)
/*    */   {
/*    */     try
/*    */     {
/* 67 */       getAppPreferences(simulation, namespace).removeNode();
/* 68 */       return true;
/*    */     }
/*    */     catch (BackingStoreException e) {
/*    */     }
/* 72 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean save(Preferences prefs)
/*    */   {
/*    */     try
/*    */     {
/* 81 */       prefs.flush();
/* 82 */       return true;
/*    */     }
/*    */     catch (BackingStoreException ex) {
/*    */     }
/* 86 */     return false;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.display.Prefs
 * JD-Core Version:    0.6.2
 */