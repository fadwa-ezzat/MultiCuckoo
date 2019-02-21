/*     */ package sim.app.wcss.tutorial14;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.field.continuous.Continuous3D;
/*     */ import sim.field.network.Network;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.Double3D;
/*     */ import sim.util.Interval;
/*     */ 
/*     */ public class Students extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  17 */   public Continuous2D yard = new Continuous2D(1.0D, 100.0D, 100.0D);
/*  18 */   public Continuous3D agitatedYard = new Continuous3D(1.0D, 100.0D, 100.0D, 100.0D);
/*     */ 
/*  20 */   public double TEMPERING_CUT_DOWN = 0.99D;
/*  21 */   public double TEMPERING_INITIAL_RANDOM_MULTIPLIER = 10.0D;
/*  22 */   public boolean tempering = true;
/*     */ 
/*  26 */   public int numStudents = 50;
/*     */ 
/*  28 */   double forceToSchoolMultiplier = 0.01D;
/*  29 */   double randomMultiplier = 0.1D;
/*     */ 
/*  51 */   public Network buddies = new Network(false);
/*     */ 
/*     */   public boolean isTempering()
/*     */   {
/*  23 */     return this.tempering; } 
/*  24 */   public void setTempering(boolean val) { this.tempering = val; }
/*     */ 
/*     */ 
/*     */   public int getNumStudents()
/*     */   {
/*  31 */     return this.numStudents; } 
/*  32 */   public void setNumStudents(int val) { if (val > 0) this.numStudents = val;  } 
/*     */   public double getForceToSchoolMultiplier() {
/*  34 */     return this.forceToSchoolMultiplier; } 
/*  35 */   public void setForceToSchoolMultiplier(double val) { if (this.forceToSchoolMultiplier >= 0.0D) this.forceToSchoolMultiplier = val;  } 
/*     */   public double getRandomMultiplier() {
/*  37 */     return this.randomMultiplier; } 
/*  38 */   public void setRandomMultiplier(double val) { if (this.randomMultiplier >= 0.0D) this.randomMultiplier = val;  } 
/*  39 */   public Object domRandomMultiplier() { return new Interval(0.0D, 100.0D); }
/*     */ 
/*     */   public double[] getAgitationDistribution()
/*     */   {
/*  43 */     Bag students = this.buddies.getAllNodes();
/*  44 */     double[] distro = new double[students.numObjs];
/*  45 */     int len = students.size();
/*  46 */     for (int i = 0; i < len; i++)
/*  47 */       distro[i] = ((Student)(Student)students.get(i)).getAgitation();
/*  48 */     return distro;
/*     */   }
/*     */ 
/*     */   public Students(long seed)
/*     */   {
/*  55 */     super(seed);
/*     */   }
/*     */ 
/*     */   public void load3DStudents()
/*     */   {
/*  60 */     Bag students = this.buddies.getAllNodes();
/*  61 */     for (int i = 0; i < students.size(); i++)
/*     */     {
/*  63 */       Student student = (Student)students.get(i);
/*  64 */       Double2D loc = this.yard.getObjectLocation(student);
/*     */ 
/*  67 */       this.agitatedYard.setObjectLocation(student, new Double3D(loc, student.getAgitation() * 5.0D));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  73 */     super.start();
/*     */ 
/*  76 */     if (this.tempering)
/*     */     {
/*  78 */       this.randomMultiplier = this.TEMPERING_INITIAL_RANDOM_MULTIPLIER;
/*  79 */       this.schedule.scheduleRepeating(0.0D, 1, new Steppable() {
/*  80 */         public void step(SimState state) { if (Students.this.tempering) Students.this.randomMultiplier *= Students.this.TEMPERING_CUT_DOWN;
/*     */         }
/*     */       });
/*     */     }
/*  84 */     this.yard.clear();
/*     */ 
/*  87 */     this.buddies.clear();
/*     */ 
/*  89 */     this.agitatedYard.clear();
/*     */ 
/*  92 */     for (int i = 0; i < this.numStudents; i++)
/*     */     {
/*  94 */       Student student = new Student();
/*  95 */       this.yard.setObjectLocation(student, new Double2D(this.yard.getWidth() * 0.5D + this.random.nextDouble() - 0.5D, this.yard.getHeight() * 0.5D + this.random.nextDouble() - 0.5D));
/*     */ 
/*  99 */       this.buddies.addNode(student);
/* 100 */       this.schedule.scheduleRepeating(student);
/* 101 */       Steppable steppable = new Steppable() {
/*     */         public void step(SimState state) {
/* 103 */           Students.this.load3DStudents();
/*     */         }
/*     */       };
/* 105 */       this.schedule.scheduleRepeating(0.0D, 2, steppable);
/*     */     }
/*     */ 
/* 109 */     Bag students = this.buddies.getAllNodes();
/* 110 */     for (int i = 0; i < students.size(); i++)
/*     */     {
/* 112 */       Object student = students.get(i);
/*     */ 
/* 115 */       Object studentB = null;
/*     */       do
/*     */       {
/* 118 */         studentB = students.get(this.random.nextInt(students.numObjs));
/* 119 */       }while (student == studentB);
/* 120 */       double buddiness = this.random.nextDouble();
/* 121 */       this.buddies.addEdge(student, studentB, new Double(buddiness));
/*     */       do
/*     */       {
/* 126 */         studentB = students.get(this.random.nextInt(students.numObjs));
/* 127 */       }while (student == studentB);
/* 128 */       buddiness = this.random.nextDouble();
/* 129 */       this.buddies.addEdge(student, studentB, new Double(-buddiness));
/*     */     }
/*     */ 
/* 132 */     load3DStudents();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 137 */     doLoop(Students.class, args);
/* 138 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial14.Students
 * JD-Core Version:    0.6.2
 */