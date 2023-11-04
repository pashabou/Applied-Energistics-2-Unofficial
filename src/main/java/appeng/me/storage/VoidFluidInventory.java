/*
 * This file is part of Applied Energistics 2. Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved. Applied
 * Energistics 2 is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version. Applied Energistics 2 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details. You should have received a copy of the GNU Lesser General Public License along with
 * Applied Energistics 2. If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.me.storage;

import javax.annotation.Nonnull;

import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IItemList;
import appeng.tile.misc.TileCondenser;

public class VoidFluidInventory implements IMEInventoryHandler<IAEFluidStack> {

    private final TileCondenser target;

    public VoidFluidInventory(final TileCondenser te) {
        this.target = te;
    }

    @Override
    public IAEFluidStack injectItems(final IAEFluidStack input, final Actionable mode, final BaseActionSource src) {
        if (mode == Actionable.SIMULATE) {
            return null;
        }

        if (input != null) {
            this.target.addPower(input.getStackSize() / 1000.0);
        }
        return null;
    }

    @Override
    public IAEFluidStack extractItems(final IAEFluidStack request, final Actionable mode, final BaseActionSource src) {
        return null;
    }

    @Override
    public IItemList<IAEFluidStack> getAvailableItems(final IItemList out) {
        return out;
    }

    @Override
    public IAEFluidStack getAvailableItem(@Nonnull IAEFluidStack request) {
        return null;
    }

    @Override
    public StorageChannel getChannel() {
        return StorageChannel.FLUIDS;
    }

    @Override
    public AccessRestriction getAccess() {
        return AccessRestriction.WRITE;
    }

    @Override
    public boolean isPrioritized(final IAEFluidStack input) {
        return false;
    }

    @Override
    public boolean canAccept(final IAEFluidStack input) {
        return true;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public int getSlot() {
        return 0;
    }

    @Override
    public boolean validForPass(final int i) {
        return i == 2;
    }

    @Override
    public boolean getSticky() {
        return false;
    }
}
