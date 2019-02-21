/*     */ package sim.display3d;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import java.awt.image.BufferedImage;
/*     */ import javax.media.j3d.Canvas3D;
/*     */ import javax.media.j3d.GraphicsContext3D;
/*     */ import javax.media.j3d.ImageComponent2D;
/*     */ import javax.media.j3d.Raster;
/*     */ import javax.vecmath.Point3f;
/*     */ 
/*     */ public class CapturingCanvas3D extends Canvas3D
/*     */ {
/*     */   boolean writeBuffer_;
/*     */   boolean keepOnWriting_;
/*     */   BufferedImage buffer_;
/*     */   int x;
/*     */   int y;
/*     */   int width;
/*     */   int height;
/*     */ 
/*     */   public CapturingCanvas3D(GraphicsConfiguration graphicsConfiguration)
/*     */   {
/*  41 */     super(graphicsConfiguration);
/*     */   }
/*     */ 
/*     */   public CapturingCanvas3D(GraphicsConfiguration graphicsConfiguration, boolean offScreen)
/*     */   {
/*  46 */     super(graphicsConfiguration, offScreen);
/*     */   }
/*     */ 
/*     */   public synchronized BufferedImage getLastImage()
/*     */   {
/*  51 */     return this.buffer_;
/*     */   }
/*     */ 
/*     */   Rectangle2D getImageSize()
/*     */   {
/*  57 */     Dimension s = getSize();
/*  58 */     Rectangle2D clip = new Rectangle2D.Double(0.0D, 0.0D, s.width, s.height);
/*  59 */     Rectangle bounds = getGraphics().getClipBounds();
/*  60 */     if (bounds != null)
/*  61 */       clip = clip.createIntersection(bounds);
/*  62 */     return clip;
/*     */   }
/*     */ 
/*     */   public void beginCapturing(boolean movie)
/*     */   {
/*  68 */     Rectangle2D r = getImageSize();
/*  69 */     synchronized (this)
/*     */     {
/*  71 */       this.x = ((int)r.getX());
/*  72 */       this.y = ((int)r.getY());
/*  73 */       this.width = ((int)r.getWidth());
/*  74 */       this.height = ((int)r.getHeight());
/*     */ 
/*  76 */       this.writeBuffer_ = true;
/*  77 */       this.keepOnWriting_ |= movie;
/*     */     }
/*     */ 
/*  80 */     fillBuffer(true);
/*     */   }
/*     */ 
/*     */   void fillBuffer(boolean doubleRasterRead)
/*     */   {
/*  87 */     GraphicsContext3D ctx = getGraphicsContext3D();
/*     */ 
/*  89 */     Raster ras = new Raster(new Point3f(-1.0F, -1.0F, -1.0F), 1, this.x, this.y, this.width, this.height, new ImageComponent2D(1, new BufferedImage(this.width, this.height, 1)), null);
/*     */ 
/*  98 */     ctx.readRaster(ras);
/*  99 */     if (doubleRasterRead) ctx.readRaster(ras);
/*     */ 
/* 101 */     this.buffer_ = ras.getImage().getImage();
/* 102 */     this.buffer_.flush();
/*     */   }
/*     */ 
/*     */   public synchronized void stopCapturing()
/*     */   {
/* 107 */     this.keepOnWriting_ = false;
/*     */   }
/*     */ 
/*     */   public void postSwap()
/*     */   {
/* 112 */     if (this.writeBuffer_)
/*     */     {
/* 114 */       fillBuffer(false);
/* 115 */       if (!this.keepOnWriting_)
/* 116 */         this.writeBuffer_ = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void postRender()
/*     */   {
/* 122 */     synchronized (this) { notify(); }
/*     */ 
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.display3d.CapturingCanvas3D
 * JD-Core Version:    0.6.2
 */