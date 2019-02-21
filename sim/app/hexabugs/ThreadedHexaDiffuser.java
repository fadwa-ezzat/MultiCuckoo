/*     */ package sim.app.hexabugs;
/*     */ 
/*     */ import sim.engine.ParallelSequence;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ 
/*     */ public class ThreadedHexaDiffuser
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public ParallelSequence diffusers;
/*     */   DoubleGrid2D updateGrid;
/*     */   DoubleGrid2D tempGrid;
/*     */   double evaporationRate;
/*     */   double diffusionRate;
/*     */ 
/*     */   public ThreadedHexaDiffuser(DoubleGrid2D updateGrid, DoubleGrid2D tempGrid, double evaporationRate, double diffusionRate)
/*     */   {
/*  35 */     this.updateGrid = updateGrid;
/*  36 */     this.tempGrid = tempGrid;
/*  37 */     this.evaporationRate = evaporationRate;
/*  38 */     this.diffusionRate = diffusionRate;
/*  39 */     this.diffusers = new ParallelSequence(new Steppable[] { new Steppable()
/*     */     {
/*     */       public void step(SimState state)
/*     */       {
/*  46 */         HexaBugs hexabugs = (HexaBugs)state;
/*  47 */         int _gridWidth = hexabugs.valgrid.getWidth();
/*  48 */         ThreadedHexaDiffuser.this.diffuse(hexabugs, 0, _gridWidth / 2);
/*     */       }
/*     */     }
/*     */     , new Steppable()
/*     */     {
/*     */       public void step(SimState state)
/*     */       {
/*  56 */         HexaBugs hexabugs = (HexaBugs)state;
/*  57 */         int _gridWidth = hexabugs.valgrid.getWidth();
/*  58 */         ThreadedHexaDiffuser.this.diffuse(hexabugs, _gridWidth / 2, _gridWidth);
/*     */       }
/*     */     }
/*     */      });
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/*  67 */     this.diffusers.step(state);
/*     */ 
/*  70 */     HexaBugs hexabugs = (HexaBugs)state;
/*  71 */     hexabugs.valgrid.setTo(hexabugs.valgrid2);
/*     */   }
/*     */ 
/*     */   public void cleanup()
/*     */   {
/*  78 */     this.diffusers.cleanup();
/*     */   }
/*     */ 
/*     */   void diffuse(HexaBugs hexabugs, int start, int end)
/*     */   {
/*  91 */     DoubleGrid2D _valgrid = this.updateGrid;
/*  92 */     double[][] _valgrid_field = this.updateGrid.field;
/*  93 */     double[][] _valgrid2_field = this.tempGrid.field;
/*  94 */     int _gridWidth = _valgrid.getWidth();
/*  95 */     int _gridHeight = _valgrid.getHeight();
/*  96 */     double _evaporationRate = this.evaporationRate;
/*  97 */     double _diffusionRate = this.diffusionRate;
/*     */ 
/* 100 */     double[] _past = _valgrid_field[_valgrid.stx(start - 1)];
/* 101 */     double[] _current = _valgrid_field[start];
/*     */ 
/* 109 */     for (int x = start; x < end; x++)
/*     */     {
/* 111 */       int xplus1 = x + 1;
/* 112 */       if (xplus1 == _gridWidth)
/* 113 */         xplus1 = 0;
/* 114 */       double[] _next = _valgrid_field[xplus1];
/* 115 */       double[] _put = _valgrid2_field[x];
/* 116 */       boolean xmodulo2equals0 = x % 2 == 0;
/*     */ 
/* 118 */       int yminus1 = _gridHeight - 1;
/* 119 */       for (int y = 0; y < _gridHeight; y++)
/*     */       {
/* 123 */         int yplus1 = y + 1;
/* 124 */         if (yplus1 == _gridHeight)
/* 125 */           yplus1 = 0;
/*     */         double average;
/*     */         double average;
/* 126 */         if (xmodulo2equals0)
/*     */         {
/* 128 */           average = (_current[y] + _past[yminus1] + _next[yminus1] + _past[y] + _next[y] + _current[yminus1] + _current[yplus1]) / 7.0D;
/*     */         }
/*     */         else
/*     */         {
/* 140 */           average = (_current[y] + _past[y] + _next[y] + _past[yplus1] + _next[yplus1] + _current[yminus1] + _current[yplus1]) / 7.0D;
/*     */         }
/*     */ 
/* 152 */         _put[y] = (_evaporationRate * (_current[y] + _diffusionRate * (average - _current[y])));
/*     */ 
/* 157 */         yminus1 = y;
/*     */       }
/*     */ 
/* 161 */       _past = _current;
/* 162 */       _current = _next;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.hexabugs.ThreadedHexaDiffuser
 * JD-Core Version:    0.6.2
 */