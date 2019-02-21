/*     */ package sim.portrayal3d.simple;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import javax.media.j3d.AmbientLight;
/*     */ import javax.media.j3d.BoundingSphere;
/*     */ import javax.media.j3d.DirectionalLight;
/*     */ import javax.media.j3d.Light;
/*     */ import javax.media.j3d.PointLight;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.vecmath.Color3f;
/*     */ import javax.vecmath.Point3d;
/*     */ import javax.vecmath.Vector3f;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ import sim.util.Double3D;
/*     */ 
/*     */ public class LightPortrayal3D extends SimplePortrayal3D
/*     */ {
/*     */   Light light;
/*     */ 
/*     */   Vector3f double3DToVector3f(Double3D d)
/*     */   {
/*  56 */     Vector3f v = new Vector3f();
/*  57 */     v.x = ((float)d.x); v.y = ((float)d.y); v.z = ((float)d.z);
/*  58 */     return v;
/*     */   }
/*     */ 
/*     */   public LightPortrayal3D(Color color, Double3D direction)
/*     */   {
/*  64 */     this.light = new DirectionalLight(new Color3f(color), double3DToVector3f(direction));
/*  65 */     this.light.setInfluencingBounds(new BoundingSphere(new Point3d(0.0D, 0.0D, 0.0D), (1.0D / 0.0D)));
/*     */   }
/*     */ 
/*     */   public LightPortrayal3D(Color color)
/*     */   {
/*  71 */     this.light = new AmbientLight(new Color3f(color));
/*  72 */     this.light.setInfluencingBounds(new BoundingSphere(new Point3d(0.0D, 0.0D, 0.0D), (1.0D / 0.0D)));
/*     */   }
/*     */ 
/*     */   public LightPortrayal3D(Color color, Double3D position, float constantAttenuation, float linearAttenuation, float quadraticAttenuation)
/*     */   {
/*  79 */     PointLight p = new PointLight();
/*  80 */     p.setAttenuation(constantAttenuation, linearAttenuation, quadraticAttenuation);
/*  81 */     p.setPosition((float)position.x, (float)position.y, (float)position.z);
/*  82 */     this.light = p;
/*  83 */     this.light.setColor(new Color3f(color));
/*  84 */     this.light.setInfluencingBounds(new BoundingSphere(new Point3d(0.0D, 0.0D, 0.0D), (1.0D / 0.0D)));
/*     */   }
/*     */ 
/*     */   public LightPortrayal3D(Light light)
/*     */   {
/*  90 */     this.light = light;
/*     */   }
/*     */ 
/*     */   public TransformGroup getModel(Object obj, TransformGroup j3dModel)
/*     */   {
/*  95 */     if (j3dModel == null)
/*     */     {
/*  97 */       j3dModel = new TransformGroup();
/*  98 */       j3dModel.setCapability(12);
/*  99 */       Light l = (Light)this.light.cloneTree(false);
/* 100 */       clearPickableFlags(l);
/* 101 */       j3dModel.addChild(l);
/*     */     }
/* 103 */     return j3dModel;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.LightPortrayal3D
 * JD-Core Version:    0.6.2
 */