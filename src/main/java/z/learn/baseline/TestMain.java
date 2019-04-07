package z.learn.baseline;

import org.springframework.util.ClassUtils;
import z.learn.baseline.annotations.Attribute;
import z.learn.baseline.model.AppEnvironment;
import z.learn.baseline.model.Baseline;
import z.learn.baseline.model.BaselineModel;
import z.learn.baseline.processor.ProcessorEnum;

import java.lang.reflect.Field;
import java.util.Arrays;

public class TestMain {

    public static void main(String[] args) {
        BaselineModel newCreated = new BaselineModel();
        AppEnvironment newEnv = new AppEnvironment();
        newEnv.setJavaConfig("1.8.3");
        newEnv.setJavaConfig("sec=on");
        newCreated.setAppEnvironment(newEnv);

        BaselineModel loadedFromDb = new BaselineModel();
        AppEnvironment oldEnv = new AppEnvironment();
        oldEnv.setJavaConfig("1.8.2");
        oldEnv.setJavaConfig("sec=off");
        loadedFromDb.setAppEnvironment(oldEnv);


        Field[] fields = Baseline.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> f.getAnnotation(Attribute.class) != null).forEach(f -> System.out.println(f.getName()));
    }

    void processField(Field f, ProcessorEnum processor, Baseline target) {
        if (ClassUtils.isPrimitiveOrWrapper(f.getType())) {

        }
    }
}
