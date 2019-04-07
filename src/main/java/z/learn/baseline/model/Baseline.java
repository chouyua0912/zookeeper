package z.learn.baseline.model;

import z.learn.baseline.annotations.Attribute;

import java.util.Map;

import static z.learn.baseline.processor.ProcessorEnum.ENCRYPT;
import static z.learn.baseline.processor.ProcessorEnum.JSON;

/**
 * DB里存储的数据结构
 * <p>
 * 创建出来直接就是当前基线
 * <p>
 * post   /components/{componentId}/baselines               创建当前基线。不需要创建出来不是当前基线的基线了。
 * get    /components/{componentId}/baselines?regionId=a&zoneId=b&schema=c.. 查询组件的基线 是否限制当前基线？
 * put    /components/{componentId}/baselines/{baselineId}  当前基线打标
 * delete /components/{componentId}/baselines/{baselineId}  删除当前极限
 * <p>
 * get    /components/baselines                             基线批量查询结构
 * <p>
 * post   /components/{componentId}/locks                   锁可以做系统间变更互斥的控制
 * get    /components/{componentId}/locks
 * put    /components/{componentId}/locks
 * delete /components/{componentId}/locks
 * <p>
 * OS基线，收编到上面的基线
 * 插件基线
 * 公共配置，云服务配置，服务配置
 */
public class Baseline {
    public enum releaseCategoryEnum {
        production,
        gray
    }

    private Long baselineId;        //唯一标示
    private Integer componentId;    //required 有region概念的时候有id就可以了
    private String regionId;        //required 当前业务树模型虽然不必要，之后改造成没有region的业务树时候必填了
    private String zoneId;
    private String schema;          //required
    private String releaseCategory; //required

    private String componentName;   //展示类信息。服务实例和业务树都能拿到 只能做展示字段 名字可能会变化
    private String regionName;      //展示类信息。名字可能被修改

    private boolean using;          //当前正在使用的基线。不是正在使用的基线和有问题的基线都是回滚时候可以使用的基线
    private boolean faulted;        //有问题基线

    private String componentVersion;
    private String componentPkg;
    private String vars;

    private transient Map<String, Object> appEnv;
    private String appEnvironment;
    private transient Map<String, Object> runningEnv;//运行环境，需要考虑数据的继承。通过解析成bean，根据上面的注解来决定是否要继承数据。
    private String runningEnvironment;
    private transient Map<String, Object> vmEnv;//兼容原来的OS基线内容。设置之后不允许变化
    private String vmEnvironment;
    private transient Map<String, Object> baseEnv;
    private String baseEnvironment;

    public Long getBaselineId() {
        return baselineId;
    }

    public void setBaselineId(Long baselineId) {
        this.baselineId = baselineId;
    }

    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getReleaseCategory() {
        return releaseCategory;
    }

    public void setReleaseCategory(String releaseCategory) {
        this.releaseCategory = releaseCategory;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public boolean isUsing() {
        return using;
    }

    public void setUsing(boolean using) {
        this.using = using;
    }

    public boolean isFaulted() {
        return faulted;
    }

    public void setFaulted(boolean faulted) {
        this.faulted = faulted;
    }

    public String getComponentVersion() {
        return componentVersion;
    }

    public void setComponentVersion(String componentVersion) {
        this.componentVersion = componentVersion;
    }

    public String getComponentPkg() {
        return componentPkg;
    }

    public void setComponentPkg(String componentPkg) {
        this.componentPkg = componentPkg;
    }

    public String getVars() {
        return vars;
    }

    public void setVars(String vars) {
        this.vars = vars;
    }

    public Map<String, Object> getAppEnv() {
        return appEnv;
    }

    public void setAppEnv(Map<String, Object> appEnv) {
        this.appEnv = appEnv;
    }

    public String getAppEnvironment() {
        return appEnvironment;
    }

    public void setAppEnvironment(String appEnvironment) {
        this.appEnvironment = appEnvironment;
    }

    public Map<String, Object> getRunningEnv() {
        return runningEnv;
    }

    public void setRunningEnv(Map<String, Object> runningEnv) {
        this.runningEnv = runningEnv;
    }

    public String getRunningEnvironment() {
        return runningEnvironment;
    }

    public void setRunningEnvironment(String runningEnvironment) {
        this.runningEnvironment = runningEnvironment;
    }

    public Map<String, Object> getVmEnv() {
        return vmEnv;
    }

    public void setVmEnv(Map<String, Object> vmEnv) {
        this.vmEnv = vmEnv;
    }

    public String getVmEnvironment() {
        return vmEnvironment;
    }

    public void setVmEnvironment(String vmEnvironment) {
        this.vmEnvironment = vmEnvironment;
    }

    public Map<String, Object> getBaseEnv() {
        return baseEnv;
    }

    public void setBaseEnv(Map<String, Object> baseEnv) {
        this.baseEnv = baseEnv;
    }

    public String getBaseEnvironment() {
        return baseEnvironment;
    }

    public void setBaseEnvironment(String baseEnvironment) {
        this.baseEnvironment = baseEnvironment;
    }
}
