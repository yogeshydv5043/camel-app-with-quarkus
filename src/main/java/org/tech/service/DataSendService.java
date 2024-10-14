package org.tech.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tech.dto.request.MessageData;
import org.tech.dto.response.ValidationResponse;
import org.tech.entity.Client;
import org.tech.repository.ClientRefRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@ApplicationScoped
public class DataSendService {

    private static final Logger log = LoggerFactory.getLogger(DataSendService.class);
    @Inject
    private ProducerTemplate producerTemplate;

    @Inject
    private ClientRefRepository clientRefRepository;

    public Response validateAndSend(Client requestData) throws IOException {
        // Check if either the rid or date is valid
        if (validateRidAndDate(requestData.getRid(), requestData.getDate())) {

            ValidationResponse validationResponse = null;

            // Ensure the InputStream is not null
            if (requestData.getXmlFile() != null) {
                // Convert the InputStream to a String once and reuse it
                String xmlContent = convertInputStreamToString(requestData.getXmlFile());
                validationResponse = producerTemplate.requestBody("direct:validateXml", xmlContent, ValidationResponse.class);

                MessageData messageData = new MessageData();
                messageData.setFrom(requestData.getFrom());
                messageData.setXmlData(xmlContent); // Reusing the converted string
                messageData.setTo(requestData.getTo());

                // Log the XML data before validation for debugging
                System.out.println("XML Data before Validation:\n" + xmlContent);
                // Check if validation is successful
                if (validationResponse.isValid()) {
                    log.info("XML Data After Validation : " + xmlContent);  // Log validated data
                    producerTemplate.requestBody("direct:sendData", messageData, String.class);
                    return Response.status(Response.Status.OK).entity("Validation Successful. Message Sending...").build();
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity(validationResponse.getErrorMessage()).build();
                }
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Validation failed: XML file is null")
                        .build();
            }

        } else {
            String error = "User Authentication failed with This Id : " + requestData.getRid() + " or Date : " + requestData.getDate();
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    //Auth with RID And Date
    private boolean validateRidAndDate(long id, String date) {
        return clientRefRepository.existsByIdAndDate(id, date);
    }


    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        System.out.println("Input Stream : "+inputStream);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

}
