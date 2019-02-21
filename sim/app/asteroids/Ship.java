/*     */ package sim.app.asteroids;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.MutableDouble2D;
/*     */ 
/*     */ public class Ship extends Element
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public int tag;
/*  40 */   public Fire fire = new Fire();
/*     */   int bulletCountdown;
/*     */   public static final int BULLET_COUNTDOWN = 10;
/*     */   public static final double MAXIMUM_RADIUS = 6.0D;
/*     */   public static final double VELOCITY_INCREMENT = 0.03D;
/*     */   public static final double ORIENTATION_INCREMENT = 0.05D;
/*     */   public static final int MAXIMUM_THRUST_DRAW_LENGTH = 5;
/*     */   public static final int NOTHING = 0;
/*     */   public static final int LEFT = 1;
/*     */   public static final int FORWARD = 2;
/*     */   public static final int RIGHT = 4;
/*     */   public static final int FIRE = 8;
/*     */   public static final int HYPERSPACE = 16;
/* 104 */   int thrust = 0;
/*     */ 
/*     */   public Ship(Asteroids asteroids, MutableDouble2D velocity, Double2D location, int tag)
/*     */   {
/*  70 */     this.velocity = velocity;
/*  71 */     this.rotationalVelocity = 0.0D;
/*  72 */     this.stopper = asteroids.schedule.scheduleRepeating(this);
/*  73 */     this.orientation = (asteroids.random.nextDouble() * 3.141592653589793D * 2.0D);
/*  74 */     asteroids.field.setObjectLocation(this, location);
/*  75 */     GeneralPath gp = new GeneralPath();
/*  76 */     gp.moveTo(-2.0F, -2.0F);
/*  77 */     gp.lineTo(2.0F, 0.0F);
/*  78 */     gp.lineTo(-2.0F, 2.0F);
/*  79 */     gp.lineTo(0.0F, 0.0F);
/*     */ 
/*  81 */     gp.closePath();
/*  82 */     this.shape = gp;
/*  83 */     this.tag = tag;
/*     */   }
/*     */ 
/*     */   public void shoot(Asteroids asteroids)
/*     */   {
/*     */     Bullet b;
/*  89 */     if (this.bulletCountdown <= 0)
/*     */     {
/*  91 */       this.bulletCountdown = 10;
/*  92 */       MutableDouble2D v2 = new MutableDouble2D(this.velocity);
/*  93 */       v2.x += 1.0D * Math.cos(this.orientation);
/*  94 */       v2.y += 1.0D * Math.sin(this.orientation);
/*  95 */       Double2D location = asteroids.field.getObjectLocation(this);
/*  96 */       Double2D l2 = new Double2D(location.x + 7.0D * Math.cos(this.orientation), location.y + 7.0D * Math.sin(this.orientation));
/*     */ 
/*  99 */       b = new Bullet(asteroids, v2, l2, 100);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/* 113 */     Asteroids asteroids = (Asteroids)state;
/*     */ 
/* 116 */     if ((asteroids.actions[this.tag] & 0x2) == 2)
/*     */     {
/* 118 */       this.velocity.x += Math.cos(this.orientation) * 0.03D;
/* 119 */       this.velocity.y += Math.sin(this.orientation) * 0.03D;
/* 120 */       this.thrust = 5;
/*     */     }
/* 122 */     if ((asteroids.actions[this.tag] & 0x1) == 1)
/*     */     {
/* 124 */       this.orientation -= 0.05D;
/*     */     }
/* 126 */     if ((asteroids.actions[this.tag] & 0x4) == 4)
/*     */     {
/* 128 */       this.orientation += 0.05D;
/*     */     }
/* 130 */     if ((asteroids.actions[this.tag] & 0x8) == 8)
/*     */     {
/* 132 */       shoot(asteroids);
/* 133 */       asteroids.actions[this.tag] &= -9;
/*     */     }
/* 135 */     if ((asteroids.actions[this.tag] & 0x10) == 16)
/*     */     {
/* 137 */       asteroids.field.setObjectLocation(this, new Double2D(asteroids.random.nextDouble() * asteroids.field.width, asteroids.random.nextDouble() * asteroids.field.height));
/*     */ 
/* 140 */       asteroids.actions[this.tag] &= -17;
/*     */     }
/*     */ 
/* 143 */     super.step(state);
/*     */ 
/* 146 */     testForHit(asteroids);
/* 147 */     this.bulletCountdown -= 1;
/*     */   }
/*     */ 
/*     */   public Color getColor()
/*     */   {
/* 152 */     return Color.green;
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/* 157 */     super.draw(object, graphics, info);
/*     */ 
/* 160 */     if (this.thrust > 0)
/*     */     {
/* 162 */       this.fire.orientation = this.orientation;
/* 163 */       this.fire.draw(object, graphics, info);
/*     */     }
/* 165 */     this.thrust -= 1;
/*     */   }
/*     */ 
/*     */   void respawn(final Asteroids asteroids)
/*     */   {
/* 170 */     asteroids.schedule.scheduleOnceIn(60.0D, new Steppable()
/*     */     {
/*     */       public void step(SimState state)
/*     */       {
/* 175 */         Double2D respawnLocation = new Double2D(asteroids.width / 2.0D, asteroids.height / 2.0D);
/* 176 */         Bag o = asteroids.field.getAllObjects();
/* 177 */         boolean safe = true;
/* 178 */         for (int i = 0; i < o.numObjs; i++)
/*     */         {
/* 180 */           Double2D loc = asteroids.field.getObjectLocation(o.objs[i]);
/* 181 */           if (loc.distance(respawnLocation) < 30.0D) {
/* 182 */             safe = false; break;
/*     */           }
/*     */         }
/*     */ 
/* 186 */         if (safe)
/* 187 */           asteroids.createShip(Ship.this.tag);
/* 188 */         else Ship.this.respawn(asteroids);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void testForHit(Asteroids asteroids)
/*     */   {
/* 198 */     Bag a = asteroids.field.getAllObjects();
/* 199 */     for (int i = 0; i < a.numObjs; i++)
/*     */     {
/* 201 */       Object obj = a.objs[i];
/* 202 */       if ((obj instanceof Asteroid))
/*     */       {
/* 204 */         Asteroid asteroid = (Asteroid)a.objs[i];
/* 205 */         if (asteroid.collisionWithElement(asteroids, this))
/*     */         {
/* 207 */           asteroid.breakApart(asteroids);
/* 208 */           breakIntoShards(asteroids);
/* 209 */           asteroids.score += 1;
/* 210 */           asteroids.deaths += 1;
/* 211 */           asteroids.ships[this.tag] = null;
/* 212 */           end(asteroids);
/* 213 */           respawn(asteroids);
/* 214 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.asteroids.Ship
 * JD-Core Version:    0.6.2
 */