/*     */ package sim.util.gui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.FileDialog;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.TitledBorder;
/*     */ 
/*     */ public class MovieMaker
/*     */ {
/*     */   Frame parentForDialogs;
/*     */   Object encoder;
/*     */   Class encoderClass;
/*     */   boolean isRunning;
/*     */   static final float DEFAULT_FRAME_RATE = 10.0F;
/*     */ 
/*     */   public MovieMaker(Frame parent)
/*     */   {
/*  38 */     this.parentForDialogs = parent;
/*     */     try
/*     */     {
/*  41 */       this.encoderClass = Class.forName("sim.util.media.MovieEncoder", true, Thread.currentThread().getContextClassLoader());
/*     */     } catch (Throwable e) {
/*  43 */       this.encoderClass = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized boolean start(BufferedImage typicalImage)
/*     */   {
/*  49 */     return start(typicalImage, 10.0F);
/*     */   }
/*     */ 
/*     */   public synchronized boolean start(BufferedImage typicalImage, float fps)
/*     */   {
/*  55 */     if (this.isRunning) return false;
/*     */ 
/*  57 */     int encodeFormatIndex = 0;
/*     */     try
/*     */     {
/*  62 */       Object[] f = (Object[])this.encoderClass.getMethod("getEncodingFormats", new Class[] { Float.TYPE, BufferedImage.class }).invoke(null, new Object[] { new Float(fps), typicalImage });
/*     */ 
/*  65 */       if (f == null) return false;
/*     */ 
/*  68 */       JPanel p = new JPanel();
/*  69 */       p.setLayout(new BorderLayout());
/*     */ 
/*  72 */       String[] fmts = new String[f.length];
/*     */ 
/*  75 */       String font = p.getFont().getFamily();
/*     */ 
/*  78 */       for (int i = 0; i < fmts.length; i++) {
/*  79 */         fmts[i] = ("<html><font face=\"" + font + "\" size=\"-2\">" + WordWrap.toHTML(WordWrap.wrap(f[i].toString(), 80)) + "</font></html>");
/*     */       }
/*     */ 
/*  82 */       JTextField framerate = new JTextField("" + fps);
/*  83 */       JPanel panel = new JPanel();
/*  84 */       panel.setLayout(new BorderLayout());
/*  85 */       panel.setBorder(new TitledBorder("Frame Rate"));
/*  86 */       panel.add(framerate, "Center");
/*     */ 
/*  88 */       JPanel panel2 = new JPanel();
/*  89 */       panel2.setLayout(new BorderLayout());
/*  90 */       panel2.setBorder(new TitledBorder("Format"));
/*  91 */       JComboBox encoding = new JComboBox(fmts);
/*  92 */       if (fmts.length == 3)
/*  93 */         encoding.setSelectedIndex(1);
/*  94 */       panel2.add(encoding, "Center");
/*     */ 
/*  97 */       String text1 = "MASON uses Sun's JMF movie generation code.  JMF saves out movies in uncompressed RGB: they are gigantic. Once saved out, you need to convert them to a better codec (we recommend H.264). However, JMF also produces videos with an incorect codec label. This breaks a number of movie players and converters.  Here's some useful information on various software and how it handles JMF's bugs:\n\nQuicktime 10.  You can load the movie (which converts it) and save it out as H.264. However Quicktime does not work with 16-bit RGB: use the 24 or 32 bit RGB formats in MASON.\n\niMovie 10.  iMovie does not work with 32-bit RGB: use the 16 or 24 RGB bit formats in MASON.\n\nFinal Cut Pro.  FCP works with any of the RGB formats.\n\nIN SHORT: We suggest you save out in 24-bit RGB.\n\nSee http://cs.gmu.edu/~eclab/projects/mason/extensions/movies/ for more information.";
/*     */ 
/* 108 */       int myNumberOfPixels = 600;
/* 109 */       JLabel label = new JLabel();
/* 110 */       label.setText("<html><br><b>Notes</b><br><font size='-2'>" + WordWrap.toHTML(WordWrap.wrap(text1, myNumberOfPixels, label.getFontMetrics(label.getFont()))) + "</font></html>");
/*     */ 
/* 114 */       JPanel panel3 = new JPanel();
/* 115 */       panel3.setLayout(new BorderLayout());
/* 116 */       panel3.add(panel2, "North");
/* 117 */       panel3.add(label, "Center");
/*     */ 
/* 119 */       p.add(panel, "North");
/* 120 */       p.add(panel3, "South");
/*     */ 
/* 123 */       if (JOptionPane.showConfirmDialog(this.parentForDialogs, p, "Create a Quicktime Movie...", 2) != 0)
/*     */       {
/* 125 */         return false;
/*     */       }
/*     */ 
/* 128 */       fps = Float.valueOf(framerate.getText()).floatValue();
/* 129 */       encodeFormatIndex = encoding.getSelectedIndex();
/*     */ 
/* 136 */       f = (Object[])this.encoderClass.getMethod("getEncodingFormats", new Class[] { Float.TYPE, BufferedImage.class }).invoke(null, new Object[] { new Float(fps), typicalImage });
/*     */ 
/* 146 */       FileDialog fd = new FileDialog(this.parentForDialogs, "Stream to Quicktime File...", 1);
/* 147 */       fd.setFile("Untitled.mov");
/* 148 */       fd.setVisible(true);
/* 149 */       if (fd.getFile() != null)
/*     */       {
/* 155 */         this.encoder = this.encoderClass.getConstructor(new Class[] { Float.TYPE, File.class, BufferedImage.class, Class.forName("javax.media.Format") }).newInstance(new Object[] { new Float(fps), new File(fd.getDirectory(), Utilities.ensureFileEndsWith(fd.getFile(), ".mov")), typicalImage, f[encodeFormatIndex] });
/*     */       }
/*     */       else
/*     */       {
/* 166 */         return false;
/*     */       }
/*     */     }
/*     */     catch (Throwable e) {
/* 170 */       e.printStackTrace();
/* 171 */       Object[] options = { "Oops" };
/* 172 */       JOptionPane.showOptionDialog(this.parentForDialogs, "JMF is not installed on your computer.\nTo create Quicktime movies of your simulation:\n\n1. Download JMF at http://java.sun.com/products/java-media/jmf/\n2. Mac users should download the \"Cross-platform Java\" version\n3. Install the JMF libraries.\n4. Make certain that the jmf.jar file is in your CLASSPATH.\n", "Java Media Framework (JMF) Not Installed", 0, 0, null, options, options[0]);
/*     */ 
/* 183 */       this.encoder = null;
/* 184 */       this.isRunning = false;
/* 185 */       return false;
/*     */     }
/*     */ 
/* 188 */     this.isRunning = true;
/* 189 */     return true;
/*     */   }
/*     */ 
/*     */   public synchronized boolean add(BufferedImage image)
/*     */   {
/* 195 */     if (!this.isRunning) return false;
/*     */ 
/*     */     try
/*     */     {
/* 200 */       this.encoderClass.getMethod("add", new Class[] { BufferedImage.class }).invoke(this.encoder, new Object[] { image });
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 205 */       ex.printStackTrace();
/* 206 */       return false;
/*     */     }
/*     */ 
/* 209 */     return true;
/*     */   }
/*     */ 
/*     */   public synchronized boolean stop()
/*     */   {
/* 215 */     boolean success = true;
/* 216 */     if (!this.isRunning) return false;
/*     */ 
/*     */     try
/*     */     {
/* 220 */       success = ((Boolean)this.encoderClass.getMethod("stop", new Class[0]).invoke(this.encoder, new Object[0])).booleanValue();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 224 */       ex.printStackTrace();
/* 225 */       return false;
/*     */     }
/* 227 */     this.isRunning = false;
/* 228 */     return success;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.MovieMaker
 * JD-Core Version:    0.6.2
 */