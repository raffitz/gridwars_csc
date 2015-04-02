import cern.ais.gridwars.Coordinates;
import cern.ais.gridwars.UniverseView;
import cern.ais.gridwars.bot.PlayerBot;
import cern.ais.gridwars.command.MovementCommand;

import java.lang.Long;
import java.util.List;

public class RadExp implements PlayerBot
{
	@Override public void getNextCommands(UniverseView universeView, List<MovementCommand> list)
	{
		long mine;
		long troops;
		long counter;
		
		char go[] = new char[4];
		
		for (Coordinates c : universeView.getMyCells()) {
			mine = universeView.getPopulation(c);
			troops = mine;
			
			/* Breeding Base: */
			troops-=5;
			
			if(troops<=0) continue;
			
			go[0]=0;
			go[1]=0;
			go[2]=0;
			go[3]=0;
			
			counter = 0;
			
			if((!universeView.belongsToMe(c.getUp())) || universeView.getPopulation(c.getUp()) < mine ){
				counter++;
				go[0]++;
			}
			
			if((!universeView.belongsToMe(c.getLeft())) || universeView.getPopulation(c.getLeft()) < mine ){
				counter++;
				go[1]++;
			}
			
			if((!universeView.belongsToMe(c.getDown())) || universeView.getPopulation(c.getDown()) < mine ){
				counter++;
				go[2]++;
			}
			
			if((!universeView.belongsToMe(c.getRight())) || universeView.getPopulation(c.getRight()) < mine ){
				counter++;
				go[3]++;
			}
			
			troops = troops / counter;
			
			if(troops<5) continue;
			
			if(go[3]==1){
				list.add(new MovementCommand(c,MovementCommand.Direction.RIGHT, troops));
			}
			
			if(go[2]==1){
				list.add(new MovementCommand(c,MovementCommand.Direction.DOWN, troops));
			}
			
			
			if(go[1]==1){
				list.add(new MovementCommand(c,MovementCommand.Direction.LEFT, troops));
			}
			
			
			
			
			if(go[0]==1){
				list.add(new MovementCommand(c,MovementCommand.Direction.UP, troops));
			}
			
			
			
				
		}
	}
}
