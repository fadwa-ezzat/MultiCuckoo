/*     */ package sim.portrayal3d.grid;
/*     */ 
/*     */ import com.sun.j3d.utils.picking.PickIntersection;
/*     */ import com.sun.j3d.utils.picking.PickResult;
/*     */ import java.awt.Color;
/*     */ import java.awt.Image;
/*     */ import javax.media.j3d.Appearance;
/*     */ import javax.media.j3d.BranchGroup;
/*     */ import javax.media.j3d.ColoringAttributes;
/*     */ import javax.media.j3d.GeometryArray;
/*     */ import javax.media.j3d.Node;
/*     */ import javax.media.j3d.PolygonAttributes;
/*     */ import javax.media.j3d.QuadArray;
/*     */ import javax.media.j3d.Shape3D;
/*     */ import javax.media.j3d.TexCoordGeneration;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.media.j3d.TransparencyAttributes;
/*     */ import javax.media.j3d.TriangleFanArray;
/*     */ import javax.vecmath.Vector3d;
/*     */ import javax.vecmath.Vector4f;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ import sim.field.grid.Grid2D;
/*     */ import sim.field.grid.IntGrid2D;
/*     */ import sim.field.grid.ObjectGrid2D;
/*     */ import sim.portrayal.FieldPortrayal;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.portrayal3d.FieldPortrayal3D;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ import sim.portrayal3d.grid.quad.QuadPortrayal;
/*     */ import sim.portrayal3d.grid.quad.TilePortrayal;
/*     */ import sim.portrayal3d.grid.quad.ValueGridCellInfo;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.MutableDouble;
/*     */ import sim.util.Valuable;
/*     */ import sim.util.gui.ColorMap;
/*     */ import sim.util.gui.SimpleColorMap;
/*     */ 
/*     */ public class ValueGrid2DPortrayal3D extends FieldPortrayal3D
/*     */ {
/*     */   Image image;
/*  58 */   double transparency = 1.0D;
/*  59 */   PolygonAttributes mPolyAttributes = new PolygonAttributes();
/*     */ 
/*  61 */   boolean useTriangles = false;
/*     */   String valueName;
/*     */   QuadPortrayal defaultPortrayal;
/*     */   float[] coords;
/*     */   float[] colors;
/* 151 */   boolean resetField = true;
/*     */ 
/* 167 */   protected Vector3d tmpVect = new Vector3d();
/*     */   private ValueGridCellInfo tmpGCI;
/*     */ 
/*     */   public boolean isUsingTriangles()
/*     */   {
/*  62 */     return this.useTriangles; } 
/*  63 */   public void setUsingTriangles(boolean val) { this.useTriangles = val; }
/*     */ 
/*     */   public Object getField()
/*     */   {
/*  67 */     return this.field;
/*     */   }
/*     */ 
/*     */   public String getValueName()
/*     */   {
/*  72 */     return this.valueName; } 
/*  73 */   public void setValueName(String name) { this.valueName = name; }
/*     */ 
/*     */   public double getTransparency()
/*     */   {
/*  77 */     return this.transparency;
/*     */   }
/*     */ 
/*     */   public void setTransparency(double transparency)
/*     */   {
/*  83 */     if ((transparency >= 0.0D) && (transparency <= 1.0D))
/*  84 */       this.transparency = transparency;
/*     */   }
/*     */ 
/*     */   public void setImage(Image image)
/*     */   {
/*  90 */     this.image = image;
/*     */   }
/*     */ 
/*     */   public Image getImage()
/*     */   {
/*  95 */     return this.image;
/*     */   }
/*     */ 
/*     */   public ValueGrid2DPortrayal3D(String valueName, Image image)
/*     */   {
/* 102 */     this(valueName, 1.0D);
/* 103 */     this.image = image;
/*     */   }
/*     */ 
/*     */   public ValueGrid2DPortrayal3D(String valueName, double transparency)
/*     */   {
/* 110 */     this.valueName = valueName;
/*     */ 
/* 113 */     SimpleColorMap cm = new SimpleColorMap();
/* 114 */     cm.setLevels(0.0D, 1.0D, Color.blue, Color.red);
/* 115 */     this.defaultPortrayal = new TilePortrayal(cm);
/* 116 */     this.transparency = transparency;
/*     */ 
/* 118 */     this.mPolyAttributes.setCapability(1);
/* 119 */     this.mPolyAttributes.setCapability(3);
/* 120 */     this.mPolyAttributes.clearCapabilityIsFrequent(1);
/* 121 */     this.mPolyAttributes.clearCapabilityIsFrequent(3);
/*     */   }
/*     */ 
/*     */   public ValueGrid2DPortrayal3D(String valueName)
/*     */   {
/* 127 */     this(valueName, 1.0D);
/*     */   }
/*     */ 
/*     */   public ValueGrid2DPortrayal3D()
/*     */   {
/* 134 */     this("Value");
/*     */   }
/*     */ 
/*     */   public PolygonAttributes polygonAttributes()
/*     */   {
/* 139 */     return this.mPolyAttributes;
/*     */   }
/*     */ 
/*     */   public Portrayal getDefaultPortrayal()
/*     */   {
/* 145 */     return this.defaultPortrayal;
/*     */   }
/*     */ 
/*     */   public void setField(Object grid)
/*     */   {
/* 154 */     if (getField() == grid) return;
/* 155 */     if ((grid instanceof Grid2D)) super.setField(grid); else {
/* 156 */       throw new RuntimeException("ValueGridPortrayal2D3D cannot portray the object: " + grid);
/*     */     }
/* 158 */     Grid2D field = (Grid2D)this.field;
/*     */ 
/* 160 */     this.tmpGCI = new ValueGridCellInfo(this, field);
/* 161 */     this.coords = new float[field.getWidth() * field.getHeight() * 4 * 3];
/* 162 */     this.colors = new float[field.getWidth() * field.getHeight() * 4 * 3];
/* 163 */     this.resetField = true;
/*     */   }
/*     */ 
/*     */   public double doubleValue(Object obj)
/*     */   {
/* 179 */     if (obj == null) return 0.0D;
/* 180 */     if ((obj instanceof Number)) return ((Number)obj).doubleValue();
/* 181 */     if ((obj instanceof Valuable)) return ((Valuable)obj).doubleValue();
/* 182 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   public TransformGroup createModel()
/*     */   {
/* 190 */     TransformGroup globalTG = new TransformGroup();
/* 191 */     globalTG.setCapability(12);
/* 192 */     globalTG.setCapability(13);
/* 193 */     globalTG.setCapability(14);
/*     */ 
/* 195 */     if (this.field == null) return globalTG;
/*     */ 
/* 197 */     QuadPortrayal quadPortrayal = (QuadPortrayal)getPortrayalForObject(this.tmpGCI);
/*     */ 
/* 199 */     Grid2D field = (Grid2D)this.field;
/*     */     GeometryArray ga;
/*     */     GeometryArray ga;
/* 202 */     if (!this.useTriangles) {
/* 203 */       ga = new QuadArray(4 * field.getWidth() * field.getHeight(), 0x5 | (this.image != null ? 32 : 0));
/*     */     }
/*     */     else
/*     */     {
/* 208 */       int[] lengths = new int[field.getWidth() * field.getHeight()];
/* 209 */       for (int i = 0; i < lengths.length; i++)
/* 210 */         lengths[i] = 4;
/* 211 */       ga = new TriangleFanArray(4 * lengths.length, 0x5 | (this.image != null ? 32 : 0), lengths);
/*     */     }
/*     */ 
/* 217 */     ga.setCapability(3);
/* 218 */     ga.setCapability(1);
/* 219 */     SimplePortrayal3D.setPickableFlags(ga);
/*     */ 
/* 221 */     this.tmpVect.z = 0.0D;
/* 222 */     int quadIndex = 0;
/* 223 */     int width = field.getWidth();
/* 224 */     int height = field.getHeight();
/*     */ 
/* 226 */     for (int i = 0; i < width; i++)
/*     */     {
/* 228 */       this.tmpGCI.x = i;
/*     */ 
/* 232 */       this.tmpVect.x = i;
/* 233 */       for (int j = 0; j < height; j++)
/*     */       {
/* 235 */         this.tmpGCI.y = j;
/* 236 */         this.tmpVect.y = j;
/*     */ 
/* 238 */         quadPortrayal.setData(this.tmpGCI, this.coords, this.colors, quadIndex, width, height);
/* 239 */         quadIndex++;
/*     */       }
/*     */     }
/* 242 */     ga.setCoordinates(0, this.coords);
/* 243 */     ga.setColors(0, this.colors);
/*     */ 
/* 245 */     Shape3D shape = new Shape3D(ga);
/* 246 */     shape.setCapability(12);
/*     */     Appearance appearance;
/* 249 */     if (this.image != null)
/*     */     {
/* 251 */       Appearance appearance = SimplePortrayal3D.appearanceForImage(this.image, true);
/* 252 */       TexCoordGeneration tex = new TexCoordGeneration();
/* 253 */       Vector4f s = new Vector4f(1.0F / width, 0.0F, 0.0F, 0.0F);
/* 254 */       tex.setPlaneS(s);
/* 255 */       Vector4f t = new Vector4f(0.0F, 1.0F / height, 0.0F, 0.0F);
/* 256 */       tex.setPlaneT(t);
/* 257 */       appearance.setTexCoordGeneration(tex);
/*     */     }
/*     */     else
/*     */     {
/* 261 */       appearance = new Appearance();
/* 262 */       if (this.transparency < 1.0D)
/*     */       {
/* 264 */         appearance.setTransparencyAttributes(new TransparencyAttributes(2, 1.0F - (float)this.transparency));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 269 */     appearance.setCapability(15);
/* 270 */     appearance.setPolygonAttributes(this.mPolyAttributes);
/* 271 */     appearance.setColoringAttributes(new ColoringAttributes(1.0F, 1.0F, 1.0F, 3));
/*     */ 
/* 273 */     shape.setAppearance(appearance);
/*     */ 
/* 275 */     LocationWrapper pi = new LocationWrapper(null, null, this);
/* 276 */     shape.setUserData(pi);
/*     */ 
/* 278 */     BranchGroup bg = new BranchGroup();
/* 279 */     bg.setCapability(12);
/* 280 */     bg.setCapability(17);
/* 281 */     bg.addChild(shape);
/* 282 */     globalTG.addChild(bg);
/* 283 */     return globalTG;
/*     */   }
/*     */ 
/*     */   public void updateModel(TransformGroup modelTG)
/*     */   {
/* 288 */     if ((this.resetField) || (modelTG.numChildren() == 0))
/*     */     {
/* 291 */       TransformGroup g = createModel();
/* 292 */       if (g.numChildren() > 0)
/*     */       {
/* 294 */         Node model = g.getChild(0);
/* 295 */         g.removeChild(0);
/*     */ 
/* 302 */         modelTG.removeAllChildren();
/* 303 */         modelTG.addChild(model);
/*     */       }
/* 305 */       this.resetField = false;
/*     */     }
/*     */     else
/*     */     {
/* 309 */       Grid2D field = (Grid2D)this.field;
/*     */ 
/* 311 */       QuadPortrayal quadPortrayal = (QuadPortrayal)getPortrayalForObject(this.tmpGCI);
/* 312 */       BranchGroup bg = (BranchGroup)modelTG.getChild(0);
/* 313 */       Shape3D shape = (Shape3D)bg.getChild(0);
/* 314 */       GeometryArray ga = (GeometryArray)shape.getGeometry();
/* 315 */       int quadIndex = 0;
/* 316 */       int width = field.getWidth();
/* 317 */       int height = field.getHeight();
/*     */ 
/* 319 */       for (int i = 0; i < width; i++)
/*     */       {
/* 321 */         this.tmpGCI.x = i;
/* 322 */         for (int j = 0; j < height; j++)
/*     */         {
/* 324 */           this.tmpGCI.y = j;
/*     */ 
/* 326 */           quadPortrayal.setData(this.tmpGCI, this.coords, this.colors, quadIndex, width, height);
/* 327 */           quadIndex++;
/*     */         }
/*     */       }
/* 330 */       ga.setCoordinates(0, this.coords);
/* 331 */       ga.setColors(0, this.colors);
/*     */     }
/*     */   }
/*     */ 
/*     */   public double newValue(int x, int y, double value)
/*     */   {
/* 340 */     if ((this.field instanceof IntGrid2D)) value = (int)value;
/*     */ 
/* 342 */     this.tmpGCI.x = x;
/* 343 */     this.tmpGCI.y = y;
/* 344 */     QuadPortrayal quadPortrayal = (QuadPortrayal)getPortrayalForObject(this.tmpGCI);
/* 345 */     if (quadPortrayal.getMap().validLevel(value)) return value;
/*     */ 
/* 348 */     if (this.field != null)
/*     */     {
/* 350 */       if ((this.field instanceof DoubleGrid2D))
/* 351 */         return ((DoubleGrid2D)this.field).field[x][y];
/* 352 */       if ((this.field instanceof ObjectGrid2D))
/* 353 */         return doubleValue(((ObjectGrid2D)this.field).field[x][y]);
/* 354 */       return ((IntGrid2D)this.field).field[x][y];
/*     */     }
/* 356 */     return quadPortrayal.getMap().defaultValue();
/*     */   }
/*     */ 
/*     */   public LocationWrapper completedWrapper(LocationWrapper w, PickIntersection pi, PickResult pr)
/*     */   {
/* 361 */     Grid2D field = (Grid2D)this.field;
/*     */ 
/* 363 */     return new LocationWrapper(new ValueGridCellInfo(this, field), ((QuadPortrayal)getPortrayalForObject(this.tmpGCI)).getCellForIntersection(pi, field), this)
/*     */     {
/* 371 */       MutableDouble val = null;
/*     */ 
/*     */       public Object getObject()
/*     */       {
/* 375 */         if (this.val == null) this.val = new MutableDouble(0.0D);
/* 376 */         this.val.val = ((ValueGridCellInfo)this.object).value();
/* 377 */         return this.val;
/*     */       }
/*     */ 
/*     */       public String getLocationName() {
/* 381 */         if (this.location != null) return ((Int2D)this.location).toCoordinates();
/* 382 */         return null;
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.grid.ValueGrid2DPortrayal3D
 * JD-Core Version:    0.6.2
 */