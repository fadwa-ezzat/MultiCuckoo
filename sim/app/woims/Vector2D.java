/*    */ package sim.app.woims;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public class Vector2D
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public double x;
/*    */   public double y;
/*    */ 
/*    */   public Vector2D(double x, double y)
/*    */   {
/* 20 */     this.x = x;
/* 21 */     this.y = y;
/*    */   }
/*    */ 
/*    */   public Vector2D(Double2D d)
/*    */   {
/* 26 */     this.x = d.x;
/* 27 */     this.y = d.y;
/*    */   }
/*    */ 
/*    */   public final Vector2D add(Vector2D b)
/*    */   {
/* 32 */     return new Vector2D(this.x + b.x, this.y + b.y);
/*    */   }
/*    */ 
/*    */   public final Vector2D add(Double2D b)
/*    */   {
/* 37 */     return new Vector2D(this.x + b.x, this.y + b.y);
/*    */   }
/*    */ 
/*    */   public final Vector2D subtract(Vector2D b)
/*    */   {
/* 42 */     return new Vector2D(this.x - b.x, this.y - b.y);
/*    */   }
/*    */ 
/*    */   public final Vector2D subtract(Double2D b)
/*    */   {
/* 47 */     return new Vector2D(this.x - b.x, this.y - b.y);
/*    */   }
/*    */ 
/*    */   public final Vector2D amplify(double alpha)
/*    */   {
/* 52 */     return new Vector2D(this.x * alpha, this.y * alpha);
/*    */   }
/*    */ 
/*    */   public final Vector2D normalize()
/*    */   {
/* 57 */     if ((this.x != 0.0D) || (this.y != 0.0D))
/*    */     {
/* 59 */       double temp = Math.sqrt(this.x * this.x + this.y * this.y);
/* 60 */       return new Vector2D(this.x / temp, this.y / temp);
/*    */     }
/*    */ 
/* 63 */     return new Vector2D(0.0D, 0.0D);
/*    */   }
/*    */ 
/*    */   public final double length()
/*    */   {
/* 68 */     return Math.sqrt(this.x * this.x + this.y * this.y);
/*    */   }
/*    */ 
/*    */   public final Vector2D setLength(double dist)
/*    */   {
/* 73 */     if (dist == 0.0D)
/* 74 */       return new Vector2D(0.0D, 0.0D);
/* 75 */     if ((this.x == 0.0D) && (this.y == 0.0D))
/* 76 */       return new Vector2D(0.0D, 0.0D);
/* 77 */     double temp = Math.sqrt(this.x * this.x + this.y * this.y);
/* 78 */     return new Vector2D(this.x * dist / temp, this.y * dist / temp);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.woims.Vector2D
 * JD-Core Version:    0.6.2
 */