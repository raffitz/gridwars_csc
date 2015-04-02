import cern.ais.gridwars.Coordinates;
import cern.ais.gridwars.UniverseView;
import cern.ais.gridwars.bot.PlayerBot;
import cern.ais.gridwars.command.MovementCommand;

import java.lang.Long;
import java.util.List;

public class RafaelBot implements PlayerBot
{
	@Override public void getNextCommands(UniverseView universeView, List<MovementCommand> list)
	{
		long troops;
		long batallion;
		
		for (Coordinates c : universeView.getMyCells()) {
			troops = universeView.getPopulation(c);
			if(troops>=20){
				list.add(new MovementCommand(c,MovementCommand.Direction.UP,(long) 10));
				troops-=10;
			}
			if(troops>=20){
				list.add(new MovementCommand(c,MovementCommand.Direction.LEFT,(long) 5));
				list.add(new MovementCommand(c,MovementCommand.Direction.RIGHT,(long) 5));
				troops-=10;
			}
			
				
		}
	}
}
