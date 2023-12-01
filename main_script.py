from recommendation_functions import fetch_stock_data, calculate_metrics, select_stocks, get_weights, get_tickers, \
    get_date_range
import yfinance as yf

if __name__ == '__main__':
    frequency = 'Annually'
    risk_appetite = 'Low'

    tickers = get_tickers(['China', 'Germany'])
    start_date, end_date = get_date_range(frequency)
    weights = get_weights(risk_appetite)

    stock_data = fetch_stock_data(tickers, start_date, end_date)
    stock_metrics = calculate_metrics(stock_data)
    top_stocks = list(select_stocks(stock_metrics, weights).keys())

    final_names = []

    for stock in top_stocks:
        final_names.append(yf.Ticker(stock).info['shortName'])

    print(final_names)
