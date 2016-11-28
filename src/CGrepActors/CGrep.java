package CGrepActors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

class CGrep {


    public static void main(String args[]){


        int argLength = args.length;

        ActorSystem system = ActorSystem.create();

        if(argLength == 0){
            System.err.println("Missing Search Pattern"); // exit program if no search patter is given
            System.exit(1);
        }

        String pattern = args[0];
        FileCount fileCount;
        int count = countFiles(args, false);

        String[] filesToSearch;

        if (argLength > 1) { // case where all arguments are passed through program args
            fileCount = new FileCount(count, system);
            filesToSearch = Arrays.copyOfRange(args,1, args.length); // copies just the filenames

        } else{ // case where the user supplies their own filepath's via keyboard
            System.out.print("Please enter filepath(s) separated by spaces: ");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            String[] parsed = input.split(" "); // split on all spaces
            fileCount = new FileCount(countFiles(parsed,true), system);
            filesToSearch = parsed;
        }


        ActorRef collectionRef = system.actorOf(Props.create(CollectionActor.class));
        collectionRef.tell(fileCount,ActorRef.noSender()); // send filecount object to collectionRef


        for(String filename : filesToSearch){

            ActorRef scanRef = system.actorOf(Props.create(ScanActor.class)); // creates a new ScanActor
            scanRef.tell(new Configure(filename,pattern,collectionRef), ActorRef.noSender());

        }

    }

    /***
     * Takes an array of filepaths and the pattern to search for, opens the files
     * and performs the "Main" loop of execution for our program. if manualInput is
     * true, then this tells method that the array of filepath's are coming from the
     * keyboard vs from the program arguments when it's false.
     * @param input
     * @param manualInput tells method where the filepath's String away is coming from.
     */
    private static int countFiles(String[] input, boolean manualInput){
        int length = input.length;
        int count = 0;
        int i = manualInput ? 0 : 1 ; // skips the first element in the array if taking input from program arguments

        for(;i<length;i++){

            String filepath = input[i];
            File file = new File(filepath);

            if(file.isFile()){
                count++;
            }
            else{
                System.err.println("'" + filepath + "'" + " not found");
            }

        }

        System.out.println("filecount: " + count);

        return count;

    }
}
