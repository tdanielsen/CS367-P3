import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class TrainSimulator 
{

	public static void main(String[] args) throws NumberFormatException, IOException, FullPlatformException, EmptyPlatformException, FullQueueException, EmptyQueueException, FullStackException 
	{ 
		
		if (args.length < 3 || args.length > 3)
			throw new IllegalArgumentException("3 arguments required");
		ArrayList<Station> allStations = new ArrayList<Station>();
		SimpleStack<Train> orderingStack = new SimpleStack<Train>();
		ArrayList<SimpleQueue<Train>> trainTracks = new ArrayList<SimpleQueue<Train>>();
		ArrayList<Train> trains = new ArrayList<Train>();
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
								int cutPoint = line.indexOf(",");
								int trainID = Integer.parseInt
										(line.substring(0, cutPoint));
								orderingStack.push(new Train(trainID));
								trains.add(new Train(trainID));
								String s = line.substring(cutPoint + 1);
								System.out.println("Train ETDs: " + s);
								System.out.println("Train ID: " + trainID);
								while (s.indexOf(",") != -1)
								{
									cutPoint = s.indexOf(",");
									int trainETD = Integer.parseInt(s.substring(0, cutPoint));
									orderingStack.peek().getETD().add(trainETD);
									s = s.substring(cutPoint + 1);
									System.out.println("Train ETD: " + trainETD);
								}
								int trainETD = Integer.parseInt(s);
								System.out.println("Train ETD: " + trainETD);
								orderingStack.peek().getETD().add(trainETD);
							}
							
							while (!orderingStack.isEmpty())
							{
//								System.out.println("Train ID: " + orderingStack.peek().getId());
//								for (int i1 = 0; i1 < orderingStack.peek().getETD().size(); i1++)
//								System.out.println(orderingStack.peek().getETD().get(i1));
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

		int n = Integer.parseInt(args[0]);
		while (!allTrainsAreDone(allStations))
		{
//			worldTime++;
			for (int i = 0; i < allStations.size(); i++)
			{
				if (!allStations.get(i).getPlatform().isEmpty())
				{
					boolean done = false;
					while (!done)
					{
						if (allStations.size() - 1 == i)
						{
							break;
						}
						if (allStations.get(i).getPlatform().isEmpty())
						{
							break;
						}
						if (getTrain(allStations, i).getETD().get(i) <= worldTime)
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
						else
						{
							done = true;
						}
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
	public static void debarkStation(ArrayList<Station> stations, int station,
			int time, ArrayList<SimpleQueue<Train>> trainTracks, int n, ArrayList<Train> trains) 
					throws EmptyPlatformException, FullQueueException, EmptyQueueException
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
		trainTracks.get(station).enqueue(departingTrain);
		int stationIDNumber = stations.get(station).getId();
		stations.get(station).getPlatform().get();
		if (n == 0)
		{
			System.out.println(time + ":	Train " + trainIDNumber + 
				" has exited from station " + stationIDNumber + ".");
		}
	}
	public static void arriveAtStation(ArrayList<Station> stations, int station,
			int train, int time, ArrayList<SimpleQueue<Train>> trainTracks,
			int n, ArrayList<Train> trains) 
					throws FullQueueException, EmptyQueueException, 
					FullPlatformException
	{
		Train arrivingTrain = trainTracks.get(station-1).dequeue();
			
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
	}
	public static boolean allTrainsAreDone(ArrayList<Station> stations)
	{
		if (stations.get(stations.size() - 1).getPlatform().isFull())
			return true;
		return false;
	}
	public static Train getTrain(ArrayList<Station> stations, int whichStation)
			throws EmptyPlatformException
	{
		return stations.get(whichStation).getPlatform().check();
	}

}
