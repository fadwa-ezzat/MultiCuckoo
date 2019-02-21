/*    */ package sim.portrayal.simple;
/*    */ 
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.Image;
/*    */ import java.awt.geom.AffineTransform;
/*    */ import java.awt.geom.Rectangle2D.Double;
/*    */ import javax.swing.ImageIcon;
/*    */ import sim.portrayal.DrawInfo2D;
/*    */ 
/*    */ public class ImagePortrayal2D extends RectanglePortrayal2D
/*    */ {
/*    */   public Image image;
/* 36 */   AffineTransform preciseTransform = new AffineTransform();
/*    */ 
/*    */   public ImagePortrayal2D(ImageIcon icon, double scale)
/*    */   {
/* 41 */     this(icon.getImage(), scale);
/*    */   }
/*    */ 
/*    */   public ImagePortrayal2D(ImageIcon icon)
/*    */   {
/* 47 */     this(icon, 1.0D);
/*    */   }
/*    */ 
/*    */   public ImagePortrayal2D(Class c, String resourceName, double scale)
/*    */   {
/* 53 */     this(new ImageIcon(c.getResource(resourceName)), scale);
/*    */   }
/*    */ 
/*    */   public ImagePortrayal2D(Class c, String resourceName)
/*    */   {
/* 59 */     this(c, resourceName, 1.0D);
/*    */   }
/*    */ 
/*    */   public ImagePortrayal2D(Image image) {
/* 63 */     this(image, 1.0D);
/*    */   }
/*    */ 
/*    */   public ImagePortrayal2D(Image image, double scale)
/*    */   {
/* 68 */     super(null, scale);
/* 69 */     this.image = image;
/* 70 */     this.scale = scale;
/*    */   }
/*    */ 
/*    */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*    */   {
/* 76 */     if (this.image == null) return;
/*    */ 
/* 79 */     int iw = this.image.getWidth(null);
/* 80 */     int ih = this.image.getHeight(null);
/*    */     double height;
/*    */     double height;
/*    */     double width;
/* 84 */     if (ih > iw)
/*    */     {
/* 86 */       double width = info.draw.width * this.scale;
/* 87 */       height = ih * width / iw;
/*    */     }
/*    */     else
/*    */     {
/* 91 */       height = info.draw.height * this.scale;
/* 92 */       width = iw * height / ih;
/*    */     }
/*    */ 
/* 95 */     double x = info.draw.x - width / 2.0D;
/* 96 */     double y = info.draw.y - height / 2.0D;
/*    */ 
/* 99 */     if (info.precise)
/*    */     {
/* 101 */       this.preciseTransform.setToScale(width, height);
/* 102 */       this.preciseTransform.translate(x, y);
/* 103 */       graphics.drawImage(this.image, this.preciseTransform, null);
/*    */     } else {
/* 105 */       graphics.drawImage(this.image, (int)x, (int)y, (int)width, (int)height, null);
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.simple.ImagePortrayal2D
 * JD-Core Version:    0.6.2
 */