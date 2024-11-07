package ft.btp.enums;

/**
 * @Description TODO
 * @Classname GenerateOrderNumEnum
 * @Date 2023/8/9 13:30
 * @Created by TJSLucian
 */
public enum GenerateOrderNumEnum {

    C66("C66", 1L, 1L, 169155810691L);

    private String name;
    //机器ID
    private Long workerId;
    //机房ID
    private Long datacenterId;
    // 代表一毫秒内生成的多个id的最新序号  12位 4096 -1 = 4095 个
    private Long sequence;

    GenerateOrderNumEnum(String name, Long workerId, Long datacenterId, Long sequence) {
        this.name = name;
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public Long getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(Long datacenterId) {
        this.datacenterId = datacenterId;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }
}
