/*    */ package sim.app.asteroids;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import java.awt.Color;
/*    */ import java.awt.Shape;
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.util.Double2D;
/*    */ import sim.util.MutableDouble2D;
/*    */ 
/*    */ public class Shard extends Element
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   int count;
/*    */   Color color;
/*    */   public static final double VELOCITY = 1.0D;
/*    */   public static final int LIFETIME = 100;
/*    */   public static final double MAXIMUM_EXPLOSION_FORCE = 0.5D;
/*    */   public static final double MAXIMUM_ROTATIONAL_VELOCITY = 0.08726646259971647D;
/*    */ 
/*    */   public Shard(Asteroids asteroids, Shape shape, double orientation, MutableDouble2D velocity, Double2D location, Color color)
/*    */   {
/* 48 */     this.velocity = velocity;
/* 49 */     this.shape = shape;
/* 50 */     this.color = color;
/* 51 */     this.count = 100;
/* 52 */     this.stopper = asteroids.schedule.scheduleRepeating(this);
/* 53 */     this.rotationalVelocity = (asteroids.random.nextDouble() * 0.08726646259971647D * (asteroids.random.nextBoolean() ? 1.0D : -1.0D));
/*    */ 
/* 55 */     this.orientation = orientation;
/* 56 */     asteroids.field.setObjectLocation(this, location);
/* 57 */     asteroids.schedule.scheduleOnceIn(100.0D, new Steppable()
/*    */     {
/*    */       public void step(SimState state)
/*    */       {
/* 62 */         Shard.this.end((Asteroids)state);
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 70 */     super.step(state);
/* 71 */     this.count -= 1;
/*    */   }
/*    */ 
/*    */   public Color getColor()
/*    */   {
/* 77 */     double v = this.count / 100.0D;
/* 78 */     return new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), (int)(255.0D * v));
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.asteroids.Shard
 * JD-Core Version:    0.6.2
 */