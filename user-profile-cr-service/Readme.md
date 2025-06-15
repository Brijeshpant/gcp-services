./gradlew :user-profile-cr-service:bootRun
./gradlew :user-profile-cr-service:bootJar

curl localhost:8080/users/profile/1
//Deploy from source without Dockerfile
```
gcloud run deploy --source . --base-image=java21
gcloud run deploy --source  user-profile-cr-service/build/libs/ --base-image=java21 \
--allow-unauthenticated

## with dockerfile
docker build -t user-profile-service .
docker run -it -ePORT=8080 -p8080:8080 user-profile-service

gcloud auth configure-docker asia-south2-docker.pkg.dev

docker tag user-profile-service:latest asia-south2-docker.pkg.dev/codewithb-453208/bp-repos/user-profile-service:latest
docker push asia-south2-docker.pkg.dev/codewithb-453208/bp-repos/user-profile-service:latest

gcloud run deploy user-profile-service \
--image=asia-south2-docker.pkg.dev/codewithb-453208/bp-repos/user-profile-service:latest \
--region=asia-south2 \
--allow-unauthenticated




//Using pack
gcloud builds submit --pack=image=asia-south2-docker.pkg.dev/codewithb-453208/bp-repos/user-profile-service1:latest
gcloud run deploy user-profile-service1 \
--image=asia-south2-docker.pkg.dev/codewithb-453208/bp-repos/user-profile-service1:latest \
--startup-probe httpGet.path=/health,httpGet.port=8080,initialDelaySeconds=5,failureThreshold=5,timeoutSeconds=10,periodSeconds=10 \
--region=asia-south2 \
--allow-unauthenticated 