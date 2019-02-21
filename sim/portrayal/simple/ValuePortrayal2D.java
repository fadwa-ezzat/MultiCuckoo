/*     */ package sim.portrayal.simple;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import sim.display.GUIState;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ import sim.portrayal.DrawInfo2D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.grid.ValueGridPortrayal2D;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.MutableDouble;
/*     */ import sim.util.gui.ColorMap;
/*     */ 
/*     */ public class ValuePortrayal2D extends RectanglePortrayal2D
/*     */ {
/*     */   public ValuePortrayal2D()
/*     */   {
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public ValuePortrayal2D(ValueGridPortrayal2D parent)
/*     */   {
/*  32 */     super(null);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setParent(ValueGridPortrayal2D parent)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
/*     */   {
/*  45 */     ValueGridPortrayal2D parent = (ValueGridPortrayal2D)info.fieldPortrayal;
/*  46 */     double levelHere = ((MutableDouble)object).val;
/*  47 */     Color c = parent.getMap().getColor(levelHere);
/*  48 */     if (c.getAlpha() != 0)
/*     */     {
/*  50 */       this.paint = c;
/*  51 */       super.draw(object, graphics, info);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/*  95 */     if ((((ValueGridPortrayal2D)wrapper.getFieldPortrayal()).getField() instanceof DoubleGrid2D)) {
/*  96 */       return Inspector.getInspector(new DoubleFilter(wrapper), state, "Properties");
/*     */     }
/*  98 */     return Inspector.getInspector(new IntFilter(wrapper), state, "Properties");
/*     */   }
/*     */ 
/*     */   public String getStatus(LocationWrapper wrapper)
/*     */   {
/* 104 */     return getName(wrapper) + ": " + ((MutableDouble)wrapper.getObject()).val;
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/* 109 */     ValueGridPortrayal2D portrayal = (ValueGridPortrayal2D)wrapper.getFieldPortrayal();
/* 110 */     return portrayal.getValueName() + " at " + wrapper.getLocationName();
/*     */   }
/*     */ 
/*     */   public static class IntFilter extends ValuePortrayal2D.Filter
/*     */   {
/*     */     public IntFilter(LocationWrapper wrapper)
/*     */     {
/*  87 */       super(); } 
/*  88 */     public int getValue() { return ((sim.field.grid.IntGrid2D)this.fieldPortrayal.getField()).field[this.x][this.y]; } 
/*  89 */     public void setValue(int val) { ((sim.field.grid.IntGrid2D)this.fieldPortrayal.getField()).field[this.x][this.y] = ((int)this.fieldPortrayal.newValue(this.x, this.y, val)); }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class DoubleFilter extends ValuePortrayal2D.Filter
/*     */   {
/*     */     public DoubleFilter(LocationWrapper wrapper)
/*     */     {
/*  79 */       super(); } 
/*  80 */     public double getValue() { return ((DoubleGrid2D)this.fieldPortrayal.getField()).field[this.x][this.y]; } 
/*  81 */     public void setValue(double val) { ((DoubleGrid2D)this.fieldPortrayal.getField()).field[this.x][this.y] = this.fieldPortrayal.newValue(this.x, this.y, val); }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static abstract class Filter
/*     */   {
/*     */     int x;
/*     */     int y;
/*     */     ValueGridPortrayal2D fieldPortrayal;
/*     */     String name;
/*     */ 
/*     */     public Filter(LocationWrapper wrapper)
/*     */     {
/*  63 */       this.fieldPortrayal = ((ValueGridPortrayal2D)wrapper.getFieldPortrayal());
/*  64 */       Int2D loc = (Int2D)wrapper.getLocation();
/*  65 */       this.x = loc.x;
/*  66 */       this.y = loc.y;
/*  67 */       this.name = (this.fieldPortrayal.getValueName() + " at " + wrapper.getLocationName());
/*     */     }
/*  69 */     public String toString() { return this.name; }
/*     */ 
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.simple.ValuePortrayal2D
 * JD-Core Version:    0.6.2
 */