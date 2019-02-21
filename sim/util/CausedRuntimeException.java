/*    */ package sim.util;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.io.PrintWriter;
/*    */ 
/*    */ /** @deprecated */
/*    */ public class CausedRuntimeException extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Throwable target;
/*    */   String message;
/*    */ 
/*    */   protected CausedRuntimeException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CausedRuntimeException(Throwable target)
/*    */   {
/* 32 */     this.target = target;
/* 33 */     this.message = "";
/*    */   }
/*    */ 
/*    */   public CausedRuntimeException(Throwable target, String message)
/*    */   {
/* 38 */     super(message);
/* 39 */     this.target = target;
/* 40 */     this.message = message;
/*    */   }
/*    */ 
/*    */   public void printStackTrace(PrintStream stream)
/*    */   {
/* 45 */     if (this.target == null) {
/* 46 */       super.printStackTrace();
/*    */     }
/*    */     else {
/* 49 */       stream.println("CausedRuntimeException: " + this.message);
/* 50 */       stream.println("Caused By:");
/* 51 */       this.target.printStackTrace(stream);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void printStackTrace(PrintWriter stream)
/*    */   {
/* 57 */     if (this.target == null) {
/* 58 */       super.printStackTrace();
/*    */     }
/*    */     else {
/* 61 */       stream.println("CausedRuntimeException: " + this.message);
/* 62 */       stream.println("Caused By:");
/* 63 */       this.target.printStackTrace(stream);
/*    */     }
/*    */   }
/*    */ 
/* 67 */   public void printStackTrace() { printStackTrace(System.err); }
/*    */ 
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.CausedRuntimeException
 * JD-Core Version:    0.6.2
 */