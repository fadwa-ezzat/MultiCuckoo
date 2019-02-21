/*    */ package sim.portrayal3d.continuous;
/*    */ 
/*    */ import com.sun.j3d.utils.picking.PickIntersection;
/*    */ import com.sun.j3d.utils.picking.PickResult;
/*    */ import javax.vecmath.Vector3d;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.field.continuous.Continuous3D;
/*    */ import sim.portrayal.FieldPortrayal;
/*    */ import sim.portrayal.LocationWrapper;
/*    */ import sim.portrayal.inspector.StableDouble2D;
/*    */ import sim.portrayal.inspector.StableLocation;
/*    */ import sim.portrayal3d.SparseFieldPortrayal3D;
/*    */ import sim.portrayal3d.inspector.StableDouble3D;
/*    */ import sim.util.Double2D;
/*    */ import sim.util.Double3D;
/*    */ 
/*    */ public class ContinuousPortrayal3D extends SparseFieldPortrayal3D
/*    */ {
/*    */   public Vector3d getLocationOfObjectAsVector3d(Object obj, Vector3d putInHere)
/*    */   {
/* 32 */     if ((this.field instanceof Continuous2D))
/*    */     {
/* 34 */       Double2D locationD2d = ((Continuous2D)this.field).getObjectLocation(obj);
/* 35 */       if (locationD2d == null) return null;
/* 36 */       putInHere.x = locationD2d.x;
/* 37 */       putInHere.y = locationD2d.y;
/* 38 */       putInHere.z = 0.0D;
/*    */     }
/*    */     else
/*    */     {
/* 42 */       Double3D locationD3d = ((Continuous3D)this.field).getObjectLocation(obj);
/* 43 */       if (locationD3d == null) return null;
/* 44 */       putInHere.x = locationD3d.x;
/* 45 */       putInHere.y = locationD3d.y;
/* 46 */       putInHere.z = locationD3d.z;
/*    */     }
/* 48 */     return putInHere;
/*    */   }
/*    */ 
/*    */   public void setField(Object field)
/*    */   {
/* 53 */     if (((field instanceof Continuous3D)) || ((field instanceof Continuous2D))) super.setField(field); else
/* 54 */       throw new RuntimeException("Invalid field for ContinuousPortrayal3D: " + field);
/*    */   }
/*    */ 
/*    */   public LocationWrapper completedWrapper(LocationWrapper w, PickIntersection pi, PickResult pr)
/*    */   {
/* 59 */     Object field = getField();
/* 60 */     StableLocation d = null;
/* 61 */     if ((field instanceof Continuous2D)) d = new StableDouble2D((Continuous2D)field, w.getObject()); else
/* 62 */       d = new StableDouble3D((Continuous3D)field, w.getObject());
/* 63 */     final StableLocation loc = d;
/* 64 */     return new LocationWrapper(w.getObject(), null, this)
/*    */     {
/*    */       public Object getLocation()
/*    */       {
/* 69 */         return loc;
/*    */       }
/*    */ 
/*    */       public String getLocationName()
/*    */       {
/* 75 */         return loc.toString();
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.continuous.ContinuousPortrayal3D
 * JD-Core Version:    0.6.2
 */