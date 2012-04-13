package com.bostontechnologies.helloquickfix;

import quickfix.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws ConfigError, IOException {
        if (args.length != 5) {
            System.out.println("Please enter arguments: senderCompId targetCompId, host, port, fileStoreBaseDirectory");
            System.out.println("you entered: " + Arrays.toString(args));
            return;
        }
        final String senderCompId = args[0];
        final String targetCompId = args[1];
        final String host = args[2];
        final int port = Integer.parseInt(args[3]);
        final String fileStoreBaseDirectory = args[4];

        InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);

        System.out.println("Enter fix username");
        String fixUsername = in.readLine();

        System.out.println("Enter fix password");
        String fixPassword = in.readLine();

        // FooApplication is your class that implements the Application interface
        BTApplication application = new BTApplication(fixUsername, fixPassword);

        SessionSettings settings = SessionSettingsFactory.apply(senderCompId, targetCompId, host,
                port, fileStoreBaseDirectory);
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new FileLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory();
        Initiator acceptor = new SocketInitiator(application, storeFactory, settings, logFactory, messageFactory);
        acceptor.start();

        System.out.println("Enter a line of text (type 'q' to exit): ");
        String CurLine = ""; // Line read from standard in
        while (!(CurLine.equals("q"))) {
            CurLine = in.readLine();
            if (!(CurLine.equals("q"))) {
                System.out.println("You entered: " + CurLine + " which currently does nothing");
            }
        }

        acceptor.stop();
    }

}
