# ZenTrade

ZenTrade, an innovative mobile application, is designed to revolutionize the investment advisory domain by aligning with the "Guardian Angel" philosophy. This app intelligently integrates financial technology with stress monitoring, offering a unique, personalized investment experience.


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
