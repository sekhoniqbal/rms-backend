# RMS Referral Management System

## Purpose
The purpose of referral management system is to facilitate clinic staff  in creating referrals for their patients  and automating the referral assignment process so that referrals are always assigned to specialists who are available and have capacity to take new patients. This will streamline the referral process among primary care physicians, specialists, and health providers involved in patient care leading to reduced patient wait times, improved specialist utilization, reduced human error, and increased patient satisfaction. System will provide following core functionalities
1. Interface to add new patients and providers to the system
2. Interface to create referrals for the patients and suggest a provider to whom it should be assigned considering work load of all the specialists in that area of specialty
3. Interface to view all the referrals assigned to a provider
4. Interface to view all the referrals for a patient
5. Interface to view all the providers with a particular specialty
6. Interface to easily add, edit or delete patient and provider information.

## Application Demo
Both frontend and backend application are currently deployed on a server for a quick demonstration of the application. Application is accessible from following URLs

#### Application Front End
http://iqserver.duckdns.org:3000
#### Swagger web UI
http://iqserver.duckdns.org:8080/swagger-ui/index.html
#### Swagger API URL
http://iqserver.duckdns.org:8080/v3/api-docs

### Application Code
#### Backend
https://github.com/sekhoniqbal/rms-backend
#### Frontend
https://github.com/sekhoniqbal/rms-frontend

## Architecture and technologies
### Requirements
 Referral Management System must be designed keeping in mind below points
 -  system will be used by thousands of health care staff on daily bases to provide fast and efficient care to the hundreds of thousands of citizens.
 - system will include sensitive and personal health information of the patients and the providers. 
 - system  will be accessed from  different type of devices such as desktop computers, tablets and smart phones
 - system might provide multiple front end user interface for each type of its end users. for example, the interface available to a clinic staff would be completely different than seen by a patient.
 ### Design
 As the system will have more than one user interface, therefore back end system must be made accessible through a restful API,  which can then be consumed by multiple front end interfaces. This design will decouple the backend and frontend of the system and would allow independent development and extension of each part.
 ### Technologies
 As this  system will be used by thousands of users everyday and will hold sensitive data, it  must be built on platform which can not only scale, but also allows secure and authenticated access to the sensitive health information it will hold.
 #### Backend Technology
 Spring boot framework would be excellent choice for the backend. Framework with allow us to easily build highly scalable restful web API service with powerful and customizable authentication and access-controls.
 #### Frontend Technology
 React would be ideal choice to build the frontend user interfaces for the system.React will allow us to building interactive user interfaces quickly and efficiently as it makes application creation and  maintenance fast and easy through reusable components.

## Database Design
For the demonstration of basic functioning of the system. backend of the application uses sqlite database which is a file based database. For the core functionality of the  application, database is  designed as below.
- database consist of 4 tables namely patient, provider, speciality, referral
- each table has the unique id column as primary key.
-  provider table has a speciality_id column which references the id column(primary key) of the specialty table
-  referral table has a patient_id, provider_id and speciality_id column which reference the id column of patient, provider and speciality tables respectively.
### SQL for each table
SQL statement used to create each table are given below.
- speciality table was created with below sql statement
CREATE TABLE speciality (
	id integer,
	name varchar(255) unique,
	 primary key (id)
  )
- patient table was created with below sql statement
CREATE TABLE patient (
  id integer,
 name varchar(255),
  primary key (id)
  )
 - provider table was created with below sql statement
 CREATE TABLE provider (
   id integer,
   is_accepting_patients boolean,
   name varchar(255),
   speciality_id bigint not null,
   primary key (id)
     )
- referral table was created with below sql statement
CREATE TABLE referral (
 id integer,
 patient_id bigint not null,
 provider_id bigint not null,
 speciality_id bigint not null,
 primary key (id)
    )

## Overview of API endpoints
Backend application provides its service through restful web service. Application is accessible through below listed api endpoints.  
***you can click on the api links below to try  each api endpoint***

### Speciality API Endpoints
GET[/api/specialities/{id}](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/speciality-controller/getSpeciality_1)
- get specialty with the given id
-  throw not found error if id is valid but specialty is not found
- throw bad request error if id is invalid

PUT[/api/specialities/{id}](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/speciality-controller/updateSpeciality)
- update the specialty with the given id. 
- throw not found error if id is valid but specialty is not found
- throw bad request error if id is invalid
- throw bad request error if specialty name matches any other specialty or name is blank

DELETE[/api/specialities/{id}](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/speciality-controller/deleteSpeciality)
- delete the specialty with the given id
-  throw not found error if id is valid but specialty is not found
- throw bad request error if id is invalid
- throw bad request error if specialty with given id is assigned to any provider or referral

GET[/api/specialities](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/speciality-controller/getSpeciality)
- get all specialties

POST[/api/specialities](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/speciality-controller/createSpeciality)
- create new specialty
- throw bad request error if specialty name matches any existing specialty or if name is blank

GET[/api/specialities/{id}/suggestedProvider](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/speciality-controller/getsuggestProviders)
- get suggested provider for the specialty with given id. algorithm suggests the provider with given specialty who is accepting patients and has least number of referrals.
-  throw not found error if id is valid but specialty is not found
- throw bad request error if id is invalid
- throw not found error if no provider can be suggested for given specialty id

GET[/api/specialities/{id}/referrals](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/speciality-controller/getSpecialityReferrals)
- get all the existing referrals for the specialty with given id
- throw not found error if id is valid but specialty is not found
- throw bad request error if id is invalid

