package org.tech.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tech.dto.request.MessageData;
import org.tech.dto.response.ValidationResponse;
import org.tech.model.Client;
import org.tech.repository.ClientRefRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@ApplicationScoped
public class DataSendService {

    private static final Logger log = LoggerFactory.getLogger(DataSendService.class);
    @Inject
    private ProducerTemplate producerTemplate;

    @Inject
    private ClientRefRepository clientRefRepository;

    public Response validateAndSend(Client requestData) {
        // Check if either the rid or date is valid
        if (validateRidAndDate(requestData.getRid(),requestData.getDate())) {

            ValidationResponse validationResponse = null;
            try {
                validationResponse = producerTemplate.requestBody("direct:validateXml", requestData.getXmlFile(), ValidationResponse.class);

                //
                MessageData messageData = new MessageData();
                messageData.setFrom(requestData.getFrom());
                messageData.setXmlData(readInputStream(requestData.getXmlFile()));
                messageData.setTo(requestData.getTo());

                //Check validation true or not
                if (validationResponse.isValid()) {
                    producerTemplate.requestBody("direct:sendData", messageData, String.class);
                    return Response.status(Response.Status.OK).entity("Validation Successfully Message Sending ...").build();
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity(validationResponse.getErrorMessage()).build();
                }
            } catch (Exception e) {
                //  throw new RuntimeException(e);
                assert validationResponse != null;
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Validation failed: " + validationResponse.getErrorMessage())
                        .build();
            }

        } else {
            // If rid or date is invalid
           // System.out.println("Validation failed for RID: " + requestData.getRid());
            String error ="User Authentication failed with This Id : "+requestData.getRid()+" or Date : "+requestData.getDate();
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
      //  return null;
    }


    //Auth with RID And Date
    private boolean validateRidAndDate(long id,String date) {
        return clientRefRepository.existsByIdAndDate(id,date);
    }

    //Converting String
    private String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n"); // Append each line and add a newline
            }
        }
        return result.toString(); // Convert StringBuilder to String
    }
}
