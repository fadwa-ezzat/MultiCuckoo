/*    */ package sim.app.pacman;
/*    */ 
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public class Clyde extends Blinky
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int DIST = 8;
/*    */   public Double2D scatterTarget;
/*    */ 
/*    */   public Double2D getStartLocation()
/*    */   {
/* 25 */     return new Double2D(14.5D, 16.0D);
/*    */   }
/*    */ 
/*    */   public Clyde(PacMan pacman)
/*    */   {
/* 31 */     super(pacman);
/* 32 */     this.waiting = 90;
/* 33 */     this.scatterTarget = new Double2D(0.0D, 32.0D);
/*    */   }
/*    */ 
/*    */   public Double2D getTarget()
/*    */   {
/* 38 */     if (this.pacman.agents.tds(new Double2D(this.location), new Double2D(this.pacman.pacClosestTo(this.location).location)) > 64.0D)
/* 39 */       return super.getTarget();
/* 40 */     return this.scatterTarget;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pacman.Clyde
 * JD-Core Version:    0.6.2
 */