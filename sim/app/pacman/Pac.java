/*     */ package sim.app.pacman;
/*     */ 
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.MutableDouble2D;
/*     */ 
/*     */ public class Pac extends Agent
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int WAIT_TIME = 100;
/*     */   public static final int SPIN_TIME = 100;
/*     */   public static final int SPIN_SPEED = 5;
/*     */   public static final int PAC_DISCRETIZATION = 9;
/*  33 */   public int eatGhostScore = 200;
/*     */   public int tag;
/*     */   public Stoppable stopper;
/*     */ 
/*     */   public Pac(PacMan pacman, int tag)
/*     */   {
/*  44 */     super(pacman);
/*  45 */     this.tag = tag;
/*  46 */     this.discretization = 9;
/*  47 */     this.stopper = pacman.schedule.scheduleRepeating(this, 0, 1.0D);
/*     */   }
/*     */ 
/*     */   public Double2D getStartLocation() {
/*  51 */     return new Double2D(13.5D, 25.0D);
/*     */   }
/*     */ 
/*     */   protected void doPolicyStep(SimState state)
/*     */   {
/*  61 */     int nextAction = this.pacman.getNextAction(this.tag);
/*     */ 
/*  64 */     if (isPossibleToDoAction(nextAction))
/*     */     {
/*  66 */       performAction(nextAction);
/*     */     }
/*  68 */     else if (isPossibleToDoAction(this.lastAction))
/*     */     {
/*  70 */       performAction(this.lastAction);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/*  84 */     doPolicyStep(state);
/*     */ 
/*  87 */     Bag nearby = this.pacman.dots.getNeighborsWithinDistance(new Double2D(this.location), 0.3D);
/*  88 */     for (int i = 0; i < nearby.numObjs; i++)
/*     */     {
/*  90 */       Object obj = nearby.objs[i];
/*  91 */       if (((obj instanceof Energizer)) && (this.pacman.dots.getObjectLocation(obj).equals(this.location)))
/*     */       {
/*  93 */         this.pacman.score += 40;
/*  94 */         this.pacman.dots.remove(obj);
/*  95 */         this.eatGhostScore = 200;
/*  96 */         this.pacman.frightenGhosts = true;
/*     */ 
/* 100 */         this.pacman.schedule.scheduleOnce(new Steppable()
/*     */         {
/*     */           public void step(SimState state)
/*     */           {
/* 104 */             Pac.this.pacman.frightenGhosts = false;
/*     */           }
/*     */         }
/*     */         , -1);
/*     */       }
/*     */ 
/* 108 */       if (((obj instanceof Dot)) && (this.pacman.dots.getObjectLocation(obj).equals(this.location)))
/*     */       {
/* 110 */         this.pacman.score += 10;
/* 111 */         this.pacman.dots.remove(obj);
/*     */       }
/*     */     }
/* 114 */     if ((nearby.numObjs > 0) && 
/* 115 */       (this.pacman.dots.size() == 0))
/*     */     {
/* 117 */       this.pacman.schedule.scheduleOnceIn(0.25D, new Steppable()
/*     */       {
/*     */         public void step(SimState state)
/*     */         {
/* 121 */           Pac.this.resetLevel();
/*     */         }
/*     */ 
/*     */       });
/*     */     }
/*     */ 
/* 128 */     nearby = this.pacman.agents.getNeighborsWithinDistance(new Double2D(this.location), 0.3D);
/* 129 */     for (int i = 0; i < nearby.numObjs; i++)
/*     */     {
/* 131 */       Object obj = nearby.objs[i];
/* 132 */       if (((obj instanceof Ghost)) && (this.location.distanceSq(this.pacman.agents.getObjectLocation(obj)) <= 0.2D))
/*     */       {
/* 134 */         Ghost m = (Ghost)obj;
/* 135 */         if (m.frightened > 0)
/*     */         {
/* 137 */           this.pacman.score += this.eatGhostScore;
/* 138 */           this.eatGhostScore *= 2;
/* 139 */           m.putInJail();
/*     */         }
/*     */         else
/*     */         {
/* 143 */           this.pacman.schedule.scheduleOnceIn(0.5D, new Steppable()
/*     */           {
/*     */             public void step(SimState state)
/*     */             {
/* 147 */               Pac.this.die();
/*     */             }
/*     */           });
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void resetLevel()
/*     */   {
/* 164 */     this.pacman.schedule.clear();
/*     */ 
/* 167 */     this.pacman.schedule.scheduleOnce(new Steppable()
/*     */     {
/* 170 */       public int count = 0;
/*     */ 
/*     */       public void step(SimState state) {
/* 173 */         if (++this.count < 200) Pac.this.pacman.schedule.scheduleOnce(this);
/*     */       }
/*     */     });
/* 177 */     this.pacman.schedule.scheduleOnceIn(200.0D, new Steppable()
/*     */     {
/*     */       public void step(SimState state) {
/* 180 */         Pac.this.pacman.level += 1; Pac.this.pacman.resetGame();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void die()
/*     */   {
/* 197 */     this.pacman.deaths += 1;
/* 198 */     if (this.pacman.pacsLeft() > 1)
/*     */     {
/* 201 */       if (this.stopper != null) this.stopper.stop();
/* 202 */       this.stopper = null;
/* 203 */       this.pacman.agents.remove(this);
/* 204 */       this.pacman.pacs[this.tag] = null;
/* 205 */       return;
/*     */     }
/*     */ 
/* 211 */     this.pacman.schedule.clear();
/*     */ 
/* 214 */     this.pacman.schedule.scheduleOnce(new Steppable()
/*     */     {
/* 217 */       public int count = 0;
/*     */ 
/*     */       public void step(SimState state) {
/* 220 */         if (++this.count < 100) Pac.this.pacman.schedule.scheduleOnce(this);
/*     */       }
/*     */     });
/* 225 */     this.pacman.schedule.scheduleOnceIn(100.0D, new Steppable()
/*     */     {
/*     */       public void step(SimState state)
/*     */       {
/* 231 */         Bag b = Pac.this.pacman.agents.getAllObjects();
/* 232 */         for (int i = 0; i < b.numObjs; i++) if (b.objs[i] != Pac.this) { b.remove(i); i--;
/*     */           }
/*     */       }
/*     */     });
/* 237 */     this.pacman.schedule.scheduleOnceIn(101.0D, new Steppable()
/*     */     {
/* 240 */       public int count = 0;
/*     */ 
/*     */       public void step(SimState state) {
/* 243 */         if (this.count % 5 == 0) Pac.this.lastAction = ((Pac.this.lastAction + 1) % 4);
/* 244 */         if (++this.count < 100) Pac.this.pacman.schedule.scheduleOnce(this);
/*     */       }
/*     */     });
/* 249 */     this.pacman.schedule.scheduleOnceIn(300.0D, new Steppable()
/*     */     {
/*     */       public void step(SimState state) {
/* 252 */         Pac.this.pacman.resetAgents();
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pacman.Pac
 * JD-Core Version:    0.6.2
 */