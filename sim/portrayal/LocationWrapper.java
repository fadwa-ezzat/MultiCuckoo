/*    */ package sim.portrayal;
/*    */ 
/*    */ public class LocationWrapper
/*    */ {
/*    */   protected Object object;
/*    */   protected Object location;
/*    */   public FieldPortrayal fieldPortrayal;
/*    */ 
/*    */   public LocationWrapper(Object object, Object location, FieldPortrayal fieldPortrayal)
/*    */   {
/* 40 */     this.object = object; this.location = location; this.fieldPortrayal = fieldPortrayal;
/*    */   }
/* 42 */   public FieldPortrayal getFieldPortrayal() { return this.fieldPortrayal; } 
/*    */   public Object getObject() {
/* 44 */     return this.object;
/*    */   }
/* 46 */   public Object getLocation() { return this.location; } 
/*    */   public String getLocationName() {
/* 48 */     return "" + this.location;
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.LocationWrapper
 * JD-Core Version:    0.6.2
 */