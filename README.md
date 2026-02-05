# ⚖️ PivotLab – Interactive Balance & OLED Animation System

PivotLab is an **ESP32-based educational mechatronics system** designed for hands-on learning in **balance, force, and control concepts**.  
It combines a **servo motor**, **AS5600 magnetic angle sensor**, **rotary encoder**, and **OLED display**, along with a **Python-based bitmap generator** for OLED startup animations.

This project is suitable for **FabLab kits**, **engineering education**, and **interactive demonstrations**.

---

## ✨ Key Features

### 🎮 ESP32 Interactive System
- Multiple operating modes:
  - **Normal Mode** – balance challenges with a fixed number of moves
  - **Infinite Mode** – endless gameplay with adjustable difficulty
  - **Education Mode** – balance-first, then answer conceptual questions
  - **Auto Balance Mode** – closed-loop automatic balancing
  - **Settings Menu** – configure stored angles and system behaviour
- Smooth servo motion with:
  - Backlash compensation
  - Direction-change ramping
  - Assist + hold control logic
- Dual AS5600 usage:
  - ADC path for fast gating and assist logic
  - I2C path for precise holding
- Persistent storage using ESP32 **Preferences (NVS)**
- OLED UI with menus, prompts, and real-time feedback
- Rotary encoder navigation with push-button confirmation

---

## 🎓 Education Mode Capabilities

Education mode dynamically generates questions after the lever is balanced:

- Force calculation
- Moment calculation
- Balance state interpretation
- Direction and motion understanding
- True / False conceptual checks

Supported answer types:
- Numeric values
- Word-based descriptions
- True / False

---

## 🧠 Control & Software Concepts

- Closed-loop feedback control
- Hysteresis and deadband handling
- Sensor filtering (EMA + trimmed mean)
- Servo backlash take-up logic
- Human–machine interface (HMI) design
- Non-blocking UI and control loops
- Persistent configuration storage

---

## 🔌 Hardware Overview

| Component | Quantity |
|---------|----------|
| ESP32 | 1 |
| Servo Motor | 1 |
| AS5600 Magnetic Encoder | 1 |
| OLED Display (SSD1306 128×64) | 1 |
| Rotary Encoder (with button) | 1 |
| External Power Supply | 1 |

---

## 📍 Pin Configuration

| Function | ESP32 Pin |
|--------|----------|
| Servo PWM | GPIO 4 |
| Encoder CLK | GPIO 25 |
| Encoder DT | GPIO 33 |
| Encoder Button | GPIO 32 |
| AS5600 Analog OUT | GPIO 34 |
| OLED SDA | GPIO 21 |
| OLED SCL | GPIO 22 |

---

## 🖥 User Interface

- Rotate encoder to navigate menus
- Press encoder to confirm selections
- OLED displays:
  - Mode selection
  - Real-time angle feedback
  - Game statistics
  - Education questions and answers
  - Auto-balance state information

---

## 🖼 OLED Frame Bitmap Generator (Python)

This project includes a **Python utility** that converts image files into **Arduino-compatible OLED bitmap headers** for animations and startup screens.

### Features
- Supports `.png`, `.jpg`, `.bmp`
- Automatically resizes to **128×64**
- Correct OLED bit mapping (1 = white, 0 = black)
- Generates:
  - One `.h` file per frame
  - A combined `frames.h` file with:
    - `frames[]` pointer array in `PROGMEM`
    - `FRAME_COUNT` definition

---

## 📂 Folder Structure

project/
├── frames/ # Input images
├── output_h/ # Generated Arduino headers
│ ├── frame_01.h
│ ├── frame_02.h
│ └── frames.h
├── convert.py # Python bitmap conversion script
└── pivotlab.ino # ESP32 firmware


> Frame playback order follows filename sorting  
> Use zero-padded names like `frame_01.png`

---

## 🛠 Software Stack

### ESP32
- Arduino framework
- Adafruit SSD1306 & GFX
- ESP32Encoder
- ESP32Servo
- Preferences (NVS)

### PC / Tooling
- Python 3
- Pillow (PIL)

Install Python dependency:
```bash
pip install pillow
🚀 Setup Instructions
Install required Arduino libraries

Flash the ESP32 firmware

Connect hardware according to the pin configuration

(Optional) Generate OLED frames using the Python script

Power on the device and navigate using the rotary encoder

🧪 Known Limitations
Single servo supported

Fixed OLED resolution (128×64)

Rule-based education questions

Bitmap generator uses hard monochrome threshold (no dithering)

🌱 Possible Improvements
Multi-servo support

Wireless monitoring or data logging

Adaptive education difficulty

OLED dithering support in Python tool

Modular .h / .cpp refactoring

👤 Author
Lewis Tan
Singapore Polytechnic – EEE / FabLab
Educational Mechatronics & Interactive Systems Project
