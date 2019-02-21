/*    */ package sim.app.pacman;
/*    */ 
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.util.Double2D;
/*    */ import sim.util.MutableDouble2D;
/*    */ 
/*    */ public class Pinky extends Ghost
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int DIST = 4;
/*    */ 
/*    */   public Double2D getStartLocation()
/*    */   {
/* 26 */     return new Double2D(12.5D, 16.0D);
/*    */   }
/*    */ 
/*    */   public Pinky(PacMan pacman) {
/* 30 */     super(pacman);
/*    */   }
/*    */ 
/*    */   public Double2D getTarget()
/*    */   {
/* 35 */     Pac pac = this.pacman.pacClosestTo(this.location);
/* 36 */     MutableDouble2D loc = pac.location;
/* 37 */     Continuous2D agents = this.pacman.agents;
/* 38 */     switch (pac.lastAction) {
/*    */     case 0:
/* 40 */       return new Double2D(loc.x, agents.sty(loc.y - 4.0D));
/*    */     case 1:
/* 41 */       return new Double2D(agents.stx(loc.x + 4.0D), loc.y);
/*    */     case 2:
/* 42 */       return new Double2D(loc.x, agents.sty(loc.y + 4.0D));
/*    */     case 3:
/* 43 */       return new Double2D(agents.stx(loc.x - 4.0D), loc.y);
/*    */     }
/* 45 */     return new Double2D(loc.x, loc.y);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pacman.Pinky
 * JD-Core Version:    0.6.2
 */