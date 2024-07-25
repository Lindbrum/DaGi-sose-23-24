# Event and ticket management - Project for S.O.S.E. (A.Y. 23-24).

1) #### First of all, ensure to clone the repository, by writing the following command:
   - git clone https://github.com/Lindbrum/DaGi-sose-23-24.git
2) #### To see the code, open the IDE "SpringToolSuit4" and import the projects by right click on the package explorer, then: import -> Maven -> Existing Maven Project -> Broswe -> and then select the repository where u cloned the projects.
3) #### Install the applications: "Docker Desktop" and "SprintToolSuite4".
4) #### Open "Docker Desktop".
5) #### Run the command "docker-compose up" inside the folder where is the cloned repository. U have to wait about 5 minutes to download all dependencies.

6) Services pages can be visited with the following URLs:

- ##### discovery-server: http://localhost:8761
- ##### event-management-soap: http://localhost:8081/event-management-soap/
- ##### feedback-prosumer-rest: http://localhost:8082/api/feedback-prosumer/services
- ##### event-merch-prosumer-rest: http://localhost:8083/api/event-merch-prosumer-rest/services
- ##### sales-analysis-prosumer-rest: http://localhost:8084/api/sales-analysis-prosumer-rest/services
- ##### merchandising-rest: http://localhost:8085/api/merchandise-rest/services
- ##### authentication-rest: http://localhost:8086/api/authentication-rest/services
- ##### soap-proxy-prosumer-rest: http://localhost:8087/api/soap-proxy/services

7) #### Open SwaggerUI by clicking the link given in the service page.
8) #### To test the APIs, you have to copy-and paste the path given in the URL query parameter in the search bar of SwaggerUI to open our Swagger definition.
9) To play with the clients, go to SprintToolSuite4, search for "customer-client" and "organizer-client" and run as java application with:
   - Main named "CustomerClientapplication" for the "customer-client". Run it and play with the console.
   - Main named "OrganizerClientapplication" for the "organizer-client". Run it and play with the console.


##The documentation with all the digrams is available inside the Docs folder.


### Members names and contacts:
1) Dario D'Ercole - (288643) - dario.dercole@student.univaq.it
2) Giovanni Spaziani - (295397) - giovanni.spaziani@student.univaq.it
