/*     */ package sim.app.woims3d;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.awt.Color;
/*     */ import javax.media.j3d.Transform3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.vecmath.Vector3d;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous3D;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ import sim.portrayal3d.simple.SpherePortrayal3D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double3D;
/*     */ 
/*     */ public class Woim3D extends SimplePortrayal3D
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final double CENTROID_DISTANCE = 20.0D;
/*     */   public static final double AVOID_DISTANCE = 16.0D;
/*     */   public static final double COPY_SPEED_DISTANCE = 40.0D;
/*     */   public static final double OBSTACLE_AVOID_COEF = 1.05D;
/*     */   public static final double OBSTACLE_FAST_AVOID_COEF = 1.5D;
/*  34 */   public static final double MAX_DISTANCE = Math.max(20.0D, Math.max(16.0D, 40.0D));
/*     */   public static final double ADJUSTMENT_RATE = 0.025D;
/*     */   public static final double MIN_VELOCITY = 0.25D;
/*     */   public static final double MAX_VELOCITY = 0.75D;
/*     */   Bag nearbyWoims;
/*     */   double[] distSqrTo;
/*     */   double ond;
/*     */   double ondSpeed;
/* 200 */   protected Vector3D woimPosition = new Vector3D(0.0D, 0.0D, 0.0D);
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   static final int numLinks = 12;
/* 243 */   Vector3d[] lastPos = new Vector3d[12];
/* 244 */   Vector3d[] lastPosRel = new Vector3d[12];
/* 245 */   Color[] colors = new Color[12];
/*     */   protected double orientation;
/* 248 */   protected Vector3D velocity = new Vector3D(0.0D, 0.0D, 0.0D);
/* 249 */   protected Vector3D acceleration = new Vector3D(0.0D, 0.0D, 0.0D);
/*     */   public static final float SKIP = 4.0F;
/*     */ 
/*     */   public Woim3D()
/*     */   {
/*  42 */     this.ond = (Math.random() * 2.0D * 3.141592653589793D);
/*  43 */     this.ondSpeed = (0.05D + Math.random() * 0.15D);
/*  44 */     for (int i = 0; i < this.colors.length; i++) {
/*  45 */       this.colors[i] = new Color(63 + 192 * (this.colors.length - i) / this.colors.length, 0, 0);
/*     */     }
/*  47 */     this.velocity = new Vector3D(0.05D, 0.05D, 0.05D);
/*  48 */     computePositions();
/*     */   }
/*     */ 
/*     */   public final double distanceSquared(Vector3D loc1, Vector3D loc2)
/*     */   {
/*  53 */     return (loc1.x - loc2.x) * (loc1.x - loc2.x) + (loc1.y - loc2.y) * (loc1.y - loc2.y) + (loc1.z - loc2.z) * (loc1.z - loc2.z);
/*     */   }
/*     */ 
/*     */   public final double distanceSquared(Vector3D loc1, Double3D loc2)
/*     */   {
/*  58 */     return (loc1.x - loc2.x) * (loc1.x - loc2.x) + (loc1.y - loc2.y) * (loc1.y - loc2.y) + (loc1.z - loc2.z) * (loc1.z - loc2.z);
/*     */   }
/*     */ 
/*     */   public final double distanceSquared(double x1, double y1, double z1, double x2, double y2, double z2)
/*     */   {
/*  63 */     return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2);
/*     */   }
/*     */ 
/*     */   void preprocessWoims(WoimsDemo3D state, Double3D pos, double distance)
/*     */   {
/*  70 */     this.nearbyWoims = state.woimEnvironment.getNeighborsWithinDistance(pos, distance);
/*     */ 
/*  75 */     this.distSqrTo = new double[this.nearbyWoims.numObjs];
/*  76 */     for (int i = 0; i < this.nearbyWoims.numObjs; i++)
/*     */     {
/*  78 */       Woim3D p = (Woim3D)this.nearbyWoims.objs[i];
/*  79 */       this.distSqrTo[i] = distanceSquared(pos.x, pos.y, pos.z, p.x, p.y, p.z);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector3D towardsFlockCenterOfMass(WoimsDemo3D state)
/*     */   {
/*  86 */     if (this.nearbyWoims == null)
/*  87 */       return new Vector3D(0.0D, 0.0D, 0.0D);
/*  88 */     Vector3D mean = new Vector3D(0.0D, 0.0D, 0.0D);
/*  89 */     int n = 0;
/*  90 */     for (int i = 0; i < this.nearbyWoims.numObjs; i++)
/*     */     {
/*  92 */       if ((this.nearbyWoims.objs[i] != this) && (this.distSqrTo[i] <= 400.0D) && (this.distSqrTo[i] > 256.0D))
/*     */       {
/*  96 */         Woim3D p = (Woim3D)this.nearbyWoims.objs[i];
/*  97 */         mean = mean.add(new Double3D(p.x, p.y, p.z));
/*  98 */         n++;
/*     */       }
/*     */     }
/* 101 */     if (n == 0) {
/* 102 */       return new Vector3D(0.0D, 0.0D, 0.0D);
/*     */     }
/*     */ 
/* 105 */     mean = mean.amplify(1.0D / n);
/* 106 */     mean = mean.subtract(this.woimPosition);
/* 107 */     return mean.normalize();
/*     */   }
/*     */ 
/*     */   public Vector3D awayFromCloseBys(WoimsDemo3D state)
/*     */   {
/* 114 */     if (this.nearbyWoims == null)
/* 115 */       return new Vector3D(0.0D, 0.0D, 0.0D);
/* 116 */     Vector3D away = new Vector3D(0.0D, 0.0D, 0.0D);
/* 117 */     for (int i = 0; i < this.nearbyWoims.numObjs; i++)
/*     */     {
/* 119 */       if ((this.nearbyWoims.objs[i] != this) && (this.distSqrTo[i] <= 256.0D))
/*     */       {
/* 122 */         Woim3D p = (Woim3D)this.nearbyWoims.objs[i];
/* 123 */         Vector3D temp = this.woimPosition.subtract(new Double3D(p.x, p.y, p.z));
/* 124 */         temp = temp.normalize();
/* 125 */         away = away.add(temp);
/*     */       }
/*     */     }
/* 128 */     return away.normalize();
/*     */   }
/*     */ 
/*     */   public Vector3D matchFlockSpeed(SimState state)
/*     */   {
/* 133 */     if (this.nearbyWoims == null)
/* 134 */       return new Vector3D(0.0D, 0.0D, 0.0D);
/* 135 */     Vector3D mean = new Vector3D(0.0D, 0.0D, 0.0D);
/* 136 */     int n = 0;
/* 137 */     for (int i = 0; i < this.nearbyWoims.numObjs; i++)
/*     */     {
/* 139 */       if ((this.nearbyWoims.objs[i] != this) && (this.distSqrTo[i] <= 1600.0D) && (this.distSqrTo[i] > 256.0D))
/*     */       {
/* 143 */         mean = mean.add(((Woim3D)this.nearbyWoims.objs[i]).velocity);
/* 144 */         n++;
/*     */       }
/*     */     }
/* 147 */     if (n == 0) {
/* 148 */       return new Vector3D(0.0D, 0.0D, 0.0D);
/*     */     }
/*     */ 
/* 151 */     mean = mean.amplify(1.0D / n);
/* 152 */     return mean.normalize();
/*     */   }
/*     */ 
/*     */   public Vector3D randomDirection(SimState state)
/*     */   {
/* 158 */     Vector3D temp = new Vector3D(1.0D - 2.0D * state.random.nextDouble(), 1.0D - 2.0D * state.random.nextDouble(), 1.0D - 2.0D * state.random.nextDouble());
/*     */ 
/* 161 */     return temp.setLength(0.25D + state.random.nextDouble() * 0.5D);
/*     */   }
/*     */ 
/*     */   public Vector3D niceUndulation(SimState state)
/*     */   {
/* 168 */     this.ond += this.ondSpeed;
/* 169 */     if (this.ond > 7.0D)
/* 170 */       this.ond -= 6.283185307179586D;
/* 171 */     double angle = Math.cos(this.ond);
/* 172 */     Vector3D temp = this.velocity;
/* 173 */     double velA = Math.atan2(temp.y, temp.x);
/* 174 */     velA += 1.570796326794897D * angle;
/* 175 */     return new Vector3D(Math.cos(velA), Math.sin(velA), 0.0D);
/*     */   }
/*     */ 
/*     */   public Vector3D avoidObstacles(SimState state)
/*     */   {
/* 180 */     double[][] info = WoimsDemo3D.obstInfo;
/* 181 */     if ((info == null) || (info.length == 0)) {
/* 182 */       return new Vector3D(0.0D, 0.0D, 0.0D);
/*     */     }
/* 184 */     Vector3D away = new Vector3D(0.0D, 0.0D, 0.0D);
/* 185 */     for (int i = 0; i < info.length; i++)
/*     */     {
/* 187 */       double dist = Math.sqrt((this.woimPosition.x - info[i][1]) * (this.woimPosition.x - info[i][1]) + (this.woimPosition.y - info[i][2]) * (this.woimPosition.y - info[i][2]) + (this.woimPosition.z - info[i][3]) * (this.woimPosition.z - info[i][3]));
/*     */ 
/* 190 */       if (dist <= info[i][0] + 16.0D)
/*     */       {
/* 192 */         Vector3D temp = this.woimPosition.subtract(new Vector3D(info[i][1], info[i][2], info[i][3]));
/* 193 */         temp = temp.normalize();
/* 194 */         away = away.add(temp);
/*     */       }
/*     */     }
/* 197 */     return away.normalize();
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/* 204 */     WoimsDemo3D bd = (WoimsDemo3D)state;
/*     */ 
/* 206 */     Double3D temp = new Double3D(this.x, this.y, this.z);
/* 207 */     this.woimPosition.x = this.x;
/* 208 */     this.woimPosition.y = this.y;
/* 209 */     this.woimPosition.z = this.z;
/* 210 */     preprocessWoims(bd, temp, MAX_DISTANCE);
/*     */ 
/* 212 */     Vector3D vel = new Vector3D(0.0D, 0.0D, 0.0D);
/* 213 */     vel = vel.add(avoidObstacles(bd).amplify(1.5D));
/* 214 */     vel = vel.add(towardsFlockCenterOfMass(bd).amplify(0.5D));
/* 215 */     vel = vel.add(matchFlockSpeed(bd).amplify(0.5D));
/* 216 */     vel = vel.add(awayFromCloseBys(bd).amplify(1.5D));
/* 217 */     if (vel.length() <= 1.0D)
/*     */     {
/* 219 */       vel = vel.add(niceUndulation(bd).amplify(0.5D));
/* 220 */       vel = vel.add(randomDirection(bd).amplify(0.25D));
/*     */     }
/*     */ 
/* 223 */     double vl = vel.length();
/* 224 */     if (vl < 0.25D)
/* 225 */       vel = vel.setLength(0.25D);
/* 226 */     else if (vl > 0.75D)
/* 227 */       vel = vel.setLength(0.75D);
/* 228 */     vel = new Vector3D(0.975D * this.velocity.x + 0.025D * vel.x, 0.975D * this.velocity.y + 0.025D * vel.y, 0.975D * this.velocity.z + 0.025D * vel.z);
/*     */ 
/* 231 */     this.velocity = vel;
/* 232 */     Double3D desiredPosition = new Double3D(this.woimPosition.x + vel.x * 30.0D, this.woimPosition.y + vel.y * 30.0D, this.woimPosition.z + vel.z * 30.0D);
/*     */ 
/* 235 */     bd.setObjectLocation(this, desiredPosition);
/*     */   }
/*     */ 
/*     */   public void computePositions()
/*     */   {
/* 259 */     double centerx = this.x + 0.5D;
/* 260 */     double centery = this.y + 0.5D;
/* 261 */     double centerz = this.z + 0.5D;
/* 262 */     this.lastPos[0] = new Vector3d(centerx, centery, centerz);
/* 263 */     Vector3d temp = new Vector3d();
/* 264 */     Vector3d velocity3d = new Vector3d(this.velocity.x, this.velocity.y, this.velocity.z);
/* 265 */     for (int i = 1; i < 12; i++)
/*     */     {
/* 267 */       if (this.lastPos[i] == null)
/*     */       {
/* 269 */         temp.scale(-1.0D, velocity3d);
/* 270 */         temp.normalize();
/* 271 */         centerx = this.lastPos[(i - 1)].x + temp.x;
/* 272 */         centery = this.lastPos[(i - 1)].y + temp.y;
/* 273 */         centerz = this.lastPos[(i - 1)].z + temp.z;
/* 274 */         this.lastPos[i] = new Vector3d(centerx, centery, centerz);
/*     */       }
/*     */       else
/*     */       {
/* 278 */         temp.sub(this.lastPos[(i - 1)], this.lastPos[i]);
/* 279 */         temp.scale(4.0D / temp.length());
/* 280 */         temp.sub(this.lastPos[(i - 1)], temp);
/* 281 */         this.lastPos[i] = new Vector3d(temp.x, temp.y, temp.z);
/*     */       }
/*     */     }
/* 284 */     for (int i = 0; i < this.lastPosRel.length; i++)
/*     */     {
/* 286 */       this.lastPosRel[i] = new Vector3d(this.lastPos[i].x - this.lastPos[0].x, this.lastPos[i].y - this.lastPos[0].y, this.lastPos[i].z - this.lastPos[0].z);
/*     */     }
/*     */   }
/*     */ 
/*     */   public TransformGroup createModel(Object obj)
/*     */   {
/* 294 */     TransformGroup globalTG = new TransformGroup();
/* 295 */     for (int i = 0; i < 12; i++)
/*     */     {
/* 300 */       SpherePortrayal3D s = new SpherePortrayal3D(this.colors[i], 4.0D, 6);
/* 301 */       s.setCurrentFieldPortrayal(getCurrentFieldPortrayal());
/* 302 */       TransformGroup localTG = s.getModel(obj, null);
/*     */ 
/* 304 */       localTG.setCapability(18);
/* 305 */       globalTG.addChild(localTG);
/*     */     }
/* 307 */     globalTG.setCapability(12);
/* 308 */     return globalTG;
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object obj, TransformGroup transf)
/*     */   {
/* 313 */     computePositions();
/* 314 */     if (transf == null) return createModel(obj);
/* 315 */     for (int i = 0; i < transf.numChildren(); i++)
/*     */     {
/* 317 */       Transform3D tmpT3d = new Transform3D();
/* 318 */       tmpT3d.setTranslation(this.lastPosRel[i]);
/* 319 */       ((TransformGroup)transf.getChild(i)).setTransform(tmpT3d);
/*     */     }
/* 321 */     return transf;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.woims3d.Woim3D
 * JD-Core Version:    0.6.2
 */