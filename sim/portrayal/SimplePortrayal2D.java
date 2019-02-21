/*    */ package sim.portrayal;
/*    */ 
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.event.MouseEvent;
/*    */ import java.io.Serializable;
/*    */ import sim.display.GUIState;
/*    */ import sim.display.Manipulating2D;
/*    */ 
/*    */ public class SimplePortrayal2D
/*    */   implements Portrayal2D, Serializable
/*    */ {
/*    */   public static final int TYPE_SELECTED_OBJECT = 0;
/*    */   public static final int TYPE_HIT_OBJECT = 1;
/*    */ 
/*    */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*    */   {
/*    */   }
/*    */ 
/*    */   public boolean hitObject(Object object, DrawInfo2D range)
/*    */   {
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*    */   {
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean handleMouseEvent(GUIState guistate, Manipulating2D manipulating, LocationWrapper wrapper, MouseEvent event, DrawInfo2D fieldPortrayalDrawInfo, int type)
/*    */   {
/* 73 */     return false;
/*    */   }
/*    */ 
/*    */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*    */   {
/* 78 */     if (wrapper == null) return null;
/* 79 */     return Inspector.getInspector(wrapper.getObject(), state, "Properties");
/*    */   }
/*    */   public String getStatus(LocationWrapper wrapper) {
/* 82 */     return getName(wrapper);
/*    */   }
/*    */ 
/*    */   public String getName(LocationWrapper wrapper) {
/* 86 */     if (wrapper == null) return "";
/* 87 */     return "" + wrapper.getObject();
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.SimplePortrayal2D
 * JD-Core Version:    0.6.2
 */