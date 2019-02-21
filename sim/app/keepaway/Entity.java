/*     */ package sim.app.keepaway;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.portrayal.simple.OvalPortrayal2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.MutableDouble2D;
/*     */ 
/*     */ public abstract class Entity extends OvalPortrayal2D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public MutableDouble2D loc;
/*     */   public MutableDouble2D velocity;
/*     */   public MutableDouble2D bump;
/*  18 */   public MutableDouble2D force = new MutableDouble2D();
/*  19 */   public MutableDouble2D accel = new MutableDouble2D();
/*  20 */   public MutableDouble2D newLoc = new MutableDouble2D();
/*  21 */   public MutableDouble2D sumVector = new MutableDouble2D(0.0D, 0.0D);
/*     */   public double speed;
/*     */   public double radius;
/*     */   public double cap;
/*     */   public double mass;
/*     */ 
/*     */   public double getX()
/*     */   {
/*  30 */     return this.loc.x; } 
/*  31 */   public void setX(double newX) { this.loc.x = newX; } 
/*     */   public double getY() {
/*  33 */     return this.loc.y; } 
/*  34 */   public void setY(double newY) { this.loc.y = newY; } 
/*     */   public double getVelocityX() {
/*  36 */     return this.velocity.x; } 
/*  37 */   public void setVelocityX(double newX) { this.velocity.x = newX; } 
/*     */   public double getVelocityY() {
/*  39 */     return this.velocity.y; } 
/*  40 */   public void setVelocityY(double newY) { this.velocity.y = newY; } 
/*     */   public double getSpeed() {
/*  42 */     return this.speed; } 
/*  43 */   public void setSpeed(double newSpeed) { this.speed = newSpeed; } 
/*     */   public double getRadius() {
/*  45 */     return this.radius;
/*     */   }
/*     */   public void setRadius(double newRadius) {
/*  48 */     this.radius = newRadius;
/*  49 */     this.scale = (2.0D * this.radius);
/*     */   }
/*     */   public double getMass() {
/*  52 */     return this.mass; } 
/*  53 */   public void setMass(double newMass) { this.mass = newMass; }
/*     */ 
/*     */ 
/*     */   public Entity(double newX, double newY, double newRadius, Color c)
/*     */   {
/*  58 */     super(c, newRadius * 2.0D);
/*     */ 
/*  60 */     this.loc = new MutableDouble2D(newX, newY);
/*  61 */     this.velocity = new MutableDouble2D(0.0D, 0.0D);
/*  62 */     this.bump = new MutableDouble2D(0.0D, 0.0D);
/*  63 */     this.radius = newRadius;
/*     */ 
/*  65 */     this.mass = 1.0D;
/*  66 */     this.cap = 1.0D;
/*     */ 
/*  68 */     this.speed = 0.4D;
/*     */   }
/*     */ 
/*     */   public boolean isValidMove(Keepaway keepaway, MutableDouble2D newLoc)
/*     */   {
/*  73 */     Bag objs = keepaway.fieldEnvironment.getNeighborsWithinDistance(new Double2D(this.loc.x, this.loc.y), 10.0D);
/*     */ 
/*  75 */     double dist = 0.0D;
/*     */ 
/*  78 */     for (int x = 0; x < objs.numObjs; x++)
/*     */     {
/*  80 */       if (objs.objs[x] != this)
/*     */       {
/*  82 */         dist = ((Entity)objs.objs[x]).loc.distance(newLoc);
/*     */ 
/*  84 */         if (((Entity)objs.objs[x]).radius + this.radius > dist) {
/*  85 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  90 */     if (newLoc.x > keepaway.xMax)
/*     */     {
/*  92 */       if (this.velocity.x > 0.0D) this.velocity.x = (-this.velocity.x);
/*  93 */       return false;
/*     */     }
/*  95 */     if (newLoc.x < keepaway.xMin)
/*     */     {
/*  97 */       if (this.velocity.x < 0.0D) this.velocity.x = (-this.velocity.x);
/*  98 */       return false;
/*     */     }
/* 100 */     if (newLoc.y > keepaway.yMax)
/*     */     {
/* 102 */       if (this.velocity.y > 0.0D) this.velocity.y = (-this.velocity.y);
/* 103 */       return false;
/*     */     }
/* 105 */     if (newLoc.y < keepaway.yMin)
/*     */     {
/* 107 */       if (this.velocity.y < 0.0D) this.velocity.y = (-this.velocity.y);
/* 108 */       return false;
/*     */     }
/*     */ 
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */   public void capVelocity()
/*     */   {
/* 117 */     if (this.velocity.length() > this.cap)
/* 118 */       this.velocity = this.velocity.resize(this.cap);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.keepaway.Entity
 * JD-Core Version:    0.6.2
 */