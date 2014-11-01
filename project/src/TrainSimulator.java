import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class TrainSimulator 
{

	public static void main(String[] args) throws NumberFormatException, IOException, FullPlatformException, EmptyPlatformException
	{ 

		if (args.length < 3 || args.length > 3)
		{
			throw new IllegalArgumentException("3 arguments required");
		}
		List<Station> allStations = new ArrayList<Station>();
		SimpleStack<Train> orderingStack = new SimpleStack<Train>();
		List<SimpleQueue<Train>> trainTracks = new ArrayList<SimpleQueue<Train>>();
		List<Train> trains = new ArrayList<Train>();
		int worldTime = 0;
		for (int i = 1; i < args.length; i++)
		{
			String fileName = args[i];
			if (i == 1)
			{
				try
			{

					BufferedReader in
					= new BufferedReader(new FileReader(fileName));
					String line = in.readLine();
					while ((line = in.readLine()) != null)
					{
						StringTokenizer token = new StringTokenizer(line, ",");
						if (token.countTokens() == 1)
						{
							
						}
						else
						{
							int stationID = Integer.parseInt(token.nextToken());
							int stationCap = Integer.parseInt(token.nextToken());
							Station newStation = new Station(stationID, stationCap);
							allStations.add(newStation);
						}
					}

					in.close();

			}
				catch (FileNotFoundException e)
				{
					System.out.println("File: " + fileName + " Not Found.");
				}
			}
			else
			{
				try
				{
					BufferedReader in
					= new BufferedReader(new FileReader(fileName));
					String line = in.readLine();
					for (int z = 0; z < allStations.size() - 1; z++)
					{
						trainTracks.add(new SimpleQueue<Train>(Integer.parseInt(line)));
					}
					orderingStack = new SimpleStack<Train>(Integer.parseInt(line));

					while ((line = in.readLine()) != null)
					{
						StringTokenizer token = new StringTokenizer(line, ",");
						if (token.countTokens() == 1)
						{
							
						}
						int trainID = Integer.parseInt(token.nextToken());
						orderingStack.push(new Train(trainID));
						trains.add(new Train(trainID));
						while (token.hasMoreTokens())
						{
							int trainETD = Integer.parseInt(token.nextToken());
							orderingStack.peek().getETD().add(trainETD);
						}
					}

					while (!orderingStack.isEmpty())
					{
						allStations.get(0).getPlatform().put(orderingStack.pop());
					}

					in.close();

				}
				catch (FileNotFoundException | FullStackException e)
				{
					System.out.println("File: " + fileName + " Not Found.");
				}	
			}
		}
		int n = Integer.parseInt(args[0]);
		while (!allTrainsAreDone(allStations))
		{
			for (int i = 0; i < allStations.size() - 1; i++)
			{
				while (!allStations.get(i).getPlatform().isEmpty() 
						&& getTrain(allStations, i).getETD().get(i) <= worldTime)
				{
					if (getTrain(allStations, i).getATA().size() > 0)
					{
						if (getTrain(allStations, i).getATA().get(i) + 1 <= worldTime)
						{
							debarkStation(allStations, i, worldTime, trainTracks, n, trains);
						}
					}
					else
					{
						debarkStation(allStations, i, worldTime, trainTracks, n, trains);
					}
				}
			}

			for (int j = 0; j < trainTracks.size(); j++)
			{
				if (!trainTracks.get(j).isEmpty())
				{
					boolean done = false;
					while (!done)
					{
						if (allStations.get(j + 1).getPlatform().isFull()
								|| trainTracks.get(j).isEmpty())
						{
							break;
						}
						try {
							if (trainTracks.get(j).peek().getATD().size() > j)
							{
								if (trainTracks.get(j).peek().getATD().get(j) + 10 <= worldTime)
								{
									arriveAtStation(allStations, j + 1,
											j, worldTime, trainTracks, n, trains);
								}
								else
								{
									done = true;
								}
							}
							else
							{
								done = true;
							}
						} catch (EmptyQueueException e) {
							System.out.println("Tracks are empty.");
						}
					}
				}
			}
			worldTime++;
		}
		if (n == 1)
		{
			String result = "";
			for(int i = 0; i < trains.size(); i++)
			{
				result = result + "[";
				for(int j = 0; j < trains.get(i).getATD().size(); j++)
				{
					result = result + trains.get(i).getATD().get(j) + ", ";
				}
				result = result.substring(0, result.length()-2);
				result = result + "]\n";
			}
			System.out.println(result);	
		}
		if (n == 2)
		{
			String result = "";
			for(int i = 0; i < trains.size(); i++)
			{
				result = result + "[";
				for(int j = 0; j < trains.get(i).getATA().size(); j++)
				{
					result = result + trains.get(i).getATA().get(j) + ", ";
				}
				result = result.substring(0, result.length()-2);
				result = result + "]\n";
			}
			System.out.println(result);	
		}

	}
	public static void debarkStation(List<Station> stations, int station,
			int time, List<SimpleQueue<Train>> trainTracks, int n, List<Train> trains) 
					throws EmptyPlatformException
	{
		Train departingTrain = stations.get(station).getPlatform().check();
		int trainIDNumber = departingTrain.getId();
		departingTrain.getATD().add(time);
		for (int i = 0; i < trains.size(); i++)
		{
			if (trains.get(i).getId() == departingTrain.getId())
			{
				trains.get(i).getATD().add(time);
			}
		}
		try {
			trainTracks.get(station).enqueue(departingTrain);
		} catch (FullQueueException e) {
			System.out.println("Tracks are full!");
		}
		int stationIDNumber = stations.get(station).getId();
		stations.get(station).getPlatform().get();
		if (n == 0)
		{
			System.out.println(time + ":	Train " + trainIDNumber + 
					" has exited from station " + stationIDNumber + ".");
		}
	}
	public static void arriveAtStation(List<Station> stations, int station,
			int train, int time, List<SimpleQueue<Train>> trainTracks,
			int n, List<Train> trains) 
					throws FullPlatformException
	{
		Train arrivingTrain;
		try 
		{
			arrivingTrain = trainTracks.get(station-1).dequeue();
			int trainIDNumber = arrivingTrain.getId();
			arrivingTrain.getATA().add(time);
			for (int i = 0; i < trains.size(); i++)
			{
				if (trains.get(i).getId() == arrivingTrain.getId())
				{
					trains.get(i).getATA().add(time);
				}
			}
			stations.get(station).getPlatform().put(arrivingTrain);
			arrivingTrain.getATA().add(time);
			int stationIDNumber = stations.get(station).getId();
			if (n == 0)
			{
				System.out.println(time + ":	Train " + trainIDNumber + 
						" has been parked at station " + stationIDNumber + ".");
			}
		} catch (EmptyQueueException e) {
			System.out.println("Tracks are empty");
		}

	}
	public static boolean allTrainsAreDone(List<Station> stations)
	{
		if (stations.get(stations.size() - 1).getPlatform().isFull())
			return true;
		return false;
	}
	public static Train getTrain(List<Station> stations, int whichStation)
			throws EmptyPlatformException
	{
		return stations.get(whichStation).getPlatform().check();
	}

}
