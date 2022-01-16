# AppCRUD

**AppCrud** - console CRUD application.

The user in the console has the ability to create, receive, edit and delete data.

#### AppCRUD has the following entities:  
>*- Writer (id, name, List<Post> posts);*  
*- Post (id, content, List<Tag> tags, PostStatus status);*   
*- Tag (id, name);*  
*- PostStatus (enum ACTIVE, DELETED).*

#### Layers:     
> ***model*** - *POJO classes;*   
***repository*** - *classes that implement access to text files;*   
***controller*** - *handling requests from the user;*   
***view*** - *all the data needed to work with the console.*   

The data store is text files: writers.json, posts.json, tags.json.  
The Gson library is used to work with JSON.  
To import dependencies - Maven.

| Console example: |
|:----:|
| ![screen](screenshots/screen.jpg) |

#### Инструкции для запуска приложения:
| № | Этапы выполнения |Скриншоты|
|:----:|:----|:---- |
| 1 | [Скопируйте код на свой ПК](https://github.com/itbatia/AppCRUD/archive/refs/heads/master.zip)||
| 2 | Распакуйте архив. Укажите - куда извлечь файлы. В указанном месте появится папка AppCRUD-master. |![](screenshots/scr 1.png)|
| 3 | Зайдите в папку AppCRUD-master и в адресной строке пропишите: cmd. |![](screenshots/scr 2.png)  |
| 4 | Откроется командная строка, в которой необходимо прописать команду: mvn package. Результатом её выполнения будет примерно следующее:|![](screenshots/scr 3.png)|
| 5 | Затем пропишите команду: mvn exec:java -Dexec.mainClass="com.itbatia.appCRUD.Main". Программа запущена и готова к работе! |![](screenshots/scr 4.png)  |

