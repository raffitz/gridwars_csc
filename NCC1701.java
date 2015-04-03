import cern.ais.gridwars.Coordinates;
import cern.ais.gridwars.UniverseView;
import cern.ais.gridwars.bot.PlayerBot;
import cern.ais.gridwars.command.MovementCommand;

import java.lang.*;
import java.util.List;
import java.util.Random;

public class NCC1701 implements PlayerBot
{
	private static final long BATSIZE = 5;
	
	private boolean first = true;
	
	private Coordinates origin;
	
	private boolean deathmatch = false;
	
	private boolean escaping = false;
	
	private MovementCommand.Direction[] getOrder(Coordinates me, Coordinates target, int size){
		int dx;
		int dy;
		
		int order;
		Random rng = new Random();
		
		MovementCommand.Direction dirs[]  = new MovementCommand.Direction[4];
		
		dy = target.getY() - me.getY();
		dx = target.getX() - me.getX();
		
		if((!escaping) || deathmatch || dx*dx+dy*dy<=64){
			
			order = rng.nextInt(4);
			
			switch(order){
				case 1:
					dirs[0] = MovementCommand.Direction.RIGHT;
					dirs[1] = MovementCommand.Direction.UP;
					dirs[2] = MovementCommand.Direction.LEFT;
					dirs[3] = MovementCommand.Direction.DOWN;
					break;
				case 2:
					dirs[0] = MovementCommand.Direction.UP;
					dirs[1] = MovementCommand.Direction.LEFT;
					dirs[2] = MovementCommand.Direction.DOWN;
					dirs[3] = MovementCommand.Direction.RIGHT;
					break;
				case 3:
					
					dirs[0] = MovementCommand.Direction.LEFT;
					dirs[1] = MovementCommand.Direction.DOWN;
					dirs[2] = MovementCommand.Direction.UP;
					dirs[3] = MovementCommand.Direction.LEFT;
					break;
				default:
					
					dirs[0] = MovementCommand.Direction.UP;
					dirs[1] = MovementCommand.Direction.LEFT;
					dirs[2] = MovementCommand.Direction.DOWN;
					dirs[3] = MovementCommand.Direction.RIGHT;
					break;
			}
		}
		
		
		
		if(Math.abs(dx)>=Math.abs(dy)){
			if((dx>0 && Math.abs(dx)<size/2) || (dx<0 && Math.abs(dx)>size/2)){
				dirs[0] = MovementCommand.Direction.LEFT;
				dirs[3] = MovementCommand.Direction.RIGHT;
			}else if(dx==0){
				order = rng.nextInt(2);
				if(order == 1){
					dirs[0] = MovementCommand.Direction.RIGHT;
					dirs[3] = MovementCommand.Direction.LEFT;
				}else{
					dirs[0] = MovementCommand.Direction.LEFT;
					dirs[3] = MovementCommand.Direction.RIGHT;
				}
			}else{
				dirs[0] = MovementCommand.Direction.RIGHT;
				dirs[3] = MovementCommand.Direction.LEFT;
			}
			
			if((dy>0 && Math.abs(dy)<size/2) || (dy<0 && Math.abs(dy)>size/2)){
				dirs[1] = MovementCommand.Direction.UP;
				dirs[2] = MovementCommand.Direction.DOWN;
			}else if(dy==0){
				order = rng.nextInt(2);
				if(order == 1){
					dirs[1] = MovementCommand.Direction.UP;
					dirs[2] = MovementCommand.Direction.DOWN;
				}else{
					dirs[1] = MovementCommand.Direction.DOWN;
					dirs[2] = MovementCommand.Direction.UP;
				}
			}else{
				dirs[1] = MovementCommand.Direction.DOWN;
				dirs[2] = MovementCommand.Direction.UP;
			}
		}else{
			if((dy>0 && Math.abs(dy)<size/2) || (dy<0 && Math.abs(dy)>size/2)){
				dirs[0] = MovementCommand.Direction.UP;
				dirs[3] = MovementCommand.Direction.DOWN;
			}else if(dy==0){
				order = rng.nextInt(2);
				if(order == 1){
					dirs[0] = MovementCommand.Direction.UP;
					dirs[3] = MovementCommand.Direction.DOWN;
				}else{
					dirs[0] = MovementCommand.Direction.DOWN;
					dirs[3] = MovementCommand.Direction.UP;
				}
			}else{
				dirs[0] = MovementCommand.Direction.DOWN;
				dirs[3] = MovementCommand.Direction.UP;
			}
			
			if((dx>0 && Math.abs(dx)<size/2) || (dx<0 && Math.abs(dx)>size/2)){
				dirs[1] = MovementCommand.Direction.LEFT;
				dirs[2] = MovementCommand.Direction.RIGHT;
			}else if(dx==0){
				order = rng.nextInt(2);
				if(order == 1){
					dirs[1] = MovementCommand.Direction.RIGHT;
					dirs[2] = MovementCommand.Direction.LEFT;
				}else{
					dirs[1] = MovementCommand.Direction.LEFT;
					dirs[2] = MovementCommand.Direction.RIGHT;
				}
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
		
		boolean done;
		
		Random rng = new Random();
		
		MovementCommand.Direction dirs[];
		
		if((!escaping) && (universeView.getCurrentTurn()/25)%4 == 3){
			deathmatch = true;
		}else{
			deathmatch = false;
		}
		
		if(universeView.getCurrentTurn()>75){
			escaping = true;
		}
		
		if(universeView.getCurrentTurn()>150){
			escaping = false;
		}
		
		for (Coordinates c : universeView.getMyCells()) {
		
			if(first){
				first = false;
				origin = c;
			
			}
		
			troops = universeView.getPopulation(c);
			
			dirs = getOrder(c ,origin,universeView.getUniverseSize());
			done = false;
			if(deathmatch){
				for(MovementCommand.Direction coiso : dirs){
					if(!universeView.belongsToMe(c.getRelative(1,coiso))){
						list.add(new MovementCommand(c,coiso,troops));
						done = true;
						break;
					}
				}
			}
			if(done) continue;
			/* Breeding Base: */
			troops-=BATSIZE;
			
			tier = troops/BATSIZE;
			
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
