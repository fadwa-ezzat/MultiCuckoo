/*     */ package sim.app.swarmgame;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.portrayal.Oriented2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class Agent
/*     */   implements Steppable, Oriented2D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public Agent a;
/*     */   public Agent b;
/*  17 */   public Double2D lastLoc = new Double2D(0.0D, 0.0D);
/*  18 */   public Double2D loc = new Double2D(0.0D, 0.0D);
/*     */ 
/*     */   public double orientation2D()
/*     */   {
/*  22 */     return Math.atan2(this.loc.y - this.lastLoc.y, this.loc.x - this.lastLoc.x);
/*     */   }
/*     */ 
/*     */   public void pick(SwarmGame swarm)
/*     */   {
/*  27 */     Bag agents = swarm.agents.allObjects;
/*     */     do
/*     */     {
/*  30 */       this.a = ((Agent)agents.objs[swarm.random.nextInt(agents.numObjs)]);
/*     */     }
/*  32 */     while (this.a == this);
/*     */     do
/*     */     {
/*  35 */       this.b = ((Agent)agents.objs[swarm.random.nextInt(agents.numObjs)]);
/*     */     }
/*  37 */     while ((this.b == this.a) || (this.b == this));
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/*  42 */     SwarmGame swarm = (SwarmGame)state;
/*  43 */     Double2D aLoc = swarm.agents.getObjectLocation(this.a);
/*  44 */     Double2D bLoc = swarm.agents.getObjectLocation(this.b);
/*  45 */     this.loc = swarm.agents.getObjectLocation(this);
/*     */ 
/*  47 */     double dis = 0.0D; double dx = 0.0D; double dy = 0.0D; double dx0 = 0.0D; double dy0 = 0.0D; double dx1 = 0.0D; double dy1 = 0.0D; double dx2 = 0.0D; double dy2 = 0.0D; double dx3 = 0.0D; double dy3 = 0.0D; double dx4 = 0.0D; double dy4 = 0.0D;
/*     */ 
/*  50 */     dx = aLoc.x - this.loc.x;
/*  51 */     dy = aLoc.y - this.loc.y;
/*     */ 
/*  54 */     dis = Math.sqrt(dx * dx + dy * dy);
/*  55 */     if (dis > 0.0D)
/*     */     {
/*  57 */       dx0 = dx / dis;
/*  58 */       dy0 = dy / dis;
/*     */     }
/*     */ 
/*  63 */     dx = this.loc.x - bLoc.x;
/*  64 */     dy = this.loc.y - bLoc.y;
/*     */ 
/*  67 */     dis = Math.sqrt(dx * dx + dy * dy);
/*  68 */     if (dis > 0.0D)
/*     */     {
/*  70 */       dx1 = dx / dis;
/*  71 */       dy1 = dy / dis;
/*     */     }
/*     */ 
/*  76 */     dx = (aLoc.x + bLoc.x) / 2.0D - this.loc.x;
/*  77 */     dy = (aLoc.y + bLoc.y) / 2.0D - this.loc.y;
/*     */ 
/*  80 */     dis = Math.sqrt(dx * dx + dy * dy);
/*  81 */     if (dis > 0.0D)
/*     */     {
/*  83 */       dx2 = dx / dis;
/*  84 */       dy2 = dy / dis;
/*     */     }
/*     */ 
/*  89 */     dx = aLoc.x + (aLoc.x - bLoc.x) - this.loc.x;
/*  90 */     dy = aLoc.y + (aLoc.y - bLoc.y) - this.loc.y;
/*     */ 
/*  93 */     dis = Math.sqrt(dx * dx + dy * dy);
/*  94 */     if (dis > 0.0D)
/*     */     {
/*  96 */       dx3 = dx / dis;
/*  97 */       dy3 = dy / dis;
/*     */     }
/*     */ 
/* 100 */     dx = state.random.nextDouble() - 0.5D;
/* 101 */     dy = state.random.nextDouble() - 0.5D;
/*     */ 
/* 104 */     dis = Math.sqrt(dx * dx + dy * dy);
/* 105 */     if (dis > 0.0D)
/*     */     {
/* 107 */       dx4 = dx / dis;
/* 108 */       dy4 = dy / dis;
/*     */     }
/*     */ 
/* 112 */     dx = swarm.stalker_v * dx0 + swarm.avoider_v * dx1 + swarm.defender_v * dx2 + swarm.aggressor_v * dx3 + swarm.random_v * dx4;
/*     */ 
/* 118 */     dy = swarm.stalker_v * dy0 + swarm.avoider_v * dy1 + swarm.defender_v * dy2 + swarm.aggressor_v * dy3 + swarm.random_v * dy4;
/*     */ 
/* 125 */     dis = Math.sqrt(dx * dx + dy * dy);
/* 126 */     dx = dx / dis * swarm.jump;
/* 127 */     dy = dy / dis * swarm.jump;
/*     */ 
/* 129 */     this.lastLoc = this.loc;
/* 130 */     this.loc = new Double2D(this.loc.x + dx, this.loc.y + dy);
/* 131 */     swarm.agents.setObjectLocation(this, this.loc);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.swarmgame.Agent
 * JD-Core Version:    0.6.2
 */