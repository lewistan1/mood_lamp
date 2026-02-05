# 🌈 MoodLamp – Wi-Fi Controlled Interactive LED & Servo Lamp

MoodLamp is a Wi-Fi–enabled interactive lighting system built using an **ESP32**, **NeoPixel LED rings**, **servo motors**, and an **Android application**.  
It supports real-time colour control, brightness adjustment, rainbow animations, and physical touch interaction.

This project combines **embedded systems**, **mobile app development**, and **interactive hardware design**, making it suitable for educational demos, FabLab showcases, and creative installations.

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

## 🧩 System Diagram

### High-Level Architecture

┌──────────────────────────┐
│ Android App │
│ (Java / OkHttp Client) │
│ │
│ • ON / OFF / Rainbow │
│ • Brightness control │
│ • Master color picker │
│ • Per-light RGB control │
│ • Request / log viewer │
└─────────────┬────────────┘
│ HTTP GET
│ (Wi-Fi)
▼
┌────────────────────────────────────┐
│ ESP32 │
│ (MicroPython Firmware) │
│ │
│ • Wi-Fi HTTP Server (Port 80) │
│ • Mode Control: │
│ - STATIC │
│ - RAINBOW │
│ • Brightness Scaling │
│ • Touch Input Handling │
│ • Smooth Servo Target Tracking │
└───────┬──────────┬──────────┬─────┘
│ │ │
▼ ▼ ▼
┌────────────┐ ┌────────────┐ ┌────────────┐
│ NeoPixel │ │ NeoPixel │ │ NeoPixel │
│ Ring 1 │ │ Ring 2 │ │ Ring 3 │
│ (GPIO 5) │ │ (GPIO 6) │ │ (GPIO 7) │
│ 24 LEDs │ │ 24 LEDs │ │ 24 LEDs │
└─────┬──────┘ └─────┬──────┘ └────────────┘
│ │
▼ ▼
┌────────────┐ ┌────────────┐
│ Servo 1 │ │ Servo 2 │
│ (GPIO 1) │ │ (GPIO 2) │
│ Colour → │ │ Colour → │
│ Angle Map │ │ Angle Map │
└────────────┘ └────────────┘

    ▲
    │
┌──────────────────────────┐
│ Touch Input │
│ (GPIO 0) │
│ │
│ • Short press: cycle │
│ • Long press (2s): OFF │
└──────────────────────────┘


---

## 🔌 Hardware Components

| Component | Quantity |
|---------|---------|
| ESP32 | 1 |
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

## 📸 Demo & Media
*(Add photos or videos here)*

---

## 🧪 Known Limitations
- Lightweight HTTP parsing (no full header processing)
- Single client connection at a time
- No persistent state storage after reboot

---

## 🌱 Future Improvements
- JSON-based REST API
- Preset save/load
- Web UI fallback
- Captive portal setup
- LED gamma correction

---

## 👤 Author
**Lionel Rafy**  
Singapore Polytechnic – FabLab / School of EEE  
Interactive & Educational Prototyping Project
