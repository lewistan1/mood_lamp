# 🌈 The Tower – Wi-Fi Controlled Interactive LED & Servo Lamp

The tower is a Wi-Fi–enabled interactive lighting system built using an **ESP32 supermini**, **NeoPixel LED rings**, **servo motors**, and an **Android application**.  
It supports real-time colour control, brightness adjustment, rainbow animations, and physical touch interaction.

This project combines **embedded systems**, **mobile app development**, and **interactive hardware design**, making it suitable for FabLab showcases, and creative installations.

---

## ✨ Features

### 📱 Android App
- Manual **ESP32 IP address input**
- **ON / OFF / Rainbow** controls
- **Brightness slider** (0–255)
- **Master color picker** (controls all lights)
- **Individual color control** for each light
- Adaptive button text colour for readability
- Live **HTTP request & response logging**

### 🔌 ESP32 Firmware
- Wi-Fi–based HTTP server (port 80)
- Controls:
  - **3 NeoPixel LED rings** (24 LEDs each)
  - **2 servo motors**
  - **Touch input** for standalone control
- Operating modes:
  - **STATIC** – solid colour with servo angles mapped from colour brightness
  - **RAINBOW** – smooth colour fade with organic servo motion
- Touch behaviour:
  - Short press → cycle colours / modes
  - Long press (≥2s) → turn off LEDs and relax servos
- Smooth servo motion with easing and randomised movement


---

## 🔌 Hardware Components

| Component | Quantity |
|---------|---------|
| ESP32 supermini | 1 |
| NeoPixel LED Ring (24 LEDs) | 3 |
| Servo Motors | 2 |
| Touch Input | 1 |
| External Power Supply | 1 |

### Pin Configuration

| Function | ESP32 Pin |
|-------|---------|
| NeoPixel Ring 1 | GPIO 5 |
| NeoPixel Ring 2 | GPIO 6 |
| NeoPixel Ring 3 | GPIO 7 |
| Servo 1 | GPIO 1 |
| Servo 2 | GPIO 2 |
| Touch Input | GPIO 0 |

---

## 🌐 HTTP API (ESP32)

| Endpoint | Description |
|-------|------------|
| `/on` | Turn on static mode |
| `/off` | Turn off all lights |
| `/rainbow?state=on` | Enable rainbow mode |
| `/brightness?value=0–255` | Set brightness |
| `/color1?r=&g=&b=` | Set Light 1 colour |
| `/color2?r=&g=&b=` | Set Light 2 colour |
| `/color3?r=&g=&b=` | Set Light 3 colour |

---

## 🖐 Touch Controls

| Interaction | Action |
|-----------|-------|
| Short press | Cycle colour / mode |
| Long press (≥2s) | Turn off LEDs and stop servos |

---

## 🛠 Software Stack

- **ESP32 Firmware**: MicroPython
- **Android App**: Java (Android Studio)
- **Networking**: HTTP GET (OkHttp)
- **LED Control**: NeoPixel
- **Servo Control**: PWM (50 Hz)

---

## 🚀 Setup Instructions

### ESP32
1. Flash MicroPython onto ESP32
2. Upload the firmware script
3. Update Wi-Fi SSID and password if needed
4. Power on ESP32 and note its IP address

### Android App
1. Open project in Android Studio
2. Build and install the APK
3. Enter the ESP32 IP address
4. Start controlling the MoodLamp 🎨

---

## 📸 Pictures
<img width="287" height="495" alt="image" src="https://github.com/user-attachments/assets/f8b531a7-e2dd-4d0d-b2df-6b6aa5e46edf" />
<img width="460" height="487" alt="image" src="https://github.com/user-attachments/assets/e7abc099-241d-44cf-8a2f-1dfdfb4c9d6f" />
<img width="650" height="648" alt="Screenshot 2025-11-17 145646" src="https://github.com/user-attachments/assets/6eb6d9c4-041b-47aa-9cb0-29ae564d3dc0" />


---

## 🧪 Known Limitations
- Lightweight HTTP parsing (no full header processing)
- Single client connection at a time
- No persistent state storage after reboot

---




## 👤 Author
**Tan Kuan Seong Lewis**  
Singapore Polytechnic – FabLab 
