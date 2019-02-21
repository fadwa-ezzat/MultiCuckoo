/*     */ package sim.portrayal3d.grid.quad;
/*     */ 
/*     */ import com.sun.j3d.utils.picking.PickIntersection;
/*     */ import sim.display.GUIState;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ import sim.field.grid.Grid2D;
/*     */ import sim.field.grid.IntGrid2D;
/*     */ import sim.field.grid.ObjectGrid2D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.portrayal3d.grid.ValueGrid2DPortrayal3D;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.MutableDouble;
/*     */ import sim.util.gui.ColorMap;
/*     */ 
/*     */ public abstract class QuadPortrayal
/*     */   implements Portrayal
/*     */ {
/*     */   double zScale;
/*     */   ColorMap colorDispenser;
/*     */ 
/*     */   public ColorMap getMap()
/*     */   {
/*  47 */     return this.colorDispenser; } 
/*  48 */   public void setMap(ColorMap map) { this.colorDispenser = map; } 
/*     */   public double getZScale() {
/*  50 */     return this.zScale; } 
/*  51 */   public void setZScale(double scale) { this.zScale = scale; }
/*     */ 
/*     */ 
/*     */   public abstract void setData(ValueGridCellInfo paramValueGridCellInfo, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */   public QuadPortrayal(ColorMap colorDispenser, double zScale)
/*     */   {
/*  58 */     this.colorDispenser = colorDispenser;
/*  59 */     this.zScale = zScale;
/*     */   }
/*     */ 
/*     */   public String getStatus(LocationWrapper wrapper)
/*     */   {
/*  64 */     return getName(wrapper) + ": " + ((MutableDouble)wrapper.getObject()).val;
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/*  69 */     ValueGrid2DPortrayal3D portrayal = (ValueGrid2DPortrayal3D)wrapper.getFieldPortrayal();
/*  70 */     return portrayal.getValueName() + " at " + wrapper.getLocationName();
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/*  76 */     if (selected) return false;
/*  77 */     return true;
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/* 116 */     if (wrapper == null) return null;
/* 117 */     Grid2D grid = (Grid2D)((ValueGrid2DPortrayal3D)wrapper.getFieldPortrayal()).getField();
/* 118 */     if ((grid instanceof DoubleGrid2D))
/* 119 */       return Inspector.getInspector(new DoubleFilter(wrapper), state, "Properties");
/* 120 */     if ((grid instanceof IntGrid2D))
/* 121 */       return Inspector.getInspector(new IntFilter(wrapper), state, "Properties");
/* 122 */     if ((grid instanceof ObjectGrid2D))
/* 123 */       return Inspector.getInspector(new ObjectFilter(wrapper), state, "Properties");
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */   public Int2D getCellForIntersection(PickIntersection pi, Grid2D field)
/*     */   {
/* 129 */     int[] indices = pi.getPrimitiveVertexIndices();
/* 130 */     if (indices == null) {
/* 131 */       return null;
/*     */     }
/* 133 */     int height = field.getHeight();
/* 134 */     int x = indices[0] / 4 / height;
/* 135 */     int y = indices[0] / 4 % height;
/* 136 */     return new Int2D(x, y);
/*     */   }
/*     */ 
/*     */   public static class IntFilter extends QuadPortrayal.Filter
/*     */   {
/*     */     public IntFilter(LocationWrapper wrapper)
/*     */     {
/* 109 */       super(); } 
/* 110 */     public int getValue() { return ((IntGrid2D)this.fieldPortrayal.getField()).field[this.x][this.y]; } 
/* 111 */     public void setValue(int val) { ((IntGrid2D)this.fieldPortrayal.getField()).field[this.x][this.y] = ((int)this.fieldPortrayal.newValue(this.x, this.y, val)); }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class DoubleFilter extends QuadPortrayal.Filter
/*     */   {
/*     */     public DoubleFilter(LocationWrapper wrapper)
/*     */     {
/* 102 */       super(); } 
/* 103 */     public double getValue() { return ((DoubleGrid2D)this.fieldPortrayal.getField()).field[this.x][this.y]; } 
/* 104 */     public void setValue(double val) { ((DoubleGrid2D)this.fieldPortrayal.getField()).field[this.x][this.y] = this.fieldPortrayal.newValue(this.x, this.y, val); }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class ObjectFilter extends QuadPortrayal.Filter
/*     */   {
/*     */     public ObjectFilter(LocationWrapper wrapper)
/*     */     {
/*  96 */       super(); } 
/*  97 */     public double getValue() { return this.fieldPortrayal.doubleValue(((ObjectGrid2D)this.fieldPortrayal.getField()).field[this.x][this.y]); }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static abstract class Filter
/*     */   {
/*     */     int x;
/*     */     int y;
/*     */     ValueGrid2DPortrayal3D fieldPortrayal;
/*     */ 
/*     */     public Filter(LocationWrapper wrapper)
/*     */     {
/*  87 */       this.fieldPortrayal = ((ValueGrid2DPortrayal3D)wrapper.getFieldPortrayal());
/*  88 */       Int2D loc = (Int2D)wrapper.getLocation();
/*  89 */       this.x = loc.x;
/*  90 */       this.y = loc.y;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.grid.quad.QuadPortrayal
 * JD-Core Version:    0.6.2
 */