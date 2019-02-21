/*     */ package sim.app.asteroids;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Area;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.MutableDouble2D;
/*     */ 
/*     */ public abstract class Element extends SimplePortrayal2D
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public Shape shape;
/*     */   public double orientation;
/*     */   public MutableDouble2D velocity;
/*     */   public double rotationalVelocity;
/*     */   public Stoppable stopper;
/*     */ 
/*     */   public Area getLocatedArea(double translateX, double translateY, double scaleX, double scaleY)
/*     */   {
/*  49 */     AffineTransform transform = new AffineTransform();
/*  50 */     transform.translate(translateX, translateY);
/*  51 */     transform.rotate(this.orientation);
/*  52 */     if ((scaleX != 1.0D) && (scaleY != 1.0D)) transform.scale(scaleX, scaleY);
/*  53 */     Area area = new Area(this.shape);
/*  54 */     area.transform(transform);
/*  55 */     return area;
/*     */   }
/*     */ 
/*     */   public Area getLocatedArea(Asteroids asteroids)
/*     */   {
/*  63 */     Double2D loc = asteroids.field.getObjectLocation(this);
/*  64 */     return getLocatedArea(loc.x, loc.y, 1.0D, 1.0D);
/*     */   }
/*     */ 
/*     */   public boolean collisionWithElement(Asteroids asteroids, Element element)
/*     */   {
/*  71 */     Double2D d = asteroids.field.getObjectLocation(this);
/*  72 */     double width = asteroids.field.width;
/*  73 */     double height = asteroids.field.height;
/*  74 */     Area elementloc = element.getLocatedArea(asteroids);
/*     */ 
/*  77 */     Area a = getLocatedArea(asteroids);
/*  78 */     a.add(elementloc);
/*  79 */     if (a.isSingular()) return true;
/*     */ 
/*  82 */     AffineTransform transform = new AffineTransform();
/*  83 */     transform.translate(d.x < width / 2.0D ? width : 0.0D - width, 0.0D);
/*     */ 
/*  86 */     a.transform(transform);
/*  87 */     a.add(elementloc);
/*  88 */     if (a.isSingular()) return true;
/*     */ 
/*  91 */     a = getLocatedArea(asteroids);
/*  92 */     AffineTransform transform2 = new AffineTransform();
/*  93 */     transform2.translate(0.0D, d.y < height / 2.0D ? height : 0.0D - height);
/*     */ 
/*  96 */     a.transform(transform2);
/*  97 */     a.add(elementloc);
/*  98 */     if (a.isSingular()) return true;
/*     */ 
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/* 106 */     Asteroids asteroids = (Asteroids)state;
/* 107 */     this.orientation += this.rotationalVelocity;
/* 108 */     Double2D location = asteroids.field.getObjectLocation(this);
/* 109 */     if (location == null) return;
/* 110 */     asteroids.field.setObjectLocation(this, new Double2D(asteroids.field.stx(location.x + this.velocity.x), asteroids.field.sty(location.y + this.velocity.y)));
/*     */   }
/*     */ 
/*     */   public Color getColor()
/*     */   {
/* 115 */     return Color.blue;
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/* 121 */     graphics.setColor(getColor());
/* 122 */     graphics.draw(getLocatedArea(info.draw.x, info.draw.y, info.draw.width, info.draw.height));
/*     */   }
/*     */ 
/*     */   public void breakIntoShards(Asteroids asteroids)
/*     */   {
/* 130 */     Double2D location = asteroids.field.getObjectLocation(this);
/* 131 */     PathIterator p = new Area(this.shape).getPathIterator(null);
/* 132 */     float[] vals = new float[6];
/* 133 */     float lastX = 0.0F;
/* 134 */     float lastY = 0.0F;
/* 135 */     float firstX = 0.0F;
/* 136 */     float firstY = 0.0F;
/* 137 */     float midX = 0.0F;
/* 138 */     float midY = 0.0F;
/* 139 */     Shard shard = null;
/* 140 */     Double2D vec = null;
/* 141 */     while (!p.isDone())
/*     */     {
/* 143 */       GeneralPath s = new GeneralPath();
/* 144 */       int type = p.currentSegment(vals);
/* 145 */       double force = asteroids.random.nextDouble() * 0.5D;
/* 146 */       switch (type)
/*     */       {
/*     */       case 0:
/* 149 */         lastX = firstX = vals[0];
/* 150 */         lastY = firstY = vals[1];
/* 151 */         break;
/*     */       case 1:
/* 153 */         midX = (lastX + vals[0]) / 2.0F;
/* 154 */         midY = (lastY + vals[1]) / 2.0F;
/* 155 */         s.moveTo(0.0F - midX, 0.0F - midY);
/* 156 */         s.lineTo(lastX - midX, lastY - midY);
/* 157 */         s.lineTo(vals[0] - midX, vals[1] - midY);
/* 158 */         s.closePath();
/*     */ 
/* 160 */         vec = new Double2D(midX, midY).normalize().multiply(force);
/* 161 */         shard = new Shard(asteroids, s, this.orientation, new MutableDouble2D(vec), new Double2D(location.x + midX, location.y + midY), getColor());
/* 162 */         lastX = vals[0];
/* 163 */         lastY = vals[1];
/* 164 */         break;
/*     */       case 4:
/* 166 */         midX = (lastX + vals[0]) / 2.0F;
/* 167 */         midY = (lastY + vals[1]) / 2.0F;
/* 168 */         s.moveTo(0.0F - midX, 0.0F - midY);
/* 169 */         s.lineTo(lastX - midX, lastY - midY);
/* 170 */         s.lineTo(firstX - midX, firstY - midY);
/* 171 */         s.closePath();
/*     */ 
/* 173 */         vec = new Double2D(midX, midY).normalize().multiply(force);
/* 174 */         shard = new Shard(asteroids, s, this.orientation, new MutableDouble2D(vec), new Double2D(location.x + midX, location.y + midY), getColor());
/* 175 */         lastX = vals[0];
/* 176 */         lastY = vals[1];
/* 177 */         break;
/*     */       case 2:
/* 179 */         midX = (lastX + vals[0] + vals[2]) / 3.0F;
/* 180 */         midY = (lastY + vals[1] + vals[3]) / 3.0F;
/* 181 */         s.moveTo(0.0F - midX, 0.0F - midY);
/* 182 */         s.lineTo(lastX - midX, lastY - midY);
/* 183 */         s.quadTo(vals[0] - midX, vals[1] - midY, vals[2] - midX, vals[3] - midY);
/* 184 */         s.closePath();
/* 185 */         vec = new Double2D(midX, midY).normalize().multiply(force);
/* 186 */         shard = new Shard(asteroids, s, this.orientation, new MutableDouble2D(vec), new Double2D(location.x + midX, location.y + midY), getColor());
/* 187 */         lastX = vals[2];
/* 188 */         lastY = vals[3];
/* 189 */         break;
/*     */       case 3:
/* 191 */         midX = (lastX + vals[0] + vals[2] + vals[4]) / 4.0F;
/* 192 */         midY = (lastY + vals[1] + vals[3] + vals[5]) / 4.0F;
/* 193 */         s.moveTo(0.0F - midX, 0.0F - midY);
/* 194 */         s.lineTo(lastX - midX, lastY - midY);
/* 195 */         s.curveTo(vals[0] - midX, vals[1] - midY, vals[2] - midX, vals[3] - midY, vals[4] - midX, vals[5] - midY);
/* 196 */         s.closePath();
/* 197 */         vec = new Double2D(midX, midY).normalize().multiply(force);
/* 198 */         shard = new Shard(asteroids, s, this.orientation, new MutableDouble2D(vec), new Double2D(location.x + midX, location.y + midY), getColor());
/* 199 */         lastX = vals[4];
/* 200 */         lastY = vals[5];
/* 201 */         break;
/*     */       default:
/* 203 */         throw new RuntimeException("default case should never occur");
/*     */       }
/* 205 */       p.next();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void end(Asteroids asteroids)
/*     */   {
/* 212 */     if (this.stopper != null) this.stopper.stop();
/* 213 */     asteroids.field.remove(this);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.asteroids.Element
 * JD-Core Version:    0.6.2
 */