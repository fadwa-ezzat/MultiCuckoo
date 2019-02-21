/*    */ package sim.util.gui;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.event.MouseAdapter;
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JColorChooser;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public class ColorWell extends JPanel
/*    */ {
/*    */   Color color;
/*    */ 
/*    */   public ColorWell()
/*    */   {
/* 19 */     this(new Color(0, 0, 0, 0));
/*    */   }
/*    */ 
/*    */   public ColorWell(Color c)
/*    */   {
/* 24 */     this.color = c;
/* 25 */     addMouseListener(new MouseAdapter()
/*    */     {
/*    */       public void mouseReleased(MouseEvent e)
/*    */       {
/* 29 */         Color col = JColorChooser.showDialog(null, "Choose Color", ColorWell.this.getBackground());
/* 30 */         ColorWell.this.setColor(col);
/*    */       }
/*    */     });
/* 33 */     setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*    */   }
/*    */ 
/*    */   public void paintComponent(Graphics g)
/*    */   {
/* 39 */     g.setColor(this.color);
/* 40 */     g.fillRect(0, 0, getWidth(), getHeight());
/*    */   }
/*    */ 
/*    */   public void setColor(Color c)
/*    */   {
/* 45 */     if (c != null)
/* 46 */       this.color = changeColor(c);
/* 47 */     repaint();
/*    */   }
/*    */ 
/*    */   public Color getColor()
/*    */   {
/* 52 */     return this.color;
/*    */   }
/*    */ 
/*    */   public Color changeColor(Color c)
/*    */   {
/* 57 */     return c;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.ColorWell
 * JD-Core Version:    0.6.2
 */