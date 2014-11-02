///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            CS367-P3
// Files:            Platform.java, PlatformADT.java, QueueADT.java,
//                   SimpleQueue.java, SimpleStack.java, StackADT.java,
//                   Station.java, Train.java, TrainSimulator.java
// Semester:         CS367 Fall 2014
//
// Author:           Tim Danielsen
// Email:            tdanielsen@wisc.edu
// CS Login:         danielsen
// Lecturer's Name:  J. Skrentny
// Lab Section:      n/a
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TrainSimulator
{

	/**
	 * Initializes stations and trains from a file that the user specifies, and
	 * displays information based on what the user specified in their first
	 * argument
	 * 
	 * @param args
	 *            Command line arguments.
	 */

	public static void main(String[] args) throws NumberFormatException,
			IOException, FullPlatformException, EmptyPlatformException
	{
		// Checks for valid input
		if (args.length < 3 || args.length > 3)
		{
			throw new IllegalArgumentException("3 arguments required");
		}
		// A list for all the stations in the simulation
		List<Station> allStations = new ArrayList<Station>();
		// A stack that enters trains into the first station in the proper order
		SimpleStack<Train> orderingStack = new SimpleStack<Train>();
		// A list of queues that simulate a track to keep track of the trains
		// in between stations
		List<SimpleQueue<Train>> trainTracks = new ArrayList<SimpleQueue<Train>>();
		// A list of all the trains in the simulation
		List<Train> trains = new ArrayList<Train>();
		int worldTime = 0; // Keeps track of the time in the simulation

		for (int i = 1; i < args.length; i++)
		{
			String fileName = args[i];
			if (i == 1)
			{
				// This reads in the stations text file and makes it into
				// stations
				try
				{
					BufferedReader in = new BufferedReader(new FileReader(
							fileName));
					String line = in.readLine();
					// Goes through each line
					while ((line = in.readLine()) != null)
					{
						// Breaks up each line to get the pertinent information
						StringTokenizer token = new StringTokenizer(line, ",");
						if (token.countTokens() == 1)
						{
							// Ignores the first line of the file
						}
						else
						{
							int stationID = Integer.parseInt(token.nextToken());
							int stationCap = Integer
									.parseInt(token.nextToken());
							Station newStation = new Station(stationID,
									stationCap);
							allStations.add(newStation);
						}
					}
					// Closes the BufferedReader
					in.close();
				}
				catch (FileNotFoundException e)
				{
					System.out.println("File: " + fileName + " Not Found.");
				}
			}
			// Reads in the train text file and makes trains from it
			else
			{
				try
				{
					BufferedReader in = new BufferedReader(new FileReader(
							fileName));
					String line = in.readLine();
					// Makes the appropriate amount of train tracks with enough
					// space on each of them to hold all the trains
					for (int z = 0; z < allStations.size() - 1; z++)
					{
						trainTracks.add(new SimpleQueue<Train>(Integer
								.parseInt(line)));
					}
					orderingStack = new SimpleStack<Train>(
							Integer.parseInt(line));
					// Reads each line to make trains off of them
					while ((line = in.readLine()) != null)
					{
						StringTokenizer token = new StringTokenizer(line, ",");
						if (token.countTokens() == 1)
						{
							// Ignores the first line of the file
						}
						int trainID = Integer.parseInt(token.nextToken());
						orderingStack.push(new Train(trainID));
						trains.add(new Train(trainID));
						// puts in the ETD of each train into the train
						while (token.hasMoreTokens())
						{
							int trainETD = Integer.parseInt(token.nextToken());
							orderingStack.peek().getETD().add(trainETD);
						}
					}
					// Puts the trains into the first station in the correct
					// order
					while (!orderingStack.isEmpty())
					{
						allStations.get(0).getPlatform()
								.put(orderingStack.pop());
					}
					// Closes the BufferedReader
					in.close();
				}
				catch (FileNotFoundException | FullStackException e)
				{
					System.out.println("File: " + fileName + " Not Found.");
				}
			}
		}
		// Looks at what operation the user wants executed
		int n = Integer.parseInt(args[0]);

		while (!allTrainsAreDone(allStations))
		{
			for (int i = 0; i < allStations.size() - 1; i++)
			{
				// Checks each station, except the last, for trains that are
				// ready to leave the station
				while (!allStations.get(i).getPlatform().isEmpty()
						&& getTrain(allStations, i).getETD().get(i) <= worldTime)
				{
					// Looks if the train is at the first station
					if (getTrain(allStations, i).getATA().size() > 0)
					{
						// Looks if the train has waited 1 time unit
						if (getTrain(allStations, i).getATA().get(i) + 1 <=
								worldTime)
						{
							debarkStation(allStations, i, worldTime,
									trainTracks, n, trains);
						}
					}
					else
					{
						debarkStation(allStations, i, worldTime, trainTracks,
								n, trains);
					}
				}
			}
			// Checks each train track to see if the first train is ready to
			// arrive at the next station
			for (int j = 0; j < trainTracks.size(); j++)
			{
				if (!trainTracks.get(j).isEmpty())
				{
					// Looks at each train on a track as long as the next
					// station is not full and the train track is not empty
					while (!allStations.get(j + 1).getPlatform().isFull()
							&& !trainTracks.get(j).isEmpty())
					{
						try
						{
							if (trainTracks.get(j).peek().getATD().size() > j)
							{
								if (trainTracks.get(j).peek().getATD().get(j)
										+ 10 <= worldTime)
								{
									arriveAtStation(allStations, j + 1, j,
											worldTime, trainTracks, n, trains);
								}
								else
								{
									break;
								}
							}
						}
						catch (EmptyQueueException e)
						{
							System.out.println("Tracks are empty.");
						}
					}
				}
			}
			worldTime++;
		}
		// If the users wants the ATDs
		if (n == 1)
		{
			String result = "";
			// Goes through the list of all the trains in the simulation and
			// retrieves their ATD
			for (int i = 0; i < trains.size(); i++)
			{
				result = result + "[";
				for (int j = 0; j < trains.get(i).getATD().size(); j++)
				{
					result = result + trains.get(i).getATD().get(j) + ", ";
				}
				result = result.substring(0, result.length() - 2);
				result = result + "]\n";
			}
			System.out.println(result);
		}
		// If the users wants the ATAs
		if (n == 2)
		{
			String result = "";
			// Goes through the list of all the trains in the simulation and
			// retrieves their ATA
			for (int i = 0; i < trains.size(); i++)
			{
				result = result + "[";
				for (int j = 0; j < trains.get(i).getATA().size(); j++)
				{
					result = result + trains.get(i).getATA().get(j) + ", ";
				}
				result = result.substring(0, result.length() - 2);
				result = result + "]\n";
			}
			System.out.println(result);
		}

	}

	/**
	 * Removes the first train in the station and moves it to the track in
	 * between that station it was at and the next on, as well as updates the
	 * trains ATD and ATA
	 *
	 * @param stations
	 *            a list of all the stations in the simulation
	 * @param station
	 *            the station that the train is currently at
	 * @param time
	 *            the time in the simulation
	 * @param trainTracks
	 *            a list of all the train tracks in the simulation
	 * @param n
	 *            which output the user wants
	 * @param trains
	 *            a list of all the trains in the simulation
	 */

	public static void debarkStation(List<Station> stations, int station,
			int time, List<SimpleQueue<Train>> trainTracks, int n,
			List<Train> trains) throws EmptyPlatformException
	{
		Train departingTrain = stations.get(station).getPlatform().check();
		int trainIDNumber = departingTrain.getId();
		departingTrain.getATD().add(time);
		// Goes through the list of all the trains and looks for the same train
		// as the departing one and updates the ATD of the train on the list of
		// all trains
		for (int i = 0; i < trains.size(); i++)
		{
			if (trains.get(i).getId() == departingTrain.getId())
			{
				trains.get(i).getATD().add(time);
			}
		}
		// Puts the train on the right tracks
		try
		{
			trainTracks.get(station).enqueue(departingTrain);
		}
		catch (FullQueueException e)
		{
			System.out.println("Tracks are full!");
		}
		int stationIDNumber = stations.get(station).getId();
		stations.get(station).getPlatform().get();
		// Looks to see if the users wants this outputted
		if (n == 0)
		{
			System.out.println(time + ":	Train " + trainIDNumber
					+ " has exited from station " + stationIDNumber + ".");
		}
	}

	/**
	 * Removes the first train in the station and moves it to the track in
	 * between that station it was at and the next on, as well as updates the
	 * trains ATD and ATA
	 *
	 * @param stations
	 *            a list of all the stations in the simulation
	 * @param station
	 *            the station that the train is currently at
	 * @param train
	 *            the train arriving at the station
	 * @param time
	 *            the time in the simulation
	 * @param trainTracks
	 *            a list of all the train tracks in the simulation
	 * @param n
	 *            which output the user wants
	 * @param trains
	 *            a list of all the trains in the simulation
	 */
	public static void arriveAtStation(List<Station> stations, int station,
			int train, int time, List<SimpleQueue<Train>> trainTracks, int n,
			List<Train> trains) throws FullPlatformException
	{
		try
		{
			// Removes the arriving train from the tracks
			Train arrivingTrain = trainTracks.get(station - 1).dequeue();
			int trainIDNumber = arrivingTrain.getId();
			arrivingTrain.getATA().add(time);
			// Goes through the list of all the trains and looks for the same
			// train as the departing one and updates the ATA of the train on
			// the list of all trains
			for (int i = 0; i < trains.size(); i++)
			{
				if (trains.get(i).getId() == arrivingTrain.getId())
				{
					trains.get(i).getATA().add(time);
				}
			}
			// Puts the arriving train in the station
			stations.get(station).getPlatform().put(arrivingTrain);
			arrivingTrain.getATA().add(time);
			int stationIDNumber = stations.get(station).getId();
			// Looks to see if the users wants this outputted
			if (n == 0)
			{
				System.out.println(time + ":	Train " + trainIDNumber
						+ " has been parked at station " + stationIDNumber
						+ ".");
			}
		}
		catch (EmptyQueueException e)
		{
			System.out.println("Tracks are empty");
		}

	}

	/**
	 * Checks to see if all the trains are in the final station
	 *
	 * @param stations
	 *            a list of all the stations in the simulation
	 * @return true if all the trains are in the final station, false otherwise
	 */

	public static boolean allTrainsAreDone(List<Station> stations)
	{
		if (stations.get(stations.size() - 1).getPlatform().isFull())
		{
			return true;
		}
		return false;
	}

	/**
	 * Gets the top train from a specific station
	 *
	 * @param stations
	 *            a list of all the stations in the simulation
	 * @param whichStation
	 *            an integer corresponding to the desired station
	 * @return the top train from the desired station
	 */

	public static Train getTrain(List<Station> stations, int whichStation)
			throws EmptyPlatformException
	{
		return stations.get(whichStation).getPlatform().check();
	}

}
