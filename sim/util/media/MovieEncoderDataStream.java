/*     */ package sim.util.media;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.io.IOException;
/*     */ import javax.media.Buffer;
/*     */ import javax.media.Format;
/*     */ import javax.media.protocol.ContentDescriptor;
/*     */ import javax.media.protocol.PullBufferStream;
/*     */ import javax.media.util.ImageToBuffer;
/*     */ 
/*     */ class MovieEncoderDataStream
/*     */   implements PullBufferStream
/*     */ {
/* 452 */   Buffer buffer = null;
/*     */   Format format;
/* 454 */   boolean ended = false;
/*     */   float frameRate;
/*     */ 
/*     */   MovieEncoderDataStream(Format format, float frameRate)
/*     */   {
/* 458 */     this.frameRate = frameRate; this.format = format;
/*     */   }
/*     */ 
/*     */   void finish() {
/* 462 */     synchronized (this)
/*     */     {
/* 464 */       this.ended = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   void write(Image i)
/*     */   {
/* 471 */     Buffer b = ImageToBuffer.createBuffer(i, this.frameRate);
/* 472 */     while (checkWriteBlock()) try {
/* 473 */         Thread.sleep(25L); } catch (InterruptedException e) { return; }
/* 474 */     synchronized (this)
/*     */     {
/* 476 */       this.buffer = b;
/*     */     }
/*     */   }
/*     */ 
/* 480 */   synchronized boolean checkWriteBlock() { return this.buffer != null; } 
/*     */   synchronized boolean checkReadBlock() {
/* 482 */     return (this.buffer == null) && (!this.ended);
/*     */   }
/* 484 */   public boolean willReadBlock() { return false; }
/*     */ 
/*     */   public void read(Buffer buf)
/*     */     throws IOException
/*     */   {
/* 489 */     while (checkReadBlock()) try { Thread.sleep(25L);
/*     */       } catch (InterruptedException e) {
/*     */       } synchronized (this)
/*     */     {
/* 493 */       if (this.buffer != null)
/*     */       {
/* 496 */         buf.setData(this.buffer.getData());
/* 497 */         buf.setLength(this.buffer.getLength());
/* 498 */         buf.setOffset(0);
/* 499 */         buf.setFormat(this.format);
/* 500 */         buf.setFlags(buf.getFlags() | 0x10 | 0x20);
/*     */       }
/* 502 */       this.buffer = null;
/* 503 */       if (this.ended)
/*     */       {
/* 506 */         buf.setEOM(true);
/* 507 */         buf.setOffset(0);
/* 508 */         buf.setLength(0);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Format getFormat()
/*     */   {
/* 515 */     return this.format;
/*     */   }
/* 517 */   public ContentDescriptor getContentDescriptor() { return new ContentDescriptor("raw"); } 
/*     */   public long getContentLength() {
/* 519 */     return 0L;
/*     */   }
/* 521 */   public boolean endOfStream() { return this.ended; } 
/*     */   public Object[] getControls() {
/* 523 */     return new Object[0];
/*     */   }
/* 525 */   public Object getControl(String type) { return null; }
/*     */ 
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.MovieEncoderDataStream
 * JD-Core Version:    0.6.2
 */