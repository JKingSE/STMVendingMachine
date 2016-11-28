package CGrepActors;

import akka.actor.ActorRef;

/***
 * created with a message containing (a) a String with the name of the file
 * to scan (or null for the standard input)
 */
class Configure {

    private String fileName = null;
    private String pattern = null;
    private ActorRef ref = null;
    // default constructor to simulate sending null
    public Configure(){}

    // normal constructor to used to assign the message to this object.
    public Configure(String input, String pattern, ActorRef ref){
        this.fileName = input;
        this.pattern = pattern;
        this.ref = ref;
    }
    public String getPattern(){
        return pattern;
    }
    public String getMessage() {
        return fileName;
    }
    public ActorRef getActorRef(){ return ref; }

}
