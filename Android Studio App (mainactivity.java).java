package com.sp.moodlamp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.res.ColorStateList;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.defaults.colorpicker.ColorObserver;
import top.defaults.colorpicker.ColorPickerView;
import androidx.appcompat.app.AlertDialog;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {

    private EditText ipInput;
    private Button btnOn, btnOff, btnNext, btnRainbow;
    private Button btnLight1, btnLight2, btnLight3;
    private SeekBar brightnessSeekBar;
    private TextView logText;
    private ColorPickerView masterColorPicker;

    private final OkHttpClient client = new OkHttpClient();

    // Store custom colors for each light; null means using master color
    private Integer colorLight1 = null;
    private Integer colorLight2 = null;
    private Integer colorLight3 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find Views
        ipInput = findViewById(R.id.ipInput);
        btnOn = findViewById(R.id.btnOn);
        btnOff = findViewById(R.id.btnOff);
        btnRainbow = findViewById(R.id.btnRainbow);
        btnLight1 = findViewById(R.id.btnLight1);
        btnLight2 = findViewById(R.id.btnLight2);
        btnLight3 = findViewById(R.id.btnLight3);
        brightnessSeekBar = findViewById(R.id.brightnessSeekBar);
        logText = findViewById(R.id.logText);
        masterColorPicker = findViewById(R.id.masterColorPicker);

        // Default brightness
        brightnessSeekBar.setProgress(128);
        ipInput.setText("172.23.12.139");

        // Basic Controls
        btnOn.setOnClickListener(v -> sendCommand("on"));
        btnOff.setOnClickListener(v -> sendCommand("off"));
        btnRainbow.setOnClickListener(v -> sendCommand("rainbow?state=on"));

        // Brightness control
        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {
                sendCommand("brightness?value=" + seekBar.getProgress());
            }
        });

        // Master color picker: overrides all lights when changed
        masterColorPicker.subscribe(new ColorObserver() {
            @Override
            public void onColor(int color, boolean fromUser, boolean shouldPropagate) {
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = color & 0xFF;

                // Send color to all 3 lights
                sendCommand("color1?r=" + r + "&g=" + g + "&b=" + b);
                sendCommand("color2?r=" + r + "&g=" + g + "&b=" + b);
                sendCommand("color3?r=" + r + "&g=" + g + "&b=" + b);

                // Override custom colors
                colorLight1 = color;
                colorLight2 = color;
                colorLight3 = color;

                // Update buttons to new color
                updateLightButtonColors();
            }
        });

        // Initial button update
        updateLightButtonColors();

        // Individual light buttons
        btnLight1.setOnClickListener(v -> openColorPickerDialog(1));
        btnLight2.setOnClickListener(v -> openColorPickerDialog(2));
        btnLight3.setOnClickListener(v -> openColorPickerDialog(3));
    }

    // Updates buttons to their custom color or master color
    private void updateLightButtonColors() {
        int masterColor = masterColorPicker.getColor();
        setButtonColor(btnLight1, colorLight1 != null ? colorLight1 : masterColor);
        setButtonColor(btnLight2, colorLight2 != null ? colorLight2 : masterColor);
        setButtonColor(btnLight3, colorLight3 != null ? colorLight3 : masterColor);
    }

    // Set button background and adaptive text color
    private void setButtonColor(Button button, int color) {
        button.setBackgroundTintList(ColorStateList.valueOf(color));

        // Calculate luminance (perceived brightness)
        double r = Color.red(color);
        double g = Color.green(color);
        double b = Color.blue(color);
        double luminance = (0.299 * r + 0.587 * g + 0.114 * b) / 255.0;

        // If it's bright, use black text; if dark, use white text
        if (luminance > 0.6) {
            button.setTextColor(Color.BLACK);
        } else {
            button.setTextColor(Color.WHITE);
        }
    }

    // Opens a temporary color picker for one light only (one-time command)
    private void openColorPickerDialog(int lightNumber) {
        ColorPickerView dialogPicker = new ColorPickerView(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Pick Color for Light " + lightNumber)
                .setView(dialogPicker)
                .setPositiveButton("Set", (d, which) -> {
                    int color = dialogPicker.getColor();
                    int r = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int b = color & 0xFF;

                    sendCommand("color" + lightNumber + "?r=" + r + "&g=" + g + "&b=" + b);

                    // Save custom color for this light only
                    if (lightNumber == 1) colorLight1 = color;
                    if (lightNumber == 2) colorLight2 = color;
                    if (lightNumber == 3) colorLight3 = color;

                    updateLightButtonColors();
                })
                .setNegativeButton("Cancel", null)
                .create();

        dialogPicker.subscribe((color, fromUser, shouldPropagate) -> {
            int r = (color >> 16) & 0xFF;
            int g = (color >> 8) & 0xFF;
            int b = color & 0xFF;
            appendLog("Preview Light " + lightNumber + ": " + r + "," + g + "," + b);
        });

        dialog.show();
    }

    // Helper: get base URL from IP input
    private String getBaseUrl() {
        String ip = ipInput.getText().toString().trim();
        if (ip.isEmpty()) {
            runOnUiThread(() -> Toast.makeText(this, "Enter ESP32 IP first", Toast.LENGTH_SHORT).show());
            return null;
        }
        return "http://" + ip;
    }

    // Send HTTP GET command
    private void sendCommand(String path) {
        String base = getBaseUrl();
        if (base == null) return;
        String url = base + "/" + path;
        appendLog("→ " + url);

        new Thread(() -> {
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                String body = response.body() != null ? response.body().string() : "";
                runOnUiThread(() -> appendLog("← " + response.code() + " " + body));
            } catch (IOException e) {
                runOnUiThread(() -> appendLog("✖ " + e.getMessage()));
            }
        }).start();
    }

    // Append logs
    private void appendLog(String msg) {
        logText.append("\n" + msg);
        final int scrollAmount = logText.getLayout() == null ? 0 :
                logText.getLayout().getLineTop(logText.getLineCount()) - logText.getHeight();
        if (scrollAmount > 0) logText.scrollTo(0, scrollAmount);
        else logText.scrollTo(0, 0);
    }
}
