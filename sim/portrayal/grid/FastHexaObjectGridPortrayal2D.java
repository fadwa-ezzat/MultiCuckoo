/*     */ package sim.portrayal.grid;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ import sim.field.grid.ObjectGrid2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.util.Valuable;
/*     */ import sim.util.gui.ColorMap;
/*     */ 
/*     */ public class FastHexaObjectGridPortrayal2D extends HexaObjectGridPortrayal2D
/*     */ {
/*  25 */   FastHexaValueGridPortrayal2D valueGridPortrayal = new FastHexaValueGridPortrayal2D("", this.immutableField);
/*     */   DoubleGrid2D grid;
/*     */ 
/*     */   public FastHexaObjectGridPortrayal2D(boolean immutableField)
/*     */   {
/*  32 */     setImmutableField(immutableField);
/*     */   }
/*     */ 
/*     */   public FastHexaObjectGridPortrayal2D()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setImmutableField(boolean immutableField) {
/*  40 */     super.setImmutableField(immutableField);
/*  41 */     this.valueGridPortrayal.setImmutableField(immutableField);
/*     */   }
/*     */ 
/*     */   public void setField(Object field)
/*     */   {
/*  46 */     super.setField(field);
/*  47 */     ObjectGrid2D og = (ObjectGrid2D)field;
/*  48 */     this.grid = new DoubleGrid2D(og.getWidth(), og.getHeight());
/*  49 */     this.valueGridPortrayal.setField(this.grid);
/*     */   }
/*     */ 
/*     */   public double doubleValue(Object obj)
/*     */   {
/*  66 */     if (obj == null) return 0.0D;
/*  67 */     if ((obj instanceof Number)) return ((Number)obj).doubleValue();
/*  68 */     if ((obj instanceof Valuable)) return ((Valuable)obj).doubleValue();
/*  69 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void reset()
/*     */   {
/*  76 */     this.valueGridPortrayal.reset();
/*     */   }
/*     */   public void setDirtyField(boolean val) {
/*  79 */     this.valueGridPortrayal.setDirtyField(val); } 
/*  80 */   public boolean isDirtyField() { return this.valueGridPortrayal.isDirtyField(); } 
/*     */   public ColorMap getMap() {
/*  82 */     return this.valueGridPortrayal.getMap(); } 
/*  83 */   public void setMap(ColorMap m) { this.valueGridPortrayal.setMap(m); } 
/*     */   public int getBuffering() {
/*  85 */     return this.valueGridPortrayal.getBuffering(); } 
/*  86 */   public void setBuffering(int val) { this.valueGridPortrayal.setBuffering(val); }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/*  90 */     if (this.field == null) return;
/*     */ 
/*  92 */     ObjectGrid2D ogrid = (ObjectGrid2D)this.field;
/*     */ 
/*  96 */     int maxX = ogrid.getWidth();
/*  97 */     int maxY = ogrid.getHeight();
/*  98 */     if ((maxX == 0) || (maxY == 0)) return;
/*     */ 
/* 100 */     double divideByX = maxX % 2 == 0 ? 3.0D * maxX / 2.0D + 0.5D : 3.0D * maxX / 2.0D + 2.0D;
/* 101 */     double divideByY = 1.0D + 2.0D * maxY;
/*     */ 
/* 103 */     double xScale = info.draw.width / divideByX;
/* 104 */     double yScale = info.draw.height / divideByY;
/* 105 */     int startx = (int)(((info.clip.x - info.draw.x) / xScale - 0.5D) / 1.5D) - 2;
/* 106 */     int starty = (int)((info.clip.y - info.draw.y) / (yScale * 2.0D)) - 2;
/* 107 */     int endx = (int)(((info.clip.x - info.draw.x + info.clip.width) / xScale - 0.5D) / 1.5D) + 4;
/* 108 */     int endy = (int)((info.clip.y - info.draw.y + info.clip.height) / (yScale * 2.0D)) + 4;
/*     */ 
/* 132 */     if (endx > maxX) endx = maxX;
/* 133 */     if (endy > maxY) endy = maxY;
/* 134 */     if (startx < 0) startx = 0;
/* 135 */     if (starty < 0) starty = 0;
/*     */ 
/* 138 */     for (int x = startx; x < endx; x++)
/*     */     {
/* 140 */       double[] gridx = this.grid.field[x];
/* 141 */       Object[] ogridx = ogrid.field[x];
/* 142 */       for (int y = starty; y < endy; y++) {
/* 143 */         gridx[y] = doubleValue(ogridx[y]);
/*     */       }
/*     */     }
/*     */ 
/* 147 */     this.valueGridPortrayal.draw(object, graphics, info);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.grid.FastHexaObjectGridPortrayal2D
 * JD-Core Version:    0.6.2
 */