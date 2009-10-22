/*
 * This file is part of aion-unique <aionunique.smfnew.com>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TRADELIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SELL_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TELEPORT;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import org.apache.log4j.Logger;
/**
 * 
 * @author orz
 * 
 */
public class CM_DIALOG_SELECT extends AionClientPacket
{
	private static final Logger	log	= Logger.getLogger(CM_DIALOG_SELECT.class);
	/**
	* Target object id that client wants to TALK WITH or 0 if wants to unselect
	*/
	private int					targetObjectId;
	private int					unk1;
	private int					unk2;
	/**
	* Constructs new instance of <tt>CM_CM_REQUEST_DIALOG </tt> packet
	* @param opcode
	*/
	public CM_DIALOG_SELECT(int opcode)
	{
		super(opcode);
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	protected void readImpl()
	{
		targetObjectId = readD();// empty
		unk1 = readH(); //total no of choice
		unk2 = readH(); //maybe answer 1
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	protected void runImpl()
	{
		Player player = getConnection().getActivePlayer();
		if(player == null)
			return;
		switch (unk1)
		{
					case 2:
						sendPacket(new SM_TRADELIST(player, targetObjectId));
						break;
					case 3:
						sendPacket(new SM_SELL_ITEM(player, targetObjectId));
						break;
					case 4:
						//stigma
						sendPacket(new SM_MESSAGE(0, null, "This feature is not available yet", null, ChatType.ANNOUNCEMENTS));
						break;
					case 20:
						//warehouse
						sendPacket(new SM_DIALOG_WINDOW(targetObjectId, 26));
						break;
					case 27:
						//Consign trade?? npc karinerk, koorunerk
						sendPacket(new SM_MESSAGE(0, null, "This feature is not available yet", null, ChatType.ANNOUNCEMENTS));
						break;
					case 29:
						//soul healing
						sendPacket(new SM_MESSAGE(0, null, "This feature is not available yet", null, ChatType.ANNOUNCEMENTS));
						break;
					case 35:
						//Godstone socketing
						sendPacket(new SM_DIALOG_WINDOW(targetObjectId, 21));
						break;
					case 36:
						//remove mana stone 
						sendPacket(new SM_DIALOG_WINDOW(targetObjectId, 20));
						break;
					case 37:
						//modify appearance
						sendPacket(new SM_DIALOG_WINDOW(targetObjectId, 19));
						break;
					case 38:
						//flight and teleport
						sendPacket(new SM_TELEPORT(player, targetObjectId));
						break;
					case 39:
						//improve extraction skill npc cornelius, jhaelas in sanctum
						sendPacket(new SM_MESSAGE(0, null, "This feature is not available yet", null, ChatType.ANNOUNCEMENTS));
						break;
					case 40:
						//learn tailoring armor smithing etc...
						sendPacket(new SM_MESSAGE(0, null, "This feature is not available yet", null, ChatType.ANNOUNCEMENTS));
						break;
					case 41:
						//expand cube
						sendPacket(new SM_MESSAGE(0, null, "This feature is not available yet", null, ChatType.ANNOUNCEMENTS));
						break;
					case 52:
						//work order from lerning npc in sanctum
						sendPacket(new SM_MESSAGE(0, null, "This feature is not available yet", null, ChatType.ANNOUNCEMENTS));
						break;
					case 53:
						//coin reward
						sendPacket(new SM_MESSAGE(0, null, "This feature is not available yet", null, ChatType.ANNOUNCEMENTS));
						break;
					default:
						sendPacket(new SM_DIALOG_WINDOW(targetObjectId, unk1));
					break;
				}
		log.info("id: "+targetObjectId+" dialog type: " + unk1 +" other: " + unk2);
	}
}
