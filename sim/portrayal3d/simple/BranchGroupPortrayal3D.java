/*     */ package sim.portrayal3d.simple;
/*     */ 
/*     */ import com.sun.j3d.loaders.Scene;
/*     */ import com.sun.j3d.loaders.lw3d.Lw3dLoader;
/*     */ import com.sun.j3d.loaders.objectfile.ObjectFile;
/*     */ import com.sun.j3d.utils.picking.PickTool;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.net.URL;
/*     */ import javax.media.j3d.Appearance;
/*     */ import javax.media.j3d.BranchGroup;
/*     */ import javax.media.j3d.CompressedGeometry;
/*     */ import javax.media.j3d.Geometry;
/*     */ import javax.media.j3d.GeometryArray;
/*     */ import javax.media.j3d.Group;
/*     */ import javax.media.j3d.Node;
/*     */ import javax.media.j3d.Shape3D;
/*     */ import javax.media.j3d.Transform3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ 
/*     */ public class BranchGroupPortrayal3D extends PrimitivePortrayal3D
/*     */ {
/*     */   public static BranchGroup getBranchGroupForResource(Class c, String resourceName)
/*     */     throws IllegalArgumentException, FileNotFoundException
/*     */   {
/*  36 */     return getBranchGroupForURL(c.getResource(resourceName));
/*     */   }
/*     */ 
/*     */   public static BranchGroup getBranchGroupForURL(URL url) throws IllegalArgumentException, FileNotFoundException
/*     */   {
/*  41 */     String s = url.getPath().trim();
/*  42 */     s = s.substring(s.length() - 4);
/*  43 */     if (s.equalsIgnoreCase(".obj")) return new ObjectFile().load(url).getSceneGroup();
/*  44 */     if ((s.equalsIgnoreCase(".lws")) || (s.equalsIgnoreCase(".lwo")))
/*  45 */       return new Lw3dLoader().load(url).getSceneGroup();
/*  46 */     throw new IllegalArgumentException("Invalid extension to file name in url (" + url + "), MASON requires '.obj' or '.lws' at present time.");
/*     */   }
/*     */ 
/*     */   public static BranchGroup getBranchGroupForFile(String filename) throws IllegalArgumentException, FileNotFoundException
/*     */   {
/*  51 */     String s = filename.trim();
/*  52 */     s = s.substring(s.length() - 4);
/*  53 */     if (s.equalsIgnoreCase(".obj")) return new ObjectFile().load(filename).getSceneGroup();
/*  54 */     if (s.equalsIgnoreCase(".lws")) return new Lw3dLoader().load(filename).getSceneGroup();
/*  55 */     throw new IllegalArgumentException("Invalid extension to file name (" + filename + "), MASON requires '.obj' or '.lws' at present time.");
/*     */   }
/*     */ 
/*     */   public BranchGroupPortrayal3D(BranchGroup scene)
/*     */   {
/*  61 */     this(scene, 1.0D, null);
/*     */   }
/*     */ 
/*     */   public BranchGroupPortrayal3D(BranchGroup scene, double scale)
/*     */   {
/*  67 */     this(scene, scale, null);
/*     */   }
/*     */ 
/*     */   public BranchGroupPortrayal3D(BranchGroup scene, Transform3D transform)
/*     */   {
/*  73 */     this(scene, transform, null);
/*     */   }
/*     */ 
/*     */   public BranchGroupPortrayal3D(BranchGroup scene, Appearance a)
/*     */   {
/*  79 */     this(scene, 1.0D, a);
/*     */   }
/*     */ 
/*     */   public BranchGroupPortrayal3D(BranchGroup scene, double scale, Appearance a)
/*     */   {
/*  85 */     setScale(null, scale);
/*  86 */     traverseForAttributes(scene);
/*  87 */     this.group = scene;
/*  88 */     this.appearance = a;
/*     */   }
/*     */ 
/*     */   public BranchGroupPortrayal3D(BranchGroup scene, Transform3D transform, Appearance a)
/*     */   {
/*  94 */     setTransform(null, transform);
/*  95 */     traverseForAttributes(scene);
/*  96 */     this.group = scene;
/*  97 */     this.appearance = a;
/*     */   }
/*     */ 
/*     */   void traverseForAttributes(Node n)
/*     */   {
/* 105 */     if ((n instanceof Shape3D))
/*     */     {
/* 107 */       Shape3D s = (Shape3D)n;
/* 108 */       setShape3DFlags(s);
/* 109 */       setPickableFlags(s);
/* 110 */       PickTool.setCapabilities(s, 4100);
/* 111 */       Geometry g = s.getGeometry();
/* 112 */       if ((g instanceof CompressedGeometry)) {
/* 113 */         ((CompressedGeometry)g).setCapability(2);
/* 114 */       } else if ((g instanceof GeometryArray))
/*     */       {
/* 116 */         ((GeometryArray)g).setCapability(8);
/* 117 */         ((GeometryArray)g).setCapability(17);
/*     */       }
/*     */     }
/* 120 */     else if ((n instanceof Group))
/*     */     {
/* 122 */       Group g = (Group)n;
/* 123 */       for (int i = 0; i < g.numChildren(); i++)
/* 124 */         traverseForAttributes(g.getChild(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   void traverseForUserDataAndAppearance(Node n, LocationWrapper wrapper)
/*     */   {
/* 132 */     if ((n instanceof Shape3D))
/*     */     {
/* 134 */       Shape3D s = (Shape3D)n;
/* 135 */       s.setUserData(wrapper);
/* 136 */       if (this.appearance != null)
/* 137 */         s.setAppearance(this.appearance);
/*     */     }
/* 139 */     else if ((n instanceof Group))
/*     */     {
/* 141 */       Group g = (Group)n;
/* 142 */       for (int i = 0; i < g.numChildren(); i++)
/* 143 */         traverseForUserDataAndAppearance(g.getChild(i), wrapper);
/*     */     }
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object obj, TransformGroup j3dModel)
/*     */   {
/* 151 */     if (j3dModel == null)
/*     */     {
/* 153 */       j3dModel = new TransformGroup();
/* 154 */       j3dModel.setCapability(12);
/*     */ 
/* 157 */       LocationWrapper pickI = new LocationWrapper(obj, null, getCurrentFieldPortrayal());
/*     */ 
/* 159 */       Node g = this.group.cloneTree(true);
/*     */ 
/* 161 */       if (this.transform != null)
/*     */       {
/* 163 */         TransformGroup tg = new TransformGroup();
/* 164 */         tg.setTransform(this.transform);
/* 165 */         tg.setCapability(17);
/* 166 */         tg.setCapability(18);
/* 167 */         tg.setCapability(12);
/* 168 */         tg.addChild(g);
/* 169 */         g = tg;
/*     */       }
/* 171 */       j3dModel.addChild(g);
/*     */ 
/* 173 */       traverseForUserDataAndAppearance(j3dModel, pickI);
/*     */     }
/* 175 */     return j3dModel;
/*     */   }
/*     */ 
/*     */   protected int numShapes() {
/* 179 */     return 0;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.BranchGroupPortrayal3D
 * JD-Core Version:    0.6.2
 */