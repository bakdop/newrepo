import java.io.*;
import java.util.*;

public class QB {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
            int n = in.nextInt();
            fruit[] fruits = new fruit[n];
            for (int i = 0; i < n; i++) {
                fruits[i] = new fruit();
                fruits[i].left = in.nextLong();
                fruits[i].right = in.nextLong();
                fruits[i].value = in.nextInt();
            }
            HashMap<Long,timeslots > hashmap=new HashMap<>();
            timeslots[] activeslots = new timeslots[n];
            for (int i=0;i<n;i++){
                activeslots[i]=new timeslots();
                activeslots[i].posi=i;
            }
            long result=0;
            int cnt = 0;
            Arrays.sort(fruits, Comparator.comparingLong(u -> u.left));
            long x=0;
            for (int i=0;i<n;i++){
                activeslots[cnt].time=Math.max(x+1,fruits[i].left);
                hashmap.put(activeslots[cnt].time,activeslots[cnt]);
                x=activeslots[cnt].time;
                cnt++;
            }
            Arrays.sort(fruits,new Comparator<fruit>(){
                @Override
                public int compare(fruit f1, fruit f2){
                    return  f2.value- f1.value;
                }
            });
            for (int i=0;i<n;i++){
                timeslots time=hashmap.get(fruits[i].left);
                if(linearmatch(fruits[i],time,activeslots)){
                    result+=fruits[i].value;
                }
            }
            out.println(result);

        out.close();

    }
    private static class fruit{
        long left;
        long right;
        int value;
        timeslots toL;
    }
    private static class timeslots{
        long time;
        boolean ismatched=false;
        int posi;
        fruit matchedfruit;
    }

    private static boolean linearmatch(fruit f,timeslots x,timeslots[] ts){
        if(x.time> f.right){
            return false;
        }
        if(!x.ismatched){
            x.matchedfruit=f;
            x.ismatched=true;
            return true;
        }
        fruit aj= x.matchedfruit;
        if(f.right>aj.right){
            return linearmatch(f,ts[x.posi+1],ts);
        }else if(linearmatch(aj,ts[x.posi+1],ts)){
            x.matchedfruit=f;
            return true;
        }
        return false;
    }

}

class QReader {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private StringTokenizer tokenizer = new StringTokenizer("");
    private String innerNextLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean hasNext() {
        while (!tokenizer.hasMoreTokens()) {
            String nextLine = innerNextLine();
            if (nextLine == null) {
                return false;
            }
            tokenizer = new StringTokenizer(nextLine);
        }
        return true;
    }

    public String nextLine() {
        tokenizer = new StringTokenizer("");
        return innerNextLine();
    }

    public String next() {
        hasNext();
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }
}
class QWriter implements Closeable {
    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public void print(Object object) {
        try {
            writer.write(object.toString());
        } catch (IOException e) {
            return;
        }
    }

    public void println(Object object) {
        try {
            writer.write(object.toString());
            writer.write("\n");
        } catch (IOException e) {
            return;
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            return;
        }
    }
}