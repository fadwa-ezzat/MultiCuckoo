/*     */ package sim.app.lightcycles;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.field.grid.SparseGrid2D;
/*     */ import sim.util.Int2D;
/*     */ 
/*     */ public class Cycle
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public int dir;
/*     */   public int my_id;
/*     */   boolean alive;
/*     */   boolean cpu;
/*     */   Stoppable stopper;
/*     */ 
/*     */   public int getdir()
/*     */   {
/*  27 */     return this.dir; } 
/*  28 */   public void setdir(int ndir) { this.dir = ndir; }
/*     */ 
/*     */   public int getmy_id() {
/*  31 */     return this.my_id; } 
/*  32 */   public void setmy_id(int nid) { this.my_id = nid; }
/*     */ 
/*     */   public boolean getalive() {
/*  35 */     return this.alive; } 
/*  36 */   public void setalive(boolean nalive) { this.alive = nalive; }
/*     */ 
/*     */   public boolean getcpu() {
/*  39 */     return this.cpu; } 
/*  40 */   public void setcpu(boolean ncpu) { this.cpu = ncpu; }
/*     */ 
/*     */   public Cycle(int id, int ndir)
/*     */   {
/*  44 */     this.my_id = id;
/*  45 */     this.dir = ndir;
/*     */ 
/*  47 */     this.alive = true;
/*  48 */     this.cpu = true;
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/*  55 */     if (!this.alive)
/*     */     {
/*  57 */       if (this.stopper != null) this.stopper.stop();
/*  58 */       return;
/*     */     }
/*     */ 
/*  61 */     LightCycles lc = (LightCycles)state;
/*     */ 
/*  63 */     int[] dirs = new int[5];
/*  64 */     int i = 0; int n = 0;
/*     */ 
/*  66 */     Int2D location = lc.cycleGrid.getObjectLocation(this);
/*  67 */     int x = location.x;
/*  68 */     int y = location.y;
/*     */ 
/*  71 */     if (this.cpu)
/*     */     {
/*  74 */       for (i = 1; i < 5; i++)
/*     */       {
/*  77 */         if (((i != 1) || (y != 0)) && ((i != 2) || (y != lc.gridHeight - 1)) && ((i != 3) || (x != 0)) && ((i != 4) || (x != lc.gridWidth - 1)))
/*     */         {
/*  83 */           n = 1;
/*  84 */           switch (i) { case 1:
/*     */           case 2:
/*     */           case 3:
/*     */           case 4:
/*  87 */             while ((lc.grid.field[x][(y - n)] == 0) && (y - (n + 1) > 0)) {
/*  88 */               n++; continue;
/*     */ 
/*  91 */               while ((lc.grid.field[x][(y + n)] == 0) && (y + (n + 1) < lc.gridHeight)) {
/*  92 */                 n++; continue;
/*     */ 
/*  95 */                 while ((lc.grid.field[(x - n)][y] == 0) && (x - (n + 1) > 0)) {
/*  96 */                   n++; continue;
/*     */ 
/*  99 */                   while ((lc.grid.field[(x + n)][y] == 0) && (x + (n + 1) < lc.gridWidth))
/* 100 */                     n++; 
/*     */                 }
/*     */               }
/*     */             } } dirs[i] = n;
/*     */         }
/*     */       }
/* 106 */       int bestDir = 0;
/* 107 */       int maxN = 0;
/*     */ 
/* 110 */       for (i = 1; i < 5; i++)
/*     */       {
/* 112 */         if ((dirs[i] > maxN) || ((dirs[i] == maxN) && (state.random.nextBoolean())))
/*     */         {
/* 114 */           maxN = dirs[i];
/* 115 */           bestDir = i;
/*     */         }
/*     */       }
/*     */ 
/* 119 */       if (maxN == 1) {
/* 120 */         this.alive = false;
/*     */       }
/* 122 */       this.dir = bestDir;
/*     */     }
/*     */ 
/* 126 */     if (this.dir == 1)
/* 127 */       y--;
/* 128 */     if (this.dir == 2)
/* 129 */       y++;
/* 130 */     if (this.dir == 3)
/* 131 */       x--;
/* 132 */     if (this.dir == 4) {
/* 133 */       x++;
/*     */     }
/*     */ 
/* 136 */     if ((x < 0) || (x >= lc.gridWidth) || (y < 0) || (y >= lc.gridHeight)) {
/* 137 */       this.alive = false;
/*     */     }
/* 141 */     else if (lc.grid.field[x][y] != 0) {
/* 142 */       this.alive = false;
/*     */     }
/*     */     else {
/* 145 */       lc.cycleGrid.setObjectLocation(this, x, y);
/* 146 */       lc.grid.field[x][y] = this.my_id;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.lightcycles.Cycle
 * JD-Core Version:    0.6.2
 */