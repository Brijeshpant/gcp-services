package com.brij;

import com.google.cloud.functions.CloudEventsFunction;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.cloudevents.CloudEvent;
import io.cloudevents.CloudEventData;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class CloudStorageFunction implements CloudEventsFunction {
    private static final Logger logger = Logger.getLogger(CloudStorageFunction.class.getName());
    private static final Storage storage = StorageOptions.getDefaultInstance().getService();
    private Gson gson = new Gson();

    @Override
    public void accept(CloudEvent event) throws IOException {
        CloudEventData data = event.getData();
        logger.info("Cloud Event data: " + new String(data.toBytes()));
        byte[] bytes = event.getData().toBytes();
        JsonObject cloudEventJson = gson.fromJson(new String(bytes), JsonObject.class);
        String bucketName = cloudEventJson.get("bucket").getAsString();
        String fileName = cloudEventJson.get("name").getAsString();

        logger.info("Received Cloud Storage event for bucket: " + bucketName + ", file: " + fileName);

        BlobId blobId = BlobId.of(bucketName, fileName);
        Blob blob = storage.get(blobId);
        if (blob != null) {
            // Read the file content (InputStream) from the Cloud Storage Blob
            byte[] fileBytes = blob.getContent();  // Get file content as byte[]

            try (InputStream fileStream = new ByteArrayInputStream(fileBytes)) {
                // Process the file content here (e.g., read, parse, etc.)
                String fileContent = new String(fileStream.readAllBytes(), StandardCharsets.UTF_8);
                logger.info("File content: " + fileContent);  // Log the file content
            } catch (Exception ex) {
                logger.severe("Error processing Cloud Event: " + ex.getMessage());
            }
        } else {
            logger.warning("File not found in bucket: " + bucketName);
        }

    }
}
