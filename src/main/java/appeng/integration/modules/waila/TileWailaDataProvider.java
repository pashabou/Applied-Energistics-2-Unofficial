/*
 * This file is part of Applied Energistics 2. Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved. Applied
 * Energistics 2 is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version. Applied Energistics 2 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details. You should have received a copy of the GNU Lesser General Public License along with
 * Applied Energistics 2. If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.integration.modules.waila;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import appeng.helpers.ICustomNameObject;
import appeng.integration.modules.waila.tile.ChargerWailaDataProvider;
import appeng.integration.modules.waila.tile.CraftingMonitorWailaDataProvider;
import appeng.integration.modules.waila.tile.InterfaceDataProvider;
import appeng.integration.modules.waila.tile.PowerStateWailaDataProvider;
import appeng.integration.modules.waila.tile.PowerStorageWailaDataProvider;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

/**
 * Delegation provider for tiles through {@link mcp.mobius.waila.api.IWailaDataProvider}
 *
 * @author thatsIch
 * @version rv2
 * @since rv2
 */
public final class TileWailaDataProvider implements IWailaDataProvider {

    private static final String NBT_TILE_CUSTOM_NAME = "tileCustomName";

    /**
     * Contains all providers
     */
    private final List<IWailaDataProvider> providers;

    /**
     * Initializes the provider list with all wanted providers
     */
    public TileWailaDataProvider() {
        final IWailaDataProvider charger = new ChargerWailaDataProvider();
        final IWailaDataProvider energyCell = new PowerStorageWailaDataProvider();
        final IWailaDataProvider craftingBlock = new PowerStateWailaDataProvider();
        final IWailaDataProvider craftingMonitor = new CraftingMonitorWailaDataProvider();
        final IWailaDataProvider interfaceBlock = new InterfaceDataProvider();

        this.providers = Lists.newArrayList(charger, energyCell, craftingBlock, craftingMonitor, interfaceBlock);
    }

    @Override
    public ItemStack getWailaStack(final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(final ItemStack itemStack, final List<String> currentToolTip,
            final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        for (final IWailaDataProvider provider : this.providers) {
            provider.getWailaHead(itemStack, currentToolTip, accessor, config);
        }

        return currentToolTip;
    }

    @Override
    public List<String> getWailaBody(final ItemStack itemStack, final List<String> currentToolTip,
            final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        for (final IWailaDataProvider provider : this.providers) {
            provider.getWailaBody(itemStack, currentToolTip, accessor, config);
        }
        if (accessor.getNBTData().hasKey(NBT_TILE_CUSTOM_NAME)) {
            currentToolTip.add(
                    EnumChatFormatting.WHITE.toString() + EnumChatFormatting.ITALIC
                            + accessor.getNBTData().getString(NBT_TILE_CUSTOM_NAME));
        }

        return currentToolTip;
    }

    @Override
    public List<String> getWailaTail(final ItemStack itemStack, final List<String> currentToolTip,
            final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        for (final IWailaDataProvider provider : this.providers) {
            provider.getWailaTail(itemStack, currentToolTip, accessor, config);
        }

        return currentToolTip;
    }

    @Override
    public NBTTagCompound getNBTData(final EntityPlayerMP player, final TileEntity te, final NBTTagCompound tag,
            final World world, final int x, final int y, final int z) {
        for (final IWailaDataProvider provider : this.providers) {
            provider.getNBTData(player, te, tag, world, x, y, z);
        }
        if (te instanceof ICustomNameObject customNameObject && customNameObject.hasCustomName()
                && !customNameObject.getCustomName().isEmpty()) {
            tag.setString(NBT_TILE_CUSTOM_NAME, customNameObject.getCustomName());
        }

        return tag;
    }
}
