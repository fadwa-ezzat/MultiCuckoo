/*     */ package sim.app.pacman;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import sim.engine.Schedule;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.field.grid.IntGrid2D;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.MutableDouble2D;
/*     */ import sim.util.TableLoader;
/*     */ 
/*     */ public class PacMan extends SimState
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public Continuous2D agents;
/*     */   public Continuous2D dots;
/*     */   public IntGrid2D maze;
/*     */   boolean frightenGhosts;
/*     */   public int[] actions;
/*  43 */   public int deaths = 0;
/*     */ 
/*  46 */   public int level = 1;
/*     */ 
/*  49 */   public int score = 0;
/*     */   public Pac[] pacs;
/*  81 */   public int MAX_MAZES = 2;
/*     */ 
/*     */   public PacMan(long seed)
/*     */   {
/*  57 */     super(seed);
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/*  63 */     super.start();
/*     */ 
/*  65 */     this.deaths = 0;
/*  66 */     this.level = 1;
/*  67 */     this.score = 0;
/*     */ 
/*  71 */     this.maze = new IntGrid2D(0, 0);
/*     */     try { this.maze.setTo(TableLoader.loadPNMFile(PacMan.class.getResourceAsStream("images/maze0.pbm"))); } catch (Exception e) {
/*  73 */       e.printStackTrace();
/*     */     }
/*  75 */     this.agents = new Continuous2D(1.0D, this.maze.getWidth(), this.maze.getHeight());
/*  76 */     this.dots = new Continuous2D(1.0D, this.maze.getWidth(), this.maze.getHeight());
/*     */ 
/*  78 */     resetGame();
/*     */   }
/*     */ 
/*     */   public void resetGame()
/*     */   {
/*  86 */     this.dots.clear();
/*     */     try
/*     */     {
/*  91 */       this.maze.setTo(TableLoader.loadPNMFile(PacMan.class.getResourceAsStream("images/maze" + (this.level - 1) % this.MAX_MAZES + ".pbm"))); } catch (Exception e) {
/*  92 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  95 */     this.dots.setObjectLocation(new Energizer(), new Double2D(1.0D, 5.0D));
/*  96 */     this.dots.setObjectLocation(new Energizer(), new Double2D(26.0D, 5.0D));
/*  97 */     this.dots.setObjectLocation(new Energizer(), new Double2D(1.0D, 25.0D));
/*  98 */     this.dots.setObjectLocation(new Energizer(), new Double2D(26.0D, 25.0D));
/*     */ 
/* 101 */     for (int x = 0; x < this.maze.getWidth(); x++)
/* 102 */       for (int y = 0; y < this.maze.getHeight(); y++)
/* 103 */         if ((this.maze.field[x][y] == 0) && ((y != 16) || (x < 12) || (x > 16)))
/*     */         {
/* 105 */           this.dots.setObjectLocation(new Dot(), new Double2D(x, y));
/*     */         }
/* 107 */     resetAgents();
/*     */   }
/*     */   public int pacsLeft() {
/* 110 */     int count = 0; for (int i = 0; i < this.pacs.length; i++) if (this.pacs[i] != null) count++; 
/* 110 */     return count;
/*     */   }
/*     */ 
/*     */   public Pac pacClosestTo(MutableDouble2D location) {
/* 114 */     if (this.pacs.length == 1) return this.pacs[0];
/* 115 */     Pac best = null;
/* 116 */     int count = 1;
/* 117 */     for (int i = 0; i < this.pacs.length; i++)
/*     */     {
/* 119 */       if (this.pacs[i] != null)
/*     */       {
/* 121 */         if ((best == null) || ((best.location.distanceSq(location) > this.pacs[i].location.distanceSq(location)) && ((count = 1) == 1)) || ((best.location.distanceSq(location) == this.pacs[i].location.distanceSq(location)) && (this.random.nextBoolean(1.0D / ++count))))
/*     */         {
/* 124 */           best = this.pacs[i];
/*     */         }
/*     */       }
/*     */     }
/* 127 */     return best;
/*     */   }
/*     */ 
/*     */   public void resetAgents()
/*     */   {
/* 134 */     this.agents.clear();
/* 135 */     this.schedule.clear();
/*     */ 
/* 138 */     this.actions = new int[] { -1, -1 };
/* 139 */     this.pacs = new Pac[2];
/*     */ 
/* 142 */     if (this.pacs.length > 1) this.pacs[1] = new Pac(this, 1);
/* 143 */     this.pacs[0] = new Pac(this, 0);
/*     */ 
/* 147 */     Blinky blinky = new Blinky(this);
/*     */ 
/* 151 */     Pinky pinky = new Pinky(this);
/*     */ 
/* 155 */     Inky inky = new Inky(this, blinky);
/*     */ 
/* 159 */     Clyde clyde = new Clyde(this);
/*     */ 
/* 162 */     this.frightenGhosts = false;
/*     */   }
/*     */ 
/*     */   public int getNextAction(int tag)
/*     */   {
/* 168 */     return this.actions[tag];
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 172 */     doLoop(PacMan.class, args);
/* 173 */     System.exit(0);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pacman.PacMan
 * JD-Core Version:    0.6.2
 */