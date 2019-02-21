/*    */ package sim.portrayal;
/*    */ 
/*    */ import java.awt.geom.Rectangle2D.Double;
/*    */ import java.awt.geom.RectangularShape;
/*    */ import sim.display.GUIState;
/*    */ 
/*    */ public class DrawInfo2D
/*    */ {
/*    */   public GUIState gui;
/*    */   public FieldPortrayal2D fieldPortrayal;
/*    */   public Rectangle2D.Double draw;
/*    */   public Rectangle2D.Double clip;
/*    */   public boolean selected;
/*    */   public boolean precise;
/*    */   public Object location;
/*    */ 
/*    */   public DrawInfo2D(GUIState gui, FieldPortrayal2D fieldPortrayal, RectangularShape draw, RectangularShape clip)
/*    */   {
/* 65 */     this.draw = new Rectangle2D.Double();
/* 66 */     this.draw.setRect(draw.getFrame());
/* 67 */     this.clip = new Rectangle2D.Double();
/* 68 */     this.clip.setRect(clip.getFrame());
/* 69 */     this.precise = false;
/* 70 */     this.gui = gui;
/* 71 */     this.fieldPortrayal = fieldPortrayal;
/*    */   }
/*    */ 
/*    */   public DrawInfo2D(DrawInfo2D other, double translateX, double translateY)
/*    */   {
/* 76 */     Rectangle2D.Double odraw = other.draw;
/* 77 */     this.draw = new Rectangle2D.Double(odraw.x + translateX, odraw.y + translateY, odraw.width, odraw.height);
/* 78 */     Rectangle2D.Double oclip = other.clip;
/* 79 */     this.clip = new Rectangle2D.Double(oclip.x + translateX, oclip.y + translateY, oclip.width, oclip.height);
/* 80 */     this.precise = other.precise;
/* 81 */     this.gui = other.gui;
/* 82 */     this.fieldPortrayal = other.fieldPortrayal;
/* 83 */     this.selected = other.selected;
/*    */   }
/*    */ 
/*    */   public DrawInfo2D(DrawInfo2D other)
/*    */   {
/* 89 */     this(other, 0.0D, 0.0D);
/* 90 */     this.location = other.location;
/*    */   }
/*    */   public String toString() {
/* 93 */     return "DrawInfo2D[ Draw: " + this.draw + " Clip: " + this.clip + " Precise: " + this.precise + " Location : " + this.location + " portrayal: " + this.fieldPortrayal + "]";
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.DrawInfo2D
 * JD-Core Version:    0.6.2
 */