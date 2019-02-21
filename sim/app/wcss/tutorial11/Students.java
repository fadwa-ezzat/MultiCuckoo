/*     */ package sim.app.wcss.tutorial11;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.field.network.Network;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.Interval;
/*     */ 
/*     */ public class Students extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  17 */   public Continuous2D yard = new Continuous2D(1.0D, 100.0D, 100.0D);
/*     */ 
/*  19 */   public int numStudents = 50;
/*     */ 
/*  21 */   double forceToSchoolMultiplier = 0.01D;
/*  22 */   double randomMultiplier = 0.1D;
/*     */ 
/*  44 */   public Network buddies = new Network(false);
/*     */ 
/*     */   public int getNumStudents()
/*     */   {
/*  24 */     return this.numStudents; } 
/*  25 */   public void setNumStudents(int val) { if (val > 0) this.numStudents = val;  } 
/*     */   public double getForceToSchoolMultiplier() {
/*  27 */     return this.forceToSchoolMultiplier; } 
/*  28 */   public void setForceToSchoolMultiplier(double val) { if (this.forceToSchoolMultiplier >= 0.0D) this.forceToSchoolMultiplier = val;  } 
/*     */   public double getRandomMultiplier() {
/*  30 */     return this.randomMultiplier; } 
/*  31 */   public void setRandomMultiplier(double val) { if (this.randomMultiplier >= 0.0D) this.randomMultiplier = val;  } 
/*  32 */   public Object domRandomMultiplier() { return new Interval(0.0D, 100.0D); }
/*     */ 
/*     */   public double[] getAgitationDistribution()
/*     */   {
/*  36 */     Bag students = this.buddies.getAllNodes();
/*  37 */     double[] distro = new double[students.numObjs];
/*  38 */     int len = students.size();
/*  39 */     for (int i = 0; i < len; i++)
/*  40 */       distro[i] = ((Student)(Student)students.get(i)).getAgitation();
/*  41 */     return distro;
/*     */   }
/*     */ 
/*     */   public Students(long seed)
/*     */   {
/*  48 */     super(seed);
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  53 */     super.start();
/*     */ 
/*  56 */     this.yard.clear();
/*     */ 
/*  59 */     this.buddies.clear();
/*     */ 
/*  62 */     for (int i = 0; i < this.numStudents; i++)
/*     */     {
/*  64 */       Student student = new Student();
/*  65 */       this.yard.setObjectLocation(student, new Double2D(this.yard.getWidth() * 0.5D + this.random.nextDouble() - 0.5D, this.yard.getHeight() * 0.5D + this.random.nextDouble() - 0.5D));
/*     */ 
/*  69 */       this.buddies.addNode(student);
/*  70 */       this.schedule.scheduleRepeating(student);
/*     */     }
/*     */ 
/*  74 */     Bag students = this.buddies.getAllNodes();
/*  75 */     for (int i = 0; i < students.size(); i++)
/*     */     {
/*  77 */       Object student = students.get(i);
/*     */ 
/*  80 */       Object studentB = null;
/*     */       do
/*     */       {
/*  83 */         studentB = students.get(this.random.nextInt(students.numObjs));
/*  84 */       }while (student == studentB);
/*  85 */       double buddiness = this.random.nextDouble();
/*  86 */       this.buddies.addEdge(student, studentB, new Double(buddiness));
/*     */       do
/*     */       {
/*  91 */         studentB = students.get(this.random.nextInt(students.numObjs));
/*  92 */       }while (student == studentB);
/*  93 */       buddiness = this.random.nextDouble();
/*  94 */       this.buddies.addEdge(student, studentB, new Double(-buddiness));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 100 */     doLoop(Students.class, args);
/* 101 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial11.Students
 * JD-Core Version:    0.6.2
 */