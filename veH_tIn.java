import cern.ais.gridwars.Coordinates;
import cern.ais.gridwars.UniverseView;
import cern.ais.gridwars.bot.PlayerBot;
import cern.ais.gridwars.command.MovementCommand;

import java.lang.*;
import java.util.List;
import java.util.Random;

public class veH_tIn implements PlayerBot
{
	private static final long BATSIZE = 5;
	
	private boolean first = true;
	
	private Coordinates getTarget(UniverseView universe)
	{
		int max = universe.getUniverseSize();
		int i,j;
		
		for(i=0;i<=max;i++){
			for(j=0;j<=max;j++){
				if((!universe.isEmpty(i,j)) && (!universe.belongsToMe(i,j))){
					return universe.getCoordinates(i,j);
				}
			}
		}
		return universe.getCoordinates(0,0);
	}
	
	private Coordinates target;
	
	private boolean chasing = true;
	
	private MovementCommand.Direction[] getOrder(Coordinates me, Coordinates target, int size){
		int dx;
		int dy;
		
		MovementCommand.Direction dirs[]  = new MovementCommand.Direction[4];
		
		dx = target.getY() - me.getY();
		dy = target.getX() - me.getX();
		
		
		if(Math.abs(dx)>Math.abs(dy)){
			if((dx>0 && Math.abs(dx)<size/2) || (dx<0 && Math.abs(dx)>size/2)){
				dirs[0] = MovementCommand.Direction.RIGHT;
				dirs[3] = MovementCommand.Direction.LEFT;
			}else{
				dirs[0] = MovementCommand.Direction.LEFT;
				dirs[3] = MovementCommand.Direction.RIGHT;
			}
			
			if((dy>0 && Math.abs(dy)<size/2) || (dy<0 && Math.abs(dy)>size/2)){
				dirs[1] = MovementCommand.Direction.DOWN;
				dirs[2] = MovementCommand.Direction.UP;
			}else{
				dirs[1] = MovementCommand.Direction.UP;
				dirs[2] = MovementCommand.Direction.DOWN;
			}
		}else{
			if((dy>0 && Math.abs(dy)<size/2) || (dy<0 && Math.abs(dy)>size/2)){
				dirs[0] = MovementCommand.Direction.DOWN;
				dirs[3] = MovementCommand.Direction.UP;
			}else{
				dirs[0] = MovementCommand.Direction.UP;
				dirs[3] = MovementCommand.Direction.DOWN;
			}
			
			if((dx>0 && Math.abs(dx)<size/2) || (dx<0 && Math.abs(dx)>size/2)){
				dirs[1] = MovementCommand.Direction.RIGHT;
				dirs[2] = MovementCommand.Direction.LEFT;
			}else{
				dirs[1] = MovementCommand.Direction.LEFT;
				dirs[2] = MovementCommand.Direction.RIGHT;
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
		
		if(first){
			first = false;
			target = getTarget(universeView);
			
		}
		
		for (Coordinates c : universeView.getMyCells()) {
			troops = universeView.getPopulation(c);
			
			/* Breeding Base: */
			troops-=BATSIZE;
			
			tier = troops/BATSIZE;
			
			
			if(chasing){
				dirs = getOrder(c ,target,universeView.getUniverseSize());
				
				for(MovementCommand.Direction coiso : dirs){
					if(tier>0){
						list.add(new MovementCommand(c,coiso,BATSIZE));
						tier--;
					}else{
						break;
					}
				}
				
				
				
			}else{
				order = rng.nextInt(4);
			
				while(tier>0){
					switch(order){
						case 1:
							list.add(new MovementCommand(c,MovementCommand.Direction.RIGHT, BATSIZE));
					tier--;
							if(tier>0){
								list.add(new MovementCommand(c,MovementCommand.Direction.UP, BATSIZE));
								tier--;
							}
							if(tier>0){
								list.add(new MovementCommand(c,MovementCommand.Direction.LEFT, BATSIZE));
								tier--;
							}
							if(tier>0){
								list.add(new MovementCommand(c,MovementCommand.Direction.DOWN, BATSIZE));
								tier--;
							}
							break;
						case 2:
							list.add(new MovementCommand(c,MovementCommand.Direction.DOWN, BATSIZE));
					tier--;
							if(tier>0){
								list.add(new MovementCommand(c,MovementCommand.Direction.RIGHT, BATSIZE));
								tier--;
							}
							if(tier>0){
								list.add(new MovementCommand(c,MovementCommand.Direction.UP, BATSIZE));
								tier--;
							}
							if(tier>0){
								list.add(new MovementCommand(c,MovementCommand.Direction.LEFT, BATSIZE));
								tier--;
							}
							break;
						case 3:
							list.add(new MovementCommand(c,MovementCommand.Direction.LEFT, BATSIZE));
					tier--;
							if(tier>0){
								list.add(new MovementCommand(c,MovementCommand.Direction.DOWN, BATSIZE));
								tier--;
							}
							if(tier>0){
								list.add(new MovementCommand(c,MovementCommand.Direction.RIGHT, BATSIZE));
								tier--;
							}
							if(tier>0){
								list.add(new MovementCommand(c,MovementCommand.Direction.UP, BATSIZE));
								tier--;
							}
							break;
						default:
							list.add(new MovementCommand(c,MovementCommand.Direction.UP, BATSIZE));
					tier--;
							if(tier>0){
								list.add(new MovementCommand(c,MovementCommand.Direction.LEFT, BATSIZE));
								tier--;
							}
							if(tier>0){
								list.add(new MovementCommand(c,MovementCommand.Direction.DOWN, BATSIZE));
								tier--;
							}
							if(tier>0){
								list.add(new MovementCommand(c,MovementCommand.Direction.RIGHT, BATSIZE));
								tier--;
							}
							break;
					}
				}
			
			}
			
				
		}
	}
}
