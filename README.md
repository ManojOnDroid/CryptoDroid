# 📈 CryptoDroid

A real-time cryptocurrency tracking application built with **Modern Android Development** standards. It features live WebSocket updates, dynamic charting, and robust connection handling.

<div align="center">
  <video src="LINK_TO_YOUR_VIDEO.mp4" width="400" />
</div>

## 📱 Screenshots

| 📉 Live Watchlist | 📊 Real-Time Detail Chart |
|:---:|:---:|
| <img src="placeholder_list.png" width="300"/> | <img src="placeholder_detail.png" width="300"/> |

## 🛠 Tech Stack

* **Architecture:** MVVM + Clean Architecture
* **UI:** Jetpack Compose (Material3)
* **Concurrency:** Kotlin Coroutines & Flow (StateFlow, SharedFlow, CallbackFlow)
* **Networking:** OkHttp (WebSockets) + Gson
* **Navigation:** Type-Safe Navigation Compose
* **Graphics:** Custom Canvas API for Bezier curves

## ✨ Key Features

* **⚡ Real-Time Data:** Connects to Binance WebSockets for millisecond-level updates.
* **📊 Dynamic Charting:** Custom `Canvas` implementation that renders a smoothed Bezier curve of price history.
* **🔄 Auto-Reconnect:** Implements exponential backoff to handle network flakiness.
* **Performance:** Uses `LazyColumn` with stable keys to handle 25+ rapidly updating items at 60fps.

## 🚀 How to Run

1.  Clone the repo:
    ```bash
    git clone [https://github.com/ManojOnDroid/CryptoDroid.git](https://github.com/ManojOnDroid/CryptoDroid.git)
    ```
2.  Open in **Android Studio**.
3.  Build and Run.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.