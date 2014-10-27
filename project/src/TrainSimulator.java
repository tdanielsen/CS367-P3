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
									System.out.println(orderingStack.peek().getId());
									individualTrainETD.add(trainETD);
									orderingStack.peek().getETD().add(trainETD);
									s = s.substring(cutPoint + 1);
									//System.out.println(s);
								}
								int trainETD = Integer.parseInt(s);
								//System.out.println(trainETD);
								orderingStack.peek().getETD().add(trainETD);
								individualTrainETD.add(trainETD);
								allTrainsETD.add(individualTrainETD);
								
							}
							while (!orderingStack.isEmpty())
							{
								//System.out.println(orderingStack.pop().getId());
								allStations.get(0).getPlatform().put(orderingStack.pop());
								System.out.println(allStations.get(0).getPlatform().check().getId());
								System.out.println("ETD " + allStations.get(0).getPlatform().check().getETD().get(0));
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
			int whichStation = allStations.size();
			System.out.println(allStations.get(1).getId());
			while (!allTrainsAreDone(allStations))
			{
				System.out.println(worldTime);
				worldTime++;
				if (allTrainsAreMoving(allStations) == false)
				{

						System.out.println("j: " + whichStation + " Limit: " + allStations.size());
						System.out.println(worldTime);
						System.out.println("Red was here");
//						System.out.println(allStations.get(whichStation).getPlatform().isEmpty());
						//System.out.println(allStations.get(j).getPlatform().check().getId());
						if (!allStations.get(trainLocation(allStations, whichStation) - 1).getPlatform().isEmpty())
						{	
							System.out.println("Train location: " + trainLocation(allStations, whichStation));

							if (getTrain(allStations,whichStation).getATA().size() > 0)
							{
								System.out.println(getTrain(allStations, whichStation).getETD().size());
								if (getTrain(allStations, whichStation)
										.getETD().get(trainLocation
												(allStations, whichStation)-1) <= worldTime)
								{
									System.out.println(getTrain(allStations, whichStation).getATA().size());
									System.out.println(whichStation);
									System.out.println(trainsInTransit.peek().getId());
									debarkStation(allStations, trainLocation(allStations, whichStation) - 1, worldTime, trainsInTransit);
									System.out.println(trainsInTransit.peek().getId());
								}

							}
							else
							{
								debarkStation(allStations, trainLocation(allStations, whichStation) - 1, worldTime, trainsInTransit);
								System.out.println(trainsInTransit.peek().getId());
							}

						}
						
						System.out.println(whichStation);
						System.out.println(trainsInTransit.isEmpty());
						System.out.println(allStations.get(whichStation).getPlatform().isEmpty());
				}
				System.out.println(whichStation);
				System.out.println(trainLocation(allStations, whichStation));
//				System.out.println(trainsInTransit.peek().getATD().get(whichStation));
				if (trainLocation(allStations, whichStation) - 1 != 0)
				{
//					if (trainLocation(allStations, whichStation) < whichStation)
					{
						System.out.println("*" + trainsInTransit.peek().getATD().get(0));
						System.out.println(whichStation);
						System.out.println(trainsInTransit.peek().getATD().size());
						System.out.println(trainsInTransit.peek().getATD().get(0));
						if (trainsInTransit.peek().getATD().get(trainLocation(allStations, whichStation) -1) + 10 == worldTime)
						{
							arriveAtStation(allStations, trainLocation(allStations, whichStation),
									whichStation, worldTime, trainsInTransit);
							
						}
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
//				System.out.println("**" + whichStation);
//				if (allStations.get(trainLocation(allStations, whichStation) - 1).getPlatform().isEmpty())
//				{
//					System.out.println("Moving on");
//					whichStation++;
//				}
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

			}
		}

	}
	public static void debarkStation(ArrayList<Station> stations, int station,
			int time, SimpleQueue<Train> travelingTrains) 
					throws EmptyPlatformException, FullQueueException, EmptyQueueException
	{
		Train departingTrain = stations.get(station).getPlatform().check();
		int trainIDNumber = departingTrain.getId();
		departingTrain.getATD().add(time);
		departingTrain.getETD().remove(station);
		travelingTrains.enqueue(departingTrain);
		System.out.println("Train " + trainIDNumber + " added to track");
		int stationIDNumber = stations.get(station).getId();
		System.out.println(stationIDNumber);
		stations.get(station).getPlatform().get();
		System.out.println(time + ":	Train " + trainIDNumber + 
				" has exited from station " + stationIDNumber + ".");
	}
	public static void arriveAtStation(ArrayList<Station> stations, int station,
			int train, int time, SimpleQueue<Train> travelingTrains) 
					throws FullQueueException, EmptyQueueException, 
					FullPlatformException
	{
		System.out.println("Station " + station);
		Train arrivingTrain = (Train) travelingTrains.dequeue();
		System.out.println("Off the rails: " + arrivingTrain.getId());
		System.out.println(travelingTrains.peek().getId());
			
		int trainIDNumber = arrivingTrain.getId();
		arrivingTrain.getATA().add(time);
		stations.get(station).getPlatform().put(arrivingTrain);
		arrivingTrain.getATA().add(time);
		int stationIDNumber = stations.get(station - 1).getId();
		System.out.println("Station Number " + stationIDNumber);
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
	public static Train getTrain(ArrayList<Station> stations, int whichStation)
			throws EmptyPlatformException
	{
		return stations.get(whichStation).getPlatform().check();
	}
	public static int trainLocation(ArrayList<Station> stations, int whichStation) throws EmptyPlatformException
	{
		return Math.abs(whichStation - getTrain(stations, whichStation).getETD().size());
	}

}
