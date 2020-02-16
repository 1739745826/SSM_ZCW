package cn.cl.love.exception;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/1/28 - 13:08
 */
public class LoginFailException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LoginFailException(String message){
        super(message);
        System.out.println("密码出错喽");
    }
}
