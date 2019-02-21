/*    */ package sim.app.asteroids;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.geom.GeneralPath;
/*    */ 
/*    */ public class Fire extends Element
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Fire()
/*    */   {
/* 24 */     GeneralPath gp = new GeneralPath();
/* 25 */     gp.moveTo(-1.0F, 0.0F);
/* 26 */     gp.lineTo(-3.0F, 1.0F);
/* 27 */     gp.lineTo(-5.0F, 0.0F);
/* 28 */     gp.lineTo(-3.0F, -1.0F);
/* 29 */     gp.closePath();
/* 30 */     this.shape = gp;
/*    */   }
/*    */ 
/*    */   public Color getColor() {
/* 34 */     return Color.red;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.asteroids.Fire
 * JD-Core Version:    0.6.2
 */