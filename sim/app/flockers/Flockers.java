/*     */ package sim.app.flockers;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class Flockers extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public Continuous2D flockers;
/*  17 */   public double width = 150.0D;
/*  18 */   public double height = 150.0D;
/*  19 */   public int numFlockers = 200;
/*  20 */   public double cohesion = 1.0D;
/*  21 */   public double avoidance = 1.0D;
/*  22 */   public double randomness = 1.0D;
/*  23 */   public double consistency = 1.0D;
/*  24 */   public double momentum = 1.0D;
/*  25 */   public double deadFlockerProbability = 0.1D;
/*  26 */   public double neighborhood = 10.0D;
/*  27 */   public double jump = 0.7D;
/*     */ 
/*  29 */   public double getCohesion() { return this.cohesion; } 
/*  30 */   public void setCohesion(double val) { if (val >= 0.0D) this.cohesion = val;  } 
/*  31 */   public double getAvoidance() { return this.avoidance; } 
/*  32 */   public void setAvoidance(double val) { if (val >= 0.0D) this.avoidance = val;  } 
/*  33 */   public double getRandomness() { return this.randomness; } 
/*  34 */   public void setRandomness(double val) { if (val >= 0.0D) this.randomness = val;  } 
/*  35 */   public double getConsistency() { return this.consistency; } 
/*  36 */   public void setConsistency(double val) { if (val >= 0.0D) this.consistency = val;  } 
/*  37 */   public double getMomentum() { return this.momentum; } 
/*  38 */   public void setMomentum(double val) { if (val >= 0.0D) this.momentum = val;  } 
/*  39 */   public int getNumFlockers() { return this.numFlockers; } 
/*  40 */   public void setNumFlockers(int val) { if (val >= 1) this.numFlockers = val;  } 
/*  41 */   public double getWidth() { return this.width; } 
/*  42 */   public void setWidth(double val) { if (val > 0.0D) this.width = val;  } 
/*  43 */   public double getHeight() { return this.height; } 
/*  44 */   public void setHeight(double val) { if (val > 0.0D) this.height = val;  } 
/*  45 */   public double getNeighborhood() { return this.neighborhood; } 
/*  46 */   public void setNeighborhood(double val) { if (val > 0.0D) this.neighborhood = val;  } 
/*  47 */   public double getDeadFlockerProbability() { return this.deadFlockerProbability; } 
/*  48 */   public void setDeadFlockerProbability(double val) { if ((val >= 0.0D) && (val <= 1.0D)) this.deadFlockerProbability = val; 
/*     */   }
/*     */ 
/*     */   public Double2D[] getLocations()
/*     */   {
/*  52 */     if (this.flockers == null) return new Double2D[0];
/*  53 */     Bag b = this.flockers.getAllObjects();
/*  54 */     if (b == null) return new Double2D[0];
/*  55 */     Double2D[] locs = new Double2D[b.numObjs];
/*  56 */     for (int i = 0; i < b.numObjs; i++)
/*  57 */       locs[i] = this.flockers.getObjectLocation(b.objs[i]);
/*  58 */     return locs;
/*     */   }
/*     */ 
/*     */   public Double2D[] getInvertedLocations()
/*     */   {
/*  63 */     if (this.flockers == null) return new Double2D[0];
/*  64 */     Bag b = this.flockers.getAllObjects();
/*  65 */     if (b == null) return new Double2D[0];
/*  66 */     Double2D[] locs = new Double2D[b.numObjs];
/*  67 */     for (int i = 0; i < b.numObjs; i++)
/*     */     {
/*  69 */       locs[i] = this.flockers.getObjectLocation(b.objs[i]);
/*  70 */       locs[i] = new Double2D(locs[i].y, locs[i].x);
/*     */     }
/*  72 */     return locs;
/*     */   }
/*     */ 
/*     */   public Flockers(long seed)
/*     */   {
/*  78 */     super(seed);
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  83 */     super.start();
/*     */ 
/*  90 */     this.flockers = new Continuous2D(this.neighborhood / 1.5D, this.width, this.height);
/*     */ 
/*  93 */     for (int x = 0; x < this.numFlockers; x++)
/*     */     {
/*  95 */       Double2D location = new Double2D(this.random.nextDouble() * this.width, this.random.nextDouble() * this.height);
/*  96 */       Flocker flocker = new Flocker(location);
/*  97 */       if (this.random.nextBoolean(this.deadFlockerProbability)) flocker.dead = true;
/*  98 */       this.flockers.setObjectLocation(flocker, location);
/*  99 */       flocker.flockers = this.flockers;
/* 100 */       flocker.theFlock = this;
/* 101 */       this.schedule.scheduleRepeating(flocker);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 107 */     doLoop(Flockers.class, args);
/* 108 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.flockers.Flockers
 * JD-Core Version:    0.6.2
 */