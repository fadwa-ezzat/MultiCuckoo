/*     */ package sim.util.gui;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.ToolTipManager;
/*     */ 
/*     */ public class MiniHistogram extends JComponent
/*     */ {
/*  36 */   static JLabel DEFAULT_SIZE_COMPARISON = new JLabel("X");
/*     */   double[] buckets;
/*     */   String[] labels;
/*  87 */   MouseMotionAdapter motionAdapter = new MouseMotionAdapter()
/*     */   {
/*     */     public void mouseMoved(MouseEvent event)
/*     */     {
/*  91 */       String s = null;
/*  92 */       if (MiniHistogram.this.buckets != null)
/*     */       {
/*  94 */         int x = (int)(event.getX() * MiniHistogram.this.buckets.length / MiniHistogram.this.getBounds().width);
/*  95 */         if ((MiniHistogram.this.labels != null) && (x < MiniHistogram.this.labels.length)) {
/*  96 */           s = "<html><font size=\"-1\" face=\"" + MiniHistogram.this.getFont().getFamily() + "\">" + "Bucket: " + x + "<br>Range: " + MiniHistogram.this.labels[x] + "<br>Value: " + MiniHistogram.this.buckets[x] + "</font></html>";
/*     */         }
/*  99 */         else if ((MiniHistogram.this.buckets != null) && (MiniHistogram.this.buckets.length != 0)) {
/* 100 */           s = "<html><font size=\"-1\" face=\"" + MiniHistogram.this.getFont().getFamily() + "\">" + "Bucket: " + x + "<br>>Value: " + MiniHistogram.this.buckets[x] + "</font></html>";
/*     */         }
/*     */         else {
/* 103 */           s = null;
/*     */         }
/*     */       }
/* 106 */       if ((s != null) && (!s.equalsIgnoreCase(MiniHistogram.this.getToolTipText())))
/* 107 */         MiniHistogram.this.setToolTipText(s);
/*     */     }
/*  87 */   };
/*     */ 
/* 113 */   MouseAdapter adapter = new MouseAdapter() {
/*     */     int initialDelay;
/*     */     int dismissDelay;
/*     */     int reshowDelay;
/*     */ 
/* 120 */     public void mouseEntered(MouseEvent event) { this.initialDelay = ToolTipManager.sharedInstance().getInitialDelay();
/* 121 */       ToolTipManager.sharedInstance().setInitialDelay(0);
/* 122 */       this.dismissDelay = ToolTipManager.sharedInstance().getDismissDelay();
/* 123 */       ToolTipManager.sharedInstance().setDismissDelay(2147483647);
/* 124 */       this.reshowDelay = ToolTipManager.sharedInstance().getReshowDelay();
/* 125 */       ToolTipManager.sharedInstance().setReshowDelay(0);
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent event)
/*     */     {
/* 130 */       ToolTipManager.sharedInstance().setInitialDelay(this.initialDelay);
/* 131 */       ToolTipManager.sharedInstance().setDismissDelay(this.dismissDelay);
/* 132 */       ToolTipManager.sharedInstance().setReshowDelay(this.reshowDelay);
/*     */     }
/* 113 */   };
/*     */ 
/*     */   public MiniHistogram()
/*     */   {
/*  42 */     setBuckets(new double[0]);
/*  43 */     addMouseListener(this.adapter);
/*  44 */     addMouseMotionListener(this.motionAdapter);
/*  45 */     setBackground(DEFAULT_SIZE_COMPARISON.getBackground());
/*     */   }
/*     */ 
/*     */   public MiniHistogram(double[] buckets, String[] labels)
/*     */   {
/*  50 */     this();
/*  51 */     setBucketsAndLabels(buckets, labels);
/*  52 */     setBackground(DEFAULT_SIZE_COMPARISON.getBackground());
/*  53 */     setOpaque(true);
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredSize()
/*     */   {
/*  58 */     return DEFAULT_SIZE_COMPARISON.getPreferredSize();
/*     */   }
/*     */ 
/*     */   public Dimension getMinimumSize()
/*     */   {
/*  63 */     return DEFAULT_SIZE_COMPARISON.getMinimumSize();
/*     */   }
/*     */ 
/*     */   public void setBuckets(double[] buckets)
/*     */   {
/*  70 */     if (buckets == null) buckets = new double[0];
/*  71 */     this.buckets = buckets;
/*  72 */     repaint();
/*     */   }
/*     */ 
/*     */   public void setBucketLabels(String[] labels)
/*     */   {
/*  78 */     this.labels = labels;
/*     */   }
/*     */ 
/*     */   public void setBucketsAndLabels(double[] buckets, String[] labels)
/*     */   {
/*  83 */     setBuckets(buckets);
/*  84 */     setBucketLabels(labels);
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics graphics)
/*     */   {
/* 138 */     int len = 0;
/* 139 */     if (this.buckets != null) len = this.buckets.length;
/* 140 */     if (len == 0) return;
/* 141 */     Rectangle bounds = getBounds();
/* 142 */     graphics.setColor(getBackground());
/* 143 */     graphics.fillRect(0, 0, bounds.width, bounds.height);
/* 144 */     int height = bounds.height - 2;
/* 145 */     if (height <= 0) return;
/*     */ 
/* 147 */     graphics.setColor(getForeground());
/*     */ 
/* 150 */     double maxbucket = this.buckets[0];
/* 151 */     double minbucket = this.buckets[0];
/* 152 */     for (int i = 1; i < len; i++)
/*     */     {
/* 154 */       if (this.buckets[i] < minbucket) minbucket = this.buckets[i];
/* 155 */       if (this.buckets[i] > maxbucket) maxbucket = this.buckets[i];
/*     */     }
/*     */ 
/* 158 */     if (maxbucket == minbucket)
/*     */     {
/* 160 */       graphics.fillRect(0, 0, bounds.width, height);
/* 161 */       return;
/*     */     }
/*     */ 
/* 164 */     for (int i = 0; i < len; i++)
/*     */     {
/* 166 */       int x0 = (int)(bounds.width / len * i);
/* 167 */       int x1 = (int)(bounds.width / len * (i + 1));
/* 168 */       int y0 = 0;
/*     */       int y1;
/* 170 */       if (this.buckets[i] == (1.0D / 0.0D)) { y1 = height; }
/*     */       else
/*     */       {
/* 171 */         int y1;
/* 171 */         if (this.buckets[i] != this.buckets[i]) y1 = 0; else
/* 172 */           y1 = (int)(height * ((this.buckets[i] - minbucket) / (maxbucket - minbucket)));
/*     */       }
/* 174 */       y0 = height - y0;
/* 175 */       int y1 = height - y1;
/* 176 */       graphics.fillRect(x0 + 1, y1 + 1, x1 - x0 + 1, y0 - y1 + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String[] makeBucketLabels(int numBuckets, double min, double max, boolean logScale)
/*     */   {
/* 186 */     String[] s = new String[numBuckets];
/*     */ 
/* 188 */     if (min > max) { double tmp = min; min = max; max = tmp;
/*     */     }
/* 190 */     if (min == max) { s[0] = ("[" + min + "..." + max + "]"); for (int x = 1; x < s.length; x++) s[x] = ""; return s; }
/* 191 */     if (logScale)
/*     */     {
/* 193 */       min = Math.log(min);
/* 194 */       max = Math.log(max);
/* 195 */       for (int x = 0; x < s.length; x++) {
/* 196 */         s[x] = ("[" + Math.exp(x / numBuckets * (max - min) + min) + "..." + Math.exp((x + 1) / numBuckets * (max - min) + min) + (x == s.length - 1 ? "]" : ")"));
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 201 */       for (int x = 0; x < s.length; x++) {
/* 202 */         s[x] = ("[" + (x / numBuckets * (max - min) + min) + "..." + ((x + 1) / numBuckets * (max - min) + min) + (x == s.length - 1 ? "]" : ")"));
/*     */       }
/*     */     }
/* 205 */     return s;
/*     */   }
/*     */ 
/*     */   public static double minimum(double[] vals)
/*     */   {
/* 234 */     double min = (1.0D / 0.0D);
/* 235 */     for (int i = 0; i < vals.length; i++)
/* 236 */       if (min > vals[i])
/* 237 */         min = vals[i];
/* 238 */     return min;
/*     */   }
/*     */ 
/*     */   public static double maximum(double[] vals)
/*     */   {
/* 245 */     double max = (-1.0D / 0.0D);
/* 246 */     for (int i = 0; i < vals.length; i++)
/* 247 */       if (max < vals[i])
/* 248 */         max = vals[i];
/* 249 */     return max;
/*     */   }
/*     */ 
/*     */   public static double[] makeBuckets(double[] vals, int numBuckets, double min, double max, boolean logScale)
/*     */   {
/* 260 */     double[] b = new double[numBuckets];
/* 261 */     if ((vals == null) || (numBuckets == 0)) return b;
/*     */ 
/* 263 */     if (logScale) { min = Math.log(min); max = Math.log(max);
/*     */     }
/* 265 */     if (min > max) { double tmp = min; min = max; max = tmp;
/* 266 */     } else if (min == max) { b[0] += vals.length; return b;
/*     */     }
/* 268 */     int count = 0;
/* 269 */     for (int x = 0; x < vals.length; x++)
/*     */     {
/* 271 */       double v = vals[x];
/* 272 */       if (logScale)
/*     */       {
/* 274 */         if (v >= 0.0D)
/* 275 */           v = Math.log(v);
/*     */       }
/* 277 */       else if ((v >= min) && (v <= max)) {
/* 278 */         int bucketnum = (int)((v - min) * numBuckets / (max - min));
/* 279 */         if (bucketnum >= numBuckets) bucketnum = numBuckets - 1;
/* 280 */         b[bucketnum] += 1.0D;
/* 281 */         count++;
/*     */       }
/*     */     }
/* 284 */     if (count != 0) {
/* 285 */       for (int x = 0; x < b.length; x++)
/* 286 */         b[x] /= count;
/*     */     }
/* 288 */     return b;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.MiniHistogram
 * JD-Core Version:    0.6.2
 */