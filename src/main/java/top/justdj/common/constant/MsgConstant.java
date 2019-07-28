package top.justdj.common.constant;

/**
 * 系统提示静态变量
 *
 * @author EricShen
 * @date 2018-04-09
 */
public class MsgConstant {

  /**
   * 操作成功
   */
  public static final String MSG_OPERATION_SUCCESS = "操作成功！";

  /**
   * 操作失败
   */
  public static final String MSG_OPERATION_FAILED = "操作失败！";

  /**
   * 删除时，提示有子节点无法删除
   */
  public static final String MSG_HAS_CHILD = "操作失败，当前所选数据有子节点数据！";

  /**
   * 加载表单数据错误提示
   */
  public static final String MSG_INIT_FORM = "查询数据为空";

  /**
   * 批量操作数据项不是全部所选
   */
  public static String batchOperation(int total, int process) {
    return "本次共处理：" + String.valueOf(total) + "条，成功：" + String.valueOf(process) + "条！";
  }

  /**
   * 通用变量，表示可用、禁用、显示、隐藏
   */
  public enum StatusType {

    /**
     * 禁用，隐藏
     */
    DISABLE(0),

    /**
     * 可用，显示
     */
    ENABLE(1),

    /**
     * 显示
     */
    SHOW(1),

    /**
     * 隐藏
     */
    HIDDEN(0),

    /**
     * 存在，未删除
     */
    EXISTING(1),

    /**
     * 不存在，已删除
     */
    DELETED(0);

    private int value;

    private StatusType(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

  }
}
