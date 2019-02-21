/*     */ package sim.display3d;
/*     */ 
/*     */ import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
/*     */ import com.sun.j3d.utils.picking.PickCanvas;
/*     */ import com.sun.j3d.utils.picking.PickIntersection;
/*     */ import com.sun.j3d.utils.picking.PickResult;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Enumeration;
/*     */ import javax.media.j3d.Bounds;
/*     */ import javax.media.j3d.BranchGroup;
/*     */ import javax.media.j3d.Canvas3D;
/*     */ import javax.media.j3d.CapabilityNotSetException;
/*     */ import javax.media.j3d.Shape3D;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.vecmath.Point3d;
/*     */ import sim.display.Controller;
/*     */ import sim.display.GUIState;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal3d.FieldPortrayal3D;
/*     */ import sim.util.Bag;
/*     */ 
/*     */ public class SelectionBehavior extends MouseBehavior
/*     */ {
/*  42 */   GUIState guiState = null;
/*     */   PickCanvas pickCanvas;
/*     */   BranchGroup r;
/*  45 */   boolean oneClick = true;
/*  46 */   boolean twoClicks = true;
/*     */ 
/*  48 */   public void setTolerance(double tolerance) { this.pickCanvas.setTolerance((float)tolerance); }
/*     */ 
/*     */   public void detach()
/*     */   {
/*     */     try
/*     */     {
/*  54 */       this.pickCanvas.getCanvas().removeMouseMotionListener(this); } catch (Exception e) {
/*     */     }try { this.pickCanvas.getCanvas().removeMouseListener(this);
/*     */     } catch (Exception e) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public SelectionBehavior(Canvas3D canvas, BranchGroup root, Bounds bounds, GUIState guiState) {
/*  61 */     super(canvas, 0);
/*  62 */     this.pickCanvas = new PickCanvas(canvas, root);
/*  63 */     setTolerance(2.0D);
/*  64 */     this.r = root;
/*  65 */     root.addChild(this);
/*  66 */     this.pickCanvas.setMode(1024);
/*  67 */     this.guiState = guiState;
/*     */   }
/*     */ 
/*     */   public void setSelectsAll(boolean selection, boolean inspection)
/*     */   {
/*  74 */     this.oneClick = selection; this.twoClicks = inspection;
/*     */   }
/*     */ 
/*     */   public void processStimulus(Enumeration criteria)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void mouseClicked(MouseEvent evt)
/*     */   {
/*  84 */     if (!this.r.isLive()) return;
/*     */ 
/*  86 */     int numClicks = evt.getClickCount();
/*  87 */     int xpos = evt.getPoint().x;
/*  88 */     int ypos = evt.getPoint().y;
/*     */ 
/*  90 */     PickResult pickResult = null;
/*  91 */     PickResult[] pickResults = null;
/*  92 */     Shape3D shape = null;
/*  93 */     this.pickCanvas.setShapeLocation(xpos, ypos);
/*     */ 
/*  95 */     Point3d eyePos = this.pickCanvas.getStartPosition();
/*     */     try
/*     */     {
/*  99 */       if ((numClicks == 1) && (!this.oneClick))
/*     */       {
/* 101 */         PickResult p = this.pickCanvas.pickClosest();
/* 102 */         if (p != null) pickResults = new PickResult[] { p }; else
/* 103 */           pickResults = new PickResult[0];
/*     */       }
/*     */       else {
/* 106 */         pickResults = this.pickCanvas.pickAllSorted();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (CapabilityNotSetException e)
/*     */     {
/* 113 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 116 */     if (pickResults == null) pickResults = new PickResult[0];
/*     */ 
/* 119 */     LocationWrapper[] picks = new LocationWrapper[pickResults.length];
/*     */ 
/* 121 */     final Bag inspectors = new Bag();
/* 122 */     Bag inspectorPortrayals = new Bag();
/* 123 */     Bag uniqueWrappers = new Bag();
/* 124 */     final Bag names = new Bag();
/*     */ 
/* 126 */     int distinctObjectCount = 0;
/*     */ 
/* 128 */     for (int i = 0; i < pickResults.length; i++)
/*     */     {
/* 130 */       pickResult = pickResults[i];
/* 131 */       shape = (Shape3D)pickResult.getNode(1);
/*     */ 
/* 134 */       LocationWrapper w = (LocationWrapper)shape.getUserData();
/* 135 */       if (w != null)
/*     */       {
/* 137 */         boolean duplicate = false;
/*     */ 
/* 139 */         for (int j = 0; j < distinctObjectCount; j++)
/* 140 */           if (w == picks[j])
/*     */           {
/* 142 */             duplicate = true;
/* 143 */             break;
/*     */           }
/* 145 */         if (!duplicate)
/*     */         {
/* 147 */           picks[(distinctObjectCount++)] = w;
/* 148 */           if (pickResult.numGeometryArrays() > 0)
/*     */           {
/* 150 */             PickIntersection pi = pickResult.getClosestIntersection(eyePos);
/*     */ 
/* 155 */             if (pi != null)
/*     */             {
/* 163 */               FieldPortrayal3D fPortrayal = (FieldPortrayal3D)w.getFieldPortrayal();
/*     */ 
/* 166 */               if (fPortrayal == null)
/*     */               {
/* 168 */                 System.err.println("WARNING: The value of a LocationWrapper.getFieldPortrayal() is null.\nLikely the wrapper was created from a SimplePortrayal3D whose field portrayal was not set before getModel(...) was called.");
/*     */               }
/*     */               else
/*     */               {
/* 172 */                 LocationWrapper filledLW = fPortrayal.completedWrapper(w, pi, pickResult);
/*     */ 
/* 174 */                 if (numClicks >= 1)
/*     */                 {
/* 176 */                   if ((this.oneClick) || (uniqueWrappers.size() == 0)) uniqueWrappers.add(filledLW);
/*     */                 }
/*     */ 
/* 179 */                 if (numClicks >= 2)
/*     */                 {
/* 181 */                   if ((this.twoClicks) || (inspectors.size() == 0))
/*     */                   {
/* 183 */                     inspectors.add(fPortrayal.getInspector(filledLW, this.guiState));
/*     */ 
/* 188 */                     inspectorPortrayals.add(fPortrayal);
/* 189 */                     names.add(fPortrayal.getName(filledLW));
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 197 */     if (numClicks >= 1) ((Display3D)this.pickCanvas.getCanvas().getParent()).performSelection(uniqueWrappers);
/*     */ 
/* 199 */     final GUIState g = this.guiState;
/* 200 */     if ((distinctObjectCount != 0) && (numClicks >= 2))
/* 201 */       SwingUtilities.invokeLater(new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 205 */           g.controller.setInspectors(inspectors, names);
/*     */         }
/*     */       });
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.display3d.SelectionBehavior
 * JD-Core Version:    0.6.2
 */