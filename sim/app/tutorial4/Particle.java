/*    */ package sim.app.tutorial4;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.grid.DoubleGrid2D;
/*    */ import sim.field.grid.SparseGrid2D;
/*    */ import sim.util.Bag;
/*    */ import sim.util.Int2D;
/*    */ 
/*    */ public class Particle
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 17 */   public boolean randomize = false;
/*    */   public int xdir;
/*    */   public int ydir;
/*    */ 
/*    */   public int getXDir()
/*    */   {
/* 21 */     return this.xdir; } 
/* 22 */   public int getYDir() { return this.ydir; } 
/* 23 */   public boolean getRandomize() { return this.randomize; } 
/* 24 */   public void setRandomize(boolean val) { this.randomize = val; }
/*    */ 
/*    */   public Particle(int xdir, int ydir)
/*    */   {
/* 28 */     this.xdir = xdir;
/* 29 */     this.ydir = ydir;
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 34 */     Tutorial4 tut = (Tutorial4)state;
/*    */ 
/* 38 */     Int2D location = tut.particles.getObjectLocation(this);
/*    */ 
/* 41 */     tut.trails.field[location.x][location.y] = 1.0D;
/*    */ 
/* 44 */     if (this.randomize)
/*    */     {
/* 46 */       this.xdir = (tut.random.nextInt(3) - 1);
/* 47 */       this.ydir = (tut.random.nextInt(3) - 1);
/* 48 */       this.randomize = false;
/* 49 */       tut.collisions += 1;
/*    */     }
/*    */ 
/* 53 */     int newx = location.x + this.xdir;
/* 54 */     int newy = location.y + this.ydir;
/*    */ 
/* 57 */     if (newx < 0) { newx++; this.xdir = (-this.xdir);
/* 58 */     } else if (newx >= tut.trails.getWidth()) { newx--; this.xdir = (-this.xdir); }
/* 59 */     if (newy < 0) { newy++; this.ydir = (-this.ydir);
/* 60 */     } else if (newy >= tut.trails.getHeight()) { newy--; this.ydir = (-this.ydir);
/*    */     }
/*    */ 
/* 63 */     Int2D newloc = new Int2D(newx, newy);
/* 64 */     tut.particles.setObjectLocation(this, newloc);
/*    */ 
/* 67 */     Bag p = tut.particles.getObjectsAtLocation(newloc);
/* 68 */     if (p.numObjs > 1)
/*    */     {
/* 70 */       for (int x = 0; x < p.numObjs; x++)
/* 71 */         ((Particle)p.objs[x]).randomize = true;
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial4.Particle
 * JD-Core Version:    0.6.2
 */