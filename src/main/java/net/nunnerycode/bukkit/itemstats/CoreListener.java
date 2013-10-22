package net.nunnerycode.bukkit.itemstats;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class CoreListener implements Listener {

	private final ItemStatsPlugin plugin;

	public CoreListener(ItemStatsPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInventoryCloseEvent(InventoryCloseEvent event) {
		for (HumanEntity he : event.getViewers()) {
			ItemStack[] armorContents = he.getEquipment().getArmorContents();
			double d = 0.0;
			for (ItemStack is : armorContents) {
				if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
					d += ParseUtil.getHealth(is.getItemMeta().getLore(), getPlugin().getSettingsManager()
							.getHealthFormat());
				}
			}
			if (he.getEquipment().getItemInHand().hasItemMeta() && he.getEquipment().getItemInHand().getItemMeta()
					.hasLore()) {
				d += ParseUtil.getHealth(he.getEquipment().getItemInHand().getItemMeta().getLore(),
						getPlugin().getSettingsManager().getHealthFormat());
			}
			double currentHealth = he.getHealth();
			he.resetMaxHealth();
			double baseMaxHealth = he.getMaxHealth();
			he.setMaxHealth(baseMaxHealth + d);
			he.setHealth(Math.min(Math.max(currentHealth, 0), he.getMaxHealth()));
			if (he instanceof Player) {
				((Player) he).setHealthScale(he.getMaxHealth());
			}
		}
	}

	public ItemStatsPlugin getPlugin() {
		return plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		ItemStack[] armorContents = event.getPlayer().getEquipment().getArmorContents();
		double d = 0.0;
		for (ItemStack is : armorContents) {
			if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
				d += ParseUtil.getHealth(is.getItemMeta().getLore(), getPlugin().getSettingsManager()
						.getHealthFormat());
			}
		}
		if (event.getPlayer().getEquipment().getItemInHand().hasItemMeta() && event.getPlayer().getEquipment()
				.getItemInHand().getItemMeta().hasLore()) {
			d += ParseUtil.getHealth(event.getPlayer().getEquipment().getItemInHand().getItemMeta().getLore(),
					getPlugin().getSettingsManager().getHealthFormat());
		}
		double currentHealth = event.getPlayer().getHealth();
		event.getPlayer().resetMaxHealth();
		double baseMaxHealth = event.getPlayer().getMaxHealth();
		event.getPlayer().setMaxHealth(baseMaxHealth + d);
		event.getPlayer().setHealth(Math.min(Math.max(currentHealth, 0), event.getPlayer().getMaxHealth()));
		event.getPlayer().setHealthScale(event.getPlayer().getMaxHealth());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
		ItemStack[] armorContents = event.getPlayer().getEquipment().getArmorContents();
		double d = 0.0;
		for (ItemStack is : armorContents) {
			if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
				d += ParseUtil.getHealth(is.getItemMeta().getLore(), getPlugin().getSettingsManager()
						.getHealthFormat());
			}
		}
		if (event.getPlayer().getEquipment().getItemInHand().hasItemMeta() && event.getPlayer().getEquipment()
				.getItemInHand().getItemMeta().hasLore()) {
			d += ParseUtil.getHealth(event.getPlayer().getEquipment().getItemInHand().getItemMeta().getLore(),
					getPlugin().getSettingsManager().getHealthFormat());
		}
		double currentHealth = event.getPlayer().getHealth();
		event.getPlayer().resetMaxHealth();
		double baseMaxHealth = event.getPlayer().getMaxHealth();
		event.getPlayer().setMaxHealth(baseMaxHealth + d);
		event.getPlayer().setHealth(Math.min(Math.max(currentHealth, 0), event.getPlayer().getMaxHealth()));
		event.getPlayer().setHealthScale(event.getPlayer().getMaxHealth());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onItemBreakEvent(PlayerItemBreakEvent event) {
		ItemStack[] armorContents = event.getPlayer().getEquipment().getArmorContents();
		double d = 0.0;
		for (ItemStack is : armorContents) {
			if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
				d += ParseUtil.getHealth(is.getItemMeta().getLore(), getPlugin().getSettingsManager()
						.getHealthFormat());
			}
		}
		if (event.getPlayer().getEquipment().getItemInHand().hasItemMeta() && event.getPlayer().getEquipment()
				.getItemInHand().getItemMeta().hasLore()) {
			d += ParseUtil.getHealth(event.getPlayer().getEquipment().getItemInHand().getItemMeta().getLore(),
					getPlugin().getSettingsManager().getHealthFormat());
		}
		double currentHealth = event.getPlayer().getHealth();
		event.getPlayer().resetMaxHealth();
		double baseMaxHealth = event.getPlayer().getMaxHealth();
		event.getPlayer().setMaxHealth(baseMaxHealth + d);
		event.getPlayer().setHealth(Math.min(Math.max(currentHealth, 0), event.getPlayer().getMaxHealth()));
		event.getPlayer().setHealthScale(event.getPlayer().getMaxHealth());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onItemHeldEvent(PlayerItemHeldEvent event) {
		ItemStack[] armorContents = event.getPlayer().getEquipment().getArmorContents();
		double d = 0.0;
		for (ItemStack is : armorContents) {
			if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
				d += ParseUtil.getHealth(is.getItemMeta().getLore(), getPlugin().getSettingsManager()
						.getHealthFormat());
			}
		}
		if (event.getPlayer().getInventory().getItem(event.getNewSlot()).hasItemMeta() && event.getPlayer()
				.getInventory().getItem(event.getNewSlot()).getItemMeta().hasLore()) {
			d += ParseUtil.getHealth(event.getPlayer().getInventory().getItem(event.getNewSlot()).getItemMeta()
					.getLore(), getPlugin().getSettingsManager().getHealthFormat());
		}
		double currentHealth = event.getPlayer().getHealth();
		event.getPlayer().resetMaxHealth();
		double baseMaxHealth = event.getPlayer().getMaxHealth();
		event.getPlayer().setMaxHealth(baseMaxHealth + d);
		event.getPlayer().setHealth(Math.min(Math.max(currentHealth, 0), event.getPlayer().getMaxHealth()));
		event.getPlayer().setHealthScale(event.getPlayer().getMaxHealth());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityRegainHealthEvent(EntityRegainHealthEvent event) {
		double amount = event.getAmount();
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) event.getEntity();
			ItemStack[] armorContents = le.getEquipment().getArmorContents();
			for (ItemStack is : armorContents) {
				if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
					amount += ParseUtil.getRegeneration(is.getItemMeta().getLore(), getPlugin().getSettingsManager()
							.getRegenerationFormat());
				}
			}
			if (le.getEquipment().getItemInHand().hasItemMeta() && le.getEquipment().getItemInHand().getItemMeta()
					.hasLore()) {
				amount += ParseUtil.getRegeneration(le.getEquipment().getItemInHand().getItemMeta().getLore(),
						getPlugin().getSettingsManager().getRegenerationFormat());
			}
		}
		event.setAmount(amount);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
		Projectile projectile = event.getEntity();
		if (projectile.getShooter() == null) {
			return;
		}
		LivingEntity le = projectile.getShooter();
		Material shotItemMaterial = getMaterialFromEntityType(projectile.getType());
		ItemStack shotItem = new ItemStack(shotItemMaterial);
		ItemStack shootingItem = null;
		if (le.getEquipment() != null && le.getEquipment().getItemInHand() != null) {
			shootingItem = le.getEquipment().getItemInHand();
		}
		if (le instanceof Player) {
			ItemStack[] contents = ((Player) le).getInventory().getContents();
			for (int i = 0, contentsLength = contents.length; i < contentsLength; i++) {
				ItemStack is = contents[i];
				if (is == null || is.getType() == null) {
					continue;
				}
				if (is.getType() == shotItemMaterial) {
					shotItem = is;
					break;
				}
			}
		}

		double arrowDamage = 0.0D;
		if (shotItem.hasItemMeta() && shotItem.getItemMeta().hasLore()) {
			arrowDamage = ParseUtil.getDamage(shotItem.getItemMeta().getLore(), getPlugin().getSettingsManager()
					.getDamageFormat());
		}
		double bowDamage = 0.0;
		if (shootingItem != null && shootingItem.hasItemMeta() && shootingItem.getItemMeta().hasLore()) {
			bowDamage = ParseUtil.getDamage(shootingItem.getItemMeta().getLore(), getPlugin().getSettingsManager()
					.getDamageFormat());
		}
		double totalDamage = arrowDamage + bowDamage;

		event.getEntity().setMetadata("itemstats.damage", new FixedMetadataValue(getPlugin(), totalDamage));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamageEvent(EntityDamageEvent event) {
		if (event.isCancelled() || event instanceof EntityDamageByEntityEvent || !(event.getEntity() instanceof
				LivingEntity)) {
			return;
		}
		double damageReduction = 0.0;
		for (ItemStack is : ((LivingEntity) event.getEntity()).getEquipment().getArmorContents()) {
			if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
				damageReduction += ParseUtil.getHealth(is.getItemMeta().getLore(), getPlugin().getSettingsManager()
						.getHealthFormat());
			}
		}
		if (((LivingEntity) event.getEntity()).getEquipment().getItemInHand().hasItemMeta() && ((LivingEntity) event
				.getEntity()).getEquipment().getItemInHand().getItemMeta().hasLore()) {
			damageReduction += ParseUtil.getArmor(((LivingEntity) event.getEntity()).getEquipment().getItemInHand()
					.getItemMeta().getLore(), getPlugin().getSettingsManager().getArmorFormat());
		}
		event.setDamage(Math.max(event.getDamage() - damageReduction, 0.0));
	}

	private Material getMaterialFromEntityType(EntityType entityType) {
		switch (entityType) {
			case ARROW:
				return Material.ARROW;
			case SNOWBALL:
				return Material.SNOW_BALL;
			case FIREBALL:
				return Material.FIREBALL;
			case SMALL_FIREBALL:
				return Material.FIREBALL;
			case ENDER_PEARL:
				return Material.ENDER_PEARL;
			case THROWN_EXP_BOTTLE:
				return Material.EXP_BOTTLE;
			case WITHER_SKULL:
				return Material.FIREBALL;
			case SPLASH_POTION:
				return Material.POTION;
			case EGG:
				return Material.EGG;
			case FISHING_HOOK:
				return Material.FISHING_ROD;
			default:
				return null;
		}
	}

}
