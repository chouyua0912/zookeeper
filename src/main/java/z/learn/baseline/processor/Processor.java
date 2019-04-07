package z.learn.baseline.processor;

import z.learn.baseline.model.Baseline;
import z.learn.baseline.model.BaselineModel;

public interface Processor {
    boolean onload(Baseline loaded, BaselineModel transformed);

    boolean onSave(BaselineModel received, Baseline transformed);
}
