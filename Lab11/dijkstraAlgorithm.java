import java.util.*;

//You are given a map with one way roads between N cities in Macedonia.
// For each road you are given the length of the road, the starting city and the destination city.
// Find the minimal distance from one city to another and back, and print the roads you are going to take.
//The map with roads is given as a directed weighted graph.

//Input: In the first line you are given the number of cities N.
// In the second line you are given the number of roads M.
// In the following M lines you are given the roads as following: 
// the number of the starting city, the name of the starting city,
// the number of the destination city, the name of the destination city,
// length of the road.
// In the last 2 lines you are given the names of the starting and the destination city.

//Output: In the first line of the output you are supposed to print the route which you are supposed to take starting from the starting city and ending at the destination city.
// In the second line you are supposed to print the route which you are supposed to take starting from the destination city and ending at the starting city.
// In the last line of the output you are supposed to print a single integer representing the total length of the routes.

public class dijkstraAlgorithm {
    static Map<Integer, Double> dijkstra(Map<Integer, Map<Integer, Double>> graph, int start) {
        Map<Integer, Double> distances = new HashMap<>();
        PriorityQueue<double[]> pq = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        pq.offer(new double[]{(double)start, 0});
        while (!pq.isEmpty()) {
            double[] current = pq.poll();
            double currentV = current[0];
            double currentD = current[1];
            if (distances.containsKey((int)currentV) && currentD > distances.get((int)currentV)) continue;
            for (Map.Entry<Integer, Double> neighborEntry : graph.get((int)currentV).entrySet()) {
                int neighbor = neighborEntry.getKey();
                double weight = neighborEntry.getValue();
                double distance = currentD + weight;
                if (!distances.containsKey(neighbor) || distance < distances.get(neighbor)) {
                    distances.put(neighbor, distance);
                    pq.offer(new double[]{neighbor, distance});
                }
            }
        }
        return distances;
    }
    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n=Integer.parseInt(sc.nextLine());
        int m=Integer.parseInt(sc.nextLine());

        Map<String,Integer> cities = new HashMap<>();
        Map<Integer,Map<Integer,Double>> roads = new HashMap<>();
        for (int i = 0; i < m; i++) {
            String []parts = sc.nextLine().split(" ");
            cities.put(parts[1],Integer.parseInt(parts[0]));
            cities.put(parts[3],Integer.parseInt(parts[2]));
            roads.put(Integer.parseInt(parts[0]),new HashMap<>());
            roads.get(Integer.parseInt(parts[0])).put(Integer.parseInt(parts[2]),Double.parseDouble(parts[4]));
        }

        String start = sc.nextLine().trim();
        String finish = sc.nextLine().trim();
        int s = cities.get(start);
        int f = cities.get(finish);
        List<Integer> forwardPath = findShortestPath(roads,s,f,m);
        List<Integer> backwardPath = findShortestPath(roads,f,s,m);

        for (int a : forwardPath) {
            System.out.print(getKeyByValue(cities,a)+ " ");
        }
        System.out.print("\n");

        for (int a : backwardPath) {
            System.out.print(getKeyByValue(cities,a)+ " ");
        }
        System.out.print("\n");

        double i = dijkstra(roads, s).get(f);
        double j = dijkstra(roads, f).get(s);
        System.out.printf("%.1f",i+j);
    }

    private static List<Integer> findShortestPath(Map<Integer, Map<Integer, Double>> roads, int s, int f, int numRoads) {
        Map<Integer, Double> distances = dijkstra(roads, s);
        System.out.println(s+"->"+f+":"+distances.toString());
        List<Integer> path = new ArrayList<>();
        int currentV = f;
        int counter = 0;
        while (currentV != s) {
            if (numRoads == counter) {
                path.add(f);
                break;
            }
            counter++;
            if (roads.containsKey(currentV)) {
                for (var entry : roads.get(currentV).entrySet()){
                    if(distances.get(currentV)== null) break;
                    double a=distances.get(currentV);
                    double b=distances.get(entry.getKey());
                    if (a == b) {
                        path.add(entry.getKey());
                        currentV = entry.getKey();
                    }
                }
            } else {
                break;
            }
        }
        path.add(s);
        Collections.reverse(path);
        return path;
    }
}

