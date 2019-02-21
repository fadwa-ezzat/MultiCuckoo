/*    */ package sim.app.networktest;
/*    */ 
/*    */ import ec.util.MersenneTwisterFast;
/*    */ import sim.engine.Schedule;
/*    */ import sim.engine.SimState;
/*    */ import sim.field.continuous.Continuous2D;
/*    */ import sim.field.network.Network;
/*    */ import sim.util.Double2D;
/*    */ 
/*    */ public class NetworkTest extends SimState
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final double XMIN = 0.0D;
/*    */   public static final double XMAX = 800.0D;
/*    */   public static final double YMIN = 0.0D;
/*    */   public static final double YMAX = 600.0D;
/*    */   public static final double DIAMETER = 8.0D;
/* 25 */   public Continuous2D environment = null;
/* 26 */   public Network network = null;
/*    */ 
/*    */   public NetworkTest(long seed)
/*    */   {
/* 31 */     super(seed);
/*    */   }
/*    */ 
/*    */   boolean acceptablePosition(CustomNode node, Double2D location)
/*    */   {
/* 36 */     if ((location.x < 4.0D) || (location.x > 796.0D) || (location.y < 4.0D) || (location.y > 596.0D))
/*    */     {
/* 38 */       return false;
/* 39 */     }return true;
/*    */   }
/*    */ 
/*    */   CustomNode makeNode(String name)
/*    */   {
/* 44 */     CustomNode node = new CustomNode(name);
/* 45 */     this.environment.setObjectLocation(node, new Double2D(this.random.nextDouble() * 792.0D + 0.0D + 4.0D, this.random.nextDouble() * 592.0D + 0.0D + 4.0D));
/*    */ 
/* 48 */     this.network.addNode(node);
/* 49 */     this.schedule.scheduleRepeating(node);
/* 50 */     return node;
/*    */   }
/*    */ 
/*    */   public void start()
/*    */   {
/* 55 */     super.start();
/*    */ 
/* 57 */     this.environment = new Continuous2D(16.0D, 800.0D, 600.0D);
/* 58 */     this.network = new Network();
/*    */ 
/* 60 */     CustomNode[] nodes = new CustomNode[6];
/* 61 */     nodes[0] = makeNode("node0");
/* 62 */     nodes[1] = makeNode("node1");
/* 63 */     nodes[2] = makeNode("node2");
/* 64 */     nodes[3] = makeNode("node3");
/* 65 */     nodes[4] = makeNode("node4");
/* 66 */     nodes[5] = makeNode("node5");
/*    */ 
/* 68 */     this.network.addEdge(nodes[0], nodes[1], "0-1");
/* 69 */     this.network.addEdge(nodes[1], nodes[2], "1-2");
/* 70 */     this.network.addEdge(nodes[2], nodes[3], "2-3");
/* 71 */     this.network.addEdge(nodes[3], nodes[4], "3-4");
/* 72 */     this.network.addEdge(nodes[4], nodes[0], "4-0");
/* 73 */     this.network.addEdge(nodes[0], nodes[5], "0-5");
/* 74 */     this.network.addEdge(nodes[1], nodes[5], "1-5");
/* 75 */     this.network.addEdge(nodes[2], nodes[5], "2-5");
/* 76 */     this.network.addEdge(nodes[3], nodes[5], "3-5");
/* 77 */     this.network.addEdge(nodes[4], nodes[5], "4-5");
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 82 */     doLoop(NetworkTest.class, args);
/* 83 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.app.networktest.NetworkTest
 * JD-Core Version:    0.6.2
 */