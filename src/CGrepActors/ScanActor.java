package CGrepActors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Each file (or the standard input if no files are given) will be scanned by a ScanActor
 * for occurrences of the pattern, using one actor per file. A ScanActor expects to receive
 * exactly one immutable Configure message containing (a) a String with the name of the
 * file to scan (or null for the standard input), and (b) an ActorRef to a CollectionActor,
 * which collects and prints scan results
 *
 */
class ScanActor extends UntypedActor{
    private boolean hasConfigureMessage = false;



    @Override
    public void onReceive(Object o){
        if(o instanceof Configure && !hasConfigureMessage){
            // scan file
            String filePath = ((Configure) o).getMessage();
            String pattern = ((Configure) o).getPattern();
            ActorRef collRef = ((Configure) o).getActorRef();
            try {

                Found results = getMatches(filePath,pattern);  // search files
                collRef.tell(results,this.self());  // send found object to collection ref

            }
            catch (Exception e) { e.printStackTrace();    }
            hasConfigureMessage = true;
        }
        else{
            System.err.print("unexpected message type: " + o.getClass());
        }
    }

    private Found getMatches(String filepath, String target) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        Pattern pattern = Pattern.compile(target);

        // Matching lines
        List<String> matches = new ArrayList<String>();

        // The current line
        String currentLine;

        // The current line number
        long line = 0;

        while ((currentLine = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(currentLine);
            if (matcher.find()) {
                matches.add(line + " " + currentLine);
            }
            line++;
        }
        reader.close();
        return new Found(filepath,matches);
    }

}
