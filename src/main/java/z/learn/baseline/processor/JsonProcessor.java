package z.learn.baseline.processor;

import z.learn.baseline.model.Baseline;
import z.learn.baseline.model.BaselineModel;

public class JsonProcessor implements Processor {
    @Override
    public boolean onload(Baseline loaded, BaselineModel transformed) {
        return false;
    }

    @Override
    public boolean onSave(BaselineModel received, Baseline transformed) {
        return false;
    }
}
