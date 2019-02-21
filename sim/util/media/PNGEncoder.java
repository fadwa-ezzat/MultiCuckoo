/*     */ package sim.util.media;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.awt.image.PixelGrabber;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.zip.CRC32;
/*     */ import java.util.zip.Deflater;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ 
/*     */ public class PNGEncoder
/*     */ {
/*     */   public static final int FILTER_NONE = 0;
/*     */   public static final int FILTER_SUB = 1;
/*     */   public static final int FILTER_UP = 2;
/*     */   byte[] pngBytes;
/*     */   byte[] priorRow;
/*     */   byte[] leftBytes;
/*     */   Image image;
/*     */   int width;
/*     */   int height;
/*     */   int bytePos;
/*     */   int maxPos;
/*  58 */   CRC32 crc = new CRC32();
/*     */   long crcValue;
/*     */   boolean encodeAlpha;
/*     */   int filter;
/*     */   int bytesPerPixel;
/*     */   int compressionLevel;
/*     */ 
/*     */   public PNGEncoder()
/*     */   {
/*  71 */     this(null, false, 0, 0);
/*     */   }
/*     */ 
/*     */   public PNGEncoder(Image image)
/*     */   {
/*  82 */     this(image, false, 0, 0);
/*     */   }
/*     */ 
/*     */   public PNGEncoder(Image image, boolean encodeAlpha)
/*     */   {
/*  94 */     this(image, encodeAlpha, 0, 0);
/*     */   }
/*     */ 
/*     */   public PNGEncoder(Image image, boolean encodeAlpha, int whichFilter)
/*     */   {
/* 107 */     this(image, encodeAlpha, whichFilter, 0);
/*     */   }
/*     */ 
/*     */   public PNGEncoder(Image image, boolean encodeAlpha, int whichFilter, int compLevel)
/*     */   {
/* 123 */     this.image = image;
/* 124 */     this.encodeAlpha = encodeAlpha;
/* 125 */     setFilter(whichFilter);
/* 126 */     if ((compLevel >= 0) && (compLevel <= 9))
/*     */     {
/* 128 */       this.compressionLevel = compLevel;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setImage(Image image)
/*     */   {
/* 141 */     this.image = image;
/* 142 */     this.pngBytes = null;
/*     */   }
/*     */ 
/*     */   public byte[] pngEncode()
/*     */   {
/* 153 */     byte[] pngIdBytes = { -119, 80, 78, 71, 13, 10, 26, 10 };
/*     */ 
/* 155 */     if (this.image == null)
/*     */     {
/* 157 */       return null;
/*     */     }
/* 159 */     this.width = this.image.getWidth(null);
/* 160 */     this.height = this.image.getHeight(null);
/*     */ 
/* 166 */     this.pngBytes = new byte[(this.width + 1) * this.height * 3 + 200];
/*     */ 
/* 171 */     this.maxPos = 0;
/*     */ 
/* 173 */     this.bytePos = writeBytes(pngIdBytes, 0);
/*     */ 
/* 175 */     writeHeader();
/*     */ 
/* 177 */     if (writeImageData())
/*     */     {
/* 179 */       writeEnd();
/* 180 */       this.pngBytes = resizeByteArray(this.pngBytes, this.maxPos);
/*     */     }
/*     */     else
/*     */     {
/* 184 */       this.pngBytes = null;
/*     */     }
/* 186 */     return this.pngBytes;
/*     */   }
/*     */ 
/*     */   public void setEncodeAlpha(boolean encodeAlpha)
/*     */   {
/* 196 */     this.encodeAlpha = encodeAlpha;
/*     */   }
/*     */ 
/*     */   public boolean getEncodeAlpha()
/*     */   {
/* 206 */     return this.encodeAlpha;
/*     */   }
/*     */ 
/*     */   public void setFilter(int whichFilter)
/*     */   {
/* 216 */     this.filter = 0;
/* 217 */     if (whichFilter <= 2)
/*     */     {
/* 219 */       this.filter = whichFilter;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getFilter()
/*     */   {
/* 230 */     return this.filter;
/*     */   }
/*     */ 
/*     */   public void setCompressionLevel(int level)
/*     */   {
/* 240 */     if ((level >= 0) && (level <= 9))
/*     */     {
/* 242 */       this.compressionLevel = level;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getCompressionLevel()
/*     */   {
/* 253 */     return this.compressionLevel;
/*     */   }
/*     */ 
/*     */   byte[] resizeByteArray(byte[] array, int newLength)
/*     */   {
/* 266 */     byte[] newArray = new byte[newLength];
/* 267 */     int oldLength = array.length;
/*     */ 
/* 269 */     System.arraycopy(array, 0, newArray, 0, Math.min(oldLength, newLength));
/*     */ 
/* 271 */     return newArray;
/*     */   }
/*     */ 
/*     */   int writeBytes(byte[] data, int offset)
/*     */   {
/* 287 */     this.maxPos = Math.max(this.maxPos, offset + data.length);
/* 288 */     if (data.length + offset > this.pngBytes.length)
/*     */     {
/* 290 */       this.pngBytes = resizeByteArray(this.pngBytes, this.pngBytes.length + Math.max(1000, data.length));
/*     */     }
/*     */ 
/* 293 */     System.arraycopy(data, 0, this.pngBytes, offset, data.length);
/* 294 */     return offset + data.length;
/*     */   }
/*     */ 
/*     */   int writeBytes(byte[] data, int nBytes, int offset)
/*     */   {
/* 311 */     this.maxPos = Math.max(this.maxPos, offset + nBytes);
/* 312 */     if (nBytes + offset > this.pngBytes.length)
/*     */     {
/* 314 */       this.pngBytes = resizeByteArray(this.pngBytes, this.pngBytes.length + Math.max(1000, nBytes));
/*     */     }
/*     */ 
/* 317 */     System.arraycopy(data, 0, this.pngBytes, offset, nBytes);
/* 318 */     return offset + nBytes;
/*     */   }
/*     */ 
/*     */   int writeInt2(int n, int offset)
/*     */   {
/* 330 */     byte[] temp = { (byte)(n >> 8 & 0xFF), (byte)(n & 0xFF) };
/*     */ 
/* 332 */     return writeBytes(temp, offset);
/*     */   }
/*     */ 
/*     */   int writeInt4(int n, int offset)
/*     */   {
/* 344 */     byte[] temp = { (byte)(n >> 24 & 0xFF), (byte)(n >> 16 & 0xFF), (byte)(n >> 8 & 0xFF), (byte)(n & 0xFF) };
/*     */ 
/* 348 */     return writeBytes(temp, offset);
/*     */   }
/*     */ 
/*     */   int writeByte(int b, int offset)
/*     */   {
/* 360 */     byte[] temp = { (byte)b };
/* 361 */     return writeBytes(temp, offset);
/*     */   }
/*     */ 
/*     */   int writeString(String s, int offset)
/*     */   {
/* 376 */     return writeBytes(s.getBytes(), offset);
/*     */   }
/*     */ 
/*     */   void writeHeader()
/*     */   {
/* 386 */     int startPos = this.bytePos = writeInt4(13, this.bytePos);
/* 387 */     this.bytePos = writeString("IHDR", this.bytePos);
/* 388 */     this.width = this.image.getWidth(null);
/* 389 */     this.height = this.image.getHeight(null);
/* 390 */     this.bytePos = writeInt4(this.width, this.bytePos);
/* 391 */     this.bytePos = writeInt4(this.height, this.bytePos);
/* 392 */     this.bytePos = writeByte(8, this.bytePos);
/* 393 */     this.bytePos = writeByte(this.encodeAlpha ? 6 : 2, this.bytePos);
/* 394 */     this.bytePos = writeByte(0, this.bytePos);
/* 395 */     this.bytePos = writeByte(0, this.bytePos);
/* 396 */     this.bytePos = writeByte(0, this.bytePos);
/* 397 */     this.crc.reset();
/* 398 */     this.crc.update(this.pngBytes, startPos, this.bytePos - startPos);
/* 399 */     this.crcValue = this.crc.getValue();
/* 400 */     this.bytePos = writeInt4((int)this.crcValue, this.bytePos);
/*     */   }
/*     */ 
/*     */   void filterSub(byte[] pixels, int startPos, int width)
/*     */   {
/* 416 */     int offset = this.bytesPerPixel;
/* 417 */     int actualStart = startPos + offset;
/* 418 */     int nBytes = width * this.bytesPerPixel;
/* 419 */     int leftInsert = offset;
/* 420 */     int leftExtract = 0;
/*     */ 
/* 422 */     for (int i = actualStart; i < startPos + nBytes; i++)
/*     */     {
/* 424 */       this.leftBytes[leftInsert] = pixels[i];
/* 425 */       pixels[i] = ((byte)((pixels[i] - this.leftBytes[leftExtract]) % 256));
/* 426 */       leftInsert = (leftInsert + 1) % 15;
/* 427 */       leftExtract = (leftExtract + 1) % 15;
/*     */     }
/*     */   }
/*     */ 
/*     */   void filterUp(byte[] pixels, int startPos, int width)
/*     */   {
/* 444 */     int nBytes = width * this.bytesPerPixel;
/*     */ 
/* 446 */     for (int i = 0; i < nBytes; i++)
/*     */     {
/* 448 */       byte current_byte = pixels[(startPos + i)];
/* 449 */       pixels[(startPos + i)] = ((byte)((pixels[(startPos + i)] - this.priorRow[i]) % 256));
/* 450 */       this.priorRow[i] = current_byte;
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean writeImageData()
/*     */   {
/* 465 */     int rowsLeft = this.height;
/* 466 */     int startRow = 0;
/*     */ 
/* 479 */     this.bytesPerPixel = (this.encodeAlpha ? 4 : 3);
/*     */ 
/* 481 */     Deflater scrunch = new Deflater(this.compressionLevel);
/* 482 */     ByteArrayOutputStream outBytes = new ByteArrayOutputStream(1024);
/*     */ 
/* 485 */     DeflaterOutputStream compBytes = new DeflaterOutputStream(outBytes, scrunch);
/*     */     try
/*     */     {
/* 489 */       while (rowsLeft > 0)
/*     */       {
/* 491 */         int nRows = Math.min(32767 / (this.width * (this.bytesPerPixel + 1)), rowsLeft);
/* 492 */         if (nRows <= 0) nRows = 1;
/*     */ 
/* 495 */         int[] pixels = new int[this.width * nRows];
/*     */ 
/* 497 */         PixelGrabber pg = new PixelGrabber(this.image, 0, startRow, this.width, nRows, pixels, 0, this.width);
/*     */         try
/*     */         {
/* 500 */           pg.grabPixels();
/*     */         }
/*     */         catch (Exception e) {
/* 503 */           System.err.println("WARNING: PNG image write interrupted waiting for pixels\n\n" + e);
/* 504 */           return false;
/*     */         }
/* 506 */         if ((pg.getStatus() & 0x80) != 0) {
/* 507 */           System.err.println("WARNING: PNG Image fetch aborted or errored.");
/* 508 */           return false;
/*     */         }
/*     */ 
/* 515 */         byte[] scanLines = new byte[this.width * nRows * this.bytesPerPixel + nRows];
/*     */ 
/* 517 */         if (this.filter == 1)
/*     */         {
/* 519 */           this.leftBytes = new byte[16];
/*     */         }
/* 521 */         if (this.filter == 2)
/*     */         {
/* 523 */           this.priorRow = new byte[this.width * this.bytesPerPixel];
/*     */         }
/*     */ 
/* 526 */         int scanPos = 0;
/* 527 */         int startPos = 1;
/* 528 */         for (int i = 0; i < this.width * nRows; i++)
/*     */         {
/* 530 */           if (i % this.width == 0)
/*     */           {
/* 532 */             scanLines[(scanPos++)] = ((byte)this.filter);
/* 533 */             startPos = scanPos;
/*     */           }
/* 535 */           scanLines[(scanPos++)] = ((byte)(pixels[i] >> 16 & 0xFF));
/* 536 */           scanLines[(scanPos++)] = ((byte)(pixels[i] >> 8 & 0xFF));
/* 537 */           scanLines[(scanPos++)] = ((byte)(pixels[i] & 0xFF));
/* 538 */           if (this.encodeAlpha)
/*     */           {
/* 540 */             scanLines[(scanPos++)] = ((byte)(pixels[i] >> 24 & 0xFF));
/*     */           }
/* 542 */           if ((i % this.width == this.width - 1) && (this.filter != 0))
/*     */           {
/* 544 */             if (this.filter == 1)
/*     */             {
/* 546 */               filterSub(scanLines, startPos, this.width);
/*     */             }
/* 548 */             if (this.filter == 2)
/*     */             {
/* 550 */               filterUp(scanLines, startPos, this.width);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 558 */         compBytes.write(scanLines, 0, scanPos);
/*     */ 
/* 561 */         startRow += nRows;
/* 562 */         rowsLeft -= nRows;
/*     */       }
/* 564 */       compBytes.close();
/*     */ 
/* 569 */       byte[] compressedLines = outBytes.toByteArray();
/* 570 */       int nCompressed = compressedLines.length;
/*     */ 
/* 572 */       this.crc.reset();
/* 573 */       this.bytePos = writeInt4(nCompressed, this.bytePos);
/* 574 */       this.bytePos = writeString("IDAT", this.bytePos);
/* 575 */       this.crc.update("IDAT".getBytes());
/* 576 */       this.bytePos = writeBytes(compressedLines, nCompressed, this.bytePos);
/* 577 */       this.crc.update(compressedLines, 0, nCompressed);
/*     */ 
/* 579 */       this.crcValue = this.crc.getValue();
/* 580 */       this.bytePos = writeInt4((int)this.crcValue, this.bytePos);
/* 581 */       scrunch.finish();
/* 582 */       return true;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 586 */       System.err.println("WARNING: IO Error while trying to write PNG image.\n\n" + e.toString());
/* 587 */     }return false;
/*     */   }
/*     */ 
/*     */   void writeEnd()
/*     */   {
/* 596 */     this.bytePos = writeInt4(0, this.bytePos);
/* 597 */     this.bytePos = writeString("IEND", this.bytePos);
/* 598 */     this.crc.reset();
/* 599 */     this.crc.update("IEND".getBytes());
/* 600 */     this.crcValue = this.crc.getValue();
/* 601 */     this.bytePos = writeInt4((int)this.crcValue, this.bytePos);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.media.PNGEncoder
 * JD-Core Version:    0.6.2
 */