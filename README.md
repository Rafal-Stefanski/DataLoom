## DATA LOOM - Fruit Company Application
### Function: Data gathering, and updating.

### Description: 
Application work on Spring boot 3.3.4 and Java 17. Using Kafka consumer, reads following events:
- fruit harvesting event
- inventory update event
- quality check event

### How to run: 
Application run on Docker containers. To run the application in detached mode:
1. make sure Docker Desktop is running,
2. use the following command in bash: ```./start_local_environment.sh```
3. Above command will create containers with 
   - Kafka, 
   - Zookeeper,
   - Kafka akhq.io for kafka UI,
   - MongoDB, with dedicated database and collections.
4. Application can be run under IDE, for testing or development purposes.

### Logic:
- Application reads events from Kafka topics, and updates MongoDB collections.

