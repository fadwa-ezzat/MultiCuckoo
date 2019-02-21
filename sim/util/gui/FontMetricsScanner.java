/*    */ package sim.util.gui;
/*    */ 
/*    */ import java.awt.FontMetrics;
/*    */ 
/*    */ class FontMetricsScanner
/*    */   implements WordWrapScanner
/*    */ {
/*    */   FontMetrics metrics;
/*    */ 
/*    */   public FontMetricsScanner(FontMetrics metrics)
/*    */   {
/* 42 */     this.metrics = metrics;
/*    */   }
/*    */ 
/*    */   public int scan(StringBuilder buf, int start, double nextLoc)
/*    */   {
/* 50 */     char[] chars = new char[buf.length() - start];
/* 51 */     buf.getChars(start, buf.length(), chars, 0);
/*    */ 
/* 54 */     for (int x = 0; x < chars.length; x++)
/*    */     {
/* 56 */       if (chars[x] == '\n')
/* 57 */         return start + x - 1;
/* 58 */       int len = this.metrics.charsWidth(chars, 0, x + 1);
/* 59 */       if (len > nextLoc) {
/* 60 */         return start + x - 1;
/*    */       }
/*    */     }
/* 63 */     return buf.length() - 1;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.FontMetricsScanner
 * JD-Core Version:    0.6.2
 */