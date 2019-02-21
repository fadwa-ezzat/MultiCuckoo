/*    */ package sim.portrayal.simple;
/*    */ 
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.geom.AffineTransform;
/*    */ import java.awt.geom.Rectangle2D.Double;
/*    */ import sim.display.GUIState;
/*    */ import sim.portrayal.DrawInfo2D;
/*    */ import sim.portrayal.Inspector;
/*    */ import sim.portrayal.LocationWrapper;
/*    */ import sim.portrayal.Oriented2D;
/*    */ import sim.portrayal.SimplePortrayal2D;
/*    */ 
/*    */ public class TransformedPortrayal2D extends SimplePortrayal2D
/*    */ {
/*    */   public SimplePortrayal2D child;
/*    */   public AffineTransform transform;
/*    */ 
/*    */   public TransformedPortrayal2D(SimplePortrayal2D child, AffineTransform transform)
/*    */   {
/* 23 */     this.child = child; this.transform = transform;
/*    */   }
/*    */ 
/*    */   public SimplePortrayal2D getChild(Object object)
/*    */   {
/* 28 */     if (this.child != null) return this.child;
/*    */ 
/* 31 */     if (!(object instanceof SimplePortrayal2D))
/* 32 */       throw new RuntimeException("Object provided to TransformedPortrayal2D is not a SimplePortrayal2D: " + object);
/* 33 */     return (SimplePortrayal2D)object;
/*    */   }
/*    */ 
/*    */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*    */   {
/* 39 */     double theta = ((Oriented2D)object).orientation2D();
/* 40 */     this.transform.setToRotation(theta);
/*    */ 
/* 42 */     AffineTransform old = graphics.getTransform();
/* 43 */     AffineTransform translationTransform = new AffineTransform();
/* 44 */     translationTransform.setToTranslation(info.draw.x, info.draw.y);
/* 45 */     graphics.transform(translationTransform);
/* 46 */     graphics.transform(this.transform);
/* 47 */     getChild(object).draw(object, graphics, new DrawInfo2D(info, -info.draw.x, -info.draw.y));
/*    */ 
/* 49 */     graphics.setTransform(old);
/*    */   }
/*    */ 
/*    */   public boolean hitObject(Object object, DrawInfo2D range)
/*    */   {
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*    */   {
/* 60 */     return getChild(wrapper.getObject()).setSelected(wrapper, selected);
/*    */   }
/*    */ 
/*    */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*    */   {
/* 65 */     return getChild(wrapper.getObject()).getInspector(wrapper, state);
/*    */   }
/*    */ 
/*    */   public String getName(LocationWrapper wrapper)
/*    */   {
/* 70 */     return getChild(wrapper.getObject()).getName(wrapper);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.simple.TransformedPortrayal2D
 * JD-Core Version:    0.6.2
 */