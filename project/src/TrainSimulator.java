import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TrainSimulator 
{

	public static void main(String[] args) throws NumberFormatException, IOException, FullPlatformException, EmptyPlatformException 
	{ 
		if (args.length < 3)
			throw new IllegalArgumentException("You need to input 3 arguments in");
		ArrayList<Station> allStations = new ArrayList<Station>();
		ArrayList<Train> allTrains = new ArrayList<Train>();
		List<List<Integer>> allTrainsETD = new ArrayList<List<Integer>>();
		int worldTime = 0;
		for (int i = 1; i < args.length; i++)
		{
			String fileName = args[i];
			if (i == 1)
				try
				{
	
					BufferedReader in
					   = new BufferedReader(new FileReader(fileName));
					String line = in.readLine();
	
							while ((line = in.readLine()) != null)
							{
								int cutPoint = line.indexOf(",");
								int stationID = Integer.parseInt
										(line.substring(0, cutPoint));
								int stationCap = Integer.parseInt(line.substring(cutPoint + 1));
								Station newStation = new Station(stationID, stationCap);
								allStations.add(newStation);
							}
						
					in.close();
	
				}
				catch (FileNotFoundException e)
				{
					System.out.println("File: " + fileName + " Not Found.");
				}
			else
			{
				List<Integer> individualTrainETD = new ArrayList<Integer>();
				try
				{
	
					BufferedReader in
					   = new BufferedReader(new FileReader(fileName));
					String line = in.readLine();
	
							while ((line = in.readLine()) != null)
							{
								int cutPoint = line.indexOf(",");
								int trainID = Integer.parseInt
										(line.substring(0, cutPoint));
								allStations.get(0).getPlatform().put(new Train(trainID));
								allTrains.add(new Train(trainID));
								while (line.indexOf(",", cutPoint) != -1)
								{
									int nextCutPoint = line.indexOf(",", cutPoint);
									int trainETD = Integer.parseInt(line.substring(cutPoint + 1, nextCutPoint));
									individualTrainETD.add(trainETD);
									cutPoint = line.indexOf(",", nextCutPoint);
								}
								int trainETD = Integer.parseInt(line.substring(cutPoint + 1));
								individualTrainETD.add(trainETD);
								allTrainsETD.add(individualTrainETD);
							}
						
					in.close();
	
				}
				catch (FileNotFoundException e)
				{
					System.out.println("File: " + fileName + " Not Found.");
				}	
			}
			
			if (Integer.parseInt(args[1]) == 0)
			{
				for (int j = 0; j < allStations.size(); j++)
				{
					if (allStations.get(j).getPlatform().isEmpty())
						worldTime++;
					while (allStations.get(j).getPlatform().isEmpty() != true)
					{
						int trainIDNumber = allStations.get(j).getPlatform().check().getId();
						int stationIDNumber = allStations.get(j).getId();
						allStations.get(j).getPlatform().get();
						worldTime++;
						System.out.println(worldTime + ":	Train " + trainIDNumber + 
								" has exited from station " + stationIDNumber + ".");
					}
				}
			}
		}

	}

}
