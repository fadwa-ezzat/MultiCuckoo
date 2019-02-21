/*      */ package sim.display3d;
/*      */ 
/*      */ import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
/*      */ import com.sun.j3d.utils.geometry.Sphere;
/*      */ import com.sun.j3d.utils.image.TextureLoader;
/*      */ import com.sun.j3d.utils.universe.SimpleUniverse;
/*      */ import com.sun.j3d.utils.universe.ViewingPlatform;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FileDialog;
/*      */ import java.awt.Frame;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.Image;
/*      */ import java.awt.Point;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ComponentAdapter;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.ItemEvent;
/*      */ import java.awt.event.ItemListener;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowListener;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.lang.reflect.Method;
/*      */ import java.security.AccessControlException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.BitSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.prefs.Preferences;
/*      */ import javax.media.j3d.Alpha;
/*      */ import javax.media.j3d.AmbientLight;
/*      */ import javax.media.j3d.Appearance;
/*      */ import javax.media.j3d.Background;
/*      */ import javax.media.j3d.BoundingSphere;
/*      */ import javax.media.j3d.BranchGroup;
/*      */ import javax.media.j3d.CapabilityNotSetException;
/*      */ import javax.media.j3d.Group;
/*      */ import javax.media.j3d.ImageComponent2D;
/*      */ import javax.media.j3d.Locale;
/*      */ import javax.media.j3d.PointArray;
/*      */ import javax.media.j3d.PointLight;
/*      */ import javax.media.j3d.PolygonAttributes;
/*      */ import javax.media.j3d.RotationInterpolator;
/*      */ import javax.media.j3d.Shape3D;
/*      */ import javax.media.j3d.Switch;
/*      */ import javax.media.j3d.Transform3D;
/*      */ import javax.media.j3d.TransformGroup;
/*      */ import javax.media.j3d.View;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.DefaultListCellRenderer;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JCheckBoxMenuItem;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.JRadioButton;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.JToggleButton;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.ToolTipManager;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import javax.swing.border.TitledBorder;
/*      */ import javax.vecmath.Color3f;
/*      */ import javax.vecmath.Point3d;
/*      */ import javax.vecmath.Point3f;
/*      */ import javax.vecmath.Vector3d;
/*      */ import sim.display.Console;
/*      */ import sim.display.Controller;
/*      */ import sim.display.Display2D;
/*      */ import sim.display.GUIState;
/*      */ import sim.display.Prefs;
/*      */ import sim.display.SimApplet;
/*      */ import sim.engine.Schedule;
/*      */ import sim.engine.SimState;
/*      */ import sim.engine.Steppable;
/*      */ import sim.engine.Stoppable;
/*      */ import sim.portrayal.FieldPortrayal;
/*      */ import sim.portrayal.Inspector;
/*      */ import sim.portrayal.LocationWrapper;
/*      */ import sim.portrayal3d.FieldPortrayal3D;
/*      */ import sim.portrayal3d.Portrayal3D;
/*      */ import sim.portrayal3d.simple.AxesPortrayal3D;
/*      */ import sim.util.Bag;
/*      */ import sim.util.gui.LabelledList;
/*      */ import sim.util.gui.MovieMaker;
/*      */ import sim.util.gui.NumberTextField;
/*      */ import sim.util.gui.Utilities;
/*      */ import sim.util.media.PNGEncoder;
/*      */ 
/*      */ public class Display3D extends JPanel
/*      */   implements Steppable
/*      */ {
/*  122 */   public String DEFAULT_PREFERENCES_KEY = "Display3D";
/*  123 */   String preferencesKey = this.DEFAULT_PREFERENCES_KEY;
/*      */ 
/*  137 */   ArrayList portrayals = new ArrayList();
/*      */   Stoppable stopper;
/*      */   GUIState simulation;
/*      */   public Box header;
/*      */   public JButton movieButton;
/*      */   public JButton snapshotButton;
/*      */   public JButton optionButton;
/*      */   public JPopupMenu refreshPopup;
/*      */   public JToggleButton refreshbutton;
/*      */   public NumberTextField scaleField;
/*      */   public NumberTextField skipField;
/*      */   public JComboBox skipBox;
/*      */   public JFrame skipFrame;
/*  164 */   public CapturingCanvas3D canvas = null;
/*      */ 
/*  167 */   public SimpleUniverse universe = null;
/*      */ 
/*  171 */   public BranchGroup root = null;
/*      */ 
/*  174 */   public BranchGroup viewRoot = null;
/*      */ 
/*  179 */   Switch portrayalSwitch = null;
/*  180 */   BitSet portrayalSwitchMask = null;
/*      */ 
/*  183 */   Switch auxillarySwitch = null;
/*      */ 
/*  185 */   BitSet auxillarySwitchMask = new BitSet(2);
/*      */   static final int NUM_AUXILLARY_ELEMENTS = 2;
/*      */   static final int AXES_AUX_INDEX = 0;
/*      */   static final int BACKGROUND_AUX_INDEX = 1;
/*  198 */   Switch lightSwitch = null;
/*  199 */   BitSet lightSwitchMask = new BitSet(2);
/*      */   static final int NUM_LIGHT_ELEMENTS = 2;
/*      */   static final int SPOTLIGHT_INDEX = 0;
/*      */   static final int AMBIENT_LIGHT_INDEX = 1;
/*  205 */   MovieMaker movieMaker = null;
/*      */   public JPopupMenu popup;
/*      */   public JToggleButton layersbutton;
/*  336 */   int subgraphCount = 0;
/*      */ 
/*  413 */   boolean dirty = false;
/*      */ 
/*  691 */   Appearance backdropAppearance = null;
/*  692 */   Image backdropImage = null;
/*  693 */   Color backdropColor = null;
/*      */ 
/*  816 */   static final float[] bogusPosition = { 0.0F, 0.0F, 0.0F };
/*      */   PointArray bogusMover;
/*      */   public TransformGroup globalModelTransformGroup;
/*      */   ToolTipBehavior toolTipBehavior;
/*      */   boolean usingToolTips;
/*      */   static final double DEFAULT_FIELD_OF_VIEW = 0.7853981633974483D;
/*      */   double scale;
/* 1175 */   Object scaleLock = new Object();
/*      */ 
/* 1214 */   RotationInterpolator autoSpin = null;
/* 1215 */   RotationInterpolator autoSpinBackground = null;
/*      */ 
/* 1220 */   public TransformGroup autoSpinTransformGroup = new TransformGroup();
/*      */ 
/* 1223 */   TransformGroup autoSpinBackgroundTransformGroup = new TransformGroup();
/*      */ 
/* 1225 */   OrbitBehavior mOrbitBehavior = null;
/* 1226 */   SelectionBehavior mSelectBehavior = null;
/*      */ 
/* 1231 */   boolean selectionAll = true;
/* 1232 */   boolean inspectionAll = true;
/*      */ 
/* 1249 */   protected int updateRule = 3;
/* 1250 */   protected long stepInterval = 1L;
/* 1251 */   protected double timeInterval = 0.0D;
/* 1252 */   protected long wallInterval = 0L;
/* 1253 */   long lastStep = -1L;
/* 1254 */   double lastTime = -1.0D;
/* 1255 */   long lastWall = -1L;
/* 1256 */   Object[] updateLock = new Object[0];
/* 1257 */   boolean updateOnce = false;
/*      */ 
/* 1564 */   JCheckBox orbitRotateXCheckBox = new JCheckBox("Rotate Left/Right");
/* 1565 */   JCheckBox orbitRotateYCheckBox = new JCheckBox("Up/Down");
/* 1566 */   JCheckBox orbitTranslateXCheckBox = new JCheckBox("Translate Left/Right");
/* 1567 */   JCheckBox orbitTranslateYCheckBox = new JCheckBox("Up/Down");
/* 1568 */   JCheckBox orbitZoomCheckBox = new JCheckBox("Move Towards/Away");
/* 1569 */   JCheckBox selectBehCheckBox = new JCheckBox("Select");
/* 1570 */   JRadioButton polyPoint = new JRadioButton("Vertices", false);
/* 1571 */   JRadioButton polyLine = new JRadioButton("Edges", false);
/* 1572 */   JRadioButton polyFill = new JRadioButton("Fill", true);
/* 1573 */   JRadioButton polyCullNone = new JRadioButton("Both Sides", true);
/* 1574 */   JRadioButton polyCullFront = new JRadioButton("Back Side Only", false);
/* 1575 */   JRadioButton polyCullBack = new JRadioButton("Front Side Only", false);
/*      */ 
/* 1577 */   JCheckBox showAxesCheckBox = new JCheckBox("Axes");
/* 1578 */   JCheckBox showBackgroundCheckBox = new JCheckBox("Backdrop");
/* 1579 */   JCheckBox tooltips = new JCheckBox("ToolTips");
/* 1580 */   JCheckBox showSpotlightCheckBox = new JCheckBox("Spotlight");
/* 1581 */   JCheckBox showAmbientLightCheckBox = new JCheckBox("Ambient Light");
/*      */ 
/* 1624 */   NumberTextField rotAxis_X = new NumberTextField(null, 0.0D, false)
/*      */   {
/*      */     public double newValue(double newValue)
/*      */     {
/* 1628 */       Display3D.this.autoSpin.setTransformAxis(Display3D.this.getTransformForAxis(newValue, Display3D.this.rotAxis_Y.getValue(), Display3D.this.rotAxis_Z.getValue()));
/*      */ 
/* 1630 */       Display3D.this.autoSpinBackground.setTransformAxis(Display3D.this.getTransformForAxis(newValue, Display3D.this.rotAxis_Y.getValue(), Display3D.this.rotAxis_Z.getValue()));
/* 1631 */       if ((Display3D.this.spinDuration.getValue() == 0.0D) || ((newValue == 0.0D) && (Display3D.this.rotAxis_Y.getValue() == 0.0D) && (Display3D.this.rotAxis_Z.getValue() == 0.0D)))
/*      */       {
/* 1633 */         Display3D.this.setSpinningEnabled(false);
/*      */       } else Display3D.this.setSpinningEnabled(true);
/* 1635 */       return newValue;
/*      */     }
/* 1624 */   };
/*      */ 
/* 1638 */   NumberTextField rotAxis_Y = new NumberTextField(null, 0.0D, false)
/*      */   {
/*      */     public double newValue(double newValue)
/*      */     {
/* 1642 */       Display3D.this.autoSpin.setTransformAxis(Display3D.this.getTransformForAxis(Display3D.this.rotAxis_X.getValue(), newValue, Display3D.this.rotAxis_Z.getValue()));
/*      */ 
/* 1644 */       Display3D.this.autoSpinBackground.setTransformAxis(Display3D.this.getTransformForAxis(Display3D.this.rotAxis_X.getValue(), newValue, Display3D.this.rotAxis_Z.getValue()));
/* 1645 */       if ((Display3D.this.spinDuration.getValue() == 0.0D) || ((Display3D.this.rotAxis_X.getValue() == 0.0D) && (newValue == 0.0D) && (Display3D.this.rotAxis_Z.getValue() == 0.0D)))
/*      */       {
/* 1647 */         Display3D.this.setSpinningEnabled(false);
/*      */       } else Display3D.this.setSpinningEnabled(true);
/* 1649 */       return newValue;
/*      */     }
/* 1638 */   };
/*      */ 
/* 1652 */   NumberTextField rotAxis_Z = new NumberTextField(null, 0.0D, false)
/*      */   {
/*      */     public double newValue(double newValue)
/*      */     {
/* 1656 */       Display3D.this.autoSpin.setTransformAxis(Display3D.this.getTransformForAxis(Display3D.this.rotAxis_X.getValue(), Display3D.this.rotAxis_Y.getValue(), newValue));
/*      */ 
/* 1658 */       Display3D.this.autoSpinBackground.setTransformAxis(Display3D.this.getTransformForAxis(Display3D.this.rotAxis_X.getValue(), Display3D.this.rotAxis_Y.getValue(), newValue));
/* 1659 */       if ((Display3D.this.spinDuration.getValue() == 0.0D) || ((Display3D.this.rotAxis_X.getValue() == 0.0D) && (Display3D.this.rotAxis_Y.getValue() == 0.0D) && (newValue == 0.0D)))
/*      */       {
/* 1661 */         Display3D.this.setSpinningEnabled(false);
/*      */       } else Display3D.this.setSpinningEnabled(true);
/* 1663 */       return newValue;
/*      */     }
/* 1652 */   };
/*      */ 
/* 1667 */   NumberTextField spinDuration = new NumberTextField(null, 0.0D, 1.0D, 0.02D)
/*      */   {
/*      */     public double newValue(double newValue)
/*      */     {
/* 1671 */       long mSecsPerRot = newValue == 0.0D ? 1L : ()(1000.0D / newValue);
/*      */ 
/* 1673 */       Display3D.this.autoSpin.getAlpha().setIncreasingAlphaDuration(mSecsPerRot);
/*      */ 
/* 1675 */       Display3D.this.autoSpinBackground.getAlpha().setIncreasingAlphaDuration(mSecsPerRot);
/* 1676 */       if ((newValue == 0.0D) || ((Display3D.this.rotAxis_X.getValue() == 0.0D) && (Display3D.this.rotAxis_Y.getValue() == 0.0D) && (Display3D.this.rotAxis_Z.getValue() == 0.0D)))
/*      */       {
/* 1678 */         Display3D.this.setSpinningEnabled(false);
/*      */       } else Display3D.this.setSpinningEnabled(true);
/* 1680 */       return newValue;
/*      */     }
/* 1667 */   };
/*      */ 
/* 1687 */   int rasterizationMode = 2;
/*      */ 
/* 1711 */   int cullingMode = 0;
/*      */ 
/* 1733 */   ArrayList selectedWrappers = new ArrayList();
/*      */ 
/* 1788 */   JButton systemPreferences = new JButton("MASON");
/* 1789 */   JButton appPreferences = new JButton("Simulation");
/*      */ 
/* 2180 */   public OptionPane3D optionPane = new OptionPane3D("3D Options");
/*      */ 
/*      */   public void setPreferencesKey(String s)
/*      */   {
/*  130 */     if (s.trim().endsWith("/"))
/*  131 */       throw new RuntimeException("Key ends with '/', which is not allowed");
/*  132 */     this.preferencesKey = s;
/*      */   }
/*  134 */   public String getPreferencesKey() { return this.preferencesKey; }
/*      */ 
/*      */ 
/*      */   public JFrame createFrame()
/*      */   {
/*  264 */     JFrame frame = new JFrame()
/*      */     {
/*  266 */       boolean previouslyShown = false;
/*      */ 
/*      */       public void dispose() {
/*  269 */         Display3D.this.quit();
/*  270 */         super.dispose();
/*      */       }
/*      */ 
/*      */       public void addWindowListener(WindowListener l)
/*      */       {
/*  278 */         if ("class javax.media.j3d.EventCatcher".compareTo(l.getClass().toString()) == 0) {
/*  279 */           l = new Display3D.LocalWindowListener();
/*      */         }
/*  281 */         super.addWindowListener(l);
/*      */       }
/*      */ 
/*      */       public void setVisible(boolean val)
/*      */       {
/*  289 */         super.setVisible(val);
/*      */ 
/*  293 */         if ((Display3D.this.canvas != null) && (val) && (this.previouslyShown) && (Display2D.isMacOSX))
/*      */         {
/*  295 */           SwingUtilities.invokeLater(new Runnable()
/*      */           {
/*      */             public void run()
/*      */             {
/*  299 */               Display3D.this.remove(Display3D.this.canvas);
/*  300 */               Display3D.this.add(Display3D.this.canvas, "Center");
/*      */             }
/*      */           });
/*      */         }
/*  304 */         if (val == true)
/*  305 */           this.previouslyShown = true;
/*      */       }
/*      */     };
/*  309 */     frame.setResizable(true);
/*      */ 
/*  312 */     frame.addComponentListener(new ComponentAdapter()
/*      */     {
/*      */       public void componentResized(ComponentEvent e)
/*      */       {
/*  317 */         Utilities.doEnsuredRepaint(Display3D.this.header);
/*      */       }
/*      */     });
/*  321 */     frame.getContentPane().setLayout(new BorderLayout());
/*  322 */     frame.getContentPane().add(this, "Center");
/*  323 */     frame.getContentPane().setBackground(Color.yellow);
/*  324 */     frame.setTitle(GUIState.getName(this.simulation.getClass()) + " Display");
/*  325 */     frame.pack();
/*  326 */     return frame;
/*      */   }
/*      */ 
/*      */   public Frame getFrame()
/*      */   {
/*  382 */     Component c = this;
/*  383 */     while (c.getParent() != null)
/*  384 */       c = c.getParent();
/*  385 */     return (Frame)c;
/*      */   }
/*      */ 
/*      */   public void reset()
/*      */   {
/*  393 */     synchronized (this.simulation.state.schedule)
/*      */     {
/*  396 */       if (this.stopper != null) this.stopper.stop();
/*      */ 
/*  398 */       this.stopper = this.simulation.scheduleRepeatingImmediatelyAfter(this);
/*      */     }
/*      */ 
/*  402 */     for (int x = 0; x < this.selectedWrappers.size(); x++)
/*      */     {
/*  404 */       LocationWrapper wrapper = (LocationWrapper)this.selectedWrappers.get(x);
/*  405 */       wrapper.getFieldPortrayal().setSelected(wrapper, false);
/*      */     }
/*  407 */     this.selectedWrappers.clear();
/*      */   }
/*      */ 
/*      */   public void attach(Portrayal3D portrayal, String name)
/*      */   {
/*  420 */     attach(portrayal, name, true);
/*      */   }
/*      */ 
/*      */   public void attach(Portrayal3D portrayal, String name, boolean visible)
/*      */   {
/*  430 */     destroySceneGraph();
/*      */ 
/*  432 */     Portrayal3DHolder p = new Portrayal3DHolder(portrayal, name, visible);
/*  433 */     this.portrayals.add(p);
/*  434 */     this.popup.add(p.menuItem);
/*  435 */     this.dirty = true;
/*  436 */     portrayal.setCurrentDisplay(this);
/*      */ 
/*  438 */     createSceneGraph();
/*      */   }
/*      */ 
/*      */   public void attach(final Inspector inspector, final String name)
/*      */   {
/*  445 */     JMenuItem consoleMenu = new JMenuItem("Show " + name);
/*  446 */     this.popup.add(consoleMenu);
/*  447 */     consoleMenu.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  451 */         Bag inspectors = new Bag();
/*  452 */         inspectors.add(inspector);
/*  453 */         Bag names = new Bag();
/*  454 */         names.add(name);
/*  455 */         Display3D.this.simulation.controller.setInspectors(inspectors, names);
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   void createConsoleMenu()
/*      */   {
/*  462 */     if ((this.simulation != null) && (this.simulation.controller != null) && ((this.simulation.controller instanceof Console)))
/*      */     {
/*  465 */       final Console c = (Console)this.simulation.controller;
/*  466 */       JMenuItem consoleMenu = new JMenuItem("Show Console");
/*  467 */       this.popup.add(consoleMenu);
/*  468 */       consoleMenu.addActionListener(new ActionListener()
/*      */       {
/*      */         public void actionPerformed(ActionEvent e)
/*      */         {
/*  472 */           c.setVisible(true);
/*      */         }
/*      */       });
/*      */     }
/*  476 */     this.popup.addSeparator();
/*      */   }
/*      */ 
/*      */   public ArrayList detatchAll()
/*      */   {
/*  482 */     ArrayList old = this.portrayals;
/*  483 */     this.popup.removeAll();
/*  484 */     createConsoleMenu();
/*  485 */     this.portrayals = new ArrayList();
/*  486 */     this.portrayalSwitchMask = null;
/*  487 */     this.subgraphCount = 0;
/*  488 */     this.dirty = true;
/*  489 */     return old;
/*      */   }
/*      */   public GUIState getSimulation() {
/*  492 */     return this.simulation;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Display3D(double width, double height, GUIState simulation, long interval)
/*      */   {
/*  502 */     this(width, height, simulation);
/*      */   }
/*      */ 
/*      */   public Display3D(double width, double height, GUIState simulation)
/*      */   {
/*  513 */     this.simulation = simulation;
/*  514 */     reset();
/*      */ 
/*  516 */     final Color headerBackground = getBackground();
/*  517 */     this.header = new Box(0)
/*      */     {
/*      */       public synchronized void paintComponent(Graphics g)
/*      */       {
/*  523 */         g.setColor(headerBackground);
/*  524 */         g.fillRect(0, 0, Display3D.this.header.getWidth(), Display3D.this.header.getHeight());
/*      */       }
/*      */ 
/*      */       public Dimension getPreferredSize()
/*      */       {
/*  529 */         Dimension d = super.getPreferredSize();
/*  530 */         d.width = 0;
/*  531 */         return d;
/*      */       }
/*      */     };
/*  537 */     setBackground(Color.black);
/*      */ 
/*  540 */     this.layersbutton = new JToggleButton(Display2D.LAYERS_ICON);
/*  541 */     this.layersbutton.setPressedIcon(Display2D.LAYERS_ICON_P);
/*  542 */     this.layersbutton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/*  543 */     this.layersbutton.setBorderPainted(false);
/*  544 */     this.layersbutton.setContentAreaFilled(false);
/*  545 */     this.layersbutton.setToolTipText("Show and hide different layers");
/*  546 */     this.header.add(this.layersbutton);
/*      */ 
/*  549 */     this.popup = new JPopupMenu();
/*  550 */     this.popup.setLightWeightPopupEnabled(false);
/*      */ 
/*  553 */     this.layersbutton.addMouseListener(new MouseAdapter()
/*      */     {
/*      */       public void mousePressed(MouseEvent e)
/*      */       {
/*  557 */         Display3D.this.popup.show(e.getComponent(), Display3D.this.layersbutton.getLocation().x, Display3D.this.layersbutton.getSize().height);
/*      */       }
/*      */ 
/*      */       public void mouseReleased(MouseEvent e)
/*      */       {
/*  564 */         Display3D.this.layersbutton.setSelected(false);
/*      */       }
/*      */     });
/*  572 */     this.refreshbutton = new JToggleButton(Display2D.REFRESH_ICON);
/*  573 */     this.refreshbutton.setPressedIcon(Display2D.REFRESH_ICON_P);
/*  574 */     this.refreshbutton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/*  575 */     this.refreshbutton.setBorderPainted(false);
/*  576 */     this.refreshbutton.setContentAreaFilled(false);
/*  577 */     this.refreshbutton.setToolTipText("Change How and When the Display Redraws Itself");
/*      */ 
/*  579 */     this.header.add(this.refreshbutton);
/*  580 */     this.refreshPopup = new JPopupMenu();
/*  581 */     this.refreshPopup.setLightWeightPopupEnabled(false);
/*      */ 
/*  584 */     this.refreshbutton.addMouseListener(new MouseAdapter()
/*      */     {
/*      */       public void mousePressed(MouseEvent e)
/*      */       {
/*  588 */         Display3D.this.rebuildRefreshPopup();
/*  589 */         Display3D.this.refreshPopup.show(e.getComponent(), 0, Display3D.this.refreshbutton.getSize().height);
/*      */       }
/*      */ 
/*      */       public void mouseReleased(MouseEvent e)
/*      */       {
/*  596 */         Display3D.this.refreshbutton.setSelected(false);
/*  597 */         Display3D.this.rebuildRefreshPopup();
/*      */       }
/*      */     });
/*  606 */     this.movieButton = new JButton(Display2D.MOVIE_OFF_ICON);
/*  607 */     this.movieButton.setPressedIcon(Display2D.MOVIE_OFF_ICON_P);
/*  608 */     this.movieButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/*  609 */     this.movieButton.setBorderPainted(false);
/*  610 */     this.movieButton.setContentAreaFilled(false);
/*  611 */     this.movieButton.setToolTipText("Create a Quicktime movie");
/*  612 */     this.movieButton.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  616 */         if (Display3D.this.movieMaker == null) Display3D.this.startMovie(); else
/*  617 */           Display3D.this.stopMovie();
/*      */       }
/*      */     });
/*  620 */     this.header.add(this.movieButton);
/*      */ 
/*  622 */     this.snapshotButton = new JButton(Display2D.CAMERA_ICON);
/*  623 */     this.snapshotButton.setPressedIcon(Display2D.CAMERA_ICON_P);
/*  624 */     this.snapshotButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/*  625 */     this.snapshotButton.setBorderPainted(false);
/*  626 */     this.snapshotButton.setContentAreaFilled(false);
/*  627 */     this.snapshotButton.setToolTipText("Create a snapshot (as a PNG file)");
/*  628 */     this.snapshotButton.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  632 */         SwingUtilities.invokeLater(new Runnable()
/*      */         {
/*      */           public void run() {
/*  635 */             Display3D.this.takeSnapshot();
/*      */           }
/*      */         });
/*      */       }
/*      */     });
/*  640 */     this.header.add(this.snapshotButton);
/*      */ 
/*  642 */     this.optionButton = new JButton(Display2D.OPTIONS_ICON);
/*  643 */     this.optionButton.setPressedIcon(Display2D.OPTIONS_ICON_P);
/*  644 */     this.optionButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/*  645 */     this.optionButton.setBorderPainted(false);
/*  646 */     this.optionButton.setContentAreaFilled(false);
/*  647 */     this.optionButton.setToolTipText("Options");
/*  648 */     this.optionButton.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  652 */         Display3D.this.optionPane.setVisible(true);
/*      */       }
/*      */     });
/*  655 */     this.header.add(this.optionButton);
/*      */ 
/*  658 */     this.scaleField = new NumberTextField("  Scale: ", 1.0D, true)
/*      */     {
/*      */       public double newValue(double newValue)
/*      */       {
/*  662 */         if (newValue <= 0.0D) newValue = this.currentValue;
/*  663 */         Display3D.this.setScale(newValue);
/*  664 */         return newValue;
/*      */       }
/*      */     };
/*  667 */     this.scaleField.setToolTipText("Magnifies the scene.  Not the same as zooming (see the options panel)");
/*  668 */     this.scaleField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
/*  669 */     this.header.add(this.scaleField);
/*      */ 
/*  672 */     setPreferredSize(new Dimension((int)width, (int)height));
/*      */ 
/*  674 */     setLayout(new BorderLayout());
/*  675 */     add(this.header, "North");
/*      */ 
/*  678 */     this.auxillarySwitchMask.clear(0);
/*  679 */     this.auxillarySwitchMask.clear(1);
/*  680 */     this.showBackgroundCheckBox.setSelected(true);
/*      */ 
/*  682 */     createSceneGraph();
/*      */ 
/*  684 */     this.skipFrame = new JFrame();
/*  685 */     rebuildSkipFrame();
/*  686 */     this.skipFrame.pack();
/*      */ 
/*  688 */     createConsoleMenu();
/*      */   }
/*      */ 
/*      */   public void clearBackdrop()
/*      */   {
/*  698 */     this.backdropAppearance = null;
/*  699 */     this.backdropImage = null;
/*  700 */     this.backdropColor = null;
/*  701 */     setShowsBackdrop(false);
/*      */   }
/*      */ 
/*      */   public void setBackdrop(Appearance appearance)
/*      */   {
/*  707 */     clearBackdrop();
/*  708 */     this.backdropAppearance = appearance;
/*  709 */     setShowsBackdrop(true);
/*      */   }
/*      */ 
/*      */   public void setBackdrop(Color color)
/*      */   {
/*  715 */     clearBackdrop();
/*  716 */     this.backdropColor = color;
/*  717 */     setShowsBackdrop(true);
/*      */   }
/*      */ 
/*      */   public void setBackdrop(Image image, boolean spherical)
/*      */   {
/*  723 */     clearBackdrop();
/*  724 */     if ((spherical) && (image != null))
/*      */     {
/*  726 */       Appearance appearance = new Appearance();
/*  727 */       appearance.setTexture(new TextureLoader(image, null).getTexture());
/*  728 */       setBackdrop(appearance);
/*      */     }
/*      */     else
/*      */     {
/*  732 */       this.backdropImage = image;
/*      */     }
/*  734 */     setShowsBackdrop(true);
/*      */   }
/*      */ 
/*      */   void rebuildAuxillarySwitch()
/*      */   {
/*  740 */     this.auxillarySwitch = new Switch(-3);
/*  741 */     this.auxillarySwitch.setCapability(18);
/*  742 */     this.auxillarySwitch.setCapability(12);
/*  743 */     this.auxillarySwitch.setCapability(14);
/*  744 */     this.auxillarySwitch.setChildMask(this.auxillarySwitchMask);
/*      */ 
/*  747 */     AxesPortrayal3D x = new AxesPortrayal3D(0.009999999776482582D, true);
/*  748 */     x.setCurrentDisplay(this);
/*  749 */     this.auxillarySwitch.insertChild(x.getModel(null, null), 0);
/*      */ 
/*  753 */     if ((this.backdropAppearance != null) || (this.backdropColor != null) || (this.backdropImage != null))
/*      */     {
/*  755 */       Background background = new Background();
/*  756 */       background.setApplicationBounds(new BoundingSphere(new Point3d(0.0D, 0.0D, 0.0D), 1.7976931348623157E+308D));
/*      */ 
/*  759 */       if (this.backdropAppearance != null)
/*      */       {
/*  761 */         BranchGroup backgroundBG = new BranchGroup();
/*  762 */         Sphere sphere = new Sphere(1.0F, 7, 45, this.backdropAppearance);
/*      */ 
/*  767 */         Transform3D strans = new Transform3D();
/*  768 */         strans.rotX(-1.570796326794897D);
/*  769 */         TransformGroup tg = new TransformGroup(strans);
/*  770 */         tg.addChild(sphere);
/*      */ 
/*  777 */         this.autoSpinBackgroundTransformGroup.addChild(tg);
/*      */ 
/*  779 */         backgroundBG.addChild(this.autoSpinBackgroundTransformGroup);
/*  780 */         background.setGeometry(backgroundBG);
/*      */       }
/*  782 */       else if (this.backdropColor != null) {
/*  783 */         background.setColor(new Color3f(this.backdropColor));
/*      */       }
/*      */       else {
/*  786 */         BufferedImage img = getGraphicsConfiguration().createCompatibleImage(this.backdropImage.getWidth(null), this.backdropImage.getHeight(null));
/*      */ 
/*  789 */         Graphics g = img.getGraphics();
/*  790 */         g.drawImage(this.backdropImage, 0, 0, null);
/*  791 */         background.setImage(new ImageComponent2D(1, img));
/*  792 */         background.setImageScaleMode(2);
/*  793 */         img.flush();
/*      */       }
/*      */ 
/*  796 */       this.auxillarySwitch.addChild(background);
/*      */     } else {
/*  798 */       this.auxillarySwitch.addChild(new Group());
/*      */     }
/*      */ 
/*  805 */     this.bogusMover = new PointArray(1, 1);
/*  806 */     this.bogusMover.setCapability(1);
/*  807 */     moveBogusMover();
/*      */ 
/*  809 */     this.auxillarySwitch.addChild(new Shape3D(this.bogusMover));
/*      */   }
/*      */ 
/*      */   void moveBogusMover()
/*      */   {
/*  820 */     this.bogusMover.setCoordinate(0, bogusPosition);
/*      */   }
/*      */ 
/*      */   void toggleAxes()
/*      */   {
/*  825 */     if (this.auxillarySwitch != null)
/*      */     {
/*  827 */       this.auxillarySwitchMask.set(0, this.showAxesCheckBox.isSelected());
/*  828 */       this.auxillarySwitch.setChildMask(this.auxillarySwitchMask);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setShowsAxes(boolean value)
/*      */   {
/*  834 */     this.showAxesCheckBox.setSelected(value);
/*  835 */     toggleAxes();
/*      */   }
/*      */ 
/*      */   void toggleBackdrop()
/*      */   {
/*  840 */     if (this.auxillarySwitch != null)
/*      */     {
/*  842 */       this.auxillarySwitchMask.set(1, this.showBackgroundCheckBox.isSelected());
/*  843 */       this.auxillarySwitch.setChildMask(this.auxillarySwitchMask);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setShowsBackdrop(boolean value)
/*      */   {
/*  849 */     this.showBackgroundCheckBox.setSelected(value);
/*  850 */     toggleBackdrop();
/*      */   }
/*      */ 
/*      */   void toggleSpotlight()
/*      */   {
/*  855 */     if (this.lightSwitch != null)
/*      */     {
/*  857 */       this.lightSwitchMask.set(0, this.showSpotlightCheckBox.isSelected());
/*  858 */       this.lightSwitch.setChildMask(this.lightSwitchMask);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setShowsSpotlight(boolean value)
/*      */   {
/*  864 */     this.showSpotlightCheckBox.setSelected(value);
/*  865 */     toggleSpotlight();
/*      */   }
/*      */ 
/*      */   void toggleAmbientLight()
/*      */   {
/*  870 */     if (this.lightSwitch != null)
/*      */     {
/*  872 */       this.lightSwitchMask.set(1, this.showAmbientLightCheckBox.isSelected());
/*  873 */       this.lightSwitch.setChildMask(this.lightSwitchMask);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setShowsAmbientLight(boolean value)
/*      */   {
/*  879 */     this.showAmbientLightCheckBox.setSelected(value);
/*  880 */     toggleAmbientLight();
/*      */   }
/*      */ 
/*      */   void rebuildGlobalModelTransformGroup()
/*      */   {
/*  890 */     TransformGroup newGroup = new TransformGroup();
/*  891 */     newGroup.setCapability(17);
/*  892 */     newGroup.setCapability(18);
/*      */ 
/*  894 */     if (this.globalModelTransformGroup != null)
/*      */     {
/*  896 */       Transform3D trans = new Transform3D();
/*  897 */       this.globalModelTransformGroup.getTransform(trans);
/*  898 */       newGroup.setTransform(trans);
/*      */     }
/*  900 */     this.globalModelTransformGroup = newGroup;
/*      */   }
/*      */ 
/*      */   public Transform3D getTransform()
/*      */   {
/*  907 */     Transform3D trans = new Transform3D();
/*  908 */     this.globalModelTransformGroup.getTransform(trans);
/*  909 */     return trans;
/*      */   }
/*      */ 
/*      */   public void setTransform(Transform3D transform)
/*      */   {
/*  916 */     if (transform != null) this.globalModelTransformGroup.setTransform(new Transform3D(transform)); else
/*  917 */       this.globalModelTransformGroup.setTransform(new Transform3D());
/*      */   }
/*      */ 
/*      */   public void transform(Transform3D transform)
/*      */   {
/*  924 */     Transform3D current = getTransform();
/*  925 */     current.mul(transform, current);
/*  926 */     setTransform(current);
/*      */   }
/*      */ 
/*      */   public void resetTransform()
/*      */   {
/*  932 */     this.globalModelTransformGroup.setTransform(new Transform3D());
/*      */   }
/*      */ 
/*      */   public void rotateX(double degrees)
/*      */   {
/*  938 */     Transform3D other = new Transform3D();
/*  939 */     other.rotX(degrees * 3.141592653589793D / 180.0D);
/*  940 */     transform(other);
/*      */   }
/*      */ 
/*      */   public void rotateY(double degrees)
/*      */   {
/*  946 */     Transform3D other = new Transform3D();
/*  947 */     other.rotY(degrees * 3.141592653589793D / 180.0D);
/*  948 */     transform(other);
/*      */   }
/*      */ 
/*      */   public void rotateZ(double degrees)
/*      */   {
/*  954 */     Transform3D other = new Transform3D();
/*  955 */     other.rotZ(degrees * 3.141592653589793D / 180.0D);
/*  956 */     transform(other);
/*      */   }
/*      */ 
/*      */   public void translate(double dx, double dy, double dz)
/*      */   {
/*  962 */     Transform3D other = new Transform3D();
/*  963 */     other.setTranslation(new Vector3d(dx, dy, dz));
/*  964 */     transform(other);
/*      */   }
/*      */ 
/*      */   public void scale(double value)
/*      */   {
/*  971 */     Transform3D other = new Transform3D();
/*  972 */     other.setScale(value);
/*  973 */     transform(other);
/*      */   }
/*      */ 
/*      */   public void scale(double sx, double sy, double sz)
/*      */   {
/*  980 */     Transform3D other = new Transform3D();
/*  981 */     other.setScale(new Vector3d(sx, sy, sz));
/*  982 */     transform(other);
/*      */   }
/*      */ 
/*      */   public void destroySceneGraph()
/*      */   {
/*  994 */     this.mSelectBehavior.detach();
/*  995 */     this.root.detach();
/*  996 */     this.universe.getLocale().removeBranchGraph(this.root);
/*  997 */     this.canvas.stopRenderer();
/*      */   }
/*      */ 
/*      */   public void createSceneGraph()
/*      */   {
/* 1009 */     this.dirty = false;
/*      */ 
/* 1012 */     if (this.universe == null)
/*      */     {
/* 1016 */       this.canvas = new CapturingCanvas3D(SimpleUniverse.getPreferredConfiguration());
/* 1017 */       add(this.canvas, "Center");
/* 1018 */       this.universe = new SimpleUniverse(this.canvas);
/* 1019 */       this.universe.getViewingPlatform().setNominalViewingTransform();
/*      */ 
/* 1022 */       this.lightSwitch = new Switch(-3);
/* 1023 */       this.lightSwitch.setCapability(18);
/* 1024 */       this.lightSwitch.setCapability(12);
/* 1025 */       this.lightSwitch.setCapability(14);
/*      */ 
/* 1027 */       this.lightSwitchMask.set(0);
/* 1028 */       this.lightSwitchMask.clear(1);
/* 1029 */       this.lightSwitch.setChildMask(this.lightSwitchMask);
/* 1030 */       PointLight pl = new PointLight(new Color3f(1.0F, 1.0F, 1.0F), new Point3f(0.0F, 0.0F, 0.0F), new Point3f(1.0F, 0.0F, 0.0F));
/*      */ 
/* 1033 */       pl.setInfluencingBounds(new BoundingSphere(new Point3d(0.0D, 0.0D, 0.0D), (1.0D / 0.0D)));
/* 1034 */       this.lightSwitch.addChild(pl);
/* 1035 */       AmbientLight al = new AmbientLight(new Color3f(1.0F, 1.0F, 1.0F));
/* 1036 */       al.setInfluencingBounds(new BoundingSphere(new Point3d(0.0D, 0.0D, 0.0D), (1.0D / 0.0D)));
/* 1037 */       this.lightSwitch.addChild(al);
/*      */ 
/* 1039 */       this.viewRoot = new BranchGroup();
/* 1040 */       this.viewRoot.addChild(this.lightSwitch);
/* 1041 */       this.universe.getViewingPlatform().getViewPlatformTransform().addChild(this.viewRoot);
/*      */     }
/*      */     else
/*      */     {
/* 1048 */       destroySceneGraph();
/*      */     }
/*      */ 
/* 1052 */     BranchGroup oldRoot = this.root;
/* 1053 */     this.root = new BranchGroup();
/*      */ 
/* 1055 */     this.root.setCapability(14);
/* 1056 */     this.root.setCapability(13);
/* 1057 */     this.root.setCapability(3);
/*      */ 
/* 1059 */     this.root.setCapability(17);
/*      */ 
/* 1062 */     this.autoSpinTransformGroup = new TransformGroup();
/* 1063 */     this.autoSpinTransformGroup.setCapability(18);
/*      */ 
/* 1066 */     this.portrayalSwitch = new Switch(-3);
/* 1067 */     this.portrayalSwitch.setCapability(18);
/* 1068 */     this.portrayalSwitch.setCapability(12);
/* 1069 */     this.portrayalSwitch.setCapability(14);
/*      */ 
/* 1073 */     this.autoSpinBackgroundTransformGroup = new TransformGroup();
/* 1074 */     this.autoSpinBackgroundTransformGroup.setCapability(18);
/*      */ 
/* 1078 */     this.portrayalSwitchMask = new BitSet(this.subgraphCount);
/* 1079 */     int count = 0;
/* 1080 */     Iterator iter = this.portrayals.iterator();
/* 1081 */     while (iter.hasNext())
/*      */     {
/* 1083 */       Portrayal3DHolder p3h = (Portrayal3DHolder)iter.next();
/* 1084 */       Portrayal3D p = p3h.portrayal;
/* 1085 */       Object obj = (p instanceof FieldPortrayal3D) ? ((FieldPortrayal3D)p).getField() : null;
/* 1086 */       p.setCurrentDisplay(this);
/* 1087 */       this.portrayalSwitch.addChild(p.getModel(obj, null));
/* 1088 */       if (p3h.visible)
/* 1089 */         this.portrayalSwitchMask.set(count);
/*      */       else
/* 1091 */         this.portrayalSwitchMask.clear(count);
/* 1092 */       count++;
/*      */     }
/* 1094 */     this.portrayalSwitch.setChildMask(this.portrayalSwitchMask);
/*      */ 
/* 1097 */     BoundingSphere bounds = new BoundingSphere(new Point3d(0.0D, 0.0D, 0.0D), (1.0D / 0.0D));
/* 1098 */     this.mSelectBehavior = new SelectionBehavior(this.canvas, this.root, bounds, this.simulation);
/* 1099 */     this.mSelectBehavior.setSelectsAll(this.selectionAll, this.inspectionAll);
/* 1100 */     this.mSelectBehavior.setEnable(this.selectBehCheckBox.isSelected());
/*      */ 
/* 1102 */     this.toolTipBehavior = new ToolTipBehavior(this.canvas, this.root, bounds);
/* 1103 */     this.toolTipBehavior.setEnable(true);
/* 1104 */     this.toolTipBehavior.setCanShowToolTips(this.usingToolTips);
/*      */ 
/* 1109 */     if (this.autoSpin == null)
/*      */     {
/* 1111 */       this.autoSpin = new RotationInterpolator(new Alpha(), this.autoSpinTransformGroup);
/* 1112 */       this.autoSpin.getAlpha().setLoopCount(0);
/* 1113 */       this.autoSpin.setSchedulingBounds(bounds);
/*      */ 
/* 1116 */       this.autoSpinBackground = new RotationInterpolator(new Alpha(), this.autoSpinBackgroundTransformGroup);
/* 1117 */       this.autoSpinBackground.getAlpha().setLoopCount(0);
/* 1118 */       this.autoSpinBackground.setSchedulingBounds(bounds);
/*      */ 
/* 1120 */       setSpinningEnabled(false);
/*      */     }
/*      */     else
/*      */     {
/* 1124 */       oldRoot.removeChild(this.autoSpin);
/* 1125 */       oldRoot.removeChild(this.autoSpinBackground);
/*      */     }
/*      */ 
/* 1129 */     rebuildGlobalModelTransformGroup();
/*      */ 
/* 1132 */     rebuildAuxillarySwitch();
/*      */ 
/* 1135 */     this.mOrbitBehavior = new OrbitBehavior(this.canvas, 112);
/* 1136 */     this.mOrbitBehavior.setRotateEnable(true);
/* 1137 */     this.mOrbitBehavior.setRotXFactor(this.orbitRotateXCheckBox.isSelected() ? 1.0D : 0.0D);
/* 1138 */     this.mOrbitBehavior.setRotYFactor(this.orbitRotateYCheckBox.isSelected() ? 1.0D : 0.0D);
/* 1139 */     this.mOrbitBehavior.setTranslateEnable(true);
/* 1140 */     this.mOrbitBehavior.setTransXFactor(this.orbitTranslateXCheckBox.isSelected() ? 1.0D : 0.0D);
/* 1141 */     this.mOrbitBehavior.setTransYFactor(this.orbitTranslateYCheckBox.isSelected() ? 1.0D : 0.0D);
/* 1142 */     this.mOrbitBehavior.setZoomEnable(this.orbitZoomCheckBox.isSelected());
/* 1143 */     this.mOrbitBehavior.setSchedulingBounds(bounds);
/* 1144 */     this.universe.getViewingPlatform().setViewPlatformBehavior(this.mOrbitBehavior);
/*      */ 
/* 1147 */     this.globalModelTransformGroup.addChild(this.portrayalSwitch);
/* 1148 */     this.autoSpinTransformGroup.addChild(this.globalModelTransformGroup);
/* 1149 */     this.autoSpinTransformGroup.addChild(this.auxillarySwitch);
/*      */ 
/* 1151 */     this.root.addChild(this.autoSpin);
/* 1152 */     this.root.addChild(this.autoSpinBackground);
/* 1153 */     this.autoSpin.setTarget(this.autoSpinTransformGroup);
/* 1154 */     this.autoSpinBackground.setTarget(this.autoSpinBackgroundTransformGroup);
/* 1155 */     this.root.addChild(this.autoSpinTransformGroup);
/*      */ 
/* 1158 */     setCullingMode(this.cullingMode);
/* 1159 */     setRasterizationMode(this.rasterizationMode);
/*      */ 
/* 1162 */     sceneGraphCreated();
/*      */ 
/* 1165 */     this.universe.addBranchGraph(this.root);
/*      */ 
/* 1168 */     this.canvas.startRenderer();
/*      */   }
/*      */ 
/*      */   protected void sceneGraphCreated()
/*      */   {
/*      */   }
/*      */ 
/*      */   public void setScale(double val)
/*      */   {
/* 1192 */     synchronized (this.scaleLock)
/*      */     {
/* 1194 */       if (val < 1.0D)
/* 1195 */         this.scale = (0.7853981633974483D + 2.356194490192345D * (1.0D - val) * (1.0D - val));
/*      */       else
/* 1197 */         this.scale = (0.7853981633974483D / val);
/* 1198 */       this.canvas.getView().setFieldOfView(this.scale);
/*      */     }
/*      */   }
/*      */ 
/*      */   public double getScale()
/*      */   {
/* 1208 */     synchronized (this.scaleLock)
/*      */     {
/* 1210 */       return this.scale;
/*      */     }
/*      */   }
/*      */ 
/*      */   public SelectionBehavior getSelectionBehavior()
/*      */   {
/* 1228 */     return this.mSelectBehavior; } 
/* 1229 */   public ToolTipBehavior getToolTipBehavior() { return this.toolTipBehavior; }
/*      */ 
/*      */ 
/*      */   public void setSelectsAll(boolean selection, boolean inspection)
/*      */   {
/* 1237 */     this.selectionAll = selection;
/* 1238 */     this.inspectionAll = inspection;
/* 1239 */     this.mSelectBehavior.setSelectsAll(this.selectionAll, this.inspectionAll);
/*      */   }
/*      */ 
/*      */   public synchronized void paintComponent(Graphics g)
/*      */   {
/* 1245 */     SwingUtilities.invokeLater(new Runnable() { public void run() { Display3D.this.updateSceneGraph(false); }
/*      */ 
/*      */     });
/*      */   }
/*      */ 
/*      */   public void requestUpdate()
/*      */   {
/* 1262 */     synchronized (this.updateLock)
/*      */     {
/* 1264 */       this.updateOnce = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean shouldUpdate()
/*      */   {
/* 1271 */     boolean val = false;
/* 1272 */     boolean up = false;
/* 1273 */     synchronized (this.updateLock) { up = this.updateOnce; }
/*      */ 
/* 1275 */     if (up) {
/* 1276 */       val = true;
/* 1277 */     } else if (this.updateRule == 3) {
/* 1278 */       val = true;
/* 1279 */     } else if (this.updateRule == 0)
/*      */     {
/* 1281 */       long step = this.simulation.state.schedule.getSteps();
/* 1282 */       val = (this.lastStep < 0L) || (this.stepInterval == 0L) || (step - this.lastStep >= this.stepInterval) || (this.lastStep % this.stepInterval >= step % this.stepInterval);
/*      */ 
/* 1284 */       if (val) this.lastStep = step;
/*      */     }
/* 1286 */     else if (this.updateRule == 2)
/*      */     {
/* 1288 */       long wall = System.currentTimeMillis();
/* 1289 */       val = (this.lastWall == 0L) || (this.wallInterval == 0L) || (wall - this.lastWall >= this.wallInterval) || (this.lastWall % this.wallInterval >= wall % this.wallInterval);
/*      */ 
/* 1291 */       if (val) this.lastWall = wall;
/*      */     }
/* 1293 */     else if (this.updateRule == 1)
/*      */     {
/* 1295 */       double time = this.simulation.state.schedule.getTime();
/* 1296 */       val = (this.lastTime == 0.0D) || (this.timeInterval == 0.0D) || (time - this.lastTime >= this.timeInterval) || (this.lastTime % this.timeInterval >= time % this.timeInterval);
/*      */ 
/* 1298 */       if (val) this.lastTime = time;
/*      */ 
/*      */     }
/*      */ 
/* 1303 */     synchronized (this.updateLock) { this.updateOnce = false; }
/*      */ 
/* 1305 */     return val;
/*      */   }
/*      */ 
/*      */   public void step(SimState state)
/*      */   {
/* 1312 */     if ((shouldUpdate()) && ((this.canvas.isShowing()) || (this.movieMaker != null)))
/*      */     {
/* 1316 */       updateSceneGraph(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateSceneGraph(boolean waitForRenderer)
/*      */   {
/* 1339 */     if (this.canvas == null) return;
/*      */ 
/* 1346 */     if ((this.dirty) && (waitForRenderer)) { createSceneGraph(); return;
/*      */     }
/*      */ 
/* 1350 */     boolean changes = false;
/* 1351 */     Iterator iter = this.portrayals.iterator();
/*      */ 
/* 1353 */     moveBogusMover();
/* 1354 */     while (iter.hasNext())
/*      */     {
/* 1356 */       Portrayal3DHolder ph = (Portrayal3DHolder)iter.next();
/* 1357 */       if (this.portrayalSwitchMask.get(ph.subgraphIndex))
/*      */       {
/* 1360 */         ph.portrayal.setCurrentDisplay(this);
/* 1361 */         ph.portrayal.getModel((ph.portrayal instanceof FieldPortrayal3D) ? ((FieldPortrayal3D)ph.portrayal).getField() : null, (TransformGroup)this.portrayalSwitch.getChild(ph.subgraphIndex));
/*      */ 
/* 1364 */         changes = true;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1377 */     waitForRenderer &= changes;
/* 1378 */     if (!waitForRenderer) {
/* 1379 */       return;
/*      */     }
/* 1381 */     synchronized (this.canvas)
/*      */     {
/*      */       try
/*      */       {
/* 1385 */         if (!Thread.currentThread().isInterrupted())
/*      */         {
/* 1387 */           this.canvas.wait(0L);
/*      */         }
/*      */       }
/*      */       catch (InterruptedException ex)
/*      */       {
/*      */         try {
/* 1393 */           Thread.currentThread().interrupt();
/*      */         }
/*      */         catch (SecurityException ex2)
/*      */         {
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1401 */     synchronized (this.simulation.state.schedule)
/*      */     {
/* 1404 */       if (this.movieMaker != null)
/* 1405 */         this.movieMaker.add(this.canvas.getLastImage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void takeSnapshot(File file)
/*      */     throws IOException
/*      */   {
/* 1413 */     this.canvas.beginCapturing(false);
/* 1414 */     BufferedImage image = this.canvas.getLastImage();
/* 1415 */     PNGEncoder tmpEncoder = new PNGEncoder(image, false, 0, 9);
/* 1416 */     OutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
/* 1417 */     stream.write(tmpEncoder.pngEncode());
/* 1418 */     stream.close();
/* 1419 */     image.flush();
/*      */   }
/*      */ 
/*      */   public void takeSnapshot()
/*      */   {
/* 1427 */     if (SimApplet.isApplet())
/*      */     {
/* 1429 */       Object[] options = { "Oops" };
/* 1430 */       JOptionPane.showOptionDialog(this, "You cannot save snapshots from an applet.", "MASON Applet Restriction", 0, 0, null, options, options[0]);
/*      */ 
/* 1435 */       return;
/*      */     }
/*      */ 
/* 1439 */     this.canvas.beginCapturing(false);
/*      */ 
/* 1442 */     FileDialog fd = new FileDialog(getFrame(), "Save Snapshot as 24-bit PNG...", 1);
/*      */ 
/* 1445 */     fd.setFile("Untitled.png");
/* 1446 */     fd.setVisible(true);
/* 1447 */     if (fd.getFile() != null)
/*      */       try
/*      */       {
/* 1450 */         File snapShotFile = new File(fd.getDirectory(), Utilities.ensureFileEndsWith(fd.getFile(), ".png"));
/*      */ 
/* 1452 */         BufferedImage image = this.canvas.getLastImage();
/* 1453 */         PNGEncoder tmpEncoder = new PNGEncoder(image, false, 0, 9);
/* 1454 */         OutputStream stream = new BufferedOutputStream(new FileOutputStream(snapShotFile));
/* 1455 */         stream.write(tmpEncoder.pngEncode());
/* 1456 */         stream.close();
/* 1457 */         image.flush();
/*      */       }
/*      */       catch (FileNotFoundException e)
/*      */       {
/*      */       }
/*      */       catch (IOException e)
/*      */       {
/*      */       }
/*      */   }
/*      */ 
/*      */   public void startMovie()
/*      */   {
/* 1473 */     if (SimApplet.isApplet())
/*      */     {
/* 1475 */       Object[] options = { "Oops" };
/* 1476 */       JOptionPane.showOptionDialog(this, "You cannot create movies from an applet.", "MASON Applet Restriction", 0, 0, null, options, options[0]);
/*      */ 
/* 1481 */       return;
/*      */     }
/*      */ 
/* 1484 */     if (this.movieMaker != null) return;
/*      */ 
/* 1491 */     synchronized (this.simulation.state.schedule)
/*      */     {
/* 1493 */       this.movieMaker = new MovieMaker(getFrame());
/*      */ 
/* 1495 */       this.canvas.beginCapturing(false);
/* 1496 */       BufferedImage typicalImage = this.canvas.getLastImage();
/*      */ 
/* 1498 */       if (!this.movieMaker.start(typicalImage)) {
/* 1499 */         this.movieMaker = null;
/*      */       }
/*      */       else {
/* 1502 */         this.canvas.beginCapturing(true);
/* 1503 */         this.movieButton.setIcon(Display2D.MOVIE_ON_ICON);
/* 1504 */         this.movieButton.setPressedIcon(Display2D.MOVIE_ON_ICON_P);
/* 1505 */         this.simulation.scheduleAtEnd(new Steppable() {
/*      */           public void step(SimState state) {
/* 1507 */             Display3D.this.stopMovie();
/*      */           }
/*      */         });
/*      */       }
/* 1511 */       typicalImage.flush();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void stopMovie()
/*      */   {
/* 1524 */     synchronized (this.simulation.state.schedule)
/*      */     {
/* 1526 */       if (this.movieMaker == null) return;
/* 1527 */       this.canvas.stopCapturing();
/* 1528 */       if (!this.movieMaker.stop())
/*      */       {
/* 1530 */         Object[] options = { "Drat" };
/* 1531 */         JOptionPane.showOptionDialog(this, "Your movie did not write to disk\ndue to a spurious JMF movie generation bug.", "JMF Movie Generation Bug", 0, 2, null, options, options[0]);
/*      */       }
/*      */ 
/* 1537 */       this.movieMaker = null;
/* 1538 */       if (this.movieButton != null)
/*      */       {
/* 1540 */         this.movieButton.setIcon(Display2D.MOVIE_OFF_ICON);
/* 1541 */         this.movieButton.setPressedIcon(Display2D.MOVIE_OFF_ICON_P);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void quit()
/*      */   {
/* 1550 */     stopMovie();
/*      */ 
/* 1553 */     this.universe.cleanup();
/*      */   }
/*      */ 
/*      */   protected void finalize()
/*      */     throws Throwable
/*      */   {
/* 1560 */     super.finalize();
/* 1561 */     quit();
/*      */   }
/*      */ 
/*      */   void setSpinningEnabled(boolean value)
/*      */   {
/* 1594 */     if (this.autoSpin != null)
/* 1595 */       if (value)
/*      */       {
/* 1597 */         this.autoSpin.setEnable(true);
/* 1598 */         this.autoSpin.getAlpha().setLoopCount(-1);
/*      */ 
/* 1600 */         this.autoSpinBackground.setEnable(true);
/* 1601 */         this.autoSpinBackground.getAlpha().setLoopCount(-1);
/*      */       }
/*      */       else
/*      */       {
/* 1605 */         this.autoSpin.setEnable(false);
/* 1606 */         this.autoSpin.getAlpha().setLoopCount(0);
/*      */ 
/* 1608 */         this.autoSpinBackground.setEnable(false);
/* 1609 */         this.autoSpinBackground.getAlpha().setLoopCount(0);
/*      */       }
/*      */   }
/*      */ 
/*      */   Transform3D getTransformForAxis(double dx, double dy, double dz)
/*      */   {
/* 1616 */     Transform3D t = new Transform3D();
/* 1617 */     Transform3D t1 = new Transform3D();
/* 1618 */     t.rotX(Math.atan2(dz, dy));
/* 1619 */     t1.rotZ(-Math.atan2(dx, Math.sqrt(dy * dy + dx * dz)));
/* 1620 */     t.mul(t1);
/* 1621 */     return t;
/*      */   }
/*      */ 
/*      */   public void setRasterizationMode(int mode)
/*      */   {
/* 1690 */     this.rasterizationMode = mode;
/* 1691 */     this.polyFill.setSelected(mode == 2);
/* 1692 */     this.polyLine.setSelected(mode == 1);
/* 1693 */     this.polyPoint.setSelected(mode == 0);
/*      */ 
/* 1695 */     Iterator iter = this.portrayals.iterator();
/* 1696 */     while (iter.hasNext())
/*      */     {
/* 1698 */       PolygonAttributes pa = ((Portrayal3DHolder)iter.next()).portrayal.polygonAttributes();
/*      */       try
/*      */       {
/* 1701 */         if (pa != null) pa.setPolygonMode(mode);
/*      */       }
/*      */       catch (CapabilityNotSetException e)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setCullingMode(int mode)
/*      */   {
/* 1714 */     this.cullingMode = mode;
/* 1715 */     this.polyCullNone.setSelected(mode == 0);
/* 1716 */     this.polyCullBack.setSelected(mode == 1);
/* 1717 */     this.polyCullFront.setSelected(mode == 2);
/*      */ 
/* 1719 */     Iterator iter = this.portrayals.iterator();
/* 1720 */     while (iter.hasNext())
/*      */     {
/* 1722 */       PolygonAttributes pa = ((Portrayal3DHolder)iter.next()).portrayal.polygonAttributes();
/*      */       try
/*      */       {
/* 1725 */         if (pa != null) pa.setCullFace(mode);
/*      */       }
/*      */       catch (CapabilityNotSetException e)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public LocationWrapper[] getSelectedWrappers()
/*      */   {
/* 1742 */     return (LocationWrapper[])this.selectedWrappers.toArray(new LocationWrapper[this.selectedWrappers.size()]);
/*      */   }
/*      */ 
/*      */   public void performSelection(LocationWrapper wrapper)
/*      */   {
/* 1748 */     Bag b = new Bag();
/* 1749 */     b.add(wrapper);
/* 1750 */     performSelection(b);
/*      */   }
/*      */ 
/*      */   public void clearSelections()
/*      */   {
/* 1755 */     for (int x = 0; x < this.selectedWrappers.size(); x++)
/*      */     {
/* 1757 */       LocationWrapper wrapper = (LocationWrapper)this.selectedWrappers.get(x);
/* 1758 */       wrapper.getFieldPortrayal().setSelected(wrapper, false);
/*      */     }
/* 1760 */     this.selectedWrappers.clear();
/*      */   }
/*      */ 
/*      */   public void performSelection(Bag locationWrappers)
/*      */   {
/* 1767 */     clearSelections();
/*      */ 
/* 1769 */     if (locationWrappers == null) return;
/*      */ 
/* 1772 */     for (int x = 0; x < locationWrappers.size(); x++)
/*      */     {
/* 1774 */       LocationWrapper wrapper = (LocationWrapper)locationWrappers.get(x);
/* 1775 */       wrapper.getFieldPortrayal().setSelected(wrapper, true);
/* 1776 */       this.selectedWrappers.add(wrapper);
/*      */     }
/*      */ 
/* 1779 */     updateSceneGraph(false);
/*      */ 
/* 1783 */     this.simulation.controller.refresh();
/*      */   }
/*      */ 
/*      */   protected void rebuildSkipFrame()
/*      */   {
/* 2187 */     this.skipFrame.getContentPane().removeAll();
/* 2188 */     this.skipFrame.getContentPane().invalidate();
/* 2189 */     this.skipFrame.getContentPane().repaint();
/* 2190 */     this.skipFrame.getContentPane().setLayout(new BorderLayout());
/*      */ 
/* 2192 */     JPanel skipHeader = new JPanel();
/* 2193 */     skipHeader.setLayout(new BorderLayout());
/* 2194 */     this.skipFrame.add(skipHeader, "Center");
/*      */ 
/* 2197 */     this.skipBox = new JComboBox(Display2D.REDRAW_OPTIONS);
/* 2198 */     this.skipBox.setSelectedIndex(this.updateRule);
/* 2199 */     ActionListener skipListener = new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2203 */         Display3D.this.updateRule = Display3D.this.skipBox.getSelectedIndex();
/* 2204 */         if ((Display3D.this.updateRule == 3) || (Display3D.this.updateRule == 4))
/*      */         {
/* 2206 */           Display3D.this.skipField.getField().setText("");
/* 2207 */           Display3D.this.skipField.setEnabled(false);
/*      */         }
/* 2209 */         else if (Display3D.this.updateRule == 0)
/*      */         {
/* 2211 */           Display3D.this.skipField.setValue(Display3D.this.stepInterval);
/* 2212 */           Display3D.this.skipField.setEnabled(true);
/*      */         }
/* 2214 */         else if (Display3D.this.updateRule == 1)
/*      */         {
/* 2216 */           Display3D.this.skipField.setValue(Display3D.this.timeInterval);
/* 2217 */           Display3D.this.skipField.setEnabled(true);
/*      */         }
/*      */         else
/*      */         {
/* 2221 */           Display3D.this.skipField.setValue(Display3D.this.wallInterval / 1000L);
/* 2222 */           Display3D.this.skipField.setEnabled(true);
/*      */         }
/*      */       }
/*      */     };
/* 2226 */     this.skipBox.addActionListener(skipListener);
/*      */ 
/* 2229 */     this.skipBox.setRenderer(new DefaultListCellRenderer()
/*      */     {
/*      */       public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
/*      */       {
/* 2234 */         JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
/* 2235 */         label.setHorizontalAlignment(4);
/* 2236 */         return label;
/*      */       }
/*      */     });
/* 2240 */     skipHeader.add(this.skipBox, "West");
/*      */ 
/* 2243 */     this.skipField = new NumberTextField(null, 1.0D, false)
/*      */     {
/*      */       public double newValue(double newValue)
/*      */       {
/*      */         double val;
/*      */         double val;
/* 2248 */         if ((Display3D.this.updateRule == 3) || (Display3D.this.updateRule == 4))
/*      */         {
/* 2250 */           val = 0.0D;
/*      */         }
/* 2252 */         else if (Display3D.this.updateRule == 0)
/*      */         {
/* 2254 */           double val = ()newValue;
/* 2255 */           if (val < 1.0D) val = Display3D.this.stepInterval;
/* 2256 */           Display3D.this.stepInterval = (()val);
/*      */         }
/* 2258 */         else if (Display3D.this.updateRule == 2)
/*      */         {
/* 2260 */           double val = newValue;
/* 2261 */           if (val < 0.0D) val = Display3D.this.wallInterval / 1000L;
/* 2262 */           Display3D.this.wallInterval = (()(newValue * 1000.0D));
/*      */         }
/*      */         else
/*      */         {
/* 2266 */           val = newValue;
/* 2267 */           if (val < 0.0D) val = Display3D.this.timeInterval;
/* 2268 */           Display3D.this.timeInterval = val;
/*      */         }
/*      */ 
/* 2272 */         Display3D.this.reset();
/*      */ 
/* 2274 */         return val;
/*      */       }
/*      */     };
/* 2277 */     this.skipField.setToolTipText("Specify the interval between screen updates");
/* 2278 */     this.skipField.getField().setColumns(10);
/* 2279 */     skipHeader.add(this.skipField, "Center");
/* 2280 */     skipHeader.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/* 2281 */     skipListener.actionPerformed(null);
/*      */   }
/*      */ 
/*      */   protected void rebuildRefreshPopup()
/*      */   {
/* 2286 */     this.refreshPopup.removeAll();
/* 2287 */     String s = "";
/* 2288 */     switch (this.updateRule)
/*      */     {
/*      */     case 0:
/* 2291 */       s = "Currently redrawing each " + this.stepInterval + " model iterations";
/*      */ 
/* 2293 */       break;
/*      */     case 1:
/* 2295 */       s = "Currently redrawing every " + this.timeInterval + " units of model time";
/*      */ 
/* 2297 */       break;
/*      */     case 2:
/* 2299 */       s = "Currently redrawing every " + this.wallInterval / 1000.0D + " seconds of real time";
/*      */ 
/* 2301 */       break;
/*      */     case 3:
/* 2303 */       s = "Currently redrawing every model iteration";
/* 2304 */       break;
/*      */     case 4:
/* 2306 */       s = "Currently never redrawing except when the window is redrawn";
/* 2307 */       break;
/*      */     default:
/* 2309 */       throw new RuntimeException("default case should never occur");
/*      */     }
/* 2311 */     JMenuItem m = new JMenuItem(s);
/* 2312 */     m.setEnabled(false);
/* 2313 */     this.refreshPopup.add(m);
/*      */ 
/* 2315 */     this.refreshPopup.addSeparator();
/*      */ 
/* 2317 */     m = new JMenuItem("Always Redraw");
/* 2318 */     this.refreshPopup.add(m);
/* 2319 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2323 */         Display3D.this.updateRule = 3;
/* 2324 */         Display3D.this.rebuildSkipFrame();
/*      */       }
/*      */     });
/* 2328 */     m = new JMenuItem("Never Redraw");
/* 2329 */     this.refreshPopup.add(m);
/* 2330 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2334 */         Display3D.this.updateRule = 4;
/* 2335 */         Display3D.this.rebuildSkipFrame();
/*      */       }
/*      */     });
/* 2339 */     m = new JMenuItem("Redraw once every 2 iterations");
/* 2340 */     this.refreshPopup.add(m);
/* 2341 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2345 */         Display3D.this.updateRule = 0;
/* 2346 */         Display3D.this.stepInterval = 2L;
/* 2347 */         Display3D.this.rebuildSkipFrame();
/*      */       }
/*      */     });
/* 2351 */     m = new JMenuItem("Redraw once every 4 iterations");
/* 2352 */     this.refreshPopup.add(m);
/* 2353 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2357 */         Display3D.this.updateRule = 0;
/* 2358 */         Display3D.this.stepInterval = 4L;
/* 2359 */         Display3D.this.rebuildSkipFrame();
/*      */       }
/*      */     });
/* 2363 */     m = new JMenuItem("Redraw once every 8 iterations");
/* 2364 */     this.refreshPopup.add(m);
/* 2365 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2369 */         Display3D.this.updateRule = 0;
/* 2370 */         Display3D.this.stepInterval = 8L;
/* 2371 */         Display3D.this.rebuildSkipFrame();
/*      */       }
/*      */     });
/* 2375 */     m = new JMenuItem("Redraw once every 16 iterations");
/* 2376 */     this.refreshPopup.add(m);
/* 2377 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2381 */         Display3D.this.updateRule = 0;
/* 2382 */         Display3D.this.stepInterval = 16L;
/* 2383 */         Display3D.this.rebuildSkipFrame();
/*      */       }
/*      */     });
/* 2387 */     m = new JMenuItem("Redraw once every 32 iterations");
/* 2388 */     this.refreshPopup.add(m);
/* 2389 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2393 */         Display3D.this.updateRule = 0;
/* 2394 */         Display3D.this.stepInterval = 16L;
/* 2395 */         Display3D.this.rebuildSkipFrame();
/*      */       }
/*      */     });
/* 2399 */     this.refreshPopup.addSeparator();
/*      */ 
/* 2401 */     m = new JMenuItem("Redraw once at the next step");
/* 2402 */     this.refreshPopup.add(m);
/* 2403 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2407 */         Display3D.this.requestUpdate();
/*      */       }
/*      */     });
/* 2412 */     m = new JMenuItem("More Options...");
/* 2413 */     this.refreshPopup.add(m);
/* 2414 */     m.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 2418 */         Display3D.this.skipFrame.setTitle(Display3D.this.getFrame().getTitle() + " Options");
/* 2419 */         Display3D.this.skipFrame.setVisible(true);
/*      */       }
/*      */     });
/* 2423 */     this.refreshPopup.revalidate();
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  220 */     ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
/*      */     try
/*      */     {
/*  228 */       System.setProperty("Quaqua.TabbedPane.design", "auto");
/*  229 */       System.setProperty("Quaqua.visualMargin", "1,1,1,1");
/*  230 */       UIManager.put("Panel.opaque", Boolean.TRUE);
/*  231 */       UIManager.setLookAndFeel((String)Class.forName("ch.randelshofer.quaqua.QuaquaManager", true, Thread.currentThread().getContextClassLoader()).getMethod("getLookAndFeelClassName", (Class[])null).invoke(null, (Object[])null));
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  241 */       System.setProperty("com.apple.hwaccel", "true");
/*  242 */       System.setProperty("apple.awt.graphics.UseQuartz", "true");
/*      */ 
/*  245 */       System.setProperty("apple.awt.showGrowBox", "true");
/*      */ 
/*  252 */       System.setProperty("com.apple.macos.use-file-dialog-packages", "true");
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public class OptionPane3D extends JFrame
/*      */   {
/*      */     static final String ROTATE_LEFT_RIGHT_KEY = "Rotate Left Right";
/*      */     static final String TRANSLATE_LEFT_RIGHT_KEY = "Translate Left Right";
/*      */     static final String MOVE_TOWARDS_AWAY_KEY = "Move Towards Away";
/*      */     static final String ROTATE_UP_DOWN_KEY = "Rotate Up Down";
/*      */     static final String TRANSLATE_UP_DOWN_KEY = "Translate Up Down";
/*      */     static final String SELECT_KEY = "Select";
/*      */     static final String AUTO_ROTATE_X_KEY = "Auto Rotate X";
/*      */     static final String AUTO_ROTATE_Y_KEY = "Auto Rotate Y";
/*      */     static final String AUTO_ROTATE_Z_KEY = "Auto Rotate Z";
/*      */     static final String AUTO_ROTATE_RATE_KEY = "Auto Rotate Rate";
/*      */     static final String AXES_KEY = "Axes";
/*      */     static final String TOOLTIPS_KEY = "Tooltips";
/*      */     static final String SPOTLIGHT_KEY = "Spotlight";
/*      */     static final String AMBIENT_LIGHT_KEY = "Ambient Light";
/*      */     static final String BACKDROP_KEY = "Backdrop";
/*      */     static final String DRAW_POLYGONS_KEY = "Draw Polygons";
/*      */     static final String DRAW_FACES_KEY = "Draw Faces";
/*      */ 
/*      */     OptionPane3D(String label)
/*      */     {
/* 1794 */       super();
/*      */ 
/* 1797 */       Display3D.this.orbitRotateXCheckBox.setToolTipText("Rotates the scene left or right. Drag the left mouse button.");
/* 1798 */       Display3D.this.orbitRotateYCheckBox.setToolTipText("Rotates the scene up or down. Drag the left mouse button.");
/* 1799 */       Display3D.this.orbitTranslateXCheckBox.setToolTipText("Move the scene left or right.  Drag the middle mouse button.");
/* 1800 */       Display3D.this.orbitTranslateYCheckBox.setToolTipText("Move the scene up or down.  Drag the middle mouse button.");
/* 1801 */       Display3D.this.orbitZoomCheckBox.setToolTipText("Moves the eye towards/away from scene.  Not the same as scaling.  Drag the right mouse button.");
/* 1802 */       Display3D.this.selectBehCheckBox.setToolTipText("Selects objects.  Double-click the left mouse button.");
/*      */ 
/* 1805 */       Box outerBehaviorsPanel = new Box(0);
/* 1806 */       outerBehaviorsPanel.setBorder(new TitledBorder("Mouse Actions"));
/*      */ 
/* 1809 */       Box leftBehaviors = new Box(1);
/* 1810 */       leftBehaviors.add(Display3D.this.orbitRotateXCheckBox);
/* 1811 */       Display3D.this.orbitRotateXCheckBox.setSelected(true);
/* 1812 */       leftBehaviors.add(Display3D.this.orbitTranslateXCheckBox);
/* 1813 */       Display3D.this.orbitTranslateXCheckBox.setSelected(true);
/* 1814 */       leftBehaviors.add(Display3D.this.orbitZoomCheckBox);
/* 1815 */       Display3D.this.orbitZoomCheckBox.setSelected(true);
/* 1816 */       leftBehaviors.add(Box.createGlue());
/*      */ 
/* 1819 */       Box rightBehaviors = new Box(1);
/* 1820 */       rightBehaviors.add(Display3D.this.orbitRotateYCheckBox);
/* 1821 */       Display3D.this.orbitRotateYCheckBox.setSelected(true);
/* 1822 */       rightBehaviors.add(Display3D.this.orbitTranslateYCheckBox);
/* 1823 */       Display3D.this.orbitTranslateYCheckBox.setSelected(true);
/* 1824 */       rightBehaviors.add(Display3D.this.selectBehCheckBox);
/* 1825 */       Display3D.this.selectBehCheckBox.setSelected(true);
/* 1826 */       rightBehaviors.add(Box.createGlue());
/*      */ 
/* 1828 */       outerBehaviorsPanel.add(leftBehaviors);
/* 1829 */       outerBehaviorsPanel.add(rightBehaviors);
/* 1830 */       outerBehaviorsPanel.add(Box.createGlue());
/*      */ 
/* 1833 */       Box resetBox = new Box(0);
/* 1834 */       resetBox.setBorder(new TitledBorder("Viewpoint"));
/* 1835 */       JButton resetButton = new JButton("Reset Viewpoint");
/* 1836 */       resetButton.setToolTipText("Resets display to original rotation, translation, and zoom.");
/* 1837 */       resetBox.add(resetButton);
/* 1838 */       resetBox.add(Box.createGlue());
/*      */ 
/* 1840 */       resetButton.addActionListener(new ActionListener()
/*      */       {
/*      */         public void actionPerformed(ActionEvent e)
/*      */         {
/* 1844 */           Display3D.this.canvas.stopRenderer();
/*      */ 
/* 1846 */           Display3D.this.scaleField.setValue(1.0D);
/* 1847 */           Display3D.this.setScale(1.0D);
/*      */ 
/* 1849 */           Display3D.this.universe.getViewingPlatform().setNominalViewingTransform();
/* 1850 */           Display3D.this.autoSpinTransformGroup.setTransform(new Transform3D());
/*      */ 
/* 1852 */           Display3D.this.autoSpinBackgroundTransformGroup.setTransform(new Transform3D());
/* 1853 */           Display3D.this.canvas.startRenderer();
/*      */         }
/*      */       });
/* 1857 */       Display3D.this.orbitRotateXCheckBox.addItemListener(new ItemListener()
/*      */       {
/*      */         public void itemStateChanged(ItemEvent e)
/*      */         {
/* 1861 */           if (Display3D.this.mOrbitBehavior != null) Display3D.this.mOrbitBehavior.setRotXFactor(Display3D.this.orbitRotateXCheckBox.isSelected() ? 1.0D : 0.0D);
/*      */         }
/*      */       });
/* 1863 */       Display3D.this.orbitRotateYCheckBox.addItemListener(new ItemListener()
/*      */       {
/*      */         public void itemStateChanged(ItemEvent e)
/*      */         {
/* 1867 */           if (Display3D.this.mOrbitBehavior != null) Display3D.this.mOrbitBehavior.setRotYFactor(Display3D.this.orbitRotateYCheckBox.isSelected() ? 1.0D : 0.0D);
/*      */         }
/*      */       });
/* 1869 */       Display3D.this.orbitTranslateXCheckBox.addItemListener(new ItemListener()
/*      */       {
/*      */         public void itemStateChanged(ItemEvent e)
/*      */         {
/* 1873 */           if (Display3D.this.mOrbitBehavior != null) Display3D.this.mOrbitBehavior.setTransXFactor(Display3D.this.orbitTranslateXCheckBox.isSelected() ? 1.0D : 0.0D);
/*      */         }
/*      */       });
/* 1875 */       Display3D.this.orbitTranslateYCheckBox.addItemListener(new ItemListener()
/*      */       {
/*      */         public void itemStateChanged(ItemEvent e)
/*      */         {
/* 1879 */           if (Display3D.this.mOrbitBehavior != null) Display3D.this.mOrbitBehavior.setTransYFactor(Display3D.this.orbitTranslateYCheckBox.isSelected() ? 1.0D : 0.0D);
/*      */         }
/*      */       });
/* 1881 */       Display3D.this.orbitZoomCheckBox.addItemListener(new ItemListener()
/*      */       {
/*      */         public void itemStateChanged(ItemEvent e) {
/* 1884 */           if (Display3D.this.mOrbitBehavior != null) Display3D.this.mOrbitBehavior.setZoomEnable(Display3D.this.orbitZoomCheckBox.isSelected());
/*      */         }
/*      */       });
/* 1886 */       Display3D.this.selectBehCheckBox.addItemListener(new ItemListener()
/*      */       {
/*      */         public void itemStateChanged(ItemEvent e) {
/* 1889 */           if (Display3D.this.mSelectBehavior != null) Display3D.this.mSelectBehavior.setEnable(Display3D.this.selectBehCheckBox.isSelected());
/*      */         }
/*      */       });
/* 1893 */       LabelledList rotatePanel = new LabelledList("Auto-Rotate About <X,Y,Z> Axis");
/* 1894 */       rotatePanel.addLabelled("X", Display3D.this.rotAxis_X);
/* 1895 */       rotatePanel.addLabelled("Y", Display3D.this.rotAxis_Y);
/* 1896 */       rotatePanel.addLabelled("Z", Display3D.this.rotAxis_Z);
/* 1897 */       rotatePanel.addLabelled("Rotations/Sec", Display3D.this.spinDuration);
/*      */ 
/* 1899 */       Box polyPanel = new Box(0);
/* 1900 */       polyPanel.setBorder(new TitledBorder("Polygon Attributes"));
/* 1901 */       ButtonGroup polyLineGroup = new ButtonGroup();
/* 1902 */       polyLineGroup.add(Display3D.this.polyPoint);
/* 1903 */       polyLineGroup.add(Display3D.this.polyLine);
/* 1904 */       polyLineGroup.add(Display3D.this.polyFill);
/* 1905 */       ButtonGroup polyCullingGroup = new ButtonGroup();
/* 1906 */       polyCullingGroup.add(Display3D.this.polyCullNone);
/* 1907 */       polyCullingGroup.add(Display3D.this.polyCullFront);
/* 1908 */       polyCullingGroup.add(Display3D.this.polyCullBack);
/*      */ 
/* 1910 */       Box polyLinebox = Box.createVerticalBox();
/* 1911 */       polyLinebox.add(Box.createGlue());
/* 1912 */       polyLinebox.add(new JLabel("Draw Polygons As..."));
/* 1913 */       polyLinebox.add(Display3D.this.polyPoint);
/* 1914 */       Display3D.this.polyPoint.addActionListener(new ActionListener() {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1916 */           Display3D.this.setRasterizationMode(0);
/*      */         }
/*      */       });
/* 1918 */       polyLinebox.add(Display3D.this.polyLine);
/* 1919 */       Display3D.this.polyLine.addActionListener(new ActionListener() {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1921 */           Display3D.this.setRasterizationMode(1);
/*      */         }
/*      */       });
/* 1923 */       polyLinebox.add(Display3D.this.polyFill);
/* 1924 */       Display3D.this.polyFill.addActionListener(new ActionListener() {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1926 */           Display3D.this.setRasterizationMode(2);
/*      */         }
/*      */       });
/* 1928 */       polyLinebox.add(Box.createGlue());
/* 1929 */       polyLinebox.setBorder(new EmptyBorder(0, 0, 0, 20));
/* 1930 */       polyPanel.add(polyLinebox);
/* 1931 */       Box polyCullbox = Box.createVerticalBox();
/* 1932 */       polyCullbox.add(Box.createGlue());
/* 1933 */       polyCullbox.add(new JLabel("Draw Faces As..."));
/* 1934 */       polyCullbox.add(Display3D.this.polyCullNone);
/* 1935 */       Display3D.this.polyCullNone.addActionListener(new ActionListener() {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1937 */           Display3D.this.setCullingMode(0);
/*      */         }
/*      */       });
/* 1939 */       polyCullbox.add(Display3D.this.polyCullBack);
/* 1940 */       Display3D.this.polyCullBack.addActionListener(new ActionListener() {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1942 */           Display3D.this.setCullingMode(1);
/*      */         }
/*      */       });
/* 1944 */       polyCullbox.add(Display3D.this.polyCullFront);
/* 1945 */       Display3D.this.polyCullFront.addActionListener(new ActionListener() {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1947 */           Display3D.this.setCullingMode(2);
/*      */         }
/*      */       });
/* 1949 */       polyCullbox.add(Box.createGlue());
/* 1950 */       polyCullbox.setBorder(new EmptyBorder(0, 0, 0, 20));
/* 1951 */       polyPanel.add(polyCullbox);
/* 1952 */       polyPanel.add(Box.createGlue());
/*      */ 
/* 1954 */       Box auxillaryPanel = new Box(1);
/* 1955 */       Box box = new Box(0);
/* 1956 */       auxillaryPanel.setBorder(new TitledBorder("Auxillary Elements"));
/* 1957 */       box.add(Display3D.this.showAxesCheckBox);
/* 1958 */       Display3D.this.showAxesCheckBox.addItemListener(new ItemListener()
/*      */       {
/*      */         public void itemStateChanged(ItemEvent e)
/*      */         {
/* 1962 */           Display3D.this.toggleAxes();
/*      */         }
/*      */       });
/* 1965 */       box.add(Display3D.this.showBackgroundCheckBox);
/* 1966 */       Display3D.this.showBackgroundCheckBox.addItemListener(new ItemListener()
/*      */       {
/*      */         public void itemStateChanged(ItemEvent e)
/*      */         {
/* 1970 */           Display3D.this.toggleBackdrop();
/*      */         }
/*      */       });
/* 1973 */       box.add(Display3D.this.tooltips);
/* 1974 */       Display3D.this.tooltips.addItemListener(new ItemListener()
/*      */       {
/*      */         public void itemStateChanged(ItemEvent e)
/*      */         {
/* 1978 */           Display3D.this.usingToolTips = Display3D.this.tooltips.isSelected();
/* 1979 */           if (Display3D.this.toolTipBehavior != null)
/* 1980 */             Display3D.this.toolTipBehavior.setCanShowToolTips(Display3D.this.usingToolTips);
/*      */         }
/*      */       });
/* 1983 */       box.add(Box.createGlue());
/* 1984 */       auxillaryPanel.add(box);
/*      */ 
/* 1987 */       box = new Box(0);
/* 1988 */       box.add(Display3D.this.showSpotlightCheckBox);
/* 1989 */       Display3D.this.showSpotlightCheckBox.setSelected(true);
/* 1990 */       Display3D.this.showSpotlightCheckBox.addItemListener(new ItemListener()
/*      */       {
/*      */         public void itemStateChanged(ItemEvent e)
/*      */         {
/* 1994 */           Display3D.this.toggleSpotlight();
/*      */         }
/*      */       });
/* 1998 */       box.add(Display3D.this.showAmbientLightCheckBox);
/* 1999 */       Display3D.this.showAmbientLightCheckBox.addItemListener(new ItemListener()
/*      */       {
/*      */         public void itemStateChanged(ItemEvent e)
/*      */         {
/* 2003 */           Display3D.this.toggleAmbientLight();
/*      */         }
/*      */       });
/* 2006 */       box.add(Box.createGlue());
/* 2007 */       auxillaryPanel.add(box);
/*      */ 
/* 2012 */       Box optionsPanel = new Box(1);
/* 2013 */       optionsPanel.add(outerBehaviorsPanel);
/* 2014 */       optionsPanel.add(rotatePanel);
/* 2015 */       optionsPanel.add(auxillaryPanel);
/* 2016 */       optionsPanel.add(polyPanel);
/* 2017 */       optionsPanel.add(resetBox);
/*      */ 
/* 2020 */       getContentPane().add(optionsPanel);
/* 2021 */       setDefaultCloseOperation(1);
/*      */ 
/* 2026 */       Box b = new Box(0);
/* 2027 */       b.add(new JLabel(" Save as Defaults for "));
/* 2028 */       b.add(Display3D.this.appPreferences);
/* 2029 */       b.add(Display3D.this.systemPreferences);
/* 2030 */       getContentPane().add(b, "South");
/*      */ 
/* 2032 */       Display3D.this.systemPreferences.putClientProperty("JComponent.sizeVariant", "mini");
/* 2033 */       Display3D.this.systemPreferences.putClientProperty("JButton.buttonType", "bevel");
/* 2034 */       Display3D.this.systemPreferences.addActionListener(new ActionListener()
/*      */       {
/*      */         public void actionPerformed(ActionEvent e)
/*      */         {
/* 2038 */           String key = Display3D.this.getPreferencesKey();
/* 2039 */           Display3D.OptionPane3D.this.savePreferences(Prefs.getGlobalPreferences(key));
/*      */ 
/* 2042 */           Prefs.removeAppPreferences(Display3D.this.simulation, key);
/*      */         }
/*      */       });
/* 2046 */       Display3D.this.appPreferences.putClientProperty("JComponent.sizeVariant", "mini");
/* 2047 */       Display3D.this.appPreferences.putClientProperty("JButton.buttonType", "bevel");
/* 2048 */       Display3D.this.appPreferences.addActionListener(new ActionListener()
/*      */       {
/*      */         public void actionPerformed(ActionEvent e)
/*      */         {
/* 2052 */           String key = Display3D.this.getPreferencesKey();
/* 2053 */           Display3D.OptionPane3D.this.savePreferences(Prefs.getAppPreferences(Display3D.this.simulation, key));
/*      */         }
/*      */       });
/* 2057 */       pack();
/* 2058 */       setResizable(false);
/*      */     }
/*      */ 
/*      */     public void savePreferences(Preferences prefs)
/*      */     {
/*      */       try
/*      */       {
/* 2067 */         prefs.putBoolean("Rotate Left Right", Display3D.this.orbitRotateXCheckBox.isSelected());
/* 2068 */         prefs.putBoolean("Rotate Up Down", Display3D.this.orbitRotateYCheckBox.isSelected());
/* 2069 */         prefs.putBoolean("Translate Left Right", Display3D.this.orbitTranslateXCheckBox.isSelected());
/* 2070 */         prefs.putBoolean("Translate Up Down", Display3D.this.orbitTranslateYCheckBox.isSelected());
/* 2071 */         prefs.putBoolean("Move Towards Away", Display3D.this.orbitZoomCheckBox.isSelected());
/* 2072 */         prefs.putBoolean("Select", Display3D.this.selectBehCheckBox.isSelected());
/*      */ 
/* 2074 */         prefs.putDouble("Auto Rotate X", Display3D.this.rotAxis_X.getValue());
/* 2075 */         prefs.putDouble("Auto Rotate Y", Display3D.this.rotAxis_Y.getValue());
/* 2076 */         prefs.putDouble("Auto Rotate Z", Display3D.this.rotAxis_Z.getValue());
/* 2077 */         prefs.putDouble("Auto Rotate Rate", Display3D.this.spinDuration.getValue());
/*      */ 
/* 2079 */         prefs.putBoolean("Axes", Display3D.this.showAxesCheckBox.isSelected());
/* 2080 */         prefs.putBoolean("Tooltips", Display3D.this.tooltips.isSelected());
/* 2081 */         prefs.putBoolean("Spotlight", Display3D.this.showSpotlightCheckBox.isSelected());
/* 2082 */         prefs.putBoolean("Ambient Light", Display3D.this.showAmbientLightCheckBox.isSelected());
/* 2083 */         prefs.putBoolean("Backdrop", Display3D.this.showBackgroundCheckBox.isSelected());
/*      */ 
/* 2085 */         prefs.putInt("Draw Polygons", Display3D.this.polyLine.isSelected() ? 1 : Display3D.this.polyPoint.isSelected() ? 0 : 2);
/*      */ 
/* 2088 */         prefs.putInt("Draw Faces", Display3D.this.polyCullBack.isSelected() ? 1 : Display3D.this.polyCullNone.isSelected() ? 0 : 2);
/*      */ 
/* 2092 */         if (!Prefs.save(prefs))
/* 2093 */           Utilities.inform("Preferences Cannot be Saved", "Your Java system can't save preferences.  Perhaps this is an applet?", this);
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
/* 2122 */         Preferences systemPrefs = Prefs.getGlobalPreferences(Display3D.this.getPreferencesKey());
/* 2123 */         Preferences appPrefs = Prefs.getAppPreferences(Display3D.this.simulation, Display3D.this.getPreferencesKey());
/*      */ 
/* 2125 */         Display3D.this.orbitRotateXCheckBox.setSelected(appPrefs.getBoolean("Rotate Left Right", systemPrefs.getBoolean("Rotate Left Right", true)));
/*      */ 
/* 2127 */         Display3D.this.orbitRotateYCheckBox.setSelected(appPrefs.getBoolean("Rotate Up Down", systemPrefs.getBoolean("Rotate Up Down", true)));
/*      */ 
/* 2129 */         Display3D.this.orbitTranslateXCheckBox.setSelected(appPrefs.getBoolean("Translate Left Right", systemPrefs.getBoolean("Translate Left Right", true)));
/*      */ 
/* 2131 */         Display3D.this.orbitTranslateYCheckBox.setSelected(appPrefs.getBoolean("Translate Up Down", systemPrefs.getBoolean("Translate Up Down", true)));
/*      */ 
/* 2133 */         Display3D.this.selectBehCheckBox.setSelected(appPrefs.getBoolean("Select", systemPrefs.getBoolean("Select", true)));
/*      */ 
/* 2136 */         Display3D.this.rotAxis_X.setValue(Display3D.this.rotAxis_X.newValue(appPrefs.getDouble("Auto Rotate X", systemPrefs.getDouble("Auto Rotate X", 0.0D))));
/*      */ 
/* 2138 */         Display3D.this.rotAxis_Y.setValue(Display3D.this.rotAxis_Y.newValue(appPrefs.getDouble("Auto Rotate Y", systemPrefs.getDouble("Auto Rotate Y", 0.0D))));
/*      */ 
/* 2140 */         Display3D.this.rotAxis_Z.setValue(Display3D.this.rotAxis_Z.newValue(appPrefs.getDouble("Auto Rotate Z", systemPrefs.getDouble("Auto Rotate Z", 0.0D))));
/*      */ 
/* 2142 */         Display3D.this.spinDuration.setValue(Display3D.this.spinDuration.newValue(appPrefs.getDouble("Auto Rotate Rate", systemPrefs.getDouble("Auto Rotate Rate", 0.0D))));
/*      */ 
/* 2145 */         Display3D.this.showAxesCheckBox.setSelected(appPrefs.getBoolean("Axes", systemPrefs.getBoolean("Axes", false)));
/*      */ 
/* 2147 */         Display3D.this.tooltips.setSelected(appPrefs.getBoolean("Tooltips", systemPrefs.getBoolean("Tooltips", false)));
/*      */ 
/* 2149 */         Display3D.this.showSpotlightCheckBox.setSelected(appPrefs.getBoolean("Spotlight", systemPrefs.getBoolean("Spotlight", true)));
/*      */ 
/* 2151 */         Display3D.this.showAmbientLightCheckBox.setSelected(appPrefs.getBoolean("Ambient Light", systemPrefs.getBoolean("Ambient Light", false)));
/*      */ 
/* 2153 */         Display3D.this.showBackgroundCheckBox.setSelected(appPrefs.getBoolean("Backdrop", systemPrefs.getBoolean("Backdrop", true)));
/*      */ 
/* 2156 */         int val = appPrefs.getInt("Draw Polygons", systemPrefs.getInt("Draw Polygons", Display3D.this.polyLine.isSelected() ? 1 : Display3D.this.polyPoint.isSelected() ? 0 : 2));
/*      */ 
/* 2160 */         if (val == 0) Display3D.this.polyPoint.setSelected(true);
/* 2161 */         else if (val == 1) Display3D.this.polyLine.setSelected(true);
/*      */         else {
/* 2163 */           Display3D.this.polyFill.setSelected(true);
/*      */         }
/* 2165 */         val = appPrefs.getInt("Draw Faces", systemPrefs.getInt("Draw Faces", Display3D.this.polyCullBack.isSelected() ? 1 : Display3D.this.polyCullNone.isSelected() ? 0 : 2));
/*      */ 
/* 2169 */         if (val == 0) Display3D.this.polyCullNone.setSelected(true);
/* 2170 */         else if (val == 1) Display3D.this.polyCullBack.setSelected(true);
/*      */         else
/* 2172 */           Display3D.this.polyCullFront.setSelected(true);
/*      */       }
/*      */       catch (AccessControlException e)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   class Portrayal3DHolder
/*      */   {
/*      */     Portrayal3D portrayal;
/*      */     String name;
/*      */     JCheckBoxMenuItem menuItem;
/*      */     int subgraphIndex;
/*  344 */     boolean visible = true;
/*      */ 
/*  345 */     public String toString() { return this.name; }
/*      */ 
/*      */     Portrayal3DHolder(Portrayal3D p, String n, boolean visible) {
/*  348 */       this.portrayal = p;
/*  349 */       this.name = n;
/*  350 */       this.visible = visible;
/*  351 */       this.menuItem = new JCheckBoxMenuItem(this.name, visible);
/*  352 */       this.subgraphIndex = Display3D.this.subgraphCount;
/*  353 */       Display3D.this.subgraphCount += 1;
/*      */ 
/*  355 */       this.menuItem.addActionListener(new ActionListener()
/*      */       {
/*      */         public void actionPerformed(ActionEvent e)
/*      */         {
/*  360 */           if (Display3D.Portrayal3DHolder.this.menuItem.isSelected())
/*      */           {
/*  362 */             Display3D.Portrayal3DHolder.this.visible = true;
/*  363 */             Display3D.this.portrayalSwitchMask.set(Display3D.Portrayal3DHolder.this.subgraphIndex);
/*      */           }
/*      */           else
/*      */           {
/*  367 */             Display3D.Portrayal3DHolder.this.visible = false;
/*  368 */             Display3D.this.portrayalSwitchMask.clear(Display3D.Portrayal3DHolder.this.subgraphIndex);
/*      */           }
/*  370 */           Display3D.this.portrayalSwitch.setChildMask(Display3D.this.portrayalSwitchMask);
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */ 
/*      */   static class LocalWindowListener extends WindowAdapter
/*      */   {
/*      */   }
/*      */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.display3d.Display3D
 * JD-Core Version:    0.6.2
 */