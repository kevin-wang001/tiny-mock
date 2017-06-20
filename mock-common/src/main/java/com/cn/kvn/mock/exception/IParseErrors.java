package com.cn.kvn.mock.exception;
/**
* @author wzy
* @date 2017年6月20日 下午2:01:20
*/
public interface IParseErrors<T> {
	/**
     * 采用枚举中定义的message作为返回信息
     * 
     * @return
     */
    public T parse();

    /**
     * 采用枚举中定义的message作为返回信息，并传递一些参数
     * 
     * @param args
     *            具体参数列表
     * @return
     */
    public T parse(Object... args);

    /**
     * 采用枚举中定义的code，使用自定义的message作为返回信息，并可能会带上一些参数
     * 
     * @param message
     *            错误信息
     * @param args
     *            具体参数列表
     * @return
     */
    public T parseMsg(String message, Object... args);
}
