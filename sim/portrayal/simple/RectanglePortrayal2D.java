/*    */ package sim.portrayal.simple;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.Paint;
/*    */ import java.awt.geom.Rectangle2D.Double;
/*    */ import sim.portrayal.DrawInfo2D;
/*    */ 
/*    */ public class RectanglePortrayal2D extends AbstractShapePortrayal2D
/*    */ {
/* 43 */   transient Rectangle2D.Double preciseRectangle = new Rectangle2D.Double();
/*    */ 
/*    */   public RectanglePortrayal2D()
/*    */   {
/* 19 */     this(Color.gray, 1.0D, true); } 
/* 20 */   public RectanglePortrayal2D(Paint paint) { this(paint, 1.0D, true); } 
/* 21 */   public RectanglePortrayal2D(double scale) { this(Color.gray, scale, true); } 
/* 22 */   public RectanglePortrayal2D(Paint paint, double scale) { this(paint, scale, true); } 
/* 23 */   public RectanglePortrayal2D(Paint paint, boolean filled) { this(paint, 1.0D, filled); } 
/* 24 */   public RectanglePortrayal2D(double scale, boolean filled) { this(Color.gray, scale, filled); }
/*    */ 
/*    */   public RectanglePortrayal2D(Paint paint, double scale, boolean filled)
/*    */   {
/* 28 */     this.paint = paint;
/* 29 */     this.scale = scale;
/* 30 */     this.filled = filled;
/*    */   }
/*    */ 
/*    */   public boolean hitObject(Object object, DrawInfo2D range)
/*    */   {
/* 36 */     double width = range.draw.width * this.scale;
/* 37 */     double height = range.draw.height * this.scale;
/* 38 */     return range.clip.intersects(range.draw.x - width / 2.0D, range.draw.y - height / 2.0D, width, height);
/*    */   }
/*    */ 
/*    */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*    */   {
/* 48 */     Rectangle2D.Double draw = info.draw;
/* 49 */     double width = draw.width * this.scale;
/* 50 */     double height = draw.height * this.scale;
/*    */ 
/* 52 */     graphics.setPaint(this.paint);
/*    */ 
/* 55 */     if (info.precise)
/*    */     {
/* 57 */       if (this.preciseRectangle == null) this.preciseRectangle = new Rectangle2D.Double();
/* 58 */       this.preciseRectangle.setFrame(info.draw.x - width / 2.0D, info.draw.y - height / 2.0D, width, height);
/* 59 */       if (this.filled) graphics.fill(this.preciseRectangle); else
/* 60 */         graphics.draw(this.preciseRectangle);
/* 61 */       return;
/*    */     }
/*    */ 
/* 64 */     int x = (int)(draw.x - width / 2.0D);
/* 65 */     int y = (int)(draw.y - height / 2.0D);
/* 66 */     int w = (int)width;
/* 67 */     int h = (int)height;
/*    */ 
/* 70 */     if (this.filled)
/* 71 */       graphics.fillRect(x, y, w, h);
/*    */     else
/* 73 */       graphics.drawRect(x, y, w, h);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.simple.RectanglePortrayal2D
 * JD-Core Version:    0.6.2
 */