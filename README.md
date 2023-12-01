# Project Overview

This project is developed using Android Studio and Kotlin, focusing on creating an intuitive UI/UX for users to input personal data, assess their risk appetite, and analyze trading frequency. It includes features for reading, picking, and uploading files to AWS S3, and utilizing AWS Textract for PDF analysis.

## Key Files

The primary files I contributed to are:

- `activity_personal_info.xml`
- `activity_trading_frequency_analyzer.xml`
- `activity_risk_appetite.xml`

...and their respective Kotlin files. These files are integral to the project and depend on the other files in the repository for proper functionality. It is crucial to include the entire project code when cloning or pulling from this repository to ensure these files operate correctly.

## Features

- **Data Input:** Users can input personal data and specify their risk appetite through designated input fields.
- **File Handling:** The app supports reading and uploading CSV and PDF files.
- **AWS S3 Integration:** Uploaded files are stored securely on AWS S3.
- **AWS Textract:** PDF files are analyzed using AWS Textract, with results displayed in the app.

## Usage

- **Input Personal Data:** Navigate to the data input section and fill in the required fields.
- **Upload File:** Use the file picker to select and upload a CSV or PDF file to AWS S3.
- **View Analysis Results:** After uploading a PDF, wait for the analysis to complete and view the results in the app.

## Tech Stack

- Kotlin
- Android SDK
- AWS SDK for Android
- AWS Textract

## Contact

For any queries, contact Chetan Karthikeya Reddy Dandolu at dreddy6@asu.edu
