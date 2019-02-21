/*    */ package sim.display;
/*    */ 
/*    */ import java.applet.Applet;
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.JTextArea;
/*    */ 
/*    */ public class SimApplet extends Applet
/*    */ {
/* 28 */   static boolean b = Display2D.isMacOSX();
/* 29 */   static int i = 380;
/*    */ 
/* 32 */   static boolean isApplet = false;
/*    */ 
/* 33 */   public SimApplet() { isApplet = true; } 
/*    */   public static boolean isApplet() {
/* 35 */     return isApplet;
/*    */   }
/*    */ 
/*    */   public void init() {
/* 39 */     String simClassName = getParameter("Simulation");
/* 40 */     String simHumanName = getParameter("Name");
/* 41 */     final JButton button = new JButton("Start " + simHumanName);
/* 42 */     setLayout(new BorderLayout());
/* 43 */     add(button, "Center");
/*    */     try
/*    */     {
/* 47 */       final Class simClass = Class.forName(simClassName, true, Thread.currentThread().getContextClassLoader());
/* 48 */       if ((!GUIState.class.isAssignableFrom(simClass)) && (!simClass.equals(Console.class)))
/* 49 */         throw new Exception("Class is not a GUIState or the Console: " + simClass);
/* 50 */       button.addActionListener(new ActionListener()
/*    */       {
/*    */         public void actionPerformed(ActionEvent evt)
/*    */         {
/*    */           try
/*    */           {
/* 56 */             SimApplet.this.setupApplet(simClass);
/*    */           }
/*    */           catch (Exception e)
/*    */           {
/* 60 */             SimApplet.this.doException(button, e);
/*    */           }
/*    */         }
/*    */       });
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 67 */       doException(button, e);
/*    */     }
/*    */   }
/*    */ 
/*    */   void setupApplet(Class GUIStateClass) throws Exception
/*    */   {
/* 73 */     if (GUIStateClass.equals(Console.class)) {
/* 74 */       Console.main(new String[0]);
/*    */     }
/*    */     else {
/* 77 */       GUIState state = (GUIState)GUIStateClass.newInstance();
/*    */ 
/* 82 */       state.createController();
/*    */     }
/*    */   }
/*    */ 
/*    */   void doException(JComponent button, Exception e)
/*    */   {
/* 88 */     JTextArea text = new JTextArea();
/* 89 */     text.setText("" + e);
/* 90 */     JScrollPane scroll = new JScrollPane(text);
/* 91 */     if (button != null) remove(button);
/* 92 */     add(scroll, "Center");
/* 93 */     e.printStackTrace();
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.display.SimApplet
 * JD-Core Version:    0.6.2
 */