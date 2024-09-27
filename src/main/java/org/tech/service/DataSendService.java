package org.tech.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.ProducerTemplate;
import org.tech.dto.request.MessageData;
import org.tech.model.Client;
import org.tech.repository.ClientRefRepository;

@ApplicationScoped
public class DataSendService {

    @Inject
    private ProducerTemplate producerTemplate;

    @Inject
    private ClientRefRepository clientRefRepository;

    public String validateAndSend(Client requestData) {
        // Check if either the rid or date is valid
        if (validateRidAndDate(requestData.getRid(), requestData.getDate())) {
            //set this data in MessageData
            MessageData messageData=new MessageData();
           messageData.setFrom(requestData.getFrom());
           messageData.setTo(requestData.getTo());
           messageData.setXmlData(requestData.getXml());

            if (messageData == null) {
                throw new IllegalArgumentException("messageData is null!");
            }else {
               // System.out.println(messageData.toString());
            producerTemplate.sendBody("direct:Validationroutes", messageData);
            }
            System.out.println("Validation successfully with this id : " + requestData.getRid());
            return "Validation successfully, message sent";
         }else {
            // If both rid and date are invalid, return validation failed
            System.out.println("Validation failed for RID: " + requestData.getRid());
            return "Validation failed";
        }
    }


    private boolean validateRidAndDate(long id,String date) {

        return clientRefRepository.existsByIdAndDate(id,date);
    }
}
