/*     */ package sim.portrayal;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JToggleButton;
/*     */ import javax.swing.SwingUtilities;
/*     */ import sim.display.Controller;
/*     */ import sim.display.GUIState;
/*     */ import sim.engine.SimState;
/*     */ import sim.engine.Stoppable;
/*     */ import sim.portrayal.inspector.PropertyInspector;
/*     */ import sim.util.Interval;
/*     */ import sim.util.Properties;
/*     */ import sim.util.gui.LabelledList;
/*     */ import sim.util.gui.NumberTextField;
/*     */ import sim.util.gui.PropertyField;
/*     */ 
/*     */ public class SimpleInspector extends Inspector
/*     */ {
/*     */   public static final int DEFAULT_MAX_PROPERTIES = 100;
/*  30 */   int maxProperties = 100;
/*     */   GUIState state;
/*     */   LabelledList propertyList;
/*     */   Properties properties;
/*  38 */   PropertyField[] members = new PropertyField[0];
/*     */ 
/*  40 */   int start = 0;
/*     */ 
/*  42 */   int count = 0;
/*  43 */   JPanel header = new JPanel() {
/*     */     public Insets getInsets() {
/*  45 */       return new Insets(2, 2, 2, 2);
/*     */     }
/*  43 */   };
/*     */   String listName;
/*  48 */   JLabel numElements = null;
/*  49 */   Box startField = null;
/*     */ 
/* 277 */   JButton updateButton = null;
/*     */ 
/*     */   public GUIState getGUIState()
/*     */   {
/*  51 */     return this.state; } 
/*  52 */   public int getMaxProperties() { return this.maxProperties; }
/*     */ 
/*     */ 
/*     */   public SimpleInspector(Properties properties, GUIState state, String name, int maxProperties)
/*     */   {
/*  59 */     this.numElements = new JLabel();
/*  60 */     this.maxProperties = maxProperties;
/*  61 */     setLayout(new BorderLayout());
/*  62 */     this.state = state;
/*  63 */     this.listName = name;
/*  64 */     this.header.setLayout(new BorderLayout());
/*  65 */     add(this.header, "North");
/*  66 */     this.properties = properties;
/*  67 */     generateProperties(0);
/*  68 */     setTitle("" + properties.getObject());
/*     */   }
/*     */ 
/*     */   public SimpleInspector(Properties properties, GUIState state, String name)
/*     */   {
/*  76 */     this(properties, state, name, state.getMaximumPropertiesForInspector());
/*     */   }
/*     */ 
/*     */   public SimpleInspector(Object object, GUIState state)
/*     */   {
/*  82 */     this(object, state, null);
/*     */   }
/*     */ 
/*     */   public SimpleInspector(Object object, GUIState state, String name)
/*     */   {
/*  90 */     this(object, state, name, state.getMaximumPropertiesForInspector());
/*     */   }
/*     */ 
/*     */   public SimpleInspector(Object object, GUIState state, String name, int maxProperties)
/*     */   {
/*  98 */     this(Properties.getProperties(object), state, name, maxProperties);
/*     */   }
/*     */ 
/*     */   JPopupMenu makePreliminaryPopup(final int index)
/*     */   {
/* 105 */     if (this.properties.isComposite(index))
/*     */     {
/* 107 */       JPopupMenu popup = new JPopupMenu();
/* 108 */       JMenuItem menu = new JMenuItem("View");
/* 109 */       menu.setEnabled(true);
/* 110 */       menu.addActionListener(new ActionListener()
/*     */       {
/*     */         public void actionPerformed(ActionEvent e)
/*     */         {
/* 114 */           Properties props = SimpleInspector.this.properties;
/* 115 */           Inspector inspector = Inspector.getInspector(props.getValue(index), SimpleInspector.this.state, null);
/* 116 */           Stoppable stopper = null;
/*     */           try
/*     */           {
/* 119 */             stopper = SimpleInspector.this.state.scheduleRepeatingImmediatelyAfter(inspector.getUpdateSteppable());
/*     */           }
/*     */           catch (IllegalArgumentException ee)
/*     */           {
/* 124 */             stopper = new Stoppable() {
/*     */               public void stop() {  } } ;
/* 126 */           }stopper = inspector.reviseStopper(stopper);
/* 127 */           SimpleInspector.this.state.controller.registerInspector(inspector, stopper);
/* 128 */           JFrame frame = inspector.createFrame(stopper);
/* 129 */           frame.setVisible(true);
/*     */         }
/*     */       });
/* 132 */       popup.add(menu);
/* 133 */       return popup;
/*     */     }
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */   PropertyField makePropertyField(final int index)
/*     */   {
/* 140 */     Class type = this.properties.getType(index);
/* 141 */     final Properties props = this.properties;
/* 142 */     return new PropertyField(null, this.properties.betterToString(this.properties.getValue(index)), this.properties.isReadWrite(index), this.properties.getDomain(index), (this.properties.getDomain(index) instanceof Interval) ? 3 : this.properties.getDomain(index) == null ? 1 : (type == Boolean.TYPE) || (type == Boolean.class) ? 0 : this.properties.isComposite(index) ? 1 : 4)
/*     */     {
/*     */       public String newValue(String newValue)
/*     */       {
/* 170 */         synchronized (SimpleInspector.this.state.state.schedule)
/*     */         {
/* 173 */           if (props.setValue(index, newValue) == null) {
/* 174 */             Toolkit.getDefaultToolkit().beep();
/*     */           }
/* 176 */           if (SimpleInspector.this.state.controller != null) {
/* 177 */             SimpleInspector.this.state.controller.refresh();
/*     */           }
/* 179 */           return props.betterToString(props.getValue(index));
/*     */         }
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   void doEnsuredRepaint(final Component component)
/*     */   {
/* 189 */     SwingUtilities.invokeLater(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 193 */         if (component != null) component.repaint();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   void generateProperties(int start)
/*     */   {
/* 200 */     int len = this.properties.numProperties();
/* 201 */     if (start < 0) start = 0;
/* 202 */     if (start > len) return;
/*     */ 
/* 204 */     if (this.propertyList != null)
/* 205 */       remove(this.propertyList);
/* 206 */     this.propertyList = new LabelledList(this.listName);
/*     */ 
/* 208 */     if (len > this.maxProperties)
/*     */     {
/* 210 */       String s = "Page forward/back through properties.  " + this.maxProperties + " properties shown at a time.";
/* 211 */       if (this.startField == null)
/*     */       {
/* 213 */         NumberTextField f = new NumberTextField(" Go to ", start, 1.0D, this.maxProperties)
/*     */         {
/*     */           public double newValue(double newValue)
/*     */           {
/* 217 */             int newIndex = (int)newValue;
/* 218 */             if (newIndex < 0) newIndex = 0;
/* 219 */             if (newIndex >= this.val$len) return (int)getValue();
/*     */ 
/* 221 */             SimpleInspector.this.generateProperties(newIndex);
/* 222 */             return newIndex;
/*     */           }
/*     */         };
/* 226 */         f.setToolTipText(s);
/* 227 */         this.numElements.setText(" of " + len + " ");
/* 228 */         this.numElements.setToolTipText(s);
/* 229 */         f.getField().setColumns(4);
/* 230 */         this.startField = new Box(0);
/* 231 */         this.startField.add(f);
/* 232 */         this.startField.add(this.numElements);
/* 233 */         this.startField.add(Box.createGlue());
/* 234 */         this.header.add(this.startField, "Center");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 239 */       start = 0;
/* 240 */       if (this.startField != null) this.header.remove(this.startField);
/*     */     }
/*     */ 
/* 243 */     this.members = new PropertyField[len];
/*     */ 
/* 245 */     int end = start + this.maxProperties;
/* 246 */     if (end > len) end = len;
/* 247 */     this.count = (end - start);
/* 248 */     for (int i = start; i < end; i++)
/*     */     {
/* 250 */       if (!this.properties.isHidden(i))
/*     */       {
/* 252 */         JLabel label = new JLabel(this.properties.getName(i) + " ");
/* 253 */         JToggleButton toggle = PropertyInspector.getPopupMenu(this.properties, i, this.state, makePreliminaryPopup(i));
/* 254 */         this.members[i] = makePropertyField(i);
/* 255 */         this.propertyList.add(null, label, toggle, this.members[i], null);
/*     */ 
/* 262 */         String description = this.properties.getDescription(i);
/* 263 */         if (description != null)
/*     */         {
/* 265 */           if (label != null) label.setToolTipText(description);
/* 266 */           if (toggle != null) toggle.setToolTipText(description);
/* 267 */           if (this.members[i] != null) this.members[i].setToolTipText(description); 
/*     */         }
/*     */       }
/* 270 */       else { this.members[i] = null; }
/*     */     }
/* 272 */     add(this.propertyList, "Center");
/* 273 */     this.start = start;
/* 274 */     revalidate();
/*     */   }
/*     */ 
/*     */   public void setVolatile(boolean val)
/*     */   {
/* 280 */     super.setVolatile(val);
/* 281 */     if (isVolatile())
/*     */     {
/* 283 */       if (this.updateButton != null)
/*     */       {
/* 285 */         this.header.remove(this.updateButton); revalidate();
/*     */       }
/*     */ 
/*     */     }
/* 290 */     else if (this.updateButton == null)
/*     */     {
/* 292 */       this.updateButton = ((JButton)makeUpdateButton());
/*     */ 
/* 295 */       NumberTextField sacrificial = new NumberTextField(1.0D, true);
/* 296 */       Dimension d = sacrificial.getPreferredSize();
/* 297 */       d.width = this.updateButton.getPreferredSize().width;
/* 298 */       this.updateButton.setPreferredSize(d);
/* 299 */       d = sacrificial.getMinimumSize();
/* 300 */       d.width = this.updateButton.getMinimumSize().width;
/* 301 */       this.updateButton.setMinimumSize(d);
/*     */ 
/* 304 */       this.header.add(this.updateButton, "West");
/* 305 */       revalidate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateInspector()
/*     */   {
/* 312 */     if (this.properties.isVolatile())
/*     */     {
/* 314 */       remove(this.propertyList);
/* 315 */       generateProperties(this.start);
/* 316 */       doEnsuredRepaint(this);
/*     */     } else {
/* 318 */       for (int i = this.start; i < this.start + this.count; i++)
/* 319 */         if (this.members[i] != null)
/* 320 */           this.members[i].setValue(this.properties.betterToString(this.properties.getValue(i)));
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.SimpleInspector
 * JD-Core Version:    0.6.2
 */