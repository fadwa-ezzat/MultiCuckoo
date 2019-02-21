/*     */ package sim.util.gui;
/*     */ 
/*     */ import java.awt.FontMetrics;
/*     */ import java.io.Serializable;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class WordWrap
/*     */   implements Serializable
/*     */ {
/*     */   public static String wrap(String string, int numColumns)
/*     */   {
/*  78 */     return wrap(string, numColumns, new CharColumnScanner());
/*     */   }
/*     */ 
/*     */   public static String wrap(String string, int numPixels, FontMetrics metrics)
/*     */   {
/*  84 */     return wrap(string, numPixels, new FontMetricsScanner(metrics));
/*     */   }
/*     */ 
/*     */   static String wrap(String string, double desiredLength, WordWrapScanner scanner)
/*     */   {
/*  89 */     StringBuilder buf = new StringBuilder(string);
/*     */ 
/*  91 */     int s = 0;
/*     */     while (true)
/*     */     {
/*  96 */       if (s == buf.length()) {
/*  97 */         return buf.toString();
/*     */       }
/*  99 */       int e = scanner.scan(buf, s, desiredLength) + 1;
/*     */ 
/* 101 */       if (e >= buf.length()) {
/* 102 */         return buf.toString();
/*     */       }
/* 104 */       char ce = buf.charAt(e);
/*     */ 
/* 106 */       if (ce == '\n')
/*     */       {
/* 108 */         s = e + 1;
/*     */       }
/* 110 */       else if (Character.isWhitespace(ce))
/*     */       {
/* 112 */         int top = e;
/*     */ 
/* 114 */         while ((top < buf.length() - 1) && (Character.isWhitespace(buf.charAt(top))) && (buf.charAt(top) != '\n'))
/*     */         {
/* 116 */           top++;
/* 117 */         }buf.delete(e, top);
/* 118 */         if (buf.charAt(e) != '\n')
/* 119 */           buf.insert(e, '\n');
/* 120 */         s = e + 1;
/*     */       }
/*     */       else
/*     */       {
/* 124 */         int l = e;
/* 125 */         while ((l > s) && (!Character.isWhitespace(buf.charAt(l))))
/*     */         {
/* 127 */           l--;
/* 128 */         }if ((l == s) && (!Character.isWhitespace(buf.charAt(l))))
/*     */         {
/* 130 */           buf.insert(e, '\n');
/* 131 */           s = e + 1;
/*     */         }
/*     */         else
/*     */         {
/* 135 */           buf.insert(l + 1, '\n');
/* 136 */           s = l + 2;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String[] split(String str)
/*     */   {
/* 146 */     StringTokenizer tok = new StringTokenizer(str, "\n");
/* 147 */     String[] s = new String[tok.countTokens()];
/* 148 */     int x = 0;
/* 149 */     while (tok.hasMoreTokens())
/* 150 */       s[(x++)] = tok.nextToken();
/* 151 */     return s;
/*     */   }
/*     */ 
/*     */   public static String toHTML(String text)
/*     */   {
/* 171 */     StringBuilder buf = new StringBuilder();
/* 172 */     char[] c = text.toCharArray();
/* 173 */     for (int x = 0; x < c.length; x++)
/*     */     {
/* 175 */       switch (c[x])
/*     */       {
/*     */       case '\n':
/*     */       case '\r':
/* 179 */         buf.append("<br>");
/* 180 */         break;
/*     */       case '&':
/* 182 */         buf.append("&amp;");
/* 183 */         break;
/*     */       case '<':
/* 185 */         buf.append("&lt;");
/* 186 */         break;
/*     */       default:
/* 188 */         buf.append(c[x]);
/*     */       }
/*     */     }
/*     */ 
/* 192 */     return buf.toString();
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.WordWrap
 * JD-Core Version:    0.6.2
 */