/*     */ package sim.app.heatbugs;
/*     */ 
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ 
/*     */ public class Diffuser
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/*  19 */     HeatBugs heatbugs = (HeatBugs)state;
/*     */ 
/* 277 */     DoubleGrid2D _valgrid = heatbugs.valgrid;
/* 278 */     double[][] _valgrid_field = heatbugs.valgrid.field;
/* 279 */     double[][] _valgrid2_field = heatbugs.valgrid2.field;
/* 280 */     int _gridWidth = _valgrid.getWidth();
/* 281 */     int _gridHeight = _valgrid.getHeight();
/* 282 */     double _evaporationRate = heatbugs.evaporationRate;
/* 283 */     double _diffusionRate = heatbugs.diffusionRate;
/*     */ 
/* 287 */     double[] _past = _valgrid_field[_valgrid.stx(-1)];
/* 288 */     double[] _current = _valgrid_field[0];
/*     */ 
/* 296 */     for (int x = 0; x < _gridWidth; x++)
/*     */     {
/* 298 */       double[] _next = _valgrid_field[_valgrid.stx(x + 1)];
/* 299 */       double[] _put = _valgrid2_field[_valgrid.stx(x)];
/*     */ 
/* 301 */       int yminus1 = _valgrid.sty(-1);
/* 302 */       for (int y = 0; y < _gridHeight; y++)
/*     */       {
/* 306 */         int yplus1 = _valgrid.sty(y + 1);
/* 307 */         double average = (_past[yminus1] + _past[y] + _past[yplus1] + _current[yminus1] + _current[y] + _current[yplus1] + _next[yminus1] + _next[y] + _next[yplus1]) / 9.0D;
/*     */ 
/* 312 */         _put[y] = (_evaporationRate * (_current[y] + _diffusionRate * (average - _current[y])));
/*     */ 
/* 317 */         yminus1 = y;
/*     */       }
/*     */ 
/* 321 */       _past = _current;
/* 322 */       _current = _next;
/*     */     }
/*     */ 
/* 357 */     _valgrid.setTo(heatbugs.valgrid2);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.heatbugs.Diffuser
 * JD-Core Version:    0.6.2
 */