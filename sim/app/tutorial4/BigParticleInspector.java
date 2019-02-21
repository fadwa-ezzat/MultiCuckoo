/*    */ package sim.app.tutorial4;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.Box;
/*    */ import javax.swing.JButton;
/*    */ import sim.display.Controller;
/*    */ import sim.display.GUIState;
/*    */ import sim.engine.SimState;
/*    */ import sim.field.grid.SparseGrid2D;
/*    */ import sim.portrayal.Inspector;
/*    */ import sim.portrayal.LocationWrapper;
/*    */ import sim.portrayal.grid.SparseGridPortrayal2D;
/*    */ import sim.util.Int2D;
/*    */ 
/*    */ public class BigParticleInspector extends Inspector
/*    */ {
/*    */   public Inspector originalInspector;
/*    */ 
/*    */   public BigParticleInspector(Inspector originalInspector, LocationWrapper wrapper, GUIState guiState)
/*    */   {
/* 27 */     this.originalInspector = originalInspector;
/*    */ 
/* 30 */     SparseGridPortrayal2D gridportrayal = (SparseGridPortrayal2D)wrapper.getFieldPortrayal();
/*    */ 
/* 32 */     final SparseGrid2D grid = (SparseGrid2D)gridportrayal.getField();
/* 33 */     final BigParticle particle = (BigParticle)wrapper.getObject();
/* 34 */     final SimState state = guiState.state;
/* 35 */     final Controller console = guiState.controller;
/*    */ 
/* 38 */     Box box = new Box(0);
/* 39 */     JButton button = new JButton("Roll the Dice");
/* 40 */     box.add(button);
/* 41 */     box.add(Box.createGlue());
/*    */ 
/* 44 */     setLayout(new BorderLayout());
/* 45 */     add(originalInspector, "Center");
/* 46 */     add(box, "North");
/*    */ 
/* 49 */     button.addActionListener(new ActionListener()
/*    */     {
/*    */       public void actionPerformed(ActionEvent e)
/*    */       {
/* 53 */         synchronized (state.schedule)
/*    */         {
/* 56 */           particle.xdir = (state.random.nextInt(3) - 1);
/* 57 */           particle.ydir = (state.random.nextInt(3) - 1);
/*    */ 
/* 60 */           grid.setObjectLocation(particle, new Int2D(state.random.nextInt(grid.getWidth()), state.random.nextInt(grid.getHeight())));
/*    */ 
/* 66 */           console.refresh();
/*    */         }
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public void updateInspector()
/*    */   {
/* 74 */     this.originalInspector.updateInspector();
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.tutorial4.BigParticleInspector
 * JD-Core Version:    0.6.2
 */