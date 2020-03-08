# Service for the library 
Another training project 

## Build with Docker 

* `sudo docker build -f Dockerfile -t library . `

## Run with Docker 
* ` sudo docker run -e "SPRING_DATASOURCE_USERNAME=library" -e "SPRING_DATASOURCE_PASSWORD=library" -e "EXTENAL_URL=https://XXXX/api/webhook/telegram" -e "LOGGING_PATH=/var/log/library/" -e "TELEGRAM_TOKEN=YYY" --network host -d --name library --dns 8.8.8.8 library `

XXXX is your url and YYY is you Telegram token 
