package jack.blockingqueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 作者：杨剑飞. 邮箱：847564732@qq.com 创建日期:2016-3-7 功能：
 */

public class BlockingQueueTest {
	public static void main(String[] args) throws InterruptedException {
		// 声明一个容量为10的缓存队列
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10);

		Producer producer1 = new Producer(queue);
		Producer producer2 = new Producer(queue);
		Producer producer3 = new Producer(queue);
		Consumer consumer = new Consumer(queue);

		// 借助Executors
		ExecutorService service = Executors.newCachedThreadPool();
		// 启动线程
		new Thread(producer1).start();
		new Thread(producer2).start();
		new Thread(producer3).start();
		new Thread(consumer).start();

		// service.submit(producer1);
		// service.submit(producer2);
		// service.submit(producer3); //需要返回值，用 submint
		// service.execute(consumer); //不需要返回值，用 execute
		// service.invokeAll(..);

		// 执行10s
		Thread.sleep(2 * 1000);
		producer1.stop(); // 生产者退出
		producer2.stop(); // 生产者退出
		producer3.stop(); // 生产者退出
		System.out.println("所有生产者退出");

		Thread.sleep(2000);
		// 退出Executor
		service.shutdown(); // 不可以再submit execute新的task，已经submit的将继续执行。
		System.out.println("关闭线程池");
	}
}
