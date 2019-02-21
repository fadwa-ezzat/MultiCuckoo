/*     */ package sim.util;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.Raster;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.StringReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Scanner;
/*     */ import javax.imageio.ImageIO;
/*     */ 
/*     */ public class TableLoader
/*     */ {
/*     */   static String tokenizeString(InputStream stream)
/*     */     throws IOException
/*     */   {
/*  55 */     return tokenizeString(stream, false);
/*     */   }
/*     */ 
/*     */   static String tokenizeString(InputStream stream, boolean oneChar) throws IOException {
/*  61 */     int EOF = -1;
/*     */ 
/*  63 */     StringBuilder b = new StringBuilder();
/*     */ 
/*  65 */     boolean inComment = false;
/*     */     int c;
/*     */     while (true) {
/*  69 */       c = stream.read();
/*  70 */       if (c == -1) throw new IOException("Stream ended prematurely, before table reading was completed.");
/*  71 */       if (inComment)
/*     */       {
/*  73 */         if ((c == 13) || (c == 10)) {
/*  74 */           inComment = false;
/*     */         }
/*     */       }
/*  77 */       else if (!Character.isWhitespace((char)c))
/*     */       {
/*  79 */         if (c != 35) break;
/*  80 */         inComment = true;
/*     */       }
/*     */     }
/*  82 */     b.append((char)c);
/*     */ 
/*  85 */     if (oneChar) return b.toString();
/*     */ 
/*     */     while (true)
/*     */     {
/*  90 */       c = stream.read();
/*  91 */       if (c == -1) break;
/*  92 */       if (c == 35)
/*     */       {
/*     */         do
/*     */         {
/*  96 */           c = stream.read();
/*  97 */           if ((c == -1) || 
/*  98 */             (c == 13)) break;  } while (c != 10);
/*     */       }
/*     */       else
/*     */       {
/* 102 */         if (Character.isWhitespace((char)c))
/*     */           break;
/* 104 */         b.append((char)c);
/*     */       }
/*     */     }
/* 106 */     return b.toString();
/*     */   }
/*     */ 
/*     */   static int tokenizeInt(InputStream stream)
/*     */     throws IOException
/*     */   {
/* 113 */     return Integer.parseInt(tokenizeString(stream));
/*     */   }
/*     */ 
/*     */   public static int[][] loadPNMFile(InputStream str, boolean flipY)
/*     */     throws IOException
/*     */   {
/* 120 */     int[][] vals = loadPNMFile(str);
/* 121 */     if (flipY)
/*     */     {
/* 123 */       for (int i = 0; i < vals.length; i++)
/*     */       {
/* 125 */         int height = vals[i].length;
/* 126 */         for (int j = 0; j < height / 2; j++)
/*     */         {
/* 128 */           int temp = vals[i][j];
/* 129 */           vals[i][j] = vals[i][(height - j + 1)];
/* 130 */           vals[i][(height - j + 1)] = temp;
/*     */         }
/*     */       }
/*     */     }
/* 134 */     return vals;
/*     */   }
/*     */ 
/*     */   public static int[][] loadPNMFile(InputStream str)
/*     */     throws IOException
/*     */   {
/* 141 */     BufferedInputStream stream = new BufferedInputStream(str);
/* 142 */     String type = tokenizeString(stream);
/* 143 */     if (type.equals("P1"))
/* 144 */       return loadPlainPBM(stream);
/* 145 */     if (type.equals("P2"))
/* 146 */       return loadPlainPGM(stream);
/* 147 */     if (type.equals("P4"))
/* 148 */       return loadRawPBM(stream);
/* 149 */     if (type.equals("P5"))
/* 150 */       return loadRawPGM(stream);
/* 151 */     throw new IOException("Not a viable PBM or PGM stream");
/*     */   }
/*     */ 
/*     */   static int[][] loadPlainPGM(InputStream stream)
/*     */     throws IOException
/*     */   {
/* 158 */     int width = tokenizeInt(stream);
/* 159 */     int height = tokenizeInt(stream);
/* 160 */     int maxGray = tokenizeInt(stream);
/* 161 */     if (width < 0) throw new IOException("Invalid width in PGM: " + width);
/* 162 */     if (height < 0) throw new IOException("Invalid height in PGM: " + height);
/* 163 */     if (maxGray <= 0) throw new IOException("Invalid maximum value in PGM: " + maxGray);
/*     */ 
/* 165 */     int[][] field = new int[width][height];
/* 166 */     for (int i = 0; i < height; i++) {
/* 167 */       for (int j = 0; j < width; j++)
/* 168 */         field[j][i] = tokenizeInt(stream);
/*     */     }
/* 170 */     return field;
/*     */   }
/*     */ 
/*     */   static int[][] loadRawPGM(InputStream stream)
/*     */     throws IOException
/*     */   {
/* 177 */     int width = tokenizeInt(stream);
/* 178 */     int height = tokenizeInt(stream);
/* 179 */     int maxVal = tokenizeInt(stream);
/* 180 */     if (width < 0) throw new IOException("Invalid width: " + width);
/* 181 */     if (height < 0) throw new IOException("Invalid height: " + height);
/* 182 */     if (maxVal <= 0) throw new IOException("Invalid maximum value: " + maxVal);
/*     */ 
/* 187 */     int[][] field = new int[width][height];
/* 188 */     for (int i = 0; i < height; i++) {
/* 189 */       for (int j = 0; j < width; j++)
/*     */       {
/* 191 */         if (maxVal < 256)
/* 192 */           field[j][i] = stream.read();
/* 193 */         else if (maxVal < 65536)
/* 194 */           field[j][i] = (stream.read() << 8 & stream.read());
/* 195 */         else if (maxVal < 16777216)
/* 196 */           field[j][i] = (stream.read() << 16 & stream.read() << 8 & stream.read());
/*     */         else
/* 198 */           field[j][i] = (stream.read() << 24 & stream.read() << 16 & stream.read() << 8 & stream.read());
/*     */       }
/*     */     }
/* 201 */     return field;
/*     */   }
/*     */ 
/*     */   static int[][] loadPlainPBM(InputStream stream)
/*     */     throws IOException
/*     */   {
/* 207 */     int width = tokenizeInt(stream);
/* 208 */     int height = tokenizeInt(stream);
/* 209 */     if (width < 0) throw new IOException("Invalid width in PBM: " + width);
/* 210 */     if (height < 0) throw new IOException("Invalid height in PBM: " + height);
/*     */ 
/* 212 */     int[][] field = new int[width][height];
/* 213 */     for (int i = 0; i < height; i++) {
/* 214 */       for (int j = 0; j < width; j++)
/*     */       {
/* 216 */         String s = tokenizeString(stream, true);
/* 217 */         if (s.equals("0")) field[j][i] = 0;
/* 218 */         else if (s.equals("1")) field[j][i] = 1; else
/* 219 */           throw new IOException("Invalid byte data in PBM");
/*     */       }
/*     */     }
/* 222 */     return field;
/*     */   }
/*     */ 
/*     */   static int[][] loadRawPBM(InputStream stream)
/*     */     throws IOException
/*     */   {
/* 228 */     int width = tokenizeInt(stream);
/* 229 */     int height = tokenizeInt(stream);
/* 230 */     if (width < 0) throw new IOException("Invalid width in PBM: " + width);
/* 231 */     if (height < 0) throw new IOException("Invalid height in PBM: " + height);
/*     */ 
/* 236 */     int[][] field = new int[width][height];
/* 237 */     for (int i = 0; i < height; i++)
/*     */     {
/* 239 */       int data = 0;
/* 240 */       int count = 0;
/* 241 */       for (int j = 0; j < width; j++)
/*     */       {
/* 243 */         if (count == 0) { data = stream.read(); count = 8; }
/* 244 */         count--;
/* 245 */         field[j][i] = (data >> count & 0x1);
/*     */       }
/*     */     }
/*     */ 
/* 249 */     return field;
/*     */   }
/*     */ 
/*     */   public static double[][] loadTextFile(InputStream str, boolean flipY)
/*     */     throws RuntimeException, IOException
/*     */   {
/* 257 */     double[][] vals = loadTextFile(str);
/* 258 */     if (flipY)
/*     */     {
/* 260 */       for (int i = 0; i < vals.length; i++)
/*     */       {
/* 262 */         int height = vals[i].length;
/* 263 */         for (int j = 0; j < height / 2; j++)
/*     */         {
/* 265 */           double temp = vals[i][j];
/* 266 */           vals[i][j] = vals[i][(height - j + 1)];
/* 267 */           vals[i][(height - j + 1)] = temp;
/*     */         }
/*     */       }
/*     */     }
/* 271 */     return vals;
/*     */   }
/*     */ 
/*     */   public static double[][] loadTextFile(InputStream stream)
/*     */     throws IOException
/*     */   {
/* 278 */     Scanner scan = new Scanner(stream);
/*     */ 
/* 280 */     ArrayList rows = new ArrayList();
/* 281 */     int width = -1;
/*     */ 
/* 283 */     while (scan.hasNextLine())
/*     */     {
/* 285 */       String srow = scan.nextLine().trim();
/* 286 */       if (srow.length() > 0)
/*     */       {
/* 288 */         int w = 0;
/* 289 */         if (width == -1)
/*     */         {
/* 291 */           ArrayList firstRow = new ArrayList();
/* 292 */           Scanner rowScan = new Scanner(new StringReader(srow));
/* 293 */           while (rowScan.hasNextDouble())
/*     */           {
/* 295 */             firstRow.add(new Double(rowScan.nextDouble()));
/* 296 */             w++;
/*     */           }
/* 298 */           width = w;
/* 299 */           double[] row = new double[width];
/* 300 */           for (int i = 0; i < width; i++)
/* 301 */             row[i] = ((Double)(Double)firstRow.get(i)).doubleValue();
/* 302 */           rows.add(row);
/*     */         }
/*     */         else
/*     */         {
/* 306 */           double[] row = new double[width];
/* 307 */           Scanner rowScan = new Scanner(new StringReader(srow));
/* 308 */           while (rowScan.hasNextDouble())
/*     */           {
/* 310 */             if (w == width)
/* 311 */               throw new IOException("Row lengths do not match in text file");
/* 312 */             row[w] = rowScan.nextDouble();
/* 313 */             w++;
/*     */           }
/* 315 */           if (w < width)
/* 316 */             throw new IOException("Row lengths do not match in text file");
/* 317 */           rows.add(row);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 322 */     if (width == -1) {
/* 323 */       return new double[0][0];
/*     */     }
/* 325 */     double[][] fieldTransposed = new double[rows.size()][];
/* 326 */     for (int i = 0; i < rows.size(); i++) {
/* 327 */       fieldTransposed[i] = ((double[])(double[])rows.get(i));
/*     */     }
/*     */ 
/* 330 */     double[][] field = new double[width][fieldTransposed.length];
/* 331 */     for (int i = 0; i < field.length; i++) {
/* 332 */       for (int j = 0; j < field[i].length; j++)
/* 333 */         field[i][j] = fieldTransposed[j][i];
/*     */     }
/* 335 */     return field;
/*     */   }
/*     */ 
/*     */   public static int[][] loadGIFFile(InputStream str, boolean flipY)
/*     */     throws IOException
/*     */   {
/* 344 */     return loadPNGFile(str, flipY);
/*     */   }
/*     */ 
/*     */   public static int[][] loadGIFFile(InputStream str)
/*     */     throws IOException
/*     */   {
/* 351 */     return loadPNGFile(str);
/*     */   }
/*     */ 
/*     */   public static int[][] loadPNGFile(InputStream str, boolean flipY)
/*     */     throws IOException
/*     */   {
/* 360 */     int[][] vals = loadPNGFile(str);
/* 361 */     if (flipY)
/*     */     {
/* 363 */       for (int i = 0; i < vals.length; i++)
/*     */       {
/* 365 */         int height = vals[i].length;
/* 366 */         for (int j = 0; j < height / 2; j++)
/*     */         {
/* 368 */           int temp = vals[i][j];
/* 369 */           vals[i][j] = vals[i][(height - j + 1)];
/* 370 */           vals[i][(height - j + 1)] = temp;
/*     */         }
/*     */       }
/*     */     }
/* 374 */     return vals;
/*     */   }
/*     */ 
/*     */   public static int[][] loadPNGFile(InputStream str)
/*     */     throws IOException
/*     */   {
/* 383 */     BufferedImage image = ImageIO.read(str);
/*     */ 
/* 386 */     int type = image.getType();
/* 387 */     if ((type == 12) || (type == 10))
/*     */     {
/* 389 */       int w = image.getWidth();
/* 390 */       int h = image.getHeight();
/* 391 */       int[][] result = new int[w][h];
/*     */ 
/* 393 */       for (int i = 0; i < w; i++)
/* 394 */         for (int j = 0; j < h; j++)
/* 395 */           result[i][j] = (image.getRGB(i, j) & 0xFF);
/* 396 */       return result;
/*     */     }
/* 398 */     if (type == 13)
/*     */     {
/* 400 */       Raster raster = image.getRaster();
/* 401 */       if (raster.getTransferType() != 0)
/* 402 */         throw new IOException("Input Stream must contain an image with byte data if indexed.");
/* 403 */       byte[] pixel = new byte[1];
/* 404 */       int w = image.getWidth();
/* 405 */       int h = image.getHeight();
/* 406 */       int[][] result = new int[w][h];
/*     */ 
/* 408 */       for (int i = 0; i < w; i++)
/* 409 */         for (int j = 0; j < h; j++)
/*     */         {
/* 411 */           result[i][j] = ((byte[])(byte[])raster.getDataElements(i, j, pixel))[0];
/* 412 */           if (result[i][j] < 0) result[i][j] += 256;
/*     */         }
/* 414 */       return result;
/*     */     }
/*     */ 
/* 419 */     throw new IOException("Input Stream must contain a binary, byte-sized grayscale, or byte-sized indexed color scheme: " + image);
/*     */   }
/*     */ 
/*     */   public static int[][] convertToIntArray(double[][] vals)
/*     */   {
/* 427 */     int[][] ret = new int[vals.length][];
/* 428 */     for (int i = 0; i < vals.length; i++)
/*     */     {
/* 430 */       double[] valsi = vals[i];
/* 431 */       int[] reti = ret[i] =  = new int[valsi.length];
/* 432 */       for (int j = 0; j < valsi.length; j++)
/*     */       {
/* 434 */         int a = (int)valsi[j];
/* 435 */         if (a == valsi[j])
/* 436 */           reti[j] = a;
/* 437 */         else return (int[][])null;
/*     */       }
/*     */     }
/* 440 */     return ret;
/*     */   }
/*     */ 
/*     */   public static double[][] convertToDoubleArray(int[][] vals)
/*     */   {
/* 446 */     double[][] ret = new double[vals.length][];
/* 447 */     for (int i = 0; i < vals.length; i++)
/*     */     {
/* 449 */       int[] valsi = vals[i];
/* 450 */       double[] reti = ret[i] =  = new double[valsi.length];
/* 451 */       for (int j = 0; j < valsi.length; j++)
/* 452 */         reti[j] = valsi[j];
/*     */     }
/* 454 */     return ret;
/*     */   }
/*     */ 
/*     */   public static long[][] convertToLongArray(double[][] vals)
/*     */   {
/* 461 */     long[][] ret = new long[vals.length][];
/* 462 */     for (int i = 0; i < vals.length; i++)
/*     */     {
/* 464 */       double[] valsi = vals[i];
/* 465 */       long[] reti = ret[i] =  = new long[valsi.length];
/* 466 */       for (int j = 0; j < valsi.length; j++)
/*     */       {
/* 468 */         long a = ()valsi[j];
/* 469 */         if (a == valsi[j])
/* 470 */           reti[j] = a;
/* 471 */         else return (long[][])null;
/*     */       }
/*     */     }
/* 474 */     return ret;
/*     */   }
/*     */ 
/*     */   public static long[][] convertToLongArray(int[][] vals)
/*     */   {
/* 480 */     long[][] ret = new long[vals.length][];
/* 481 */     for (int i = 0; i < vals.length; i++)
/*     */     {
/* 483 */       int[] valsi = vals[i];
/* 484 */       long[] reti = ret[i] =  = new long[valsi.length];
/* 485 */       for (int j = 0; j < valsi.length; j++)
/* 486 */         reti[j] = valsi[j];
/*     */     }
/* 488 */     return ret;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.TableLoader
 * JD-Core Version:    0.6.2
 */