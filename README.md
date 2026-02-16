# 📈 CryptoDroid

A real-time cryptocurrency tracking application built with **Modern Android Development** standards. It features live WebSocket updates, dynamic charting, and robust connection handling.

## 📱 App Demo & Screenshots

| 🎥 Live Demo | 📉 Watchlist | 📊 Real-Time Detail Chart |
|:---:|:---:|:---:|
| <img src="https://github.com/user-attachments/assets/5307851c-e810-46cf-97ec-61fb5eab2ebf" width="250" /> | <img src="https://github.com/user-attachments/assets/f6584220-fea2-4549-819d-b8fd2f0c43f4" width="250" /> | <img src="https://github.com/user-attachments/assets/75ccb82f-2a2e-41cc-a91e-c047437c7b46" width="250" /> |

## 🛠 Tech Stack

* **Architecture:** MVVM (Model-View-ViewModel) with Clean Architecture principles.
* **UI:** Jetpack Compose (Material3).
* **Concurrency:** Kotlin Coroutines & Flow (StateFlow, SharedFlow, CallbackFlow).
* **Networking:**
    * **OkHttp:** For raw WebSocket connections.
    * **Gson:** For JSON parsing.
* **Navigation:** Type-Safe Navigation Compose (Kotlin Serialization).
* **Graphics:** Custom Canvas API for real-time Bezier curve charts.

## ✨ Key Features

* **⚡ Real-Time Data:** Connects directly to Binance WebSockets for live price updates (Tick-by-tick).
* **📊 Dynamic Charting:** Custom-built `Canvas` chart that renders a smoothed Bezier curve of the last 5 minutes of price history.
* **🔄 Resilience:** Implements **Exponential Backoff** to automatically reconnect during network failures.
* **🧵 Optimization:** Uses `callbackFlow` to bridge WebSocket events to Flow, and a custom **Sampler** to throttle UI updates for performance.
* **🏗 Scalability:** Architecture supports watching 25+ coins simultaneously with efficient `LazyColumn` rendering.

## 🚀 How to Run

1.  Clone the repo:
    ```bash
    git clone [https://github.com/ManojOnDroid/CryptoDroid.git](https://github.com/ManojOnDroid/CryptoDroid.git)
    ```
2.  Open in **Android Studio**.
3.  Sync Gradle and Run on Emulator/Device.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.