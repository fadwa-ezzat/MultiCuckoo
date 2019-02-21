/*     */ package sim.app.keepaway;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.awt.Color;
/*     */ import java.io.PrintStream;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.MutableDouble2D;
/*     */ 
/*     */ public class Ball extends Entity
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public MutableDouble2D stillPos;
/*     */   public double dt;
/*  70 */   MutableDouble2D friction = new MutableDouble2D();
/*  71 */   MutableDouble2D stuckPos = new MutableDouble2D();
/*     */ 
/*     */   public Ball(double x, double y)
/*     */   {
/*  23 */     super(x, y, 1.0D, Color.white);
/*     */ 
/*  25 */     this.cap = 2.0D;
/*     */ 
/*  27 */     this.bump = new MutableDouble2D(0.0D, 0.0D);
/*  28 */     this.stillPos = new MutableDouble2D(0.0D, 0.0D);
/*  29 */     this.dt = 0.0D;
/*     */   }
/*     */ 
/*     */   public MutableDouble2D getForces(Keepaway keepaway)
/*     */   {
/*  36 */     this.sumVector.setTo(0.0D, 0.0D);
/*  37 */     Bag objs = keepaway.fieldEnvironment.getNeighborsWithinDistance(new Double2D(this.loc.x, this.loc.y), 100.0D);
/*     */ 
/*  39 */     double dist = 0.0D;
/*     */ 
/*  41 */     for (int x = 0; x < objs.numObjs; x++)
/*     */     {
/*  43 */       if (objs.objs[x] != this)
/*     */       {
/*  45 */         dist = ((Entity)objs.objs[x]).loc.distance(this.loc);
/*     */ 
/*  47 */         if (((Entity)objs.objs[x]).radius + this.radius > dist)
/*     */         {
/*  49 */           if (!(objs.objs[x] instanceof Ball));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  64 */     this.sumVector = this.sumVector.addIn(this.bump);
/*  65 */     this.bump.x = 0.0D;
/*  66 */     this.bump.y = 0.0D;
/*  67 */     return this.sumVector;
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/*  75 */     Keepaway keepaway = (Keepaway)state;
/*     */ 
/*  78 */     MutableDouble2D force = getForces(keepaway);
/*     */ 
/*  81 */     this.accel.multiply(force, 1.0D / this.mass);
/*     */ 
/*  84 */     this.friction.multiply(this.velocity, -0.025D);
/*     */ 
/*  87 */     this.velocity.addIn(this.accel);
/*  88 */     this.velocity.addIn(this.friction);
/*  89 */     capVelocity();
/*     */ 
/*  92 */     this.newLoc.add(this.loc, this.velocity);
/*     */ 
/*  95 */     if (isValidMove(keepaway, this.newLoc))
/*     */     {
/*  97 */       this.loc = this.newLoc;
/*     */     }
/*     */ 
/* 101 */     if (this.loc.distanceSq(this.stuckPos) < 0.01D) {
/* 102 */       this.dt += 1.0D;
/*     */     }
/*     */     else {
/* 105 */       this.dt = 0.0D;
/* 106 */       this.stuckPos.setTo(this.loc);
/*     */     }
/*     */ 
/* 110 */     if (this.dt > 1000.0D)
/*     */     {
/* 112 */       System.out.println("stuck");
/* 113 */       this.dt = 0.0D;
/* 114 */       this.stuckPos.setTo(this.loc);
/* 115 */       this.loc.x = (keepaway.random.nextDouble() * keepaway.xMax);
/* 116 */       this.loc.y = (keepaway.random.nextDouble() * keepaway.yMax);
/*     */     }
/*     */ 
/* 119 */     keepaway.fieldEnvironment.setObjectLocation(this, new Double2D(this.loc));
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.keepaway.Ball
 * JD-Core Version:    0.6.2
 */