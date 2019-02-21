/*     */ package sim.util;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.geom.Point2D.Float;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public final class Double2D
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public final double x;
/*     */   public final double y;
/*     */   static final double infinity = (1.0D / 0.0D);
/*     */ 
/*     */   public Double2D()
/*     */   {
/*  25 */     this.x = 0.0D; this.y = 0.0D; } 
/*  26 */   public Double2D(Int2D p) { this.x = p.x; this.y = p.y; } 
/*  27 */   public Double2D(MutableInt2D p) { this.x = p.x; this.y = p.y; } 
/*  28 */   public Double2D(MutableDouble2D p) { this.x = p.x; this.y = p.y; } 
/*  29 */   public Double2D(Point p) { this.x = p.x; this.y = p.y; } 
/*  30 */   public Double2D(Point2D.Double p) { this.x = p.x; this.y = p.y; } 
/*  31 */   public Double2D(Point2D.Float p) { this.x = p.x; this.y = p.y; } 
/*     */   public Double2D(Point2D p) {
/*  33 */     this.x = p.getX(); this.y = p.getY(); } 
/*  34 */   public Double2D(double x, double y) { this.x = x; this.y = y; } 
/*  35 */   public final double getX() { return this.x; } 
/*  36 */   public final double getY() { return this.y; } 
/*  37 */   public String toString() { return "Double2D[" + this.x + "," + this.y + "]"; } 
/*  38 */   public String toCoordinates() { return "(" + this.x + ", " + this.y + ")"; } 
/*     */   public Point2D.Double toPoint2D() {
/*  40 */     return new Point2D.Double(this.x, this.y);
/*     */   }
/*     */ 
/*     */   public final int hashCode() {
/*  44 */     double x = this.x;
/*  45 */     double y = this.y;
/*     */ 
/*  49 */     if (x == -0.0D) x = 0.0D;
/*  50 */     if (y == -0.0D) y = 0.0D;
/*     */ 
/*  53 */     if (((int)x == x) && ((int)y == y))
/*     */     {
/*  57 */       int y_ = (int)y;
/*  58 */       int x_ = (int)x;
/*     */ 
/*  63 */       y_ += (y_ << 15 ^ 0xFFFFFFFF);
/*  64 */       y_ ^= y_ >>> 10;
/*  65 */       y_ += (y_ << 3);
/*  66 */       y_ ^= y_ >>> 6;
/*  67 */       y_ += (y_ << 11 ^ 0xFFFFFFFF);
/*  68 */       y_ ^= y_ >>> 16;
/*     */ 
/*  72 */       return x_ ^ y_;
/*     */     }
/*     */ 
/* 108 */     long key = Double.doubleToLongBits(y);
/*     */ 
/* 110 */     key += (key << 32 ^ 0xFFFFFFFF);
/* 111 */     key ^= key >>> 22;
/* 112 */     key += (key << 13 ^ 0xFFFFFFFF);
/* 113 */     key ^= key >>> 8;
/* 114 */     key += (key << 3);
/* 115 */     key ^= key >>> 15;
/* 116 */     key += (key << 27 ^ 0xFFFFFFFF);
/* 117 */     key ^= key >>> 31;
/*     */ 
/* 121 */     key ^= Double.doubleToLongBits(x);
/*     */ 
/* 124 */     return (int)(key ^ key >> 32);
/*     */   }
/*     */ 
/*     */   public final boolean equals(Object obj)
/*     */   {
/* 132 */     if (obj == null) return false;
/* 133 */     if ((obj instanceof Double2D))
/*     */     {
/* 135 */       Double2D other = (Double2D)obj;
/*     */ 
/* 137 */       return ((this.x == other.x) || ((Double.isNaN(this.x)) && (Double.isNaN(other.x)))) && ((this.y == other.y) || ((Double.isNaN(this.y)) && (Double.isNaN(other.y))));
/*     */     }
/*     */ 
/* 144 */     if ((obj instanceof MutableDouble2D))
/*     */     {
/* 146 */       MutableDouble2D other = (MutableDouble2D)obj;
/*     */ 
/* 148 */       return ((this.x == other.x) || ((Double.isNaN(this.x)) && (Double.isNaN(other.x)))) && ((this.y == other.y) || ((Double.isNaN(this.y)) && (Double.isNaN(other.y))));
/*     */     }
/*     */ 
/* 155 */     if ((obj instanceof Int2D))
/*     */     {
/* 157 */       Int2D other = (Int2D)obj;
/* 158 */       return (other.x == this.x) && (other.y == this.y);
/*     */     }
/* 160 */     if ((obj instanceof MutableInt2D))
/*     */     {
/* 162 */       MutableInt2D other = (MutableInt2D)obj;
/* 163 */       return (other.x == this.x) && (other.y == this.y);
/*     */     }
/* 165 */     return false;
/*     */   }
/*     */ 
/*     */   public double distance(double x, double y)
/*     */   {
/* 172 */     double dx = this.x - x;
/* 173 */     double dy = this.y - y;
/* 174 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(Double2D p)
/*     */   {
/* 180 */     double dx = this.x - p.x;
/* 181 */     double dy = this.y - p.y;
/* 182 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(Int2D p)
/*     */   {
/* 188 */     double dx = this.x - p.x;
/* 189 */     double dy = this.y - p.y;
/* 190 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(MutableInt2D p)
/*     */   {
/* 196 */     double dx = this.x - p.x;
/* 197 */     double dy = this.y - p.y;
/* 198 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(Point2D p)
/*     */   {
/* 204 */     double dx = this.x - p.getX();
/* 205 */     double dy = this.y - p.getY();
/* 206 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distanceSq(double x, double y)
/*     */   {
/* 212 */     double dx = this.x - x;
/* 213 */     double dy = this.y - y;
/* 214 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Double2D p)
/*     */   {
/* 220 */     double dx = this.x - p.x;
/* 221 */     double dy = this.y - p.y;
/* 222 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Int2D p)
/*     */   {
/* 228 */     double dx = this.x - p.x;
/* 229 */     double dy = this.y - p.y;
/* 230 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(MutableInt2D p)
/*     */   {
/* 236 */     double dx = this.x - p.x;
/* 237 */     double dy = this.y - p.y;
/* 238 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Point2D p)
/*     */   {
/* 244 */     double dx = this.x - p.getX();
/* 245 */     double dy = this.y - p.getY();
/* 246 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(double x, double y)
/*     */   {
/* 252 */     double dx = Math.abs(this.x - x);
/* 253 */     double dy = Math.abs(this.y - y);
/* 254 */     return dx + dy;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(Double2D p)
/*     */   {
/* 260 */     double dx = Math.abs(this.x - p.x);
/* 261 */     double dy = Math.abs(this.y - p.y);
/* 262 */     return dx + dy;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(Int2D p)
/*     */   {
/* 268 */     double dx = Math.abs(this.x - p.x);
/* 269 */     double dy = Math.abs(this.y - p.y);
/* 270 */     return dx + dy;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(MutableDouble2D p)
/*     */   {
/* 276 */     double dx = Math.abs(this.x - p.x);
/* 277 */     double dy = Math.abs(this.y - p.y);
/* 278 */     return dx + dy;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(MutableInt2D p)
/*     */   {
/* 284 */     double dx = Math.abs(this.x - p.x);
/* 285 */     double dy = Math.abs(this.y - p.y);
/* 286 */     return dx + dy;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(Point2D p)
/*     */   {
/* 292 */     double dx = Math.abs(this.x - p.getX());
/* 293 */     double dy = Math.abs(this.y - p.getY());
/* 294 */     return dx + dy;
/*     */   }
/*     */ 
/*     */   public final Double2D add(Double2D other)
/*     */   {
/* 299 */     return new Double2D(this.x + other.x, this.y + other.y);
/*     */   }
/*     */ 
/*     */   public final Double2D subtract(Double2D other)
/*     */   {
/* 306 */     return new Double2D(this.x - other.x, this.y - other.y);
/*     */   }
/*     */ 
/*     */   public final double length()
/*     */   {
/* 312 */     return Math.sqrt(this.x * this.x + this.y * this.y);
/*     */   }
/*     */ 
/*     */   public final double angle()
/*     */   {
/* 318 */     return Math.atan2(this.y, this.x);
/*     */   }
/*     */ 
/*     */   public final double lengthSq()
/*     */   {
/* 324 */     return this.x * this.x + this.y * this.y;
/*     */   }
/*     */ 
/*     */   public final Double2D multiply(double val)
/*     */   {
/* 330 */     return new Double2D(this.x * val, this.y * val);
/*     */   }
/*     */ 
/*     */   public final Double2D resize(double dist)
/*     */   {
/* 340 */     if (dist == 0.0D)
/* 341 */       return new Double2D(0.0D, 0.0D);
/* 342 */     if ((dist == (1.0D / 0.0D)) || (dist == (-1.0D / 0.0D)) || (dist != dist))
/* 343 */       throw new ArithmeticException("Cannot resize to distance " + dist);
/* 344 */     if (((this.x == 0.0D) && (this.y == 0.0D)) || (this.x == (1.0D / 0.0D)) || (this.x == (-1.0D / 0.0D)) || (this.x != this.x) || (this.y == (1.0D / 0.0D)) || (this.y == (-1.0D / 0.0D)) || (this.y != this.y))
/*     */     {
/* 347 */       throw new ArithmeticException("Cannot resize a vector with infinite or NaN values, or of length 0, except to length 0");
/*     */     }
/* 349 */     double temp = length();
/* 350 */     return new Double2D(this.x * dist / temp, this.y * dist / temp);
/*     */   }
/*     */ 
/*     */   public final Double2D normalize()
/*     */   {
/* 365 */     return resize(1.0D);
/*     */   }
/*     */ 
/*     */   public final double dot(Double2D other)
/*     */   {
/* 371 */     return other.x * this.x + other.y * this.y;
/*     */   }
/*     */ 
/*     */   public double perpDot(Double2D other)
/*     */   {
/* 385 */     return -this.y * other.x + this.x * other.y;
/*     */   }
/*     */ 
/*     */   public final Double2D negate()
/*     */   {
/* 391 */     return new Double2D(-this.x, -this.y);
/*     */   }
/*     */ 
/*     */   public final Double2D rotate(double theta)
/*     */   {
/* 407 */     double sinTheta = Math.sin(theta);
/* 408 */     double cosTheta = Math.cos(theta);
/* 409 */     double x = this.x;
/* 410 */     double y = this.y;
/* 411 */     return new Double2D(cosTheta * x + -sinTheta * y, sinTheta * x + cosTheta * y);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.Double2D
 * JD-Core Version:    0.6.2
 */