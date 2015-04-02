import cern.ais.gridwars.Coordinates;
import cern.ais.gridwars.UniverseView;
import cern.ais.gridwars.bot.PlayerBot;
import cern.ais.gridwars.command.MovementCommand;

import java.lang.Long;
import java.util.List;

public class NewRad implements PlayerBot
{
	@Override public void getNextCommands(UniverseView universeView, List<MovementCommand> list)
	{
		long troops;
		long mine;
		
		long go[] = new long[4];
		
		/*	0 - UP
			1 - LEFT
			2 - DOWN
			3 - RIGHT	*/
		
		
		for (Coordinates c : universeView.getMyCells()) {
			mine = universeView.getPopulation(c);
			
			troops = mine;
			
			if(troops>=25){
				troops-=5;
				troops = troops/4;
				
				if(troops<5) continue;
				
				if( universeView.isEmpty(c.getUp()) || !universeView.belongsToMe(c.getUp())){
					go[0] = troops;
				}else if(universeView.getPopulation(c.getUp()) < mine){
					go[0] = troops;
				}else{
					go[0] = 0;
				}
				
				if( universeView.isEmpty(c.getLeft()) || !universeView.belongsToMe(c.getLeft())){
					go[1] = troops;
				}else if(universeView.getPopulation(c.getLeft()) < mine){
					go[1] = troops;
				}else{
					go[1] = 0;
				}
				
				if( universeView.isEmpty(c.getDown()) || !universeView.belongsToMe(c.getDown())){
					go[2] = troops;
				}else if(universeView.getPopulation(c.getDown()) < mine){
					go[2] = troops;
				}else{
					go[2] = 0;
				}
				
				if( universeView.isEmpty(c.getRight()) || !universeView.belongsToMe(c.getRight())){
					go[3] = troops;
				}else if(universeView.getPopulation(c.getRight()) < mine){
					go[3] = troops;
				}else{
					go[3] = 0;
				}
				
				list.add(new MovementCommand(c,MovementCommand.Direction.UP, go[0]));
				list.add(new MovementCommand(c,MovementCommand.Direction.LEFT, go[1]));
				list.add(new MovementCommand(c,MovementCommand.Direction.DOWN, go[2]));
				list.add(new MovementCommand(c,MovementCommand.Direction.RIGHT, go[3]));
				
			}
			
			
			
				
		}
	}
}
