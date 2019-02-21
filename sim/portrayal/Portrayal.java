package sim.portrayal;

import sim.display.GUIState;

public abstract interface Portrayal
{
  public abstract Inspector getInspector(LocationWrapper paramLocationWrapper, GUIState paramGUIState);

  public abstract String getName(LocationWrapper paramLocationWrapper);

  public abstract String getStatus(LocationWrapper paramLocationWrapper);

  public abstract boolean setSelected(LocationWrapper paramLocationWrapper, boolean paramBoolean);
}

/* Location:           D:\To Dr-Leena\To Dr-Leena\MultiCuckoo.jar
 * Qualified Name:     sim.portrayal.Portrayal
 * JD-Core Version:    0.6.2
 */