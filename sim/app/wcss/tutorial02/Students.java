/*    */ package sim.app.wcss.tutorial02;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
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
/*    */   public Students(long seed)
/*    */   {
/* 22 */     super(seed);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 27 */     super.start();
/*    */ 
/* 30 */     this.yard.clear();
/*    */ 
/* 33 */     for (int i = 0; i < this.numStudents; i++)
/*    */     {
/* 35 */       Student student = new Student();
/* 36 */       this.yard.setObjectLocation(student, new Double2D(this.yard.getWidth() * 0.5D + this.random.nextDouble() - 0.5D, this.yard.getHeight() * 0.5D + this.random.nextDouble() - 0.5D));
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 44 */     doLoop(Students.class, args);
/* 45 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial02.Students
 * JD-Core Version:    0.6.2
 */