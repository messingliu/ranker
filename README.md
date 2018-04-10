Build:
gradle build


Run:
./gradlew bootRun

go to browser or curl:
http://localhost:8008/ranker?id=1&candidateIds=8,2,3,4&modelId=0&linearModelParameter=popularity:0#type:0.5#distance:0.5
return
[4,3,2,8]
    
Log data in /var/log/ranker.log

run ./gradlew bootRun --debug to show print out log


# ranker
# ranker
