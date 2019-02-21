/*    */ package sim.app.lightcycles;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.field.grid.IntGrid2D;
/*    */ import sim.field.grid.SparseGrid2D;
/*    */ 
/*    */ public class LightCycles extends SimState
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public int gridHeight;
/*    */   public int gridWidth;
/*    */   public int cycleCount;
/*    */   public IntGrid2D grid;
/*    */   public SparseGrid2D cycleGrid;
/*    */ 
/*    */   public LightCycles(long seed)
/*    */   {
/* 27 */     this(seed, 100, 100, 10);
/*    */   }
/*    */ 
/*    */   public LightCycles(long seed, int width, int height, int count)
/*    */   {
/* 32 */     super(seed);
/* 33 */     this.gridWidth = width; this.gridHeight = height; this.cycleCount = count;
/* 34 */     createGrids();
/*    */   }
/*    */ 
/*    */   protected void createGrids()
/*    */   {
/* 39 */     this.grid = new IntGrid2D(this.gridWidth, this.gridHeight, 0);
/* 40 */     this.cycleGrid = new SparseGrid2D(this.gridWidth, this.gridHeight);
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 46 */     super.start();
/*    */ 
/* 49 */     createGrids();
/*    */ 
/* 52 */     for (int x = 0; x < this.cycleCount; x++)
/*    */     {
/* 54 */       Cycle c = new Cycle(x + 1, this.random.nextInt(4) + 1);
/* 55 */       this.cycleGrid.setObjectLocation(c, this.random.nextInt(this.gridWidth), this.random.nextInt(this.gridHeight));
/* 56 */       c.stopper = this.schedule.scheduleRepeating(c);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 62 */     doLoop(LightCycles.class, args);
/* 63 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.lightcycles.LightCycles
 * JD-Core Version:    0.6.2
 */