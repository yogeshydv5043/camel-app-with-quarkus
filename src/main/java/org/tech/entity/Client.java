package org.tech.entity;

import jakarta.ws.rs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import java.io.InputStream;

public class Client {

    @FormParam("rid")
    private Long rid;

    @FormParam("date")
    private String date;

    @FormParam("from")
    private String from;

    @FormParam("to")
    private String to;

    @FormParam("xmlFile")
    @PartType("application/xml")
    private InputStream xmlFile;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public InputStream getXmlFile() {
        return xmlFile;
    }

    public void setXmlFile(InputStream xmlFile) {
        this.xmlFile = xmlFile;
    }
}



