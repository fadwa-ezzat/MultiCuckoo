/*    */ package sim.portrayal.simple;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.Paint;
/*    */ import java.awt.geom.Ellipse2D.Double;
/*    */ import java.awt.geom.Rectangle2D.Double;
/*    */ import sim.portrayal.DrawInfo2D;
/*    */ 
/*    */ public class OvalPortrayal2D extends AbstractShapePortrayal2D
/*    */ {
/* 20 */   protected double offset = 0.0D;
/*    */ 
/* 38 */   transient Ellipse2D.Double preciseEllipse = new Ellipse2D.Double();
/*    */ 
/*    */   public OvalPortrayal2D()
/*    */   {
/* 22 */     this(Color.gray, 1.0D, true); } 
/* 23 */   public OvalPortrayal2D(Paint paint) { this(paint, 1.0D, true); } 
/* 24 */   public OvalPortrayal2D(double scale) { this(Color.gray, scale, true); } 
/* 25 */   public OvalPortrayal2D(Paint paint, double scale) { this(paint, scale, true); } 
/* 26 */   public OvalPortrayal2D(Paint paint, boolean filled) { this(paint, 1.0D, filled); } 
/* 27 */   public OvalPortrayal2D(double scale, boolean filled) { this(Color.gray, scale, filled); }
/*    */ 
/*    */   public OvalPortrayal2D(Paint paint, double scale, boolean filled)
/*    */   {
/* 31 */     this.paint = paint;
/* 32 */     this.scale = scale;
/* 33 */     this.filled = filled;
/*    */   }
/*    */ 
/*    */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*    */   {
/* 43 */     Rectangle2D.Double draw = info.draw;
/* 44 */     double width = draw.width * this.scale + this.offset;
/* 45 */     double height = draw.height * this.scale + this.offset;
/*    */ 
/* 47 */     graphics.setPaint(this.paint);
/*    */ 
/* 50 */     if (info.precise)
/*    */     {
/* 52 */       if (this.preciseEllipse == null) this.preciseEllipse = new Ellipse2D.Double();
/* 53 */       this.preciseEllipse.setFrame(info.draw.x - width / 2.0D, info.draw.y - height / 2.0D, width, height);
/* 54 */       if (this.filled) graphics.fill(this.preciseEllipse); else
/* 55 */         graphics.draw(this.preciseEllipse);
/* 56 */       return;
/*    */     }
/*    */ 
/* 59 */     int x = (int)(draw.x - width / 2.0D);
/* 60 */     int y = (int)(draw.y - height / 2.0D);
/* 61 */     int w = (int)width;
/* 62 */     int h = (int)height;
/*    */ 
/* 65 */     if (this.filled)
/* 66 */       graphics.fillOval(x, y, w, h);
/*    */     else
/* 68 */       graphics.drawOval(x, y, w, h);
/*    */   }
/*    */ 
/*    */   public boolean hitObject(Object object, DrawInfo2D range)
/*    */   {
/* 74 */     if (this.preciseEllipse == null) this.preciseEllipse = new Ellipse2D.Double();
/* 75 */     double SLOP = 1.0D;
/* 76 */     double width = range.draw.width * this.scale;
/* 77 */     double height = range.draw.height * this.scale;
/* 78 */     this.preciseEllipse.setFrame(range.draw.x - width / 2.0D - 1.0D, range.draw.y - height / 2.0D - 1.0D, width + 2.0D, height + 2.0D);
/* 79 */     return this.preciseEllipse.intersects(range.clip.x, range.clip.y, range.clip.width, range.clip.height);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.simple.OvalPortrayal2D
 * JD-Core Version:    0.6.2
 */