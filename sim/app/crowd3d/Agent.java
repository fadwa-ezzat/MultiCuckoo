/*     */ package sim.app.crowd3d;
/*     */ 
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.field.continuous.Continuous3D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double3D;
/*     */ import sim.util.MutableDouble3D;
/*     */ 
/*     */ public class Agent
/*     */   implements Steppable, Stoppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final double SIGHT = 5.0D;
/*     */   public static final double SPEED = 0.05D;
/*     */   public static final double WALL_AVERSION = 4.0D;
/*     */   public static final double CROWD_AVERSION = 1.0D;
/*     */   public static final double FORCE_MIN_THRESHOLD = 0.75D;
/*  21 */   MutableDouble3D direction = new MutableDouble3D();
/*  22 */   static MutableDouble3D tmpSumOfCrowdForces = new MutableDouble3D();
/*  23 */   static MutableDouble3D tmpSumOfWallForces = new MutableDouble3D();
/*  24 */   static MutableDouble3D tmpSumOfForces = new MutableDouble3D();
/*  25 */   static MutableDouble3D tmpMyPosition = new MutableDouble3D();
/*     */ 
/*  98 */   private Stoppable stopper = null;
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/*  29 */     CrowdSim hb = (CrowdSim)state;
/*  30 */     Double3D myPositionD3D = hb.boidSpace.getObjectLocation(this);
/*  31 */     tmpMyPosition.x = myPositionD3D.x;
/*  32 */     tmpMyPosition.y = myPositionD3D.y;
/*  33 */     tmpMyPosition.z = myPositionD3D.z;
/*     */ 
/*  35 */     Bag neighbors = hb.boidSpace.getNeighborsWithinDistance(myPositionD3D, 5.0D);
/*  36 */     tmpSumOfCrowdForces.x = (tmpSumOfCrowdForces.y = tmpSumOfCrowdForces.z = 0.0D);
/*  37 */     tmpSumOfWallForces.x = (tmpSumOfWallForces.y = tmpSumOfWallForces.z = 0.0D);
/*     */ 
/*  40 */     for (int i = 0; i < neighbors.numObjs; i++)
/*     */     {
/*  42 */       if (neighbors.objs[i] != this)
/*     */       {
/*  44 */         Double3D nPosition = hb.boidSpace.getObjectLocation(neighbors.objs[i]);
/*  45 */         tmpSumOfCrowdForces.x += fn(hb, myPositionD3D.x - nPosition.x);
/*  46 */         tmpSumOfCrowdForces.y += fn(hb, myPositionD3D.y - nPosition.y);
/*  47 */         tmpSumOfCrowdForces.z += fn(hb, myPositionD3D.z - nPosition.z);
/*     */       }
/*     */     }
/*  49 */     tmpSumOfCrowdForces.multiplyIn(1.0D);
/*     */ 
/*  52 */     if (myPositionD3D.x < 5.0D)
/*  53 */       tmpSumOfWallForces.x += fn(hb, myPositionD3D.x);
/*  54 */     if (myPositionD3D.x > hb.spaceWidth - 5.0D) {
/*  55 */       tmpSumOfWallForces.x -= fn(hb, hb.spaceWidth - myPositionD3D.x);
/*     */     }
/*  57 */     if (myPositionD3D.y < 5.0D)
/*  58 */       tmpSumOfWallForces.y += fn(hb, myPositionD3D.y);
/*  59 */     if (myPositionD3D.y > hb.spaceHeight - 5.0D) {
/*  60 */       tmpSumOfWallForces.y -= fn(hb, hb.spaceHeight - myPositionD3D.y);
/*     */     }
/*  62 */     if (myPositionD3D.z < 5.0D)
/*  63 */       tmpSumOfWallForces.z += fn(hb, myPositionD3D.z);
/*  64 */     if (myPositionD3D.z > hb.spaceDepth - 5.0D)
/*  65 */       tmpSumOfWallForces.z -= fn(hb, hb.spaceDepth - myPositionD3D.z);
/*  66 */     tmpSumOfWallForces.multiplyIn(4.0D);
/*     */ 
/*  70 */     tmpSumOfForces.add(tmpSumOfCrowdForces, tmpSumOfWallForces);
/*     */ 
/*  72 */     if (tmpSumOfForces.length() > 0.75D)
/*     */     {
/*  74 */       tmpSumOfForces.normalize();
/*  75 */       tmpSumOfForces.multiplyIn(0.05D);
/*  76 */       tmpMyPosition.addIn(tmpSumOfForces);
/*     */ 
/*  78 */       clamp(tmpMyPosition, hb);
/*     */ 
/*  81 */       Double3D newLocation = new Double3D(tmpMyPosition.x, tmpMyPosition.y, tmpMyPosition.z);
/*  82 */       hb.boidSpace.setObjectLocation(this, newLocation);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void clamp(MutableDouble3D position, CrowdSim hb)
/*     */   {
/*  88 */     position.x = Math.min(Math.max(position.x, 0.0D), hb.spaceWidth);
/*  89 */     position.y = Math.min(Math.max(position.y, 0.0D), hb.spaceHeight);
/*  90 */     position.z = Math.min(Math.max(position.z, 0.0D), hb.spaceDepth);
/*     */   }
/*     */ 
/*     */   private double fn(CrowdSim hb, double d)
/*     */   {
/*  95 */     return Math.min(hb.maxFnVal, 1.0D / d);
/*     */   }
/*     */ 
/*     */   public void setStopper(Stoppable stopper) {
/*  99 */     this.stopper = stopper; } 
/* 100 */   public void stop() { this.stopper.stop(); }
/*     */ 
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.crowd3d.Agent
 * JD-Core Version:    0.6.2
 */