package com.mediasoft.gestion_produit.communs.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@XmlRootElement(name = "responseObject")
public class ResponseObject implements Serializable {
    @JsonProperty("status")
    @XmlElement(name = "status")
    private String status;
    @JsonProperty("codeStatus")
    @XmlElement(name = "codeStatus")
    private int codeStatus;
    @JsonProperty("message")
    @XmlElement(name = "message")
    private String message;


    public ResponseObject(String status, int codeStatus, String message) {
        this.status = status;
        this.codeStatus = codeStatus;
        this.message = message;
    }


}
