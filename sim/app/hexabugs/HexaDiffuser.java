/*     */ package sim.app.hexabugs;
/*     */ 
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ 
/*     */ public class HexaDiffuser
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   DoubleGrid2D updateGrid;
/*     */   DoubleGrid2D tempGrid;
/*     */   double evaporationRate;
/*     */   double diffusionRate;
/*     */ 
/*     */   public HexaDiffuser(DoubleGrid2D updateGrid, DoubleGrid2D tempGrid, double evaporationRate, double diffusionRate)
/*     */   {
/*  29 */     this.updateGrid = updateGrid;
/*  30 */     this.tempGrid = tempGrid;
/*  31 */     this.evaporationRate = evaporationRate;
/*  32 */     this.diffusionRate = diffusionRate;
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/* 115 */     DoubleGrid2D _valgrid = this.updateGrid;
/* 116 */     double[][] _valgrid_field = this.updateGrid.field;
/* 117 */     double[][] _valgrid2_field = this.tempGrid.field;
/* 118 */     int _gridWidth = _valgrid.getWidth();
/* 119 */     int _gridHeight = _valgrid.getHeight();
/* 120 */     double _evaporationRate = this.evaporationRate;
/* 121 */     double _diffusionRate = this.diffusionRate;
/*     */ 
/* 125 */     double[] _past = _valgrid_field[_valgrid.stx(-1)];
/* 126 */     double[] _current = _valgrid_field[0];
/*     */ 
/* 134 */     for (int x = 0; x < _gridWidth; x++)
/*     */     {
/* 136 */       int xplus1 = x + 1;
/* 137 */       if (xplus1 == _gridWidth)
/* 138 */         xplus1 = 0;
/* 139 */       double[] _next = _valgrid_field[xplus1];
/* 140 */       double[] _put = _valgrid2_field[x];
/* 141 */       boolean xmodulo2equals0 = x % 2 == 0;
/*     */ 
/* 143 */       int yminus1 = _gridHeight - 1;
/* 144 */       for (int y = 0; y < _gridHeight; y++)
/*     */       {
/* 148 */         int yplus1 = y + 1;
/* 149 */         if (yplus1 == _gridHeight)
/* 150 */           yplus1 = 0;
/*     */         double average;
/*     */         double average;
/* 151 */         if (xmodulo2equals0)
/*     */         {
/* 153 */           average = (_current[y] + _past[yminus1] + _next[yminus1] + _past[y] + _next[y] + _current[yminus1] + _current[yplus1]) / 7.0D;
/*     */         }
/*     */         else
/*     */         {
/* 165 */           average = (_current[y] + _past[y] + _next[y] + _past[yplus1] + _next[yplus1] + _current[yminus1] + _current[yplus1]) / 7.0D;
/*     */         }
/*     */ 
/* 177 */         _put[y] = (_evaporationRate * (_current[y] + _diffusionRate * (average - _current[y])));
/*     */ 
/* 182 */         yminus1 = y;
/*     */       }
/*     */ 
/* 186 */       _past = _current;
/* 187 */       _current = _next;
/*     */     }
/*     */ 
/* 190 */     this.updateGrid.setTo(this.tempGrid);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.hexabugs.HexaDiffuser
 * JD-Core Version:    0.6.2
 */