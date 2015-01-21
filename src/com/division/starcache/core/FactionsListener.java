package com.division.starcache.core;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.massivecore.ps.PS;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class FactionsListener implements Listener{
	private Map<String, Integer> factionMap = new HashMap<String, Integer>();
	private final StarCache sC;
	
	public FactionsListener(StarCache instance){
		sC = instance;
	}
	
	
    @EventHandler(priority = EventPriority.LOW)
    public void onLandClaim(EventFactionsChunksChange evt) {
        CacheEvent cacheEvent = sC.getCacheEvent();
        if (cacheEvent == null || !cacheEvent.isActive()) {
            return;
        }
        Chunk eventChunk = cacheEvent.getEventChunk();
        Set<PS> evtChunks = evt.getChunks();
        Iterator<PS> iter = evtChunks.iterator();
        while(iter.hasNext()){
            PS chunk = iter.next();
        if (chunk.getChunkX() == eventChunk.getX() && chunk.getChunkZ() == eventChunk.getZ()) {
            Faction evtFaction = evt.getNewFaction();
            if(factionMap.containsKey(evtFaction.getName())){
                factionMap.put(evt.getNewFaction().getName(), factionMap.get(evtFaction.getName()).intValue()+1);
                if(factionMap.get(evtFaction.getName())>= 5){
                    evtFaction.sendMessage(ChatColor.GREEN+"*-*StarCache"+ChatColor.YELLOW+" unclaimed ALL of your faction's land.");
                    evtFaction.sendMessage(ChatColor.BLACK+"Just kidding! <3 Shake.");
                    factionMap.remove(evtFaction.getName());
                }
            } else{
                factionMap.put(evtFaction.getName(), 1);
            }
            evt.setCancelled(true);
            evt.getSender().sendMessage(String.format(StarCache.chatFormat, "You cannot claim the StarCache chunk."));
        }
        }
    }

}
