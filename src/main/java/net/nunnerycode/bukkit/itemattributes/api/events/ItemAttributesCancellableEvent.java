package net.nunnerycode.bukkit.itemattributes.api.events;

import org.bukkit.event.Cancellable;

public class ItemAttributesCancellableEvent extends ItemAttributesEvent implements Cancellable {

  private boolean cancelled;

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public void setCancelled(boolean b) {
    this.cancelled = b;
  }

}
