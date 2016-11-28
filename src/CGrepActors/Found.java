package CGrepActors;

import java.util.List;

public class Found {

    // Name of the file
    private final String filename;


    /**
    one entry in the list for each matching line. Each string in the list consists of the line number, a space,
    and the text of the line itself. The list must be ordered by location of the line in the file (i.e.,
    first matching line at position 0, second matching line at position 1, etc.)
    */
    private final List<String> matches;

    public Found(String filename, List<String> matches){
        this.filename= filename;
        this.matches = matches;
    }

    public String getFilename(){
        return filename;
    }

    public List<String> getMatches(){
        return matches;
    }
}
