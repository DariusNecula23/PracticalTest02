package practicaltest02.pdsd.systems.cs.pub.ro.practicaltest02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest02MainActivity extends AppCompatActivity {

    // Server widgets
    private EditText serverPortEditText = null;
    private Button startButton = null;

    // Client widgets
    private EditText clientUrlEditText = null;
    private Button goButton = null;
    private WebView webView = null;

    private ServerThread serverThread = null;
    private ClientThread clientThread = null;

    private StartButtonClickListener startButtonClickListener = new StartButtonClickListener();
    private class StartButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Server port should be filled!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() != null) {
                serverThread.start();
            } else {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
            }

        }
    }

    private GoButtonClickListener goButtonClickListener = new GoButtonClickListener();
    private class GoButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientUrl = clientUrlEditText.getText().toString();
            clientThread = new ClientThread(clientUrl);
            clientThread.start();
        }
    }

    private WebButtonClickListener webButtonClickListener = new WebButtonClickListener();
    private class WebButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientUrl = clientUrlEditText.getText().toString();
            clientThread = new ClientThread(clientUrl);
            clientThread.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
        startButton = (Button)findViewById(R.id.start_button);
        startButton.setOnClickListener(startButtonClickListener);

        clientUrlEditText = (EditText)findViewById(R.id.client_url_edit_text);

        goButton = (Button)findViewById(R.id.go_button);
        goButton.setOnClickListener(goButtonClickListener);

        webView = (WebView)findViewById(R.id.web_view);
        webView.setOnClickListener(webButtonClickListener);
    }
}
