package jack.blockingqueue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者：杨剑飞.
 * 邮箱：847564732@qq.com
 * 创建日期:2016-3-7
 * 功能：生产者线程
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

		System.out.println(Thread.currentThread().getName() + "_启动生产者线程！");
		try {
			while (isRunning) {
				System.out.println(Thread.currentThread().getName()
						+ "_正在生产数据...");
				Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));

				data = "data:" + count.incrementAndGet();

				//将数据加入到 BlockingQueue 中
				if (queue.offer(data, 5, TimeUnit.SECONDS)) {
					System.out.println(Thread.currentThread().getName()
							+ "_将数据：" + data + "放入队列...");
				} else {
					System.out.println(Thread.currentThread().getName()
							+ "_放入数据失败：" + data);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} finally {
			System.out.println(Thread.currentThread().getName() + "_退出生产者线程！");
		}
	}

	public void stop() {
		isRunning = false;
	}
}
