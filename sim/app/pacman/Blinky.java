/*    */ package sim.app.pacman;
/*    */ 
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public class Blinky extends Ghost
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Double2D getStartLocation()
/*    */   {
/* 20 */     return new Double2D(13.5D, 13.0D);
/*    */   }
/*    */ 
/*    */   public Blinky(PacMan pacman) {
/* 24 */     super(pacman);
/* 25 */     this.waiting = 0;
/*    */   }
/*    */ 
/*    */   public Double2D getTarget()
/*    */   {
/* 30 */     return new Double2D(this.pacman.pacClosestTo(this.location).location);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pacman.Blinky
 * JD-Core Version:    0.6.2
 */