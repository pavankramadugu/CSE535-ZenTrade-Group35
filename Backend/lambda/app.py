# app.py

import json
from stock_analysis import get_recommendations, build_response, get_recommendations_static_without_dependency
from users import handle_users_request

def handler(event, context):
    """
    Lambda function handler for processing requests.

    Parameters:
    - event (dict): AWS Lambda event containing information about the request.
    - context (object): AWS Lambda context object.

    Returns:
    - dict: Response to be returned to the client.
    """
    path = event['path']
    data = json.loads(event['body'])

    if path == "/recommendations":
        # Process recommendation request
        return build_response(get_recommendations(data['countries'], data['frequency'], data['factor']))
    elif path.startswith("/users"):
        # Process user-related requests
        return handle_users_request(path, data)
    else:
        # Return error response for unrecognized endpoints
        return build_response({"error": "Endpoint not found"})
