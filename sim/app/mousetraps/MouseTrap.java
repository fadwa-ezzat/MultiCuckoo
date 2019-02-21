/*    */ package sim.app.mousetraps;
/*    */ 
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ 
/*    */ public class MouseTrap
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public int posx;
/*    */   public int posy;
/*    */ 
/*    */   public MouseTrap(int x, int y)
/*    */   {
/* 17 */     this.posx = x;
/* 18 */     this.posy = y;
/*    */   }
/*    */ 
/*    */   public void step(SimState state) {
/* 22 */     ((MouseTraps)state).triggerTrap(this.posx, this.posy);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.mousetraps.MouseTrap
 * JD-Core Version:    0.6.2
 */