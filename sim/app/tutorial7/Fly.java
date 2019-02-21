/*    */ package sim.app.tutorial7;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.grid.SparseGrid3D;
/*    */ import sim.util.Int3D;
/*    */ 
/*    */ public class Fly
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 18 */     Tutorial7 tut = (Tutorial7)state;
/*    */ 
/* 20 */     SparseGrid3D flies = tut.flies;
/*    */ 
/* 23 */     Int3D myLoc = flies.getObjectLocation(this);
/* 24 */     int x = flies.stx(myLoc.x + (tut.random.nextBoolean() ? 1 : -1));
/* 25 */     int y = flies.sty(myLoc.y + (tut.random.nextBoolean() ? 1 : -1));
/* 26 */     int z = flies.stz(myLoc.z + (tut.random.nextBoolean() ? 1 : -1));
/*    */ 
/* 28 */     flies.setObjectLocation(this, new Int3D(x, y, z));
/*    */ 
/* 31 */     tut.xProjection.field[y][z] += Math.log(x + 2);
/* 32 */     tut.yProjection.field[x][z] += Math.log(y + 2);
/* 33 */     tut.zProjection.field[x][y] += Math.log(z + 2);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial7.Fly
 * JD-Core Version:    0.6.2
 */