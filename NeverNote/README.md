# NeverNote REST API

“Nevernote” is a simple REST API that can be used to save, retrieve and update organized notes. Each note exists as part of a notebook, and a notebook is a collection of notes. Each note also has a set of "tags", that can be used to organize the notes within a notebook. These tags can be used as filters when retrieving a set of notes in a notebook. 

The purpose of this document is to inform the reader of the functionality of the API, the technologies used in the API, and how to build/run this API locally.

## Environment Setup

Pre-requisites to running this service locally:

Java SE Runtime Environment:
http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html

Java SE Development Kit:
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

Maven:
https://maven.apache.org/

Once these programs are installed, you can develop/build/run the service locally. 

## Starting the NeverNote API
To start the NeverNote API execute the following command from the directory where your pom.xml file lives:

    mvn spring-boot:run
    
This will start the web service on http://localhost:8080

## REST Endpoints

The following REST endpoints make up the NeverNote API

| HTTP Verb        | URL           | Description  | Status Codes |
| ------------- |-------------|:-----| ----|
| `POST` | `http://localhost:8080/notebook` | Creates a notebook, and returns the body of the newly created NoteBook| <ul><li>`201 Created`</li></ul> |
| `GET` | `http://localhost:8080/notebook` | Returns a list of all notebooks, or an empty set if none have been created yet | <ul><li>`200 OK`</li></ul> |
| `GET` | `http://localhost:8080/notebook/{id}` | Returns NoteBook with the given id| <ul><li>`200 OK` if NoteBook exists</li><li>`404 Not Found` if the notebook doesn't exist</li></ul> |
| `DELETE` | `http://localhost:8080/notebook/{id}`  | Deletes NoteBook with the given id| <ul><li>`204 No Content` if notebook successfully deleted</li><li>`404 Not Found` if the notebook doesn't exist</li></ul> |
| `POST` | `http://localhost:8080/notebook/{id}/note` | Creates a note in the notebook specified in the URL path| <ul><li>`201 Created`</li><li>`404 if the notebook doesn't exist`</li></ul> |
| `GET` | `http://localhost:8080/notebook/{id}/note?tag={tagname}` | Returns all notes in a given notebook, with the option to filter on tag, and returns empty set if none exist| <ul><li>`200 Ok`</li><li>`404 Not Found` if the notebook doesn't exist</li></ul> |
| `GET` | `http://localhost:8080/note/{id}` | Retrieves Note with the given id| <ul><li>`200 Ok` if Note exists</li><li>`404 Not Found` if the note doesn't exist</li></ul> |
| `DELETE` | `http://localhost:8080/note/{id}` | Deletes Note with the given id| <ul><li>`204 No Content` if Note successfully deleted</li><li>`404 Not Found` if the ote doesn't exist</li></ul> |
| `PUT` | `http://localhost:8080/note/{id}` | Updates Note with the given id, ignores invalid request body members | <ul><li>`200 Ok` if note successfully updates</li><li>`404 Not Found` if the note doesn't exist</li></ul> |

## Sample JSON Request Bodies

Note:
{
	"title":"This is a test title",
	"body" :"This is a test body",
    "tags" : ["test","tags"]
}

NoteBook:
{}

## Technologies used in this project
<ul>
<li>Maven</li>
<li>Spring Framework</li>
<li>Java</li>
<li>JUnit</li>
</ul>
