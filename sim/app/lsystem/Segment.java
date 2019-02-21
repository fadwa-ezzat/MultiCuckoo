/*    */ package sim.app.lsystem;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.geom.Rectangle2D.Double;
/*    */ import sim.portrayal.DrawInfo2D;
/*    */ import sim.portrayal.SimplePortrayal2D;
/*    */ import sim.util.Bag;
/*    */ 
/*    */ public class Segment extends SimplePortrayal2D
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public double x;
/*    */   public double y;
/*    */   public double x2;
/*    */   public double y2;
/*    */ 
/*    */   public Segment(double x, double y, double dist, double theta)
/*    */   {
/* 21 */     this.x = x;
/* 22 */     this.y = y;
/* 23 */     this.x2 = (Math.cos(theta) * dist);
/* 24 */     this.y2 = (Math.sin(theta) * dist);
/*    */   }
/*    */ 
/*    */   public void draw(Object object, Graphics2D g, DrawInfo2D info)
/*    */   {
/* 29 */     g.setColor(new Color(0, 127, 0));
/* 30 */     g.drawLine((int)info.draw.x, (int)info.draw.y, (int)info.draw.x + (int)(info.draw.width * this.x2), (int)info.draw.y + (int)(info.draw.height * this.y2));
/*    */   }
/*    */ 
/*    */   public void hitObjects(DrawInfo2D range, Bag putInHere)
/*    */   {
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.lsystem.Segment
 * JD-Core Version:    0.6.2
 */