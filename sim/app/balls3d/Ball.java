/*     */ package sim.app.balls3d;
/*     */ 
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous3D;
/*     */ import sim.field.network.Edge;
/*     */ import sim.field.network.Network;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double3D;
/*     */ 
/*     */ public class Ball
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public double forcex;
/*     */   public double forcey;
/*     */   public double forcez;
/*     */   public double mass;
/*     */   public double oldMass;
/*     */   public double velocityx;
/*     */   public double velocityy;
/*     */   public double velocityz;
/*     */   public boolean collision;
/*     */   public boolean oldCollision;
/*     */   public double diameter;
/*  62 */   Bag myBag = new Bag();
/*     */ 
/*     */   public double getVelocityX()
/*     */   {
/*  43 */     return this.velocityx; } 
/*  44 */   public void setVelocityX(double val) { this.velocityx = val; } 
/*  45 */   public double getVelocityY() { return this.velocityy; } 
/*  46 */   public void setVelocityY(double val) { this.velocityy = val; } 
/*  47 */   public double getVelocityZ() { return this.velocityz; } 
/*  48 */   public void setVelocityZ(double val) { this.velocityz = val; } 
/*  49 */   public double getMass() { return this.mass; } 
/*  50 */   public void setMass(double val) { if (val > 0.0D) { this.mass = val; this.diameter = Math.sqrt(val); } }
/*     */ 
/*     */   public Ball(double vx, double vy, double vz, double m)
/*     */   {
/*  54 */     this.velocityx = vx;
/*  55 */     this.velocityy = vy;
/*  56 */     this.velocityz = vz;
/*  57 */     this.mass = m;
/*  58 */     this.oldMass = m;
/*  59 */     this.diameter = Math.sqrt(m);
/*     */   }
/*     */ 
/*     */   public void computeCollision(Balls3D tut)
/*     */   {
/*  65 */     Double3D me = tut.balls.getObjectLocation(this);
/*  66 */     Bag b = tut.balls.getNeighborsExactlyWithinDistance(me, 5.0D);
/*  67 */     this.collision = (b.numObjs > 1);
/*     */   }
/*     */ 
/*     */   public void addForce(Double3D otherBallLoc, Double3D myLoc, Band band)
/*     */   {
/*  74 */     double dx = otherBallLoc.x - myLoc.x;
/*  75 */     double dy = otherBallLoc.y - myLoc.y;
/*  76 */     double dz = otherBallLoc.z - myLoc.z;
/*  77 */     double len = Math.sqrt(dx * dx + dy * dy + dz * dz);
/*  78 */     double l = band.laxDistance;
/*     */ 
/*  80 */     double k = band.strength / 512.0D;
/*  81 */     double forcemagnitude = (len - l) * k;
/*     */ 
/*  84 */     if (len - l > 0.0D)
/*     */     {
/*  86 */       this.forcex += dx * forcemagnitude / len;
/*  87 */       this.forcey += dy * forcemagnitude / len;
/*  88 */       this.forcez += dz * forcemagnitude / len;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void computeForce(SimState state)
/*     */   {
/*  94 */     Balls3D tut = (Balls3D)state;
/*  95 */     Network bands = tut.bands;
/*  96 */     Continuous3D balls = tut.balls;
/*     */ 
/*  98 */     Double3D me = balls.getObjectLocation(this);
/*     */ 
/* 100 */     this.forcex = 0.0D; this.forcey = 0.0D; this.forcez = 0.0D;
/*     */ 
/* 104 */     Bag in = bands.getEdgesIn(this);
/* 105 */     Bag out = bands.getEdgesOut(this);
/* 106 */     if (in != null)
/* 107 */       for (int x = 0; x < in.numObjs; x++)
/*     */       {
/* 109 */         Edge e = (Edge)in.objs[x];
/* 110 */         Band b = (Band)e.info;
/* 111 */         Ball other = (Ball)e.from();
/* 112 */         Double3D him = balls.getObjectLocation(other);
/* 113 */         addForce(him, me, b);
/*     */       }
/* 115 */     if (out != null)
/* 116 */       for (int x = 0; x < out.numObjs; x++)
/*     */       {
/* 118 */         Edge e = (Edge)out.objs[x];
/* 119 */         Band b = (Band)e.info;
/* 120 */         Ball other = (Ball)e.to();
/* 121 */         Double3D him = balls.getObjectLocation(other);
/* 122 */         addForce(him, me, b);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/* 128 */     Balls3D tut = (Balls3D)state;
/*     */ 
/* 131 */     double ax = this.forcex / this.mass;
/* 132 */     double ay = this.forcey / this.mass;
/* 133 */     double az = this.forcez / this.mass;
/*     */ 
/* 136 */     this.velocityx += ax;
/* 137 */     this.velocityy += ay;
/* 138 */     this.velocityz += az;
/*     */ 
/* 141 */     Double3D pos = tut.balls.getObjectLocation(this);
/* 142 */     Double3D newpos = new Double3D(pos.x + this.velocityx, pos.y + this.velocityy, pos.z + this.velocityz);
/* 143 */     tut.balls.setObjectLocation(this, newpos);
/*     */ 
/* 146 */     computeCollision(tut);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.balls3d.Ball
 * JD-Core Version:    0.6.2
 */