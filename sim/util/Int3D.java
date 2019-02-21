/*     */ package sim.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public final class Int3D
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public final int x;
/*     */   public final int y;
/*     */   public final int z;
/*     */ 
/*     */   public Int3D()
/*     */   {
/*  26 */     this.x = 0; this.y = 0; this.z = 0; } 
/*  27 */   public Int3D(int x, int y, int z) { this.x = x; this.y = y; this.z = z; } 
/*     */   public Int3D(Int2D p) {
/*  29 */     this.x = p.x; this.y = p.y; this.z = 0; } 
/*  30 */   public Int3D(Int2D p, int z) { this.x = p.x; this.y = p.y; this.z = z; } 
/*  31 */   public Int3D(MutableInt2D p) { this.x = p.x; this.y = p.y; this.z = 0; } 
/*  32 */   public Int3D(MutableInt2D p, int z) { this.x = p.x; this.y = p.y; this.z = z; } 
/*  33 */   public final int getX() { return this.x; } 
/*  34 */   public final int getY() { return this.y; } 
/*  35 */   public final int getZ() { return this.z; } 
/*  36 */   public String toString() { return "Int3D[" + this.x + "," + this.y + "," + this.z + "]"; } 
/*  37 */   public String toCoordinates() { return "(" + this.x + ", " + this.y + ", " + this.z + ")"; }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  41 */     int z = this.z;
/*     */ 
/*  69 */     z += (z << 15 ^ 0xFFFFFFFF);
/*  70 */     z ^= z >>> 10;
/*  71 */     z += (z << 3);
/*  72 */     z ^= z >>> 6;
/*  73 */     z += (z << 11 ^ 0xFFFFFFFF);
/*  74 */     z ^= z >>> 16;
/*     */ 
/*  76 */     z ^= this.y;
/*  77 */     z += 17;
/*     */ 
/*  79 */     z += (z << 15 ^ 0xFFFFFFFF);
/*  80 */     z ^= z >>> 10;
/*  81 */     z += (z << 3);
/*  82 */     z ^= z >>> 6;
/*  83 */     z += (z << 11 ^ 0xFFFFFFFF);
/*  84 */     z ^= z >>> 16;
/*     */ 
/*  88 */     return this.x ^ z;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  95 */     if (obj == null) return false;
/*  96 */     if ((obj instanceof Int3D))
/*     */     {
/*  98 */       Int3D other = (Int3D)obj;
/*  99 */       return (other.x == this.x) && (other.y == this.y) && (other.z == this.z);
/*     */     }
/* 101 */     if ((obj instanceof MutableInt3D))
/*     */     {
/* 103 */       MutableInt3D other = (MutableInt3D)obj;
/* 104 */       return (other.x == this.x) && (other.y == this.y) && (other.z == this.z);
/*     */     }
/* 106 */     if ((obj instanceof Double3D))
/*     */     {
/* 108 */       Double3D other = (Double3D)obj;
/* 109 */       return (other.x == this.x) && (other.y == this.y) && (other.z == this.z);
/*     */     }
/* 111 */     if ((obj instanceof MutableDouble3D))
/*     */     {
/* 113 */       MutableDouble3D other = (MutableDouble3D)obj;
/* 114 */       return (other.x == this.x) && (other.y == this.y) && (other.z == this.z);
/*     */     }
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   public double distance(double x, double y, double z)
/*     */   {
/* 124 */     double dx = this.x - x;
/* 125 */     double dy = this.y - y;
/* 126 */     double dz = this.z - z;
/* 127 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distance(Double3D p)
/*     */   {
/* 133 */     double dx = this.x - p.x;
/* 134 */     double dy = this.y - p.y;
/* 135 */     double dz = this.z - p.z;
/* 136 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distance(MutableInt3D p)
/*     */   {
/* 142 */     double dx = this.x - p.x;
/* 143 */     double dy = this.y - p.y;
/* 144 */     double dz = this.z - p.z;
/* 145 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distance(Int3D p)
/*     */   {
/* 151 */     double dx = this.x - p.x;
/* 152 */     double dy = this.y - p.y;
/* 153 */     double dz = this.z - p.z;
/* 154 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distanceSq(double x, double y, double z)
/*     */   {
/* 160 */     double dx = this.x - x;
/* 161 */     double dy = this.y - y;
/* 162 */     double dz = this.z - z;
/* 163 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Double3D p)
/*     */   {
/* 169 */     double dx = this.x - p.x;
/* 170 */     double dy = this.y - p.y;
/* 171 */     double dz = this.z - p.z;
/* 172 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public double distanceSq(MutableInt3D p)
/*     */   {
/* 178 */     double dx = this.x - p.x;
/* 179 */     double dy = this.y - p.y;
/* 180 */     double dz = this.z - p.z;
/* 181 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Int3D p)
/*     */   {
/* 187 */     double dx = this.x - p.x;
/* 188 */     double dy = this.y - p.y;
/* 189 */     double dz = this.z - p.z;
/* 190 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public long manhattanDistance(int x, int y, int z)
/*     */   {
/* 196 */     return Math.abs(this.x - x) + Math.abs(this.y - y) + Math.abs(this.z - z);
/*     */   }
/*     */ 
/*     */   public long manhattanDistance(MutableInt3D p)
/*     */   {
/* 202 */     return Math.abs(this.x - p.x) + Math.abs(this.y - p.y) + Math.abs(this.z - p.z);
/*     */   }
/*     */ 
/*     */   public long manhattanDistance(Int3D p)
/*     */   {
/* 208 */     return Math.abs(this.x - p.x) + Math.abs(this.y - p.y) + Math.abs(this.z - p.z);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.Int3D
 * JD-Core Version:    0.6.2
 */