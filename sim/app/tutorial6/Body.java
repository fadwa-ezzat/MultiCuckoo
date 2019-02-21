/*    */ package sim.app.tutorial6;
/*    */ 
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public class Body
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public double velocity;
/*    */   public double distanceFromSun;
/*    */ 
/*    */   public double getVelocity()
/*    */   {
/* 18 */     return this.velocity; } 
/* 19 */   public double getDistanceFromSun() { return this.distanceFromSun; }
/*    */ 
/*    */   public Body(double vel, double d)
/*    */   {
/* 23 */     this.velocity = vel; this.distanceFromSun = d;
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 28 */     Tutorial6 tut = (Tutorial6)state;
/* 29 */     if (this.distanceFromSun > 0.0D)
/*    */     {
/* 31 */       double theta = this.velocity / this.distanceFromSun * state.schedule.getSteps() % 6.283185307179586D;
/* 32 */       tut.bodies.setObjectLocation(this, new Double2D(this.distanceFromSun * Math.cos(theta), this.distanceFromSun * Math.sin(theta)));
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial6.Body
 * JD-Core Version:    0.6.2
 */