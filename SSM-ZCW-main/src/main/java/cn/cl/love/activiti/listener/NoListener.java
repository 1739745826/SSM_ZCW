package cn.cl.love.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 标题：流程监听器
 * 作者：何处是归程
 * 时间：2020/2/6 - 16:55
 */
public class NoListener implements ExecutionListener {
	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
		System.out.println("审批拒绝");
	}
}
