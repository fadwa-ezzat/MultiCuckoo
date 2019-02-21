/*    */ package sim.app.mousetraps;
/*    */ 
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.engine.Steppable;
/*    */ import sim.field.continuous.Continuous3D;
/*    */ import sim.util.Double3D;
/*    */ 
/*    */ public class Ball
/*    */   implements Steppable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public double posX;
/*    */   public double posY;
/*    */   public double posZ;
/*    */   public double velocityX;
/*    */   public double velocityY;
/*    */   public double velocityZ;
/*    */ 
/*    */   public Ball(double x, double y, double z, double vx, double vy, double vz)
/*    */   {
/* 20 */     this.posX = x;
/* 21 */     this.posY = y;
/* 22 */     this.posZ = z;
/* 23 */     this.velocityX = vx;
/* 24 */     this.velocityY = vy;
/* 25 */     this.velocityZ = vz;
/*    */   }
/*    */ 
/*    */   public void step(SimState state)
/*    */   {
/* 30 */     MouseTraps sim = (MouseTraps)state;
/* 31 */     if ((this.posZ <= 0.0D) && (this.velocityZ <= 0.0D))
/*    */     {
/* 33 */       sim.schedule.scheduleOnce(sim.schedule.getTime() + 1.0D, new MouseTrap(sim.discretizeX(this.posX), sim.discretizeY(this.posY)));
/* 34 */       sim.ballSpace.remove(this);
/* 35 */       return;
/*    */     }
/* 37 */     double timeStepDuration = 0.015625D;
/* 38 */     this.posX += this.velocityX * timeStepDuration;
/* 39 */     this.posY += this.velocityY * timeStepDuration;
/* 40 */     this.posZ += this.velocityZ * timeStepDuration;
/* 41 */     this.velocityZ -= 9.800000000000001D * timeStepDuration;
/*    */ 
/* 43 */     if (sim.toroidalWorld)
/*    */     {
/* 45 */       this.posX = ((this.posX + sim.spaceWidth) % sim.spaceWidth);
/* 46 */       this.posY = ((this.posY + sim.spaceHeight) % sim.spaceHeight);
/*    */     }
/*    */     else
/*    */     {
/* 53 */       if (this.posX > sim.spaceWidth)
/*    */       {
/* 55 */         this.posX = sim.spaceWidth; this.velocityX = (-this.velocityX);
/*    */       }
/* 57 */       if (this.posX < 0.0D)
/*    */       {
/* 59 */         this.posX = 0.0D; this.velocityX = (-this.velocityX);
/*    */       }
/* 61 */       if (this.posY >= sim.spaceHeight)
/*    */       {
/* 63 */         this.posY = sim.spaceHeight; this.velocityY = (-this.velocityY);
/*    */       }
/* 65 */       if (this.posY < 0.0D)
/*    */       {
/* 67 */         this.posY = 0.0D; this.velocityY = (-this.velocityY);
/*    */       }
/*    */     }
/* 70 */     sim.ballSpace.setObjectLocation(this, new Double3D(this.posX, this.posY, this.posZ));
/* 71 */     sim.schedule.scheduleOnce(this);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.mousetraps.Ball
 * JD-Core Version:    0.6.2
 */