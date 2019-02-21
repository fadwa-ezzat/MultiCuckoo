/*     */ package sim.util;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MutableInt2D
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public int x;
/*     */   public int y;
/*     */ 
/*     */   public MutableInt2D()
/*     */   {
/*  26 */     this.x = 0; this.y = 0; } 
/*  27 */   public MutableInt2D(Point p) { this.x = p.x; this.y = p.y; } 
/*  28 */   public MutableInt2D(Int2D p) { this.x = p.x; this.y = p.y; } 
/*  29 */   public MutableInt2D(int x, int y) { this.x = x; this.y = y; } 
/*  30 */   public final int getX() { return this.x; } 
/*  31 */   public final int getY() { return this.y; } 
/*  32 */   public final void setX(int val) { this.x = val; } 
/*  33 */   public final void setY(int val) { this.y = val; } 
/*  34 */   public void setTo(int x, int y) { this.x = x; this.y = y; } 
/*  35 */   public void setTo(Point p) { this.x = p.x; this.y = p.y; } 
/*  36 */   public void setTo(Int2D p) { this.x = p.x; this.y = p.y; } 
/*  37 */   public void setTo(MutableInt2D p) { this.x = p.x; this.y = p.y; } 
/*  39 */   /** @deprecated */
/*     */   public void setLocation(int x, int y) { this.x = x; this.y = y; } 
/*  41 */   /** @deprecated */
/*     */   public void setLocation(Point p) { this.x = p.x; this.y = p.y; } 
/*  43 */   /** @deprecated */
/*     */   public void setLocation(Int2D p) { this.x = p.x; this.y = p.y; } 
/*  45 */   /** @deprecated */
/*     */   public void setLocation(MutableInt2D p) { this.x = p.x; this.y = p.y; } 
/*  46 */   public Point2D.Double toPoint2D() { return new Point2D.Double(this.x, this.y); } 
/*  47 */   public Point toPoint() { return new Point(this.x, this.y); } 
/*  48 */   public String toString() { return "MutableInt2D[" + this.x + "," + this.y + "]"; } 
/*  49 */   public String toCoordinates() { return "(" + this.x + ", " + this.y + ")"; }
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try {
/*  54 */       return super.clone();
/*     */     }
/*     */     catch (CloneNotSupportedException e) {
/*     */     }
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */   public final int hashCode()
/*     */   {
/*  64 */     int y = this.y;
/*     */ 
/*  93 */     y += (y << 15 ^ 0xFFFFFFFF);
/*  94 */     y ^= y >>> 10;
/*  95 */     y += (y << 3);
/*  96 */     y ^= y >>> 6;
/*  97 */     y += (y << 11 ^ 0xFFFFFFFF);
/*  98 */     y ^= y >>> 16;
/*     */ 
/* 102 */     return this.x ^ y;
/*     */   }
/*     */ 
/*     */   public final boolean equals(Object obj)
/*     */   {
/* 109 */     if (obj == null) return false;
/* 110 */     if ((obj instanceof Int2D))
/*     */     {
/* 112 */       Int2D other = (Int2D)obj;
/* 113 */       return (other.x == this.x) && (other.y == this.y);
/*     */     }
/* 115 */     if ((obj instanceof MutableInt2D))
/*     */     {
/* 117 */       MutableInt2D other = (MutableInt2D)obj;
/* 118 */       return (other.x == this.x) && (other.y == this.y);
/*     */     }
/* 120 */     if ((obj instanceof Double2D))
/*     */     {
/* 122 */       Double2D other = (Double2D)obj;
/* 123 */       return (other.x == this.x) && (other.y == this.y);
/*     */     }
/* 125 */     if ((obj instanceof MutableDouble2D))
/*     */     {
/* 127 */       MutableDouble2D other = (MutableDouble2D)obj;
/* 128 */       return (other.x == this.x) && (other.y == this.y);
/*     */     }
/* 130 */     return false;
/*     */   }
/*     */ 
/*     */   public double distance(double x, double y)
/*     */   {
/* 137 */     double dx = this.x - x;
/* 138 */     double dy = this.y - y;
/* 139 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(Double2D p)
/*     */   {
/* 145 */     double dx = this.x - p.x;
/* 146 */     double dy = this.y - p.y;
/* 147 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(MutableInt2D p)
/*     */   {
/* 153 */     double dx = this.x - p.x;
/* 154 */     double dy = this.y - p.y;
/* 155 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(Int2D p)
/*     */   {
/* 161 */     double dx = this.x - p.x;
/* 162 */     double dy = this.y - p.y;
/* 163 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distance(Point2D p)
/*     */   {
/* 169 */     double dx = this.x - p.getX();
/* 170 */     double dy = this.y - p.getY();
/* 171 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   public double distanceSq(double x, double y)
/*     */   {
/* 177 */     double dx = this.x - x;
/* 178 */     double dy = this.y - y;
/* 179 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Double2D p)
/*     */   {
/* 185 */     double dx = this.x - p.x;
/* 186 */     double dy = this.y - p.y;
/* 187 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Point2D p)
/*     */   {
/* 193 */     double dx = this.x - p.getX();
/* 194 */     double dy = this.y - p.getY();
/* 195 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(MutableInt2D p)
/*     */   {
/* 201 */     double dx = this.x - p.x;
/* 202 */     double dy = this.y - p.y;
/* 203 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Int2D p)
/*     */   {
/* 209 */     double dx = this.x - p.x;
/* 210 */     double dy = this.y - p.y;
/* 211 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   public long manhattanDistance(int x, int y)
/*     */   {
/* 217 */     return Math.abs(this.x - x) + Math.abs(this.y - y);
/*     */   }
/*     */ 
/*     */   public long manhattanDistance(MutableInt2D p)
/*     */   {
/* 223 */     return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
/*     */   }
/*     */ 
/*     */   public long manhattanDistance(Int2D p)
/*     */   {
/* 229 */     return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
/*     */   }
/*     */ 
/*     */   public long manhattanDistance(Point p)
/*     */   {
/* 235 */     return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.MutableInt2D
 * JD-Core Version:    0.6.2
 */