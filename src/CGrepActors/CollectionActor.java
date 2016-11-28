package CGrepActors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;

class CollectionActor extends UntypedActor{
    private int fileCount;
    private boolean hasFileCount = false;
    private ActorRef ref;
    private int count = 0;
    private ActorSystem system;

    public CollectionActor(){
    }


    private void accept(FileCount fileCount){
        if(!hasFileCount){ // this way we can only set the file count once
            this.fileCount = (fileCount).getFileCount();
            this.system = fileCount.getSystem();
            hasFileCount = true;
        }
    }

    /**
     * upon receipt, are printed by the CollectionActor. Printout consists of
     * the file name (or "-" for standard input) and the list of matching lines
     * @param foundObject
     */
    private void accept(Found foundObject){
        /**
         * When all the Found messages have been processed, the CollectionActor
         * shuts down all actors using Actors.registry().shutdown()
         */
        String ret = foundObject.getFilename() + "\n\t";
        for (String match : foundObject.getMatches()) {
            ret = ret + " " + match + "\n\t";
        }
        System.out.println(ret);
        count ++;
        if(count == fileCount){
            system.shutdown();
        }
    }



    @Override
    public void onReceive(Object message){
        if(message instanceof FileCount){
            try {
                accept((FileCount) message );       // accepts a filecount object
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        else{
            try {
                accept((Found) message);    // accepts a found objec
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
