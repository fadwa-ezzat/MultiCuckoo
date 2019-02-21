/*    */ package sim.app.asteroids;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.geom.Ellipse2D.Double;
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.util.Bag;
/*    */ import sim.util.Double2D;
/*    */ import sim.util.MutableDouble2D;
/*    */ 
/*    */ public class Bullet extends Element
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final double VELOCITY = 1.0D;
/*    */   public static final int LIFETIME = 100;
/*    */ 
/*    */   public Bullet(Asteroids asteroids, MutableDouble2D velocity, Double2D location, int lifetime)
/*    */   {
/* 35 */     this.shape = new Ellipse2D.Double(-1.0D, -1.0D, 2.0D, 2.0D);
/* 36 */     this.velocity = velocity;
/* 37 */     this.rotationalVelocity = 0.0D;
/* 38 */     this.stopper = asteroids.schedule.scheduleRepeating(this);
/* 39 */     asteroids.field.setObjectLocation(this, location);
/* 40 */     asteroids.schedule.scheduleOnceIn(lifetime, new Steppable()
/*    */     {
/*    */       public void step(SimState state)
/*    */       {
/* 45 */         Bullet.this.end((Asteroids)state);
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 53 */     testForHit((Asteroids)state);
/* 54 */     super.step(state);
/*    */   }
/*    */ 
/*    */   public Color getColor() {
/* 58 */     return Color.white;
/*    */   }
/*    */ 
/*    */   public void testForHit(Asteroids asteroids)
/*    */   {
/* 65 */     Bag a = asteroids.field.getAllObjects();
/* 66 */     for (int i = 0; i < a.numObjs; i++)
/*    */     {
/* 68 */       Object obj = a.objs[i];
/* 69 */       if ((obj instanceof Asteroid))
/*    */       {
/* 71 */         Asteroid asteroid = (Asteroid)a.objs[i];
/* 72 */         if (asteroid.collisionWithElement(asteroids, this))
/*    */         {
/* 74 */           end(asteroids);
/* 75 */           asteroid.breakApart(asteroids);
/* 76 */           asteroids.score += 1;
/* 77 */           break;
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.asteroids.Bullet
 * JD-Core Version:    0.6.2
 */