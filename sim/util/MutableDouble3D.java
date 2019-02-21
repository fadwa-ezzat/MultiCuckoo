/*     */ package sim.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public final class MutableDouble3D
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   static final double infinity = (1.0D / 0.0D);
/*     */ 
/*     */   public MutableDouble3D()
/*     */   {
/*  25 */     this.x = 0.0D; this.y = 0.0D; this.z = 0.0D;
/*     */   }
/*  27 */   public MutableDouble3D(Int2D p) { this.x = p.x; this.y = p.y; this.z = 0.0D; } 
/*  28 */   public MutableDouble3D(Int2D p, double z) { this.x = p.x; this.y = p.y; this.z = z; } 
/*  29 */   public MutableDouble3D(Int3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*     */   public MutableDouble3D(MutableInt2D p) {
/*  31 */     this.x = p.x; this.y = p.y; this.z = 0.0D; } 
/*  32 */   public MutableDouble3D(MutableInt2D p, double z) { this.x = p.x; this.y = p.y; this.z = z; } 
/*  33 */   public MutableDouble3D(MutableInt3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*     */   public MutableDouble3D(Double2D p) {
/*  35 */     this.x = p.x; this.y = p.y; this.z = 0.0D; } 
/*  36 */   public MutableDouble3D(Double2D p, double z) { this.x = p.x; this.y = p.y; this.z = z; } 
/*  37 */   public MutableDouble3D(Double3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*     */   public MutableDouble3D(MutableDouble2D p) {
/*  39 */     this.x = p.x; this.y = p.y; this.z = 0.0D; } 
/*  40 */   public MutableDouble3D(MutableDouble2D p, double z) { this.x = p.x; this.y = p.y; this.z = z; } 
/*  41 */   public MutableDouble3D(MutableDouble3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*  42 */   public MutableDouble3D(double x, double y, double z) { this.x = x; this.y = y; this.z = z; } 
/*  43 */   public final double getX() { return this.x; } 
/*  44 */   public final double getY() { return this.y; } 
/*  45 */   public final double getZ() { return this.z; } 
/*  46 */   public final void setX(double val) { this.x = val; } 
/*  47 */   public final void setY(double val) { this.y = val; } 
/*  48 */   public final void setZ(double val) { this.z = val; } 
/*  49 */   public void setTo(double x, double y, double z) { this.x = x; this.y = y; this.z = z; } 
/*  50 */   public void setTo(Int3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*  51 */   public void setTo(MutableInt3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*  52 */   public void setTo(Double3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*  53 */   public void setTo(MutableDouble3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*  54 */   public String toString() { return "MutableDouble3D[" + this.x + "," + this.y + "," + this.z + "]"; } 
/*  55 */   public String toCoordinates() { return "(" + this.x + ", " + this.y + ", " + this.z + ")"; }
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try {
/*  60 */       return super.clone();
/*     */     }
/*     */     catch (CloneNotSupportedException e) {
/*     */     }
/*  64 */     return null;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  70 */     double x = this.x;
/*  71 */     double y = this.y;
/*  72 */     double z = this.z;
/*     */ 
/*  76 */     if (x == -0.0D) x = 0.0D;
/*  77 */     if (y == -0.0D) y = 0.0D;
/*  78 */     if (z == -0.0D) z = 0.0D;
/*     */ 
/*  81 */     if (((int)x == x) && ((int)y == y) && ((int)z == z))
/*     */     {
/*  85 */       int y_ = (int)y;
/*  86 */       int x_ = (int)x;
/*  87 */       int z_ = (int)z;
/*     */ 
/*  92 */       z_ += (z_ << 15 ^ 0xFFFFFFFF);
/*  93 */       z_ ^= z_ >>> 10;
/*  94 */       z_ += (z_ << 3);
/*  95 */       z_ ^= z_ >>> 6;
/*  96 */       z_ += (z_ << 11 ^ 0xFFFFFFFF);
/*  97 */       z_ ^= z_ >>> 16;
/*     */ 
/*  99 */       z_ ^= y_;
/* 100 */       z_ += 17;
/*     */ 
/* 102 */       z_ += (z_ << 15 ^ 0xFFFFFFFF);
/* 103 */       z_ ^= z_ >>> 10;
/* 104 */       z_ += (z_ << 3);
/* 105 */       z_ ^= z_ >>> 6;
/* 106 */       z_ += (z_ << 11 ^ 0xFFFFFFFF);
/* 107 */       z_ ^= z_ >>> 16;
/*     */ 
/* 111 */       return x_ ^ z_;
/*     */     }
/*     */ 
/* 147 */     long key = Double.doubleToLongBits(z);
/* 148 */     key += (key << 32 ^ 0xFFFFFFFF);
/* 149 */     key ^= key >>> 22;
/* 150 */     key += (key << 13 ^ 0xFFFFFFFF);
/* 151 */     key ^= key >>> 8;
/* 152 */     key += (key << 3);
/* 153 */     key ^= key >>> 15;
/* 154 */     key += (key << 27 ^ 0xFFFFFFFF);
/* 155 */     key ^= key >>> 31;
/*     */ 
/* 157 */     key ^= Double.doubleToLongBits(y);
/* 158 */     key += 17L;
/*     */ 
/* 160 */     key += (key << 32 ^ 0xFFFFFFFF);
/* 161 */     key ^= key >>> 22;
/* 162 */     key += (key << 13 ^ 0xFFFFFFFF);
/* 163 */     key ^= key >>> 8;
/* 164 */     key += (key << 3);
/* 165 */     key ^= key >>> 15;
/* 166 */     key += (key << 27 ^ 0xFFFFFFFF);
/* 167 */     key ^= key >>> 31;
/*     */ 
/* 171 */     key ^= Double.doubleToLongBits(x);
/*     */ 
/* 174 */     return (int)(key ^ key >> 32);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 181 */     if (obj == null) return false;
/* 182 */     if ((obj instanceof Double3D))
/*     */     {
/* 184 */       Double3D other = (Double3D)obj;
/*     */ 
/* 186 */       return ((this.x == other.x) || ((Double.isNaN(this.x)) && (Double.isNaN(other.x)))) && ((this.y == other.y) || ((Double.isNaN(this.y)) && (Double.isNaN(other.y)))) && ((this.z == other.z) || ((Double.isNaN(this.z)) && (Double.isNaN(other.z))));
/*     */     }
/*     */ 
/* 195 */     if ((obj instanceof MutableDouble3D))
/*     */     {
/* 197 */       MutableDouble3D other = (MutableDouble3D)obj;
/*     */ 
/* 199 */       return ((this.x == other.x) || ((Double.isNaN(this.x)) && (Double.isNaN(other.x)))) && ((this.y == other.y) || ((Double.isNaN(this.y)) && (Double.isNaN(other.y)))) && ((this.z == other.z) || ((Double.isNaN(this.z)) && (Double.isNaN(other.z))));
/*     */     }
/*     */ 
/* 208 */     if ((obj instanceof Int3D))
/*     */     {
/* 210 */       Int3D other = (Int3D)obj;
/* 211 */       return (other.x == this.x) && (other.y == this.y) && (other.z == this.z);
/*     */     }
/* 213 */     if ((obj instanceof MutableInt3D))
/*     */     {
/* 215 */       MutableInt3D other = (MutableInt3D)obj;
/* 216 */       return (other.x == this.x) && (other.y == this.y) && (other.z == this.z);
/*     */     }
/* 218 */     return false;
/*     */   }
/*     */ 
/*     */   public double distance(double x, double y, double z)
/*     */   {
/* 224 */     double dx = this.x - x;
/* 225 */     double dy = this.y - y;
/* 226 */     double dz = this.z - z;
/* 227 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distance(Double3D p)
/*     */   {
/* 233 */     double dx = this.x - p.x;
/* 234 */     double dy = this.y - p.y;
/* 235 */     double dz = this.z - p.z;
/* 236 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distance(Int3D p)
/*     */   {
/* 242 */     double dx = this.x - p.x;
/* 243 */     double dy = this.y - p.y;
/* 244 */     double dz = this.z - p.z;
/* 245 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distance(MutableInt3D p)
/*     */   {
/* 251 */     double dx = this.x - p.x;
/* 252 */     double dy = this.y - p.y;
/* 253 */     double dz = this.z - p.z;
/* 254 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distance(MutableDouble3D p)
/*     */   {
/* 260 */     double dx = this.x - p.x;
/* 261 */     double dy = this.y - p.y;
/* 262 */     double dz = this.z - p.z;
/* 263 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distanceSq(double x, double y, double z)
/*     */   {
/* 269 */     double dx = this.x - x;
/* 270 */     double dy = this.y - y;
/* 271 */     double dz = this.z - z;
/* 272 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Double3D p)
/*     */   {
/* 278 */     double dx = this.x - p.x;
/* 279 */     double dy = this.y - p.y;
/* 280 */     double dz = this.z - p.z;
/* 281 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Int3D p)
/*     */   {
/* 287 */     double dx = this.x - p.x;
/* 288 */     double dy = this.y - p.y;
/* 289 */     double dz = this.z - p.z;
/* 290 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public double distanceSq(MutableInt3D p)
/*     */   {
/* 296 */     double dx = this.x - p.x;
/* 297 */     double dy = this.y - p.y;
/* 298 */     double dz = this.z - p.z;
/* 299 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public double distanceSq(MutableDouble3D p)
/*     */   {
/* 305 */     double dx = this.x - p.x;
/* 306 */     double dy = this.y - p.y;
/* 307 */     double dz = this.z - p.z;
/* 308 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(double x, double y, double z)
/*     */   {
/* 315 */     double dx = Math.abs(this.x - x);
/* 316 */     double dy = Math.abs(this.y - y);
/* 317 */     double dz = Math.abs(this.z - z);
/* 318 */     return dx + dy + dz;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(Double3D p)
/*     */   {
/* 324 */     double dx = Math.abs(this.x - p.x);
/* 325 */     double dy = Math.abs(this.y - p.y);
/* 326 */     double dz = Math.abs(this.z - p.z);
/* 327 */     return dx + dy + dz;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(Int3D p)
/*     */   {
/* 333 */     double dx = Math.abs(this.x - p.x);
/* 334 */     double dy = Math.abs(this.y - p.y);
/* 335 */     double dz = Math.abs(this.z - p.z);
/* 336 */     return dx + dy + dz;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(MutableDouble3D p)
/*     */   {
/* 342 */     double dx = Math.abs(this.x - p.x);
/* 343 */     double dy = Math.abs(this.y - p.y);
/* 344 */     double dz = Math.abs(this.z - p.z);
/* 345 */     return dx + dy + dz;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(MutableInt3D p)
/*     */   {
/* 351 */     double dx = Math.abs(this.x - p.x);
/* 352 */     double dy = Math.abs(this.y - p.y);
/* 353 */     double dz = Math.abs(this.z - p.z);
/* 354 */     return dx + dy + dz;
/*     */   }
/*     */ 
/*     */   public final MutableDouble3D addIn(Double3D other)
/*     */   {
/* 360 */     this.x = (other.x + this.x);
/* 361 */     this.y = (other.y + this.y);
/* 362 */     this.z = (other.z + this.z);
/* 363 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble3D addIn(MutableDouble3D other)
/*     */   {
/* 369 */     other.x += this.x;
/* 370 */     other.y += this.y;
/* 371 */     other.z += this.z;
/* 372 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble3D add(MutableDouble3D other1, MutableDouble3D other2)
/*     */   {
/* 378 */     other1.x += other2.x;
/* 379 */     other1.y += other2.y;
/* 380 */     other1.z += other2.z;
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble3D addIn(double x, double y, double z)
/*     */   {
/* 387 */     this.x += x;
/* 388 */     this.y += y;
/* 389 */     this.z += z;
/* 390 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble3D dup()
/*     */   {
/* 396 */     return new MutableDouble3D(this);
/*     */   }
/*     */ 
/*     */   public final MutableDouble3D subtractIn(Double3D other)
/*     */   {
/* 403 */     this.x -= other.x;
/* 404 */     this.y -= other.y;
/* 405 */     this.z -= other.z;
/* 406 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble3D subtractIn(MutableDouble3D other)
/*     */   {
/* 412 */     this.x -= other.x;
/* 413 */     this.y -= other.y;
/* 414 */     this.z -= other.z;
/* 415 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble3D subtract(MutableDouble3D other1, MutableDouble3D other2)
/*     */   {
/* 421 */     other1.x -= other2.x;
/* 422 */     other1.y -= other2.y;
/* 423 */     other1.z -= other2.z;
/* 424 */     return this;
/*     */   }
/*     */ 
/*     */   public final double length()
/*     */   {
/* 430 */     return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/*     */   }
/*     */ 
/*     */   public final MutableDouble3D multiplyIn(double val)
/*     */   {
/* 436 */     this.x *= val;
/* 437 */     this.y *= val;
/* 438 */     this.z *= val;
/* 439 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble3D multiply(MutableDouble3D other, double val)
/*     */   {
/* 445 */     other.x *= val;
/* 446 */     other.y *= val;
/* 447 */     other.z *= val;
/* 448 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble3D normalize()
/*     */   {
/* 455 */     double invertedlen = 1.0D / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/* 456 */     if ((invertedlen == (1.0D / 0.0D)) || (invertedlen == (-1.0D / 0.0D)) || (invertedlen == 0.0D) || (invertedlen != invertedlen))
/* 457 */       throw new ArithmeticException("" + this + " length is " + Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z) + ", cannot normalize");
/* 458 */     this.x *= invertedlen;
/* 459 */     this.y *= invertedlen;
/* 460 */     this.z *= invertedlen;
/* 461 */     return this;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public final MutableDouble3D setLength(double val)
/*     */   {
/* 467 */     return resize(val);
/*     */   }
/*     */ 
/*     */   public final MutableDouble3D resize(double val)
/*     */   {
/* 472 */     if (val < 0.0D)
/* 473 */       throw new IllegalArgumentException("The argument to MutableDouble3D.setLength(...) must be zero or positive");
/* 474 */     double len = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/* 475 */     if (val == 0.0D) { this.x = (this.y = this.z = 0.0D);
/*     */     } else
/*     */     {
/* 478 */       if ((len != len) || (len == (1.0D / 0.0D)) || (len == (-1.0D / 0.0D)) || (len == 0.0D))
/* 479 */         throw new ArithmeticException("" + this + " length is " + len + " cannot change its length");
/* 480 */       double invertedlen = val / len;
/* 481 */       this.x *= invertedlen;
/* 482 */       this.y *= invertedlen;
/* 483 */       this.z *= invertedlen;
/*     */     }
/* 485 */     return this;
/*     */   }
/*     */ 
/*     */   public final double dot(MutableDouble3D other)
/*     */   {
/* 491 */     return other.x * this.x + other.y * this.y + other.z * this.z;
/*     */   }
/*     */ 
/*     */   public final MutableDouble3D zero()
/*     */   {
/* 497 */     this.x = 0.0D;
/* 498 */     this.y = 0.0D;
/* 499 */     this.z = 0.0D;
/* 500 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble3D setToMinus(MutableDouble3D b)
/*     */   {
/* 506 */     this.x = (-b.x);
/* 507 */     this.y = (-b.y);
/* 508 */     this.z = (-b.z);
/* 509 */     return this;
/*     */   }
/*     */ 
/*     */   public final MutableDouble3D negate()
/*     */   {
/* 515 */     this.x = (-this.x);
/* 516 */     this.y = (-this.y);
/* 517 */     this.z = (-this.z);
/* 518 */     return this;
/*     */   }
/*     */ 
/*     */   public final double lengthSq()
/*     */   {
/* 524 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.MutableDouble3D
 * JD-Core Version:    0.6.2
 */