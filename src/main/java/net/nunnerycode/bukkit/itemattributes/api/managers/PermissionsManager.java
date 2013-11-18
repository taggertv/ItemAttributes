package net.nunnerycode.bukkit.itemattributes.api.managers;

import java.util.List;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;

public interface PermissionsManager {

	ItemAttributes getPlugin();

	List<String> getPermissions();

	void addPermissions(String... permissions);

	void removePermissions(String... permissions);

}
