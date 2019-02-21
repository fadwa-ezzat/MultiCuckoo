/*     */ package sim.app.tutorial5;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.Sequence;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.field.network.Network;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class Tutorial5 extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public Continuous2D balls;
/*     */   public Network bands;
/*  20 */   public int numBalls = 50;
/*  21 */   public int numBands = 60;
/*     */   public static final double maxMass = 10.0D;
/*     */   public static final double minMass = 1.0D;
/*     */   public static final double minLaxBandDistance = 10.0D;
/*     */   public static final double maxLaxBandDistance = 50.0D;
/*     */   public static final double minBandStrength = 5.0D;
/*     */   public static final double maxBandStrength = 10.0D;
/*     */   public static final double collisionDistance = 5.0D;
/*     */ 
/*     */   public int getNumBalls()
/*     */   {
/*  31 */     return this.numBalls; } 
/*  32 */   public void setNumBalls(int val) { if (val >= 2) this.numBalls = val;  } 
/*  33 */   public int getNumBands() { return this.numBands; } 
/*  34 */   public void setNumBands(int val) { if (val >= 0) this.numBands = val; 
/*     */   }
/*     */ 
/*     */   public Tutorial5(long seed)
/*     */   {
/*  38 */     super(seed);
/*  39 */     this.balls = new Continuous2D(5.0D, 100.0D, 100.0D);
/*  40 */     this.bands = new Network();
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  45 */     super.start();
/*     */ 
/*  47 */     this.balls = new Continuous2D(5.0D, 100.0D, 100.0D);
/*  48 */     this.bands = new Network(false);
/*     */ 
/*  50 */     Steppable[] s = new Steppable[this.numBalls];
/*     */ 
/*  53 */     for (int i = 0; i < this.numBalls; i++)
/*     */     {
/*  56 */       final Ball ball = new Ball(0.0D, 0.0D, this.random.nextDouble() * 9.0D + 1.0D);
/*  57 */       this.balls.setObjectLocation(ball, new Double2D(this.random.nextDouble() * 100.0D, this.random.nextDouble() * 100.0D));
/*     */ 
/*  60 */       this.bands.addNode(ball);
/*  61 */       this.schedule.scheduleRepeating(ball);
/*     */ 
/*  64 */       s[i] = new Steppable() {
/*     */         private static final long serialVersionUID = 1L;
/*     */ 
/*     */         public void step(SimState state) {
/*  68 */           ball.computeForce(state);
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*  73 */     this.schedule.scheduleRepeating(0.0D, 1, new Sequence(s), 1.0D);
/*     */ 
/*  76 */     Bag ballObjs = this.balls.getAllObjects();
/*  77 */     for (int i = 0; i < this.numBands; i++)
/*     */     {
/*  79 */       Band band = new Band(this.random.nextDouble() * 40.0D + 10.0D, this.random.nextDouble() * 5.0D + 5.0D);
/*     */ 
/*  84 */       Ball from = (Ball)ballObjs.objs[this.random.nextInt(ballObjs.numObjs)];
/*     */ 
/*  86 */       Ball to = from;
/*  87 */       while (to == from) {
/*  88 */         to = (Ball)ballObjs.objs[this.random.nextInt(ballObjs.numObjs)];
/*     */       }
/*  90 */       this.bands.addEdge(from, to, band);
/*     */     }
/*     */ 
/*  94 */     ballObjs = this.balls.getAllObjects();
/*  95 */     for (int i = 0; i < ballObjs.numObjs; i++)
/*  96 */       ((Ball)ballObjs.objs[i]).computeCollision(this);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 101 */     doLoop(Tutorial5.class, args);
/* 102 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial5.Tutorial5
 * JD-Core Version:    0.6.2
 */