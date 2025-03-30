# OOP Hotel

OOP Hotel is an information system software built for a hotel that solves major issues related to management of medium scale organizations. 

***
## 🔧 Technologies



***
## 📋 Contents
1. [Specifications](#specifications)
2. [Dependencies](#dependencies)
3. [Getting Started](#start)
4. [License](#license)
5. [Contact](#contact)
***
<a name="specifications"><a/>
## 📌 Specifications:

#### 🧍 Users: 
There are multiple types of users including administrator, receptionist/agent, janitor and guest, every one of them can perform unique set of operations related to their respective role, for example administrators can perform CRUD operations on whole set of entities, receptionsts can manage guests and rooms, while janitors are able to check rooms whether they are clean or not.

#### 📁 Hotel Organization: 
Entities related to hotel organization are rooms, room types, reservations, price lists and additional services, where rooms can be free or occupied, dirty or cleaned, reservations can be either on hold, accepted, rejected or canceled, every reservation is related to date, room type and additional services chosen by guest. They can be managed by both administrators and receptionsts. Price lists are made by administrators, prices are defined for every room type, additional services are chosen by either existing users or receptionsts. Additional services include breakfast, lunch, dinner, drinks, spa, pool, massages, etc. 

#### 💻 GUI:
The graphical user interface (GUI) is built using Swing. After logging in, each user type is presented with a personalized menu featuring the previously mentioned functionalities. Additionally, users have access to interactive charts displaying key hotel metrics, including income trends over the past 12 months, reservation statuses, janitor occupancy rates. This enhances usability and provides valuable insights at a glance.

#### 🧪 Unit Testing: 
To ensure the proper functionality of the information system, I implemented a set of tests that helped validate its performance and reliability.

***
<a name="dependencies"><a/>
## ⚒️ Dependencies
In order to run this project properly you need to make sure that you have installed:
1. **Java Development Kit (JDK) - Version 17**: distribution of Java technology by Oracle Corporation
2. **JUnit 5 Library**: modern foundation for developer-side testing on the JVM
3. **XChart Library**:  light-weight and convenient library for plotting data
4. **Java IDE or text editor**: you can use either [Intellij IDEA](https://www.jetbrains.com/idea/), [Eclipse](https://www.eclipse.org/downloads/) or [Visual Studio Code](https://code.visualstudio.com/)
5. **Git** (optional): if you want to download this project and contribute to it

***
<a name="start"><a/>
## 🚀 Getting Started

1. **Clone the Repository**:
   Open a terminal and run the following command to clone the repository:

   ```bash
   git clone https://github.com/vasicm4/oop-hotel.git
   
Alternatively, download the repository as a ZIP file and extract it.

2. **Import the Project into IDE**:
   - Open your IDE or a text editor of choice.
   - Select `File > Open` (in IntelliJ) or `Import > Existing Project` (in Eclipse).
   - Navigate to the folder where the repository is located and open it.

3. **Configure JDK and Dependencies**:
   - Ensure JDK 17 is selected as the default SDK for the project:
     - **In IntelliJ IDEA**: Navigate to `File > Project Structure > SDK` and select `JDK 17`.
     - **In Eclipse**: Right-click on the project, go to `Properties > Java Build Path > Libraries`, and add `JDK 17`.
   - Verify that the following libraries are added to the **Classpath**:
     - `junit-4.13.2-javadoc.jar`
     - `xchart-3.8.8.jar`

4. **Set Up Classpath**:
   - In the IDE, add the provided `.jar` files to the project:
     - **For IntelliJ IDEA**:
       - Right-click on the project folder.
       - Select `Add Framework Support > Add Library`.
       - Locate the `.jar` files and add them.
     - **For Eclipse**:
       - Right-click on the `Project > Build Path > Add External Archives`.
       - Navigate to the folder containing the `.jar` files and add them.

5. **Open the `Main.java` file in your IDE.**
6. **Run the file:**
   - **In IntelliJ IDEA**: Right-click on `Main.java` and select `Run`.
   - **In Eclipse**: Right-click on `Main.java` and select `Run As > Java Application`.
7. When the application starts, you can access the Hotel Management System's features.
8. **Testing:** In order to validate software's functionalities make sure to run test cases from `test` directory

***
<a name="license"><a/>
## ⚖️ License
This project is licensed under the [MIT License](./LICENSE). See the [LICENSE](./LICENSE) file for details.

***
<a name="contact"><a/>
## ☎️ Contact me

 - **Email**: [vasicmaksim4@gmail.com](mailto:vasicmaksim4@gmail.com)
 - **Github**: [vasicm4](https://github.com/vasicm4)
 - **Linkedin**: [Maksim Vasic](https://rs.linkedin.com/in/maksim-vasi%C4%87-514b11327)
***
