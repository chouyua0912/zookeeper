package z.learn.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import z.learn.service.ConfigService;


import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/configs")
public class ConfigApi {


    @RequestMapping(method = RequestMethod.GET)
    public Map<String, String> getConfig(@RequestParam(value = "key", required = false) String key) {
        return configService.getConfig(key);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, String> setConfig(@RequestParam("key") String key, @RequestParam("value") String value) {
        return configService.setConfig(key, value);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Map<String, String> deleteConfig(@RequestParam("key") String key) {
        return configService.deleteConfig(key);
    }

    @Resource
    private ConfigService configService;
}
