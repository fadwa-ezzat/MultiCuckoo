/*     */ package sim.app.keepaway;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.MutableDouble2D;
/*     */ 
/*     */ public class Bot extends Entity
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  42 */   public MutableDouble2D tempVector = new MutableDouble2D();
/*     */ 
/*     */   public Bot(double x, double y, Color c)
/*     */   {
/*  20 */     super(x, y, 2.0D, c);
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D g, DrawInfo2D info)
/*     */   {
/*  26 */     super.draw(object, g, info);
/*     */ 
/*  30 */     double width = info.draw.width * this.radius * 2.0D;
/*  31 */     double height = info.draw.height * this.radius * 2.0D;
/*     */ 
/*  33 */     g.setColor(Color.white);
/*  34 */     double d = this.velocity.angle();
/*  35 */     g.drawLine((int)info.draw.x, (int)info.draw.y, (int)info.draw.x + (int)(width / 2.0D * Math.cos(d)), (int)info.draw.y + (int)(height / 2.0D * Math.sin(d)));
/*     */   }
/*     */ 
/*     */   public MutableDouble2D getForces(Keepaway keepaway)
/*     */   {
/*  46 */     this.sumVector.setTo(0.0D, 0.0D);
/*  47 */     Bag objs = keepaway.fieldEnvironment.getNeighborsWithinDistance(new Double2D(this.loc.x, this.loc.y), 100.0D);
/*     */ 
/*  49 */     double dist = 0.0D;
/*     */ 
/*  55 */     for (int x = 0; x < objs.numObjs; x++)
/*     */     {
/*  57 */       if (objs.objs[x] != this)
/*     */       {
/*  59 */         dist = ((Entity)objs.objs[x]).loc.distance(this.loc);
/*     */ 
/*  61 */         if ((((Entity)objs.objs[x]).radius + this.radius) * 1.25D > dist)
/*     */         {
/*  65 */           if (((objs.objs[x] instanceof Ball)) && (keepaway.random.nextDouble() < 0.1D))
/*     */           {
/*  67 */             this.tempVector.subtract(((Entity)objs.objs[x]).loc, this.loc);
/*  68 */             this.tempVector.normalize().multiplyIn(2.0D);
/*  69 */             ((Entity)objs.objs[x]).velocity.addIn(this.tempVector);
/*     */           }
/*     */           else
/*     */           {
/*  73 */             this.tempVector.x = 0.0D;
/*  74 */             this.tempVector.y = 0.0D;
/*     */ 
/*  76 */             double mass1 = this.mass - ((Entity)objs.objs[x]).mass;
/*  77 */             mass1 /= (this.mass + ((Entity)objs.objs[x]).mass);
/*     */ 
/*  79 */             double mass2 = 2.0D * ((Entity)objs.objs[x]).mass;
/*  80 */             mass2 /= (this.mass + ((Entity)objs.objs[x]).mass);
/*     */ 
/*  83 */             this.tempVector.x = (this.velocity.x * mass1 + ((Entity)objs.objs[x]).velocity.x * mass2);
/*  84 */             this.tempVector.y = (this.velocity.y * mass1 + ((Entity)objs.objs[x]).velocity.y * mass2);
/*     */ 
/*  87 */             ((Entity)objs.objs[x]).bump.x = (this.velocity.x * mass2 - ((Entity)objs.objs[x]).velocity.x * mass1);
/*  88 */             ((Entity)objs.objs[x]).bump.y = (this.velocity.y * mass2 - ((Entity)objs.objs[x]).velocity.y * mass1);
/*     */ 
/*  90 */             this.velocity.x = this.tempVector.x;
/*  91 */             this.velocity.y = this.tempVector.y;
/*     */           }
/*     */         }
/*  94 */         else if ((objs.objs[x] instanceof Ball))
/*     */         {
/*  97 */           this.tempVector.subtract(((Entity)objs.objs[x]).loc, this.loc);
/*  98 */           this.tempVector.resize(0.5D);
/*  99 */           this.sumVector.addIn(this.tempVector);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 104 */     this.sumVector.addIn(this.bump);
/* 105 */     this.bump.x = 0.0D;
/* 106 */     this.bump.y = 0.0D;
/* 107 */     return this.sumVector;
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/* 113 */     Keepaway keepaway = (Keepaway)state;
/*     */ 
/* 116 */     MutableDouble2D force = getForces(keepaway);
/*     */ 
/* 119 */     this.accel.multiply(force, 1.0D / this.mass);
/*     */ 
/* 122 */     this.velocity.addIn(this.accel);
/* 123 */     capVelocity();
/*     */ 
/* 126 */     this.newLoc.add(this.loc, this.velocity);
/*     */ 
/* 129 */     if (isValidMove(keepaway, this.newLoc)) {
/* 130 */       this.loc = this.newLoc;
/*     */     }
/* 132 */     keepaway.fieldEnvironment.setObjectLocation(this, new Double2D(this.loc));
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.keepaway.Bot
 * JD-Core Version:    0.6.2
 */