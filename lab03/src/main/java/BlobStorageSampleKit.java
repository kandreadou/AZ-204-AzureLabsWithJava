import com.azure.storage.blob.*;
import com.azure.storage.blob.models.StorageAccountInfo;
import com.azure.storage.common.StorageSharedKeyCredential;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class BlobStorageSampleKit {

    static String BLOB_SERVICE = "<YOUR_BLOB_SERVICE_ENDPOINT>";
    static String STORAGE_ACCOUNT_NAME = "<YOUR_STORAGE_ACCOUNT_NAME>";
    static String ACCOUNT_KEY = "<ONE_OF_THE_STORAGE_ACCOUNT_KEYS>";
    static String DOG_CONTAINER_NAME = "dogs";
    static String DOG_FILE = "/dog.jpeg";

    public static void main(String args[]) {
        try {
            new BlobStorageSampleKit().listContainersAndBlobs();
        } catch (URISyntaxException e) {
            System.out.println(e);
        }
    }

    public void listContainersAndBlobs() throws URISyntaxException {
        StorageSharedKeyCredential credential = new StorageSharedKeyCredential(STORAGE_ACCOUNT_NAME, ACCOUNT_KEY);
        /* Create a new BlobServiceClient with a shared key credential */
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint(BLOB_SERVICE)
                .credential(credential)
                .buildClient();
        /* Get some account info */
        StorageAccountInfo accountInfo = blobServiceClient.getAccountInfo();
        System.out.println("Account name: " + STORAGE_ACCOUNT_NAME);
        System.out.println("Account kind: " + accountInfo.getAccountKind());
        System.out.println("Account SKU: " + accountInfo.getSkuName());
        /* List all containers in storage account */
        blobServiceClient.listBlobContainers().forEach(container -> {
            System.out.println("Container name: " + container.getName());
            /* List all blobs in container */
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(container.getName());
            blobContainerClient.listBlobs().forEach(blobItem -> {
                System.out.println("Blob name: " + blobItem.getName());
            });
        });
        /* Create new container and upload a blob */
        BlobContainerClient dogContainer = blobServiceClient.createBlobContainer(DOG_CONTAINER_NAME);
        BlobClient blobClient = dogContainer.getBlobClient(DOG_FILE);
        URL resource = getClass().getClassLoader().getResource(DOG_FILE);
        blobClient.uploadFromFile(Paths.get(resource.toURI()).toString());
    }
}
