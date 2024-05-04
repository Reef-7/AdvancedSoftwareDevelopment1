package test;

import java.io.*;

public class BookScrabbleHandler implements ClientHandler {

    @Override
    public void handleClient(InputStream inputFromClient, OutputStream outputToClient) {
        String line;
        try {
            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputFromClient));
            PrintWriter sendToClient = new PrintWriter(outputToClient);
            line = readFromClient.readLine();
            String[] splits = line.split(",");
            String type = splits[0];
            String[] arguments = new String[splits.length - 1];
            System.arraycopy(splits, 1, arguments, 0, splits.length - 1);
            if (type.equals("Q")) {
                sendToClient.println(DictionaryManager.get().query(arguments));
                sendToClient.flush();
            } else if (type.equals("C")) {
                sendToClient.println(DictionaryManager.get().challenge(arguments));
                sendToClient.flush();
            }
            readFromClient.close();
            sendToClient.close();
        } catch (IOException ioex) {

        }

    }

    @Override
    public void close() {

    }

}
