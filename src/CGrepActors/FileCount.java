package CGrepActors;

import akka.actor.ActorSystem;

/**
 * Contains a count of the number of files being scanned. This class is meant to passed to the
 * CollectionActor for processing.
 */
class FileCount {
    private final int count;
    public FileCount(int count, ActorSystem system){
        this.count = count;
        this.system = system;
    }
    public int getFileCount(){
        return count;
    }
    public ActorSystem getSystem(){return system;}
    private ActorSystem system;
}
