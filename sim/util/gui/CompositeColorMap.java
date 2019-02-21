/*    */ package sim.util.gui;
/*    */ 
/*    */ import java.awt.Color;
/*    */ 
/*    */ public class CompositeColorMap
/*    */   implements ColorMap
/*    */ {
/*    */   ColorMap[] maps;
/*    */ 
/*    */   public CompositeColorMap(ColorMap map1, ColorMap map2)
/*    */   {
/* 26 */     this(new ColorMap[] { map1, map2 });
/*    */   }
/*    */   public CompositeColorMap(ColorMap map1, ColorMap map2, ColorMap map3) {
/* 29 */     this(new ColorMap[] { map1, map2, map3 });
/*    */   }
/*    */   public CompositeColorMap(ColorMap map1, ColorMap map2, ColorMap map3, ColorMap map4) {
/* 32 */     this(new ColorMap[] { map1, map2, map3, map4 });
/*    */   }
/*    */ 
/*    */   public CompositeColorMap(ColorMap[] maps)
/*    */   {
/* 37 */     if (maps.length == 0)
/* 38 */       throw new RuntimeException("CompositeColorMap requires at least one ColorMap");
/* 39 */     this.maps = maps;
/*    */   }
/*    */ 
/*    */   public Color getColor(double level)
/*    */   {
/* 44 */     for (int i = 0; i < this.maps.length - 1; i++)
/*    */     {
/* 46 */       if (this.maps[i].validLevel(level))
/* 47 */         return this.maps[i].getColor(level);
/*    */     }
/* 49 */     return this.maps[(this.maps.length - 1)].getColor(level);
/*    */   }
/*    */ 
/*    */   public int getRGB(double level)
/*    */   {
/* 54 */     for (int i = 0; i < this.maps.length - 1; i++)
/*    */     {
/* 56 */       if (this.maps[i].validLevel(level))
/* 57 */         return this.maps[i].getRGB(level);
/*    */     }
/* 59 */     return this.maps[(this.maps.length - 1)].getRGB(level);
/*    */   }
/*    */ 
/*    */   public int getAlpha(double level)
/*    */   {
/* 64 */     for (int i = 0; i < this.maps.length - 1; i++)
/*    */     {
/* 66 */       if (this.maps[i].validLevel(level))
/* 67 */         return this.maps[i].getAlpha(level);
/*    */     }
/* 69 */     return this.maps[(this.maps.length - 1)].getAlpha(level);
/*    */   }
/*    */ 
/*    */   public boolean validLevel(double level)
/*    */   {
/* 74 */     for (int i = 0; i < this.maps.length - 1; i++)
/*    */     {
/* 76 */       if (this.maps[i].validLevel(level))
/* 77 */         return true;
/*    */     }
/* 79 */     return this.maps[(this.maps.length - 1)].validLevel(level);
/*    */   }
/*    */ 
/*    */   public double defaultValue()
/*    */   {
/* 84 */     return this.maps[(this.maps.length - 1)].defaultValue();
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.util.gui.CompositeColorMap
 * JD-Core Version:    0.6.2
 */