/*      */ package sim.display;
/*      */ 
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FileDialog;
/*      */ import java.awt.Font;
/*      */ import java.awt.Frame;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ComponentAdapter;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.KeyListener;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.awt.event.MouseMotionAdapter;
/*      */ import java.awt.event.MouseMotionListener;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Point2D.Double;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.geom.Rectangle2D.Double;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Method;
/*      */ import java.security.AccessControlException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.prefs.Preferences;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.DefaultListCellRenderer;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JCheckBoxMenuItem;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.JRadioButton;
/*      */ import javax.swing.JScrollBar;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.JToggleButton;
/*      */ import javax.swing.JToolTip;
/*      */ import javax.swing.JViewport;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.ToolTipManager;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.TitledBorder;
/*      */ import sim.engine.Schedule;
/*      */ import sim.engine.SimState;
/*      */ import sim.engine.Steppable;
/*      */ import sim.engine.Stoppable;
/*      */ import sim.portrayal.DrawInfo2D;
/*      */ import sim.portrayal.FieldPortrayal;
/*      */ import sim.portrayal.FieldPortrayal2D;
/*      */ import sim.portrayal.Inspector;
/*      */ import sim.portrayal.LocationWrapper;
/*      */ import sim.portrayal.SimplePortrayal2D;
/*      */ import sim.util.Bag;
/*      */ import sim.util.Double2D;
/*      */ import sim.util.gui.LabelledList;
/*      */ import sim.util.gui.MovieMaker;
/*      */ import sim.util.gui.NumberTextField;
/*      */ import sim.util.gui.Utilities;
/*      */ import sim.util.media.PDFEncoder;
/*      */ import sim.util.media.PNGEncoder;
/*      */ 
/*      */ public class Display2D extends JComponent
/*      */   implements Steppable, Manipulating2D
/*      */ {
/*   52 */   boolean forcePrecise = false;
/*   53 */   boolean precise = false;
/*      */ 
/*   65 */   public String DEFAULT_PREFERENCES_KEY = "Display2D";
/*   66 */   String preferencesKey = this.DEFAULT_PREFERENCES_KEY;
/*      */ 
/*  759 */   public static final boolean isMacOSX = isMacOSX();
/*      */ 
/*  762 */   public static final boolean isWindows = isWindows();
/*      */ 
/*  765 */   public static final String javaVersion = getJavaVersion();
/*      */ 
/*  848 */   public static final ImageIcon OPEN_HAND_CURSOR_P = iconFor("OpenHand.png");
/*  849 */   public static final ImageIcon CLOSED_HAND_CURSOR_P = iconFor("ClosedHand.png");
/*      */ 
/*  851 */   public static final ImageIcon LAYERS_ICON = iconFor("Layers.png");
/*  852 */   public static final ImageIcon LAYERS_ICON_P = iconFor("LayersPressed.png");
/*  853 */   public static final ImageIcon REFRESH_ICON = iconFor("Reload.png");
/*  854 */   public static final ImageIcon REFRESH_ICON_P = iconFor("ReloadPressed.png");
/*  855 */   public static final ImageIcon MOVIE_ON_ICON = iconFor("MovieOn.png");
/*  856 */   public static final ImageIcon MOVIE_ON_ICON_P = iconFor("MovieOnPressed.png");
/*  857 */   public static final ImageIcon MOVIE_OFF_ICON = iconFor("MovieOff.png");
/*  858 */   public static final ImageIcon MOVIE_OFF_ICON_P = iconFor("MovieOffPressed.png");
/*  859 */   public static final ImageIcon CAMERA_ICON = iconFor("Camera.png");
/*  860 */   public static final ImageIcon CAMERA_ICON_P = iconFor("CameraPressed.png");
/*  861 */   public static final ImageIcon OPTIONS_ICON = iconFor("Options.png");
/*  862 */   public static final ImageIcon OPTIONS_ICON_P = iconFor("OptionsPressed.png");
/*      */ 
/*  864 */   public static final Object[] REDRAW_OPTIONS = { "Steps/Redraw", "Model Secs/Redraw", "Real Secs/Redraw", "Always Redraw", "Never Redraw" };
/*      */   boolean useTooltips;
/*  872 */   long lastEncodedSteps = -1L;
/*      */   MovieMaker movieMaker;
/*      */   public InnerDisplay2D insideDisplay;
/*  880 */   public OptionPane optionPane = new OptionPane("");
/*      */ 
/*  883 */   ArrayList portrayals = new ArrayList();
/*      */   public JScrollPane display;
/*      */   JViewport port;
/*      */   Stoppable stopper;
/*      */   GUIState simulation;
/*      */   public Box header;
/*      */   public JPopupMenu popup;
/*      */   public JToggleButton layersbutton;
/*      */   public JPopupMenu refreshPopup;
/*      */   public JToggleButton refreshbutton;
/*      */   public JButton movieButton;
/*      */   public JButton snapshotButton;
/*      */   public JButton optionButton;
/*      */   public NumberTextField scaleField;
/*      */   public NumberTextField skipField;
/*      */   public JComboBox skipBox;
/*      */   public JFrame skipFrame;
/*  918 */   double scale = 1.0D;
/*  919 */   final Object scaleLock = new Object();
/*      */ 
/*  985 */   boolean clipping = true;
/*      */ 
/*  995 */   Paint backdrop = Color.white;
/*      */   int horizontalMaximum;
/*      */   int horizontalMinimum;
/*      */   int horizontalCurrent;
/*      */   int verticalMaximum;
/*      */   int verticalMinimum;
/*      */   int verticalCurrent;
/* 1026 */   final Object scrollLock = new Object();
/*      */ 
/* 1635 */   ArrayList selectedWrappers = new ArrayList();
/*      */   public static final int SELECTION_MODE_MULTI = 0;
/*      */   public static final int SELECTION_MODE_SINGLE = 1;
/* 1684 */   int selectionMode = 0;
/*      */   static final int SCROLL_BAR_SCROLL_RATIO = 10;
/* 1826 */   private Object sacrificialObj = null;
/*      */   public static final int TYPE_PDF = 1;
/*      */   public static final int TYPE_PNG = 2;
/*      */   public static final int UPDATE_RULE_STEPS = 0;
/*      */   public static final int UPDATE_RULE_INTERNAL_TIME = 1;
/*      */   public static final int UPDATE_RULE_WALLCLOCK_TIME = 2;
/*      */   public static final int UPDATE_RULE_ALWAYS = 3;
/*      */   public static final int UPDATE_RULE_NEVER = 4;
/* 2040 */   protected int updateRule = 3;
/* 2041 */   protected long stepInterval = 1L;
/* 2042 */   protected double timeInterval = 0.0D;
/* 2043 */   protected long wallInterval = 0L;
/* 2044 */   long lastStep = -1L;
/* 2045 */   double lastTime = -1.0D;
/* 2046 */   long lastWall = -1L;
/* 2047 */   Object[] updateLock = new Object[0];
/* 2048 */   boolean updateOnce = false;
/*      */   double originalXOffset;
/*      */   double originalYOffset;
/* 2102 */   Point originalMousePoint = null;
/* 2103 */   String originalText = "";
/*      */ 
/* 2105 */   boolean mouseChangesOffset = false;
/*      */ 
/* 2117 */   LocationWrapper movingWrapper = null;
/*      */ 
/* 2122 */   boolean openHand = false;
/* 2123 */   Cursor OPEN_HAND_CURSOR_C = getToolkit().createCustomCursor(OPEN_HAND_CURSOR_P.getImage(), new Point(8, 8), "Open Hand");
/* 2124 */   Cursor CLOSED_HAND_CURSOR_C = getToolkit().createCustomCursor(CLOSED_HAND_CURSOR_P.getImage(), new Point(8, 8), "Closed Hand");
/*      */ 
/*      */   public boolean getPrecise()
/*      */   {
/*   58 */     return this.precise;
/*      */   }
/*      */ 
/*      */   public void setPrecise(boolean precise)
/*      */   {
/*   63 */     this.precise = precise; this.optionPane.preciseDrawing.setSelected(precise);
/*      */   }
/*      */ 
/*      */   public void setPreferencesKey(String s)
/*      */   {
/*   73 */     if (s.trim().endsWith("/"))
/*   74 */       throw new RuntimeException("Key ends with '/', which is not allowed");
/*   75 */     this.preferencesKey = s;
/*      */   }
/*   77 */   public String getPreferencesKey() { return this.preferencesKey; }
/*      */ 
/*      */ 
/*      */   public void removeListeners()
/*      */   {
/*  306 */     this.insideDisplay.removeListeners();
/*      */   }
/*      */ 
/*      */   static boolean isMacOSX()
/*      */   {
/*      */     try
/*      */     {
/*  771 */       return System.getProperty("mrj.version") != null; } catch (Throwable e) {
/*      */     }
/*  773 */     return false;
/*      */   }
/*      */ 
/*      */   static boolean isWindows()
/*      */   {
/*      */     try
/*      */     {
/*  780 */       return (!isMacOSX()) && (System.getProperty("os.name").startsWith("Win")); } catch (Throwable e) {
/*      */     }
/*  782 */     return false;
/*      */   }
/*      */ 
/*      */   static String getJavaVersion()
/*      */   {
/*      */     try
/*      */     {
/*  789 */       return System.getProperty("java.version"); } catch (Throwable e) {
/*      */     }
/*  791 */     return "unknown";
/*      */   }
/*      */ 
/*      */   static ImageIcon iconFor(String name)
/*      */   {
/*  843 */     return new ImageIcon(Display2D.class.getResource(name));
/*      */   }
/*      */ 
/*      */   public void setScale(double val)
/*      */   {
/*  924 */     double oldScale = this.scale;
/*      */ 
/*  926 */     synchronized (this.scaleLock)
/*      */     {
/*  928 */       if (val > 0.0D)
/*      */       {
/*  930 */         this.scale = val;
/*  931 */         this.scaleField.setValue(this.scale);
/*      */       } else {
/*  933 */         throw new RuntimeException("setScale requires a value which is > 0.");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  939 */     this.insideDisplay.paintLock = true;
/*      */ 
/*  942 */     Rectangle r = this.port.getViewRect();
/*      */ 
/*  945 */     double centerx = r.x + r.width / 2.0D;
/*  946 */     double centery = r.y + r.height / 2.0D;
/*  947 */     centerx *= this.scale / oldScale;
/*  948 */     centery *= this.scale / oldScale;
/*  949 */     Point topleft = new Point((int)(centerx - r.width / 2.0D), (int)(centery - r.height / 2.0D));
/*  950 */     if (topleft.x < 0) topleft.x = 0;
/*  951 */     if (topleft.y < 0) topleft.y = 0;
/*      */ 
/*  954 */     if (SwingUtilities.isEventDispatchThread()) {
/*  955 */       this.port.setView(this.insideDisplay);
/*      */     }
/*      */     else {
/*  958 */       SwingUtilities.invokeLater(new Runnable() { public void run() { Display2D.this.port.setView(Display2D.this.insideDisplay); }
/*      */ 
/*      */       });
/*      */     }
/*  962 */     this.optionPane.xOffsetField.setValue(this.insideDisplay.xOffset * this.scale);
/*  963 */     this.optionPane.yOffsetField.setValue(this.insideDisplay.yOffset * this.scale);
/*      */ 
/*  966 */     this.insideDisplay.paintLock = false;
/*      */ 
/*  968 */     this.port.setViewPosition(topleft);
/*  969 */     repaint();
/*      */   }
/*      */ 
/*      */   public double getScale()
/*      */   {
/*  974 */     synchronized (this.scaleLock) { return this.scale; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public boolean isClipping()
/*      */   {
/*  988 */     return this.clipping;
/*      */   }
/*  990 */   public void setClipping(boolean val) { this.clipping = val; }
/*      */ 
/*      */ 
/*      */   public void setBackdrop(Paint c)
/*      */   {
/* 1000 */     this.backdrop = c;
/*      */   }
/*      */   public Paint getBackdrop() {
/* 1003 */     return this.backdrop;
/*      */   }
/*      */ 
/*      */   void loadScrollValues()
/*      */   {
/* 1030 */     this.port.setScrollMode(2);
/*      */ 
/* 1034 */     JScrollBar horizontal = this.display.getHorizontalScrollBar();
/* 1035 */     this.horizontalCurrent = horizontal.getValue();
/* 1036 */     horizontal.setValue(2147483647);
/* 1037 */     this.horizontalMaximum = horizontal.getValue();
/* 1038 */     horizontal.setValue(-2147483648);
/* 1039 */     this.horizontalMinimum = horizontal.getValue();
/* 1040 */     horizontal.setValue(this.horizontalCurrent);
/*      */ 
/* 1042 */     JScrollBar vertical = this.display.getVerticalScrollBar();
/* 1043 */     this.verticalCurrent = vertical.getValue();
/* 1044 */     vertical.setValue(2147483647);
/* 1045 */     this.verticalMaximum = vertical.getValue();
/* 1046 */     vertical.setValue(-2147483648);
/* 1047 */     this.verticalMinimum = vertical.getValue();
/* 1048 */     vertical.setValue(this.verticalCurrent);
/*      */   }
/*      */ 
/*      */   void loadScrollValuesHack()
/*      */   {
/* 1054 */     repaint();
/*      */ 
/* 1056 */     SwingUtilities.invokeLater(new Runnable() { public void run() { Display2D.this.port.setScrollMode(1); }
/*      */ 
/*      */     });
/*      */   }
/*      */ 
/*      */   public Double2D getScrollPosition()
/*      */   {
/* 1063 */     synchronized (this.scrollLock)
/*      */     {
/* 1065 */       loadScrollValues();
/* 1066 */       loadScrollValuesHack();
/*      */ 
/* 1068 */       return new Double2D(this.horizontalMaximum - this.horizontalMinimum <= 0.0D ? 0.0D : (this.horizontalCurrent - this.horizontalMinimum) / (this.horizontalMaximum - this.horizontalMinimum), this.verticalMaximum - this.verticalMinimum <= 0.0D ? 0.0D : (this.verticalCurrent - this.verticalMinimum) / (this.verticalMaximum - this.verticalMinimum));
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setScrollPosition(Double2D vals)
/*      */   {
/* 1080 */     setScrollPosition(vals.x, vals.y);
/*      */   }
/*      */ 
/*      */   public void setScrollPosition(double x, double y)
/*      */   {
/* 1085 */     synchronized (this.scrollLock)
/*      */     {
/* 1087 */       if ((x < 0.0D) || (x > 1.0D) || (y < 0.0D) || (y > 1.0D)) {
/* 1088 */         throw new RuntimeException("X or Y value out of bounds.  Must be >= 0.0 and <= 1.0.");
/*      */       }
/* 1090 */       loadScrollValues();
/* 1091 */       int h = (int)(this.horizontalMinimum + x * (this.horizontalMaximum - this.horizontalMinimum));
/* 1092 */       int v = (int)(this.verticalMinimum + y * (this.verticalMaximum - this.verticalMinimum));
/*      */ 
/* 1096 */       JScrollBar horizontal = this.display.getHorizontalScrollBar();
/* 1097 */       horizontal.setValue(h);
/* 1098 */       JScrollBar vertical = this.display.getVerticalScrollBar();
/* 1099 */       vertical.setValue(v);
/*      */ 
/* 1101 */       loadScrollValuesHack();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setOffset(double x, double y)
/*      */   {
/* 1111 */     this.insideDisplay.xOffset = x;
/* 1112 */     this.insideDisplay.yOffset = y;
/* 1113 */     repaint();
/*      */   }
/*      */ 
/*      */   public void setOffset(Point2D.Double d)
/*      */   {
/* 1119 */     setOffset(d.getX(), d.getY());
/*      */   }
/*      */ 
/*      */   public Point2D.Double getOffset()
/*      */   {
/* 1125 */     return new Point2D.Double(this.insideDisplay.xOffset, this.insideDisplay.yOffset);
/*      */   }
/*      */ 
/*      */   protected void finalize()
/*      */     throws Throwable
/*      */   {
/* 1134 */     super.finalize();
/* 1135 */     quit();
/*      */   }
/*      */ 
/*      */   public void quit()
/*      */   {
/* 1142 */     if (this.stopper != null) this.stopper.stop();
/* 1143 */     this.stopper = null;
/* 1144 */     stopMovie();
/*      */   }
/*      */ 
/*      */   public void reset()
/*      */   {
/* 1151 */     if (this.stopper != null) this.stopper.stop(); try {
/* 1152 */       this.stopper = this.simulation.scheduleRepeatingImmediatelyAfter(this);
/*      */     } catch (IllegalArgumentException e) {
/*      */     }
/* 1155 */     clearSelections();
/*      */   }
/*      */ 
/*      */   public void attach(FieldPortrayal2D portrayal, String name)
/*      */   {
/* 1165 */     attach(portrayal, name, true);
/*      */   }
/*      */ 
/*      */   public void attach(FieldPortrayal2D portrayal, String name, Rectangle2D.Double bounds)
/*      */   {
/* 1174 */     attach(portrayal, name, bounds, true);
/*      */   }
/*      */ 
/*      */   public void attach(FieldPortrayal2D portrayal, String name, boolean visible)
/*      */   {
/* 1186 */     attach(portrayal, name, 0.0D, 0.0D, visible);
/*      */   }
/*      */ 
/*      */   public void attach(FieldPortrayal2D portrayal, String name, double x, double y, boolean visible)
/*      */   {
/* 1200 */     attach(portrayal, name, new Rectangle2D.Double(x, y, this.insideDisplay.width, this.insideDisplay.height), visible);
/*      */   }
/*      */ 
/*      */   public void attach(FieldPortrayal2D portrayal, String name, Rectangle2D.Double bounds, boolean visible)
/*      */   {
/* 1213 */     FieldPortrayal2DHolder p = new FieldPortrayal2DHolder(portrayal, name, bounds, visible);
/* 1214 */     this.portrayals.add(p);
/* 1215 */     this.popup.add(p.menuItem);
/*      */   }
/*      */ 
/*      */   public void attach(final Inspector inspector, final String name)
/*      */   {
/* 1222 */     JMenuItem consoleMenu = new JMenuItem("Show " + name);
/* 1223 */     this.popup.add(consoleMenu);
/* 1224 */     consoleMenu.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 1228 */         Bag inspectors = new Bag();
/* 1229 */         inspectors.add(inspector);
/* 1230 */         Bag names = new Bag();
/* 1231 */         names.add(name);
/* 1232 */         Display2D.this.simulation.controller.setInspectors(inspectors, names);
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   void createConsoleMenu()
/*      */   {
/* 1239 */     if ((this.simulation != null) && (this.simulation.controller != null) && ((this.simulation.controller instanceof Console)))
/*      */     {
/* 1242 */       final Console c = (Console)this.simulation.controller;
/* 1243 */       JMenuItem consoleMenu = new JMenuItem("Show Console");
/* 1244 */       this.popup.add(consoleMenu);
/* 1245 */       consoleMenu.addActionListener(new ActionListener()
/*      */       {
/*      */         public void actionPerformed(ActionEvent e)
/*      */         {
/* 1249 */           c.setVisible(true);
/*      */         }
/*      */       });
/*      */     }
/* 1253 */     this.popup.addSeparator();
/*      */   }
/*      */ 
/*      */   public ArrayList detachAll()
/*      */   {
/* 1259 */     ArrayList old = this.portrayals;
/* 1260 */     this.popup.removeAll();
/* 1261 */     createConsoleMenu();
/* 1262 */     this.portrayals = new ArrayList();
/* 1263 */     return old;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Display2D(double width, double height, GUIState simulation, long interval)
/*      */   {
/* 1274 */     this(width, height, simulation);
/*      */   }
/*      */ 
/*      */   public Display2D(double width, double height, GUIState simulation)
/*      */   {
/* 1282 */     this.simulation = simulation;
/*      */ 
/* 1284 */     reset();
/*      */ 
/* 1287 */     this.insideDisplay = new InnerDisplay2D(width, height);
/* 1288 */     this.display = new JScrollPane(this.insideDisplay, 22, 32);
/*      */ 
/* 1291 */     this.display.setMinimumSize(new Dimension(0, 0));
/* 1292 */     this.display.setBorder(null);
/* 1293 */     this.display.getHorizontalScrollBar().setBorder(null);
/* 1294 */     this.display.getVerticalScrollBar().setBorder(null);
/* 1295 */     this.port = this.display.getViewport();
/* 1296 */     this.insideDisplay.setViewRect(this.port.getViewRect());
/* 1297 */     this.insideDisplay.setOpaque(true);
/*      */ 
/* 1300 */     this.insideDisplay.setBackground(UIManager.getColor("Panel.background"));
/* 1301 */     this.display.setBackground(UIManager.getColor("Panel.background"));
/* 1302 */     this.port.setBackground(UIManager.getColor("Panel.background"));
/*      */ 
/* 1305 */     this.header = new Box(0)
/*      */     {
/*      */       public Dimension getPreferredSize()
/*      */       {
/* 1309 */         Dimension d = super.getPreferredSize();
/* 1310 */         d.width = 0;
/* 1311 */         return d;
/*      */       }
/*      */     };
/* 1316 */     this.layersbutton = new JToggleButton(LAYERS_ICON);
/* 1317 */     this.layersbutton.setPressedIcon(LAYERS_ICON_P);
/* 1318 */     this.layersbutton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/* 1319 */     this.layersbutton.setBorderPainted(false);
/* 1320 */     this.layersbutton.setContentAreaFilled(false);
/* 1321 */     this.layersbutton.setToolTipText("Show and hide different layers");
/*      */ 
/* 1323 */     this.header.add(this.layersbutton);
/* 1324 */     this.popup = new JPopupMenu();
/* 1325 */     this.popup.setLightWeightPopupEnabled(false);
/*      */ 
/* 1328 */     this.layersbutton.addMouseListener(new MouseAdapter()
/*      */     {
/*      */       public void mousePressed(MouseEvent e)
/*      */       {
/* 1332 */         Display2D.this.popup.show(e.getComponent(), 0, Display2D.this.layersbutton.getSize().height);
/*      */       }
/*      */ 
/*      */       public void mouseReleased(MouseEvent e)
/*      */       {
/* 1338 */         Display2D.this.layersbutton.setSelected(false);
/*      */       }
/*      */     });
/* 1344 */     this.refreshbutton = new JToggleButton(REFRESH_ICON);
/* 1345 */     this.refreshbutton.setPressedIcon(REFRESH_ICON_P);
/* 1346 */     this.refreshbutton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/* 1347 */     this.refreshbutton.setBorderPainted(false);
/* 1348 */     this.refreshbutton.setContentAreaFilled(false);
/* 1349 */     this.refreshbutton.setToolTipText("Change How and When the Display Redraws Itself");
/*      */ 
/* 1351 */     this.header.add(this.refreshbutton);
/* 1352 */     this.refreshPopup = new JPopupMenu();
/* 1353 */     this.refreshPopup.setLightWeightPopupEnabled(false);
/*      */ 
/* 1356 */     this.refreshbutton.addMouseListener(new MouseAdapter()
/*      */     {
/*      */       public void mousePressed(MouseEvent e)
/*      */       {
/* 1360 */         Display2D.this.rebuildRefreshPopup();
/* 1361 */         Display2D.this.refreshPopup.show(e.getComponent(), 0, Display2D.this.refreshbutton.getSize().height);
/*      */       }
/*      */ 
/*      */       public void mouseReleased(MouseEvent e)
/*      */       {
/* 1368 */         Display2D.this.refreshbutton.setSelected(false);
/* 1369 */         Display2D.this.rebuildRefreshPopup();
/*      */       }
/*      */     });
/* 1379 */     this.insideDisplay.addMouseListener(new MouseAdapter()
/*      */     {
/*      */       public void mouseClicked(MouseEvent e)
/*      */       {
/* 1383 */         if (Display2D.this.handleMouseEvent(e)) { Display2D.this.repaint(); return;
/*      */         }
/*      */ 
/* 1387 */         int modifiers = e.getModifiers();
/* 1388 */         if ((modifiers & 0x10) == 16)
/*      */         {
/* 1390 */           Point point = e.getPoint();
/* 1391 */           if (e.getClickCount() == 2) {
/* 1392 */             Display2D.this.createInspectors(new Rectangle2D.Double(point.x, point.y, 1.0D, 1.0D), Display2D.this.simulation);
/*      */           }
/* 1394 */           if ((e.getClickCount() == 1) || (e.getClickCount() == 2))
/* 1395 */             Display2D.this.performSelection(new Rectangle2D.Double(point.x, point.y, 1.0D, 1.0D));
/* 1396 */           Display2D.this.repaint();
/*      */         }
/*      */       }
/*      */ 
/*      */       public void mouseExited(MouseEvent e)
/*      */       {
/* 1404 */         Display2D.this.insideDisplay.lastToolTipEvent = null;
/* 1405 */         if (Display2D.this.handleMouseEvent(e)) { Display2D.this.repaint(); return; }
/*      */       }
/*      */ 
/*      */       public void mouseEntered(MouseEvent e)
/*      */       {
/* 1410 */         if (Display2D.this.handleMouseEvent(e)) { Display2D.this.repaint(); return; }
/*      */       }
/*      */ 
/*      */       public void mousePressed(MouseEvent e)
/*      */       {
/* 1415 */         if (Display2D.this.handleMouseEvent(e)) { Display2D.this.repaint(); return; }
/*      */       }
/*      */ 
/*      */       public void mouseReleased(MouseEvent e)
/*      */       {
/* 1420 */         if (Display2D.this.handleMouseEvent(e)) { Display2D.this.repaint(); return;
/*      */         }
/*      */       }
/*      */     });
/* 1424 */     this.insideDisplay.addMouseMotionListener(new MouseMotionAdapter()
/*      */     {
/*      */       public void mouseDragged(MouseEvent e)
/*      */       {
/* 1428 */         if (Display2D.this.handleMouseEvent(e)) { Display2D.this.repaint(); return; }
/*      */       }
/*      */ 
/*      */       public void mouseMoved(MouseEvent e)
/*      */       {
/* 1433 */         if (Display2D.this.handleMouseEvent(e)) { Display2D.this.repaint(); return;
/*      */         }
/*      */       }
/*      */     });
/* 1450 */     this.insideDisplay.setToolTipText("Display");
/*      */ 
/* 1453 */     this.movieButton = new JButton(MOVIE_OFF_ICON);
/* 1454 */     this.movieButton.setPressedIcon(MOVIE_OFF_ICON_P);
/* 1455 */     this.movieButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/* 1456 */     this.movieButton.setBorderPainted(false);
/* 1457 */     this.movieButton.setContentAreaFilled(false);
/* 1458 */     this.movieButton.setToolTipText("Create a Quicktime movie");
/* 1459 */     this.movieButton.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 1463 */         if (Display2D.this.movieMaker == null)
/*      */         {
/* 1465 */           Display2D.this.startMovie();
/*      */         }
/*      */         else
/*      */         {
/* 1469 */           Display2D.this.stopMovie();
/*      */         }
/*      */       }
/*      */     });
/* 1473 */     this.header.add(this.movieButton);
/*      */ 
/* 1476 */     this.snapshotButton = new JButton(CAMERA_ICON);
/* 1477 */     this.snapshotButton.setPressedIcon(CAMERA_ICON_P);
/* 1478 */     this.snapshotButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/* 1479 */     this.snapshotButton.setBorderPainted(false);
/* 1480 */     this.snapshotButton.setContentAreaFilled(false);
/* 1481 */     this.snapshotButton.setToolTipText("Create a snapshot (as a PNG or PDF file)");
/* 1482 */     this.snapshotButton.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 1486 */         Display2D.this.takeSnapshot();
/*      */       }
/*      */     });
/* 1489 */     this.header.add(this.snapshotButton);
/*      */ 
/* 1493 */     this.optionButton = new JButton(OPTIONS_ICON);
/* 1494 */     this.optionButton.setPressedIcon(OPTIONS_ICON_P);
/* 1495 */     this.optionButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/* 1496 */     this.optionButton.setBorderPainted(false);
/* 1497 */     this.optionButton.setContentAreaFilled(false);
/* 1498 */     this.optionButton.setToolTipText("Show the Option Pane");
/* 1499 */     this.optionButton.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 1503 */         Display2D.this.optionPane.setTitle(Display2D.this.getFrame().getTitle() + " Options");
/* 1504 */         Display2D.this.optionPane.pack();
/* 1505 */         Display2D.this.optionPane.setVisible(true);
/*      */       }
/*      */     });
/* 1508 */     this.header.add(this.optionButton);
/*      */ 
/* 1511 */     this.scaleField = new NumberTextField("  Scale: ", 1.0D, true)
/*      */     {
/*      */       public double newValue(double newValue)
/*      */       {
/* 1515 */         if (newValue <= 0.0D) newValue = this.currentValue;
/* 1516 */         Display2D.this.setScale(newValue);
/* 1517 */         return newValue;
/*      */       }
/*      */     };
/* 1521 */     this.scaleField.setToolTipText("Zoom in and out");
/* 1522 */     this.scaleField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
/* 1523 */     this.header.add(this.scaleField);
/*      */ 
/* 1525 */     this.skipFrame = new JFrame();
/* 1526 */     rebuildSkipFrame();
/* 1527 */     this.skipFrame.pack();
/*      */ 
/* 1532 */     setLayout(new BorderLayout());
/* 1533 */     add(this.header, "North");
/* 1534 */     add(this.display, "Center");
/*      */ 
/* 1536 */     createConsoleMenu();
/*      */ 
/* 1539 */     this.optionPane.resetToPreferences();
/*      */   }
/*      */ 
/*      */   public Bag[] objectsHitBy(Rectangle2D.Double rect)
/*      */   {
/* 1553 */     Bag[] hitObjs = new Bag[this.portrayals.size()];
/* 1554 */     Iterator iter = this.portrayals.iterator();
/* 1555 */     int x = 0;
/*      */ 
/* 1557 */     while (iter.hasNext())
/*      */     {
/* 1559 */       hitObjs[x] = new Bag();
/* 1560 */       FieldPortrayal2DHolder p = (FieldPortrayal2DHolder)iter.next();
/* 1561 */       if (p.visible)
/*      */       {
/* 1563 */         p.portrayal.hitObjects(getDrawInfo2D(p, rect), hitObjs[x]);
/*      */       }
/* 1565 */       x++;
/*      */     }
/* 1567 */     return hitObjs;
/*      */   }
/*      */ 
/*      */   public Bag[] objectsHitBy(Point2D point)
/*      */   {
/* 1580 */     return objectsHitBy(new Rectangle2D.Double(point.getX(), point.getY(), 1.0D, 1.0D));
/*      */   }
/*      */ 
/*      */   public DrawInfo2D getDrawInfo2D(FieldPortrayal2D portrayal, Point2D point)
/*      */   {
/* 1586 */     return getDrawInfo2D(portrayal, new Rectangle2D.Double(point.getX(), point.getY(), 1.0D, 1.0D));
/*      */   }
/*      */ 
/*      */   public DrawInfo2D getDrawInfo2D(FieldPortrayal2D portrayal, Rectangle2D clip)
/*      */   {
/* 1592 */     Iterator iter = this.portrayals.iterator();
/* 1593 */     while (iter.hasNext())
/*      */     {
/* 1595 */       FieldPortrayal2DHolder p = (FieldPortrayal2DHolder)iter.next();
/* 1596 */       if (p.portrayal == portrayal) return getDrawInfo2D(p, clip);
/*      */     }
/* 1598 */     return null;
/*      */   }
/*      */ 
/*      */   DrawInfo2D getDrawInfo2D(FieldPortrayal2DHolder holder, Rectangle2D clip)
/*      */   {
/* 1604 */     if (holder == null) return null;
/*      */ 
/* 1606 */     double scale = getScale();
/*      */ 
/* 1608 */     int origindx = 0;
/* 1609 */     int origindy = 0;
/*      */ 
/* 1612 */     Rectangle2D fullComponent = this.insideDisplay.getViewRect();
/* 1613 */     if (fullComponent.getWidth() > this.insideDisplay.width * scale)
/* 1614 */       origindx = (int)((fullComponent.getWidth() - this.insideDisplay.width * scale) / 2.0D);
/* 1615 */     if (fullComponent.getHeight() > this.insideDisplay.height * scale) {
/* 1616 */       origindy = (int)((fullComponent.getHeight() - this.insideDisplay.height * scale) / 2.0D);
/*      */     }
/*      */ 
/* 1619 */     origindx += (int)(this.insideDisplay.xOffset * scale);
/* 1620 */     origindy += (int)(this.insideDisplay.yOffset * scale);
/*      */ 
/* 1622 */     Rectangle2D.Double region = new Rectangle2D.Double((int)(holder.bounds.x * scale) + origindx, (int)(holder.bounds.y * scale) + origindy, (int)(holder.bounds.width * scale), (int)(holder.bounds.height * scale));
/*      */ 
/* 1628 */     DrawInfo2D d2d = new DrawInfo2D(this.simulation, holder.portrayal, region, clip);
/* 1629 */     d2d.gui = this.simulation;
/* 1630 */     d2d.precise = ((this.forcePrecise) || (this.precise));
/* 1631 */     return d2d;
/*      */   }
/*      */ 
/*      */   public LocationWrapper[] getSelectedWrappers()
/*      */   {
/* 1644 */     return (LocationWrapper[])this.selectedWrappers.toArray(new LocationWrapper[this.selectedWrappers.size()]);
/*      */   }
/*      */ 
/*      */   public void performSelection(LocationWrapper wrapper)
/*      */   {
/* 1650 */     Bag b = new Bag();
/* 1651 */     b.add(wrapper);
/* 1652 */     performSelection(b);
/*      */   }
/*      */ 
/*      */   public void clearSelections()
/*      */   {
/* 1657 */     for (int x = 0; x < this.selectedWrappers.size(); x++)
/*      */     {
/* 1659 */       LocationWrapper wrapper = (LocationWrapper)this.selectedWrappers.get(x);
/* 1660 */       wrapper.getFieldPortrayal().setSelected(wrapper, false);
/*      */     }
/* 1662 */     this.selectedWrappers.clear();
/*      */   }
/*      */ 
/*      */   public void performSelection(Point2D point)
/*      */   {
/* 1667 */     performSelection(new Rectangle2D.Double(point.getX(), point.getY(), 1.0D, 1.0D));
/*      */   }
/*      */ 
/*      */   public void performSelection(Rectangle2D.Double rect)
/*      */   {
/* 1673 */     Bag[] hitObjects = objectsHitBy(rect);
/* 1674 */     Bag collection = new Bag();
/* 1675 */     for (int x = 0; x < hitObjects.length; x++)
/* 1676 */       collection.addAll(hitObjects[x]);
/* 1677 */     performSelection(collection);
/*      */   }
/*      */ 
/*      */   public int getSelectionMode()
/*      */   {
/* 1686 */     return this.selectionMode;
/*      */   }
/* 1688 */   public void setSelectionMode(int val) { this.selectionMode = val; }
/*      */ 
/*      */   public void performSelection(Bag locationWrappers)
/*      */   {
/* 1692 */     clearSelections();
/*      */ 
/* 1694 */     if (locationWrappers == null) return;
/*      */ 
/* 1697 */     if (this.selectionMode == 1)
/*      */     {
/* 1699 */       if (locationWrappers.size() > 0)
/*      */       {
/* 1701 */         LocationWrapper wrapper = (LocationWrapper)locationWrappers.get(locationWrappers.size() - 1);
/* 1702 */         wrapper.getFieldPortrayal().setSelected(wrapper, true);
/* 1703 */         this.selectedWrappers.add(wrapper);
/*      */       }
/*      */     }
/*      */     else {
/* 1707 */       for (int x = 0; x < locationWrappers.size(); x++)
/*      */       {
/* 1709 */         LocationWrapper wrapper = (LocationWrapper)locationWrappers.get(x);
/* 1710 */         wrapper.getFieldPortrayal().setSelected(wrapper, true);
/* 1711 */         this.selectedWrappers.add(wrapper);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1716 */     this.simulation.controller.refresh();
/*      */   }
/*      */ 
/*      */   public void createInspector(LocationWrapper wrapper, GUIState simulation)
/*      */   {
/* 1722 */     Bag b = new Bag();
/* 1723 */     b.add(wrapper);
/* 1724 */     createInspectors(b, simulation);
/*      */   }
/*      */ 
/*      */   public void createInspectors(Point2D point, GUIState simulation)
/*      */   {
/* 1731 */     createInspectors(new Rectangle2D.Double(point.getX(), point.getY(), 1.0D, 1.0D), simulation);
/*      */   }
/*      */ 
/*      */   public void createInspectors(Bag locationWrappers, GUIState simulation)
/*      */   {
/* 1738 */     Bag inspectors = new Bag();
/* 1739 */     Bag names = new Bag();
/*      */ 
/* 1741 */     for (int i = 0; i < locationWrappers.size(); i++)
/*      */     {
/* 1743 */       LocationWrapper wrapper = (LocationWrapper)locationWrappers.get(i);
/* 1744 */       inspectors.add(wrapper.fieldPortrayal.getInspector(wrapper, simulation));
/* 1745 */       names.add(wrapper.fieldPortrayal.getName(wrapper));
/*      */     }
/* 1747 */     simulation.controller.setInspectors(inspectors, names);
/*      */   }
/*      */ 
/*      */   public void createInspectors(Rectangle2D.Double rect, GUIState simulation)
/*      */   {
/* 1754 */     Bag wrappers = new Bag();
/* 1755 */     Bag[] hitObjects = objectsHitBy(rect);
/* 1756 */     for (int x = 0; x < hitObjects.length; x++)
/*      */     {
/* 1758 */       FieldPortrayal2DHolder p = (FieldPortrayal2DHolder)this.portrayals.get(x);
/* 1759 */       for (int i = 0; i < hitObjects[x].numObjs; i++)
/*      */       {
/* 1761 */         LocationWrapper wrapper = (LocationWrapper)hitObjects[x].objs[i];
/*      */ 
/* 1763 */         wrapper.fieldPortrayal = p.portrayal;
/* 1764 */         wrappers.add(wrapper);
/*      */       }
/*      */     }
/* 1767 */     createInspectors(wrappers, simulation);
/*      */   }
/*      */ 
/*      */   public JFrame createFrame()
/*      */   {
/* 1779 */     JFrame frame = new JFrame()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 1783 */         Display2D.this.quit();
/* 1784 */         super.dispose();
/*      */       }
/*      */     };
/* 1788 */     frame.setResizable(true);
/*      */ 
/* 1791 */     frame.addComponentListener(new ComponentAdapter()
/*      */     {
/*      */       public void componentResized(ComponentEvent e)
/*      */       {
/* 1796 */         Utilities.doEnsuredRepaint(Display2D.this.header);
/* 1797 */         Display2D.this.display.getHorizontalScrollBar().setUnitIncrement(Display2D.this.display.getViewport().getWidth() / 10);
/* 1798 */         Display2D.this.display.getVerticalScrollBar().setUnitIncrement(Display2D.this.display.getViewport().getHeight() / 10);
/*      */       }
/*      */     });
/* 1802 */     frame.getContentPane().setLayout(new BorderLayout());
/* 1803 */     frame.getContentPane().add(this, "Center");
/*      */ 
/* 1805 */     frame.setTitle(GUIState.getName(this.simulation.getClass()) + " Display");
/* 1806 */     frame.pack();
/* 1807 */     return frame;
/*      */   }
/*      */ 
/*      */   public Frame getFrame()
/*      */   {
/* 1814 */     Component c = this;
/* 1815 */     while (c.getParent() != null)
/* 1816 */       c = c.getParent();
/* 1817 */     return (Frame)c;
/*      */   }
/*      */ 
/*      */   public void takeSnapshot(File file, int type)
/*      */     throws IOException
/*      */   {
/* 1833 */     if (type == 2)
/*      */     {
/* 1835 */       Graphics g = this.insideDisplay.getGraphics();
/* 1836 */       BufferedImage img = this.insideDisplay.paint(g, true, false);
/* 1837 */       g.dispose();
/* 1838 */       OutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
/* 1839 */       PNGEncoder tmpEncoder = new PNGEncoder(img, false, 0, 9);
/* 1840 */       stream.write(tmpEncoder.pngEncode());
/* 1841 */       stream.close();
/*      */     }
/*      */     else
/*      */     {
/* 1845 */       boolean oldprecise = this.forcePrecise;
/* 1846 */       this.forcePrecise = true;
/* 1847 */       PDFEncoder.generatePDF(this.port, file);
/* 1848 */       this.forcePrecise = oldprecise;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void takeSnapshot()
/*      */   {
/* 1854 */     synchronized (this.simulation.state.schedule)
/*      */     {
/* 1856 */       if (SimApplet.isApplet)
/*      */       {
/* 1858 */         Object[] options = { "Oops" };
/* 1859 */         JOptionPane.showOptionDialog(this, "You cannot save snapshots from an applet.", "MASON Applet Restriction", 0, 0, null, options, options[0]);
/*      */ 
/* 1864 */         return;
/*      */       }
/*      */ 
/* 1868 */       boolean havePDF = false;
/*      */ 
/* 1871 */       Graphics g = this.insideDisplay.getGraphics();
/* 1872 */       BufferedImage img = this.insideDisplay.paint(g, true, false);
/*      */       try
/*      */       {
/* 1875 */         this.sacrificialObj = Class.forName("com.lowagie.text.Cell", true, Thread.currentThread().getContextClassLoader()).newInstance();
/*      */ 
/* 1877 */         havePDF = true;
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */ 
/* 1884 */       g.dispose();
/*      */ 
/* 1887 */       int CANCEL_BUTTON = 0;
/* 1888 */       int PNG_BUTTON = 1;
/* 1889 */       int PDF_BUTTON = 2;
/* 1890 */       int PDF_NO_BACKDROP_BUTTON = 3;
/* 1891 */       int result = 1;
/* 1892 */       if (havePDF)
/*      */       {
/* 1894 */         Object[] options = { "Cancel", "Save to PNG Bitmap", "Save to PDF", "Save to PDF with no Backdrop" };
/* 1895 */         result = JOptionPane.showOptionDialog(getFrame(), "Save window snapshot to what kind of file format?", "Save Format", 0, 3, null, options, options[0]);
/*      */       }
/*      */ 
/* 1900 */       if (result == 1)
/*      */       {
/* 1903 */         FileDialog fd = new FileDialog(getFrame(), "Save Snapshot as 24-bit PNG...", 1);
/*      */ 
/* 1905 */         fd.setFile("Untitled.png");
/* 1906 */         fd.setVisible(true);
/* 1907 */         if (fd.getFile() != null)
/*      */           try {
/* 1909 */             OutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fd.getDirectory(), Utilities.ensureFileEndsWith(fd.getFile(), ".png"))));
/*      */ 
/* 1911 */             PNGEncoder tmpEncoder = new PNGEncoder(img, false, 0, 9);
/*      */ 
/* 1913 */             stream.write(tmpEncoder.pngEncode());
/* 1914 */             stream.close();
/*      */           } catch (Exception e) {
/* 1916 */             e.printStackTrace();
/*      */           }
/* 1918 */       } else if ((result == 2) || (result == 3))
/*      */       {
/* 1920 */         FileDialog fd = new FileDialog(getFrame(), "Save Snapshot as PDF...", 1);
/*      */ 
/* 1922 */         fd.setFile("Untitled.pdf");
/* 1923 */         fd.setVisible(true);
/* 1924 */         if (fd.getFile() != null)
/*      */           try {
/* 1926 */             boolean oldprecise = this.forcePrecise;
/* 1927 */             this.forcePrecise = true;
/* 1928 */             Paint b = getBackdrop();
/* 1929 */             if (result == 3)
/* 1930 */               setBackdrop(null);
/* 1931 */             PDFEncoder.generatePDF(this.port, new File(fd.getDirectory(), Utilities.ensureFileEndsWith(fd.getFile(), ".pdf")));
/* 1932 */             this.forcePrecise = oldprecise;
/* 1933 */             if (result == 3)
/* 1934 */               setBackdrop(b);
/*      */           } catch (Exception e) {
/* 1936 */             e.printStackTrace();
/*      */           }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void startMovie()
/*      */   {
/* 1957 */     synchronized (this.simulation.state.schedule)
/*      */     {
/* 1960 */       if (SimApplet.isApplet)
/*      */       {
/* 1962 */         Object[] options = { "Oops" };
/* 1963 */         JOptionPane.showOptionDialog(this, "You cannot create movies from an applet.", "MASON Applet Restriction", 0, 0, null, options, options[0]);
/*      */ 
/* 1968 */         return;
/*      */       }
/*      */ 
/* 1971 */       if (this.movieMaker != null) return;
/* 1972 */       this.movieMaker = new MovieMaker(getFrame());
/* 1973 */       Graphics g = this.insideDisplay.getGraphics();
/* 1974 */       BufferedImage typicalImage = this.insideDisplay.paint(g, true, false);
/* 1975 */       g.dispose();
/*      */ 
/* 1977 */       if (!this.movieMaker.start(typicalImage)) {
/* 1978 */         this.movieMaker = null;
/*      */       }
/*      */       else {
/* 1981 */         this.movieButton.setIcon(MOVIE_ON_ICON);
/* 1982 */         this.movieButton.setPressedIcon(MOVIE_ON_ICON_P);
/*      */ 
/* 1985 */         Console console = (Console)this.simulation.controller;
/* 1986 */         if (console.getPlayState() == 0) {
/* 1987 */           console.pressPause();
/*      */         }
/* 1989 */         this.lastEncodedSteps = -1L;
/*      */ 
/* 1992 */         this.insideDisplay.paintToMovie(null);
/*      */ 
/* 1995 */         this.simulation.scheduleAtEnd(new Steppable() {
/*      */           public void step(SimState state) {
/* 1997 */             Display2D.this.stopMovie();
/*      */           }
/*      */         });
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void stopMovie()
/*      */   {
/* 2013 */     synchronized (this.simulation.state.schedule)
/*      */     {
/* 2015 */       if (this.movieMaker == null) return;
/* 2016 */       if (!this.movieMaker.stop())
/*      */       {
/* 2018 */         Object[] options = { "Drat" };
/* 2019 */         JOptionPane.showOptionDialog(this, "Your movie did not write to disk\ndue to a spurious JMF movie generation bug.", "JMF Movie Generation Bug", 0, 2, null, options, options[0]);
/*      */       }
/*      */ 
/* 2025 */       this.movieMaker = null;
/* 2026 */       if (this.movieButton != null)
/*      */       {
/* 2028 */         this.movieButton.setIcon(MOVIE_OFF_ICON);
/* 2029 */         this.movieButton.setPressedIcon(MOVIE_OFF_ICON_P);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void requestUpdate()
/*      */   {
/* 2053 */     synchronized (this.updateLock)
/*      */     {
/* 2055 */       this.updateOnce = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean shouldUpdate()
/*      */   {
/* 2062 */     boolean val = false;
/* 2063 */     boolean up = false;
/* 2064 */     synchronized (this.updateLock) { up = this.updateOnce; }
/*      */ 
/* 2066 */     if (up) {
/* 2067 */       val = true;
/* 2068 */     } else if (this.updateRule == 3) {
/* 2069 */       val = true;
/* 2070 */     } else if (this.updateRule == 0)
/*      */     {
/* 2072 */       long step = this.simulation.state.schedule.getSteps();
/* 2073 */       val = (this.lastStep < 0L) || (this.stepInterval == 0L) || (step - this.lastStep >= this.stepInterval) || (this.lastStep % this.stepInterval >= step % this.stepInterval);
/*      */ 
/* 2075 */       if (val) this.lastStep = step;
/*      */     }
/* 2077 */     else if (this.updateRule == 2)
/*      */     {
/* 2079 */       long wall = System.currentTimeMillis();
/* 2080 */       val = (this.lastWall == 0L) || (this.wallInterval == 0L) || (wall - this.lastWall >= this.wallInterval) || (this.lastWall % this.wallInterval >= wall % this.wallInterval);
/*      */ 
/* 2082 */       if (val) this.lastWall = wall;
/*      */     }
/* 2084 */     else if (this.updateRule == 1)
/*      */     {
/* 2086 */       double time = this.simulation.state.schedule.getTime();
/* 2087 */       val = (this.lastTime == 0.0D) || (this.timeInterval == 0.0D) || (time - this.lastTime >= this.timeInterval) || (this.lastTime % this.timeInterval >= time % this.timeInterval);
/*      */ 
/* 2089 */       if (val) this.lastTime = time;
/*      */ 
/*      */     }
/*      */ 
/* 2094 */     synchronized (this.updateLock) { this.updateOnce = false; }
/*      */ 
/* 2096 */     return val;
/*      */   }
/*      */ 
/*      */   public void setMouseChangesOffset(boolean val)
/*      */   {
/* 2109 */     this.mouseChangesOffset = val;
/*      */   }
/*      */ 
/*      */   public boolean getMouseChangesOffset() {
/* 2113 */     return this.mouseChangesOffset;
/*      */   }
/*      */ 
/*      */   public void setMovingWrapper(LocationWrapper wrapper)
/*      */   {
/* 2120 */     this.movingWrapper = wrapper;
/*      */   }
/*      */ 
/*      */   public boolean handleMouseEvent(MouseEvent event)
/*      */   {
/* 2128 */     if ((this.mouseChangesOffset) && ((event.getModifiers() & 0x4) == 4))
/*      */     {
/* 2130 */       if ((event.getID() == 500) && (event.getClickCount() >= 2))
/*      */       {
/* 2133 */         this.insideDisplay.xOffset = 0.0D;
/* 2134 */         this.insideDisplay.yOffset = 0.0D;
/* 2135 */         setScale(1.0D);
/* 2136 */         repaint();
/*      */       }
/* 2138 */       else if ((event.getID() == 500) && (event.getClickCount() == 1))
/*      */       {
/* 2141 */         MouseEvent m = SwingUtilities.convertMouseEvent(this.insideDisplay, event, this.port);
/* 2142 */         this.insideDisplay.xOffset -= m.getX() - this.port.getWidth() / 2;
/* 2143 */         this.insideDisplay.yOffset -= m.getY() - this.port.getHeight() / 2;
/* 2144 */         setScale(getScale() * 2.0D);
/* 2145 */         repaint();
/*      */       } else {
/* 2147 */         if (event.getID() == 501)
/*      */         {
/* 2149 */           setCursor(this.OPEN_HAND_CURSOR_C);
/* 2150 */           this.openHand = true;
/* 2151 */           event = SwingUtilities.convertMouseEvent(this, event, this.display);
/* 2152 */           this.originalXOffset = this.insideDisplay.xOffset;
/* 2153 */           this.originalYOffset = this.insideDisplay.yOffset;
/* 2154 */           this.originalMousePoint = event.getPoint();
/* 2155 */           this.originalText = this.scaleField.getText();
/* 2156 */           return true;
/*      */         }
/* 2158 */         if (event.getID() == 502)
/*      */         {
/* 2160 */           setCursor(new Cursor(13));
/* 2161 */           this.openHand = false;
/*      */ 
/* 2163 */           this.scaleField.setText(this.originalText);
/* 2164 */           this.originalMousePoint = null;
/* 2165 */           return true;
/*      */         }
/* 2167 */         if (event.getID() == 506)
/*      */         {
/* 2169 */           if (this.openHand)
/*      */           {
/* 2171 */             setCursor(this.CLOSED_HAND_CURSOR_C);
/* 2172 */             this.openHand = false;
/*      */           }
/*      */ 
/* 2175 */           event = SwingUtilities.convertMouseEvent(this, event, this.display);
/* 2176 */           this.insideDisplay.xOffset = (this.originalXOffset - (this.originalMousePoint.x - event.getX()) / this.scale);
/* 2177 */           this.insideDisplay.yOffset = (this.originalYOffset - (this.originalMousePoint.y - event.getY()) / this.scale);
/* 2178 */           this.optionPane.xOffsetField.setValue(this.insideDisplay.xOffset);
/* 2179 */           this.optionPane.yOffsetField.setValue(this.insideDisplay.yOffset);
/* 2180 */           this.scaleField.setText("Translating Origin to (" + this.insideDisplay.xOffset + ", " + this.insideDisplay.yOffset + ")");
/* 2181 */           repaint();
/* 2182 */           return true;
/*      */         }
/*      */       }
/*      */     }
/* 2186 */     Point2D.Double p = new Point2D.Double(event.getX(), event.getY());
/*      */ 
/* 2189 */     if (this.movingWrapper != null)
/*      */     {
/* 2191 */       FieldPortrayal2D f = (FieldPortrayal2D)this.movingWrapper.getFieldPortrayal();
/* 2192 */       Object obj = this.movingWrapper.getObject();
/* 2193 */       SimplePortrayal2D portrayal = (SimplePortrayal2D)f.getPortrayalForObject(obj);
/* 2194 */       if (portrayal.handleMouseEvent(this.simulation, this, this.movingWrapper, event, getDrawInfo2D(f, p), 0))
/*      */       {
/* 2196 */         this.simulation.controller.refresh();
/* 2197 */         return true;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2203 */     for (int x = 0; x < this.selectedWrappers.size(); x++)
/*      */     {
/* 2205 */       LocationWrapper wrapper = (LocationWrapper)this.selectedWrappers.get(x);
/* 2206 */       FieldPortrayal2D f = (FieldPortrayal2D)wrapper.getFieldPortrayal();
/* 2207 */       Object obj = wrapper.getObject();
/* 2208 */       SimplePortrayal2D portrayal = (SimplePortrayal2D)f.getPortrayalForObject(obj);
/* 2209 */       if (portrayal.handleMouseEvent(this.simulation, this, wrapper, event, getDrawInfo2D(f, p), 0))
/*      */       {
/* 2211 */         this.simulation.controller.refresh();
/* 2212 */         return true;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2219 */     Bag[] hitObjects = objectsHitBy(p);
/* 2220 */     for (int x = hitObjects.length - 1; x >= 0; x--) {
/* 2221 */       for (int i = hitObjects[x].numObjs - 1; i >= 0; i--)
/*      */       {
/* 2223 */         LocationWrapper wrapper = (LocationWrapper)hitObjects[x].objs[i];
/* 2224 */         FieldPortrayal2D f = (FieldPortrayal2D)wrapper.getFieldPortrayal();
/* 2225 */         Object obj = wrapper.getObject();
/* 2226 */         SimplePortrayal2D portrayal = (SimplePortrayal2D)f.getPortrayalForObject(obj);
/* 2227 */         if (portrayal.handleMouseEvent(this.simulation, this, wrapper, event, getDrawInfo2D(f, p), 1))
/*      */         {
/* 2229 */           this.simulation.controller.refresh();
/* 2230 */           return true;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2236 */     return false;
/*      */   }
/*      */ 
/*      */   protected void rebuildSkipFrame()
/*      */   {
/* 2241 */     this.skipFrame.getContentPane().removeAll();
/* 2242 */     this.skipFrame.getContentPane().invalidate();
/* 2243 */     this.skipFrame.getContentPane().repaint();
/* 2244 */     this.skipFrame.getContentPane().setLayout(new BorderLayout());
/*      */ 
/* 2246 */     JPanel skipHeader = new JPanel();
/* 2247 */     skipHeader.setLayout(new BorderLayout());
/* 2248 */     this.skipFrame.add(skipHeader, "Center");
/*      */ 
/* 2251 */     this.skipBox = new JComboBox(REDRAW_OPTIONS);
/* 2252 */     this.skipBox.setSelectedIndex(this.updateRule);
/* 2253 */     ActionListener skipListener = new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2257 */         Display2D.this.updateRule = Display2D.this.skipBox.getSelectedIndex();
/* 2258 */         if ((Display2D.this.updateRule == 3) || (Display2D.this.updateRule == 4))
/*      */         {
/* 2260 */           Display2D.this.skipField.getField().setText("");
/* 2261 */           Display2D.this.skipField.setEnabled(false);
/*      */         }
/* 2263 */         else if (Display2D.this.updateRule == 0)
/*      */         {
/* 2265 */           Display2D.this.skipField.setValue(Display2D.this.stepInterval);
/* 2266 */           Display2D.this.skipField.setEnabled(true);
/*      */         }
/* 2268 */         else if (Display2D.this.updateRule == 1)
/*      */         {
/* 2270 */           Display2D.this.skipField.setValue(Display2D.this.timeInterval);
/* 2271 */           Display2D.this.skipField.setEnabled(true);
/*      */         }
/*      */         else
/*      */         {
/* 2275 */           Display2D.this.skipField.setValue(Display2D.this.wallInterval / 1000L);
/* 2276 */           Display2D.this.skipField.setEnabled(true);
/*      */         }
/*      */       }
/*      */     };
/* 2280 */     this.skipBox.addActionListener(skipListener);
/*      */ 
/* 2283 */     this.skipBox.setRenderer(new DefaultListCellRenderer()
/*      */     {
/*      */       public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
/*      */       {
/* 2288 */         JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
/* 2289 */         label.setHorizontalAlignment(4);
/* 2290 */         return label;
/*      */       }
/*      */     });
/* 2294 */     skipHeader.add(this.skipBox, "West");
/*      */ 
/* 2297 */     this.skipField = new NumberTextField(null, 1.0D, false)
/*      */     {
/*      */       public double newValue(double newValue)
/*      */       {
/*      */         double val;
/*      */         double val;
/* 2302 */         if ((Display2D.this.updateRule == 3) || (Display2D.this.updateRule == 4))
/*      */         {
/* 2304 */           val = 0.0D;
/*      */         }
/* 2306 */         else if (Display2D.this.updateRule == 0)
/*      */         {
/* 2308 */           double val = ()newValue;
/* 2309 */           if (val < 1.0D) val = Display2D.this.stepInterval;
/* 2310 */           Display2D.this.stepInterval = (()val);
/*      */         }
/* 2312 */         else if (Display2D.this.updateRule == 2)
/*      */         {
/* 2314 */           double val = newValue;
/* 2315 */           if (val < 0.0D) val = Display2D.this.wallInterval / 1000L;
/* 2316 */           Display2D.this.wallInterval = (()(newValue * 1000.0D));
/*      */         }
/*      */         else
/*      */         {
/* 2320 */           val = newValue;
/* 2321 */           if (val < 0.0D) val = Display2D.this.timeInterval;
/* 2322 */           Display2D.this.timeInterval = val;
/*      */         }
/*      */ 
/* 2326 */         Display2D.this.reset();
/*      */ 
/* 2328 */         return val;
/*      */       }
/*      */     };
/* 2331 */     this.skipField.setToolTipText("Specify the interval between screen updates");
/* 2332 */     this.skipField.getField().setColumns(10);
/* 2333 */     skipHeader.add(this.skipField, "Center");
/* 2334 */     skipHeader.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/* 2335 */     skipListener.actionPerformed(null);
/*      */   }
/*      */ 
/*      */   protected void rebuildRefreshPopup()
/*      */   {
/* 2340 */     this.refreshPopup.removeAll();
/* 2341 */     String s = "";
/* 2342 */     switch (this.updateRule)
/*      */     {
/*      */     case 0:
/* 2345 */       s = "Currently redrawing every " + this.stepInterval + " model iterations";
/*      */ 
/* 2347 */       break;
/*      */     case 1:
/* 2349 */       s = "Currently redrawing every " + this.timeInterval + " units of model time";
/*      */ 
/* 2351 */       break;
/*      */     case 2:
/* 2353 */       s = "Currently redrawing every " + this.wallInterval / 1000.0D + " seconds of real time";
/*      */ 
/* 2355 */       break;
/*      */     case 3:
/* 2357 */       s = "Currently redrawing each model iteration";
/* 2358 */       break;
/*      */     case 4:
/* 2360 */       s = "Currently never redrawing except when the window is redrawn";
/* 2361 */       break;
/*      */     default:
/* 2363 */       throw new RuntimeException("default case should never occur");
/*      */     }
/* 2365 */     JMenuItem m = new JMenuItem(s);
/* 2366 */     m.setEnabled(false);
/* 2367 */     this.refreshPopup.add(m);
/*      */ 
/* 2369 */     this.refreshPopup.addSeparator();
/*      */ 
/* 2371 */     m = new JMenuItem("Always Redraw");
/* 2372 */     this.refreshPopup.add(m);
/* 2373 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2377 */         Display2D.this.updateRule = 3;
/* 2378 */         Display2D.this.rebuildSkipFrame();
/*      */       }
/*      */     });
/* 2382 */     m = new JMenuItem("Never Redraw");
/* 2383 */     this.refreshPopup.add(m);
/* 2384 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2388 */         Display2D.this.updateRule = 4;
/* 2389 */         Display2D.this.rebuildSkipFrame();
/*      */       }
/*      */     });
/* 2393 */     m = new JMenuItem("Redraw once every 2 iterations");
/* 2394 */     this.refreshPopup.add(m);
/* 2395 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2399 */         Display2D.this.updateRule = 0;
/* 2400 */         Display2D.this.stepInterval = 2L;
/* 2401 */         Display2D.this.rebuildSkipFrame();
/*      */       }
/*      */     });
/* 2405 */     m = new JMenuItem("Redraw once every 4 iterations");
/* 2406 */     this.refreshPopup.add(m);
/* 2407 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2411 */         Display2D.this.updateRule = 0;
/* 2412 */         Display2D.this.stepInterval = 4L;
/* 2413 */         Display2D.this.rebuildSkipFrame();
/*      */       }
/*      */     });
/* 2417 */     m = new JMenuItem("Redraw once every 8 iterations");
/* 2418 */     this.refreshPopup.add(m);
/* 2419 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2423 */         Display2D.this.updateRule = 0;
/* 2424 */         Display2D.this.stepInterval = 8L;
/* 2425 */         Display2D.this.rebuildSkipFrame();
/*      */       }
/*      */     });
/* 2429 */     m = new JMenuItem("Redraw once every 16 iterations");
/* 2430 */     this.refreshPopup.add(m);
/* 2431 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2435 */         Display2D.this.updateRule = 0;
/* 2436 */         Display2D.this.stepInterval = 16L;
/* 2437 */         Display2D.this.rebuildSkipFrame();
/*      */       }
/*      */     });
/* 2441 */     m = new JMenuItem("Redraw once every 32 iterations");
/* 2442 */     this.refreshPopup.add(m);
/* 2443 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2447 */         Display2D.this.updateRule = 0;
/* 2448 */         Display2D.this.stepInterval = 16L;
/* 2449 */         Display2D.this.rebuildSkipFrame();
/*      */       }
/*      */     });
/* 2453 */     this.refreshPopup.addSeparator();
/*      */ 
/* 2455 */     m = new JMenuItem("Redraw once at the next step");
/* 2456 */     this.refreshPopup.add(m);
/* 2457 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2461 */         Display2D.this.requestUpdate();
/*      */       }
/*      */     });
/* 2466 */     m = new JMenuItem("More Options...");
/* 2467 */     this.refreshPopup.add(m);
/* 2468 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2472 */         Display2D.this.skipFrame.setTitle(Display2D.this.getFrame().getTitle() + " Options");
/* 2473 */         Display2D.this.skipFrame.setVisible(true);
/*      */       }
/*      */     });
/* 2477 */     this.refreshPopup.revalidate();
/*      */   }
/*      */ 
/*      */   public void step(SimState state)
/*      */   {
/* 2485 */     if (shouldUpdate())
/*      */     {
/* 2506 */       if ((this.insideDisplay.isShowing()) && ((getFrame().getExtendedState() & 0x1) == 0))
/*      */       {
/* 2509 */         this.insideDisplay.repaint();
/*      */       }
/* 2511 */       else if (this.movieMaker != null)
/*      */       {
/* 2513 */         this.insideDisplay.paintToMovie(null);
/*      */       }
/* 2515 */       this.insideDisplay.updateToolTips();
/*      */     }
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  803 */     ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
/*      */     try
/*      */     {
/*  811 */       System.setProperty("Quaqua.TabbedPane.design", "auto");
/*  812 */       System.setProperty("Quaqua.visualMargin", "1,1,1,1");
/*  813 */       UIManager.put("Panel.opaque", Boolean.TRUE);
/*  814 */       UIManager.setLookAndFeel((String)Class.forName("ch.randelshofer.quaqua.QuaquaManager", true, Thread.currentThread().getContextClassLoader()).getMethod("getLookAndFeelClassName", (Class[])null).invoke(null, (Object[])null));
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  824 */       System.setProperty("com.apple.hwaccel", "true");
/*  825 */       System.setProperty("apple.awt.graphics.UseQuartz", "true");
/*      */ 
/*  828 */       System.setProperty("apple.awt.showGrowBox", "true");
/*      */ 
/*  835 */       System.setProperty("com.apple.macos.use-file-dialog-packages", "true");
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   class FieldPortrayal2DHolder
/*      */   {
/*      */     Rectangle2D.Double bounds;
/*      */     FieldPortrayal2D portrayal;
/*      */     String name;
/*      */     JCheckBoxMenuItem menuItem;
/*      */     boolean visible;
/*      */ 
/*      */     public String toString()
/*      */     {
/*  732 */       return this.name;
/*      */     }
/*      */ 
/*      */     FieldPortrayal2DHolder(FieldPortrayal2D p, String n, Rectangle2D.Double bounds, boolean visible) {
/*  736 */       this.bounds = bounds;
/*  737 */       this.portrayal = p;
/*  738 */       this.name = n;
/*  739 */       this.visible = visible;
/*  740 */       this.menuItem = new JCheckBoxMenuItem(this.name, visible);
/*  741 */       this.menuItem.addActionListener(new ActionListener()
/*      */       {
/*      */         public void actionPerformed(ActionEvent e)
/*      */         {
/*  745 */           Display2D.FieldPortrayal2DHolder.this.visible = Display2D.FieldPortrayal2DHolder.this.menuItem.isSelected();
/*  746 */           Display2D.this.repaint();
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */ 
/*      */   public class InnerDisplay2D extends JComponent
/*      */   {
/*  313 */     BufferedImage buffer = null;
/*      */     public double width;
/*      */     public double height;
/*      */     public double xOffset;
/*      */     public double yOffset;
/*      */     public RenderingHints unbufferedHints;
/*      */     public RenderingHints bufferedHints;
/*  429 */     WeakReference toolTip = new WeakReference(null);
/*      */ 
/*  437 */     protected MouseEvent lastToolTipEvent = null;
/*      */ 
/*  450 */     String lastToolTipText = null;
/*      */     static final int MAX_TOOLTIP_LINES = 10;
/*  500 */     boolean paintLock = false;
/*      */ 
/*  692 */     Rectangle viewRect = new Rectangle(0, 0, 0, 0);
/*      */ 
/*  695 */     final Object viewRectLock = new Object();
/*      */ 
/*      */     /** @deprecated */
/*      */     public void removeListeners()
/*      */     {
/*  327 */       MouseListener[] mls = (MouseListener[])getListeners(MouseListener.class);
/*  328 */       for (int x = 0; x < mls.length; x++)
/*  329 */         removeMouseListener(mls[x]);
/*  330 */       MouseMotionListener[] mmls = (MouseMotionListener[])getListeners(MouseMotionListener.class);
/*  331 */       for (int x = 0; x < mmls.length; x++)
/*  332 */         removeMouseMotionListener(mmls[x]);
/*  333 */       KeyListener[] kls = (KeyListener[])getListeners(KeyListener.class);
/*  334 */       for (int x = 0; x < kls.length; x++)
/*  335 */         removeKeyListener(kls[x]);
/*      */     }
/*      */ 
/*      */     InnerDisplay2D(double width, double height)
/*      */     {
/*  341 */       this.width = width;
/*  342 */       this.height = height;
/*  343 */       setupHints(false, false, false);
/*      */     }
/*      */ 
/*      */     public Dimension getPreferredSize()
/*      */     {
/*  348 */       return new Dimension((int)(this.width * Display2D.this.getScale()), (int)(this.height * Display2D.this.getScale()));
/*      */     }
/*      */ 
/*      */     public Dimension getMinimumSize() {
/*  352 */       return getPreferredSize();
/*      */     }
/*      */ 
/*      */     public void paintToMovie(Graphics g)
/*      */     {
/*  369 */       synchronized (Display2D.this.simulation.state.schedule)
/*      */       {
/*  372 */         long steps = Display2D.this.simulation.state.schedule.getSteps();
/*  373 */         if ((steps > Display2D.this.lastEncodedSteps) && (Display2D.this.shouldUpdate()) && (Display2D.this.simulation.state.schedule.getTime() < (1.0D / 0.0D)))
/*      */         {
/*  377 */           Display2D.this.movieMaker.add(paint(g, true, false));
/*  378 */           Display2D.this.lastEncodedSteps = steps;
/*      */         } else {
/*  380 */           paint(g, false, false);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public void setupHints(boolean antialias, boolean niceAlphaInterpolation, boolean niceInterpolation)
/*      */     {
/*  393 */       this.unbufferedHints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
/*  394 */       this.unbufferedHints.put(RenderingHints.KEY_INTERPOLATION, niceInterpolation ? RenderingHints.VALUE_INTERPOLATION_BILINEAR : RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
/*      */ 
/*  401 */       this.unbufferedHints.put(RenderingHints.KEY_ANTIALIASING, antialias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
/*      */ 
/*  404 */       this.unbufferedHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, antialias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
/*      */ 
/*  407 */       this.unbufferedHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, niceAlphaInterpolation ? RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY : RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
/*      */ 
/*  412 */       this.bufferedHints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
/*  413 */       this.bufferedHints.put(RenderingHints.KEY_INTERPOLATION, niceInterpolation ? RenderingHints.VALUE_INTERPOLATION_BILINEAR : RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
/*      */ 
/*  417 */       this.bufferedHints.put(RenderingHints.KEY_ANTIALIASING, antialias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
/*      */ 
/*  420 */       this.bufferedHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, antialias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
/*      */ 
/*  423 */       this.bufferedHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, niceAlphaInterpolation ? RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY : RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
/*      */     }
/*      */ 
/*      */     public JToolTip createToolTip()
/*      */     {
/*  432 */       JToolTip tip = super.createToolTip();
/*  433 */       this.toolTip = new WeakReference(tip);
/*  434 */       return tip;
/*      */     }
/*      */ 
/*      */     public String getToolTipText(MouseEvent event)
/*      */     {
/*  441 */       if (Display2D.this.useTooltips)
/*      */       {
/*  443 */         this.lastToolTipEvent = event;
/*  444 */         Point point = event.getPoint();
/*  445 */         return createToolTipText(new Rectangle2D.Double(point.x, point.y, 1.0D, 1.0D), Display2D.this.simulation);
/*      */       }
/*  447 */       return null;
/*      */     }
/*      */ 
/*      */     public void updateToolTips()
/*      */     {
/*  455 */       if ((Display2D.this.useTooltips) && (this.lastToolTipEvent != null))
/*      */       {
/*  457 */         final String s = getToolTipText(this.lastToolTipEvent);
/*  458 */         if ((s == null) || (!s.equals(this.lastToolTipText)))
/*      */         {
/*  461 */           SwingUtilities.invokeLater(new Runnable()
/*      */           {
/*      */             public void run()
/*      */             {
/*  465 */               Display2D.InnerDisplay2D.this.setToolTipText(Display2D.InnerDisplay2D.this.lastToolTipText = s);
/*  466 */               JToolTip tip = (JToolTip)Display2D.InnerDisplay2D.this.toolTip.get();
/*  467 */               if ((tip != null) && (tip.getComponent() == Display2D.InnerDisplay2D.this)) tip.setTipText(s);
/*      */             }
/*      */           });
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public String createToolTipText(Rectangle2D.Double rect, GUIState simulation)
/*      */     {
/*  478 */       String s = "<html><font face=\"" + getFont().getFamily() + "\" size=\"-1\">";
/*      */ 
/*  480 */       Bag[] hitObjects = Display2D.this.objectsHitBy(rect);
/*  481 */       int count = 0;
/*  482 */       for (int x = 0; x < hitObjects.length; x++)
/*      */       {
/*  484 */         Display2D.FieldPortrayal2DHolder p = (Display2D.FieldPortrayal2DHolder)Display2D.this.portrayals.get(x);
/*  485 */         for (int i = 0; i < hitObjects[x].numObjs; i++)
/*      */         {
/*  487 */           if (count > 0) s = s + "<br>";
/*  488 */           if (count >= 10) return s + "...<i>etc.</i></font></html>";
/*  489 */           count++;
/*  490 */           String status = p.portrayal.getStatus((LocationWrapper)hitObjects[x].objs[i]);
/*  491 */           if (status != null) s = s + status;
/*      */         }
/*      */       }
/*  494 */       if (count == 0) return null;
/*  495 */       s = s + "</font></html>";
/*  496 */       return s;
/*      */     }
/*      */ 
/*      */     public synchronized void paintComponent(Graphics g)
/*      */     {
/*  507 */       if (this.paintLock) return;
/*      */ 
/*  509 */       if (SwingUtilities.isEventDispatchThread())
/*  510 */         setViewRect(Display2D.this.port.getViewRect());
/*  511 */       paintComponent(g, false);
/*      */     }
/*      */ 
/*      */     /** @deprecated */
/*      */     public void paintComponent(Graphics g, boolean buffer)
/*      */     {
/*  520 */       synchronized (Display2D.this.simulation.state.schedule)
/*      */       {
/*  522 */         if (Display2D.this.movieMaker != null)
/*  523 */           Display2D.this.insideDisplay.paintToMovie(g);
/*  524 */         else paint(g, buffer, true);
/*      */       }
/*      */     }
/*      */ 
/*      */     Rectangle2D computeClip()
/*      */     {
/*  539 */       Rectangle2D clip = getViewRect();
/*      */ 
/*  542 */       double scale = Display2D.this.getScale();
/*  543 */       int origindx = 0;
/*  544 */       int origindy = 0;
/*  545 */       if (clip.getWidth() > this.width * scale)
/*  546 */         origindx = (int)((clip.getWidth() - this.width * scale) / 2.0D);
/*  547 */       if (clip.getHeight() > this.height * scale) {
/*  548 */         origindy = (int)((clip.getHeight() - this.height * scale) / 2.0D);
/*      */       }
/*  550 */       if (Display2D.this.isClipping())
/*      */       {
/*  552 */         Dimension s = getPreferredSize();
/*  553 */         clip = clip.createIntersection(new Rectangle2D.Double(origindx, origindy, s.width, s.height));
/*      */       }
/*  555 */       return clip;
/*      */     }
/*      */ 
/*      */     public BufferedImage paint(Graphics graphics, boolean buffered, boolean shared)
/*      */     {
/*  589 */       synchronized (Display2D.this.simulation.state.schedule)
/*      */       {
/*  591 */         BufferedImage result = null;
/*  592 */         Rectangle2D clip = computeClip();
/*  593 */         if (!buffered)
/*  594 */           paintUnbuffered((Graphics2D)graphics, clip);
/*      */         else
/*  596 */           result = paintBuffered((Graphics2D)graphics, clip);
/*  597 */         if (!shared) this.buffer = null;
/*  598 */         if (result != null) result.flush();
/*  599 */         return result;
/*      */       }
/*      */     }
/*      */ 
/*      */     BufferedImage paintBuffered(Graphics2D graphics, Rectangle2D clip)
/*      */     {
/*  609 */       double ww = clip.getWidth();
/*  610 */       double hh = clip.getHeight();
/*  611 */       if ((this.buffer == null) || (this.buffer.getWidth(null) != ww) || (this.buffer.getHeight(null) != hh))
/*      */       {
/*  616 */         this.buffer = getGraphicsConfiguration().createCompatibleImage((int)ww, (int)hh);
/*      */       }
/*      */ 
/*  620 */       Graphics2D g = (Graphics2D)this.buffer.getGraphics();
/*  621 */       g.setColor(Display2D.this.port.getBackground());
/*  622 */       g.fillRect(0, 0, this.buffer.getWidth(null), this.buffer.getHeight(null));
/*  623 */       g.translate(-(int)clip.getX(), -(int)clip.getY());
/*  624 */       paintUnbuffered(g, clip);
/*  625 */       g.dispose();
/*      */ 
/*  628 */       if (graphics != null)
/*      */       {
/*  630 */         graphics.setRenderingHints(this.bufferedHints);
/*  631 */         graphics.drawImage(this.buffer, (int)clip.getX(), (int)clip.getY(), null);
/*      */       }
/*  633 */       return this.buffer;
/*      */     }
/*      */ 
/*      */     void paintUnbuffered(Graphics2D g, Rectangle2D clip)
/*      */     {
/*  643 */       if (g == null) return;
/*      */ 
/*  645 */       g.setRenderingHints(this.unbufferedHints);
/*      */ 
/*  648 */       if (Display2D.this.isClipping()) g.setClip(clip);
/*  649 */       if ((clip.getWidth() != 0.0D) && (clip.getHeight() != 0.0D))
/*      */       {
/*  651 */         if (Display2D.this.backdrop != null)
/*      */         {
/*  653 */           g.setPaint(Display2D.this.backdrop);
/*  654 */           g.fillRect((int)clip.getX(), (int)clip.getY(), (int)clip.getWidth(), (int)clip.getHeight());
/*      */         }
/*      */ 
/*  657 */         Iterator iter = Display2D.this.portrayals.iterator();
/*  658 */         while (iter.hasNext())
/*      */         {
/*  660 */           Display2D.FieldPortrayal2DHolder p = (Display2D.FieldPortrayal2DHolder)iter.next();
/*  661 */           if (p.visible)
/*      */           {
/*  664 */             int buf = p.portrayal.getBuffering();
/*  665 */             p.portrayal.setBuffering(Display2D.this.optionPane.buffering);
/*      */ 
/*  668 */             g.setClip(g.getClip());
/*      */ 
/*  671 */             p.portrayal.draw(p.portrayal.getField(), g, Display2D.this.getDrawInfo2D(p, clip));
/*      */ 
/*  675 */             p.portrayal.setBuffering(buf);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     Rectangle getViewRect()
/*      */     {
/*  700 */       synchronized (this.viewRectLock)
/*      */       {
/*  702 */         return new Rectangle(this.viewRect);
/*      */       }
/*      */     }
/*      */ 
/*      */     void setViewRect(Rectangle rect)
/*      */     {
/*  709 */       synchronized (this.viewRectLock)
/*      */       {
/*  711 */         this.viewRect = new Rectangle(rect);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public class OptionPane extends JFrame
/*      */   {
/*      */     int buffering;
/*   85 */     JRadioButton useNoBuffer = new JRadioButton("By Drawing Separate Rectangles");
/*   86 */     JRadioButton useBuffer = new JRadioButton("Using a Stretched Image");
/*   87 */     JRadioButton useDefault = new JRadioButton("Let the Program Decide How");
/*   88 */     ButtonGroup usageGroup = new ButtonGroup();
/*      */ 
/*   90 */     JCheckBox antialias = new JCheckBox("Antialias Graphics");
/*   91 */     JCheckBox alphaInterpolation = new JCheckBox("Better Transparency");
/*   92 */     JCheckBox interpolation = new JCheckBox("Bilinear Interpolation of Images");
/*   93 */     JCheckBox tooltips = new JCheckBox("Tool Tips");
/*   94 */     JCheckBox preciseDrawing = new JCheckBox("Precise Drawing");
/*      */ 
/*   96 */     JButton systemPreferences = new JButton("MASON");
/*   97 */     JButton appPreferences = new JButton("Simulation");
/*      */ 
/*   99 */     NumberTextField xOffsetField = new NumberTextField(0.0D, 1.0D, 50.0D)
/*      */     {
/*      */       public double newValue(double val)
/*      */       {
/*  103 */         double scale = Display2D.this.getScale();
/*  104 */         Display2D.this.insideDisplay.xOffset = (val / scale);
/*  105 */         Display2D.this.repaint();
/*  106 */         return Display2D.this.insideDisplay.xOffset * scale;
/*      */       }
/*   99 */     };
/*      */ 
/*  110 */     NumberTextField yOffsetField = new NumberTextField(0.0D, 1.0D, 50.0D)
/*      */     {
/*      */       public double newValue(double val)
/*      */       {
/*  114 */         double scale = Display2D.this.getScale();
/*  115 */         Display2D.this.insideDisplay.yOffset = (val / scale);
/*  116 */         Display2D.this.repaint();
/*  117 */         return Display2D.this.insideDisplay.yOffset * scale; }  } ;
/*      */ 
/*  121 */     ActionListener listener = null;
/*      */     static final String DRAW_GRIDS_KEY = "Draw Grids";
/*      */     static final String X_OFFSET_KEY = "X Offset";
/*      */     static final String Y_OFFSET_KEY = "Y Offset";
/*      */     static final String ANTIALIAS_KEY = "Antialias";
/*      */     static final String BETTER_TRANSPARENCY_KEY = "Better Transparency";
/*      */     static final String INTERPOLATION_KEY = "Bilinear Interpolation";
/*      */     static final String TOOLTIPS_KEY = "Tool Tips";
/*      */     static final String PRECISE_KEY = "Precise Drawing";
/*      */ 
/*  125 */     OptionPane(String title) { super();
/*  126 */       this.useDefault.setSelected(true);
/*  127 */       this.useNoBuffer.setToolTipText("<html>When not using transparency on Windows/XWindows,<br>this method is often (but not always) faster</html>");
/*  128 */       this.usageGroup.add(this.useNoBuffer);
/*  129 */       this.usageGroup.add(this.useBuffer);
/*  130 */       this.useBuffer.setToolTipText("<html>When using transparency, <i>or</i> when on a Mac,<br>this method is usually faster, but may require more<br>memory (especially on Windows/XWindows) --<br>increasing heap size can help performance.</html>");
/*  131 */       this.usageGroup.add(this.useDefault);
/*      */ 
/*  133 */       JPanel p2 = new JPanel();
/*      */ 
/*  135 */       Box b = new Box(1);
/*  136 */       b.add(this.useNoBuffer);
/*  137 */       b.add(this.useBuffer);
/*  138 */       b.add(this.useDefault);
/*  139 */       JPanel p = new JPanel();
/*  140 */       p.setLayout(new BorderLayout());
/*  141 */       p.setBorder(new TitledBorder("Draw Grids of Rectangles..."));
/*  142 */       p.add(b, "Center");
/*  143 */       p2.setLayout(new BorderLayout());
/*  144 */       p2.add(p, "North");
/*      */ 
/*  146 */       LabelledList l = new LabelledList("Origin Offset in Pixels");
/*  147 */       l.addLabelled("X Offset", this.xOffsetField);
/*  148 */       l.addLabelled("Y Offset", this.yOffsetField);
/*  149 */       p2.add(l, "Center");
/*  150 */       getContentPane().add(p2, "North");
/*  151 */       String text = "<html>Sets the offset of the origin of the display.  This is <b>independent of the scrollbars</b>.<br><br>If the simulation has enabled it, you can also change the offset by dragging with the<br>right mouse button down (or on the Mac, a two finger tap-drag or Command-drag).<br><br>Additionally, you can reset the origin to (0,0) with a right-mouse button double-click.</html>";
/*      */ 
/*  155 */       l.setToolTipText(text);
/*  156 */       this.xOffsetField.setToolTipText(text);
/*  157 */       this.yOffsetField.setToolTipText(text);
/*      */ 
/*  159 */       b = new Box(1);
/*  160 */       b.add(this.antialias);
/*  161 */       b.add(this.interpolation);
/*  162 */       b.add(this.alphaInterpolation);
/*  163 */       b.add(this.tooltips);
/*  164 */       b.add(this.preciseDrawing);
/*  165 */       p = new JPanel();
/*  166 */       p.setLayout(new BorderLayout());
/*  167 */       p.setBorder(new TitledBorder("Graphics Features"));
/*  168 */       p.add(b, "Center");
/*  169 */       getContentPane().add(p, "Center");
/*      */ 
/*  171 */       this.listener = new ActionListener()
/*      */       {
/*      */         public void actionPerformed(ActionEvent e)
/*      */         {
/*  175 */           Display2D.this.useTooltips = Display2D.OptionPane.this.tooltips.isSelected();
/*  176 */           Display2D.this.precise = Display2D.OptionPane.this.preciseDrawing.isSelected();
/*  177 */           if (Display2D.OptionPane.this.useDefault.isSelected())
/*  178 */             Display2D.OptionPane.this.buffering = 0;
/*  179 */           else if (Display2D.OptionPane.this.useBuffer.isSelected())
/*  180 */             Display2D.OptionPane.this.buffering = 1;
/*  181 */           else Display2D.OptionPane.this.buffering = 2;
/*  182 */           Display2D.this.insideDisplay.setupHints(Display2D.OptionPane.this.antialias.isSelected(), Display2D.OptionPane.this.alphaInterpolation.isSelected(), Display2D.OptionPane.this.interpolation.isSelected());
/*  183 */           Display2D.this.repaint();
/*      */         }
/*      */       };
/*  186 */       this.useNoBuffer.addActionListener(this.listener);
/*  187 */       this.useBuffer.addActionListener(this.listener);
/*  188 */       this.useDefault.addActionListener(this.listener);
/*  189 */       this.antialias.addActionListener(this.listener);
/*  190 */       this.alphaInterpolation.addActionListener(this.listener);
/*  191 */       this.interpolation.addActionListener(this.listener);
/*  192 */       this.tooltips.addActionListener(this.listener);
/*  193 */       this.preciseDrawing.addActionListener(this.listener);
/*      */ 
/*  197 */       b = new Box(0);
/*  198 */       b.add(new JLabel(" Save as Defaults for "));
/*  199 */       b.add(this.appPreferences);
/*  200 */       b.add(this.systemPreferences);
/*  201 */       getContentPane().add(b, "South");
/*      */ 
/*  203 */       this.systemPreferences.putClientProperty("JComponent.sizeVariant", "mini");
/*  204 */       this.systemPreferences.putClientProperty("JButton.buttonType", "bevel");
/*  205 */       this.systemPreferences.addActionListener(new ActionListener()
/*      */       {
/*      */         public void actionPerformed(ActionEvent e)
/*      */         {
/*  209 */           String key = Display2D.this.getPreferencesKey();
/*  210 */           Display2D.OptionPane.this.savePreferences(Prefs.getGlobalPreferences(key));
/*      */ 
/*  213 */           Prefs.removeAppPreferences(Display2D.this.simulation, key);
/*      */         }
/*      */       });
/*  217 */       this.appPreferences.putClientProperty("JComponent.sizeVariant", "mini");
/*  218 */       this.appPreferences.putClientProperty("JButton.buttonType", "bevel");
/*  219 */       this.appPreferences.addActionListener(new ActionListener()
/*      */       {
/*      */         public void actionPerformed(ActionEvent e)
/*      */         {
/*  223 */           String key = Display2D.this.getPreferencesKey();
/*  224 */           Display2D.OptionPane.this.savePreferences(Prefs.getAppPreferences(Display2D.this.simulation, key));
/*      */         }
/*      */       });
/*  228 */       setDefaultCloseOperation(1);
/*  229 */       setResizable(false);
/*  230 */       pack();
/*      */     }
/*      */ 
/*      */     void savePreferences(Preferences prefs)
/*      */     {
/*      */       try
/*      */       {
/*  239 */         prefs.putInt("Draw Grids", this.useBuffer.isSelected() ? 1 : this.useNoBuffer.isSelected() ? 0 : 2);
/*      */ 
/*  242 */         prefs.putDouble("X Offset", this.xOffsetField.getValue());
/*  243 */         prefs.putDouble("Y Offset", this.yOffsetField.getValue());
/*  244 */         prefs.putBoolean("Antialias", this.antialias.isSelected());
/*  245 */         prefs.putBoolean("Better Transparency", this.alphaInterpolation.isSelected());
/*  246 */         prefs.putBoolean("Bilinear Interpolation", this.interpolation.isSelected());
/*  247 */         prefs.putBoolean("Tool Tips", this.tooltips.isSelected());
/*  248 */         prefs.putBoolean("Precise Drawing", this.preciseDrawing.isSelected());
/*      */ 
/*  250 */         if (!Prefs.save(prefs))
/*  251 */           Utilities.inform("Preferences Cannot be Saved", "Your Java system can't save preferences.  Perhaps this is an applet?", this);
/*      */       }
/*      */       catch (AccessControlException e)
/*      */       {
/*      */       }
/*      */     }
/*      */ 
/*      */     void resetToPreferences()
/*      */     {
/*      */       try
/*      */       {
/*  271 */         Preferences systemPrefs = Prefs.getGlobalPreferences(Display2D.this.getPreferencesKey());
/*  272 */         Preferences appPrefs = Prefs.getAppPreferences(Display2D.this.simulation, Display2D.this.getPreferencesKey());
/*  273 */         int val = appPrefs.getInt("Draw Grids", systemPrefs.getInt("Draw Grids", this.useBuffer.isSelected() ? 1 : this.useNoBuffer.isSelected() ? 0 : 2));
/*      */ 
/*  277 */         if (val == 0) this.useNoBuffer.setSelected(true);
/*  278 */         else if (val == 1) this.useBuffer.setSelected(true);
/*      */         else
/*  280 */           this.useDefault.setSelected(true);
/*  281 */         this.xOffsetField.setValue(this.xOffsetField.newValue(appPrefs.getDouble("X Offset", systemPrefs.getDouble("X Offset", 0.0D))));
/*      */ 
/*  283 */         this.yOffsetField.setValue(this.yOffsetField.newValue(appPrefs.getDouble("Y Offset", systemPrefs.getDouble("Y Offset", 0.0D))));
/*      */ 
/*  285 */         this.antialias.setSelected(appPrefs.getBoolean("Antialias", systemPrefs.getBoolean("Antialias", false)));
/*      */ 
/*  287 */         this.alphaInterpolation.setSelected(appPrefs.getBoolean("Better Transparency", systemPrefs.getBoolean("Better Transparency", false)));
/*      */ 
/*  289 */         this.interpolation.setSelected(appPrefs.getBoolean("Bilinear Interpolation", systemPrefs.getBoolean("Bilinear Interpolation", false)));
/*      */ 
/*  291 */         this.tooltips.setSelected(appPrefs.getBoolean("Tool Tips", systemPrefs.getBoolean("Tool Tips", false)));
/*      */ 
/*  293 */         this.preciseDrawing.setSelected(appPrefs.getBoolean("Precise Drawing", systemPrefs.getBoolean("Precise Drawing", false)));
/*      */ 
/*  296 */         this.listener.actionPerformed(null);
/*      */       }
/*      */       catch (AccessControlException e)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.display.Display2D
 * JD-Core Version:    0.6.2
 */