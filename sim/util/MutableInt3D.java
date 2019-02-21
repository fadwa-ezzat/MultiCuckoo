/*     */ package sim.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public final class MutableInt3D
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */ 
/*     */   public MutableInt3D()
/*     */   {
/*  28 */     this.x = 0; this.y = 0; this.z = 0; } 
/*  29 */   public MutableInt3D(int x, int y, int z) { this.x = x; this.y = y; this.z = z; } 
/*     */   public MutableInt3D(Int2D p) {
/*  31 */     this.x = p.x; this.y = p.y; this.z = 0; } 
/*  32 */   public MutableInt3D(Int2D p, int z) { this.x = p.x; this.y = p.y; this.z = z; } 
/*  33 */   public MutableInt3D(Int3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*  34 */   public MutableInt3D(MutableInt2D p) { this.x = p.x; this.y = p.y; this.z = 0; } 
/*  35 */   public MutableInt3D(MutableInt2D p, int z) { this.x = p.x; this.y = p.y; this.z = z; } 
/*  36 */   public final int getX() { return this.x; } 
/*  37 */   public final int getY() { return this.y; } 
/*  38 */   public final int getZ() { return this.z; } 
/*  39 */   public final void setX(int val) { this.x = val; } 
/*  40 */   public final void setY(int val) { this.y = val; } 
/*  41 */   public final void setZ(int val) { this.z = val; } 
/*  42 */   public void setTo(int x, int y, int z) { this.x = x; this.y = y; this.z = z; } 
/*  43 */   public void setTo(Int3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*  44 */   public void setTo(MutableInt3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*  46 */   /** @deprecated */
/*     */   public void setLocation(int x, int y, int z) { this.x = x; this.y = y; this.z = z; } 
/*  48 */   /** @deprecated */
/*     */   public void setLocation(Int3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*  50 */   /** @deprecated */
/*     */   public void setLocation(MutableInt3D p) { this.x = p.x; this.y = p.y; this.z = p.z; } 
/*  51 */   public String toString() { return "MutableInt3D[" + this.x + "," + this.y + "," + this.z + "]"; } 
/*  52 */   public String toCoordinates() { return "(" + this.x + ", " + this.y + ", " + this.z + ")"; }
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try {
/*  57 */       return super.clone();
/*     */     }
/*     */     catch (CloneNotSupportedException e) {
/*     */     }
/*  61 */     return null;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  67 */     int z = this.z;
/*     */ 
/*  95 */     z += (z << 15 ^ 0xFFFFFFFF);
/*  96 */     z ^= z >>> 10;
/*  97 */     z += (z << 3);
/*  98 */     z ^= z >>> 6;
/*  99 */     z += (z << 11 ^ 0xFFFFFFFF);
/* 100 */     z ^= z >>> 16;
/*     */ 
/* 102 */     z ^= this.y;
/* 103 */     z += 17;
/*     */ 
/* 105 */     z += (z << 15 ^ 0xFFFFFFFF);
/* 106 */     z ^= z >>> 10;
/* 107 */     z += (z << 3);
/* 108 */     z ^= z >>> 6;
/* 109 */     z += (z << 11 ^ 0xFFFFFFFF);
/* 110 */     z ^= z >>> 16;
/*     */ 
/* 114 */     return this.x ^ z;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 121 */     if (obj == null) return false;
/* 122 */     if ((obj instanceof Int3D))
/*     */     {
/* 124 */       Int3D other = (Int3D)obj;
/* 125 */       return (other.x == this.x) && (other.y == this.y) && (other.z == this.z);
/*     */     }
/* 127 */     if ((obj instanceof MutableInt3D))
/*     */     {
/* 129 */       MutableInt3D other = (MutableInt3D)obj;
/* 130 */       return (other.x == this.x) && (other.y == this.y) && (other.z == this.z);
/*     */     }
/* 132 */     if ((obj instanceof Double3D))
/*     */     {
/* 134 */       Double3D other = (Double3D)obj;
/* 135 */       return (other.x == this.x) && (other.y == this.y) && (other.z == this.z);
/*     */     }
/* 137 */     if ((obj instanceof MutableDouble3D))
/*     */     {
/* 139 */       MutableDouble3D other = (MutableDouble3D)obj;
/* 140 */       return (other.x == this.x) && (other.y == this.y) && (other.z == this.z);
/*     */     }
/* 142 */     return false;
/*     */   }
/*     */ 
/*     */   public double distance(double x, double y, double z)
/*     */   {
/* 149 */     double dx = this.x - x;
/* 150 */     double dy = this.y - y;
/* 151 */     double dz = this.z - z;
/* 152 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distance(Double3D p)
/*     */   {
/* 158 */     double dx = this.x - p.x;
/* 159 */     double dy = this.y - p.y;
/* 160 */     double dz = this.z - p.z;
/* 161 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distance(MutableInt3D p)
/*     */   {
/* 167 */     double dx = this.x - p.x;
/* 168 */     double dy = this.y - p.y;
/* 169 */     double dz = this.z - p.z;
/* 170 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distance(Int3D p)
/*     */   {
/* 176 */     double dx = this.x - p.x;
/* 177 */     double dy = this.y - p.y;
/* 178 */     double dz = this.z - p.z;
/* 179 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   public double distanceSq(double x, double y, double z)
/*     */   {
/* 185 */     double dx = this.x - x;
/* 186 */     double dy = this.y - y;
/* 187 */     double dz = this.z - z;
/* 188 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Double3D p)
/*     */   {
/* 194 */     double dx = this.x - p.x;
/* 195 */     double dy = this.y - p.y;
/* 196 */     double dz = this.z - p.z;
/* 197 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public double distanceSq(MutableInt3D p)
/*     */   {
/* 203 */     double dx = this.x - p.x;
/* 204 */     double dy = this.y - p.y;
/* 205 */     double dz = this.z - p.z;
/* 206 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Int3D p)
/*     */   {
/* 212 */     double dx = this.x - p.x;
/* 213 */     double dy = this.y - p.y;
/* 214 */     double dz = this.z - p.z;
/* 215 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */   public long manhattanDistance(int x, int y, int z)
/*     */   {
/* 221 */     return Math.abs(this.x - x) + Math.abs(this.y - y) + Math.abs(this.z - z);
/*     */   }
/*     */ 
/*     */   public long manhattanDistance(MutableInt3D p)
/*     */   {
/* 227 */     return Math.abs(this.x - p.x) + Math.abs(this.y - p.y) + Math.abs(this.z - p.z);
/*     */   }
/*     */ 
/*     */   public long manhattanDistance(Int3D p)
/*     */   {
/* 233 */     return Math.abs(this.x - p.x) + Math.abs(this.y - p.y) + Math.abs(this.z - p.z);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.MutableInt3D
 * JD-Core Version:    0.6.2
 */