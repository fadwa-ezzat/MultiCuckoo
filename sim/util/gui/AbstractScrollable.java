/*    */ package sim.util.gui;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Rectangle;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.Scrollable;
/*    */ 
/*    */ public abstract class AbstractScrollable extends JPanel
/*    */   implements Scrollable
/*    */ {
/*    */   public Dimension getPreferredScrollableViewportSize()
/*    */   {
/* 18 */     return getPreferredSize();
/*    */   }
/*    */ 
/*    */   public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
/*    */   {
/* 24 */     return visibleRect.height / 20;
/*    */   }
/*    */ 
/*    */   public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
/*    */   {
/* 30 */     return visibleRect.height / 2;
/*    */   }
/*    */ 
/*    */   public boolean getScrollableTracksViewportWidth()
/*    */   {
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean getScrollableTracksViewportHeight()
/*    */   {
/* 40 */     return false;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.AbstractScrollable
 * JD-Core Version:    0.6.2
 */