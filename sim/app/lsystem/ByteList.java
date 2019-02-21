/*    */ package sim.app.lsystem;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ByteList
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -7841332939713409966L;
/*    */   public byte[] b;
/* 15 */   public int length = 0;
/*    */ 
/*    */   ByteList()
/*    */   {
/* 19 */     this.b = new byte[16];
/*    */   }
/*    */ 
/*    */   ByteList(int size)
/*    */   {
/* 24 */     this.b = new byte[size];
/*    */   }
/*    */ 
/*    */   ByteList(ByteList a)
/*    */   {
/* 29 */     this.b = new byte[a.b.length];
/* 30 */     System.arraycopy(a.b, 0, this.b, 0, a.length);
/* 31 */     this.length = a.length;
/*    */   }
/*    */ 
/*    */   public void resize(int toAtLeast)
/*    */   {
/* 36 */     if (this.b.length >= toAtLeast) {
/* 37 */       return;
/*    */     }
/* 39 */     if (this.b.length * 2 > toAtLeast) {
/* 40 */       toAtLeast = this.b.length * 2;
/*    */     }
/*    */ 
/* 43 */     byte[] newb = new byte[toAtLeast];
/* 44 */     System.arraycopy(this.b, 0, newb, 0, this.length);
/* 45 */     this.b = newb;
/*    */   }
/*    */ 
/*    */   public void add(byte n)
/*    */   {
/* 51 */     if (this.length + 1 > this.b.length)
/* 52 */       resize(this.length + 1);
/* 53 */     this.b[this.length] = n;
/* 54 */     this.length += 1;
/*    */   }
/*    */ 
/*    */   public void addAll(ByteList a)
/*    */   {
/* 59 */     if (this.length + a.length > this.b.length)
/* 60 */       resize(this.length + a.length);
/* 61 */     System.arraycopy(a.b, 0, this.b, this.length, a.length);
/* 62 */     this.length += a.length;
/*    */   }
/*    */ 
/*    */   public void clear()
/*    */   {
/* 67 */     this.b = new byte[16];
/* 68 */     this.length = 0;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.lsystem.ByteList
 * JD-Core Version:    0.6.2
 */