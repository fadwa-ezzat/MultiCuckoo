/*     */ package sim.util;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public final class Int2D
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public final int x;
/*     */   public final int y;
/*     */ 
/*     */   public Int2D()
/*     */   {
/*  25 */     this.x = 0; this.y = 0; } 
/*  26 */   public Int2D(Point p) { this.x = p.x; this.y = p.y; } 
/*  27 */   public Int2D(MutableInt2D p) { this.x = p.x; this.y = p.y; } 
/*  28 */   public Int2D(int x, int y) { this.x = x; this.y = y; } 
/*  29 */   public final int getX() { return this.x; } 
/*  30 */   public final int getY() { return this.y; } 
/*  31 */   public Point2D.Double toPoint2D() { return new Point2D.Double(this.x, this.y); } 
/*  32 */   public Point toPoint() { return new Point(this.x, this.y); } 
/*  33 */   public String toString() { return "Int2D[" + this.x + "," + this.y + "]"; } 
/*  34 */   public String toCoordinates() { return "(" + this.x + ", " + this.y + ")"; }
/*     */ 
/*     */ 
/*     */   public final int hashCode()
/*     */   {
/*  39 */     int y = this.y;
/*     */ 
/*  68 */     y += (y << 15 ^ 0xFFFFFFFF);
/*  69 */     y ^= y >>> 10;
/*  70 */     y += (y << 3);
/*  71 */     y ^= y >>> 6;
/*  72 */     y += (y << 11 ^ 0xFFFFFFFF);
/*  73 */     y ^= y >>> 16;
/*     */ 
/*  77 */     return this.x ^ y;
/*     */   }
/*     */ 
/*     */   public final boolean equals(Object obj)
/*     */   {
/*  85 */     if (obj == null) return false;
/*  86 */     if ((obj instanceof Int2D))
/*     */     {
/*  88 */       Int2D other = (Int2D)obj;
/*  89 */       return (other.x == this.x) && (other.y == this.y);
/*     */     }
/*  91 */     if ((obj instanceof MutableInt2D))
/*     */     {
/*  93 */       MutableInt2D other = (MutableInt2D)obj;
/*  94 */       return (other.x == this.x) && (other.y == this.y);
/*     */     }
/*  96 */     if ((obj instanceof Double2D))
/*     */     {
/*  98 */       Double2D other = (Double2D)obj;
/*  99 */       return (other.x == this.x) && (other.y == this.y);
/*     */     }
/* 101 */     if ((obj instanceof MutableDouble2D))
/*     */     {
/* 103 */       MutableDouble2D other = (MutableDouble2D)obj;
/* 104 */       return (other.x == this.x) && (other.y == this.y);
/*     */     }
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */   public double distance(double x, double y)
/*     */   {
/* 113 */     double dx = this.x - x;
/* 114 */     double dy = this.y - y;
/* 115 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(Double2D p)
/*     */   {
/* 121 */     double dx = this.x - p.x;
/* 122 */     double dy = this.y - p.y;
/* 123 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(MutableInt2D p)
/*     */   {
/* 129 */     double dx = this.x - p.x;
/* 130 */     double dy = this.y - p.y;
/* 131 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(Int2D p)
/*     */   {
/* 137 */     double dx = this.x - p.x;
/* 138 */     double dy = this.y - p.y;
/* 139 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(Point2D p)
/*     */   {
/* 145 */     double dx = this.x - p.getX();
/* 146 */     double dy = this.y - p.getY();
/* 147 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distanceSq(double x, double y)
/*     */   {
/* 153 */     double dx = this.x - x;
/* 154 */     double dy = this.y - y;
/* 155 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Double2D p)
/*     */   {
/* 161 */     double dx = this.x - p.x;
/* 162 */     double dy = this.y - p.y;
/* 163 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Point2D p)
/*     */   {
/* 169 */     double dx = this.x - p.getX();
/* 170 */     double dy = this.y - p.getY();
/* 171 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(MutableInt2D p)
/*     */   {
/* 177 */     double dx = this.x - p.x;
/* 178 */     double dy = this.y - p.y;
/* 179 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Int2D p)
/*     */   {
/* 185 */     double dx = this.x - p.x;
/* 186 */     double dy = this.y - p.y;
/* 187 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public long manhattanDistance(int x, int y)
/*     */   {
/* 193 */     return Math.abs(this.x - x) + Math.abs(this.y - y);
/*     */   }
/*     */ 
/*     */   public long manhattanDistance(MutableInt2D p)
/*     */   {
/* 199 */     return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
/*     */   }
/*     */ 
/*     */   public long manhattanDistance(Int2D p)
/*     */   {
/* 205 */     return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
/*     */   }
/*     */ 
/*     */   public long manhattanDistance(Point p)
/*     */   {
/* 211 */     return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.Int2D
 * JD-Core Version:    0.6.2
 */