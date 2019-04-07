package z.learn.baseline.model;

import z.learn.baseline.annotations.Attribute;
import z.learn.baseline.processor.ProcessorEnum;

public class AppEnvironment {
    private String tomcat;
    @Attribute(ProcessorEnum.OVERRID)
    private String java;

    @Attribute({ProcessorEnum.ENCRYPT, ProcessorEnum.OVERRID})
    private String javaConfig;

    public String getTomcat() {
        return tomcat;
    }

    public void setTomcat(String tomcat) {
        this.tomcat = tomcat;
    }

    public String getJava() {
        return java;
    }

    public void setJava(String java) {
        this.java = java;
    }

    public String getJavaConfig() {
        return javaConfig;
    }

    public void setJavaConfig(String javaConfig) {
        this.javaConfig = javaConfig;
    }
}
