package a;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PrisonersProblem {

	private static Set<Integer> remaining = new HashSet<>();
	
	public static void main(String[] args) {
		
		int numberOfPrisoners = 100;
		Random random = new Random();
		int numberOfPrisons = 1_000_000;
		int survivedPrisons = 0;

		for (int prisonNumber = 0; prisonNumber < numberOfPrisons; prisonNumber++) {

			HashMap<Integer, Integer> boxes = randomHashMapGenerator(numberOfPrisoners);
			loopFinder(random.nextInt(numberOfPrisoners) + 1, boxes);
			
			Collection<Integer> values = boxes.values();
			remaining = new HashSet<>(values);

			boolean survived = true;
			while (!remaining.isEmpty()) {

				// The first element from the remaining numbers
				int startNumber = remaining.iterator().next();

				boolean stillAlive = loopFinder(startNumber, boxes);
				if (!stillAlive) {
					survived = false;
				}
			}

			if (survived) {
				survivedPrisons++;
			}

//			System.out.println("Prison - "+prisonNumber+" survived: "+survived);

		}

//		System.out.println(survivedPrisons);
		System.out.println(((double) survivedPrisons / numberOfPrisons)*100+ " %");

	}

	private static HashMap<Integer, Integer> randomHashMapGenerator(int peopleInPrison) {
		
		// creates a hashmap with random numbers
		// key - value = box number - number inside of the box
		
		List<Integer> numbers = new ArrayList<>();
		for (int i = 1; i <= peopleInPrison; i++) {
		    numbers.add(i);
		}

		// Shuffle the list to get random order
		Collections.shuffle(numbers);

		// Create a HashMap to store the numbers
		HashMap<Integer, Integer> randomMap = new HashMap<>();

		// Populate the HashMap with keys 1 to 100 and shuffled values
		for (int i = 0; i < peopleInPrison; i++) {
		    randomMap.put(i + 1, numbers.get(i));
		}
		
		return randomMap;
	}

	public static boolean loopFinder(int startNumber, HashMap<Integer, Integer> boxes) {
		Set<Integer> visited = new HashSet<>();
		int counter = 0;
		int currentNumber = startNumber;

		while (remaining.contains(currentNumber)) {
			if (visited.contains(currentNumber)) {
				break;
			}
			visited.add(currentNumber);
			currentNumber = boxes.get(currentNumber);
			counter++;
		}

		remaining.removeAll(visited);
		if (counter <= 50) {
//			System.out.println("SURVIVED, loop length: " + counter);
			return true;
		} else {
//			System.out.println("DEATH, loop length: " + counter);
			return false;
		}
	}
}
