package net.nunnerycode.bukkit.itemattributes.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.managers.PermissionsManager;

public class ItemAttributesPermissionsManager implements PermissionsManager {

	private final ItemAttributes plugin;
	private final List<String> permissions;

	public ItemAttributesPermissionsManager(ItemAttributes plugin) {
		this.plugin = plugin;
		permissions = new ArrayList<String>();
	}

	public void load() {
		getPlugin().getPermissionsYAML().load();
		permissions.clear();
		permissions.addAll(getPlugin().getPermissionsYAML().getStringList("permissions"));
	}

	public void save() {
		getPlugin().getPermissionsYAML().load();
		getPlugin().getPermissionsYAML().set("permissions", this.permissions);
		getPlugin().getPermissionsYAML().save();
	}

	@Override
	public ItemAttributes getPlugin() {
		return plugin;
	}

	@Override
	public List<String> getPermissions() {
		return Collections.unmodifiableList(permissions);
	}

	@Override
	public void addPermissions(String... permissions) {
		Collections.addAll(this.permissions, permissions);
	}

	@Override
	public void removePermissions(String... permissions) {
		for (String s : permissions) {
			if (this.permissions.contains(s)) {
				this.permissions.remove(s);
			}
		}
	}
}
