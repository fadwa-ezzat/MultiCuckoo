/*    */ package sim.app.wcss.tutorial08;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.field.network.Edge;
/*    */ import sim.field.network.Network;
/*    */ import sim.util.Bag;
/*    */ import sim.util.Double2D;
/*    */ import sim.util.MutableDouble2D;
/*    */ 
/*    */ public class Student
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final double MAX_FORCE = 3.0D;
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 21 */     Students students = (Students)state;
/* 22 */     Continuous2D yard = students.yard;
/*    */ 
/* 24 */     Double2D me = students.yard.getObjectLocation(this);
/*    */ 
/* 26 */     MutableDouble2D sumForces = new MutableDouble2D();
/*    */ 
/* 30 */     MutableDouble2D forceVector = new MutableDouble2D();
/* 31 */     Bag out = students.buddies.getEdges(this, null);
/* 32 */     int len = out.size();
/* 33 */     for (int buddy = 0; buddy < len; buddy++)
/*    */     {
/* 35 */       Edge e = (Edge)out.get(buddy);
/* 36 */       double buddiness = ((Double)e.info).doubleValue();
/*    */ 
/* 40 */       Double2D him = students.yard.getObjectLocation(e.getOtherNode(this));
/*    */ 
/* 42 */       if (buddiness >= 0.0D)
/*    */       {
/* 44 */         forceVector.setTo((him.x - me.x) * buddiness, (him.y - me.y) * buddiness);
/* 45 */         if (forceVector.length() > 3.0D)
/* 46 */           forceVector.resize(3.0D);
/*    */       }
/*    */       else
/*    */       {
/* 50 */         forceVector.setTo((him.x - me.x) * buddiness, (him.y - me.y) * buddiness);
/* 51 */         if (forceVector.length() > 3.0D)
/* 52 */           forceVector.resize(0.0D);
/* 53 */         else if (forceVector.length() > 0.0D)
/* 54 */           forceVector.resize(3.0D - forceVector.length());
/*    */       }
/* 56 */       sumForces.addIn(forceVector);
/*    */     }
/*    */ 
/* 62 */     sumForces.addIn(new Double2D((yard.width * 0.5D - me.x) * students.forceToSchoolMultiplier, (yard.height * 0.5D - me.y) * students.forceToSchoolMultiplier));
/*    */ 
/* 66 */     sumForces.addIn(new Double2D(students.randomMultiplier * (students.random.nextDouble() * 1.0D - 0.5D), students.randomMultiplier * (students.random.nextDouble() * 1.0D - 0.5D)));
/*    */ 
/* 69 */     sumForces.addIn(me);
/*    */ 
/* 71 */     students.yard.setObjectLocation(this, new Double2D(sumForces));
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial08.Student
 * JD-Core Version:    0.6.2
 */