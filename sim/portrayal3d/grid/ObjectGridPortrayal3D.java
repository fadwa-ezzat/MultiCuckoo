/*     */ package sim.portrayal3d.grid;
/*     */ 
/*     */ import com.sun.j3d.utils.picking.PickIntersection;
/*     */ import com.sun.j3d.utils.picking.PickResult;
/*     */ import java.util.HashMap;
/*     */ import javax.media.j3d.BranchGroup;
/*     */ import javax.media.j3d.Group;
/*     */ import javax.media.j3d.Node;
/*     */ import javax.media.j3d.SceneGraphPath;
/*     */ import javax.media.j3d.Transform3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.vecmath.Vector3d;
/*     */ import sim.field.grid.ObjectGrid2D;
/*     */ import sim.field.grid.ObjectGrid3D;
/*     */ import sim.portrayal.FieldPortrayal;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal.Portrayal;
/*     */ import sim.portrayal.grid.ObjectGridPortrayal2D.Message;
/*     */ import sim.portrayal3d.FieldPortrayal3D;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Int2D;
/*     */ import sim.util.Int3D;
/*     */ import sim.util.IntBag;
/*     */ 
/*     */ public class ObjectGridPortrayal3D extends FieldPortrayal3D
/*     */ {
/*     */   static final int SEARCH_DISTANCE = 2;
/*     */   static final int BAG_SIZE = 125;
/* 322 */   IntBag xPos = new IntBag(125);
/* 323 */   IntBag yPos = new IntBag(125);
/* 324 */   IntBag zPos = new IntBag(125);
/*     */ 
/* 340 */   final ObjectGridPortrayal2D.Message unknown = new ObjectGridPortrayal2D.Message("It's too costly to figure out where the object went.");
/*     */ 
/*     */   protected TransformGroup createModel()
/*     */   {
/*  43 */     TransformGroup globalTG = new TransformGroup();
/*  44 */     globalTG.setCapability(12);
/*     */ 
/*  47 */     Group global = new Group();
/*  48 */     global.setCapability(12);
/*  49 */     global.setCapability(13);
/*  50 */     global.setCapability(14);
/*  51 */     global.setUserData(this);
/*     */ 
/*  53 */     global.setCapability(1);
/*     */ 
/*  55 */     globalTG.addChild(global);
/*     */ 
/*  57 */     if (this.field == null) return globalTG;
/*  58 */     Transform3D tmpLocalT = new Transform3D();
/*     */ 
/*  60 */     if ((this.field instanceof ObjectGrid2D))
/*     */     {
/*  62 */       Object[][] grid = ((ObjectGrid2D)this.field).field;
/*  63 */       for (int x = 0; x < grid.length; x++)
/*     */       {
/*  65 */         Object[] gridx = grid[x];
/*  66 */         for (int y = 0; y < gridx.length; y++)
/*     */         {
/*  68 */           Object o = gridx[y];
/*     */ 
/*  70 */           Portrayal p = getPortrayalForObject(o);
/*  71 */           if (!(p instanceof SimplePortrayal3D)) {
/*  72 */             throw new RuntimeException("Unexpected Portrayal " + p + " for object " + o + " -- expecting a SimplePortrayal3D");
/*     */           }
/*  74 */           SimplePortrayal3D p3d = (SimplePortrayal3D)p;
/*  75 */           p3d.setCurrentFieldPortrayal(this);
/*  76 */           TransformGroup newTransformGroup = p3d.getModel(o, null);
/*  77 */           newTransformGroup.setCapability(12);
/*  78 */           newTransformGroup.setCapability(18);
/*  79 */           newTransformGroup.setCapability(17);
/*     */ 
/*  81 */           newTransformGroup.setCapability(1);
/*  82 */           tmpLocalT.setTranslation(new Vector3d(x, y, 0.0D));
/*  83 */           newTransformGroup.setTransform(tmpLocalT);
/*  84 */           newTransformGroup.setUserData(new Int2D(x, y));
/*     */ 
/*  86 */           BranchGroup bg = new BranchGroup();
/*  87 */           bg.setCapability(12);
/*  88 */           bg.setCapability(17);
/*     */ 
/*  90 */           bg.setCapability(1);
/*  91 */           bg.addChild(newTransformGroup);
/*  92 */           bg.setUserData(o);
/*  93 */           global.addChild(bg);
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  99 */       Object[][][] grid = ((ObjectGrid3D)this.field).field;
/* 100 */       for (int x = 0; x < grid.length; x++)
/*     */       {
/* 102 */         Object[][] gridx = grid[x];
/* 103 */         for (int y = 0; y < gridx.length; y++)
/*     */         {
/* 105 */           Object[] gridy = gridx[y];
/* 106 */           for (int z = 0; z < gridy.length; z++)
/*     */           {
/* 108 */             Object o = gridy[z];
/*     */ 
/* 110 */             Portrayal p = getPortrayalForObject(o);
/* 111 */             if (!(p instanceof SimplePortrayal3D)) {
/* 112 */               throw new RuntimeException("Unexpected Portrayal " + p + " for object " + o + " -- expecting a SimplePortrayal3D");
/*     */             }
/* 114 */             SimplePortrayal3D p3d = (SimplePortrayal3D)p;
/* 115 */             p3d.setCurrentFieldPortrayal(this);
/* 116 */             TransformGroup newTransformGroup = p3d.getModel(o, null);
/* 117 */             newTransformGroup.setCapability(12);
/* 118 */             newTransformGroup.setCapability(18);
/* 119 */             newTransformGroup.setCapability(17);
/*     */ 
/* 121 */             newTransformGroup.setCapability(1);
/* 122 */             tmpLocalT.setTranslation(new Vector3d(x, y, z));
/* 123 */             newTransformGroup.setUserData(new Int3D(x, y, z));
/* 124 */             newTransformGroup.setTransform(tmpLocalT);
/*     */ 
/* 126 */             BranchGroup bg = new BranchGroup();
/* 127 */             bg.setCapability(12);
/* 128 */             bg.setCapability(17);
/*     */ 
/* 130 */             bg.setCapability(1);
/* 131 */             bg.addChild(newTransformGroup);
/* 132 */             bg.setUserData(o);
/* 133 */             global.addChild(bg);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 139 */     return globalTG;
/*     */   }
/*     */ 
/*     */   protected void updateModel(TransformGroup globalTG)
/*     */   {
/* 146 */     Group global = (Group)globalTG.getChild(0);
/*     */ 
/* 148 */     if (this.field == null) return;
/*     */ 
/* 150 */     int count = 0;
/*     */ 
/* 155 */     HashMap models = new HashMap();
/*     */ 
/* 157 */     Transform3D tmpLocalT = new Transform3D();
/* 158 */     count = 0;
/* 159 */     if ((this.field instanceof ObjectGrid2D))
/*     */     {
/* 161 */       Object[][] grid = ((ObjectGrid2D)this.field).field;
/* 162 */       for (int x = 0; x < grid.length; x++)
/*     */       {
/* 164 */         Object[] gridx = grid[x];
/* 165 */         for (int y = 0; y < gridx.length; y++)
/*     */         {
/* 167 */           Object o = gridx[y];
/*     */ 
/* 169 */           Portrayal p = getPortrayalForObject(o);
/* 170 */           if (!(p instanceof SimplePortrayal3D)) {
/* 171 */             throw new RuntimeException("Unexpected Portrayal " + p + " for object " + o + " -- expecting a SimplePortrayal3D");
/*     */           }
/* 173 */           SimplePortrayal3D p3d = (SimplePortrayal3D)p;
/* 174 */           p3d.setCurrentFieldPortrayal(this);
/* 175 */           BranchGroup bg = (BranchGroup)global.getChild(count++);
/* 176 */           TransformGroup originalTransformGroup = null;
/* 177 */           if (bg.numChildren() > 0) originalTransformGroup = (TransformGroup)bg.getChild(0);
/* 178 */           TransformGroup newTransformGroup = null;
/* 179 */           Object originalData = bg.getUserData();
/* 180 */           if (originalData == o)
/*     */           {
/* 182 */             newTransformGroup = p3d.getModel(o, originalTransformGroup);
/*     */           }
/*     */           else
/*     */           {
/* 186 */             Bag b = (Bag)models.get(o);
/* 187 */             if ((b != null) && (b.numObjs > 0))
/*     */             {
/* 190 */               BranchGroup replacementBranchGroup = (BranchGroup)b.remove(0);
/* 191 */               originalTransformGroup = (TransformGroup)replacementBranchGroup.getChild(0);
/* 192 */               newTransformGroup = p3d.getModel(o, originalTransformGroup);
/* 193 */               if (newTransformGroup == originalTransformGroup)
/* 194 */                 global.setChild(replacementBranchGroup, count - 1);
/*     */             }
/*     */             else
/*     */             {
/* 198 */               newTransformGroup = p3d.getModel(o, null);
/*     */             }
/*     */           }
/*     */ 
/* 202 */           if (newTransformGroup != originalTransformGroup)
/*     */           {
/* 205 */             newTransformGroup.setCapability(12);
/* 206 */             newTransformGroup.setCapability(18);
/* 207 */             newTransformGroup.setCapability(17);
/*     */ 
/* 209 */             newTransformGroup.setCapability(1);
/*     */ 
/* 211 */             BranchGroup bg2 = new BranchGroup();
/* 212 */             bg2.setCapability(12);
/* 213 */             bg2.setCapability(17);
/*     */ 
/* 215 */             bg2.setCapability(1);
/* 216 */             tmpLocalT.setTranslation(new Vector3d(x, y, 0.0D));
/* 217 */             newTransformGroup.setTransform(tmpLocalT);
/* 218 */             newTransformGroup.setUserData(new Int2D(x, y));
/* 219 */             bg2.addChild(newTransformGroup);
/* 220 */             bg2.setUserData(o);
/* 221 */             global.setChild(bg2, count - 1);
/*     */ 
/* 224 */             Bag b = (Bag)models.get(originalData);
/* 225 */             if (b == null) { b = new Bag(); models.put(originalData, b); }
/* 226 */             b.add(bg);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 233 */       Object[][][] grid = ((ObjectGrid3D)this.field).field;
/* 234 */       for (int x = 0; x < grid.length; x++)
/*     */       {
/* 236 */         Object[][] gridx = grid[x];
/* 237 */         for (int y = 0; y < gridx.length; y++)
/*     */         {
/* 239 */           Object[] gridy = gridx[y];
/* 240 */           for (int z = 0; z < gridy.length; z++)
/*     */           {
/* 242 */             Object o = gridy[z];
/*     */ 
/* 244 */             Portrayal p = getPortrayalForObject(o);
/* 245 */             if (!(p instanceof SimplePortrayal3D)) {
/* 246 */               throw new RuntimeException("Unexpected Portrayal " + p + " for object " + o + " -- expecting a SimplePortrayal3D");
/*     */             }
/* 248 */             SimplePortrayal3D p3d = (SimplePortrayal3D)p;
/* 249 */             p3d.setCurrentFieldPortrayal(this);
/* 250 */             BranchGroup bg = (BranchGroup)global.getChild(count++);
/* 251 */             TransformGroup originalTransformGroup = null;
/* 252 */             if (bg.numChildren() > 0) originalTransformGroup = (TransformGroup)bg.getChild(0);
/* 253 */             TransformGroup newTransformGroup = null;
/* 254 */             Object originalData = bg.getUserData();
/* 255 */             if (originalData == o)
/*     */             {
/* 257 */               newTransformGroup = p3d.getModel(o, originalTransformGroup);
/*     */             }
/*     */             else
/*     */             {
/* 261 */               Bag b = (Bag)models.get(o);
/* 262 */               if ((b != null) && (b.numObjs > 0))
/*     */               {
/* 265 */                 BranchGroup replacementBranchGroup = (BranchGroup)b.remove(0);
/* 266 */                 originalTransformGroup = (TransformGroup)replacementBranchGroup.getChild(0);
/* 267 */                 newTransformGroup = p3d.getModel(o, originalTransformGroup);
/* 268 */                 if (newTransformGroup == originalTransformGroup) {
/* 269 */                   global.setChild(replacementBranchGroup, count - 1);
/*     */                 }
/*     */               }
/*     */               else
/*     */               {
/* 274 */                 newTransformGroup = p3d.getModel(o, null);
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 279 */             if (newTransformGroup != originalTransformGroup)
/*     */             {
/* 282 */               newTransformGroup.setCapability(12);
/* 283 */               newTransformGroup.setCapability(18);
/* 284 */               newTransformGroup.setCapability(17);
/*     */ 
/* 286 */               newTransformGroup.setCapability(1);
/*     */ 
/* 288 */               BranchGroup bg2 = new BranchGroup();
/* 289 */               bg2.setCapability(12);
/* 290 */               bg2.setCapability(17);
/*     */ 
/* 292 */               bg2.setCapability(1);
/* 293 */               tmpLocalT.setTranslation(new Vector3d(x, y, z));
/* 294 */               newTransformGroup.setTransform(tmpLocalT);
/* 295 */               newTransformGroup.setUserData(new Int3D(x, y, z));
/* 296 */               bg2.addChild(newTransformGroup);
/* 297 */               bg2.setUserData(o);
/* 298 */               global.setChild(bg2, count - 1);
/*     */ 
/* 301 */               Bag b = (Bag)models.get(originalData);
/* 302 */               if (b == null) { b = new Bag(); models.put(originalData, b); }
/* 303 */               b.add(bg);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setField(Object field)
/*     */   {
/* 314 */     if (((field instanceof ObjectGrid3D)) || ((field instanceof ObjectGrid2D))) super.setField(field); else
/* 315 */       throw new RuntimeException("Invalid field for ObjectGridPortrayal3D: " + field);
/*     */   }
/*     */ 
/*     */   Int3D searchForObject(Object object, Int3D loc)
/*     */   {
/* 328 */     ObjectGrid3D field = (ObjectGrid3D)this.field;
/* 329 */     Object[][][] grid = field.field;
/* 330 */     if (grid[loc.x][loc.y][loc.z] == object) {
/* 331 */       return new Int3D(loc.x, loc.y, loc.z);
/*     */     }
/* 333 */     field.getMooreLocations(loc.x, loc.y, loc.z, 2, 2, true, this.xPos, this.yPos, this.yPos);
/* 334 */     for (int i = 0; i < this.xPos.numObjs; i++)
/* 335 */       if (grid[this.xPos.get(i)][this.yPos.get(i)][this.zPos.get(i)] == object) return new Int3D(this.xPos.get(i), this.yPos.get(i), this.zPos.get(i));
/* 336 */     return null;
/*     */   }
/*     */ 
/*     */   public LocationWrapper completedWrapper(LocationWrapper w, PickIntersection pi, PickResult pr)
/*     */   {
/* 344 */     SceneGraphPath path = pr.getSceneGraphPath();
/* 345 */     int len = path.nodeCount();
/* 346 */     boolean found = false;
/*     */ 
/* 348 */     int i = 0;
/* 349 */     for (i = 0; i < len; i++)
/*     */     {
/* 351 */       Node node = path.getNode(i);
/* 352 */       Object userdata = path.getNode(i).getUserData();
/* 353 */       if ((userdata == this) && ((node instanceof Group)))
/*     */       {
/* 355 */         found = true; break;
/*     */       }
/*     */     }
/* 358 */     if (!found) throw new RuntimeException("Internal error: ObjectGridPortrayal3D.completedWrapper() couldn't find the root transform group.  This shouldn't happen.");
/*     */ 
/* 361 */     Object location = path.getNode(i + 2).getUserData();
/*     */ 
/* 363 */     final ObjectGrid3D field = (ObjectGrid3D)this.field;
/* 364 */     return new LocationWrapper(w.getObject(), location, this)
/*     */     {
/*     */       public Object getLocation()
/*     */       {
/* 368 */         Int3D loc = (Int3D)super.getLocation();
/* 369 */         if (field.field[loc.x][loc.y][loc.z] == getObject())
/*     */         {
/* 371 */           return loc;
/*     */         }
/*     */ 
/* 375 */         Int3D result = ObjectGridPortrayal3D.this.searchForObject(this.object, loc);
/* 376 */         if (result != null)
/*     */         {
/* 378 */           this.location = result;
/* 379 */           return result;
/*     */         }
/*     */ 
/* 383 */         return ObjectGridPortrayal3D.this.unknown;
/*     */       }
/*     */ 
/*     */       public String getLocationName()
/*     */       {
/* 390 */         Object loc = getLocation();
/* 391 */         if ((loc instanceof Int3D))
/* 392 */           return ((Int3D)this.location).toCoordinates();
/* 393 */         return "Location Unknown";
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.grid.ObjectGridPortrayal3D
 * JD-Core Version:    0.6.2
 */