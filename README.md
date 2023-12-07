# ZenTrade

ZenTrade, an innovative mobile application, is designed to revolutionize the investment advisory domain by aligning with the "Guardian Angel" philosophy. This app intelligently integrates financial technology with stress monitoring, offering a unique, personalized investment experience.

# UI
---
## Introduction

This README provides instructions on how to run the Android application located in the "UI" folder.

## Prerequisites

- Android Studio (latest version recommended)
- An Android device or emulator

## Installation and Setup

1. **Clone the Repository:**
   If you haven't already, clone the repository to your local machine.

2. **Open Android Studio:**
   Launch Android Studio and select 'Open an existing Android Studio project'.

3. **Navigate to the 'UI' Folder:**
   In the file dialog, navigate to the cloned repository and open the 'UI' folder.

4. **Wait for Gradle Build:**
   Once opened, the project will start a Gradle build. Wait for this process to complete.

## Running the App

1. **Connect a Device or Use an Emulator:**
   Connect your Android device via USB and enable USB debugging. Alternatively, you can use the Android emulator.

2. **Run the Application:**
   Click on the 'Run' button (green triangle) in Android Studio. Choose your device or a virtual device from the list.

3. **View the App:**
   Once the app is installed, it will launch automatically on your device or emulator.

## Troubleshooting

- **Gradle Sync Issues:** If you encounter Gradle sync issues, try rebuilding the project or restarting Android Studio.
- **Device Connection Problems:** Ensure that USB debugging is enabled on your device and that it is properly connected to your computer.
- **App Crashes:** Check the Logcat in Android Studio for any errors or exceptions that may indicate the cause of the crash.


# Backend
---

## Readme for Executing the Stock Recommendation and Testing Scripts

This readme provides instructions on how to execute the stock recommendation and testing scripts. These scripts use `yfinance`, `pandas`, `niftystocks`, and `pytickersymbols` for fetching and analyzing stock data.

## Requirements

Before running the scripts, ensure you have the following installed:

- Python 3.8+
- Libraries: yfinance, pandas
- Additional packages: niftystocks, pytickersymbols

You can install these libraries using pip:

```bash
pip install yfinance pandas niftystocks pytickersymbols
```

## File Structure

Navigate to `Recommendations` folder inside Backend. Remaining folders has deployment setup and infrastructure.

- `recommendation_functions.py`: Contains core functions for stock data fetching and analysis.
- `main_script.py`: The main script that uses functions from `recommendation_functions.py` to provide stock recommendations.
- `test_script.py`: Contains unit tests for the functions in `recommendation_functions.py`.

## Running the Main Script (`main_script.py`)

1. Open your command line interface (CLI).
2. Navigate to the directory containing the script.
3. Run the script using Python:

   ```bash
   python main_script.py
   ```

This script will fetch stock data based on predefined criteria, analyze it, and print a list of recommended stocks.

## Running the Test Script (`test_script.py`)

1. Make sure you are in the same directory as the scripts.
2. Run the test script using Python:

   ```bash
   python test_script.py
   ```

This will execute a series of unit tests defined in the script to ensure the functionality of `recommendation_functions.py`.

## Contact

For further assistance or to report issues, please contact Group 35 Members.

---