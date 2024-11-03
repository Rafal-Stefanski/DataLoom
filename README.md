## DATA LOOM - Fruit Company Application
### Function: Data gathering, updating, and combining into a single collection in MongoDB.

### Description: 
Application main function is to gather data from different sources, and update a single document in DataLoomCatalogue collection in MongoDB.
Main goal is mimic gathering data from fruit harvesting, where each batch of fruits is described with type of fruit origin, quantity, quality, and place of storage. Using Kafka consumer, app reads events which are stored in MongoDB collections to be reprocessed and combined into a single document in DataLoomCatalogue collection, when all needed information for each fruit are present. 
Each document contains information about fruit harvest events, inventory updates, and quality checks all bound to a fruit type and fruit grade, with exact amount of fruits in storage. 

### Technologies:
Application works on Spring boot 3.3.4 and Java 17, MongoDB 4.0.28, Kafka 3.2.0. For local development and testing, local environment is created using Docker containers, all needed components are started and prepared in bash scripts. Including database creation and configuration, and starting Kafka UI for ease of testing messages. 

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
- DataLoomCatalogue stores information about fruit harvest events, inventory updates, and quality checks all bound to a batchId.
- Following events are read from Kafka topics:
  - FruitHarvestEvent
  - InventoryUpdateEvent
  - QualityCheckEvent
- when all three are present for specific batchId, the information is combined into a single document in DataLoomCatalogue or updated if the document already exists.

### Usage (local environment): 
- Sending events: 
  - Events can be sent to Kafka topics using Kafka AKHQ UI ```http://localhost:8090/ui/docker-kafka-server/```
  - All collections can be viewed in MongoDB client ```mongodb://localhost:27017```
  - To check currently available fruits in storage, use ```http://localhost:8080/fruits```
  
    This will return all list of  fruits it's grade and available quantity in kg.
  
    Example: 
  ```
  "Available: Fruit Type: Apple, Quality Grade: A_PLUS, Quantity: 550",
  "Available: Fruit Type: Pear, Quality Grade: B, Quantity: 230"
  ```


