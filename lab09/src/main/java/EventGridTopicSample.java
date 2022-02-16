import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridEvent;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.azure.messaging.eventgrid.EventGridPublisherClientBuilder;

import java.net.URISyntaxException;

public class EventGridTopicSample {

    private static String TOPIC_ENDPOINT = "<YOUR_EVENTGRID_TOPIC_ENDPOINT>";
    private static String TOPIC_ACCESS_KEY = "<YOUR_EVENTGRID_TOPIC_ACCESS_KEY>";

    public static void main(String args[]) {
        try {
            new EventGridTopicSample().publishToEventGrid();
        } catch (URISyntaxException e) {
            System.out.println(e);
        }
    }

    private void publishToEventGrid() throws URISyntaxException {
        // Create a client to send events of EventGridEvent schema
        EventGridPublisherClient<EventGridEvent> publisherClient = new EventGridPublisherClientBuilder()
                .endpoint(TOPIC_ENDPOINT)
                .credential(new AzureKeyCredential(TOPIC_ACCESS_KEY))
                .buildEventGridEventPublisherClient();
        // Create an EventGrid event
        EventGridEvent firstEvent = new EventGridEvent("New employee: Me At home",
                "Employees.Registration.New", BinaryData.fromObject(new Employee("Me", "At home")), "0.1");
        EventGridEvent secondEvent = new EventGridEvent("New employee: Leopold Bloom",
                "Employees.Registration.New", BinaryData.fromObject(new Employee("Leopold Bloom", "Ireland")), "0.1");
        // Send the events
        publisherClient.sendEvent(firstEvent);
        publisherClient.sendEvent(secondEvent);
    }

    class Employee {
        private String fullName;
        private String address;

        public Employee(String fullName, String address) {
            this.fullName = fullName;
            this.address = address;
        }
    }
}
