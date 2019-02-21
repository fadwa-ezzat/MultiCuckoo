/*      */ package sim.display;
/*      */ 
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.CardLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FileDialog;
/*      */ import java.awt.Font;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.GraphicsDevice;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ComponentAdapter;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FilenameFilter;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PrintStream;
/*      */ import java.io.StreamTokenizer;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.security.AccessControlException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import java.util.WeakHashMap;
/*      */ import java.util.prefs.Preferences;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.DefaultListCellRenderer;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuBar;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JSlider;
/*      */ import javax.swing.JSplitPane;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.JViewport;
/*      */ import javax.swing.ListCellRenderer;
/*      */ import javax.swing.ListModel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.ToolTipManager;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import sim.engine.Schedule;
/*      */ import sim.engine.SimState;
/*      */ import sim.engine.Steppable;
/*      */ import sim.engine.Stoppable;
/*      */ import sim.portrayal.Inspector;
/*      */ import sim.util.Bag;
/*      */ import sim.util.gui.AbstractScrollable;
/*      */ import sim.util.gui.HTMLBrowser;
/*      */ import sim.util.gui.LabelledList;
/*      */ import sim.util.gui.PropertyField;
/*      */ import sim.util.gui.Utilities;
/*      */ 
/*      */ public class Console extends JFrame
/*      */   implements Controller
/*      */ {
/*      */   public static final int DEFAULT_WIDTH = 380;
/*      */   public static final int DEFAULT_HEIGHT = 380;
/*      */   public static final int DEFAULT_GUTTER = 5;
/*      */   public static final int MAXIMUM_STEPS = 20;
/*      */   GUIState simulation;
/*   84 */   static Vector classNames = new Vector();
/*      */ 
/*   87 */   static Vector shortNames = new Vector();
/*      */   static boolean allowOtherClassNames;
/*   93 */   boolean newMenuAllowed = true;
/*      */ 
/*  102 */   boolean saveMenuAllowed = true;
/*      */ 
/*  112 */   boolean openMenuAllowed = true;
/*      */ 
/*  122 */   int preferredInspectorIndex = 0;
/*      */ 
/*  175 */   static final ImageIcon I_PLAY_ON = iconFor("Playing.png");
/*  176 */   static final ImageIcon I_PLAY_OFF = iconFor("NotPlaying.png");
/*  177 */   static final ImageIcon I_STOP_ON = iconFor("Stopped.png");
/*  178 */   static final ImageIcon I_STOP_OFF = iconFor("NotStopped.png");
/*  179 */   static final ImageIcon I_PAUSE_ON = iconFor("PauseOn.png");
/*  180 */   static final ImageIcon I_PAUSE_OFF = iconFor("PauseOff.png");
/*  181 */   static final ImageIcon I_STEP_ON = iconFor("StepOn.png");
/*  182 */   static final ImageIcon I_STEP_OFF = iconFor("StepOff.png");
/*      */   JComponent infoPanel;
/*      */   JLabel time;
/*      */   JSlider frameRateSlider;
/*      */   JLabel frameRateSliderText;
/*  200 */   boolean frameRateSliderChanging = false;
/*      */   JSlider stepSlider;
/*      */   JLabel stepSliderText;
/*  205 */   boolean stepSliderChanging = false;
/*      */   JCheckBox repeatButton;
/*      */   JButton stopButton;
/*      */   JButton playButton;
/*      */   JButton pauseButton;
/*      */   JTabbedPane tabPane;
/*      */   JList frameListDisplay;
/*      */   Vector frameList;
/*      */   PropertyField endField;
/*      */   PropertyField pauseField;
/*      */   PropertyField timeEndField;
/*      */   PropertyField timePauseField;
/*      */   PropertyField randomField;
/*      */   JMenuBar menuBar;
/*      */   JMenuItem newMenu;
/*      */   JMenuItem saveMenu;
/*      */   JMenuItem saveAsMenu;
/*      */   JMenuItem openMenu;
/*      */   JSplitPane innerInspectorPanel;
/*      */   JPanel inspectorPanel;
/*      */   JCheckBox incrementSeedOnStop;
/*      */   JList inspectorList;
/*      */   JPanel inspectorSwitcher;
/*      */   CardLayout inspectorCardLayout;
/*      */   JButton detatchButton;
/*      */   JButton removeButton;
/*      */   Inspector modelInspector;
/*      */   JScrollPane modelInspectorScrollPane;
/*      */   Box buttonBox;
/*      */   JComboBox timeBox;
/*  273 */   long randomSeed = 0L;
/*      */ 
/*  278 */   int numStepsPerStepButtonPress = 1;
/*      */   public static final String DEFAULT_PREFERENCES_KEY = "Console";
/*      */   public static final String DELAY_KEY = "Delay";
/*      */   public static final String STEPS_KEY = "Steps";
/*      */   public static final String AUTOMATIC_STOP_STEPS_KEY = "Automatically Stop at Step";
/*      */   public static final String AUTOMATIC_STOP_TIME_KEY = "Automatically Stop after Time";
/*      */   public static final String AUTOMATIC_PAUSE_STEPS_KEY = "Automatically Pause at Step";
/*      */   public static final String AUTOMATIC_PAUSE_TIME_KEY = "Automatically Pause after Time";
/*      */   public static final String INCREMENT_KEY = "Increment";
/*      */   public static final String REPEAT_KEY = "Repeat";
/*      */   public static final String NUM_DISPLAYS_KEY = "Number of Displays";
/*      */   public static final String DISPLAY_X_KEY = "Display X ";
/*      */   public static final String DISPLAY_Y_KEY = "Display Y ";
/*      */   public static final String DISPLAY_WIDTH_KEY = "Display Width ";
/*      */   public static final String DISPLAY_HEIGHT_KEY = "Display Height ";
/*      */   public static final String DISPLAY_SHOWN_KEY = "Display Shown ";
/*      */   public static final String CONSOLE_KEY = "-1";
/* 1338 */   boolean shouldRepeat = false;
/*      */ 
/* 1359 */   int threadPriority = 5;
/*      */ 
/* 1387 */   long whenShouldEnd = 9223372036854775807L;
/*      */ 
/* 1408 */   long whenShouldPause = 9223372036854775807L;
/*      */ 
/* 1429 */   double whenShouldEndTime = (1.0D / 0.0D);
/*      */ 
/* 1450 */   double whenShouldPauseTime = (1.0D / 0.0D);
/*      */ 
/* 1472 */   long playSleep = 0L;
/*      */   static final double MAX_FRAME_RATE = 8.0D;
/*      */   static final int MAX_FRAME_RATE_SLIDER_VALUE = 1024;
/*      */   Thread playThread;
/* 1532 */   final Object playThreadLock = new Object();
/*      */ 
/* 1535 */   boolean threadShouldStop = false;
/*      */   public static final int PS_STOPPED = 0;
/*      */   public static final int PS_PLAYING = 1;
/*      */   public static final int PS_PAUSED = 2;
/* 1565 */   int playState = 0;
/*      */ 
/* 1662 */   static boolean isQuitting = false;
/*      */ 
/* 1664 */   static final Object isQuittingLock = new Object();
/*      */ 
/* 1692 */   public static WeakHashMap allControllers = new WeakHashMap();
/*      */ 
/* 1694 */   boolean isClosing = false;
/*      */ 
/* 1696 */   final Object isClosingLock = new Object();
/*      */   static boolean sacrificial;
/* 1730 */   static JFrame aboutFrame = null;
/*      */ 
/* 1863 */   static Object classLock = new Object();
/* 1864 */   static boolean classListLoaded = false;
/*      */   public static final String ONLY_INDICATOR = "ONLY";
/*      */   public static final String NAME_INDICATOR = "NAME:";
/* 2095 */   File simulationFile = null;
/*      */ 
/* 2277 */   boolean requiresConfirmationToStop = false;
/*      */ 
/* 2515 */   double lastTime = -1.0D;
/*      */ 
/* 2517 */   double lastRate = 0.0D;
/* 2518 */   long lastSteps = 0L;
/*      */   static final int SHOWING_TIME = 0;
/*      */   static final int SHOWING_STEPS = 1;
/*      */   static final int SHOWING_TPS = 2;
/*      */   static final int SHOWING_NOTHING = 3;
/* 2523 */   int showing = 0;
/*      */   NumberFormat rateFormat;
/* 2530 */   String lastText = null;
/*      */ 
/* 2656 */   Runnable blocker = new Runnable() {
/* 2656 */     public void run() {  }  } ;
/*      */ 
/* 3007 */   Vector inspectorNames = new Vector();
/*      */ 
/* 3009 */   Vector inspectorStoppables = new Vector();
/*      */ 
/* 3011 */   Vector inspectorToolbars = new Vector();
/*      */ 
/* 3016 */   WeakHashMap allInspectors = new WeakHashMap();
/*      */ 
/*      */   public GUIState getSimulation()
/*      */   {
/*   81 */     return this.simulation;
/*      */   }
/*      */ 
/*      */   public void setNewMenuAllowed(boolean val)
/*      */   {
/*   96 */     this.newMenuAllowed = val;
/*   97 */     this.newMenu.setEnabled((this.newMenuAllowed) && (classNames.size() > 0));
/*      */   }
/*   99 */   public boolean isNewMenuAllowed() { return this.newMenuAllowed; }
/*      */ 
/*      */ 
/*      */   public void setSaveMenuAllowed(boolean val)
/*      */   {
/*  105 */     this.saveMenuAllowed = val;
/*  106 */     this.saveMenu.setEnabled(this.saveMenuAllowed);
/*  107 */     this.saveAsMenu.setEnabled(this.saveMenuAllowed);
/*      */   }
/*  109 */   public boolean isSaveMenuAllowed() { return this.saveMenuAllowed; }
/*      */ 
/*      */ 
/*      */   public void setOpenMenuAllowed(boolean val)
/*      */   {
/*  115 */     this.openMenuAllowed = val;
/*  116 */     this.openMenu.setEnabled(this.openMenuAllowed);
/*      */   }
/*  118 */   public boolean isOpenMenuAllowed() { return this.openMenuAllowed; }
/*      */ 
/*      */ 
/*      */   static ImageIcon iconFor(String name)
/*      */   {
/*  172 */     return new ImageIcon(Console.class.getResource(name));
/*      */   }
/*      */ 
/*      */   public Console(final GUIState simulation)
/*      */   {
/*  289 */     super(GUIState.getName(simulation.getClass()));
/*      */ 
/*  291 */     Color transparentBackground = new JPanel().getBackground();
/*      */ 
/*  293 */     this.simulation = simulation;
/*  294 */     this.randomSeed = simulation.state.seed();
/*      */ 
/*  296 */     this.rateFormat = NumberFormat.getInstance();
/*  297 */     this.rateFormat.setMaximumFractionDigits(3);
/*  298 */     this.rateFormat.setMinimumIntegerDigits(1);
/*      */ 
/*  303 */     this.buttonBox = new Box(0);
/*      */ 
/*  307 */     this.playButton = new JButton(I_PLAY_OFF);
/*  308 */     this.playButton.setPressedIcon(I_PLAY_ON);
/*  309 */     this.playButton.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  313 */         Console.this.pressPlay();
/*      */       }
/*      */     });
/*  316 */     this.playButton.setBorderPainted(false);
/*  317 */     this.playButton.setContentAreaFilled(false);
/*  318 */     this.playButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
/*      */ 
/*  321 */     this.buttonBox.add(this.playButton);
/*      */ 
/*  325 */     this.pauseButton = new JButton(I_PAUSE_OFF);
/*  326 */     this.pauseButton.setPressedIcon(I_PAUSE_ON);
/*  327 */     this.pauseButton.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  331 */         Console.this.pressPause();
/*      */       }
/*      */     });
/*  334 */     this.pauseButton.setBorderPainted(false);
/*  335 */     this.pauseButton.setContentAreaFilled(false);
/*  336 */     this.pauseButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
/*      */ 
/*  339 */     this.buttonBox.add(this.pauseButton);
/*      */ 
/*  342 */     this.stopButton = new JButton(I_STOP_OFF);
/*  343 */     this.stopButton.setIcon(I_STOP_ON);
/*  344 */     this.stopButton.setPressedIcon(I_STOP_OFF);
/*      */ 
/*  346 */     this.stopButton.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  350 */         Console.this.pressStopMaybe();
/*      */       }
/*      */     });
/*  353 */     this.stopButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
/*      */ 
/*  356 */     this.stopButton.setBorderPainted(false);
/*  357 */     this.stopButton.setContentAreaFilled(false);
/*  358 */     this.buttonBox.add(this.stopButton);
/*      */ 
/*  360 */     this.timeBox = new JComboBox(new Object[] { "Time", "Steps", "Rate", "None" });
/*  361 */     this.timeBox.setSelectedIndex(0);
/*  362 */     this.timeBox.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  366 */         synchronized (Console.this.time)
/*      */         {
/*  368 */           Console.this.showing = Console.this.timeBox.getSelectedIndex();
/*      */         }
/*  370 */         Console.this.updateTime();
/*      */       }
/*      */     });
/*  375 */     Box timeBox1 = new Box(1);
/*  376 */     timeBox1.add(Box.createGlue());
/*  377 */     timeBox1.add(this.timeBox);
/*  378 */     timeBox1.add(Box.createGlue());
/*      */ 
/*  381 */     this.time = new JLabel("");
/*      */ 
/*  383 */     this.time.setPreferredSize(new JLabel("8888888888888888888").getPreferredSize());
/*      */ 
/*  385 */     this.time.setMinimumSize(new JLabel("888").getMinimumSize());
/*  386 */     this.buttonBox.add(this.time);
/*  387 */     this.buttonBox.add(Box.createGlue());
/*  388 */     this.buttonBox.add(new JLabel(" "));
/*  389 */     this.buttonBox.add(timeBox1);
/*  390 */     if (Display2D.isMacOSX()) this.buttonBox.add(new JLabel("    "));
/*      */ 
/*  397 */     this.infoPanel = new HTMLBrowser(GUIState.getInfo(simulation.getClass()));
/*      */ 
/*  401 */     this.frameList = new Vector();
/*  402 */     this.frameListDisplay = new JList(this.frameList);
/*      */ 
/*  404 */     this.frameListDisplay.addMouseListener(new MouseAdapter()
/*      */     {
/*      */       public void mouseClicked(MouseEvent e)
/*      */       {
/*  408 */         if (e.getClickCount() == 2)
/*      */         {
/*  410 */           int index = Console.this.frameListDisplay.locationToIndex(e.getPoint());
/*  411 */           JFrame frame = (JFrame)Console.this.frameListDisplay.getModel().getElementAt(index);
/*      */ 
/*  413 */           frame.setVisible(!frame.isVisible());
/*  414 */           Console.this.frameListDisplay.repaint();
/*      */         }
/*      */       }
/*      */     });
/*  419 */     this.frameListDisplay.setCellRenderer(new ListCellRenderer()
/*      */     {
/*  424 */       protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
/*      */ 
/*      */       public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/*  427 */         JLabel renderer = (JLabel)this.defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
/*  428 */         JFrame frame = (JFrame)value;
/*  429 */         if (frame.isVisible())
/*  430 */           renderer.setForeground(Color.black);
/*      */         else
/*  432 */           renderer.setForeground(Color.gray);
/*  433 */         renderer.setText(frame.getTitle());
/*  434 */         return renderer;
/*      */       }
/*      */     });
/*  439 */     Box b = new Box(0);
/*  440 */     JButton button = new JButton("Show All");
/*  441 */     button.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  445 */         Console.this.showAllFrames();
/*      */       }
/*      */     });
/*  448 */     b.add(button);
/*  449 */     button = new JButton("Show");
/*  450 */     button.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  454 */         Console.this.showSelectedFrames();
/*      */       }
/*      */     });
/*  457 */     b.add(button);
/*  458 */     button = new JButton("Hide");
/*  459 */     button.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  463 */         Console.this.hideSelectedFrames();
/*      */       }
/*      */     });
/*  466 */     b.add(button);
/*  467 */     button = new JButton("Hide All");
/*  468 */     button.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  472 */         Console.this.hideAllFrames();
/*      */       }
/*      */     });
/*  475 */     b.add(button);
/*  476 */     b.add(Box.createGlue());
/*      */ 
/*  480 */     Box displayPrefs = new Box(0);
/*  481 */     JButton saveDisplayPreferences = new JButton("Save");
/*  482 */     JButton resetDisplayPreferences = new JButton("Reset");
/*  483 */     JLabel displayLabel = new JLabel(" Window Position Defaults: ");
/*  484 */     displayLabel.putClientProperty("JComponent.sizeVariant", "mini");
/*  485 */     displayPrefs.add(Box.createGlue());
/*  486 */     displayPrefs.add(displayLabel);
/*  487 */     displayPrefs.add(saveDisplayPreferences);
/*  488 */     displayPrefs.add(resetDisplayPreferences);
/*      */ 
/*  490 */     saveDisplayPreferences.putClientProperty("JComponent.sizeVariant", "mini");
/*  491 */     saveDisplayPreferences.putClientProperty("JButton.buttonType", "bevel");
/*  492 */     saveDisplayPreferences.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  496 */         Console.this.saveDisplayPreferences();
/*      */       }
/*      */     });
/*  499 */     resetDisplayPreferences.putClientProperty("JComponent.sizeVariant", "mini");
/*  500 */     resetDisplayPreferences.putClientProperty("JButton.buttonType", "bevel");
/*  501 */     resetDisplayPreferences.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  505 */         Console.this.resetDisplayPreferences();
/*      */       }
/*      */     });
/*  508 */     Box combinedPanel = new Box(1);
/*  509 */     combinedPanel.add(b);
/*  510 */     combinedPanel.add(displayPrefs);
/*      */ 
/*  512 */     JPanel frameListPanel = new JPanel();
/*  513 */     frameListPanel.setLayout(new BorderLayout());
/*  514 */     frameListPanel.add(new JScrollPane(this.frameListDisplay), "Center");
/*  515 */     frameListPanel.add(combinedPanel, "South");
/*      */ 
/*  524 */     LabelledList controlPanel = new LabelledList()
/*      */     {
/*  526 */       Insets insets = new Insets(2, 4, 2, 4);
/*      */ 
/*      */       public Insets getInsets() {
/*  529 */         return this.insets;
/*      */       }
/*      */     };
/*  533 */     this.frameRateSlider = new JSlider(0, 1024, 0);
/*  534 */     this.frameRateSlider.addChangeListener(new ChangeListener()
/*      */     {
/*      */       public void stateChanged(ChangeEvent e)
/*      */       {
/*  538 */         Console.this.setPlaySleep(Console.this.sliderToFrameRate(Console.this.frameRateSlider.getValue()));
/*      */       }
/*      */     });
/*  541 */     b = new Box(0)
/*      */     {
/*  543 */       Insets insets = new Insets(2, 4, 2, 4);
/*      */ 
/*      */       public Insets getInsets() {
/*  546 */         return this.insets;
/*      */       }
/*      */     };
/*  549 */     b.add(this.frameRateSlider);
/*  550 */     this.frameRateSliderText = new JLabel("0");
/*  551 */     this.frameRateSliderText.setMinimumSize(new JLabel("88.88").getMinimumSize());
/*  552 */     this.frameRateSliderText.setPreferredSize(new JLabel("88.88").getPreferredSize());
/*  553 */     b.add(this.frameRateSliderText);
/*  554 */     controlPanel.addLabelled("Delay (Sec/Step) ", b);
/*      */ 
/*  558 */     this.stepSlider = new JSlider(1, 20, 1);
/*  559 */     this.stepSlider.addChangeListener(new ChangeListener()
/*      */     {
/*      */       public void stateChanged(ChangeEvent e)
/*      */       {
/*  563 */         Console.this.setNumStepsPerStepButtonPress(Console.this.stepSlider.getValue());
/*      */       }
/*      */     });
/*  566 */     b = new Box(0)
/*      */     {
/*  568 */       Insets insets = new Insets(2, 4, 2, 4);
/*      */ 
/*      */       public Insets getInsets() {
/*  571 */         return this.insets;
/*      */       }
/*      */     };
/*  574 */     b.add(this.stepSlider);
/*  575 */     this.stepSliderText = new JLabel("1");
/*  576 */     this.stepSliderText.setMinimumSize(new JLabel("8.888").getMinimumSize());
/*  577 */     this.stepSliderText.setPreferredSize(new JLabel("8.888").getPreferredSize());
/*  578 */     b.add(this.stepSliderText);
/*  579 */     controlPanel.addLabelled("Steps per Step-Button ", b);
/*      */ 
/*  583 */     this.endField = new PropertyField("")
/*      */     {
/*      */       public String newValue(String value)
/*      */       {
/*  587 */         long l = -1L;
/*      */         try
/*      */         {
/*  590 */           l = Long.parseLong(value);
/*  591 */           if (l < 0L)
/*  592 */             l = 9223372036854775807L;
/*      */         }
/*      */         catch (NumberFormatException num)
/*      */         {
/*  596 */           l = 9223372036854775807L;
/*      */         }
/*  598 */         Console.this.setWhenShouldEnd(l);
/*  599 */         if (l == 9223372036854775807L) {
/*  600 */           return "";
/*      */         }
/*  602 */         return "" + l;
/*      */       }
/*      */     };
/*  606 */     this.endField.getField().setColumns(20);
/*  607 */     this.endField.setMaximumSize(this.endField.getField().getPreferredSize());
/*  608 */     this.endField.setPreferredSize(this.endField.getField().getPreferredSize());
/*      */ 
/*  610 */     b = new Box(0)
/*      */     {
/*  612 */       Insets insets = new Insets(2, 4, 2, 4);
/*      */ 
/*      */       public Insets getInsets() {
/*  615 */         return this.insets;
/*      */       }
/*      */     };
/*  618 */     b.add(this.endField);
/*  619 */     controlPanel.addLabelled("Automatically Stop at Step ", b);
/*      */ 
/*  621 */     this.timeEndField = new PropertyField("")
/*      */     {
/*      */       public String newValue(String value)
/*      */       {
/*  625 */         double l = -1.0D;
/*      */         try
/*      */         {
/*  628 */           l = Double.parseDouble(value);
/*  629 */           if ((l < 0.0D) || (l != l))
/*  630 */             l = (1.0D / 0.0D);
/*      */         }
/*      */         catch (NumberFormatException num)
/*      */         {
/*  634 */           l = (1.0D / 0.0D);
/*      */         }
/*  636 */         Console.this.setWhenShouldEndTime(l);
/*  637 */         if (l == (1.0D / 0.0D)) {
/*  638 */           return "";
/*      */         }
/*  640 */         return "" + l;
/*      */       }
/*      */     };
/*  644 */     this.timeEndField.getField().setColumns(20);
/*  645 */     this.timeEndField.setMaximumSize(this.endField.getField().getPreferredSize());
/*  646 */     this.timeEndField.setPreferredSize(this.endField.getField().getPreferredSize());
/*      */ 
/*  648 */     b = new Box(0)
/*      */     {
/*  650 */       Insets insets = new Insets(2, 4, 2, 4);
/*      */ 
/*      */       public Insets getInsets() {
/*  653 */         return this.insets;
/*      */       }
/*      */     };
/*  656 */     b.add(this.timeEndField);
/*  657 */     controlPanel.addLabelled("Automatically Stop after Time ", b);
/*      */ 
/*  661 */     this.pauseField = new PropertyField("")
/*      */     {
/*      */       public String newValue(String value)
/*      */       {
/*  665 */         long l = 9223372036854775807L;
/*      */         try
/*      */         {
/*  668 */           l = Long.parseLong(value);
/*  669 */           if (l < 0L)
/*  670 */             l = 9223372036854775807L;
/*      */         }
/*      */         catch (NumberFormatException num)
/*      */         {
/*  674 */           l = 9223372036854775807L;
/*      */         }
/*  676 */         Console.this.setWhenShouldPause(l);
/*  677 */         if (l == 9223372036854775807L) {
/*  678 */           return "";
/*      */         }
/*  680 */         return "" + l;
/*      */       }
/*      */     };
/*  684 */     this.pauseField.getField().setColumns(20);
/*  685 */     this.pauseField.setMaximumSize(this.pauseField.getField().getPreferredSize());
/*  686 */     this.pauseField.setPreferredSize(this.pauseField.getField().getPreferredSize());
/*      */ 
/*  688 */     b = new Box(0)
/*      */     {
/*  690 */       Insets insets = new Insets(2, 4, 2, 4);
/*      */ 
/*      */       public Insets getInsets() {
/*  693 */         return this.insets;
/*      */       }
/*      */     };
/*  696 */     b.add(this.pauseField);
/*  697 */     controlPanel.addLabelled("Automatically Pause at Step ", b);
/*      */ 
/*  701 */     this.timePauseField = new PropertyField("")
/*      */     {
/*      */       public String newValue(String value)
/*      */       {
/*  705 */         double l = -1.0D;
/*      */         try
/*      */         {
/*  708 */           l = Double.parseDouble(value);
/*  709 */           if ((l < 0.0D) || (l != l))
/*  710 */             l = (1.0D / 0.0D);
/*      */         }
/*      */         catch (NumberFormatException num)
/*      */         {
/*  714 */           l = (1.0D / 0.0D);
/*      */         }
/*  716 */         Console.this.setWhenShouldPauseTime(l);
/*  717 */         if (l == (1.0D / 0.0D)) {
/*  718 */           return "";
/*      */         }
/*  720 */         return "" + l;
/*      */       }
/*      */     };
/*  724 */     this.timePauseField.getField().setColumns(20);
/*  725 */     this.timePauseField.setMaximumSize(this.pauseField.getField().getPreferredSize());
/*  726 */     this.timePauseField.setPreferredSize(this.pauseField.getField().getPreferredSize());
/*      */ 
/*  728 */     b = new Box(0)
/*      */     {
/*  730 */       Insets insets = new Insets(2, 4, 2, 4);
/*      */ 
/*      */       public Insets getInsets() {
/*  733 */         return this.insets;
/*      */       }
/*      */     };
/*  736 */     b.add(this.timePauseField);
/*  737 */     controlPanel.addLabelled("Automatically Pause After Time ", b);
/*      */ 
/*  740 */     this.randomField = new PropertyField("")
/*      */     {
/*  742 */       boolean lock = false;
/*      */ 
/*      */       public String newValue(String value) {
/*  745 */         if (this.lock) return value;
/*  746 */         this.lock = true;
/*      */         try
/*      */         {
/*  749 */           long l = Long.parseLong(value);
/*  750 */           if ((l > 2147483647L) || (l < -2147483648L)) {
/*  751 */             Utilities.inform("Seed Will Be Truncated", "The random number generator only uses 32 bits of a given seed.  You've specified a longer seed than this.Not all the bits of this seed will be used.", Console.this);
/*      */           }
/*      */ 
/*  754 */           l = (int)l;
/*  755 */           Console.this.randomSeed = l;
/*      */ 
/*  757 */           this.lock = false;
/*  758 */           return "" + l;
/*      */         }
/*      */         catch (NumberFormatException num)
/*      */         {
/*  762 */           this.lock = false;
/*  763 */         }return getValue();
/*      */       }
/*      */     };
/*  769 */     this.randomField.getField().setColumns(20);
/*  770 */     this.randomField.setMaximumSize(this.randomField.getField().getPreferredSize());
/*  771 */     this.randomField.setPreferredSize(this.randomField.getField().getPreferredSize());
/*  772 */     this.randomField.setValue("" + this.randomSeed);
/*      */ 
/*  774 */     b = new Box(0)
/*      */     {
/*  776 */       Insets insets = new Insets(2, 4, 2, 4);
/*      */ 
/*      */       public Insets getInsets() {
/*  779 */         return this.insets;
/*      */       }
/*      */     };
/*  782 */     b.add(this.randomField);
/*  783 */     controlPanel.addLabelled("Random Number Seed ", b);
/*      */ 
/*  787 */     b = new Box(0)
/*      */     {
/*  789 */       Insets insets = new Insets(2, 4, 2, 4);
/*      */ 
/*      */       public Insets getInsets() {
/*  792 */         return this.insets;
/*      */       }
/*      */     };
/*  795 */     this.incrementSeedOnStop = new JCheckBox();
/*  796 */     this.incrementSeedOnStop.setSelected(true);
/*  797 */     b.add(this.incrementSeedOnStop);
/*  798 */     controlPanel.addLabelled("Increment Seed on Stop ", b);
/*      */ 
/*  801 */     b = new Box(0)
/*      */     {
/*  803 */       Insets insets = new Insets(2, 4, 2, 4);
/*      */ 
/*      */       public Insets getInsets() {
/*  806 */         return this.insets;
/*      */       }
/*      */     };
/*  809 */     this.repeatButton = new JCheckBox();
/*  810 */     this.repeatButton.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  814 */         Console.this.setShouldRepeat(Console.this.repeatButton.isSelected());
/*      */       }
/*      */     });
/*  818 */     this.repeatButton.setSelected(false);
/*  819 */     b.add(this.repeatButton);
/*  820 */     controlPanel.addLabelled("Repeat Play on Stop ", b);
/*      */ 
/*  825 */     Box defaults = new Box(0);
/*  826 */     JLabel defaultsLabel = new JLabel(" Save as Defaults for ");
/*  827 */     defaultsLabel.putClientProperty("JComponent.sizeVariant", "mini");
/*  828 */     defaults.add(Box.createGlue());
/*  829 */     defaults.add(defaultsLabel);
/*  830 */     JButton appPreferences = new JButton("Simulation");
/*  831 */     JButton systemPreferences = new JButton("MASON");
/*  832 */     defaults.add(appPreferences);
/*  833 */     defaults.add(systemPreferences);
/*      */ 
/*  835 */     systemPreferences.putClientProperty("JComponent.sizeVariant", "mini");
/*  836 */     systemPreferences.putClientProperty("JButton.buttonType", "bevel");
/*  837 */     systemPreferences.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  841 */         Console.this.savePreferences(false);
/*      */ 
/*  844 */         Prefs.removeAppPreferences(simulation, "Console");
/*      */       }
/*      */     });
/*  848 */     appPreferences.putClientProperty("JComponent.sizeVariant", "mini");
/*  849 */     appPreferences.putClientProperty("JButton.buttonType", "bevel");
/*  850 */     appPreferences.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  854 */         Console.this.savePreferences(true);
/*      */       }
/*      */     });
/*  862 */     JPanel lowerPane = new JPanel();
/*  863 */     lowerPane.setLayout(new BorderLayout());
/*  864 */     this.removeButton = new JButton("Empty List");
/*  865 */     this.removeButton.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  869 */         Console.this.removeAllInspectors(false);
/*      */       }
/*      */     });
/*  872 */     this.removeButton.setEnabled(false);
/*  873 */     this.detatchButton = new JButton("Detatch");
/*  874 */     this.detatchButton.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  878 */         Console.this.detatchInspector();
/*      */       }
/*      */     });
/*  881 */     this.detatchButton.setEnabled(false);
/*  882 */     Box removeButtonBox = new Box(0);
/*  883 */     removeButtonBox.add(this.removeButton);
/*  884 */     removeButtonBox.add(this.detatchButton);
/*  885 */     removeButtonBox.add(Box.createGlue());
/*      */ 
/*  888 */     this.inspectorList = new JList(this.inspectorNames);
/*  889 */     this.inspectorList.setSelectionMode(0);
/*  890 */     this.inspectorList.addListSelectionListener(new ListSelectionListener()
/*      */     {
/*      */       public void valueChanged(ListSelectionEvent e)
/*      */       {
/*  894 */         if ((!e.getValueIsAdjusting()) && (Console.this.inspectorList.getSelectedIndex() != -1))
/*      */         {
/*  897 */           Console.this.inspectorCardLayout.show(Console.this.inspectorSwitcher, "" + Console.this.inspectorList.getSelectedIndex());
/*  898 */           Console.this.preferredInspectorIndex = Console.this.inspectorList.getSelectedIndex();
/*      */         }
/*      */       }
/*      */     });
/*  902 */     JScrollPane listPane = new JScrollPane(this.inspectorList);
/*  903 */     listPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*      */ 
/*  906 */     this.inspectorSwitcher = new JPanel();
/*  907 */     this.inspectorSwitcher.setLayout(this.inspectorCardLayout = new CardLayout());
/*      */ 
/*  910 */     this.innerInspectorPanel = new JSplitPane(0, true, listPane, this.inspectorSwitcher);
/*      */ 
/*  912 */     this.innerInspectorPanel.setDividerLocation(60);
/*  913 */     this.inspectorPanel = new JPanel();
/*  914 */     this.inspectorPanel.setLayout(new BorderLayout());
/*  915 */     this.inspectorPanel.add(this.innerInspectorPanel, "Center");
/*  916 */     this.inspectorPanel.add(removeButtonBox, "South");
/*      */ 
/*  923 */     getContentPane().setLayout(new BorderLayout());
/*  924 */     getContentPane().add(this.buttonBox, "South");
/*  925 */     this.tabPane = new JTabbedPane()
/*      */     {
/*      */       public Dimension getMinimumSize()
/*      */       {
/*  930 */         return new Dimension(super.getMinimumSize().width, 0);
/*      */       }
/*      */     };
/*  934 */     this.tabPane.putClientProperty("Quaqua.TabbedPane.contentBorderPainted", Boolean.FALSE);
/*  935 */     this.tabPane.addTab("About", this.infoPanel);
/*      */ 
/*  938 */     AbstractScrollable consoleScrollable = new AbstractScrollable() {
/*      */       public boolean getScrollableTracksViewportWidth() {
/*  940 */         return true;
/*      */       }
/*      */     };
/*  942 */     consoleScrollable.setLayout(new BorderLayout());
/*  943 */     consoleScrollable.add(controlPanel, "Center");
/*      */ 
/*  945 */     JScrollPane controlScroll = new JScrollPane(consoleScrollable)
/*      */     {
/*  947 */       Insets insets = new Insets(0, 0, 0, 0);
/*      */ 
/*      */       public Insets getInsets() {
/*  950 */         return this.insets;
/*      */       }
/*      */     };
/*  953 */     controlScroll.getViewport().setBackground(transparentBackground);
/*      */ 
/*  955 */     JPanel upperPane = new JPanel();
/*  956 */     upperPane.setLayout(new BorderLayout());
/*  957 */     upperPane.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0, 64)));
/*  958 */     upperPane.add(controlScroll, "Center");
/*  959 */     JPanel outerPane = new JPanel();
/*  960 */     outerPane.setLayout(new BorderLayout());
/*  961 */     outerPane.add(upperPane, "Center");
/*  962 */     outerPane.add(defaults, "South");
/*      */ 
/*  964 */     this.tabPane.addTab("Console", outerPane);
/*  965 */     this.tabPane.addTab("Displays", frameListPanel);
/*  966 */     this.tabPane.addTab("Inspectors", this.inspectorPanel);
/*      */ 
/*  968 */     buildModelInspector();
/*      */ 
/*  971 */     getContentPane().add(this.tabPane, "Center");
/*      */ 
/*  979 */     this.menuBar = new JMenuBar();
/*  980 */     setJMenuBar(this.menuBar);
/*      */ 
/*  984 */     JMenu fileMenu = new JMenu("File");
/*  985 */     this.menuBar.add(fileMenu);
/*      */ 
/*  987 */     buildClassList();
/*      */ 
/*  989 */     this.newMenu = new JMenuItem("New Simulation...");
/*  990 */     setNewMenuAllowed(isNewMenuAllowed());
/*  991 */     this.newMenu.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/*  995 */         Console.this.doNew();
/*      */       }
/*      */     });
/*  998 */     fileMenu.add(this.newMenu);
/*  999 */     this.openMenu = new JMenuItem("Open...");
/* 1000 */     if (SimApplet.isApplet) this.openMenu.setEnabled(false);
/* 1001 */     this.openMenu.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 1005 */         Console.this.doOpen();
/*      */       }
/*      */     });
/* 1008 */     fileMenu.add(this.openMenu);
/* 1009 */     this.saveMenu = new JMenuItem("Save");
/* 1010 */     if (SimApplet.isApplet) this.saveMenu.setEnabled(false);
/* 1011 */     this.saveMenu.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 1015 */         synchronized (simulation.state.schedule) { Console.this.doSave(); }
/*      */ 
/*      */       }
/*      */     });
/* 1018 */     fileMenu.add(this.saveMenu);
/* 1019 */     this.saveAsMenu = new JMenuItem("Save As...");
/* 1020 */     if (SimApplet.isApplet) this.saveAsMenu.setEnabled(false);
/* 1021 */     this.saveAsMenu.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 1025 */         synchronized (simulation.state.schedule) { Console.this.doSaveAs(); }
/*      */ 
/*      */       }
/*      */     });
/* 1028 */     fileMenu.add(this.saveAsMenu);
/*      */ 
/* 1030 */     JMenuItem _about = new JMenuItem("About MASON");
/* 1031 */     _about.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 1035 */         Console.this.doAbout();
/*      */       }
/*      */     });
/* 1038 */     fileMenu.add(_about);
/*      */ 
/* 1040 */     JMenuItem quit = new JMenuItem("Quit");
/* 1041 */     quit.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e)
/*      */       {
/* 1045 */         Console.doQuit();
/*      */       }
/*      */     });
/* 1048 */     fileMenu.add(quit);
/*      */ 
/* 1052 */     addComponentListener(new ComponentAdapter()
/*      */     {
/*      */       public void componentResized(ComponentEvent e)
/*      */       {
/* 1056 */         Utilities.doEnsuredRepaint(Console.this.getContentPane());
/* 1057 */         Utilities.doEnsuredRepaint(Console.this.menuBar);
/*      */       }
/*      */     });
/* 1062 */     addWindowListener(new WindowAdapter()
/*      */     {
/*      */       public void windowClosing(WindowEvent e)
/*      */       {
/* 1066 */         Console.this.doClose();
/*      */       }
/*      */     });
/* 1070 */     setDefaultCloseOperation(0);
/*      */ 
/* 1074 */     setSize(380, 380);
/* 1075 */     Point defLoc = getLocation();
/* 1076 */     setLocation(-10000, -10000);
/* 1077 */     setResizable(true);
/* 1078 */     repaint();
/*      */ 
/* 1083 */     allControllers.put(this, this);
/*      */ 
/* 1090 */     invokeInSwing(new Runnable() { public void run() { try { simulation.init(Console.this); } catch (Exception e) { e.printStackTrace(); }
/*      */ 
/*      */       }
/*      */     });
/*      */     try
/*      */     {
/* 1096 */       Rectangle screen = new Rectangle(0, 0, 0, 0);
/* 1097 */       GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
/* 1098 */       for (int i = 0; i < screens.length; i++) {
/* 1099 */         screen = screen.union(screens[i].getDefaultConfiguration().getBounds());
/*      */       }
/* 1101 */       Preferences appPrefs = Prefs.getAppPreferences(simulation, "Console");
/* 1102 */       if (appPrefs.getInt("Number of Displays", -1) != -1)
/*      */       {
/* 1108 */         Iterator iter = this.frameList.iterator();
/* 1109 */         while (iter.hasNext()) {
/* 1110 */           ((Component)iter.next()).setVisible(false);
/*      */         }
/*      */ 
/* 1113 */         Iterator i = this.frameList.iterator();
/* 1114 */         int count = 0;
/* 1115 */         while (i.hasNext())
/*      */         {
/* 1117 */           Component c = (Component)i.next();
/* 1118 */           Rectangle bounds = c.getBounds();
/* 1119 */           bounds.x = appPrefs.getInt("Display X " + count, bounds.x);
/* 1120 */           bounds.y = appPrefs.getInt("Display Y " + count, bounds.y);
/* 1121 */           bounds.width = appPrefs.getInt("Display Width " + count, bounds.width);
/* 1122 */           bounds.height = appPrefs.getInt("Display Height " + count, bounds.height);
/* 1123 */           c.setBounds(bounds.intersection(screen));
/* 1124 */           c.setVisible(appPrefs.getBoolean("Display Shown " + count, c.isVisible()));
/* 1125 */           count++;
/*      */         }
/* 1127 */         Rectangle bounds = getBounds();
/* 1128 */         bounds.x = appPrefs.getInt("Display X -1", bounds.x);
/* 1129 */         bounds.y = appPrefs.getInt("Display Y -1", bounds.y);
/* 1130 */         bounds.width = appPrefs.getInt("Display Width -1", bounds.width);
/* 1131 */         bounds.height = appPrefs.getInt("Display Height -1", bounds.height);
/* 1132 */         setBounds(bounds.intersection(screen));
/*      */       }
/*      */       else
/*      */       {
/* 1138 */         Point loc = getLocation();
/* 1139 */         if ((loc.x == -10000) && (loc.y == -10000))
/*      */         {
/* 1143 */           Rectangle bounds = new Rectangle(0, 0, 0, 0);
/* 1144 */           Iterator i = this.frameList.iterator();
/* 1145 */           while (i.hasNext())
/* 1146 */             bounds = bounds.union(((Component)i.next()).getBounds());
/* 1147 */           if (bounds.width + getWidth() + 5 <= screen.width)
/* 1148 */             setLocation(bounds.width + 5, defLoc.y);
/* 1149 */           else setLocation(defLoc);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (AccessControlException e)
/*      */     {
/*      */     }
/*      */ 
/* 1157 */     invokeInSwing(new Runnable() { public void run() { Console.this.resetToPreferences(); }
/*      */ 
/*      */     });
/*      */   }
/*      */ 
/*      */   void invokeInSwing(Runnable runnable)
/*      */   {
/* 1164 */     if (SwingUtilities.isEventDispatchThread()) runnable.run(); else
/*      */       try
/*      */       {
/* 1167 */         SwingUtilities.invokeAndWait(runnable);
/*      */       }
/*      */       catch (InterruptedException e)
/*      */       {
/*      */       }
/*      */       catch (InvocationTargetException e)
/*      */       {
/*      */       }
/*      */   }
/*      */ 
/*      */   void buildModelInspector() {
/* 1178 */     if (this.modelInspectorScrollPane != null)
/* 1179 */       this.tabPane.remove(this.modelInspectorScrollPane);
/* 1180 */     this.modelInspector = this.simulation.getInspector();
/* 1181 */     if (this.modelInspector != null)
/*      */     {
/* 1183 */       this.modelInspectorScrollPane = new JScrollPane(this.modelInspector)
/*      */       {
/* 1185 */         Insets insets = new Insets(0, 0, 0, 0);
/*      */ 
/*      */         public Insets getInsets() {
/* 1188 */           return this.insets;
/*      */         }
/*      */       };
/* 1191 */       this.modelInspectorScrollPane.getViewport().setBackground(new JPanel().getBackground());
/*      */ 
/* 1193 */       this.tabPane.addTab("Model", this.modelInspectorScrollPane);
/*      */     }
/* 1195 */     this.tabPane.revalidate();
/*      */   }
/*      */ 
/*      */   void savePreferences(boolean appPreferences)
/*      */   {
/* 1213 */     Preferences prefs = null;
/*      */     try
/*      */     {
/* 1216 */       if (appPreferences)
/* 1217 */         prefs = Prefs.getAppPreferences(this.simulation, "Console");
/*      */       else {
/* 1219 */         prefs = Prefs.getGlobalPreferences("Console");
/*      */       }
/* 1221 */       prefs.putInt("Delay", this.frameRateSlider.getValue());
/* 1222 */       prefs.putInt("Steps", getNumStepsPerStepButtonPress());
/* 1223 */       prefs.put("Automatically Stop at Step", this.endField.getValue());
/* 1224 */       prefs.put("Automatically Stop after Time", this.timeEndField.getValue());
/* 1225 */       prefs.put("Automatically Pause at Step", this.pauseField.getValue());
/* 1226 */       prefs.put("Automatically Pause after Time", this.timePauseField.getValue());
/* 1227 */       prefs.putBoolean("Increment", this.incrementSeedOnStop.isSelected());
/* 1228 */       prefs.putBoolean("Repeat", this.repeatButton.isSelected());
/*      */ 
/* 1230 */       if (!Prefs.save(prefs))
/* 1231 */         Utilities.inform("Preferences Cannot be Saved", "Your Java system can't save preferences.  Perhaps this is an applet?", this);
/*      */     }
/*      */     catch (AccessControlException e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   void resetToPreferences() {
/*      */     try {
/* 1240 */       Preferences systemPrefs = Prefs.getGlobalPreferences("Console");
/* 1241 */       Preferences appPrefs = Prefs.getAppPreferences(this.simulation, "Console");
/* 1242 */       this.frameRateSlider.setValue(appPrefs.getInt("Delay", systemPrefs.getInt("Delay", this.frameRateSlider.getValue())));
/* 1243 */       setNumStepsPerStepButtonPress(appPrefs.getInt("Steps", systemPrefs.getInt("Steps", this.stepSlider.getValue())));
/* 1244 */       this.endField.setValue(this.endField.newValue(appPrefs.get("Automatically Stop at Step", systemPrefs.get("Automatically Stop at Step", this.endField.getValue()))));
/* 1245 */       this.timeEndField.setValue(this.timeEndField.newValue(appPrefs.get("Automatically Stop after Time", systemPrefs.get("Automatically Stop after Time", this.timeEndField.getValue()))));
/* 1246 */       this.pauseField.setValue(this.pauseField.newValue(appPrefs.get("Automatically Pause at Step", systemPrefs.get("Automatically Pause at Step", this.pauseField.getValue()))));
/* 1247 */       this.timePauseField.setValue(this.timePauseField.newValue(appPrefs.get("Automatically Pause after Time", systemPrefs.get("Automatically Pause after Time", this.timePauseField.getValue()))));
/* 1248 */       this.incrementSeedOnStop.setSelected(appPrefs.getBoolean("Increment", systemPrefs.getBoolean("Increment", this.incrementSeedOnStop.isSelected())));
/* 1249 */       this.repeatButton.setSelected(appPrefs.getBoolean("Repeat", systemPrefs.getBoolean("Repeat", this.repeatButton.isSelected())));
/* 1250 */       setShouldRepeat(this.repeatButton.isSelected());
/*      */     }
/*      */     catch (AccessControlException e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   void saveDisplayPreferences()
/*      */   {
/*      */     try
/*      */     {
/* 1272 */       resetDisplayPreferences();
/* 1273 */       Preferences appPrefs = Prefs.getAppPreferences(this.simulation, "Console");
/* 1274 */       Iterator i = this.frameList.iterator();
/* 1275 */       int count = 0;
/* 1276 */       while (i.hasNext())
/*      */       {
/* 1278 */         Component c = (Component)i.next();
/* 1279 */         Rectangle bounds = c.getBounds();
/* 1280 */         appPrefs.putInt("Display X " + count, bounds.x);
/* 1281 */         appPrefs.putInt("Display Y " + count, bounds.y);
/* 1282 */         appPrefs.putInt("Display Width " + count, bounds.width);
/* 1283 */         appPrefs.putInt("Display Height " + count, bounds.height);
/* 1284 */         appPrefs.putBoolean("Display Shown " + count, c.isVisible());
/* 1285 */         count++;
/*      */       }
/* 1287 */       Rectangle bounds = getBounds();
/* 1288 */       appPrefs.putInt("Display X -1", bounds.x);
/* 1289 */       appPrefs.putInt("Display Y -1", bounds.y);
/* 1290 */       appPrefs.putInt("Display Width -1", bounds.width);
/* 1291 */       appPrefs.putInt("Display Height -1", bounds.height);
/* 1292 */       appPrefs.putBoolean("Display Shown -1", true);
/*      */ 
/* 1294 */       appPrefs.putInt("Number of Displays", count);
/*      */     }
/*      */     catch (AccessControlException e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   void resetDisplayPreferences() {
/*      */     try {
/* 1303 */       Preferences appPrefs = Prefs.getAppPreferences(this.simulation, "Console");
/* 1304 */       Iterator i = this.frameList.iterator();
/* 1305 */       int count = 0;
/* 1306 */       while (i.hasNext())
/*      */       {
/* 1308 */         i.next();
/* 1309 */         count++;
/*      */       }
/*      */ 
/* 1315 */       int totalcount = Math.max(count, appPrefs.getInt("Number of Displays", 0));
/* 1316 */       for (int j = -1; j < totalcount; j++)
/*      */       {
/* 1318 */         appPrefs.remove("Display X " + j);
/* 1319 */         appPrefs.remove("Display Y " + j);
/* 1320 */         appPrefs.remove("Display Width " + j);
/* 1321 */         appPrefs.remove("Display Height " + j);
/* 1322 */         appPrefs.remove("Display Shown " + j);
/*      */       }
/*      */ 
/* 1325 */       appPrefs.remove("Number of Displays");
/*      */     }
/*      */     catch (AccessControlException e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setShouldRepeat(boolean val)
/*      */   {
/* 1343 */     synchronized (this.playThreadLock)
/*      */     {
/* 1345 */       this.shouldRepeat = val;
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean getShouldRepeat()
/*      */   {
/* 1352 */     synchronized (this.playThreadLock)
/*      */     {
/* 1354 */       return this.shouldRepeat;
/*      */     }
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setThreadPriority(int val)
/*      */   {
/* 1366 */     synchronized (this.playThreadLock)
/*      */     {
/* 1368 */       this.threadPriority = val;
/*      */ 
/* 1370 */       if (this.playThread != null)
/* 1371 */         this.playThread.setPriority(this.threadPriority);
/*      */     }
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public int getThreadPriority()
/*      */   {
/* 1380 */     synchronized (this.playThreadLock)
/*      */     {
/* 1382 */       return this.threadPriority;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setWhenShouldEnd(long val)
/*      */   {
/* 1392 */     synchronized (this.playThreadLock)
/*      */     {
/* 1394 */       this.whenShouldEnd = val;
/*      */     }
/*      */   }
/*      */ 
/*      */   public long getWhenShouldEnd()
/*      */   {
/* 1401 */     synchronized (this.playThreadLock)
/*      */     {
/* 1403 */       return this.whenShouldEnd;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setWhenShouldPause(long val)
/*      */   {
/* 1413 */     synchronized (this.playThreadLock)
/*      */     {
/* 1415 */       this.whenShouldPause = val;
/*      */     }
/*      */   }
/*      */ 
/*      */   public long getWhenShouldPause()
/*      */   {
/* 1422 */     synchronized (this.playThreadLock)
/*      */     {
/* 1424 */       return this.whenShouldPause;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setWhenShouldEndTime(double val)
/*      */   {
/* 1434 */     synchronized (this.playThreadLock)
/*      */     {
/* 1436 */       this.whenShouldEndTime = val;
/*      */     }
/*      */   }
/*      */ 
/*      */   public double getWhenShouldEndTime()
/*      */   {
/* 1443 */     synchronized (this.playThreadLock)
/*      */     {
/* 1445 */       return this.whenShouldEndTime;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setWhenShouldPauseTime(double val)
/*      */   {
/* 1455 */     synchronized (this.playThreadLock)
/*      */     {
/* 1457 */       this.whenShouldPauseTime = val;
/*      */     }
/*      */   }
/*      */ 
/*      */   public double getWhenShouldPauseTime()
/*      */   {
/* 1464 */     synchronized (this.playThreadLock)
/*      */     {
/* 1466 */       return this.whenShouldPauseTime;
/*      */     }
/*      */   }
/*      */ 
/*      */   long sliderToFrameRate(int val)
/*      */   {
/* 1478 */     double maxAsPowerOfTwo = Math.log(8.0D) / Math.log(2.0D);
/* 1479 */     double v = val / 1024.0D * 2.0D;
/* 1480 */     return Math.round(Math.pow(v, maxAsPowerOfTwo) * 1000.0D);
/*      */   }
/*      */ 
/*      */   int frameRateToSlider(long valInMS)
/*      */   {
/* 1485 */     double val = valInMS / 1000.0D;
/* 1486 */     double maxAsPowerOfTwo = Math.log(8.0D) / Math.log(2.0D);
/* 1487 */     double v = Math.pow(val, 1.0D / maxAsPowerOfTwo);
/* 1488 */     return (int)Math.round(v * 1024.0D / 2.0D);
/*      */   }
/*      */ 
/*      */   public void setPlaySleep(long sleep)
/*      */   {
/* 1497 */     if (sleep < 0L) sleep = 0L;
/* 1498 */     if (!this.frameRateSliderChanging)
/*      */     {
/* 1500 */       this.frameRateSliderChanging = true;
/* 1501 */       synchronized (this.playThreadLock)
/*      */       {
/* 1503 */         this.playSleep = sleep;
/*      */       }
/* 1505 */       this.frameRateSlider.setValue(frameRateToSlider(sleep));
/* 1506 */       DecimalFormat format = new DecimalFormat();
/* 1507 */       format.setMinimumFractionDigits(0);
/* 1508 */       format.setDecimalSeparatorAlwaysShown(false);
/* 1509 */       String text = format.format(sleep / 1000.0D);
/* 1510 */       if (text.length() > 5)
/* 1511 */         text = text.substring(0, 5);
/* 1512 */       this.frameRateSliderText.setText(text);
/* 1513 */       this.frameRateSliderChanging = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   public long getPlaySleep()
/*      */   {
/* 1520 */     synchronized (this.playThreadLock)
/*      */     {
/* 1522 */       return this.playSleep;
/*      */     }
/*      */   }
/*      */ 
/*      */   boolean getThreadShouldStop()
/*      */   {
/* 1540 */     synchronized (this.playThreadLock)
/*      */     {
/* 1542 */       return this.threadShouldStop;
/*      */     }
/*      */   }
/*      */ 
/*      */   void setThreadShouldStop(boolean stop)
/*      */   {
/* 1549 */     synchronized (this.playThreadLock)
/*      */     {
/* 1551 */       this.threadShouldStop = stop;
/*      */     }
/*      */   }
/*      */ 
/*      */   void setPlayState(int state)
/*      */   {
/* 1570 */     synchronized (this.playThreadLock)
/*      */     {
/* 1572 */       this.playState = state;
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getPlayState()
/*      */   {
/* 1579 */     synchronized (this.playThreadLock)
/*      */     {
/* 1581 */       return this.playState;
/*      */     }
/*      */   }
/*      */ 
/*      */   void startSimulation()
/*      */   {
/* 1593 */     removeAllInspectors(true);
/* 1594 */     this.simulation.state.setSeed(this.randomSeed);
/* 1595 */     this.simulation.start();
/* 1596 */     updateTime(this.simulation.state.schedule.getSteps(), this.simulation.state.schedule.getTime(), -1.0D);
/*      */ 
/* 1603 */     if (this.modelInspector != null)
/*      */     {
/* 1605 */       Steppable stepper = new Steppable()
/*      */       {
/*      */         public void step(final SimState state)
/*      */         {
/* 1609 */           SwingUtilities.invokeLater(new Runnable()
/*      */           {
/*      */             public void run()
/*      */             {
/* 1613 */               synchronized (state.schedule)
/*      */               {
/* 1617 */                 if (Console.this.modelInspector.isVolatile())
/*      */                 {
/* 1619 */                   Console.this.modelInspector.updateInspector();
/* 1620 */                   Console.this.modelInspector.repaint();
/*      */                 }
/*      */               }
/*      */             }
/*      */           });
/*      */         }
/*      */       };
/* 1627 */       if (this.modelInspector.isVolatile())
/* 1628 */         this.simulation.scheduleRepeatingImmediatelyAfter(stepper);
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized JTabbedPane getTabPane()
/*      */   {
/* 1646 */     return this.tabPane;
/*      */   }
/*      */ 
/*      */   public synchronized Inspector getModelInspector()
/*      */   {
/* 1654 */     return this.modelInspector;
/*      */   }
/*      */ 
/*      */   public static void doQuit()
/*      */   {
/* 1669 */     synchronized (isQuittingLock)
/*      */     {
/* 1671 */       if (isQuitting) return;
/* 1672 */       isQuitting = true;
/*      */ 
/* 1675 */       Object[] entries = allControllers.entrySet().toArray();
/* 1676 */       for (int x = 0; x < entries.length; x++) {
/* 1677 */         if ((entries[x] != null) && ((entries[x] instanceof Console)))
/* 1678 */           ((Console)((Map.Entry)entries[x]).getKey()).doClose();
/* 1679 */         else if ((entries[x] != null) && ((entries[x] instanceof SimpleController)))
/* 1680 */           ((SimpleController)((Map.Entry)entries[x]).getKey()).doClose();
/*      */       }
/* 1682 */       if (!SimApplet.isApplet) try {
/* 1683 */           System.exit(0); } catch (Exception e) {
/*      */         } isQuitting = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doClose()
/*      */   {
/* 1703 */     synchronized (this.isClosingLock)
/*      */     {
/* 1705 */       if (this.isClosing) return;
/* 1706 */       this.isClosing = true;
/*      */     }
/* 1708 */     pressStop();
/* 1709 */     this.simulation.quit();
/* 1710 */     dispose();
/* 1711 */     allControllers.remove(this);
/* 1712 */     if (allControllers.size() == 0)
/* 1713 */       doQuit();
/*      */   }
/*      */ 
/*      */   public static void main(String[] args)
/*      */   {
/* 1723 */     sacrificial = Display2D.isMacOSX();
/*      */ 
/* 1726 */     if ((!doNew(null, true)) && (!SimApplet.isApplet)) System.exit(0);
/*      */   }
/*      */ 
/*      */   protected void doAbout()
/*      */   {
/* 1733 */     if (aboutFrame == null)
/*      */     {
/* 1737 */       aboutFrame = new JFrame("About MASON");
/* 1738 */       JPanel p = new JPanel();
/* 1739 */       p.setBorder(BorderFactory.createEmptyBorder(25, 30, 30, 30));
/* 1740 */       Box b = new Box(1);
/* 1741 */       p.add(b, "Center");
/* 1742 */       aboutFrame.getContentPane().add(p, "Center");
/* 1743 */       aboutFrame.setResizable(false);
/* 1744 */       Font small = new Font("Dialog", 0, 9);
/*      */ 
/* 1747 */       JLabel j = new JLabel("MASON");
/* 1748 */       j.setFont(new Font("Serif", 0, 36));
/* 1749 */       b.add(j);
/*      */ 
/* 1751 */       NumberFormat n = NumberFormat.getInstance();
/* 1752 */       n.setMinimumFractionDigits(0);
/* 1753 */       j = new JLabel("Version " + n.format(SimState.version()));
/* 1754 */       b.add(j);
/* 1755 */       JLabel spacer = new JLabel(" ");
/* 1756 */       spacer.setFont(new Font("Dialog", 0, 6));
/* 1757 */       b.add(spacer);
/*      */ 
/* 1759 */       j = new JLabel("Co-created by George Mason University's");
/* 1760 */       b.add(j);
/* 1761 */       j = new JLabel("Evolutionary Computation Laboratory and");
/* 1762 */       b.add(j);
/* 1763 */       j = new JLabel("Center for Social Complexity");
/* 1764 */       b.add(j);
/*      */ 
/* 1766 */       spacer = new JLabel(" ");
/* 1767 */       spacer.setFont(new Font("Dialog", 0, 6));
/* 1768 */       b.add(spacer);
/*      */ 
/* 1770 */       j = new JLabel("http://cs.gmu.edu/~eclab/projects/mason/");
/* 1771 */       b.add(j);
/*      */ 
/* 1773 */       spacer = new JLabel(" ");
/* 1774 */       spacer.setFont(new Font("Dialog", 0, 6));
/* 1775 */       b.add(spacer);
/*      */ 
/* 1777 */       j = new JLabel("Major contributors include Sean Luke,");
/* 1778 */       b.add(j);
/* 1779 */       j = new JLabel("Gabriel Catalin Balan, Liviu Panait,");
/* 1780 */       b.add(j);
/* 1781 */       j = new JLabel("Claudio Cioffi-Revilla, Sean Paus,");
/* 1782 */       b.add(j);
/* 1783 */       j = new JLabel("Keith Sullivan, and Daniel Kuebrich.");
/* 1784 */       b.add(j);
/*      */ 
/* 1786 */       spacer = new JLabel(" ");
/* 1787 */       spacer.setFont(new Font("Dialog", 0, 6));
/* 1788 */       b.add(spacer);
/*      */ 
/* 1790 */       j = new JLabel("MASON is (c) 2005-2012 Sean Luke and George Mason University,");
/* 1791 */       j.setFont(small);
/* 1792 */       b.add(j);
/*      */ 
/* 1794 */       j = new JLabel("with various elements copyrighted by the above contributors.");
/* 1795 */       j.setFont(small);
/* 1796 */       b.add(j);
/*      */ 
/* 1798 */       j = new JLabel("PNGEncoder is (c) 2000 J. David Eisenberg.  MovieEncoder,", 2);
/* 1799 */       j.setFont(small);
/* 1800 */       b.add(j);
/*      */ 
/* 1802 */       j = new JLabel("SelectionBehavior, and WireFrameBoxPortrayal3D are partly", 2);
/* 1803 */       j.setFont(small);
/* 1804 */       b.add(j);
/*      */ 
/* 1806 */       j = new JLabel("(c) 1996 Sun Microsystems.  MersenneTwisterFast is partly", 2);
/* 1807 */       j.setFont(small);
/* 1808 */       b.add(j);
/*      */ 
/* 1810 */       j = new JLabel("(c) 1993 Michael Lecuyer.  CapturingCanvas3D is based in", 2);
/* 1811 */       j.setFont(small);
/* 1812 */       b.add(j);
/*      */ 
/* 1814 */       j = new JLabel("part on code by Peter Kunszt.", 2);
/* 1815 */       j.setFont(small);
/* 1816 */       b.add(j);
/* 1817 */       aboutFrame.pack();
/*      */     }
/*      */ 
/* 1821 */     if (!aboutFrame.isVisible())
/*      */     {
/* 1823 */       Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
/* 1824 */       d.width -= aboutFrame.getWidth();
/* 1825 */       d.height -= aboutFrame.getHeight();
/* 1826 */       d.width /= 2;
/* 1827 */       d.height /= 2;
/* 1828 */       if (d.width < 0) d.width = 0;
/* 1829 */       if (d.height < 0) d.height = 0;
/* 1830 */       aboutFrame.setLocation(d.width, d.height);
/*      */     }
/*      */ 
/* 1834 */     aboutFrame.setVisible(true);
/*      */   }
/*      */ 
/*      */   public void doNew()
/*      */   {
/* 1840 */     doNew(this, false);
/*      */   }
/*      */ 
/*      */   static int showOptionDialog(JFrame originalFrame, JComponent component, String title, Object[] options, boolean resizable)
/*      */   {
/* 1848 */     JOptionPane p = new JOptionPane(component, -1, 2, null, options, options[0]);
/*      */ 
/* 1852 */     JDialog d = p.createDialog(originalFrame, title);
/* 1853 */     d.pack();
/* 1854 */     d.setResizable(resizable);
/* 1855 */     p.selectInitialValue();
/* 1856 */     d.setVisible(true);
/* 1857 */     for (int counter = 0; counter < options.length; counter++)
/* 1858 */       if (options[counter].equals(p.getValue()))
/* 1859 */         return counter;
/* 1860 */     return -1;
/*      */   }
/*      */ 
/*      */   static void buildClassList()
/*      */   {
/* 1873 */     synchronized (classLock) { if (classListLoaded) return; classListLoaded = true;
/*      */     }
/*      */ 
/* 1876 */     allowOtherClassNames = true;
/*      */     try
/*      */     {
/* 1879 */       InputStream s = Console.class.getResourceAsStream("simulation.classes");
/* 1880 */       StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(s)));
/* 1881 */       st.resetSyntax();
/* 1882 */       st.wordChars(32, 255);
/* 1883 */       st.whitespaceChars(0, 31);
/* 1884 */       st.commentChar(35);
/* 1885 */       boolean errout = false;
/* 1886 */       String nextName = null;
/*      */ 
/* 1888 */       while (st.nextToken() != -1)
/*      */       {
/* 1890 */         if (st.sval != null)
/* 1891 */           if ("ONLY".equalsIgnoreCase(st.sval)) {
/* 1892 */             allowOtherClassNames = false;
/* 1893 */           } else if (st.sval.toUpperCase().startsWith("NAME:"))
/*      */           {
/* 1897 */             nextName = st.sval.substring(5).trim();
/*      */           }
/*      */           else
/*      */           {
/* 1901 */             String shortName = null;
/* 1902 */             if (nextName == null)
/*      */             {
/*      */               try
/*      */               {
/* 1906 */                 Class c = Class.forName(st.sval);
/*      */                 try {
/* 1908 */                   shortName = GUIState.getName(c);
/*      */                 } catch (Throwable e) {
/* 1910 */                   shortName = GUIState.getTruncatedName(c);
/*      */                 }
/*      */               }
/*      */               catch (Throwable e) {
/* 1914 */                 if (!errout) {
/* 1915 */                   System.err.println("WARNING: Not all classes loaded, due to error: probably no Java3D.");
/*      */                 }
/*      */ 
/* 1918 */                 errout = true;
/*      */               }
/*      */             } else {
/* 1921 */               shortName = nextName; nextName = null;
/*      */             }
/* 1923 */             if (shortName != null)
/*      */             {
/* 1925 */               classNames.add(st.sval);
/* 1926 */               shortNames.add(shortName);
/*      */             }
/*      */           }
/*      */       }
/* 1930 */       if (nextName != null) System.err.println("WARNING: Spurious NAME tag at end of simulation.classes file:\n\tNAME: " + nextName);
/* 1931 */       s.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1935 */       System.err.println("WARNING: Couldn't load the simulation.classes file because of error. \nLikely the file does not exist or could not be opened.\nThe error was:\n");
/* 1936 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   static boolean launchClass(JFrame originalFrame, String className)
/*      */   {
/*      */     try
/*      */     {
/* 1945 */       Constructor cons = Class.forName(className).getConstructor(new Class[0]);
/*      */ 
/* 1947 */       GUIState state = (GUIState)Class.forName(className).newInstance();
/*      */ 
/* 1956 */       if (SwingUtilities.isEventDispatchThread())
/* 1957 */         state.createController();
/* 1958 */       else SwingUtilities.invokeAndWait(new Runnable() { public void run() { this.val$state.createController(); } } );
/* 1959 */       return true;
/*      */     }
/*      */     catch (NoSuchMethodException e)
/*      */     {
/* 1963 */       Utilities.informOfError(e, "The simulation does not have a default constructor: " + className, originalFrame);
/* 1964 */       return false;
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/* 1968 */       Utilities.informOfError(e, "An error occurred while creating the simulation " + className, originalFrame);
/*      */     }
/* 1970 */     return false;
/*      */   }
/*      */ 
/*      */   static boolean doNew(JFrame originalFrame, boolean startingUp)
/*      */   {
/* 1977 */     buildClassList();
/* 1978 */     if (classNames.size() == 1)
/*      */     {
/* 1980 */       return launchClass(originalFrame, (String)classNames.get(0));
/*      */     }
/*      */ 
/* 1983 */     String defaultText = "<html><body bgcolor='white'><font face='dialog'><br><br><br><br><p align='center'>Select a MASON simulation from the list at left,<br>or type a Java class name below.</p></font></body></html>";
/* 1984 */     String nothingSelectedText = "<html><body bgcolor='white'></body></html>";
/*      */ 
/* 1988 */     final JList list = new JList(classNames);
/* 1989 */     JScrollPane pane = new JScrollPane(list);
/*      */ 
/* 1991 */     list.setCellRenderer(new DefaultListCellRenderer()
/*      */     {
/*      */       public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
/*      */       {
/* 2000 */         JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
/*      */ 
/* 2002 */         if (index >= 0)
/*      */         {
/* 2004 */           label.setText("<html><body><font face='dialog'> " + Console.shortNames.get(index) + "<font size='-2' color='#AAAAAA'><br> " + Console.classNames.get(index) + "</font></font></body></html>");
/*      */         }
/*      */ 
/* 2008 */         return label;
/*      */       }
/*      */     });
/* 2012 */     final HTMLBrowser browser = new HTMLBrowser("<html><body bgcolor='white'><font face='dialog'><br><br><br><br><p align='center'>Select a MASON simulation from the list at left,<br>or type a Java class name below.</p></font></body></html>") {
/*      */       public Dimension getPreferredSize() {
/* 2014 */         return new Dimension(400, 400); } 
/* 2015 */       public Dimension getMinimumSize() { return new Dimension(10, 10); }
/*      */ 
/*      */     };
/* 2018 */     JTextField field = new JTextField("sim.app.");
/* 2019 */     JPanel fieldp = new JPanel();
/* 2020 */     fieldp.setLayout(new BorderLayout());
/* 2021 */     fieldp.add(field, "Center");
/* 2022 */     fieldp.add(new JLabel("Simulation class name: "), "West");
/* 2023 */     fieldp.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
/*      */ 
/* 2025 */     list.addListSelectionListener(new ListSelectionListener()
/*      */     {
/*      */       public void valueChanged(ListSelectionEvent e)
/*      */       {
/* 2029 */         if (!e.getValueIsAdjusting())
/*      */           try {
/* 2031 */             this.val$field.setText((String)list.getSelectedValue());
/* 2032 */             browser.setText(GUIState.getInfo(Class.forName(this.val$field.getText(), true, Thread.currentThread().getContextClassLoader())));
/*      */           }
/*      */           catch (Throwable ex)
/*      */           {
/* 2036 */             this.val$field.setText((String)list.getSelectedValue());
/* 2037 */             browser.setText("<html><body bgcolor='white'></body></html>");
/*      */           }
/*      */       }
/*      */     });
/* 2060 */     boolean[] doubleClick = { false };
/* 2061 */     list.addMouseListener(new MouseAdapter()
/*      */     {
/*      */       public void mouseClicked(MouseEvent e)
/*      */       {
/* 2065 */         if (e.getClickCount() == 2)
/*      */         {
/* 2067 */           this.val$doubleClick[0] = true;
/*      */ 
/* 2070 */           Component c = list;
/* 2071 */           while (c.getParent() != null)
/* 2072 */             c = c.getParent();
/* 2073 */           ((Window)c).dispose();
/*      */         }
/*      */       }
/*      */     });
/* 2078 */     JPanel p = new JPanel();
/* 2079 */     p.setLayout(new BorderLayout());
/* 2080 */     p.add(browser, "Center");
/* 2081 */     p.add(pane, "West");
/* 2082 */     if (allowOtherClassNames) p.add(fieldp, "South");
/*      */ 
/* 2084 */     int reply = showOptionDialog(null, p, "New Simulation", new Object[] { "Select", startingUp ? "Quit" : "Cancel" }, true);
/* 2085 */     if ((reply != 0) && (doubleClick[0] == 0))
/*      */     {
/* 2087 */       return false;
/*      */     }
/*      */ 
/* 2090 */     return launchClass(originalFrame, field.getText());
/*      */   }
/*      */ 
/*      */   public void doSaveAs()
/*      */   {
/* 2100 */     FileDialog fd = new FileDialog(this, "Save Simulation As...", 1);
/* 2101 */     if (this.simulationFile == null)
/*      */     {
/* 2103 */       fd.setFile("Untitled.checkpoint");
/*      */     }
/*      */     else
/*      */     {
/* 2107 */       fd.setFile(this.simulationFile.getName());
/* 2108 */       fd.setDirectory(this.simulationFile.getParentFile().getPath());
/*      */     }
/* 2110 */     fd.setVisible(true);
/* 2111 */     File f = null;
/* 2112 */     if (fd.getFile() != null)
/*      */       try
/*      */       {
/* 2115 */         f = new File(fd.getDirectory(), Utilities.ensureFileEndsWith(fd.getFile(), ".checkpoint"));
/* 2116 */         this.simulation.state.writeToCheckpoint(f);
/* 2117 */         this.simulationFile = f;
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 2121 */         Utilities.informOfError(e, "An error occurred while saving the simulation to the file " + (f == null ? " " : f.getName()), null);
/*      */       }
/*      */   }
/*      */ 
/*      */   public void doSave()
/*      */   {
/* 2130 */     if (this.simulationFile == null)
/*      */     {
/* 2132 */       doSaveAs();
/*      */     }
/*      */     else
/*      */       try
/*      */       {
/* 2137 */         this.simulation.state.writeToCheckpoint(this.simulationFile);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 2141 */         Utilities.informOfError(e, "An error occurred while saving the simulation to the file " + this.simulationFile.getName(), null);
/*      */       }
/*      */   }
/*      */ 
/*      */   public void doOpen()
/*      */   {
/* 2150 */     FileDialog fd = new FileDialog(this, "Load Saved Simulation...", 0);
/* 2151 */     fd.setFilenameFilter(new FilenameFilter()
/*      */     {
/*      */       public boolean accept(File dir, String name)
/*      */       {
/* 2155 */         return Utilities.ensureFileEndsWith(name, ".checkpoint").equals(name);
/*      */       }
/*      */     });
/* 2159 */     if (this.simulationFile != null)
/*      */     {
/* 2161 */       fd.setFile(this.simulationFile.getName());
/* 2162 */       fd.setDirectory(this.simulationFile.getParentFile().getPath());
/*      */     }
/*      */ 
/* 2165 */     boolean failed = true;
/* 2166 */     int originalPlayState = getPlayState();
/* 2167 */     if (originalPlayState == 1) {
/* 2168 */       pressPause();
/*      */     }
/* 2170 */     fd.setVisible(true);
/* 2171 */     File f = null;
/* 2172 */     if (fd.getFile() != null) {
/*      */       try
/*      */       {
/* 2175 */         f = new File(fd.getDirectory(), fd.getFile());
/* 2176 */         if (!this.simulation.readNewStateFromCheckpoint(f))
/* 2177 */           throw new RuntimeException("Invalid SimState class.  Original state: " + this.simulation.state);
/* 2178 */         this.simulationFile = f;
/*      */ 
/* 2180 */         buildModelInspector();
/* 2181 */         removeAllInspectors(true);
/*      */ 
/* 2183 */         if (originalPlayState == 0)
/* 2184 */           pressPause(false);
/* 2185 */         failed = false;
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/* 2189 */         Utilities.informOfError(e, "An error occurred while loading the simulation from the file " + (f == null ? fd.getFile() : f.getName()), null);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2197 */     if ((failed) && (originalPlayState == 1)) {
/* 2198 */       pressPause();
/*      */     }
/*      */ 
/* 2201 */     updateTime(this.simulation.state.schedule.getSteps(), this.simulation.state.schedule.getTime(), -1.0D);
/* 2202 */     this.randomField.setValue("" + this.simulation.state.seed());
/*      */   }
/*      */ 
/*      */   public synchronized ArrayList getAllFrames()
/*      */   {
/* 2217 */     return new ArrayList(this.frameList);
/*      */   }
/*      */ 
/*      */   synchronized void showSelectedFrames()
/*      */   {
/* 2224 */     Object[] vals = this.frameListDisplay.getSelectedValues();
/*      */ 
/* 2226 */     for (int x = 0; x < vals.length; x++)
/*      */     {
/* 2228 */       ((JFrame)vals[x]).toFront();
/* 2229 */       ((JFrame)vals[x]).setVisible(true);
/*      */     }
/* 2231 */     this.frameListDisplay.repaint();
/*      */   }
/*      */ 
/*      */   public synchronized void showAllFrames()
/*      */   {
/* 2238 */     Object[] vals = (Object[])this.frameList.toArray();
/* 2239 */     for (int x = 0; x < vals.length; x++)
/*      */     {
/* 2241 */       ((JFrame)vals[x]).toFront();
/* 2242 */       ((JFrame)vals[x]).setVisible(true);
/*      */     }
/* 2244 */     this.frameListDisplay.repaint();
/*      */   }
/*      */ 
/*      */   synchronized void hideSelectedFrames()
/*      */   {
/* 2251 */     Object[] vals = this.frameListDisplay.getSelectedValues();
/*      */ 
/* 2253 */     for (int x = 0; x < vals.length; x++)
/*      */     {
/* 2255 */       ((JFrame)vals[x]).setVisible(false);
/*      */     }
/* 2257 */     this.frameListDisplay.repaint();
/*      */   }
/*      */ 
/*      */   public synchronized void hideAllFrames()
/*      */   {
/* 2264 */     Object[] vals = (Object[])this.frameList.toArray();
/* 2265 */     for (int x = 0; x < vals.length; x++)
/*      */     {
/* 2267 */       ((JFrame)vals[x]).setVisible(false);
/*      */     }
/* 2269 */     this.frameListDisplay.repaint();
/*      */   }
/*      */ 
/*      */   public synchronized void setRequiresConfirmationToStop(boolean val)
/*      */   {
/* 2280 */     this.requiresConfirmationToStop = val;
/*      */   }
/*      */ 
/*      */   public synchronized boolean getRequiresConfirmationToStop()
/*      */   {
/* 2285 */     return this.requiresConfirmationToStop;
/*      */   }
/*      */ 
/*      */   synchronized void pressStopMaybe()
/*      */   {
/* 2296 */     if (this.requiresConfirmationToStop)
/*      */     {
/* 2298 */       if (getPlayState() != 0)
/*      */       {
/* 2300 */         boolean result = false;
/*      */ 
/* 2302 */         synchronized (this.simulation.state.schedule)
/*      */         {
/* 2305 */           result = JOptionPane.showConfirmDialog(this, "The simulation is running.  Really stop it?", "Stop the simulation?", 2) == 0;
/*      */         }
/*      */ 
/* 2310 */         if (result) pressStop(); 
/*      */       }
/*      */     }
/*      */     else
/* 2313 */       pressStop();
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setIncrementSeedOnPlay(boolean val)
/*      */   {
/* 2319 */     setIncrementSeedOnStop(val);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public boolean getIncrementSeedOnPlay()
/*      */   {
/* 2325 */     return getIncrementSeedOnStop();
/*      */   }
/*      */ 
/*      */   public void setIncrementSeedOnStop(boolean val)
/*      */   {
/* 2330 */     this.incrementSeedOnStop.setSelected(val);
/*      */   }
/*      */ 
/*      */   public boolean getIncrementSeedOnStop()
/*      */   {
/* 2335 */     return this.incrementSeedOnStop.isSelected();
/*      */   }
/*      */ 
/*      */   public synchronized void pressStop()
/*      */   {
/* 2343 */     if (getPlayState() != 0)
/*      */     {
/* 2345 */       this.stopButton.setIcon(I_STOP_ON);
/* 2346 */       this.stopButton.setPressedIcon(I_STOP_OFF);
/* 2347 */       this.playButton.setIcon(I_PLAY_OFF);
/* 2348 */       this.playButton.setPressedIcon(I_PLAY_ON);
/* 2349 */       this.pauseButton.setIcon(I_PAUSE_OFF);
/* 2350 */       this.pauseButton.setPressedIcon(I_PAUSE_ON);
/*      */ 
/* 2352 */       repaint();
/* 2353 */       killPlayThread();
/* 2354 */       this.simulation.finish();
/* 2355 */       stopAllInspectors(true);
/* 2356 */       setPlayState(0);
/* 2357 */       repaint();
/*      */ 
/* 2360 */       if (this.incrementSeedOnStop.isSelected())
/*      */       {
/* 2362 */         this.randomSeed = ((int)(this.randomSeed + 1L));
/* 2363 */         this.randomField.setValue("" + this.randomSeed);
/*      */       }
/*      */ 
/* 2368 */       if (getShouldRepeat())
/*      */       {
/* 2373 */         SwingUtilities.invokeLater(new Runnable() { public void run() { Console.this.pressPlay(); }
/*      */ 
/*      */         });
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized void pressPause()
/*      */   {
/* 2382 */     pressPause(true);
/*      */   }
/*      */ 
/*      */   synchronized void pressPause(boolean shouldStartSimulationIfStopped)
/*      */   {
/* 2393 */     if (getPlayState() == 1)
/*      */     {
/* 2395 */       killPlayThread();
/*      */ 
/* 2397 */       this.pauseButton.setIcon(I_PAUSE_ON);
/* 2398 */       this.pauseButton.setPressedIcon(I_PAUSE_OFF);
/* 2399 */       this.playButton.setIcon(I_STEP_OFF);
/* 2400 */       this.playButton.setPressedIcon(I_STEP_ON);
/* 2401 */       setPlayState(2);
/* 2402 */       refresh();
/*      */     }
/* 2404 */     else if (getPlayState() == 2)
/*      */     {
/* 2406 */       this.pauseButton.setIcon(I_PAUSE_OFF);
/* 2407 */       this.pauseButton.setPressedIcon(I_PAUSE_ON);
/* 2408 */       this.playButton.setIcon(I_PLAY_ON);
/* 2409 */       this.playButton.setPressedIcon(I_PLAY_OFF);
/*      */ 
/* 2411 */       spawnPlayThread();
/* 2412 */       setPlayState(1);
/*      */     }
/* 2414 */     else if (getPlayState() == 0)
/*      */     {
/* 2420 */       if (shouldStartSimulationIfStopped) startSimulation();
/*      */ 
/* 2422 */       this.stopButton.setIcon(I_STOP_OFF);
/* 2423 */       this.stopButton.setPressedIcon(I_STOP_ON);
/*      */ 
/* 2425 */       this.pauseButton.setIcon(I_PAUSE_ON);
/* 2426 */       this.pauseButton.setPressedIcon(I_PAUSE_OFF);
/* 2427 */       this.playButton.setIcon(I_STEP_OFF);
/* 2428 */       this.playButton.setPressedIcon(I_STEP_ON);
/* 2429 */       setPlayState(2);
/* 2430 */       refresh();
/*      */     }
/*      */ 
/* 2433 */     repaint();
/*      */   }
/*      */ 
/*      */   public int getNumStepsPerStepButtonPress()
/*      */   {
/* 2439 */     return this.numStepsPerStepButtonPress;
/*      */   }
/*      */ 
/*      */   public void setNumStepsPerStepButtonPress(int val)
/*      */   {
/* 2445 */     if (val <= 0) val = 1;
/* 2446 */     if (!this.stepSliderChanging)
/*      */     {
/* 2448 */       this.stepSliderChanging = true;
/* 2449 */       this.numStepsPerStepButtonPress = val;
/* 2450 */       this.stepSlider.setValue(val);
/* 2451 */       this.stepSliderText.setText("" + val);
/* 2452 */       this.stepSliderChanging = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized void pressPlay()
/*      */   {
/* 2459 */     if (getPlayState() == 0)
/*      */     {
/* 2462 */       this.stopButton.setIcon(I_STOP_OFF);
/* 2463 */       this.stopButton.setPressedIcon(I_STOP_ON);
/*      */ 
/* 2465 */       this.playButton.setIcon(I_PLAY_ON);
/* 2466 */       this.playButton.setPressedIcon(I_PLAY_OFF);
/* 2467 */       this.pauseButton.setIcon(I_PAUSE_OFF);
/* 2468 */       this.pauseButton.setPressedIcon(I_PAUSE_ON);
/* 2469 */       repaint();
/*      */ 
/* 2471 */       startSimulation();
/*      */ 
/* 2473 */       spawnPlayThread();
/*      */ 
/* 2475 */       setPlayState(1);
/*      */     }
/* 2477 */     else if (getPlayState() == 2)
/*      */     {
/* 2479 */       for (int x = 0; x < this.numStepsPerStepButtonPress; x++)
/*      */       {
/* 2482 */         if ((!this.simulation.step()) || (this.simulation.state.schedule.getTime() >= getWhenShouldEndTime()) || (this.simulation.state.schedule.getSteps() >= getWhenShouldEnd()))
/*      */         {
/* 2486 */           pressStop();
/*      */ 
/* 2489 */           updateTime(this.simulation.state.schedule.getSteps(), this.simulation.state.schedule.getTime(), -1.0D);
/* 2490 */           break;
/*      */         }
/*      */ 
/* 2496 */         updateTime(this.simulation.state.schedule.getSteps(), this.simulation.state.schedule.getTime(), -1.0D);
/*      */       }
/*      */ 
/* 2499 */       refresh();
/*      */     }
/* 2501 */     repaint();
/*      */   }
/*      */ 
/*      */   void updateTimeText(final String timeString)
/*      */   {
/* 2534 */     if (!timeString.equals(this.lastText))
/*      */     {
/* 2536 */       this.lastText = timeString;
/*      */ 
/* 2538 */       SwingUtilities.invokeLater(new Runnable()
/*      */       {
/*      */         public void run()
/*      */         {
/* 2542 */           Console.this.time.setText(timeString);
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */ 
/*      */   void updateTime()
/*      */   {
/*      */     long steps;
/*      */     double time;
/*      */     double rate;
/* 2552 */     synchronized (this.time)
/*      */     {
/* 2554 */       steps = this.lastSteps; time = this.lastTime; rate = this.lastRate;
/*      */     }
/* 2556 */     updateTime(steps, time, rate);
/*      */   }
/*      */ 
/*      */   void updateTime(long steps, double time, double rate)
/*      */   {
/* 2562 */     boolean simulationExists = (this.simulation != null) && (this.simulation.state != null);
/*      */     int showing;
/* 2563 */     synchronized (this.time) { this.lastRate = rate; this.lastSteps = steps; this.lastTime = time; showing = this.showing; }
/* 2564 */     switch (showing)
/*      */     {
/*      */     case 0:
/* 2567 */       updateTimeText(simulationExists ? this.simulation.state.schedule.getTimestamp(this.lastTime, "At Start", "At End") : "");
/*      */ 
/* 2569 */       break;
/*      */     case 1:
/* 2571 */       updateTimeText(simulationExists ? "" + this.lastSteps : "");
/* 2572 */       break;
/*      */     case 2:
/* 2574 */       if (this.lastRate != 0.0D) updateTimeText(this.lastRate < 0.0D ? "" : this.rateFormat.format(this.lastRate)); break;
/*      */     case 3:
/* 2577 */       updateTimeText("");
/* 2578 */       break;
/*      */     default:
/* 2580 */       throw new RuntimeException("default case should never occur");
/*      */     }
/*      */   }
/*      */ 
/*      */   public double getStepsPerSecond()
/*      */   {
/* 2587 */     synchronized (this.time)
/*      */     {
/* 2589 */       return this.lastRate;
/*      */     }
/*      */   }
/*      */ 
/*      */   synchronized void killPlayThread()
/*      */   {
/* 2613 */     setThreadShouldStop(true);
/*      */     try
/*      */     {
/* 2618 */       if (this.playThread != null)
/*      */       {
/*      */         do
/*      */         {
/*      */           try
/*      */           {
/* 2636 */             synchronized (this.simulation.state.schedule)
/*      */             {
/* 2638 */               this.playThread.interrupt();
/*      */             }
/*      */           } catch (SecurityException ex) {
/*      */           }
/* 2642 */           this.playThread.join(50L);
/*      */         }
/* 2644 */         while (this.playThread.isAlive());
/* 2645 */         this.playThread = null;
/*      */       }
/*      */     }
/*      */     catch (InterruptedException e) {
/* 2649 */       System.err.println("WARNING: This should never happen: " + e);
/*      */     }
/*      */   }
/*      */ 
/*      */   synchronized void spawnPlayThread()
/*      */   {
/* 2673 */     setThreadShouldStop(false);
/*      */ 
/* 2676 */     Runnable run = new Runnable()
/*      */     {
/*      */       public void run()
/*      */       {
/* 2683 */         double currentRate = 0.0D;
/*      */         try
/*      */         {
/* 2690 */           long lastRateDisplayStepTime = System.currentTimeMillis();
/* 2691 */           long lastTimeDisplayStepTime = lastRateDisplayStepTime;
/*      */ 
/* 2693 */           int currentSteps = 0;
/* 2694 */           long RATE_UPDATE_INTERVAL = 500L;
/* 2695 */           long TIME_UPDATE_INTERVAL = 40L;
/*      */ 
/* 2700 */           if ((!Thread.currentThread().isInterrupted()) && (!Console.this.getThreadShouldStop()))
/*      */           {
/*      */             try
/*      */             {
/* 2705 */               SwingUtilities.invokeAndWait(Console.this.blocker);
/*      */             }
/*      */             catch (InterruptedException e)
/*      */             {
/*      */               try
/*      */               {
/* 2711 */                 Thread.currentThread().interrupt();
/*      */               }
/*      */               catch (SecurityException ex) {
/*      */               }
/*      */             }
/*      */             catch (InvocationTargetException e) {
/* 2717 */               System.err.println("WARNING: This should never happen: " + e);
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 2721 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */ 
/* 2725 */           Console.this.simulation.state.nameThread();
/*      */ 
/* 2730 */           boolean result = true;
/*      */ 
/* 2734 */           while (!Console.this.getThreadShouldStop())
/*      */           {
/* 2737 */             result = Console.this.simulation.step();
/*      */ 
/* 2742 */             double t = Console.this.simulation.state.schedule.getTime();
/* 2743 */             long s = Console.this.simulation.state.schedule.getSteps();
/*      */ 
/* 2765 */             currentSteps++;
/* 2766 */             long l = System.currentTimeMillis();
/* 2767 */             if (l - lastRateDisplayStepTime >= 500L)
/*      */             {
/* 2769 */               currentRate = currentSteps / ((l - lastRateDisplayStepTime) / 1000.0D);
/* 2770 */               currentSteps = 0;
/* 2771 */               lastRateDisplayStepTime = l;
/*      */             }
/*      */ 
/* 2774 */             if (l - lastTimeDisplayStepTime >= 40L)
/*      */             {
/* 2776 */               Console.this.updateTime(s, t, currentRate);
/* 2777 */               lastTimeDisplayStepTime = l;
/*      */             }
/*      */ 
/* 2790 */             if ((!Thread.currentThread().isInterrupted()) && (!Console.this.getThreadShouldStop()))
/*      */             {
/*      */               try
/*      */               {
/* 2795 */                 SwingUtilities.invokeAndWait(Console.this.blocker);
/*      */               }
/*      */               catch (InterruptedException e)
/*      */               {
/*      */                 try
/*      */                 {
/* 2801 */                   Thread.currentThread().interrupt();
/*      */                 }
/*      */                 catch (SecurityException ex) {
/*      */                 }
/*      */               }
/*      */               catch (InvocationTargetException e) {
/* 2807 */                 System.err.println("WARNING: This should never happen" + e);
/*      */               }
/*      */               catch (Exception e)
/*      */               {
/* 2811 */                 e.printStackTrace();
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 2816 */             if ((!result) || (Console.this.getThreadShouldStop()) || (t >= Console.this.getWhenShouldEndTime()) || (t >= Console.this.getWhenShouldPauseTime()) || (s >= Console.this.getWhenShouldEnd()) || (s >= Console.this.getWhenShouldPause()))
/*      */             {
/*      */               break;
/*      */             }
/* 2820 */             long sleep = Console.this.getPlaySleep();
/*      */ 
/* 2823 */             if ((sleep > 0L) && (!Thread.currentThread().isInterrupted()) && (!Console.this.getThreadShouldStop())) {
/*      */               try
/*      */               {
/* 2826 */                 Thread.sleep(sleep);
/*      */               }
/*      */               catch (InterruptedException e)
/*      */               {
/*      */                 try
/*      */                 {
/* 2832 */                   Thread.currentThread().interrupt();
/*      */                 }
/*      */                 catch (SecurityException ex) {
/*      */                 }
/*      */               }
/*      */               catch (Exception e) {
/* 2838 */                 e.printStackTrace();
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 2850 */           if ((!result) || (Console.this.simulation.state.schedule.getTime() >= Console.this.getWhenShouldEndTime()) || (Console.this.simulation.state.schedule.getSteps() >= Console.this.getWhenShouldEnd()))
/*      */           {
/* 2852 */             SwingUtilities.invokeLater(new Runnable()
/*      */             {
/*      */               public void run()
/*      */               {
/*      */                 try
/*      */                 {
/* 2858 */                   Console.this.pressStop();
/*      */                 }
/*      */                 catch (Exception e)
/*      */                 {
/* 2862 */                   System.err.println("WARNING: This should never happen: " + e);
/*      */                 }
/*      */               } } );
/*      */           }
/* 2866 */           else if ((Console.this.simulation.state.schedule.getTime() >= Console.this.getWhenShouldPauseTime()) || (Console.this.simulation.state.schedule.getSteps() >= Console.this.getWhenShouldPause()))
/*      */           {
/* 2868 */             SwingUtilities.invokeLater(new Runnable()
/*      */             {
/*      */               public void run()
/*      */               {
/*      */                 try
/*      */                 {
/* 2874 */                   Console.this.pressPause();
/*      */ 
/* 2876 */                   Console.this.pauseField.setValue("");
/* 2877 */                   Console.this.timePauseField.setValue("");
/* 2878 */                   Console.this.setWhenShouldPause(9223372036854775807L);
/* 2879 */                   Console.this.setWhenShouldPauseTime((1.0D / 0.0D));
/*      */                 }
/*      */                 catch (Exception e)
/*      */                 {
/* 2883 */                   System.err.println("WARNING: This should never happen: " + e);
/*      */                 }
/*      */               } } );
/*      */           }
/*      */         } catch (Exception e) {
/* 2888 */           e.printStackTrace();
/*      */         }
/*      */ 
/* 2891 */         Console.this.updateTime(Console.this.simulation.state.schedule.getSteps(), Console.this.simulation.state.schedule.getTime(), currentRate);
/*      */       }
/*      */     };
/* 2894 */     this.playThread = new Thread(run);
/* 2895 */     this.playThread.setPriority(getThreadPriority());
/* 2896 */     this.playThread.start();
/*      */   }
/*      */ 
/*      */   public synchronized boolean registerFrame(JFrame frame)
/*      */   {
/* 2913 */     this.frameList.add(frame);
/* 2914 */     this.frameListDisplay.setListData(this.frameList);
/* 2915 */     return true;
/*      */   }
/*      */ 
/*      */   public synchronized boolean unregisterFrame(JFrame frame)
/*      */   {
/* 2921 */     this.frameList.removeElement(frame);
/* 2922 */     this.frameListDisplay.setListData(this.frameList);
/* 2923 */     return true;
/*      */   }
/*      */ 
/*      */   public synchronized boolean unregisterAllFrames()
/*      */   {
/* 2929 */     this.frameList.removeAllElements();
/* 2930 */     this.frameListDisplay.setListData(this.frameList);
/* 2931 */     return true;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public synchronized void doChangeCode(Runnable r)
/*      */   {
/* 2939 */     if (this.playThread != null)
/*      */     {
/* 2941 */       killPlayThread();
/* 2942 */       r.run();
/* 2943 */       spawnPlayThread();
/*      */     }
/*      */     else {
/* 2946 */       r.run();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void refresh()
/*      */   {
/* 2953 */     Enumeration e = this.frameList.elements();
/* 2954 */     while (e.hasMoreElements()) {
/* 2955 */       ((JFrame)e.nextElement()).getContentPane().repaint();
/*      */     }
/*      */ 
/* 2958 */     Iterator i = this.allInspectors.keySet().iterator();
/* 2959 */     while (i.hasNext())
/*      */     {
/* 2961 */       Inspector c = (Inspector)i.next();
/* 2962 */       if ((c != null) && (!c.isStopped()))
/*      */       {
/* 2964 */         if (c.isVolatile())
/*      */         {
/* 2966 */           c.updateInspector();
/* 2967 */           c.repaint();
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2973 */     if ((this.modelInspector != null) && (this.modelInspector.isVolatile()))
/*      */     {
/* 2975 */       this.modelInspector.updateInspector();
/* 2976 */       this.modelInspector.repaint();
/*      */     }
/* 2978 */     getContentPane().repaint();
/*      */   }
/*      */ 
/*      */   void resetInspectors(int preferredSelection)
/*      */   {
/* 3026 */     this.inspectorSwitcher = new JPanel();
/* 3027 */     this.inspectorSwitcher.setLayout(this.inspectorCardLayout = new CardLayout());
/* 3028 */     int loc = this.innerInspectorPanel.getDividerLocation();
/* 3029 */     this.innerInspectorPanel.setBottomComponent(this.inspectorSwitcher);
/* 3030 */     this.innerInspectorPanel.setDividerLocation(loc);
/* 3031 */     for (int x = 0; x < this.inspectorToolbars.size(); x++)
/*      */     {
/* 3033 */       this.inspectorSwitcher.add((JComponent)this.inspectorToolbars.elementAt(x), "" + x);
/*      */     }
/*      */ 
/* 3042 */     this.inspectorSwitcher.add(new JPanel(), "-1");
/* 3043 */     this.inspectorList.setListData(this.inspectorNames);
/* 3044 */     if (preferredSelection >= this.inspectorToolbars.size())
/* 3045 */       preferredSelection = 0;
/* 3046 */     if (preferredSelection >= this.inspectorToolbars.size())
/* 3047 */       preferredSelection = -1;
/* 3048 */     this.inspectorCardLayout.show(this.inspectorSwitcher, "" + preferredSelection);
/* 3049 */     this.inspectorList.setSelectedIndex(preferredSelection);
/*      */ 
/* 3051 */     boolean shouldEnableButtons = this.inspectorNames.size() > 0;
/* 3052 */     this.detatchButton.setEnabled(shouldEnableButtons);
/* 3053 */     this.removeButton.setEnabled(shouldEnableButtons);
/*      */   }
/*      */ 
/*      */   void detatchInspector()
/*      */   {
/* 3061 */     int currentInspector = this.inspectorList.getSelectedIndex();
/* 3062 */     if (currentInspector == -1) return;
/*      */ 
/* 3064 */     this.inspectorNames.remove(currentInspector);
/* 3065 */     Stoppable stoppable = (Stoppable)this.inspectorStoppables.remove(currentInspector);
/* 3066 */     JScrollPane oldInspector = (JScrollPane)this.inspectorToolbars.remove(currentInspector);
/* 3067 */     Point oldInspectorLocation = oldInspector.getLocationOnScreen();
/*      */ 
/* 3071 */     Inspector i = (Inspector)oldInspector.getViewport().getView();
/* 3072 */     oldInspector.remove(i);
/* 3073 */     JFrame frame = i.createFrame(stoppable);
/*      */ 
/* 3077 */     frame.setLocation(oldInspectorLocation);
/* 3078 */     frame.setVisible(true);
/*      */ 
/* 3081 */     if (this.inspectorNames.size() == 0)
/* 3082 */       currentInspector = -1;
/* 3083 */     else if (currentInspector == this.inspectorNames.size()) {
/* 3084 */       currentInspector--;
/*      */     }
/* 3086 */     resetInspectors(currentInspector);
/*      */   }
/*      */ 
/*      */   public void setInspectors(final Bag inspectors, Bag names)
/*      */   {
/* 3099 */     removeAllInspectors(false);
/*      */ 
/* 3102 */     if (inspectors.numObjs != names.numObjs) {
/* 3103 */       throw new RuntimeException("Number of inspectors and names do not match");
/*      */     }
/*      */ 
/* 3106 */     for (int x = 0; x < inspectors.numObjs; x++)
/*      */     {
/* 3108 */       if (inspectors.objs[x] != null)
/*      */       {
/* 3110 */         final int xx = x;
/* 3111 */         Steppable stepper = new Steppable()
/*      */         {
/*      */           public void step(final SimState state)
/*      */           {
/* 3115 */             SwingUtilities.invokeLater(new Runnable()
/*      */             {
/* 3117 */               Inspector inspector = (Inspector)Console.61.this.val$inspectors.objs[Console.61.this.val$xx];
/*      */ 
/*      */               public void run() {
/* 3120 */                 synchronized (state.schedule)
/*      */                 {
/* 3124 */                   if (this.inspector.isVolatile())
/*      */                   {
/* 3126 */                     this.inspector.updateInspector();
/* 3127 */                     this.inspector.repaint();
/*      */                   }
/*      */                 }
/*      */               }
/*      */             });
/*      */           }
/*      */         };
/* 3135 */         Stoppable stopper = null;
/*      */         try
/*      */         {
/* 3138 */           stopper = ((Inspector)inspectors.objs[x]).reviseStopper(this.simulation.scheduleRepeatingImmediatelyAfter(stepper));
/* 3139 */           this.inspectorStoppables.addElement(stopper);
/*      */         }
/*      */         catch (IllegalArgumentException ex)
/*      */         {
/*      */         }
/* 3144 */         registerInspector((Inspector)inspectors.objs[x], stopper);
/* 3145 */         JScrollPane scrollInspector = new JScrollPane((Component)inspectors.objs[x]);
/* 3146 */         scrollInspector.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/* 3147 */         this.inspectorSwitcher.add(scrollInspector, "" + x);
/* 3148 */         this.inspectorNames.addElement((String)names.objs[x]);
/* 3149 */         this.inspectorToolbars.add(scrollInspector);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3159 */     resetInspectors(this.preferredInspectorIndex);
/*      */     try
/*      */     {
/* 3164 */       this.tabPane.setSelectedComponent(this.inspectorPanel);
/* 3165 */       Utilities.doEnsuredRepaint(this.inspectorPanel);
/*      */     }
/*      */     catch (IllegalArgumentException e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void registerInspector(Inspector inspector, Stoppable stopper)
/*      */   {
/* 3176 */     this.allInspectors.put(inspector, stopper);
/*      */   }
/*      */ 
/*      */   public ArrayList getAllInspectors()
/*      */   {
/* 3187 */     ArrayList list = new ArrayList();
/* 3188 */     Iterator i = this.allInspectors.keySet().iterator();
/* 3189 */     while (i.hasNext())
/* 3190 */       list.add((Inspector)i.next());
/* 3191 */     return list;
/*      */   }
/*      */ 
/*      */   public void stopAllInspectors(boolean killDraggedOutWindowsToo)
/*      */   {
/* 3200 */     Iterator i = this.allInspectors.keySet().iterator();
/* 3201 */     while (i.hasNext())
/*      */     {
/* 3203 */       Inspector insp = (Inspector)i.next();
/* 3204 */       insp.updateInspector();
/* 3205 */       insp.repaint();
/*      */     }
/*      */ 
/* 3210 */     for (int x = 0; x < this.inspectorStoppables.size(); x++)
/*      */     {
/* 3212 */       Stoppable stopper = (Stoppable)this.inspectorStoppables.elementAt(x);
/* 3213 */       if (stopper != null) stopper.stop();
/*      */ 
/*      */     }
/*      */ 
/* 3217 */     if (killDraggedOutWindowsToo)
/*      */     {
/* 3219 */       i = this.allInspectors.keySet().iterator();
/* 3220 */       while (i.hasNext())
/*      */       {
/* 3222 */         Inspector insp = (Inspector)i.next();
/* 3223 */         Stoppable stopper = (Stoppable)this.allInspectors.get(insp);
/* 3224 */         if (stopper != null) stopper.stop();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeAllInspectors(boolean killDraggedOutWindowsToo)
/*      */   {
/* 3233 */     stopAllInspectors(killDraggedOutWindowsToo);
/* 3234 */     if (killDraggedOutWindowsToo)
/*      */     {
/* 3237 */       Iterator i = this.allInspectors.keySet().iterator();
/* 3238 */       while (i.hasNext())
/*      */       {
/* 3240 */         Inspector inspector = (Inspector)i.next();
/* 3241 */         inspector.disposeFrame();
/*      */       }
/* 3243 */       this.allInspectors = new WeakHashMap();
/*      */     }
/* 3245 */     this.inspectorStoppables = new Vector();
/* 3246 */     this.inspectorNames = new Vector();
/* 3247 */     this.inspectorToolbars = new Vector();
/* 3248 */     resetInspectors(-1);
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  132 */     ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
/*      */     try
/*      */     {
/*  140 */       System.setProperty("Quaqua.TabbedPane.design", "auto");
/*  141 */       System.setProperty("Quaqua.visualMargin", "1,1,1,1");
/*  142 */       UIManager.put("Panel.opaque", Boolean.TRUE);
/*  143 */       UIManager.setLookAndFeel((String)Class.forName("ch.randelshofer.quaqua.QuaquaManager").getMethod("getLookAndFeelClassName", (Class[])null).invoke(null, (Object[])null));
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  153 */       System.setProperty("com.apple.hwaccel", "true");
/*  154 */       System.setProperty("apple.awt.graphics.UseQuartz", "true");
/*      */ 
/*  157 */       System.setProperty("apple.awt.showGrowBox", "true");
/*      */ 
/*  164 */       System.setProperty("com.apple.macos.use-file-dialog-packages", "true");
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.display.Console
 * JD-Core Version:    0.6.2
 */