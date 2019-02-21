/*     */ package sim.app.flockers;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.portrayal.Orientable2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class Flocker
/*     */   implements Steppable, Orientable2D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  17 */   public Double2D loc = new Double2D(0.0D, 0.0D);
/*  18 */   public Double2D lastd = new Double2D(0.0D, 0.0D);
/*     */   public Continuous2D flockers;
/*     */   public Flockers theFlock;
/*  21 */   public boolean dead = false;
/*     */ 
/*  23 */   public Flocker(Double2D location) { this.loc = location; }
/*     */ 
/*     */   public Bag getNeighbors()
/*     */   {
/*  27 */     return this.flockers.getNeighborsExactlyWithinDistance(this.loc, this.theFlock.neighborhood, true);
/*     */   }
/*     */   public double getOrientation() {
/*  30 */     return orientation2D(); } 
/*  31 */   public boolean isDead() { return this.dead; } 
/*  32 */   public void setDead(boolean val) { this.dead = val; }
/*     */ 
/*     */   public void setOrientation2D(double val)
/*     */   {
/*  36 */     this.lastd = new Double2D(Math.cos(val), Math.sin(val));
/*     */   }
/*     */ 
/*     */   public double orientation2D()
/*     */   {
/*  41 */     if ((this.lastd.x == 0.0D) && (this.lastd.y == 0.0D)) return 0.0D;
/*  42 */     return Math.atan2(this.lastd.y, this.lastd.x);
/*     */   }
/*     */ 
/*     */   public Double2D momentum()
/*     */   {
/*  47 */     return this.lastd;
/*     */   }
/*     */ 
/*     */   public Double2D consistency(Bag b, Continuous2D flockers)
/*     */   {
/*  52 */     if ((b == null) || (b.numObjs == 0)) return new Double2D(0.0D, 0.0D);
/*     */ 
/*  54 */     double x = 0.0D;
/*  55 */     double y = 0.0D;
/*  56 */     int i = 0;
/*  57 */     int count = 0;
/*  58 */     for (i = 0; i < b.numObjs; i++)
/*     */     {
/*  60 */       Flocker other = (Flocker)b.objs[i];
/*  61 */       if (!other.dead)
/*     */       {
/*  63 */         double dx = flockers.tdx(this.loc.x, other.loc.x);
/*  64 */         double dy = flockers.tdy(this.loc.y, other.loc.y);
/*  65 */         Double2D m = ((Flocker)b.objs[i]).momentum();
/*  66 */         count++;
/*  67 */         x += m.x;
/*  68 */         y += m.y;
/*     */       }
/*     */     }
/*  71 */     if (count > 0) { x /= count; y /= count; }
/*  72 */     return new Double2D(x, y);
/*     */   }
/*     */ 
/*     */   public Double2D cohesion(Bag b, Continuous2D flockers)
/*     */   {
/*  77 */     if ((b == null) || (b.numObjs == 0)) return new Double2D(0.0D, 0.0D);
/*     */ 
/*  79 */     double x = 0.0D;
/*  80 */     double y = 0.0D;
/*     */ 
/*  82 */     int count = 0;
/*  83 */     int i = 0;
/*  84 */     for (i = 0; i < b.numObjs; i++)
/*     */     {
/*  86 */       Flocker other = (Flocker)b.objs[i];
/*  87 */       if (!other.dead)
/*     */       {
/*  89 */         double dx = flockers.tdx(this.loc.x, other.loc.x);
/*  90 */         double dy = flockers.tdy(this.loc.y, other.loc.y);
/*  91 */         count++;
/*  92 */         x += dx;
/*  93 */         y += dy;
/*     */       }
/*     */     }
/*  96 */     if (count > 0) { x /= count; y /= count; }
/*  97 */     return new Double2D(-x / 10.0D, -y / 10.0D);
/*     */   }
/*     */ 
/*     */   public Double2D avoidance(Bag b, Continuous2D flockers)
/*     */   {
/* 102 */     if ((b == null) || (b.numObjs == 0)) return new Double2D(0.0D, 0.0D);
/* 103 */     double x = 0.0D;
/* 104 */     double y = 0.0D;
/*     */ 
/* 106 */     int i = 0;
/* 107 */     int count = 0;
/*     */ 
/* 109 */     for (i = 0; i < b.numObjs; i++)
/*     */     {
/* 111 */       Flocker other = (Flocker)b.objs[i];
/* 112 */       if (other != this)
/*     */       {
/* 114 */         double dx = flockers.tdx(this.loc.x, other.loc.x);
/* 115 */         double dy = flockers.tdy(this.loc.y, other.loc.y);
/* 116 */         double lensquared = dx * dx + dy * dy;
/* 117 */         count++;
/* 118 */         x += dx / (lensquared * lensquared + 1.0D);
/* 119 */         y += dy / (lensquared * lensquared + 1.0D);
/*     */       }
/*     */     }
/* 122 */     if (count > 0) { x /= count; y /= count; }
/* 123 */     return new Double2D(400.0D * x, 400.0D * y);
/*     */   }
/*     */ 
/*     */   public Double2D randomness(MersenneTwisterFast r)
/*     */   {
/* 128 */     double x = r.nextDouble() * 2.0D - 1.0D;
/* 129 */     double y = r.nextDouble() * 2.0D - 1.0D;
/* 130 */     double l = Math.sqrt(x * x + y * y);
/* 131 */     return new Double2D(0.05D * x / l, 0.05D * y / l);
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/* 136 */     Flockers flock = (Flockers)state;
/* 137 */     this.loc = flock.flockers.getObjectLocation(this);
/*     */ 
/* 139 */     if (this.dead) return;
/*     */ 
/* 141 */     Bag b = getNeighbors();
/*     */ 
/* 143 */     Double2D avoid = avoidance(b, flock.flockers);
/* 144 */     Double2D cohe = cohesion(b, flock.flockers);
/* 145 */     Double2D rand = randomness(flock.random);
/* 146 */     Double2D cons = consistency(b, flock.flockers);
/* 147 */     Double2D mome = momentum();
/*     */ 
/* 149 */     double dx = flock.cohesion * cohe.x + flock.avoidance * avoid.x + flock.consistency * cons.x + flock.randomness * rand.x + flock.momentum * mome.x;
/* 150 */     double dy = flock.cohesion * cohe.y + flock.avoidance * avoid.y + flock.consistency * cons.y + flock.randomness * rand.y + flock.momentum * mome.y;
/*     */ 
/* 153 */     double dis = Math.sqrt(dx * dx + dy * dy);
/* 154 */     if (dis > 0.0D)
/*     */     {
/* 156 */       dx = dx / dis * flock.jump;
/* 157 */       dy = dy / dis * flock.jump;
/*     */     }
/*     */ 
/* 160 */     this.lastd = new Double2D(dx, dy);
/* 161 */     this.loc = new Double2D(flock.flockers.stx(this.loc.x + dx), flock.flockers.sty(this.loc.y + dy));
/* 162 */     flock.flockers.setObjectLocation(this, this.loc);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.flockers.Flocker
 * JD-Core Version:    0.6.2
 */