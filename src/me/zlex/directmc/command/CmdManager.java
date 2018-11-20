package me.zlex.directmc.command;
import java.util.ArrayList;
public class CmdManager{
	private ArrayList<Cmd> cmds;
	public CmdManager(){
		cmds = new ArrayList<Cmd>();
	}
	public void addCommand(Cmd cmd){
		cmds.add(cmd);
	}
	public void removeCommand(Cmd cmd){
		cmds.remove(cmd);
	}
	public ArrayList<Cmd> getCommands(){
		return cmds;
	}
	public Cmd getCommandByName(String name){
		for (Cmd cmd: cmds){
			if (name.toLowerCase() == cmd.getName().toLowerCase()){
				return cmd;
			}
		}
		return null;
	}
	public String getString(String cmd, String title){
		return getCommandByName(cmd).getString(title);
	}
	public int getInteger(String cmd, String title){
		return getCommandByName(cmd).getInteger(title);
	}
	public boolean getBoolean(String cmd, String title){
		return getCommandByName(cmd).getBoolean(title);
	}
	public double getDouble(String cmd, String title){
		return getCommandByName(cmd).getDouble(title);
	}
}