package examplebot;


import cern.ais.gridwars.UniverseView;
import cern.ais.gridwars.Coordinates;
import cern.ais.gridwars.bot.PlayerBot;
import cern.ais.gridwars.command.MovementCommand;

import java.util.List;
import java.util.Random;



public class ExampleBot implements PlayerBot
{
	
	private double dist(UniverseView universeview){
		int enemy_x=0, enemy_y=0, friend_x=0, friend_y=0;
		
		int size=  universeview.getUniverseSize();
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(!(universeview.isEmpty(i, j)) && !(universeview.belongsToMe(i, j))){
					enemy_x = i;
					enemy_y=j;
				}else if((universeview.isEmpty(i, j)) && (universeview.belongsToMe(i, j))){
					friend_x=i;
					friend_y=j;
				}
			}
		}
		
		double dist_x=friend_x-enemy_x;
		dist_x=Math.pow(dist_x, 2);
		double dist_y=friend_y-enemy_y;
		dist_y=Math.pow(dist_y, 2);
		double d=Math.sqrt(dist_x+dist_y);
		
		
		
		return d;
	}
	/*private Coordinates myStartCoordinates(UniverseView universeview){
		int size=  universeview.getUniverseSize();
		Coordinates oi=universeview.getCoordinates(0, 0);;
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if((universeview.isEmpty(i, j)) && (universeview.belongsToMe(i, j))){
					oi = universeview.getCoordinates(i, j);
					
				}
			}
		}
		
		return oi;
		
	}*/
	public static final long BATSIZE = 5;
	public void getNextCommands(UniverseView universeView, List<MovementCommand> list){
		
		double d=dist(universeView);
	//	Coordinates start=myStartCoordinates(universeView);
		int turn=universeView.getCurrentTurn();
		if(turn==0){
			for (Coordinates c : universeView.getMyCells()) {
				long pop=universeView.getPopulation(c);
				pop-=5;
				long up=pop/4;
				long down=pop/4;
				long right=pop/4;
				long left=pop-up-down-right;
				
				list.add(new MovementCommand(c, MovementCommand.Direction.UP, up));
				list.add(new MovementCommand(c, MovementCommand.Direction.DOWN, down));
				list.add(new MovementCommand(c, MovementCommand.Direction.LEFT, left));
				list.add(new MovementCommand(c, MovementCommand.Direction.RIGHT, right));
				
			}
		}
		else if(turn>0 || turn<d/5){
			for (Coordinates c : universeView.getMyCells()) {
				int x=0,y=0, start_x=0,start_y=0;
				long pop=universeView.getPopulation(c);
				x=c.getX();
				y=c.getY();
				start_x=c.getX();
				start_y=c.getY();
				
				if(pop>5){
					//right
					if(x>start_x){
						list.add(new MovementCommand(c, MovementCommand.Direction.RIGHT, pop-5));
					}
					//left
					if(x<start_x){
						list.add(new MovementCommand(c, MovementCommand.Direction.LEFT, pop-5));
					}
					//up
					if(y>start_y){
						list.add(new MovementCommand(c, MovementCommand.Direction.UP, pop-5));
					}
					//down
					if(y<start_y){
						list.add(new MovementCommand(c, MovementCommand.Direction.DOWN, pop-5));
					}
				}
			}
			
		}else{
	
			 long troops=0;
			//long batallion;
			long tier;
			int order;
			Random rng = new Random();
			
			for (Coordinates c : universeView.getMyCells()) {
				troops-=BATSIZE;
				
				tier = troops/BATSIZE;
				
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
