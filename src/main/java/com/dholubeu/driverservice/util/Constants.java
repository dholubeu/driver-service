package com.dholubeu.driverservice.util;

public class Constants {
    public static final String RESOURCE_ALREADY_EXISTS_MESSAGE =
            "Car with number %s has been already registered";
    public static final String RESOURCE_DOES_NOT_EXIST_BY_ID_MESSAGE = "Car with id %d does not exist";

    public static final String ILLEGAL_OPERATION_EXCEPTION_MESSAGE =
            "You have insufficient funds to transfer amount %d to the card";
    public static final String RESOURCE_DOES_NOT_EXIST_BY_EMAIL_MESSAGE = "Driver with email %s does not exist";

    public static final String URL =
            "https://nominatim.openstreetmap.org/search?q=%s&format=json&polygon_kml=1&addressdetails=1";


}
