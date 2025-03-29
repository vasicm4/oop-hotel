### Dear Reader,

### This project is an information system built for a hotel. 

### Application is written in Java 17 with GUI implemented using Swing and XChart libraries. 

### Specifications:

#### Users: There are multiple types of users including administrator, receptionist/agent, janitor and guest, every one of them can perform unique set of operations related to their respective role, for example administrators can perform CRUD operations on whole set of entities, receptionsts can manage guests and rooms, while janitors are able to check rooms whether they are clean or not.

#### Hotel Organization: Entities related to hotel organization are rooms, room types, reservations, price lists and additional services, where rooms can be free or occupied, dirty or cleaned, reservations can be either on hold, accepted, rejected or canceled, every reservation is related to date, room type and additional services chosen by guest. They can be managed by both administrators and receptionsts. Price lists are made by administrators, prices are defined for every room type, additional services are chosen by either existing users or receptionsts. Additional services include breakfast, lunch, dinner, drinks, spa, pool, massages, etc. 

#### GUI: The graphical user interface (GUI) is built using Swing. After logging in, each user type is presented with a personalized menu featuring the previously mentioned functionalities. Additionally, users have access to interactive charts displaying key hotel metrics, including income trends over the past 12 months, reservation statuses, janitor occupancy rates. This enhances usability and provides valuable insights at a glance.

#### Unit Testing: To ensure the proper functionality of the information system, I implemented a set of tests that helped validate its performance and reliability.
