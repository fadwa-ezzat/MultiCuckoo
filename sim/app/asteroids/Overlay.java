/*    */ package sim.app.asteroids;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.Shape;
/*    */ import java.awt.font.TextLayout;
/*    */ import java.awt.geom.AffineTransform;
/*    */ import java.awt.geom.Rectangle2D;
/*    */ import java.awt.geom.Rectangle2D.Double;
/*    */ import sim.display.SimpleController;
/*    */ import sim.portrayal.DrawInfo2D;
/*    */ import sim.portrayal.FieldPortrayal2D;
/*    */ 
/*    */ public class Overlay extends FieldPortrayal2D
/*    */ {
/*    */   AsteroidsWithUI ui;
/*    */   public static final int GUTTER = 48;
/*    */   public static final int BORDER = 8;
/*    */   public static final int FONTSIZE = 20;
/* 23 */   Font font = new Font("SansSerif", 1, 20);
/* 24 */   Color color = new Color(255, 255, 255, 64);
/*    */ 
/* 28 */   int firstTimeScoreY = 0;
/*    */ 
/*    */   public Overlay(AsteroidsWithUI ui)
/*    */   {
/* 26 */     this.ui = ui;
/*    */   }
/*    */ 
/*    */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*    */   {
/* 32 */     Asteroids asteroids = (Asteroids)this.ui.state;
/* 33 */     graphics.setFont(this.font);
/*    */ 
/* 37 */     Rectangle2D bounds = new TextLayout("" + asteroids.score, this.font, graphics.getFontRenderContext()).getBounds();
/* 38 */     if (this.firstTimeScoreY == 0) {
/* 39 */       this.firstTimeScoreY = ((int)((48.0D + bounds.getHeight()) / 2.0D));
/*    */     }
/*    */ 
/* 42 */     if (((SimpleController)this.ui.controller).getPlayState() == 2)
/*    */     {
/* 44 */       bounds = new TextLayout("Paused", this.font, graphics.getFontRenderContext()).getBounds();
/* 45 */       graphics.setColor(Color.white);
/* 46 */       graphics.drawString("Paused", (int)((info.clip.width - bounds.getWidth()) / 2.0D), (int)((info.clip.height - bounds.getHeight()) / 2.0D));
/*    */     }
/*    */ 
/* 50 */     graphics.setColor(this.color);
/* 51 */     String text = "Deaths: " + asteroids.deaths;
/* 52 */     drawOutline(graphics, text, 8.0D, this.firstTimeScoreY);
/* 53 */     text = "Level: " + asteroids.level;
/* 54 */     drawOutline(graphics, text, 8.0D, this.firstTimeScoreY + 30.0D);
/* 55 */     text = "Score: " + asteroids.score;
/* 56 */     drawOutline(graphics, text, 8.0D, this.firstTimeScoreY + 60);
/*    */ 
/* 59 */     text = "M: MASON";
/* 60 */     drawOutline(graphics, text, 8.0D, info.clip.height - 48.0D + this.firstTimeScoreY - 90.0D);
/* 61 */     text = "P: Pause";
/* 62 */     drawOutline(graphics, text, 8.0D, info.clip.height - 48.0D + this.firstTimeScoreY - 60.0D);
/* 63 */     text = "R: Reset";
/* 64 */     drawOutline(graphics, text, 8.0D, info.clip.height - 48.0D + this.firstTimeScoreY - 30.0D);
/* 65 */     text = "←→↑↓ space";
/* 66 */     drawOutline(graphics, text, 8.0D, info.clip.height - 48.0D + this.firstTimeScoreY);
/*    */   }
/*    */ 
/*    */   public void drawOutline(Graphics2D graphics, String text, double x, double y)
/*    */   {
/* 73 */     TextLayout textlo = new TextLayout(text, this.font, graphics.getFontRenderContext());
/* 74 */     Shape outline = textlo.getOutline(null);
/* 75 */     AffineTransform transform = graphics.getTransform();
/* 76 */     AffineTransform oldTransform = graphics.getTransform();
/* 77 */     transform.translate(x, y);
/* 78 */     graphics.transform(transform);
/* 79 */     graphics.draw(outline);
/* 80 */     graphics.setTransform(oldTransform);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.asteroids.Overlay
 * JD-Core Version:    0.6.2
 */