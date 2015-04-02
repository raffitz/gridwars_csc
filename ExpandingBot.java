import cern.ais.gridwars.Coordinates;
import cern.ais.gridwars.UniverseView;
import cern.ais.gridwars.bot.PlayerBot;
import cern.ais.gridwars.command.MovementCommand;

import java.lang.Long;
import java.util.List;

public class ExpandingBot implements PlayerBot
{
	@Override public void getNextCommands(UniverseView universeView, List<MovementCommand> list)
	{
		long troops;
		long batallion;
		int tier;
		
		for (Coordinates c : universeView.getMyCells()) {
			troops = universeView.getPopulation(c);
			
			/* Breeding Base: */
			troops-=10;
			
			tier = ((int) troops)/10;
			
			while(tier>0){
				list.add(new MovementCommand(c,MovementCommand.Direction.UP, (long)10));
				tier--;
				if(tier>0){
					list.add(new MovementCommand(c,MovementCommand.Direction.DOWN, (long)10));
					tier--;
				}
				if(tier>0){
					list.add(new MovementCommand(c,MovementCommand.Direction.LEFT, (long)10));
					tier--;
				}
				if(tier>0){
					list.add(new MovementCommand(c,MovementCommand.Direction.RIGHT, (long)10));
					tier--;
				}
			}
			
			
			
				
		}
	}
}
