package jack.blockingqueue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 作者：杨剑飞.
 * 邮箱：847564732@qq.com
 * 创建日期:2016-3-7
 * 功能：消费者线程
 */

public class Consumer implements Runnable {
	private volatile BlockingQueue<String> queue;
	private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;
	
	public Consumer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName() + "_启动消费者线程！");
		Random r = new Random();
		boolean isRunning = true;
		try {
			while (isRunning) {
				System.out.println(Thread.currentThread().getName()
						+ "\t\t_正从队列获取数据...");
				String data;
				data = queue.poll(2, TimeUnit.SECONDS);
//				data = queue.take();
				if (null != data) {
					System.out.println(Thread.currentThread().getName()
							+ "\t\t_从队列中取出产品，正在消费数据：" + data);
					Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
					System.out.println(Thread.currentThread().getName()
							+ "\t\t_完成消费数据：" + data);
				} else {
					// 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
					isRunning = false;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} finally {
			System.out.println(Thread.currentThread().getName()
					+ "\t\t_退出消费者线程！");
		}
	}
	
}
