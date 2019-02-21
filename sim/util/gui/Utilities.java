/*     */ package sim.util.gui;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ public class Utilities
/*     */ {
/*     */   static final int DIALOG_WIDTH = 400;
/*     */   static final int TRACE_FRAME_WIDTH = 600;
/*     */   static final int TRACE_FRAME_HEIGHT = 400;
/*     */ 
/*     */   private static int iFloor(double d)
/*     */   {
/*  21 */     int i = (int)d;
/*  22 */     if (d >= 0.0D) return i;
/*  23 */     if (d < -2147483647.0D)
/*  24 */       return -2147483648;
/*  25 */     if (i == d) return i;
/*  26 */     return i - 1;
/*     */   }
/*     */ 
/*     */   private static int iCeil(double d)
/*     */   {
/*  34 */     int i = (int)d;
/*  35 */     if (d <= 0.0D) return i;
/*  36 */     if (d >= 2147483647.0D)
/*  37 */       return 2147483647;
/*  38 */     if (i == d) return i;
/*  39 */     return i + 1;
/*     */   }
/*     */ 
/*     */   private static int iRound(double d)
/*     */   {
/*  47 */     d += 0.5D;
/*  48 */     int i = (int)d;
/*  49 */     if (d >= 0.0D) return i;
/*  50 */     if (d < -2147483647.0D)
/*  51 */       return -2147483648;
/*  52 */     if (i == d) return i;
/*  53 */     return i - 1;
/*     */   }
/*     */ 
/*     */   public static String ensureFileEndsWith(String filename, String ending)
/*     */   {
/*  60 */     if (filename.regionMatches(false, filename.length() - ending.length(), ending, 0, ending.length()))
/*  61 */       return filename;
/*  62 */     return filename + ending;
/*     */   }
/*     */ 
/*     */   public static void doEnsuredRepaint(Component component)
/*     */   {
/*  70 */     SwingUtilities.invokeLater(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*  74 */         if (this.val$component != null) this.val$component.repaint();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static Thread doLater(long milliseconds, Runnable doThis)
/*     */   {
/*  82 */     Thread thread = new Thread(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try
/*     */         {
/*  88 */           Thread.sleep(this.val$milliseconds);
/*  89 */           SwingUtilities.invokeAndWait(this.val$doThis);
/*     */         }
/*     */         catch (InterruptedException e)
/*     */         {
/*     */         }
/*     */         catch (InvocationTargetException e)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/*  95 */     thread.start();
/*  96 */     return thread;
/*     */   }
/*     */ 
/*     */   public static void informOfError(Throwable error, String description, JFrame frame)
/*     */   {
/* 110 */     error.printStackTrace();
/* 111 */     Object[] options = { "Show Trace", "Okay" };
/* 112 */     JLabel label = new JLabel();
/* 113 */     Font labelFont = label.getFont();
/* 114 */     Font boldFont = labelFont.deriveFont(1);
/* 115 */     FontMetrics boldFontMetrics = label.getFontMetrics(boldFont);
/* 116 */     Font smallFont = labelFont.deriveFont(labelFont.getSize2D() - 2.0F);
/*     */ 
/* 119 */     label.setText("<html><p style=\"padding-top: 12pt; padding-right: 50pt; font: " + boldFont.getSize() + "pt " + boldFont.getFamily() + ";\"><b>" + WordWrap.toHTML(WordWrap.wrap(new StringBuilder().append("").append(description).toString(), 400, boldFontMetrics)) + "</b></p>" + "<p style=\"padding-top: 12pt; padding-right: 50pt; padding-bottom: 12pt; font: " + smallFont.getSize() + "pt " + smallFont.getFamily() + ";\">" + error + "</p></html>");
/*     */ 
/* 125 */     int n = JOptionPane.showOptionDialog(frame, label, "Error", 0, 0, null, options, options[1]);
/*     */ 
/* 132 */     if (n == 0)
/*     */     {
/* 134 */       StringWriter writer = new StringWriter();
/* 135 */       PrintWriter pWriter = new PrintWriter(writer);
/* 136 */       error.printStackTrace(pWriter);
/* 137 */       pWriter.flush();
/* 138 */       JTextArea area = new JTextArea(writer.toString());
/* 139 */       area.setFont(new Font("Monospaced", 0, 12));
/* 140 */       JScrollPane pane = new JScrollPane(area)
/*     */       {
/*     */         public Dimension getPreferredSize()
/*     */         {
/* 144 */           return new Dimension(600, 400);
/*     */         }
/*     */ 
/*     */         public Dimension getMinimumSize() {
/* 148 */           return new Dimension(600, 400);
/*     */         }
/*     */       };
/* 151 */       JOptionPane.showMessageDialog(null, pane);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void inform(String description, String subDescription, JFrame frame)
/*     */   {
/* 161 */     JLabel label = new JLabel();
/* 162 */     Font labelFont = label.getFont();
/* 163 */     Font boldFont = labelFont.deriveFont(1);
/* 164 */     FontMetrics boldFontMetrics = label.getFontMetrics(boldFont);
/* 165 */     Font smallFont = labelFont.deriveFont(labelFont.getSize2D() - 2.0F);
/* 166 */     FontMetrics smallFontMetrics = label.getFontMetrics(smallFont);
/*     */ 
/* 168 */     label.setText("<html><p style=\"padding-top: 12pt; padding-right: 50pt; font: " + boldFont.getSize() + "pt " + boldFont.getFamily() + ";\"><b>" + WordWrap.toHTML(WordWrap.wrap(new StringBuilder().append("").append(description).toString(), 400, boldFontMetrics)) + "</b></p>" + "<p style=\"padding-top: 12pt; padding-right: 50pt; padding-bottom: 12pt; font: " + smallFont.getSize() + "pt " + smallFont.getFamily() + ";\">" + WordWrap.toHTML(WordWrap.wrap(new StringBuilder().append("").append(subDescription).toString(), 400, smallFontMetrics)) + "</p></html>");
/*     */ 
/* 174 */     JOptionPane.showMessageDialog(frame, label, "Error", 1);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.Utilities
 * JD-Core Version:    0.6.2
 */