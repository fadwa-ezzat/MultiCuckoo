/*     */ package sim.portrayal3d.grid;
/*     */ 
/*     */ import com.sun.j3d.utils.picking.PickIntersection;
/*     */ import com.sun.j3d.utils.picking.PickResult;
/*     */ import java.awt.Color;
/*     */ import java.util.BitSet;
/*     */ import javax.media.j3d.PolygonAttributes;
/*     */ import javax.media.j3d.Shape3D;
/*     */ import javax.media.j3d.Switch;
/*     */ import javax.media.j3d.Transform3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.vecmath.Vector3f;
/*     */ import sim.field.grid.AbstractGrid2D;
/*     */ import sim.field.grid.AbstractGrid3D;
/*     */ import sim.field.grid.DoubleGrid2D;
/*     */ import sim.field.grid.DoubleGrid3D;
/*     */ import sim.field.grid.IntGrid2D;
/*     */ import sim.field.grid.IntGrid3D;
/*     */ import sim.portrayal.FieldPortrayal;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.portrayal3d.FieldPortrayal3D;
/*     */ import sim.portrayal3d.Portrayal3D;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ import sim.portrayal3d.simple.ValuePortrayal3D;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.Int3D;
/*     */ import sim.util.MutableDouble;
/*     */ import sim.util.gui.ColorMap;
/*     */ import sim.util.gui.SimpleColorMap;
/*     */ 
/*     */ public class ValueGridPortrayal3D extends FieldPortrayal3D
/*     */ {
/*     */   String valueName;
/*     */   double scale;
/*  25 */   ColorMap map = new SimpleColorMap();
/*     */ 
/*  27 */   int width = 0;
/*  28 */   int height = 0;
/*  29 */   int length = 0;
/*     */ 
/*  31 */   final MutableDouble valueToPass = new MutableDouble(0.0D);
/*     */ 
/*  38 */   boolean dirtyScale = false;
/*     */ 
/*  42 */   ValuePortrayal3D defaultPortrayal = new ValuePortrayal3D();
/*     */ 
/*     */   public ColorMap getMap()
/*     */   {
/*  33 */     return this.map; } 
/*  34 */   public void setMap(ColorMap m) { this.map = m; } 
/*  35 */   public String getValueName() { return this.valueName; } 
/*  36 */   public void setValueName(String name) { this.valueName = name; }
/*     */ 
/*     */   public double getScale() {
/*  39 */     return this.scale; } 
/*  40 */   public void setScale(double val) { this.scale = val; this.dirtyScale = true;
/*     */   }
/*     */ 
/*     */   public Portrayal getDefaultPortrayal()
/*     */   {
/*  45 */     return this.defaultPortrayal;
/*     */   }
/*     */ 
/*     */   public void setField(Object field)
/*     */   {
/*  50 */     if (((field instanceof IntGrid3D)) || ((field instanceof DoubleGrid3D)) || ((field instanceof IntGrid2D)) || ((field instanceof DoubleGrid2D)))
/*  51 */       super.setField(field);
/*  52 */     else throw new RuntimeException("Invalid field for ValueGridPortrayal3D: " + field); 
/*     */   }
/*     */ 
/*  55 */   public Object getField() { return this.field; }
/*     */ 
/*     */   public ValueGridPortrayal3D()
/*     */   {
/*  59 */     this("Value", 1.0D);
/*     */   }
/*     */ 
/*     */   public ValueGridPortrayal3D(String valueName)
/*     */   {
/*  64 */     this(valueName, 1.0D);
/*     */   }
/*     */ 
/*     */   public ValueGridPortrayal3D(double s)
/*     */   {
/*  69 */     this("Value", s);
/*     */   }
/*     */ 
/*     */   public ValueGridPortrayal3D(String valueName, double scale)
/*     */   {
/*  74 */     this.valueName = valueName;
/*  75 */     this.scale = scale;
/*     */   }
/*     */ 
/*     */   public PolygonAttributes polygonAttributes()
/*     */   {
/*  80 */     return ((Portrayal3D)getPortrayalForObject(new ValueWrapper(0.0D, new Int3D(), this))).polygonAttributes();
/*     */   }
/*     */ 
/*     */   public double newValue(int x, int y, int z, double value)
/*     */   {
/*  88 */     if (((this.field instanceof IntGrid2D)) || ((this.field instanceof IntGrid3D))) value = (int)value;
/*     */ 
/*  90 */     if (this.map.validLevel(value)) return value;
/*     */ 
/*  92 */     if (this.field != null)
/*     */     {
/*  94 */       if ((this.field instanceof DoubleGrid3D))
/*  95 */         return ((DoubleGrid3D)this.field).field[x][y][z];
/*  96 */       if ((this.field instanceof IntGrid3D))
/*  97 */         return ((IntGrid3D)this.field).field[x][y][z];
/*  98 */       if ((this.field instanceof DoubleGrid2D)) {
/*  99 */         return ((DoubleGrid2D)this.field).field[x][y];
/*     */       }
/* 101 */       return ((IntGrid2D)this.field).field[x][y];
/*     */     }
/* 103 */     return this.map.defaultValue();
/*     */   }
/*     */ 
/*     */   double gridValue(int x, int y, int z)
/*     */   {
/* 109 */     if ((this.field instanceof DoubleGrid3D))
/* 110 */       return ((DoubleGrid3D)this.field).field[x][y][z];
/* 111 */     if ((this.field instanceof IntGrid3D))
/* 112 */       return ((IntGrid3D)this.field).field[x][y][z];
/* 113 */     if ((this.field instanceof DoubleGrid2D)) {
/* 114 */       return ((DoubleGrid2D)this.field).field[x][y];
/*     */     }
/* 116 */     return ((IntGrid2D)this.field).field[x][y];
/*     */   }
/*     */ 
/*     */   public TransformGroup createModel()
/*     */   {
/* 121 */     TransformGroup globalTG = new TransformGroup();
/* 122 */     globalTG.setCapability(12);
/*     */ 
/* 124 */     if (this.field == null) return globalTG;
/*     */ 
/* 126 */     this.dirtyScale = false;
/*     */ 
/* 128 */     Switch localSwitch = new Switch(-3);
/* 129 */     localSwitch.setCapability(17);
/* 130 */     localSwitch.setCapability(18);
/* 131 */     localSwitch.setCapability(12);
/*     */ 
/* 133 */     globalTG.addChild(localSwitch);
/*     */ 
/* 135 */     extractDimensions();
/*     */ 
/* 137 */     BitSet childMask = new BitSet(this.width * this.height * this.length);
/*     */ 
/* 139 */     Transform3D trans = new Transform3D();
/*     */ 
/* 141 */     Portrayal p = getPortrayalForObject(new ValueWrapper(0.0D, new Int3D(), this));
/* 142 */     if (!(p instanceof SimplePortrayal3D)) {
/* 143 */       throw new RuntimeException("Unexpected Portrayal " + p + "for object " + this.valueToPass + " -- expected a SimplePortrayal3D");
/*     */     }
/*     */ 
/* 146 */     SimplePortrayal3D portrayal = (SimplePortrayal3D)p;
/* 147 */     portrayal.setCurrentFieldPortrayal(this);
/*     */ 
/* 149 */     int i = 0;
/* 150 */     int width = this.width;
/* 151 */     int height = this.height;
/* 152 */     int length = this.length;
/* 153 */     for (int x = 0; x < width; x++) {
/* 154 */       for (int y = 0; y < height; y++)
/* 155 */         for (int z = 0; z < length; z++)
/*     */         {
/* 157 */           double value = gridValue(x, y, z);
/* 158 */           TransformGroup tg = portrayal.getModel(new ValueWrapper(0.0D, new Int3D(x, y, z), this), null);
/* 159 */           tg.setCapability(17);
/* 160 */           tg.setCapability(18);
/* 161 */           tg.setCapability(12);
/* 162 */           trans.setTranslation(new Vector3f(x, y, z));
/* 163 */           trans.setScale(this.scale);
/* 164 */           tg.setTransform(trans);
/*     */ 
/* 166 */           localSwitch.addChild(tg);
/*     */ 
/* 168 */           if (this.map.getAlpha(value) > 2)
/* 169 */             childMask.set(i);
/*     */           else {
/* 171 */             childMask.clear(i);
/*     */           }
/* 173 */           i++;
/*     */         }
/*     */     }
/* 176 */     localSwitch.setChildMask(childMask);
/* 177 */     return globalTG;
/*     */   }
/*     */ 
/*     */   public void updateModel(TransformGroup modelTG)
/*     */   {
/* 184 */     if (this.field == null) return;
/*     */ 
/* 186 */     extractDimensions();
/* 187 */     Switch localSwitch = (Switch)modelTG.getChild(0);
/* 188 */     BitSet childMask = localSwitch.getChildMask();
/*     */ 
/* 190 */     Portrayal p = getPortrayalForObject(this.valueToPass);
/* 191 */     if (!(p instanceof SimplePortrayal3D)) {
/* 192 */       throw new RuntimeException("Unexpected Portrayal " + p + "for object " + this.valueToPass + " -- expected a SimplePortrayal3D");
/*     */     }
/*     */ 
/* 195 */     SimplePortrayal3D portrayal = (SimplePortrayal3D)p;
/* 196 */     portrayal.setCurrentFieldPortrayal(this);
/*     */ 
/* 198 */     if ((this.dirtyScale) || (isDirtyField())) {
/* 199 */       reviseScale(localSwitch);
/*     */     }
/* 201 */     int i = 0;
/* 202 */     int width = this.width;
/* 203 */     int height = this.height;
/* 204 */     int length = this.length;
/* 205 */     for (int x = 0; x < width; x++)
/* 206 */       for (int y = 0; y < height; y++)
/* 207 */         for (int z = 0; z < length; z++)
/*     */         {
/* 209 */           TransformGroup tg = (TransformGroup)localSwitch.getChild(i);
/*     */ 
/* 216 */           Shape3D shape = (Shape3D)tg.getChild(0);
/*     */ 
/* 218 */           ValueWrapper wrapper = (ValueWrapper)shape.getUserData();
/* 219 */           double value = gridValue(x, y, z);
/* 220 */           double oldValue = wrapper.lastValue;
/*     */ 
/* 222 */           if (value != oldValue)
/* 223 */             if (this.map.getAlpha(value) > 2)
/*     */             {
/* 225 */               childMask.set(i);
/* 226 */               wrapper.lastValue = value;
/* 227 */               portrayal.getModel(wrapper, tg);
/*     */             } else {
/* 229 */               childMask.clear(i);
/*     */             }
/* 230 */           i++;
/*     */         }
/* 232 */     localSwitch.setChildMask(childMask);
/*     */   }
/*     */ 
/*     */   void reviseScale(Switch localSwitch)
/*     */   {
/* 238 */     Transform3D trans = new Transform3D();
/* 239 */     int i = 0;
/* 240 */     int width = this.width;
/* 241 */     int height = this.height;
/* 242 */     int length = this.length;
/* 243 */     for (int x = 0; x < width; x++)
/* 244 */       for (int y = 0; y < height; y++)
/* 245 */         for (int z = 0; z < length; z++)
/*     */         {
/* 247 */           TransformGroup tg = (TransformGroup)localSwitch.getChild(i);
/* 248 */           tg.getTransform(trans);
/* 249 */           trans.setScale(this.scale);
/* 250 */           tg.setTransform(trans);
/* 251 */           i++;
/*     */         }
/* 253 */     this.dirtyScale = false;
/*     */   }
/*     */ 
/*     */   void extractDimensions()
/*     */   {
/* 259 */     if (((this.field instanceof IntGrid3D)) || ((this.field instanceof DoubleGrid3D)))
/*     */     {
/* 261 */       AbstractGrid3D v = (AbstractGrid3D)this.field;
/* 262 */       int _width = v.getWidth();
/* 263 */       int _height = v.getHeight();
/* 264 */       int _length = v.getLength();
/* 265 */       if ((this.width != 0) && ((_width != this.width) || (_height != this.height) || (_length != this.length)))
/* 266 */         throw new RuntimeException("Cannot presently change the dimensions of a field once it's set in ValueGridPortrayal3D.  Sorry.");
/* 267 */       this.width = _width;
/* 268 */       this.height = _height;
/* 269 */       this.length = _length;
/*     */     }
/* 271 */     else if (((this.field instanceof IntGrid2D)) || ((this.field instanceof DoubleGrid2D)))
/*     */     {
/* 273 */       AbstractGrid2D v = (AbstractGrid2D)this.field;
/* 274 */       int _width = v.getWidth();
/* 275 */       int _height = v.getHeight();
/* 276 */       int _length = 1;
/* 277 */       if ((this.width != 0) && ((_width != this.width) || (_height != this.height) || (_length != this.length)))
/* 278 */         throw new RuntimeException("Cannot presently change the dimensions of a field once it's set in ValueGridPortrayal3D.  Sorry.");
/* 279 */       this.width = _width;
/* 280 */       this.height = _height;
/* 281 */       this.length = _length;
/*     */     } else {
/* 283 */       throw new RuntimeException("Invalid field for ValueGridPortrayal3D: " + this.field);
/*     */     }
/*     */   }
/*     */ 
/*     */   public LocationWrapper completedWrapper(LocationWrapper w, PickIntersection pi, PickResult pr) {
/* 288 */     return w;
/*     */   }
/*     */ 
/*     */   public Color getColorFor(Object wrapper)
/*     */   {
/* 335 */     return getMap().getColor(((ValueWrapper)wrapper).lastValue);
/*     */   }
/*     */ 
/*     */   static class ValueWrapper extends LocationWrapper
/*     */   {
/*     */     public double lastValue;
/*     */ 
/*     */     public ValueWrapper(double value, Object location, ValueGridPortrayal3D portrayal)
/*     */     {
/* 299 */       super(location, portrayal);
/* 300 */       this.lastValue = value;
/*     */     }
/*     */ 
/*     */     public Object getObject()
/*     */     {
/* 305 */       Int3D loc = (Int3D)this.location;
/* 306 */       Object field = this.fieldPortrayal.getField();
/* 307 */       MutableDouble val = (MutableDouble)this.object;
/* 308 */       if ((field instanceof DoubleGrid3D))
/* 309 */         val.val = ((DoubleGrid3D)field).field[loc.x][loc.y][loc.z];
/* 310 */       else if ((field instanceof IntGrid3D))
/* 311 */         val.val = ((IntGrid3D)field).field[loc.x][loc.y][loc.z];
/* 312 */       else if ((field instanceof DoubleGrid2D))
/* 313 */         val.val = ((DoubleGrid2D)field).field[loc.x][loc.y];
/*     */       else
/* 315 */         val.val = ((IntGrid2D)field).field[loc.x][loc.y];
/* 316 */       return val;
/*     */     }
/*     */ 
/*     */     public String getLocationName()
/*     */     {
/* 321 */       Int3D loc = (Int3D)this.location;
/* 322 */       Object field = this.fieldPortrayal.getField();
/* 323 */       if (((field instanceof DoubleGrid3D)) || ((field instanceof IntGrid3D)))
/* 324 */         return loc.toCoordinates();
/* 325 */       return new Int2D(loc.x, loc.y).toCoordinates();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.grid.ValueGridPortrayal3D
 * JD-Core Version:    0.6.2
 */