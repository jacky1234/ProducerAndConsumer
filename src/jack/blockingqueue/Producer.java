package jack.blockingqueue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ���ߣ����.
 * ���䣺847564732@qq.com
 * ��������:2016-3-7
 * ���ܣ��������߳�
 */

public class Producer implements Runnable {
	private boolean isRunning = true;
	private volatile BlockingQueue queue;
	private static AtomicInteger count = new AtomicInteger();
	private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;
	
	public Producer(BlockingQueue queue) {
		this.queue = queue;
	}

	public void run() {
		String data = null;
		Random r = new Random();

		System.out.println(Thread.currentThread().getName() + "_�����������̣߳�");
		try {
			while (isRunning) {
				System.out.println(Thread.currentThread().getName()
						+ "_������������...");
				Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));

				data = "data:" + count.incrementAndGet();

				//�����ݼ��뵽 BlockingQueue ��
				if (queue.offer(data, 5, TimeUnit.SECONDS)) {
					System.out.println(Thread.currentThread().getName()
							+ "_�����ݣ�" + data + "�������...");
				} else {
					System.out.println(Thread.currentThread().getName()
							+ "_��������ʧ�ܣ�" + data);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} finally {
			System.out.println(Thread.currentThread().getName() + "_�˳��������̣߳�");
		}
	}

	public void stop() {
		isRunning = false;
	}
}
