/*     */ package sim.app.lightcycles;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import sim.display.Console;
/*     */ import sim.display.Controller;
/*     */ import sim.display.Display2D;
/*     */ import sim.display.Display2D.InnerDisplay2D;
/*     */ import sim.field.grid.SparseGrid2D;
/*     */ import sim.portrayal.grid.SparseGridPortrayal2D;
/*     */ import sim.portrayal.simple.OvalPortrayal2D;
/*     */ import sim.portrayal.simple.RectanglePortrayal2D;
/*     */ import sim.util.Bag;
/*     */ 
/*     */ public class ControlUI
/*     */ {
/*     */   LightCyclesWithUI lcui;
/*     */   LightCycles lc;
/*     */   Cycle c;
/*     */   SparseGridPortrayal2D cyclePortrayal;
/*     */ 
/*     */   public ControlUI(LightCyclesWithUI nlc, SparseGridPortrayal2D ncp)
/*     */   {
/*  29 */     this.lcui = nlc;
/*  30 */     this.lc = ((LightCycles)this.lcui.state);
/*  31 */     this.cyclePortrayal = ncp;
/*     */ 
/*  34 */     this.lcui.display.insideDisplay.setRequestFocusEnabled(true);
/*     */     try
/*     */     {
/*  38 */       initListeners();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  42 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void initListeners()
/*     */   {
/*  48 */     this.lcui.display.insideDisplay.addKeyListener(new KeyAdapter()
/*     */     {
/*     */       public void keyPressed(KeyEvent e)
/*     */       {
/*  54 */         if (e.getKeyCode() == 32)
/*     */         {
/*  56 */           for (int x = 0; x < ControlUI.this.lc.cycleGrid.allObjects.numObjs; x++)
/*     */           {
/*  58 */             if (ControlUI.this.c != null)
/*     */             {
/*  61 */               if ((ControlUI.this.c == (Cycle)ControlUI.this.lc.cycleGrid.allObjects.objs[x]) && (x != ControlUI.this.lc.cycleGrid.allObjects.numObjs - 1))
/*     */               {
/*  63 */                 ControlUI.this.cyclePortrayal.setPortrayalForObject(ControlUI.this.c, new OvalPortrayal2D(Color.white));
/*  64 */                 ControlUI.this.c.cpu = true;
/*  65 */                 ControlUI.this.c = ((Cycle)ControlUI.this.lc.cycleGrid.allObjects.objs[(x + 1)]);
/*     */ 
/*  67 */                 break;
/*     */               }
/*     */ 
/*  70 */               if ((ControlUI.this.c == (Cycle)ControlUI.this.lc.cycleGrid.allObjects.objs[x]) && (x == ControlUI.this.lc.cycleGrid.allObjects.numObjs - 1))
/*     */               {
/*  72 */                 ControlUI.this.cyclePortrayal.setPortrayalForObject(ControlUI.this.c, new OvalPortrayal2D(Color.white));
/*  73 */                 ControlUI.this.c.cpu = true;
/*  74 */                 ControlUI.this.c = ((Cycle)ControlUI.this.lc.cycleGrid.allObjects.objs[0]);
/*     */ 
/*  76 */                 break;
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/*  81 */               ControlUI.this.c = ((Cycle)ControlUI.this.lc.cycleGrid.allObjects.objs[x]);
/*  82 */               break;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*  87 */           ControlUI.this.c.cpu = false;
/*  88 */           ControlUI.this.cyclePortrayal.setPortrayalForObject(ControlUI.this.c, new RectanglePortrayal2D(Color.green, 1.5D));
/*     */ 
/*  91 */           ControlUI.this.lcui.controller.refresh();
/*     */         }
/*  95 */         else if ((ControlUI.this.c != null) && (e.getKeyCode() == 37))
/*     */         {
/*  97 */           if (ControlUI.this.c.dir == 1)
/*  98 */             ControlUI.this.c.dir = 3;
/*  99 */           else if (ControlUI.this.c.dir == 2)
/* 100 */             ControlUI.this.c.dir = 4;
/* 101 */           else if (ControlUI.this.c.dir == 3)
/* 102 */             ControlUI.this.c.dir = 2;
/*     */           else {
/* 104 */             ControlUI.this.c.dir = 1;
/*     */           }
/*     */ 
/*     */         }
/* 108 */         else if ((ControlUI.this.c != null) && (e.getKeyCode() == 39))
/*     */         {
/* 110 */           if (ControlUI.this.c.dir == 1)
/* 111 */             ControlUI.this.c.dir = 4;
/* 112 */           else if (ControlUI.this.c.dir == 2)
/* 113 */             ControlUI.this.c.dir = 3;
/* 114 */           else if (ControlUI.this.c.dir == 3)
/* 115 */             ControlUI.this.c.dir = 1;
/*     */           else {
/* 117 */             ControlUI.this.c.dir = 2;
/*     */           }
/*     */ 
/*     */         }
/* 122 */         else if (e.getKeyCode() == 65)
/*     */         {
/* 124 */           ((Console)ControlUI.this.lcui.controller).pressPlay();
/*     */         }
/* 127 */         else if (e.getKeyCode() == 83)
/*     */         {
/* 129 */           ((Console)ControlUI.this.lcui.controller).pressPause();
/*     */         }
/* 132 */         else if (e.getKeyCode() == 68)
/*     */         {
/* 134 */           ((Console)ControlUI.this.lcui.controller).pressStop();
/*     */         }
/*     */       }
/*     */     });
/* 142 */     this.lcui.display.insideDisplay.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void getFocus()
/*     */       {
/* 146 */         ControlUI.this.lcui.display.insideDisplay.requestFocus();
/*     */       }
/*     */ 
/*     */       public void mouseClicked(MouseEvent e)
/*     */       {
/* 151 */         getFocus();
/*     */       }
/*     */ 
/*     */       public void mouseEntered(MouseEvent e)
/*     */       {
/* 157 */         getFocus();
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.lightcycles.ControlUI
 * JD-Core Version:    0.6.2
 */