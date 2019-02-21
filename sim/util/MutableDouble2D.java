/*     */ package sim.util;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.geom.Point2D.Float;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public final class MutableDouble2D
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public double x;
/*     */   public double y;
/*     */   static final double infinity = (1.0D / 0.0D);
/*     */ 
/*     */   public MutableDouble2D()
/*     */   {
/*  24 */     this.x = 0.0D; this.y = 0.0D; } 
/*  25 */   public MutableDouble2D(Int2D p) { this.x = p.x; this.y = p.y; } 
/*  26 */   public MutableDouble2D(MutableInt2D p) { this.x = p.x; this.y = p.y; } 
/*  27 */   public MutableDouble2D(MutableDouble2D p) { this.x = p.x; this.y = p.y; } 
/*  28 */   public MutableDouble2D(Double2D p) { this.x = p.x; this.y = p.y; } 
/*  29 */   public MutableDouble2D(Point p) { this.x = p.x; this.y = p.y; } 
/*  30 */   public MutableDouble2D(Point2D.Double p) { this.x = p.x; this.y = p.y; } 
/*  31 */   public MutableDouble2D(Point2D.Float p) { this.x = p.x; this.y = p.y; } 
/*     */   public MutableDouble2D(Point2D p) {
/*  33 */     this.x = p.getX(); this.y = p.getY(); } 
/*  34 */   public MutableDouble2D(double x, double y) { this.x = x; this.y = y; } 
/*  35 */   public final double getX() { return this.x; } 
/*  36 */   public final double getY() { return this.y; } 
/*  37 */   public final void setX(double val) { this.x = val; } 
/*  38 */   public final void setY(double val) { this.y = val; } 
/*  39 */   public final void setTo(double bx, double by) { this.x = bx; this.y = by; } 
/*  40 */   public final void setTo(Int2D b) { this.x = b.x; this.y = b.y; } 
/*  41 */   public final void setTo(Double2D b) { this.x = b.x; this.y = b.y; } 
/*  42 */   public final void setTo(MutableInt2D b) { this.x = b.x; this.y = b.y; } 
/*  43 */   public final void setTo(MutableDouble2D b) { this.x = b.x; this.y = b.y; } 
/*  44 */   public String toString() { return "MutableDouble2D[" + this.x + "," + this.y + "]"; } 
/*  45 */   public String toCoordinates() { return "(" + this.x + ", " + this.y + ")"; } 
/*     */   public Point2D.Double toPoint2D() {
/*  47 */     return new Point2D.Double(this.x, this.y);
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try {
/*  53 */       return super.clone();
/*     */     }
/*     */     catch (CloneNotSupportedException e) {
/*     */     }
/*  57 */     return null;
/*     */   }
/*     */ 
/*     */   public final int hashCode()
/*     */   {
/*  64 */     double x = this.x;
/*  65 */     double y = this.y;
/*     */ 
/*  69 */     if (x == -0.0D) x = 0.0D;
/*  70 */     if (y == -0.0D) y = 0.0D;
/*     */ 
/*  73 */     if (((int)x == x) && ((int)y == y))
/*     */     {
/*  77 */       int y_ = (int)y;
/*  78 */       int x_ = (int)x;
/*     */ 
/*  83 */       y_ += (y_ << 15 ^ 0xFFFFFFFF);
/*  84 */       y_ ^= y_ >>> 10;
/*  85 */       y_ += (y_ << 3);
/*  86 */       y_ ^= y_ >>> 6;
/*  87 */       y_ += (y_ << 11 ^ 0xFFFFFFFF);
/*  88 */       y_ ^= y_ >>> 16;
/*     */ 
/*  92 */       return x_ ^ y_;
/*     */     }
/*     */ 
/* 129 */     long key = Double.doubleToLongBits(y);
/*     */ 
/* 131 */     key += (key << 32 ^ 0xFFFFFFFF);
/* 132 */     key ^= key >>> 22;
/* 133 */     key += (key << 13 ^ 0xFFFFFFFF);
/* 134 */     key ^= key >>> 8;
/* 135 */     key += (key << 3);
/* 136 */     key ^= key >>> 15;
/* 137 */     key += (key << 27 ^ 0xFFFFFFFF);
/* 138 */     key ^= key >>> 31;
/*     */ 
/* 142 */     key ^= Double.doubleToLongBits(x);
/*     */ 
/* 145 */     return (int)(key ^ key >> 32);
/*     */   }
/*     */ 
/*     */   public final boolean equals(Object obj)
/*     */   {
/* 152 */     if (obj == null) return false;
/* 153 */     if ((obj instanceof Double2D))
/*     */     {
/* 155 */       Double2D other = (Double2D)obj;
/*     */ 
/* 157 */       return ((this.x == other.x) || ((Double.isNaN(this.x)) && (Double.isNaN(other.x)))) && ((this.y == other.y) || ((Double.isNaN(this.y)) && (Double.isNaN(other.y))));
/*     */     }
/*     */ 
/* 164 */     if ((obj instanceof MutableDouble2D))
/*     */     {
/* 166 */       MutableDouble2D other = (MutableDouble2D)obj;
/*     */ 
/* 168 */       return ((this.x == other.x) || ((Double.isNaN(this.x)) && (Double.isNaN(other.x)))) && ((this.y == other.y) || ((Double.isNaN(this.y)) && (Double.isNaN(other.y))));
/*     */     }
/*     */ 
/* 175 */     if ((obj instanceof Int2D))
/*     */     {
/* 177 */       Int2D other = (Int2D)obj;
/* 178 */       return (other.x == this.x) && (other.y == this.y);
/*     */     }
/* 180 */     if ((obj instanceof MutableInt2D))
/*     */     {
/* 182 */       MutableInt2D other = (MutableInt2D)obj;
/* 183 */       return (other.x == this.x) && (other.y == this.y);
/*     */     }
/* 185 */     return false;
/*     */   }
/*     */ 
/*     */   public double distance(double x, double y)
/*     */   {
/* 192 */     double dx = this.x - x;
/* 193 */     double dy = this.y - y;
/* 194 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(MutableDouble2D p)
/*     */   {
/* 200 */     double dx = this.x - p.x;
/* 201 */     double dy = this.y - p.y;
/* 202 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(Double2D p)
/*     */   {
/* 208 */     double dx = this.x - p.x;
/* 209 */     double dy = this.y - p.y;
/* 210 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(Int2D p)
/*     */   {
/* 216 */     double dx = this.x - p.x;
/* 217 */     double dy = this.y - p.y;
/* 218 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(MutableInt2D p)
/*     */   {
/* 224 */     double dx = this.x - p.x;
/* 225 */     double dy = this.y - p.y;
/* 226 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(Point2D p)
/*     */   {
/* 232 */     double dx = this.x - p.getX();
/* 233 */     double dy = this.y - p.getY();
/* 234 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distanceSq(double x, double y)
/*     */   {
/* 240 */     double dx = this.x - x;
/* 241 */     double dy = this.y - y;
/* 242 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Double2D p)
/*     */   {
/* 248 */     double dx = this.x - p.x;
/* 249 */     double dy = this.y - p.y;
/* 250 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(MutableDouble2D p)
/*     */   {
/* 256 */     double dx = this.x - p.x;
/* 257 */     double dy = this.y - p.y;
/* 258 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Int2D p)
/*     */   {
/* 264 */     double dx = this.x - p.x;
/* 265 */     double dy = this.y - p.y;
/* 266 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(MutableInt2D p)
/*     */   {
/* 272 */     double dx = this.x - p.x;
/* 273 */     double dy = this.y - p.y;
/* 274 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Point2D p)
/*     */   {
/* 280 */     double dx = this.x - p.getX();
/* 281 */     double dy = this.y - p.getY();
/* 282 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(double x, double y)
/*     */   {
/* 288 */     double dx = Math.abs(this.x - x);
/* 289 */     double dy = Math.abs(this.y - y);
/* 290 */     return dx + dy;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(Double2D p)
/*     */   {
/* 296 */     double dx = Math.abs(this.x - p.x);
/* 297 */     double dy = Math.abs(this.y - p.y);
/* 298 */     return dx + dy;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(Int2D p)
/*     */   {
/* 304 */     double dx = Math.abs(this.x - p.x);
/* 305 */     double dy = Math.abs(this.y - p.y);
/* 306 */     return dx + dy;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(MutableDouble2D p)
/*     */   {
/* 312 */     double dx = Math.abs(this.x - p.x);
/* 313 */     double dy = Math.abs(this.y - p.y);
/* 314 */     return dx + dy;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(MutableInt2D p)
/*     */   {
/* 320 */     double dx = Math.abs(this.x - p.x);
/* 321 */     double dy = Math.abs(this.y - p.y);
/* 322 */     return dx + dy;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(Point2D p)
/*     */   {
/* 328 */     double dx = Math.abs(this.x - p.getX());
/* 329 */     double dy = Math.abs(this.y - p.getY());
/* 330 */     return dx + dy;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D addIn(Double2D other)
/*     */   {
/* 336 */     this.x = (other.x + this.x);
/* 337 */     this.y = (other.y + this.y);
/* 338 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D addIn(MutableDouble2D other)
/*     */   {
/* 344 */     other.x += this.x;
/* 345 */     other.y += this.y;
/* 346 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D addIn(double x, double y)
/*     */   {
/* 352 */     this.x += x;
/* 353 */     this.y += y;
/* 354 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D add(MutableDouble2D other1, MutableDouble2D other2)
/*     */   {
/* 360 */     other1.x += other2.x;
/* 361 */     other1.y += other2.y;
/* 362 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D add(Double2D other1, MutableDouble2D other2)
/*     */   {
/* 368 */     this.x = (other1.x + other2.x);
/* 369 */     this.y = (other1.y + other2.y);
/* 370 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D add(MutableDouble2D other1, Double2D other2)
/*     */   {
/* 376 */     other1.x += other2.x;
/* 377 */     other1.y += other2.y;
/* 378 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D dup()
/*     */   {
/* 384 */     return new MutableDouble2D(this);
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D subtractIn(Double2D other)
/*     */   {
/* 391 */     this.x -= other.x;
/* 392 */     this.y -= other.y;
/* 393 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D subtractIn(MutableDouble2D other)
/*     */   {
/* 399 */     this.x -= other.x;
/* 400 */     this.y -= other.y;
/* 401 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D subtract(MutableDouble2D other1, MutableDouble2D other2)
/*     */   {
/* 407 */     other1.x -= other2.x;
/* 408 */     other1.y -= other2.y;
/* 409 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D subtract(Double2D other1, MutableDouble2D other2)
/*     */   {
/* 415 */     this.x = (other1.x - other2.x);
/* 416 */     this.y = (other1.y - other2.y);
/* 417 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D subtract(MutableDouble2D other1, Double2D other2)
/*     */   {
/* 423 */     other1.x -= other2.x;
/* 424 */     other1.y -= other2.y;
/* 425 */     return this;
/*     */   }
/*     */ 
/*     */   public final double length()
/*     */   {
/* 431 */     return Math.sqrt(this.x * this.x + this.y * this.y);
/*     */   }
/*     */ 
/*     */   public final double lengthSq()
/*     */   {
/* 437 */     return this.x * this.x + this.y * this.y;
/*     */   }
/*     */ 
/*     */   public final double angle()
/*     */   {
/* 443 */     return Math.atan2(this.y, this.x);
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D multiplyIn(double val)
/*     */   {
/* 449 */     this.x *= val;
/* 450 */     this.y *= val;
/* 451 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D multiply(MutableDouble2D other, double val)
/*     */   {
/* 457 */     other.x *= val;
/* 458 */     other.y *= val;
/* 459 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D multiply(Double2D other, double val)
/*     */   {
/* 465 */     this.x = (other.x * val);
/* 466 */     this.y = (other.y * val);
/* 467 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D normalize()
/*     */   {
/* 474 */     double invertedlen = 1.0D / Math.sqrt(this.x * this.x + this.y * this.y);
/* 475 */     if ((invertedlen == (1.0D / 0.0D)) || (invertedlen == (-1.0D / 0.0D)) || (invertedlen == 0.0D) || (invertedlen != invertedlen))
/* 476 */       throw new ArithmeticException("" + this + " length is " + Math.sqrt(this.x * this.x + this.y * this.y) + ", cannot normalize");
/* 477 */     this.x *= invertedlen;
/* 478 */     this.y *= invertedlen;
/* 479 */     return this;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public final MutableDouble2D setLength(double val)
/*     */   {
/* 485 */     return resize(val);
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D resize(double val)
/*     */   {
/* 490 */     if (val < 0.0D)
/* 491 */       throw new IllegalArgumentException("The argument to MutableDouble2D.setLength(...) must be zero or positive");
/* 492 */     if (val == 0.0D) { this.x = (this.y = 0.0D);
/*     */     } else
/*     */     {
/* 495 */       double len = Math.sqrt(this.x * this.x + this.y * this.y);
/* 496 */       if ((len != len) || (len == (1.0D / 0.0D)) || (len == (-1.0D / 0.0D)) || (len == 0.0D))
/* 497 */         throw new ArithmeticException("" + this + " length is " + len + " cannot change its length");
/* 498 */       double invertedlen = val / len;
/* 499 */       this.x *= invertedlen;
/* 500 */       this.y *= invertedlen;
/*     */     }
/* 502 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D rotate(double theta)
/*     */   {
/* 511 */     double sinTheta = Math.sin(theta);
/* 512 */     double cosTheta = Math.cos(theta);
/* 513 */     double x = this.x;
/* 514 */     double y = this.y;
/* 515 */     this.x = (cosTheta * x + -sinTheta * y);
/* 516 */     this.y = (sinTheta * x + cosTheta * y);
/* 517 */     return this;
/*     */   }
/*     */ 
/*     */   public final double dot(MutableDouble2D other)
/*     */   {
/* 523 */     return other.x * this.x + other.y * this.y;
/*     */   }
/*     */ 
/*     */   public double perpDot(MutableDouble2D other)
/*     */   {
/* 530 */     return -this.y * other.x + this.x * other.y;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D zero()
/*     */   {
/* 536 */     this.x = 0.0D;
/* 537 */     this.y = 0.0D;
/* 538 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D setToMinus(MutableDouble2D b)
/*     */   {
/* 544 */     this.x = (-b.x);
/* 545 */     this.y = (-b.y);
/* 546 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble2D negate()
/*     */   {
/* 552 */     this.x = (-this.x);
/* 553 */     this.y = (-this.y);
/* 554 */     return this;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.MutableDouble2D
 * JD-Core Version:    0.6.2
 */