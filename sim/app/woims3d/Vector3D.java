/*    */ package sim.app.woims3d;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import sim.util.Double3D;
/*    */ 
/*    */ public class Vector3D
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public double x;
/*    */   public double y;
/*    */   public double z;
/*    */ 
/*    */   public Vector3D(double x, double y, double z)
/*    */   {
/* 21 */     this.x = x;
/* 22 */     this.y = y;
/* 23 */     this.z = z;
/*    */   }
/*    */ 
/*    */   public Vector3D(Double3D d)
/*    */   {
/* 28 */     this.x = d.x;
/* 29 */     this.y = d.y;
/* 30 */     this.z = d.z;
/*    */   }
/*    */ 
/*    */   public final Vector3D add(Vector3D b)
/*    */   {
/* 35 */     return new Vector3D(this.x + b.x, this.y + b.y, this.z + b.z);
/*    */   }
/*    */ 
/*    */   public final Vector3D add(Double3D b)
/*    */   {
/* 40 */     return new Vector3D(this.x + b.x, this.y + b.y, this.z + b.z);
/*    */   }
/*    */ 
/*    */   public final Vector3D subtract(Vector3D b)
/*    */   {
/* 45 */     return new Vector3D(this.x - b.x, this.y - b.y, this.z - b.z);
/*    */   }
/*    */ 
/*    */   public final Vector3D subtract(Double3D b)
/*    */   {
/* 50 */     return new Vector3D(this.x - b.x, this.y - b.y, this.z - b.z);
/*    */   }
/*    */ 
/*    */   public final Vector3D amplify(double alpha)
/*    */   {
/* 55 */     return new Vector3D(this.x * alpha, this.y * alpha, this.z * alpha);
/*    */   }
/*    */ 
/*    */   public final Vector3D normalize()
/*    */   {
/* 60 */     if ((this.x != 0.0D) || (this.y != 0.0D) || (this.z != 0.0D))
/*    */     {
/* 62 */       double temp = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/* 63 */       return new Vector3D(this.x / temp, this.y / temp, this.z / temp);
/*    */     }
/*    */ 
/* 66 */     return new Vector3D(0.0D, 0.0D, 0.0D);
/*    */   }
/*    */ 
/*    */   public final double length()
/*    */   {
/* 71 */     return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/*    */   }
/*    */ 
/*    */   public final Vector3D setLength(double dist)
/*    */   {
/* 76 */     if (dist == 0.0D)
/* 77 */       return new Vector3D(0.0D, 0.0D, 0.0D);
/* 78 */     if ((this.x == 0.0D) && (this.y == 0.0D) && (this.z == 0.0D))
/* 79 */       return new Vector3D(0.0D, 0.0D, 0.0D);
/* 80 */     double temp = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/* 81 */     return new Vector3D(this.x * dist / temp, this.y * dist / temp, this.z * dist / temp);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.woims3d.Vector3D
 * JD-Core Version:    0.6.2
 */