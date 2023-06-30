the first step before running the executable jar file is to setup database using docker,  the steps to initialize the DB in you local: 
1. first of all, you will need to installation of docker, its pretty simple, you could just download it and install into you pc, and start it, please refer: https://docs.docker.com/engine/install/
2. second, open your terminal(powershell in windows, terminal in mac) and go to database/ folder under project root folder
3. then, run command 'docker compose up' in your terminal.
4. you shoud be able to see last message line like "database-postgres-1  | 2023-06-22 03:03:56.063 UTC [1] LOG:  database system is ready to accept connections"
5. once this done, you could proceed to double click the executable jar file to run the application.
