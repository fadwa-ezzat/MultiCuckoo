/*    */ package sim.portrayal.network;
/*    */ 
/*    */ import java.awt.geom.Point2D.Double;
/*    */ import java.awt.geom.RectangularShape;
/*    */ import sim.display.GUIState;
/*    */ import sim.portrayal.DrawInfo2D;
/*    */ import sim.portrayal.FieldPortrayal2D;
/*    */ 
/*    */ public class EdgeDrawInfo2D extends DrawInfo2D
/*    */ {
/*    */   public Point2D.Double secondPoint;
/*    */ 
/*    */   public EdgeDrawInfo2D(GUIState state, FieldPortrayal2D fieldPortrayal, RectangularShape draw, RectangularShape clip, Point2D.Double secondPoint)
/*    */   {
/* 24 */     super(state, fieldPortrayal, draw, clip);
/* 25 */     this.secondPoint = secondPoint;
/*    */   }
/*    */ 
/*    */   public EdgeDrawInfo2D(DrawInfo2D other, double translateX, double translateY, Point2D.Double secondPoint)
/*    */   {
/* 30 */     super(other, translateX, translateY);
/* 31 */     this.secondPoint = secondPoint;
/*    */   }
/*    */ 
/*    */   public EdgeDrawInfo2D(DrawInfo2D other, Point2D.Double secondPoint)
/*    */   {
/* 36 */     super(other);
/* 37 */     this.secondPoint = secondPoint;
/*    */   }
/*    */ 
/*    */   public EdgeDrawInfo2D(EdgeDrawInfo2D other)
/*    */   {
/* 42 */     this(other, new Point2D.Double(other.secondPoint.x, other.secondPoint.y));
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 47 */     return "EdgeDrawInfo2D[ Draw: " + this.draw + " Clip: " + this.clip + " Precise: " + this.precise + " Location : " + this.location + " 2nd: " + this.secondPoint + "]";
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.network.EdgeDrawInfo2D
 * JD-Core Version:    0.6.2
 */