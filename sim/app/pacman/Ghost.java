/*     */ package sim.app.pacman;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.MutableDouble2D;
/*     */ import sim.util.Valuable;
/*     */ 
/*     */ public abstract class Ghost extends Agent
/*     */   implements Steppable, Valuable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int FRIGHTENED_PERIOD = 360;
/*     */   public static final int FRIGHTENED_DISCRETIZATION = 15;
/*     */   public static final int REGULAR_DISCRETIZATION = 10;
/*     */   public static final int WAITING_PERIOD = 360;
/*     */   public static final int INITIAL_WAITING_PERIOD = 90;
/*  58 */   public Double2D exitLocation = new Double2D(13.5D, 16.0D);
/*     */ 
/*  61 */   public int frightened = 0;
/*     */ 
/*  64 */   public int waiting = 90;
/*     */ 
/*  67 */   public boolean exiting = false;
/*     */   static final int MIN_DIST_FOR_TOROIDAL = 4;
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/*  73 */     if (this.frightened > 0)
/*     */     {
/*  75 */       if (this.frightened > 120) return 4.0D;
/*     */ 
/*  78 */       int s = this.frightened / 20;
/*  79 */       if (s % 2 == 0) return 4.0D;
/*  80 */       return 5.0D;
/*     */     }
/*     */ 
/*  83 */     switch (this.lastAction) {
/*     */     case 0:
/*  85 */       return 0.0D;
/*     */     case 3:
/*  86 */       return 1.0D;
/*     */     case 2:
/*  87 */       return 2.0D;
/*     */     case 1:
/*  88 */       return 3.0D;
/*     */     }
/*  90 */     return 3.0D;
/*     */   }
/*     */ 
/*     */   public void putInJail()
/*     */   {
/*  97 */     this.location = new MutableDouble2D(this.exitLocation);
/*  98 */     this.pacman.agents.setObjectLocation(this, this.exitLocation);
/*  99 */     this.lastAction = 3;
/* 100 */     this.frightened = 0;
/* 101 */     this.waiting = 360;
/*     */   }
/*     */ 
/*     */   public Ghost(PacMan pacman)
/*     */   {
/* 108 */     super(pacman);
/* 109 */     this.lastAction = 3;
/* 110 */     pacman.schedule.scheduleRepeating(this, 1, 1.0D);
/*     */   }
/*     */ 
/*     */   public static int reverseOf(int action)
/*     */   {
/* 118 */     switch (action) {
/*     */     case 0:
/* 120 */       return 2;
/*     */     case 1:
/* 121 */       return 3;
/*     */     case 2:
/* 122 */       return 0;
/*     */     case 3:
/* 123 */       return 1;
/*     */     }
/* 125 */     return -1;
/*     */   }
/*     */ 
/*     */   public abstract Double2D getTarget();
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/* 148 */     if ((this.pacman.frightenGhosts) && (this.waiting <= 0) && (!this.exiting))
/*     */     {
/* 150 */       this.lastAction = reverseOf(this.lastAction);
/* 151 */       this.frightened = 360;
/*     */     }
/*     */ 
/* 154 */     if (this.frightened > 0)
/* 155 */       this.discretization = 15;
/*     */     else {
/* 157 */       this.discretization = 10;
/*     */     }
/*     */ 
/* 160 */     if ((this.waiting <= 0) && (this.location.x == this.exitLocation.x) && (this.location.y <= this.exitLocation.y) && (this.location.y > this.exitLocation.y - 3.0D))
/*     */     {
/* 162 */       this.exiting = true;
/* 163 */       double x = this.location.x;
/* 164 */       double y = this.location.y;
/* 165 */       y = this.pacman.agents.sty(y - speed());
/* 166 */       if (y <= this.exitLocation.y - 3.0D) y = this.exitLocation.y - 3.0D;
/* 167 */       changeLocation(x, y);
/* 168 */       this.lastAction = (this.pacman.random.nextBoolean() ? 3 : 1);
/*     */     }
/*     */     else
/*     */     {
/* 173 */       this.exiting = false;
/*     */ 
/* 175 */       if ((this.location.x == (int)this.location.x) && (this.location.y == (int)this.location.y))
/*     */       {
/* 177 */         double x = this.location.x;
/* 178 */         double y = this.location.y;
/*     */ 
/* 180 */         int bestAction = -1;
/* 181 */         double bestActionDistanceSquared = (1.0D / 0.0D);
/*     */ 
/* 183 */         Double2D target = getTarget();
/*     */ 
/* 185 */         int reverseAction = reverseOf(this.lastAction);
/* 186 */         Continuous2D agents = this.pacman.agents;
/*     */ 
/* 189 */         double nx = 0.0D;
/* 190 */         double ny = 0.0D;
/*     */ 
/* 192 */         int tick = 1;
/*     */ 
/* 195 */         for (int action = 0; action <= 3; action++) {
/* 196 */           if ((action != reverseAction) && (isPossibleToDoAction(action)))
/*     */           {
/* 198 */             switch (action) {
/*     */             case 0:
/* 200 */               nx = x; ny = y - 1.0D; break;
/*     */             case 1:
/* 201 */               nx = x + 1.0D; ny = y; break;
/*     */             case 2:
/* 202 */               nx = x; ny = y + 1.0D; break;
/*     */             case 3:
/* 203 */               nx = x - 1.0D; ny = y; break;
/*     */             default:
/* 205 */               throw new RuntimeException("default case should never occur");
/*     */             }
/*     */ 
/* 213 */             double dist = 0.0D;
/* 214 */             if (((this.frightened <= 0) && (Math.abs(agents.stx(target.x - nx)) <= 4.0D)) || (Math.abs(agents.sty(target.y - ny)) < -4.0D))
/*     */             {
/* 217 */               dist = agents.tds(target, new Double2D(nx, ny));
/*     */             } else dist = target.distanceSq(new Double2D(nx, ny));
/*     */ 
/* 220 */             if (((this.frightened <= 0) && ((bestAction == -1) || (dist < bestActionDistanceSquared))) || ((this.frightened > 0) && (this.pacman.random.nextBoolean(1.0D / tick++))))
/*     */             {
/* 222 */               bestAction = action; bestActionDistanceSquared = dist;
/*     */             }
/*     */           }
/*     */         }
/* 226 */         if (bestAction == -1) {
/* 227 */           bestAction = reverseAction;
/*     */         }
/* 229 */         performAction(bestAction);
/*     */       } else {
/* 231 */         performAction(this.lastAction);
/*     */       }
/*     */ 
/* 235 */       if (--this.frightened < 0) this.frightened = 0;
/* 236 */       if (--this.waiting < 0) this.waiting = 0;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pacman.Ghost
 * JD-Core Version:    0.6.2
 */