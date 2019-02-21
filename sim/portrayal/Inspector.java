/*     */ package sim.portrayal;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.SwingUtilities;
/*     */ import sim.display.Controller;
/*     */ import sim.display.Display2D;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Steppable;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.portrayal.inspector.ProvidesInspector;
/*     */ import sim.portrayal.inspector.Tabbable;
/*     */ import sim.portrayal.inspector.TabbedInspector;
/*     */ 
/*     */ public abstract class Inspector extends JPanel
/*     */ {
/*  82 */   boolean _volatile = true;
/*     */ 
/* 112 */   String title = "";
/*     */ 
/* 175 */   public static final ImageIcon INSPECT_ICON = iconFor("Inspect.png");
/* 176 */   public static final ImageIcon INSPECT_ICON_P = iconFor("InspectPressed.png");
/* 177 */   public static final ImageIcon UPDATE_ICON = iconFor("Update.png");
/* 178 */   public static final ImageIcon UPDATE_ICON_P = iconFor("UpdatePressed.png");
/*     */   boolean stopped;
/*     */ 
/*     */   public static Inspector getInspector(Object obj, GUIState state, String name)
/*     */   {
/*  89 */     if (obj == null)
/*  90 */       return new SimpleInspector(obj, state, name);
/*  91 */     if ((obj instanceof ProvidesInspector))
/*  92 */       return ((ProvidesInspector)obj).provideInspector(state, name);
/*  93 */     if ((obj instanceof Tabbable)) {
/*  94 */       return new TabbedInspector((Tabbable)obj, state, name);
/*     */     }
/*  96 */     return new SimpleInspector(obj, state, name);
/*     */   }
/*     */ 
/*     */   public void setVolatile(boolean val) {
/* 100 */     this._volatile = val;
/*     */   }
/*     */   public boolean isVolatile() {
/* 103 */     return this._volatile;
/*     */   }
/*     */ 
/*     */   public abstract void updateInspector();
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 113 */     return this.title; } 
/* 114 */   public void setTitle(String title) { this.title = title; }
/*     */ 
/*     */ 
/*     */   public Steppable getUpdateSteppable()
/*     */   {
/* 122 */     return new Steppable()
/*     */     {
/*     */       public void step(final SimState state)
/*     */       {
/* 126 */         SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 130 */             synchronized (state.schedule)
/*     */             {
/* 132 */               Inspector.this.updateInspector();
/* 133 */               Inspector.this.repaint();
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   protected final void updateButtonPressed()
/*     */   {
/* 147 */     updateInspector();
/*     */   }
/*     */ 
/*     */   public Component makeUpdateButton()
/*     */   {
/* 155 */     JButton jb = new JButton(UPDATE_ICON);
/* 156 */     jb.setText("Refresh");
/*     */ 
/* 158 */     jb.putClientProperty("Quaqua.Button.style", "square");
/*     */ 
/* 163 */     jb.setToolTipText("Refreshes this inspector to reflect the current underlying values in the model.");
/*     */ 
/* 165 */     jb.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/* 169 */         Inspector.this.updateInspector();
/*     */       }
/*     */     });
/* 172 */     return jb;
/*     */   }
/*     */ 
/*     */   static ImageIcon iconFor(String name)
/*     */   {
/* 183 */     return new ImageIcon(Inspector.class.getResource(name));
/*     */   }
/*     */ 
/*     */   public Stoppable reviseStopper(final Stoppable stopper)
/*     */   {
/* 196 */     return new Stoppable()
/*     */     {
/*     */       public void stop()
/*     */       {
/* 200 */         stopper.stop();
/* 201 */         Inspector.this.stopped = true;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public void disposeFrame()
/*     */   {
/* 210 */     Component c = this;
/* 211 */     while ((c != null) && (!(c instanceof JFrame))) {
/* 212 */       c = c.getParent();
/*     */     }
/*     */ 
/* 217 */     if ((c != null) && (!(c instanceof Controller)))
/* 218 */       ((JFrame)c).dispose();
/*     */   }
/*     */ 
/*     */   public boolean isStopped()
/*     */   {
/* 223 */     return this.stopped;
/*     */   }
/*     */ 
/*     */   public JFrame createFrame(Stoppable stopper)
/*     */   {
/* 230 */     JScrollPane scroller = new JScrollPane(this);
/* 231 */     scroller.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
/*     */ 
/* 237 */     final Stoppable[] stopperHolder = { stopper };
/* 238 */     JFrame frame = new JFrame()
/*     */     {
/*     */       public void dispose()
/*     */       {
/* 242 */         super.dispose();
/* 243 */         if (stopperHolder[0] != null) stopperHolder[0].stop();
/* 244 */         stopperHolder[0] = null;
/*     */       }
/*     */     };
/* 247 */     frame.getRootPane().putClientProperty("Window.style", "small");
/*     */ 
/* 249 */     frame.setTitle(getTitle());
/* 250 */     frame.setDefaultCloseOperation(2);
/* 251 */     frame.getContentPane().setLayout(new BorderLayout());
/* 252 */     frame.getContentPane().add(scroller, "Center");
/* 253 */     frame.setResizable(true);
/* 254 */     frame.pack();
/*     */ 
/* 256 */     if (Display2D.isMacOSX)
/*     */     {
/* 259 */       Dimension d = frame.getSize();
/* 260 */       if (d.width < 128) d.width = 128;
/* 261 */       if (d.height < 37) d.height = 37;
/* 262 */       frame.setSize(d);
/*     */     }
/* 264 */     return frame;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.Inspector
 * JD-Core Version:    0.6.2
 */