GET[/api/specialities/{id}/providers](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/speciality-controller/getSpecialityProviders)
- get all the providers for the specialty with  given id
- throw not found error if id is valid but specialty is not found
- throw bad request error if id is invalid
 
 ### Referral API  Endpoint 

GET[/api/referrals/{id}](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/referral-controller/getReferral_1)
 - get referral with the given id
 -  throw not found error if id is valid but referral is not found
- throw bad request error if id is invalid

PUT[/api/referrals/{id}](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/referral-controller/updateReferral)
- update the referral with the given id
- throw not found error if id is valid but referral is not found
- throw bad request error if id is invalid
- throw bad request error if specialty, provider or patient field is blank
- throw bad request if specialty and provider specialty does not match.

DELETE[/api/referrals/{id}](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/referral-controller/deleteReferral)
- delete the referral with the given id
- throw not found error if id is valid but referral is not found
- throw bad request error if id is invalid

GET[/api/referrals](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/referral-controller/getReferral)
- get all referrals

POST[/api/referrals](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/referral-controller/createReferral)
- create a new referral
- throw bad request error if specialty, provider or patient field is blank
- throw bad request if specialty and provider specialty does not match.

### Provider API Endpoint

GET[/api/providers/{id}](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/provider-controller/getProvider_1)
- get the provider with given id
- throw not found error if id is valid but provider is not found
- throw bad request error if id is invalid

PUT[/api/providers/{id}](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/provider-controller/updateProvider)
-  update the provider with the given id
- update can change provider name, specialty and whether provider is accepting new patients
- throw not found error if id is valid but provider is not found
- throw bad request error if id is invalid

DELETE[/api/providers/{id}](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/provider-controller/deleteProvider)
-  throw not found error if id is valid but provider is not found
- throw bad request error if id is invalid
-  throw bad request error if provider with given id has referrals assigned to itself
- delete provider if provider has no referrals assigned to itself

GET[/api/providers](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/provider-controller/getProvider)
- get all providers
POST[/api/providers](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/provider-controller/createProvider)
- create new provider
- throw error if the new provider does not have name, valid specialty id and boolean value indicating whether provider is accepting new patients or not.

GET[/api/providers/{id}/referrals](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/provider-controller/getProviderReferrals)
- get all the referrals which are assigned to provider with given id
### Patient API Endpoint

GET[/api/patients/{id}](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/patient-controller/getPatient_1)
- get the patient with given id
- throw not found error if id is valid but patient is not found
- throw bad request error if id is invalid

PUT[/api/patients/{id}](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/patient-controller/updatePatient)
- update the patient with the given id
-  throw not found error if id is valid but patient is not found
- throw bad request error if id is invalid
- throw bad request error if patient name is blank or less than 2 characters or longer than 30 characters

DELETE[/api/patients/{id}](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/patient-controller/deletePatient)
-  throw not found error if id is valid but patient is not found
- throw bad request error if id is invalid
- throw bad request if there referrals assigned to patient with given id
GET[/api/patients](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/patient-controller/getPatient)
- get all patients

POST[/api/patients](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/patient-controller/createPatient)
- create new patient with given name
-  throw bad request error if patient name is blank or less than 2 characters or longer than 30 characters
GET[/api/patients/{id}/referrals](http://iqserver.duckdns.org:8080/swagger-ui/index.html#/patient-controller/getPatientReferrals)
-get all the referrals for patient with given id
-  throw not found error if id is valid but patient is not found
- throw bad request error if id is invalid







## Application Backend Installation
Backend of the application has been built using the spring boot framework.
It needs java 17 installed.
### Installing java on Linux. 
below are commands to install java on linux server running ubuntu
`mkdir /opt/app/rms`
`cd /opt/app/rms`
`wget https://download.oracle.com/java/17/archive/jdk-17.0.7_linux-x64_bin.tar.gz`
`gunzip jdk-17.0.7_linux-x64_bin.tar.gz`
` tar -xf jdk-17.0.7_linux-x64_bin.tar`
setup JAVA_HOME and PATH variable correctly
`JAVA_HOME=/opt/app/rms/jdk-17.0.7`
`export JAVA_HOME`
`PATH=$PATH:$JAVA_HOME/bin`
`export PATH`

### Installing java on Windows
download and install jdk from https://download.oracle.com/java/17/latest/jdk-17_windows-x64_bin.msi

### Running Backend Application
download application code
`git clone https://github.com/sekhoniqbal/rms-backend`

#### for  linux
make the mvnw file executable if not already on lin
`cd /opt/app/rms/rms-backend`
`chmod 777 /opt/app/rms/rms-backend/mvnw`
`mvnw clean package`
`mvnw spring-boot:start`
#### for windows
open the folder `rms-backend` folder in windows command prompt
run the command
`mvnw clean package`
`mvnw spring-boot:start`

Once the application launches, you should be able to access it at 
http://localhost:8080/
http://localhost:8080/v3/api-docs
http://localhost:8080/swagger-ui/index.html



## Application  Frontend Installation
Frontend of the application has been build using react frontend framework. Requirement to run the react application is to have node and npm on development machine. 
please follow the steps given at https://nodejs.org/en to install LTS version of  nodejs and then follow the steps below.
`cd /opt/app/rms/`
`git clone https://github.com/sekhoniqbal/rms-frontend`

`cd /opt/app/rms/rms-frontend`
`npm install`

### for local development
`npm start`
in your browser go to http://localhost:3000
### for production build
`npm run build`
 `npx serve -s -l 3000 ./build`
 go to http://yourservername:3000







