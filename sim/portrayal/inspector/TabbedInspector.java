/*     */ package sim.portrayal.inspector;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import sim.display.GUIState;
/*     */ import sim.portrayal.Inspector;
/*     */ import sim.portrayal.SimpleInspector;
/*     */ import sim.util.Properties;
/*     */ import sim.util.SimpleProperties;
/*     */ 
/*     */ public class TabbedInspector extends Inspector
/*     */ {
/*  37 */   public ArrayList inspectors = new ArrayList();
/*  38 */   public JTabbedPane tabs = null;
/*     */   boolean updatingAllInspectors;
/*     */ 
/*     */   public TabbedInspector()
/*     */   {
/*  44 */     buildTabbedInspector(null, true);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public TabbedInspector(boolean isVolatile)
/*     */   {
/*  52 */     buildTabbedInspector(null, isVolatile);
/*     */   }
/*     */ 
/*     */   public TabbedInspector(String name)
/*     */   {
/*  58 */     buildTabbedInspector(name, true);
/*     */   }
/*     */ 
/*     */   void buildTabbedInspector(String name, boolean isVolatile)
/*     */   {
/*  64 */     UIDefaults defaults = UIManager.getDefaults();
/*  65 */     Object def = defaults.get("TabbedPane.useSmallLayout");
/*  66 */     defaults.put("TabbedPane.useSmallLayout", Boolean.TRUE);
/*  67 */     this.tabs = new JTabbedPane();
/*  68 */     defaults.put("TabbedPane.useSmallLayout", def);
/*     */ 
/*  71 */     setLayout(new BorderLayout());
/*  72 */     add(this.tabs, "Center");
/*  73 */     this.tabs.addChangeListener(new ChangeListener()
/*     */     {
/*     */       public void stateChanged(ChangeEvent e)
/*     */       {
/*  77 */         TabbedInspector.this.updateDisplayedInspector();
/*     */       }
/*     */     });
/*  80 */     setVolatile(isVolatile);
/*  81 */     if (name != null)
/*  82 */       setBorder(new TitledBorder(name));
/*     */   }
/*     */ 
/*     */   public TabbedInspector(Tabbable object, GUIState state, String name)
/*     */   {
/*  94 */     Properties properties = Properties.getProperties(object);
/*  95 */     if ((properties instanceof SimpleProperties))
/*     */     {
/*  97 */       buildTabbedInspector((SimpleProperties)properties, state, name, object.provideTabProperties(), object.provideTabNames(), object.provideExtraTab());
/*     */     }
/*     */     else
/*     */     {
/* 101 */       throw new RuntimeException("A Tabbable Object must provide SimpleProperties.");
/*     */     }
/*     */   }
/*     */ 
/*     */   public TabbedInspector(SimpleProperties properties, GUIState state, String name, String[][] propertyNames, String[] tabNames, String extraTab, boolean isVolatile)
/*     */   {
/* 113 */     buildTabbedInspector(properties, state, name, propertyNames, tabNames, extraTab);
/*     */   }
/*     */ 
/*     */   void buildTabbedInspector(SimpleProperties properties, GUIState state, String name, String[][] propertyNames, String[] tabNames, String extraTab)
/*     */   {
/* 118 */     buildTabbedInspector(name, true);
/*     */ 
/* 120 */     if (tabNames == null)
/* 121 */       throw new RuntimeException("Tab names provided is null.");
/* 122 */     if (propertyNames == null)
/* 123 */       throw new RuntimeException("Property names provided is null.");
/* 124 */     if (tabNames.length != propertyNames.length)
/*     */     {
/* 126 */       throw new RuntimeException("Property names and tab names must have the same length.");
/*     */     }
/*     */ 
/* 129 */     for (int i = 0; i < propertyNames.length; i++)
/*     */     {
/* 131 */       SimpleProperties simp = properties.getPropertiesSubset(propertyNames[i], true);
/* 132 */       addInspector(new SimpleInspector(simp, state, null), tabNames[i]);
/*     */     }
/*     */ 
/* 135 */     if (extraTab != null)
/*     */     {
/* 138 */       int count = 0;
/* 139 */       for (int i = 0; i < propertyNames.length; i++)
/* 140 */         count += propertyNames[i].length;
/* 141 */       String[] group = new String[count];
/* 142 */       count = 0;
/* 143 */       for (int i = 0; i < propertyNames.length; i++)
/*     */       {
/* 145 */         System.arraycopy(propertyNames[i], 0, group, count, propertyNames[i].length);
/* 146 */         count += propertyNames[i].length;
/*     */       }
/*     */ 
/* 149 */       SimpleProperties simp = properties.getPropertiesSubset(group, false);
/* 150 */       addInspector(new SimpleInspector(simp, state, null), extraTab);
/*     */     }
/*     */ 
/* 153 */     setTitle("" + properties.getObject());
/*     */   }
/*     */ 
/*     */   void updateDisplayedInspector()
/*     */   {
/* 159 */     if ((this.tabs.getTabCount() > 0) && (this.tabs.getSelectedIndex() >= 0))
/*     */     {
/* 161 */       Inspector i = (Inspector)this.inspectors.get(this.tabs.getSelectedIndex());
/* 162 */       i.updateInspector();
/* 163 */       i.repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setUpdatingAllInspectors(boolean val)
/*     */   {
/* 172 */     this.updatingAllInspectors = true;
/*     */   }
/*     */ 
/*     */   public boolean isUpdatingAllInspectors()
/*     */   {
/* 180 */     return this.updatingAllInspectors;
/*     */   }
/*     */ 
/*     */   public void setVolatile(boolean val)
/*     */   {
/* 186 */     int len = this.inspectors.size();
/* 187 */     for (int x = 0; x < len; x++)
/* 188 */       ((Inspector)this.inspectors.get(x)).setVolatile(val);
/* 189 */     super.setVolatile(val);
/*     */   }
/*     */ 
/*     */   public void updateInspector()
/*     */   {
/* 194 */     int len = this.inspectors.size();
/* 195 */     if (this.updatingAllInspectors)
/* 196 */       for (int x = 0; x < len; x++)
/* 197 */         ((Inspector)this.inspectors.get(x)).updateInspector();
/* 198 */     else updateDisplayedInspector();
/*     */   }
/*     */ 
/*     */   public void addInspector(Inspector i, String tab)
/*     */   {
/* 204 */     i.setTitle(tab);
/* 205 */     this.inspectors.add(i);
/* 206 */     this.tabs.addTab(tab, i);
/* 207 */     i.setVolatile(isVolatile());
/*     */   }
/*     */ 
/*     */   public void addInspector(Inspector i)
/*     */   {
/* 213 */     this.inspectors.add(i);
/* 214 */     this.tabs.addTab(i.getTitle(), i);
/* 215 */     i.setVolatile(isVolatile());
/*     */   }
/*     */ 
/*     */   public Inspector removeInspector(Inspector i)
/*     */   {
/* 221 */     int len = this.inspectors.size();
/* 222 */     int x = 0;
/* 223 */     for (x = 0; (x < len) && 
/* 224 */       (this.inspectors.get(x) != i); x++);
/* 225 */     if (x == len) return null;
/*     */ 
/* 227 */     this.tabs.removeTabAt(x);
/* 228 */     return (Inspector)this.inspectors.remove(x);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 234 */     this.tabs.removeAll();
/* 235 */     this.inspectors.clear();
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.inspector.TabbedInspector
 * JD-Core Version:    0.6.2
 */