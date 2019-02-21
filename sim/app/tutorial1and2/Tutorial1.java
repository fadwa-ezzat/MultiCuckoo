/*     */ package sim.app.tutorial1and2;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.grid.IntGrid2D;
/*     */ 
/*     */ public class Tutorial1 extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public IntGrid2D grid;
/*  24 */   public int gridWidth = 100;
/*  25 */   public int gridHeight = 100;
/*     */ 
/*  31 */   public static final int[][] b_heptomino = { { 0, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 }, { 0, 0, 1 } };
/*     */ 
/*     */   public Tutorial1(long seed)
/*     */   {
/*  18 */     super(seed);
/*     */   }
/*     */ 
/*     */   void seedGrid()
/*     */   {
/*  40 */     for (int x = 0; x < b_heptomino.length; x++)
/*  41 */       for (int y = 0; y < b_heptomino[x].length; y++)
/*  42 */         this.grid.field[(x + this.grid.field.length / 2 - b_heptomino.length / 2)][(y + this.grid.field[x].length / 2 - b_heptomino[x].length / 2)] = b_heptomino[x][y];
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  49 */     super.start();
/*  50 */     this.grid = new IntGrid2D(this.gridWidth, this.gridHeight);
/*  51 */     seedGrid();
/*  52 */     this.schedule.scheduleRepeating(new CA());
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  58 */     Tutorial1 tutorial1 = new Tutorial1(System.currentTimeMillis());
/*  59 */     tutorial1.start();
/*  60 */     long steps = 0L;
/*  61 */     while (steps < 5000L)
/*     */     {
/*  63 */       if (!tutorial1.schedule.step(tutorial1))
/*     */         break;
/*  65 */       steps = tutorial1.schedule.getSteps();
/*  66 */       if (steps % 500L == 0L)
/*  67 */         System.out.println("Steps: " + steps + " Time: " + tutorial1.schedule.getTime());
/*     */     }
/*  69 */     tutorial1.finish();
/*  70 */     System.exit(0);
/*     */   }
/*     */ 
/*     */   public static void main2(String[] args)
/*     */   {
/*  85 */     Tutorial1 tutorial1 = null;
/*     */ 
/*  90 */     for (int x = 0; x < args.length - 1; x++) {
/*  91 */       if (args[x].equals("-checkpoint"))
/*     */       {
/*  93 */         SimState state = SimState.readFromCheckpoint(new File(args[(x + 1)]));
/*  94 */         if (state == null) {
/*  95 */           System.exit(1);
/*  96 */         } else if (!(state instanceof Tutorial1))
/*     */         {
/*  98 */           System.out.println("Checkpoint contains some other simulation: " + state);
/*  99 */           System.exit(1);
/*     */         }
/*     */         else {
/* 102 */           tutorial1 = (Tutorial1)state;
/*     */         }
/*     */       }
/*     */     }
/* 106 */     if (tutorial1 == null)
/*     */     {
/* 108 */       tutorial1 = new Tutorial1(System.currentTimeMillis());
/* 109 */       tutorial1.start();
/*     */     }
/*     */ 
/* 112 */     long steps = 0L;
/* 113 */     while (steps < 5000L)
/*     */     {
/* 115 */       if (!tutorial1.schedule.step(tutorial1))
/*     */         break;
/* 117 */       steps = tutorial1.schedule.getSteps();
/* 118 */       if (steps % 500L == 0L)
/*     */       {
/* 120 */         System.out.println("Steps: " + steps + " Time: " + tutorial1.schedule.getTime());
/* 121 */         String s = steps + ".Tutorial1.checkpoint";
/* 122 */         System.out.println("Checkpointing to file: " + s);
/* 123 */         tutorial1.writeToCheckpoint(new File(s));
/*     */       }
/*     */     }
/* 126 */     tutorial1.finish();
/* 127 */     System.exit(0);
/*     */   }
/*     */ 
/*     */   public static void main3(String[] args)
/*     */   {
/* 142 */     doLoop(Tutorial1.class, args);
/* 143 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial1and2.Tutorial1
 * JD-Core Version:    0.6.2
 */