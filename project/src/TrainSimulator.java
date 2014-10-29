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
	//	SimpleQueue<Train> trainsInTransit = new SimpleQueue<Train>();
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
				//	trainsInTransit = new SimpleQueue<Train>(Integer.parseInt(line));
					for (int z = 0; z < allStations.size() - 1; z++)
					{
						trainTracks.add(new SimpleQueue<Train>(Integer.parseInt(line)));
					}
					trains.add(new Train(Integer.parseInt(line)));
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
//									System.out.println(orderingStack.peek().getId());
									orderingStack.peek().getETD().add(trainETD);
									s = s.substring(cutPoint + 1);
									//System.out.println(s);
								}
								int trainETD = Integer.parseInt(s);
								//System.out.println(trainETD);
								orderingStack.peek().getETD().add(trainETD);
								
							}
							while (!orderingStack.isEmpty())
							{
								//System.out.println(orderingStack.pop().getId());
								allStations.get(0).getPlatform().put(orderingStack.pop());
//								System.out.println(allStations.get(0).getPlatform().check().getId());
//								System.out.println("ETD " + allStations.get(0).getPlatform().check().getETD().get(0));
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
//		System.out.println("Tracks: " + trainTracks.size());
	
//			int whichStation = 0;
//			System.out.println(allStations.get(1).getId());
//			System.out.println(trainTracks.get(0).isEmpty());
//			System.out.println(trainTracks.get(1).isEmpty());
		int n = Integer.parseInt(args[0]);
		
		while (!allTrainsAreDone(allStations))
		{
//				System.out.println(worldTime);
//				System.out.println("Track 1 is empty: " + trainTracks.get(0).isEmpty());
//				System.out.println("Track 2 is empty: " + trainTracks.get(1).isEmpty());
			worldTime++;
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
									debarkStation(allStations, i, worldTime, trainTracks, n);
								}
							}
							else
							{
								debarkStation(allStations, i, worldTime, trainTracks, n);
							}
						}
						else
						{
							done = true;
						}
					}
				}
				//System.out.println(allStations.get(i).getPlatform().isEmpty());
			}

			//looks at the "train tracks"
			for (int j = 0; j < trainTracks.size(); j++)
			{
				if (!trainTracks.get(j).isEmpty())
				{
					boolean done = false;
					while (!done)
					{
						//System.out.println("J: " + j);	
						if (allStations.get(j + 1).getPlatform().isFull()
								|| trainTracks.get(j).isEmpty())
						{
						//	System.out.println("Done and done.");
							break;
						}
//							System.out.println("J: " + j);	
//							System.out.println("Front: " + trainTracks.get(j).getFront());
//							System.out.println("Rear: " + trainTracks.get(j).getRear());
						//System.out.println(trainTracks.get(1).peek().getId());
						if (trainTracks.get(j).peek().getATD().size() > j)
						{
							//System.out.println(trainTracks.get(j).peek().getATD().get(j));
							if (trainTracks.get(j).peek().getATD().get(j) + 10 <= worldTime)
							{
								arriveAtStation(allStations, j + 1,
										j, worldTime, trainTracks, n);
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
//							done = true;
					}
				}
			}

		}
		if (n == 1)
		{
			String result = "";
			for(int i = 0; i < trains.size(); i++)
			{
				result = result + "[";
				for(int j = 0; j < trains.get(i).getATD().size(); j++)
				{
					result = result + " " + trains.get(trains.size() - 1).getATD().get(j);
				}
				result = result + "]";
			}
			System.out.println(result);	
		}
		
	}
	public static void debarkStation(ArrayList<Station> stations, int station,
			int time, ArrayList<SimpleQueue<Train>> trainTracks, int n) 
					throws EmptyPlatformException, FullQueueException, EmptyQueueException
	{
		Train departingTrain = stations.get(station).getPlatform().check();
		int trainIDNumber = departingTrain.getId();
		departingTrain.getATD().add(time);
//		System.out.println(departingTrain.getATD().get(station));
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
			int train, int time, ArrayList<SimpleQueue<Train>> trainTracks, int n) 
					throws FullQueueException, EmptyQueueException, 
					FullPlatformException
	{
		Train arrivingTrain = trainTracks.get(station-1).dequeue();
			
		int trainIDNumber = arrivingTrain.getId();
		arrivingTrain.getATA().add(time);
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
