/*    */ package sim.app.wcss.tutorial03;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.util.Double2D;
/*    */ import sim.util.MutableDouble2D;
/*    */ 
/*    */ public class Student
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 18 */     Students students = (Students)state;
/* 19 */     Continuous2D yard = students.yard;
/*    */ 
/* 21 */     Double2D me = students.yard.getObjectLocation(this);
/*    */ 
/* 23 */     MutableDouble2D sumForces = new MutableDouble2D();
/*    */ 
/* 26 */     sumForces.addIn(new Double2D((yard.width * 0.5D - me.x) * students.forceToSchoolMultiplier, (yard.height * 0.5D - me.y) * students.forceToSchoolMultiplier));
/*    */ 
/* 30 */     sumForces.addIn(new Double2D(students.randomMultiplier * (students.random.nextDouble() * 1.0D - 0.5D), students.randomMultiplier * (students.random.nextDouble() * 1.0D - 0.5D)));
/*    */ 
/* 33 */     sumForces.addIn(me);
/*    */ 
/* 35 */     students.yard.setObjectLocation(this, new Double2D(sumForces));
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial03.Student
 * JD-Core Version:    0.6.2
 */