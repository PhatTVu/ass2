import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class test{
    public static void main(String[] arg) throws IOException, InterruptedException {

        /*Parse the output file*/
        File file = new File("output");

        /*ArrayList to store all PID*/
        ArrayList<Integer> list = new ArrayList<>();

        /*Hashmap to store level*/
        HashMap<Integer,Integer> map = new HashMap<>();

        /*Scan output file to get all PID and store them to an ArrayList*/
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Scanner sc = new Scanner(line);
                sc.skip("[^0-9]*");
                int result = sc.nextInt();
                System.out.println(result);
                list.add(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Output file
        String output = "input.dot";

        //Create the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));
        writer.write("digraph mygraph{" + System.lineSeparator());
        writer.write("  node [shape=oval]" + System.lineSeparator());

        /*Scan the ArrayList to get the smallest PID 
        which is the parent process of process level 0*/
        Integer smallest = test.min(list);

        /*Delete used PID*/
        for(int i = 1; i < list.size(); i = i + 2){
            if(list.get(i) == smallest){
                list.remove(i);
                list.remove(i-1);
            }
        }

        int level = 0;
        String str = "";
        String pre = "";
        String post = "";
        Integer start = 0;

        /*Loop*/
        while(true){
            /*If the List is empty, break*/
            if(list.isEmpty() == true){
                break;
            }

            start = test.min(list);
            // if(map.containsKey() != start)
            // map.put(start,)

            for(int i = 1; i < list.size(); i = i + 2){
                if(list.get(i).intValue() == start){
                    int child = list.get(i-1).intValue();
                    map.put(child,level+1);
                    pre = "PID: " + String.valueOf(start) + "\nLevel: " + String.valueOf(level);
                    post = "PID: " + String.valueOf(child) + "\nLevel: " + String.valueOf(level+1);
                    str = '"' + pre + "->" + post + '"';
                    writer.write(str + System.lineSeparator());
                }
            }

        }

        // write end of file
        // writer.write("a -> b;" + System.lineSeparator());
        writer.write("}" + System.lineSeparator());

        writer.close();
    }

    public static Integer min(ArrayList<Integer> list){
        Integer smallest = list.get(1);
        for(int i = 1; i < list.size(); i = i + 2){
            if(list.get(i) < smallest){
                smallest = list.get(i);
            }
        }
        return smallest;
    }
}