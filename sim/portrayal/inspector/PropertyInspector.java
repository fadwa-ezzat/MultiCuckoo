/*     */ package sim.portrayal.inspector;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StreamTokenizer;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JToggleButton;
/*     */ import sim.display.Console;
/*     */ import sim.display.Controller;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.util.Bag;
/*     */ import sim.util.Properties;
/*     */ import sim.util.gui.Utilities;
/*     */ 
/*     */ public abstract class PropertyInspector extends Inspector
/*     */ {
/*     */   public int index;
/*     */   public Properties properties;
/*     */   public GUIState simulation;
/*  60 */   static Bag classes = null;
/*  61 */   boolean validInspector = false;
/*     */   protected Stoppable stopper;
/*     */ 
/*     */   public void setStopper(Stoppable stopper)
/*     */   {
/*  66 */     this.stopper = stopper;
/*     */   }
/*     */ 
/*     */   public Stoppable getStopper()
/*     */   {
/*  71 */     return this.stopper;
/*     */   }
/*     */ 
/*     */   protected void setValidInspector(boolean val) {
/*  75 */     this.validInspector = val;
/*     */   }
/*     */   public boolean isValidInspector() {
/*  78 */     return this.validInspector;
/*     */   }
/*     */ 
/*     */   public static PropertyInspector makeInspector(Class inspectorClass, Properties properties, int index, Frame parent, GUIState simulation)
/*     */   {
/*  85 */     synchronized (simulation.state.schedule)
/*     */     {
/*     */       try
/*     */       {
/*  89 */         PropertyInspector inspector = (PropertyInspector)inspectorClass.getConstructor(new Class[] { Properties.class, Integer.TYPE, Frame.class, GUIState.class }).newInstance(new Object[] { properties, Integer.valueOf(index), parent, simulation });
/*     */ 
/*  92 */         if (inspector.isValidInspector()) return inspector;
/*  93 */         return null;
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  97 */         e.printStackTrace();
/*  98 */         return null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String name() {
/* 104 */     return "Name Not Set";
/*     */   }
/*     */   public static Class[] types() {
/* 107 */     return new Class[0];
/*     */   }
/*     */ 
/*     */   public PropertyInspector(Properties properties, int index, Frame parent, GUIState simulation)
/*     */   {
/* 116 */     this.properties = properties;
/* 117 */     this.index = index;
/*     */ 
/* 119 */     this.simulation = simulation;
/*     */   }
/*     */ 
/*     */   public static String getMenuNameForPropertyInspectorClass(String classname)
/*     */   {
/*     */     try
/*     */     {
/* 126 */       return (String)Class.forName(classname, true, Thread.currentThread().getContextClassLoader()).getMethod("name", new Class[0]).invoke(null, new Object[0]);
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/* 130 */       e.printStackTrace();
/* 131 */     }return null;
/*     */   }
/*     */ 
/*     */   static boolean typesForClassCompatable(String classname, Class type)
/*     */   {
/*     */     try
/*     */     {
/* 139 */       Class[] types = (Class[])Class.forName(classname, true, Thread.currentThread().getContextClassLoader()).getMethod("types", new Class[0]).invoke(null, new Object[0]);
/* 140 */       if (types == null) return true;
/* 141 */       for (int x = 0; x < types.length; x++)
/* 142 */         if (types[x].isAssignableFrom(type))
/* 143 */           return true;
/*     */     } catch (Throwable e) {
/* 145 */       e.printStackTrace();
/* 146 */     }return false;
/*     */   }
/*     */ 
/*     */   public static Bag getPropertyInspectorClassNames()
/*     */   {
/* 151 */     if (classes == null)
/*     */     {
/* 153 */       classes = new Bag();
/*     */       try
/*     */       {
/* 157 */         InputStream s = PropertyInspector.class.getResourceAsStream("propertyinspector.classes");
/* 158 */         StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(s)));
/* 159 */         st.resetSyntax();
/* 160 */         st.wordChars(33, 255);
/* 161 */         st.commentChar(35);
/* 162 */         st.whitespaceChars(0, 32);
/* 163 */         while (st.nextToken() != -1)
/* 164 */           if (st.sval != null) classes.add(st.sval);
/* 165 */         s.close();
/*     */       }
/*     */       catch (Throwable e)
/*     */       {
/* 169 */         System.err.println("WARNING: Couldn't load the Propertyinspector.classes file because of error. \nLikely the file does not exist or could not be opened.\nThe error was:\n");
/* 170 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 173 */     return classes;
/*     */   }
/*     */ 
/*     */   public static JToggleButton getPopupMenu(final Properties properties, final int index, final GUIState state, JPopupMenu pop)
/*     */   {
/* 181 */     boolean somethingCompatable = false;
/* 182 */     Bag classes = getPropertyInspectorClassNames();
/*     */ 
/* 186 */     if (pop == null) pop = new JPopupMenu();
/* 187 */     JPopupMenu popup = pop;
/* 188 */     popup.setLightWeightPopupEnabled(false);
/* 189 */     final JToggleButton toggleButton = new JToggleButton(INSPECT_ICON);
/* 190 */     toggleButton.setPressedIcon(INSPECT_ICON_P);
/* 191 */     toggleButton.setBorderPainted(false);
/* 192 */     toggleButton.setContentAreaFilled(false);
/* 193 */     toggleButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/* 194 */     toggleButton.setToolTipText("Show Additional Per-Property Inspectors");
/* 195 */     toggleButton.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mousePressed(MouseEvent e)
/*     */       {
/* 199 */         this.val$popup.show(e.getComponent(), 0, toggleButton.getSize().height);
/*     */       }
/*     */ 
/*     */       public void mouseReleased(MouseEvent e) {
/* 203 */         toggleButton.setSelected(false);
/*     */       }
/*     */     });
/* 207 */     for (int x = 0; x < classes.numObjs; x++)
/*     */     {
/* 210 */       Class _theClass = null;
/*     */       try
/*     */       {
/* 213 */         _theClass = Class.forName((String)classes.objs[x], true, Thread.currentThread().getContextClassLoader());
/*     */       }
/*     */       catch (ClassNotFoundException error)
/*     */       {
/* 218 */         continue;
/*     */       }
/*     */       catch (NoClassDefFoundError error)
/*     */       {
/* 223 */         continue;
/*     */       }
/* 225 */       Class theClass = _theClass;
/*     */ 
/* 227 */       JMenuItem menu = new JMenuItem(getMenuNameForPropertyInspectorClass((String)classes.objs[x]));
/* 228 */       popup.add(menu);
/* 229 */       if (!typesForClassCompatable((String)classes.objs[x], properties.getType(index)))
/* 230 */         menu.setEnabled(false);
/* 231 */       else somethingCompatable = true;
/*     */ 
/* 233 */       menu.addActionListener(new ActionListener()
/*     */       {
/*     */         public void actionPerformed(ActionEvent e)
/*     */         {
/* 237 */           PropertyInspector inspector = PropertyInspector.makeInspector(this.val$theClass, properties, index, (Console)state.controller, state);
/* 238 */           if (inspector != null)
/*     */           {
/*     */             try
/*     */             {
/* 242 */               inspector.setStopper(inspector.reviseStopper(state.scheduleRepeatingImmediatelyAfter(inspector.getUpdateSteppable())));
/*     */             }
/*     */             catch (IllegalArgumentException ex)
/*     */             {
/* 246 */               Utilities.inform("The simulation is over and the item will not be tracked further.", "If you wanted to track, restart the simulation in paused state, then try tracking the item again.", null);
/*     */ 
/* 248 */               inspector.setStopper(inspector.reviseStopper(new Stoppable() {
/*     */                 public void stop() {  } } ));
/*     */             }
/* 251 */             state.controller.registerInspector(inspector, inspector.getStopper());
/*     */ 
/* 253 */             if (inspector.shouldCreateFrame())
/*     */             {
/* 255 */               JFrame frame = inspector.createFrame(inspector.getStopper());
/* 256 */               frame.setVisible(true);
/*     */             }
/*     */ 
/* 260 */             inspector.updateInspector();
/*     */           }
/*     */         }
/*     */       });
/*     */     }
/*     */ 
/* 266 */     if (!somethingCompatable) return null;
/* 267 */     return toggleButton;
/*     */   }
/*     */ 
/*     */   public JFrame createFrame(Stoppable stopper)
/*     */   {
/* 272 */     JFrame frame = super.createFrame(stopper);
/* 273 */     frame.setTitle("" + this.properties.getName(this.index) + " of " + this.properties.getObject());
/* 274 */     return frame;
/*     */   }
/*     */ 
/*     */   public boolean shouldCreateFrame() {
/* 278 */     return true;
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.inspector.PropertyInspector
 * JD-Core Version:    0.6.2
 */