/*      */ package ec.util;
/*      */ 
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Random;
/*      */ 
/*      */ public class MersenneTwisterFast
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   private static final long serialVersionUID = -8219700664442619525L;
/*      */   private static final int N = 624;
/*      */   private static final int M = 397;
/*      */   private static final int MATRIX_A = -1727483681;
/*      */   private static final int UPPER_MASK = -2147483648;
/*      */   private static final int LOWER_MASK = 2147483647;
/*      */   private static final int TEMPERING_MASK_B = -1658038656;
/*      */   private static final int TEMPERING_MASK_C = -272236544;
/*      */   private int[] mt;
/*      */   private int mti;
/*      */   private int[] mag01;
/*      */   private double __nextNextGaussian;
/*      */   private boolean __haveNextNextGaussian;
/*      */ 
/*      */   public strictfp Object clone()
/*      */   {
/*      */     try
/*      */     {
/*  216 */       MersenneTwisterFast f = (MersenneTwisterFast)super.clone();
/*  217 */       f.mt = ((int[])this.mt.clone());
/*  218 */       f.mag01 = ((int[])this.mag01.clone());
/*  219 */       return f; } catch (CloneNotSupportedException e) {
/*      */     }
/*  221 */     throw new InternalError();
/*      */   }
/*      */ 
/*      */   public strictfp boolean stateEquals(MersenneTwisterFast other)
/*      */   {
/*  233 */     if (other == this) return true;
/*  234 */     if (other == null) return false;
/*      */ 
/*  236 */     if (this.mti != other.mti) return false;
/*  237 */     for (int x = 0; x < this.mag01.length; x++)
/*  238 */       if (this.mag01[x] != other.mag01[x]) return false;
/*  239 */     for (int x = 0; x < this.mt.length; x++)
/*  240 */       if (this.mt[x] != other.mt[x]) return false;
/*  241 */     return true;
/*      */   }
/*      */ 
/*      */   public strictfp void readState(DataInputStream stream)
/*      */     throws IOException
/*      */   {
/*  247 */     int len = this.mt.length;
/*  248 */     for (int x = 0; x < len; x++) this.mt[x] = stream.readInt();
/*      */ 
/*  250 */     len = this.mag01.length;
/*  251 */     for (int x = 0; x < len; x++) this.mag01[x] = stream.readInt();
/*      */ 
/*  253 */     this.mti = stream.readInt();
/*  254 */     this.__nextNextGaussian = stream.readDouble();
/*  255 */     this.__haveNextNextGaussian = stream.readBoolean();
/*      */   }
/*      */ 
/*      */   public strictfp void writeState(DataOutputStream stream)
/*      */     throws IOException
/*      */   {
/*  261 */     int len = this.mt.length;
/*  262 */     for (int x = 0; x < len; x++) stream.writeInt(this.mt[x]);
/*      */ 
/*  264 */     len = this.mag01.length;
/*  265 */     for (int x = 0; x < len; x++) stream.writeInt(this.mag01[x]);
/*      */ 
/*  267 */     stream.writeInt(this.mti);
/*  268 */     stream.writeDouble(this.__nextNextGaussian);
/*  269 */     stream.writeBoolean(this.__haveNextNextGaussian);
/*      */   }
/*      */ 
/*      */   public strictfp MersenneTwisterFast()
/*      */   {
/*  277 */     this(System.currentTimeMillis());
/*      */   }
/*      */ 
/*      */   public strictfp MersenneTwisterFast(long seed)
/*      */   {
/*  287 */     setSeed(seed);
/*      */   }
/*      */ 
/*      */   public strictfp MersenneTwisterFast(int[] array)
/*      */   {
/*  299 */     setSeed(array);
/*      */   }
/*      */ 
/*      */   public strictfp void setSeed(long seed)
/*      */   {
/*  313 */     this.__haveNextNextGaussian = false;
/*      */ 
/*  315 */     this.mt = new int[624];
/*      */ 
/*  317 */     this.mag01 = new int[2];
/*  318 */     this.mag01[0] = 0;
/*  319 */     this.mag01[1] = -1727483681;
/*      */ 
/*  321 */     this.mt[0] = ((int)(seed & 0xFFFFFFFF));
/*  322 */     for (this.mti = 1; this.mti < 624; this.mti += 1)
/*      */     {
/*  324 */       this.mt[this.mti] = (1812433253 * (this.mt[(this.mti - 1)] ^ this.mt[(this.mti - 1)] >>> 30) + this.mti);
/*      */     }
/*      */   }
/*      */ 
/*      */   public strictfp void setSeed(int[] array)
/*      */   {
/*  345 */     if (array.length == 0) {
/*  346 */       throw new IllegalArgumentException("Array length must be greater than zero");
/*      */     }
/*  348 */     setSeed(19650218L);
/*  349 */     int i = 1; int j = 0;
/*  350 */     for (int k = 624 > array.length ? 624 : array.length; 
/*  351 */       k != 0; k--)
/*      */     {
/*  353 */       this.mt[i] = ((this.mt[i] ^ (this.mt[(i - 1)] ^ this.mt[(i - 1)] >>> 30) * 1664525) + array[j] + j);
/*      */ 
/*  355 */       i++;
/*  356 */       j++;
/*  357 */       if (i >= 624) { this.mt[0] = this.mt[623]; i = 1; }
/*  358 */       if (j >= array.length) j = 0;
/*      */     }
/*  360 */     for (k = 623; k != 0; k--)
/*      */     {
/*  362 */       this.mt[i] = ((this.mt[i] ^ (this.mt[(i - 1)] ^ this.mt[(i - 1)] >>> 30) * 1566083941) - i);
/*      */ 
/*  364 */       i++;
/*  365 */       if (i >= 624)
/*      */       {
/*  367 */         this.mt[0] = this.mt[623]; i = 1;
/*      */       }
/*      */     }
/*  370 */     this.mt[0] = -2147483648;
/*      */   }
/*      */ 
/*      */   public strictfp int nextInt()
/*      */   {
/*  378 */     if (this.mti >= 624)
/*      */     {
/*  381 */       int[] mt = this.mt;
/*  382 */       int[] mag01 = this.mag01;
/*      */ 
/*  384 */       for (int kk = 0; kk < 227; kk++)
/*      */       {
/*  386 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  387 */         mt[kk] = (mt[(kk + 397)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  389 */       for (; kk < 623; kk++)
/*      */       {
/*  391 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  392 */         mt[kk] = (mt[(kk + -227)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  394 */       int y = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  395 */       mt[623] = (mt[396] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */ 
/*  397 */       this.mti = 0;
/*      */     }
/*      */ 
/*  400 */     int y = this.mt[(this.mti++)];
/*  401 */     y ^= y >>> 11;
/*  402 */     y ^= y << 7 & 0x9D2C5680;
/*  403 */     y ^= y << 15 & 0xEFC60000;
/*  404 */     y ^= y >>> 18;
/*      */ 
/*  406 */     return y;
/*      */   }
/*      */ 
/*      */   public strictfp short nextShort()
/*      */   {
/*  415 */     if (this.mti >= 624)
/*      */     {
/*  418 */       int[] mt = this.mt;
/*  419 */       int[] mag01 = this.mag01;
/*      */ 
/*  421 */       for (int kk = 0; kk < 227; kk++)
/*      */       {
/*  423 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  424 */         mt[kk] = (mt[(kk + 397)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  426 */       for (; kk < 623; kk++)
/*      */       {
/*  428 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  429 */         mt[kk] = (mt[(kk + -227)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  431 */       int y = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  432 */       mt[623] = (mt[396] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */ 
/*  434 */       this.mti = 0;
/*      */     }
/*      */ 
/*  437 */     int y = this.mt[(this.mti++)];
/*  438 */     y ^= y >>> 11;
/*  439 */     y ^= y << 7 & 0x9D2C5680;
/*  440 */     y ^= y << 15 & 0xEFC60000;
/*  441 */     y ^= y >>> 18;
/*      */ 
/*  443 */     return (short)(y >>> 16);
/*      */   }
/*      */ 
/*      */   public strictfp char nextChar()
/*      */   {
/*  452 */     if (this.mti >= 624)
/*      */     {
/*  455 */       int[] mt = this.mt;
/*  456 */       int[] mag01 = this.mag01;
/*      */ 
/*  458 */       for (int kk = 0; kk < 227; kk++)
/*      */       {
/*  460 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  461 */         mt[kk] = (mt[(kk + 397)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  463 */       for (; kk < 623; kk++)
/*      */       {
/*  465 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  466 */         mt[kk] = (mt[(kk + -227)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  468 */       int y = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  469 */       mt[623] = (mt[396] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */ 
/*  471 */       this.mti = 0;
/*      */     }
/*      */ 
/*  474 */     int y = this.mt[(this.mti++)];
/*  475 */     y ^= y >>> 11;
/*  476 */     y ^= y << 7 & 0x9D2C5680;
/*  477 */     y ^= y << 15 & 0xEFC60000;
/*  478 */     y ^= y >>> 18;
/*      */ 
/*  480 */     return (char)(y >>> 16);
/*      */   }
/*      */ 
/*      */   public strictfp boolean nextBoolean()
/*      */   {
/*  488 */     if (this.mti >= 624)
/*      */     {
/*  491 */       int[] mt = this.mt;
/*  492 */       int[] mag01 = this.mag01;
/*      */ 
/*  494 */       for (int kk = 0; kk < 227; kk++)
/*      */       {
/*  496 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  497 */         mt[kk] = (mt[(kk + 397)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  499 */       for (; kk < 623; kk++)
/*      */       {
/*  501 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  502 */         mt[kk] = (mt[(kk + -227)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  504 */       int y = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  505 */       mt[623] = (mt[396] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */ 
/*  507 */       this.mti = 0;
/*      */     }
/*      */ 
/*  510 */     int y = this.mt[(this.mti++)];
/*  511 */     y ^= y >>> 11;
/*  512 */     y ^= y << 7 & 0x9D2C5680;
/*  513 */     y ^= y << 15 & 0xEFC60000;
/*  514 */     y ^= y >>> 18;
/*      */ 
/*  516 */     return y >>> 31 != 0;
/*      */   }
/*      */ 
/*      */   public strictfp boolean nextBoolean(float probability)
/*      */   {
/*  531 */     if ((probability < 0.0F) || (probability > 1.0F))
/*  532 */       throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.");
/*  533 */     if (probability == 0.0F) return false;
/*  534 */     if (probability == 1.0F) return true;
/*  535 */     if (this.mti >= 624)
/*      */     {
/*  538 */       int[] mt = this.mt;
/*  539 */       int[] mag01 = this.mag01;
/*      */ 
/*  541 */       for (int kk = 0; kk < 227; kk++)
/*      */       {
/*  543 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  544 */         mt[kk] = (mt[(kk + 397)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  546 */       for (; kk < 623; kk++)
/*      */       {
/*  548 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  549 */         mt[kk] = (mt[(kk + -227)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  551 */       int y = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  552 */       mt[623] = (mt[396] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */ 
/*  554 */       this.mti = 0;
/*      */     }
/*      */ 
/*  557 */     int y = this.mt[(this.mti++)];
/*  558 */     y ^= y >>> 11;
/*  559 */     y ^= y << 7 & 0x9D2C5680;
/*  560 */     y ^= y << 15 & 0xEFC60000;
/*  561 */     y ^= y >>> 18;
/*      */ 
/*  563 */     return (y >>> 8) / 16777216.0F < probability;
/*      */   }
/*      */ 
/*      */   public strictfp boolean nextBoolean(double probability)
/*      */   {
/*  576 */     if ((probability < 0.0D) || (probability > 1.0D))
/*  577 */       throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.");
/*  578 */     if (probability == 0.0D) return false;
/*  579 */     if (probability == 1.0D) return true;
/*  580 */     if (this.mti >= 624)
/*      */     {
/*  583 */       int[] mt = this.mt;
/*  584 */       int[] mag01 = this.mag01;
/*      */ 
/*  586 */       for (int kk = 0; kk < 227; kk++)
/*      */       {
/*  588 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  589 */         mt[kk] = (mt[(kk + 397)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  591 */       for (; kk < 623; kk++)
/*      */       {
/*  593 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  594 */         mt[kk] = (mt[(kk + -227)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  596 */       int y = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  597 */       mt[623] = (mt[396] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */ 
/*  599 */       this.mti = 0;
/*      */     }
/*      */ 
/*  602 */     int y = this.mt[(this.mti++)];
/*  603 */     y ^= y >>> 11;
/*  604 */     y ^= y << 7 & 0x9D2C5680;
/*  605 */     y ^= y << 15 & 0xEFC60000;
/*  606 */     y ^= y >>> 18;
/*      */ 
/*  608 */     if (this.mti >= 624)
/*      */     {
/*  611 */       int[] mt = this.mt;
/*  612 */       int[] mag01 = this.mag01;
/*      */ 
/*  614 */       for (int kk = 0; kk < 227; kk++)
/*      */       {
/*  616 */         int z = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  617 */         mt[kk] = (mt[(kk + 397)] ^ z >>> 1 ^ mag01[(z & 0x1)]);
/*      */       }
/*  619 */       for (; kk < 623; kk++)
/*      */       {
/*  621 */         int z = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  622 */         mt[kk] = (mt[(kk + -227)] ^ z >>> 1 ^ mag01[(z & 0x1)]);
/*      */       }
/*  624 */       int z = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  625 */       mt[623] = (mt[396] ^ z >>> 1 ^ mag01[(z & 0x1)]);
/*      */ 
/*  627 */       this.mti = 0;
/*      */     }
/*      */ 
/*  630 */     int z = this.mt[(this.mti++)];
/*  631 */     z ^= z >>> 11;
/*  632 */     z ^= z << 7 & 0x9D2C5680;
/*  633 */     z ^= z << 15 & 0xEFC60000;
/*  634 */     z ^= z >>> 18;
/*      */ 
/*  637 */     return ((y >>> 6 << 27) + (z >>> 5)) / 9007199254740992.0D < probability;
/*      */   }
/*      */ 
/*      */   public strictfp byte nextByte()
/*      */   {
/*  645 */     if (this.mti >= 624)
/*      */     {
/*  648 */       int[] mt = this.mt;
/*  649 */       int[] mag01 = this.mag01;
/*      */ 
/*  651 */       for (int kk = 0; kk < 227; kk++)
/*      */       {
/*  653 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  654 */         mt[kk] = (mt[(kk + 397)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  656 */       for (; kk < 623; kk++)
/*      */       {
/*  658 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  659 */         mt[kk] = (mt[(kk + -227)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  661 */       int y = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  662 */       mt[623] = (mt[396] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */ 
/*  664 */       this.mti = 0;
/*      */     }
/*      */ 
/*  667 */     int y = this.mt[(this.mti++)];
/*  668 */     y ^= y >>> 11;
/*  669 */     y ^= y << 7 & 0x9D2C5680;
/*  670 */     y ^= y << 15 & 0xEFC60000;
/*  671 */     y ^= y >>> 18;
/*      */ 
/*  673 */     return (byte)(y >>> 24);
/*      */   }
/*      */ 
/*      */   public strictfp void nextBytes(byte[] bytes)
/*      */   {
/*  681 */     for (int x = 0; x < bytes.length; x++)
/*      */     {
/*  683 */       if (this.mti >= 624)
/*      */       {
/*  686 */         int[] mt = this.mt;
/*  687 */         int[] mag01 = this.mag01;
/*      */ 
/*  689 */         for (int kk = 0; kk < 227; kk++)
/*      */         {
/*  691 */           int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  692 */           mt[kk] = (mt[(kk + 397)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */         }
/*  694 */         for (; kk < 623; kk++)
/*      */         {
/*  696 */           int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  697 */           mt[kk] = (mt[(kk + -227)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */         }
/*  699 */         int y = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  700 */         mt[623] = (mt[396] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */ 
/*  702 */         this.mti = 0;
/*      */       }
/*      */ 
/*  705 */       int y = this.mt[(this.mti++)];
/*  706 */       y ^= y >>> 11;
/*  707 */       y ^= y << 7 & 0x9D2C5680;
/*  708 */       y ^= y << 15 & 0xEFC60000;
/*  709 */       y ^= y >>> 18;
/*      */ 
/*  711 */       bytes[x] = ((byte)(y >>> 24));
/*      */     }
/*      */   }
/*      */ 
/*      */   public strictfp long nextLong()
/*      */   {
/*  721 */     if (this.mti >= 624)
/*      */     {
/*  724 */       int[] mt = this.mt;
/*  725 */       int[] mag01 = this.mag01;
/*      */ 
/*  727 */       for (int kk = 0; kk < 227; kk++)
/*      */       {
/*  729 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  730 */         mt[kk] = (mt[(kk + 397)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  732 */       for (; kk < 623; kk++)
/*      */       {
/*  734 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  735 */         mt[kk] = (mt[(kk + -227)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  737 */       int y = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  738 */       mt[623] = (mt[396] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */ 
/*  740 */       this.mti = 0;
/*      */     }
/*      */ 
/*  743 */     int y = this.mt[(this.mti++)];
/*  744 */     y ^= y >>> 11;
/*  745 */     y ^= y << 7 & 0x9D2C5680;
/*  746 */     y ^= y << 15 & 0xEFC60000;
/*  747 */     y ^= y >>> 18;
/*      */ 
/*  749 */     if (this.mti >= 624)
/*      */     {
/*  752 */       int[] mt = this.mt;
/*  753 */       int[] mag01 = this.mag01;
/*      */ 
/*  755 */       for (int kk = 0; kk < 227; kk++)
/*      */       {
/*  757 */         int z = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  758 */         mt[kk] = (mt[(kk + 397)] ^ z >>> 1 ^ mag01[(z & 0x1)]);
/*      */       }
/*  760 */       for (; kk < 623; kk++)
/*      */       {
/*  762 */         int z = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  763 */         mt[kk] = (mt[(kk + -227)] ^ z >>> 1 ^ mag01[(z & 0x1)]);
/*      */       }
/*  765 */       int z = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  766 */       mt[623] = (mt[396] ^ z >>> 1 ^ mag01[(z & 0x1)]);
/*      */ 
/*  768 */       this.mti = 0;
/*      */     }
/*      */ 
/*  771 */     int z = this.mt[(this.mti++)];
/*  772 */     z ^= z >>> 11;
/*  773 */     z ^= z << 7 & 0x9D2C5680;
/*  774 */     z ^= z << 15 & 0xEFC60000;
/*  775 */     z ^= z >>> 18;
/*      */ 
/*  777 */     return (y << 32) + z;
/*      */   }
/*      */ 
/*      */   public strictfp long nextLong(long n)
/*      */   {
/*  786 */     if (n <= 0L) {
/*  787 */       throw new IllegalArgumentException("n must be positive, got: " + n);
/*      */     }
/*      */ 
/*      */     long bits;
/*      */     long val;
/*      */     do
/*      */     {
/*  795 */       if (this.mti >= 624)
/*      */       {
/*  798 */         int[] mt = this.mt;
/*  799 */         int[] mag01 = this.mag01;
/*      */ 
/*  801 */         for (int kk = 0; kk < 227; kk++)
/*      */         {
/*  803 */           int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  804 */           mt[kk] = (mt[(kk + 397)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */         }
/*  806 */         for (; kk < 623; kk++)
/*      */         {
/*  808 */           int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  809 */           mt[kk] = (mt[(kk + -227)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */         }
/*  811 */         int y = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  812 */         mt[623] = (mt[396] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */ 
/*  814 */         this.mti = 0;
/*      */       }
/*      */ 
/*  817 */       int y = this.mt[(this.mti++)];
/*  818 */       y ^= y >>> 11;
/*  819 */       y ^= y << 7 & 0x9D2C5680;
/*  820 */       y ^= y << 15 & 0xEFC60000;
/*  821 */       y ^= y >>> 18;
/*      */ 
/*  823 */       if (this.mti >= 624)
/*      */       {
/*  826 */         int[] mt = this.mt;
/*  827 */         int[] mag01 = this.mag01;
/*      */ 
/*  829 */         for (int kk = 0; kk < 227; kk++)
/*      */         {
/*  831 */           int z = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  832 */           mt[kk] = (mt[(kk + 397)] ^ z >>> 1 ^ mag01[(z & 0x1)]);
/*      */         }
/*  834 */         for (; kk < 623; kk++)
/*      */         {
/*  836 */           int z = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  837 */           mt[kk] = (mt[(kk + -227)] ^ z >>> 1 ^ mag01[(z & 0x1)]);
/*      */         }
/*  839 */         int z = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  840 */         mt[623] = (mt[396] ^ z >>> 1 ^ mag01[(z & 0x1)]);
/*      */ 
/*  842 */         this.mti = 0;
/*      */       }
/*      */ 
/*  845 */       int z = this.mt[(this.mti++)];
/*  846 */       z ^= z >>> 11;
/*  847 */       z ^= z << 7 & 0x9D2C5680;
/*  848 */       z ^= z << 15 & 0xEFC60000;
/*  849 */       z ^= z >>> 18;
/*      */ 
/*  851 */       bits = (y << 32) + z >>> 1;
/*  852 */       val = bits % n;
/*  853 */     }while (bits - val + (n - 1L) < 0L);
/*  854 */     return val;
/*      */   }
/*      */ 
/*      */   public strictfp double nextDouble()
/*      */   {
/*  864 */     if (this.mti >= 624)
/*      */     {
/*  867 */       int[] mt = this.mt;
/*  868 */       int[] mag01 = this.mag01;
/*      */ 
/*  870 */       for (int kk = 0; kk < 227; kk++)
/*      */       {
/*  872 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  873 */         mt[kk] = (mt[(kk + 397)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  875 */       for (; kk < 623; kk++)
/*      */       {
/*  877 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  878 */         mt[kk] = (mt[(kk + -227)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/*  880 */       int y = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  881 */       mt[623] = (mt[396] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */ 
/*  883 */       this.mti = 0;
/*      */     }
/*      */ 
/*  886 */     int y = this.mt[(this.mti++)];
/*  887 */     y ^= y >>> 11;
/*  888 */     y ^= y << 7 & 0x9D2C5680;
/*  889 */     y ^= y << 15 & 0xEFC60000;
/*  890 */     y ^= y >>> 18;
/*      */ 
/*  892 */     if (this.mti >= 624)
/*      */     {
/*  895 */       int[] mt = this.mt;
/*  896 */       int[] mag01 = this.mag01;
/*      */ 
/*  898 */       for (int kk = 0; kk < 227; kk++)
/*      */       {
/*  900 */         int z = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  901 */         mt[kk] = (mt[(kk + 397)] ^ z >>> 1 ^ mag01[(z & 0x1)]);
/*      */       }
/*  903 */       for (; kk < 623; kk++)
/*      */       {
/*  905 */         int z = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  906 */         mt[kk] = (mt[(kk + -227)] ^ z >>> 1 ^ mag01[(z & 0x1)]);
/*      */       }
/*  908 */       int z = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  909 */       mt[623] = (mt[396] ^ z >>> 1 ^ mag01[(z & 0x1)]);
/*      */ 
/*  911 */       this.mti = 0;
/*      */     }
/*      */ 
/*  914 */     int z = this.mt[(this.mti++)];
/*  915 */     z ^= z >>> 11;
/*  916 */     z ^= z << 7 & 0x9D2C5680;
/*  917 */     z ^= z << 15 & 0xEFC60000;
/*  918 */     z ^= z >>> 18;
/*      */ 
/*  921 */     return ((y >>> 6 << 27) + (z >>> 5)) / 9007199254740992.0D;
/*      */   }
/*      */ 
/*      */   public strictfp double nextDouble(boolean includeZero, boolean includeOne)
/*      */   {
/*  940 */     double d = 0.0D;
/*      */     do
/*      */     {
/*  943 */       d = nextDouble();
/*  944 */       if ((includeOne) && (nextBoolean())) d += 1.0D;
/*      */     }
/*  946 */     while ((d > 1.0D) || ((!includeZero) && (d == 0.0D)));
/*      */ 
/*  948 */     return d;
/*      */   }
/*      */ 
/*      */   public strictfp void clearGaussian()
/*      */   {
/*  957 */     this.__haveNextNextGaussian = false;
/*      */   }
/*      */ 
/*      */   public strictfp double nextGaussian()
/*      */   {
/*  962 */     if (this.__haveNextNextGaussian)
/*      */     {
/*  964 */       this.__haveNextNextGaussian = false;
/*  965 */       return this.__nextNextGaussian;
/*      */     }
/*      */ 
/*      */     double v1;
/*      */     double v2;
/*      */     double s;
/*      */     do
/*      */     {
/*  977 */       if (this.mti >= 624)
/*      */       {
/*  980 */         int[] mt = this.mt;
/*  981 */         int[] mag01 = this.mag01;
/*      */ 
/*  983 */         for (int kk = 0; kk < 227; kk++)
/*      */         {
/*  985 */           int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  986 */           mt[kk] = (mt[(kk + 397)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */         }
/*  988 */         for (; kk < 623; kk++)
/*      */         {
/*  990 */           int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/*  991 */           mt[kk] = (mt[(kk + -227)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */         }
/*  993 */         int y = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/*  994 */         mt[623] = (mt[396] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */ 
/*  996 */         this.mti = 0;
/*      */       }
/*      */ 
/*  999 */       int y = this.mt[(this.mti++)];
/* 1000 */       y ^= y >>> 11;
/* 1001 */       y ^= y << 7 & 0x9D2C5680;
/* 1002 */       y ^= y << 15 & 0xEFC60000;
/* 1003 */       y ^= y >>> 18;
/*      */ 
/* 1005 */       if (this.mti >= 624)
/*      */       {
/* 1008 */         int[] mt = this.mt;
/* 1009 */         int[] mag01 = this.mag01;
/*      */ 
/* 1011 */         for (int kk = 0; kk < 227; kk++)
/*      */         {
/* 1013 */           int z = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/* 1014 */           mt[kk] = (mt[(kk + 397)] ^ z >>> 1 ^ mag01[(z & 0x1)]);
/*      */         }
/* 1016 */         for (; kk < 623; kk++)
/*      */         {
/* 1018 */           int z = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/* 1019 */           mt[kk] = (mt[(kk + -227)] ^ z >>> 1 ^ mag01[(z & 0x1)]);
/*      */         }
/* 1021 */         int z = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/* 1022 */         mt[623] = (mt[396] ^ z >>> 1 ^ mag01[(z & 0x1)]);
/*      */ 
/* 1024 */         this.mti = 0;
/*      */       }
/*      */ 
/* 1027 */       int z = this.mt[(this.mti++)];
/* 1028 */       z ^= z >>> 11;
/* 1029 */       z ^= z << 7 & 0x9D2C5680;
/* 1030 */       z ^= z << 15 & 0xEFC60000;
/* 1031 */       z ^= z >>> 18;
/*      */ 
/* 1033 */       if (this.mti >= 624)
/*      */       {
/* 1036 */         int[] mt = this.mt;
/* 1037 */         int[] mag01 = this.mag01;
/*      */ 
/* 1039 */         for (int kk = 0; kk < 227; kk++)
/*      */         {
/* 1041 */           int a = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/* 1042 */           mt[kk] = (mt[(kk + 397)] ^ a >>> 1 ^ mag01[(a & 0x1)]);
/*      */         }
/* 1044 */         for (; kk < 623; kk++)
/*      */         {
/* 1046 */           int a = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/* 1047 */           mt[kk] = (mt[(kk + -227)] ^ a >>> 1 ^ mag01[(a & 0x1)]);
/*      */         }
/* 1049 */         int a = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/* 1050 */         mt[623] = (mt[396] ^ a >>> 1 ^ mag01[(a & 0x1)]);
/*      */ 
/* 1052 */         this.mti = 0;
/*      */       }
/*      */ 
/* 1055 */       int a = this.mt[(this.mti++)];
/* 1056 */       a ^= a >>> 11;
/* 1057 */       a ^= a << 7 & 0x9D2C5680;
/* 1058 */       a ^= a << 15 & 0xEFC60000;
/* 1059 */       a ^= a >>> 18;
/*      */ 
/* 1061 */       if (this.mti >= 624)
/*      */       {
/* 1064 */         int[] mt = this.mt;
/* 1065 */         int[] mag01 = this.mag01;
/*      */ 
/* 1067 */         for (int kk = 0; kk < 227; kk++)
/*      */         {
/* 1069 */           int b = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/* 1070 */           mt[kk] = (mt[(kk + 397)] ^ b >>> 1 ^ mag01[(b & 0x1)]);
/*      */         }
/* 1072 */         for (; kk < 623; kk++)
/*      */         {
/* 1074 */           int b = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/* 1075 */           mt[kk] = (mt[(kk + -227)] ^ b >>> 1 ^ mag01[(b & 0x1)]);
/*      */         }
/* 1077 */         int b = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/* 1078 */         mt[623] = (mt[396] ^ b >>> 1 ^ mag01[(b & 0x1)]);
/*      */ 
/* 1080 */         this.mti = 0;
/*      */       }
/*      */ 
/* 1083 */       int b = this.mt[(this.mti++)];
/* 1084 */       b ^= b >>> 11;
/* 1085 */       b ^= b << 7 & 0x9D2C5680;
/* 1086 */       b ^= b << 15 & 0xEFC60000;
/* 1087 */       b ^= b >>> 18;
/*      */ 
/* 1090 */       v1 = 2.0D * (((y >>> 6 << 27) + (z >>> 5)) / 9007199254740992.0D) - 1.0D;
/*      */ 
/* 1093 */       v2 = 2.0D * (((a >>> 6 << 27) + (b >>> 5)) / 9007199254740992.0D) - 1.0D;
/*      */ 
/* 1095 */       s = v1 * v1 + v2 * v2;
/* 1096 */     }while ((s >= 1.0D) || (s == 0.0D));
/* 1097 */     double multiplier = StrictMath.sqrt(-2.0D * StrictMath.log(s) / s);
/* 1098 */     this.__nextNextGaussian = (v2 * multiplier);
/* 1099 */     this.__haveNextNextGaussian = true;
/* 1100 */     return v1 * multiplier;
/*      */   }
/*      */ 
/*      */   public strictfp float nextFloat()
/*      */   {
/* 1114 */     if (this.mti >= 624)
/*      */     {
/* 1117 */       int[] mt = this.mt;
/* 1118 */       int[] mag01 = this.mag01;
/*      */ 
/* 1120 */       for (int kk = 0; kk < 227; kk++)
/*      */       {
/* 1122 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/* 1123 */         mt[kk] = (mt[(kk + 397)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/* 1125 */       for (; kk < 623; kk++)
/*      */       {
/* 1127 */         int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/* 1128 */         mt[kk] = (mt[(kk + -227)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */       }
/* 1130 */       int y = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/* 1131 */       mt[623] = (mt[396] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */ 
/* 1133 */       this.mti = 0;
/*      */     }
/*      */ 
/* 1136 */     int y = this.mt[(this.mti++)];
/* 1137 */     y ^= y >>> 11;
/* 1138 */     y ^= y << 7 & 0x9D2C5680;
/* 1139 */     y ^= y << 15 & 0xEFC60000;
/* 1140 */     y ^= y >>> 18;
/*      */ 
/* 1142 */     return (y >>> 8) / 16777216.0F;
/*      */   }
/*      */ 
/*      */   public strictfp float nextFloat(boolean includeZero, boolean includeOne)
/*      */   {
/* 1160 */     float d = 0.0F;
/*      */     do
/*      */     {
/* 1163 */       d = nextFloat();
/* 1164 */       if ((includeOne) && (nextBoolean())) d += 1.0F;
/*      */     }
/* 1166 */     while ((d > 1.0F) || ((!includeZero) && (d == 0.0F)));
/*      */ 
/* 1168 */     return d;
/*      */   }
/*      */ 
/*      */   public strictfp int nextInt(int n)
/*      */   {
/* 1177 */     if (n <= 0) {
/* 1178 */       throw new IllegalArgumentException("n must be positive, got: " + n);
/*      */     }
/* 1180 */     if ((n & -n) == n)
/*      */     {
/* 1184 */       if (this.mti >= 624)
/*      */       {
/* 1187 */         int[] mt = this.mt;
/* 1188 */         int[] mag01 = this.mag01;
/*      */ 
/* 1190 */         for (int kk = 0; kk < 227; kk++)
/*      */         {
/* 1192 */           int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/* 1193 */           mt[kk] = (mt[(kk + 397)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */         }
/* 1195 */         for (; kk < 623; kk++)
/*      */         {
/* 1197 */           int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/* 1198 */           mt[kk] = (mt[(kk + -227)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */         }
/* 1200 */         int y = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/* 1201 */         mt[623] = (mt[396] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */ 
/* 1203 */         this.mti = 0;
/*      */       }
/*      */ 
/* 1206 */       int y = this.mt[(this.mti++)];
/* 1207 */       y ^= y >>> 11;
/* 1208 */       y ^= y << 7 & 0x9D2C5680;
/* 1209 */       y ^= y << 15 & 0xEFC60000;
/* 1210 */       y ^= y >>> 18;
/*      */ 
/* 1212 */       return (int)(n * (y >>> 1) >> 31);
/*      */     }
/*      */ 
/*      */     int bits;
/*      */     int val;
/*      */     do
/*      */     {
/* 1220 */       if (this.mti >= 624)
/*      */       {
/* 1223 */         int[] mt = this.mt;
/* 1224 */         int[] mag01 = this.mag01;
/*      */ 
/* 1226 */         for (int kk = 0; kk < 227; kk++)
/*      */         {
/* 1228 */           int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/* 1229 */           mt[kk] = (mt[(kk + 397)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */         }
/* 1231 */         for (; kk < 623; kk++)
/*      */         {
/* 1233 */           int y = mt[kk] & 0x80000000 | mt[(kk + 1)] & 0x7FFFFFFF;
/* 1234 */           mt[kk] = (mt[(kk + -227)] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */         }
/* 1236 */         int y = mt[623] & 0x80000000 | mt[0] & 0x7FFFFFFF;
/* 1237 */         mt[623] = (mt[396] ^ y >>> 1 ^ mag01[(y & 0x1)]);
/*      */ 
/* 1239 */         this.mti = 0;
/*      */       }
/*      */ 
/* 1242 */       int y = this.mt[(this.mti++)];
/* 1243 */       y ^= y >>> 11;
/* 1244 */       y ^= y << 7 & 0x9D2C5680;
/* 1245 */       y ^= y << 15 & 0xEFC60000;
/* 1246 */       y ^= y >>> 18;
/*      */ 
/* 1248 */       bits = y >>> 1;
/* 1249 */       val = bits % n;
/* 1250 */     }while (bits - val + (n - 1) < 0);
/* 1251 */     return val;
/*      */   }
/*      */ 
/*      */   public static strictfp void main(String[] args)
/*      */   {
/* 1267 */     MersenneTwisterFast r = new MersenneTwisterFast(new int[] { 291, 564, 837, 1110 });
/* 1268 */     System.out.println("Output of MersenneTwisterFast with new (2002/1/26) seeding mechanism");
/* 1269 */     for (int j = 0; j < 1000; j++)
/*      */     {
/* 1272 */       long l = r.nextInt();
/* 1273 */       if (l < 0L) l += 4294967296L;
/* 1274 */       String s = String.valueOf(l);
/* 1275 */       while (s.length() < 10) s = " " + s;
/* 1276 */       System.out.print(s + " ");
/* 1277 */       if (j % 5 == 4) System.out.println();
/*      */ 
/*      */     }
/*      */ 
/* 1282 */     long SEED = 4357L;
/*      */ 
/* 1285 */     System.out.println("\nTime to test grabbing 100000000 ints");
/*      */ 
/* 1287 */     Random rr = new Random(4357L);
/* 1288 */     int xx = 0;
/* 1289 */     long ms = System.currentTimeMillis();
/* 1290 */     for (j = 0; j < 100000000; j++)
/* 1291 */       xx += rr.nextInt();
/* 1292 */     System.out.println("java.util.Random: " + (System.currentTimeMillis() - ms) + "          Ignore this: " + xx);
/*      */ 
/* 1294 */     r = new MersenneTwisterFast(4357L);
/* 1295 */     ms = System.currentTimeMillis();
/* 1296 */     xx = 0;
/* 1297 */     for (j = 0; j < 100000000; j++)
/* 1298 */       xx += r.nextInt();
/* 1299 */     System.out.println("Mersenne Twister Fast: " + (System.currentTimeMillis() - ms) + "          Ignore this: " + xx);
/*      */ 
/* 1304 */     System.out.println("\nGrab the first 1000 booleans");
/* 1305 */     r = new MersenneTwisterFast(4357L);
/* 1306 */     for (j = 0; j < 1000; j++)
/*      */     {
/* 1308 */       System.out.print(r.nextBoolean() + " ");
/* 1309 */       if (j % 8 == 7) System.out.println();
/*      */     }
/* 1311 */     if (j % 8 != 7) System.out.println();
/*      */ 
/* 1313 */     System.out.println("\nGrab 1000 booleans of increasing probability using nextBoolean(double)");
/* 1314 */     r = new MersenneTwisterFast(4357L);
/* 1315 */     for (j = 0; j < 1000; j++)
/*      */     {
/* 1317 */       System.out.print(r.nextBoolean(j / 999.0D) + " ");
/* 1318 */       if (j % 8 == 7) System.out.println();
/*      */     }
/* 1320 */     if (j % 8 != 7) System.out.println();
/*      */ 
/* 1322 */     System.out.println("\nGrab 1000 booleans of increasing probability using nextBoolean(float)");
/* 1323 */     r = new MersenneTwisterFast(4357L);
/* 1324 */     for (j = 0; j < 1000; j++)
/*      */     {
/* 1326 */       System.out.print(r.nextBoolean(j / 999.0F) + " ");
/* 1327 */       if (j % 8 == 7) System.out.println();
/*      */     }
/* 1329 */     if (j % 8 != 7) System.out.println();
/*      */ 
/* 1331 */     byte[] bytes = new byte[1000];
/* 1332 */     System.out.println("\nGrab the first 1000 bytes using nextBytes");
/* 1333 */     r = new MersenneTwisterFast(4357L);
/* 1334 */     r.nextBytes(bytes);
/* 1335 */     for (j = 0; j < 1000; j++)
/*      */     {
/* 1337 */       System.out.print(bytes[j] + " ");
/* 1338 */       if (j % 16 == 15) System.out.println();
/*      */     }
/* 1340 */     if (j % 16 != 15) System.out.println();
/*      */ 
/* 1343 */     System.out.println("\nGrab the first 1000 bytes -- must be same as nextBytes");
/* 1344 */     r = new MersenneTwisterFast(4357L);
/* 1345 */     for (j = 0; j < 1000; j++)
/*      */     {
/* 1348 */       byte b;
/* 1347 */       System.out.print((b = r.nextByte()) + " ");
/* 1348 */       if (b != bytes[j]) System.out.print("BAD ");
/* 1349 */       if (j % 16 == 15) System.out.println();
/*      */     }
/* 1351 */     if (j % 16 != 15) System.out.println();
/*      */ 
/* 1353 */     System.out.println("\nGrab the first 1000 shorts");
/* 1354 */     r = new MersenneTwisterFast(4357L);
/* 1355 */     for (j = 0; j < 1000; j++)
/*      */     {
/* 1357 */       System.out.print(r.nextShort() + " ");
/* 1358 */       if (j % 8 == 7) System.out.println();
/*      */     }
/* 1360 */     if (j % 8 != 7) System.out.println();
/*      */ 
/* 1362 */     System.out.println("\nGrab the first 1000 ints");
/* 1363 */     r = new MersenneTwisterFast(4357L);
/* 1364 */     for (j = 0; j < 1000; j++)
/*      */     {
/* 1366 */       System.out.print(r.nextInt() + " ");
/* 1367 */       if (j % 4 == 3) System.out.println();
/*      */     }
/* 1369 */     if (j % 4 != 3) System.out.println();
/*      */ 
/* 1371 */     System.out.println("\nGrab the first 1000 ints of different sizes");
/* 1372 */     r = new MersenneTwisterFast(4357L);
/* 1373 */     int max = 1;
/* 1374 */     for (j = 0; j < 1000; j++)
/*      */     {
/* 1376 */       System.out.print(r.nextInt(max) + " ");
/* 1377 */       max *= 2;
/* 1378 */       if (max <= 0) max = 1;
/* 1379 */       if (j % 4 == 3) System.out.println();
/*      */     }
/* 1381 */     if (j % 4 != 3) System.out.println();
/*      */ 
/* 1383 */     System.out.println("\nGrab the first 1000 longs");
/* 1384 */     r = new MersenneTwisterFast(4357L);
/* 1385 */     for (j = 0; j < 1000; j++)
/*      */     {
/* 1387 */       System.out.print(r.nextLong() + " ");
/* 1388 */       if (j % 3 == 2) System.out.println();
/*      */     }
/* 1390 */     if (j % 3 != 2) System.out.println();
/*      */ 
/* 1392 */     System.out.println("\nGrab the first 1000 longs of different sizes");
/* 1393 */     r = new MersenneTwisterFast(4357L);
/* 1394 */     long max2 = 1L;
/* 1395 */     for (j = 0; j < 1000; j++)
/*      */     {
/* 1397 */       System.out.print(r.nextLong(max2) + " ");
/* 1398 */       max2 *= 2L;
/* 1399 */       if (max2 <= 0L) max2 = 1L;
/* 1400 */       if (j % 4 == 3) System.out.println();
/*      */     }
/* 1402 */     if (j % 4 != 3) System.out.println();
/*      */ 
/* 1404 */     System.out.println("\nGrab the first 1000 floats");
/* 1405 */     r = new MersenneTwisterFast(4357L);
/* 1406 */     for (j = 0; j < 1000; j++)
/*      */     {
/* 1408 */       System.out.print(r.nextFloat() + " ");
/* 1409 */       if (j % 4 == 3) System.out.println();
/*      */     }
/* 1411 */     if (j % 4 != 3) System.out.println();
/*      */ 
/* 1413 */     System.out.println("\nGrab the first 1000 doubles");
/* 1414 */     r = new MersenneTwisterFast(4357L);
/* 1415 */     for (j = 0; j < 1000; j++)
/*      */     {
/* 1417 */       System.out.print(r.nextDouble() + " ");
/* 1418 */       if (j % 3 == 2) System.out.println();
/*      */     }
/* 1420 */     if (j % 3 != 2) System.out.println();
/*      */ 
/* 1422 */     System.out.println("\nGrab the first 1000 gaussian doubles");
/* 1423 */     r = new MersenneTwisterFast(4357L);
/* 1424 */     for (j = 0; j < 1000; j++)
/*      */     {
/* 1426 */       System.out.print(r.nextGaussian() + " ");
/* 1427 */       if (j % 3 == 2) System.out.println();
/*      */     }
/* 1429 */     if (j % 3 != 2) System.out.println();
/*      */   }
/*      */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     ec.util.MersenneTwisterFast
 * JD-Core Version:    0.6.2
 */