
### Run locally
``./gradlew runFunction -Prun.functionTarget=com.brij.CloudFunctionDemo
``
gcloud config set project projectId
``
```
gcloud run deploy gcp-plain-cloudfunction \
--gen2 \
--region asia-south2 \
--entry-point com.brij.CloudFunctionDemo \
--runtime java21 \
--trigger-http \
--source . \
--memory=256MB \
--timeout=540 \
--verbosity=debug \
--allow-unauthenticated

```
### Deploy to cloud
gcloud run deploy cloudrun-function-pubsub-event-demo \
--source gcp-plain-cloudfunction/build/libs \
--function com.brij.PubSubFunction \
--base-image java21 \
--region asia-south2 \
--allow-unauthenticated
### test locally
Pubsub based api call
```
curl --location --request POST 'localhost:8080/' \
--header 'Content-Type: application/cloudevents+json' \
--data-raw '{
  "specversion": "1.0",
  "type": "google.cloud.pubsub.topic.v1.messagePublished",
  "source": "testtopic",
  "id": "12345",
  "data": {
    "message": {
      "data": "SGVsbG8gd29ybGQ="
    }
  }
}'
```
curl localhost:8080 \
-X POST \
-H "Content-Type: application/json" \
-H "ce-id: 123451234512345" \
-H "ce-specversion: 1.0" \
-H "ce-time: 2020-01-02T12:34:56.789Z" \
-H "ce-type: google.cloud.pubsub.topic.v1.messagePublished" \
-H "ce-source: //pubsub.googleapis.com/projects/MY-PROJECT/topics/MY-TOPIC" \
-d '{
"message": {
"data": "d29ybGQ=",
"attributes": {
"attr1":"attr1-value"
}
},
"subscription": "projects/MY-PROJECT/subscriptions/MY-SUB"
}'
```

```
## Curl for cloud storage

```courseignore
curl --location --request POST 'localhost:8080/' \
--header 'Content-Type: application/cloudevents+json' \
--data-raw '{
    "specversion": "1.0",
    "type": "google.cloud.storage.object.v1.finalized",
    "source": "projects/_/buckets/YOUR_BUCKET_NAME",
    "id": "12345",
    "time": "2025-01-07T00:00:00.000Z",
    "datacontenttype": "application/json",
    "data": {
         "kind": "storage#object",
        "id": "test-bucket20201/products.csv/1736265641719750",
        "name": "products.csv",
        "bucket": "test-bucket20201",
        "contentType": "text/csv"
    }
}'
```
```courseignore
curl localhost:8080 \
  -X POST \
  -H "Content-Type: application/json" \
  -H "ce-id: 123451234512345" \
  -H "ce-specversion: 1.0" \
  -H "ce-time: 2020-01-02T12:34:56.789Z" \
  -H "ce-type: google.cloud.storage.object.v1.finalized" \
  -H "ce-source: //storage.googleapis.com/projects/_/buckets/MY-BUCKET-NAME" \
  -H "ce-subject: objects/MY_FILE.txt" \
  -d '{
        "bucket": "MY_BUCKET",
        "contentType": "text/plain",
        "kind": "storage#object",
        "md5Hash": "...",
        "metageneration": "1",
        "name": "MY_FILE.txt",
        "size": "352",
        "storageClass": "MULTI_REGIONAL",
        "timeCreated": "2020-04-23T07:38:57.230Z",
        "timeStorageClassUpdated": "2020-04-23T07:38:57.230Z",
        "updated": "2020-04-23T07:38:57.230Z"
      }'
```

Commands:
gcloud functions list
gcloud topics list
``
`` install gcloud ``
``gcloud auth application-default login``
``gcloud projects list
























```
trigger:
```
gcloud eventarc triggers create cloud-storagetrigger  \
--location=asia \
--destination-run-service=cloud-runfunctiondemo-fromzip-cs  \
--destination-run-region=asia-south2 \
--event-filters="type=google.cloud.storage.object.v1.finalized" \
--event-filters="bucket=bp-demo-bucket" \
--service-account=116837538178-compute@developer.gserviceaccount.com