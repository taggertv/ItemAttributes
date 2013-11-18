package net.nunnerycode.bukkit.itemattributes.api.managers;

import java.util.List;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import org.bukkit.entity.Player;

public interface PermissionsManager {

	ItemAttributes getPlugin();

	List<String> getPermissions();

	void addPermissions(String... permissions);

	void removePermissions(String... permissions);

	boolean hasPermission(Player player, String permission);

}
