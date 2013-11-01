package net.nunnerycode.bukkit.itemattributes.events;

import net.nunnerycode.bukkit.itemattributes.api.events.ItemAttributesEvent;
import net.nunnerycode.bukkit.itemattributes.api.events.attributes.LevelRequirementUpdateEvent;
import net.nunnerycode.bukkit.itemattributes.api.events.attributes.LivingEntityAttributeEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;

public class ItemAttributesLevelRequirementUpdateEvent extends ItemAttributesEvent implements LivingEntityAttributeEvent,
		LevelRequirementUpdateEvent, Cancellable {

	private LivingEntity livingEntity;
	private int levelRequired;
	private int currentLevel;
	private boolean cancelled;

	public ItemAttributesLevelRequirementUpdateEvent(LivingEntity livingEntity, int levelRequired, int currentLevel) {
		this.livingEntity = livingEntity;
		this.levelRequired = levelRequired;
		this.currentLevel = currentLevel;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean b) {
		this.cancelled = b;
	}

	@Override
	public LivingEntity getLivingEntity() {
		return livingEntity;
	}

	@Override
	public int getLevelRequired() {
		return levelRequired;
	}

	@Override
	public int getCurrentLevel() {
		return currentLevel;
	}
}
