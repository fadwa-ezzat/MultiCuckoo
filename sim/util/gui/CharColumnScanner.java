/*    */ package sim.util.gui;
/*    */ 
/*    */ class CharColumnScanner
/*    */   implements WordWrapScanner
/*    */ {
/*    */   public int scan(StringBuilder buf, int start, double nextLoc)
/*    */   {
/* 25 */     int nextIndex = start + (int)nextLoc - 1;
/* 26 */     if (buf.length() <= nextIndex) {
/* 27 */       nextIndex = buf.length() - 1;
/*    */     }
/*    */ 
/* 30 */     for (int x = start; x < nextIndex; x++)
/* 31 */       if (buf.charAt(x) == '\n')
/* 32 */         nextIndex = x - 1;
/* 33 */     return nextIndex;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.CharColumnScanner
 * JD-Core Version:    0.6.2
 */