/*    */ package sim.app.pacman;
/*    */ 
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.util.Double2D;
/*    */ import sim.util.MutableDouble2D;
/*    */ 
/*    */ public class Inky extends Pinky
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   Blinky blinky;
/*    */ 
/*    */   public Double2D getStartLocation()
/*    */   {
/* 25 */     return new Double2D(13.5D, 16.0D);
/*    */   }
/*    */ 
/*    */   public Inky(PacMan pacman, Blinky blinky) {
/* 29 */     super(pacman);
/* 30 */     this.blinky = blinky;
/*    */   }
/*    */ 
/*    */   public Double2D getTarget()
/*    */   {
/* 35 */     Double2D target = super.getTarget();
/* 36 */     MutableDouble2D blinkyLoc = this.blinky.location;
/* 37 */     Continuous2D agents = this.pacman.agents;
/* 38 */     return new Double2D(agents.stx(2.0D * blinkyLoc.x - target.x), agents.sty(2.0D * blinkyLoc.y - target.y));
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pacman.Inky
 * JD-Core Version:    0.6.2
 */