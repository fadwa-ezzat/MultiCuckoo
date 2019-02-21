/*     */ package sim.portrayal.grid;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ import java.util.HashMap;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.field.grid.DenseGrid2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.portrayal.SimplePortrayal2D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.IntBag;
/*     */ import sim.util.MutableInt2D;
/*     */ 
/*     */ public class DenseGridPortrayal2D extends ObjectGridPortrayal2D
/*     */ {
/*     */   public DrawPolicy policy;
/*  87 */   protected final MutableInt2D locationToPass = new MutableInt2D(0, 0);
/*     */ 
/*     */   public DenseGridPortrayal2D()
/*     */   {
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public DenseGridPortrayal2D(DrawPolicy policy)
/*     */   {
/*  38 */     this.policy = policy;
/*     */   }
/*     */ 
/*     */   public void setDrawPolicy(DrawPolicy policy)
/*     */   {
/*  43 */     this.policy = policy;
/*     */   }
/*     */ 
/*     */   public DrawPolicy getDrawPolicy()
/*     */   {
/*  48 */     return this.policy;
/*     */   }
/*     */ 
/*     */   public void setField(Object field)
/*     */   {
/*  53 */     if ((field instanceof DenseGrid2D)) super.setField(field); else
/*  54 */       throw new RuntimeException("Invalid field for DenseGridPortrayal2D: " + field);
/*     */   }
/*     */ 
/*     */   public Object getObjectLocation(Object object, GUIState gui)
/*     */   {
/*  59 */     synchronized (gui.state.schedule)
/*     */     {
/*  61 */       DenseGrid2D field = (DenseGrid2D)this.field;
/*  62 */       if (field == null) return null;
/*     */ 
/*  64 */       int maxX = field.getWidth();
/*  65 */       int maxY = field.getHeight();
/*  66 */       if ((maxX == 0) || (maxY == 0)) return null;
/*     */ 
/*  69 */       for (int x = 0; x < maxX; x++)
/*     */       {
/*  71 */         Object[] fieldx = field.field[x];
/*  72 */         for (int y = 0; y < maxY; y++)
/*     */         {
/*  74 */           Bag objects = (Bag)fieldx[y];
/*  75 */           if (objects != null)
/*  76 */             for (int i = 0; i < objects.numObjs; i++)
/*  77 */               if (objects.objs[i] == object)
/*  78 */                 return new Int2D(x, y);
/*     */         }
/*     */       }
/*  81 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void hitOrDraw(Graphics2D graphics, DrawInfo2D info, Bag putInHere)
/*     */   {
/*  91 */     DenseGrid2D field = (DenseGrid2D)this.field;
/*  92 */     Bag policyBag = new Bag();
/*     */ 
/*  94 */     if (field == null) return;
/*     */ 
/*  96 */     boolean objectSelected = !this.selectedWrappers.isEmpty();
/*  97 */     Object selectedObject = this.selectedWrapper == null ? null : this.selectedWrapper.getObject();
/*     */ 
/* 107 */     int maxX = field.getWidth();
/* 108 */     int maxY = field.getHeight();
/* 109 */     if ((maxX == 0) || (maxY == 0)) return;
/*     */ 
/* 111 */     double xScale = info.draw.width / maxX;
/* 112 */     double yScale = info.draw.height / maxY;
/* 113 */     int startx = (int)((info.clip.x - info.draw.x) / xScale);
/* 114 */     int starty = (int)((info.clip.y - info.draw.y) / yScale);
/* 115 */     int endx = (int)((info.clip.x - info.draw.x + info.clip.width) / xScale) + 1;
/* 116 */     int endy = (int)((info.clip.y - info.draw.y + info.clip.height) / yScale) + 1;
/*     */ 
/* 118 */     DrawInfo2D newinfo = new DrawInfo2D(info.gui, info.fieldPortrayal, new Rectangle2D.Double(0.0D, 0.0D, xScale, yScale), info.clip);
/* 119 */     newinfo.precise = info.precise;
/* 120 */     newinfo.location = this.locationToPass;
/*     */ 
/* 122 */     if (endx > maxX) endx = maxX;
/* 123 */     if (endy > maxY) endy = maxY;
/* 124 */     if (startx < 0) startx = 0;
/* 125 */     if (starty < 0) starty = 0;
/* 126 */     for (int x = startx; x < endx; x++)
/* 127 */       for (int y = starty; y < endy; y++)
/*     */       {
/* 129 */         Bag objects = field.field[x][y];
/*     */ 
/* 131 */         if (objects != null)
/*     */         {
/* 133 */           if (((this.policy != null ? 1 : 0) & (graphics != null ? 1 : 0)) != 0)
/*     */           {
/* 135 */             policyBag.clear();
/* 136 */             if (this.policy.objectToDraw(objects, policyBag))
/* 137 */               objects = policyBag;
/*     */           }
/* 139 */           this.locationToPass.x = x;
/* 140 */           this.locationToPass.y = y;
/*     */ 
/* 142 */           for (int i = 0; i < objects.numObjs; i++)
/*     */           {
/* 144 */             Object portrayedObject = objects.objs[i];
/* 145 */             Portrayal p = getPortrayalForObject(portrayedObject);
/* 146 */             if (!(p instanceof SimplePortrayal2D)) {
/* 147 */               throw new RuntimeException("Unexpected Portrayal " + p + " for object " + portrayedObject + " -- expected a SimplePortrayal2D");
/*     */             }
/* 149 */             SimplePortrayal2D portrayal = (SimplePortrayal2D)p;
/*     */ 
/* 152 */             newinfo.draw.x = ((int)(info.draw.x + xScale * x));
/* 153 */             newinfo.draw.y = ((int)(info.draw.y + yScale * y));
/* 154 */             newinfo.draw.width = ((int)(info.draw.x + xScale * (x + 1)) - newinfo.draw.x);
/* 155 */             newinfo.draw.height = ((int)(info.draw.y + yScale * (y + 1)) - newinfo.draw.y);
/*     */ 
/* 158 */             newinfo.draw.x += newinfo.draw.width / 2.0D;
/* 159 */             newinfo.draw.y += newinfo.draw.height / 2.0D;
/*     */ 
/* 161 */             if (graphics == null)
/*     */             {
/* 163 */               if ((portrayedObject != null) && (portrayal.hitObject(portrayedObject, newinfo))) {
/* 164 */                 putInHere.add(getWrapper(portrayedObject, new Int2D(x, y)));
/*     */               }
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 170 */               newinfo.selected = ((objectSelected) && ((selectedObject == portrayedObject) || (this.selectedWrappers.get(portrayedObject) != null)));
/*     */ 
/* 181 */               portrayal.draw(portrayedObject, graphics, newinfo);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 186 */     drawGrid(graphics, xScale, yScale, maxX, maxY, info);
/* 187 */     drawBorder(graphics, xScale, info);
/*     */   }
/*     */ 
/*     */   Int2D searchForObject(Object object, Int2D loc)
/*     */   {
/* 193 */     DenseGrid2D field = (DenseGrid2D)this.field;
/* 194 */     Object[][] grid = field.field;
/* 195 */     if (grid[loc.x][loc.y] != null)
/*     */     {
/* 197 */       Bag b = (Bag)grid[loc.x][loc.y];
/* 198 */       if (b.contains(object)) {
/* 199 */         return new Int2D(loc.x, loc.y);
/*     */       }
/*     */     }
/* 202 */     field.getMooreLocations(loc.x, loc.y, 3, 2, false, this.xPos, this.yPos);
/* 203 */     for (int i = 0; i < this.xPos.numObjs; i++)
/*     */     {
/* 205 */       if (grid[this.xPos.get(i)][this.yPos.get(i)] != null)
/*     */       {
/* 207 */         Bag b = (Bag)grid[this.xPos.get(i)][this.yPos.get(i)];
/* 208 */         if (b.contains(object))
/* 209 */           return new Int2D(this.xPos.get(i), this.yPos.get(i));
/*     */       }
/*     */     }
/* 212 */     return null;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.grid.DenseGridPortrayal2D
 * JD-Core Version:    0.6.2
 */