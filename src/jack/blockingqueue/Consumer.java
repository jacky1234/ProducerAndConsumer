package jack.blockingqueue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ���ߣ����.
 * ���䣺847564732@qq.com
 * ��������:2016-3-7
 * ���ܣ��������߳�
 */

public class Consumer implements Runnable {
	private volatile BlockingQueue<String> queue;
	private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;
	
	public Consumer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName() + "_�����������̣߳�");
		Random r = new Random();
		boolean isRunning = true;
		try {
			while (isRunning) {
				System.out.println(Thread.currentThread().getName()
						+ "\t\t_���Ӷ��л�ȡ����...");
				String data;
				data = queue.poll(2, TimeUnit.SECONDS);
//				data = queue.take();
				if (null != data) {
					System.out.println(Thread.currentThread().getName()
							+ "\t\t_�Ӷ�����ȡ����Ʒ�������������ݣ�" + data);
					Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
					System.out.println(Thread.currentThread().getName()
							+ "\t\t_����������ݣ�" + data);
				} else {
					// ����2s��û���ݣ���Ϊ���������̶߳��Ѿ��˳����Զ��˳������̡߳�
					isRunning = false;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} finally {
			System.out.println(Thread.currentThread().getName()
					+ "\t\t_�˳��������̣߳�");
		}
	}
	
}
