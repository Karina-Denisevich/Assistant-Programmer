package com.github.karina_denisevich.app.common.exception.model;


public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -8658131859261427602L;

    private String service;

    public ServiceException(String message, String service) {
        super(message);
        this.service = service;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
