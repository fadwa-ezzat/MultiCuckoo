package sim.display;

import java.util.ArrayList;
import javax.swing.JFrame;
import sim.engine.Stoppable;
import sim.portrayal.Inspector;
import sim.util.Bag;

public abstract interface Controller
{
  /** @deprecated */
  public abstract void doChangeCode(Runnable paramRunnable);

  public abstract boolean registerFrame(JFrame paramJFrame);

  public abstract boolean unregisterFrame(JFrame paramJFrame);

  public abstract boolean unregisterAllFrames();

  public abstract ArrayList getAllFrames();

  public abstract void refresh();

  public abstract void setInspectors(Bag paramBag1, Bag paramBag2);

  public abstract void registerInspector(Inspector paramInspector, Stoppable paramStoppable);

  public abstract ArrayList getAllInspectors();
}

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.display.Controller
 * JD-Core Version:    0.6.2
 */