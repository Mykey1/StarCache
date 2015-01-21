/* 
 * Copyright (C) 2015 Evan Cleary
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.division.starcache.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * 
 * @author Evan
 */
public class Cache {

	private Map<String, Integer> itemMap = new HashMap<String, Integer>();

	public Cache(List<String> entries) {
		for (int i = 0; i < entries.size(); ++i) {
			String raw = entries.get(i);
			String[] vals = raw.split("%");
			itemMap.put(vals[0], Integer.parseInt(vals[1]));
		}
	}

	public void insertCacheIntoChest(Chest chest) {
		Inventory inventory = chest.getBlockInventory();
		Set<String> keys = itemMap.keySet();
		for (String key : keys) {
			String[] split = key.split("#");
			if (split.length == 1) {
				inventory.addItem(new ItemStack(Integer.parseInt(split[0]), itemMap.get(key)));
			} else {
				inventory.addItem(new ItemStack(Integer.parseInt(split[0]), itemMap.get(key), Short.parseShort(split[1])));
			}
		}
	}
}
