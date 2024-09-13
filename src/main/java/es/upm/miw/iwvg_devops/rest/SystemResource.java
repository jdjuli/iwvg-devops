package es.upm.miw.iwvg_devops.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.Calendar;
import java.util.GregorianCalendar;

@RestController
@RequestMapping(SystemResource.SYSTEM)

public class SystemResource {
    static final String SYSTEM = "/system";

    static final String APP_INFO = "/app-info";
    static final String VERSION_BADGE = "/version-badge";
    static final String RANDOM_INTEGER = "/random-integer";
    static final String CURRENT_TIME = "/current-time";

    @Value("${application.name}")
    private String applicationName;
    @Value("${build.version}")
    private String buildVersion;
    @Value("${build.timestamp}")
    private String buildTimestamp;

    @GetMapping(value = VERSION_BADGE, produces = {"image/svg+xml"}) // http://localhost:8080/system/version-badge
    public byte[] generateBadge() { // http://localhost:8080/system/badge
        return new Badge().generateBadge("Heroku", "v" + buildVersion).getBytes();
    }

    @GetMapping(value = APP_INFO) // http://localhost:8080/system/app-info
    public String applicationInfo() {
        return "{\"version\":\"" + this.applicationName + "::" +
                this.buildVersion + "::" + this.buildTimestamp + "\"}";
    }
    
    @GetMapping(value = RANDOM_INTEGER)
    public String randomInteger() {
        String value = String.valueOf((new Random()).nextInt(Integer.MAX_VALUE));
        return "{\"value\": \"" + value + "\"}";
    }

    @GetMapping(value = CURRENT_TIME)
    public String currentTime() {
        Calendar now = GregorianCalendar.getInstance();
        return "{\"time\": \"" + String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", now) + "\"}";
    }
}
