# email-summary-generator
This application utilizes Gmail API to analyze emails you receive and provides insightful report that you can use to understand your inbox better. It also schedules appointments from email using Google Calendar on your emails.

### Prerequisites
* You'll need a Google account...obviously.
* Create Google Cloud Platform account using your email which will be used to get auth tokens for accessing your Gmail via APIs.
* Make sure that you have Maven 3.4+ installed on your machine. You can check by using below command or [install it here](https://maven.apache.org/install.html).
```shell
mvn --version
```

### Setup
1. To begin, fork this repo on GitHub.
2. Clone the repository of your fork. Launch your terminal, and enter the following command:
```shell
git clone https://github.com/<your-username>/email-summary-generator.git
```
Replace <your-username> with your GitHub username.
3. Create credentials from GCP portal, download the json file and place under `src/main/resources`.
4. Navigate to `src/main/resources` and update `config.json` file.
5. On the root directory of your project, enter the following command to compile the application:
```shell
mvn clean install
```
6. Once the build is complete, enter below command to run the application:
```shell
java -jar target/email-summary-1.0-SNAPSHOT.jar
```