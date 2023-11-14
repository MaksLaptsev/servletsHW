package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

@UtilityClass
public class JsonUtil {
    private final ObjectMapper mapper = new ObjectMapper();

    public Map<?,?> getMapOfObjects (String nameFile, Class<? extends Map<?,?>> collectionClass, Class<?> keyClass, Class<?> valueClass){
        TypeFactory factory = TypeFactory.defaultInstance();
        MapType type = factory.constructMapType(collectionClass, keyClass, valueClass);
        URL path = JsonUtil.class.getClassLoader().getResource(nameFile);
        try {
            return mapper.readValue(path, type);
        } catch (IOException e) {
            System.out.println("Not found file with name: "+nameFile);
        }
        return null;
    }

    public String objectToJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    public Object jsonToObject(String json, Class<?> objClass) throws JsonProcessingException {
        return mapper.readValue(json,objClass);
    }


}
