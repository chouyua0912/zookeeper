package z.learn.baseline.processor;

public enum ProcessorEnum {
    JSON(new JsonProcessor()),
    ENCRYPT(new JsonProcessor()),
    INHERIT(new JsonProcessor()),
    OVERRID(new JsonProcessor());

    ProcessorEnum(Processor processor) {
        this.processor = processor;
    }

    private Processor processor;

    public Processor getProcessor() {
        return processor;
    }
}
