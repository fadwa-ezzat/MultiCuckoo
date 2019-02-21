/*    */ package sim.app.wcss.tutorial01;
/*    */ 
/*    */ import sim.engine.SimState;
/*    */ 
/*    */ public class Students extends SimState
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Students(long seed)
/*    */   {
/* 16 */     super(seed);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 21 */     super.start();
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 26 */     doLoop(Students.class, args);
/* 27 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial01.Students
 * JD-Core Version:    0.6.2
 */