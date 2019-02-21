/*     */ package sim.portrayal;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.swing.JLabel;
/*     */ import sim.display.GUIState;
/*     */ import sim.util.Bag;
/*     */ import sim.util.gui.DisclosurePanel;
/*     */ import sim.util.gui.LabelledList;
/*     */ 
/*     */ public abstract class FieldPortrayal
/*     */ {
/*     */   public Portrayal portrayalForAll;
/*     */   public Portrayal portrayalForNull;
/*     */   public Portrayal portrayalForNonNull;
/*     */   public Portrayal portrayalForRemainder;
/*     */   public WeakHashMap portrayals;
/*     */   public WeakHashMap classPortrayals;
/*     */   protected Object field;
/*     */   protected boolean immutableField;
/*     */   boolean dirtyField;
/*     */ 
/*     */   public FieldPortrayal()
/*     */   {
/* 185 */     this.field = null;
/* 186 */     this.immutableField = false;
/*     */ 
/* 193 */     this.dirtyField = true;
/*     */   }
/*     */ 
/*     */   public void setPortrayalForAll(Portrayal portrayal)
/*     */   {
/*  84 */     this.portrayalForAll = portrayal;
/*     */   }
/*     */ 
/*     */   public Portrayal getPortrayalForAll()
/*     */   {
/*  89 */     return this.portrayalForAll;
/*     */   }
/*     */ 
/*     */   public void setPortrayalForNull(Portrayal portrayal)
/*     */   {
/*  95 */     this.portrayalForNull = portrayal;
/*     */   }
/*     */ 
/*     */   public Portrayal getPortrayalForNull()
/*     */   {
/* 100 */     return this.portrayalForNull;
/*     */   }
/*     */ 
/*     */   public void setPortrayalForNonNull(Portrayal portrayal)
/*     */   {
/* 106 */     this.portrayalForNonNull = portrayal;
/*     */   }
/*     */ 
/*     */   public Portrayal getPortrayalForNonNull()
/*     */   {
/* 111 */     return this.portrayalForNonNull;
/*     */   }
/*     */ 
/*     */   public void setPortrayalForRemainder(Portrayal portrayal)
/*     */   {
/* 117 */     this.portrayalForRemainder = portrayal;
/*     */   }
/*     */ 
/*     */   public Portrayal getPortrayalForRemainder()
/*     */   {
/* 122 */     return this.portrayalForRemainder;
/*     */   }
/*     */ 
/*     */   public void setPortrayalForClass(Class cls, Portrayal portrayal)
/*     */   {
/* 129 */     if (this.classPortrayals == null) this.classPortrayals = new WeakHashMap();
/* 130 */     if (portrayal == null)
/* 131 */       this.classPortrayals.remove(cls);
/* 132 */     else this.classPortrayals.put(cls, portrayal);
/*     */   }
/*     */ 
/*     */   public void setPortrayalForObject(Object obj, Portrayal portrayal)
/*     */   {
/* 139 */     if (this.portrayals == null) this.portrayals = new WeakHashMap();
/* 140 */     if (portrayal == null)
/* 141 */       this.portrayals.remove(obj);
/* 142 */     else this.portrayals.put(obj, portrayal);
/*     */   }
/*     */ 
/*     */   public Portrayal getDefaultNullPortrayal()
/*     */   {
/* 150 */     return getDefaultPortrayal();
/*     */   }
/*     */ 
/*     */   public abstract Portrayal getDefaultPortrayal();
/*     */ 
/*     */   public Portrayal getPortrayalForObject(Object obj)
/*     */   {
/* 163 */     if (this.portrayalForAll != null) return this.portrayalForAll;
/*     */ 
/* 165 */     if (obj == null)
/*     */     {
/* 167 */       if (this.portrayalForNull != null) return this.portrayalForNull;
/*     */       Portrayal tmp;
/* 168 */       if ((this.portrayals != null) && ((tmp = (Portrayal)this.portrayals.get(obj)) != null))
/* 169 */         return tmp;
/* 170 */       return getDefaultNullPortrayal();
/*     */     }
/*     */ 
/* 174 */     if ((obj instanceof Portrayal)) return (Portrayal)obj;
/* 175 */     if (this.portrayalForNonNull != null) return this.portrayalForNonNull;
/*     */     Portrayal tmp;
/* 176 */     if ((this.portrayals != null) && ((tmp = (Portrayal)this.portrayals.get(obj)) != null))
/* 177 */       return tmp;
/*     */     Portrayal tmp;
/* 178 */     if ((this.classPortrayals != null) && ((tmp = (Portrayal)this.classPortrayals.get(obj.getClass())) != null))
/* 179 */       return tmp;
/* 180 */     if (this.portrayalForRemainder != null) return this.portrayalForRemainder;
/* 181 */     return getDefaultPortrayal();
/*     */   }
/*     */ 
/*     */   public synchronized void setDirtyField(boolean val)
/*     */   {
/* 195 */     this.dirtyField = val; } 
/* 196 */   public synchronized boolean isDirtyField() { return this.dirtyField; }
/*     */ 
/*     */   /** @deprecated */
/*     */   public synchronized void reset()
/*     */   {
/* 201 */     this.dirtyField = true;
/*     */   }
/*     */ 
/*     */   public boolean isImmutableField()
/*     */   {
/* 206 */     return this.immutableField;
/*     */   }
/*     */ 
/*     */   public void setImmutableField(boolean val)
/*     */   {
/* 211 */     this.immutableField = val; setDirtyField(true);
/*     */   }
/*     */ 
/*     */   public Object getField()
/*     */   {
/* 216 */     return this.field;
/*     */   }
/*     */ 
/*     */   public void setField(Object field)
/*     */   {
/* 223 */     this.field = field;
/* 224 */     setDirtyField(true);
/*     */   }
/*     */ 
/*     */   public Inspector getInspector(LocationWrapper wrapper, GUIState state)
/*     */   {
/* 296 */     if (wrapper == null) return null;
/* 297 */     Inspector objectInspectorComponent = getPortrayalForObject(wrapper.getObject()).getInspector(wrapper, state);
/*     */ 
/* 299 */     if (objectInspectorComponent == null) return null;
/* 300 */     return new CustomInspector(wrapper, objectInspectorComponent, state);
/*     */   }
/*     */ 
/*     */   public String getName(LocationWrapper wrapper)
/*     */   {
/* 305 */     if (wrapper == null) return "";
/* 306 */     return getPortrayalForObject(wrapper.getObject()).getName(wrapper);
/*     */   }
/*     */ 
/*     */   public String getStatus(LocationWrapper wrapper)
/*     */   {
/* 311 */     if (wrapper == null) return "";
/* 312 */     return getPortrayalForObject(wrapper.getObject()).getStatus(wrapper);
/*     */   }
/*     */ 
/*     */   public boolean setSelected(LocationWrapper wrapper, boolean selected)
/*     */   {
/* 320 */     return getPortrayalForObject(wrapper.getObject()).setSelected(wrapper, selected);
/*     */   }
/*     */ 
/*     */   public void setSelected(Bag locationWrappers, boolean selected)
/*     */   {
/* 325 */     for (int x = 0; x < locationWrappers.numObjs; x++)
/*     */     {
/* 327 */       LocationWrapper wrapper = (LocationWrapper)locationWrappers.objs[x];
/* 328 */       setSelected(wrapper, selected);
/*     */     }
/*     */   }
/*     */ 
/*     */   class CustomInspector extends Inspector
/*     */   {
/* 229 */     public JLabel positions = new JLabel();
/*     */     public DisclosurePanel disclosurePanel;
/* 231 */     public LabelledList fieldComponent = new LabelledList("Location");
/*     */     public Inspector locationInspector;
/*     */     public Inspector objectInspector;
/*     */     public LocationWrapper wrapper;
/*     */     public Object lastObject;
/*     */     public Object lastLocation;
/*     */     public GUIState state;
/*     */ 
/*     */     public CustomInspector(LocationWrapper wrapper, Inspector objectInspector, GUIState state)
/*     */     {
/* 243 */       this.state = state;
/* 244 */       this.wrapper = wrapper;
/* 245 */       this.objectInspector = objectInspector;
/* 246 */       this.state = state;
/* 247 */       this.lastObject = wrapper.getObject();
/* 248 */       setLayout(new BorderLayout());
/* 249 */       this.lastLocation = wrapper.getLocation();
/* 250 */       this.positions.setText(wrapper.getLocationName());
/* 251 */       this.locationInspector = Inspector.getInspector(this.lastLocation, state, null);
/* 252 */       this.disclosurePanel = new DisclosurePanel(this.positions, this.locationInspector, "Position");
/* 253 */       add(this.disclosurePanel, "North");
/* 254 */       add(objectInspector, "Center");
/* 255 */       updateInspector();
/*     */     }
/*     */ 
/*     */     public String getTitle()
/*     */     {
/* 263 */       return this.objectInspector.getTitle();
/*     */     }
/*     */ 
/*     */     public void updateInspector()
/*     */     {
/* 268 */       Object newObject = this.wrapper.getObject();
/* 269 */       if (newObject != this.lastObject)
/*     */       {
/* 277 */         remove(this.objectInspector);
/* 278 */         this.objectInspector = FieldPortrayal.this.getPortrayalForObject(this.wrapper.getObject()).getInspector(this.wrapper, this.state);
/* 279 */         add(this.objectInspector, "Center");
/* 280 */         revalidate();
/*     */       }
/* 282 */       Object location = this.wrapper.getLocation();
/* 283 */       if (location != this.lastLocation)
/*     */       {
/* 285 */         this.disclosurePanel.setDisclosedComponent(Inspector.getInspector(location, this.state, null));
/* 286 */         this.lastLocation = location;
/*     */       }
/* 288 */       this.positions.setText(this.wrapper.getLocationName());
/* 289 */       this.objectInspector.updateInspector();
/* 290 */       this.locationInspector.updateInspector();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.FieldPortrayal
 * JD-Core Version:    0.6.2
 */