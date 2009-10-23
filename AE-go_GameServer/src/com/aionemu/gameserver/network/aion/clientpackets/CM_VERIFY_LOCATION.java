/**
 * This file is part of aion-emu <aion-emu.com>.
 *
 *  aion-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-emu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.clientpackets;

import org.apache.log4j.Logger;

import com.aionemu.gameserver.controllers.movement.MovementType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.google.inject.Inject;

/**
 * Packet about player flying teleport movement.
 * 
 * @author -Nemesiss-
 * 
 */
public class CM_VERIFY_LOCATION extends AionClientPacket
{
	/**
	 * logger for this class
	 */
	private static final Logger	log	= Logger.getLogger(CM_VERIFY_LOCATION.class);
	@Inject
	private World				world;

	/**
	 * Constructs new instance of <tt>CM_VERIFY_LOCATION </tt> packet
	 * 
	 * @param opcode
	 */
	public CM_VERIFY_LOCATION(int opcode)
	{
		super(opcode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void readImpl()
	{
		int mapid;
		float x, y, z;
		Player player = getConnection().getActivePlayer();
		mapid = readD();
		x = readF();
		y = readF();
		z = readF();
		byte heading = (byte) readC();
		//Todo broadcast the correct packet for player move
		//PacketSendUtility.broadcastPacket(player, new SM_MOVE(player, x, y, z, x2, y2, z2, heading, type), false);
		world.updatePosition(player, x, y, z, heading);
	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void runImpl()
	{
		Player player = getConnection().getActivePlayer();

		if(player.isProtectionActive())
			player.setProtectionActive(false);
	}
}