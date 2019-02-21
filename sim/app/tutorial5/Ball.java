/*     */ package sim.app.tutorial5;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Ellipse2D.Double;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.field.network.Edge;
/*     */ import sim.field.network.Network;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ 
/*     */ public class Ball extends SimplePortrayal2D
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public double forcex;
/*     */   public double forcey;
/*     */   public double mass;
/*     */   public double velocityx;
/*     */   public double velocityy;
/*     */   public boolean collision;
/*     */   public double diameter;
/*     */ 
/*     */   public double getVelocityX()
/*     */   {
/*  38 */     return this.velocityx; } 
/*  39 */   public void setVelocityX(double val) { this.velocityx = val; } 
/*  40 */   public double getVelocityY() { return this.velocityy; } 
/*  41 */   public void setVelocityY(double val) { this.velocityy = val; } 
/*  42 */   public double getMass() { return this.mass; } 
/*  43 */   public void setMass(double val) { if (val > 0.0D) { this.mass = val; this.diameter = Math.sqrt(val); } }
/*     */ 
/*     */   public Ball(double vx, double vy, double m)
/*     */   {
/*  47 */     this.velocityx = vx;
/*  48 */     this.velocityy = vy;
/*  49 */     this.mass = m;
/*  50 */     this.diameter = Math.sqrt(m);
/*     */   }
/*     */ 
/*     */   public void computeCollision(Tutorial5 tut)
/*     */   {
/*  74 */     Double2D me = tut.balls.getObjectLocation(this);
/*  75 */     Bag b = tut.balls.getNeighborsExactlyWithinDistance(me, 5.0D);
/*  76 */     this.collision = (b.numObjs > 1);
/*     */   }
/*     */ 
/*     */   public void addForce(Double2D otherBallLoc, Double2D myLoc, Band band)
/*     */   {
/*  82 */     double dx = otherBallLoc.x - myLoc.x;
/*  83 */     double dy = otherBallLoc.y - myLoc.y;
/*  84 */     double len = Math.sqrt(dx * dx + dy * dy);
/*  85 */     double l = band.laxDistance;
/*     */ 
/*  87 */     double k = band.strength / 512.0D;
/*  88 */     double forcemagnitude = (len - l) * k;
/*     */ 
/*  91 */     if (len - l > 0.0D)
/*     */     {
/*  93 */       this.forcex += dx * forcemagnitude / len;
/*  94 */       this.forcey += dy * forcemagnitude / len;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void computeForce(SimState state)
/*     */   {
/* 100 */     Tutorial5 tut = (Tutorial5)state;
/* 101 */     Network bands = tut.bands;
/* 102 */     Continuous2D balls = tut.balls;
/*     */ 
/* 104 */     Double2D me = balls.getObjectLocation(this);
/*     */ 
/* 106 */     this.forcex = 0.0D; this.forcey = 0.0D;
/*     */ 
/* 110 */     Bag in = bands.getEdgesIn(this);
/* 111 */     Bag out = bands.getEdgesOut(this);
/* 112 */     if (in != null)
/* 113 */       for (int x = 0; x < in.numObjs; x++)
/*     */       {
/* 115 */         Edge e = (Edge)in.objs[x];
/* 116 */         Band b = (Band)e.info;
/* 117 */         Ball other = (Ball)e.from();
/* 118 */         Double2D him = balls.getObjectLocation(other);
/* 119 */         addForce(him, me, b);
/*     */       }
/* 121 */     if (out != null)
/* 122 */       for (int x = 0; x < out.numObjs; x++)
/*     */       {
/* 124 */         Edge e = (Edge)out.objs[x];
/* 125 */         Band b = (Band)e.info;
/* 126 */         Ball other = (Ball)e.to();
/* 127 */         Double2D him = balls.getObjectLocation(other);
/* 128 */         addForce(him, me, b);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/* 134 */     Tutorial5 tut = (Tutorial5)state;
/*     */ 
/* 137 */     double ax = this.forcex / this.mass;
/* 138 */     double ay = this.forcey / this.mass;
/*     */ 
/* 141 */     this.velocityx += ax;
/* 142 */     this.velocityy += ay;
/*     */ 
/* 145 */     Double2D pos = tut.balls.getObjectLocation(this);
/* 146 */     Double2D newpos = new Double2D(pos.x + this.velocityx, pos.y + this.velocityy);
/* 147 */     tut.balls.setObjectLocation(this, newpos);
/*     */ 
/* 150 */     computeCollision(tut);
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/* 156 */     double width = info.draw.width * this.diameter;
/* 157 */     double height = info.draw.height * this.diameter;
/*     */ 
/* 159 */     if (this.collision) graphics.setColor(Color.red); else {
/* 160 */       graphics.setColor(Color.blue);
/*     */     }
/* 162 */     int x = (int)(info.draw.x - width / 2.0D);
/* 163 */     int y = (int)(info.draw.y - height / 2.0D);
/* 164 */     int w = (int)width;
/* 165 */     int h = (int)height;
/*     */ 
/* 168 */     graphics.fillOval(x, y, w, h);
/*     */   }
/*     */ 
/*     */   public boolean hitObject(Object object, DrawInfo2D range)
/*     */   {
/* 173 */     double SLOP = 1.0D;
/* 174 */     double width = range.draw.width * this.diameter;
/* 175 */     double height = range.draw.height * this.diameter;
/*     */ 
/* 177 */     Ellipse2D.Double ellipse = new Ellipse2D.Double(range.draw.x - width / 2.0D - 1.0D, range.draw.y - height / 2.0D - 1.0D, width + 2.0D, height + 2.0D);
/*     */ 
/* 182 */     return ellipse.intersects(range.clip.x, range.clip.y, range.clip.width, range.clip.height);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial5.Ball
 * JD-Core Version:    0.6.2
 */