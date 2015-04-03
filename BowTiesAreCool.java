import cern.ais.gridwars.Coordinates;
import cern.ais.gridwars.UniverseView;
import cern.ais.gridwars.bot.PlayerBot;
import cern.ais.gridwars.command.MovementCommand;

import java.lang.*;
import java.util.List;
import java.util.Random;

public class BowTiesAreCool implements PlayerBot
{
	private static final long BATSIZE = 5;
	
	private boolean first = true;
	
	private Coordinates origin;
	
	private boolean deathmatch = false;
	
	private MovementCommand.Direction[] getOrder(Coordinates me, Coordinates target, int size){
		int dx;
		int dy;
		
		MovementCommand.Direction dirs[]  = new MovementCommand.Direction[4];
		
		dy = target.getY() - me.getY();
		dx = target.getX() - me.getX();
		
		
		if(Math.abs(dx)>Math.abs(dy)){
			if((dx>0 && Math.abs(dx)<size/2) || (dx<0 && Math.abs(dx)>size/2)){
				dirs[0] = MovementCommand.Direction.LEFT;
				dirs[3] = MovementCommand.Direction.RIGHT;
			}else{
				dirs[0] = MovementCommand.Direction.RIGHT;
				dirs[3] = MovementCommand.Direction.LEFT;
			}
			
			if((dy>0 && Math.abs(dy)<size/2) || (dy<0 && Math.abs(dy)>size/2)){
				dirs[1] = MovementCommand.Direction.UP;
				dirs[2] = MovementCommand.Direction.DOWN;
			}else{
				dirs[1] = MovementCommand.Direction.DOWN;
				dirs[2] = MovementCommand.Direction.UP;
			}
		}else{
			if((dy>0 && Math.abs(dy)<size/2) || (dy<0 && Math.abs(dy)>size/2)){
				dirs[0] = MovementCommand.Direction.UP;
				dirs[3] = MovementCommand.Direction.DOWN;
			}else{
				dirs[0] = MovementCommand.Direction.DOWN;
				dirs[3] = MovementCommand.Direction.UP;
			}
			
			if((dx>0 && Math.abs(dx)<size/2) || (dx<0 && Math.abs(dx)>size/2)){
				dirs[1] = MovementCommand.Direction.LEFT;
				dirs[2] = MovementCommand.Direction.RIGHT;
			}else{
				dirs[1] = MovementCommand.Direction.RIGHT;
				dirs[2] = MovementCommand.Direction.LEFT;
			}
		}
		
		return dirs;
	}
	
	@Override public void getNextCommands(UniverseView universeView, List<MovementCommand> list)
	{
		long troops;
		long batallion;
		long tier;
		int order;
		Random rng = new Random();
		
		MovementCommand.Direction dirs[];
		
		if((!deathmatch) && universeView.getCurrentTurn()>universeView.getTurnLimit()/4){
			deathmatch = true;
		}
		
		for (Coordinates c : universeView.getMyCells()) {
		
			if(first){
				first = false;
				origin = c;
			
			}
		
			troops = universeView.getPopulation(c);
			
			if(deathmatch){
				if(!universeView.belongsToMe(c.getUp())){
					list.add(new MovementCommand(c,MovementCommand.Direction.UP, troops));
					continue;
				}
				if(!universeView.belongsToMe(c.getLeft())){
					list.add(new MovementCommand(c,MovementCommand.Direction.LEFT, troops));
					continue;
				}
				if(!universeView.belongsToMe(c.getRight())){
					list.add(new MovementCommand(c,MovementCommand.Direction.RIGHT, troops));
					continue;
				}
				if(!universeView.belongsToMe(c.getDown())){
					list.add(new MovementCommand(c,MovementCommand.Direction.DOWN, troops));
					continue;
				}
			}
			/* Breeding Base: */
			troops-=BATSIZE;
			
			tier = troops/BATSIZE;
			
			dirs = getOrder(c ,origin,universeView.getUniverseSize());
			
			while(tier>0){
				for(MovementCommand.Direction coiso : dirs){
					if(tier>0){
						list.add(new MovementCommand(c,coiso,BATSIZE));
						tier--;
					}else{
						break;
					}
				}
			}
			
				
		}
	}
}
