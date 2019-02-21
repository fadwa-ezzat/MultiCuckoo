/*    */ package sim.app.tutorial3;
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
/*    */   public Particle(int xdir, int ydir)
/*    */   {
/* 23 */     this.xdir = xdir;
/* 24 */     this.ydir = ydir;
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 29 */     Tutorial3 tut = (Tutorial3)state;
/*    */ 
/* 33 */     Int2D location = tut.particles.getObjectLocation(this);
/*    */ 
/* 36 */     tut.trails.field[location.x][location.y] = 1.0D;
/*    */ 
/* 39 */     if (this.randomize)
/*    */     {
/* 41 */       this.xdir = (tut.random.nextInt(3) - 1);
/* 42 */       this.ydir = (tut.random.nextInt(3) - 1);
/* 43 */       this.randomize = false;
/*    */     }
/*    */ 
/* 47 */     int newx = location.x + this.xdir;
/* 48 */     int newy = location.y + this.ydir;
/*    */ 
/* 51 */     if (newx < 0) { newx++; this.xdir = (-this.xdir);
/* 52 */     } else if (newx >= tut.trails.getWidth()) { newx--; this.xdir = (-this.xdir); }
/* 53 */     if (newy < 0) { newy++; this.ydir = (-this.ydir);
/* 54 */     } else if (newy >= tut.trails.getHeight()) { newy--; this.ydir = (-this.ydir);
/*    */     }
/*    */ 
/* 57 */     Int2D newloc = new Int2D(newx, newy);
/* 58 */     tut.particles.setObjectLocation(this, newloc);
/*    */ 
/* 61 */     Bag p = tut.particles.getObjectsAtLocation(newloc);
/* 62 */     if (p.numObjs > 1)
/*    */     {
/* 64 */       for (int x = 0; x < p.numObjs; x++)
/* 65 */         ((Particle)p.objs[x]).randomize = true;
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial3.Particle
 * JD-Core Version:    0.6.2
 */