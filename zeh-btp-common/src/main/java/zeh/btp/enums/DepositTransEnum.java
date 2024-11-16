package zeh.btp.enums;

/**
 * @ClassName DepositTransEnum
 * @Description 存款审批提案状态
 * @Author TJSAlex
 * @Date 2023/6/30 11:36
 * @Version 1.0
 **/
public enum DepositTransEnum {

    /**
     * 0=Pending或waiting(都是一样的，可能不同页面文案显示显示不同)；
     * 1=processing, 一个中间状态，是人工审核中的一个状态（已成历史现在没有这个状态了）；
     * 2=Approved或Success，即成功状态；
     * -1=Cancel by customer，用户取消，当前没有给用户这个操作，单可以预留着;
     * -2=Cancel by office,  后台取消了订单；
     * -4=系统失败, BGM页面显示的Denied；
     */
    //-1 Pending 刚创建，等待一审
    Pending(0, "Pending"),

    //0 Process 一审通过/二审通过
    Process(1, "Process"),

    //1 Approved 订单成功完成
    Approved(2, "Approved"),

    //Rejected 订单失败
    Rejected(-1, "Rejected");

    private Integer code;
    private String name;

    DepositTransEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
