/*    */ package sim.app.pacman;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.font.TextLayout;
/*    */ import java.awt.geom.Rectangle2D;
/*    */ import java.awt.geom.Rectangle2D.Double;
/*    */ import sim.display.SimpleController;
/*    */ import sim.portrayal.DrawInfo2D;
/*    */ import sim.portrayal.FieldPortrayal2D;
/*    */ 
/*    */ public class Overlay extends FieldPortrayal2D
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   PacManWithUI ui;
/*    */   public static final int GUTTER = 32;
/*    */   public static final int BORDER = 8;
/* 24 */   Font font = new Font("SansSerif", 1, 18);
/* 25 */   Color color = new Color(33, 33, 222);
/*    */ 
/* 29 */   int firstTimeScoreY = 0;
/*    */ 
/*    */   public Overlay(PacManWithUI ui)
/*    */   {
/* 27 */     this.ui = ui;
/*    */   }
/*    */ 
/*    */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*    */   {
/* 33 */     PacMan pacman = (PacMan)this.ui.state;
/* 34 */     graphics.setFont(this.font);
/* 35 */     graphics.setColor(Color.white);
/*    */ 
/* 39 */     Rectangle2D bounds = new TextLayout("" + pacman.score, this.font, graphics.getFontRenderContext()).getBounds();
/* 40 */     if (this.firstTimeScoreY == 0) {
/* 41 */       this.firstTimeScoreY = ((int)((32.0D + bounds.getHeight()) / 2.0D));
/*    */     }
/*    */ 
/* 44 */     if (((SimpleController)this.ui.controller).getPlayState() == 2)
/*    */     {
/* 46 */       bounds = new TextLayout("Paused", this.font, graphics.getFontRenderContext()).getBounds();
/* 47 */       graphics.drawString("Paused", (int)((info.clip.width - bounds.getWidth()) / 2.0D), this.firstTimeScoreY);
/*    */     }
/*    */     else
/*    */     {
/* 51 */       graphics.drawString("Deaths: " + pacman.deaths, 8, this.firstTimeScoreY);
/* 52 */       graphics.drawString("Level: " + pacman.level, (int)((info.clip.width - 16.0D) * 1.0D / 3.0D + 8.0D), this.firstTimeScoreY);
/* 53 */       graphics.drawString("Score: " + pacman.score, (int)((info.clip.width - 16.0D) * 2.0D / 3.0D + 8.0D), this.firstTimeScoreY);
/*    */     }
/*    */ 
/* 57 */     String text = "M: MASON   P: Pause   R: Reset   adws / ←→↑↓";
/* 58 */     bounds = new TextLayout(text, this.font, graphics.getFontRenderContext()).getBounds();
/* 59 */     graphics.drawString(text, (int)((info.clip.width - bounds.getWidth()) / 2.0D), (int)(info.clip.height - 32.0D + this.firstTimeScoreY));
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pacman.Overlay
 * JD-Core Version:    0.6.2
 */