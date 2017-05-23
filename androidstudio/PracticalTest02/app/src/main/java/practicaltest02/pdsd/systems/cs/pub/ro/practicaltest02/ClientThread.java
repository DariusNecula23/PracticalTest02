package practicaltest02.pdsd.systems.cs.pub.ro.practicaltest02;

import android.util.Log;
import android.webkit.WebView;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by student on 23.05.2017.
 */

public class ClientThread extends Thread {

    private WebView webViewTextView;
    String hostname = "localhost";
    int port = 2017;
    Socket socket;
    private String url;

    public ClientThread(String url) {
        this.url = url;
    }


    @Override
    public void run() {
        try {
            socket = new Socket(hostname, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
            }

            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader != null && printWriter != null) {
                printWriter.println(url);
                printWriter.flush();
                String webContent;
                while ((webContent = bufferedReader.readLine()) != null) {
                    final String finalizedWebInformation = webContent;
                    webViewTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            webViewTextView.loadDataWithBaseURL(finalizedWebInformation, hostname, "text/html", "utf-8", null);
                        }
                    });
                }
            } else {
                Log.e(Constants.TAG, "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
            }
            socket.close();
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }
}
