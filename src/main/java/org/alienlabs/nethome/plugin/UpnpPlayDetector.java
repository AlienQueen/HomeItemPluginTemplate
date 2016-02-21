/**
 * Copyright (C) 2005-2013, Stefan Strömberg <stefangs@nethome.nu>
 * <p>
 * This file is part of OpenNetHome  (http://www.nethome.nu)
 * <p>
 * OpenNetHome is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * OpenNetHome is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.alienlabs.nethome.plugin;

import nu.nethome.home.impl.CommandLineExecutor;
import nu.nethome.home.item.HomeItem;
import nu.nethome.home.item.HomeItemAdapter;
import nu.nethome.home.item.HomeItemType;
import nu.nethome.home.system.Event;
import nu.nethome.home.system.HomeService;
import nu.nethome.util.plugin.Plugin;

import java.util.logging.Logger;

/**
 * Detects when a network device launches a UPnP media play.
 *
 * @author Zala Pierre GOUPIL
 */
@Plugin
@HomeItemType(value = "Controls", creationEvents = "Upnp_Detector_Message")
public class UpnpPlayDetector extends HomeItemAdapter implements HomeItem
{

	private static final String MODEL = ("<?xml version = \"1.0\"?> \n"
		+ "<HomeItem Class=\"UpnpPlayDetector\" Category=\"Controls\"> "
		+ "  <Attribute Name=\"UPnP play detector\" Type=\"String\" Get=\"getValue\" Default=\"false\" /> "
		+ "  <Attribute Name=\"State\" Type=\"String\" Get=\"getState\" Init=\"setState\" Default=\"true\" />"
		+ "  <Attribute Name=\"Delay\" Type=\"String\" Get=\"getDelay\" 	Set=\"setDelay\" />"
		+ "  <Attribute Name=\"Device IP address\" Type=\"String\" Get=\"getDeviceIp\" Set=\"setDeviceIp\" /> "
		+ "  <Attribute Name=\"Command1\" Type=\"Command\" Get=\"getCommand1\" 	Set=\"setCommand1\" />"
		+ "  <Attribute Name=\"Command2\" Type=\"Command\" Get=\"getCommand2\" 	Set=\"setCommand2\" />"
		+ "  <Attribute Name=\"Command3\" Type=\"Command\" Get=\"getCommand3\" 	Set=\"setCommand3\" />"
		+ "  <Attribute Name=\"Command4\" Type=\"Command\" Get=\"getCommand4\" 	Set=\"setCommand4\" />"
		+ "  <Attribute Name=\"Command5\" Type=\"Command\" Get=\"getCommand5\" 	Set=\"setCommand5\" />"
		+ "  <Attribute Name=\"Command6\" Type=\"Command\" Get=\"getCommand6\" 	Set=\"setCommand6\" />"
		+ "  <Action Name=\"Enable\" 	Method=\"enableDetector\" />"
		+ "  <Action Name=\"Disable\" 	Method=\"disableDetector\" />"
		+ "  <Action Name=\"Detect\" Method=\"detect\"/> </HomeItem>");

	protected Logger logger = Logger.getLogger(UpnpPlayDetector.class.getName());
	// Public attributes
	protected String play = "false";
	protected long m_Delay = 5000; // Delay in ms
	protected String m_Command1 = "";
	protected String m_Command2 = "";
	protected String m_Command3 = "";
	protected String m_Command4 = "";
	protected String m_Command5 = "";
	protected CommandLineExecutor m_Executor;
	protected String itemDeviceIp = "";
	protected boolean activeState = true;

	public UpnpPlayDetector()
	{
	}

	@Override
	public void activate(HomeService server)
	{
		super.activate(server);
		m_Executor = new CommandLineExecutor(server, true);
	}

	@Override
	public boolean receiveEvent(Event event)
	{
		return super.receiveEvent(event);
	}

	public String getModel()
	{
		return MODEL;
	}

	/**
	 * HomeItem method which stops all object activity for program termination
	 */
	public void stop()
	{
	}

	public String getState()
	{
		return activeState ? "Enabled" : "Disabled";
	}

	public void setState(String state)
	{
		activeState = state.compareToIgnoreCase("disabled") != 0;
	}

	protected void performCommand(String commandString)
	{
		String result = m_Executor.executeCommandLine(commandString);
		if (!result.startsWith("ok"))
		{
			logger.warning(result);
		}
	}

	public void action()
	{
		if (!activeState)
		{
			return;
		}
		try
		{
			performCommand(m_Command1);
			if (m_Command2.length() != 0)
				Thread.sleep(m_Delay);
			performCommand(m_Command2);
			if (m_Command3.length() != 0)
				Thread.sleep(m_Delay);
			performCommand(m_Command3);
			if (m_Command4.length() != 0)
				Thread.sleep(m_Delay);
			performCommand(m_Command4);
			if (m_Command5.length() != 0)
				Thread.sleep(m_Delay);
			performCommand(m_Command5);
		}
		catch (InterruptedException i)
		{
		}
	}

	public String getDeviceIp()
	{
		return itemDeviceIp;
	}

	public void setDeviceIp(String deviceIp)
	{
		itemDeviceIp = deviceIp;
	}

	public void detect()
	{
		play = "true";
	}

	public String enableDetector()
	{
		activeState = true;
		return "";
	}

    public String disableDetector()
	{
		activeState = false;
		return "";
	}

	public String getValue()
	{
		return Double.toString(Math.random());
	}

	/**
	 * @return Returns the m_Delay.
	 */
	public String getDelay()
	{
		return Double.toString(m_Delay / 1000.0);
	}

	/**
	 * @param Delay
	 *            The m_Delay to set.
	 */
	public void setDelay(String Delay)
	{
		m_Delay = Math.round(Double.parseDouble(Delay) * 1000);
	}

	/**
	 * @return Returns the m_Command2.
	 */
	public String getCommand2()
	{
		return m_Command2;
	}

	/**
	 * @param Command2
	 *            The m_Command2 to set.
	 */
	public void setCommand2(String Command2)
	{
		m_Command2 = Command2;
	}

	/**
	 * @return Returns the m_Command1.
	 */
	public String getCommand1()
	{
		return m_Command1;
	}

	/**
	 * @param Command1
	 *            The m_Command1 to set.
	 */
	public void setCommand1(String Command1)
	{
		m_Command1 = Command1;
	}

	/**
	 * @return Returns the m_Command3.
	 */
	public String getCommand3()
	{
		return m_Command3;
	}

	/**
	 * @param Command3
	 *            The m_Command3 to set.
	 */
	public void setCommand3(String Command3)
	{
		m_Command3 = Command3;
	}

	/**
	 * @return Returns the m_Command4.
	 */
	public String getCommand4()
	{
		return m_Command4;
	}

	/**
	 * @param Command4
	 *            The m_Command4 to set.
	 */
	public void setCommand4(String Command4)
	{
		m_Command4 = Command4;
	}

	public String getCommand5()
	{
		return m_Command5;
	}

	public void setCommand5(String Command5)
	{
		m_Command5 = Command5;
	}
}
