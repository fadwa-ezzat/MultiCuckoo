/*     */ package sim.portrayal3d.simple;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import javax.media.j3d.Appearance;
/*     */ import javax.media.j3d.ColoringAttributes;
/*     */ import javax.media.j3d.GeometryArray;
/*     */ import javax.media.j3d.PolygonAttributes;
/*     */ import javax.media.j3d.QuadArray;
/*     */ import javax.media.j3d.Shape3D;
/*     */ import javax.media.j3d.Transform3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.media.j3d.TransparencyAttributes;
/*     */ import sim.display.GUIState;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ import sim.field.grid.DoubleGrid3D;
/*     */ import sim.field.grid.Grid3D;
/*     */ import sim.field.grid.IntGrid3D;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal3d.grid.ValueGridPortrayal3D;
/*     */ import sim.util.Int3D;
/*     */ 
/*     */ public class ValuePortrayal3D extends Shape3DPortrayal3D
/*     */ {
/*     */   public static final int SHAPE_CUBE = 0;
/*     */   public static final int SHAPE_SQUARE = 1;
/*  32 */   static final float[] verts_cube = { 0.5F, -0.5F, 0.5F, 0.5F, 0.5F, 0.5F, -0.5F, 0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F, -0.5F, -0.5F, -0.5F, 0.5F, -0.5F, 0.5F, 0.5F, -0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F, -0.5F, 0.5F, 0.5F, -0.5F, 0.5F, 0.5F, 0.5F, 0.5F, -0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F, 0.5F, 0.5F, -0.5F, 0.5F, -0.5F, -0.5F, -0.5F, -0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F, -0.5F, 0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F, -0.5F, -0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F, 0.5F };
/*     */ 
/*  53 */   static final float[] verts_square = { 0.5F, -0.5F, 0.0F, 0.5F, 0.5F, 0.0F, -0.5F, 0.5F, 0.0F, -0.5F, -0.5F, 0.0F };
/*     */   PolygonAttributes mPolyAttributes;
/*  95 */   static final Color SEMITRANSPARENT = new Color(64, 64, 64, 64);
/*     */ 
/*     */   public ValuePortrayal3D()
/*     */   {
/*  61 */     this(0);
/*     */   }
/*     */ 
/*     */   public boolean setTransform(TransformGroup j3dModel, Transform3D transform)
/*     */   {
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */   protected Shape3D getShape(TransformGroup j3dModel, int shapeNumber)
/*     */   {
/*  72 */     Shape3D p = null;
/*  73 */     if ((j3dModel.getChild(0) instanceof TransformGroup))
/*     */     {
/*  75 */       TransformGroup g = (TransformGroup)j3dModel.getChild(0);
/*  76 */       p = (Shape3D)g.getChild(0);
/*     */     }
/*     */     else
/*     */     {
/*  80 */       p = (Shape3D)j3dModel.getChild(0);
/*     */     }
/*  82 */     return p;
/*     */   }
/*     */ 
/*     */   static GeometryArray processArray(int shape)
/*     */   {
/*  88 */     float[] verts = shape == 0 ? verts_cube : verts_square;
/*  89 */     GeometryArray ga = new QuadArray(verts.length / 3, 1);
/*  90 */     ga.setCoordinates(0, verts);
/*  91 */     return ga;
/*     */   }
/*     */ 
/*     */   public ValuePortrayal3D(int shape)
/*     */   {
/* 102 */     super(processArray(shape), SEMITRANSPARENT);
/*     */ 
/* 104 */     this.mPolyAttributes = new PolygonAttributes();
/* 105 */     this.mPolyAttributes.setCapability(3);
/* 106 */     this.mPolyAttributes.clearCapabilityIsFrequent(3);
/* 107 */     if (shape == 1)
/*     */     {
/* 109 */       this.mPolyAttributes.setCapability(1);
/* 110 */       this.mPolyAttributes.clearCapabilityIsFrequent(1);
/*     */     }
/*     */     else {
/* 113 */       this.mPolyAttributes.setCullFace(1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PolygonAttributes polygonAttributes() {
/* 118 */     return this.mPolyAttributes;
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object obj, TransformGroup j3dModel)
/*     */   {
/* 126 */     Color color = ((ValueGridPortrayal3D)getCurrentFieldPortrayal()).getColorFor(obj);
/*     */ 
/* 129 */     if (j3dModel == null)
/*     */     {
/* 131 */       j3dModel = super.getModel(obj, j3dModel);
/*     */ 
/* 144 */       Appearance app = appearanceForColor(color);
/* 145 */       app.setPolygonAttributes(polygonAttributes());
/*     */ 
/* 165 */       setAppearance(j3dModel, app);
/*     */ 
/* 168 */       getShape(j3dModel, 0).setUserData(obj);
/*     */     }
/*     */     else
/*     */     {
/* 172 */       j3dModel = super.getModel(obj, j3dModel);
/*     */ 
/* 175 */       Appearance appearance = getAppearance(j3dModel);
/* 176 */       float[] c = color.getRGBComponents(null);
/* 177 */       appearance.getColoringAttributes().setColor(c[0], c[1], c[2]);
/* 178 */       appearance.getTransparencyAttributes().setTransparency(1.0F - c[3]);
/*     */     }
/* 180 */     return j3dModel;
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/* 263 */     Object field = ((ValueGridPortrayal3D)wrapper.getFieldPortrayal()).getField();
/*     */ 
/* 265 */     if (((field instanceof DoubleGrid3D)) || ((field instanceof DoubleGrid2D))) {
/* 266 */       return Inspector.getInspector(new DoubleFilter(wrapper), state, "Properties");
/*     */     }
/* 268 */     return Inspector.getInspector(new IntFilter(wrapper), state, "Properties");
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/* 275 */     ValueGridPortrayal3D portrayal = (ValueGridPortrayal3D)wrapper.getFieldPortrayal();
/* 276 */     return portrayal.getValueName() + " at " + wrapper.getLocationName();
/*     */   }
/*     */ 
/*     */   public static class IntFilter extends ValuePortrayal3D.Filter
/*     */   {
/*     */     public IntFilter(LocationWrapper wrapper)
/*     */     {
/* 241 */       super();
/*     */     }
/*     */ 
/*     */     public int getValue() {
/* 245 */       if ((this.grid instanceof IntGrid3D)) {
/* 246 */         return ((IntGrid3D)this.grid).field[this.x][this.y][this.z];
/*     */       }
/* 248 */       return ((sim.field.grid.IntGrid2D)this.grid).field[this.x][this.y];
/*     */     }
/*     */ 
/*     */     public void setValue(int val)
/*     */     {
/* 253 */       if ((this.grid instanceof IntGrid3D))
/* 254 */         ((IntGrid3D)this.grid).field[this.x][this.y][this.z] = ((int)this.fieldPortrayal.newValue(this.x, this.y, this.z, val));
/*     */       else
/* 256 */         ((sim.field.grid.IntGrid2D)this.grid).field[this.x][this.y] = ((int)this.fieldPortrayal.newValue(this.x, this.y, this.z, val));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class DoubleFilter extends ValuePortrayal3D.Filter
/*     */   {
/*     */     public DoubleFilter(LocationWrapper wrapper)
/*     */     {
/* 220 */       super();
/*     */     }
/*     */     public double getValue() {
/* 223 */       if ((this.grid instanceof DoubleGrid3D)) {
/* 224 */         return ((DoubleGrid3D)this.grid).field[this.x][this.y][this.z];
/*     */       }
/* 226 */       return ((DoubleGrid2D)this.grid).field[this.x][this.y];
/*     */     }
/*     */ 
/*     */     public void setValue(double val) {
/* 230 */       if ((this.grid instanceof DoubleGrid3D))
/* 231 */         ((DoubleGrid3D)this.grid).field[this.x][this.y][this.z] = this.fieldPortrayal.newValue(this.x, this.y, this.z, val);
/*     */       else
/* 233 */         ((DoubleGrid2D)this.grid).field[this.x][this.y] = this.fieldPortrayal.newValue(this.x, this.y, this.z, val);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract class Filter
/*     */   {
/*     */     int x;
/*     */     int y;
/*     */     int z;
/*     */     ValueGridPortrayal3D fieldPortrayal;
/*     */     Grid3D grid;
/*     */     String name;
/*     */ 
/*     */     public Filter(LocationWrapper wrapper)
/*     */     {
/* 206 */       this.fieldPortrayal = ((ValueGridPortrayal3D)wrapper.getFieldPortrayal());
/* 207 */       this.grid = ((Grid3D)this.fieldPortrayal.getField());
/* 208 */       Int3D loc = (Int3D)wrapper.getLocation();
/* 209 */       this.x = loc.x;
/* 210 */       this.y = loc.y;
/* 211 */       this.z = loc.z;
/* 212 */       this.name = (this.fieldPortrayal.getValueName() + " at " + wrapper.getLocationName());
/*     */     }
/*     */     public String toString() {
/* 215 */       return this.name;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.ValuePortrayal3D
 * JD-Core Version:    0.6.2
 */