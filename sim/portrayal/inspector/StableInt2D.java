/*    */ package sim.portrayal.inspector;
/*    */ 
/*    */ import sim.field.grid.SparseGrid2D;
/*    */ import sim.util.Int2D;
/*    */ 
/*    */ public class StableInt2D
/*    */   implements StableLocation
/*    */ {
/* 19 */   public int x = 0;
/* 20 */   public int y = 0;
/*    */   public boolean exists;
/*    */   public SparseGrid2D field;
/*    */   public Object object;
/*    */ 
/*    */   public String toString()
/*    */   {
/* 27 */     update();
/* 28 */     if (!this.exists) return "Gone";
/* 29 */     return "(" + this.x + ", " + this.y + ")";
/*    */   }
/*    */ 
/*    */   public StableInt2D(SparseGrid2D field, Object object)
/*    */   {
/* 34 */     this.field = field;
/* 35 */     this.object = object;
/*    */   }
/*    */ 
/*    */   void update()
/*    */   {
/* 40 */     Int2D pos = null;
/* 41 */     if (this.field != null) pos = this.field.getObjectLocation(this.object);
/* 42 */     if (pos == null) { this.exists = false; } else {
/* 43 */       this.x = pos.x; this.y = pos.y; this.exists = true;
/*    */     }
/*    */   }
/* 46 */   public int getX() { update(); return this.x; } 
/* 47 */   public int getY() { update(); return this.y; } 
/* 48 */   public boolean getExists() { update(); return this.exists; }
/*    */ 
/*    */   public void setX(int val)
/*    */   {
/* 52 */     if (this.field != null) this.field.setObjectLocation(this.object, new Int2D(val, getY()));
/* 53 */     this.x = val;
/* 54 */     this.exists = true;
/*    */   }
/*    */ 
/*    */   public void setY(int val)
/*    */   {
/* 59 */     if (this.field != null) this.field.setObjectLocation(this.object, new Int2D(getX(), val));
/* 60 */     this.y = val;
/* 61 */     this.exists = true;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.inspector.StableInt2D
 * JD-Core Version:    0.6.2
 */