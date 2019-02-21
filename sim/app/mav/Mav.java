/*     */ package sim.app.mav;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.awt.geom.Area;
/*     */ import java.io.PrintStream;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.portrayal.Oriented2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class Mav
/*     */   implements Steppable, Oriented2D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  20 */   static final double[] theta = { 0.0D, 0.7853981633974483D, 1.570796326794897D, 2.356194490192345D, 3.141592653589793D, 3.926990816987241D, 4.71238898038469D, 5.497787143782138D };
/*     */ 
/*  32 */   static final double[] xd = { Math.cos(theta[0]), Math.cos(theta[1]), Math.cos(theta[2]), Math.cos(theta[3]), Math.cos(theta[4]), Math.cos(theta[5]), Math.cos(theta[6]), Math.cos(theta[7]) };
/*     */ 
/*  44 */   static final double[] yd = { Math.sin(theta[0]), Math.sin(theta[1]), Math.sin(theta[2]), Math.sin(theta[3]), Math.sin(theta[4]), Math.sin(theta[5]), Math.sin(theta[6]), Math.sin(theta[7]) };
/*     */ 
/*  56 */   public int orientation = 0;
/*     */   public double x;
/*     */   public double y;
/*  88 */   double[] proximitySensors = new double[8];
/*     */ 
/* 123 */   final double sinTheta = Math.sin(0.3926990816987241D);
/* 124 */   final double cosTheta = Math.cos(0.3926990816987241D);
/*     */ 
/*     */   public double orientation2D()
/*     */   {
/*  60 */     return theta[this.orientation];
/*     */   }
/*     */ 
/*     */   public Mav(int orientation, double x, double y) {
/*  64 */     this.orientation = orientation; this.x = x; this.y = y;
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/*  69 */     MavDemo mavdemo = (MavDemo)state;
/*  70 */     this.orientation += mavdemo.random.nextInt(3) - 1;
/*  71 */     if (this.orientation > 7) this.orientation = 0;
/*  72 */     if (this.orientation < 0) this.orientation = 7;
/*  73 */     this.x += xd[this.orientation];
/*  74 */     this.y += yd[this.orientation];
/*  75 */     if (this.x >= mavdemo.width) this.x = (mavdemo.width - 1.0D);
/*  76 */     else if (this.x < 0.0D) this.x = 0.0D;
/*  77 */     if (this.y >= mavdemo.height) this.y = (mavdemo.height - 1.0D);
/*  78 */     else if (this.y < 0.0D) this.y = 0.0D;
/*  79 */     mavdemo.mavs.setObjectLocation(this, new Double2D(this.x, this.y));
/*  80 */     act(nearbyMAVs(mavdemo), currentSurface(mavdemo));
/*     */   }
/*     */ 
/*     */   public void act(double[] sensorReading, int currentSurface)
/*     */   {
/*  85 */     if (currentSurface == 100) System.out.println("Acting");
/*     */   }
/*     */ 
/*     */   public double[] nearbyMAVs(MavDemo mavdemo)
/*     */   {
/*  93 */     for (int i = 0; i < 8; i++) this.proximitySensors[i] = 1.7976931348623157E+308D;
/*     */ 
/*  95 */     double d = mavdemo.sensorRangeDistance * mavdemo.sensorRangeDistance;
/*     */ 
/*  97 */     Bag nearbyMavs = mavdemo.mavs.getNeighborsWithinDistance(new Double2D(this.x, this.y), 16.0D, false, false);
/*  98 */     for (int i = 0; i < nearbyMavs.numObjs; i++)
/*     */     {
/* 100 */       Mav mav = (Mav)nearbyMavs.objs[i];
/* 101 */       double mavDistance = (mav.x - this.x) * (mav.x - this.x) + (mav.y - this.y) * (mav.y - this.y);
/* 102 */       if (mavDistance < d)
/*     */       {
/* 104 */         int octant = sensorForPoint(mav.x, mav.y);
/* 105 */         this.proximitySensors[octant] = Math.min(this.proximitySensors[octant], mavDistance);
/*     */       }
/*     */     }
/* 108 */     return this.proximitySensors;
/*     */   }
/*     */ 
/*     */   public int currentSurface(MavDemo mavdemo)
/*     */   {
/* 114 */     for (int i = 0; i < mavdemo.region.length; i++)
/* 115 */       if (mavdemo.region[i].area.contains(this.x - mavdemo.region[i].originx, this.y - mavdemo.region[i].originy))
/*     */       {
/* 117 */         return mavdemo.region[i].surface;
/*     */       }
/* 118 */     return 0;
/*     */   }
/*     */ 
/*     */   public int sensorForPoint(double px, double py)
/*     */   {
/* 128 */     int o = 0;
/*     */ 
/* 130 */     px -= this.x; py -= this.y;
/*     */ 
/* 133 */     double xx = px * this.cosTheta + py * -this.sinTheta;
/* 134 */     double yy = px * this.sinTheta + py * this.cosTheta;
/*     */ 
/* 141 */     if ((xx != 0.0D) || (yy != 0.0D))
/*     */     {
/* 143 */       if (xx > 0.0D)
/*     */       {
/* 145 */         if (yy > 0.0D)
/*     */         {
/* 147 */           if (xx > yy) o = 0; else {
/* 148 */             o = 1;
/*     */           }
/*     */ 
/*     */         }
/* 152 */         else if (xx > -yy) o = 7; else {
/* 153 */           o = 6;
/*     */         }
/*     */ 
/*     */       }
/* 158 */       else if (yy > 0.0D)
/*     */       {
/* 160 */         if (-xx > yy) o = 3; else {
/* 161 */           o = 2;
/*     */         }
/*     */ 
/*     */       }
/* 165 */       else if (-xx > -yy) o = 4; else {
/* 166 */         o = 5;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 172 */     o += this.orientation;
/* 173 */     if (o >= 8) o %= 8;
/* 174 */     return o;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.mav.Mav
 * JD-Core Version:    0.6.2
 */