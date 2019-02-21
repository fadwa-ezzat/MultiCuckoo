/*     */ package sim.util.media;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.net.URI;
/*     */ import javax.media.Buffer;
/*     */ import javax.media.CannotRealizeException;
/*     */ import javax.media.ConfigureCompleteEvent;
/*     */ import javax.media.Controller;
/*     */ import javax.media.ControllerEvent;
/*     */ import javax.media.ControllerListener;
/*     */ import javax.media.DataSink;
/*     */ import javax.media.EndOfMediaEvent;
/*     */ import javax.media.Format;
/*     */ import javax.media.Manager;
/*     */ import javax.media.MediaLocator;
/*     */ import javax.media.NoDataSinkException;
/*     */ import javax.media.NoProcessorException;
/*     */ import javax.media.PrefetchCompleteEvent;
/*     */ import javax.media.Processor;
/*     */ import javax.media.RealizeCompleteEvent;
/*     */ import javax.media.ResourceUnavailableEvent;
/*     */ import javax.media.control.TrackControl;
/*     */ import javax.media.datasink.DataSinkErrorEvent;
/*     */ import javax.media.datasink.DataSinkEvent;
/*     */ import javax.media.datasink.DataSinkListener;
/*     */ import javax.media.datasink.EndOfStreamEvent;
/*     */ import javax.media.protocol.ContentDescriptor;
/*     */ import javax.media.util.ImageToBuffer;
/*     */ 
/*     */ public class MovieEncoder
/*     */   implements DataSinkListener, ControllerListener, Serializable
/*     */ {
/*     */   boolean started;
/*     */   boolean stopped;
/*     */   int width;
/*     */   int height;
/*     */   int type;
/*     */   float frameRate;
/*     */   Processor processor;
/*     */   MovieEncoderDataSource source;
/*     */   DataSink sink;
/*     */   File file;
/*     */   Format encodeFormat;
/* 191 */   final Object waitSync = new Object();
/* 192 */   boolean stateTransitionOK = true;
/*     */ 
/* 254 */   final Object waitFileSync = new Object();
/* 255 */   boolean fileDone = false;
/* 256 */   boolean fileSuccess = true;
/*     */ 
/*     */   public static Format[] getEncodingFormats(float fps, BufferedImage typicalImage)
/*     */   {
/* 127 */     return new MovieEncoder().getEncodingFormatsHelper(fps, typicalImage);
/*     */   }
/*     */ 
/*     */   private Format[] getEncodingFormatsHelper(float fps, BufferedImage typicalImage)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       Format format = ImageToBuffer.createBuffer(typicalImage, fps).getFormat();
/*     */ 
/* 137 */       MovieEncoderDataSource source = new MovieEncoderDataSource(format, fps);
/*     */ 
/* 139 */       Processor processor = Manager.createProcessor(source);
/* 140 */       processor.addControllerListener(this);
/*     */ 
/* 142 */       processor.configure();
/*     */ 
/* 146 */       if (!waitForState(processor, 180)) {
/* 147 */         throw new RuntimeException("Failed to configure processor");
/*     */       }
/* 149 */       processor.setContentDescriptor(new ContentDescriptor("video.quicktime"));
/*     */ 
/* 151 */       TrackControl[] tcs = processor.getTrackControls();
/*     */ 
/* 154 */       Format[] f = tcs[0].getSupportedFormats();
/* 155 */       if ((f == null) || (f.length <= 0))
/* 156 */         throw new RuntimeException("The mux does not support the input format: " + tcs[0].getFormat());
/* 157 */       processor.removeControllerListener(this);
/* 158 */       return f;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 162 */       e.printStackTrace();
/* 163 */       this.processor.removeControllerListener(this);
/* 164 */     }return null;
/*     */   }
/*     */ 
/*     */   MovieEncoder()
/*     */   {
/*     */   }
/*     */ 
/*     */   public MovieEncoder(float frameRate, File file, BufferedImage typicalImage, Format encodeFormat)
/*     */   {
/* 175 */     this.frameRate = frameRate;
/* 176 */     this.file = file;
/* 177 */     this.encodeFormat = encodeFormat;
/*     */     try
/*     */     {
/* 180 */       setup(typicalImage);
/* 181 */       this.started = true;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 185 */       e.printStackTrace();
/* 186 */       this.stopped = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean waitForState(Processor p, int state)
/*     */   {
/* 200 */     synchronized (this.waitSync) {
/*     */       try {
/* 202 */         while ((p.getState() < state) && (this.stateTransitionOK))
/* 203 */           this.waitSync.wait(); 
/*     */       } catch (Exception e) {  }
/*     */ 
/*     */     }
/* 206 */     return this.stateTransitionOK;
/*     */   }
/*     */ 
/*     */   public void controllerUpdate(ControllerEvent evt)
/*     */   {
/* 213 */     if (((evt instanceof ConfigureCompleteEvent)) || ((evt instanceof RealizeCompleteEvent)) || ((evt instanceof PrefetchCompleteEvent)))
/*     */     {
/* 216 */       synchronized (this.waitSync) {
/* 217 */         this.stateTransitionOK = true;
/* 218 */         this.waitSync.notifyAll();
/*     */       }
/* 220 */     } else if ((evt instanceof ResourceUnavailableEvent)) {
/* 221 */       synchronized (this.waitSync) {
/* 222 */         this.stateTransitionOK = false;
/* 223 */         this.waitSync.notifyAll();
/*     */       }
/* 225 */     } else if ((evt instanceof EndOfMediaEvent)) {
/* 226 */       evt.getSourceController().stop();
/*     */       try
/*     */       {
/* 230 */         evt.getSourceController().close();
/*     */       }
/*     */       catch (Exception e) {
/* 233 */         System.err.println("WARNING: Spurious Sun JMF Error?\n\n"); e.printStackTrace();
/*     */ 
/* 237 */         synchronized (this.waitSync)
/*     */         {
/* 239 */           this.stateTransitionOK = false;
/* 240 */           this.waitSync.notifyAll();
/*     */         }
/* 242 */         synchronized (this.waitFileSync)
/*     */         {
/* 244 */           this.fileSuccess = false;
/* 245 */           this.fileDone = true;
/* 246 */           this.waitFileSync.notifyAll();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean waitForFileDone()
/*     */   {
/* 262 */     synchronized (this.waitFileSync) {
/*     */       try {
/* 264 */         while (!this.fileDone)
/* 265 */           this.waitFileSync.wait(); 
/*     */       } catch (Exception e) {  }
/*     */ 
/*     */     }
/* 268 */     return this.fileSuccess;
/*     */   }
/*     */ 
/*     */   public void dataSinkUpdate(DataSinkEvent evt)
/*     */   {
/* 277 */     if ((evt instanceof EndOfStreamEvent))
/* 278 */       synchronized (this.waitFileSync) {
/* 279 */         this.fileDone = true;
/* 280 */         this.waitFileSync.notifyAll();
/*     */       }
/* 282 */     else if ((evt instanceof DataSinkErrorEvent))
/* 283 */       synchronized (this.waitFileSync) {
/* 284 */         this.fileDone = true;
/* 285 */         this.fileSuccess = false;
/* 286 */         this.waitFileSync.notifyAll();
/*     */       }
/*     */   }
/*     */ 
/*     */   void setup(BufferedImage i)
/*     */     throws IOException, NoDataSinkException, NoProcessorException, CannotRealizeException, RuntimeException
/*     */   {
/* 296 */     this.width = i.getWidth();
/* 297 */     this.height = i.getHeight();
/* 298 */     this.type = i.getType();
/*     */ 
/* 302 */     Format format = ImageToBuffer.createBuffer(i, this.frameRate).getFormat();
/* 303 */     this.source = new MovieEncoderDataSource(format, this.frameRate);
/*     */ 
/* 305 */     this.processor = Manager.createProcessor(this.source);
/*     */ 
/* 307 */     this.processor.addControllerListener(this);
/* 308 */     this.processor.configure();
/* 309 */     if (!waitForState(this.processor, 180)) {
/* 310 */       throw new RuntimeException("Failed to configure processor");
/*     */     }
/* 312 */     this.processor.setContentDescriptor(new ContentDescriptor("video.quicktime"));
/*     */ 
/* 314 */     TrackControl[] tcs = this.processor.getTrackControls();
/*     */ 
/* 317 */     tcs[0].setFormat(this.encodeFormat);
/*     */ 
/* 320 */     this.processor.realize();
/* 321 */     if (!waitForState(this.processor, 300)) {
/* 322 */       throw new RuntimeException("Failed to Realize processor");
/*     */     }
/*     */ 
/* 326 */     this.sink = Manager.createDataSink(this.processor.getDataOutput(), new MediaLocator(this.file.toURI().toURL()));
/* 327 */     this.sink.addDataSinkListener(this);
/* 328 */     this.sink.open();
/* 329 */     this.processor.start();
/* 330 */     this.sink.start();
/*     */ 
/* 332 */     this.started = true;
/*     */   }
/*     */ 
/*     */   BufferedImage preprocess(BufferedImage i)
/*     */   {
/* 339 */     if ((i.getWidth() != this.width) || (i.getHeight() != this.height) || (i.getType() != this.type))
/*     */     {
/* 341 */       BufferedImage temp = new BufferedImage(this.width, this.height, this.type);
/* 342 */       Graphics2D g = temp.createGraphics();
/* 343 */       g.drawImage(i, 0, 0, null);
/* 344 */       i = temp;
/*     */     }
/* 346 */     return i;
/*     */   }
/*     */ 
/*     */   public synchronized boolean add(BufferedImage i)
/*     */   {
/* 357 */     if (!this.stopped)
/*     */     {
/* 359 */       i = preprocess(i);
/* 360 */       this.source.add(i);
/*     */     }
/* 362 */     return !this.stopped;
/*     */   }
/*     */ 
/*     */   public synchronized boolean stop()
/*     */   {
/* 369 */     if (!this.started) return false;
/* 370 */     if (this.stopped) return false;
/* 371 */     this.stopped = true;
/* 372 */     this.source.finish();
/*     */ 
/* 374 */     boolean success = waitForFileDone();
/*     */     try { this.sink.close(); } catch (Exception e) {
/* 376 */     }this.processor.removeControllerListener(this);
/* 377 */     this.stopped = true;
/* 378 */     return success;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.MovieEncoder
 * JD-Core Version:    0.6.2
 */