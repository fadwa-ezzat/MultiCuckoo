/*     */ package sim.app.pacman;
/*     */ 
/*     */ import sim.field.continuous.Continuous2D;
/*     */ import sim.field.grid.IntGrid2D;
/*     */ import sim.portrayal.Oriented2D;
/*     */ import sim.util.Double2D;
/*     */ import sim.util.MutableDouble2D;
/*     */ 
/*     */ public abstract class Agent
/*     */   implements Oriented2D
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int N = 0;
/*     */   public static final int E = 1;
/*     */   public static final int S = 2;
/*     */   public static final int W = 3;
/*     */   public static final int NOTHING = -1;
/*  43 */   public int lastAction = -1;
/*     */   public MutableDouble2D location;
/*     */   PacMan pacman;
/*  50 */   public int discretization = 10;
/*     */ 
/*     */   public double speed()
/*     */   {
/*  55 */     return 1.0D / this.discretization;
/*     */   }
/*     */ 
/*     */   public abstract Double2D getStartLocation();
/*     */ 
/*     */   public Agent(PacMan pacman)
/*     */   {
/*  65 */     this.pacman = pacman;
/*  66 */     Double2D loc = getStartLocation();
/*  67 */     this.location = new MutableDouble2D(loc);
/*  68 */     pacman.agents.setObjectLocation(this, loc);
/*     */   }
/*     */ 
/*     */   public double orientation2D()
/*     */   {
/*  74 */     switch (this.lastAction)
/*     */     {
/*     */     case 0:
/*  77 */       return 4.71238898038469D;
/*     */     case 1:
/*  79 */       return 0.0D;
/*     */     case 2:
/*  81 */       return 1.570796326794897D;
/*     */     case 3:
/*  83 */       return 3.141592653589793D;
/*     */     }
/*  85 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public void changeLocation(double x, double y)
/*     */   {
/*  93 */     this.location.x = ((int)Math.round(x * this.discretization) / this.discretization);
/*  94 */     this.location.y = ((int)Math.round(y * this.discretization) / this.discretization);
/*  95 */     this.pacman.agents.setObjectLocation(this, new Double2D(this.location));
/*     */   }
/*     */ 
/*     */   protected MutableDouble2D nextCell(int nextAction)
/*     */   {
/* 100 */     double nx = 0.0D; double ny = 0.0D;
/* 101 */     switch (nextAction)
/*     */     {
/*     */     case 0:
/* 104 */       nx = this.location.x;
/* 105 */       ny = this.location.y - 1.0D;
/* 106 */       break;
/*     */     case 1:
/* 108 */       nx = this.location.x + 1.0D;
/* 109 */       ny = this.location.y;
/* 110 */       break;
/*     */     case 2:
/* 112 */       nx = this.location.x;
/* 113 */       ny = this.location.y + 1.0D;
/* 114 */       break;
/*     */     case 3:
/* 116 */       nx = this.location.x - 1.0D;
/* 117 */       ny = this.location.y;
/* 118 */       break;
/*     */     default:
/* 120 */       throw new RuntimeException("default case should never occur");
/*     */     }
/* 122 */     return new MutableDouble2D(nx, ny);
/*     */   }
/*     */ 
/*     */   public void performAction(int action)
/*     */   {
/* 128 */     double x = this.location.x;
/* 129 */     double y = this.location.y;
/*     */ 
/* 131 */     switch (action)
/*     */     {
/*     */     case 0:
/* 135 */       y = this.pacman.agents.sty(y - speed());
/* 136 */       break;
/*     */     case 1:
/* 138 */       x = this.pacman.agents.stx(x + speed());
/* 139 */       break;
/*     */     case 2:
/* 141 */       y = this.pacman.agents.sty(y + speed());
/* 142 */       break;
/*     */     case 3:
/* 144 */       x = this.pacman.agents.stx(x - speed());
/* 145 */       break;
/*     */     default:
/* 147 */       throw new RuntimeException("default case should never occur");
/*     */     }
/* 149 */     changeLocation(x, y);
/* 150 */     this.lastAction = action;
/*     */   }
/*     */ 
/*     */   public boolean isPossibleToDoAction(int action)
/*     */   {
/* 156 */     if (action == -1)
/*     */     {
/* 158 */       return false;
/*     */     }
/* 160 */     IntGrid2D maze = this.pacman.maze;
/* 161 */     int[][] field = maze.field;
/*     */ 
/* 167 */     int x0 = (int)this.location.x;
/* 168 */     int y0 = (int)this.location.y;
/* 169 */     int x1 = this.location.x == x0 ? x0 : x0 + 1;
/* 170 */     int y1 = this.location.y == y0 ? y0 : y0 + 1;
/*     */ 
/* 173 */     if (((x0 == x1) && (y0 == y1)) || (this.lastAction == -1))
/*     */     {
/* 175 */       switch (action)
/*     */       {
/*     */       case 0:
/* 179 */         return field[maze.stx(x0)][maze.sty(y0 - 1)] == 0;
/*     */       case 1:
/* 181 */         return field[maze.stx(x0 + 1)][maze.sty(y0)] == 0;
/*     */       case 2:
/* 183 */         return field[maze.stx(x0)][maze.sty(y0 + 1)] == 0;
/*     */       case 3:
/* 185 */         return field[maze.stx(x0 - 1)][maze.sty(y0)] == 0;
/*     */       }
/*     */ 
/*     */     }
/* 189 */     else if (action == this.lastAction)
/*     */     {
/* 191 */       switch (action)
/*     */       {
/*     */       case 0:
/* 195 */         return field[maze.stx(x0)][maze.sty(y0)] == 0;
/*     */       case 1:
/* 197 */         return field[maze.stx(x1)][maze.sty(y0)] == 0;
/*     */       case 2:
/* 199 */         return field[maze.stx(x0)][maze.sty(y1)] == 0;
/*     */       case 3:
/* 201 */         return field[maze.stx(x0)][maze.sty(y0)] == 0;
/*     */       }
/*     */     }
/* 204 */     else if (((action == 0) && (this.lastAction == 2)) || ((action == 2) && (this.lastAction == 0)) || ((action == 1) && (this.lastAction == 3)) || ((action == 3) && (this.lastAction == 1)))
/*     */     {
/* 209 */       return true;
/*     */     }
/*     */ 
/* 212 */     return false;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.pacman.Agent
 * JD-Core Version:    0.6.2
 */