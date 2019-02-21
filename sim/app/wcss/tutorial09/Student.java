/*    */ package sim.app.wcss.tutorial09;
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
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 25 */     Students students = (Students)state;
/* 26 */     Continuous2D yard = students.yard;
/*    */ 
/* 28 */     Double2D me = students.yard.getObjectLocation(this);
/*    */ 
/* 30 */     MutableDouble2D sumForces = new MutableDouble2D();
/*    */ 
/* 32 */     this.friendsClose = (this.enemiesCloser = 0.0D);
/*    */ 
/* 35 */     MutableDouble2D forceVector = new MutableDouble2D();
/* 36 */     Bag out = students.buddies.getEdges(this, null);
/* 37 */     int len = out.size();
/* 38 */     for (int buddy = 0; buddy < len; buddy++)
/*    */     {
/* 40 */       Edge e = (Edge)out.get(buddy);
/* 41 */       double buddiness = ((Double)e.info).doubleValue();
/*    */ 
/* 45 */       Double2D him = students.yard.getObjectLocation(e.getOtherNode(this));
/*    */ 
/* 47 */       if (buddiness >= 0.0D)
/*    */       {
/* 49 */         forceVector.setTo((him.x - me.x) * buddiness, (him.y - me.y) * buddiness);
/* 50 */         if (forceVector.length() > 3.0D)
/* 51 */           forceVector.resize(3.0D);
/* 52 */         this.friendsClose += forceVector.length();
/*    */       }
/*    */       else
/*    */       {
/* 56 */         forceVector.setTo((him.x - me.x) * buddiness, (him.y - me.y) * buddiness);
/* 57 */         if (forceVector.length() > 3.0D)
/* 58 */           forceVector.resize(0.0D);
/* 59 */         else if (forceVector.length() > 0.0D)
/* 60 */           forceVector.resize(3.0D - forceVector.length());
/* 61 */         this.enemiesCloser += forceVector.length();
/*    */       }
/* 63 */       sumForces.addIn(forceVector);
/*    */     }
/*    */ 
/* 68 */     sumForces.addIn(new Double2D((yard.width * 0.5D - me.x) * students.forceToSchoolMultiplier, (yard.height * 0.5D - me.y) * students.forceToSchoolMultiplier));
/*    */ 
/* 72 */     sumForces.addIn(new Double2D(students.randomMultiplier * (students.random.nextDouble() * 1.0D - 0.5D), students.randomMultiplier * (students.random.nextDouble() * 1.0D - 0.5D)));
/*    */ 
/* 75 */     sumForces.addIn(me);
/*    */ 
/* 77 */     students.yard.setObjectLocation(this, new Double2D(sumForces));
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.wcss.tutorial09.Student
 * JD-Core Version:    0.6.2
 */