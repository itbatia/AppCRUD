# AppCRUD
**AppCrud** - console CRUD application.

The user in the console has the ability to create, receive, edit and delete data.

**AppCRUD has the following entities:**  
*- Writer(id, name, List<Post> posts);*  
*- Post(id, content, List<Tag> tags, PostStatus status);*   
*- Tag(id, name);*  
*- PostStatus (enum ACTIVE, DELETED).*

**Layers:**   
***:white_check_mark: model*** - *POJO classes;*   
***:white_check_mark: repository*** - *classes that implement access to text files;*   
***:white_check_mark: controller*** - *handling requests from the user;*   
***:white_check_mark: view*** - *all the data needed to work with the console.*   

The data store is text files: writers.json, posts.json, tags.json

To work with JSON, you need to use the Gson library.  
To import dependencies - Maven

**Console example:**
