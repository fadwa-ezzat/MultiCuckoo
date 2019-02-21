/*     */ package sim.app.asteroids;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.MutableDouble2D;
/*     */ 
/*     */ public class Asteroids extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int WAIT_PERIOD = 60;
/*     */   public static final double SAFE_DISTANCE = 30.0D;
/*     */   public Continuous2D field;
/*  35 */   public double width = 150.0D;
/*     */ 
/*  38 */   public double height = 150.0D;
/*     */ 
/*  41 */   public int numAsteroids = 5;
/*     */ 
/*  44 */   public int numShips = 1;
/*     */ 
/*  47 */   public int score = 0;
/*     */ 
/*  50 */   public int deaths = 0;
/*     */ 
/*  53 */   public int level = 0;
/*     */ 
/*  56 */   public int asteroidCount = 0;
/*     */ 
/*  61 */   public int[] actions = new int[this.numShips];
/*     */ 
/*  64 */   public Ship[] ships = new Ship[this.numShips];
/*     */ 
/*     */   public Asteroids(long seed)
/*     */   {
/*  69 */     super(seed);
/*     */   }
/*     */ 
/*     */   public void createShip(int tag)
/*     */   {
/*  76 */     this.ships[tag] = new Ship(this, new MutableDouble2D(0.0D, 0.0D), new Double2D(this.width / 2.0D, this.height / 2.0D), tag);
/*     */   }
/*     */ 
/*     */   public void createAsteroids()
/*     */   {
/*  82 */     this.level += 1;
/*     */ 
/*  84 */     for (int x = 0; x < this.numAsteroids; x++)
/*     */     {
/*  86 */       double angle = this.random.nextDouble() * 3.141592653589793D * 2.0D;
/*  87 */       Double2D loc = null;
/*  88 */       for (int i = 0; i < 1000; i++)
/*     */       {
/*  90 */         if (this.random.nextBoolean())
/*  91 */           loc = new Double2D(0.0D, this.random.nextDouble() * this.height);
/*     */         else
/*  93 */           loc = new Double2D(this.random.nextDouble() * this.width, 0.0D);
/*  94 */         boolean bad = false;
/*  95 */         for (int j = 0; j < this.numShips; j++)
/*  96 */           if ((this.ships[j] != null) && (this.field.getObjectLocation(this.ships[j]).distance(loc) < 30.0D)) {
/*  97 */             bad = true; break;
/*     */           }
/*  98 */         if (!bad) break;
/*     */       }
/* 100 */       new Asteroid(this, 4, new MutableDouble2D(0.3D * Math.cos(angle), 0.3D * Math.sin(angle)), loc);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/* 110 */     super.start();
/*     */ 
/* 113 */     this.field = new Continuous2D(this.width, this.width, this.height);
/*     */ 
/* 115 */     for (int x = 0; x < this.numShips; x++)
/* 116 */       createShip(x);
/* 117 */     createAsteroids();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 123 */     doLoop(Asteroids.class, args);
/* 124 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.asteroids.Asteroids
 * JD-Core Version:    0.6.2
 */