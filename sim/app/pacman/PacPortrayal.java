/*    */ package sim.app.pacman;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.geom.Arc2D.Double;
/*    */ import java.awt.geom.Rectangle2D.Double;
/*    */ import sim.engine.Schedule;
/*    */ import sim.portrayal.DrawInfo2D;
/*    */ import sim.portrayal.SimplePortrayal2D;
/*    */ 
/*    */ public class PacPortrayal extends SimplePortrayal2D
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected Color color;
/*    */   PacMan pacman;
/*    */   public static final int MOUTH_RATE = 10;
/*    */   public static final double MAXIMUM_MOUTH_ANGLE = 60.0D;
/* 32 */   Arc2D.Double arc = new Arc2D.Double();
/*    */ 
/*    */   public PacPortrayal(PacMan pacman, Color color)
/*    */   {
/* 30 */     this.pacman = pacman; this.color = color;
/*    */   }
/*    */ 
/*    */   public void draw(Object object, Graphics2D g, DrawInfo2D info)
/*    */   {
/* 35 */     Pac pac = (Pac)object;
/*    */ 
/* 37 */     int time = (int)this.pacman.schedule.getTime();
/* 38 */     int step = time % 20;
/* 39 */     if (step > 10) {
/* 40 */       step = 10 - (step - 10);
/*    */     }
/*    */ 
/* 43 */     double x = info.draw.x;
/* 44 */     double y = info.draw.y;
/* 45 */     double w = info.draw.width * 0.8D;
/*    */ 
/* 47 */     double a = 0.0D;
/* 48 */     switch (pac.lastAction) {
/*    */     case 0:
/* 50 */       a = 90.0D; break;
/*    */     case 1:
/* 51 */       a = 0.0D; break;
/*    */     case 2:
/* 52 */       a = -90.0D; break;
/*    */     case 3:
/* 53 */       a = 180.0D; break;
/*    */     case -1:
/* 54 */       a = 0.0D; break;
/*    */     default:
/* 56 */       throw new RuntimeException("default case should never occur");
/*    */     }
/*    */ 
/* 59 */     double starta = a - 60.0D * step / 10.0D;
/* 60 */     double enda = 120.0D * step / 10.0D - 360.0D;
/*    */ 
/* 62 */     this.arc.setArcByCenter(x, y, w, starta, enda, 2);
/* 63 */     g.setColor(this.color);
/* 64 */     g.fill(this.arc);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pacman.PacPortrayal
 * JD-Core Version:    0.6.2
 */