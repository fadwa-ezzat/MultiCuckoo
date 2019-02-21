/*     */ package sim.app.balls3d;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.Sequence;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous3D;
/*     */ import sim.field.network.Network;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double3D;
/*     */ 
/*     */ public class Balls3D extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public Continuous3D balls;
/*     */   public Network bands;
/*  21 */   public int numBalls = 50;
/*  22 */   public int numBands = 60;
/*     */ 
/*  24 */   public double gridWidth = 100.0D;
/*  25 */   public double gridHeight = 100.0D;
/*  26 */   public double gridLength = 100.0D;
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
/*  36 */     return this.numBalls; } 
/*  37 */   public void setNumBalls(int val) { if (val >= 2) this.numBalls = val;  } 
/*  38 */   public int getNumBands() { return this.numBands; } 
/*  39 */   public void setNumBands(int val) { if (val >= 0) this.numBands = val; 
/*     */   }
/*     */ 
/*     */   public Double3D[] getBallPositions()
/*     */   {
/*  43 */     if (this.balls == null) return new Double3D[0];
/*     */ 
/*  45 */     Bag bag = this.balls.getAllObjects();
/*  46 */     if (bag == null) return new Double3D[0];
/*     */ 
/*  48 */     Double3D[] d = new Double3D[bag.size()];
/*  49 */     for (int i = 0; i < d.length; i++)
/*     */     {
/*  51 */       d[i] = this.balls.getObjectLocation(bag.get(i));
/*     */     }
/*  53 */     return d;
/*     */   }
/*     */ 
/*     */   public Balls3D(long seed)
/*     */   {
/*  58 */     super(seed);
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  63 */     super.start();
/*     */ 
/*  65 */     this.balls = new Continuous3D(5.0D, this.gridWidth, this.gridHeight, this.gridLength);
/*  66 */     this.bands = new Network();
/*     */ 
/*  68 */     Steppable[] s = new Steppable[this.numBalls];
/*     */ 
/*  71 */     for (int i = 0; i < this.numBalls; i++)
/*     */     {
/*  74 */       final Ball ball = new Ball(0.0D, 0.0D, 0.0D, this.random.nextDouble() * 9.0D + 1.0D);
/*  75 */       this.balls.setObjectLocation(ball, new Double3D(this.random.nextDouble() * this.gridWidth, this.random.nextDouble() * this.gridHeight, this.random.nextDouble() * this.gridLength));
/*     */ 
/*  79 */       this.bands.addNode(ball);
/*  80 */       this.schedule.scheduleRepeating(ball);
/*     */ 
/*  83 */       s[i] = new Steppable() { static final long serialVersionUID = -4269174171145445918L;
/*     */ 
/*  85 */         public void step(SimState state) { ball.computeForce(state); }
/*     */ 
/*     */ 
/*     */       };
/*     */     }
/*     */ 
/*  92 */     this.schedule.scheduleRepeating(0.0D, 1, new Sequence(s), 1.0D);
/*     */ 
/*  95 */     Bag ballObjs = this.balls.getAllObjects();
/*  96 */     for (int i = 0; i < this.numBands; i++)
/*     */     {
/*  98 */       Band band = new Band(this.random.nextDouble() * 40.0D + 10.0D, this.random.nextDouble() * 5.0D + 5.0D);
/*     */ 
/* 103 */       Ball from = (Ball)ballObjs.objs[this.random.nextInt(ballObjs.numObjs)];
/*     */ 
/* 105 */       Ball to = from;
/* 106 */       while (to == from)
/* 107 */         to = (Ball)ballObjs.objs[this.random.nextInt(ballObjs.numObjs)];
/* 108 */       this.bands.addEdge(from, to, band);
/*     */     }
/*     */ 
/* 112 */     ballObjs = this.balls.getAllObjects();
/* 113 */     for (int i = 0; i < ballObjs.numObjs; i++)
/* 114 */       ((Ball)ballObjs.objs[i]).computeCollision(this);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 119 */     doLoop(Balls3D.class, args);
/* 120 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.balls3d.Balls3D
 * JD-Core Version:    0.6.2
 */