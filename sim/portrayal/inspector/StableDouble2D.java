/*    */ package sim.portrayal.inspector;
/*    */ 
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public class StableDouble2D
/*    */   implements StableLocation
/*    */ {
/* 19 */   public double x = 0.0D;
/* 20 */   public double y = 0.0D;
/*    */   public boolean exists;
/*    */   public Continuous2D field;
/*    */   public Object object;
/*    */ 
/*    */   public String toString()
/*    */   {
/* 27 */     update();
/* 28 */     if (!this.exists) return "Gone";
/* 29 */     return "(" + this.x + ", " + this.y + ")";
/*    */   }
/*    */ 
/*    */   public StableDouble2D(Continuous2D field, Object object)
/*    */   {
/* 34 */     this.field = field;
/* 35 */     this.object = object;
/*    */   }
/*    */ 
/*    */   void update()
/*    */   {
/* 40 */     Double2D pos = null;
/* 41 */     if (this.field != null) pos = this.field.getObjectLocation(this.object);
/* 42 */     if (pos == null) { this.exists = false; } else {
/* 43 */       this.x = pos.x; this.y = pos.y; this.exists = true;
/*    */     }
/*    */   }
/* 46 */   public double getX() { update(); return this.x; } 
/* 47 */   public double getY() { update(); return this.y; } 
/* 48 */   public boolean getExists() { update(); return this.exists; }
/*    */ 
/*    */   public void setX(double val)
/*    */   {
/* 52 */     if (this.field != null) this.field.setObjectLocation(this.object, new Double2D(val, getY()));
/* 53 */     this.x = val;
/* 54 */     this.exists = true;
/*    */   }
/*    */ 
/*    */   public void setY(double val)
/*    */   {
/* 59 */     if (this.field != null) this.field.setObjectLocation(this.object, new Double2D(getX(), val));
/* 60 */     this.y = val;
/* 61 */     this.exists = true;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.inspector.StableDouble2D
 * JD-Core Version:    0.6.2
 */