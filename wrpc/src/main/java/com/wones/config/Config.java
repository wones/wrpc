package com.wones.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class Config {
    static Properties properties;
    static {
        try (InputStream in = Config.class.getResourceAsStream("/config.properties")){
            properties = new Properties();
            properties.load(in);
            }catch (IOException e){
            throw new ExceptionInInitializerError(e);
        }
    }
    public static String getZkServerAddress(){
        String value = properties.getProperty("zookeeper.serverAddress");
        if(value == null){
            return "127.0.0.1:2181";
        }else {
            return value;
        }

    }
    public static String getServerHost(){
        String value = properties.getProperty("server.host");
        if(value == null){
            return "127.0.0.1";
        }else {
            return value;
        }

    }
    public static int getServerPort() {
        String value = properties.getProperty("server.port");
        if(value == null) {
            return 8080;
        } else {
            return Integer.parseInt(value);
        }
    }
    public static int getSchedule() {
        String value = properties.getProperty("schedule");
        if(value == null) {
            return 0;
        } else {
            return Integer.parseInt(value);
        }
    }
    public static byte getSerializerAlgorithm() {
        String value = properties.getProperty("serializer.algorithm");
        if(value == null) {
            return 0;
        } else {
            return Byte.parseByte(value);
        }
    }
    public static short getMessageTypeRequest() {
        String value = properties.getProperty("messageType.request");
        if(value == null) {
            return 0;
        } else {
            return Short.parseShort(value);
        }
    }
    public static short getMessageTypeResponse() {
        String value = properties.getProperty("messageType.response");
        if(value == null) {
            return 1;
        } else {
            return Short.parseShort(value);
        }
    }
}
