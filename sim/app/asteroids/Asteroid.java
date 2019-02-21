/*     */ package sim.app.asteroids;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.MutableDouble2D;
/*     */ 
/*     */ public class Asteroid extends Element
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public int size;
/*     */   public static final double INITIAL_VELOCITY = 0.3D;
/*     */   public static final double MAXIMUM_ROTATIONAL_VELOCITY = 0.0174532925199433D;
/*     */   public static final int MAXIMUM_SIZE = 4;
/*     */   public static final double MAXIMUM_EXPLOSION_FORCE = 1.0D;
/*     */   public static final double MINIMUM_EXPLOSION_FORCE = 0.5D;
/*  60 */   static final Shape[][] shapes = { { new Rectangle2D.Double(-3.0D, -3.0D, 5.0D, 5.0D) }, { new Rectangle2D.Double(-4.0D, -4.0D, 8.0D, 8.0D) }, { new Rectangle2D.Double(-4.0D, -4.0D, 8.0D, 8.0D) }, { new Rectangle2D.Double(-4.0D, -4.0D, 9.0D, 9.0D) }, { new Rectangle2D.Double(-5.0D, -5.0D, 10.0D, 10.0D) } };
/*     */ 
/*  71 */   static final int[][][] breakMap = { { new int[0] }, { { 0, 0 } }, { { 0, 1 }, { 0, 0, 0 } }, { { 0, 0, 1 }, { 0, 2 }, { 1, 1 } }, { { 1, 2 }, { 0, 3 }, { 0, 1, 1 } } };
/*     */ 
/*     */   public Asteroid(Asteroids asteroids, int size, MutableDouble2D velocity, Double2D location)
/*     */   {
/*  48 */     this.size = size;
/*  49 */     this.velocity = velocity;
/*  50 */     this.stopper = asteroids.schedule.scheduleRepeating(this);
/*  51 */     this.shape = shapes[size][asteroids.random.nextInt(shapes[size].length)];
/*  52 */     this.rotationalVelocity = (asteroids.random.nextDouble() * 0.0174532925199433D * (asteroids.random.nextBoolean() ? 1.0D : -1.0D));
/*     */ 
/*  54 */     this.orientation = (asteroids.random.nextDouble() * 3.141592653589793D * 2.0D);
/*  55 */     asteroids.field.setObjectLocation(this, location);
/*  56 */     asteroids.asteroidCount += 1;
/*     */   }
/*     */ 
/*     */   public void breakApart(final Asteroids asteroids)
/*     */   {
/*  86 */     Double2D location = asteroids.field.getObjectLocation(this);
/*  87 */     int[] sizes = breakMap[this.size][asteroids.random.nextInt(breakMap[this.size].length)];
/*     */ 
/*  89 */     if (sizes.length > 0)
/*     */     {
/*  92 */       int sum = 0;
/*  93 */       for (int i = 0; i < sizes.length; i++) {
/*  94 */         sum += sizes[i];
/*     */       }
/*     */ 
/*  97 */       double explosionForce = asteroids.random.nextDouble() * 0.5D + 0.5D;
/*  98 */       double sumForceX = 0.0D;
/*  99 */       double sumForceY = 0.0D;
/*     */       Asteroid a;
/* 100 */       for (int i = 0; i < sizes.length; i++)
/*     */       {
/* 102 */         double angle = asteroids.random.nextDouble() * 3.141592653589793D * 2.0D;
/* 103 */         double force = explosionForce / sizes.length;
/* 104 */         double forceX = force * Math.cos(angle);
/* 105 */         double forceY = force * Math.sin(angle);
/* 106 */         if (i == sizes.length - 1) {
/* 107 */           forceX = -sumForceX; forceY = -sumForceY; } else {
/* 108 */           sumForceX += forceX; sumForceY += forceY;
/*     */         }
/* 110 */         a = new Asteroid(asteroids, sizes[i], new MutableDouble2D(this.velocity.x + forceX, this.velocity.y + forceY), location);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 115 */       breakIntoShards(asteroids);
/*     */     }
/* 117 */     end(asteroids);
/* 118 */     asteroids.asteroidCount -= 1;
/* 119 */     if (asteroids.asteroidCount <= 0)
/*     */     {
/* 121 */       asteroids.schedule.scheduleOnceIn(60.0D, new Steppable()
/*     */       {
/*     */         public void step(SimState state)
/*     */         {
/* 125 */           asteroids.createAsteroids();
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.asteroids.Asteroid
 * JD-Core Version:    0.6.2
 */