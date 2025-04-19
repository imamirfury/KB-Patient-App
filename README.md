# KB Patient App

An Android application developed using **Kotlin** and **Jetpack Compose**, designed to manage patient registrations, visit details, and prescription summaries. The app follows an offline-first approach, storing all data locally and supporting synchronization when the device is online.

## 🔧 Features

- **Patient Registration Screen**
  - Input: Name, Age, Gender, Contact Number (optional), Location, Health ID/Unique Identifier.
  - Required field validation for Name and Age.
  - Responsive layout built with Jetpack Compose.
  - Data saved locally using Room Database.

- **Patient Visit Details Screen**
  - Inputs: Visit Date (auto-filled with current date), Symptoms, Diagnosis, Medicine Name, Dosage, Frequency, Duration.
  - Offline-first: Visit details are stored locally and synced later when online.

- **Prescription Summary Screen**
  - Displays complete patient and visit details.
  - Actions:
    - Print Prescription *(mocked)*.
    - Share via WhatsApp/SMS *(mocked)*.
    - Mark visit as "Completed" and store the status locally.

---

## 🧱 Project Architecture

The app follows **MVVM (Model-View-ViewModel)** architecture combined with:

- **Jetpack Compose** for UI.
- **Room** for local data storage.
- **Hilt** for dependency injection.
- **Coroutines & Flows** for asynchronous operations.
- **Repository pattern** for data abstraction.
- **Offline-first strategy** with support for sync when network is available.

### Layers Breakdown:

- **UI (View)**: Built using Jetpack Compose. Responsive and adaptive layouts for form inputs and summaries.
- **ViewModel**: Contains state management logic using `StateFlow` and `MutableState`.
- **Repository**: Handles data access operations from both local and (mocked) remote sources.
- **Local Data**: Managed with Room, includes Patient and Visit entities.
- **Sync Manager**: Handles logic to sync local data with a remote server when internet is available.

---

## 🚀 Getting Started

Follow the steps below to run the app locally:

### 1. Clone the Repository
```bash
git clone https://github.com/imamirfury/KB-Patient-App.git
cd KB-Patient-App