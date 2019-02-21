/*    */ package sim.portrayal3d.inspector;
/*    */ 
/*    */ import sim.field.SparseField;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.field.continuous.Continuous3D;
/*    */ import sim.portrayal.inspector.StableLocation;
/*    */ import sim.util.Double2D;
/*    */ import sim.util.Double3D;
/*    */ 
/*    */ public class StableDouble3D
/*    */   implements StableLocation
/*    */ {
/* 20 */   public double x = 0.0D;
/* 21 */   public double y = 0.0D;
/* 22 */   public double z = 0.0D;
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
/*    */   public StableDouble3D(Continuous2D field, Object object)
/*    */   {
/* 36 */     this.field = field;
/* 37 */     this.object = object;
/*    */   }
/*    */ 
/*    */   public StableDouble3D(Continuous3D field, Object object)
/*    */   {
/* 42 */     this.field = field;
/* 43 */     this.object = object;
/*    */   }
/*    */ 
/*    */   void update()
/*    */   {
/* 48 */     Double3D pos = null;
/* 49 */     if (this.field == null) return;
/* 50 */     if ((this.field instanceof Continuous2D))
/* 51 */       pos = new Double3D(((Continuous2D)this.field).getObjectLocation(this.object));
/*    */     else {
/* 53 */       pos = ((Continuous3D)this.field).getObjectLocation(this.object);
/*    */     }
/* 55 */     if (pos == null) { this.exists = false; } else {
/* 56 */       this.x = pos.x; this.y = pos.y; this.z = pos.z; this.exists = true;
/*    */     }
/*    */   }
/*    */ 
/* 60 */   public double getX() { update(); return this.x; } 
/* 61 */   public double getY() { update(); return this.y; } 
/* 62 */   public double getZ() { update(); return this.z; } 
/* 63 */   public boolean getExists() { update(); return this.exists; }
/*    */ 
/*    */   public void setX(double val)
/*    */   {
/* 67 */     if (this.field == null) return;
/* 68 */     if ((this.field instanceof Continuous2D)) {
/* 69 */       ((Continuous2D)this.field).setObjectLocation(this.object, new Double2D(val, getY())); this.z = 0.0D; } else {
/* 70 */       ((Continuous3D)this.field).setObjectLocation(this.object, new Double3D(val, getY(), getZ()));
/* 71 */     }this.x = val;
/* 72 */     this.exists = true;
/*    */   }
/*    */ 
/*    */   public void setY(double val)
/*    */   {
/* 77 */     if (this.field == null) return;
/* 78 */     if ((this.field instanceof Continuous2D)) {
/* 79 */       ((Continuous2D)this.field).setObjectLocation(this.object, new Double2D(getX(), val)); this.z = 0.0D; } else {
/* 80 */       ((Continuous3D)this.field).setObjectLocation(this.object, new Double3D(getX(), val, getZ()));
/* 81 */     }this.y = val;
/* 82 */     this.exists = true;
/*    */   }
/*    */ 
/*    */   public void setZ(double val)
/*    */   {
/* 87 */     if (this.field == null) return;
/* 88 */     if ((this.field instanceof Continuous2D)) { this.z = 0.0D; return; }
/* 89 */     ((Continuous3D)this.field).setObjectLocation(this.object, new Double3D(getX(), getY(), val));
/* 90 */     this.z = val;
/* 91 */     this.exists = true;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.inspector.StableDouble3D
 * JD-Core Version:    0.6.2
 */