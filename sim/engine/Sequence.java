/*     */ package sim.engine;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedHashSet;
/*     */ import sim.util.Bag;
/*     */ 
/*     */ public class Sequence
/*     */   implements Steppable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected Steppable[] steps;
/*     */   protected int size;
/*  74 */   LinkedHashSet stepsHash = null;
/*     */ 
/*  77 */   Bag toBeRemoved = new Bag();
/*     */ 
/*  80 */   Bag toBeAdded = new Bag();
/*     */ 
/*  83 */   Steppable[] toReplace = null;
/*     */ 
/*  86 */   boolean ensuresOrder = false;
/*     */ 
/*     */   public Sequence(Steppable[] steps)
/*     */   {
/*  90 */     this.steps = ((Steppable[])steps.clone());
/*  91 */     this.size = steps.length;
/*     */   }
/*     */ 
/*     */   public Sequence(Collection collection)
/*     */   {
/*  96 */     this.steps = new Steppable[collection.size()];
/*  97 */     this.steps = ((Steppable[])collection.toArray(this.steps));
/*     */   }
/*     */ 
/*     */   public boolean getEnsuresOrder()
/*     */   {
/* 104 */     return this.ensuresOrder;
/*     */   }
/*     */ 
/*     */   public void setEnsuresOrder(boolean val)
/*     */   {
/* 110 */     this.ensuresOrder = val;
/*     */   }
/*     */ 
/*     */   protected boolean canEnsureOrder() {
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean getUsesSets()
/*     */   {
/* 119 */     return this.stepsHash != null;
/*     */   }
/*     */ 
/*     */   public void setUsesSets(boolean val)
/*     */   {
/* 126 */     if ((val) && (this.stepsHash == null))
/*     */     {
/* 128 */       this.stepsHash = new LinkedHashSet();
/* 129 */       for (int i = 0; i < this.size; i++)
/* 130 */         if (!this.stepsHash.add(this.steps[i]))
/* 131 */           throw new RuntimeException("This Sequence is set up to use Sets, but duplicate Steppables were added to the sequence, which is not permitted in this mode.");
/*     */     }
/* 133 */     else if ((!val) && (this.stepsHash != null))
/*     */     {
/* 135 */       this.stepsHash = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   void loadStepsSet()
/*     */   {
/* 142 */     boolean stepsHashChanged = false;
/*     */ 
/* 145 */     if (this.toReplace != null)
/*     */     {
/* 147 */       stepsHashChanged = true;
/* 148 */       this.stepsHash.clear();
/* 149 */       for (int i = 0; i < this.toReplace.length; i++)
/* 150 */         if (!this.stepsHash.add(this.toReplace[i]))
/* 151 */           throw new RuntimeException("This Sequence is set up to use Sets, but duplicate Steppables were added to the sequence, which is not permitted in this mode.");
/* 152 */       this.size = this.toReplace.length;
/* 153 */       this.toReplace = null;
/*     */     }
/*     */ 
/* 157 */     int toBeRemovedSize = this.toBeRemoved.size();
/* 158 */     if (toBeRemovedSize > 0)
/*     */     {
/* 160 */       stepsHashChanged = true;
/* 161 */       for (int i = 0; i < toBeRemovedSize; i++)
/*     */       {
/* 163 */         this.stepsHash.remove(this.toBeRemoved.get(i));
/*     */       }
/* 165 */       this.toBeRemoved.clear();
/*     */     }
/*     */ 
/* 169 */     int toBeAddedSize = this.toBeAdded.size();
/* 170 */     if (toBeAddedSize > 0)
/*     */     {
/* 172 */       stepsHashChanged = true;
/* 173 */       for (int i = 0; i < toBeAddedSize; i++)
/*     */       {
/* 175 */         if (this.stepsHash.add(this.toBeAdded.get(i)));
/*     */       }
/*     */ 
/* 179 */       this.toBeAdded.clear();
/*     */     }
/*     */ 
/* 183 */     if (stepsHashChanged)
/*     */     {
/* 185 */       if (this.steps == null)
/* 186 */         this.steps = new Steppable[this.stepsHash.size()];
/* 187 */       this.steps = ((Steppable[])this.stepsHash.toArray(this.steps));
/* 188 */       this.size = this.steps.length;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void loadSteps()
/*     */   {
/* 199 */     if (this.stepsHash != null)
/*     */     {
/* 201 */       loadStepsSet();
/* 202 */       return;
/*     */     }
/*     */ 
/* 206 */     if (this.toReplace != null)
/*     */     {
/* 208 */       this.steps = this.toReplace;
/* 209 */       this.size = this.steps.length;
/* 210 */       this.toReplace = null;
/*     */     }
/*     */ 
/* 214 */     int toBeRemovedSize = this.toBeRemoved.size();
/* 215 */     if (toBeRemovedSize > 0)
/*     */     {
/* 217 */       boolean ensuresOrder = (this.ensuresOrder) && (canEnsureOrder());
/* 218 */       Steppable[] steps = this.steps;
/* 219 */       Bag toBeRemoved = this.toBeRemoved;
/* 220 */       int stepsSize = this.size;
/*     */ 
/* 222 */       for (int s = stepsSize - 1; s >= 0; s--)
/*     */       {
/* 224 */         for (int r = 0; r < toBeRemovedSize; r++)
/*     */         {
/* 226 */           if (steps[s] == toBeRemoved.get(r))
/*     */           {
/* 228 */             if (s < stepsSize - 1)
/*     */             {
/* 231 */               if (ensuresOrder)
/* 232 */                 System.arraycopy(steps, s + 1, steps, s, stepsSize - s - 1);
/*     */               else {
/* 234 */                 steps[s] = steps[(stepsSize - 1)];
/*     */               }
/*     */             }
/*     */ 
/* 238 */             steps[(stepsSize - 1)] = null;
/* 239 */             stepsSize--;
/*     */ 
/* 242 */             toBeRemoved.remove(r);
/* 243 */             toBeRemovedSize--;
/*     */ 
/* 245 */             break;
/*     */           }
/*     */         }
/*     */ 
/* 249 */         if (toBeRemovedSize == 0)
/*     */         {
/*     */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 256 */       toBeRemoved.clear();
/* 257 */       this.size = stepsSize;
/*     */     }
/*     */ 
/* 262 */     int toBeAddedSize = this.toBeAdded.size();
/* 263 */     if (toBeAddedSize > 0)
/*     */     {
/* 266 */       Bag toBeAdded = this.toBeAdded;
/* 267 */       int stepsSize = this.size;
/* 268 */       int newLen = stepsSize + toBeAddedSize;
/* 269 */       if (newLen >= this.steps.length)
/*     */       {
/* 271 */         int newSize = this.steps.length * 2 + 1;
/* 272 */         if (newSize <= newLen) newSize = newLen;
/* 273 */         Steppable[] newSteppables = new Steppable[newSize];
/* 274 */         System.arraycopy(this.steps, 0, newSteppables, 0, this.steps.length);
/* 275 */         this.steps = newSteppables;
/* 276 */         this.steps = newSteppables;
/*     */       }
/*     */ 
/* 280 */       if (toBeAddedSize < 20)
/* 281 */         for (int i = 0; i < toBeAddedSize; i++)
/* 282 */           this.steps[(stepsSize + i)] = ((Steppable)(Steppable)toBeAdded.get(i));
/*     */       else {
/* 284 */         toBeAdded.copyIntoArray(0, this.steps, stepsSize, toBeAddedSize);
/*     */       }
/*     */ 
/* 288 */       toBeAdded.clear();
/* 289 */       this.size = newLen;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void replaceSteppables(Collection collection)
/*     */   {
/* 297 */     if (this.toReplace == null)
/* 298 */       this.toReplace = new Steppable[collection.size()];
/* 299 */     this.toReplace = ((Steppable[])collection.toArray(this.toReplace));
/*     */   }
/*     */ 
/*     */   public void replaceSteppables(Steppable[] steppables)
/*     */   {
/* 305 */     this.toReplace = ((Steppable[])steppables.clone());
/*     */   }
/*     */ 
/*     */   public void addSteppable(Steppable steppable)
/*     */   {
/* 311 */     this.toBeAdded.add(steppable);
/*     */   }
/*     */ 
/*     */   public void addSteppables(Steppable[] steppables)
/*     */   {
/* 317 */     this.toBeAdded.addAll(steppables);
/*     */   }
/*     */ 
/*     */   public void addSteppables(Collection steppables)
/*     */   {
/* 323 */     this.toBeAdded.addAll(steppables);
/*     */   }
/*     */ 
/*     */   public void removeSteppable(Steppable steppable)
/*     */   {
/* 329 */     this.toBeRemoved.add(steppable);
/*     */   }
/*     */ 
/*     */   public void removeSteppables(Steppable[] steppables)
/*     */   {
/* 335 */     this.toBeRemoved.addAll(steppables);
/*     */   }
/*     */ 
/*     */   public void removeSteppables(Collection steppables)
/*     */   {
/* 341 */     this.toBeRemoved.addAll(steppables);
/*     */   }
/*     */ 
/*     */   public void step(SimState state)
/*     */   {
/* 346 */     loadSteps();
/*     */ 
/* 348 */     int stepsSize = this.size;
/* 349 */     Steppable[] steps = this.steps;
/*     */ 
/* 351 */     for (int x = 0; x < stepsSize; x++)
/*     */     {
/* 353 */       if (steps[x] != null)
/*     */       {
/* 355 */         steps[x].step(state);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.engine.Sequence
 * JD-Core Version:    0.6.2
 */