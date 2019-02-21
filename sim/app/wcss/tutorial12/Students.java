/*     */ package sim.app.wcss.tutorial12;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
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
/*  19 */   public double TEMPERING_CUT_DOWN = 0.99D;
/*  20 */   public double TEMPERING_INITIAL_RANDOM_MULTIPLIER = 10.0D;
/*  21 */   public boolean tempering = true;
/*     */ 
/*  25 */   public int numStudents = 50;
/*     */ 
/*  27 */   double forceToSchoolMultiplier = 0.01D;
/*  28 */   double randomMultiplier = 0.1D;
/*     */ 
/*  50 */   public Network buddies = new Network(false);
/*     */ 
/*     */   public boolean isTempering()
/*     */   {
/*  22 */     return this.tempering; } 
/*  23 */   public void setTempering(boolean val) { this.tempering = val; }
/*     */ 
/*     */ 
/*     */   public int getNumStudents()
/*     */   {
/*  30 */     return this.numStudents; } 
/*  31 */   public void setNumStudents(int val) { if (val > 0) this.numStudents = val;  } 
/*     */   public double getForceToSchoolMultiplier() {
/*  33 */     return this.forceToSchoolMultiplier; } 
/*  34 */   public void setForceToSchoolMultiplier(double val) { if (this.forceToSchoolMultiplier >= 0.0D) this.forceToSchoolMultiplier = val;  } 
/*     */   public double getRandomMultiplier() {
/*  36 */     return this.randomMultiplier; } 
/*  37 */   public void setRandomMultiplier(double val) { if (this.randomMultiplier >= 0.0D) this.randomMultiplier = val;  } 
/*  38 */   public Object domRandomMultiplier() { return new Interval(0.0D, 100.0D); }
/*     */ 
/*     */   public double[] getAgitationDistribution()
/*     */   {
/*  42 */     Bag students = this.buddies.getAllNodes();
/*  43 */     double[] distro = new double[students.numObjs];
/*  44 */     int len = students.size();
/*  45 */     for (int i = 0; i < len; i++)
/*  46 */       distro[i] = ((Student)(Student)students.get(i)).getAgitation();
/*  47 */     return distro;
/*     */   }
/*     */ 
/*     */   public Students(long seed)
/*     */   {
/*  54 */     super(seed);
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  59 */     super.start();
/*     */ 
/*  62 */     if (this.tempering)
/*     */     {
/*  64 */       this.randomMultiplier = this.TEMPERING_INITIAL_RANDOM_MULTIPLIER;
/*  65 */       this.schedule.scheduleRepeating(0.0D, 1, new Steppable() {
/*  66 */         public void step(SimState state) { if (Students.this.tempering) Students.this.randomMultiplier *= Students.this.TEMPERING_CUT_DOWN;
/*     */         }
/*     */       });
/*     */     }
/*  70 */     this.yard.clear();
/*     */ 
/*  73 */     this.buddies.clear();
/*     */ 
/*  76 */     for (int i = 0; i < this.numStudents; i++)
/*     */     {
/*  78 */       Student student = new Student();
/*  79 */       this.yard.setObjectLocation(student, new Double2D(this.yard.getWidth() * 0.5D + this.random.nextDouble() - 0.5D, this.yard.getHeight() * 0.5D + this.random.nextDouble() - 0.5D));
/*     */ 
/*  83 */       this.buddies.addNode(student);
/*  84 */       this.schedule.scheduleRepeating(student);
/*     */     }
/*     */ 
/*  88 */     Bag students = this.buddies.getAllNodes();
/*  89 */     for (int i = 0; i < students.size(); i++)
/*     */     {
/*  91 */       Object student = students.get(i);
/*     */ 
/*  94 */       Object studentB = null;
/*     */       do
/*     */       {
/*  97 */         studentB = students.get(this.random.nextInt(students.numObjs));
/*  98 */       }while (student == studentB);
/*  99 */       double buddiness = this.random.nextDouble();
/* 100 */       this.buddies.addEdge(student, studentB, new Double(buddiness));
/*     */       do
/*     */       {
/* 105 */         studentB = students.get(this.random.nextInt(students.numObjs));
/* 106 */       }while (student == studentB);
/* 107 */       buddiness = this.random.nextDouble();
/* 108 */       this.buddies.addEdge(student, studentB, new Double(-buddiness));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 114 */     doLoop(Students.class, args);
/* 115 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial12.Students
 * JD-Core Version:    0.6.2
 */