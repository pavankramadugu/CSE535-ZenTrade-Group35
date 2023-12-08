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
- packages: pymongo, yfinance, pandas, numpy, niftystocks, pytickersymbols, python-dotenv, pytest, etc (Which are mentioned in the requirements.txt file)

You can install these packages using pip and follow the below command:

Execute this command

```bash
pip install -r requirements.txt
```

## File Structure

- `cdk`: Contains cdk code for AWS deployment.
- `lambda`: The folder which contains the code for the AWS lambda
    - `app.py`: The starting point of the lambda which contains the code for proper routing.
    - `stock_analysis.py`: The file that deals with fetching the records from the recommendations.py file
    - `users.py`: The file that deals with the Create, read, and delete operations of the user database.
-`layers`: contains all the dependencies required for deployment of the lambda functions in AWS.
- `tests`: The directory which contains the unit-test cases
    - `test_stock_analysis.py`: Contains unit tests for the functions in `stock_analysis.py`.
    - `test_users.py`: Contains unit tests for the functions in `users.py`

## Running the Stock Analysis Script (`stock_analysis.py`)

1. Open your command line interface (CLI).
2. Navigate to the directory(lambda) using the command `cd lambda'.
3. Run the script using Python command:

   ```bash
   python stock_analysis.py
   ```

This script will show the recommendations fetched from the recommendation engine and print them as a list, also performs API response build.

## Running the Test Scripts

1. Move to the `tests` directory
2. Run the test script using Python:

   ```bash
   pytest 
   ```
## Contact

For further assistance or to report issues, please contact Group 35 Members.

---
