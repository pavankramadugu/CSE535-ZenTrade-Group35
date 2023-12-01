# stock_analysis.py

import yfinance as yf
import json
from recommendation_function import get_tickers, get_date_range, get_weights, fetch_stock_data, calculate_metrics, select_stocks

def get_recommendations(countries, frequency, risk_appetite):
    """
    Get stock recommendations based on specified countries, frequency, and risk appetite.

    Parameters:
    - countries (list): List of countries.
    - frequency (str): Frequency for stock analysis (e.g., 'Weekly', 'Monthly').
    - risk_appetite (str): Risk appetite level (e.g., 'Low', 'Medium', 'High').

    Returns:
    - list: Recommended stock names.
    """
    tickers = get_tickers(countries)
    start_date, end_date = get_date_range(frequency)
    weights = get_weights(risk_appetite)
    stock_data = fetch_stock_data(tickers, start_date, end_date)
    stock_metrics = calculate_metrics(stock_data)
    top_stocks = list(select_stocks(stock_metrics, weights).keys())
    final_names = [yf.Ticker(stock).info['shortName'] for stock in top_stocks]
    return final_names

def get_recommendations_static_without_dependency(countries, frequency, risk_appetite):

    return ["Apple Inc."]

def build_response(recommendations):
    """
    Build a response containing stock recommendations.

    Parameters:
    - recommendations (list): List of recommended stock names.

    Returns:
    - dict: Response containing the recommendations.
    """
    return {
        "statusCode": 200,
        "headers": {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json'
        },
        "body": json.dumps({
            "recommendations": recommendations
        })
    }

if __name__ == '__main__':
    frequency = 'Annually'
    risk_appetite = 'Low'
    countries  = ['china', 'Germany']
    # Fetch stock data for Chinese stocks based on low risk appetite
    print(get_recommendations(countries, frequency, risk_appetite)) 
    