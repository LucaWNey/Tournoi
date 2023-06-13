package fr.neyuux.tournament.utils;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Anvil {

    public static Inventory openAnvilInventory(final Player player) {

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        FakeAnvil fakeAnvil = new FakeAnvil(entityPlayer);
        int containerId = entityPlayer.nextContainerCounter();

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutOpenWindow(containerId, "minecraft:anvil", new ChatMessage("Repairing", new Object[]{}), 0));

        entityPlayer.activeContainer = fakeAnvil;
        entityPlayer.activeContainer.windowId = containerId;
        entityPlayer.activeContainer.addSlotListener(entityPlayer);
        entityPlayer.activeContainer = fakeAnvil;
        entityPlayer.activeContainer.windowId = containerId;

        return fakeAnvil.getBukkitView().getTopInventory();
    }
}

final class FakeAnvil extends ContainerAnvil {

    public FakeAnvil(EntityHuman entityHuman) {
        super(entityHuman.inventory, entityHuman.world, new BlockPosition(0,0,0), entityHuman);
    }


    @Override
    public boolean a(EntityHuman entityHuman) {
        return true;
    }
}