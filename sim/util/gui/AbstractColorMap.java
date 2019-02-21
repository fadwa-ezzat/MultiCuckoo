/*    */ package sim.util.gui;
/*    */ 
/*    */ import java.awt.Color;
/*    */ 
/*    */ public abstract class AbstractColorMap
/*    */   implements ColorMap
/*    */ {
/*    */   public abstract Color getColor(double paramDouble);
/*    */ 
/*    */   public int getRGB(double level)
/*    */   {
/* 26 */     return getColor(level).getRGB();
/*    */   }
/* 28 */   public int getAlpha(double level) { return getColor(level).getAlpha(); } 
/*    */   public boolean validLevel(double level) {
/* 30 */     return true;
/*    */   }
/* 32 */   public double defaultValue() { return 0.0D; }
/*    */ 
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.AbstractColorMap
 * JD-Core Version:    0.6.2
 */