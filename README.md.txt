This Is our application

To Run the application we write maven command.
	mvn spting-boot:run
	
we use H2 database as 'In memory DB'
The username and password are 'root' and 'root' defined in application.properties
if you have different password we change them.
And one more thing we save file at path C:// if you don't have this path change it in File_Controller.java file at two places.

Now
We have total 4 API
1)To upload a file and save it's Metadata. open postman and hit a POST api
	localhost:8080/upload
	Inside it's body add 'key' as 'file' and 'value' as 'path of the file' in system.
Ones we hit it the file is saved in path C://abc.txt and in response we get a unique ID.

2)Get api to get metadata of saved file by it's "Name"
    localhost:8080/metabyname/abc.txt

3)Get api to get metadata of saved file by it's "ID"
	localhost:8080/metabyid/1

4)Get api to download file content stream by it's name
	localhost:8080/download/abc.txt
  
If you have problem while Running it let me know.