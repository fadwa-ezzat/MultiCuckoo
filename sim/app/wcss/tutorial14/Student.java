/*    */ package sim.app.wcss.tutorial14;
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
/* 19 */   double friendsClose = 0.0D;
/* 20 */   double enemiesCloser = 10.0D;
/*    */ 
/* 21 */   public double getAgitation() { return this.friendsClose + this.enemiesCloser; } 
/*    */   public String toString() {
/* 23 */     return "[" + System.identityHashCode(this) + "] agitation: " + getAgitation();
/*    */   }
/*    */ 
/*    */   public void step(SimState state) {
/* 27 */     Students students = (Students)state;
/* 28 */     Continuous2D yard = students.yard;
/*    */ 
/* 30 */     Double2D me = students.yard.getObjectLocation(this);
/*    */ 
/* 32 */     MutableDouble2D sumForces = new MutableDouble2D();
/*    */ 
/* 34 */     this.friendsClose = (this.enemiesCloser = 0.0D);
/*    */ 
/* 37 */     MutableDouble2D forceVector = new MutableDouble2D();
/* 38 */     Bag out = students.buddies.getEdges(this, null);
/* 39 */     int len = out.size();
/* 40 */     for (int buddy = 0; buddy < len; buddy++)
/*    */     {
/* 42 */       Edge e = (Edge)out.get(buddy);
/* 43 */       double buddiness = ((Double)e.info).doubleValue();
/*    */ 
/* 47 */       Double2D him = students.yard.getObjectLocation(e.getOtherNode(this));
/*    */ 
/* 49 */       if (buddiness >= 0.0D)
/*    */       {
/* 51 */         forceVector.setTo((him.x - me.x) * buddiness, (him.y - me.y) * buddiness);
/* 52 */         if (forceVector.length() > 3.0D)
/* 53 */           forceVector.resize(3.0D);
/* 54 */         this.friendsClose += forceVector.length();
/*    */       }
/*    */       else
/*    */       {
/* 58 */         forceVector.setTo((him.x - me.x) * buddiness, (him.y - me.y) * buddiness);
/* 59 */         if (forceVector.length() > 3.0D)
/* 60 */           forceVector.resize(0.0D);
/* 61 */         else if (forceVector.length() > 0.0D)
/* 62 */           forceVector.resize(3.0D - forceVector.length());
/* 63 */         this.enemiesCloser += forceVector.length();
/*    */       }
/* 65 */       sumForces.addIn(forceVector);
/*    */     }
/*    */ 
/* 70 */     sumForces.addIn(new Double2D((yard.width * 0.5D - me.x) * students.forceToSchoolMultiplier, (yard.height * 0.5D - me.y) * students.forceToSchoolMultiplier));
/*    */ 
/* 74 */     sumForces.addIn(new Double2D(students.randomMultiplier * (students.random.nextDouble() * 1.0D - 0.5D), students.randomMultiplier * (students.random.nextDouble() * 1.0D - 0.5D)));
/*    */ 
/* 77 */     sumForces.addIn(me);
/*    */ 
/* 79 */     students.yard.setObjectLocation(this, new Double2D(sumForces));
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial14.Student
 * JD-Core Version:    0.6.2
 */