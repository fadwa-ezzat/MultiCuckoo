/*     */ package sim.util.gui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import sim.util.Bag;
/*     */ 
/*     */ public class SimpleColorMap
/*     */   implements ColorMap
/*     */ {
/*  65 */   final Color clearColor = new Color(0, 0, 0, 0);
/*     */   static final int COLOR_DISCRETIZATION = 257;
/*  68 */   int minRed = 0;
/*  69 */   int minBlue = 0;
/*  70 */   int minGreen = 0;
/*  71 */   int minAlpha = 0;
/*  72 */   int maxRed = 0;
/*  73 */   int maxBlue = 0;
/*  74 */   int maxGreen = 0;
/*  75 */   int maxAlpha = 0;
/*  76 */   double maxLevel = 0.0D;
/*  77 */   double minLevel = 0.0D;
/*  78 */   Color minColor = this.clearColor;
/*  79 */   Color maxColor = this.clearColor;
/*     */   public Color[] colors;
/*  85 */   Bag[] colorCache = new Bag[257];
/*     */ 
/*     */   public SimpleColorMap()
/*     */   {
/*  98 */     setLevels(0.0D, 1.0D, Color.black, Color.white);
/*     */   }
/*     */ 
/*     */   public SimpleColorMap(double minLevel, double maxLevel, Color minColor, Color maxColor)
/*     */   {
/* 109 */     setLevels(minLevel, maxLevel, minColor, maxColor);
/*     */   }
/*     */ 
/*     */   public SimpleColorMap(Color[] colorTable)
/*     */   {
/* 120 */     setColorTable(colorTable);
/*     */   }
/*     */ 
/*     */   public SimpleColorMap(Color[] colorTable, double minLevel, double maxLevel, Color minColor, Color maxColor)
/*     */   {
/* 135 */     setColorTable(colorTable);
/* 136 */     setLevels(minLevel, maxLevel, minColor, maxColor);
/*     */   }
/*     */ 
/*     */   public Color[] setColorTable(Color[] colorTable)
/*     */   {
/* 144 */     Color[] retval = this.colors;
/* 145 */     this.colors = colorTable;
/* 146 */     return retval;
/*     */   }
/*     */ 
/*     */   public double filterLevel(double level)
/*     */   {
/* 157 */     return level;
/*     */   }
/*     */ 
/*     */   public double transformLevel(double level, double minLevel, double maxLevel)
/*     */   {
/* 169 */     if (level <= minLevel) return minLevel;
/* 170 */     if (level >= maxLevel) return maxLevel;
/* 171 */     double interval = maxLevel - minLevel;
/* 172 */     return filterLevel((level - minLevel) / interval) * interval + minLevel;
/*     */   }
/*     */ 
/*     */   public void setLevels(double minLevel, double maxLevel, Color minColor, Color maxColor)
/*     */   {
/* 181 */     if ((maxLevel != maxLevel) || (minLevel != minLevel)) throw new RuntimeException("maxLevel or minLevel cannot be NaN");
/* 182 */     if ((Double.isInfinite(maxLevel)) || (Double.isInfinite(minLevel))) throw new RuntimeException("maxLevel or minLevel cannot be infinite");
/* 183 */     if (maxLevel < minLevel) throw new RuntimeException("maxLevel cannot be less than minLevel");
/* 184 */     this.minRed = minColor.getRed(); this.minGreen = minColor.getGreen(); this.minBlue = minColor.getBlue(); this.minAlpha = minColor.getAlpha();
/* 185 */     this.maxRed = maxColor.getRed(); this.maxGreen = maxColor.getGreen(); this.maxBlue = maxColor.getBlue(); this.maxAlpha = maxColor.getAlpha();
/* 186 */     this.maxLevel = maxLevel; this.minLevel = minLevel;
/* 187 */     this.minColor = minColor;
/* 188 */     this.maxColor = maxColor;
/*     */ 
/* 192 */     for (int x = 0; x < 257; x++) this.colorCache[x] = new Bag();
/*     */   }
/*     */ 
/*     */   public Color getColor(double level)
/*     */   {
/* 207 */     if ((this.colors != null) && (level >= 0.0D) && (level < this.colors.length))
/*     */     {
/* 209 */       return this.colors[((int)level)];
/*     */     }
/*     */ 
/* 214 */     level = transformLevel(level, this.minLevel, this.maxLevel);
/*     */ 
/* 216 */     if (level != level) level = (-1.0D / 0.0D);
/*     */ 
/* 218 */     if (level == this.minLevel) return this.minColor;
/* 219 */     if (level == this.maxLevel) return this.maxColor;
/*     */ 
/* 221 */     double interpolation = (level - this.minLevel) / (this.maxLevel - this.minLevel);
/*     */ 
/* 230 */     int alpha = this.maxAlpha == this.minAlpha ? this.minAlpha : (int)(interpolation * (this.maxAlpha - this.minAlpha + 1) + this.minAlpha);
/* 231 */     if (alpha == 0) return this.clearColor;
/* 232 */     int red = this.maxRed == this.minRed ? this.minRed : (int)(interpolation * (this.maxRed - this.minRed + 1) + this.minRed);
/* 233 */     int green = this.maxGreen == this.minGreen ? this.minGreen : (int)(interpolation * (this.maxGreen - this.minGreen + 1) + this.minGreen);
/* 234 */     int blue = this.maxBlue == this.minBlue ? this.minBlue : (int)(interpolation * (this.maxBlue - this.minBlue + 1) + this.minBlue);
/* 235 */     int rgb = alpha << 24 | red << 16 | green << 8 | blue;
/* 236 */     Bag colors = this.colorCache[((int)(interpolation * 256.0D))];
/* 237 */     for (int x = 0; x < colors.numObjs; x++)
/*     */     {
/* 239 */       Color c = (Color)colors.objs[x];
/* 240 */       if (c.getRGB() == rgb)
/* 241 */         return c;
/*     */     }
/* 243 */     Color c = new Color(rgb, alpha != 0);
/* 244 */     colors.add(c);
/* 245 */     return c;
/*     */   }
/*     */ 
/*     */   public int getAlpha(double level)
/*     */   {
/* 254 */     if ((this.colors != null) && (level >= 0.0D) && (level < this.colors.length))
/*     */     {
/* 256 */       return this.colors[((int)level)].getAlpha();
/*     */     }
/*     */ 
/* 261 */     level = transformLevel(level, this.minLevel, this.maxLevel);
/*     */ 
/* 263 */     if (level != level) level = (-1.0D / 0.0D);
/*     */ 
/* 268 */     if (level >= this.maxLevel) return this.maxColor.getAlpha();
/* 269 */     if (level <= this.minLevel) return this.minColor.getAlpha();
/*     */ 
/* 271 */     double interpolation = (level - this.minLevel) / (this.maxLevel - this.minLevel);
/*     */ 
/* 273 */     int maxAlpha = this.maxAlpha;
/* 274 */     int minAlpha = this.minAlpha;
/* 275 */     return maxAlpha == minAlpha ? minAlpha : (int)(interpolation * (maxAlpha - minAlpha) + minAlpha);
/*     */   }
/*     */ 
/*     */   public int getRGB(double level)
/*     */   {
/* 282 */     if ((this.colors != null) && (level >= 0.0D) && (level < this.colors.length))
/*     */     {
/* 284 */       return this.colors[((int)level)].getRGB();
/*     */     }
/*     */ 
/* 289 */     level = transformLevel(level, this.minLevel, this.maxLevel);
/*     */ 
/* 291 */     if (level != level) level = (-1.0D / 0.0D);
/*     */ 
/* 294 */     if (level >= this.maxLevel) return this.maxColor.getRGB();
/* 295 */     if (level <= this.minLevel) return this.minColor.getRGB();
/*     */ 
/* 297 */     double interpolation = (level - this.minLevel) / (this.maxLevel - this.minLevel);
/*     */ 
/* 299 */     int maxAlpha = this.maxAlpha;
/* 300 */     int minAlpha = this.minAlpha;
/* 301 */     int alpha = maxAlpha == minAlpha ? minAlpha : (int)(interpolation * (maxAlpha - minAlpha) + minAlpha);
/* 302 */     if (alpha == 0) return 0;
/*     */ 
/* 304 */     int maxRed = this.maxRed;
/* 305 */     int minRed = this.minRed;
/* 306 */     int maxGreen = this.maxGreen;
/* 307 */     int minGreen = this.minGreen;
/* 308 */     int maxBlue = this.maxBlue;
/* 309 */     int minBlue = this.minBlue;
/* 310 */     int red = maxRed == minRed ? minRed : (int)(interpolation * (maxRed - minRed) + minRed);
/* 311 */     int green = maxGreen == minGreen ? minGreen : (int)(interpolation * (maxGreen - minGreen) + minGreen);
/* 312 */     int blue = maxBlue == minBlue ? minBlue : (int)(interpolation * (maxBlue - minBlue) + minBlue);
/* 313 */     return alpha << 24 | red << 16 | green << 8 | blue;
/*     */   }
/*     */ 
/*     */   public boolean validLevel(double value)
/*     */   {
/* 326 */     if ((this.colors != null) && (value >= 0.0D) && (value < this.colors.length))
/* 327 */       return true;
/* 328 */     if ((value <= this.maxLevel) && (value >= this.minLevel))
/* 329 */       return true;
/* 330 */     return false;
/*     */   }
/*     */ 
/*     */   public double defaultValue()
/*     */   {
/* 335 */     if (this.colors != null) return 0.0D;
/* 336 */     return this.minLevel;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.SimpleColorMap
 * JD-Core Version:    0.6.2
 */