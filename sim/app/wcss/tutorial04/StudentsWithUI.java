/*    */ package sim.app.wcss.tutorial04;
/*    */ 
/*    */ import sim.display.Console;
/*    */ import sim.display.GUIState;
/*    */ import sim.engine.SimState;
/*    */ 
/*    */ public class StudentsWithUI extends GUIState
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 16 */     StudentsWithUI vid = new StudentsWithUI();
/* 17 */     Console c = new Console(vid);
/* 18 */     c.setVisible(true);
/*    */   }
/*    */   public StudentsWithUI() {
/* 21 */     super(new Students(System.currentTimeMillis())); } 
/* 22 */   public StudentsWithUI(SimState state) { super(state); } 
/*    */   public static String getName() {
/* 24 */     return "WCSS 2008 Tutorial";
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial04.StudentsWithUI
 * JD-Core Version:    0.6.2
 */