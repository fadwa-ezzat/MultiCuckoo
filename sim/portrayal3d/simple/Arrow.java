/*     */ package sim.portrayal3d.simple;
/*     */ 
/*     */ import com.sun.j3d.utils.geometry.Cone;
/*     */ import com.sun.j3d.utils.geometry.Cylinder;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import javax.media.j3d.Appearance;
/*     */ import javax.media.j3d.Font3D;
/*     */ import javax.media.j3d.OrientedShape3D;
/*     */ import javax.media.j3d.Text3D;
/*     */ import javax.media.j3d.Transform3D;
/*     */ import javax.media.j3d.TransformGroup;
/*     */ import javax.vecmath.AxisAngle4d;
/*     */ import javax.vecmath.Point3f;
/*     */ import javax.vecmath.Vector3d;
/*     */ import javax.vecmath.Vector3f;
/*     */ import sim.portrayal3d.SimplePortrayal3D;
/*     */ import sim.util.Double3D;
/*     */ 
/*     */ public class Arrow extends TransformGroup
/*     */ {
/*  28 */   static final Color DEFAULT_ARROW_COLOR = Color.gray;
/*  29 */   static final Font3D DEFAULT_FONT3D = new Font3D(new Font(null, 0, 1), null);
/*     */   Cone arrowHead;
/*     */   Cylinder arrowTail;
/*     */ 
/*     */   public Cylinder getArrowTail()
/*     */   {
/*  33 */     return this.arrowTail; } 
/*  34 */   public Cone getArrowHead() { return this.arrowHead; }
/*     */ 
/*     */ 
/*     */   public Arrow(double arrowTailRadius, Double3D startPoint, Double3D endPoint, String stLabel, String endLabel, Appearance appearance)
/*     */   {
/*  44 */     Vector3d stPt = new Vector3d(startPoint.x, startPoint.y, startPoint.z);
/*  45 */     Vector3d endPt = new Vector3d(endPoint.x, endPoint.y, endPoint.z);
/*  46 */     Vector3d v = new Vector3d(stPt);
/*  47 */     v.negate();
/*  48 */     v.add(new Vector3d(endPt));
/*     */ 
/*  51 */     float arrowLen = (float)v.length();
/*  52 */     float arrowHeadLen = 5.0F * (float)arrowTailRadius;
/*  53 */     float arrowHeadMaxRadius = 3.0F * (float)arrowTailRadius;
/*  54 */     float cylinderLen = arrowLen - arrowHeadLen;
/*     */ 
/*  56 */     if (cylinderLen < 0.0F)
/*     */     {
/*  60 */       arrowHeadLen = arrowLen / 16.0F;
/*  61 */       cylinderLen = arrowLen - arrowHeadLen;
/*     */     }
/*     */ 
/*  65 */     Appearance caAppearance = appearance;
/*  66 */     if (caAppearance == null)
/*     */     {
/*  68 */       caAppearance = SimplePortrayal3D.appearanceForColors(DEFAULT_ARROW_COLOR, null, DEFAULT_ARROW_COLOR, DEFAULT_ARROW_COLOR, 1.0D, 1.0D);
/*     */     }
/*     */ 
/*  72 */     Transform3D caTransform = new Transform3D();
/*  73 */     caTransform.setTranslation(stPt);
/*     */ 
/*  75 */     Vector3d oy = new Vector3d(0.0D, 1.0D, 0.0D);
/*     */ 
/*  77 */     Vector3d axis = new Vector3d();
/*  78 */     axis.cross(oy, v);
/*     */ 
/*  80 */     if (axis.length() != 0.0D)
/*     */     {
/*  83 */       caTransform.setRotation(new AxisAngle4d(axis, Math.asin(axis.length() / v.length())));
/*     */     }
/*     */ 
/*  88 */     caTransform.setTranslation(stPt);
/*     */ 
/*  90 */     setTransform(caTransform);
/*     */ 
/*  92 */     this.arrowTail = new Cylinder((float)arrowTailRadius, cylinderLen, caAppearance);
/*  93 */     Transform3D arrowCylinderTransform = new Transform3D();
/*  94 */     arrowCylinderTransform.set(new Vector3f(0.0F, cylinderLen / 2.0F, 0.0F));
/*  95 */     TransformGroup arrowCylinderTransformGroup = new TransformGroup(arrowCylinderTransform);
/*     */ 
/*  97 */     arrowCylinderTransformGroup.addChild(this.arrowTail);
/*  98 */     addChild(arrowCylinderTransformGroup);
/*     */ 
/* 100 */     Transform3D arrowHeadTransform = new Transform3D();
/* 101 */     arrowHeadTransform.set(new Vector3f(0.0F, cylinderLen, 0.0F));
/* 102 */     TransformGroup arrowHeadTransformGroup = new TransformGroup(arrowHeadTransform);
/*     */ 
/* 104 */     this.arrowHead = new Cone(arrowHeadMaxRadius, arrowHeadLen, 1, caAppearance);
/* 105 */     arrowHeadTransformGroup.addChild(this.arrowHead);
/* 106 */     addChild(arrowHeadTransformGroup);
/*     */ 
/* 108 */     if (stLabel != null)
/*     */     {
/* 110 */       Text3D txt = new Text3D(DEFAULT_FONT3D, stLabel);
/* 111 */       OrientedShape3D os3d = new OrientedShape3D(txt, caAppearance, 1, new Point3f(0.0F, 0.0F, 0.0F));
/*     */ 
/* 114 */       Transform3D t = new Transform3D();
/* 115 */       t.setScale(5.0F * (float)arrowTailRadius);
/* 116 */       t.setTranslation(new Vector3f(0.0F, -0.1F, 0.0F));
/* 117 */       TransformGroup stLabelTG = new TransformGroup(t);
/*     */ 
/* 119 */       stLabelTG.addChild(os3d);
/* 120 */       addChild(stLabelTG);
/*     */     }
/*     */ 
/* 123 */     if (endLabel != null)
/*     */     {
/* 125 */       Text3D txt = new Text3D(DEFAULT_FONT3D, endLabel);
/* 126 */       OrientedShape3D os3d = new OrientedShape3D(txt, caAppearance, 1, new Point3f(0.0F, arrowLen, 0.0F));
/*     */ 
/* 130 */       Transform3D t = new Transform3D();
/* 131 */       t.setScale(5.0F * (float)arrowTailRadius);
/* 132 */       t.setTranslation(new Vector3f(0.0F, arrowLen + 0.1F, 0.0F));
/* 133 */       TransformGroup endLabelTG = new TransformGroup(t);
/*     */ 
/* 135 */       endLabelTG.addChild(os3d);
/* 136 */       addChild(endLabelTG);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal3d.simple.Arrow
 * JD-Core Version:    0.6.2
 */