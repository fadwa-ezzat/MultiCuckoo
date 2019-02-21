/*     */ package sim.app.heatbugs;
/*     */ 
/*     */ import sim.engine.ParallelSequence;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ 
/*     */ public class ThreadedDiffuser
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public ParallelSequence diffusers;
/*     */ 
/*     */   public ThreadedDiffuser(final int numThreads)
/*     */   {
/*  27 */     Steppable[] threads = new Steppable[numThreads];
/*     */ 
/*  30 */     for (int i = 0; i < numThreads - 1; i++)
/*     */     {
/*  32 */       final int _i = i;
/*  33 */       threads[i] = new Steppable()
/*     */       {
/*     */         public void step(SimState state)
/*     */         {
/*  37 */           HeatBugs heatbugs = (HeatBugs)state;
/*  38 */           int _gridWidth = heatbugs.valgrid.getWidth();
/*  39 */           ThreadedDiffuser.this.diffuse(heatbugs, _gridWidth / numThreads * _i, _gridWidth / numThreads * (_i + 1));
/*     */         }
/*     */ 
/*     */       };
/*     */     }
/*     */ 
/*  45 */     threads[(numThreads - 1)] = new Steppable()
/*     */     {
/*     */       public void step(SimState state)
/*     */       {
/*  49 */         HeatBugs heatbugs = (HeatBugs)state;
/*  50 */         int _gridWidth = heatbugs.valgrid.getWidth();
/*  51 */         ThreadedDiffuser.this.diffuse(heatbugs, _gridWidth / numThreads * (numThreads - 1), _gridWidth);
/*     */       }
/*     */     };
/*  54 */     this.diffusers = new ParallelSequence(threads);
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/*  60 */     this.diffusers.step(state);
/*     */ 
/*  63 */     HeatBugs heatbugs = (HeatBugs)state;
/*  64 */     heatbugs.valgrid.setTo(heatbugs.valgrid2);
/*     */   }
/*     */ 
/*     */   public void cleanup()
/*     */   {
/*  72 */     this.diffusers.cleanup();
/*     */   }
/*     */ 
/*     */   void diffuse(HeatBugs heatbugs, int start, int end)
/*     */   {
/*  83 */     DoubleGrid2D _valgrid = heatbugs.valgrid;
/*  84 */     double[][] _valgrid_field = heatbugs.valgrid.field;
/*  85 */     double[][] _valgrid2_field = heatbugs.valgrid2.field;
/*  86 */     int _gridHeight = _valgrid.getHeight();
/*  87 */     double _evaporationRate = heatbugs.evaporationRate;
/*  88 */     double _diffusionRate = heatbugs.diffusionRate;
/*     */ 
/*  92 */     double[] _past = _valgrid_field[_valgrid.stx(start - 1)];
/*  93 */     double[] _current = _valgrid_field[start];
/*     */ 
/* 101 */     for (int x = start; x < end; x++)
/*     */     {
/* 103 */       double[] _next = _valgrid_field[_valgrid.stx(x + 1)];
/* 104 */       double[] _put = _valgrid2_field[_valgrid.stx(x)];
/*     */ 
/* 106 */       int yminus1 = _valgrid.sty(-1);
/* 107 */       for (int y = 0; y < _gridHeight; y++)
/*     */       {
/* 111 */         int yplus1 = _valgrid.sty(y + 1);
/* 112 */         double average = (_past[yminus1] + _past[y] + _past[yplus1] + _current[yminus1] + _current[y] + _current[yplus1] + _next[yminus1] + _next[y] + _next[yplus1]) / 9.0D;
/*     */ 
/* 117 */         _put[y] = (_evaporationRate * (_current[y] + _diffusionRate * (average - _current[y])));
/*     */ 
/* 122 */         yminus1 = y;
/*     */       }
/*     */ 
/* 126 */       _past = _current;
/* 127 */       _current = _next;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.heatbugs.ThreadedDiffuser
 * JD-Core Version:    0.6.2
 */