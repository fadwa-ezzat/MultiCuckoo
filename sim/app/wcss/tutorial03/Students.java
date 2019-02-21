/*    */ package sim.app.wcss.tutorial03;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public class Students extends SimState
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 16 */   public Continuous2D yard = new Continuous2D(1.0D, 100.0D, 100.0D);
/*    */ 
/* 18 */   public int numStudents = 50;
/*    */ 
/* 20 */   double forceToSchoolMultiplier = 0.01D;
/* 21 */   double randomMultiplier = 0.1D;
/*    */ 
/*    */   public Students(long seed)
/*    */   {
/* 25 */     super(seed);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 30 */     super.start();
/*    */ 
/* 33 */     this.yard.clear();
/*    */ 
/* 36 */     for (int i = 0; i < this.numStudents; i++)
/*    */     {
/* 38 */       Student student = new Student();
/* 39 */       this.yard.setObjectLocation(student, new Double2D(this.yard.getWidth() * 0.5D + this.random.nextDouble() - 0.5D, this.yard.getHeight() * 0.5D + this.random.nextDouble() - 0.5D));
/*    */ 
/* 43 */       this.schedule.scheduleRepeating(student);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 49 */     doLoop(Students.class, args);
/* 50 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial03.Students
 * JD-Core Version:    0.6.2
 */