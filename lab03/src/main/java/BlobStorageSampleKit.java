import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.StorageAccountInfo;
import com.azure.storage.common.StorageSharedKeyCredential;

public class BlobStorageSampleKit {

    static String BLOB_SERVICE = "<YOUR_BLOB_SERVICE_ENDPOINT>";
    static String STORAGE_ACCOUNT_NAME = "<YOUR_STORAGE_ACCOUNT_NAME>";
    static String ACCOUNT_KEY = "<ONE_OF_THE_STORAGE_ACCOUNT_KEYS>";

    public static void main(String args[]) {
        listContainers();
    }

    public static void listContainers() {
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
        });

    }
}
