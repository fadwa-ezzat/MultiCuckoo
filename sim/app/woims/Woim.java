/*     */ package sim.app.woims;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Ellipse2D.Double;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class Woim extends SimplePortrayal2D
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final double CENTROID_DISTANCE = 20.0D;
/*     */   public static final double AVOID_DISTANCE = 16.0D;
/*     */   public static final double COPY_SPEED_DISTANCE = 40.0D;
/*     */   public static final double OBSTACLE_AVOID_COEF = 1.05D;
/*     */   public static final double OBSTACLE_FAST_AVOID_COEF = 1.5D;
/*  26 */   public static final double MAX_DISTANCE = Math.max(20.0D, Math.max(16.0D, 40.0D));
/*     */   public static final double ADJUSTMENT_RATE = 0.025D;
/*     */   public static final double MIN_VELOCITY = 0.25D;
/*     */   public static final double MAX_VELOCITY = 0.75D;
/*     */   Bag nearbyWoims;
/*     */   double[] distSqrTo;
/*     */   double ond;
/*     */   double ondSpeed;
/* 206 */   protected Vector2D woimPosition = new Vector2D(0.0D, 0.0D);
/*     */   public double x;
/*     */   public double y;
/*     */   Vector2D[] lastPos;
/*     */   Color[] colors;
/* 248 */   int numLinks = 7;
/*     */   protected double orientation;
/* 267 */   protected Vector2D velocity = new Vector2D(0.0D, 0.0D);
/* 268 */   protected Vector2D acceleration = new Vector2D(0.0D, 0.0D);
/*     */ 
/*     */   public Woim()
/*     */   {
/*  37 */     this.ond = (Math.random() * 2.0D * 3.141592653589793D);
/*  38 */     this.ondSpeed = (0.05D + Math.random() * 0.15D);
/*  39 */     setNumberOfLinks(this.numLinks);
/*     */   }
/*     */ 
/*     */   public final double distanceSquared(Vector2D loc1, Vector2D loc2)
/*     */   {
/*  45 */     return (loc1.x - loc2.x) * (loc1.x - loc2.x) + (loc1.y - loc2.y) * (loc1.y - loc2.y);
/*     */   }
/*     */ 
/*     */   public final double distanceSquared(Vector2D loc1, Double2D loc2)
/*     */   {
/*  51 */     return (loc1.x - loc2.x) * (loc1.x - loc2.x) + (loc1.y - loc2.y) * (loc1.y - loc2.y);
/*     */   }
/*     */ 
/*     */   public final double distanceSquared(double x1, double y1, double x2, double y2)
/*     */   {
/*  57 */     return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
/*     */   }
/*     */ 
/*     */   public final double dotproduct(Vector2D loc1, Vector2D loc2)
/*     */   {
/*  63 */     return loc1.x * loc2.x + loc1.y * loc2.y;
/*     */   }
/*     */ 
/*     */   void preprocessWoims(WoimsDemo state, Double2D pos, double distance)
/*     */   {
/*  71 */     this.nearbyWoims = state.woimsEnvironment.getNeighborsWithinDistance(pos, distance);
/*     */ 
/*  78 */     this.distSqrTo = new double[this.nearbyWoims.numObjs];
/*  79 */     for (int i = 0; i < this.nearbyWoims.numObjs; i++)
/*     */     {
/*  81 */       Woim p = (Woim)this.nearbyWoims.objs[i];
/*  82 */       this.distSqrTo[i] = distanceSquared(pos.x, pos.y, p.x, p.y);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector2D towardsFlockCenterOfMass(WoimsDemo state)
/*     */   {
/*  90 */     if (this.nearbyWoims == null)
/*  91 */       return new Vector2D(0.0D, 0.0D);
/*  92 */     Vector2D mean = new Vector2D(0.0D, 0.0D);
/*  93 */     int n = 0;
/*  94 */     for (int i = 0; i < this.nearbyWoims.numObjs; i++)
/*     */     {
/*  96 */       if ((this.nearbyWoims.objs[i] != this) && (this.distSqrTo[i] <= 400.0D) && (this.distSqrTo[i] > 256.0D))
/*     */       {
/* 100 */         Woim p = (Woim)this.nearbyWoims.objs[i];
/* 101 */         mean = mean.add(new Double2D(p.x, p.y));
/* 102 */         n++;
/*     */       }
/*     */     }
/* 105 */     if (n == 0) {
/* 106 */       return new Vector2D(0.0D, 0.0D);
/*     */     }
/*     */ 
/* 109 */     mean = mean.amplify(1.0D / n);
/* 110 */     mean = mean.subtract(this.woimPosition);
/* 111 */     return mean.normalize();
/*     */   }
/*     */ 
/*     */   public Vector2D awayFromCloseBys(WoimsDemo state)
/*     */   {
/* 118 */     if (this.nearbyWoims == null)
/* 119 */       return new Vector2D(0.0D, 0.0D);
/* 120 */     Vector2D away = new Vector2D(0.0D, 0.0D);
/* 121 */     for (int i = 0; i < this.nearbyWoims.numObjs; i++)
/*     */     {
/* 123 */       if ((this.nearbyWoims.objs[i] != this) && (this.distSqrTo[i] <= 256.0D))
/*     */       {
/* 126 */         Woim p = (Woim)this.nearbyWoims.objs[i];
/* 127 */         Vector2D temp = this.woimPosition.subtract(new Double2D(p.x, p.y));
/* 128 */         temp = temp.normalize();
/* 129 */         away = away.add(temp);
/*     */       }
/*     */     }
/* 132 */     return away.normalize();
/*     */   }
/*     */ 
/*     */   public Vector2D matchFlockSpeed(SimState state)
/*     */   {
/* 138 */     if (this.nearbyWoims == null)
/* 139 */       return new Vector2D(0.0D, 0.0D);
/* 140 */     Vector2D mean = new Vector2D(0.0D, 0.0D);
/* 141 */     int n = 0;
/* 142 */     for (int i = 0; i < this.nearbyWoims.numObjs; i++)
/*     */     {
/* 144 */       if ((this.nearbyWoims.objs[i] != this) && (this.distSqrTo[i] <= 1600.0D) && (this.distSqrTo[i] > 256.0D))
/*     */       {
/* 148 */         mean = mean.add(((Woim)this.nearbyWoims.objs[i]).velocity);
/* 149 */         n++;
/*     */       }
/*     */     }
/* 152 */     if (n == 0) {
/* 153 */       return new Vector2D(0.0D, 0.0D);
/*     */     }
/*     */ 
/* 156 */     mean = mean.amplify(1.0D / n);
/* 157 */     return mean.normalize();
/*     */   }
/*     */ 
/*     */   public Vector2D randomDirection(SimState state)
/*     */   {
/* 164 */     Vector2D temp = new Vector2D(1.0D - 2.0D * state.random.nextDouble(), 1.0D - 2.0D * state.random.nextDouble());
/*     */ 
/* 166 */     return temp.setLength(0.25D + state.random.nextDouble() * 0.5D);
/*     */   }
/*     */ 
/*     */   public Vector2D niceUndulation(SimState state)
/*     */   {
/* 174 */     this.ond += this.ondSpeed;
/* 175 */     if (this.ond > 7.0D)
/* 176 */       this.ond -= 6.283185307179586D;
/* 177 */     double angle = Math.cos(this.ond);
/* 178 */     Vector2D temp = this.velocity;
/* 179 */     double velA = Math.atan2(temp.y, temp.x);
/* 180 */     velA += 1.570796326794897D * angle;
/* 181 */     return new Vector2D(Math.cos(velA), Math.sin(velA));
/*     */   }
/*     */ 
/*     */   public Vector2D avoidObstacles(SimState state)
/*     */   {
/* 187 */     double[][] info = WoimsDemo.obstInfo;
/* 188 */     if ((info == null) || (info.length == 0)) {
/* 189 */       return new Vector2D(0.0D, 0.0D);
/*     */     }
/* 191 */     Vector2D away = new Vector2D(0.0D, 0.0D);
/* 192 */     for (int i = 0; i < info.length; i++)
/*     */     {
/* 194 */       double dist = Math.sqrt((this.woimPosition.x - info[i][1]) * (this.woimPosition.x - info[i][1]) + (this.woimPosition.y - info[i][2]) * (this.woimPosition.y - info[i][2]));
/*     */ 
/* 196 */       if (dist <= info[i][0] + 16.0D)
/*     */       {
/* 198 */         Vector2D temp = this.woimPosition.subtract(new Vector2D(info[i][1], info[i][2]));
/* 199 */         temp = temp.normalize();
/* 200 */         away = away.add(temp);
/*     */       }
/*     */     }
/* 203 */     return away.normalize();
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/* 210 */     WoimsDemo bd = (WoimsDemo)state;
/*     */ 
/* 212 */     Double2D temp = new Double2D(this.x, this.y);
/* 213 */     this.woimPosition.x = this.x;
/* 214 */     this.woimPosition.y = this.y;
/* 215 */     preprocessWoims(bd, temp, MAX_DISTANCE);
/*     */ 
/* 218 */     Vector2D vel = new Vector2D(0.0D, 0.0D);
/* 219 */     vel = vel.add(avoidObstacles(bd).amplify(1.5D));
/* 220 */     vel = vel.add(towardsFlockCenterOfMass(bd).amplify(0.5D));
/* 221 */     vel = vel.add(matchFlockSpeed(bd).amplify(0.5D));
/* 222 */     vel = vel.add(awayFromCloseBys(bd).amplify(1.5D));
/* 223 */     if (vel.length() <= 1.0D)
/*     */     {
/* 225 */       vel = vel.add(niceUndulation(bd).amplify(0.5D));
/* 226 */       vel = vel.add(randomDirection(bd).amplify(0.25D));
/*     */     }
/*     */ 
/* 229 */     double vl = vel.length();
/* 230 */     if (vl < 0.25D)
/* 231 */       vel = vel.setLength(0.25D);
/* 232 */     else if (vl > 0.75D)
/* 233 */       vel = vel.setLength(0.75D);
/* 234 */     vel = new Vector2D(0.975D * this.velocity.x + 0.025D * vel.x, 0.975D * this.velocity.y + 0.025D * vel.y);
/*     */ 
/* 236 */     this.velocity = vel;
/* 237 */     Double2D desiredPosition = new Double2D(this.woimPosition.x + vel.x * 2.0D, this.woimPosition.y + vel.y * 2.0D);
/*     */ 
/* 239 */     bd.setObjectLocation(this, desiredPosition);
/* 240 */     updateLinkPosition();
/*     */   }
/*     */ 
/*     */   public int getNumberOfLinks()
/*     */   {
/* 249 */     return this.numLinks;
/*     */   }
/*     */   public void setNumberOfLinks(int n) {
/* 252 */     if ((this.numLinks == n) && (this.colors != null))
/* 253 */       return;
/* 254 */     if (n <= 0)
/* 255 */       return;
/* 256 */     if (n > 1000)
/* 257 */       n = 1000;
/* 258 */     this.numLinks = n;
/* 259 */     this.lastPos = new Vector2D[this.numLinks];
/* 260 */     this.colors = new Color[this.numLinks];
/* 261 */     for (int i = 0; i < this.colors.length; i++)
/* 262 */       this.colors[i] = new Color((int)(63.0D + 192.0D * (this.colors.length - i) / this.colors.length), 0, 0);
/* 263 */     updateLinkPosition();
/*     */   }
/*     */ 
/*     */   void drawLink(Graphics2D graphics, double x, double y, double rx, double ry, Color color)
/*     */   {
/* 273 */     graphics.setColor(color);
/* 274 */     graphics.fillOval((int)(x - rx / 2.0D), (int)(y - ry / 2.0D), (int)rx, (int)ry);
/*     */   }
/*     */ 
/*     */   public void updateLinkPosition()
/*     */   {
/* 283 */     double centerx = this.x;
/* 284 */     double centery = this.y;
/* 285 */     this.lastPos[0] = new Vector2D(centerx, centery);
/* 286 */     for (int i = 1; i < this.numLinks; i++)
/*     */     {
/* 288 */       if (this.lastPos[i] == null)
/*     */       {
/* 290 */         Vector2D temp = this.velocity.normalize().amplify(-1.0D);
/* 291 */         centerx = this.lastPos[(i - 1)].x + 1.0D * temp.x;
/* 292 */         centery = this.lastPos[(i - 1)].y + 1.0D * temp.y;
/* 293 */         this.lastPos[i] = new Vector2D(centerx, centery);
/*     */       }
/*     */       else
/*     */       {
/* 297 */         Vector2D temp = this.lastPos[(i - 1)].subtract(this.lastPos[i]);
/* 298 */         temp = temp.setLength(1.0D);
/* 299 */         temp = this.lastPos[(i - 1)].subtract(temp);
/* 300 */         this.lastPos[i] = temp;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/* 307 */     if (this.lastPos == null)
/* 308 */       return;
/* 309 */     for (int i = 0; i < this.numLinks; i++)
/* 310 */       if (this.lastPos[i] != null)
/* 311 */         drawLink(graphics, info.draw.x + info.draw.width * (this.lastPos[i].x - this.lastPos[0].x), info.draw.y + info.draw.height * (this.lastPos[i].y - this.lastPos[0].y), info.draw.width, info.draw.height, this.colors[i]);
/*     */   }
/*     */ 
/*     */   public boolean hitObject(Object object, DrawInfo2D info)
/*     */   {
/* 322 */     if (this.lastPos == null)
/* 323 */       return false;
/* 324 */     for (int i = 0; i < this.numLinks; i++)
/* 325 */       if (this.lastPos[i] != null)
/*     */       {
/* 327 */         Ellipse2D.Double ellipse = new Ellipse2D.Double(info.draw.x + info.draw.width * (this.lastPos[i].x - this.lastPos[0].x), info.draw.y + info.draw.height * (this.lastPos[i].y - this.lastPos[0].y), info.draw.width, info.draw.height);
/*     */ 
/* 332 */         if (ellipse.intersects(info.clip.x, info.clip.y, info.clip.width, info.clip.height))
/*     */         {
/* 334 */           return true;
/*     */         }
/*     */       }
/* 337 */     return false;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.woims.Woim
 * JD-Core Version:    0.6.2
 */