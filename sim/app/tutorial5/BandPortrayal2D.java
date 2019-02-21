/*    */ package sim.app.tutorial5;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.FontMetrics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.geom.Point2D.Double;
/*    */ import java.awt.geom.Rectangle2D.Double;
/*    */ import java.text.NumberFormat;
/*    */ import sim.field.network.Edge;
/*    */ import sim.portrayal.DrawInfo2D;
/*    */ import sim.portrayal.network.EdgeDrawInfo2D;
/*    */ import sim.portrayal.network.SimpleEdgePortrayal2D;
/*    */ 
/*    */ public class BandPortrayal2D extends SimpleEdgePortrayal2D
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   NumberFormat strengthFormat;
/*    */ 
/*    */   public BandPortrayal2D()
/*    */   {
/* 21 */     this.strengthFormat = NumberFormat.getInstance();
/* 22 */     this.strengthFormat.setMinimumIntegerDigits(1);
/* 23 */     this.strengthFormat.setMaximumFractionDigits(2);
/*    */   }
/*    */ 
/*    */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*    */   {
/* 29 */     EdgeDrawInfo2D ei = (EdgeDrawInfo2D)info;
/*    */ 
/* 31 */     Edge e = (Edge)object;
/*    */ 
/* 34 */     int startX = (int)ei.draw.x;
/* 35 */     int startY = (int)ei.draw.y;
/* 36 */     int endX = (int)ei.secondPoint.x;
/* 37 */     int endY = (int)ei.secondPoint.y;
/* 38 */     int midX = (int)((ei.draw.x + ei.secondPoint.x) / 2.0D);
/* 39 */     int midY = (int)((ei.draw.y + ei.secondPoint.y) / 2.0D);
/*    */ 
/* 42 */     graphics.setColor(Color.black);
/* 43 */     graphics.drawLine(startX, startY, endX, endY);
/*    */ 
/* 46 */     graphics.setColor(Color.blue);
/* 47 */     graphics.setFont(this.labelFont);
/* 48 */     String information = this.strengthFormat.format(((Band)e.info).strength);
/* 49 */     int width = graphics.getFontMetrics().stringWidth(information);
/* 50 */     graphics.drawString(information, midX - width / 2, midY);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial5.BandPortrayal2D
 * JD-Core Version:    0.6.2
 */