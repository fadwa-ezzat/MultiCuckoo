/*    */ package sim.app.particles3d;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.grid.SparseGrid3D;
/*    */ import sim.util.Bag;
/*    */ import sim.util.Int3D;
/*    */ import sim.util.IntBag;
/*    */ 
/*    */ public class Particle
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 17 */   public boolean randomize = false;
/*    */   public int xdir;
/*    */   public int ydir;
/*    */   public int zdir;
/* 22 */   IntBag xPos = new IntBag();
/* 23 */   IntBag yPos = new IntBag();
/* 24 */   IntBag zPos = new IntBag();
/*    */ 
/*    */   public Particle(int xdir, int ydir, int zdir)
/*    */   {
/* 28 */     this.xdir = xdir;
/* 29 */     this.ydir = ydir;
/* 30 */     this.zdir = zdir;
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 35 */     Particles3D tut = (Particles3D)state;
/*    */ 
/* 37 */     Int3D location = tut.particles.getObjectLocation(this);
/* 38 */     tut.trails.field[location.x][location.y][location.z] = 1.0D;
/*    */ 
/* 41 */     if (this.randomize)
/*    */     {
/* 43 */       this.xdir = (tut.random.nextInt(3) - 1);
/* 44 */       this.ydir = (tut.random.nextInt(3) - 1);
/* 45 */       this.zdir = (tut.random.nextInt(3) - 1);
/* 46 */       this.randomize = false;
/*    */     }
/*    */ 
/* 50 */     int newx = location.x + this.xdir;
/* 51 */     int newy = location.y + this.ydir;
/* 52 */     int newz = location.z + this.zdir;
/*    */ 
/* 55 */     if (newx < 0) { newx++; this.xdir = (-this.xdir);
/* 56 */     } else if (newx >= tut.particles.getWidth()) { newx--; this.xdir = (-this.xdir); }
/* 57 */     if (newy < 0) { newy++; this.ydir = (-this.ydir);
/* 58 */     } else if (newy >= tut.particles.getHeight()) { newy--; this.ydir = (-this.ydir); }
/* 59 */     if (newz < 0) { newz++; this.zdir = (-this.zdir);
/* 60 */     } else if (newz >= tut.particles.getLength()) { newz--; this.zdir = (-this.zdir);
/*    */     }
/*    */ 
/* 63 */     Int3D newLoc = new Int3D(newx, newy, newz);
/* 64 */     tut.particles.setObjectLocation(this, newLoc);
/*    */ 
/* 67 */     Bag p = tut.particles.getObjectsAtLocation(newLoc);
/* 68 */     if (p.numObjs > 1)
/*    */     {
/* 70 */       for (int x = 0; x < p.numObjs; x++)
/* 71 */         ((Particle)p.objs[x]).randomize = true;
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.particles3d.Particle
 * JD-Core Version:    0.6.2
 */