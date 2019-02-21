/*     */ package sim.app.antsforage;
/*     */ 
/*     */ import ec.util.MersenneTwisterFast;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.field.grid.SparseGrid2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.simple.OvalPortrayal2D;
/*     */ import sim.util.Int2D;
/*     */ 
/*     */ public class Ant extends OvalPortrayal2D
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  22 */   public boolean hasFoodItem = false;
/*  23 */   double reward = 0.0D;
/*     */   int x;
/*     */   int y;
/*     */   Int2D last;
/* 206 */   private Color noFoodColor = Color.black;
/* 207 */   private Color foodColor = Color.red;
/*     */ 
/*     */   public boolean getHasFoodItem()
/*     */   {
/*  20 */     return this.hasFoodItem; } 
/*  21 */   public void setHasFoodItem(boolean val) { this.hasFoodItem = val; }
/*     */ 
/*     */ 
/*     */   public Ant(double initialReward)
/*     */   {
/*  30 */     this.reward = initialReward;
/*     */   }
/*     */ 
/*     */   public void depositPheromone(SimState state)
/*     */   {
/*  39 */     AntsForage af = (AntsForage)state;
/*     */ 
/*  41 */     Int2D location = af.buggrid.getObjectLocation(this);
/*  42 */     int x = location.x;
/*  43 */     int y = location.y;
/*     */ 
/*  48 */     if (this.hasFoodItem)
/*     */     {
/*  50 */       double max = af.toFoodGrid.field[x][y];
/*  51 */       for (int dx = -1; dx < 2; dx++)
/*  52 */         for (int dy = -1; dy < 2; dy++)
/*     */         {
/*  54 */           int _x = dx + x;
/*  55 */           int _y = dy + y;
/*  56 */           if ((_x >= 0) && (_y >= 0) && (_x < 100) && (_y < 100)) {
/*  57 */             double m = af.toFoodGrid.field[_x][_y] * (dx * dy != 0 ? af.diagonalCutDown : af.updateCutDown) + this.reward;
/*     */ 
/*  61 */             if (m > max) max = m; 
/*     */           }
/*     */         }
/*  63 */       af.toFoodGrid.field[x][y] = max;
/*     */     }
/*     */     else
/*     */     {
/*  67 */       double max = af.toHomeGrid.field[x][y];
/*  68 */       for (int dx = -1; dx < 2; dx++)
/*  69 */         for (int dy = -1; dy < 2; dy++)
/*     */         {
/*  71 */           int _x = dx + x;
/*  72 */           int _y = dy + y;
/*  73 */           if ((_x >= 0) && (_y >= 0) && (_x < 100) && (_y < 100)) {
/*  74 */             double m = af.toHomeGrid.field[_x][_y] * (dx * dy != 0 ? af.diagonalCutDown : af.updateCutDown) + this.reward;
/*     */ 
/*  78 */             if (m > max) max = m; 
/*     */           }
/*     */         }
/*  80 */       af.toHomeGrid.field[x][y] = max;
/*     */     }
/*     */ 
/*  83 */     this.reward = 0.0D;
/*     */   }
/*     */ 
/*     */   public void act(SimState state)
/*     */   {
/*  88 */     AntsForage af = (AntsForage)state;
/*     */ 
/*  90 */     Int2D location = af.buggrid.getObjectLocation(this);
/*  91 */     int x = location.x;
/*  92 */     int y = location.y;
/*     */ 
/*  94 */     if (this.hasFoodItem)
/*     */     {
/*  96 */       double max = -1.0D;
/*  97 */       int max_x = x;
/*  98 */       int max_y = y;
/*  99 */       int count = 2;
/* 100 */       for (int dx = -1; dx < 2; dx++)
/* 101 */         for (int dy = -1; dy < 2; dy++)
/*     */         {
/* 103 */           int _x = dx + x;
/* 104 */           int _y = dy + y;
/* 105 */           if (((dx != 0) || (dy != 0)) && (_x >= 0) && (_y >= 0) && (_x < 100) && (_y < 100) && (af.obstacles.field[_x][_y] != 1))
/*     */           {
/* 109 */             double m = af.toHomeGrid.field[_x][_y];
/* 110 */             if (m > max)
/*     */             {
/* 112 */               count = 2;
/*     */             }
/*     */ 
/* 115 */             if ((m > max) || ((m == max) && (state.random.nextBoolean(1.0D / count++))))
/*     */             {
/* 117 */               max = m;
/* 118 */               max_x = _x;
/* 119 */               max_y = _y;
/*     */             }
/*     */           }
/*     */         }
/* 122 */       if ((max == 0.0D) && (this.last != null))
/*     */       {
/* 124 */         if (state.random.nextBoolean(af.momentumProbability))
/*     */         {
/* 126 */           int xm = x + (x - this.last.x);
/* 127 */           int ym = y + (y - this.last.y);
/* 128 */           if ((xm >= 0) && (xm < 100) && (ym >= 0) && (ym < 100) && (af.obstacles.field[xm][ym] == 0)) {
/* 129 */             max_x = xm; max_y = ym;
/*     */           }
/*     */         }
/* 132 */       } else if (state.random.nextBoolean(af.randomActionProbability))
/*     */       {
/* 134 */         int xd = state.random.nextInt(3) - 1;
/* 135 */         int yd = state.random.nextInt(3) - 1;
/* 136 */         int xm = x + xd;
/* 137 */         int ym = y + yd;
/* 138 */         if (((xd != 0) || (yd != 0)) && (xm >= 0) && (xm < 100) && (ym >= 0) && (ym < 100) && (af.obstacles.field[xm][ym] == 0)) {
/* 139 */           max_x = xm; max_y = ym;
/*     */         }
/*     */       }
/* 141 */       af.buggrid.setObjectLocation(this, new Int2D(max_x, max_y));
/* 142 */       if (af.sites.field[max_x][max_y] == 1) {
/* 143 */         this.reward = af.reward; this.hasFoodItem = (!this.hasFoodItem);
/*     */       }
/*     */     }
/*     */     else {
/* 147 */       double max = -1.0D;
/* 148 */       int max_x = x;
/* 149 */       int max_y = y;
/* 150 */       int count = 2;
/* 151 */       for (int dx = -1; dx < 2; dx++)
/* 152 */         for (int dy = -1; dy < 2; dy++)
/*     */         {
/* 154 */           int _x = dx + x;
/* 155 */           int _y = dy + y;
/* 156 */           if (((dx != 0) || (dy != 0)) && (_x >= 0) && (_y >= 0) && (_x < 100) && (_y < 100) && (af.obstacles.field[_x][_y] != 1))
/*     */           {
/* 160 */             double m = af.toFoodGrid.field[_x][_y];
/* 161 */             if (m > max)
/*     */             {
/* 163 */               count = 2;
/*     */             }
/*     */ 
/* 166 */             if ((m > max) || ((m == max) && (state.random.nextBoolean(1.0D / count++))))
/*     */             {
/* 168 */               max = m;
/* 169 */               max_x = _x;
/* 170 */               max_y = _y;
/*     */             }
/*     */           }
/*     */         }
/* 173 */       if ((max == 0.0D) && (this.last != null))
/*     */       {
/* 175 */         if (state.random.nextBoolean(af.momentumProbability))
/*     */         {
/* 177 */           int xm = x + (x - this.last.x);
/* 178 */           int ym = y + (y - this.last.y);
/* 179 */           if ((xm >= 0) && (xm < 100) && (ym >= 0) && (ym < 100) && (af.obstacles.field[xm][ym] == 0)) {
/* 180 */             max_x = xm; max_y = ym;
/*     */           }
/*     */         }
/* 183 */       } else if (state.random.nextBoolean(af.randomActionProbability))
/*     */       {
/* 185 */         int xd = state.random.nextInt(3) - 1;
/* 186 */         int yd = state.random.nextInt(3) - 1;
/* 187 */         int xm = x + xd;
/* 188 */         int ym = y + yd;
/* 189 */         if (((xd != 0) || (yd != 0)) && (xm >= 0) && (xm < 100) && (ym >= 0) && (ym < 100) && (af.obstacles.field[xm][ym] == 0)) {
/* 190 */           max_x = xm; max_y = ym;
/*     */         }
/*     */       }
/* 192 */       af.buggrid.setObjectLocation(this, new Int2D(max_x, max_y));
/* 193 */       if (af.sites.field[max_x][max_y] == 2) {
/* 194 */         this.reward = af.reward; this.hasFoodItem = (!this.hasFoodItem);
/*     */       }
/*     */     }
/* 196 */     this.last = location;
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/* 201 */     depositPheromone(state);
/* 202 */     act(state);
/*     */   }
/*     */ 
/*     */   public final void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/* 210 */     if (this.hasFoodItem)
/* 211 */       graphics.setColor(this.foodColor);
/*     */     else {
/* 213 */       graphics.setColor(this.noFoodColor);
/*     */     }
/*     */ 
/* 216 */     int x = (int)(info.draw.x - info.draw.width / 2.0D);
/* 217 */     int y = (int)(info.draw.y - info.draw.height / 2.0D);
/* 218 */     int width = (int)info.draw.width;
/* 219 */     int height = (int)info.draw.height;
/* 220 */     graphics.fillOval(x, y, width, height);
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.antsforage.Ant
 * JD-Core Version:    0.6.2
 */