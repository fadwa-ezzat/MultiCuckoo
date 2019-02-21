/*     */ package sim.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public final class Double3D
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public final double x;
/*     */   public final double y;
/*     */   public final double z;
/*     */   static final double infinity = (1.0D / 0.0D);
/*     */ 
/*     */   public Double3D()
/*     */   {
/*  27 */     this.x = 0.0D; this.y = 0.0D; this.z = 0.0D;
/*     */   }
/*  29 */   public Double3D(Int2D p) { this.x = p.x; this.y = p.y; this.z = 0.0D; } 
/*  30 */   public Double3D(Int2D p, double z) { this.x = p.x; this.y = p.y; this.z = z; } 
/*  31 */   public Double3D(Int3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*     */   public Double3D(MutableInt2D p) {
/*  33 */     this.x = p.x; this.y = p.y; this.z = 0.0D; } 
/*  34 */   public Double3D(MutableInt2D p, double z) { this.x = p.x; this.y = p.y; this.z = z; } 
/*  35 */   public Double3D(MutableInt3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*     */   public Double3D(Double2D p) {
/*  37 */     this.x = p.x; this.y = p.y; this.z = 0.0D; } 
/*  38 */   public Double3D(Double2D p, double z) { this.x = p.x; this.y = p.y; this.z = z; } 
/*  39 */   public Double3D(Double3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*     */   public Double3D(MutableDouble2D p) {
/*  41 */     this.x = p.x; this.y = p.y; this.z = 0.0D; } 
/*  42 */   public Double3D(MutableDouble2D p, double z) { this.x = p.x; this.y = p.y; this.z = z; } 
/*  43 */   public Double3D(MutableDouble3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*  44 */   public Double3D(double x, double y, double z) { this.x = x; this.y = y; this.z = z; } 
/*  45 */   public final double getX() { return this.x; } 
/*  46 */   public final double getY() { return this.y; } 
/*  47 */   public final double getZ() { return this.z; } 
/*  48 */   public String toString() { return "Double3D[" + this.x + "," + this.y + "," + this.z + "]"; } 
/*  49 */   public String toCoordinates() { return "(" + this.x + ", " + this.y + ", " + this.z + ")"; }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  53 */     double x = this.x;
/*  54 */     double y = this.y;
/*  55 */     double z = this.z;
/*     */ 
/*  59 */     if (x == -0.0D) x = 0.0D;
/*  60 */     if (y == -0.0D) y = 0.0D;
/*  61 */     if (z == -0.0D) z = 0.0D;
/*     */ 
/*  64 */     if (((int)x == x) && ((int)y == y) && ((int)z == z))
/*     */     {
/*  68 */       int y_ = (int)y;
/*  69 */       int x_ = (int)x;
/*  70 */       int z_ = (int)z;
/*     */ 
/*  75 */       z_ += (z_ << 15 ^ 0xFFFFFFFF);
/*  76 */       z_ ^= z_ >>> 10;
/*  77 */       z_ += (z_ << 3);
/*  78 */       z_ ^= z_ >>> 6;
/*  79 */       z_ += (z_ << 11 ^ 0xFFFFFFFF);
/*  80 */       z_ ^= z_ >>> 16;
/*     */ 
/*  82 */       z_ ^= y_;
/*  83 */       z_ += 17;
/*     */ 
/*  85 */       z_ += (z_ << 15 ^ 0xFFFFFFFF);
/*  86 */       z_ ^= z_ >>> 10;
/*  87 */       z_ += (z_ << 3);
/*  88 */       z_ ^= z_ >>> 6;
/*  89 */       z_ += (z_ << 11 ^ 0xFFFFFFFF);
/*  90 */       z_ ^= z_ >>> 16;
/*     */ 
/*  94 */       return x_ ^ z_;
/*     */     }
/*     */ 
/* 130 */     long key = Double.doubleToLongBits(z);
/* 131 */     key += (key << 32 ^ 0xFFFFFFFF);
/* 132 */     key ^= key >>> 22;
/* 133 */     key += (key << 13 ^ 0xFFFFFFFF);
/* 134 */     key ^= key >>> 8;
/* 135 */     key += (key << 3);
/* 136 */     key ^= key >>> 15;
/* 137 */     key += (key << 27 ^ 0xFFFFFFFF);
/* 138 */     key ^= key >>> 31;
/*     */ 
/* 140 */     key ^= Double.doubleToLongBits(y);
/* 141 */     key += 17L;
/*     */ 
/* 143 */     key += (key << 32 ^ 0xFFFFFFFF);
/* 144 */     key ^= key >>> 22;
/* 145 */     key += (key << 13 ^ 0xFFFFFFFF);
/* 146 */     key ^= key >>> 8;
/* 147 */     key += (key << 3);
/* 148 */     key ^= key >>> 15;
/* 149 */     key += (key << 27 ^ 0xFFFFFFFF);
/* 150 */     key ^= key >>> 31;
/*     */ 
/* 154 */     key ^= Double.doubleToLongBits(x);
/*     */ 
/* 157 */     return (int)(key ^ key >> 32);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 164 */     if (obj == null) return false;
/* 165 */     if ((obj instanceof Double3D))
/*     */     {
/* 167 */       Double3D other = (Double3D)obj;
/*     */ 
/* 169 */       return ((this.x == other.x) || ((Double.isNaN(this.x)) && (Double.isNaN(other.x)))) && ((this.y == other.y) || ((Double.isNaN(this.y)) && (Double.isNaN(other.y)))) && ((this.z == other.z) || ((Double.isNaN(this.z)) && (Double.isNaN(other.z))));
/*     */     }
/*     */ 
/* 178 */     if ((obj instanceof MutableDouble3D))
/*     */     {
/* 180 */       MutableDouble3D other = (MutableDouble3D)obj;
/*     */ 
/* 182 */       return ((this.x == other.x) || ((Double.isNaN(this.x)) && (Double.isNaN(other.x)))) && ((this.y == other.y) || ((Double.isNaN(this.y)) && (Double.isNaN(other.y)))) && ((this.z == other.z) || ((Double.isNaN(this.z)) && (Double.isNaN(other.z))));
/*     */     }
/*     */ 
/* 191 */     if ((obj instanceof Int3D))
/*     */     {
/* 193 */       Int3D other = (Int3D)obj;
/* 194 */       return (other.x == this.x) && (other.y == this.y) && (other.z == this.z);
/*     */     }
/* 196 */     if ((obj instanceof MutableInt3D))
/*     */     {
/* 198 */       MutableInt3D other = (MutableInt3D)obj;
/* 199 */       return (other.x == this.x) && (other.y == this.y) && (other.z == this.z);
/*     */     }
/* 201 */     return false;
/*     */   }
/*     */ 
/*     */   public double distance(double x, double y, double z)
/*     */   {
/* 208 */     double dx = this.x - x;
/* 209 */     double dy = this.y - y;
/* 210 */     double dz = this.z - z;
/* 211 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distance(Double3D p)
/*     */   {
/* 217 */     double dx = this.x - p.x;
/* 218 */     double dy = this.y - p.y;
/* 219 */     double dz = this.z - p.z;
/* 220 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distance(Int3D p)
/*     */   {
/* 226 */     double dx = this.x - p.x;
/* 227 */     double dy = this.y - p.y;
/* 228 */     double dz = this.z - p.z;
/* 229 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distance(MutableInt3D p)
/*     */   {
/* 235 */     double dx = this.x - p.x;
/* 236 */     double dy = this.y - p.y;
/* 237 */     double dz = this.z - p.z;
/* 238 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distanceSq(double x, double y, double z)
/*     */   {
/* 244 */     double dx = this.x - x;
/* 245 */     double dy = this.y - y;
/* 246 */     double dz = this.z - z;
/* 247 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Double3D p)
/*     */   {
/* 253 */     double dx = this.x - p.x;
/* 254 */     double dy = this.y - p.y;
/* 255 */     double dz = this.z - p.z;
/* 256 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Int3D p)
/*     */   {
/* 262 */     double dx = this.x - p.x;
/* 263 */     double dy = this.y - p.y;
/* 264 */     double dz = this.z - p.z;
/* 265 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public double distanceSq(MutableInt3D p)
/*     */   {
/* 271 */     double dx = this.x - p.x;
/* 272 */     double dy = this.y - p.y;
/* 273 */     double dz = this.z - p.z;
/* 274 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(double x, double y, double z)
/*     */   {
/* 280 */     double dx = Math.abs(this.x - x);
/* 281 */     double dy = Math.abs(this.y - y);
/* 282 */     double dz = Math.abs(this.z - z);
/* 283 */     return dx + dy + dz;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(Double3D p)
/*     */   {
/* 289 */     double dx = Math.abs(this.x - p.x);
/* 290 */     double dy = Math.abs(this.y - p.y);
/* 291 */     double dz = Math.abs(this.z - p.z);
/* 292 */     return dx + dy + dz;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(Int3D p)
/*     */   {
/* 298 */     double dx = Math.abs(this.x - p.x);
/* 299 */     double dy = Math.abs(this.y - p.y);
/* 300 */     double dz = Math.abs(this.z - p.z);
/* 301 */     return dx + dy + dz;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(MutableDouble3D p)
/*     */   {
/* 307 */     double dx = Math.abs(this.x - p.x);
/* 308 */     double dy = Math.abs(this.y - p.y);
/* 309 */     double dz = Math.abs(this.z - p.z);
/* 310 */     return dx + dy + dz;
/*     */   }
/*     */ 
/*     */   public double manhattanDistance(MutableInt3D p)
/*     */   {
/* 316 */     double dx = Math.abs(this.x - p.x);
/* 317 */     double dy = Math.abs(this.y - p.y);
/* 318 */     double dz = Math.abs(this.z - p.z);
/* 319 */     return dx + dy + dz;
/*     */   }
/*     */ 
/*     */   public final Double3D add(Double3D other)
/*     */   {
/* 326 */     return new Double3D(this.x + other.x, this.y + other.y, this.z + other.z);
/*     */   }
/*     */ 
/*     */   public final Double3D subtract(Double3D other)
/*     */   {
/* 333 */     return new Double3D(this.x - other.x, this.y - other.y, this.z - other.z);
/*     */   }
/*     */ 
/*     */   public final double length()
/*     */   {
/* 339 */     return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/*     */   }
/*     */ 
/*     */   public final double lengthSq()
/*     */   {
/* 345 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */ 
/*     */   public final Double3D multiply(double val)
/*     */   {
/* 351 */     return new Double3D(this.x * val, this.y * val, this.z * val);
/*     */   }
/*     */ 
/*     */   public final Double3D resize(double dist)
/*     */   {
/* 359 */     if (dist == 0.0D)
/* 360 */       return new Double3D(0.0D, 0.0D, 0.0D);
/* 361 */     if ((dist == (1.0D / 0.0D)) || (dist == (-1.0D / 0.0D)) || (dist != dist))
/* 362 */       throw new ArithmeticException("Cannot resize to distance " + dist);
/* 363 */     if (((this.x == 0.0D) && (this.y == 0.0D) && (this.z == 0.0D)) || (this.x == (1.0D / 0.0D)) || (this.x == (-1.0D / 0.0D)) || (this.x != this.x) || (this.y == (1.0D / 0.0D)) || (this.y == (-1.0D / 0.0D)) || (this.y != this.y) || (this.z == (1.0D / 0.0D)) || (this.z == (-1.0D / 0.0D)) || (this.z != this.z))
/*     */     {
/* 367 */       throw new ArithmeticException("Cannot resize a vector with infinite or NaN values, or of length 0, except to length 0");
/*     */     }
/* 369 */     double temp = length();
/* 370 */     return new Double3D(this.x * dist / temp, this.y * dist / temp, this.z * dist / temp);
/*     */   }
/*     */ 
/*     */   public final Double3D normalize()
/*     */   {
/* 385 */     return resize(1.0D);
/*     */   }
/*     */ 
/*     */   public final double dot(Double3D other)
/*     */   {
/* 391 */     return other.x * this.x + other.y * this.y + other.z * this.z;
/*     */   }
/*     */ 
/*     */   public final Double3D negate()
/*     */   {
/* 397 */     return new Double3D(-this.x, -this.y, -this.z);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.Double3D
 * JD-Core Version:    0.6.2
 */