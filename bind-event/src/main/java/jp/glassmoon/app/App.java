package jp.glassmoon.app;

import java.io.File;

import jp.glassmoon.events.GerritEvent;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gerrit.server.events.PatchSetCreatedEvent;

public class App
{
    public static void main( String[] args ) throws Exception
    {
        File f = new File("event.txt");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        GerritEvent ev = mapper.readValue(f, GerritEvent.class);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ev));

        if (new PatchSetCreatedEvent().type.equals(ev.type)) {
            PatchSetCreatedEvent event = mapper.readValue(f, PatchSetCreatedEvent.class);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(event));
        }
    }
}
