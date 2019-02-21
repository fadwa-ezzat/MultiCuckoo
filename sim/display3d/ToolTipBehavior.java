/*     */ package sim.display3d;
/*     */ 
/*     */ import com.sun.j3d.utils.picking.PickCanvas;
/*     */ import com.sun.j3d.utils.picking.PickIntersection;
/*     */ import com.sun.j3d.utils.picking.PickResult;
/*     */ import com.sun.j3d.utils.picking.behaviors.PickMouseBehavior;
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.Enumeration;
/*     */ import javax.media.j3d.Bounds;
/*     */ import javax.media.j3d.BranchGroup;
/*     */ import javax.media.j3d.Canvas3D;
/*     */ import javax.media.j3d.Shape3D;
/*     */ import javax.media.j3d.WakeupCondition;
/*     */ import javax.media.j3d.WakeupCriterion;
/*     */ import javax.media.j3d.WakeupOnAWTEvent;
/*     */ import javax.media.j3d.WakeupOr;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.Timer;
/*     */ import javax.swing.ToolTipManager;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.vecmath.Point3d;
/*     */ import sim.portrayal.LocationWrapper;
/*     */ import sim.portrayal3d.FieldPortrayal3D;
/*     */ import sim.util.gui.WordWrap;
/*     */ 
/*     */ public class ToolTipBehavior extends PickMouseBehavior
/*     */ {
/*     */   Canvas3D canvas;
/*  57 */   ActionListener timeUpNoMovement = new ActionListener()
/*     */   {
/*     */     public void actionPerformed(ActionEvent value0)
/*     */     {
/*  62 */       ToolTipBehavior.this.showing = true;
/*  63 */       ToolTipBehavior.this.updateScene(ToolTipBehavior.this.lastPoint.x, ToolTipBehavior.this.lastPoint.y);
/*     */     }
/*  57 */   };
/*     */   WakeupCondition myWakeupCondition;
/*     */   Timer timer;
/*  79 */   Point lastPoint = null;
/*     */ 
/* 100 */   boolean showing = false;
/* 101 */   boolean canShow = false;
/*     */   static final int CURSOR_SKIP = 20;
/*     */ 
/*     */   public ToolTipBehavior(Canvas3D canvas, BranchGroup root, Bounds bounds)
/*     */   {
/*  47 */     super(canvas, root, bounds);
/*  48 */     this.canvas = canvas;
/*  49 */     setSchedulingBounds(bounds);
/*  50 */     root.addChild(this);
/*  51 */     this.pickCanvas.setMode(1024);
/*     */ 
/*  53 */     this.timer = new Timer(ToolTipManager.sharedInstance().getInitialDelay(), this.timeUpNoMovement);
/*  54 */     this.timer.setRepeats(false);
/*     */   }
/*     */ 
/*     */   public void initialize()
/*     */   {
/*  88 */     this.myWakeupCondition = new WakeupOr(new WakeupCriterion[] { new WakeupOnAWTEvent(503), new WakeupOnAWTEvent(501), new WakeupOnAWTEvent(504), new WakeupOnAWTEvent(505) });
/*     */ 
/*  97 */     wakeupOn(this.myWakeupCondition);
/*     */   }
/*     */ 
/*     */   public void setCanShowToolTips(boolean val)
/*     */   {
/* 106 */     this.canShow = val;
/* 107 */     if (!this.canShow)
/*     */     {
/* 109 */       DialogToolTip.hideToolTip();
/* 110 */       this.timer.stop();
/* 111 */       this.showing = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean getCanShowToolTips()
/*     */   {
/* 118 */     return this.canShow;
/*     */   }
/*     */ 
/*     */   public void processStimulus(Enumeration criteria)
/*     */   {
/* 127 */     AWTEvent[] evt = null;
/*     */ 
/* 129 */     while (criteria.hasMoreElements())
/*     */     {
/* 131 */       WakeupCriterion wakeup = (WakeupCriterion)criteria.nextElement();
/* 132 */       if ((wakeup instanceof WakeupOnAWTEvent)) {
/* 133 */         evt = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
/*     */       }
/*     */ 
/* 138 */       AWTEvent levt = evt[(evt.length - 1)];
/*     */ 
/* 140 */       if ((levt instanceof MouseEvent))
/*     */       {
/* 142 */         MouseEvent mlevt = (MouseEvent)levt;
/* 143 */         int mlevtId = mlevt.getID();
/* 144 */         if (mlevtId == 505)
/*     */         {
/* 146 */           DialogToolTip.hideToolTip();
/* 147 */           this.timer.stop();
/* 148 */           this.showing = false;
/*     */         }
/* 150 */         else if (this.canShow)
/*     */         {
/* 152 */           this.lastPoint = mlevt.getPoint();
/* 153 */           if (this.showing) {
/* 154 */             updateScene(this.lastPoint.x, this.lastPoint.y);
/*     */           }
/*     */           else
/*     */           {
/* 158 */             this.timer.restart();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 162 */     wakeupOn(this.myWakeupCondition);
/*     */   }
/*     */ 
/*     */   public void updateScene(int xpos, int ypos)
/*     */   {
/* 172 */     PickResult pickResult = null;
/* 173 */     PickResult[] pickResults = null;
/* 174 */     Shape3D shape = null;
/* 175 */     this.pickCanvas.setShapeLocation(xpos, ypos);
/*     */ 
/* 177 */     Point3d eyePos = this.pickCanvas.getStartPosition();
/*     */ 
/* 179 */     if (!this.pickCanvas.getBranchGroup().isLive()) {
/* 180 */       return;
/*     */     }
/*     */ 
/* 183 */     pickResults = this.pickCanvas.pickAllSorted();
/* 184 */     if (pickResults == null) {
/* 185 */       return;
/*     */     }
/*     */ 
/* 188 */     LocationWrapper[] picks = new LocationWrapper[pickResults.length];
/*     */ 
/* 190 */     int distinctObjectCount = 0;
/*     */ 
/* 192 */     String htmlText = null;
/*     */ 
/* 194 */     for (int i = 0; i < pickResults.length; i++)
/*     */     {
/* 196 */       pickResult = pickResults[i];
/* 197 */       shape = (Shape3D)pickResult.getNode(1);
/*     */ 
/* 201 */       LocationWrapper w = (LocationWrapper)shape.getUserData();
/* 202 */       if (w != null) {
/* 203 */         boolean duplicate = false;
/*     */ 
/* 205 */         for (int j = 0; j < distinctObjectCount; j++)
/* 206 */           if (w == picks[j])
/*     */           {
/* 208 */             duplicate = true;
/* 209 */             break;
/*     */           }
/* 211 */         if (!duplicate) {
/* 212 */           picks[(distinctObjectCount++)] = w;
/* 213 */           if (pickResult.numGeometryArrays() > 0)
/*     */           {
/* 215 */             PickIntersection pi = pickResult.getClosestIntersection(eyePos);
/*     */ 
/* 221 */             if (pi != null)
/*     */             {
/* 233 */               FieldPortrayal3D fPortrayal = (FieldPortrayal3D)w.getFieldPortrayal();
/* 234 */               if (fPortrayal != null)
/*     */               {
/* 236 */                 LocationWrapper filledLW = fPortrayal.completedWrapper(w, pi, pickResult);
/* 237 */                 if (htmlText == null) htmlText = fPortrayal.getName(filledLW); else
/* 238 */                   htmlText = htmlText + "<br>" + fPortrayal.getName(filledLW); 
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 244 */     Point s = this.canvas.getLocationOnScreen();
/* 245 */     s.x += this.lastPoint.x;
/* 246 */     s.y += this.lastPoint.y + 20;
/*     */ 
/* 248 */     if (htmlText != null) {
/* 249 */       htmlText = "<html><font size=\"-1\" face=\"" + WordWrap.toHTML(this.canvas.getFont().getFamily()) + "\">" + htmlText + "</font></html>";
/*     */     }
/*     */ 
/* 252 */     DialogToolTip.showToolTip(s, htmlText);
/*     */   }
/*     */ 
/*     */   static class DialogToolTip extends JDialog
/*     */   {
/* 257 */     static DialogToolTip tip = new DialogToolTip();
/* 258 */     static JLabel label = new JLabel("", 0);
/*     */ 
/*     */     static void showToolTip(Point locationOnScreen, String htmlText)
/*     */     {
/* 277 */       if (htmlText == null) {
/* 278 */         tip.setVisible(false);
/*     */       }
/*     */       else {
/* 281 */         label.setText(htmlText);
/* 282 */         tip.pack();
/*     */ 
/* 285 */         tip.pack();
/* 286 */         tip.setLocation(locationOnScreen);
/* 287 */         tip.setVisible(true);
/*     */       }
/*     */     }
/*     */ 
/*     */     static void hideToolTip()
/*     */     {
/* 293 */       tip.setVisible(false);
/*     */     }
/*     */ 
/*     */     static
/*     */     {
/* 261 */       tip.setUndecorated(true);
/* 262 */       tip.getContentPane().setBackground(Color.yellow);
/* 263 */       tip.getContentPane().setLayout(new BorderLayout());
/* 264 */       tip.getContentPane().add(label);
/*     */ 
/* 266 */       tip.setModal(false);
/*     */ 
/* 268 */       label.setBorder(new EmptyBorder(2, 2, 2, 2));
/* 269 */       label.setBackground(Color.yellow);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.display3d.ToolTipBehavior
 * JD-Core Version:    0.6.2
 */