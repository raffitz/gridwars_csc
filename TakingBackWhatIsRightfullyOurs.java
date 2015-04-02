import cern.ais.gridwars.Coordinates;
import cern.ais.gridwars.UniverseView;
import cern.ais.gridwars.bot.PlayerBot;
import cern.ais.gridwars.command.MovementCommand;

import java.lang.Long;
import java.util.List;

public class TakingBackWhatIsRightfullyOurs implements PlayerBot
{
	public static final long BATSIZE = 5;
	@Override public void getNextCommands(UniverseView universeView, List<MovementCommand> list)
	{
		long troops;
		long batallion;
		long tier;
		
		for (Coordinates c : universeView.getMyCells()) {
			troops = universeView.getPopulation(c);
			
			/* Breeding Base: */
			troops-=BATSIZE;
			
			tier = troops/BATSIZE;
			
			while(tier>0){
				list.add(new MovementCommand(c,MovementCommand.Direction.UP, BATSIZE));
				tier--;
				if(tier>0){
					list.add(new MovementCommand(c,MovementCommand.Direction.DOWN, BATSIZE));
					tier--;
				}
				if(tier>0){
					list.add(new MovementCommand(c,MovementCommand.Direction.LEFT, BATSIZE));
					tier--;
				}
				if(tier>0){
					list.add(new MovementCommand(c,MovementCommand.Direction.RIGHT, BATSIZE));
					tier--;
				}
			}
			
			
			
				
		}
	}
}
