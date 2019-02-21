/*    */ package sim.app.woims;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Paint;
/*    */ import sim.portrayal.simple.OvalPortrayal2D;
/*    */ 
/*    */ public class Obstacle extends OvalPortrayal2D
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 16 */   public static final Paint obstacleColor = new Color(192, 255, 192);
/*    */ 
/*    */   public Obstacle(double diam)
/*    */   {
/* 22 */     super(obstacleColor, diam);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.woims.Obstacle
 * JD-Core Version:    0.6.2
 */