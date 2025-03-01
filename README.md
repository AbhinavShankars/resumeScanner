# ResumeScanner
## <h3>🚀 Object :</h3> This application is meant for demo purpose only and it's based on spring-boot framewok where user can upload the resume/CV/File and based on inputs ; program will analyze the data and show-case the results.

This application provide the accuracy to data and provide the matches based on user-inputs.

ResumeScanner is a Java-based web application that utilizes the Apache OpenNLP library for keyword extraction and matching against the contents of a resume PDF file. The application takes a resume PDF and a job description as inputs, and then extracts the text from the PDF file and identifies the relevant keywords from the job description using OpenNLP. It then calculates the percentage of matched keywords in the resume and identifies any unmatched keywords.

The project is designed to assist job seekers in ensuring that their resumes are optimized for applicant tracking systems (ATS) used by many employers. By identifying the relevant keywords from the job description, the application helps job seekers to tailor their resumes to meet the requirements of the specific job.


## <h3>🚀 Technology : </h3>  Java , springboot , microService , Apache Kafka


## <h3>🚀 Database : </h3> MySQL or mongoDB

### <h3>🚀 End to End Flow Diagram : </h3>
![endToendFlow](https://github.com/user-attachments/assets/fca817a8-1711-482d-bcff-9a8cd5a14dc2)


### Prerequisites

To build and run the application, you will need:

- Java 17
- Apache Maven

### How to Test API

 - Go to postman and click on form date and put parameter.

  ![image](https://github.com/user-attachments/assets/fef8a019-60ae-4265-924e-33569c19261a)


## Installation:

- Clone this repository to your local machine
- Navigate to the project directory
- Run mvn package to build the application
- Run java -jar target/resumescanner.jar to start the application

## Usage:

- Open a REST API client tool such as Postman
- Send a POST request to http://localhost:8001/check-resume
- Attach a resume PDF file to the request using the "file" parameter
- Provide the job description as a string using the "desc" parameter
- Send the request
- The application will return the percentage of matched keywords and any unmatched keywords found in the resume in JSON format.

## Acknowledgments
This project was inspired by the need to optimize resumes for ATS systems, and is made possible by the Apache OpenNLP library.

## Thanks for visit
[![image](https://github.com/user-attachments/assets/6a3bffbc-38a1-4ead-810a-4d339c74f5b3)](https://media.giphy.com/media/3odxXG6oUNRVhsdcLK/giphy.gif)

