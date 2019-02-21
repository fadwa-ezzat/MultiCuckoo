/*    */ package sim.util.media;
/*    */ 
/*    */ import com.lowagie.text.Document;
/*    */ import com.lowagie.text.Rectangle;
/*    */ import com.lowagie.text.pdf.DefaultFontMapper;
/*    */ import com.lowagie.text.pdf.PdfContentByte;
/*    */ import com.lowagie.text.pdf.PdfTemplate;
/*    */ import com.lowagie.text.pdf.PdfWriter;
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.geom.Rectangle2D;
/*    */ import java.awt.geom.Rectangle2D.Double;
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import org.jfree.chart.JFreeChart;
/*    */ 
/*    */ public class PDFEncoder
/*    */ {
/*    */   public static void generatePDF(Component component, File file)
/*    */   {
/* 20 */     int width = component.getWidth();
/* 21 */     int height = component.getHeight();
/*    */     try
/*    */     {
/* 24 */       Document document = new Document(new Rectangle(width, height));
/* 25 */       PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
/* 26 */       document.addAuthor("MASON");
/* 27 */       document.open();
/* 28 */       PdfContentByte cb = writer.getDirectContent();
/* 29 */       PdfTemplate tp = cb.createTemplate(width, height);
/* 30 */       Graphics g2 = tp.createGraphics(width, height, new DefaultFontMapper());
/* 31 */       component.paint(g2);
/* 32 */       g2.dispose();
/* 33 */       cb.addTemplate(tp, 0.0F, 0.0F);
/* 34 */       document.close();
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 38 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void generatePDF(JFreeChart chart, int width, int height, File file)
/*    */   {
/*    */     try
/*    */     {
/* 48 */       Document document = new Document(new Rectangle(width, height));
/* 49 */       PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
/* 50 */       document.addAuthor("MASON");
/* 51 */       document.open();
/* 52 */       PdfContentByte cb = writer.getDirectContent();
/* 53 */       PdfTemplate tp = cb.createTemplate(width, height);
/* 54 */       Graphics2D g2 = tp.createGraphics(width, height, new DefaultFontMapper());
/* 55 */       Rectangle2D rectangle2D = new Rectangle2D.Double(0.0D, 0.0D, width, height);
/* 56 */       chart.draw(g2, rectangle2D);
/* 57 */       g2.dispose();
/* 58 */       cb.addTemplate(tp, 0.0F, 0.0F);
/* 59 */       document.close();
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 63 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.PDFEncoder
 * JD-Core Version:    0.6.2
 */