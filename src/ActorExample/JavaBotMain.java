package ActorExample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by nate on 11/14/16.
 */
class JavaBotMain {

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create();
        final ActorRef akkaBot = system.actorOf(Props.create(JavaAkkaBot.class));


        // The ActorRef.noSender() method just tells the actorref that the message is being sent from non-actor code
        akkaBot.tell(new JavaAkkaBot.Move(JavaAkkaBot.Direction.FORWARD), ActorRef.noSender());
        akkaBot.tell(new JavaAkkaBot.Move(JavaAkkaBot.Direction.BACKWARDS), ActorRef.noSender());
        akkaBot.tell(new JavaAkkaBot.Move(JavaAkkaBot.Direction.RIGHT), ActorRef.noSender());

        akkaBot.tell(new JavaAkkaBot.Stop(), ActorRef.noSender());


        system.shutdown();
    }

}
