/*     */ package sim.util.media;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import javax.media.Format;
/*     */ import javax.media.MediaLocator;
/*     */ import javax.media.Time;
/*     */ import javax.media.protocol.PullBufferDataSource;
/*     */ import javax.media.protocol.PullBufferStream;
/*     */ 
/*     */ class MovieEncoderDataSource extends PullBufferDataSource
/*     */ {
/*     */   MovieEncoderDataStream[] streams;
/*     */ 
/*     */   public MovieEncoderDataSource(Format format, float frameRate)
/*     */   {
/* 396 */     this.streams = new MovieEncoderDataStream[1];
/* 397 */     this.streams[0] = new MovieEncoderDataStream(format, frameRate);
/*     */   }
/*     */   public void setLocator(MediaLocator source) {
/*     */   }
/*     */   public MediaLocator getLocator() {
/* 402 */     return null;
/*     */   }
/* 404 */   public String getContentType() { return "raw"; } 
/*     */   public void connect() {
/*     */   }
/*     */   public void disconnect() {
/*     */   }
/*     */   public void start() {
/*     */   }
/*     */   public void stop() {
/*     */   }
/*     */   public PullBufferStream[] getStreams() {
/* 414 */     return this.streams;
/*     */   }
/* 416 */   public Time getDuration() { return DURATION_UNKNOWN; } 
/*     */   public Object[] getControls() {
/* 418 */     return new Object[0];
/*     */   }
/* 420 */   public Object getControl(String type) { return null; }
/*     */ 
/*     */   public void add(Image i)
/*     */   {
/* 424 */     this.streams[0].write(i);
/*     */   }
/*     */ 
/*     */   public void finish()
/*     */   {
/* 429 */     this.streams[0].finish();
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.MovieEncoderDataSource
 * JD-Core Version:    0.6.2
 */