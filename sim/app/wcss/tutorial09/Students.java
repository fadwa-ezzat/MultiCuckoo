/*    */ package sim.app.wcss.tutorial09;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.field.network.Network;
/*    */ import sim.util.Bag;
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public class Students extends SimState
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 17 */   public Continuous2D yard = new Continuous2D(1.0D, 100.0D, 100.0D);
/*    */ 
/* 19 */   public int numStudents = 50;
/*    */ 
/* 21 */   double forceToSchoolMultiplier = 0.01D;
/* 22 */   double randomMultiplier = 0.1D;
/*    */ 
/* 24 */   public Network buddies = new Network(false);
/*    */ 
/*    */   public Students(long seed)
/*    */   {
/* 28 */     super(seed);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 33 */     super.start();
/*    */ 
/* 36 */     this.yard.clear();
/*    */ 
/* 39 */     this.buddies.clear();
/*    */ 
/* 42 */     for (int i = 0; i < this.numStudents; i++)
/*    */     {
/* 44 */       Student student = new Student();
/* 45 */       this.yard.setObjectLocation(student, new Double2D(this.yard.getWidth() * 0.5D + this.random.nextDouble() - 0.5D, this.yard.getHeight() * 0.5D + this.random.nextDouble() - 0.5D));
/*    */ 
/* 49 */       this.buddies.addNode(student);
/* 50 */       this.schedule.scheduleRepeating(student);
/*    */     }
/*    */ 
/* 54 */     Bag students = this.buddies.getAllNodes();
/* 55 */     for (int i = 0; i < students.size(); i++)
/*    */     {
/* 57 */       Object student = students.get(i);
/*    */ 
/* 60 */       Object studentB = null;
/*    */       do
/*    */       {
/* 63 */         studentB = students.get(this.random.nextInt(students.numObjs));
/* 64 */       }while (student == studentB);
/* 65 */       double buddiness = this.random.nextDouble();
/* 66 */       this.buddies.addEdge(student, studentB, new Double(buddiness));
/*    */       do
/*    */       {
/* 71 */         studentB = students.get(this.random.nextInt(students.numObjs));
/* 72 */       }while (student == studentB);
/* 73 */       buddiness = this.random.nextDouble();
/* 74 */       this.buddies.addEdge(student, studentB, new Double(-buddiness));
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 80 */     doLoop(Students.class, args);
/* 81 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial09.Students
 * JD-Core Version:    0.6.2
 */