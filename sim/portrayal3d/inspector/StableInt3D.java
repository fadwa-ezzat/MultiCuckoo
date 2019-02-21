/*    */ package sim.portrayal3d.inspector;
/*    */ 
/*    */ import sim.field.SparseField;
/*    */ import sim.field.grid.SparseGrid2D;
/*    */ import sim.field.grid.SparseGrid3D;
/*    */ import sim.portrayal.inspector.StableLocation;
/*    */ import sim.util.Int2D;
/*    */ import sim.util.Int3D;
/*    */ 
/*    */ public class StableInt3D
/*    */   implements StableLocation
/*    */ {
/* 20 */   public int x = 0;
/* 21 */   public int y = 0;
/* 22 */   public int z = 0;
/* 23 */   public boolean exists = false;
/*    */   public SparseField field;
/*    */   public Object object;
/*    */ 
/*    */   public String toString()
/*    */   {
/* 29 */     update();
/* 30 */     if (!this.exists) return "Gone";
/* 31 */     return "(" + this.x + ", " + this.y + ", " + this.z + ")";
/*    */   }
/*    */ 
/*    */   public StableInt3D(SparseGrid2D field, Object object)
/*    */   {
/* 36 */     this.field = field;
/* 37 */     this.object = object;
/*    */   }
/*    */ 
/*    */   public StableInt3D(SparseGrid3D field, Object object)
/*    */   {
/* 42 */     this.field = field;
/* 43 */     this.object = object;
/*    */   }
/*    */ 
/*    */   void update()
/*    */   {
/* 48 */     Int3D pos = null;
/* 49 */     if (this.field == null) return;
/* 50 */     if ((this.field instanceof SparseGrid2D))
/* 51 */       pos = new Int3D(((SparseGrid2D)this.field).getObjectLocation(this.object));
/*    */     else
/* 53 */       pos = ((SparseGrid3D)this.field).getObjectLocation(this.object);
/* 54 */     if (pos == null) { this.exists = false; } else {
/* 55 */       this.x = pos.x; this.y = pos.y; this.z = pos.z; this.exists = true;
/*    */     }
/*    */   }
/* 58 */   public int getX() { update(); return this.x; } 
/* 59 */   public int getY() { update(); return this.y; } 
/* 60 */   public int getZ() { update(); return this.z; } 
/* 61 */   public boolean getExists() { update(); return this.exists; }
/*    */ 
/*    */   public void setX(int val)
/*    */   {
/* 65 */     if (this.field == null) return;
/* 66 */     if ((this.field instanceof SparseGrid2D)) {
/* 67 */       ((SparseGrid2D)this.field).setObjectLocation(this.object, new Int2D(val, getY())); this.z = 0; } else {
/* 68 */       ((SparseGrid3D)this.field).setObjectLocation(this.object, new Int3D(val, getY(), getZ()));
/* 69 */     }this.x = val;
/* 70 */     this.exists = true;
/*    */   }
/*    */ 
/*    */   public void setY(int val)
/*    */   {
/* 75 */     if (this.field == null) return;
/* 76 */     if ((this.field instanceof SparseGrid2D)) {
/* 77 */       ((SparseGrid2D)this.field).setObjectLocation(this.object, new Int2D(getX(), val)); this.z = 0; } else {
/* 78 */       ((SparseGrid3D)this.field).setObjectLocation(this.object, new Int3D(getX(), val, getZ()));
/* 79 */     }this.y = val;
/* 80 */     this.exists = true;
/*    */   }
/*    */ 
/*    */   public void setZ(int val)
/*    */   {
/* 85 */     if (this.field == null) return;
/* 86 */     if ((this.field instanceof SparseGrid2D)) { this.z = 0; return; }
/* 87 */     ((SparseGrid3D)this.field).setObjectLocation(this.object, new Int3D(getX(), getY(), val));
/* 88 */     this.z = val;
/* 89 */     this.exists = true;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.inspector.StableInt3D
 * JD-Core Version:    0.6.2
 */