import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TrainSimulator 
{

	public static void main(String[] args) throws NumberFormatException, IOException, FullPlatformException, EmptyPlatformException, FullQueueException, EmptyQueueException, FullStackException 
	{ 
		
		if (args.length < 3)
			throw new IllegalArgumentException("You need to input 3 arguments in");
		ArrayList<Station> allStations = new ArrayList<Station>();
		SimpleQueue<Train> trainsInTransit = new SimpleQueue<Train>();
		List<List<Integer>> allTrainsETD = new ArrayList<List<Integer>>();
		SimpleStack<Train> orderingStack = new SimpleStack<Train>();
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
					trainsInTransit = new SimpleQueue<Train>(Integer.parseInt(line));
					System.out.println(trainsInTransit.size());
					orderingStack = new SimpleStack<Train>(Integer.parseInt(line));

							while ((line = in.readLine()) != null)
							{
								int cutPoint = line.indexOf(",");
								int trainID = Integer.parseInt
										(line.substring(0, cutPoint));
								orderingStack.push(new Train(trainID));
								//System.out.println(orderingStack.peek().getId());
								//allStations.get(0).getPlatform().put(new Train(trainID));
								//System.out.println(allStations.get(0).getPlatform().check().getId());
								String s = line.substring(cutPoint + 1);
								//System.out.println(s);
								while (s.indexOf(",") != -1)
								{
									cutPoint = line.indexOf(",");
									//System.out.println(cutPoint);
									int trainETD = Integer.parseInt(line.substring(0, cutPoint));
									
									individualTrainETD.add(trainETD);
									s = s.substring(cutPoint + 1);
									//System.out.println(s);
								}
								int trainETD = Integer.parseInt(s);
								//System.out.println(trainETD);
								individualTrainETD.add(trainETD);
								allTrainsETD.add(individualTrainETD);
								
							}
							while (!orderingStack.isEmpty())
							{
								//System.out.println(orderingStack.pop().getId());
								allStations.get(0).getPlatform().put(orderingStack.pop());
							}
						
					in.close();
	
				}
				catch (FileNotFoundException e)
				{
					System.out.println("File: " + fileName + " Not Found.");
				}	
			}
			
		}
//		System.out.println(allStations.get(0).getPlatform().isEmpty());
//		List<Station> copy = new ArrayList<Station>(allStations);
//		for (int u = 0; u < copy.size(); u ++)
//			System.out.println(copy.get(0).getPlatform().get().getId());
//		System.out.println(allStations.get(0).getPlatform().isEmpty());
		if (Integer.parseInt(args[0]) == 0)
		{
			while (!allTrainsAreDone(allStations))
			{
				worldTime++;
				if (allTrainsAreMoving(allStations) == false)
				{
					for (int j = 0; j < allStations.size(); j++)
					{
						System.out.println("j: " + j + "Limit: " + allStations.size());
						System.out.println(worldTime);
						System.out.println("Red was here");
						//System.out.println(allStations.get(j).getPlatform().check().getId());
						if (allStations.get(j).getPlatform().isEmpty() == false)
						{
							debarkStation(allStations, j, worldTime, trainsInTransit);
							System.out.println(trainsInTransit.peek().getId());
						}
						System.out.println(trainsInTransit.isEmpty());
						System.out.println(allStations.get(j).getPlatform().isEmpty());
					}
				}
				if (allTrainsAreMoving(allStations) != false)
				{
					System.out.println("Got here");
					int headingTowards = trainsInTransit.peek().getATD().size();
					if (trainsInTransit.peek().getATD().get(headingTowards - 1) + 10 == worldTime)
					{
						worldTime++;
						arriveAtStation(allStations, headingTowards + 1,
								headingTowards, worldTime, trainsInTransit);
					}
				}
				while (trainsInTransit.isFull())
				{
					int headingTowards = 
							trainsInTransit.peek().getATD().size();
					if (!allStations.get(headingTowards).getPlatform()
							.isFull())
					{
						worldTime++;
						System.out.println(trainsInTransit.peek().getATD());
						System.out.println(worldTime);
						if (trainsInTransit.peek().getATD().
								get(headingTowards - 1) + 10 == worldTime)
						{
							arriveAtStation(allStations, headingTowards + 1,
									headingTowards, worldTime,
									trainsInTransit);
						}
					}
				}
				if (worldTime == 3)
					break;
				
			}
		}

	}
	public static void debarkStation(ArrayList<Station> stations, int station,
			int time, SimpleQueue<Train> travelingTrains) 
					throws EmptyPlatformException, FullQueueException
	{
		Train departingTrain = stations.get(station).getPlatform().check();
		int trainIDNumber = departingTrain.getId();
		departingTrain.getATD().add(time);
		travelingTrains.enqueue(departingTrain);
		System.out.println("Train " + trainIDNumber + " added to track");
		int stationIDNumber = stations.get(station).getId();
		stations.get(station).getPlatform().get();
		System.out.println(time + ":	Train " + trainIDNumber + 
				" has exited from station " + stationIDNumber + ".");
	}
	public static void arriveAtStation(ArrayList<Station> stations,int station, 
			int train, int time, SimpleQueue<Train> travelingTrains) 
					throws FullQueueException, EmptyQueueException, 
					FullPlatformException
	{
		Train arrivingTrain = (Train) travelingTrains.dequeue();
		System.out.println(arrivingTrain.getId());
		int trainIDNumber = arrivingTrain.getId();
		stations.get(station).getPlatform().put(arrivingTrain);
		arrivingTrain.getATA().add(time);
		int stationIDNumber = stations.get(station).getId();
		System.out.println(time + ":	Train " + trainIDNumber + 
				" has been parked at station " + stationIDNumber + ".");
	}
	public static boolean allTrainsAreDone(ArrayList<Station> stations)
	{
		if (stations.get(stations.size() - 1).getPlatform().isFull())
			return true;
		return false;
	}
	public static boolean allTrainsAreMoving(ArrayList<Station> stations)
	{
		for (int i = 0; i < stations.size(); i++)
		{
			if (!stations.get(i).getPlatform().isEmpty())
			{
				return false;
			}
		}
		return true;
	}

}
