package ActorExample;

import akka.actor.UntypedActor;

/**
 * Created by nate on 11/14/16.
 */
public class JavaAkkaBot extends UntypedActor {


    // Change onReceive method to be
    public void onReceive(Object message) {

        if (message instanceof Move) {
            Direction direction = ((Move) message).direction;
            System.out.println("Moving --> " + direction);
        }
        else if (message instanceof Stop) {
            System.out.println("Stopping");
        }
        else {
            unhandled(message);
        }
    }


    // Inside the AkkaBot.java code add the following inner classes:
    public enum Direction { FORWARD, BACKWARDS, RIGHT, LEFT }

    public static class Move {
        public final Direction direction;
        public Move(Direction direction) { this.direction = direction; }
    }
    public static class Stop {}
}
