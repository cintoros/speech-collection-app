## Requirements
**Required**
* Java 11 
* MariaDB 10.4
* Node.js 12.10.0 - 13.12.0
* Miniconda/Anaconda

**Note**: Other versions might work, but have not been tested.

**Optional**
* npm: @angular/cli
* IntelliJ Idea
## Data Structure
* the data can be loaded using `data_import/data-import.py`
   * use `conda env create -f environment.yml` to install the environment.
    
the data-structure looks like this:
* `data` the base data directory (can be changed in the configuration)
   * `source` directory containing the raw data used by the import & edit
      * `<id>` id of the transcript
         * `audio.wav` the raw audio file (used for import and re-cutting audio)
         * `indexes.xml` the transcript (only used for import)
   * `original_text` used to save the original text documents
      * `<id>.bin`
   * `recording` used to save the recordings
      * `<id>.ogg`
   * `text_audio` used to save pre-cut audio
      * `<id>.flac`
## Development
* copy the `application-dev-test.yml.example` to `application-dev-test.yml` and change it to match your local setup
* copy the `config.py.example` to `config.py` and change it to match your local setup   
* to run the development version run `gradle devBootRun` && `npm start` 

### Database Changes
We use a database first approach based on  https://flywaydb.org/ and https://www.jooq.org/   
In case the database needs to be changed:
1. create a new migration in `backend/src/main/resources/db/migration`
1. run the migration i.e `gradle devBootRun`
1. update the generated database classes using `gradle generateSampleJooqSchemaSource --rerun-tasks`

### Email
for local testing of the email features https://mailtrap.io/ can be used.

## Deployment
some additional packages may be needed (Ubuntu 18.04.3 ):
* `sudo apt install default-libmysqlclient-dev python3 mariadb-client libssl-dev nginx ffmpeg`
* mariadb 10.4 see https://downloads.mariadb.org/mariadb/repositories/#distro=Ubuntu&distro_release=bionic--ubuntu_bionic&mirror=cnrs&version=10.4

**NOTE:** for deployments the default admin password should be changed.

1. run `gradle buildProd` to build the production jar
   1. In case of other servers without sub-url/proxie it is possible to just use  `gradle buildProd2`
   1. In case of a different sub-url it is best to add another build-task/configuration based on `prod2`
1. `rsync backend/build/libs/backend-1.0.0-SNAPSHOT.jar s1042:~/speech-collection-app/backend-1.0.0-SNAPSHOT.jar`
1. `ssh s1042`
1. `systemctl restart speech-collection-app`

In case the data_import has changed run:
* `rsync data_import/data_import.py s1042:~/speech-collection-app/data_import/data_import.py` 
* `rsync data_import/sentences.py s1042:~/speech-collection-app/data_import/sentences.py`

### Automatic Deployment
1. `nano /etc/nginx/nginx.conf` 
    ```nginx
    http {
        //...
        server {
            listen	80;
            server_name localhost;
            location / {
                proxy_pass http://localhost:8084/;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_set_header X-Forwarded-Port $server_port;
            }
            location /dev/ {
                proxy_pass http://localhost:8085/;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_set_header X-Forwarded-Port $server_port;
            }
        }
    }
    
    ```
1. `nano /lib/systemd/system/speech-collection-app.service`
    ```
    [Unit]
    Description=Speech Collection App
    After=network.target
    [Service]
    Environment=SPRING_CONFIG_LOCATION=classpath:/,classpath:/config/,file:/home/stt/speech-collection-app/application.yml
    Type=simple
    Restart=always
    RestartSec=1
    User=stt
    ExecStart=/usr/bin/java -jar /home/stt/speech-collection-app/backend-1.0.0-SNAPSHOT.jar
    [Install]
    WantedBy=multi-user.target
    ```
   `nano /lib/systemd/system/speech-collection-app-dev.service`
   ```
   [Unit]
   Description=Speech Collection App
   After=network.target
   [Service]
   Environment=SPRING_CONFIG_LOCATION=classpath:/,classpath:/config/,file:/home/stt/speech-collection-app-dev/application.yml
   Type=simple
   Restart=always
   RestartSec=1
   User=stt
   ExecStart=/usr/bin/java -jar /home/stt/speech-collection-app-dev/backend-1.0.0-SNAPSHOT.jar
   [Install]
   WantedBy=multi-user.target
   ```
1. `systemctl enable speech-collection-app.service` && `systemctl enable speech-collection-app-dev.service`

### Configuration
for passwords etc. configuration you can add a `application.yml` see 
https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config-application-property-files  
Create a single schema/user for deployments like 
`create schema <schema>; CREATE USER 'newuser'@'localhost' IDENTIFIED BY 'password'; GRANT ALL PRIVILEGES ON <schema> . * TO 'newuser'@'localhost';`
