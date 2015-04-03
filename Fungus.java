import cern.ais.gridwars.Coordinates;
import cern.ais.gridwars.UniverseView;
import cern.ais.gridwars.bot.PlayerBot;
import cern.ais.gridwars.command.MovementCommand;

import java.lang.Long;
import java.util.List;
import java.util.Random;

public class RedShirt implements PlayerBot
{
	private boolean first = true;
	private Coordinates origin;
	private static final long BATSIZE = 5;
	
	private int[] getvector(Coordinates origin,Coordinates me,int size)
	{
		int result[] = new int[2];
		
		result[0] = origin.getX() - me.getX();
		result[1] = origin.getY() - me.getY();
		
		if(result[0]<-size/2){
			result[0]+=size;
		}else if(result[0]>size/2){
			result[0]-=size;
		}
		
		if(result[1]<-size/2){
			result[1]+=size;
		}else if(result[1]>size/2){
			result[1]-=size;
		}
		
		return result;
	}
	
	@Override public void getNextCommands(UniverseView universeView, List<MovementCommand> list)
	{
		long troops;
		long batallion;
		long tier;
		int order;
		Random rng = new Random();
		int ratio;
		
		
		
		for (Coordinates c : universeView.getMyCells()) {
			if(first){
				first = false;
				origin = c;
			}
			
			
			
			troops = universeView.getPopulation(c);
			
			
			
			/* Breeding Base: */
			troops-=BATSIZE;
			
			
			
			
			/*
						list.add(new MovementCommand(c,MovementCommand.Direction.RIGHT, BATSIZE));*/			
			
			
				
		}
	}
}
