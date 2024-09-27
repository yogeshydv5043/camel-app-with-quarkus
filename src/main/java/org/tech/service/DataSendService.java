package org.tech.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.ProducerTemplate;
import org.tech.dto.request.MessageData;
import org.tech.model.Client;
import org.tech.repository.ClientRefRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@ApplicationScoped
public class DataSendService {

    @Inject
    private ProducerTemplate producerTemplate;

    @Inject
    private ClientRefRepository clientRefRepository;

    public String validateAndSend(Client requestData) {
        // Check if either the rid or date is valid
        if (validateRidAndDate(requestData.getRid(), requestData.getDate())) {
            MessageData messageData = new MessageData();
            messageData.setFrom(requestData.getFrom());
            messageData.setTo(requestData.getTo());

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestData.getXmlFile()))) {
                StringBuilder xmlBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    xmlBuilder.append(line).append("\n");
                }
                messageData.setXmlData(xmlBuilder.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return "Error reading XML file: " + e.getMessage();
            }

            if (messageData == null) {
                return "messageData is null!";
            } else {
                // Send the data to the validation route
                try {
                    String response = producerTemplate.requestBody("direct:Validationroutes", messageData.getXmlData(), String.class);
                    producerTemplate.requestBody("direct:sendData", messageData, String.class);
                    return response;

                } catch (Exception e) {
                    return "Error sending to queue: " + e.getMessage();
                }
            }
        } else {
            // If rid or date is invalid
            System.out.println("Validation failed for RID: " + requestData.getRid());
            return "Validation failed for RID: " + requestData.getRid();
        }
    }


    private boolean validateRidAndDate(long id,String date) {

        return clientRefRepository.existsByIdAndDate(id,date);
    }
}
